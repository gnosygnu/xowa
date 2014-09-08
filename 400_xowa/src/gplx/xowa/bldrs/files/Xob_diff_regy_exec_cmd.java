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
package gplx.xowa.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.fsdb.*; import gplx.xowa.bldrs.oimgs.*;
public class Xob_diff_regy_exec_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private Io_url sql_dir;
	public Xob_diff_regy_exec_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY_oimg;} public static final String KEY_oimg = "file.diff_regy.exec";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {Exec_main();}
	public void Cmd_end() {}
	public void Cmd_print() {}
	private void Exec_main() {
		if (sql_dir == null)
			sql_dir = wiki.Ctx().App().Fsys_mgr().File_dir().GenSubDir_nest(wiki.Domain_str(), "tmp_sql");
		Xob_diff_regy_sql_runner runner = new Xob_diff_regy_sql_runner();
		Io_url[] urls = Io_mgr._.QueryDir_fils(sql_dir);
		int urls_len = urls.length;
		for (int i = 0; i < urls_len; ++i)
			runner.Exec(app, urls[i]);
		Xob_diff_regy_sql_runner.Get_provider(wiki, 0, Fsdb_db_tid_.Tid_atr).Exec_sql("VACUUM;");
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_sql_dir_))		sql_dir = m.ReadIoUrl("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_sql_dir_ = "sql_dir_";
}
class Xob_diff_regy_sql_runner {
	public Io_url Url() {return url;} private Io_url url;
	public String Wiki_domain() {return wiki_domain;} private String wiki_domain;
	public int Fsdb_db_id() {return fsdb_db_id;} private int fsdb_db_id;
	public byte Fsdb_db_tid() {return fsdb_db_tid;} private byte fsdb_db_tid;
	public void Exec(Xoa_app app, Io_url url) {
		Parse_url(url);
		Run_sql(app);
	}
	public void Parse_url(Io_url url) {
		this.url = url;
		String[] parts = String_.Split(url.NameOnly(), "-");
		wiki_domain = parts[0];
		fsdb_db_id = Int_.parse_(parts[1]);
		fsdb_db_tid = Fsdb_db_tid_.Xto_tid(parts[2]);
	}
	public void Run_sql(Xoa_app app) {
		Xow_wiki wiki = app.Wiki_mgr().Get_by_key_or_null(Bry_.new_utf8_(wiki_domain));
		app.Usr_dlg().Prog_many("", "", "running sql: url=~{0}", url.NameAndExt());
		Db_provider provider = Get_provider(wiki, fsdb_db_id, fsdb_db_tid);
		provider.Exec_sql(Io_mgr._.LoadFilStr(url));
		if (fsdb_db_tid == Fsdb_db_tid_.Tid_bin)
			provider.Exec_sql("VACUUM;");
	}
	public static Db_provider Get_provider(Xow_wiki wiki, int fsdb_db_id, byte fsdb_db_tid) {
		wiki.File_mgr().Fsdb_mgr().Init_by_wiki(wiki);
		Fsdb_db_abc_mgr abc_mgr = wiki.File_mgr().Fsdb_mgr().Mnt_mgr().Abc_mgr_at(0);
		if		(fsdb_db_tid == Fsdb_db_tid_.Tid_bin)
			return abc_mgr.Bin_mgr().Get_at(fsdb_db_id).Provider();
		else if (fsdb_db_tid == Fsdb_db_tid_.Tid_atr)
			return abc_mgr.Atr_mgr().Get_at(0).Provider();
		else
			throw Err_.unhandled(fsdb_db_tid);
	}
	public static String Build_url(String wiki_domain, int fsdb_db_id, String fsdb_db_type) {
		return String_.Format("{0}-{1}-{2}.sql", wiki_domain, Int_.Xto_str_pad_bgn(fsdb_db_id, 3), fsdb_db_type);
	}
}
