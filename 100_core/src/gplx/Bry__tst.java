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
package gplx;
import org.junit.*; import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.tests.*;
public class Bry__tst {
	private final    Bry__fxt fxt = new Bry__fxt();
	@Test  public void new_ascii_() {
		fxt.Test_new_a7("a"		, Bry_.New_by_ints(97));				// one
		fxt.Test_new_a7("abc"	, Bry_.New_by_ints(97, 98, 99));		// many
		fxt.Test_new_a7(""		, Bry_.Empty);						// none
		fxt.Test_new_a7("¢€𤭢"	, Bry_.New_by_ints(63, 63, 63, 63));	// non-ascii -> ?
	}
	@Test  public void new_u8() {
		fxt.Test_new_u8("a"		, Bry_.New_by_ints(97));						// one
		fxt.Test_new_u8("abc"	, Bry_.New_by_ints(97, 98, 99));				// many
		fxt.Test_new_u8("¢"		, Bry_.New_by_ints(194, 162));					// bry_len=2; cent
		fxt.Test_new_u8("€"		, Bry_.New_by_ints(226, 130, 172));				// bry_len=3; euro
		fxt.Test_new_u8("𤭢"	, Bry_.New_by_ints(240, 164, 173, 162));		// bry_len=4; example from en.w:UTF-8
	}
	@Test   public void Add__bry_plus_byte() {
		fxt.Test_add("a"		, Byte_ascii.Pipe		, "a|");			// basic
		fxt.Test_add(""			, Byte_ascii.Pipe		, "|");				// empty String
	}
	@Test   public void Add__byte_plus_bry() {
		fxt.Test_add(Byte_ascii.Pipe	, "a"			, "|a");			// basic
		fxt.Test_add(Byte_ascii.Pipe	, ""			, "|");				// empty String
	}
	@Test   public void Add_w_dlm() {
		fxt.Test_add_w_dlm(Byte_ascii.Pipe, String_.Ary("a", "b", "c")	, "a|b|c");					// basic
		fxt.Test_add_w_dlm(Byte_ascii.Pipe, String_.Ary("a")				, "a");					// one item
		fxt.Test_add_w_dlm(Byte_ascii.Pipe, String_.Ary("a", null, "c")	, "a||c");					// null
	}
	@Test   public void Add_w_dlm_bry() {
		fxt.Test_add_w_dlm("<>", String_.Ary("a","b","c"), "a<>b<>c");
	}
	@Test  public void MidByPos() {
		tst_MidByPos("abcba", 0, 1, "a");
		tst_MidByPos("abcba", 0, 2, "ab");
		tst_MidByPos("abcba", 1, 4, "bcb");
	}	void tst_MidByPos(String src, int bgn, int end, String expd) {Tfds.Eq(expd, String_.new_u8(Bry_.Mid(Bry_.new_u8(src), bgn, end)));}
	@Test  public void Replace_one() {
		tst_ReplaceOne("a"		, "b"	, "c"	, "a");
		tst_ReplaceOne("b"		, "b"	, "c"	, "c");
		tst_ReplaceOne("bb"		, "b"	, "c"	, "cb");
		tst_ReplaceOne("abcd"	, "bc"	, ""	, "ad");
		tst_ReplaceOne("abcd"	, "b"	, "ee"	, "aeecd");
	}	void tst_ReplaceOne(String src, String find, String repl, String expd) {Tfds.Eq(expd, String_.new_u8(Bry_.Replace_one(Bry_.new_u8(src), Bry_.new_u8(find), Bry_.new_u8(repl))));}
	@Test  public void XtoStrBytesByInt() {
		tst_XtoStrBytesByInt(0, 0);
		tst_XtoStrBytesByInt(9, 9);
		tst_XtoStrBytesByInt(10, 1, 0);
		tst_XtoStrBytesByInt(321, 3, 2, 1);
		tst_XtoStrBytesByInt(-321, Bry_.Byte_NegSign, 3, 2, 1);
		tst_XtoStrBytesByInt(Int_.Max_value, 2,1,4,7,4,8,3,6,4,7);
	}
	void tst_XtoStrBytesByInt(int val, int... expdAryAsInt) {
		byte[] expd = new byte[expdAryAsInt.length];
		for (int i = 0; i < expd.length; i++) {
			int expdInt = expdAryAsInt[i];
			expd[i] = expdInt == Bry_.Byte_NegSign ? Bry_.Byte_NegSign : Byte_ascii.To_a7_str(expdAryAsInt[i]);
		}
		Tfds.Eq_ary(expd, Bry_.To_a7_bry(val, Int_.DigitCount(val)));
	}
	@Test  public void Has_at_end() {
		tst_HasAtEnd("a|bcd|e", "d"	, 2, 5, true);		// y_basic
		tst_HasAtEnd("a|bcd|e", "bcd"	, 2, 5, true);		// y_many
		tst_HasAtEnd("a|bcd|e", "|bcd"	, 2, 5, false);		// n_long
		tst_HasAtEnd("a|bcd|e", "|bc"	, 2, 5, false);		// n_pos
		tst_HasAtEnd("abc", "bc", true);		// y
		tst_HasAtEnd("abc", "bd", false);		// n
		tst_HasAtEnd("a", "ab", false);		// exceeds_len
	}
	void tst_HasAtEnd(String src, String find, int bgn, int end, boolean expd) {Tfds.Eq(expd, Bry_.Has_at_end(Bry_.new_u8(src), Bry_.new_u8(find), bgn, end));}
	void tst_HasAtEnd(String src, String find, boolean expd) {Tfds.Eq(expd, Bry_.Has_at_end(Bry_.new_u8(src), Bry_.new_u8(find)));}
	@Test  public void Has_at_bgn() {
		tst_HasAtBgn("y_basic"	, "a|bcd|e", "b"	, 2, 5, true);
		tst_HasAtBgn("y_many"	, "a|bcd|e", "bcd"	, 2, 5, true);
		tst_HasAtBgn("n_long"	, "a|bcd|e", "bcde"	, 2, 5, false);
		tst_HasAtBgn("n_pos"	, "a|bcd|e", "|bc"	, 2, 5, false);
	}	void tst_HasAtBgn(String tst, String src, String find, int bgn, int end, boolean expd) {Tfds.Eq(expd, Bry_.Has_at_bgn(Bry_.new_u8(src), Bry_.new_u8(find), bgn, end), tst);}
	@Test  public void Match() {
		tst_Match("abc", 0, "abc", true);
		tst_Match("abc", 2,  "c", true);
		tst_Match("abc", 0, "cde", false);
		tst_Match("abc", 2, "abc", false);	// bounds check
		tst_Match("abc", 0, "abcd", false);
		tst_Match("a"  , 0, "", false);
		tst_Match(""  , 0, "a", false);
		tst_Match(""  , 0, "", true);
		tst_Match("ab", 0, "a", false);	// FIX: "ab" should not match "a" b/c .length is different
	}	void tst_Match(String src, int srcPos, String find, boolean expd) {Tfds.Eq(expd, Bry_.Match(Bry_.new_u8(src), srcPos, Bry_.new_u8(find)));}
	@Test  public void ReadCsvStr() {
		tst_ReadCsvStr("a|"	   , "a");
		tst_ReadCsvStr("|a|", 1 , "a");
		Int_obj_ref bgn = Int_obj_ref.New_zero(); tst_ReadCsvStr("a|b|c|", bgn, "a"); tst_ReadCsvStr("a|b|c|", bgn, "b"); tst_ReadCsvStr("a|b|c|", bgn, "c");
		tst_ReadCsvStr("|", "");
		tst_ReadCsvStr_err("a");

		tst_ReadCsvStr("'a'|"		, "a");
		tst_ReadCsvStr("'a''b'|"	, "a'b");
		tst_ReadCsvStr("'a|b'|"		, "a|b");
		tst_ReadCsvStr("''|", "");
		tst_ReadCsvStr_err("''");
		tst_ReadCsvStr_err("'a'b'");
		tst_ReadCsvStr_err("'a");
		tst_ReadCsvStr_err("'a|");
		tst_ReadCsvStr_err("'a'");
	}
	@Test  public void XtoIntBy4Bytes() {	// test len=1, 2, 3, 4
		tst_XtoIntBy4Bytes(32, (byte)32);			// space
		tst_XtoIntBy4Bytes(8707, (byte)34, (byte)3);	// &exist;
		tst_XtoIntBy4Bytes(6382179, Byte_ascii.Ltr_a, Byte_ascii.Ltr_b, Byte_ascii.Ltr_c);
		tst_XtoIntBy4Bytes(1633837924, Byte_ascii.Ltr_a, Byte_ascii.Ltr_b, Byte_ascii.Ltr_c, Byte_ascii.Ltr_d);
	}
	@Test  public void XtoInt() {
		tst_XtoInt("1", 1);
		tst_XtoInt("123", 123);
		tst_XtoInt("a", Int_.Min_value, Int_.Min_value);
		tst_XtoInt("-1", Int_.Min_value, -1);
		tst_XtoInt("-123", Int_.Min_value, -123);
		tst_XtoInt("123-1", Int_.Min_value, Int_.Min_value);
		tst_XtoInt("+123", Int_.Min_value, 123);
		tst_XtoInt("", -1);
	}
	void tst_XtoInt(String val, int expd)					{tst_XtoInt(val, -1, expd);}
	void tst_XtoInt(String val, int or, int expd)			{Tfds.Eq(expd, Bry_.To_int_or(Bry_.new_u8(val), or));}
	void tst_XtoIntBy4Bytes(int expd, byte... ary)	{Tfds.Eq(expd, Bry_.To_int_by_a7(ary), "XtoInt"); Tfds.Eq_ary(ary, Bry_.new_by_int(expd), "XbyInt");}
	void tst_ReadCsvStr(String raw, String expd)			{tst_ReadCsvStr(raw, Int_obj_ref.New_zero()  , expd);}
	void tst_ReadCsvStr(String raw, int bgn, String expd)	{tst_ReadCsvStr(raw, Int_obj_ref.New(bgn), expd);}
	void tst_ReadCsvStr(String raw, Int_obj_ref bgnRef, String expd) {
		int bgn = bgnRef.Val();
		boolean rawHasQuotes = String_.CharAt(raw, bgn) == '\'';
		String actl = String_.Replace(Bry_.ReadCsvStr(Bry_.new_u8(String_.Replace(raw, "'", "\"")), bgnRef, (byte)'|'), "\"", "'");
		Tfds.Eq(expd, actl, "rv");
		if (rawHasQuotes) {
			int quoteAdj = String_.Count(actl, "'");
			Tfds.Eq(bgn + 1 + String_.Len(actl) + 2 + quoteAdj, bgnRef.Val(), "pos_quote");	// +1=lkp.Len; +2=bgn/end quotes
		}
		else
			Tfds.Eq(bgn + 1 + String_.Len(actl), bgnRef.Val(), "pos");	// +1=lkp.Len
	}
	void tst_ReadCsvStr_err(String raw) {
		try {Bry_.ReadCsvStr(Bry_.new_u8(String_.Replace(raw, "'", "\"")), Int_obj_ref.New_zero(), (byte)'|');}
		catch (Exception e) {Err_.Noop(e); return;}
		Tfds.Fail_expdError();
	}
	@Test  public void ReadCsvDte() {
		tst_ReadCsvDte("20110801 221435.987");
	}	void tst_ReadCsvDte(String raw) {Tfds.Eq_date(DateAdp_.parse_fmt(raw, Bry_.Fmt_csvDte), Bry_.ReadCsvDte(Bry_.new_u8(raw + "|"), Int_obj_ref.New_zero(), (byte)'|'));}
	@Test  public void ReadCsvInt() {
		tst_ReadCsvInt("1234567890");
	}	void tst_ReadCsvInt(String raw) {Tfds.Eq(Int_.parse(raw), Bry_.ReadCsvInt(Bry_.new_u8(raw + "|"), Int_obj_ref.New_zero(), (byte)'|'));}
	@Test  public void Trim() {
		Trim_tst("a b c", 1, 4, "b");
		Trim_tst("a  c", 1, 3, "");
		Trim_tst("  ", 0, 2, "");
	}	void Trim_tst(String raw, int bgn, int end, String expd) {Tfds.Eq(expd, String_.new_u8(Bry_.Trim(Bry_.new_u8(raw), bgn, end)));}
	@Test  public void Xto_int_lax() {
		tst_Xto_int_lax("12a", 12);
		tst_Xto_int_lax("1", 1);
		tst_Xto_int_lax("123", 123);
		tst_Xto_int_lax("a", 0);
		tst_Xto_int_lax("-1", -1);
	}
	private void tst_Xto_int_lax(String val, int expd)				{Tfds.Eq(expd, Bry_.To_int_or__lax(Bry_.new_u8(val), 0, String_.Len(val), 0));}
	@Test  public void To_int_or__trim_ws() {
		tst_Xto_int_trim("123 "	, 123);
		tst_Xto_int_trim(" 123"	, 123);
		tst_Xto_int_trim(" 123 "	, 123);
		tst_Xto_int_trim(" 1 3 "	, -1);
	}
	private void tst_Xto_int_trim(String val, int expd)			{Tfds.Eq(expd, Bry_.To_int_or__trim_ws(Bry_.new_u8(val), 0, String_.Len(val), -1));}
	@Test  public void Compare() {
		tst_Compare("abcde", 0, 1, "abcde", 0, 1, CompareAble_.Same);
		tst_Compare("abcde", 0, 1, "abcde", 1, 2, CompareAble_.Less);
		tst_Compare("abcde", 1, 2, "abcde", 0, 1, CompareAble_.More);
		tst_Compare("abcde", 0, 1, "abcde", 0, 2, CompareAble_.Less);
		tst_Compare("abcde", 0, 2, "abcde", 0, 1, CompareAble_.More);
		tst_Compare("abcde", 2, 3, "abçde", 2, 3, CompareAble_.Less);
	}	void tst_Compare(String lhs, int lhs_bgn, int lhs_end, String rhs, int rhs_bgn, int rhs_end, int expd) {Tfds.Eq(expd, Bry_.Compare(Bry_.new_u8(lhs), lhs_bgn, lhs_end, Bry_.new_u8(rhs), rhs_bgn, rhs_end));}
	@Test  public void Increment_last() {
		tst_IncrementLast(ary_(0), ary_(1));
		tst_IncrementLast(ary_(0, 255), ary_(1, 0));
		tst_IncrementLast(ary_(104, 111, 112, 101), ary_(104, 111, 112, 102));
	}
	byte[] ary_(int... ary) {
		byte[] rv = new byte[ary.length];
		for (int i = 0; i < ary.length; i++)
			rv[i] = Byte_.By_int(ary[i]);
		return rv;
	}
	void tst_IncrementLast(byte[] ary, byte[] expd) {Tfds.Eq_ary(expd, Bry_.Increment_last(Bry_.Copy(ary)));}
	@Test   public void Replace_between() {
		tst_Replace_between("a[0]b"					, "[", "]", "0", "a0b");
		tst_Replace_between("a[0]b[1]c"				, "[", "]", "0", "a0b0c");
		tst_Replace_between("a[0b"					, "[", "]", "0", "a[0b");
	}	public void tst_Replace_between(String src, String bgn, String end, String repl, String expd) {Tfds.Eq(expd, String_.new_a7(Bry_.Replace_between(Bry_.new_a7(src), Bry_.new_a7(bgn), Bry_.new_a7(end), Bry_.new_a7(repl))));}
	@Test    public void Replace() {
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		tst_Replace(tmp_bfr, "a0b"					,    "0", "00", "a00b");		// 1 -> 1
		tst_Replace(tmp_bfr, "a0b0c"				,    "0", "00", "a00b00c");		// 1 -> 2
		tst_Replace(tmp_bfr, "a00b00c"				,   "00",  "0", "a0b0c");		// 2 -> 1
		tst_Replace(tmp_bfr, "a0b0"					,    "0", "00", "a00b00");		// 1 -> 2; EOS
		tst_Replace(tmp_bfr, "a00b00"				,   "00",  "0", "a0b0");		// 2 -> 1; EOS
		tst_Replace(tmp_bfr, "a0b0"					,    "1",  "2", "a0b0");		// no match
		tst_Replace(tmp_bfr, "a0b0"					,   "b1", "b2", "a0b0");		// false match; EOS
	}
	public void tst_Replace(Bry_bfr tmp_bfr, String src, String bgn, String repl, String expd) {
		Tfds.Eq(expd, String_.new_a7(Bry_.Replace(tmp_bfr, Bry_.new_a7(src), Bry_.new_a7(bgn), Bry_.new_a7(repl))));
	}
	@Test  public void Split_bry() {
		Split_bry_tst("a|b|c|"		, "|"	, String_.Ary("a", "b", "c"));
		Split_bry_tst("a|"			, "|"	, String_.Ary("a"));
	}
	void Split_bry_tst(String src, String dlm, String[] expd) {
		String[] actl = String_.Ary(Bry_split_.Split(Bry_.new_a7(src), Bry_.new_a7(dlm)));
		Tfds.Eq_ary_str(expd, actl);
	}
	@Test   public void Split_lines() {
		Tst_split_lines("a\nb"		, "a", "b");					// basic
		Tst_split_lines("a\nb\n"	, "a", "b");					// do not create empty trailing lines
		Tst_split_lines("a\r\nb"	, "a", "b");					// crlf
		Tst_split_lines("a\rb"		, "a", "b");					// cr only
	}
	void Tst_split_lines(String src, String... expd) {		
		Tfds.Eq_ary(expd, New_ary(Bry_split_.Split_lines(Bry_.new_a7(src))));
	}
	String[] New_ary(byte[][] lines) {
		int len = lines.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++)
			rv[i] = String_.new_u8(lines[i]);
		return rv;
	}
	@Test   public void Match_bwd_any() {
		Tst_match_bwd_any("abc", 2, 0, "c", true);
		Tst_match_bwd_any("abc", 2, 0, "b", false);
		Tst_match_bwd_any("abc", 2, 0, "bc", true);
		Tst_match_bwd_any("abc", 2, 0, "abc", true);
		Tst_match_bwd_any("abc", 2, 0, "zabc", false);
		Tst_match_bwd_any("abc", 1, 0, "ab", true);
	}
	void Tst_match_bwd_any(String src, int src_end, int src_bgn, String find, boolean expd) {
		Tfds.Eq(expd, Bry_.Match_bwd_any(Bry_.new_a7(src), src_end, src_bgn, Bry_.new_a7(find)));
	}
	@Test   public void Trim_end() {
		fxt.Test_trim_end("a "		, Byte_ascii.Space, "a");	// trim.one
		fxt.Test_trim_end("a   "	, Byte_ascii.Space, "a");	// trim.many
		fxt.Test_trim_end("a"		, Byte_ascii.Space, "a");	// trim.none
		fxt.Test_trim_end(""		, Byte_ascii.Space, "");	// empty
	}
	@Test   public void Mid_w_trim() {
		fxt.Test_Mid_w_trim("abc", "abc");								// no ws
		fxt.Test_Mid_w_trim(" a b c ", "a b c");						// ws at bgn and end
		fxt.Test_Mid_w_trim("\r\n\t a\r\n\t b \r\n\t ", "a\r\n\t b");	// space at bgn and end
		fxt.Test_Mid_w_trim("", "");									// handle 0 bytes
		fxt.Test_Mid_w_trim("   ", "");									// handle all ws
	}
	@Test   public void New_u8_nl_apos() {
		fxt.Test__new_u8_nl_apos(String_.Ary("a"), "a");
		fxt.Test__new_u8_nl_apos(String_.Ary("a", "b"), "a\nb");
		fxt.Test__new_u8_nl_apos(String_.Ary("a", "b'c", "d"), "a\nb\"c\nd");
	}
	@Test   public void Repeat_bry() {
		fxt.Test__repeat_bry("abc"  , 3, "abcabcabc");
	}
	@Test   public void Xcase__build__all() {
		fxt.Test__xcase__build__all(Bool_.N, "abc", "abc");
		fxt.Test__xcase__build__all(Bool_.N, "aBc", "abc");
	}
}
class Bry__fxt {
	private final    Bry_bfr tmp = Bry_bfr_.New();
	public void Test_trim_end(String raw, byte trim, String expd) {
		byte[] raw_bry = Bry_.new_a7(raw);
		Tfds.Eq(expd, String_.new_u8(Bry_.Trim_end(raw_bry, trim, raw_bry.length)));
	}
	public void Test_new_u8(String raw, byte[] expd)			{Tfds.Eq_ary(expd, Bry_.new_u8(raw));}
	public void Test_new_a7(String raw, byte[] expd)			{Tfds.Eq_ary(expd, Bry_.new_a7(raw));}
	public void Test_add(String s, byte b, String expd)			{Tfds.Eq_str(expd, String_.new_u8(Bry_.Add(Bry_.new_u8(s), b)));}
	public void Test_add(byte b, String s, String expd)			{Tfds.Eq_str(expd, String_.new_u8(Bry_.Add(b, Bry_.new_u8(s))));}
	public void Test_add_w_dlm(String dlm, String[] itms, String expd)	{Tfds.Eq(expd, String_.new_u8(Bry_.Add_w_dlm(Bry_.new_u8(dlm), Bry_.Ary(itms))));}
	public void Test_add_w_dlm(byte dlm, String[] itms, String expd)	{Tfds.Eq(expd, String_.new_u8(Bry_.Add_w_dlm(dlm, Bry_.Ary(itms))));}
	public void Test_Mid_w_trim(String src, String expd) {byte[] bry = Bry_.new_u8(src); Tfds.Eq(expd, String_.new_u8(Bry_.Mid_w_trim(bry, 0, bry.length)));}
	public void Test__new_u8_nl_apos(String[] ary, String expd) {
		Tfds.Eq_str_lines(expd, String_.new_u8(Bry_.New_u8_nl_apos(ary)));
	}
	public void Test__repeat_bry(String s, int count, String expd) {
		Gftest.Eq__str(expd, Bry_.Repeat_bry(Bry_.new_u8(s), count));
	}
	public void Test__xcase__build__all(boolean upper, String src, String expd) {
		Gftest.Eq__str(expd, Bry_.Xcase__build__all(tmp, upper, Bry_.new_u8(src)));
	}
}
