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
package gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.vendor.*; import gplx.xowa.mediawiki.vendor.wikimedia.*; import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.*;
import org.junit.*; import gplx.core.tests.*;
public class XomwEvaluator_tst {
	// REF: https://unicode.org/reports/tr35/tr35-numbers.html#Language_Plural_Rules
	private final    XomwEvaluator_fxt fxt = new XomwEvaluator_fxt();
	@Test  public void Rule__n() { // "absolute value of the source number (integer and decimals)."
		fxt.Init__rule("n = 1");
		fxt.Test__match__y("1", "-1");
		fxt.Test__match__y("1.0", "-1.0"); // not sure if this is correct, but "'n' => floatval( $absValStr )", and "echo(floatval("1.00"));" -> "1"
		fxt.Test__match__n("2", "1.1");
	}
	@Test  public void Rule__i() { // "integer digits of n."
		fxt.Init__rule("i = 1");
		fxt.Test__match__y("1");
		fxt.Test__match__n("0", "2");
	}
	@Test  public void Rule__v() { // "number of visible fraction digits in n, with trailing zeros."
		fxt.Init__rule("v = 1");
		fxt.Test__match__y("2.3");
		fxt.Test__match__n("2", "2.30");
	}
	@Test  public void Rule__w() { // "number of visible fraction digits in n, without trailing zeros."
		fxt.Init__rule("w = 1");
		fxt.Test__match__y("2.30", "2.3");
		fxt.Test__match__n("2");
	}
	@Test  public void Rule__f() { // "visible fractional digits in n, with trailing zeros."
		fxt.Init__rule("f = 1");
		fxt.Test__match__y("2.1");
		fxt.Test__match__n("2", "2.10");
	}
	@Test  public void Rule__t() { // "visible fractional digits in n, without trailing zeros."
		fxt.Init__rule("t = 1");
		fxt.Test__match__y("2.1", "2.10");
		fxt.Test__match__n("2");
	}
	@Test  public void Rule__sample() { // MW ignores samples
		fxt.Init__rule("n = 1 @integer f = 1"); // embed fake rule for "1.1" after @integer
		fxt.Test__match__y("1");
	}
	@Test   public void Lang__0() {
		// NOTE: never parse "other" rule; "// Don't record "other" rules, which have an empty condition"; REF.MW:/includes/cache/localization/LocalizationCache.php
		// fxt.Init__rule("@integer 0~15, 100, 1000, 10000, 100000, 1000000, … @decimal 0.0~1.5, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, …");
		fxt.Init__rule();
		fxt.Test__match(0, "1", "1.2", "-1"); // basically, anything
	}
	@Test   public void Lang__1__am() {
		fxt.Init__rule("i = 0 or n = 1 @integer 0, 1 @decimal 0.0~1.0, 0.00~0.04");
		fxt.Test__match(0, "0", "0.1", "-0.1", "1", "-1");
		fxt.Test__match(1, "2", "1.1", "-1.1");
	}
	@Test   public void Lang__1__ff__fr() {
		fxt.Init__rule("i = 0,1 @integer 0, 1 @decimal 0.0~1.5");
		fxt.Test__match(0, "0", "0.1", "1", "1.0", "1.9");
		fxt.Test__match(1, "2");
	}
	@Test   public void Lang__1__ast__de__fr() {
		fxt.Init__rule("i = 1 and v = 0 @integer 1");
		fxt.Test__match(0, "1", "-1");
		fxt.Test__match(1, "1.1", "-1.1", "2");
	}
	@Test   public void Lang__1__si() {
		fxt.Init__rule("n = 0,1 or i = 0 and f = 1 @integer 0, 1 @decimal 0.0, 0.1, 1.0, 0.00, 0.01, 1.00, 0.000, 0.001, 1.000, 0.0000, 0.0001, 1.0000");
		fxt.Test__match(0, "0", "1", "-1", "0.1", "-0.1");
		fxt.Test__match(1, "1.1", "0.11", "2");
	}
	@Test   public void Lang__1__ak__bh() {
		fxt.Init__rule("n = 0..1 @integer 0, 1 @decimal 0.0, 1.0, 0.00, 1.00, 0.000, 1.000, 0.0000, 1.0000");
		fxt.Test__match(0, "0", "1", "-1");
		fxt.Test__match(1, "0.123", "-0.123", "1.1", "2");
	}
	@Test   public void Lang__1__tzm() {
		fxt.Init__rule("n = 0..1 or n = 11..99 @integer 0, 1, 11~24 @decimal 0.0, 1.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0");
		fxt.Test__match(0, "0", "1", "11", "21", "99");
		fxt.Test__match(1, "0.123", "-0.123", "1.1", "2", "11.1", "99.1");
	}
	@Test   public void Lang__1__pt() {
		fxt.Init__rule("n = 0..2 and n != 2 @integer 0, 1 @decimal 0.0, 1.0, 0.00, 1.00, 0.000, 1.000, 0.0000, 1.0000");
		fxt.Test__match(0, "0", "1", "0.0", "1.0", "-1");
		fxt.Test__match(1, "2", "1.1", "-2");
	}
	@Test   public void Lang__1__da() {
		fxt.Init__rule("n = 1 or t != 0 and i = 0,1 @integer 1 @decimal 0.1~1.6");
		fxt.Test__match(0, "0.2", "1", "-1", "1.2");
		fxt.Test__match(1, "0", "2");
	}
	@Test   public void Lang__1__is() {
		fxt.Init__rule("t = 0 and i % 10 = 1 and i % 100 != 11 or t != 0 @integer 1, 21, 31, 41, 51, 61, 71, 81, 101, 1001, … @decimal 0.1~1.6, 10.1, 100.1, 1000.1, …");
		fxt.Test__match(0, "1", "21", "101", "0.1", "1.1", "10.1");
		fxt.Test__match(1, "0", "2", "11", "100", "0.0", "10", "10.0");
	}
	@Test   public void Lang__3__he__iw() {
		fxt.Init__rule
			( "i = 1 and v = 0 @integer 1"
			, "i = 2 and v = 0 @integer 2"
			, "v = 0 and n != 0..10 and n % 10 = 0 @integer 20, 30, 40, 50, 60, 70, 80, 90, 100, 1000, 10000, 100000, 1000000, …"
			);
		fxt.Test__match(0, "1", "-1");
		fxt.Test__match(1, "2", "-2");
		fxt.Test__match(2, "20", "30", "100", "110", "1000");
		fxt.Test__match(3
			, "1.2", "-1.2"
			, "2.3", "-2.3"
			, "3", "9", "-3", "11", "19", "101"
			);
	}
	@Test   public void Lang__4__br() {
		fxt.Init__rule
			( "n % 10 = 1 and n % 100 != 11,71,91 @integer 1, 21, 31, 41, 51, 61, 81, 101, 1001, … @decimal 1.0, 21.0, 31.0, 41.0, 51.0, 61.0, 81.0, 101.0, 1001.0, …"
			, "n % 10 = 2 and n % 100 != 12,72,92 @integer 2, 22, 32, 42, 52, 62, 82, 102, 1002, … @decimal 2.0, 22.0, 32.0, 42.0, 52.0, 62.0, 82.0, 102.0, 1002.0, …"
			, "n % 10 = 3..4,9 and n % 100 != 10..19,70..79,90..99 @integer 3, 4, 9, 23, 24, 29, 33, 34, 39, 43, 44, 49, 103, 1003, … @decimal 3.0, 4.0, 9.0, 23.0, 24.0, 29.0, 33.0, 34.0, 103.0, 1003.0, …"
			, "n != 0 and n % 1000000 = 0 @integer 1000000, … @decimal 1000000.0, 1000000.00, 1000000.000, …"
			);
		fxt.Test__match(0, "1", "21", "1.0", "21.0", "-1");
		fxt.Test__match(1, "2", "22", "2.0", "22.0", "-2");
		fxt.Test__match(2, "3", "4", "9", "23", "103", "3.0", "103.0", "-3");
		fxt.Test__match(3, "1000000", "1000000.0");
		fxt.Test__match(4
			, "1.1", "11"
			, "2.1"
			, "3.1", "5", "6", "7", "8", "10", "19", "70", "79"
			, "1000000.1" // NOTE: fails in C#
			, "60"
			);
	}
	@Test   public void Lang__5__ar() {
		fxt.Init__rule
			( "n = 0 @integer 0 @decimal 0.0, 0.00, 0.000, 0.0000"
			, "n = 1 @integer 1 @decimal 1.0, 1.00, 1.000, 1.0000"
			, "n = 2 @integer 2 @decimal 2.0, 2.00, 2.000, 2.0000"
			, "n % 100 = 3..10 @integer 3~10, 103~110, 1003, … @decimal 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 103.0, 1003.0, …"
			, "n % 100 = 11..99 @integer 11~26, 111, 1011, … @decimal 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 111.0, 1011.0, …"
			);
		fxt.Test__match(0, "0", "0.00");
		fxt.Test__match(1, "1", "-1", "1.0");
		fxt.Test__match(2, "2", "-2", "2.0");
		fxt.Test__match(3, "3", "4", "10", "103", "1003", "-3", "3.0");
		fxt.Test__match(4, "11", "99", "111", "1011", "-11", "11.0");
		fxt.Test__match(5
			, "0.1", "-0.1"
			, "1.1", "-1.1"
			, "2.1", "-2.1"
			, "3.1", "10.1"
			, "100", "102", "200", "1000"
			);
	}
}
class XomwEvaluator_fxt {
	private final    XophpArray rules = XophpArray.New();
	public void Init__rule(String... ary) {
		rules.Clear();
		for (String itm : ary)
			rules.Add(itm);
	}
	public void Test__match__y(String... ary) {Test__match(0, ary);}
	public void Test__match__n(String... ary) {Test__match(1, ary);}
	public void Test__match(int expd, String... ary) {
		for (String itm : ary) {
			int actl = XomwEvaluator.evaluate(itm, rules);
			Gftest.Eq__int(expd, actl, itm);
		}
	}
}
