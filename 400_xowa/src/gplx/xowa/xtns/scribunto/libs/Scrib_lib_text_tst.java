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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.langs.jsons.*;
public class Scrib_lib_text_tst {
	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib_text lib;
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_text();
		lib.Init();
	}
	@Test  public void Unstrip() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_text.Invk_unstrip, Object_.Ary("a"), "a");
	}
	@Test  public void GetEntityTable() {
		Keyval[] actl = fxt.Test_scrib_proc_rv_as_kv_ary(lib, Scrib_lib_text.Invk_getEntityTable, Object_.Ary());
		Tfds.Eq(1510, actl.length);	// large result; only test # of entries
	}
}
