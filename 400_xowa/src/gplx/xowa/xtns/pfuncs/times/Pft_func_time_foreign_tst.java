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
	private Xop_fxt fxt = new Xop_fxt();
	@Before	public void init()					{fxt.Reset(); Tfds.Now_set(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));}
	@After public void term()				{Tfds.Now_enabled_n_();}
	@Test   public void Roman()					{fxt.Test_parse_tmpl_str_test("{{#time:xrY|2012}}"							, "{{test}}"			, "MMXII");}
	@Test   public void Thai()					{fxt.Test_parse_tmpl_str_test("{{#time:xkY|2012}}"							, "{{test}}"			, "2555");}
	@Test   public void Minguo()				{fxt.Test_parse_tmpl_str_test("{{#time:xoY|2012}}"							, "{{test}}"			, "101");}
	@Test   public void Roman_various() {
		tst_Roman(   1, "I");
		tst_Roman(   2, "II");
		tst_Roman(   3, "III");
		tst_Roman(   4, "IV");
		tst_Roman(   5, "V");
		tst_Roman(   6, "VI");
		tst_Roman(   7, "VII");
		tst_Roman(   8, "VIII");
		tst_Roman(   9, "IX");
		tst_Roman(  10, "X");
		tst_Roman(  11, "XI");
		tst_Roman( 100, "C");
		tst_Roman( 101, "CI");
		tst_Roman( 111, "CXI");
		tst_Roman(1000, "M");
		tst_Roman(1001, "MI");
		tst_Roman(4000, "4000");
	}
	private void tst_Roman(int v, String expd) {
		Bry_bfr bfr = Bry_bfr.new_(16);
		Pfxtp_roman.ToRoman(v, bfr);
		String actl = bfr.XtoStrAndClear();
		Tfds.Eq(expd, actl);
	}
}
