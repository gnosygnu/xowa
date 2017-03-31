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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.core.envs.*; import gplx.xowa.apps.progs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.strings.*;
import gplx.langs.htmls.entitys.*;
import gplx.xowa.parsers.logs.*; import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.miscs.*; import gplx.xowa.parsers.htmls.*;
public class Xop_xnde_wkr implements Xop_ctx_wkr {
	public void Ctor_ctx(Xop_ctx ctx) {}
	public boolean Pre_at_bos() {return pre_at_bos;} public void Pre_at_bos_(boolean v) {pre_at_bos = v;} private boolean pre_at_bos;
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {} 
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {this.Clear();}
	private void Clear() {pre_at_bos = false;}
	public void AutoClose(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_tkn_itm tkn, int closing_tkn_tid) {
		Xop_xnde_tkn xnde = (Xop_xnde_tkn)tkn;
		xnde.Src_end_(src_len);
		xnde.Subs_move(root);
		if (closing_tkn_tid == Xop_tkn_itm_.Tid_lnki_end) Xop_xnde_wkr_.AutoClose_handle_dangling_nde_in_caption(root, tkn);	// PAGE:sr.w:Сићевачка_клисура; DATE:2014-07-03
		ctx.Msg_log().Add_itm_none(Xop_xnde_log.Dangling_xnde, src, xnde.Src_bgn(), xnde.Name_end());	// NOTE: xnde.Src_bgn to start at <; xnde.Name_end b/c xnde.Src_end is -1
	}
	private static final    Btrie_rv trv = new Btrie_rv();
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		if (bgn_pos == Xop_parser_.Doc_bgn_bos) bgn_pos = 0;			// do not allow -1 pos
		if (cur_pos == src_len) return ctx.Lxr_make_txt_(src_len);		// "<" is EOS; don't raise error;
		Xop_tkn_itm last_tkn = ctx.Stack_get_last();					// BLOCK:invalid_ttl_check
		if (	last_tkn != null
			&&	last_tkn.Tkn_tid() == Xop_tkn_itm_.Tid_lnki) {
			Xop_lnki_tkn lnki = (Xop_lnki_tkn)last_tkn;
			if (	lnki.Pipe_count_is_zero()
				// &&	!Xop_lnki_wkr_.Parse_ttl(ctx, src, lnki, bgn_pos)	// NOTE: no ttl parse check; <xnde> in ttl is automatically invalid; EX: [[a<b>c</b>|d]]; "a" is valid ttl, but "a<b>c</b>" is not
				) {
				ctx.Stack_pop_last();
				return Xop_lnki_wkr_.Invalidate_lnki(ctx, src, root, lnki, bgn_pos);
			}
		}

		// check for "</"
		byte cur_byt = src[cur_pos];
		boolean tag_is_closing = false;
		if (cur_byt == Byte_ascii.Slash) { // "</"
			tag_is_closing = true;
			++cur_pos;
			if (cur_pos == src_len) return ctx.Lxr_make_txt_(src_len);	// "</" is EOS
			cur_byt = src[cur_pos];
		}
		// get node_name
		Btrie_slim_mgr tag_trie = ctx.Xnde_tag_regy().Get_trie(ctx.Xnde_names_tid());
		Object tag_obj; int atrs_bgn_pos;
		synchronized (trv) {
			tag_trie.Match_at_w_b0(trv, cur_byt, src, cur_pos, src_len);	// NOTE:tag_obj can be null in wiki_tmpl mode; EX: "<ul" is not a valid tag in wiki_tmpl, but is valid in wiki_main
			tag_obj = trv.Obj();
			atrs_bgn_pos = trv.Pos();
		}

