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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.tmpls.*;	
public class Xop_lnki_lxr_bgn implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_lnki_bgn;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Xop_tkn_.Lnki_bgn, this);}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		Xop_tkn_itm prv_tkn = ctx.Stack_get_last();
		if (prv_tkn != null
			&& prv_tkn.Tkn_tid() == Xop_tkn_itm_.Tid_lnki) {
			Xop_lnki_tkn prv_lnki = (Xop_lnki_tkn)prv_tkn;
			if (prv_lnki.Pipe_count() == 0) {
				ctx.Stack_pop_last();
				return Xop_lnki_wkr_.Invalidate_lnki(ctx, src, root, prv_lnki, bgn_pos);
			}
		}
		Xop_lnki_tkn lnki = tkn_mkr.Lnki(bgn_pos, cur_pos);
		ctx.Subs_add_and_stack(root, lnki); 
		return cur_pos;
	}
	public static final Xop_lnki_lxr_bgn Instance = new Xop_lnki_lxr_bgn();
}
class Xop_lnki_size	{public static final int  None = 0, Width		= 1, Height		= 2, WidthHeight = 4, Upright	= 8;}
/*
Spaces + NewLines
. ignored near posts: '[[ '; ' ]]'; ' | '
. not ignored in: ' ='; basically breaks key
. not ignored in: '= '; will add to value; EX: alt=  a -> '  a'

NewLines
. will break lnk if in trg area (before | or ]]); EX:[[Image:The\nFabs -> [[Image:The Fabs
. will break alt (which apparently does not like new lines)
. will be converted to space for caption

http://en.wikipedia.org/wiki/Wikipedia:Extended_image_syntax
The image syntax begins with "[[", contains components separated by "|", and ends with "]]". The "[[" and the first "|" (or, if there is no "|", the terminating "]]") 
must be on the same line; other spaces and line breaks are ignored if they are next to "|" characters or just inside the brackets.
Spaces or line breaks are not allowed just before the "=" in the following options, and may have undesirable side effects if they appear just after the "=".
*/
