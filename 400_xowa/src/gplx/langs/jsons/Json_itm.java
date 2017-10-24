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
public interface Json_itm {
	byte		Tid();
	int			Src_bgn();
	int			Src_end();
	Object		Data();
	byte[]		Data_bry();
	void		Print_as_json(Bry_bfr bfr, int depth);
	boolean		Data_eq(byte[] comp);
}
class Json_itm_null extends Json_itm_base {
	Json_itm_null() {this.Ctor(-1, -1);}
	@Override public byte Tid() {return Json_itm_.Tid__null;}
	@Override public Object Data() {return null;}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {bfr.Add(Object_.Bry__null);}
	@Override public byte[] Data_bry() {return Object_.Bry__null;}
	public static final    Json_itm_null Null = new Json_itm_null();
}
