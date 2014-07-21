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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xob_search_sql_cmd extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_search_sql_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY_search_sql;} public static final String KEY_search_sql = "import.sql.search_title.cmd";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {this.Exec(wiki);}
	public void Cmd_end() {}
	public void Cmd_print() {}
	public void Exec(Xow_wiki wiki) {
		usr_dlg.Log_many("", "", "search_title.cmd: initing wiki");
		if (!Env_.Mode_testing()) wiki.Init_assert();
		Xodb_fsys_mgr db_fs = wiki.Db_mgr_as_sql().Fsys_mgr();
		usr_dlg.Log_many("", "", "search_title.cmd: getting core db");
		Xodb_file page_db = db_fs.Get_tid_root(Xodb_file_tid_.Tid_core);
		usr_dlg.Log_many("", "", "search_title.cmd: getting existing searchdb");
		Xodb_file search_db = db_fs.Get_tid_root(Xodb_file_tid_.Tid_search);
		if (search_db == null) {
			usr_dlg.Log_many("", "", "search_title.cmd: making new searchdb");
			search_db = db_fs.Make(Xodb_file_tid_.Tid_search);
		}
		DataRdr page_rdr = DataRdr_.Null;
		Db_provider search_provider = search_db.Provider();
		usr_dlg.Log_many("", "", "search_title.cmd: droping tables");
		Sqlite_engine_.Tbl_delete_many(search_provider, Xodb_tbl_search_title_temp.Tbl_name, Xodb_search_title_word_tbl.Tbl_name, Xodb_search_title_page_tbl.Tbl_name);
		usr_dlg.Log_many("", "", "search_title.cmd: creating db connection; conn=~{0}", search_provider.ConnectInfo().Raw_of_db_connect());
		Xodb_tbl_search_title_temp search_temp_tbl = new Xodb_tbl_search_title_temp().Create_table(search_provider);
		try {
			usr_dlg.Log_many("", "", "search_title.cmd: starting select;");
			search_provider.Txn_mgr().Txn_bgn_if_none();
			page_rdr = wiki.Db_mgr_as_sql().Tbl_page().Select_all(page_db.Provider());
			usr_dlg.Log_many("", "", "search_title.cmd: other init;");
			Db_stmt search_temp_stmt = search_temp_tbl.Insert_stmt(search_provider);
			Xol_lang lang = wiki.Lang();
			Bry_bfr bfr = Bry_bfr.reset_(1024);
			OrderedHash hash = OrderedHash_.new_();
			int page_count = 0;
			while (page_rdr.MoveNextPeer()) {
				int page_id = page_rdr.ReadInt(Xodb_page_tbl.Fld_page_id);
				byte[] ttl = page_rdr.ReadBryByStr(Xodb_page_tbl.Fld_page_title);
				byte[][] words = Xob_search_base.Split(lang, hash, bfr, ttl);
				int words_len = words.length;
				for (int i = 0; i < words_len; i++) {
					byte[] word = words[i];
					search_temp_tbl.Insert(search_temp_stmt, page_id, word);
				}
				++page_count;
				if		((page_count % commit_interval) == 0)
					Commit(search_provider);
				else if ((page_count % progress_interval) == 0)
					usr_dlg.Prog_many("", "", "parse progress: count=~{0} last=~{1}", page_count, String_.new_utf8_(ttl));
			}
			this.Commit(search_provider);
		} 
		finally {
			page_rdr.Rls();
		}
		search_provider.Txn_mgr().Txn_end_all_bgn_if_none();
		search_temp_tbl.Make_data(usr_dlg, search_provider);
		search_provider.Txn_mgr().Txn_bgn_if_none();
		wiki.Db_mgr_as_sql().Tbl_xowa_db().Commit_all(db_fs.Core_provider(), db_fs.Ary());
		search_provider.Txn_mgr().Txn_end_all();
	}	private int commit_interval = 100000, progress_interval = 10000;
	private void Commit(Db_provider search_provider) {
		search_provider.Txn_mgr().Txn_end_all_bgn_if_none();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_commit_interval_))		commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_progress_interval_))		progress_interval = m.ReadInt("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_progress_interval_ = "progress_interval_", Invk_commit_interval_ = "commit_interval_";
}
