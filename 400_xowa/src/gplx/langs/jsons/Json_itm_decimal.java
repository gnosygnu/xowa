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

import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.basics.utls.DoubleUtl;
public class Json_itm_decimal extends Json_itm_base {
	private final Json_doc doc;
	private final int src_bgn, src_end;
	private GfoDecimal data;
	private byte[] data_bry;

	private Json_itm_decimal(Json_doc doc, int src_bgn, int src_end, GfoDecimal data) {
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
				? BryLni.Mid(doc.Src(), src_bgn, src_end)
				: BryUtl.NewU8(DoubleUtl.ToStrLoose(data.ToDouble()));
		}
		return data_bry;
	}
	public GfoDecimal Data_as_decimal() {
		if (data == null) {
			String s = StringUtl.NewA7(this.Data_bry());
			s = StringUtl.Replace(s, "e", "E"); // exponent can be either "e" or "E" in JSON, but Java decimal parse only takes "E"; ISSUE#:565; DATE:2020-03-25
			data = GfoDecimalUtl.Parse(s);
		}
		return data;
	}
	@Override public void Print_as_json(BryWtr bfr, int depth) {
		if (doc == null) {
			bfr.AddStrA7(DoubleUtl.ToStrLoose(data.ToDouble()));
		}
		else {
			bfr.AddMid(doc.Src(), src_bgn, src_end);
		}
	}

	public static Json_itm_decimal NewByDoc(Json_doc doc, int src_bgn, int src_end) {return new Json_itm_decimal(doc, src_bgn, src_end, null);}
	public static Json_itm_decimal NewByVal(GfoDecimal val) {return new Json_itm_decimal(null, -1, -1, val);}
}
