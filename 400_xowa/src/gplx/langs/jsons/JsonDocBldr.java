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
import gplx.List_adp;
import gplx.List_adp_;
import gplx.String_;

public class JsonDocBldr {
	private final List_adp stack = List_adp_.New();
	private final Json_doc doc = new Json_doc();
	private Json_grp root;
	private Json_grp cur;
	JsonDocBldr() {}
	public JsonDocBldr Clear(boolean isRootNode) {
		this.root = isRootNode ? Json_nde.NewByDoc(doc, 0) : Json_ary.NewByDoc(doc, 0, 0);
		doc.Ctor(Bry_.Empty, root);
		this.cur = root;
		stack.Clear();
		return this;
	}
	public JsonDocBldr NdeBgn(String key) {
		Json_nde nde = Json_nde.NewByDoc(doc, 1);
		if (cur.Tid() == Json_itm_.Tid__nde) {
			Json_kv kv = new Json_kv(Json_itm_str.NewByVal(key), nde);
			cur.Add(kv);
		}
		else {
			cur.Add(nde);
		}
		stack.Add(cur);
		cur = nde;
		return this;
	}
	public JsonDocBldr NdeEnd() {
		this.cur = (Json_grp)List_adp_.Pop_last(stack);
		return this;
	}
	public JsonDocBldr KvBool(String key, boolean val) {return Kv(key, Json_itm_bool.Get(val));}
	public JsonDocBldr KvInt(String key, int val)      {return Kv(key, Json_itm_int.NewByVal(val));}
	public JsonDocBldr KvStr(String key, byte[] val)   {return Kv(key, Json_itm_str.NewByVal(String_.new_u8(val)));}
	public JsonDocBldr KvStr(String key, String val)   {return Kv(key, Json_itm_str.NewByVal(val));}
	private JsonDocBldr Kv(String key, Json_itm val) {
		Json_kv rv = new Json_kv(Json_itm_str.NewByVal(key), val);
		cur.Add(rv);
		return this;
	}
	public Json_doc ToDoc() {
		return doc;
	}
	public Json_nde ToRootNde() {
		return doc.Root_nde();
	}
	public static JsonDocBldr NewRootNde() {return new JsonDocBldr().Clear(true);}
}
