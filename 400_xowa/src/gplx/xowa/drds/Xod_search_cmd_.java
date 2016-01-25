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
package gplx.xowa.drds; import gplx.*; import gplx.xowa.*;
import gplx.xowa.specials.search.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xod_search_cmd_ {
	public static final Xod_search_cmd 
	  New__page_eq		= Xod_search_cmd__page_eq.Instance
	, New__page_like	= Xod_search_cmd__page_like.Instance
	, New__word_eq		= Xod_search_cmd__word_tbl.Instance_eq
	, New__word_like	= Xod_search_cmd__word_tbl.Instance_like
	;
}
class Xod_search_cmd__page_eq implements Xod_search_cmd {
	public void Search(Cancelable cancelable, Xow_wiki wiki, Xows_ui_async ui_async, String search) {
		Xowd_page_itm page_itm = new Xowd_page_itm();
		if (wiki.Data__core_mgr().Tbl__page().Select_by_ttl(page_itm, wiki.Ns_mgr().Ns_main(), Bry_.Ucase__1st(Bry_.new_u8(search)))) {
			Xows_db_row search_itm = new Xows_db_row(wiki.Domain_bry(), wiki.Ttl_parse(page_itm.Ttl_page_db()), page_itm.Id(), page_itm.Text_len());
			ui_async.Add(search_itm);
		}
	}
        public static final Xod_search_cmd__page_eq Instance = new Xod_search_cmd__page_eq(); Xod_search_cmd__page_eq() {}
}
class Xod_search_cmd__page_like implements Xod_search_cmd {// NOTE: slow; takes at least 10+ seconds
	public void Search(Cancelable cancelable, Xow_wiki wiki, Xows_ui_async ui_async, String search) {
		List_adp tmp_list = List_adp_.new_();
		wiki.Data__core_mgr().Tbl__page().Select_by_search(cancelable, tmp_list, Bry_.Ucase__1st(Bry_.new_u8(search + "*")), 50);
		int len = tmp_list.Count();
		for (int i = 0; i < len; ++i) {
			Xowd_page_itm page_itm = (Xowd_page_itm)tmp_list.Get_at(i);
			Xows_db_row search_itm = new Xows_db_row(wiki.Domain_bry(), wiki.Ttl_parse(page_itm.Ttl_page_db()), page_itm.Id(), page_itm.Text_len());
			ui_async.Add(search_itm);
		}
	}
        public static final Xod_search_cmd__page_like Instance = new Xod_search_cmd__page_like(); Xod_search_cmd__page_like() {}
}
class Xod_search_cmd__word_tbl implements Xod_search_cmd {
	private final boolean wildcard;
	private final int results_wanted;
	private final Xows_db_wkr search_wkr = new Xows_db_wkr();
	Xod_search_cmd__word_tbl(boolean wildcard, int results_wanted) {this.wildcard = wildcard; this.results_wanted = results_wanted;}
	public void Search(Cancelable cancelable, Xow_wiki wiki, Xows_ui_async ui_async, String search) {
		search_wkr.Search_by_drd(cancelable, wiki, ui_async, Bry_.new_u8(Standardize_search(search, wildcard)), results_wanted);
	}
        public static final Xod_search_cmd__word_tbl Instance_eq = new Xod_search_cmd__word_tbl(Bool_.N, 10), Instance_like = new Xod_search_cmd__word_tbl(Bool_.Y, 50);
	private static String Standardize_search(String search, boolean wildcard) {
		String rv = "";
		String[] words = String_.Split(search, " ");
		int words_len = words.length;
		for (int i = 0; i < words_len; ++i) {
			String word = words[i];
			if (String_.Len(word) < 3) continue;
			if (String_.Len(rv) != 0) rv += " ";
			rv += word;
			if (wildcard) rv += "*";
		}
		return rv;
	}
}
