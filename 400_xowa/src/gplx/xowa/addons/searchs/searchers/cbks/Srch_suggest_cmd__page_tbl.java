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
//namespace gplx.xowa.addons.searchs.searchers.cbks {
//	class Srch_suggest_cmd__page_tbl {
//		private boolean Search_by_all_pages_v2() {
//			rslts_2.Clear();
//			Xoa_ttl search_ttl = Xoa_ttl.parse(wiki, search_bry); if (search_ttl == null) return false;
//			byte[] search_ttl_bry = search_ttl.Page_db();
//			wiki.Db_mgr().Load_mgr().Load_ttls_for_search_suggest(this, rslts_2, search_ttl.Ns(), search_ttl_bry, max_results, all_pages_min, all_pages_extend, true, false);
//			return true;
//		}
//		private boolean Search_by_all_pages_v1() {
//			rslts_2.Clear();
//			Xowd_page_itm rslt_nxt = new Xowd_page_itm();
//			Xowd_page_itm rslt_prv = new Xowd_page_itm();
//			Xoa_ttl search_ttl = Xoa_ttl.parse(wiki, search_bry); if (search_ttl == null) return false;
//			byte[] search_ttl_bry = search_ttl.Page_db();
//			List_adp page_list = List_adp_.new_();
//			wiki.Db_mgr().Load_mgr().Load_ttls_for_all_pages(this, page_list, rslt_nxt, rslt_prv, Int_obj_ref.zero_(), wiki.Ns_mgr().Ns_main(), search_ttl_bry, max_results, all_pages_min, all_pages_extend, true, false);
//			Xowd_page_itm[] page_ary = (Xowd_page_itm[])page_list.To_ary_and_clear(typeof(Xowd_page_itm));
//			int idx = 0, page_ary_len = page_ary.length;
//			for (int i = 0; i < page_ary_len; i++) {
//				Xowd_page_itm page = page_ary[i];
//				if (page != null) {
//					if (!Bry_.Has_at_bgn(page.Ttl_page_db(), search_ttl_bry)) continue;	// look-ahead may return other titles that don't begin with search; ignore
//					if (page.Text_len() > all_pages_min) {
//						rslts_2.Add(page);
//						idx++;
//					}
//				}
//				if (idx == max_results) break;
//			}
//			return true;
//		}
//	}
//}