		int name_bgn = cur_pos, name_end = atrs_bgn_pos;
		int tag_end_pos = atrs_bgn_pos - 1;
		if (tag_obj != null) {
			if (atrs_bgn_pos >= src_len) return ctx.Lxr_make_txt_(atrs_bgn_pos);	// EOS: EX: "<br"EOS
			// check next char after tag name; EX: "<span"
			switch (src[atrs_bgn_pos]) {	// NOTE: not sure about rules; Preprocessor_DOM.php calls preg_match on $elementsRegex which seems to break on word boundaries; $elementsRegex = "~($xmlishRegex)(?:\s|\/>|>)|(!--)~iA";
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
					++atrs_bgn_pos;			// set bgn_pos to be after ws
					break;
				case Byte_ascii.Slash: case Byte_ascii.Angle_end:
					++atrs_bgn_pos;			// set bgn_pos to be after char
					break;
				case Byte_ascii.Backslash:	// NOTE: MW treats \ as /; EX: <br\>" -> "<br/>
					++tag_end_pos;
					break;
				case Byte_ascii.Dollar:		// handles <br$2>;
				default:					// allow all other symbols by defaults; TODO_OLD: need to filter out some like <br@>
					break;
				// letters / numbers after tag; tag is invalid; EX: "<spanA"
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				case Byte_ascii.Percent: // EX:<ref%s>; PAGE:pl.w:Scynk_nadrzewny; DATE:2016-08-07
					tag_obj = null;	
					break;
			}
		}
		boolean ctx_cur_tid_is_tblw_atr_owner = false;
		switch (ctx.Cur_tkn_tid()) {
			case Xop_tkn_itm_.Tid_tblw_tb: case Xop_tkn_itm_.Tid_tblw_tr: case Xop_tkn_itm_.Tid_tblw_th:
				ctx_cur_tid_is_tblw_atr_owner = true;
				break;
		}
		if (tag_obj == null) {	// not a known xml tag; EX: "<abcd>"; "if 5 < 7 then"
			if (ctx.Parse_tid() == Xop_parser_tid_.Tid__wtxt) {
				if (ctx_cur_tid_is_tblw_atr_owner)			// unknown_tag is occurring inside tblw element (EX: {| style='margin:1em<f'); just add to txt tkn
					return ctx.Lxr_make_txt_(cur_pos);
				else {										// unknown_tag is occurring anywhere else; escape < to &lt; and resume from character just after it;
					ctx.Subs_add(root, Make_bry_tkn(tkn_mkr, src, bgn_pos, cur_pos));
					return cur_pos;
				}
			}
			else {
				if (ctx_cur_tid_is_tblw_atr_owner) Xop_tblw_wkr.Atrs_close(ctx, src, root, Bool_.N);
				return ctx.Lxr_make_txt_(cur_pos);
			}
		}
		Xop_xnde_tag tag = (Xop_xnde_tag)tag_obj;
		if (pre_at_bos) {
			pre_at_bos = false;
			if (tag.Block_close() == Xop_xnde_tag.Block_end
				) {	// NOTE: only ignore if Block_end; loosely based on Parser.php|doBlockLevels|$closematch; DATE:2013-12-01
				ctx.Para().Process_block__bgn_n__end_y(tag);
				ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, cur_pos, Xop_ignore_tkn.Ignore_tid_pre_at_bos));
			}
		}
		// find closing >; NOTE: MW does not ignore > inside quotes; EX: <div id="a>b">abc</div> -> <div id="a>
		int gt_pos = -1;
		boolean pre2_hack = false;
		int end_name_pos = cur_pos + tag.Name_len();
		Mwh_atr_parser atr_parser = ctx.App().Parser_mgr().Xnde__atr_parser();
		for (int i = end_name_pos; i < src_len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Lt:	// < encountered; may be inner node inside tag which is legal in wikitext; EX: "<ul style=<nowiki>#</nowiki>FFFFFF>"
					int name_bgn_pos = i + 1;
					if (name_bgn_pos < src_len) {	// chk that name_bgn is less than src_len else arrayIndex error; EX: <ref><p></p<<ref/>; not that "<" is last char of String; DATE:2014-01-18							
						int valid_inner_xnde_gt = atr_parser.Xnde_find_gt_find(src, name_bgn_pos, src_len); // check if <nowiki>, <noinclude>, <includeonly> or <onlyinclude> (which can exist inside tag)
						if (valid_inner_xnde_gt == String_.Find_none){	// not a <nowiki>
							switch (tag.Id()) {
								case Xop_xnde_tag_.Tid__input:	break; // noop; needed for Options which may have < in value; DATE:2014-07-04
								default:						return ctx.Lxr_make_txt_(cur_pos);	// escape text; EX: "<div </div>" -> "&lt;div </div>"; SEE:it.u:; DATE:2014-02-03
							}								
						}
						else {											// is a <nowiki> skip to </nowiki>
							if (	i == end_name_pos
								&&	ctx.Parse_tid() == Xop_parser_tid_.Tid__defn
								&&	Bry_.Eq(atr_parser.Bry_obj().Val(), Xop_xnde_tag_.Tag__includeonly.Name_bry())
								) {
								pre2_hack = true;
							}
							i = valid_inner_xnde_gt;
						}
					}
					break;
				case Byte_ascii.Gt:
					gt_pos = i;
					i = src_len;
					break;
			}
		}
		if (pre2_hack) {
			// Xop_xnde_tkn tt = tkn_mkr.Xnde(bgn_pos, gt_pos + 1).Tag_(tag);
			// ctx.Stack_add(tt);
			pre2_pending = true;
			return ctx.Lxr_make_txt_(cur_pos);
		}
		if (gt_pos == -1) {return ctx.Lxr_make_log_(Xop_xnde_log.Eos_while_closing_tag, src, bgn_pos, cur_pos);}
		boolean force_xtn_for_nowiki = false;
		int end_pos = gt_pos + 1;
		switch (ctx.Parse_tid()) {					// NOTE: special logic to handle <include>; SEE: NOTE_1 below
			case Xop_parser_tid_.Tid__wtxt:	// NOTE: ignore if (a) wiki and (b) <noinclude> or <onlyinclude>
				switch (tag.Id()) {
					case Xop_xnde_tag_.Tid__noinclude:
					case Xop_xnde_tag_.Tid__onlyinclude:
						ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, end_pos, Xop_ignore_tkn.Ignore_tid_include_wiki));
						return end_pos;
					case Xop_xnde_tag_.Tid__nowiki:
						force_xtn_for_nowiki = true;
						ctx_cur_tid_is_tblw_atr_owner = false;
						break;
				}
				break;
			case Xop_parser_tid_.Tid__defn:			// NOTE: ignore if (a) tmpl and (b) <includeonly>
				switch (tag.Id()) {
					case Xop_xnde_tag_.Tid__includeonly:
						ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, end_pos, Xop_ignore_tkn.Ignore_tid_include_tmpl));
						return end_pos;
					case Xop_xnde_tag_.Tid__noinclude:
						return Make_noinclude(ctx, tkn_mkr, root, src, src_len, bgn_pos, gt_pos, tag, atrs_bgn_pos - 1, tag_is_closing);	// -1 b/c atrs_bgn_pos may be set past >; may need to adjust above logic; DATE:2014-06-24
					case Xop_xnde_tag_.Tid__nowiki:
						force_xtn_for_nowiki = true;
						break;
					case Xop_xnde_tag_.Tid__onlyinclude:
						break;
					default:
						break;
				}
				break;
			case Xop_parser_tid_.Tid__tmpl:		// NOTE: added late; SEE:comment test for "a <!-<noinclude></noinclude>- b -->c"
				switch (tag.Id()) {
					case Xop_xnde_tag_.Tid__noinclude:
						ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, end_pos, Xop_ignore_tkn.Ignore_tid_include_tmpl));
						return end_pos;
					case Xop_xnde_tag_.Tid__nowiki:		// if encountered in page_tmpl stage, mark nowiki as xtn; added for nowiki_xnde_frag; DATE:2013-01-27
					case Xop_xnde_tag_.Tid__includeonly:	// includeonly should be resolved during template stage; EX: =<io>=</io>A=<io>=</io>; DATE:2014-02-12
						force_xtn_for_nowiki = true;
						break;
				}
				break;
		}
		if (ctx_cur_tid_is_tblw_atr_owner)
			Xop_tblw_wkr.Atrs_close(ctx, src, root, Bool_.Y);	// < found inside tblw; close off tblw attributes; EX: |- id='abcd' <td>a</td> (which is valid wikitext; NOTE: must happen after <nowiki>
		if (tag_is_closing)
			return Make_xtag_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, gt_pos, tag);
		else
			return Make_xtag_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, gt_pos, name_bgn, name_end, tag, atrs_bgn_pos, src[tag_end_pos], force_xtn_for_nowiki, pre2_hack);
	}
	private static Xop_tkn_itm Make_bry_tkn(Xop_tkn_mkr tkn_mkr, byte[] src, int bgn_pos, int cur_pos) {
		int len = cur_pos - bgn_pos;
		byte[] bry = null;
		if		(len == 1	&& src[bgn_pos]		== Byte_ascii.Lt)		bry = Gfh_entity_.Lt_bry;
		else if	(len == 2	&& src[bgn_pos]		== Byte_ascii.Lt
							&& src[bgn_pos + 1]	== Byte_ascii.Slash)	bry = Bry_escape_lt_slash;	// NOTE: should use bgn_pos, not cur_pos; DATE:2014-10-22
		else															bry = Bry_.Add(Gfh_entity_.Lt_bry, Bry_.Mid(src, bgn_pos + 1, cur_pos));	// +1 to skip <
		return tkn_mkr.Bry_raw(bgn_pos, cur_pos, bry);
	}
	private int Make_noinclude(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int gtPos, Xop_xnde_tag tag, int tag_end_pos, boolean tag_is_closing) {
		tag_end_pos = Bry_find_.Find_fwd_while(src, tag_end_pos, src_len, Byte_ascii.Space);// NOTE: must skip spaces else "<noinclude />" will not work with safesubst; PAGE:en.w:Wikipedia:Featured_picture_candidates; DATE:2014-06-24
		byte tag_end_byte = src[tag_end_pos];
		if (tag_end_byte == Byte_ascii.Slash) {	// inline
			boolean valid = true;
			for (int i = tag_end_pos; i < gtPos; i++) {
				switch (src[i]) {
					case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: break;
					case Byte_ascii.Slash: break;
					default: valid = false; break;
				}
			}
			if (valid) {
				ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, gtPos, Xop_ignore_tkn.Ignore_tid_include_tmpl));
				return gtPos + 1; // 1=adj_next_char
			}
			else {
				return ctx.Lxr_make_txt_(gtPos);
			}
		}
		int end_rhs = -1, findPos = gtPos;
		byte[] end_bry = Xop_xnde_tag_.Tag__noinclude.Xtn_end_tag(); int end_bry_len = end_bry.length;
		if (tag_is_closing)	// </noinclude>; no end tag to search for; DATE:2014-05-02
			end_rhs = gtPos;
		else {				// <noinclude>; search for end tag
			while (true) {
				int end_lhs = Bry_find_.Find_fwd(src, end_bry, findPos);
				if (end_lhs == -1 || (end_lhs + end_bry_len) == src_len) break;	// nothing found or EOS;
				findPos = end_lhs;
				for (int i = end_lhs + end_bry_len; i < src_len; i++) {
					switch (src[i]) {
						case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: break;
						case Byte_ascii.Slash: break;
						case Byte_ascii.Gt: end_rhs = i + 1; i = src_len; break;	// +1 to place after Gt
						default:			findPos = i    ; i = src_len; break;
					}
				}
				if (end_rhs != -1) break;
			}
			if (end_rhs == -1)	// end tag not found; match to end of String
				end_rhs = src_len;
		}
		ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, end_rhs, Xop_ignore_tkn.Ignore_tid_include_tmpl));
		return end_rhs;
	}
	private boolean pre2_pending = false;
	private int Make_xtag_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int gtPos, int name_bgn, int name_end, Xop_xnde_tag tag, int tag_end_pos, byte tag_end_byte, boolean force_xtn_for_nowiki, boolean pre2_hack) {
		boolean inline = false;
		int open_tag_end = gtPos + 1, atrs_bgn = -1, atrs_end = -1;	// 1=adj_next_char
		// calc (a) inline; (b) atrs
		switch (tag_end_byte) {	// look at last char of tag; EX: for b, following are registered: "b/","b>","b\s","b\n","b\t"
			case Byte_ascii.Slash:	// "/" EX: "<br/"; // NOTE: <pre/a>, <pre//> are allowed
				inline = true;		
				break;
			case Byte_ascii.Backslash:	// allow <br\>; EX:w:Mosquito
				if (tag.Inline_by_backslash())
					src[tag_end_pos] = Byte_ascii.Slash;
				break;
			case Byte_ascii.Gt:		// ">" "normal" tag; noop
				break;
			default:				// "\s", "\n", "\t"
				atrs_bgn = tag_end_pos;		// set atrs_bgn to first char after ws; EX: "<a\shref='b/>" atrs_bgn = pos(h)
				atrs_end = gtPos;			// set atrs_end to gtPos;				EX: "<a\shref='b/>" atrs_end = pos(>)
				if (src[gtPos - 1] == Byte_ascii.Slash) {	// adjust if inline
					--atrs_end;
					inline = true;
				}
				break;
		}
		Mwh_atr_itm[] atrs = null;
		if (ctx.Parse_tid() == Xop_parser_tid_.Tid__wtxt) {
			atrs = ctx.App().Parser_mgr().Xnde__parse_atrs(src, atrs_bgn, atrs_end);
		}
		if ((	(	tag.Xtn() 
				&&	(	ctx.Parse_tid() != Xop_parser_tid_.Tid__defn	// do not gobble up rest if in tmpl; handle <poem>{{{1}}}</poem>; DATE:2014-03-03
					||	tag.Xtn_skips_template_args()					// ignore above if tag specifically skips template args; EX: <pre>; DATE:2014-04-10
					)
				)
			||	(force_xtn_for_nowiki && !inline)
			)
			)	{
			return Make_xnde_xtn(ctx, tkn_mkr, root, src, src_len, tag, bgn_pos, gtPos + 1, name_bgn, name_end, atrs_bgn, atrs_end, atrs, inline, pre2_hack);	// find end tag and do not parse anything inbetween
		}
		if (tag.Restricted()) {
			Xoae_page page = ctx.Page();
			if (	page.Html_data().Html_restricted() 
				&&	page.Wiki().Domain_tid() != Xow_domain_tid_.Tid__home) {
				int end_pos = gtPos + 1;
				ctx.Subs_add(root, tkn_mkr.Bry_raw(bgn_pos, end_pos, Bry_.Add(Gfh_entity_.Lt_bry, Bry_.Mid(src, bgn_pos + 1, end_pos)))); // +1 to skip <
				return end_pos;
			}
		}
		int prv_acs = ctx.Stack_idx_find_but_stop_at_tbl(Xop_tkn_itm_.Tid_xnde);
		Xop_xnde_tkn prv_xnde = prv_acs == -1 ? null : (Xop_xnde_tkn)ctx.Stack_get(prv_acs); //(Xop_xnde_tkn)ctx.Stack_get_typ(Xop_tkn_itm_.Tid_xnde);
		int prv_xnde_tagId = prv_xnde == null ? Xop_tkn_itm_.Tid_null : prv_xnde.Tag().Id();

		boolean tag_ignore = false;
		int tagId = tag.Id();
		if (tagId == Xop_xnde_tag_.Tid__table || tag.Tbl_sub()) {							// tbl tag; EX: <table>,<tr>,<td>,<th>
			Tblw_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, gtPos + 1, tagId, atrs_bgn, atrs_end);
			return gtPos + 1;
		}
		else if (prv_xnde_tagId == Xop_xnde_tag_.Tid__p && tagId == Xop_xnde_tag_.Tid__p) {
			ctx.Msg_log().Add_itm_none(Xop_xnde_log.Auto_closing_section, src, bgn_pos, bgn_pos);
			End_tag(ctx, root, prv_xnde, src, src_len, bgn_pos - 1, bgn_pos - 1, tagId, true, tag);
		}
		else if (tagId == prv_xnde_tagId && tag.Repeat_ends()) {	// EX: "<code>a<code>b" -> "<code>a</code>b"
			End_tag(ctx, root, prv_xnde, src, src_len, bgn_pos - 1, bgn_pos - 1, tagId, true, tag);
			return gtPos + 1;
		}
		else if (tagId == prv_xnde_tagId && tag.Repeat_mids()) {	// EX: "<li>a<li>b" -> "<li>a</li><li>b"
			End_tag(ctx, root, prv_xnde, src, src_len, bgn_pos - 1, bgn_pos - 1, tagId, true, tag);
		}
		else if (tag.Single_only()) inline = true; // <br></br> not allowed; convert <br> to <br/> </br> will be escaped
		else if (tag.No_inline() && inline) {
			Xop_xnde_tkn xnde_inline = Xnde_bgn(ctx, tkn_mkr, root, tag, Xop_xnde_tkn.CloseMode_open, src, bgn_pos, open_tag_end, atrs_bgn, atrs_end, atrs);
			End_tag(ctx, root, xnde_inline, src, src_len, bgn_pos, gtPos, tagId, false, tag);
			ctx.Msg_log().Add_itm_none(Xop_xnde_log.No_inline, src, bgn_pos, gtPos);
			return gtPos + Int_.Const_position_after_char;
		}
		Xop_xnde_tkn xnde = null;
		xnde = Xnde_bgn(ctx, tkn_mkr, root, tag, inline ? Xop_xnde_tkn.CloseMode_inline : Xop_xnde_tkn.CloseMode_open, src, bgn_pos, open_tag_end, atrs_bgn, atrs_end, atrs);
		if (!inline && tag.Bgn_mode() != Xop_xnde_tag_.Bgn_mode__inline)
			ctx.Stack_add(xnde);
		if (tag_ignore)
			xnde.Tag_visible_(false);
		if (tag.Empty_ignored()) ctx.Empty_ignored_y_();
		return open_tag_end;
	}
	private boolean Stack_find_xnde(Xop_ctx ctx, int cur_tag_id) {
		int acs_end = ctx.Stack_len() - 1;
		if (acs_end == -1) return false;
		for (int i = acs_end; i > -1; i--) {
			Xop_tkn_itm tkn = ctx.Stack_get(i);
			switch (tkn.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_tblw_tb:	// needed for badly formed tables;PAGE:ro.b:Pagina_principala DATE:2014-06-26
				case Xop_tkn_itm_.Tid_tblw_td:
				case Xop_tkn_itm_.Tid_tblw_th:
				case Xop_tkn_itm_.Tid_tblw_tc:	// tables always reset tag_stack; EX: <table><tr><td><li><table><tr><td><li>; 2nd li is not nested in 1st
					return false;
				case Xop_tkn_itm_.Tid_xnde:
					Xop_xnde_tkn xnde_tkn = (Xop_xnde_tkn)tkn;
					int stack_tag_id = xnde_tkn.Tag().Id();
					if (cur_tag_id == Xop_xnde_tag_.Tid__li) {
						switch (stack_tag_id) {
							case Xop_xnde_tag_.Tid__ul:	// ul / ol resets tag_stack for li; EX: <li><ul><li>; 2nd li is not nested in 1st
							case Xop_xnde_tag_.Tid__ol:
								return false;
						}
					}
					if (stack_tag_id == cur_tag_id) return true;
					break;
			}
		}
		return false;
	}
	private void Tblw_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, int tagId, int atrs_bgn, int atrs_end) {
		byte wlxr_type = 0;
		switch (tagId) {
			case Xop_xnde_tag_.Tid__table:	wlxr_type = Xop_tblw_wkr.Tblw_type_tb; break;
			case Xop_xnde_tag_.Tid__tr:		wlxr_type = Xop_tblw_wkr.Tblw_type_tr; break;
			case Xop_xnde_tag_.Tid__td:		wlxr_type = Xop_tblw_wkr.Tblw_type_td; break;
			case Xop_xnde_tag_.Tid__th:		wlxr_type = Xop_tblw_wkr.Tblw_type_th; break;
			case Xop_xnde_tag_.Tid__caption:	wlxr_type = Xop_tblw_wkr.Tblw_type_tc; break;
		}
		ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, true, wlxr_type, Xop_tblw_wkr.Called_from_general, atrs_bgn, atrs_end);
	}
	private void Tblw_end(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, int tagId) {
		int typeId = 0;
		byte wlxr_type = 0;
		switch (tagId) {
			case Xop_xnde_tag_.Tid__table:	typeId = Xop_tkn_itm_.Tid_tblw_tb; wlxr_type = Xop_tblw_wkr.Tblw_type_tb; break;
			case Xop_xnde_tag_.Tid__tr:		typeId = Xop_tkn_itm_.Tid_tblw_tr; wlxr_type = Xop_tblw_wkr.Tblw_type_tr; break;
			case Xop_xnde_tag_.Tid__td:		typeId = Xop_tkn_itm_.Tid_tblw_td; wlxr_type = Xop_tblw_wkr.Tblw_type_td; break;
			case Xop_xnde_tag_.Tid__th:		typeId = Xop_tkn_itm_.Tid_tblw_th; wlxr_type = Xop_tblw_wkr.Tblw_type_th; break;
			case Xop_xnde_tag_.Tid__caption:	typeId = Xop_tkn_itm_.Tid_tblw_tc; wlxr_type = Xop_tblw_wkr.Tblw_type_tc; break;
		}
		Xop_tblw_tkn prv_tkn = ctx.Stack_get_tbl();
		int prv_tkn_typeId = prv_tkn == null ? -1 : prv_tkn.Tkn_tid();
		ctx.Tblw().Make_tkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, typeId, wlxr_type, prv_tkn, prv_tkn_typeId, true);
