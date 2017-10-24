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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.paras.*;
public class Xop_comm_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_comment;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Bgn_ary, this);}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int lhs_end = cur_pos;
		int end_pos = Bry_find_.Find_fwd(src, End_ary, cur_pos, src_len);	// search for "-->"	// NOTE: do not reuse cur_pos, else cur_pos may become -1 and fatal error in ctx.Msg_log() below; DATE:2014-06-08
		int rhs_bgn = end_pos;
		if (end_pos == Bry_find_.Not_found) {								// "-->" not found
			ctx.Msg_log().Add_itm_none(Xop_comm_log.Eos, src, bgn_pos, cur_pos);
			cur_pos = src_len;												// gobble up rest of content
		}
		else
			cur_pos = end_pos + End_len;
		cur_pos = Trim_ws_if_entire_line_is_commment(ctx, tkn_mkr, root, src, src_len, cur_pos, lhs_end, rhs_bgn);
		ctx.Subs_add(root, tkn_mkr.Ignore(bgn_pos, cur_pos, Xop_ignore_tkn.Ignore_tid_comment));
		return cur_pos;
	}
	private static int Trim_ws_if_entire_line_is_commment(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int cur_pos, int lhs_end, int rhs_bgn) {// REF.MW:Preprocessor_DOM.php|preprocessToXml|handle comments; DATE:2014-02-24
		if (	ctx.Tid_is_popup()
			&&	ctx.Parse_tid() == Xop_parser_tid_.Tid__wtxt		// note that only popup parse can generate <!-- --> that makes it to wtxt
			&&	Bry_.Match(src, lhs_end, rhs_bgn, Xowa_skip_text_bry)	// <!--XOWA_SKIP-->
			)
			return cur_pos;	// in popup mode only do not gobble trailing \n; PAGE:en.w:Gwynedd; DATE:2014-07-01
		int nl_lhs = -1;
		int subs_len = root.Subs_len();
		for (int i = subs_len - 1; i > -1; i--) {			// look bwd for "\n"
			Xop_tkn_itm sub = root.Subs_get(i);
			switch (sub.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab:
					break;
				case Xop_tkn_itm_.Tid_ignore:
					Xop_ignore_tkn sub_as_ignore = (Xop_ignore_tkn)sub;
					if (sub_as_ignore.Ignore_type() != Xop_ignore_tkn.Ignore_tid_comment)
						i = -1;
					break;
				case Xop_tkn_itm_.Tid_newLine:				// new_line found; anything afterwards is a \s or a \t; SEE.WIKT:coincidence
					nl_lhs = i;
					break;
				default:
					i = -1;
					break;
			}
		}
		if (nl_lhs == -1) return cur_pos;					// non ws tkns found before \n; exit now; EX: \n\sa<!--
		boolean loop = true;
		int nl_rhs = -1, loop_pos = cur_pos;
		while (loop) {										// look fwd for \n
			if (loop_pos == src_len) break;
			switch (src[loop_pos++]) {
				case Byte_ascii.Space:
				case Byte_ascii.Tab:
					break;
				case Byte_ascii.Nl:
					loop = false;
					nl_rhs = loop_pos;
					break;
				default:
					loop = false;
					break;
			}
		}
		if (nl_rhs == -1) return cur_pos;					// non ws tkns found before \n; exit now; EX: -->a\n
		for (int i = nl_lhs + 1; i < subs_len; i++) {		// entire line is ws; trim everything from nl_lhs + 1 to nl_rhs; do not trim nl_lhs
			Xop_tkn_itm sub_tkn = root.Subs_get(i);
			sub_tkn.Ignore_y_grp_(ctx, root, i);
		}
		ctx.Subs_add(root, tkn_mkr.NewLine(nl_rhs - 1, nl_rhs, Xop_nl_tkn.Tid_char, 1).Ignore_y_()); // add tkn for nl_rhs, but mark as ignore; needed for multiple comment nls; EX: "<!-- -->\n<!-- -->\n;"; DATE:2014-02-24
		return nl_rhs;
	}
	public static final    byte[] Bgn_ary = new byte[] {60, 33, 45, 45}, /*<!--*/ End_ary = new byte[] {45, 45, 62}; /*-->*/
	private static final    int End_len = End_ary.length;
	public static final    Xop_comm_lxr Instance = new Xop_comm_lxr(); Xop_comm_lxr() {}
	private static final    String Xowa_skip_text_str = "XOWA_SKIP";
	private static final    byte[] Xowa_skip_text_bry = Bry_.new_a7(Xowa_skip_text_str);
	public static final    byte[] Xowa_skip_comment_bry = Bry_.new_a7("<!--" + Xowa_skip_text_str + "-->");
}
