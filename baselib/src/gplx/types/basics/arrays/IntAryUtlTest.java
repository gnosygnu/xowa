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
package gplx.types.basics.arrays;
import gplx.types.basics.utls.BryUtl;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.IntUtl;
import org.junit.Test;
public class IntAryUtlTest {
	private final IntAryTstr tstr = new IntAryTstr();
	@Test public void Parse() {
		tstr.TestParse("1,2,3"                        , 3, IntAryUtl.Empty,   1,   2,   3);
		tstr.TestParse("123,321,213"                  , 3, IntAryUtl.Empty, 123, 321, 213);
		tstr.TestParse(" 1,  2,3"                     , 3, IntAryUtl.Empty,   1,   2,   3);
		tstr.TestParse("-1,+2,-3"                     , 3, IntAryUtl.Empty,  -1,   2,  -3);
		tstr.TestParse(IntUtl.ToStr(IntUtl.MinValue)    , 1, IntAryUtl.Empty, IntUtl.MinValue);
		tstr.TestParse(IntUtl.ToStr(IntUtl.MaxValue)    , 1, IntAryUtl.Empty, IntUtl.MaxValue);
		tstr.TestParse("1,2"                          , 1, IntAryUtl.Empty);
		tstr.TestParse("1"                            , 2, IntAryUtl.Empty);
		tstr.TestParse("a"                            , 1, IntAryUtl.Empty);
		tstr.TestParse("1-2,"                         , 1, IntAryUtl.Empty);
	}
	@Test public void ParseOr() {
		tstr.TestParseOr("1", 1);
		tstr.TestParseOr("123", 123);
		tstr.TestParseOr("1,2,123", 1, 2, 123);
		tstr.TestParseOr("1,2,12,123", 1, 2, 12, 123);
		tstr.TestParseOr("1-5", 1, 2, 3, 4, 5);
		tstr.TestParseOr("1-1", 1);
		tstr.TestParseOr("1-3,7,11-13,21", 1, 2, 3, 7, 11, 12, 13, 21);

		tstr.TestParseOrEmpty("1 2");            // NOTE: MW would gen 12; treat as invalid
		tstr.TestParseOrEmpty("1,");            // eos
		tstr.TestParseOrEmpty("1,,2");            // empty comma
		tstr.TestParseOrEmpty("1-");            // eos
		tstr.TestParseOrEmpty("3-1");            // bgn > end
		tstr.TestParseOrEmpty("1,a,2");
		tstr.TestParseOrEmpty("a-1,2");
		tstr.TestParseOrEmpty("-1");            // no rng bgn
	}
}
class IntAryTstr {
	public void TestParse(String raw, int reqd_len, int[] or, int... expd) {GfoTstr.EqAry(expd, IntAryUtl.Parse(raw, reqd_len, or), "failed to parse: {0}", raw);}
	public void TestParseOrEmpty(String raw)         {GfoTstr.EqAry(IntAryUtl.Empty, IntAryUtl.ParseOr(BryUtl.NewA7(raw), IntAryUtl.Empty));}
	public void TestParseOr(String raw, int... expd) {GfoTstr.EqAry(expd        , IntAryUtl.ParseOr(BryUtl.NewA7(raw), IntAryUtl.Empty));}
}