//			ctx.Para().Process_block__bgn_n__end_y(ctx, root, src, bgn_pos, cur_pos);
	}		
	private int Make_xtag_end(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, Xop_xnde_tag end_tag) {
		int end_tag_id = end_tag.Id();
		cur_pos = Bry_find_.Find_fwd_while_not_ws(src, cur_pos, src_len) + 1;
		int prv_xnde_pos = ctx.Stack_idx_find_but_stop_at_tbl(Xop_tkn_itm_.Tid_xnde);	// find any previous xnde on stack
		Xop_xnde_tkn bgn_nde = (Xop_xnde_tkn)ctx.Stack_get(prv_xnde_pos);
		int bgn_tag_id = bgn_nde == null ? -1 : bgn_nde.Tag().Id();

		int end_nde_mode = end_tag.End_mode();
		boolean force_end_tag_to_match_bgn_tag = false;
		switch (bgn_tag_id) {
			case Xop_xnde_tag_.Tid__sub:		if (end_tag_id == Xop_xnde_tag_.Tid__sup) force_end_tag_to_match_bgn_tag = true; break;
			case Xop_xnde_tag_.Tid__sup:		if (end_tag_id == Xop_xnde_tag_.Tid__sub) force_end_tag_to_match_bgn_tag = true; break;
			case Xop_xnde_tag_.Tid__mark:	if (end_tag_id == Xop_xnde_tag_.Tid__span) force_end_tag_to_match_bgn_tag = true; break;
			case Xop_xnde_tag_.Tid__span:	if (end_tag_id == Xop_xnde_tag_.Tid__font) force_end_tag_to_match_bgn_tag = true; break;
		}
		if (force_end_tag_to_match_bgn_tag) {
			end_tag_id = bgn_tag_id;
			ctx.Msg_log().Add_itm_none(Xop_xnde_log.Sub_sup_swapped, src, bgn_pos, cur_pos);
		}
		if (end_tag_id == Xop_xnde_tag_.Tid__table || end_tag.Tbl_sub()) {
			Tblw_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, end_tag_id);
			return cur_pos;
		}
		if (end_tag.Empty_ignored() && ctx.Empty_ignored()		// emulate TidyHtml logic for pruning empty tags; EX: "<li> </li>" -> "")
			&& bgn_nde != null) {								// bgn_nde will be null if only end_nde; EX:WP:Sukhoi Su-47; "* </li>" 
			ctx.Empty_ignore(root, bgn_nde.Tkn_sub_idx());
			End_tag(ctx, root, bgn_nde, src, src_len, bgn_pos, cur_pos, end_tag_id, true, end_tag);
			return cur_pos;
		}
		switch (end_nde_mode) {
			case Xop_xnde_tag_.End_mode__inline:	// PATCH.WP: allows </br>, </br/> and many other variants
				Xnde_bgn(ctx, tkn_mkr, root, end_tag, Xop_xnde_tkn.CloseMode_inline, src, bgn_pos, cur_pos, Int_.Min_value, Int_.Min_value, null);	// NOTE: atrs is null b/c </br> will never have atrs
				return cur_pos;
			case Xop_xnde_tag_.End_mode__escape:	// handle </hr>
				ctx.Lxr_make_(false);
				ctx.Msg_log().Add_itm_none(Xop_xnde_log.Escaped_xnde, src, bgn_pos, cur_pos - 1);
				return cur_pos;
		}
		if (prv_xnde_pos != Xop_ctx.Stack_not_found) {	// something found
			if		(bgn_tag_id == end_tag_id) {		// end_nde matches bgn_nde; normal; 
				End_tag(ctx, root, bgn_nde, src, src_len, bgn_pos, cur_pos, end_tag_id, true, end_tag);
				return cur_pos;
			}
			else {
				if (Stack_find_xnde(ctx, end_tag_id)) {	// end_tag has bgnTag somewhere in stack;					
					int end = ctx.Stack_len() - 1;
					for (int i = end; i > -1; i--) {	// iterate stack and close all nodes until bgn_nde that matches end_nde
						Xop_tkn_itm tkn = ctx.Stack_get(i);
						if (tkn.Tkn_tid() == Xop_tkn_itm_.Tid_xnde) {
							Xop_xnde_tkn xnde_tkn = (Xop_xnde_tkn)tkn;
							End_tag(ctx, root, xnde_tkn, src, src_len, bgn_pos, bgn_pos, xnde_tkn.Tag().Id(), false, end_tag);
							ctx.Stack_pop_idx(i);
							if (xnde_tkn.Tag().Id() == end_tag_id) {
								xnde_tkn.Src_end_(cur_pos);
								return cur_pos;
							}
							else
								ctx.Msg_log().Add_itm_none(Xop_xnde_log.Auto_closing_section, src, bgn_nde.Src_bgn(), bgn_nde.Name_end());
						}
						else
							ctx.Stack_auto_close(root, src, tkn, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_xnde);
					}
				}
			}
		}
		if (end_tag.Restricted())	// restricted tags (like <script>) are not placed on stack; for now, just write it out
			ctx.Subs_add(root, tkn_mkr.Bry_raw(bgn_pos, cur_pos, Bry_.Add(Gfh_entity_.Lt_bry, Bry_.Mid(src, bgn_pos + 1, cur_pos)))); // +1 to skip <
		else {                
			if (pre2_pending) {
				pre2_pending = false;
				return ctx.Lxr_make_txt_(cur_pos);
			}
			else {
				if (end_tag.Xtn())	// if xtn end tag, ignore it; tidy / browser doesn't know about xtn_tags like "</poem>" so these need to be hidden, else they will show; DATE:2014-07-22
					ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, cur_pos, Xop_ignore_tkn.Ignore_tid_xnde_dangling));
				else				// regular tag; show it; depend on tidy to clean up; DATE:2014-07-22
					ctx.Subs_add(root, tkn_mkr.Bry_mid(src, bgn_pos, cur_pos));
			}
		}
		ctx.Para().Process_block__xnde(end_tag, end_tag.Block_close());

		ctx.Msg_log().Add_itm_none(Xop_xnde_log.Escaped_xnde, src, bgn_pos, cur_pos - 1);
		return cur_pos;
	}
	private void End_tag(Xop_ctx ctx, Xop_root_tkn root, Xop_xnde_tkn bgn_nde, byte[] src, int src_len, int bgn_pos, int cur_pos, int tagId, boolean pop, Xop_xnde_tag end_tag) {
		bgn_nde.Src_end_(cur_pos);
		bgn_nde.CloseMode_(Xop_xnde_tkn.CloseMode_pair);
		bgn_nde.Tag_close_rng_(bgn_pos, cur_pos);
		if (pop)
			ctx.Stack_pop_til(root, src, ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_xnde), false, cur_pos, cur_pos, Xop_tkn_itm_.Tid_xnde);
		bgn_nde.Subs_move(root);	// NOTE: Subs_move must go after Stack_pop_til, b/c Stack_pop_til adds tkns; see Xnde_td_list
		ctx.Para().Process_block__xnde(end_tag, end_tag.Block_close());
	}
	private Xop_xnde_tkn Xnde_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, Xop_xnde_tag tag, byte closeMode, byte[] src, int bgn_pos, int cur_pos, int atrs_bgn, int atrs_end, Mwh_atr_itm[] atrs) {
		Xop_xnde_tkn xnde = tkn_mkr.Xnde(bgn_pos, cur_pos).CloseMode_(closeMode);
		int xndeBgn = bgn_pos + 1;
		xnde.Name_rng_(xndeBgn, xndeBgn + tag.Name_len());
		xnde.Tag_(tag);
		xnde.Tag_open_rng_(bgn_pos, cur_pos);
		if (atrs_bgn > 0) {
			xnde.Atrs_rng_(atrs_bgn, atrs_end);
			xnde.Atrs_ary_(atrs);
		}
		ctx.Subs_add(root, xnde);
		ctx.Para().Process_block__xnde(tag, tag.Block_open());
		return xnde;
	}
	private int Find_end_tag_pos(byte[] src, int src_len, int find_bgn) {
		int rv = find_bgn;
		boolean found = false, loop = true;
		while (loop) {
			if (rv == src_len) break;
			byte b = src[rv];
			switch (b) {
				case Byte_ascii.Space:
				case Byte_ascii.Nl:
				case Byte_ascii.Tab:
					++rv;
					break;
				case Byte_ascii.Gt:
					found = true;
					loop = false;
					++rv; // add 1 to position after >
					break;
				default:
					loop = false;
					break;
			}
		}
		return found ? rv : Bry_find_.Not_found;
	}
	private int Find_xtn_end_lhs(Xop_ctx ctx, Xop_xnde_tag tag, byte[] src, int src_len, int open_bgn, int open_end, byte[] open_bry, byte[] close_bry) {
		return Xop_xnde_wkr_.Find_xtn_end(ctx, src, open_end, src_len, open_bry, close_bry); // UNIQ; DATE:2017-03-31
	}
	private int Make_xnde_xtn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, Xop_xnde_tag tag, int open_bgn, int open_end, int name_bgn, int name_end, int atrs_bgn, int atrs_end, Mwh_atr_itm[] atrs, boolean inline, boolean pre2_hack) {
		// NOTE: find end_tag that exactly matches bgnTag; must be case sensitive;
		int xnde_end = open_end;
		Xop_xnde_tkn xnde = null;
		if (inline) {
			xnde = Xnde_bgn(ctx, tkn_mkr, root, tag, Xop_xnde_tkn.CloseMode_inline, src, open_bgn, open_end, atrs_bgn, atrs_end, atrs);
			xnde.Tag_close_rng_(open_end, open_end);			// NOTE: inline tag, so set TagClose to open_end; should noop
		}
		else {
			byte[] close_bry = tag.Xtn_end_tag_tmp();			// get tmp bry (so as not to new)
			if (tag.Langs() != null) {							// cur tag has langs; EX:<section>; DATE:2014-07-18
				Xop_xnde_tag_lang tag_lang = tag.Langs_get(ctx.Lang().Case_mgr(), ctx.Page().Lang().Lang_id(), src, name_bgn, name_end);
				if (tag_lang == null)							// tag does not match lang; EX:<trecho> and lang=de;
					return ctx.Lxr_make_txt_(open_end);
				if (tag_lang != Xop_xnde_tag_lang.Instance)		// tag matches; note Xop_xnde_tag_lang._ is a wildcard match; EX:<section>
					close_bry = tag_lang.Xtn_end_tag_tmp();
			}
			int src_offset = open_bgn - 1;						// open bgn to start at <; -2 to ignore </ ; +1 to include <
			int close_ary_len = close_bry.length;
			for (int i = 2; i < close_ary_len; i++)				// 2 to ignore </
				close_bry[i] = src[src_offset + i];
			boolean auto_close = false;
			int close_bgn = Find_xtn_end_lhs(ctx, tag, src, src_len, open_bgn, open_end, tag.Xtn_bgn_tag(), close_bry);
			if (close_bgn == Bry_find_.Not_found) auto_close = true;	// auto-close if end not found; verified with <poem>, <gallery>, <imagemap>, <hiero>, <references> DATE:2014-08-23
			int close_end = -1;
			if (auto_close) {
				return ctx.Lxr_make_txt_(open_end);	// dangling tags are now escaped; used to gobble up rest of page with "xnde_end = close_bgn = close_end = src_len;"; DATE:2017-01-10
			}
			else {
				close_end = Find_end_tag_pos(src, src_len, close_bgn + close_bry.length);
				if (close_end == Bry_find_.Not_found) return ctx.Lxr_make_log_(Xop_xnde_log.Xtn_end_not_found, src, open_bgn, open_end);
				xnde_end = close_end;
			}

			if (pre2_hack)
				return ctx.Lxr_make_txt_(close_end);
			xnde = New_xnde_pair(ctx, root, tkn_mkr, tag, open_bgn, open_end, close_bgn, close_end);
			xnde.Atrs_rng_(atrs_bgn, atrs_end);
			xnde.Atrs_ary_(atrs);
			if (close_bgn - open_end > 0)
				xnde.Subs_add(tkn_mkr.Txt(open_end, close_bgn));
		}
		switch (ctx.Parse_tid()) {
			case Xop_parser_tid_.Tid__tmpl: {
				Xox_xnde xnde_xtn = null;
				switch (tag.Id()) {
					case Xop_xnde_tag_.Tid__xowa_cmd:				xnde_xtn = tkn_mkr.Xnde__xowa_cmd(); break;
				}
				if (xnde_xtn != null) { 
					xnde_xtn.Xtn_parse(ctx.Wiki(), ctx, root, src, xnde);
					xnde.Xnde_xtn_(xnde_xtn);
				}
				break;
			}
			case Xop_parser_tid_.Tid__wtxt: {
				Xox_xnde xnde_xtn = null;
				int tag_id = tag.Id();
				boolean escaped = false;
				switch (tag_id) {
					case Xop_xnde_tag_.Tid__xowa_cmd:				xnde_xtn = tkn_mkr.Xnde__xowa_cmd(); break;
					case Xop_xnde_tag_.Tid__math:					xnde_xtn = tkn_mkr.Xnde__math(); break;
					case Xop_xnde_tag_.Tid__poem:					xnde_xtn = tkn_mkr.Xnde__poem(); break;
					case Xop_xnde_tag_.Tid__ref:					xnde_xtn = gplx.xowa.xtns.cites.References_nde.Enabled ? tkn_mkr.Xnde__ref() : null; break;
					case Xop_xnde_tag_.Tid__references:				xnde_xtn = gplx.xowa.xtns.cites.References_nde.Enabled ? tkn_mkr.Xnde__references() : null; break;
					case Xop_xnde_tag_.Tid__gallery:				xnde_xtn = tkn_mkr.Xnde__gallery(); break;
					case Xop_xnde_tag_.Tid__imageMap:				xnde_xtn = tkn_mkr.Xnde__imageMap(); break;
					case Xop_xnde_tag_.Tid__hiero:					xnde_xtn = tkn_mkr.Xnde__hiero(); break;
					case Xop_xnde_tag_.Tid__inputBox:				xnde_xtn = tkn_mkr.Xnde__inputbox(); break;
					case Xop_xnde_tag_.Tid__dynamicPageList:		xnde_xtn = tkn_mkr.Xnde__dynamicPageList(); break;
					case Xop_xnde_tag_.Tid__pages: {
						xnde_xtn = tkn_mkr.Xnde__pages();
						boolean enabled = ctx.Wiki().Xtn_mgr().Xtn_proofread().Enabled();
						if (!enabled) {	// if Page / Index ns does not exist, disable xtn and escape content; DATE:2014-11-28
							escaped = true;
							xnde_xtn = null;
						}
						break;
					}
					case Xop_xnde_tag_.Tid__pagequality:			xnde_xtn = tkn_mkr.Xnde__pagequality(); break;
					case Xop_xnde_tag_.Tid__pagelist:				xnde_xtn = tkn_mkr.Xnde__pagelist(); break;
					case Xop_xnde_tag_.Tid__section:				xnde_xtn = tkn_mkr.Xnde__section(); break;
					case Xop_xnde_tag_.Tid__categoryList:			xnde_xtn = tkn_mkr.Xnde__categoryList(); break;
					case Xop_xnde_tag_.Tid__source:					// changed to be synonymn of syntax_highlight; DATE:2014-06-24
					case Xop_xnde_tag_.Tid__syntaxHighlight:		xnde_xtn = tkn_mkr.Xnde__syntaxHighlight(); break;
					case Xop_xnde_tag_.Tid__score:					xnde_xtn = tkn_mkr.Xnde__score(); break;
					case Xop_xnde_tag_.Tid__translate:				xnde_xtn = tkn_mkr.Xnde__translate(); break;
					case Xop_xnde_tag_.Tid__languages:				xnde_xtn = tkn_mkr.Xnde__languages(); break;
					case Xop_xnde_tag_.Tid__templateData:			xnde_xtn = tkn_mkr.Xnde__templateData(); break;
					case Xop_xnde_tag_.Tid__rss:					xnde_xtn = tkn_mkr.Xnde__rss(); break;
					case Xop_xnde_tag_.Tid__quiz:					xnde_xtn = tkn_mkr.Xnde__quiz(); break;
					case Xop_xnde_tag_.Tid__indicator:				xnde_xtn = tkn_mkr.Xnde__indicator(); break;
					case Xop_xnde_tag_.Tid__xowa_html:				xnde_xtn = tkn_mkr.Xnde__xowa_html(); break;
					case Xop_xnde_tag_.Tid__xowa_wiki_setup:		xnde_xtn = tkn_mkr.Xnde__xowa_wiki_setup(); break;
					case Xop_xnde_tag_.Tid__graph:					xnde_xtn = tkn_mkr.Xnde__graph(); break;
					case Xop_xnde_tag_.Tid__mapframe:				xnde_xtn = tkn_mkr.Xnde__mapframe(); break;
					case Xop_xnde_tag_.Tid__maplink:				xnde_xtn = tkn_mkr.Xnde__maplink(); break;
					case Xop_xnde_tag_.Tid__random_selection:		xnde_xtn = tkn_mkr.Xnde__random_selection(); break;
					case Xop_xnde_tag_.Tid__tabber:					xnde_xtn = tkn_mkr.Xnde__tabber(); break;
					case Xop_xnde_tag_.Tid__tabview:				xnde_xtn = tkn_mkr.Xnde__tabview(); break;
					case Xop_xnde_tag_.Tid__listing_buy:
					case Xop_xnde_tag_.Tid__listing_do:
					case Xop_xnde_tag_.Tid__listing_drink:
					case Xop_xnde_tag_.Tid__listing_eat:
					case Xop_xnde_tag_.Tid__listing_listing:
					case Xop_xnde_tag_.Tid__listing_see:
					case Xop_xnde_tag_.Tid__listing_sleep:			xnde_xtn = tkn_mkr.Xnde__listing(tag_id); break;
					case Xop_xnde_tag_.Tid__timeline:
						boolean log_wkr_enabled = Timeline_log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Timeline_log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_timeline, src, xnde);
						ctx.Page().Html_data().Head_mgr().Itm__timeline().Enabled_y_();
						break;
					case Xop_xnde_tag_.Tid__xowa_tag_bgn:
					case Xop_xnde_tag_.Tid__xowa_tag_end:
						break;
					case Xop_xnde_tag_.Tid__pre:	 // NOTE: pre must be an xtn, but does not create an xtn node (it gobbles up everything between); still need to touch the para_wkr; DATE:2014-02-20
						ctx.Para().Process_block__xnde(tag, Xop_xnde_tag.Block_bgn);
						if (Bry_find_.Find_fwd(src, Byte_ascii.Nl, xnde.Tag_open_end(), xnde.Tag_close_bgn()) != Bry_find_.Not_found)
							ctx.Para().Process_nl(ctx, root, src, xnde.Tag_open_bgn(), xnde.Tag_open_bgn());
						ctx.Para().Process_block__xnde(tag, Xop_xnde_tag.Block_end);
						break;
				}
				if (escaped) {
					root.Subs_del_after(root.Subs_len() - 1);	// since content is escaped, delete xnde_xtn; DATE:2014-09-08
					return ctx.Lxr_make_txt_(open_end);			// return after lhs_end, not entire xnde;
				}
				if (xnde_xtn != null) {
					try {
						xnde.Xnde_xtn_(xnde_xtn);	// NOTE: must set xnde_xtn, else null ref (html_wtr expects non-null nde)
						xnde_xtn.Xtn_parse(ctx.Wiki(), ctx, root, src, xnde);
					}
					catch (Exception e) {
						String err_msg = String_.Format("failed to render extension: title={0} excerpt={1} err={2}", ctx.Page().Ttl().Full_txt_w_ttl_case()
							, Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn())
							, Err_.Message_gplx_log(e));
						if (Env_.Mode_testing()) 
							throw Err_.new_exc(e, "xo", err_msg);
						else
							ctx.Wiki().Appe().Usr_dlg().Warn_many("", "", err_msg);
					}
				}
				break;
			}
		}
		return xnde_end;
	}
	private Xop_xnde_tkn New_xnde_pair(Xop_ctx ctx, Xop_root_tkn root, Xop_tkn_mkr tkn_mkr, Xop_xnde_tag tag, int open_bgn, int open_end, int close_bgn, int close_end) {
		Xop_xnde_tkn rv = tkn_mkr.Xnde(open_bgn, close_end).Tag_(tag).Tag_open_rng_(open_bgn, open_end).Tag_close_rng_(close_bgn, close_end).CloseMode_(Xop_xnde_tkn.CloseMode_pair);
		int name_bgn = open_bgn + 1;
		rv.Name_rng_(name_bgn, name_bgn + tag.Name_len());
		ctx.Subs_add(root, rv);
		return rv;
	}
	private static final    byte[] 
	  Bry_escape_lt_slash = Bry_.new_a7("&lt;/")
	;
	public static int Find_gt_pos(Xop_ctx ctx, byte[] src, int cur_pos, int src_len) {	// UNUSED
		int gt_pos = -1;	// find closing >
		for (int i = cur_pos; i < src_len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Lt:	// < encountered; may be inner node inside tag which is legal in wikitext; EX: "<ul style=<nowiki>#</nowiki>FFFFFF>"
					int valid_inner_xnde_gt = ctx.App().Parser_mgr().Xnde__atr_parser().Xnde_find_gt_find(src, i + 1, src_len);
					if (valid_inner_xnde_gt != String_.Find_none) {
						i = valid_inner_xnde_gt;
					}
					break;
				case Byte_ascii.Gt:
					gt_pos = i;
					i = src_len;
					break;
			}
		}
		return gt_pos;
	}
	public static Xop_log_basic_wkr Timeline_log_wkr = Xop_log_basic_wkr.Null;
}
/*
NOTE_1: special logic for <*include*>
cannot process like regular xnde tag b/c cannot auto-close tags on tmpl
  EX: <includeonly>{{subst:</includeonly><includeonly>substcheck}}</includeonly>
      1st </io> would autoclose {{subst:
Since the basic intent is to "hide" the tags in certain modes, then basically create ignore_tkn and exit
*/
