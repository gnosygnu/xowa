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
package gplx.xowa.html.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*;
import gplx.html.*;
import gplx.xowa.apis.xowa.html.modules.*;
import gplx.xowa.parsers.lnkes.*;
import gplx.xowa.gui.views.*;
public class Xow_popup_parser {
	private Xoa_app app; private Xow_wiki wiki; private Xop_parser parser;
	private ByteTrieMgr_fast orig_trie, wtxt_trie; private Xop_tkn_mkr tkn_mkr;
	private Xop_ctx orig_ctx; private Xop_root_tkn orig_root, wtxt_root; private Xot_compile_data orig_props = new Xot_compile_data();
	private Bry_bfr hdom_bfr = Bry_bfr.reset_(255);
	private Xoh_html_wtr_ctx hctx = Xoh_html_wtr_ctx.Popup;
	private boolean add_wtxt_skip_space = false;
	private boolean stop_at_hdr_done;
	private int words_found;
	private int words_needed = -1;
	private Cancelable cancelable; private Bry_bfr tmp_bfr = Bry_bfr.reset_(32);
	public Xop_ctx Wtxt_ctx() {return wtxt_ctx;} private Xop_ctx wtxt_ctx;
	private ListAdp words_found_list = ListAdp_.new_();
	public int Scan_len() {return scan_len;} public void Scan_len_(int v) {scan_len = v;} private int scan_len = Xoapi_popups.Dflt_scan_len;
	public int Scan_max() {return scan_max;} public void Scan_max_(int v) {scan_max = v;} private int scan_max = Xoapi_popups.Dflt_scan_max;
	public int Show_all_if_less_than() {return show_all_if_less_than;} public void Show_all_if_less_than_(int v) {show_all_if_less_than = v;} private int show_all_if_less_than = Xoapi_popups.Dflt_show_all_if_less_than;
	public byte[] Ellipsis() {return ellipsis;} public void Ellipsis_(byte[] v) {ellipsis = v;} private byte[] ellipsis = Ellipsis_const;
	public byte[] Notoc() {return notoc;} public void Notoc_(byte[] v) {notoc = v;} private byte[] notoc = Notoc_const;
	public boolean Stop_at_hdr() {return stop_at_hdr;} public void Stop_at_hdr_(boolean v) {stop_at_hdr = v;} private boolean stop_at_hdr = false;
	public boolean Output_js_clean() {return output_js_clean;} public void Output_js_clean_(boolean v) {output_js_clean = v;} private boolean output_js_clean = true;
	public boolean Output_tidy() {return output_tidy;} public void Output_tidy_(boolean v) {output_tidy = v;} private boolean output_tidy = true;
	public int Read_til_stop_fwd() {return read_til_stop_fwd;} public void Read_til_stop_fwd_(int v) {read_til_stop_fwd = v;} private int read_til_stop_fwd = Xoapi_popups.Dflt_read_til_stop_fwd;
	public int Read_til_stop_bwd() {return read_til_stop_bwd;} public void Read_til_stop_bwd_(int v) {read_til_stop_bwd = v;} private int read_til_stop_bwd = Xoapi_popups.Dflt_read_til_stop_bwd;
	public int Tmpl_tkn_max() {return tmpl_tkn_max;}
	public void Tmpl_tkn_max_(int v) {
		if (v < 0) v = Int_.MaxValue;	// allow -1 as shortcut to deactivate
		orig_ctx.Tmpl_tkn_max_(v);
		wtxt_ctx.Tmpl_tkn_max_(v);
	}	private int tmpl_tkn_max = Xoapi_popups.Dflt_tmpl_tkn_max;
	public Bry_fmtr Html_fmtr() {return html_fmtr;}
	public void Init_by_wiki(Xow_wiki wiki) {
		this.wiki = wiki; this.app = wiki.App(); this.parser = wiki.Parser(); this.tkn_mkr = app.Tkn_mkr();
		this.orig_ctx = Xop_ctx.new_(wiki); this.wtxt_ctx = Xop_ctx.new_(wiki);
		Xop_lxr_mgr orig_lxr_mgr = Xop_lxr_mgr.Popup_lxr_mgr;
		orig_lxr_mgr.Init_by_wiki(wiki);
		this.orig_trie = orig_lxr_mgr.Trie(); this.wtxt_trie = parser.Wtxt_lxr_mgr().Trie();
		orig_ctx.Parse_tid_(Xop_parser_.Parse_tid_page_tmpl); wtxt_ctx.Parse_tid_(Xop_parser_.Parse_tid_page_wiki);
		orig_ctx.Xnde_names_tid_(Xop_parser_.Parse_tid_page_wiki);
		orig_ctx.Tid_is_popup_(true); wtxt_ctx.Tid_is_popup_(true);
		orig_root = tkn_mkr.Root(Bry_.Empty); wtxt_root = tkn_mkr.Root(Bry_.Empty);
		html_fmtr.Eval_mgr_(wiki.Eval_mgr());
		view_time_fmtr.Eval_mgr_(wiki.Eval_mgr());
		xwiki_fmtr.Eval_mgr_(wiki.Eval_mgr());
		ellipsis = wiki.Msg_mgr().Val_by_key_obj(Msg_key_ellipsis);
	}	private static byte[] Msg_key_ellipsis = Bry_.new_ascii_("ellipsis");
	private Hash_adp_bry xnde_id_ignore_list = Hash_adp_bry.ci_ascii_();
	public void Xnde_ignore_ids_(byte[] xnde_id_ignore_bry) {
		byte[][] ary = Bry_.Split(xnde_id_ignore_bry, Byte_ascii.Pipe);
		int ary_len = ary.length;
		xnde_id_ignore_list.Clear();
		for (int i = 0; i < ary_len; i++) {
			byte[] bry = ary[i];
			if (bry.length == 0) continue;	// ignore empty entries; EX: "a|"
			xnde_id_ignore_list.Add(bry, bry);
		}
	}
	private int words_needed_orig = -1;
	public byte[] Parse(Xow_popup_itm popup_itm, Xoa_page page, byte[] cur_wiki_domain, Xog_tab_itm cur_tab) {
		byte[] orig_src = page.Data_raw(); int orig_len = orig_src.length; if (orig_len == 0) return Bry_.Empty;
		String page_url = hdom_bfr.Add(page.Wiki().Domain_bry()).Add(Xoa_consts.Url_wiki_intermediary).Add(app.Url_converter_href()
			.Encode(page.Ttl().Full_db()))	// NOTE: was page.Url().Raw(), but that doesn't work for Special:Search; PAGE:en.w:Earth and "Quotations"; DATE:2014-06-29
			.XtoStrAndClear()
		;
		this.cancelable = popup_itm;
		int orig_pos = Xop_parser_.Doc_bgn_bos;
		orig_ctx.Clear(); 
		orig_ctx.Cur_page().Ttl_(page.Ttl());	// NOTE: must set cur_page, else page-dependent templates won't work; EX: {{FULLPAGENAME}}; PAGE:en.w:June_20; DATE:2014-06-20
		orig_ctx.Page_bgn(orig_root, orig_src);	
		Wtxt_ctx_init(true, orig_src);
		orig_ctx.Cur_page().Ttl_(page.Ttl());	// NOTE: must set cur_page, or rel lnkis won't work; EX: [[../A]]
		hdom_bfr.Clear();
		int scan_cur = scan_len;
		words_found_list.Clear();
		words_needed = popup_itm.Words_needed(); words_found = 0; stop_at_hdr_done = false;
		if (read_til_stop_fwd > 0 && words_needed != Int_.MaxValue) {
			words_needed_orig = words_needed;
			words_needed += read_til_stop_fwd;
		}
		else
			words_needed_orig = -1;
		if (orig_len < show_all_if_less_than) words_needed = Int_.MaxValue;
		if (words_needed == Int_.MaxValue) scan_max = Int_.MaxValue;	// if max words, automatically set scan to max
		while (words_found < words_needed) {
			if (cancelable.Canceled()) return null;
			orig_root.Clear();
			int orig_end = orig_pos + scan_cur; if (orig_end > orig_len) orig_end = orig_len; // limit to orig_len; EX: page is 16 bytes, but block is 1024
			if (cur_tab != null && cur_tab.Tab_is_loading()) return null;
			int new_pos = parser.Parse_to_stack_end(orig_root, orig_ctx, tkn_mkr, orig_src, orig_len, orig_trie, orig_pos, orig_end);
			if (cur_tab != null && cur_tab.Tab_is_loading()) return null;
			byte[] wtxt_bry = Parse_to_wtxt(orig_src);
			int wtxt_len = wtxt_bry.length; // if (wtxt_len == 0) {break;}// no wtxt; continue; EX: blank page
			wtxt_root.Clear();
			int wtxt_bgn = (orig_pos == Xop_parser_.Doc_bgn_bos) ? Xop_parser_.Doc_bgn_bos : 0;	// if first pass, parse from -1; needed for lxrs which assume nl at bos; EX: "*a"
			if (cur_tab != null && cur_tab.Tab_is_loading()) return null;
			parser.Parse_to_src_end(wtxt_root, wtxt_ctx, tkn_mkr, wtxt_bry, wtxt_trie, wtxt_bgn, wtxt_len);
			if (	wtxt_ctx.Stack_len() > 0				// dangling lnki / hdr / tblw
				&&	(orig_pos + scan_cur) < scan_max		// too much read; stop and give whatever's available; PAGE:en.w:List_of_air_forces; DATE:2014-06-18
				&&	scan_cur < orig_len						// only reparse if scan_cur is < entire page; needed for pages which have dangling items; EX:"<i>a"
				) {	
				new_pos = orig_pos; //;stack_0.Src_bgn();
				scan_cur += scan_len;
				wtxt_ctx.Clear();
			}
			else {
				Add_to_hdom_bfr(wtxt_root, wtxt_bry, wtxt_len);
				scan_cur = scan_len;
			}
			orig_pos = new_pos;
			if (	orig_pos == orig_len
				||	orig_pos > scan_max		// too much read; stop and give whatever's available
				)	break;
		}
		Adjust_for_header();
		if (	words_found >= words_needed	// don't add ellipsis if words_found < words_needed. note that it will add "..." if words_on_page == words_needed; DATE:2014-06-18
			&&	!stop_at_hdr_done			// don't add ellipsis if stopped at hdr
			)	
			hdom_bfr.Add(ellipsis);
		if (cancelable.Canceled()) return null;
		if (cur_tab != null && cur_tab.Tab_is_loading()) return null;
		byte[] rv = Parse_to_html(page);
		html_fmtr.Bld_bfr_many(hdom_bfr
		, rv
		, wiki.Lang().Dir_bry()
		, page_url, String_.new_utf8_(page.Ttl().Full_txt())
		, cur_wiki_domain	// NOTE: use cur_wiki, not page_wiki; DATE:2014-06-28
		, Get_xwiki_item(tmp_bfr, cur_wiki_domain, page.Wiki().Domain_bry())
		, gplx.ios.Io_size_.Xto_str(page.Data_raw().length), page.Revision_data().Modified_on().XtoStr_fmt_yyyy_MM_dd_HH_mm_ss()
		, Get_view_time(page.Ttl())
		, app.Fsys_mgr().Root_dir().To_http_file_bry()
		, popup_itm.Popup_id()
		);
		if (cancelable.Canceled()) return null;
		popup_itm.Popup_html_word_count_(words_found);
		return hdom_bfr.XtoAryAndClear();
	}
	private void Adjust_for_header() {
		Xow_popup_word[] words_found_ary = (Xow_popup_word[])words_found_list.XtoAryAndClear(Xow_popup_word.class);
		int words_found_all = words_found_ary.length;
		boolean read_til_stop_done = false;
		if (read_til_stop_fwd != -1 && words_needed_orig != -1) {
			Xow_popup_word hdr_word = null;
			for (int i = words_needed_orig; i < words_found_all; ++i) {
				Xow_popup_word word = words_found_ary[i];
				if (word.Tid() == Xop_tkn_itm_.Tid_hdr) {
					hdr_word = word;
					break;
				}
			}
			if (hdr_word == null) {
				if (words_needed_orig < words_found_all) {
					hdr_word = words_found_ary[words_needed_orig - 1];
					hdom_bfr.Delete_rng_to_end(hdr_word.Bfr_end());
				}
			}
			else {
				hdr_word = words_found_ary[hdr_word.Idx() - 1];
				hdom_bfr.Delete_rng_to_end(hdr_word.Bfr_end());
				read_til_stop_done = true;
			}
		}
		if (	read_til_stop_bwd != -1
			&&	!read_til_stop_done
			) {
//				Xow_popup_word hdr_word = null;
//				for (int i = words_needed_orig; i < words_found_all; ++i) {
//					Xow_popup_word word = words_found_ary[i];
//					if (word.Tid() == Xop_tkn_itm_.Tid_hdr) {
//						hdr_word = word;
//						break;
//					}
//				}
		}
	}
	private byte[] Parse_to_wtxt(byte[] src) {
		int subs_len = orig_root.Subs_len();
		for (int i = 0; i < subs_len; i++)
			orig_root.Subs_get(i).Tmpl_compile(orig_ctx, src, orig_props);
		return Xot_tmpl_wtr._.Write_all(orig_ctx, orig_root, src);
	}
	private byte[] Parse_to_html(Xoa_page page) {
		hdom_bfr.Add(notoc);
		byte[] hdom_bry = hdom_bfr.XtoAryAndClear();
		wtxt_root.Clear();
		Wtxt_ctx_init(Bool_.N, hdom_bry);
		parser.Parse_to_src_end(wtxt_root, wtxt_ctx, tkn_mkr, hdom_bry, wtxt_trie, Xop_parser_.Doc_bgn_bos, hdom_bry.length);
		wtxt_ctx.Page_end(wtxt_root, hdom_bry, hdom_bry.length);
		wiki.Html_mgr().Html_wtr().Write_all(hdom_bfr, wtxt_ctx, hctx, hdom_bry, wtxt_root);
		app.Html_mgr().Js_cleaner().Clean_bfr(wiki, page.Ttl(), hdom_bfr, 0);
		if (output_tidy)
			app.Html_mgr().Tidy_mgr().Run_tidy_html(page, hdom_bfr);
		return hdom_bfr.XtoAryAndClear();
	}
	private void Wtxt_ctx_init(boolean incremental, byte[] bry) {
		wtxt_ctx.Clear();
		wtxt_ctx.Para().Enabled_(!incremental);		// NOTE: if incremental, disable para; easier to work with \n rather than <p>; also, must be enabled before Page_bgn; DATE:2014-06-18DATE:2014-06-18
		wtxt_ctx.Lnke().Dangling_goes_on_stack_(incremental);
		wtxt_ctx.Page_bgn(wtxt_root, bry);
	}
	private byte[] Get_view_time(Xoa_ttl ttl) {
		byte[] view_time_item = Bry_.Empty;
		gplx.xowa.users.history.Xou_history_itm history_itm = app.User().History_mgr().Get_or_null(wiki.Domain_bry(), ttl.Full_txt());
		if (history_itm != null)
			view_time_item = view_time_fmtr.Bld_bry_many(hdom_bfr, history_itm.View_end().XtoStr_fmt_yyyy_MM_dd_HH_mm_ss());
		return view_time_item;
	}
	private byte[] Get_xwiki_item(Bry_bfr tmp_bfr, byte[] wiki_domain, byte[] page_domain) {
		return Bry_.Eq(wiki_domain, page_domain)
			? Bry_.Empty	// same domain; return "";
			: xwiki_fmtr.Bld_bry_many(tmp_bfr, page_domain);
	}
	private void Increment_words_found(Xop_tkn_itm tkn) {
		words_found_list.Add(new Xow_popup_word(tkn.Tkn_tid(), hdom_bfr.Len(), words_found, tkn.Src_bgn(), tkn.Src_end()));
		++words_found;
	}
	private void Add_to_hdom_bfr(Xop_tkn_itm tkn, byte[] wtxt_bry, int wtxt_len) {
		if (cancelable.Canceled()) return;
		boolean add = true, recur = true; Xop_xnde_tkn xnde = null;
		int tkn_src_bgn = tkn.Src_bgn(), tkn_src_end = tkn.Src_end();
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_root: 
				add = false;					// don't add root
				break;
			case Xop_tkn_itm_.Tid_txt:
				Increment_words_found(tkn);
				break;
			case Xop_tkn_itm_.Tid_tblw_tb: case Xop_tkn_itm_.Tid_tblw_tc: case Xop_tkn_itm_.Tid_tblw_td:
			case Xop_tkn_itm_.Tid_tblw_te: case Xop_tkn_itm_.Tid_tblw_th: case Xop_tkn_itm_.Tid_tblw_tr:
				add = recur = false;			// skip tblws
				break;
			case Xop_tkn_itm_.Tid_xnde:
				xnde = (Xop_xnde_tkn)tkn;
				switch (xnde.Tag().Id()) {
					case Xop_xnde_tag_.Tid_ref:
					case Xop_xnde_tag_.Tid_div:
					case Xop_xnde_tag_.Tid_gallery:
					case Xop_xnde_tag_.Tid_imageMap:
					case Xop_xnde_tag_.Tid_xowa_html:	// needed for Help:Options, else \n at top of doc; DATE:2014-06-22
					case Xop_xnde_tag_.Tid_table: case Xop_xnde_tag_.Tid_tr: case Xop_xnde_tag_.Tid_td: case Xop_xnde_tag_.Tid_th: 
					case Xop_xnde_tag_.Tid_caption: case Xop_xnde_tag_.Tid_thead: case Xop_xnde_tag_.Tid_tfoot: case Xop_xnde_tag_.Tid_tbody:
						add = recur = false;		// skip tblxs
						xnde = null;
						break;
					case Xop_xnde_tag_.Tid_br:
						if (hdom_bfr.Len_eq_0())	// don't add <br/> to start of document; needed for Help:Options, but good to have everywhere; DATE:2014-06-22
							add = recur = false;
						break;
					default:
						add = false;			// don't add xnde, but still recur
						if (Xnde_id_ignore_list_chk(xnde, wtxt_bry)) {
							recur = false;
							xnde = null;
						}
						break;
				}
				break;
			case Xop_tkn_itm_.Tid_lnke:
				Xop_lnke_tkn lnke = (Xop_lnke_tkn)tkn;
				switch (lnke.Lnke_typ()) {
					case Xop_lnke_tkn.Lnke_typ_brack:
						Add_to_hdom_bfr_recurse(tkn, wtxt_bry, wtxt_len, Bool_.N);	// add subs which are caption tkns; note that Bool_.N will add all words so that captions don't get split; EX: "a [http://a.org b c d]" -> "a b c d" if words_needed == 2;
						add = recur = false;	// ignore lnke, but add any text tkns; EX: [http://a.org b c d] -> "b c d"
						break;
					case Xop_lnke_tkn.Lnke_typ_text:
						Increment_words_found(tkn);	// increment words_found; EX: a http://b.org c -> 3 words;
						break;
				}
				break;
			case Xop_tkn_itm_.Tid_lnki: 
				Xop_lnki_tkn lnki = (Xop_lnki_tkn)tkn;
				switch (lnki.Ns_id()) {
					case Xow_ns_.Id_category:	// skip [[Category:]]
					case Xow_ns_.Id_file:		// skip [[File:]]
						add = recur = false;
						break;
					default:
						Increment_words_found(tkn);	// increment words_found; EX: a [[B|c d e]] f -> 3 words;
						break;
				}
				break;
			case Xop_tkn_itm_.Tid_space:
				if (	add_wtxt_skip_space					// previous tkn skipped add and set add_wtxt_skip_space to true
					&&	hdom_bfr.Match_end_byt_nl_or_bos()	// only ignore space if it will cause pre; note that some <ref>s will have spaces that should be preserved; EX:"a<ref>b</ref> c"; PAGE:en.w:Mehmed_the_Conqueror; DATE:2014-06-18
					)
					add = false;							// skip ws
				break;
			case Xop_tkn_itm_.Tid_newLine: {
				// heuristic to handle skipped <div> / <table> which does not skip \n; EX:"<div>a</div>\nb"; div is skipped, but "\n" remains; PAGE:en.w:Eulogy;DATE:2014-06-18
				int hdom_bfr_len = hdom_bfr.Len();
				if		(hdom_bfr_len == 0)					// don't add \n at bos; does not handle pages where bos intentionally has multiple \n\n
					add = false;
				else if (hdom_bfr_len > 2) {				// bounds check
					if (Wtxt_bfr_ends_w_2_nl(hdom_bfr_len))	// don't add \n if "\n\n"; does not handle intentional sequences of 2+ \n; 
						add = false;
				}
				break;
			}
			case Xop_tkn_itm_.Tid_hdr: {
				if (stop_at_hdr && words_found != 0) {		// if 1st word is header (no intro para), don't exit
					words_found = words_needed;
					stop_at_hdr_done = true;
					return;
				}
				Increment_words_found(tkn);	// count entire header as one word; not worth counting words in header
				recur = false;	// add entire tkn; do not recur
				int hdom_bfr_len = hdom_bfr.Len();
				if	(hdom_bfr_len > 2) {					// bounds check
					if (Wtxt_bfr_ends_w_2_nl(hdom_bfr_len))	// heuristic: 2 \n in bfr, and about to add a hdr tkn which starts with "\n"; delete last \n
						hdom_bfr.Del_by_1();
				}
				if (	tkn_src_end < wtxt_len				// bounds check
					&&	wtxt_bry[tkn_src_end] == Byte_ascii.NewLine	// hdr_tkn will not include trailing "\n". add it; note that this behavior is by design. NOTE:hdr.trailing_nl; DATE:2014-06-17
					) {
					hdom_bfr.Add_mid(wtxt_bry, tkn_src_bgn, tkn_src_end + 1);	// +1 to add the trailing \n
					add = false;
				}
				break;
			}
			default:
				break;
		}
		add_wtxt_skip_space = false;	// always reset; only used once above for Tid_space; DATE:2014-06-17
		if (add) {
			if (tkn_src_end - tkn_src_bgn > 0)	// handle paras which have src_bgn == src_end
				hdom_bfr.Add_mid(wtxt_bry, tkn_src_bgn, tkn_src_end);
		}
		else	// tkn not added
			add_wtxt_skip_space = true;	// skip next space; note this is done with member variable to handle recursive iteration; DATE:2014-06-17
		if (recur) {
			if (xnde != null) hdom_bfr.Add_mid(wtxt_bry, xnde.Tag_open_bgn(), xnde.Tag_open_end());		// add open tag; EX: "<span id=a>"
			Add_to_hdom_bfr_recurse(tkn, wtxt_bry, wtxt_len, Bool_.Y);
			if (xnde != null) hdom_bfr.Add_mid(wtxt_bry, xnde.Tag_close_bgn(), xnde.Tag_close_end());	// add close tag; EX: "</span>"
		}
	}
	private void Add_to_hdom_bfr_recurse(Xop_tkn_itm tkn, byte[] wtxt_bry, int wtxt_len, boolean chk_words_found) {
		int subs_len = tkn.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Xop_tkn_itm sub = tkn.Subs_get(i);
			Add_to_hdom_bfr(sub, wtxt_bry, wtxt_len);
			if (chk_words_found && words_found >= words_needed) break;
		}
	}
	private boolean Xnde_id_ignore_list_chk(Xop_xnde_tkn xnde, byte[] src) {
		Xop_xatr_itm[] atrs_ary = xnde.Atrs_ary();
		int atrs_len = atrs_ary.length;
		for (int i = 0; i < atrs_len; i++) {
			Xop_xatr_itm atr = atrs_ary[i];
			if (	Bry_.Eq(atr.Key_bry(), Html_atrs.Id_bry)
				&&	xnde_id_ignore_list.Get_by_bry(atr.Val_as_bry(src)) != null
				) {
                    return true;
			}
		}
		return false;
	}
	private boolean Wtxt_bfr_ends_w_2_nl(int hdom_bfr_len) {
		byte[] hdom_bfr_bry = hdom_bfr.Bfr();
		return
			(	hdom_bfr_bry[hdom_bfr_len - 1] == Byte_ascii.NewLine	// prv 2 bytes are \n
			&&	hdom_bfr_bry[hdom_bfr_len - 2] == Byte_ascii.NewLine
			);
	}
	private static final byte[]
	  Notoc_const = Bry_.new_ascii_(" __NOTOC__") // NOTE: always add a space else __NOTOC__ will be deactivated if last tkn is lnke; DATE:2014-06-22
	, Ellipsis_const = Bry_.new_ascii_("...")
	;
	private Bry_fmtr 
	  html_fmtr = Bry_fmtr.keys_(Xoapi_popups.Dflt_html_fmt_keys)
	, view_time_fmtr = Bry_fmtr.new_("\n    <span class='data_key'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.viewed-name');<>}:</span><span class='data_val'>~{view_time}</span>", "view_time")
	, xwiki_fmtr = Bry_fmtr.new_("\n    <span class='data_key'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.wiki-name');<>}:</span><span class='data_val'>~{wiki_domain}</span>", "wiki_domain")
	;
}
class Xow_popup_word {
	public Xow_popup_word(int tid, int bfr_bgn, int idx, int bgn, int end) {this.tid = tid; this.bfr_bgn = bfr_bgn; this.idx = idx; this.bgn = bgn; this.end = end;}
	public int Tid() {return tid;} private int tid;
	public int Bfr_bgn() {return bfr_bgn;} private int bfr_bgn;
	public int Bfr_end() {return bfr_bgn + this.Len();}
	public int Idx() {return idx;} private int idx;
	public int Bgn() {return bgn;} private int bgn;
	public int End() {return end;} private int end;
	public int Len() {return end - bgn;}
}
