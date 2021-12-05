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
package gplx.langs.mustaches;

import gplx.objects.primitives.BoolUtl;
import gplx.Bry_;
import gplx.String_;
import gplx.langs.jsons.Json_ary;
import gplx.langs.jsons.Json_itm;
import gplx.langs.jsons.Json_itm_;
import gplx.langs.jsons.Json_kv;
import gplx.langs.jsons.Json_nde;
import gplx.objects.ObjectUtl;

public class JsonMustacheNde implements Mustache_doc_itm {
	private final Json_nde nde;
	public JsonMustacheNde(Json_nde nde) {this.nde = nde;}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		Json_itm itm = nde.Get_itm(Bry_.new_u8(key));
		if (itm == null) { // mustacheKey does not exist in current jsonNde
			return false;
		}
		else { // mustacheKey exists
			switch (itm.Tid()) {
				// array / bool node -> ignore; EX: `{{#person}}Never shown{{/person}}`
				case Json_itm_.Tid__bool:
				case Json_itm_.Tid__ary:
				case Json_itm_.Tid__nde:
					return false;
				// item node -> render it; EX: `Hello {{name}}`
				default:
					bfr.Add_bry(Json_kv.Cast(itm).Val_as_bry());
					return true;
			}
		}
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		Json_itm itm = nde.Get_itm(Bry_.new_u8(key));
		if (itm == null) { // mustacheKey does not exist in current jsonNde
			return Mustache_doc_itm_.Ary__bool__n;
		}
		else { // mustacheKey exists
			if (itm.Tid() == Json_itm_.Tid__kv) {
				Json_kv kv = Json_kv.Cast(itm);
				switch (kv.Val().Tid()) {
					// bool node -> render; EX: `{{#person}}Never shown{{/person}}`
					case Json_itm_.Tid__bool:
						boolean dataVal = BoolUtl.Cast(kv.Val().Data());
						return dataVal ? Mustache_doc_itm_.Ary__bool__y : Mustache_doc_itm_.Ary__bool__n;
					// array node -> render; EX: `{{#repo}} <b>{{name}}</b>{{/repo}}`
					case Json_itm_.Tid__ary:
						return ToJsonMustachNdeAry(itm);
					// item node -> render only if key matchers
					default:
						return new Mustache_doc_itm[] {new JsonMustacheVal(true, key, kv.Val().Data())};
				}
			}
			else {
				return Mustache_doc_itm_.Ary__bool__n;
			}
		}
	}
	private static Mustache_doc_itm[] ToJsonMustachNdeAry(Json_itm itm) {
		Json_ary dataAry = Json_ary.cast_or_null(Json_kv.Cast(itm).Val());
		int subs_len = dataAry.Len();
		Mustache_doc_itm[] rv = new Mustache_doc_itm[subs_len];
		for (int i = 0; i < subs_len; i++) {
			Json_itm sub = dataAry.Get_at(i);
			if (sub.Tid() == Json_itm_.Tid__nde) {
				rv[i] = new JsonMustacheNde((Json_nde)sub);
			}
			else {
				rv[i] = new JsonMustacheVal(false, Mustache_tkn_def.ItemString, sub.Data());
			}
		}
		return rv;
	}
}
class JsonMustacheVal implements Mustache_doc_itm {
	private final boolean fromArray;
	private final String jsonKey;
	private final Object jsonVal;
	public JsonMustacheVal(boolean fromArray, String jsonKey, Object jsonVal) {
		this.fromArray = fromArray;
		this.jsonKey = jsonKey;
		this.jsonVal = jsonVal;
	}
	public boolean Mustache__write(String mustacheKey, Mustache_bfr bfr) {
		if (	(String_.Eq(mustacheKey, jsonKey))                                     // print if `{{match}}`; EX: `{{#prop}}{{prop}}{{/prop}}`
			||	(String_.Eq(mustacheKey, Mustache_tkn_def.ItemString) && fromArray)) { // print if `{{.}}` and from array; EX: `{{#array}}{{.}}{{/array}}`
			bfr.Add_bry(Bry_.new_u8(ObjectUtl.ToStr(jsonVal)));
			return true;
		}
		else {
			return false;
		}
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {return Mustache_doc_itm_.Ary__empty;}
}
