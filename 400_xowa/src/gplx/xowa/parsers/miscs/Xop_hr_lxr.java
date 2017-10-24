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
import gplx.xowa.parsers.xndes.*;
public class Xop_hr_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_hr;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr parse_trie) {parse_trie.Add(Hook_ary, this);} static final    byte[] Hook_ary = new byte[] {Byte_ascii.Nl, Byte_ascii.Dash, Byte_ascii.Dash, Byte_ascii.Dash, Byte_ascii.Dash};
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int nl_adj = -1;	// -1 to ignore nl at bgn for hr_len
		boolean bos = bgn_pos == Xop_parser_.Doc_bgn_bos;
		if (bos) {
			bgn_pos = 0;	// do not allow -1 pos
			nl_adj = 0;		// no nl at bgn, so nl_adj = 0
		}
		ctx.Apos().End_frame(ctx, root, src, bgn_pos, false);
		ctx.CloseOpenItms(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);		// close open items
		cur_pos = Bry_find_.Find_fwd_while(src, cur_pos, src_len, Hook_byt);	// gobble consecutive dashes
		if (!bos)
			ctx.Para().Process_nl(ctx, root, src, bgn_pos, bgn_pos);	// simulate \n in front of ----
		ctx.Para().Process_block__bgn_y__end_n(Xop_xnde_tag_.Tag__hr);	// para=n; block=y
		int hr_len = cur_pos - bgn_pos + nl_adj;						// TODO_OLD: syntax_check if > 4
		ctx.Subs_add(root, tkn_mkr.Hr(bgn_pos, cur_pos, hr_len));
		ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__hr);	// block=n; para=y;
		return cur_pos;
	}	private static final byte Hook_byt = Byte_ascii.Dash;
	public static final int Hr_len = 4;
	public static final    Xop_hr_lxr Instance = new Xop_hr_lxr(); Xop_hr_lxr() {}
}
