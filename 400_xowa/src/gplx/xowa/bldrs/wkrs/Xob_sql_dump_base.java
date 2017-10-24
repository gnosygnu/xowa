/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.bldrs.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.flds.*; import gplx.core.ios.*; import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.bldrs.sql_dumps.*; import gplx.xowa.wikis.tdbs.bldrs.*;
public abstract class Xob_sql_dump_base extends Xob_itm_dump_base implements Xob_cmd, Gfo_invk {
	private final    Xosql_dump_parser parser; protected boolean fail = false;
	public abstract String Cmd_key();
	public Xob_sql_dump_base() {
		this.parser = New_parser();
	}
	public Io_url Src_fil() {return src_fil;} private Io_url src_fil;
	public Io_url_gen Make_url_gen() {return make_url_gen;} private Io_url_gen make_url_gen;
	public Xob_sql_dump_base Src_dir_manual_(Io_url v) {src_dir_manual = v; return this;} private Io_url src_dir_manual;
	public abstract String Sql_file_name();
	protected abstract Xosql_dump_parser New_parser();
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		this.Init_dump(this.Cmd_key());
		make_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("make"));
		if (src_fil == null) {
			Io_url src_dir = src_dir_manual == null ? wiki.Fsys_mgr().Root_dir() : src_dir_manual;
			src_fil = Xob_io_utl_.Find_nth_by_wildcard_or_null(src_dir, Sql_file_name() + ".sql", ".gz", ".sql");
			if (src_fil == null) {
				String msg = String_.Format(".sql file not found in dir. Some data will not be imported.\nPlease download the file for your wiki from dumps.wikimedia.org.\nfile={0} dir={1}", Sql_file_name(), wiki.Fsys_mgr().Root_dir());
				app.Usr_dlg().Warn_many("", "", msg);
				app.Gui_mgr().Kit().Ask_ok("", "", msg);
				fail = true;
				return;
			}
		}
		parser.Src_fil_(src_fil);
		Cmd_bgn_hook(bldr, parser);
	}	protected Gfo_fld_wtr fld_wtr = Gfo_fld_wtr.xowa_();
	public abstract void Cmd_bgn_hook(Xob_bldr bldr, Xosql_dump_parser parser);
	public void Cmd_run() {
		if (fail) return;
		parser.Parse(bldr.Usr_dlg());
	}
	@gplx.Virtual public void Cmd_end() {
		if (fail) return;
		Xobdc_merger.Basic(bldr.Usr_dlg(), dump_url_gen, temp_dir.GenSubDir("sort"), sort_mem_len, Io_line_rdr_key_gen_all.Instance, new Io_sort_fil_basic(bldr.Usr_dlg(), make_url_gen, make_fil_len));
	}
	public void Cmd_term() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_fil_))			src_fil = m.ReadIoUrl("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	public static final String Invk_src_fil_ = "src_fil_";
	public void Cmd_cleanup_sql() {
		// get dump files to delete; EX: "*-categorylinks.sql*" matches "simplewiki-latest-categorylinks.sql" and "simplewiki-latest-categorylinks.sql.gz"
		Io_url[] dump_files = Io_mgr.Instance.QueryDir_args(wiki.Fsys_mgr().Root_dir()).FilPath_("*-" + this.Sql_file_name() + ".sql*").ExecAsUrlAry();
		for (Io_url dump_file : dump_files)
			Io_mgr.Instance.DeleteFil(dump_file);
	}
}
