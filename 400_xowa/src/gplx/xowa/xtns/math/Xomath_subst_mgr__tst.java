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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Xomath_subst_mgr__tst {
	private final    Xomath_subst_mgr subst_regy = new Xomath_subst_mgr();
	@Test  public void Basic()						{tst("a\\plusmn b"			, "a\\pm b");}
	@Test  public void Match_fails()				{tst("a\\plusmna b"			, "a\\plusmna b");}
	@Test  public void Part()						{tst("a\\part_t b"			, "a\\partial_t b");}	// PAGE:en.w:Faraday's law of induction
	@Test  public void Partial()					{tst("a\\partial_{x_i}"		, "a\\partial_{x_i}");}	// DEFECT: partial -> partialial
	private void tst(String src, String expd) {Tfds.Eq(expd, String_.new_u8(subst_regy.Subst(Bry_.new_u8(src))));}
}
