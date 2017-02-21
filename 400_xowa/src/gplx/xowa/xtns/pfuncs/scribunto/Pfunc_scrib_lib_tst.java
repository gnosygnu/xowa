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
package gplx.xowa.xtns.pfuncs.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
public class Pfunc_scrib_lib_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = new Pfunc_scrib_lib();
		lib.Init();
		lib.Core_(fxt.Core());
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Pfunc_scrib_lib lib;
	@Test   public void Expr__pass() {
		fxt.Test_scrib_proc_str(lib, Pfunc_scrib_lib.Invk_expr, Object_.Ary("1 + 2")						, "3");
	}
	@Test   public void Expr__int() {
		fxt.Test_scrib_proc_str(lib, Pfunc_scrib_lib.Invk_expr, Object_.Ary(3)								, "3");	// int should not cause class cast error; PAGE:en.w:531_BC; DATE:2016-04-29
	}
	@Test   public void Expr__fail() {	// PURPOSE: if bad input don't throw error; return error message; PAGE:es.w:Freer_(Texas) DATE:2015-07-28
		fxt.Test_scrib_proc_str(lib, Pfunc_scrib_lib.Invk_expr, Object_.Ary("fail")							, "<strong class=\"error\">Expression error: Unrecognised word \"fail\"</strong>");
	}
}	
