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
package gplx.xowa.addons.wikis.searchs.searchers.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.xowa.wikis.data.tbls.*;
public class Srch_rslt_list_ {
	public static boolean Add_if_new(Srch_search_ctx ctx, Srch_rslt_list rslts, Srch_rslt_row row) {
		Srch_rslt_list_.Highlight(ctx, row);	// always highlight title first; needed for suggest_box to update highlighting when increasing word; EX: Eart -> Earth; "Earth" should be highlighted, not "Eart"
		return
			(	!rslts.Has(row.Key)				// ignore: page already added by another word; EX: "A B"; word is "B", but "A B" already added by "A"
			&&	!rslts.Ids__has(row.Page_id)	// ignore: page already added by page-tbl or by redirect
			&&	!Redirect_exists(rslts, row)	// ignore: page is redirect, and target page already added 
			);
	}
	public static void Get_redirect_ttl(Xowd_page_tbl page_tbl, Xowd_page_itm tmp_page_itm, Srch_rslt_row row) {
		int redirect_id = row.Page_redirect_id;
		if (redirect_id == Srch_rslt_row.Page_redirect_id_null) return;
		if (!page_tbl.Select_by_id(tmp_page_itm, redirect_id)) {Xoa_app_.Usr_dlg().Warn_many("", "", "page not found for redirect_id; redirect_id=~{0}", redirect_id); return;}
		row.Page_redirect_ttl = Xoa_ttl.Replace_unders(tmp_page_itm.Ttl_page_db());
	}
	private static void Highlight(Srch_search_ctx ctx, Srch_rslt_row row) {
		try {row.Page_ttl_highlight = ctx.Highlight_mgr.Highlight(row.Page_ttl.Full_txt_w_ttl_case());}	// NOTE: always highlight row; needed for when search done in url_bar (highlight=n) and then same search reused for search (highlight=y)
		catch (Exception e) {Xoa_app_.Usr_dlg().Warn_many("", "", "highlight failed; ttl=~{0} err=~{1}", row.Page_ttl_wo_ns, Err_.Message_gplx_log(e));}
	}
	private static boolean Redirect_exists(Srch_rslt_list rslts, Srch_rslt_row cur_row) {
		int trg_id = cur_row.Page_redirect_id;			
		if (trg_id == Srch_rslt_row.Page_redirect_id_null) {	// src_page is not redirect
			return false;
		} else {												// src_page is redirect
			Srch_rslt_row trg_row = rslts.Ids__get_or_null(trg_id);
			if (trg_row == null) {								// trg_page has not been seen before
				rslts.Ids__add(trg_id, cur_row);				// add trg_id to known ids; handles double-redirects; 1 -> 2 -> 3;
				return false;
			}
			else												// trg_page has been seen before
				return true;
		}
	}
}
