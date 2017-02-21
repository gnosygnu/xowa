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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public class Json_printer {
	private final Json_parser parser = new Json_parser();
	private final Json_wtr wtr = new Json_wtr();
	public Json_printer Opt_quote_byte_(byte v) {wtr.Opt_quote_byte_(v); return this;}
	public Json_wtr Wtr() {return wtr;}
	public byte[] To_bry() {return wtr.To_bry_and_clear();}
	public String To_str() {return wtr.To_str_and_clear();}
	public Json_printer Print_by_bry(byte[] src) {
		Json_doc jdoc = parser.Parse(src);
		return (jdoc.Root_grp().Tid() == Json_itm_.Tid__ary)
			? Print_by_ary(jdoc.Root_ary())
			: Print_by_nde(jdoc.Root_nde())
			;
	}
	public Json_printer Print_by_ary(Json_ary ary) {
		wtr.Doc_ary_bgn();
		int len = ary.Len();			
		for (int i = 0; i < len; ++i) {
			Json_itm itm = ary.Get_at(i);
			wtr.Ary_itm_obj(wtr.Get_x(itm));
		}
		wtr.Doc_ary_end();
		return this;
	}
	public Json_printer Print_by_nde(Json_nde nde) {
		wtr.Doc_nde_bgn();
		int len = nde.Len();
		for (int i = 0; i < len; ++i) {
			Json_kv kv = nde.Get_at_as_kv(i);
			Object kv_val = wtr.Get_x(kv.Val());
			wtr.Kv_obj(kv.Key_as_bry(), kv_val, Type_adp_.To_tid_obj(kv_val));
		}
		wtr.Doc_nde_end();
		return this;
	}
}
