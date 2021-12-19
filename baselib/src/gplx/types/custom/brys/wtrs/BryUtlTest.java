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
package gplx.types.custom.brys.wtrs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.commons.GfoDateUtl;
import gplx.types.custom.brys.BrySplit;
import org.junit.Test;
public class BryUtlTest {
	private final BryUtlTstr tstr = new BryUtlTstr();
	@Test public void NewA7() {
		tstr.TestNewA7("a"      , BryUtl.NewByInts(97));                // one
		tstr.TestNewA7("abc"    , BryUtl.NewByInts(97, 98, 99));        // many
		tstr.TestNewA7(""       , BryUtl.Empty);                        // none
		tstr.TestNewA7("¢€𤭢"   , BryUtl.NewByInts(63, 63, 63, 63));    // non-ascii -> ?
	}
	@Test public void NewU8() {
		tstr.TestNewU8("a"      , BryUtl.NewByInts(97));                        // one
		tstr.TestNewU8("abc"    , BryUtl.NewByInts(97, 98, 99));                // many
		tstr.TestNewU8("¢"      , BryUtl.NewByInts(194, 162));                  // bry_len=2; cent
		tstr.TestNewU8("€"      , BryUtl.NewByInts(226, 130, 172));             // bry_len=3; euro
		tstr.TestNewU8("𤭢"     , BryUtl.NewByInts(240, 164, 173, 162));        // bry_len=4; example from en.w:UTF-8
	}
	@Test public void AddByte() {
		tstr.TestAddBryByte("a"          , AsciiByte.Pipe        , "a|");          // basic
		tstr.TestAddBryByte(""           , AsciiByte.Pipe        , "|");           // empty String
	}
	@Test public void AddBry() {
		tstr.TestAddByteBry(AsciiByte.Pipe  , "a"           , "|a");            // basic
		tstr.TestAddByteBry(AsciiByte.Pipe  , ""            , "|");             // empty String
	}
	@Test public void AddWithDlmByte() {
		tstr.TestAddWithDlmByte(AsciiByte.Pipe, StringUtl.Ary("a", "b", "c")    , "a|b|c");                    // basic
		tstr.TestAddWithDlmByte(AsciiByte.Pipe, StringUtl.Ary("a")                , "a");                    // one item
		tstr.TestAddWithDlmByte(AsciiByte.Pipe, StringUtl.Ary("a", null, "c")    , "a||c");                    // null
	}
	@Test public void AddWithDlmBry() {
		tstr.TestAddWithDlmBry("<>", StringUtl.Ary("a","b","c"), "a<>b<>c");
	}
	@Test public void MidByPos() {
		tstr.TestMid("abcba", 0, 1, "a");
		tstr.TestMid("abcba", 0, 2, "ab");
		tstr.TestMid("abcba", 1, 4, "bcb");
	}
	@Test public void ReplaceOne() {
		tstr.TestReplaceOne("a"        , "b"    , "c"    , "a");
		tstr.TestReplaceOne("b"        , "b"    , "c"    , "c");
		tstr.TestReplaceOne("bb"        , "b"    , "c"    , "cb");
		tstr.TestReplaceOne("abcd"    , "bc"    , ""    , "ad");
		tstr.TestReplaceOne("abcd"    , "b"    , "ee"    , "aeecd");
	}
	@Test public void ToA7BryByInt() {
		tstr.TestToA7BryByInt(0, 0);
		tstr.TestToA7BryByInt(9, 9);
		tstr.TestToA7BryByInt(10, 1, 0);
		tstr.TestToA7BryByInt(321, 3, 2, 1);
		tstr.TestToA7BryByInt(-321, BryUtl.ByteNegSign, 3, 2, 1);
		tstr.TestToA7BryByInt(IntUtl.MaxValue, 2,1,4,7,4,8,3,6,4,7);
	}
	@Test public void HasAtEnd() {
		tstr.TestHasAtEnd("a|bcd|e", "d"    , 2, 5, true);        // y_basic
		tstr.TestHasAtEnd("a|bcd|e", "bcd"    , 2, 5, true);        // y_many
		tstr.TestHasAtEnd("a|bcd|e", "|bcd"    , 2, 5, false);        // n_long
		tstr.TestHasAtEnd("a|bcd|e", "|bc"    , 2, 5, false);        // n_pos
		tstr.TestHasAtEnd("abc", "bc", true);        // y
		tstr.TestHasAtEnd("abc", "bd", false);        // n
		tstr.TestHasAtEnd("a", "ab", false);        // exceeds_len
	}
	@Test public void Has_at_bgn() {
		tstr.TestHasAtBgn("y_basic"    , "a|bcd|e", "b"    , 2, 5, true);
		tstr.TestHasAtBgn("y_many"    , "a|bcd|e", "bcd"    , 2, 5, true);
		tstr.TestHasAtBgn("n_long"    , "a|bcd|e", "bcde"    , 2, 5, false);
		tstr.TestHasAtBgn("n_pos"    , "a|bcd|e", "|bc"    , 2, 5, false);
	}
	@Test public void Match() {
		tstr.TestMatch("abc", 0, "abc", true);
		tstr.TestMatch("abc", 2,  "c", true);
		tstr.TestMatch("abc", 0, "cde", false);
		tstr.TestMatch("abc", 2, "abc", false);    // bounds check
		tstr.TestMatch("abc", 0, "abcd", false);
		tstr.TestMatch("a"  , 0, "", false);
		tstr.TestMatch(""  , 0, "a", false);
		tstr.TestMatch(""  , 0, "", true);
		tstr.TestMatch("ab", 0, "a", false);    // FIX: "ab" should not match "a" b/c .length is different
	}
	@Test public void ReadCsvStr() {
		tstr.TestReadCsvStr("a|"       , "a");
		tstr.TestReadCsvStr("|a|", 1 , "a");
		IntRef bgn = IntRef.NewZero(); tstr.TestReadCsvStr("a|b|c|", bgn, "a"); tstr.TestReadCsvStr("a|b|c|", bgn, "b"); tstr.TestReadCsvStr("a|b|c|", bgn, "c");
		tstr.TestReadCsvStr("|", "");
		tstr.TestReadCsvStrFail("a");

		tstr.TestReadCsvStr("'a'|"        , "a");
		tstr.TestReadCsvStr("'a''b'|"    , "a'b");
		tstr.TestReadCsvStr("'a|b'|"        , "a|b");
		tstr.TestReadCsvStr("''|", "");
		tstr.TestReadCsvStrFail("''");
		tstr.TestReadCsvStrFail("'a'b'");
		tstr.TestReadCsvStrFail("'a");
		tstr.TestReadCsvStrFail("'a|");
		tstr.TestReadCsvStrFail("'a'");
	}
	@Test public void XtoIntBy4Bytes() {    // test len=1, 2, 3, 4
		tstr.TestToIntByA7(32, (byte)32);            // space
		tstr.TestToIntByA7(8707, (byte)34, (byte)3);    // &exist;
		tstr.TestToIntByA7(6382179, AsciiByte.Ltr_a, AsciiByte.Ltr_b, AsciiByte.Ltr_c);
		tstr.TestToIntByA7(1633837924, AsciiByte.Ltr_a, AsciiByte.Ltr_b, AsciiByte.Ltr_c, AsciiByte.Ltr_d);
	}
	@Test public void XtoInt() {
		tstr.TestToInt("1", 1);
		tstr.TestToInt("123", 123);
		tstr.TestToIntOr("a", IntUtl.MinValue, IntUtl.MinValue);
		tstr.TestToIntOr("-1", IntUtl.MinValue, -1);
		tstr.TestToIntOr("-123", IntUtl.MinValue, -123);
		tstr.TestToIntOr("123-1", IntUtl.MinValue, IntUtl.MinValue);
		tstr.TestToIntOr("+123", IntUtl.MinValue, 123);
		tstr.TestToInt("", -1);
	}
	@Test public void ReadCsvDte() {
		tstr.TestReadCsvDte("20110801 221435.987");
	}
	@Test public void ReadCsvInt() {
		tstr.TestReadCsvInt("1234567890");
	}
	@Test public void Trim() {
		tstr.TestTrim("a b c", 1, 4, "b");
		tstr.TestTrim("a  c", 1, 3, "");
		tstr.TestTrim("  ", 0, 2, "");
	}
	@Test public void Xto_int_lax() {
		tstr.TestToIntOrLax("12a", 12);
		tstr.TestToIntOrLax("1", 1);
		tstr.TestToIntOrLax("123", 123);
		tstr.TestToIntOrLax("a", 0);
		tstr.TestToIntOrLax("-1", -1);
	}
	@Test public void To_int_or__trim_ws() {
		tstr.TestToIntOrTrimWs("123 "    , 123);
		tstr.TestToIntOrTrimWs(" 123"    , 123);
		tstr.TestToIntOrTrimWs(" 123 "    , 123);
		tstr.TestToIntOrTrimWs(" 1 3 "    , -1);
	}
	@Test public void Compare() {
		tstr.TestCompare("abcde", 0, 1, "abcde", 0, 1, CompareAbleUtl.Same);
		tstr.TestCompare("abcde", 0, 1, "abcde", 1, 2, CompareAbleUtl.Less);
		tstr.TestCompare("abcde", 1, 2, "abcde", 0, 1, CompareAbleUtl.More);
		tstr.TestCompare("abcde", 0, 1, "abcde", 0, 2, CompareAbleUtl.Less);
		tstr.TestCompare("abcde", 0, 2, "abcde", 0, 1, CompareAbleUtl.More);
		tstr.TestCompare("abcde", 2, 3, "abçde", 2, 3, CompareAbleUtl.Less);
	}
	@Test public void Increment_last() {
		tstr.TestIncrementLast(tstr.NewAryInt(0), tstr.NewAryInt(1));
		tstr.TestIncrementLast(tstr.NewAryInt(0, 255), tstr.NewAryInt(1, 0));
		tstr.TestIncrementLast(tstr.NewAryInt(104, 111, 112, 101), tstr.NewAryInt(104, 111, 112, 102));
	}
	@Test public void Replace_between() {
		tstr.TestReplaceBetween("a[0]b"                    , "[", "]", "0", "a0b");
		tstr.TestReplaceBetween("a[0]b[1]c"                , "[", "]", "0", "a0b0c");
		tstr.TestReplaceBetween("a[0b"                    , "[", "]", "0", "a[0b");
	}
	@Test public void Replace() {
		BryWtr tmp_bfr = BryWtr.New();
		tstr.TestReplace(tmp_bfr, "a0b"                    ,    "0", "00", "a00b");        // 1 -> 1
		tstr.TestReplace(tmp_bfr, "a0b0c"                ,    "0", "00", "a00b00c");        // 1 -> 2
		tstr.TestReplace(tmp_bfr, "a00b00c"                ,   "00",  "0", "a0b0c");        // 2 -> 1
		tstr.TestReplace(tmp_bfr, "a0b0"                    ,    "0", "00", "a00b00");        // 1 -> 2; EOS
		tstr.TestReplace(tmp_bfr, "a00b00"                ,   "00",  "0", "a0b0");        // 2 -> 1; EOS
		tstr.TestReplace(tmp_bfr, "a0b0"                    ,    "1",  "2", "a0b0");        // no match
		tstr.TestReplace(tmp_bfr, "a0b0"                    ,   "b1", "b2", "a0b0");        // false match; EOS
	}
	@Test public void SplitBry() {
		tstr.TestSplitBry("a|b|c|"        , "|"    , StringUtl.Ary("a", "b", "c"));
		tstr.TestSplitBry("a|"            , "|"    , StringUtl.Ary("a"));
	}
	@Test public void SplitLines() {
		tstr.TestSplitLines("a\nb"        , "a", "b");                    // basic
		tstr.TestSplitLines("a\nb\n"      , "a", "b");                    // do not create empty trailing lines
		tstr.TestSplitLines("a\r\nb"      , "a", "b");                    // crlf
		tstr.TestSplitLines("a\rb"        , "a", "b");                    // cr only
	}
	@Test public void Match_bwd_any() {
		tstr.TestMatchBwdAny("abc", 2, 0, "c", true);
		tstr.TestMatchBwdAny("abc", 2, 0, "b", false);
		tstr.TestMatchBwdAny("abc", 2, 0, "bc", true);
		tstr.TestMatchBwdAny("abc", 2, 0, "abc", true);
		tstr.TestMatchBwdAny("abc", 2, 0, "zabc", false);
		tstr.TestMatchBwdAny("abc", 1, 0, "ab", true);
	}
	@Test public void Trim_bgn() {
		tstr.TestTrimBgn(" a"        , AsciiByte.Space, "a");    // trim.one
		tstr.TestTrimBgn("   a"    , AsciiByte.Space, "a");    // trim.many
		tstr.TestTrimBgn("a"        , AsciiByte.Space, "a");    // trim.none
		tstr.TestTrimBgn(""        , AsciiByte.Space, "");    // empty
	}
	@Test public void Trim_end() {
		tstr.TestTrimEnd("a "        , AsciiByte.Space, "a");    // trim.one
		tstr.TestTrimEnd("a   "    , AsciiByte.Space, "a");    // trim.many
		tstr.TestTrimEnd("a"        , AsciiByte.Space, "a");    // trim.none
		tstr.TestTrimEnd(""        , AsciiByte.Space, "");    // empty
	}
	@Test public void Mid_w_trim() {
		tstr.TestMidWithTrim("abc", "abc");                                // no ws
		tstr.TestMidWithTrim(" a b c ", "a b c");                        // ws at bgn and end
		tstr.TestMidWithTrim("\r\n\t a\r\n\t b \r\n\t ", "a\r\n\t b");    // space at bgn and end
		tstr.TestMidWithTrim("", "");                                    // handle 0 bytes
		tstr.TestMidWithTrim("   ", "");                                    // handle all ws
	}
	@Test public void New_u8_nl_apos() {
		tstr.TestNewU8NlSwapApos(StringUtl.Ary("a"), "a");
		tstr.TestNewU8NlSwapApos(StringUtl.Ary("a", "b"), "a\nb");
		tstr.TestNewU8NlSwapApos(StringUtl.Ary("a", "b'c", "d"), "a\nb\"c\nd");
	}
	@Test public void Repeat_bry() {
		tstr.TestRepeatBry("abc"  , 3, "abcabcabc");
	}
	@Test public void Xcase__build__all() {
		tstr.TestXcaseBuildAll(BoolUtl.N, "abc", "abc");
		tstr.TestXcaseBuildAll(BoolUtl.N, "aBc", "abc");
	}
}
class BryUtlTstr {
	private final BryWtr tmp = BryWtr.New();
	public void TestNewU8(String raw, byte[] expd)            {GfoTstr.Eq(expd, BryUtl.NewU8(raw));}
	public void TestNewA7(String raw, byte[] expd)            {GfoTstr.Eq(expd, BryUtl.NewA7(raw));}
	public void TestTrimEnd(String raw, byte trim, String expd) {
		byte[] rawBry = BryUtl.NewA7(raw);
		GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.TrimEnd(rawBry, trim, rawBry.length)));
	}
	public void TestTrimBgn(String raw, byte trim, String expd) {
		byte[] rawBry = BryUtl.NewA7(raw);
		GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.TrimBgn(rawBry, trim, 0)));
	}
	public void TestAddBryByte(String s, byte b, String expd)             {GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.Add(BryUtl.NewU8(s), b)));}
	public void TestAddByteBry(byte b, String s, String expd)             {GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.Add(b, BryUtl.NewU8(s))));}
	public void TestAddWithDlmByte(byte dlm, String[] itms, String expd)  {GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.AddWithDlm(dlm, BryUtl.Ary(itms))));}
	public void TestAddWithDlmBry(String dlm, String[] itms, String expd) {GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.AddWithDlm(BryUtl.NewU8(dlm), BryUtl.Ary(itms))));}
	public void TestMidWithTrim(String src, String expd) {byte[] bry = BryUtl.NewU8(src); GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.MidWithTrim(bry, 0, bry.length)));}
	public void TestNewU8NlSwapApos(String[] ary, String expd) {
		GfoTstr.EqLines(expd, StringUtl.NewU8(BryUtlByWtr.NewU8NlSwapApos(ary)));
	}
	public void TestRepeatBry(String s, int count, String expd) {
		GfoTstr.Eq(expd, BryUtl.RepeatBry(BryUtl.NewU8(s), count));
	}
	public void TestXcaseBuildAll(boolean upper, String src, String expd) {
		GfoTstr.Eq(expd, BryUtlByWtr.XcaseBuildAll(tmp, upper, BryUtl.NewU8(src)));
	}
	public void TestMid(String src, int bgn, int end, String expd) {GfoTstr.Eq(expd, StringUtl.NewU8(BryLni.Mid(BryUtl.NewU8(src), bgn, end)));}
	public void TestReplaceOne(String src, String find, String repl, String expd) {GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.ReplaceOne(BryUtl.NewU8(src), BryUtl.NewU8(find), BryUtl.NewU8(repl))));}
	public void TestToA7BryByInt(int val, int... expdAryAsInt) {
		byte[] expd = new byte[expdAryAsInt.length];
		for (int i = 0; i < expd.length; i++) {
			int expdInt = expdAryAsInt[i];
			expd[i] = expdInt == BryUtl.ByteNegSign ? BryUtl.ByteNegSign : AsciiByte.ToA7Str(expdAryAsInt[i]);
		}
		GfoTstr.Eq(expd, BryUtl.ToA7Bry(val, IntUtl.CountDigits(val)));
	}
	public void TestHasAtEnd(String src, String find, int bgn, int end, boolean expd) {GfoTstr.Eq(expd, BryUtl.HasAtEnd(BryUtl.NewU8(src), BryUtl.NewU8(find), bgn, end));}
	public void TestHasAtEnd(String src, String find, boolean expd) {GfoTstr.Eq(expd, BryUtl.HasAtEnd(BryUtl.NewU8(src), BryUtl.NewU8(find)));}
	public void TestHasAtBgn(String tst, String src, String find, int bgn, int end, boolean expd) {GfoTstr.Eq(expd, BryUtl.HasAtBgn(BryUtl.NewU8(src), BryUtl.NewU8(find), bgn, end), tst);}
	public void TestMatch(String src, int srcPos, String find, boolean expd) {GfoTstr.Eq(expd, BryLni.Eq(BryUtl.NewU8(src), srcPos, BryUtl.NewU8(find)));}
	public void TestReadCsvDte(String raw) {GfoTstr.Eq(GfoDateUtl.ParseFmt(raw, BryUtl.FmtCsvDte), BryUtlByWtr.ReadCsvDte(BryUtl.NewU8(raw + "|"), IntRef.NewZero(), (byte)'|'));}
	public void TestReadCsvInt(String raw) {GfoTstr.Eq(IntUtl.Parse(raw), BryUtlByWtr.ReadCsvInt(BryUtl.NewU8(raw + "|"), IntRef.NewZero(), (byte)'|'));}
	public void TestTrim(String raw, int bgn, int end, String expd) {GfoTstr.Eq(expd, StringUtl.NewU8(BryUtl.Trim(BryUtl.NewU8(raw), bgn, end)));}
	public void TestToIntOrTrimWs(String val, int expd) {GfoTstr.Eq(expd, BryUtl.ToIntOrTrimWs(BryUtl.NewU8(val), 0, StringUtl.Len(val), -1));}
	public void TestToIntOrLax(String val, int expd)    {GfoTstr.Eq(expd, BryUtl.ToIntOrLax(BryUtl.NewU8(val), 0, StringUtl.Len(val), 0));}
	public void TestCompare(String lhs, int lhs_bgn, int lhs_end, String rhs, int rhs_bgn, int rhs_end, int expd) {GfoTstr.Eq(expd, BryUtl.Compare(BryUtl.NewU8(lhs), lhs_bgn, lhs_end, BryUtl.NewU8(rhs), rhs_bgn, rhs_end));}
	public byte[] NewAryInt(int... ary) {
		byte[] rv = new byte[ary.length];
		for (int i = 0; i < ary.length; i++)
			rv[i] = ByteUtl.ByInt(ary[i]);
		return rv;
	}
	public void TestIncrementLast(byte[] ary, byte[] expd) {GfoTstr.EqAry(expd, BryUtl.IncrementLast(BryUtl.Copy(ary)));}
	public void TestReplaceBetween(String src, String bgn, String end, String repl, String expd) {GfoTstr.Eq(expd, StringUtl.NewA7(BryUtlByWtr.ReplaceBetween(BryUtl.NewA7(src), BryUtl.NewA7(bgn), BryUtl.NewA7(end), BryUtl.NewA7(repl))));}
	public void TestReplace(BryWtr tmp_bfr, String src, String bgn, String repl, String expd) {
		GfoTstr.Eq(expd, StringUtl.NewA7(BryUtlByWtr.Replace(tmp_bfr, BryUtl.NewA7(src), BryUtl.NewA7(bgn), BryUtl.NewA7(repl))));
	}
	public void TestSplitBry(String src, String dlm, String[] expd) {
		String[] actl = StringUtl.Ary(BrySplit.Split(BryUtl.NewA7(src), BryUtl.NewA7(dlm)));
		GfoTstr.EqLines(expd, actl);
	}
	public void TestSplitLines(String src, String... expd) {
		GfoTstr.EqLines(expd, NewAryBry(BrySplit.SplitLines(BryUtl.NewA7(src))));
	}
	public String[] NewAryBry(byte[][] lines) {
		int len = lines.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++)
			rv[i] = StringUtl.NewU8(lines[i]);
		return rv;
	}
	public void TestMatchBwdAny(String src, int src_end, int src_bgn, String find, boolean expd) {
		GfoTstr.Eq(expd, BryUtl.MatchBwdAny(BryUtl.NewA7(src), src_end, src_bgn, BryUtl.NewA7(find)));
	}
	public void TestToInt(String val, int expd)                  {this.TestToIntOr(val, -1, expd);}
	public void TestToIntOr(String val, int or, int expd)        {GfoTstr.Eq(expd, BryUtl.ToIntOr(BryUtl.NewU8(val), or));}
	public void TestToIntByA7(int expd, byte... ary)             {GfoTstr.Eq(expd, BryUtl.ToIntByA7(ary), "XtoInt"); GfoTstr.EqAry(ary, BryUtl.NewByInt(expd), "XbyInt");}
	public void TestReadCsvStr(String raw, String expd)          {this.TestReadCsvStr(raw, IntRef.NewZero()  , expd);}
	public void TestReadCsvStr(String raw, int bgn, String expd) {this.TestReadCsvStr(raw, IntRef.New(bgn), expd);}
	public void TestReadCsvStr(String raw, IntRef bgnRef, String expd) {
		int bgn = bgnRef.Val();
		boolean rawHasQuotes = StringUtl.CharAt(raw, bgn) == '\'';
		String actl = StringUtl.Replace(BryUtlByWtr.ReadCsvStr(BryUtl.NewU8(StringUtl.Replace(raw, "'", "\"")), bgnRef, (byte)'|'), "\"", "'");
		GfoTstr.Eq(expd, actl, "rv");
		if (rawHasQuotes) {
			int quoteAdj = StringUtl.Count(actl, "'");
			GfoTstr.Eq(bgn + 1 + StringUtl.Len(actl) + 2 + quoteAdj, bgnRef.Val(), "pos_quote");    // +1=lkp.Len; +2=bgn/end quotes
		}
		else
			GfoTstr.Eq(bgn + 1 + StringUtl.Len(actl), bgnRef.Val(), "pos");    // +1=lkp.Len
	}
	public void TestReadCsvStrFail(String raw) {
		try {BryUtlByWtr.ReadCsvStr(BryUtl.NewU8(StringUtl.Replace(raw, "'", "\"")), IntRef.NewZero(), (byte)'|');}
		catch (Exception e) {return;}
		GfoTstr.FailBcExpdError();
	}
}
