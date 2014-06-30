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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Pf_str_pad_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test  public void L_len_3()			{fxt.Test_parse_tmpl_str_test("{{padleft: a|4|0}}"				, "{{test}}"	, "000a");}
	@Test  public void L_str_ab()			{fxt.Test_parse_tmpl_str_test("{{padleft: a|4|01}}"				, "{{test}}"	, "010a");}
	@Test  public void L_len_neg1()			{fxt.Test_parse_tmpl_str_test("{{padleft: a|-1|01}}"				, "{{test}}"	, "a");}
	@Test  public void L_val_null()			{fxt.Test_parse_tmpl_str_test("{{padleft: |4|0}}"				, "{{test}}"	, "0000");}
	@Test  public void L_word_3()			{fxt.Test_parse_tmpl_str_test("{{padleft: abc|4}}"				, "{{test}}"	, "0abc");}
	@Test  public void L_exc_len_bad1()		{fxt.Test_parse_tmpl_str_test("{{padleft:a|bad|01}}"				, "{{test}}"	, "a");}
	@Test  public void L_exc_pad_ws()		{fxt.Test_parse_tmpl_str_test("{{padleft:a|4|\n \t}}"			, "{{test}}"	, "a");}
	@Test  public void R_len_3()			{fxt.Test_parse_tmpl_str_test("{{padright:a|4|0}}"				, "{{test}}"	, "a000");}
	@Test  public void R_str_ab()			{fxt.Test_parse_tmpl_str_test("{{padright:a|4|01}}"				, "{{test}}"	, "a010");}
	@Test  public void R_str_intl()			{fxt.Test_parse_tmpl_str_test("{{padright:|6|devanā}}"			, "{{test}}"	, "devanā");}
}
/*
{{padleft: a|4|0}}
*/