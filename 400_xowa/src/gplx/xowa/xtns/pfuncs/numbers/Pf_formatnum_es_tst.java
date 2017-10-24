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
package gplx.xowa.xtns.pfuncs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*;
import gplx.core.intls.*; import gplx.xowa.langs.numbers.*;
public class Pf_formatnum_es_tst {
	private Xop_fxt fxt;
	@Before public void init() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xol_lang_itm lang = new Xol_lang_itm(app.Lang_mgr(), Bry_.new_a7("es")).Init_by_load_assert();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "es.wikipedia.org", lang);
		fxt = new Xop_fxt(app, wiki);
	}
	@Test  public void Basic()	{
		fxt.Test_parse_tmpl_str_test("{{formatnum:1234.56}}"		, "{{test}}",	"1234.56");		// fmt.n;
		fxt.Test_parse_tmpl_str_test("{{formatnum:1234}}"			, "{{test}}",	"1234");		// fmt.n; decimal
		fxt.Test_parse_tmpl_str_test("{{formatnum:-1234.56}}"		, "{{test}}",	"-1234.56");	// fmt.n; neg
		fxt.Test_parse_tmpl_str_test("{{formatnum:12345.90}}"		, "{{test}}",	"12,345.90");	// fmt.y; 5
		fxt.Test_parse_tmpl_str_test("{{formatnum:123456.90}}"		, "{{test}}",	"123,456.90");	// fmt.y; 6
		fxt.Test_parse_tmpl_str_test("{{formatnum:1234.}}"			, "{{test}}",	"1,234.");		// stress; decimal at end
		fxt.Test_parse_tmpl_str_test("{{formatnum:123456a}}"		, "{{test}}",	"123,456a");	// stress; letters
	}
}
