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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.core.intls.ucas.*;
import gplx.xowa.users.history.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*;
public abstract class Xoctg_fmt_itm_base implements gplx.core.brys.Bfr_arg {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private Xow_wiki wiki;
	private Xoctg_catpage_grp grp;
	private Uca_ltr_extractor ltr_extractor;
	private byte[] ltr_cur; private int loop_bgn; private int col_end;

	public int		Loop_end_idx() {return loop_end_idx;} private int loop_end_idx;
	public boolean		Loop_ends_at_col() {return loop_ends_at_col;} private boolean loop_ends_at_col;
	public void		Col_end_(int col_bgn, int col_idx) {
		this.col_end = col_bgn + Calc_col_len(grp.Itms__len(), col_idx, Cols_max);
	}
	public void Init_from_ltr(Xow_wiki wiki, Xoctg_catpage_grp grp, Uca_ltr_extractor ltr_extractor) {
		this.wiki = wiki;
		this.grp = grp;
		this.ltr_extractor = ltr_extractor;
	}
	public void Set_ltr_and_bgn(byte[] ltr_cur, int loop_bgn) {this.ltr_cur = ltr_cur; this.loop_bgn = loop_bgn;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		// init vars
		Xoh_href_parser href_parser = wiki.App().Html__href_parser();
		Xou_history_mgr history_mgr = wiki.App().User().History_mgr(); 
		int grp_end = grp.Itms__len();

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
			byte[] itm_sortkey = itm.Sortkey_handle();

			// reached end of ltr; exit
			byte[] ltr_1st = ltr_extractor.Get_1st_ltr(itm_sortkey);
			if (!Bry_.Has_at_bgn(ltr_1st, ltr_cur, 0, ltr_1st.length)) {
				loop_end_idx = i;
				loop_ends_at_col = i == col_end;
				return;
			}

			Xoa_ttl itm_ttl = itm.Page_ttl();
			if (itm_ttl == Xoa_ttl.Null)
				Fmt__missing.Bld_many(bfr, itm.Page_id());
			else				
				Bld_html(bfr, wiki, history_mgr, href_parser, itm, itm_ttl);
		}
		loop_end_idx = grp_end;
		loop_ends_at_col = true;
	}
	@gplx.Virtual public void Bld_html(Bry_bfr bfr, Xow_wiki wiki, Xou_history_mgr history_mgr, Xoh_href_parser href_parser, Xoctg_catpage_itm itm, Xoa_ttl ttl) {
		byte[] itm_full_ttl = Gfh_utl.Escape_html_as_bry(tmp_bfr, ttl.Full_txt_w_ttl_case());// NOTE: ttl.Full_txt() to get full ns; EX: Template:A instead of just "A"
		byte[] itm_href = wiki.Html__href_wtr().Build_to_bry(wiki, ttl);
		byte[] itm_atr_cls = Xoh_lnki_wtr.Lnki_cls_visited(history_mgr, wiki.Domain_bry(), ttl.Page_txt());	// NOTE: must be ttl.Page_txt() in order to match Xou_history_mgr.Add
		Fmt__exists.Bld_many(bfr, itm_href, itm_atr_cls, itm_full_ttl, itm_full_ttl, gplx.core.encoders.Hex_utl_.Encode_bry(itm.Sortkey_binary()));
	}
	private static final    Bry_fmt
	  Fmt__missing = Bry_fmt.Auto_nl_skip_last
	( ""
	, "            <li class=\"xowa-missing-category-entry\"><span title=\"id not found: #~{itm_id} might be talk/user page\">missing page (~{itm_id})</li>"
	)
	, Fmt__exists = Bry_fmt.Auto_nl_skip_last
	( ""
	, "            <li><a href=\"~{itm_href}\"~{itm_atr_cls} title=\"~{itm_title}\">~{itm_text}</a></li>"	// <!--~{itm_sortkey}-->
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
