/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
import org.junit.*;
public class Bry_bfr_tst {
	Bry_bfr bb = Bry_bfr.new_(16);
	@Before public void setup() {bb.Clear();} private ByteAryBfr_fxt fxt = new ByteAryBfr_fxt();
	@Test  public void AddByte() {
		bb = Bry_bfr.new_(2);	// NOTE: make sure auto-expands
		tst_AddByte("a", "a", 2);
		tst_AddByte("b", "ab", 2);
		tst_AddByte("c", "abc", 4);
	}
	@Test  public void AddAry() {	// NOTE: make sure auto-expands
		bb = Bry_bfr.new_(2);
		tst_AddByte("abcd", "abcd", 12);
	}
	@Test  public void Add_byte_repeat() {	// NOTE: make sure auto-expands
		bb = Bry_bfr.new_(2);
		tst_Add_byte_repeat(Byte_ascii.Space, 12, String_.Repeat(" ", 12));
	}	void tst_Add_byte_repeat(byte b, int len, String expd) {Tfds.Eq(expd, bb.Add_byte_repeat(b, len).XtoStrAndClear());}
	void tst_AddByte(String s, String expdStr, int expdLen) {
		if (String_.Len(s) == 1)
			bb.Add_byte((byte)String_.CharAt(s, 0));
		else
			bb.Add(Bry_.new_utf8_(s));
		Tfds.Eq(expdStr, String_.new_utf8_(bb.XtoAry()));
		Tfds.Eq(expdLen, bb.Bfr_max());
	}
	@Test  public void Add_dte() {
		tst_AddDte("20110801 221435.987");
	}
	void tst_AddDte(String raw) {
		bb.Add_dte(DateAdp_.parse_fmt(raw, Bry_.Fmt_csvDte));
		Tfds.Eq(raw, String_.new_utf8_(bb.XtoAry()));
	}
	@Test  public void Add_int_variable() {
		Add_int_variable(-1);
		Add_int_variable(-12);
		Add_int_variable(-1234);
		Add_int_variable(2);
		Add_int_variable(12);
		Add_int_variable(1234);
		Add_int_variable(123456789);
	}
	@Test  public void Add_float() {
		tst_Add_float(1 / 3);
		tst_Add_float(-1 / 3);
	}
	void tst_Add_float(float v) {
		bb.Add_float(v);
		Tfds.Eq(v, Float_.parse_(String_.new_utf8_(bb.XtoAry())));
	}
	void Add_int_variable(int val) {
		bb.Clear();
		bb.Add_int_variable(val);
		Tfds.Eq(val, Int_.parse_(String_.new_utf8_(bb.XtoAry())));
	}
	@Test  public void Add_int_fixed_len3()				{tst_Add_int_fixed(123, 3, "123");}
	@Test  public void Add_int_fixed_pad_1()			{tst_Add_int_fixed(2, 1, "2");}
	@Test  public void Add_int_fixed_pad_2()			{tst_Add_int_fixed(2, 2, "02");}
	@Test  public void Add_int_fixed_pad_16()			{tst_Add_int_fixed(2, 16, "0000000000000002");}	// test overflows int
	@Test  public void Add_int_fixed_neg()				{tst_Add_int_fixed(-2, 2, "-2");}
	@Test  public void Add_int_fixed_neg_pad1()			{tst_Add_int_fixed(-2, 1, "-");}
	@Test  public void Add_int_fixed_chop_1()			{tst_Add_int_fixed(123, 1, "3");}
	@Test  public void Add_int_fixed_chop_neg()			{tst_Add_int_fixed(-21, 2, "-1");}
	void tst_Add_int_fixed(int val, int digits, String expd) {Tfds.Eq(expd, String_.new_utf8_(bb.Add_int_fixed(val, digits).XtoAry()));}
	@Test  public void Add_long_fixed_len3()			{tst_Add_long_fixed(123, 3, "123");}
	@Test  public void Add_long_fixed_pad_1()			{tst_Add_long_fixed(2, 1, "2");}
	@Test  public void Add_long_fixed_pad_2()			{tst_Add_long_fixed(2, 2, "02");}
	@Test  public void Add_long_fixed_pad_16()			{tst_Add_long_fixed(2, 16, "0000000000000002");}	// test overflows long
	@Test  public void Add_long_fixed_neg()				{tst_Add_long_fixed(-2, 2, "-2");}
	@Test  public void Add_long_fixed_neg_pad1()		{tst_Add_long_fixed(-2, 1, "-");}
	@Test  public void Add_long_fixed_chop_1()			{tst_Add_long_fixed(123, 1, "3");}
	@Test  public void Add_long_fixed_chop_neg()		{tst_Add_long_fixed(-21, 2, "-1");}
	@Test  public void Add_long_fixed_large()			{tst_Add_long_fixed(123456789012345L, 15, "123456789012345");}
	void tst_Add_long_fixed(long val, int digits, String expd) {Tfds.Eq(expd, String_.new_utf8_(bb.Add_long_fixed(val, digits).XtoAry()));}
	@Test  public void AddDte_short() {
		tst_AddDte_short("2010-08-26T22:38:36Z");
	}
	void tst_AddDte_short(String raw) {
//			byte[] ary = String_.XtoByteAryAscii(raw);
//			Bry_fmtr_IntBldr ib = new Bry_fmtr_IntBldr();
//			int y = 0, m = 0, d = 0, h = 0, n = 0, s = 0, aryLen = ary.length;
//			for (int i = 0; i < aryLen; i++) {
//				byte b = ary[i];				
//				switch (i) {
//					case  4: y = ib.XtoIntAndClear(); break;
//					case  7: m = ib.XtoIntAndClear(); break;
//					case 10: d = ib.XtoIntAndClear(); break;
//					case 13: h = ib.XtoIntAndClear(); break;
//					case 16: n = ib.XtoIntAndClear(); break;
//					case 19: s = ib.XtoIntAndClear(); break;
//					default: ib.Add(b); break;
//				}
//			}
//            long l = Pow38_to(y, m, d, h, n, s);
////			Base85_utl.XtoStrByAry(l, bb.
//			bb.Add_int(l);
	}
//		@Test  public void InsertAt_str() {
//			tst_InsertAt_str("", 0, "c", "c");
//			tst_InsertAt_str("ab", 0, "c", "cab");
//			tst_InsertAt_str("ab", 0, "cdefghij", "cdefghijab");
//		}
//		void tst_InsertAt_str(String orig, int insertAt, String insertStr, String expd) {
//			bb = Bry_bfr.new_(16);
//			bb.Add_str(orig);
//			bb.InsertAt_str(insertAt, insertStr);
//			String actl = bb.XtoStrAndClear();
//			Tfds.Eq(expd, actl);
//		}
	@Test  public void XtoAryAndClearAndTrim() {
		tst_XtoAryAndClearAndTrim("a"	, "a");
		tst_XtoAryAndClearAndTrim(" a "	, "a");
		tst_XtoAryAndClearAndTrim(" a b "	, "a b");
		tst_XtoAryAndClearAndTrim("  "		, "");
	}
	void tst_XtoAryAndClearAndTrim(String raw, String expd) {
		bb.Add_str(raw);
		Tfds.Eq(expd, String_.new_utf8_(bb.XtoAryAndClearAndTrim()));
	}
	@Test  public void XtoInt() {
		tst_XtoInt("123", 123);
		tst_XtoInt("a", Int_.MinValue);
		tst_XtoInt("9999999999", Int_.MinValue);
	}
	void tst_XtoInt(String raw, int expd) {
		bb.Add_str(raw);
		Tfds.Eq(expd, bb.XtoIntAndClear(Int_.MinValue));
	}
	static long Pow38_to(int year, int month, int day, int hour, int minute, int second, int frac) {
		return	((long)year)				<< 26 
			|	((long)month	& 0x0f)		<< 22	// 16
			|	((long)day		& 0x1f)		<< 17	// 32
			|	((long)hour		& 0x1f)		<< 12	// 32
			|	((long)minute	& 0x3f)		<<  6	// 64
			|	((long)second	& 0x3f)				// 64
			;
	}
	static DateAdp Pow38_by(long v) {
		int year	= (int)	(v >> 26);
		int month	= (int)((v >> 22) & 0x0f); 
		int day		= (int)((v >> 17) & 0x1f); 
		int hour	= (int)((v >> 12) & 0x1f); 
		int minute	= (int)((v >>  6) & 0x3f); 
		int second	= (int)((v		) & 0x3f); 
		return DateAdp_.new_(year, month, day, hour, minute, second, 0);
	}
	@Test  public void Add_bfr_trimEnd_and_clear() {
		tst_Add_bfr_trimEnd_and_clear("a ", "a");
	}
	void tst_Add_bfr_trimEnd_and_clear(String raw, String expd) {
		Bry_bfr tmp = Bry_bfr.new_().Add_str(raw);
		Tfds.Eq(expd, bb.Add_bfr_trim_and_clear(tmp, false, true).XtoStrAndClear());
	}
	@Test  public void Add_bfr_trimAll_and_clear() {
		tst_Add_bfr_trimAll_and_clear(" a ", "a");
		tst_Add_bfr_trimAll_and_clear(" a b ", "a b");
		tst_Add_bfr_trimAll_and_clear("a", "a");
		tst_Add_bfr_trimAll_and_clear("", "");
	}
	void tst_Add_bfr_trimAll_and_clear(String raw, String expd) {
		Bry_bfr tmp = Bry_bfr.new_().Add_str(raw);
		Tfds.Eq(expd, bb.Add_bfr_trim_and_clear(tmp, true, true).XtoStrAndClear());
	}
	@Test  public void Add_int_pad_bgn() {
		fxt.Test_Add_int_pad_bgn(Byte_ascii.Num_0, 3,    0, "000");
		fxt.Test_Add_int_pad_bgn(Byte_ascii.Num_0, 3,    1, "001");
		fxt.Test_Add_int_pad_bgn(Byte_ascii.Num_0, 3,   10, "010");
		fxt.Test_Add_int_pad_bgn(Byte_ascii.Num_0, 3,  100, "100");
		fxt.Test_Add_int_pad_bgn(Byte_ascii.Num_0, 3, 1000, "1000");
	}
	@Test  public void Add_bry_escape() {
		fxt.Test_Add_bry_escape("abc"					, "abc");		// nothing to escape
		fxt.Test_Add_bry_escape("a'bc"					, "a''bc");		// single escape (code handles first quote differently)
		fxt.Test_Add_bry_escape("a'b'c"					, "a''b''c");	// double escape (code handles subsequent quotes different than first)
	}
	@Test  public void Insert_at() {
		fxt.Test_Insert_at("abcd", 0, "xyz"					, "xyzabcd");	// bgn
		fxt.Test_Insert_at("abcd", 4, "xyz"					, "abcdxyz");	// end
		fxt.Test_Insert_at("abcd", 2, "xyz"					, "abxyzcd");	// mid
		fxt.Test_Insert_at("abcd", 2, "xyz", 1, 2			, "abycd");		// mid
	}
	@Test  public void Delete_rng() {
		fxt.Test_Delete_rng("abcd", 0, 2						, "cd");	// bgn
		fxt.Test_Delete_rng("abcd", 2, 4						, "ab");	// end
		fxt.Test_Delete_rng("abcd", 1, 3						, "ad");	// mid
	}
	@Test  public void Delete_rng_to_bgn() {
		fxt.Test_Delete_rng_to_bgn("abcd", 2					, "cd");
	}
	@Test  public void Delete_rng_to_end() {
		fxt.Test_Delete_rng_to_end("abcd", 2					, "ab");
	}
}
class ByteAryBfr_fxt {
	private Bry_bfr bfr = Bry_bfr.reset_(16);
	public void Clear() {
		bfr.ClearAndReset();
	}
	public void Test_Add_int_pad_bgn(byte pad_byte, int str_len, int val, String expd) {Tfds.Eq(expd, bfr.Add_int_pad_bgn(pad_byte, str_len, val).XtoStrAndClear());}
	public void Test_Add_bry_escape(String val, String expd) {
		byte[] val_bry = Bry_.new_utf8_(val);
		Tfds.Eq(expd, bfr.Add_bry_escape(Byte_ascii.Apos, Byte_ascii.Apos, val_bry, 0, val_bry.length).XtoStrAndClear());
	}
	public void Test_Insert_at(String init, int pos, String val, String expd)	{Tfds.Eq(expd, bfr.Add_str(init).Insert_at(pos, Bry_.new_utf8_(val)).XtoStrAndClear());}
	public void Test_Insert_at(String init, int pos, String val, int val_bgn, int val_end, String expd)	{Tfds.Eq(expd, bfr.Add_str(init).Insert_at(pos, Bry_.new_utf8_(val), val_bgn, val_end).XtoStrAndClear());}
	public void Test_Delete_rng(String init, int bgn, int end, String expd)		{Tfds.Eq(expd, bfr.Add_str(init).Delete_rng(bgn, end).XtoStrAndClear());}
	public void Test_Delete_rng_to_bgn(String init, int pos, String expd)		{Tfds.Eq(expd, bfr.Add_str(init).Delete_rng_to_bgn(pos).XtoStrAndClear());}
	public void Test_Delete_rng_to_end(String init, int pos, String expd)		{Tfds.Eq(expd, bfr.Add_str(init).Delete_rng_to_end(pos).XtoStrAndClear());}
}
