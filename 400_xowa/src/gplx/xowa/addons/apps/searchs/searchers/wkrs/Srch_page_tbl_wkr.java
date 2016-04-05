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
package gplx.xowa.addons.apps.searchs.searchers.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*; import gplx.xowa.addons.apps.searchs.searchers.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.apps.searchs.searchers.rslts.*;
import gplx.xowa.addons.apps.searchs.searchers.crts.*;
public class Srch_page_tbl_wkr {
	private final    Xowd_page_itm tmp_page_row = new Xowd_page_itm();
	private final    Bry_bfr tmp_bfr = Bry_bfr.new_();
	public void Search(Srch_search_ctx ctx, Srch_rslt_cbk cbk) {
		byte[] search_raw = To_bry_or_null(tmp_bfr, ctx.Scanner_syms.Wild(), ctx.Crt_mgr);	// build up search String but handle escapes "\+" -> "+"
		if (search_raw == null) return;	// search-term has not or symbols; EX: "earth -history"; "(earth & history)"
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
