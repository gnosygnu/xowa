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
public class Xosrh_page_mgr implements Xosrh_page_mgr_searcher {
	public int Pages_len() {return pages_ary.length;}
	public int Itms_bgn() {return itms_bgn;} private int itms_bgn;
	public int Itms_end() {return itms_end;} private int itms_end;
	public int Itms_per_page() {return itms_per_page;} public Xosrh_page_mgr Itms_per_page_(int v) {itms_per_page = v; return this;} private int itms_per_page = 100;
	public byte Sort_tid() {return sort_tid;}
	public Xosrh_page_mgr Sort_tid_(byte v) {
		if (sort_tid == v) return this;
		sort_tid = v;
		sorter.Tid_(v);
		prv_search_bry = Bry_.Empty;	// reset search			
		return this;
	} 	byte sort_tid;
	private byte[] prv_search_bry = Bry_.Empty, prv_ns_bry = Bry_.Empty; 
	public boolean Prv_search_is_same(byte[] cur_search_bry, byte[] cur_ns_bry) {
		return Bry_.Eq(cur_ns_bry, prv_ns_bry) && Bry_.Eq(cur_search_bry, prv_search_bry);
	}
	private Xosrh_rslt_grp[] pages_ary; ListAdp cur_ids = ListAdp_.new_();
	private Xosrh_rslt_itm_sorter sorter = new Xosrh_rslt_itm_sorter();		
	private Xow_wiki wiki;
	public Xosrh_ns_mgr Ns_mgr() {return ns_mgr;} public void Ns_mgr_(Xosrh_ns_mgr v) {ns_mgr = v;} private Xosrh_ns_mgr ns_mgr = new Xosrh_ns_mgr();
	public Xosrh_rslt_grp Search(Bry_bfr bfr, Xow_wiki wiki, byte[] search, int page_idx, Xosrh_page_mgr_searcher searcher) {return Search(bfr, wiki, search, page_idx, searcher, Cancelable_.Never);}
	public Xosrh_rslt_grp Search(Bry_bfr bfr, Xow_wiki wiki, byte[] search, int page_idx, Xosrh_page_mgr_searcher searcher, Cancelable cancelable) {
		this.wiki = wiki;
		Xosrh_rslt_grp rv = null;
		itms_bgn = page_idx * itms_per_page;
		itms_end = itms_bgn + itms_per_page;
		if (Prv_search_is_same(search, ns_mgr.Xto_hash_key())) {	// search is same
			if (page_idx < pages_ary.length) {		// page_idx is in bounds
				rv = pages_ary[page_idx];
				if (rv != null) return rv;			// page_found; return it;
			}
		}
		else {										// search is new; rebuild;
			ListAdp ids = searcher.Parse_search_and_load_ids(cancelable, bfr, ns_mgr, search);
			Rebuild(cancelable, wiki, ids);
			prv_search_bry = search;
			prv_ns_bry = ns_mgr.Xto_hash_key();
		}
		int ids_len = cur_ids.Count();
		if (itms_end > ids_len) itms_end = ids_len;
		if (page_idx < 0 || page_idx >= pages_ary.length) return Grp_empty;
		if (	sort_tid != Xosrh_rslt_itm_sorter.Tid_ttl_asc 
			&&	cancelable == Cancelable_.Never								// cancelable != Cancelable_.Never for search_suggest
			&&	wiki.Db_mgr().Tid() == gplx.xowa.dbs.Xodb_mgr_txt.Tid_txt	// txt only has search_title fields (page_id, word_id), so it needs to do another load to get page fields; note that sql has page fields already
			) {	
			wiki.Db_mgr().Load_mgr().Load_by_ids(cancelable, cur_ids, itms_bgn, itms_end);
		}
		rv = new Xosrh_rslt_grp(page_idx);
		for (int i = itms_bgn; i < itms_end; i++)
			rv.Itms_add((Xodb_page)cur_ids.FetchAt(i));
		pages_ary[page_idx] = rv;
		rv.Itms_total_(cur_ids.Count());
		return rv;
	}
	public ListAdp Parse_search_and_load_ids(Cancelable cancelable, Bry_bfr bfr, Xosrh_ns_mgr ns_mgr, byte[] search) {
		search = wiki.Lang().Case_mgr().Case_build_lower(search, 0, search.length);
		Xosrh_qry_itm cur_root = Xosrh_parser._.Parse(search);
		cur_root.Search(cancelable, bfr, search, wiki, itms_per_page, ns_mgr);
		return cur_root.Matches(search).Ids();
	}
	private void Rebuild(Cancelable cancelable, Xow_wiki wiki, ListAdp ids) {
		int ids_len = ids.Count();
		int pages_len = ((ids_len - 1) / itms_per_page) + 1; if (pages_len == 0) pages_len = 1;
		pages_ary = new Xosrh_rslt_grp[pages_len];
		cur_ids.Clear();
		for (int i = 0; i < ids_len; i++)
			cur_ids.Add(ids.FetchAt(i));
		if (sort_tid == Xosrh_rslt_itm_sorter.Tid_ttl_asc) {
			cur_ids.SortBy(sorter.Tid_(Xosrh_rslt_itm_sorter.Tid_id));
			wiki.Db_mgr().Load_mgr().Load_by_ids(cancelable, cur_ids, 0, ids_len);
			cur_ids.SortBy(sorter.Tid_(Xosrh_rslt_itm_sorter.Tid_ttl_asc));
		}
		else {
			cur_ids.SortBy(sorter.Tid_(Xosrh_rslt_itm_sorter.Tid_len_dsc));
		}		
	}
	private static final Xosrh_rslt_grp Grp_empty = new Xosrh_rslt_grp(-1);
}
