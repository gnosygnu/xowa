/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki;

import gplx.objects.primitives.BoolUtl;
import gplx.Err_;
import gplx.Int_;
import gplx.String_;
import gplx.core.tests.Gftest;
import org.junit.Test;

public class XophpArrayStaticTest {
	private final XophpArray_fxt fxt = new XophpArray_fxt();
	// REF.PHP:https://www.php.net/manual/en/function.array-merge.php
	@Test public void array_merge__basic() {
		XophpArray ary1 = fxt.Make().Add("key1", "val1").Add("a");
		XophpArray ary2 = fxt.Make().Add("key2", "val2").Add("b");
		fxt.Test__eq
		( fxt.Make().Add("key1", "val1").Add("a").Add("key2", "val2").Add("b")
		, XophpArray.array_merge(ary1, ary2));
	}
	@Test public void array_merge__same_key() {
		XophpArray ary1 = fxt.Make().Add("key", "val1");
		XophpArray ary2 = fxt.Make().Add("key", "val2");
		fxt.Test__eq
		( fxt.Make().Add("key", "val2")
		, XophpArray.array_merge(ary1, ary2));
	}
	@Test public void array_merge__same_idx() {
		XophpArray ary1 = fxt.Make().Add(0, "a");
		XophpArray ary2 = fxt.Make().Add(0, "b");
		fxt.Test__eq
		( fxt.Make().Add(0, "a").Add(1, "b")
		, XophpArray.array_merge(ary1, ary2));
	}
	@Test public void array_merge__renumber() {
		XophpArray ary1 = fxt.Make().Add(3, "a");
		XophpArray ary2 = fxt.Make().Add(2, "b");
		fxt.Test__eq
		( fxt.Make().Add(0, "a").Add(1, "b")
		, XophpArray.array_merge(ary1, ary2));
	}
	@Test public void array_merge__example_1() {
		XophpArray ary1 = fxt.Make().Add("color", "red").Add_many(2, 4);
		XophpArray ary2 = fxt.Make().Add_many("a", "b").Add("color", "green").Add("shape", "trapezoid").Add(4);
		fxt.Test__eq
		( fxt.Make().Add("color", "green").Add_many(2, 4, "a", "b").Add("shape", "trapezoid").Add(4)
		, XophpArray.array_merge(ary1, ary2));
	}
	@Test public void array_merge__example_2() {
		XophpArray ary1 = fxt.Make();
		XophpArray ary2 = fxt.Make().Add(1, "data");
		fxt.Test__eq
		( fxt.Make().Add(0, "data")
		, XophpArray.array_merge(ary1, ary2));
	}
	@Test public void array_add() {
		XophpArray ary1 = fxt.Make().Add(0, "zero_a").Add(2, "two_a").Add(3, "three_a");
		XophpArray ary2 = fxt.Make().Add(1, "one_b").Add(3, "three_b").Add(4, "four_b");
		fxt.Test__eq
		( fxt.Make().Add(0, "zero_a").Add(2, "two_a").Add(3, "three_a").Add(1, "one_b").Add(4, "four_b")
		, XophpArray.array_add(ary1, ary2));
	}
	@Test public void array_splice__bgn_is_positive() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArray.array_splice(src, 1);
		fxt.Test__eq
		( fxt.Make().Add_many("a")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b", "c", "d")
		, del
		);
	}
	@Test public void array_splice__bgn_is_positive_large() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArray.array_splice(src, 99);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "b", "c", "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many()
		, del
		);
	}
	@Test public void array_splice__bgn_is_negative() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArray.array_splice(src, -3);
		fxt.Test__eq
		( fxt.Make().Add_many("a")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b", "c", "d")
		, del
		);
	}
	@Test public void array_splice__bgn_is_negative_large() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArray.array_splice(src, -99);
		fxt.Test__eq
		( fxt.Make()
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "b", "c", "d")
		, del
		);
	}
	@Test public void array_splice__len_is_positive() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArray.array_splice(src, 1, 2);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b", "c")
		, del
		);
	}
	@Test public void array_splice__len_is_positive_large() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArray.array_splice(src, 1, 99);
		fxt.Test__eq
		( fxt.Make().Add_many("a")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b", "c", "d")
		, del
		);
	}
	@Test public void array_splice__len_is_negative() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArray.array_splice(src, 1, -2);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "c", "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("b")
		, del
		);
	}
	@Test public void array_splice__len_is_negative_large() {
		XophpArray src = fxt.Make().Add_many("a", "b", "c", "d");
		XophpArray del = XophpArray.array_splice(src, 1, -99);
		fxt.Test__eq
		( fxt.Make().Add_many("a", "b", "c", "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make()
		, del
		);
	}
	@Test public void array_splice__repl() {
		XophpArray src = fxt.Make().Add(0, "a").Add(1, "b").Add(2, "c").Add(3, "d");
		XophpArray del = XophpArray.array_splice(src, 1, 2, fxt.Make().Add(0, "x"));
		fxt.Test__eq
		( fxt.Make().Add(0, "a").Add(1, "x").Add(2, "d")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add(0, "b").Add(1, "c")
		, del
		);
	}
	@Test public void array_splice__example_1a() {
		XophpArray src = fxt.Make().Add_many("red", "green", "blue", "yellow");
		XophpArray del = XophpArray.array_splice(src, 2);
		fxt.Test__eq
		( fxt.Make().Add_many("red", "green")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("blue", "yellow")
		, del
		);
	}
	@Test public void array_splice__example_1b() {
		XophpArray src = fxt.Make().Add_many("red", "green", "blue", "yellow");
		XophpArray del = XophpArray.array_splice(src, 1, -1);
		fxt.Test__eq
		( fxt.Make().Add_many("red", "yellow")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("green", "blue")
		, del
		);
	}
	@Test public void array_splice__example_1c() {
		XophpArray src = fxt.Make().Add_many("red", "green", "blue", "yellow");
		XophpArray del = XophpArray.array_splice(src, 1, 4, XophpArray.New("orange"));
		fxt.Test__eq
		( fxt.Make().Add_many("red", "orange")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("green", "blue", "yellow")
		, del
		);
	}
	@Test public void array_splice__example_1d() {
		XophpArray src = fxt.Make().Add_many("red", "green", "blue", "yellow");
		XophpArray del = XophpArray.array_splice(src, -1, 1, XophpArray.New("black", "maroon"));
		fxt.Test__eq
		( fxt.Make().Add_many("red", "green", "blue", "black", "maroon")
		, src
		);
		fxt.Test__eq
		( fxt.Make().Add_many("yellow")
		, del
		);
	}
	@Test public void values() {
		XophpArray orig = fxt.Make().Add("size", "XL").Add("color", "gold");
		fxt.Test__eq
			( fxt.Make().Add(0, "XL").Add(1, "gold")
			, XophpArray.array_values(orig)
			);
	}
	@Test public void array_map() {
		XophpArray orig = fxt.Make().Add_many("a", "b", "c");
		fxt.Test__eq
			( fxt.Make().Add_many("A", "B", "C")
			, XophpArray.array_map(XophpString_.Callback_owner, "strtoupper", orig)
			);
	}
	@Test public void array_flip__basic() {
		XophpArray orig = fxt.Make().Add_many("oranges", "apples", "pears");
		fxt.Test__eq
			( fxt.Make().Add("oranges", 0).Add("apples", 1).Add("pears", 2)
			, XophpArray.array_flip(orig)
			);
	}
	@Test public void array_flip__collision() {
		XophpArray orig = fxt.Make().Add("a", 1).Add("b", 1).Add("c", 2);
		fxt.Test__eq
			( fxt.Make().Add("1", "b").Add("2", "c")
			, XophpArray.array_flip(orig)
			);
	}
	@Test public void implode() {
		XophpArray orig = fxt.Make().Add_many("a", "b", "c");
		Gftest.Eq__str
			( "a b c"
			, XophpArray.implode(" ", orig)
			);
	}
	@Test public void in_array() {
		// PHP examples
		XophpArray array;
		// Example #1
		array = XophpArray.New("Mac", "NT", "Irix", "Linux");
		Gftest.Eq__bool(BoolUtl.Y, XophpArray.in_array("Irix", array));
		Gftest.Eq__bool(BoolUtl.N, XophpArray.in_array("mac" , array));

		// Example #2
		array = XophpArray.New(12.4d, 1.13d);
		Gftest.Eq__bool(BoolUtl.N, XophpArray.in_array("12.4", array, true));
		Gftest.Eq__bool(BoolUtl.Y, XophpArray.in_array( 1.13d, array, true));

		// Example #3
		array = XophpArray.New(XophpArray.New('p', 'h'), XophpArray.New('p', 'r'), 'o');
		Gftest.Eq__bool(BoolUtl.Y, XophpArray.in_array(XophpArray.New('p', 'h'), array));
		Gftest.Eq__bool(BoolUtl.N, XophpArray.in_array(XophpArray.New('f', 'i'), array));
		Gftest.Eq__bool(BoolUtl.Y, XophpArray.in_array('o', array));
	}
	@Test public void array_shift() {
		XophpArray array;
		String shifted;

		// key is int
		array = XophpArray.New("a", "b");
		shifted = (String)XophpArray.array_shift(array);

		Gftest.Eq__str("a", shifted);
		fxt.Test__eq
		( XophpArray.New().Add(0, "b")
		, array
		);

		// key is str and int
		array = XophpArray.New().Add("a", "a").Add(2, "b").Add(5, "c");
		shifted = (String)XophpArray.array_shift(array);

		Gftest.Eq__str("a", shifted);
		fxt.Test__eq
		( XophpArray.New().Add(0, "b").Add(1, "c")
		, array
		);

		// empty
		array = XophpArray.New();
		shifted = (String)XophpArray.array_shift(array);

		Gftest.Eq__bool_y(shifted == null);
		fxt.Test__eq
		( XophpArray.New()
		, array
		);

		// null
		array = null;
		shifted = (String)XophpArray.array_shift(array);

		Gftest.Eq__bool_y(shifted == null);
		Gftest.Eq__bool_y(array == null);

		// PHP examples
		// Example #1
		array = XophpArray.New("orange", "banana", "apple", "strawberry");
		shifted = (String)XophpArray.array_shift(array);

		Gftest.Eq__str("orange", shifted);
		fxt.Test__eq
		( XophpArray.New().Add(0, "banana").Add(1, "apple").Add(2, "strawberry")
		, array
		);
	}
	@Test public void array_filter() {
		// PHP examples
		// Example #1 array_filter() example
		XophpArrayTestCallbackOwner callbackOwner = new XophpArrayTestCallbackOwner();
		XophpArray array;
		array = XophpArray.New().Add("a", 1).Add("b", 2).Add("c", 3).Add("d", 4).Add("e", 5);
		fxt.Test__eq
		( XophpArray.New().Add("a", 1).Add("c", 3).Add("e", 5)
		, XophpArray.array_filter(array, callbackOwner.NewCallback("array_filter_odd"))
		);

		array = XophpArray.New(6, 7, 8, 9, 10, 11, 12);
		fxt.Test__eq
		( XophpArray.New().Add(0, 6).Add(2, 8).Add(4, 10).Add(6, 12)
		, XophpArray.array_filter(array, callbackOwner.NewCallback( "array_filter_even"))
		);

		// Example #2 array_filter() without callback
		array = XophpArray.New().Add(0, "foo").Add(1, false).Add(2, -1).Add(3, null).Add(4, "").Add(5, "0").Add(6, 0);
		fxt.Test__eq
		( XophpArray.New().Add(0, "foo").Add(2, -1)
		, XophpArray.array_filter(array)
		);

		// Example #3 array_filter() with flag
		array = XophpArray.New().Add("a", 1).Add("b", 2).Add("c", 3).Add("d", 4);
		fxt.Test__eq
		( XophpArray.New().Add("b", 2)
		, XophpArray.array_filter(array, callbackOwner.NewCallback("array_filter_key"), XophpArray.ARRAY_FILTER_USE_KEY)
		);
		fxt.Test__eq
		( XophpArray.New().Add("b", 2).Add("d", 4)
		, XophpArray.array_filter(array, callbackOwner.NewCallback("array_filter_both"), XophpArray.ARRAY_FILTER_USE_BOTH)
		);
	}
	@Test public void reset() {
		// PHP examples
		// Example #1 reset() example
		XophpArray array;
		array = XophpArray.New("step one", "step two", "step three", "step four");

		// by default, the pointer is on the first element
		Gftest.Eq__str("step one", (String)XophpArray.current(array));

		// skip two steps
		XophpArray.next(array);
		XophpArray.next(array);
		Gftest.Eq__str("step three", (String)XophpArray.current(array));

		// reset pointer, start again on step one
		XophpArray.reset(array);
		Gftest.Eq__str("step one", (String)XophpArray.current(array));
	}
	@Test public void array_diff() {
		// test To_str
		XophpArray<String> array1 = new XophpArray().Add("a", "0");
		XophpArray<Integer> arrayInt = new XophpArray().Add("a", 0);

		fxt.Test__eq
		( new XophpArray<String>()
		, XophpArray.array_diff(array1, arrayInt)
		);

		// test many
		XophpArray<String> array2, array3, array4;
		array1 = new XophpArray().Add_many("a", "b", "c", "d");
		array2 = new XophpArray().Add_many("a");
		array3 = new XophpArray().Add_many("c");
		array4 = new XophpArray().Add_many("d");

		fxt.Test__eq
		( new XophpArray<String>().Add(1, "b")
		, XophpArray.array_diff(array1, array2, array3, array4)
		);

		// PHP examples
		// Example #1 array_diff() example
		array1 = new XophpArray().Add("a", "green").Add_many("red", "blue", "red");
		array2 = new XophpArray().Add("b", "green").Add_many("yellow", "red");

		fxt.Test__eq
		( new XophpArray<String>().Add(1, "blue")
		, XophpArray.array_diff(array1, array2)
		);
	}
	@Test public void array_diff_key() {
		// PHP examples
		// Example #1 array_diff_key() example
		XophpArray<Integer> array1, array2;
		array1 = new XophpArray<>().Add("blue", 1).Add("red", 2).Add("green", 3).Add("purple", 4);
		array2 = new XophpArray<>().Add("green", 5).Add("yellow", 7).Add("cyan", 8);

		fxt.Test__eq
		( new XophpArray<String>().Add("blue", 1).Add("red", 2).Add("purple", 4)
		, XophpArray.array_diff_key(array1, array2)
		);

		XophpArray<Integer> array3;
		array1 = new XophpArray<>().Add("blue", 1).Add("red", 2).Add("green", 3).Add("purple", 4);
		array2 = new XophpArray<>().Add("green", 5).Add("yellow", 7).Add("cyan", 8);
		array3 = new XophpArray<>().Add("blue", 6).Add("yellow", 7).Add("mauve", 8);

		fxt.Test__eq
		( new XophpArray<String>().Add("red", 2).Add("purple", 4)
		, XophpArray.array_diff_key(array1, array2, array3)
		);
	}
}
class XophpArray_fxt {
	public XophpArray Make() {return new XophpArray();}
	public void Test__eq(XophpArray expd, XophpArray actl) {
		Gftest.Eq__str(expd.To_str(), actl.To_str());
	}
}
class XophpArrayTestCallbackOwner implements XophpCallbackOwner {
	public XophpCallback NewCallback(String method) {return new XophpCallback(this, method);}
	public Object Call(String method, Object... args) {
		switch (method) {
			case "array_filter_even":
				return Int_.Cast(args[0]) % 2 == 0;
			case "array_filter_odd":
				return Int_.Cast(args[0]) % 2 == 1;
			case "array_filter_key":
				return String_.cast(args[0]).equals("b");
			case "array_filter_both":
				return String_.cast(args[0]).equals("b") || Int_.Cast(args[1]) == 4;
			default:
				throw Err_.new_unhandled_default(method);
		}
	}
}