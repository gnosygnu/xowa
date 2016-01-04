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
