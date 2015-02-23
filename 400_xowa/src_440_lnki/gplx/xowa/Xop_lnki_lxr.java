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
package gplx.xowa; import gplx.*;
import gplx.core.btries.*;
class Xop_lnki_lxr_bgn implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_lnki_bgn;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Xop_tkn_.Lnki_bgn, this);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
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
	public static final Xop_lnki_lxr_bgn _ = new Xop_lnki_lxr_bgn();
}
class Xop_lnki_lxr_end implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_lnki_end;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Xop_tkn_.Lnki_end, this);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {return ctx.Lnki().Make_tkn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);}
	public static final Xop_lnki_lxr_end _ = new Xop_lnki_lxr_end();
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
