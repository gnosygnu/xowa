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
public class Json_doc_bldr {
	public Json_nde Nde(Json_doc jdoc) {return factory.Nde(jdoc, -1);}
	public Json_nde Nde(Json_doc jdoc, Json_grp owner) {
		Json_nde rv = factory.Nde(jdoc, -1);
		owner.Add(rv);
		return rv;
	}
	public Json_itm Str(byte[] v) {return Str(String_.new_u8(v));}
	public Json_itm Str(String v) {return Json_itm_tmp.new_str_(v);}
	public Json_itm Int(int v) {return Json_itm_tmp.new_int_(v);}
	public Json_kv Kv_int(Json_grp owner, String key, int val)		{Json_kv rv = factory.Kv(Json_itm_tmp.new_str_(key), Json_itm_tmp.new_int_(val)); owner.Add(rv); return rv;}
	public Json_kv Kv_str(Json_grp owner, String key, String val)	{Json_kv rv = factory.Kv(Json_itm_tmp.new_str_(key), Json_itm_tmp.new_str_(val)); owner.Add(rv); return rv;}
	public Json_ary Kv_ary(Json_grp owner, String key, Json_itm... subs) {
		Json_itm key_itm = Json_itm_tmp.new_str_(key);
		Json_ary val_ary = factory.Ary(-1, -1);			
		Json_kv kv = factory.Kv(key_itm, val_ary);
		owner.Add(kv);
		int len = subs.length;
		for (int i = 0; i < len; i++)
			val_ary.Add(subs[i]);
		return val_ary;
	}
	Json_doc doc = new Json_doc(); Json_factory factory = new Json_factory();
}
