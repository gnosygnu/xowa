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
package gplx.xowa.xtns.pfuncs.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Pfunc_wiki_props_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before	public void setup()						{fxt.Reset(); fxt.Wiki().Stats().Load_by_db(1, 2, 3, 4, 5, 6, 7, 8);}
	@Test   public void NumPages()					{fxt.Test_parse_tmpl_str_test("{{NUMBEROFPAGES}}"				, "{{test}}", "1");}
	@Test   public void NumArticles()				{fxt.Test_parse_tmpl_str_test("{{NUMBEROFARTICLES}}"			, "{{test}}", "2");}
	@Test   public void NumFiles()					{fxt.Test_parse_tmpl_str_test("{{NUMBEROFFILES}}"				, "{{test}}", "3");}
	@Test   public void NumEdits()					{fxt.Test_parse_tmpl_str_test("{{NUMBEROFEDITS}}"				, "{{test}}", "4");}
	@Test   public void NumViews()					{fxt.Test_parse_tmpl_str_test("{{NUMBEROFVIEWS}}"				, "{{test}}", "5");}
	@Test   public void NumUsers()					{fxt.Test_parse_tmpl_str_test("{{NUMBEROFUSERS}}"				, "{{test}}", "6");}
	@Test   public void NumUsersActive()			{fxt.Test_parse_tmpl_str_test("{{NUMBEROFACTIVEUSERS}}"			, "{{test}}", "7");}
	@Test   public void NumAdmins()					{fxt.Test_parse_tmpl_str_test("{{NUMBEROFADMINS}}"				, "{{test}}", "8");}

	@Test   public void NumArticles_raw()			{Init_numArticles(1234); fxt.Test_parse_tmpl_str_test("{{NUMBEROFARTICLES:R}}"			, "{{test}}", "1234"); Init_numArticles(2);}
	@Test   public void NumArticles_fmt()			{Init_numArticles(1234); fxt.Test_parse_tmpl_str_test("{{NUMBEROFARTICLES}}"			, "{{test}}", "1,234"); Init_numArticles(2);}
	@Test   public void NumArticles_fmt_i18n()	{ // PURPOSE: use lang's num_mgr; PAGE:ru.u:Main_Page; DATE:2014-07-03
		fxt.Lang_by_id_(Xol_lang_stub_.Id_ru);
		Init_numArticles(1234); fxt.Test_parse_tmpl_str_test("{{NUMBEROFARTICLES}}"			, "{{test}}", "1234"); Init_numArticles(2);
	}
	private Pfunc_wiki_props_tst Init_numArticles(int v) {fxt.Wiki().Stats().Load_by_db(1, v, 3, 4, 5, 6, 7, 8); return this;}
}
