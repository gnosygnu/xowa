/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.core.lists.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.*;
class Dpl_page_finder {
	private final Dpl_itm itm;
	private final Xowe_wiki wiki;

	public Dpl_page_finder(Dpl_itm itm, Xowe_wiki wiki) {
		this.itm = itm;
		this.wiki = wiki;
	}
	public Ordered_hash Find() {
		// get include_ttls
		List_adp include_ttls = itm.Ctg_includes();
		if (include_ttls == null) return Ordered_hash_.New(); // exit early if none exists

		// get exclude_pages
		Ordered_hash exclude_pages = Get_exclude_pages(itm.Ctg_excludes());

		// init vars for loop below
		int itm_ns_filter = itm.Ns_filter();
		List_adp remove_list = List_adp_.New();
		Int_obj_ref tmp_id = Int_obj_ref.New_zero();

		// get include_pags; note that this is a UNION of all member pages; EX: include_ttls=Ctg_A,Ctg_B,Ctg_C will only return pages in Ctg_A AND Ctg_B AND Ctg_C
		Ordered_hash rv = Ordered_hash_.New();
		int len = include_ttls.Len();
		for (int i = 0; i < len; i++) {
			// get ttl
			Xoa_ttl ttl = Get_ctg_ttl_or_null(include_ttls, i);
			if (ttl == null) continue;

			// get pages
			Ordered_hash cur_pages = Ordered_hash_.New();
			Find_pages_in_ctg(cur_pages, itm.Page_ttl(), ttl);

			// identify pages (a) not in previous list; (b) excluded; (c) ns_filter
			remove_list.Clear();
			int cur_len = cur_pages.Len();
			for (int j = 0; j < cur_len; j++) {
				// get item and init tmp
				Xowd_page_itm page_itm = (Xowd_page_itm)cur_pages.Get_at(j);
				tmp_id.Val_(page_itm.Id());

				// check if should be removed
				if (   (i != 0 && !rv.Has(tmp_id)) // item doesn't exist in previous set; note this doesn't apply to the 0th set
					|| exclude_pages.Has(tmp_id) // item is marked as excluded
					|| itm_ns_filter != Dpl_itm.Ns_filter_null && itm_ns_filter != page_itm.Ns().Id()  // item does not match specified filter
					) {
					remove_list.Add(page_itm);
				}
			}

			// remove pages
			int remove_len = remove_list.Len();
			for (int j = 0; j < remove_len; j++) {
				Xowd_page_itm page_itm = (Xowd_page_itm)remove_list.Get_at(j);
				cur_pages.Del(tmp_id.Val_(page_itm.Id()));
			}

			// set cur_pages as main list
			rv = cur_pages;
		}

		// sorting
		rv.Sort_by
			( itm.Sort_ascending() == Bool_.__byte
			? (ComparerAble)Xowd_page_itm_sorter.IdAsc // sort not specified; use default;
			: (ComparerAble)new Dpl_page_sorter(itm)); // sort specified
		return rv;
	}
	private Ordered_hash Get_exclude_pages(List_adp ttls) {
		Ordered_hash rv = Ordered_hash_.New();

		// return empty hash if no ttls
		if (ttls == null)
			return rv;

		// loop exclude ttls
		int len = ttls.Len();
		for (int i = 0; i < len; i++) {
			Xoa_ttl ttl = Get_ctg_ttl_or_null(ttls, i);
			if (ttl == null) continue;
			Find_pages_in_ctg(rv, itm.Page_ttl(), ttl);
		}
		return rv;
	}
	private void Find_pages_in_ctg(Ordered_hash rv, byte[] page_ttl, Xoa_ttl cat_ttl) {
		// get ctg
		Xoctg_catpage_ctg ctg = wiki.Ctg__catpage_mgr().Get_by_cache_or_null(page_ttl, Xoctg_catpage_url.New__blank(), cat_ttl, Int_.Max_value);
		if (ctg == null) return;

		// loop grps to get each page
		Int_obj_ref tmp_id = Int_obj_ref.New_zero();
		for (byte tid = 0; tid < Xoa_ctg_mgr.Tid___max; tid++) {
			// get grp; EX: subc; page; file
			Xoctg_catpage_grp grp = ctg.Grp_by_tid(tid);

			// loop itms in grp and add to hash
			int len = grp.Itms__len();
			for (int i = 0; i < len; i++) {
				Xoctg_catpage_itm itm = grp.Itms__get_at(i);
				int itm_page_id = itm.Page_id();

				if (rv.Has(tmp_id.Val_(itm_page_id))) continue; // check to make sure not already added

				Xowd_page_itm page = new Xowd_page_itm();
				if (itm.Page_ttl() == null) continue; // cat_link can exist without entry in page_db.page
				page.Id_(itm_page_id);
				page.Ttl_(itm.Page_ttl());
				rv.Add(Int_obj_ref.New(itm_page_id), page);
			}
		}
	}

	private Xoa_ttl Get_ctg_ttl_or_null(List_adp list, int i) {// helper method to extract ttl from list
		// get ttl
		byte[] ttl_bry = (byte[])list.Get_at(i);
		Xoa_ttl ttl = wiki.Ttl_parse(gplx.xowa.wikis.nss.Xow_ns_.Tid__category, ttl_bry);

		// log if invalid; NOTE: pages in en.n will pass "{{{2}}}" as category title; PAGE:en.b:Category:Egypt DATE:2016-10-18
		if (ttl == null) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "category title is invalid; wiki=~{0} page=~{1} ttl=~{2}", wiki.Domain_str(), itm.Page_ttl(), ttl_bry);
		}
		return ttl;
	}
}
