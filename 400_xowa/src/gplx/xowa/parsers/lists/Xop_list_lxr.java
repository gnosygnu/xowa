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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
public class Xop_list_lxr implements Xop_lxr {//20111222
	public byte Lxr_tid() {return Xop_lxr_.Tid_list;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {Add_ary(core_trie, this, Xop_list_tkn_.Hook_ul, Xop_list_tkn_.Hook_ol, Xop_list_tkn_.Hook_dt, Xop_list_tkn_.Hook_dd);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	private void Add_ary(Btrie_fast_mgr core_trie, Object val, byte[]... ary) {for (byte[] itm : ary) core_trie.Add(itm, val);}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {return ctx.List().MakeTkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);}
	public static final Xop_list_lxr _ = new Xop_list_lxr(); Xop_list_lxr() {}
}
