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
package gplx.xowa.xtns.jsonConfigs.scribunto;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.ClassUtl;
import gplx.xowa.langs.*;
class Jscfg_localizer {
	public KeyVal[] Localize(Xol_lang_itm lang, byte[] page, KeyVal[] root) {
		if (lang == null) return root; // if no lang, return original

		int len = root.length;
		for (int i = 0; i < len; i++) {
			KeyVal nde = root[i];
			String nde_key = nde.KeyToStr();
			if      (StringUtl.Eq(nde_key, Id__root__license)) {
			}
			else if (StringUtl.Eq(nde_key, Id__root__description)) {
				root[i] = pickLocalizedString(lang, page, nde);
			}
			else if (StringUtl.Eq(nde_key, Id__root__schema)) {
				nde = Localize_schema(lang, page, nde);
			}
			else if (StringUtl.Eq(nde_key, Id__root__data)) {
				nde = Localize_data(lang, page, nde);
			}
		}

		return root;
	}
	private KeyVal Localize_schema(Xol_lang_itm lang, byte[] page, KeyVal schema) {
		KeyVal[] schemas = Cast_to_kvary_or_null(page, schema) ; if (schemas == null) return schema;
		KeyVal[] fields  = Cast_to_kvary_or_null(page, schemas[0]); if (fields == null) return schema;
		for (KeyVal field : fields) {
			KeyVal[] atrs = (KeyVal[])field.Val();
			int atrs_len = atrs.length;
                for (int i = 0; i < atrs_len; i++) {
				KeyVal atr = atrs[i];
				if (StringUtl.Eq(atr.KeyToStr(), Id__fld__title)) {
					atrs[i] = pickLocalizedString(lang, page, atr);
				}
			}
		}
		return schema;
	}
	private KeyVal Localize_data(Xol_lang_itm lang, byte[] page, KeyVal data) {
		KeyVal[] rows = Cast_to_kvary_or_null(page, data); if (rows == null) return data;
		for (KeyVal row : rows) {
			Object[] vals = (Object[])row.Val();
			int len = vals.length;
                for (int i = 0; i < len; i++) {
				Object val = vals[i];
				if (ClassUtl.EqByObj(KeyVal[].class, val)) {
					KeyVal val_as_kv = pickLocalizedString(lang, IntUtl.ToStr(i), (KeyVal[])val);
					vals[i] = val_as_kv.Val();
				}
			}
		}
		return data;
	}
	private static KeyVal pickLocalizedString(Xol_lang_itm lang, byte[] page, KeyVal kv) {
		KeyVal[] kvs = Cast_to_kvary_or_null(page, kv.KeyToStr(), kv.Val());
		KeyVal rv = pickLocalizedString(lang, kv.KeyToStr(), kvs);
		return rv == null ? kv : rv;
	}
	public static KeyVal pickLocalizedString(Xol_lang_itm lang, String key, KeyVal[] ary) {
		// local vars for conditional logic
		Object val_lang = null, val_en = null, val_1st = null;
		Object[] val_fallbacks = null;

		// local vars for lang
		String langCode = lang.Key_str();
		Ordered_hash fallback_hash = lang.Fallback_hash();

		// loop ary to populate local vars
		for (KeyVal itm : ary) {
			String itm_key = itm.KeyToStr();
			Object itm_val = itm.Val();
			if (val_1st == null) {
				val_1st = itm_val;
			}
			if (StringUtl.Eq(itm_key, langCode)) {
				val_lang = itm_val;
			}
			else if (fallback_hash.Has(itm_key)) {
				if (val_fallbacks == null) {
					val_fallbacks = new Object[fallback_hash.Len()];
				}
				int idx = fallback_hash.IdxOf(itm_key);
				val_fallbacks[idx] = itm_val;
			}
			else if (StringUtl.Eq(itm_key, "en")) {
				val_en = itm_val;
			}

		}

		if (val_lang != null) {
			return KeyVal.NewStr(key, val_lang);
		}

		if (val_fallbacks != null) {
			for (Object v : val_fallbacks) {
				if (v != null)
					return KeyVal.NewStr(key, v);
			}
		}

		// If fallbacks fail, check if english is defined
		if (val_en != null) {
			return KeyVal.NewStr(key, val_en);
		}

		// We have a custom default, return that
		// if (defaultValue != null) {
		// 	return null;
		// }

		// Return first available value, or an empty String
		// There might be a better way to get the first value from an Object
		Object val = val_1st == null ? "" : val_1st;
		return KeyVal.NewStr(key, val);
	}
	private static KeyVal[] Cast_to_kvary_or_null(byte[] page, KeyVal kv) {return Cast_to_kvary_or_null(page, kv.KeyToStr(), kv.Val());}
	private static KeyVal[] Cast_to_kvary_or_null(byte[] page, String key, Object val) {
		if (ClassUtl.EqByObj(KeyVal[].class, val)) {
			return (KeyVal[])val;
		}
		else {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "could not cast to kvary; page=~{0} key=~{1}", key);
			return null;
		}
	} 
	private static final String
	  Id__root__schema        = "schema"
	, Id__root__data          = "data"
	, Id__root__description   = "description"
	, Id__root__license       = "license"
	, Id__fld__title          = "title"
	;
}
