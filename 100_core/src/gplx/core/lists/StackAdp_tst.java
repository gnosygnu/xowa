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
public class StackAdp_tst {
	@Test  public void XtoList() {
		tst_XtoList(1, 2, 3);
	}
	void tst_XtoList(int... ary) {
		StackAdp stack = StackAdp_.new_();
		for (int i : ary)
			stack.Push(i);
		List_adp list = stack.XtoList();
		int[] actl = (int[])list.To_ary(int.class);
		for (int i = 0; i < ary.length; i++)
			Tfds.Eq(ary[i], actl[i]);
	}
}
