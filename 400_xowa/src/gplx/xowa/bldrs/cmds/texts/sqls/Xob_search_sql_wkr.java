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
import gplx.ios.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_search_sql_wkr extends Xob_search_base implements Io_make_cmd {			// search version 2
	private Xowd_db_mgr db_mgr; private Xowd_search_link_tbl search_page_tbl; private Xowd_search_word_tbl search_word_tbl;
	private int search_id = 0; private byte[] prv_word = Bry_.Empty; private int page_count;
	public Xob_search_sql_wkr(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Wkr_key() {return Xob_cmd_keys.Key_text_search_wkr;}
	@Override public gplx.ios.Io_make_cmd Make_cmd_site() {return this;}
	public Io_sort_cmd Make_dir_(Io_url v) {return this;}	// noop	
	public void Sort_bgn() {
		this.usr_dlg = Xoa_app_.Usr_dlg();
		this.db_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		Xob_search_sql_cmd.Dbs__get_or_make(db_mgr);
		this.search_page_tbl = db_mgr.Db__search().Tbl__search_link();
		this.search_word_tbl = db_mgr.Db__search().Tbl__search_word(); 
		search_page_tbl.Insert_bgn(); search_word_tbl.Insert_bgn();
	}
	public void Sort_do(Io_line_rdr rdr) {
		byte[] bry = rdr.Bfr();
		byte[] cur_word = Bry_.Mid(bry, rdr.Key_pos_bgn(), rdr.Key_pos_end());
		if (!Bry_.Eq(cur_word, prv_word)) {
			search_word_tbl.Insert_cmd_by_batch(++search_id, cur_word, page_count);
			prv_word = cur_word;
			page_count = 0;
		}
		search_page_tbl.Insert_cmd_by_batch(search_id, Base85_utl.XtoIntByAry(bry, rdr.Key_pos_end() + 1, rdr.Key_pos_end() +  5)); // -1: ignore rdr_dlm
		++page_count;
		if (search_id % 10000 == 0)
			usr_dlg.Prog_many("", "", "creating title index: count=~{0}", search_id);
	}
	public void Sort_end() {
		search_page_tbl.Insert_end(); search_word_tbl.Insert_end();
		Xowd_db_file search_db = db_mgr.Db__search();
		Xowd_search_temp_tbl search_temp_tbl = new Xowd_search_temp_tbl(search_db.Conn(), db_mgr.Props().Schema_is_1());
		search_temp_tbl.Create_idx(usr_dlg, search_page_tbl, search_word_tbl);
		search_word_tbl.Ddl__page_count__cfg(search_db.Tbl__cfg());
	}
}
class Xob_search_wkr extends Xob_itm_basic_base implements Xobd_wkr {
	private Xowd_db_file search_db; private Xowd_search_temp_tbl search_temp_tbl;
	private Xol_lang lang; private final Bry_bfr tmp_bfr = Bry_bfr.new_(255); private final Ordered_hash list = Ordered_hash_.new_bry_();
	public String Wkr_key() {return Xob_cmd_keys.Key_text_search_wkr;}
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		if (!Env_.Mode_testing()) wiki.Init_assert();
		this.lang = wiki.Lang();
		Xowd_db_mgr db_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		this.search_db = Xob_search_sql_cmd.Dbs__get_or_make(db_mgr);
		this.search_temp_tbl = new Xowd_search_temp_tbl(search_db.Conn(), db_mgr.Props().Schema_is_1());
		search_temp_tbl.Create_tbl();
		search_temp_tbl.Insert_bgn();
	}
	public void Wkr_run(Xowd_page_itm page) {
		try {
			int page_id = page.Id();
			byte[] ttl = page.Ttl_page_db();
			byte[][] words = Xob_search_base.Split_ttl_into_words(lang, list, tmp_bfr, ttl);
			int len = words.length;
			for (int i = 0; i < len; ++i) {
				byte[] word = words[i];
				search_temp_tbl.Insert_cmd_by_batch(page_id, word);
			}
		}	catch (Exception e) {bldr.Usr_dlg().Warn_many("", "", "search_index:fatal error: err=~{0}", Err_.Message_gplx_full(e));}	// never let single page crash entire import
	}
	public void Wkr_end() {
		search_temp_tbl.Make_data(usr_dlg, search_db.Tbl__search_link(), search_db.Tbl__search_word());
		search_temp_tbl.Insert_end();
	}
	public void Wkr_print() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set)) {}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_set = "set";
}
