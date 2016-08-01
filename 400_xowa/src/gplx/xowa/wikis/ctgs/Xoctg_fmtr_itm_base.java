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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.users.history.*;
abstract class Xoctg_fmtr_itm_base implements gplx.core.brys.Bfr_arg, Xoctg_fmtr_itm {
	private Xou_history_mgr history_mgr; private Xoh_wtr_ctx hctx;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	protected Xowe_wiki wiki; Xol_lang_itm lang; Xoctg_view_ctg ctg; protected int len; protected Xoh_href_parser href_parser; protected Bry_fmtr html_itm, html_itm_missing; protected Xoctg_view_grp list; protected Xow_msg_mgr msg_mgr;
	public void Init_from_all(Xowe_wiki wiki, Xol_lang_itm lang, Xoh_wtr_ctx hctx, Xoctg_view_ctg ctg, Xoctg_fmtr_all mgr, Xoctg_view_grp itms_list, int itms_list_len) {
		this.wiki = wiki; this.lang = lang; this.hctx = hctx; this.ctg = ctg; this.list = itms_list; this.len = itms_list_len; this.msg_mgr = wiki.Msg_mgr();
		href_parser = wiki.Appe().Html__href_parser();
		html_itm = mgr.Html_itm();
		html_itm_missing = mgr.Html_itm_missing();
		history_mgr = wiki.Appe().Usere().History_mgr();
	}
	public void Init_from_grp(byte[] ttl_char_0, int col_bgn) {this.ttl_char_0 = ttl_char_0; this.col_bgn = col_bgn;} private byte[] ttl_char_0; int col_bgn; int col_end;
	public int Col_idx() {return col_idx;}
	public void Col_idx_(int col_idx, int col_bgn) {
		this.col_idx = col_idx;
		this.col_bgn = col_bgn;
		col_end = col_bgn + Xoctg_fmtr_grp.Calc_col_max(Xoctg_html_mgr.Cols_max, len, col_idx);
	} int col_idx;
	public int Grp_end_idx() {return grp_end_idx;} private int grp_end_idx;
	public boolean Grp_end_at_col() {return grp_end_at_col;} private boolean grp_end_at_col;
	public void Bfr_arg__add(Bry_bfr bfr) {
		for (int i = col_bgn; i < len; i++) {
			if (i == col_end) {
				grp_end_idx = i;
				grp_end_at_col = true;
				return;				
			}
			Xoctg_view_itm itm = list.Itms()[i];
			Xoa_ttl ttl = itm.Ttl();
			byte[] itm_sortkey = itm.Sort_key();
			byte[] ttl_bry = ttl.Page_txt();
			if (!Bry_.Has_at_bgn(itm_sortkey, ttl_char_0, 0, itm_sortkey.length)) {
				grp_end_idx = i;
				grp_end_at_col = i == col_end;
				return;
			}
			Bld_html(bfr, wiki, hctx, itm, ttl, ttl_bry, href_parser, html_itm);
		}
		grp_end_idx = len;
		grp_end_at_col = true;
	}
	@gplx.Virtual public void Bld_html(Bry_bfr bfr, Xowe_wiki wiki, Xoh_wtr_ctx hctx, Xoctg_view_itm itm, Xoa_ttl ttl, byte[] ttl_page, Xoh_href_parser href_parser, Bry_fmtr html_itm) {
		byte[] itm_href = wiki.Html__href_wtr().Build_to_bry(wiki, ttl);
		byte[] itm_full_ttl = gplx.langs.htmls.Gfh_utl.Escape_html_as_bry(tmp_bfr, ttl.Full_txt_w_ttl_case());// NOTE: ttl.Full_txt() to get full ns; EX: Template:A instead of just "A"
		byte[] itm_atr_cls = hctx.Mode_is_hdump() ? Bry_.Empty : Xoh_lnki_wtr.Lnki_cls_visited(history_mgr, wiki.Domain_bry(), ttl.Page_txt());	// NOTE: must be ttl.Page_txt() in order to match Xou_history_mgr.Add
		Bry_fmtr fmtr = itm.Missing() ? html_itm_missing : html_itm;
		fmtr.Bld_bfr_many(bfr, itm_href, itm_full_ttl, itm_full_ttl, itm.Page_id(), itm_atr_cls);
	}
}
