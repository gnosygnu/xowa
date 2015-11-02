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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.core.btries.*;
import gplx.xowa.langs.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.tmpls.*;
public class Xop_parser {	// NOTE: parsers are reused; do not keep any read-write state
	private final Xowe_wiki wiki;
	private final Btrie_fast_mgr tmpl_trie, wtxt_trie;
	Xop_parser(Xowe_wiki wiki, Xop_lxr_mgr tmpl_lxr_mgr, Xop_lxr_mgr wtxt_lxr_mgr) {
		this.wiki = wiki;
		this.tmpl_lxr_mgr = tmpl_lxr_mgr; this.tmpl_trie = tmpl_lxr_mgr.Trie();
		this.wtxt_lxr_mgr = wtxt_lxr_mgr; this.wtxt_trie = wtxt_lxr_mgr.Trie();
	}
	public Xop_lxr_mgr Tmpl_lxr_mgr() {return tmpl_lxr_mgr;} private final Xop_lxr_mgr tmpl_lxr_mgr;
	public Xop_lxr_mgr Wtxt_lxr_mgr() {return wtxt_lxr_mgr;} private final Xop_lxr_mgr wtxt_lxr_mgr;
	public void Init_by_wiki(Xowe_wiki wiki) {
		tmpl_lxr_mgr.Init_by_wiki(wiki);
		wtxt_lxr_mgr.Init_by_wiki(wiki);
	}
	public void Init_by_lang(Xol_lang_itm lang) {
		tmpl_lxr_mgr.Init_by_lang(lang);
		wtxt_lxr_mgr.Init_by_lang(lang);
	}
	public byte[] Parse_text_to_html(Xop_ctx ctx, byte[] src) {
		Bry_bfr bfr = Xoa_app_.Utl__bfr_mkr().Get_b512();
		Parse_text_to_html(bfr, ctx.Cur_page(), false, src);
		return bfr.To_bry_and_rls();
	}
	public void Parse_text_to_html(Bry_bfr trg, Xoae_page page, boolean para_enabled, byte[] src) {
		Xop_ctx ctx = Xop_ctx.new_sub_(wiki, page);
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		Xop_parser parser = wiki.Parser_mgr().Main();
		byte[] wtxt = parser.Parse_text_to_wtxt(root, ctx, tkn_mkr, src);
		root.Reset();
		ctx.Para().Enabled_(para_enabled);
		parser.Parse_wtxt_to_wdom(root, ctx, ctx.Tkn_mkr(), wtxt, Xop_parser_.Doc_bgn_bos);
		wiki.Html_mgr().Html_wtr().Write_all(trg, ctx, wtxt, root);
	}
	public Xot_defn_tmpl Parse_text_to_defn_obj(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xow_ns ns, byte[] name, byte[] src) {
		Xot_defn_tmpl rv = new Xot_defn_tmpl();
		Parse_text_to_defn(rv, ctx, tkn_mkr, ns, name, src); 
		return rv;
	}
	public void Parse_text_to_defn(Xot_defn_tmpl tmpl, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xow_ns ns, byte[] name, byte[] src) {
		Xop_root_tkn root = tkn_mkr.Root(src);
		Parse(root, ctx, tkn_mkr, src, Xop_parser_.Parse_tid_tmpl, tmpl_trie, Xop_parser_.Doc_bgn_bos);
		tmpl_props.OnlyInclude_exists = false; int subs_len = root.Subs_len();
		for (int i = 0; i < subs_len; i++)
			root.Subs_get(i).Tmpl_compile(ctx, src, tmpl_props);
		boolean only_include_chk = Bry_find_.Find_fwd(src, Xop_xnde_tag_.Name_onlyinclude, 0, src.length) != Bry_find_.Not_found;
		if (only_include_chk) tmpl_props.OnlyInclude_exists = true;
		tmpl.Init_by_new(ns, name, src, root, tmpl_props.OnlyInclude_exists);
	}	private Xot_compile_data tmpl_props = new Xot_compile_data();
	public void Parse_page_all_clear(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src) {
		ctx.Cur_page().Clear(); ctx.App().Msg_log().Clear();
		Parse_text_to_wdom(root, ctx, tkn_mkr, src, Xop_parser_.Doc_bgn_bos);
	}
	public Xop_root_tkn Parse_text_to_wdom_old_ctx(Xop_ctx old_ctx, byte[] src, boolean doc_bgn_pos) {return Parse_text_to_wdom(Xop_ctx.new_sub_(old_ctx.Wiki()), src, doc_bgn_pos);}
	public Xop_root_tkn Parse_text_to_wdom(Xop_ctx new_ctx, byte[] src, boolean doc_bgn_pos) {
		new_ctx.Para().Enabled_n_();
		Xop_tkn_mkr tkn_mkr = new_ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		Parse_text_to_wdom(root, new_ctx, tkn_mkr, src, doc_bgn_pos ? Xop_parser_.Doc_bgn_bos : Xop_parser_.Doc_bgn_char_0);
		return root;
	}
	public void Parse_text_to_wdom(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, int doc_bgn_pos) {
		byte parse_tid_old = ctx.Parse_tid();// NOTE: must store parse_tid b/c ctx can be reused by other classes
		ctx.Parse_tid_(Xop_parser_.Parse_tid_page_tmpl);
		root.Reset();
		byte[] mid_bry = Parse_text_to_wtxt(root, ctx, tkn_mkr, src);
		root.Data_mid_(mid_bry);
		root.Reset();
		Parse_wtxt_to_wdom(root, ctx, tkn_mkr, mid_bry, doc_bgn_pos);
		ctx.Parse_tid_(parse_tid_old);
	}
	public byte[] Parse_text_to_wtxt(byte[] src) {
		Xop_ctx ctx = Xop_ctx.new_sub_(wiki);
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		Xop_root_tkn root = tkn_mkr.Root(src);
		return wiki.Parser_mgr().Main().Parse_text_to_wtxt(root, ctx, tkn_mkr, src);
	}
	public byte[] Parse_text_to_wtxt(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src) {
		Parse(root, ctx, tkn_mkr, src, Xop_parser_.Parse_tid_page_tmpl, tmpl_trie, Xop_parser_.Doc_bgn_bos);
		int subs_len = root.Subs_len();
		for (int i = 0; i < subs_len; i++)
			root.Subs_get(i).Tmpl_compile(ctx, src, tmpl_props);
		return Xot_tmpl_wtr.Instance.Write_all(ctx, root, src);	// NOTE: generate new src since most callers will use it;
	}
	public void Parse_wtxt_to_wdom(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] wtxt, int doc_bgn_pos) {
		root.Root_src_(wtxt);	// always set latest src; needed for Parse_all wherein src will first be raw and then parsed tmpl
		Parse(root, ctx, tkn_mkr, wtxt, Xop_parser_.Parse_tid_page_wiki, wtxt_trie, doc_bgn_pos);
	}
	private void Parse(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, byte parse_type, Btrie_fast_mgr trie, int doc_bgn_pos) {
		int len = src.length; if (len == 0) return;	// nothing to parse;
		byte parse_tid_old = ctx.Parse_tid();	// NOTE: must store parse_tid b/c ctx can be reused by other classes
		ctx.Parse_tid_(parse_type);
		ctx.Page_bgn(root, src);
		ctx.App().Parser_mgr().Core__uniq_mgr().Clear();
		Parse_to_src_end(root, ctx, tkn_mkr, src, trie, doc_bgn_pos, len);
		ctx.Page_end(root, src, len);
		ctx.Parse_tid_(parse_tid_old);
	}
	public int Parse_to_src_end(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, Btrie_fast_mgr trie, int pos, int len) {
		byte b = pos == -1 ? Byte_ascii.Nl : src[pos];	// simulate newLine at bgn of src; needed for lxrs which rely on \n (EX: "=a=")
		int txt_bgn = pos == -1 ? 0 : pos; Xop_tkn_itm txt_tkn = null;
		while (true) {
			Object o = trie.Match_bgn_w_byte(b, src, pos, len);
			if (o == null)				// no lxr found; char is txt; increment pos
				pos++;
			else {						// lxr found
				Xop_lxr lxr = (Xop_lxr)o;
				if (txt_bgn != pos)		// chars exist between pos and txt_bgn; make txt_tkn; see NOTE_1
					txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
				ctx.Lxr_make_(true);
				pos = lxr.Make_tkn(ctx, tkn_mkr, root, src, len, pos, trie.Match_pos());
				if (ctx.Lxr_make()) {txt_bgn = pos; txt_tkn = null;}	// reset txt_tkn
			}
			if (pos == len) break;
			b = src[pos];
		}
		if (txt_bgn != pos) txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
		return pos;
	}
	public int Parse_to_stack_end(Xop_root_tkn root, Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, byte[] src, int src_len, Btrie_fast_mgr trie, int pos, int end) {
		byte b = pos == -1 ? Byte_ascii.Nl : src[pos];	// simulate \n at bgn of src; needed for lxrs which rely on \n (EX: "=a=")
		int txt_bgn = pos == -1 ? 0 : pos; Xop_tkn_itm txt_tkn = null;
		Xop_lxr lxr = null;
		while (true) {
			lxr = null;
			Object o = trie.Match_bgn_w_byte(b, src, pos, src_len);
			if (o == null)				// no lxr found; char is txt; increment pos
				pos++;
			else {						// lxr found
				lxr = (Xop_lxr)o;
				if (txt_bgn != pos)		// chars exist between pos and txt_bgn; make txt_tkn; see NOTE_1
					txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
				ctx.Lxr_make_(true);
				pos = lxr.Make_tkn(ctx, tkn_mkr, root, src, src_len, pos, trie.Match_pos());
				if (ctx.Lxr_make()) {txt_bgn = pos; txt_tkn = null;}	// reset txt_tkn
			}
			if (	pos >= end 
				&&	ctx.Stack_len() == 0	// check stack is 0 to avoid dangling templates
				) {
				if		(o == null) {}		// last sequence is not text; avoids splitting words across blocks; EX: 4 block and word of "abcde" will split to "abcd" and "e"
				else {
					if (lxr != null) { 
						boolean stop = true;
						switch (lxr.Lxr_tid()) {
							case Xop_lxr_.Tid_eq:
							case Xop_lxr_.Tid_nl:
								stop = false;
								break;
						}
						if (stop)
							break;
					}
					else {
						break;
					}
				}
			}
			if (pos >= src_len) break;
			b = src[pos];
		}
		if (txt_bgn != pos) txt_tkn = Txt_add(ctx, tkn_mkr, root, txt_tkn, txt_bgn, pos);
		return pos;
	}
	private static Xop_tkn_itm Txt_add(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, Xop_tkn_itm tkn, int txt_bgn, int pos) {
		if (pos == Xop_parser_.Doc_bgn_bos) return null;	// don't make txt_tkn for Bos_pos
		if (tkn == null) {									// no existing txt_tkn; create new one
			tkn = tkn_mkr.Txt(txt_bgn, pos);
			ctx.Subs_add(root, tkn);
		}
		else												// existing txt_tkn; happens for false matches; EX: abc[[\nef[[a]]; see NOTE_1
			tkn.Src_end_(pos);
		return tkn;
	}
	public static Xop_parser new_(Xowe_wiki wiki, Xop_lxr_mgr tmpl_lxr_mgr, Xop_lxr_mgr wtxt_lxr_mgr) {return new Xop_parser(wiki, tmpl_lxr_mgr, wtxt_lxr_mgr);}
	public static Xop_parser new_wiki(Xowe_wiki wiki) {
		Xop_parser rv = new Xop_parser(wiki, Xop_lxr_mgr.new_tmpl_(), Xop_lxr_mgr.new_wiki_());
		rv.Init_by_wiki(wiki);
		rv.Init_by_lang(wiki.Lang());
		return rv;
	}
}
/*
NOTE_1
abc[[\nef[[a]]
<BOS>	: txt_bgn = 0; txt_tkn = null;
abc		: increment pos
[[\n	: lnki lxr
		: (1): txt_tkn == null, so create txt_tkn with (0, 3)
		: (2): lxr.Make_tkn() entered for lnki; however \n exits lnki
		: (3): note that ctx.Lxr_make == false, so txt_bgn/txt_tkn is not reset
ef		: still just text; increment pos
[[a]]	: lnki entered
		: (1): txt_tkn != null; set end to 8
		: (2): lxr.Make_tkn() entered and lnki made
		: (3): note that ctx.Lxr_make == true, so txt_bgn = 13 and txt_tkn = null
*/
