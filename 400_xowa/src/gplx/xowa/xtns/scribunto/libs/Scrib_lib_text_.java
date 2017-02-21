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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.core.net.*;
class Scrib_lib_text_ {
	public static Keyval[] Init_nowiki_protocols(Xowe_wiki wiki) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b128();
		Ordered_hash protocols = Gfo_protocol_itm.Regy;
		int len = protocols.Count();
		List_adp rv = List_adp_.New();
		for (int i = 0; i < len; i++) {
			Gfo_protocol_itm itm = (Gfo_protocol_itm)protocols.Get_at(i);
			if (itm.Text_ends_w_colon()) {	// To convert the protocol into a case-insensitive Lua pattern, we need to replace letters with a character class like [Xx] and insert a '%' before various punctuation.
				Keyval kv = Init_nowiki_protocols_itm(bfr, itm);
				rv.Add(kv);
			}
		}
		bfr.Mkr_rls();
		return (Keyval[])rv.To_ary(Keyval.class);
	}
	private static Keyval Init_nowiki_protocols_itm(Bry_bfr bfr, Gfo_protocol_itm itm) {
		byte[] key = itm.Key_wo_colon_bry();
		int end = key.length - 1;	// -1 to ignore final colon
		for (int i = 0; i < end; i++) {
			byte b = key[i];
			switch (b) {
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
					bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(b).Add_byte(Byte_ascii.Case_lower(b)).Add_byte(Byte_ascii.Brack_end);	// [Aa]
					break;
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
					bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Case_upper(b)).Add_byte(b).Add_byte(Byte_ascii.Brack_end);	// [Aa]
					break;
				case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Pow: case Byte_ascii.Dollar: case Byte_ascii.Percent: case Byte_ascii.Dot:
				case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end: case Byte_ascii.Star: case Byte_ascii.Plus: case Byte_ascii.Question: case Byte_ascii.Dash:
					bfr.Add_byte(Byte_ascii.Percent).Add_byte(b);	// regex is '/([a-zA-Z])|([()^$%.\[\]*+?-])/'
					break;
				default:	// ignore
					break;
			}
		}
		bfr.Add(Colon_encoded);
		return Keyval_.new_(itm.Key_wo_colon_str(), bfr.To_str_and_clear());
	}	private static final    byte[] Colon_encoded = Bry_.new_a7("&#58;");
}
