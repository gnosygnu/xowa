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

public class Json_itm_int extends Json_itm_base {
	private final Json_doc doc;
	private byte[] data_bry;
	private int data;
	private boolean data_is_null = true;

	public Json_itm_int(Json_doc doc, int src_bgn, int src_end) {
		this.Ctor(src_bgn, src_end);
		this.doc = doc;
	}
	public Json_itm_int(int val) {
		this.Ctor(-1, -1);
		this.doc = null;
		this.data = val;
		this.data_is_null = false;
	}
	@Override public byte Tid() {return Json_itm_.Tid__int;}
	public int Data_as_int() {
		if (data_is_null) {
			data = doc.Utl_num_parser().Parse(doc.Src(), Src_bgn(), Src_end()).Rv_as_int();
			data_is_null = false;
		}
		return data;
	}
	@Override public Object Data() {return Data_as_int();}
	@Override public byte[] Data_bry() {
		if (data_bry == null)
			data_bry = Bry_.Mid(doc.Src(), this.Src_bgn(), this.Src_end());
		return data_bry;
	}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		if (doc != null && this.Src_bgn() >= 0)
			bfr.Add_mid(doc.Src(), this.Src_bgn(), this.Src_end());
		else
			bfr.Add_int_variable(data);
	}
	public static Json_itm_int cast(Json_itm v) {return v == null || v.Tid() != Json_itm_.Tid__int ? null : (Json_itm_int)v;}
}