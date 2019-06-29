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
public class Xophp_ary_tst { // REF: http://php.net/manual/en/language.types.array.php
	private final    XophpArray_fxt fxt = new XophpArray_fxt();
	@Test  public void array__kvs() {
		// $array = array("foo" => "bar", "bar" => "foo",);
		fxt.Test__array
			( Xophp_ary.New()
			.   Add("foo", "bar")
			.   Add("bar", "foo")				
			, Xophp_ary_itm.New("foo", "bar")
			, Xophp_ary_itm.New("bar", "foo")
			);
	}
	@Test  public void array__casting() {
		// $array = array(1 => "a", "1" => "b", 1.5 => "c", true => "d",);
		fxt.Test__array
			( Xophp_ary.New()
			.   Add(1   , "a")
			.   Add("1" , "b")
			.   Add(1.5 , "c")
			.   Add(true, "d")				
			, Xophp_ary_itm.New(1, "d"));
	}
	@Test  public void array__mixed() {
		// $array = array("foo" => "bar", "bar" => "foo", 100 => -100, -100 => 100);
		fxt.Test__array
			( Xophp_ary.New()
			.   Add("foo", "bar")
			.   Add("bar", "foo")
			.   Add(100, -100)
			.   Add(-100, 100)
			, Xophp_ary_itm.New("foo", "bar")
			, Xophp_ary_itm.New("bar", "foo")
			, Xophp_ary_itm.New(100, -100)
			, Xophp_ary_itm.New(-100, 100)
			);
	}
	@Test  public void array__objs() {
		// $array = array("foo", "bar", "hello", "world");
		fxt.Test__array
			( Xophp_ary.New()
			.   Add("foo")
			.   Add("bar")
			.   Add("hello")
			.   Add("world")
			, Xophp_ary_itm.New(0, "foo")
			, Xophp_ary_itm.New(1, "bar")
			, Xophp_ary_itm.New(2, "hello")
			, Xophp_ary_itm.New(3, "world")
			);
	}
	@Test  public void array__unkeyed() {
		// $array = array("a", "b", 6 => "c", "d");
		fxt.Test__array
			( Xophp_ary.New()
			.   Add("a")
			.   Add("b")
			.   Add(6, "c")
			.   Add("d")
			, Xophp_ary_itm.New(0, "a")
			, Xophp_ary_itm.New(1, "b")
			, Xophp_ary_itm.New(6, "c")
			, Xophp_ary_itm.New(7, "d")
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
		( Xophp_ary.New()
		.    Add("foo"   , "bar")
		.    Add(42      , 24)
		.    Add("multi" , Xophp_ary.New()
		.        Add("dimensional", Xophp_ary.New()
		.            Add("array", "foo")
		))
		, Xophp_ary_itm.New("foo", "bar")
		, Xophp_ary_itm.New(42, "24")
		, Xophp_ary_itm.New("multi", Xophp_ary.New()
		.       Add("dimensional", Xophp_ary.New()
		.            Add("array", "foo")
		))
		);
	}
	@Test  public void array__unset() {
		Xophp_ary ary = Xophp_ary.New();
		ary.Add(0, "a").Add(1, "b");

		// delete all
		ary.Unset(0);
		ary.Unset(1);
		fxt.Test__array(ary);

		// add new and assert idx is 2
		ary.Add("c");
		fxt.Test__array(ary, Xophp_ary_itm.New(2, "c"));

		ary = ary.Values();
		ary.Add("d");
		fxt.Test__array(ary, Xophp_ary_itm.New(0, "c"), Xophp_ary_itm.New(1, "d"));
	}
}
class XophpArray_fxt {
	public void Test__array(Xophp_ary ary, Xophp_ary_itm... expd) {
		Xophp_ary_itm[] actl = ary.To_ary();
		Gftest.Eq__ary(expd, actl);
	}
	public void Test__unset(Xophp_ary ary, int idx, Xophp_ary_itm... expd) {
		Xophp_ary_itm[] actl = ary.To_ary();
		Gftest.Eq__ary(expd, actl);
	}
}
