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
public class Pft_func_time__hebrew__tst {
	@Before	public void init()					{fxt.Clear();} private Pft_func_time_foreign_fxt fxt = new Pft_func_time_foreign_fxt();
	@After public void term()				{fxt.Term();}
	@Test   public void Year_num()				{fxt.Test_parse("{{#time:xjY|2012-01-02}}"		, "5772");}
	@Test   public void Month_num()				{fxt.Test_parse("{{#time:xjn|2012-01-02}}"		, "4");}
	@Test   public void Day_num()				{fxt.Test_parse("{{#time:xjj|2012-01-02}}"		, "7");}
	@Test   public void Month_days_count()		{fxt.Test_parse("{{#time:xjt|2012-01-02}}"		, "29");}
	@Test   public void Month_name_full()		{fxt.Init_msg("hebrew-calendar-m4"		, "Tevet").Test_parse("{{#time:xjF|2012-01-02}}"		, "Tevet");}
	@Test   public void Month_name_gen()		{fxt.Init_msg("hebrew-calendar-m4-gen"	, "Tevet").Test_parse("{{#time:xjx|2012-01-02}}"		, "Tevet");}
	@Test   public void Numeral__empty()		{fxt.Test_parse("{{#time:xh}}"					, "");}
	@Test   public void Numeral_many()			{fxt.Test_parse("{{#time:xhxjj xjx xhxjY|28-08-1608 + 341 days}}"	, "ד'  ה'שס\"ט");}
	@Test   public void Numeral__year()	{
		String s = String_.new_u8(Bry_.New_by_ints(215, 148, 39, 215, 170, 215, 169, 215, 153, 34, 215, 152));	// ה'תש"ך 
		fxt.Test_parse("{{#time:xhxjY|1959}}", s); // NOTE: 1959 chosen b/c it will choose the first char from the end_trie; .Add_str_str("כ", "ך")
	} 
}
