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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
import gplx.xowa.parsers.xndes.*;
public class Xop_tblw_ws_itm {
	public byte Tblw_type() {return tblw_type;} private byte tblw_type;
	public int Hook_len() {return hook_len;} private int hook_len;
	public Xop_tblw_ws_itm(byte tblw_type, int hook_len) {this.tblw_type = tblw_type; this.hook_len = hook_len;}

	public static final byte Type_tb = Xop_tblw_wkr.Tblw_type_tb, Type_te = Xop_tblw_wkr.Tblw_type_te, Type_tr = Xop_tblw_wkr.Tblw_type_tr, Type_tc = Xop_tblw_wkr.Tblw_type_tc
		, Type_th = Xop_tblw_wkr.Tblw_type_th, Type_td = Xop_tblw_wkr.Tblw_type_td, Type_nl = 16, Type_xnde = 17;
	public static Btrie_slim_mgr trie_() {// MW.REF:Parser.php|doBlockLevels
		Btrie_slim_mgr rv = Btrie_slim_mgr.cs();
		trie_itm(rv, Type_tb, Xop_tblw_lxr_ws.Hook_tb);
		trie_itm(rv, Type_te, Xop_tblw_lxr_ws.Hook_te);
		trie_itm(rv, Type_tr, Xop_tblw_lxr_ws.Hook_tr);
		trie_itm(rv, Type_th, Xop_tblw_lxr_ws.Hook_th);
		trie_itm(rv, Type_tc, Xop_tblw_lxr_ws.Hook_tc);
		trie_itm(rv, Type_td, Byte_ascii.Pipe_bry);
		trie_itm(rv, Type_nl, Byte_ascii.Nl_bry);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__table);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__tr);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__td);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__th);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__blockquote);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__h1);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__h2);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__h3);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__h4);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__h5);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__h6);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__pre);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__p);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__div);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__hr);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__li);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__ul);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag__ol);
		return rv;
	}
	private static void trie_itm(Btrie_slim_mgr trie, byte type, byte[] bry) {trie.Add_obj(bry, new Xop_tblw_ws_itm(type, bry.length));}
	private static void trie_itm_xnde(Btrie_slim_mgr trie, Xop_xnde_tag tag) {
		byte[] tag_name = tag.Name_bry();
		int tag_name_len = tag_name.length;
		trie.Add_obj(Bry_.Add(Bry_xnde_bgn, tag_name), new Xop_tblw_ws_itm(Type_xnde, tag_name_len));
		trie.Add_obj(Bry_.Add(Bry_xnde_end, tag_name), new Xop_tblw_ws_itm(Type_xnde, tag_name_len + 1));
	}	private static byte[] Bry_xnde_bgn = new byte[] {Byte_ascii.Lt, Byte_ascii.Slash}, Bry_xnde_end = new byte[] {Byte_ascii.Lt};
}
