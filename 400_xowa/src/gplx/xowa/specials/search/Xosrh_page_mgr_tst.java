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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import org.junit.*;
public class Xosrh_page_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xosrh_page_mgr_fxt fxt = new Xosrh_page_mgr_fxt();
	@Test   public void Basic() {
		fxt.Init_itms_per_page_(3).Init_site_ids(10, 20);
		Xosrh_page_mgr_searcher ids_a = fxt.ids_(10, 17);
		fxt.Test_search("A", ids_a, 0, Int_.Ary(16, 15, 14));
		fxt.Test_search("A", ids_a, 1, Int_.Ary(13, 12, 11));
		fxt.Test_search("A", ids_a, 2, Int_.Ary(10));
		fxt.Test_search("A", ids_a, 0, Int_.Ary(16, 15, 14));
		Xosrh_page_mgr_searcher ids_b = fxt.ids_(17, 20);
		fxt.Init_sort_by_name_(true).Test_search("B", ids_b, 0, Int_.Ary(17, 18, 19));
	}
}
class Xosrh_page_mgr_fxt {
	public Xosrh_page_mgr_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			tmp_bfr = Bry_bfr.reset_(255);
			page_mgr = new Xosrh_page_mgr();
			hive_mgr = new Xowd_hive_mgr(wiki, Xow_dir_info_.Tid_id);
		}
		return this;
	}	private Xoa_app app; Xow_wiki wiki; Bry_bfr tmp_bfr; Xosrh_page_mgr page_mgr; Xowd_hive_mgr hive_mgr;
	public Xosrh_page_mgr_fxt Init_site_ids(int bgn, int end) {
		Xodb_page tmp_itm = new Xodb_page();
		for (int i = bgn; i < end; i++) {
			byte[] id_bry = new byte[5];	// NOTE: do not reuse; will break hive_mgr
			Base85_utl.XtoStrByAry(i, id_bry, 0, 5);
			tmp_itm.Ns_id_(Xow_ns_.Id_main).Set_all_(i, 0, i - bgn, false, 10, Bry_.XtoStrBytesByInt(i, 0));
			Xodb_page_.Txt_id_save(tmp_bfr, tmp_itm);
			hive_mgr.Create(id_bry, tmp_bfr.XtoAryAndClear(), null);
		}
		return this;
	}
	// public Xodb_page Set_all(int id, int fil_idx, int row_idx, boolean type_redirect, int itm_len, int ns_id, byte[] ttl) {
	public Xosrh_page_mgr_fxt Init_sort_by_name_(boolean v) {page_mgr.Sort_tid_(v ? Xosrh_rslt_itm_sorter.Tid_ttl_asc : Xosrh_rslt_itm_sorter.Tid_len_dsc); return this;}
	public Xosrh_page_mgr_fxt Init_itms_per_page_(int v) {page_mgr.Itms_per_page_(v); return this;}
	public Xosrh_page_mgr_searcher_mok ids_(int bgn, int end) {
		ListAdp rv = ListAdp_.new_();
		int len = end - bgn;
		for (int i = 0; i < len; i++) {
			int itm_id = i + bgn;
			int itm_len = itm_id;
			Xodb_page itm = Xodb_page.srch_(itm_id, itm_len);
			rv.Add(itm);
		}
		return new Xosrh_page_mgr_searcher_mok(rv);
	}
	public void Test_search(String search_str, Xosrh_page_mgr_searcher searcher, int page_idx, int[] expd) {
		byte[] search_bry = Bry_.new_ascii_(search_str);		
		Xosrh_rslt_grp page = page_mgr.Search(tmp_bfr, wiki, search_bry, page_idx, searcher);
		Tfds.Eq_ary(expd, Xto_int_ary(page));
	}
	int[] Xto_int_ary(Xosrh_rslt_grp page) {
		int len = page.Itms_len();
		int[] rv = new int[len];
		for (int i = 0; i < len; i++) {
			rv[i] = ((Xodb_page)page.Itms_get_at(i)).Id();
		}
		return rv;
	}
}
class Xosrh_page_mgr_searcher_mok implements Xosrh_page_mgr_searcher {
	public Xosrh_page_mgr_searcher_mok(ListAdp list) {this.list = list;} ListAdp list;
	public ListAdp Parse_search_and_load_ids(Cancelable cancelable, Bry_bfr bfr, Xosrh_ns_mgr ns_mgr, byte[] search) {return list;}
}
