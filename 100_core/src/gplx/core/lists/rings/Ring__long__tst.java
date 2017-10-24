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
package gplx.core.lists.rings; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
import org.junit.*; import gplx.core.tests.*;
public class Ring__long__tst {
	private final    Ring__long__fxt fxt = new Ring__long__fxt();
	@Test   public void Basic__1()	{fxt.Clear().Add(1)												.Test__to_ary(1);}
	@Test   public void Basic__2()	{fxt.Clear().Add(1, 2)											.Test__to_ary(1, 2);}
	@Test   public void Basic__3()	{fxt.Clear().Add(1, 2, 3)										.Test__to_ary(1, 2, 3);}
	@Test   public void Wrap__1()	{fxt.Clear().Add(1, 2, 3, 4)									.Test__to_ary(2, 3, 4);}
	@Test   public void Wrap__2()	{fxt.Clear().Add(1, 2, 3, 4, 5)									.Test__to_ary(3, 4, 5);}
	@Test   public void Wrap__3()	{fxt.Clear().Add(1, 2, 3, 4, 5, 6)								.Test__to_ary(4, 5, 6);}
}
class Ring__long__fxt {
	private Ring__long ring = new Ring__long(3);
	public Ring__long__fxt Clear() {ring.Clear(); return this;}
	public Ring__long__fxt Add(long... ary) {
		for (long itm : ary)
 				ring.Add(itm);
		return this;
	}
	public Ring__long__fxt Test__to_ary(long... expd) {
		Gftest.Eq__ary(expd, ring.To_ary(), "to_ary");
		return this;
	}
}
