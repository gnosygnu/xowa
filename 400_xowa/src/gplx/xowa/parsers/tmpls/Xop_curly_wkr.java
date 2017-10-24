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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.xndes.*;
public class Xop_curly_wkr implements Xop_ctx_wkr {
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public void AutoClose(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int lxr_bgn_pos, int lxr_cur_pos, Xop_tkn_itm tkn) {}
	public int MakeTkn_bgn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int lxr_bgn_pos, int lxr_cur_pos) {
		int lxr_end_pos = Bry_find_.Find_fwd_while(src, lxr_cur_pos, src_len, Byte_ascii.Curly_bgn);	// NOTE: can be many consecutive {; EX: {{{{{1}}}|a}}
		ctx.Subs_add_and_stack(root, tkn_mkr.Tmpl_curly_bgn(lxr_bgn_pos, lxr_end_pos));
		return lxr_end_pos;
	}		
	public int MakeTkn_end(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int lxr_bgn_pos, int lxr_cur_pos) {
		if (ctx.Cur_tkn_tid() == Xop_tkn_itm_.Tid_brack_bgn)	// WORKAROUND: ignore }} if inside lnki; EX.CM:Template:Protected; {{#switch:a|b=[[a|ja=}}]]}}
			return ctx.Lxr_make_txt_(lxr_cur_pos);
		int lxr_end_pos = Bry_find_.Find_fwd_while(src, lxr_cur_pos, src_len, Byte_ascii.Curly_end);	// NOTE: can be many consecutive }; EX: {{a|{{{1}}}}}
		int end_tkn_len = lxr_end_pos - lxr_bgn_pos;
		boolean vnt_enabled = ctx.Wiki().Lang().Vnt_mgr().Enabled();
		while (end_tkn_len > 0) {
			int acs_pos = -1, acs_len = ctx.Stack_len();
			for (int i = acs_len - 1; i > -1; i--) {		// find auto-close pos
				Xop_tkn_itm stack_tkn = ctx.Stack_get(i);
				switch (stack_tkn.Tkn_tid()) {
					case Xop_tkn_itm_.Tid_tmpl_curly_bgn:	// found curly_bgn; mark and exit
						acs_pos = i;
						i = -1;
						break;
					case Xop_tkn_itm_.Tid_brack_bgn:		// found no curly_bgn, but found brack_bgn; note that extra }} should not close any frames beyond lnki; EX:w:Template:Cite wikisource; w:John Fletcher (playwright)
						i = -1;
						break;
					case Xop_tkn_itm_.Tid_xnde:				// found xnde; ignore; handle {{template|<poem>}}</poem>}} DATE:2014-03-03
						Xop_xnde_tkn stack_xnde = (Xop_xnde_tkn)stack_tkn;
						if (stack_xnde.Tag().Xtn())
							i = -1;
						break;
				}
			}
			if (acs_pos == -1) {	// "}}+" found but no "{{+" found; warn and output literal tkn
				ctx.Msg_log().Add_itm_none(Xop_curly_log.Bgn_not_found, src, lxr_bgn_pos, lxr_end_pos);
				ctx.Subs_add(root, tkn_mkr.Txt(lxr_bgn_pos, lxr_end_pos));
				return lxr_end_pos;
			}

			Xop_curly_bgn_tkn bgn_tkn = (Xop_curly_bgn_tkn)ctx.Stack_pop_til(root, src, acs_pos, true, lxr_bgn_pos, lxr_end_pos, Xop_tkn_itm_.Tid_tmpl_curly_bgn);	// NOTE: in theory, an unclosed [[ can be on stack; for now, ignore
			int bgn_tkn_len = bgn_tkn.Src_end() - bgn_tkn.Src_bgn();
			int bgn_tkn_pos_bgn = bgn_tkn.Src_bgn();// save original pos_bgn
			boolean vnt_dash_adjust = false;
			if (vnt_enabled ) {
				int curly_bgn_dash = bgn_tkn.Src_bgn() - 1;
				if (curly_bgn_dash > -1 && src[curly_bgn_dash] == Byte_ascii.Dash) {			// "-" exists before curlies; EX: "-{{"
					int curly_end_dash = lxr_end_pos;
					if (curly_end_dash < src_len && src[curly_end_dash] == Byte_ascii.Dash) {	// "-" exists after curlies;  EX: "}}-"
						if (bgn_tkn_len > 2 && end_tkn_len > 2) {	// more than 3 curlies at bgn / end with flanking dashes; EX: "-{{{ }}}-"; NOTE: 3 is needed b/c 2 will never be reduced; EX: "-{{" will always be "-" and "{{", not "-{" and "{"
							int numeric_val = Bry_.To_int_or(src, bgn_tkn.Src_end(), lxr_bgn_pos, -1);
							if (	numeric_val != -1						// do not apply if numeric val; EX:"-{{{0}}}-" vs "-{{{#expr:0}}}-" sr.w:Template:Link_FA
								&&	bgn_tkn_len == 3 && end_tkn_len == 3	// exactly 3 tokens; assume param token; "-{{{" -> "-" + "{{{" x> -> "-{" + "{{"; if unbalanced (3,4 or 4,3) fall into code below
								) {
							}												// noop; PAGE:sr.w:ДНК; EX:<span id="interwiki-{{{1}}}-fa"></span> DATE:2014-07-03
							else {
								--bgn_tkn_len;		// reduce bgn curlies by 1; EX: "{{{" -> "{{"
								++bgn_tkn_pos_bgn;	// add one to bgn tkn_pos;
								--end_tkn_len;		// reduce end curlies by 1; EX: "}}}" -> "}}"
								--lxr_end_pos;		// reduce end by 1; this will "reprocess" the final "}" as a text tkn; EX: "}}}-" -> "}}" and position before "}-"
								vnt_dash_adjust = true;
							}
						}
					}
				}
			}

			int new_tkn_len = 0;
			if		(bgn_tkn_len == end_tkn_len)	// exact match; should be majority of cases
				new_tkn_len = bgn_tkn_len;
			else if (bgn_tkn_len >  end_tkn_len)	// more bgn than end; use end, and deduct bgn; EX: {{{{{1}}}|a}}
				new_tkn_len = end_tkn_len;
			else   /*bgn_tkn_len <  end_tkn_len*/	// more end than bgn; use bgn, and deduct end; EX: {{a|{{{1}}}}}
				new_tkn_len = bgn_tkn_len;

			int keep_curly_bgn = 0;
			/* NOTE: this is a semi-hack; if bgn_tkn > new_tkn, then pretend bgn_tkn fits new_tkn, give to bldr, and then adjust back later
			EX: {{{{{1}}}|a}} -> bgn_tkn_len=5,new_tkn_len=3 -> change bgn(0, 5) to bgn(2, 5)
			The "correct" way is to insert a new_bgn_tkn after cur_bgn_tkn on root, but this would have performance implications: array would have to be resized, and all subs will have to be reindexed
			NOTE: bgn curlies should also be preserved if new_tkn_len > 3; EX: {{{{{{1}}}}}}; note that bgn = end, but len > 3
			*/
			if (bgn_tkn_len > new_tkn_len || new_tkn_len > 3) {
				bgn_tkn.Tkn_ini_pos(false, bgn_tkn.Src_end() - new_tkn_len, bgn_tkn.Src_end());
				keep_curly_bgn = 1;	// preserves {{
			}
			switch (new_tkn_len) {
				case 0:			// EXC_CASE: should not happen; warn;
					ctx.Msg_log().Add_itm_none(Xop_curly_log.Bgn_len_0, src, bgn_tkn.Src_bgn(), lxr_end_pos);
					break;		
//					case 1:			// EXC_CASE: SEE:NOTE_1;
//						break;
				case 2:			// USE_CASE: make invk_tkn
					ctx.Invk().Make_tkn(ctx, root, src, lxr_bgn_pos, lxr_bgn_pos + new_tkn_len, bgn_tkn, keep_curly_bgn);
					break;
				default:		// USE_CASE: make prm_tkn; NOTE: 3 or more
					new_tkn_len = 3;	// gobble 3 at a time; EX: 6 -> 3 -> 0; EX: 7 -> 4 -> 1;
					prm_wkr.Make_tkn(ctx, tkn_mkr, root, src, src_len, lxr_bgn_pos, lxr_bgn_pos + new_tkn_len, bgn_tkn, keep_curly_bgn);
					break;
			}
			switch (bgn_tkn_len - new_tkn_len) {	// continuation of semi-hack above; some bgn still left over; adjust and throw back on stack
				case 1: // 1 tkn; convert curly to generic text tkn
					bgn_tkn.Src_end_(bgn_tkn.Src_end() - 1);	// NOTE: shorten end of bgn_tkn by 1; TEST
					ctx.Stack_add(tkn_mkr.Txt(bgn_tkn_pos_bgn, bgn_tkn.Src_end() - new_tkn_len));
					break;
				case 0:	// noop
					break;
				default:
					bgn_tkn.Tkn_ini_pos(false, bgn_tkn_pos_bgn, bgn_tkn.Src_end() - new_tkn_len);	// bgn(2, 5) -> bgn (0, 2)
					ctx.Stack_add(bgn_tkn);
					break;
			}
			if (vnt_dash_adjust) {
				Xop_tkn_itm text_tkn = root.Subs_get_or_null(root.Subs_len() - 2);	// -2 to get tkn before newly-created tmpl / prm
				if (text_tkn == null || text_tkn.Tkn_tid() != Xop_tkn_itm_.Tid_txt)
					ctx.Wiki().Appe().Usr_dlg().Warn_many("", "", "token before curly_bgn was not text tkn; src=~{0}", String_.new_u8(src, lxr_bgn_pos, lxr_end_pos));
				else
					text_tkn.Src_end_(text_tkn.Src_end() + 1);	// +1 to extend txt_tkn with dash be 1 to include curly; EX: "-" "{{{" -> "-{" "{{"
			}

			end_tkn_len -= new_tkn_len;
			lxr_bgn_pos += new_tkn_len;	// move lxr_bgn_pos along
			if (end_tkn_len == 1) {	// SEE:NOTE_1:
				ctx.Subs_add(root, tkn_mkr.Txt(lxr_bgn_pos, lxr_bgn_pos + 1));
				end_tkn_len = 0;
				++lxr_bgn_pos;
			}
		}
		return lxr_end_pos;
	}
	private Xot_prm_wkr prm_wkr = Xot_prm_wkr.Instance;
	public static final    byte[] 
	  Hook_prm_bgn = new byte[] {Byte_ascii.Curly_bgn, Byte_ascii.Curly_bgn, Byte_ascii.Curly_bgn}
	, Hook_prm_end = new byte[] {Byte_ascii.Curly_end, Byte_ascii.Curly_end, Byte_ascii.Curly_end};
}
/*
NOTE_1:
. end_tkn_len = 1 can happen when 4 on either side, or other permutations; EX: {{{{leaf_1}}}};
. cannot put in switch(new_tkn_len) b/c the 1 } will pop any {{ or {{{ off stack;
.. EX: "{{#switch:a|{{{1}}}}=y|n}}"; 4th } will pop {{ of #switch off stack
.. make separate check near end
*/