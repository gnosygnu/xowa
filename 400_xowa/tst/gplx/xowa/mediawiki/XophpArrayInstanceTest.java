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
package gplx.xowa.mediawiki;

import org.junit.*; import gplx.core.tests.*;
public class XophpArrayInstanceTest { // REF: http://php.net/manual/en/language.types.array.php
	private final XophpArrayfxt fxt = new XophpArrayfxt();
	@Test  public void array__kvs() {
		// $array = array("foo" => "bar", "bar" => "foo",);
		fxt.Test__array
			( XophpArray.New()
			.   Add("foo", "bar")
			.   Add("bar", "foo")				
			, XophpArrayItm.NewStr("foo", "bar")
			, XophpArrayItm.NewStr("bar", "foo")
			);
	}
	@Test  public void array__casting() {
		// $array = array(1 => "a", "1" => "b", 1.5 => "c", true => "d",);
		fxt.Test__array
			( XophpArray.New()
			.   Add(1   , "a")
			.   Add("1" , "b")
			.   Add(1.5 , "c")
			.   Add(true, "d")				
			, XophpArrayItm.NewInt(1, "d"));
	}
	@Test  public void array__mixed() {
		// $array = array("foo" => "bar", "bar" => "foo", 100 => -100, -100 => 100);
		fxt.Test__array
			( XophpArray.New()
			.   Add("foo", "bar")
			.   Add("bar", "foo")
			.   Add(100, -100)
			.   Add(-100, 100)
			, XophpArrayItm.NewStr("foo", "bar")
			, XophpArrayItm.NewStr("bar", "foo")
			, XophpArrayItm.NewInt(100, -100)
			, XophpArrayItm.NewInt(-100, 100)
			);
	}
	@Test  public void array__objs() {
		// $array = array("foo", "bar", "hello", "world");
		fxt.Test__array
			( XophpArray.New()
			.   Add("foo")
			.   Add("bar")
			.   Add("hello")
			.   Add("world")
			, XophpArrayItm.NewInt(0, "foo")
			, XophpArrayItm.NewInt(1, "bar")
			, XophpArrayItm.NewInt(2, "hello")
			, XophpArrayItm.NewInt(3, "world")
			);
	}
	@Test  public void array__unkeyed() {
		// $array = array("a", "b", 6 => "c", "d");
		fxt.Test__array
			( XophpArray.New()
			.   Add("a")
			.   Add("b")
			.   Add(6, "c")
			.   Add("d")
			, XophpArrayItm.NewInt(0, "a")
			, XophpArrayItm.NewInt(1, "b")
			, XophpArrayItm.NewInt(6, "c")
			, XophpArrayItm.NewInt(7, "d")
			);
	}
	@Test  public void array__multidimensional() {
		/*
		$array = array(
			"foo" => "bar",
			42    => 24,
			"multi" => array(
				"dimensional" => array(
					"array" => "foo"
				)
			)
		);
		*/
		fxt.Test__array
		( XophpArray.New()
		.    Add("foo"   , "bar")
		.    Add(42      , 24)
		.    Add("multi" , XophpArray.New()
		.        Add("dimensional", XophpArray.New()
		.            Add("array", "foo")
		))
		, XophpArrayItm.NewStr("foo", "bar")
		, XophpArrayItm.NewInt(42, "24")
		, XophpArrayItm.NewStr("multi", XophpArray.New()
		.       Add("dimensional", XophpArray.New()
		.            Add("array", "foo")
		))
		);
	}
	@Test  public void array__unset() {
		XophpArray ary = XophpArray.New();
		ary.Add(0, "a").Add(1, "b");

		// delete all
		XophpArray.unset(ary, 0);
		XophpArray.unset(ary, 1);
		fxt.Test__array(ary);

		// add new and assert idx is 2
		ary.Add("c");
		fxt.Test__array(ary, XophpArrayItm.NewInt(2, "c"));
	}
	@Test  public void Pop() {
		XophpArray ary = XophpArray.New();
		ary.Add(0, "a").Add(1, "b").Add(2, "c");

		// pop all
		fxt.Test__Pop(ary, "c");
		fxt.Test__Pop(ary, "b");
		fxt.Test__Pop(ary, "a");
		fxt.Test__Count(ary, 0);
		fxt.Test__Pop(ary, null);
	}
	@Test   public void Itm_str_concat_end() {
		XophpArray ary = XophpArray.New();
		ary.Add(0, "a").Add(1, "b").Add(2, "c");

		// pop all
		fxt.Test__Itm_str_concat_end(ary, "a0", 0, "0");
		fxt.Test__Itm_str_concat_end(ary, "b1", 1, "1");
		fxt.Test__Itm_str_concat_end(ary, "c2", 2, "2");
	}
	@Test   public void Clone() {
		XophpArray ary = XophpArray.New();
		ary.Add(0, "a").Add(1, "b").Add(2, "c");

		fxt.Test__Eq(ary, ary.Clone());
	}
	@Test   public void Get_by() {
		XophpArray ary = XophpArray.New();
		ary.Add("0", "a").Add("1", "b").Add("2", "c");

		fxt.Test__Get_by(ary, "0", "a");
		fxt.Test__Get_by(ary, "missing", null);
	}
}
class XophpArrayfxt {
	public void Test__Count(XophpArray ary, int expd) {
		Gftest.Eq__int(expd, ary.Len());
	}
	public void Test__array(XophpArray ary, XophpArrayItm... expd) {
		XophpArrayItm[] actl = ary.To_ary();
		Gftest.Eq__ary(expd, actl);
	}
	public void Test__unset(XophpArray ary, int idx, XophpArrayItm... expd) {
		XophpArrayItm[] actl = ary.To_ary();
		Gftest.Eq__ary(expd, actl);
	}
	public void Test__Pop(XophpArray ary, String expd) {
		String actl = (String)XophpArray.array_pop(ary);
		Gftest.Eq__str(expd, actl);
	}
	public void Test__Itm_str_concat_end(XophpArray ary, String expd, int idx, String v) {
		ary.Itm_str_concat_end(idx, v);
		String actl = ary.Get_at_str(idx);
		Gftest.Eq__str(expd, actl);
	}
	public void Test__Eq(XophpArray lhs, XophpArray rhs) {
		Gftest.Eq__ary(lhs.To_ary(), rhs.To_ary());
	}
	public void Test__Get_by(XophpArray ary, String key, Object expd) {
		Gftest.Eq__obj_or_null(expd, ary.Get_by(key));
	}
}
