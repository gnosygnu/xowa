/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa; import gplx.*;
import gplx.core.flds.*; import gplx.ios.*; import gplx.xowa.bldrs.*; 
public abstract class Xob_sql_dump_base extends Xob_itm_dump_base implements Xob_cmd, GfoInvkAble {
	public abstract String Cmd_key();
	public Io_url Src_fil() {return src_fil;} Io_url src_fil;
	public Io_url_gen Make_url_gen() {return make_url_gen;} Io_url_gen make_url_gen;
	private Sql_file_parser parser = new Sql_file_parser();
	private boolean fail = false;
	public abstract String Sql_file_name();
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		this.Init_dump(this.Cmd_key());
		make_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("make"));
		if (src_fil == null) {
			src_fil = Xow_fsys_mgr.Find_file_or_null(wiki.Fsys_mgr().Root_dir(), "*" + Sql_file_name() + "*", ".gz", ".sql");
			if (src_fil == null) {
				String msg = String_.Format(".sql file not found in dir: {0} {1}", Sql_file_name(), wiki.Fsys_mgr().Root_dir());
				app.Gui_wtr().Warn_many("", "", msg);
				app.Gui_mgr().Kit().Ask_ok("", "", msg);
				fail = true;
				return;
			}
		}
		parser.Src_fil_(src_fil).Trg_fil_gen_(dump_url_gen);
		Cmd_bgn_hook(bldr, parser);
	}	protected Gfo_fld_wtr fld_wtr = Gfo_fld_wtr.xowa_();
	public abstract void Cmd_bgn_hook(Xob_bldr bldr, Sql_file_parser parser);
	public void Cmd_run() {
		if (fail) return;
		parser.Parse(bldr.Usr_dlg());
	}
	@gplx.Virtual public void Cmd_end() {
		if (fail) return;
		Xobdc_merger.Basic(bldr.Usr_dlg(), dump_url_gen, temp_dir.GenSubDir("sort"), sort_mem_len, Io_line_rdr_key_gen_all._, new Io_sort_fil_basic(bldr.Usr_dlg(), make_url_gen, make_fil_len));
	}
	public void Cmd_print() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_fil_))			src_fil = m.ReadIoUrl("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	public static final String Invk_src_fil_ = "src_fil_";
}
