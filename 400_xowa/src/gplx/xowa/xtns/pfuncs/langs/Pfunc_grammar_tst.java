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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Pfunc_grammar_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void English() {// for now, mark unimplemented langs by returning not_found; [[Template:grammar]]; wait for users to report
		fxt	.Test_parse_tmpl_str_test ("{{grammar:a|b}}"			, "{{test}}", "[[:Template:grammar]]");
	}
	@Test  public void Finnish() {
		fxt.Lang_by_id_(Xol_lang_stub_.Id_fi);
		fxt.Reset().Test_html_full_str("{{grammar:elative|Wikiuutiset}}"		, "Wikiuutisista");
		fxt.Reset().Test_html_full_str("{{grammar:talo}}"						, "");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|talo}}"				, "talon");
		fxt.Reset().Test_html_full_str("{{grammar:elative|talo}}"				, "talosta");
		fxt.Reset().Test_html_full_str("{{grammar:inessive|talo}}"				, "talossa");
		fxt.Reset().Test_html_full_str("{{grammar:partitive|talo}}"				, "taloa");
		fxt.Reset().Test_html_full_str("{{grammar:illative|talo}}"				, "taloon");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|sängy}}"				, "sängyn");
		fxt.Reset().Test_html_full_str("{{grammar:elative|sängy}}"				, "sängystä");
		fxt.Reset().Test_html_full_str("{{grammar:inessive|sängy}}"				, "sängyssä");
		fxt.Reset().Test_html_full_str("{{grammar:partitive|sängy}}"			, "sängyä");
		fxt.Reset().Test_html_full_str("{{grammar:illative|sängy}}"				, "sängyyn");
	}
	@Test  public void Russian() {
		fxt.Lang_by_id_(Xol_lang_stub_.Id_ru);
		fxt.Reset().Test_html_full_str("{{grammar:unknown}}"					, "");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|aвики}}"				, "aвики");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|aВики}}"				, "aВики");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|aь}}"				, "aя");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|aия}}"				, "aии");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|aка}}"				, "aки");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|aти}}"				, "aтей");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|aды}}"				, "aдов");
		fxt.Reset().Test_html_full_str("{{grammar:genitive|aник}}"				, "aника");
		fxt.Reset().Test_html_full_str("{{grammar:dative|a}}"					, "a");
		fxt.Reset().Test_html_full_str("{{grammar:accusative|a}}"				, "a");
		fxt.Reset().Test_html_full_str("{{grammar:instrumental|a}}"				, "a");
		fxt.Reset().Test_html_full_str("{{grammar:prepositional|a}}"			, "a");
	}
	@Test  public void Hebrew() {
		fxt.Lang_by_id_(Xol_lang_stub_.Id_he);
		fxt.Reset().Test_html_full_str("{{grammar:unknown|abc}}"				, "abc");
		fxt.Reset().Test_html_full_str("{{grammar:prefixed|וabc}}"				, "ווabc");	// waw: add ו
		fxt.Reset().Test_html_full_str("{{grammar:prefixed|ווabc}}"				, "ווabc");	// waw: do not add ו if וו
		fxt.Reset().Test_html_full_str("{{grammar:prefixed|הabc}}"				, "abc");	// he: remove ה
		fxt.Reset().Test_html_full_str("{{grammar:prefixed|אabc}}"				, "־אabc");	// maqaf: add ־
	}
}
