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
public class Pft_func_date_rev_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before	public void setup()						{fxt.Reset(); fxt.Page().Db().Page().Modified_on_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));}
	@After public void teardown()				{}
	@Test   public void Rev_year()					{fxt.Test_parse_tmpl_str_test("{{REVISIONYEAR}}"		, "{{test}}", "2012");}
	@Test   public void Rev_month_int()				{fxt.Test_parse_tmpl_str_test("{{REVISIONMONTH1}}"		, "{{test}}", "1");}
	@Test   public void Rev_month_int_len2()		{fxt.Test_parse_tmpl_str_test("{{REVISIONMONTH}}"		, "{{test}}", "01");}
	@Test   public void Rev_day_int()				{fxt.Test_parse_tmpl_str_test("{{REVISIONDAY}}"			, "{{test}}", "2");}
	@Test   public void Rev_day_int_len2()			{fxt.Test_parse_tmpl_str_test("{{REVISIONDAY2}}"		, "{{test}}", "02");}
	@Test   public void Rev_timestamp()				{fxt.Test_parse_tmpl_str_test("{{REVISIONTIMESTAMP}}"	, "{{test}}", "20120102030405");}
}
