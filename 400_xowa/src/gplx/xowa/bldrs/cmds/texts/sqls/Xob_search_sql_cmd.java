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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.xowa.langs.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.bldrs.*;
public class Xob_search_sql_cmd extends Xob_itm_basic_base implements Xob_cmd {	// search version 2; upgrade
	private int commit_interval = 100000, progress_interval = 10000;
	public Xob_search_sql_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_text_search_cmd;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {this.Exec(wiki);}
	public void Cmd_end() {}
	public void Cmd_term() {}		
	public void Exec(Xowe_wiki wiki) {
		if (!Env_.Mode_testing()) wiki.Init_assert();
		Xowd_db_mgr db_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		db_mgr.Dbs__delete_by_tid(Xowd_db_file_.Tid_search_core);
		Xowd_db_file search_db = Xob_search_sql_cmd.Dbs__get_or_make(db_mgr);
		Db_conn search_conn = search_db.Conn();
		Xowd_search_temp_tbl search_temp_tbl = new Xowd_search_temp_tbl(search_conn, db_mgr.Props().Schema_is_1());
		search_temp_tbl.Create_tbl();
		search_temp_tbl.Insert_bgn();
		Xowd_page_tbl page_tbl = db_mgr.Tbl__page();
		Db_rdr page_rdr = page_tbl.Select_all();
		try {
			Xol_lang_itm lang = wiki.Lang();
			Bry_bfr bfr = Bry_bfr.reset_(1024);
			Ordered_hash hash = Ordered_hash_.New();
			int page_count = 0;
			String fld_page_id = page_tbl.Fld_page_id(), fld_page_ttl = page_tbl.Fld_page_title();
			while (page_rdr.Move_next()) {
				int page_id = page_rdr.Read_int(fld_page_id);
				byte[] ttl = page_rdr.Read_bry_by_str(fld_page_ttl);
				byte[][] words = Xob_search_base.Split_ttl_into_words(lang, hash, bfr, ttl);
				int words_len = words.length;
				for (int i = 0; i < words_len; i++) {
					byte[] word = words[i];
					search_temp_tbl.Insert_cmd_by_batch(page_id, word);
				}
				++page_count;
				if		((page_count % commit_interval) == 0)
					Commit(search_conn);
				else if ((page_count % progress_interval) == 0)
					usr_dlg.Prog_many("", "", "parse progress: count=~{0} last=~{1}", page_count, String_.new_u8(ttl));
			}
			this.Commit(search_conn);
		} 
		finally {page_rdr.Rls();}
		search_conn.Txn_end();
		search_temp_tbl.Make_data(usr_dlg, db_mgr.Db__search().Tbl__search_link(), db_mgr.Db__search().Tbl__search_word());
	}
	private void Commit(Db_conn search_conn) {
		search_conn.Txn_sav();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_commit_interval_))		commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_progress_interval_))		progress_interval = m.ReadInt("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_progress_interval_ = "progress_interval_", Invk_commit_interval_ = "commit_interval_";
	public static Xowd_db_file Dbs__get_or_make(Xowd_db_mgr db_mgr) {
		Xowd_db_file db = db_mgr.Props().Layout_text().Tid_is_all_or_few()
			? db_mgr.Db__core()
			: db_mgr.Dbs__make_by_tid(Xowd_db_file_.Tid_search_core)
			;
		db.Tbl__search_word().Ddl__page_count_y_();
		db.Tbl__search_word().Create_tbl();
		db.Tbl__search_link().Create_tbl();
		return db;
	}
}
