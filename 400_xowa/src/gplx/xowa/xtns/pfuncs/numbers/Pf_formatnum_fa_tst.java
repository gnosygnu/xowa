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
import org.junit.*;
import gplx.core.intls.*; import gplx.xowa.langs.*; import gplx.xowa.langs.numbers.*;
public class Pf_formatnum_fa_tst {
	private Xop_fxt fxt;
	@Before public void init() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xol_lang_itm lang = new Xol_lang_itm(app.Lang_mgr(), Bry_.new_a7("fa")).Init_by_load_assert();
		app.Gfs_mgr().Run_str_for(lang, Persian_numbers_gfs);
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "fa.wikipedia.org", lang);
		fxt = new Xop_fxt(app, wiki);
	}
	@Test  public void Basic() {
		fxt.Test_parse_tmpl_str_test("{{formatnum:۱۵۰|R}}"		, "{{test}}",	"150");
	}
	@Test  public void English() {	// PURPOSE: make sure regular numbers are still read; DATE:2015-07-18
		fxt.Test_parse_tmpl_str_test("{{formatnum:150|R}}"		, "{{test}}",	"150");
	}
	public static final String Persian_numbers_gfs = String_.Concat_lines_nl
	( "numbers {"
	, "  digits {"
	, "    clear;"
	, "    set('0', '۰');"
	, "    set('1', '۱');"
	, "    set('2', '۲');"
	, "    set('3', '۳');"
	, "    set('4', '۴');"
	, "    set('5', '۵');"
	, "    set('6', '۶');"
	, "    set('7', '۷');"
	, "    set('8', '۸');"
	, "    set('9', '۹');"
	, "    set('%', '٪');"
	, "    set('.', '٫');"
	, "    set(',', '٬');"
	, "  }"
	, "}"
	);
}
