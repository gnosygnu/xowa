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
package gplx.xowa.langs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xol_num_fmtr_base_tst {
	Xol_num_fmtr_base mgr = new Xol_num_fmtr_base();
	@Before public void init() {mgr.Clear();}
	@Test  public void Outliers() {
		ini_(".", dat_(",", 3));
		tst_Fmt("1234a1234"							, "1,234a1,234");
		tst_Fmt("1234abc1234"						, "1,234abc1,234");
		tst_Fmt("1234,1234"							, "1,234,1,234");
		tst_Fmt("1234.1234"							, "1,234.1234");
		tst_Fmt("1234."								, "1,234.");
		tst_Fmt("1234.1234.1234.1234"				, "1,234.1234.1234.1234");
		tst_Fmt("-1234567"							, "-1,234,567");
		tst_Fmt("1,234,567"							, "1,234,567");
	}
	@Test  public void English() {
		ini_(".", dat_(",", 3));
		tst_Fmt("123"								, "123");
		tst_Fmt("1234"								, "1,234");
		tst_Fmt("12345678"							, "12,345,678");
		tst_Fmt("12345678901234567890"				, "12,345,678,901,234,567,890");
		tst_Raw("1,234.12"							, "1234.12");
	}
	@Test  public void French() {
		ini_(",", dat_(" ", 3));
		tst_Fmt("123"								, "123");
		tst_Fmt("1234"								, "1 234");
		tst_Fmt("12345678"							, "12 345 678");
		tst_Fmt("12345678901234567890"				, "12 345 678 901 234 567 890");
		tst_Fmt("1234,5678"							, "1 234 5 678"); // NOTE: nbsp here; also, nbsp is repeated. see dewiki and {{formatnum:1234,56}}
	}
	@Test  public void Croatia() {
		ini_(",", dat_(".", 3), dat_(",", 3));
		tst_Fmt("123"								, "123");
		tst_Fmt("1234"								, "1.234");
		tst_Fmt("12345678"							, "12,345.678");
		tst_Fmt("12345678901234567890"				, "12,345.678,901.234,567.890");
	}
	@Test  public void Mexico() {
		ini_(".", dat_(",", 3, false), dat_("'", 3, false), dat_(",", 3));
		tst_Fmt("123"								, "123");
		tst_Fmt("1234"								, "1,234");
		tst_Fmt("12345678"							, "12'345,678");
		tst_Fmt("12345678901234567890"				, "12,345,678,901,234'567,890");
		tst_Raw("12'345,678.90"						, "12345678.90");
	}
	@Test  public void China() {
		ini_(".", dat_(",", 4));
		tst_Fmt("123"								, "123");
		tst_Fmt("1234"								, "1234");
		tst_Fmt("12345678"							, "1234,5678");
		tst_Fmt("12345678901234567890"				, "1234,5678,9012,3456,7890");
	}
	@Test  public void Hindi() {
		ini_(".", dat_(",", 3, false), dat_(",", 2));
		tst_Fmt("123"								, "123");
		tst_Fmt("1234"								, "1,234");
		tst_Fmt("12345678"							, "1,23,45,678");
		tst_Fmt("12345678901234567890"				, "1,23,45,67,89,01,23,45,67,890");
	}
	@Test  public void India() {
		ini_(".", dat_(",", 3), dat_(",", 2), dat_(",", 2));
		tst_Fmt("123"								, "123");
		tst_Fmt("1234"								, "1,234");
		tst_Fmt("12345678"							, "1,23,45,678");
		tst_Fmt("12345678901234567890"				, "1,23,456,78,90,123,45,67,890");
	}
	@Test  public void MiddleDot() {
		ini_("·", dat_("·", 3));
		tst_Fmt("123"								, "123");
		tst_Fmt("1234"								, "1·234");
		tst_Fmt("12345678"							, "12·345·678");
		tst_Fmt("12345678901234567890"				, "12·345·678·901·234·567·890");
		tst_Fmt("1234·5678"							, "1·234·5·678");// NOTE: middle-dot is repeated. see dewiki and {{formatnum:1234,5678}}
		tst_Raw("1234·5678"							, "1234.5678");
	}
	Xol_num_grp dat_(String dlm, int digits)				{return new Xol_num_grp(Bry_.new_u8(dlm), digits, true);}
	Xol_num_grp dat_(String dlm, int digits, boolean repeat)	{return new Xol_num_grp(Bry_.new_u8(dlm), digits, repeat);}
	private void tst_Fmt(String val, String expd) {Tfds.Eq(expd, String_.new_u8(mgr.Fmt(Bry_.new_u8(val))));}
	private void tst_Raw(String val, String expd) {Tfds.Eq(expd, String_.new_u8(mgr.Raw(Xol_num_fmtr_base.Tid_raw, Bry_.new_u8(val))));}
	private void ini_(String dec_dlm, Xol_num_grp... ary) {
		mgr.Dec_dlm_(Bry_.new_u8(dec_dlm));
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++)
			mgr.Grps_add(ary[i]);
	}
}
/*
'france'  			' 3#' ',0%' 						// 1 234 567,89
'spain'				'.3#' "'0%" 						// 1.234.567'89
'germany'			'.3#' ",0%" 						// 1.234.567,89
'italy'				''3#' ",0%" 						// 1'234'567,89
'en-us' 			',3#' '.0%'							// 1,234,567.89
'en-sa' 			',3#' '\u00120%'					// 1,234,567·89
'croatia' 			',3#*' '.3#*' ',0%'					// 1,234.567,890.123,45
'china'				',4$'								// 123,4567.89
'mexico'			',3#*' "'3#" ',3#'					// 1'234,567.89
'hindi'				",2#*" ',3#'						// 1,23,45,678.9
'india'				',2#*' ',2#*' ',3#*'				// 1,245,67,89,012
*/