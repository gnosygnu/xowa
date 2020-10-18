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
import gplx.Int_;

public class Json_itm_int extends Json_itm_base {
	private final Json_doc doc;
	private final int src_bgn, src_end;
	private byte[] data_bry;
	private int data;
	private boolean data_needs_making;

	private Json_itm_int(Json_doc doc, int src_bgn, int src_end, boolean data_needs_making, int data) {
		this.doc = doc;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		this.data_needs_making = data_needs_making;
		if (!data_needs_making)
			this.data = data;
	}
	@Override public byte Tid() {return Json_itm_.Tid__int;}
	@Override public Object Data() {return Data_as_int();}
	@Override public byte[] Data_bry() {
		if (data_bry == null) {
			data_bry = doc == null
				? Int_.To_bry(data)
				: Bry_.Mid(doc.Src(), src_bgn, src_end);
		}
		return data_bry;
	}
	public int Data_as_int() {
		if (data_needs_making) {
			data = doc.Utl_num_parser().Parse(doc.Src(), src_bgn, src_end).Rv_as_int();
			data_needs_making = false;
		}
		return data;
	}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		if (doc == null)
			bfr.Add_int_variable(data);
		else
			bfr.Add_mid(doc.Src(), src_bgn, src_end);
	}

	public static Json_itm_int NewByDoc(Json_doc doc, int src_bgn, int src_end) {return new Json_itm_int(doc, src_bgn, src_end, true, -1);}
	public static Json_itm_int NewByVal(int val) {return new Json_itm_int(null, -1, -1, false, val);}
	public static Json_itm_int Cast(Json_itm v) {return v == null || v.Tid() != Json_itm_.Tid__int ? null : (Json_itm_int)v;}
}