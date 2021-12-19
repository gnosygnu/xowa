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
package gplx.langs.jsons;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Test;
public class Json_doc_wtr_tst {
	private final Json_doc_wtr_fxt fxt = new Json_doc_wtr_fxt();
	@Test public void Basic() {
		fxt.Test__Bld_as_str
		( fxt.Exec__Kv_simple("k1", "v\"1")
		, fxt.Exec__Concat_apos
		( "{"
		, "  'k1':'v\\\"1'"
		, "}"));
	}
	@Test public void Quotes() {
		fxt.Test__string__quotes("a\"z"                      , "a\\\"z");
		fxt.Test__string__quotes("a\u0008z"                  , "a\\bz");
		fxt.Test__string__quotes("a\fz"                      , "a\\fz");
		fxt.Test__string__quotes("a\nz"                      , "a\\nz");
		fxt.Test__string__quotes("a\rz"                      , "a\\rz");
		fxt.Test__string__quotes("a\tz"                      , "a\\tz");
		fxt.Test__string__quotes("aēz"                       , "aēz");
		fxt.Test__string__quotes("az"                       , "a\\u000Fz");
		fxt.Test__string__quotes("a z"                       , "a\\u00A0z");
		fxt.Test__string__quotes("a‎z"                       , "a\\u200Ez");
		fxt.Test__string__quotes("a‏z"                       , "a\\u200Fz");
	}
}
class Json_doc_wtr_fxt {
	public Json_doc_wtr Exec__Kv_simple(String key, String val) {
		Json_doc_wtr doc_wtr = new Json_doc_wtr();
		doc_wtr.Nde_bgn();
		doc_wtr.Kv(BoolUtl.N, BryUtl.NewU8(key), BryUtl.NewU8(val));
		doc_wtr.Nde_end();
		return doc_wtr;
	}
	public void Test__Bld_as_str(Json_doc_wtr doc_wtr, String expd) {
		GfoTstr.EqLines(expd, doc_wtr.Bld_as_str());
	}
	public String Exec__Concat_apos(String... ary) {
		return Json_doc.Make_str_by_apos(ary);
	}
	public void Test__string__quotes(String raw, String expd) {
		Json_doc_wtr doc_wtr = new Json_doc_wtr();
		doc_wtr.Opt_unicode_y_();
		doc_wtr.Str(BryUtl.NewU8(raw));
		String actl = doc_wtr.Bld_as_str();
		actl = StringUtl.Mid(actl, 1, StringUtl.Len(actl) - 1);
		GfoTstr.Eq(expd, actl);
	}
}
