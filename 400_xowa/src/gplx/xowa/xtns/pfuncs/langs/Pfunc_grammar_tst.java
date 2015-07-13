/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Pfunc_grammar_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void English() {// for now, mark unimplemented langs by returning not_found; [[Template:grammar]]; wait for users to report
		fxt.Test_parse_tmpl_str_test("{{grammar:a|b}}"						, "{{test}}"	, "[[:Template:grammar]]");
	}
	@Test  public void Finnish() {
		fxt.Lang_by_id_(Xol_lang_itm_.Id_fi);
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:elative|Wikiuutiset}}"		, "{{test}}"	, "Wikiuutisista");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:talo}}"						, "{{test}}"	, "");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|talo}}"			, "{{test}}"	, "talon");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:elative|talo}}"				, "{{test}}"	, "talosta");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:inessive|talo}}"			, "{{test}}"	, "talossa");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:partitive|talo}}"			, "{{test}}"	, "taloa");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:illative|talo}}"			, "{{test}}"	, "taloon");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|sängy}}"			, "{{test}}"	, "sängyn");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:elative|sängy}}"			, "{{test}}"	, "sängystä");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:inessive|sängy}}"			, "{{test}}"	, "sängyssä");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:partitive|sängy}}"			, "{{test}}"	, "sängyä");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:illative|sängy}}"			, "{{test}}"	, "sängyyn");
	}
	@Test  public void Russian() {
		fxt.Lang_by_id_(Xol_lang_itm_.Id_ru);
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:unknown}}"					, "{{test}}"	, "");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|aвики}}"			, "{{test}}"	, "aвики");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|aВики}}"			, "{{test}}"	, "aВики");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|aь}}"				, "{{test}}"	, "aя");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|aия}}"				, "{{test}}"	, "aии");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|aка}}"				, "{{test}}"	, "aки");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|aти}}"				, "{{test}}"	, "aтей");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|aды}}"				, "{{test}}"	, "aдов");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:genitive|aник}}"			, "{{test}}"	, "aника");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:dative|a}}"					, "{{test}}"	, "a");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:accusative|a}}"				, "{{test}}"	, "a");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:instrumental|a}}"			, "{{test}}"	, "a");
		fxt.Reset().Test_parse_tmpl_str_test("{{grammar:prepositional|a}}"			, "{{test}}"	, "a");
	}
}
