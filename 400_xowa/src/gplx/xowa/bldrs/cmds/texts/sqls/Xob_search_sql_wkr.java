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
	private int search_id = 0; private byte[] prv_word = Bry_.Empty;
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
			search_word_tbl.Insert_cmd_by_batch(++search_id, cur_word);
			prv_word = cur_word;
		}
		search_page_tbl.Insert_cmd_by_batch(search_id, Base85_utl.XtoIntByAry(bry, rdr.Key_pos_end() + 1, rdr.Key_pos_end() +  5)); // -1: ignore rdr_dlm
		if (search_id % 10000 == 0)
			usr_dlg.Prog_many("", "", "creating title index: count=~{0}", search_id);
	}
	public void Sort_end() {
		search_page_tbl.Insert_end(); search_word_tbl.Insert_end();
		Xowd_search_temp_tbl search_temp_tbl = new Xowd_search_temp_tbl(db_mgr.Db__search().Conn(), db_mgr.Props().Schema_is_1());
		search_temp_tbl.Create_idx(usr_dlg, search_page_tbl, search_word_tbl);
	}
}
