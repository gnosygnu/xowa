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
package gplx.xowa.addons.wikis.searchs.searchers.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Srch_page_tbl_wkr {
	private final    Xowd_page_itm tmp_page_row = new Xowd_page_itm();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public void Search(Srch_search_ctx ctx, Srch_rslt_cbk cbk) {
		byte[] search_raw = To_bry_or_null(tmp_bfr, ctx.Scanner_syms.Wild(), ctx.Crt_mgr);	// build up search String but handle escapes "\+" -> "+"
		if (search_raw == null) return;	// search-term has not or symbols; EX: "earth -history"; "(earth & history)"
		Gfo_usr_dlg_.Instance.Log_many("", "", "search.search by page_tbl; search=~{0}", search_raw);
		Xoa_ttl ttl = ctx.Wiki.Ttl_parse(search_raw); if (ttl == null) return;
		Xowd_page_tbl page_tbl = ctx.Tbl__page;
		if (ctx.Cxl.Canceled()) return;
		if (page_tbl.Select_by_ttl(tmp_page_row, ttl.Ns(), ttl.Page_db())) {
			if (ctx.Cxl.Canceled()) return;
			Srch_rslt_row row = Srch_rslt_row.New(ctx.Wiki_domain, ttl, tmp_page_row.Id(), tmp_page_row.Text_len(), ctx.Addon.Db_mgr().Cfg().Link_score_max() * 3, tmp_page_row.Redirect_id());
			if (Srch_rslt_list_.Add_if_new(ctx, ctx.Rslts_list, row)) {
				Srch_rslt_list_.Get_redirect_ttl(page_tbl, tmp_page_row, row);
				ctx.Rslts_list.Add(row);
				ctx.Rslts_list.Ids__add(row.Page_id, row);
				cbk.On_rslts_found(ctx.Qry, ctx.Rslts_list, 0, 1);
				ctx.Rslts_list.Rslts_are_first = false;
			}
		}
	}
	public static byte[] To_bry_or_null(Bry_bfr bfr, byte wildcard_byte, Srch_crt_mgr mgr) {
		if (mgr.Words_tid == Srch_crt_mgr.Tid__mixed) return null;
		Srch_crt_tkn[] tkns = mgr.Tkns;
		int len = tkns.length;
		for (int i = 0; i < len; ++i) {
			Srch_crt_tkn tkn = tkns[i];
			switch (tkn.Tid) {
				case Srch_crt_tkn.Tid__word:
				case Srch_crt_tkn.Tid__word_w_quote:
					break;
				default:
					return null;
			}
			if (i != 0) bfr.Add_byte_space();
			byte[] tkn_raw = tkn.Val;
			int tkn_raw_len = tkn_raw.length;
			int wildcard_pos = Bry_find_.Find_fwd(tkn_raw, wildcard_byte, 0, tkn_raw_len);
			if (wildcard_pos != Bry_find_.Not_found) {
				int last_pos = tkn_raw_len - 1;
				if (wildcard_pos == last_pos)
					bfr.Add_mid(tkn_raw, 0, last_pos);
				else
					return null;
			}
			else
				bfr.Add(tkn.Val);
		}
		return bfr.To_bry_and_clear();
	}
}
