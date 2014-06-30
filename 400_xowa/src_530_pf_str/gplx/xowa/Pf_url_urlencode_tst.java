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
public class Pf_url_urlencode_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()				{fxt.Reset();}
	@Test   public void Numbers()			{fxt.Test_parse_tmpl_str_test("{{urlencode:0123456789}}"						, "{{test}}", "0123456789");}
	@Test   public void Ltrs_lower()		{fxt.Test_parse_tmpl_str_test("{{urlencode:abcdefghijklmnopqrstuvwxyz}}"		, "{{test}}", "abcdefghijklmnopqrstuvwxyz");}
	@Test   public void Ltrs_upper()		{fxt.Test_parse_tmpl_str_test("{{urlencode:ABCDEFGHIJKLMNOPQRSTUVWXYZ}}"		, "{{test}}", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");}
	@Test   public void Syms_allowed()		{fxt.Test_parse_tmpl_str_test("{{urlencode:-_.}}"								, "{{test}}", "-_.");}
	@Test   public void Space()				{fxt.Test_parse_tmpl_str_test("{{urlencode:a b}}"								, "{{test}}", "a+b");}
	@Test   public void Syms()				{fxt.Test_parse_tmpl_str_test("{{urlencode:!?^~:}}"								, "{{test}}", "%21%3F%5E%7E%3A");}
	@Test   public void Extended()			{fxt.Test_parse_tmpl_str_test("{{urlencode:aéb}}"								, "{{test}}", "a%C3%A9b");}
}
