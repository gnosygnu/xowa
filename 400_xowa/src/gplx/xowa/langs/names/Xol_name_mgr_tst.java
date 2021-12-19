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
package gplx.xowa.langs.names;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import org.junit.*;
public class Xol_name_mgr_tst {
	private final Xol_name_mgr_fxt fxt = new Xol_name_mgr_fxt();

	@Test public void Cldr_only() {
		fxt.Test__fetchLanguageNamesUncached
			( "en", Xol_name_mgr.Scope__int__all
			, fxt.Make__cldr_names("en", "de")
			, fxt.Make__lang_names()
			, fxt.Make__lang_files()
			, fxt.Make__expd_langs
			( "de|de_cldr"
			, "en|en_cldr"
			));
	}
	@Test public void Lang_name__langs() {
		fxt.Test__fetchLanguageNamesUncached
			( "en", Xol_name_mgr.Scope__int__all
			, fxt.Make__cldr_names("en", "de", "es")
			, fxt.Make__lang_names("en", "de", "fr")
			, fxt.Make__lang_files()
			, fxt.Make__expd_langs
			( "de|de_cldr" // do not use de_lange
			, "en|en_lang" // use en_lang b/c of "if (String_.Eq(code, inLanguage)"
			, "es|es_cldr" // make sure es_lang is still there
			, "fr|fr_lang" // add fr_lang
			));
	}
	@Test public void mwFile() {
		fxt.Test__fetchLanguageNamesUncached
			( "en", Xol_name_mgr.Scope__int__mwFile
			, fxt.Make__cldr_names("en", "de")
			, fxt.Make__lang_names("en", "de", "fr")
			, fxt.Make__lang_files("en", "de", "es")
			, fxt.Make__expd_langs
			( "de|de_cldr"
			, "en|en_lang"
			));
	}
	@Test public void mw() {
		fxt.Test__fetchLanguageNamesUncached
			( "en", Xol_name_mgr.Scope__int__mw
			, fxt.Make__cldr_names("en", "de")
			, fxt.Make__lang_names("en", "de", "fr")
			, fxt.Make__lang_files("en", "de", "es")
			, fxt.Make__expd_langs
			( "de|de_cldr"
			, "en|en_lang"
			, "fr|fr_lang"
			));
	}
}
class Xol_name_mgr_fxt {
	public Ordered_hash Make__cldr_names(String... vals) {return Fill(Add_suffix(vals, "_cldr"));}
	public Ordered_hash Make__lang_names(String... vals) {return Fill(Add_suffix(vals, "_lang"));}
	public Ordered_hash Make__lang_files(String... vals) {return Fill(Add_suffix(vals, "_file"));}
	public Ordered_hash Make__expd_langs(String... vals) {return Fill(vals);}
	public void Test__fetchLanguageNamesUncached
		( String inLanguage, byte include
		, Ordered_hash cldr_names
		, Ordered_hash lang_names
		, Ordered_hash lang_files
		, Ordered_hash expd_langs) {
		Ordered_hash actl_langs = Xol_name_mgr.fetchLanguageNamesUncached(inLanguage, include, cldr_names, lang_names, lang_files);
		GfoTstr.EqLines(To_str_ary(expd_langs), To_str_ary(actl_langs));
	}
	private static String[] Add_suffix(String[] ary, String val_suffix) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			String itm = ary[i];
			ary[i] = itm + "|" + itm + val_suffix;
		}
		return ary;
	}
	private static String[] To_str_ary(Ordered_hash hash) {
		int len = hash.Len();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			KeyVal kv = (KeyVal)hash.GetAt(i);
			rv[i] = kv.KeyToStr() + "|" + kv.Val();
		}
		return rv;
	}
	private static Ordered_hash Fill(String... ary) {
		Ordered_hash hash = Ordered_hash_.New();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			KeyVal kv = Split(ary[i]);
			hash.Add(kv.KeyToStr(), kv);
		}
		return hash;
	}
	private static KeyVal Split(String str) {
		String[] ary = StringUtl.Split(str, "|");
		return KeyVal.NewStr(ary[0], ary.length == 1 ? ary[0] : ary[1]);
	}
}
