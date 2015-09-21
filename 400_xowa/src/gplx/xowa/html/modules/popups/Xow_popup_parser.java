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
import gplx.core.btries.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.apis.xowa.html.modules.*; import gplx.xowa.html.modules.popups.keeplists.*;
import gplx.xowa.gui.views.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*; import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.tmpls.*;	
public class Xow_popup_parser {
	private Xoae_app app; private Xowe_wiki wiki; private Xop_parser parser;
	private Btrie_fast_mgr tmpl_trie, wtxt_trie; private Xop_tkn_mkr tkn_mkr;
	private Xop_ctx tmpl_ctx; private Xop_root_tkn tmpl_root, wtxt_root; private Xot_compile_data tmpl_props = new Xot_compile_data();		
	private Xoh_wtr_ctx hctx = Xoh_wtr_ctx.Popup;
	private Xow_popup_anchor_finder hdr_finder = new Xow_popup_anchor_finder();
	public Xow_popup_cfg Cfg() {return cfg;} private Xow_popup_cfg cfg = new Xow_popup_cfg();
	public Xow_popup_wrdx_mkr Wrdx_mkr() {return wrdx_mkr;} private Xow_popup_wrdx_mkr wrdx_mkr = new Xow_popup_wrdx_mkr();
	public Xow_popup_html_mkr Html_mkr() {return html_mkr;} private Xow_popup_html_mkr html_mkr = new Xow_popup_html_mkr();
	public Xow_popup_parser_data Data() {return data;} private Xow_popup_parser_data data = new Xow_popup_parser_data();
	public Xop_keeplist_wiki Tmpl_keeplist() {return tmpl_keeplist;} private Xop_keeplist_wiki tmpl_keeplist; // private byte[] tmpl_keeplist_bry = Bry_.Empty;
	public Xop_ctx Wtxt_ctx() {return wtxt_ctx;} private Xop_ctx wtxt_ctx;
	public void Tmpl_tkn_max_(int v) {
		if (v < 0) v = Int_.Max_value;	// allow -1 as shortcut to deactivate
		tmpl_ctx.Tmpl_tkn_max_(v);
		wtxt_ctx.Tmpl_tkn_max_(v);
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		this.wiki = wiki; this.app = wiki.Appe(); this.parser = wiki.Parser_mgr().Main(); this.tkn_mkr = app.Parser_mgr().Tkn_mkr();
		this.tmpl_ctx = Xop_ctx.new_(wiki); this.wtxt_ctx = Xop_ctx.new_(wiki);
		Xop_lxr_mgr tmpl_lxr_mgr = Xop_lxr_mgr.Popup_lxr_mgr;
		tmpl_lxr_mgr.Init_by_wiki(wiki);
		this.tmpl_trie = tmpl_lxr_mgr.Trie(); this.wtxt_trie = parser.Wtxt_lxr_mgr().Trie();
		tmpl_ctx.Parse_tid_(Xop_parser_.Parse_tid_page_tmpl); wtxt_ctx.Parse_tid_(Xop_parser_.Parse_tid_page_wiki);
		tmpl_ctx.Xnde_names_tid_(Xop_parser_.Parse_tid_page_wiki);
		tmpl_ctx.Tid_is_popup_(true); wtxt_ctx.Tid_is_popup_(true);
		tmpl_root = tkn_mkr.Root(Bry_.Empty); wtxt_root = tkn_mkr.Root(Bry_.Empty);
		html_mkr.Ctor(app, wiki);
		cfg.Ellipsis_(wiki.Msg_mgr().Val_by_key_obj(Xow_popup_cfg.Msg_key_ellipsis));
	}
	public void Tmpl_keeplist_(Xop_keeplist_wiki v) {this.tmpl_keeplist = v;}
	public void Tmpl_keeplist_init_(byte[] raw) {
		if (tmpl_keeplist == null) {
			tmpl_keeplist = new Xop_keeplist_wiki(wiki);
			tmpl_ctx.Tmpl_keeplist_(tmpl_keeplist);
		}
		if (!Bry_.Has_at_end(raw, Byte_ascii.Nl_bry)) raw = Bry_.Add(raw, Byte_ascii.Nl_bry);
		tmpl_keeplist.Srl().Load_by_bry(raw);
	}
	private boolean Canceled(Xow_popup_itm popup_itm, Xog_tab_itm cur_tab) {return popup_itm.Canceled() || cur_tab != null && cur_tab.Tab_is_loading();}
	private void Init_ctxs(byte[] tmpl_src, Xoa_ttl ttl) {
		tmpl_ctx.Clear(); 
		tmpl_ctx.Cur_page().Ttl_(ttl);	// NOTE: must set cur_page, else page-dependent templates won't work; EX: {{FULLPAGENAME}}; PAGE:en.w:June_20; DATE:2014-06-20
		tmpl_ctx.Cur_page().Html_data().Html_restricted_(data.Html_restricted());	// NOTE: must set data.Html_restricted() if Special:XowaPopupHistory
		tmpl_ctx.Page_bgn(tmpl_root, tmpl_src);	
		Wtxt_ctx_init(true, tmpl_src);
		wtxt_ctx.Cur_page().Ttl_(ttl);	// NOTE: must set cur_page, or rel lnkis won't work; EX: [[../A]]
	}
	public byte[] Parse(Xowe_wiki cur_wiki, Xoae_page page, Xog_tab_itm cur_tab, Xow_popup_itm popup_itm) {	// NOTE: must pass cur_wiki for xwiki label; DATE:2014-07-02
		if (Bry_.Eq(popup_itm.Wiki_domain(), Xow_domain_itm_.Bry__wikidata)) {
			data.Wrdx_bfr().Add(app.Wiki_mgr().Wdata_mgr().Popup_text(page));
		}
		else {
			byte[] tmpl_src = page.Data_raw(); int tmpl_len = tmpl_src.length; if (tmpl_len == 0) return Bry_.Empty;
			int tmpl_bgn_orig = Xow_popup_parser_.Tmpl_bgn_get_(app, popup_itm, page.Ttl(), hdr_finder, tmpl_src, tmpl_len);
			int tmpl_bgn = tmpl_bgn_orig;
			int tmpl_read_len_cur = cfg.Tmpl_read_len();
			wrdx_mkr.Init();
			data.Init(cfg, popup_itm, tmpl_len);
			Init_ctxs(tmpl_src, page.Ttl());
			while (data.Words_needed_chk()) {
				if (Canceled(popup_itm, cur_tab)) return null;
				tmpl_root.Clear();
				int tmpl_end = tmpl_bgn + tmpl_read_len_cur; if (tmpl_end > tmpl_len) tmpl_end = tmpl_len; // limit to tmpl_len; EX: page is 16 bytes, but block is 1024
				int new_tmpl_bgn = parser.Parse_to_stack_end(tmpl_root, tmpl_ctx, tkn_mkr, tmpl_src, tmpl_len, tmpl_trie, tmpl_bgn, tmpl_end);
				if (Canceled(popup_itm, cur_tab)) return null;
				byte[] wtxt_bry = Parse_to_wtxt(tmpl_src);
				int wtxt_len = wtxt_bry.length;
				wtxt_root.Clear();
				int wtxt_bgn = (tmpl_bgn == Xop_parser_.Doc_bgn_bos) ? Xop_parser_.Doc_bgn_bos : 0;	// if first pass, parse from -1; needed for lxrs which assume nl at bos; EX: "*a"
				if (Canceled(popup_itm, cur_tab)) return null;
				parser.Parse_to_src_end(wtxt_root, wtxt_ctx, tkn_mkr, wtxt_bry, wtxt_trie, wtxt_bgn, wtxt_len);
				if (	wtxt_ctx.Stack_len() > 0									// dangling lnki / hdr / tblw
					&&	(tmpl_bgn + tmpl_read_len_cur) < data.Tmpl_max()			// too much read; stop and give whatever's available; PAGE:en.w:List_of_air_forces; DATE:2014-06-18
					&&	tmpl_read_len_cur < tmpl_len								// only reparse if tmpl_read_len_cur is < entire page; needed for pages which have dangling items; EX:"<i>a"
					) {	
					new_tmpl_bgn = tmpl_bgn;
					tmpl_read_len_cur = Xow_popup_parser_.Calc_read_len(wtxt_ctx, tmpl_read_len_cur, cfg.Tmpl_read_len(), tmpl_src, tmpl_bgn, tmpl_end);
					wtxt_ctx.Clear();
				}
				else {
					wrdx_mkr.Process_tkn(cfg, data, data.Wrdx_bfr(), wtxt_root, wtxt_bry, wtxt_len);
					tmpl_read_len_cur = cfg.Tmpl_read_len();
				}
				tmpl_bgn = new_tmpl_bgn;
				data.Tmpl_loop_count_add();
				if (	tmpl_bgn == tmpl_len								// end of template
					||	tmpl_bgn - tmpl_bgn_orig >	data.Tmpl_max()			// too much read; stop and give whatever's available
					)
					break;
			}
			if (Canceled(popup_itm, cur_tab)) return null;
			Parse_wrdx_to_html(popup_itm, data.Wrdx_bfr());
		}
		byte[] rv = html_mkr.Bld(cur_wiki, page, popup_itm, data.Wrdx_bfr());
		return (Canceled(popup_itm, cur_tab)) ? null : rv;
	}
	private void Parse_wrdx_to_html(Xow_popup_itm popup_itm, Bry_bfr wrdx_bfr) {
		Adjust_wrdx_end(popup_itm, wrdx_bfr);
		wrdx_bfr.Add(cfg.Notoc());	// always add notoc at end
		byte[] wrdx_bry = wrdx_bfr.Xto_bry_and_clear();
		wtxt_root.Clear();			// now start parsing wrdx_bry from wtxt to html
		Wtxt_ctx_init(false, wrdx_bry);
		parser.Parse_to_src_end(wtxt_root, wtxt_ctx, tkn_mkr, wrdx_bry, wtxt_trie, Xop_parser_.Doc_bgn_bos, wrdx_bry.length);
		wtxt_ctx.Page_end(wtxt_root, wrdx_bry, wrdx_bry.length);
		wiki.Html_mgr().Html_wtr().Write_all(wrdx_bfr, wtxt_ctx, hctx, wrdx_bry, wtxt_root);
	}
	private void Adjust_wrdx_end(Xow_popup_itm popup_itm, Bry_bfr wrdx_bfr) {
		popup_itm.Words_found_(data.Words_found());
		if (popup_itm.Mode_all()) return; // mode_all needs no adjustments
		Xow_popup_word[] words = data.Words_found_ary();
		int words_len = words.length;
		int last_word_idx = -1; Xow_popup_word last_hdr_tkn = null;
		int words_needed_val = data.Words_needed_val();
		if (cfg.Read_til_stop_fwd() != -1) {
			for (int i = words_needed_val; i < words_len; ++i) {		// find hdr after orig
				Xow_popup_word word = words[i];
				if (word.Tid() == Xop_tkn_itm_.Tid_hdr) {
					last_hdr_tkn = word;
					break;
				}
			}
			last_word_idx = (last_hdr_tkn == null)						// no hdr found
				? words_needed_val - List_adp_.Base1						// get last word
				: last_hdr_tkn.Idx() - 1								// get word before hdr
				;
			if (last_word_idx >= words_len)
				last_word_idx = -1;
		}
		boolean page_partially_parsed = data.Words_found() == data.Words_needed_max();	// adhoc way of figuring out if parsing prematurely stopped before eos; PAGE:en.q:Anaximander DATE:2014-07-02
		if (	cfg.Read_til_stop_bwd() != -1
			&&	page_partially_parsed			// never read bwd if entire tmpl is read; DATE:2014-07-01
			) {
			int read_bwd_end = last_word_idx == -1 ? words_len - 1 : last_word_idx;	// if !cfg.Read_til_stop_fwd() use last_wrd, else use read_fwd's last_word
			int read_bwd_bgn = read_bwd_end - cfg.Read_til_stop_bwd();
			if (read_bwd_bgn > -1) {	// handle pages with "==a==" near start
				int last_hdr_idx = -1;
				for (int i = read_bwd_end; i >= read_bwd_bgn; i--) {
					Xow_popup_word word = words[i];
					if (word.Tid() == Xop_tkn_itm_.Tid_hdr) {
						if (last_hdr_idx == -1)				// last_hdr_idx not set
							last_hdr_idx = i;				// set it
						else {								// last_hdr_idx set
							if (i + 1 == last_hdr_idx)		// two consecutive hdrs; update earlier and continue
								last_hdr_idx = i;
							else							// earlier hdr; ignore it and take later one
								break;
						}
						last_hdr_tkn = word;
					}
				}
				if (last_hdr_idx != -1)						// hdr found
					last_word_idx = last_hdr_idx - 1;		// get word before last_word_idx
			}
		}
		if (last_word_idx != -1) {
			Xow_popup_word last_word = words[last_word_idx];
			wrdx_bfr.Delete_rng_to_end(last_word.Bfr_end());// delete everything after last_word
			popup_itm.Words_found_(last_word_idx + List_adp_.Base1);	// last_word_idx = 0 -> words_found = 1
			if (last_word.Tid() == Xop_tkn_itm_.Tid_hdr)	// on odd case where hdr is still last word, add \n else text will literally be "==A==" b/c no trailing \n
				wrdx_bfr.Add_byte_nl();
		}
		if (last_hdr_tkn != null) {
			wrdx_bfr.Trim_end(Byte_ascii.Nl);
			byte[] last_hdr_bry = ((Xop_hdr_tkn)last_hdr_tkn.Tkn()).Hdr_toc_text();
			html_mkr.Fmtr_next_sect().Bld_bfr_one(wrdx_bfr, last_hdr_bry);
		}
		else {
			if (page_partially_parsed)
				wrdx_bfr.Add(cfg.Ellipsis());
		}
	}
	private void Wtxt_ctx_init(boolean incremental, byte[] bry) {
		wtxt_ctx.Clear();
		wtxt_ctx.Cur_page().Html_data().Html_restricted_(data.Html_restricted());
		wtxt_ctx.Para().Enabled_(!incremental);		// NOTE: if incremental, disable para; easier to work with \n rather than <p>; also, must be enabled before Page_bgn; DATE:2014-06-18DATE:2014-06-18
		wtxt_ctx.Lnke().Dangling_goes_on_stack_(incremental);
		wtxt_ctx.Page_bgn(wtxt_root, bry);
	}
	private byte[] Parse_to_wtxt(byte[] src) {
		int subs_len = tmpl_root.Subs_len();
		for (int i = 0; i < subs_len; i++)
			tmpl_root.Subs_get(i).Tmpl_compile(tmpl_ctx, src, tmpl_props);
		return Xot_tmpl_wtr._.Write_all(tmpl_ctx, tmpl_root, src);
	}
}
class Xow_popup_parser_ {
	public static int Tmpl_bgn_get_(Xoae_app app, Xow_popup_itm itm, Xoa_ttl page_ttl, Xow_popup_anchor_finder hdr_finder, byte[] src, int src_len) {
		int rv = Xop_parser_.Doc_bgn_bos; if (itm.Mode_all()) return rv;
		byte[] anch = itm.Page_href()[0] == Byte_ascii.Hash ? Bry_.Mid(Xoa_app_.Utl__encoder_mgr().Href().Decode(itm.Page_href()), 1) : page_ttl.Anch_txt();
		if (anch == null) return rv;
		int hdr_bgn = hdr_finder.Find(src, src_len, anch, rv);	// NOTE: starting search from Xop_parser_.Doc_bgn_bos
		return hdr_bgn == Bry_find_.Not_found ? rv : hdr_bgn;
	}
	public static int Calc_read_len(Xop_ctx ctx, int tmpl_read_cur, int tmpl_read_len, byte[] src, int bgn, int end) {// DATE:2014-07-19
		int rv_default = tmpl_read_cur + tmpl_read_len;
		Xop_tkn_itm tkn = Get_expensive_dangling_tkn(ctx);
		if (tkn == null) return rv_default;					// no expensive tkns found; return rv_default; EX: headers are not considered expensive
		int tkn_end = Calc_tkn_end(tkn, src, end);
		if (tkn_end == Bry_.NotFound) return rv_default;	// no end found; return rv_default; might want to return src.length at future date
		return tkn_end - bgn;
	}
	private static Xop_tkn_itm Get_expensive_dangling_tkn(Xop_ctx ctx) {
		int stack_len = ctx.Stack_len();
		if (stack_len == 0) return null;	// no dangling tkns; just add tmpl_read_len; shouldn't happen, but keep prior behavior
		for (int i = 0; i < stack_len; ++i) {
			Xop_tkn_itm tkn = ctx.Stack_get(i);
			switch (tkn.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_tblw_tb:
					return tkn;
			}
		}
		return null;
	}
	private static int Calc_tkn_end(Xop_tkn_itm tkn, byte[] src, int pos) {
		byte[] end_bry = null;
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_tblw_tb:	// "{|" can be expensive; PAGE:en.w:List_of_countries_and_dependencies_by_area; DATE:2014-07-19
				end_bry = Xop_tblw_lxr.Hook_te;
				break;
		}
		if (end_bry == null) return Bry_.NotFound;	// no end defined for tkn; return null which should revert to dflt
		int end_pos = Bry_find_.Find_fwd(src, end_bry, pos);
		return end_pos == Bry_find_.Not_found ? Bry_find_.Not_found : end_pos + end_bry.length;
	}
}
