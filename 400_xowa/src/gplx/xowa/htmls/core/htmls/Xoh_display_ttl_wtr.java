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
package gplx.xowa.htmls.core.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.btries.*;
import gplx.xowa.parsers.htmls.*;
class Xoh_display_ttl_wtr {
	public static boolean Is_style_restricted(Bry_bfr bfr, Xoh_wtr_ctx hctx, byte[] src, Mwh_atr_itm atr, byte[] atr_key) {
		if (atr_key != null 
			&& Bry_.Eq(atr_key, Atr_key_style)
			) {
			byte[] atr_val = atr.Val_as_bry(); if (atr_val == null) return false; // bounds_chk
			int atr_val_len = atr_val.length;
			int atr_pos = 0;
			while (atr_pos < atr_val_len) {
				byte b = atr_val[atr_pos];
				Object o = style_trie.Match_bgn_w_byte(b, atr_val, atr_pos, atr_val_len);
				if (o != null) {
					bfr.Add(Msg_style_restricted);
					return true;
				}
				++atr_pos;
			}
		}
		return false;
	}
	private static final    byte[] 
	  Atr_key_style = Bry_.new_a7("style")
	, Msg_style_restricted = Bry_.new_a7(" style='/* attempt to bypass $wgRestrictDisplayTitle */'")
	;
	private static final    Btrie_slim_mgr style_trie = Btrie_slim_mgr.ci_a7()
	.Add_str_byte__many(Byte_.By_int(0), "display", "user-select", "visibility");  // if ( preg_match( '/(display|user-select|visibility)\s*:/i', $decoded['style'] ) ) {
}
