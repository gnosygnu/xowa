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