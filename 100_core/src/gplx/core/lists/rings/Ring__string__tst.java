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
import org.junit.*;
public class Ring__string__tst {
	private final    Ring__string__fxt fxt = new Ring__string__fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Clear().Max_(3).Push_many("a")								.Expd("a");
		fxt.Clear().Max_(3).Push_many("a", "b")							.Expd("a", "b");
		fxt.Clear().Max_(3).Push_many("a", "b", "c")					.Expd("a", "b", "c");
		fxt.Clear().Max_(3).Push_many("a", "b", "c", "d")				.Expd("b", "c", "d");
		fxt.Clear().Max_(3).Push_many("a", "b", "c", "d", "e")			.Expd("c", "d", "e");
		fxt.Clear().Max_(3).Push_many("a", "b", "c", "d", "e", "f")		.Expd("d", "e", "f");
	}
}
class Ring__string__fxt {
	Ring__string ring = new Ring__string();
	public Ring__string__fxt Clear() {ring.Clear(); return this;}
	public Ring__string__fxt Max_(int v) {ring.Max_(v); return this;}
	public Ring__string__fxt Push_many(String... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++)
 				ring.Push(ary[i]); 
		return this;
	}
	public Ring__string__fxt Expd(String... expd) {
		Tfds.Eq_ary_str(expd, ring.Xto_str_ary());
		return this;
	}
}
