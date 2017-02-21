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
public class Json_itm_tmp implements Json_itm {	// TEST:
	public Json_itm_tmp(byte tid, String data) {this.tid = tid; this.data = data;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Data_bry() {return Bry_.new_u8(Object_.Xto_str_strict_or_empty(data));}
	public int Src_bgn() {return -1;}
	public int Src_end() {return -1;}
	public Object Data() {return data;} private String data;
	public void Print_as_json(Bry_bfr bfr, int depth) {bfr.Add_str_u8(data);}
	public boolean Data_eq(byte[] comp) {return false;}
	public void Clear() {}
	public static Json_itm new_str_(String v)	{return new Json_itm_tmp(Json_itm_.Tid__str, "\"" + v + "\"");}
	public static Json_itm new_int_(int v)		{return new Json_itm_tmp(Json_itm_.Tid__int, Int_.To_str(v));}
}
