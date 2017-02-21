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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_anchorencode_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test  public void Lnke()				{fxt.Test_parse_tmpl_str_test("{{anchorencode:[irc://a b c]}}"					, "{{test}}"	, "b_c");}
	@Test  public void Apos_bold()			{fxt.Test_parse_tmpl_str_test("{{anchorencode:a ''b'' c}}"						, "{{test}}"	, "a_b_c");}
	@Test  public void Apos_1()				{fxt.Test_parse_tmpl_str_test("{{anchorencode:a 'b c}}"							, "{{test}}"	, "a_.27b_c");}
	@Test  public void Lnki_trg()			{fxt.Test_parse_tmpl_str_test("{{anchorencode:a [[b]] c}}"						, "{{test}}"	, "a_b_c");}
	@Test  public void Lnki_caption()		{fxt.Test_parse_tmpl_str_test("{{anchorencode:a [[b|c]] c}}"					, "{{test}}"	, "a_c_c");}
	@Test  public void Lnki_file()			{fxt.Test_parse_tmpl_str_test("{{anchorencode:a [[Image:b|thumb|c]] d}}"		, "{{test}}"	, "a_thumb.7Cc_d");}
	@Test  public void Xnde()				{fxt.Test_parse_tmpl_str_test("{{anchorencode:a <i>b</i> c}}"					, "{{test}}"	, "a_b_c");}
	@Test  public void Html_ncr()			{fxt.Test_parse_tmpl_str_test("{{anchorencode:a &#34; b}}"						, "{{test}}"	, "a_.22_b");}
	@Test  public void Html_ref()			{fxt.Test_parse_tmpl_str_test("{{anchorencode:a &quot; b}}"						, "{{test}}"	, "a_.22_b");}
	@Test  public void Tmpl_missing_basic() {fxt.Test_parse_tmpl_str_test("{{anchorencode:{{a}}}}"							, "{{test}}"	, "Template:a");}
	@Test  public void Tmpl_missing_colon() {fxt.Test_parse_tmpl_str_test("{{anchorencode:{{:a}}}}"							, "{{test}}"	, "a");}	// NOTE: changed from "Template:A" to "a"; DATE:2016-06-24
	@Test  public void Lnki_literal()		{fxt.Test_parse_tmpl_str_test("{{anchorencode:[[:a]]}}"							, "{{test}}"	, "a");}
}
