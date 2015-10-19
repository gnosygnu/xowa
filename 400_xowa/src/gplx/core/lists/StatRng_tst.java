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
package gplx.core.lists; import gplx.*; import gplx.core.*;
import org.junit.*;
public class StatRng_tst {
//		Mwl_parser_fxt fx = new Mwl_parser_fxt(); Pf_func_lang_rsc rsc = Pf_func_lang_rsc.Instance;
	StatRng_fxt fx = new StatRng_fxt();
	@Test  public void Empty()				{
 			fx.ini_(1, 1, 5);
		fx.Count_(7).Lo_(2).Hi_(8).Avg_(5)
		.Lo_ary_(2)
		.Hi_ary_(8)
		.Slots_(3, 4);
		fx.tst_(5,7,2,8,3,4,6);
	}
//@Test  public void Basic() {fx.Test_parse_tmpl_str_test("{{#switch:{{{1}}}|a=1|b=2|3}}", "{{test|a}}", "1");}
//@Test  public void Basic() {fx.Test_parse_tmpl_str_test("{{#switch:{{{1}}}|b=2|#default=3|a=1}}", "{{test|a}}", "1");}
//@Test  public void Basic() {fx.Test_parse_tmpl_str_test("{{#switch:{{{1}}}|a|b|c=1|d=2}}", "{{test|a}}", "1");}
}
/*
public class Pf_func_switch_tst {
//		Mwl_parser_fxt fx = new Mwl_parser_fxt(); Pf_func_lang_rsc rsc = Pf_func_lang_rsc.Instance;

*/