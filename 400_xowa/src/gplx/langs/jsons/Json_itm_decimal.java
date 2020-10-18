/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Decimal_adp;
import gplx.Decimal_adp_;
import gplx.Double_;
import gplx.String_;

public class Json_itm_decimal extends Json_itm_base {
	private final Json_doc doc;
	private final int src_bgn, src_end;
	private Decimal_adp data;
	private byte[] data_bry;

	private Json_itm_decimal(Json_doc doc, int src_bgn, int src_end, Decimal_adp data) {
		this.doc = doc;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		this.data = data;
	}
	@Override public byte Tid() {return Json_itm_.Tid__decimal;}
	@Override public Object Data() {return this.Data_as_decimal();}
	@Override public byte[] Data_bry() {
		if (data_bry == null) {
			data_bry = data == null
				? Bry_.Mid(doc.Src(), src_bgn, src_end)
				: Bry_.new_u8(Double_.To_str_loose(data.To_double()));
		}
		return data_bry;
	}
	public Decimal_adp Data_as_decimal() {
		if (data == null) {
			String s = String_.new_a7(this.Data_bry());
			s = String_.Replace(s, "e", "E"); // exponent can be either "e" or "E" in JSON, but Java decimal parse only takes "E"; ISSUE#:565; DATE:2020-03-25
			data = Decimal_adp_.parse(s);
		}
		return data;
	}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		if (doc == null) {
			bfr.Add_str_a7(Double_.To_str_loose(data.To_double()));
		}
		else {
			bfr.Add_mid(doc.Src(), src_bgn, src_end);
		}
	}

	public static Json_itm_decimal NewByDoc(Json_doc doc, int src_bgn, int src_end) {return new Json_itm_decimal(doc, src_bgn, src_end, null);}
	public static Json_itm_decimal NewByVal(Decimal_adp val) {return new Json_itm_decimal(null, -1, -1, val);}
}
