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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
public class Gfh_html_ {
	public static byte[] rawElement(Bry_bfr bfr, int tag_id, byte[] body, Gfh_atr_itm... atrs) {
		// add "<tag"
		byte[] tag_name = Bry_.new_u8(Gfh_tag_.To_str(tag_id));
		Gfh_tag_.Bld_lhs_bgn(bfr, tag_name);

		// add " key1=val1 ..."
		int len = atrs.length;
		for (int i = 0; i < len; i++) {
			Gfh_atr_itm atr = atrs[i];
			Gfh_atr_.Add(bfr, atr.Key(), atr.Val());
		}

		// add ">"
		Gfh_tag_.Bld_lhs_end_nde(bfr);

		bfr.Add(body);

		// add "</tag>"
		Gfh_tag_.Bld_rhs(bfr, tag_name);
		return bfr.To_bry_and_clear();
	}
}
