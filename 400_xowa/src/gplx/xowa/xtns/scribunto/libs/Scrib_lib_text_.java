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
package gplx.xowa.xtns.scribunto.libs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.KeyVal;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.xowa.*;
import gplx.core.net.*;
class Scrib_lib_text_ {
	public static KeyVal[] Init_nowiki_protocols(Xowe_wiki wiki) {
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB128();
		Ordered_hash protocols = Gfo_protocol_itm.Regy;
		int len = protocols.Len();
		List_adp rv = List_adp_.New();
		for (int i = 0; i < len; i++) {
			Gfo_protocol_itm itm = (Gfo_protocol_itm)protocols.GetAt(i);
			if (itm.Text_ends_w_colon()) {	// To convert the protocol into a case-insensitive Lua pattern, we need to replace letters with a character class like [Xx] and insert a '%' before various punctuation.
				KeyVal kv = Init_nowiki_protocols_itm(bfr, itm);
				rv.Add(kv);
			}
		}
		bfr.MkrRls();
		return (KeyVal[])rv.ToAry(KeyVal.class);
	}
	private static KeyVal Init_nowiki_protocols_itm(BryWtr bfr, Gfo_protocol_itm itm) {
		byte[] key = itm.Key_wo_colon_bry();
		int end = key.length - 1;	// -1 to ignore final colon
		for (int i = 0; i < end; i++) {
			byte b = key[i];
			switch (b) {
				case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E:
				case AsciiByte.Ltr_F: case AsciiByte.Ltr_G: case AsciiByte.Ltr_H: case AsciiByte.Ltr_I: case AsciiByte.Ltr_J:
				case AsciiByte.Ltr_K: case AsciiByte.Ltr_L: case AsciiByte.Ltr_M: case AsciiByte.Ltr_N: case AsciiByte.Ltr_O:
				case AsciiByte.Ltr_P: case AsciiByte.Ltr_Q: case AsciiByte.Ltr_R: case AsciiByte.Ltr_S: case AsciiByte.Ltr_T:
				case AsciiByte.Ltr_U: case AsciiByte.Ltr_V: case AsciiByte.Ltr_W: case AsciiByte.Ltr_X: case AsciiByte.Ltr_Y: case AsciiByte.Ltr_Z:
					bfr.AddByte(AsciiByte.BrackBgn).AddByte(b).AddByte(AsciiByte.CaseLower(b)).AddByte(AsciiByte.BrackEnd);	// [Aa]
					break;
				case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e:
				case AsciiByte.Ltr_f: case AsciiByte.Ltr_g: case AsciiByte.Ltr_h: case AsciiByte.Ltr_i: case AsciiByte.Ltr_j:
				case AsciiByte.Ltr_k: case AsciiByte.Ltr_l: case AsciiByte.Ltr_m: case AsciiByte.Ltr_n: case AsciiByte.Ltr_o:
				case AsciiByte.Ltr_p: case AsciiByte.Ltr_q: case AsciiByte.Ltr_r: case AsciiByte.Ltr_s: case AsciiByte.Ltr_t:
				case AsciiByte.Ltr_u: case AsciiByte.Ltr_v: case AsciiByte.Ltr_w: case AsciiByte.Ltr_x: case AsciiByte.Ltr_y: case AsciiByte.Ltr_z:
					bfr.AddByte(AsciiByte.BrackBgn).AddByte(AsciiByte.CaseUpper(b)).AddByte(b).AddByte(AsciiByte.BrackEnd);	// [Aa]
					break;
				case AsciiByte.ParenBgn: case AsciiByte.ParenEnd: case AsciiByte.Pow: case AsciiByte.Dollar: case AsciiByte.Percent: case AsciiByte.Dot:
				case AsciiByte.BrackBgn: case AsciiByte.BrackEnd: case AsciiByte.Star: case AsciiByte.Plus: case AsciiByte.Question: case AsciiByte.Dash:
					bfr.AddByte(AsciiByte.Percent).AddByte(b);	// regex is '/([a-zA-Z])|([()^$%.\[\]*+?-])/'
					break;
				default:	// ignore
					break;
			}
		}
		bfr.Add(Colon_encoded);
		return KeyVal.NewStr(itm.Key_wo_colon_str(), bfr.ToStrAndClear());
	}	private static final byte[] Colon_encoded = BryUtl.NewA7("&#58;");
}
