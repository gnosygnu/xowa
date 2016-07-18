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
