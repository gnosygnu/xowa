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
public class Pft_func_time_foreign_tst {
	@Before	public void init()							{fxt.Clear();} private Pft_func_time_foreign_fxt fxt = new Pft_func_time_foreign_fxt();
	@After public void term()						{fxt.Term();}
	@Test   public void Roman()							{fxt.Test_parse("{{#time:xrY|2012}}"			, "MMXII");}
	@Test   public void Thai()							{fxt.Test_parse("{{#time:xkY|2012}}"			, "2555");}
	@Test   public void Minguo()						{fxt.Test_parse("{{#time:xoY|2012}}"			, "101");}
	@Test   public void Hebrew_year_num()				{fxt.Test_parse("{{#time:xjY|2012-01-02}}"		, "5772");}
	@Test   public void Hebrew_month_num()				{fxt.Test_parse("{{#time:xjn|2012-01-02}}"		, "4");}
	@Test   public void Hebrew_day_num()				{fxt.Test_parse("{{#time:xjj|2012-01-02}}"		, "7");}
	@Test   public void Hebrew_month_days_count()		{fxt.Test_parse("{{#time:xjt|2012-01-02}}"		, "29");}
	@Test   public void Hebrew_month_name_full()		{fxt.Init_msg("hebrew-calendar-m4"		, "Tevet").Test_parse("{{#time:xjF|2012-01-02}}"		, "Tevet");}
	@Test   public void Hebrew_month_name_gen()			{fxt.Init_msg("hebrew-calendar-m4-gen"	, "Tevet").Test_parse("{{#time:xjx|2012-01-02}}"		, "Tevet");}
	@Test   public void Hebrew_numeral()				{fxt.Test_parse("{{#time:xh}}"				, "");}
	@Test   public void Hebrew_numeral_2()				{fxt.Test_parse("{{#time:xhxjY|2014}}"			, "ה'תשע\"ד");}
	@Test   public void Roman_various() {
		fxt.Test_Roman(   1, "I");
		fxt.Test_Roman(   2, "II");
		fxt.Test_Roman(   3, "III");
		fxt.Test_Roman(   4, "IV");
		fxt.Test_Roman(   5, "V");
		fxt.Test_Roman(   6, "VI");
		fxt.Test_Roman(   7, "VII");
		fxt.Test_Roman(   8, "VIII");
		fxt.Test_Roman(   9, "IX");
		fxt.Test_Roman(  10, "X");
		fxt.Test_Roman(  11, "XI");
		fxt.Test_Roman( 100, "C");
		fxt.Test_Roman( 101, "CI");
		fxt.Test_Roman( 111, "CXI");
		fxt.Test_Roman(1000, "M");
		fxt.Test_Roman(1001, "MI");
		fxt.Test_Roman(4000, "4000");
	}
}
class Pft_func_time_foreign_fxt {
	private Xop_fxt fxt = new Xop_fxt();
	public void Clear() {
		fxt.Reset();
		Tfds.Now_set(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));
	}
	public void Term() {
		Tfds.Now_enabled_n_();
	}
	public Pft_func_time_foreign_fxt Init_msg(String key, String val) {
		Xol_msg_itm msg = fxt.Wiki().Msg_mgr().Get_or_make(Bry_.new_u8(key));
		msg.Atrs_set(Bry_.new_u8(val), false, false);
		return this;
	}
	public void Test_parse(String raw, String expd) {
		fxt.Test_parse_tmpl_str_test(raw, "{{test}}", expd);
	}
	public void Test_Roman(int v, String expd) {
		Bry_bfr bfr = Bry_bfr.new_(16);
		Pfxtp_roman.ToRoman(v, bfr);
		String actl = bfr.Xto_str_and_clear();
		Tfds.Eq(expd, actl);
	}
}
