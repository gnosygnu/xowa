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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.users.history.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*;
public abstract class Xoctg_fmt_itm_base implements gplx.core.brys.Bfr_arg {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private Xow_wiki wiki;
	private Xoctg_catpage_grp grp;
	private byte[] ltr_cur; private int loop_bgn; private int col_end;

	public int		Loop_end_idx() {return loop_end_idx;} private int loop_end_idx;
	public boolean		Loop_ends_at_col() {return loop_ends_at_col;} private boolean loop_ends_at_col;
	public void		Col_end_(int col_bgn, int col_idx) {
		this.col_end = col_bgn + Calc_col_len(grp.Count_by_page(), col_idx, Cols_max);
	}
	public void Init_from_ltr(Xow_wiki wiki, Xoctg_catpage_grp grp) {this.wiki = wiki; this.grp = grp;}
	public void Set_ltr_and_bgn(byte[] ltr_cur, int loop_bgn) {this.ltr_cur = ltr_cur; this.loop_bgn = loop_bgn;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		// init vars
		Xoh_href_parser href_parser = wiki.App().Html__href_parser();
		Xou_history_mgr history_mgr = wiki.App().User().History_mgr(); 
		int grp_end = grp.End();

		// loop over itms; 
		for (int i = loop_bgn; i < grp_end; i++) {
			// reached end of col; exit
			if (i == col_end) {
				loop_end_idx = i;
				loop_ends_at_col = true;
				return;				
			}

			// get sortkey
			Xoctg_catpage_itm itm = grp.Itms__get_at(i);
			byte[] itm_sortkey = itm.Sort_key();

			// reached end of ltr; exit
			if (!Bry_.Has_at_bgn(itm_sortkey, ltr_cur, 0, itm_sortkey.length)) {
				loop_end_idx = i;
				loop_ends_at_col = i == col_end;
				return;
			}

			Bld_html(bfr, wiki, history_mgr, href_parser, itm, itm.Page_ttl());
		}
		loop_end_idx = grp_end;
		loop_ends_at_col = true;
	}
	@gplx.Virtual public void Bld_html(Bry_bfr bfr, Xow_wiki wiki, Xou_history_mgr history_mgr, Xoh_href_parser href_parser, Xoctg_catpage_itm itm, Xoa_ttl ttl) {
		byte[] itm_full_ttl = Gfh_utl.Escape_html_as_bry(tmp_bfr, ttl.Full_txt_w_ttl_case());// NOTE: ttl.Full_txt() to get full ns; EX: Template:A instead of just "A"
		if (itm.Missing())
			fmt_missing.Bld_many(bfr, itm.Page_id(), itm_full_ttl);
		else {
			byte[] itm_href = wiki.Html__href_wtr().Build_to_bry(wiki, ttl);
			byte[] itm_atr_cls = Xoh_lnki_wtr.Lnki_cls_visited(history_mgr, wiki.Domain_bry(), ttl.Page_txt());	// NOTE: must be ttl.Page_txt() in order to match Xou_history_mgr.Add
			fmt_exists.Bld_many(bfr, itm_href, itm_atr_cls, itm_full_ttl, itm_full_ttl);
		}
	}
	private static final    Bry_fmt
	 fmt_missing = Bry_fmt.Auto_nl_skip_last
	( ""
	, "            <li class=\"xowa-missing-category-entry\"><span title=\"id not found: #~{itm_id} might be talk/user page\">~{itm_text} (missing)</li>"
	)
	, fmt_exists = Bry_fmt.Auto_nl_skip_last
	( ""
	, "            <li><a href=\"~{itm_href}\"~{itm_atr_cls} title=\"~{itm_title}\">~{itm_text}</a></li>"
	)
	;
	public static final int Cols_max = 3;
	public static int Calc_col_len(int grp_len, int col_idx, int max_cols) {	// TEST
		if (grp_len == 0) return 0;								// 0 items in group; return 0;
		int max_itms_in_col = ((grp_len - 1) / max_cols) + 1;	// EX: grp with 4, 5, 6 items should have max of 2 items in 1 col, so (a) subtract 1; (b) divide by 3; (c) add 1
		return col_idx <= ((grp_len - 1) % max_cols)			// complicated formula but works; rely on example and note that only 2 or 1 can be returned; EX: 4=2,1,1; 5=2,2,1; 6=2,2,2
			? max_itms_in_col 
			: max_itms_in_col - 1; 
	}
}
