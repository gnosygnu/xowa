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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import gplx.langs.htmls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.lnkis.*;
public class Xow_popup_wrdx_mkr {
	private boolean skip_space;
	private Xop_tkn_itm prv_tkn_seen, prv_tkn_added;
	public Hash_adp_bry Xnde_id_ignore_list() {return xnde_id_ignore_list;} private Hash_adp_bry xnde_id_ignore_list = Hash_adp_bry.ci_a7();
	public void Init() {
		skip_space = false;
		prv_tkn_seen = prv_tkn_added = null;
	}
	public void Process_tkn(Xow_popup_cfg cfg, Xow_popup_parser_data data, Bry_bfr wrdx_bfr, Xop_tkn_itm tkn, byte[] wtxt_bry, int wtxt_len) {
		boolean add_tkn = true, add_subs = true; Xop_xnde_tkn xnde = null;
		int tkn_src_bgn = tkn.Src_bgn(), tkn_src_end = tkn.Src_end();
		prv_tkn_seen = tkn;
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_root: 
				add_tkn = false;					// don't add_tkn root
				break;
			case Xop_tkn_itm_.Tid_txt:
				data.Words_found_add(tkn);
				break;
			case Xop_tkn_itm_.Tid_apos:
				if (	prv_tkn_added != null
					&&	prv_tkn_seen != prv_tkn_added						// prv seen tkn was skipped
					&& 	prv_tkn_added.Tkn_tid() == Xop_tkn_itm_.Tid_apos	// prv added tkn was apos
					)
					wrdx_bfr.Add_byte_space();								// prv && cur are apos, but something was skipped inbetween; add a space so that apos doesn't combine EX:''{{skip}}'' x> ''''; PAGE:en.w:Somalia; DATE:2014-07-02
				break;
			case Xop_tkn_itm_.Tid_ignore:			// always skip ignores, particularly comments; PAGE:en.w:List_of_countries_by_GDP_(PPP); DATE:2014-07-01
			case Xop_tkn_itm_.Tid_tblw_tb: case Xop_tkn_itm_.Tid_tblw_tc: case Xop_tkn_itm_.Tid_tblw_td:
			case Xop_tkn_itm_.Tid_tblw_te: case Xop_tkn_itm_.Tid_tblw_th: case Xop_tkn_itm_.Tid_tblw_tr:
				add_tkn = add_subs = false;			// skip tblws
				break;
			case Xop_tkn_itm_.Tid_xnde:
				xnde = (Xop_xnde_tkn)tkn;
				switch (xnde.Tag().Id()) {
					case Xop_xnde_tag_.Tid__div:
					case Xop_xnde_tag_.Tid__table: case Xop_xnde_tag_.Tid__tr: case Xop_xnde_tag_.Tid__td: case Xop_xnde_tag_.Tid__th: 
					case Xop_xnde_tag_.Tid__caption: case Xop_xnde_tag_.Tid__thead: case Xop_xnde_tag_.Tid__tfoot: case Xop_xnde_tag_.Tid__tbody:
					case Xop_xnde_tag_.Tid__ref: case Xop_xnde_tag_.Tid__gallery: case Xop_xnde_tag_.Tid__imageMap: case Xop_xnde_tag_.Tid__timeline:
					case Xop_xnde_tag_.Tid__xowa_wiki_setup:
					case Xop_xnde_tag_.Tid__xowa_html:	// needed for Help:Options, else \n at top of doc; DATE:2014-06-22
						add_tkn = add_subs = false;		// skip tblxs
						xnde = null;
						break;
					case Xop_xnde_tag_.Tid__math:		// add <math> as one unit; PAGE:en.w:System_of_polynomial_equations DATE:2014-07-01
						add_subs = false;				// never recur
						xnde = null;
						data.Words_found_add(tkn);		// treat it as one word
						break;
					case Xop_xnde_tag_.Tid__br:
						add_tkn = false;				// never add_tkn Src_bgn / Src_end; note add_subs should still be true; PAGE:en.q:Earth; DATE:2014-06-30
						if (wrdx_bfr.Len_eq_0())		// don't add <br/> to start of document; needed for Help:Options, but good to have everywhere; DATE:2014-06-22
							add_subs = false;
						break;
					default:
						add_tkn = false;				// don't add_tkn xnde, but still add_subs
						if (Xnde_id_ignore_list_chk(xnde, wtxt_bry)) {
							add_subs = false;
							xnde = null;
						}
						break;
				}
				break;
			case Xop_tkn_itm_.Tid_lnke:
				Xop_lnke_tkn lnke = (Xop_lnke_tkn)tkn;
				switch (lnke.Lnke_typ()) {
					case Xop_lnke_tkn.Lnke_typ_brack:
						Process_subs(cfg, data, wrdx_bfr, tkn, wtxt_bry, wtxt_len, Bool_.N);	// add subs which are caption tkns; note that Bool_.N will add all words so that captions don't get split; EX: "a [http://a.org b c d]" -> "a b c d" if words_needed == 2;
						add_tkn = add_subs = false;	// ignore lnke, but add any text tkns; EX: [http://a.org b c d] -> "b c d"
						break;
					case Xop_lnke_tkn.Lnke_typ_text:
						data.Words_found_add(tkn);	// increment words_found; EX: a http://b.org c -> 3 words;
						break;
				}
				break;
			case Xop_tkn_itm_.Tid_lnki: 
				Xop_lnki_tkn lnki = (Xop_lnki_tkn)tkn;
				switch (lnki.Ns_id()) {
					case Xow_ns_.Tid__category:	// skip [[Category:]]
					case Xow_ns_.Tid__file:		// skip [[File:]]
						add_tkn = add_subs = false;
						break;
					default:
						data.Words_found_add(tkn);	// increment words_found; EX: a [[B|c d e]] f -> 3 words;
						break;
				}
				break;
			case Xop_tkn_itm_.Tid_space:
				if (	skip_space					// previous tkn skipped add and set skip_space to true
					&&	wrdx_bfr.Match_end_byt_nl_or_bos()	// only ignore space if it will cause pre; note that some <ref>s will have spaces that should be preserved; EX:"a<ref>b</ref> c"; PAGE:en.w:Mehmed_the_Conqueror; DATE:2014-06-18
					)
					add_tkn = false;							// skip ws
				break;
			case Xop_tkn_itm_.Tid_newLine: {
				// heuristic to handle skipped <div> / <table> which does not skip \n; EX:"<div>a</div>\nb"; div is skipped, but "\n" remains; PAGE:en.w:Eulogy;DATE:2014-06-18
				int wrdx_bfr_len = wrdx_bfr.Len();
				if		(wrdx_bfr_len == 0)					// don't add_tkn \n at bos; does not handle pages where bos intentionally has multiple \n\n
					add_tkn = false;
				else if (wrdx_bfr_len > 2) {				// bounds check
					if (Wtxt_bfr_ends_w_2_nl(wrdx_bfr, wrdx_bfr_len))	// don't add \n if "\n\n"; does not handle intentional sequences of 2+ \n; 
						add_tkn = false;
				}
				break;
			}
			case Xop_tkn_itm_.Tid_hdr: {
				data.Words_found_add(tkn);	// count entire header as one word; not worth counting words in header
				add_subs = false;	// add entire tkn; do not add_subs
				int wrdx_bfr_len = wrdx_bfr.Len();
				if	(wrdx_bfr_len > 2) {					// bounds check
					if (Wtxt_bfr_ends_w_2_nl(wrdx_bfr, wrdx_bfr_len))	// heuristic: 2 \n in bfr, and about to add a hdr tkn which starts with "\n"; delete last \n
						wrdx_bfr.Del_by_1();
				}
				if (	tkn_src_end < wtxt_len				// bounds check
					&&	wtxt_bry[tkn_src_end] == Byte_ascii.Nl	// hdr_tkn will not include trailing "\n". add it; note that this behavior is by design. NOTE:hdr.trailing_nl; DATE:2014-06-17
					) {
					wrdx_bfr.Add_mid(wtxt_bry, tkn_src_bgn, tkn_src_end + 1);	// +1 to add the trailing \n
					add_tkn = false;
				}
				break;
			}
			default:
				break;
		}
		skip_space = false;	// always reset; only used once above for Tid_space; DATE:2014-06-17
		if (add_tkn && xnde == null) {
			if (tkn_src_end - tkn_src_bgn > 0) {	// handle paras which have src_bgn == src_end
				wrdx_bfr.Add_mid(wtxt_bry, tkn_src_bgn, tkn_src_end);
				prv_tkn_added = tkn;
			}
		}
		else	// tkn not added
			skip_space = true;	// skip next space; note this is done with member variable to handle recursive iteration; DATE:2014-06-17
		if (add_subs) {
			if (xnde != null) wrdx_bfr.Add_mid(wtxt_bry, xnde.Tag_open_bgn(), xnde.Tag_open_end());		// add open tag; EX: "<span id=a>"
			Process_subs(cfg, data, wrdx_bfr, tkn, wtxt_bry, wtxt_len, Bool_.Y);
			if (xnde != null) wrdx_bfr.Add_mid(wtxt_bry, xnde.Tag_close_bgn(), xnde.Tag_close_end());	// add close tag; EX: "</span>"
		}
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_hdr:
				if (	cfg.Stop_if_hdr_after_enabled() 
					&&	data.Stop_if_hdr_after_chk(cfg))
					return;
				break;
		}
	}
	private void Process_subs(Xow_popup_cfg cfg, Xow_popup_parser_data data, Bry_bfr wrdx_bfr, Xop_tkn_itm tkn, byte[] wtxt_bry, int wtxt_len, boolean chk_words_found) {
		int subs_len = tkn.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Xop_tkn_itm sub = tkn.Subs_get(i);
			Process_tkn(cfg, data, wrdx_bfr, sub, wtxt_bry, wtxt_len);
			if (chk_words_found && !data.Words_needed_chk()) break;
		}
	}
	private boolean Xnde_id_ignore_list_chk(Xop_xnde_tkn xnde, byte[] src) {
		Mwh_atr_itm[] atrs_ary = xnde.Atrs_ary();
		int atrs_len = atrs_ary.length;
		for (int i = 0; i < atrs_len; i++) {
			Mwh_atr_itm atr = atrs_ary[i];
			if (	Bry_.Eq(atr.Key_bry(), Gfh_atr_.Bry__id)
				&&	xnde_id_ignore_list.Get_by_bry(atr.Val_as_bry()) != null
				) {
                    return true;
			}
		}
		return false;
	}
	public void Xnde_ignore_ids_(byte[] xnde_id_ignore_bry) {
		byte[][] ary = Bry_split_.Split(xnde_id_ignore_bry, Byte_ascii.Pipe);
		int ary_len = ary.length;
		xnde_id_ignore_list.Clear();
		for (int i = 0; i < ary_len; i++) {
			byte[] bry = ary[i];
			if (bry.length == 0) continue;	// ignore empty entries; EX: "a|"
			xnde_id_ignore_list.Add(bry, bry);
		}
	}
	private boolean Wtxt_bfr_ends_w_2_nl(Bry_bfr wrdx_bfr, int wrdx_bfr_len) {
		byte[] hdom_bfr_bry = wrdx_bfr.Bfr();
		return
			(	hdom_bfr_bry[wrdx_bfr_len - 1] == Byte_ascii.Nl	// prv 2 bytes are \n
			&&	hdom_bfr_bry[wrdx_bfr_len - 2] == Byte_ascii.Nl
			);
	}
}
