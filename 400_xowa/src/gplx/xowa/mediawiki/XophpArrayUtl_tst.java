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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.tests.*;
public class XophpArrayUtl_tst { // REF:https://www.php.net/manual/en/function.array-merge.php
	private final    XophpArrayUtl_fxt fxt = new XophpArrayUtl_fxt();
	@Test  public void array_merge__basic() {
		XophpArray ary1 = fxt.Make().Add("key1", "val1").Add("a");
		XophpArray ary2 = fxt.Make().Add("key2", "val2").Add("b");
		fxt.Test__array_merge
		( fxt.Make().Add("key1", "val1").Add("a").Add("key2", "val2").Add("b")
		, ary1, ary2);
	}
	@Test  public void array_merge__same_key() {
		XophpArray ary1 = fxt.Make().Add("key", "val1");
		XophpArray ary2 = fxt.Make().Add("key", "val2");
		fxt.Test__array_merge
		( fxt.Make().Add("key", "val2")
		, ary1, ary2);
	}
	@Test  public void array_merge__same_idx() {
		XophpArray ary1 = fxt.Make().Add(0, "a");
		XophpArray ary2 = fxt.Make().Add(0, "b");
		fxt.Test__array_merge
		( fxt.Make().Add(0, "a").Add(1, "b")
		, ary1, ary2);
	}
	@Test  public void array_merge__renumber() {
		XophpArray ary1 = fxt.Make().Add(3, "a");
		XophpArray ary2 = fxt.Make().Add(2, "b");
		fxt.Test__array_merge
		( fxt.Make().Add(0, "a").Add(1, "b")
		, ary1, ary2);
	}
	@Test  public void array_merge__example_1() {
		XophpArray ary1 = fxt.Make().Add("color", "red").Add_many(2, 4);
		XophpArray ary2 = fxt.Make().Add_many("a", "b").Add("color", "green").Add("shape", "trapezoid").Add(4);
		fxt.Test__array_merge
		( fxt.Make().Add("color", "green").Add_many(2, 4, "a", "b").Add("shape", "trapezoid").Add(4)
		, ary1, ary2);
	}
	@Test  public void array_merge__example_2() {
		XophpArray ary1 = fxt.Make();
		XophpArray ary2 = fxt.Make().Add(1, "data");
		fxt.Test__array_merge
		( fxt.Make().Add(0, "data")
		, ary1, ary2);
	}
	@Test  public void array_splice__bgn_is_positive() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArrayUtl.array_splice(src, 1);
		fxt.Test__eq
		( fxt.Make().Add_many("a")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b", "c", "d")
		, del
		);
	}
	@Test  public void array_splice__bgn_is_positive_large() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArrayUtl.array_splice(src, 99);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "b", "c", "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many()
		, del
		);
	}
	@Test  public void array_splice__bgn_is_negative() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArrayUtl.array_splice(src, -3);
		fxt.Test__eq
		( fxt.Make().Add_many("a")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b", "c", "d")
		, del
		);
	}
	@Test  public void array_splice__bgn_is_negative_large() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArrayUtl.array_splice(src, -99);
		fxt.Test__eq
		( fxt.Make()
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "b", "c", "d")
		, del
		);
	}
	@Test  public void array_splice__len_is_positive() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArrayUtl.array_splice(src, 1, 2);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b", "c")
		, del
		);
	}
	@Test  public void array_splice__len_is_positive_large() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArrayUtl.array_splice(src, 1, 99);
		fxt.Test__eq
		( fxt.Make().Add_many("a")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b", "c", "d")
		, del
		);
	}
	@Test  public void array_splice__len_is_negative() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArrayUtl.array_splice(src, 1, -2);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "c", "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b")
		, del
		);
	}
	@Test  public void array_splice__len_is_negative_large() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArrayUtl.array_splice(src, 1, -99);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "b", "c", "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make()
		, del
		);
	}
	@Test  public void array_splice__repl() {
		XophpArray src = fxt.Make().Add(0, "a").Add(1, "b").Add(2, "c").Add(3, "d");
		XophpArray del = XophpArrayUtl.array_splice(src, 1, 2, fxt.Make().Add(0, "x"));
		fxt.Test__eq
		( fxt.Make().Add(0, "a").Add(1, "x").Add(2, "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add(0, "b").Add(1, "c")
		, del
		);
	}
	@Test  public void array_splice__example_1a() {
		XophpArray src = fxt.Make().Add_many("red", "green", "blue", "yellow");
		XophpArray del = XophpArrayUtl.array_splice(src, 2);
		fxt.Test__eq
		( fxt.Make().Add_many("red", "green")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("blue", "yellow")
		, del
		);
	}
	@Test  public void array_splice__example_1b() {
		XophpArray src = fxt.Make().Add_many("red", "green", "blue", "yellow");
		XophpArray del = XophpArrayUtl.array_splice(src, 1, -1);
		fxt.Test__eq
		( fxt.Make().Add_many("red", "yellow")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("green", "blue")
		, del
		);
	}
	@Test  public void array_splice__example_1c() {
		XophpArray src = fxt.Make().Add_many("red", "green", "blue", "yellow");
		XophpArray del = XophpArrayUtl.array_splice(src, 1, 4, XophpArray.New("orange"));
		fxt.Test__eq
		( fxt.Make().Add_many("red", "orange")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("green", "blue", "yellow")
		, del
		);
	}
	@Test  public void array_splice__example_1d() {
		XophpArray src = fxt.Make().Add_many("red", "green", "blue", "yellow");
		XophpArray del = XophpArrayUtl.array_splice(src, -1, 1, XophpArray.New("black", "maroon"));
		fxt.Test__eq
		( fxt.Make().Add_many("red", "green", "blue", "black", "maroon")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("yellow")
		, del
		);
	}
}
class XophpArrayUtl_fxt {
	public XophpArray Make() {return new XophpArray();}
	public void Test__array_merge(XophpArray expd, XophpArray... vals) {
		XophpArray actl = XophpArrayUtl.array_merge(vals);
		Gftest.Eq__str(expd.To_str(), actl.To_str());
	}
	public void Test__eq(XophpArray expd, XophpArray actl) {
		Gftest.Eq__str(expd.To_str(), actl.To_str());
	}
}
