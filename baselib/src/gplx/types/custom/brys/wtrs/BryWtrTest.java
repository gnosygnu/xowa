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
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.basics.constants.AsciiByte;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoGuidUtl;
import org.junit.Before;
import org.junit.Test;
public class BryWtrTest {
	private final ByteAryBfrTstr tstr = new ByteAryBfrTstr();
	private BryWtr bfr = BryWtr.New();
	@Before public void setup() {
		bfr = BryWtr.NewWithSize(2); // NOTE: make sure auto-expands
	}
	@Test public void AddByte() {
		tstr.TestAddByte("a", "a", 2);
		tstr.TestAddByte("b", "ab", 2);
		tstr.TestAddByte("c", "abc", 4);
	}
	@Test public void AddAry() {
		tstr.TestAddByte("abcd", "abcd", 12);
	}
	@Test public void AddByteRepeat() {
		tstr.TestAddByteRepeat(AsciiByte.Space, 12, StringUtl.Repeat(" ", 12));
	}
	@Test public void AddDate() {
		tstr.TestAddDate("20110801 221435.987");
	}
	@Test public void TestAddIntVariable() {
		tstr.TestAddIntVariable(-1);
		tstr.TestAddIntVariable(-12);
		tstr.TestAddIntVariable(-1234);
		tstr.TestAddIntVariable(2);
		tstr.TestAddIntVariable(12);
		tstr.TestAddIntVariable(1234);
		tstr.TestAddIntVariable(123456789);
	}
	@Test public void Add_float() {
		TestAddFloat(1 / 3);
		TestAddFloat(-1 / 3);
	}
	private void TestAddFloat(float v) {
		bfr.AddFloat(v);
		GfoTstr.EqFloat(v, FloatUtl.Parse(StringUtl.NewU8(bfr.ToBry())));
	}
	@Test public void Add_int_fixed_len3()                {TestAdd_int_fixed(123, 3, "123");}
	@Test public void Add_int_fixed_pad_1()            {TestAdd_int_fixed(2, 1, "2");}
	@Test public void Add_int_fixed_pad_2()            {TestAdd_int_fixed(2, 2, "02");}
	@Test public void Add_int_fixed_pad_16()            {TestAdd_int_fixed(2, 16, "0000000000000002");}    // test overflows int
	@Test public void Add_int_fixed_neg()                {TestAdd_int_fixed(-2, 2, "-2");}
	@Test public void Add_int_fixed_neg_pad1()            {TestAdd_int_fixed(-2, 1, "-");}
	@Test public void Add_int_fixed_chop_1()            {TestAdd_int_fixed(123, 1, "3");}
	@Test public void Add_int_fixed_chop_neg()            {TestAdd_int_fixed(-21, 2, "-1");}
	void TestAdd_int_fixed(int val, int digits, String expd) {GfoTstr.Eq(expd, StringUtl.NewU8(bfr.AddIntFixed(val, digits).ToBry()));}
	@Test public void Add_long_fixed_len3()            {TestAdd_long_fixed(123, 3, "123");}
	@Test public void Add_long_fixed_pad_1()            {TestAdd_long_fixed(2, 1, "2");}
	@Test public void Add_long_fixed_pad_2()            {TestAdd_long_fixed(2, 2, "02");}
	@Test public void Add_long_fixed_pad_16()            {TestAdd_long_fixed(2, 16, "0000000000000002");}    // test overflows long
	@Test public void Add_long_fixed_neg()                {TestAdd_long_fixed(-2, 2, "-2");}
	@Test public void Add_long_fixed_neg_pad1()        {TestAdd_long_fixed(-2, 1, "-");}
	@Test public void Add_long_fixed_chop_1()            {TestAdd_long_fixed(123, 1, "3");}
	@Test public void Add_long_fixed_chop_neg()        {TestAdd_long_fixed(-21, 2, "-1");}
	@Test public void Add_long_fixed_large()            {TestAdd_long_fixed(123456789012345L, 15, "123456789012345");}
	void TestAdd_long_fixed(long val, int digits, String expd) {GfoTstr.Eq(expd, StringUtl.NewU8(bfr.AddLongFixed(val, digits).ToBry()));}
	@Test public void AddDte_short() {
		TestAddDte_short("2010-08-26T22:38:36Z");
	}
	void TestAddDte_short(String raw) {
//            byte[] ary = String_.XtoByteAryAscii(raw);
//            Bry_fmtr_IntBldr ib = new Bry_fmtr_IntBldr();
//            int y = 0, m = 0, d = 0, h = 0, n = 0, s = 0, aryLen = ary.length;
//            for (int i = 0; i < aryLen; i++) {
//                byte b = ary[i];                
//                switch (i) {
//                    case  4: y = ib.To_int_and_clear(); break;
//                    case  7: m = ib.To_int_and_clear(); break;
//                    case 10: d = ib.To_int_and_clear(); break;
//                    case 13: h = ib.To_int_and_clear(); break;
//                    case 16: n = ib.To_int_and_clear(); break;
//                    case 19: s = ib.To_int_and_clear(); break;
//                    default: ib.Add(b); break;
//                }
//            }
//            long l = Pow38_to(y, m, d, h, n, s);
////            Base85_.Set_bry(l, bb.
//            bb.Add_int(l);
	}
//        @Test public void InsertAt_str() {
//            TestInsertAt_str("", 0, "c", "c");
//            TestInsertAt_str("ab", 0, "c", "cab");
//            TestInsertAt_str("ab", 0, "cdefghij", "cdefghijab");
//        }
//        void TestInsertAt_str(String orig, int insertAt, String insertStr, String expd) {
//            bb = Bry_bfr_.New(16);
//            bb.Add_str(orig);
//            bb.InsertAt_str(insertAt, insertStr);
//            String actl = bb.To_str_and_clear();
//            Tfds.Eq(expd, actl);
//        }
	@Test public void To_bry_and_clear_and_trim() {
		TestXtoAryAndClearAndTrim("a"    , "a");
		TestXtoAryAndClearAndTrim(" a "    , "a");
		TestXtoAryAndClearAndTrim(" a b "    , "a b");
		TestXtoAryAndClearAndTrim("  "        , "");
	}
	void TestXtoAryAndClearAndTrim(String raw, String expd) {
		bfr.AddStrU8(raw);
		GfoTstr.Eq(expd, StringUtl.NewU8(bfr.ToBryAndClearAndTrim()));
	}
	@Test public void XtoInt() {
		TestXtoInt("123", 123);
		TestXtoInt("a", IntUtl.MinValue);
		TestXtoInt("9999999999", IntUtl.MinValue);
	}
	void TestXtoInt(String raw, int expd) {
		bfr.AddStrU8(raw);
		GfoTstr.Eq(expd, bfr.ToIntAndClear(IntUtl.MinValue));
	}
	static long Pow38_to(int year, int month, int day, int hour, int minute, int second, int frac) {
		return    ((long)year)                << 26 
			|    ((long)month    & 0x0f)        << 22    // 16
			|    ((long)day        & 0x1f)        << 17    // 32
			|    ((long)hour        & 0x1f)        << 12    // 32
			|    ((long)minute    & 0x3f)        <<  6    // 64
			|    ((long)second    & 0x3f)                // 64
			;
	}
	static GfoDate Pow38_by(long v) {
		int year    = (int)    (v >> 26);
		int month    = (int)((v >> 22) & 0x0f); 
		int day        = (int)((v >> 17) & 0x1f); 
		int hour    = (int)((v >> 12) & 0x1f); 
		int minute    = (int)((v >>  6) & 0x3f); 
		int second    = (int)((v        ) & 0x3f); 
		return GfoDateUtl.New(year, month, day, hour, minute, second, 0);
	}
	@Test public void Add_bfr_trimEnd_and_clear() {
		TestAdd_bfr_trimEnd_and_clear("a ", "a");
	}
	void TestAdd_bfr_trimEnd_and_clear(String raw, String expd) {
		BryWtr tmp = BryWtr.New().AddStrU8(raw);
		GfoTstr.Eq(expd, bfr.AddBfrTrimAndClear(tmp, false, true).ToStrAndClear());
	}
	@Test public void Add_bfr_trimAll_and_clear() {
		TestAdd_bfr_trimAll_and_clear(" a ", "a");
		TestAdd_bfr_trimAll_and_clear(" a b ", "a b");
		TestAdd_bfr_trimAll_and_clear("a", "a");
		TestAdd_bfr_trimAll_and_clear("", "");
	}
	void TestAdd_bfr_trimAll_and_clear(String raw, String expd) {
		BryWtr tmp = BryWtr.New().AddStrU8(raw);
		GfoTstr.Eq(expd, bfr.AddBfrTrimAndClear(tmp, true, true).ToStrAndClear());
	}
	@Test public void Add_int_pad_bgn() {
		tstr.TestAddIntPadBgn(AsciiByte.Num0, 3,    0, "000");
		tstr.TestAddIntPadBgn(AsciiByte.Num0, 3,    1, "001");
		tstr.TestAddIntPadBgn(AsciiByte.Num0, 3,   10, "010");
		tstr.TestAddIntPadBgn(AsciiByte.Num0, 3,  100, "100");
		tstr.TestAddIntPadBgn(AsciiByte.Num0, 3, 1000, "1000");
	}
	@Test public void Add_bry_escape() {
		tstr.TestAddBryEscape("abc"                  , "abc");            // nothing to escape
		tstr.TestAddBryEscape("a'bc"                 , "a''bc");          // single escape (code handles first quote differently)
		tstr.TestAddBryEscape("a'b'c"                , "a''b''c");        // double escape (code handles subsequent quotes different than first)
		tstr.TestAddBryEscape("abc", 1, 2            , "b");              // nothing to escape
	}
	@Test public void Add_bry_escape_html() {
		tstr.TestAddBryEscapeHtml("abc"             , "abc");                            // escape=none
		tstr.TestAddBryEscapeHtml("a&\"'<>b"        , "a&amp;&quot;&#39;&lt;&gt;b");     // escape=all; code handles first escape differently
		tstr.TestAddBryEscapeHtml("a&b&c"           , "a&amp;b&amp;c");                  // staggered; code handles subsequent escapes differently
		tstr.TestAddBryEscapeHtml("abc", 1, 2       , "b");                              // by index; fixes bug in initial implementation
	}
	@Test public void Insert_at() {
		tstr.TestInsertAt("abcd", 0, "xyz"                    , "xyzabcd");    // bgn
		tstr.TestInsertAt("abcd", 4, "xyz"                    , "abcdxyz");    // end
		tstr.TestInsertAt("abcd", 2, "xyz"                    , "abxyzcd");    // mid
		tstr.TestInsertAt("abcd", 2, "xyz", 1, 2            , "abycd");        // mid
	}
	@Test public void Delete_rng() {
		tstr.TestDelRng("abcd", 0, 2                        , "cd");    // bgn
		tstr.TestDelRng("abcd", 2, 4                        , "ab");    // end
		tstr.TestDelRng("abcd", 1, 3                        , "ad");    // mid
	}
	@Test public void Delete_rng_to_bgn() {
		tstr.TestDelRngToBgn("abcd", 2                    , "cd");
	}
	@Test public void Delete_rng_to_end() {
		tstr.TestDelRngToEnd("abcd", 2                    , "ab");
	}
	@Test public void To_bry_ary_and_clear() {
		tstr.TestToBryAryAndClear(""            );                        // empty
		tstr.TestToBryAryAndClear("a"            , "a");                    // lines=1
		tstr.TestToBryAryAndClear("a\nb\nc"    , "a", "b", "c");        // lines=n
		tstr.TestToBryAryAndClear("a\n"        , "a");                    // nl at end
	}
	@Test public void To_bry_ary_and_clear_and_trim__memory_reference_bug() {// PURPOSE:test that bry isn't reused; ISSUE#:562; DATE:2019-09-02
		String str_a = "aaaaaaaaaaaaaaaa" // NOTE: length is 16 b/c bry_bfr init's to 16 len
			,  str_b = "bbbbbbbbbbbbbbbb";
		BryWtr bfr = BryWtr.New();
		bfr.AddStrA7(str_a);
		byte[] bry_a = bfr.ToBryAndClearAndTrim();
		GfoTstr.Eq(str_a, StringUtl.NewU8(bry_a));

		bfr.AddStrA7(str_b);
		byte[] bry_b = bfr.ToBryAndClearAndTrim();
		GfoTstr.Eq(str_b, StringUtl.NewU8(bry_b));

		GfoTstr.Eq(str_a, StringUtl.NewU8(bry_a)); // fais if bry_b
	}
	@Test public void AddObjToStr() {
		String s = "00000000-0000-0000-0000-000000000000";
		tstr.TestAddObj(GfoGuidUtl.Parse(s), s);
	}
	@Test public void AddObjBfrArg() {
		String val = "testString";
		tstr.TestAddObj(new BryBfrArgMock(val), val);
	}
}
class BryBfrArgMock implements BryBfrArg {
	public BryBfrArgMock(String val) {this.val = val;} private String val;
	@Override public void AddToBfr(BryWtr bfr) {
		bfr.AddStrU8(val);
	}
}
class ByteAryBfrTstr {
	private final BryWtr bfr = BryWtr.NewWithSize(2);
	public BryWtr Bfr() {return bfr;}
	public void Clear() {
		bfr.ClearAndReset();
	}
	public void TestAddByteRepeat(byte b, int len, String expd) {GfoTstr.Eq(expd, bfr.AddByteRepeat(b, len).ToStrAndClear());}
	public void TestAddByte(String s, String expdStr, int expdMax) {
		if (StringUtl.Len(s) == 1)
			bfr.AddByte((byte)StringUtl.CharAt(s, 0));
		else
			bfr.Add(BryUtl.NewU8(s));
		GfoTstr.Eq(expdStr, StringUtl.NewU8(bfr.ToBry()));
		GfoTstr.Eq(expdMax, bfr.Max());
	}
	public void TestAddDate(String raw) {
		bfr.AddDate(GfoDateUtl.ParseFmt(raw, BryUtl.FmtCsvDte));
		GfoTstr.Eq(raw, StringUtl.NewU8(bfr.ToBry()));
	}
	public void TestAddIntVariable(int val) {
		bfr.Clear();
		bfr.AddIntVariable(val);
		GfoTstr.Eq(val, IntUtl.Parse(StringUtl.NewU8(bfr.ToBry())));
	}
	public void TestAddIntPadBgn(byte pad_byte, int str_len, int val, String expd) {GfoTstr.Eq(expd, bfr.AddIntPadBgn(pad_byte, str_len, val).ToStrAndClear());}
	public void TestAddBryEscape(String src, String expd) {TestAddBryEscape(src, 0, StringUtl.Len(src), expd);}
	public void TestAddBryEscape(String src, int src_bgn, int src_end, String expd) {
		byte[] val_bry = BryUtl.NewU8(src);
		GfoTstr.Eq(expd, bfr.AddBryEscape(AsciiByte.Apos, new byte[] {AsciiByte.Apos, AsciiByte.Apos}, val_bry, src_bgn, src_end).ToStrAndClear());
	}
	public void TestInsertAt(String init, int pos, String val, String expd)    {GfoTstr.Eq(expd, bfr.AddStrU8(init).InsertAt(pos, BryUtl.NewU8(val)).ToStrAndClear());}
	public void TestInsertAt(String init, int pos, String val, int val_bgn, int val_end, String expd)    {GfoTstr.Eq(expd, bfr.AddStrU8(init).InsertAt(pos, BryUtl.NewU8(val), val_bgn, val_end).ToStrAndClear());}
	public void TestDelRng(String init, int bgn, int end, String expd)        {GfoTstr.Eq(expd, bfr.AddStrU8(init).DelRng(bgn, end).ToStrAndClear());}
	public void TestDelRngToBgn(String init, int pos, String expd)        {GfoTstr.Eq(expd, bfr.AddStrU8(init).DelRngToBgn(pos).ToStrAndClear());}
	public void TestDelRngToEnd(String init, int pos, String expd)        {GfoTstr.Eq(expd, bfr.AddStrU8(init).DelRngToEnd(pos).ToStrAndClear());}
	public void TestToBryAryAndClear(String bfr_str, String... expd) {
		GfoTstr.EqLines(expd, StringUtl.Ary(bfr.AddStrU8(bfr_str).ToBryAryAndClear()));
	}
	public void TestAddBryEscapeHtml(String src, String expd) {TestAddBryEscapeHtml(src, 0, StringUtl.Len(src), expd);}
	public void TestAddBryEscapeHtml(String src, int src_bgn, int src_end, String expd) {
		GfoTstr.Eq(BryUtl.NewU8(expd), bfr.AddBryEscapeHtml(BryUtl.NewU8(src), src_bgn, src_end).ToBryAndClear());
	}
	public void TestAddObj(Object o, String expd) {
		GfoTstr.Eq(BryUtl.NewU8(expd), bfr.AddObj(o).ToBryAndClear());
	}
}
