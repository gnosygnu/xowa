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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pft_func_time__uncommon__tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before	public void init()					{fxt.Reset(); Datetime_now.Manual_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));}
	@After public void term()				{Datetime_now.Manual_n_();}
	@Test   public void Year__5_digits()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|00123-4-5}}"				, "{{test}}"			, "2003-04-05");} // PURPOSE: emulate PHP's incorrect date parsing; EX:ca.w:Nicolau_de_Mira; DATE:2014-04-17
	@Test   public void Year__multiple()		{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|12 November 2016 2008}}"	, "{{test}}"			, "2016-11-12");} // PURPOSE: multiple years should take 1st, not last; EX:en.w:Antipas,_Cotabato; DATE:2017-04-01
	@Test   public void Day__multiple()			{fxt.Test_parse_tmpl_str_test("{{#time:Y-m-d|2 12 November 2016}}"		, "{{test}}"			, "<strong class=\"error\">Invalid month: 12</strong>");} // PURPOSE: multiple days should generate error, not create a real date; EX:en.w:Antipas,_Cotabato; DATE:2017-04-01
}
