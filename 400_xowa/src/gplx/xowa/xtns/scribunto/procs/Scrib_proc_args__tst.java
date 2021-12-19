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
package gplx.xowa.xtns.scribunto.procs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import org.junit.*;
public class Scrib_proc_args__tst {
	private final Scrib_proc_args__fxt fxt = new Scrib_proc_args__fxt();
	@Test public void Pull_kv_ary__basic() {	// PURPOSE.assert:
		fxt.Init(KeyVal.NewInt(1, KeyValUtl.Ary(KeyVal.NewInt(1, "a"), KeyVal.NewInt(2, "b"), KeyVal.NewInt(3, "c"))));
		fxt.Test__pull_kv_ary(0, KeyVal.NewInt(1, "a"), KeyVal.NewInt(2, "b"), KeyVal.NewInt(3, "c"));
	}
	@Test public void Pull_kv_ary__gaps() {	// PURPOSE.fix: gaps cause null-ref; PAGE:en.w:Shalkar_District DATE:2016-09-12
		fxt.Init(KeyVal.NewInt(1, KeyValUtl.Ary(KeyVal.NewInt(1, "a"), KeyVal.NewInt(3, "c"), KeyVal.NewInt(5, "e"))));
		fxt.Test__pull_kv_ary(0, KeyVal.NewInt(1, "a"), KeyVal.NewInt(2, null), KeyVal.NewInt(3, "c"), KeyVal.NewInt(4, null), KeyVal.NewInt(5, "e"));
	}
	@Test public void Pull_kv_ary__gaps__many() {	// PURPOSE.assert:
		fxt.Init(KeyVal.NewInt(1, KeyValUtl.Ary(KeyVal.NewInt(1, "a"), KeyVal.NewInt(4, "d"), KeyVal.NewInt(5, "e"), KeyVal.NewInt(8, "h"))));
		fxt.Test__pull_kv_ary(0
		, KeyVal.NewInt(1, "a"), KeyVal.NewInt(2, null), KeyVal.NewInt(3, null), KeyVal.NewInt(4, "d"), KeyVal.NewInt(5, "e")
		, KeyVal.NewInt(6, null), KeyVal.NewInt(7, null), KeyVal.NewInt(8, "h")
		);
	}
	@Test public void Pull_kv_ary__null() {	// PURPOSE: null arg shouldn't fail; PAGE:en.w:Huadu_District DATE:2017-05-11
		fxt.Init(KeyVal.NewInt(1, KeyValUtl.Ary(KeyVal.NewInt(1, "a"), null, KeyVal.NewInt(2, "b"))));
		fxt.Test__pull_kv_ary(0, KeyVal.NewInt(1, "a"), null, KeyVal.NewInt(2, "b"));
	}
}
class Scrib_proc_args__fxt {
	private Scrib_proc_args args;
	public void Init(KeyVal... ary) {this.args = new Scrib_proc_args(ary);}
	public void Test__pull_kv_ary(int idx, KeyVal... expd) {
		KeyVal[] actl = args.Pull_kv_ary_safe(idx);
		GfoTstr.Eq(KeyValUtl.AryToStr(expd), KeyValUtl.AryToStr(actl));
	}
}
