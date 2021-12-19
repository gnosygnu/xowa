/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.btries;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.constants.AsciiByte;
import org.junit.*;
public class Btrie_slim_itm_tst {
	private Btrie_slim_itm itm = new Btrie_slim_itm(ByteUtl.Zero, null, false);
	@Before public void init() {itm.Clear();}
	@Test public void Find_nil() {
		tst_Find(AsciiByte.Ltr_a, null);
	}
	@Test public void Add_one() {
		run_Add(AsciiByte.Ltr_a);
		tst_Find(AsciiByte.Ltr_a, "a");
	}
	@Test public void Add_many() {
		run_Add(AsciiByte.Bang, AsciiByte.Num0, AsciiByte.Ltr_a, AsciiByte.Ltr_B);
		tst_Find(AsciiByte.Ltr_a, "a");
	}
	@Test public void Del() {
		run_Add(AsciiByte.Bang, AsciiByte.Num0, AsciiByte.Ltr_a, AsciiByte.Ltr_B);
		tst_Find(AsciiByte.Ltr_a, "a");
		run_Del(AsciiByte.Ltr_a);
		tst_Find(AsciiByte.Ltr_a, null);
		tst_Find(AsciiByte.Num0, "0");
		tst_Find(AsciiByte.Ltr_B, "B");
	}
	private void tst_Find(byte b, String expd) {
		Btrie_slim_itm actl_itm = itm.Ary_find(b);
		Object actl = actl_itm == null ? null : actl_itm.Val();
		GfoTstr.EqObj(expd, actl);
	}
	private void run_Add(byte... ary) {for (byte b : ary) itm.Ary_add(b, CharUtl.ToStr((char)b));}
	private void run_Del(byte... ary) {for (byte b : ary) itm.Ary_del(b);}
}
