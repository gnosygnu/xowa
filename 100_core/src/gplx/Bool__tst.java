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
package gplx;
import org.junit.*;
public class Bool__tst {
	private final Bool__fxt fxt = new Bool__fxt();
	@Test  public void Compare() {
		fxt.Test__compare(Bool_.Y, Bool_.Y, CompareAble_.Same);
		fxt.Test__compare(Bool_.N, Bool_.N, CompareAble_.Same);
		fxt.Test__compare(Bool_.N, Bool_.Y, CompareAble_.Less);
		fxt.Test__compare(Bool_.Y, Bool_.N, CompareAble_.More);
	}
}
class Bool__fxt {
	public void Test__compare(boolean lhs, boolean rhs, int expd) {Tfds.Eq(expd, Bool_.Compare(lhs, rhs));}
}
