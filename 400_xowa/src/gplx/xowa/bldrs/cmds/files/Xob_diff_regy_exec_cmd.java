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
package gplx.xowa.bldrs.cmds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.fsdb.*; import gplx.fsdb.meta.*;
import gplx.xowa.bldrs.*;
public class Xob_diff_regy_exec_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private Io_url sql_dir;
	public Xob_diff_regy_exec_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_diff_regy_exec;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {Exec_main();}
	public void Cmd_end() {}
	public void Cmd_term() {}
	private void Exec_main() {
		if (sql_dir == null)
			sql_dir = wiki.Ctx().App().Fsys_mgr().File_dir().GenSubDir_nest(wiki.Domain_str(), "tmp_sql");
		Xob_diff_regy_sql_runner runner = new Xob_diff_regy_sql_runner();
		Io_url[] urls = Io_mgr.I.QueryDir_fils(sql_dir);
		int urls_len = urls.length;
		for (int i = 0; i < urls_len; ++i)
			runner.Exec(app, urls[i]);
		Xob_diff_regy_sql_runner.Get_conn(wiki, 0, Fsdb_db_tid_.Tid_atr).Exec_sql("VACUUM;");
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
	public void Exec(Xoae_app app, Io_url url) {
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
	public void Run_sql(Xoae_app app) {
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_null(Bry_.new_u8(wiki_domain));
		app.Usr_dlg().Prog_many("", "", "running sql: url=~{0}", url.NameAndExt());
		Db_conn conn = Get_conn(wiki, fsdb_db_id, fsdb_db_tid);
		conn.Exec_sql(Io_mgr.I.LoadFilStr(url));
		if (fsdb_db_tid == Fsdb_db_tid_.Tid_bin)
			conn.Exec_sql("VACUUM;");
	}
	public static Db_conn Get_conn(Xowe_wiki wiki, int fsdb_db_id, byte fsdb_db_tid) {
		wiki.File_mgr().Init_file_mgr_by_load(wiki);
		Fsm_mnt_itm abc_mgr = wiki.File_mgr().Fsdb_mgr().Mnt_mgr().Mnts__get_main();
		if		(fsdb_db_tid == Fsdb_db_tid_.Tid_bin)
			return abc_mgr.Bin_mgr().Dbs__get_at(fsdb_db_id).Conn();
		else if (fsdb_db_tid == Fsdb_db_tid_.Tid_atr)
			return abc_mgr.Atr_mgr().Db__core().Conn();
		else
			throw Err_.unhandled(fsdb_db_tid);
	}
	public static String Build_url(String wiki_domain, int fsdb_db_id, String fsdb_db_type) {
		return String_.Format("{0}-{1}-{2}.sql", wiki_domain, Int_.Xto_str_pad_bgn_zero(fsdb_db_id, 3), fsdb_db_type);
	}
}
class Fsdb_db_tid_ {
	public static final byte Tid_cfg = 0, Tid_atr = 1, Tid_bin = 2;
	public static final String Key_cfg = "cfg", Key_atr = "atr", Key_bin = "bin";
	public static byte Xto_tid(String s) {
		if		(String_.Eq(s, Key_cfg))		return Tid_cfg;
		else if	(String_.Eq(s, Key_atr))		return Tid_atr;
		else if	(String_.Eq(s, Key_bin))		return Tid_bin;
		else									throw Err_.unhandled(s);
	}
	public static String Xto_key(byte v) {
		switch (v) {
			case Tid_cfg:	return Key_cfg;
			case Tid_atr:	return Key_atr;
			case Tid_bin:	return Key_bin;
			default:		throw Err_.unhandled(v);
		}
	}
}
