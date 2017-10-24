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
package gplx.xowa.xtns.imaps.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
public class Imap_desc_tid {
	public static final byte Tid_tr = 0, Tid_br = 1, Tid_bl = 2, Tid_tl = 3, Tid_none = 4, Tid_null = 5;
	public static final    byte[] 
	  Key_tr		= Bry_.new_a7("top-right")
	, Key_br		= Bry_.new_a7("bottom-right")
	, Key_bl		= Bry_.new_a7("bottom-left")
	, Key_tl		= Bry_.new_a7("top-left")
	, Key_none		= Bry_.new_a7("none")
	;

	public static byte Parse_to_tid(Btrie_slim_mgr trie, byte[] src, int bgn, int end) {
		Object rv = trie.Match_bgn(src, bgn, end);
		return rv == null ? Tid_null : ((Byte_obj_val)rv).Val();
	}
	public static void Calc_margins(Int_2_ref rv, byte tid, int html_w, int html_h) {
		int margin_l 
			= tid == Tid_tl || tid == Tid_bl
			? 0
			: html_w - 20;
		int margin_t
			= tid == Tid_tl || tid == Tid_tr
			? -html_h + 1	// 1px hack for IE, to stop it poking out the top
			: -20;
		rv.Val_all_(margin_l, margin_t);
	}
	public static Btrie_slim_mgr New_trie(Xowe_wiki wiki) {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_u8();
		Add_to_trie(rv, Key_tr, Key_br, Key_bl, Key_tl, Key_none);
		byte[][] lang_types = Parse_lang_types(wiki);
		if (lang_types != null)
			Add_to_trie(rv, lang_types);
		return rv;
	}
	private static void Add_to_trie(Btrie_slim_mgr trie, byte[]... ary) {
		trie.Add_bry_byte(ary[0]	,Tid_tr);
		trie.Add_bry_byte(ary[1]	,Tid_br);
		trie.Add_bry_byte(ary[2]	,Tid_bl);
		trie.Add_bry_byte(ary[3]	,Tid_tl);
		trie.Add_bry_byte(ary[4]	,Tid_none);
	}
	private static byte[][] Parse_lang_types(Xowe_wiki wiki) {
		byte[] val = wiki.Msg_mgr().Val_by_key_obj("imagemap_desc_types");
		if (Bry_.Len_eq_0(val)) return null;					// no msg in lang; return;
		byte[][] ary = Bry_split_.Split(val, Byte_ascii.Comma);	// msg is 5 words concatenated by comma: EX:top-right,bottom-right-bottom-left,top-left,none
		int ary_len = ary.length;
		if (ary_len != 5) wiki.Appe().Usr_dlg().Warn_many("", "", "imap_desc does not have 5 items; wiki=~{0} val=~{1}", wiki.Domain_bry(), val);
		for (int i = 0; i < 5; ++i)
			ary[i] = Bry_.Trim(ary[i]);							// note that items will have trailing ws; EX: "top-right, bottom-right, bottom-left, top-left, none"
		return ary;
	}
}
