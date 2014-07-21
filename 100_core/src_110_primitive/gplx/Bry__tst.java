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
import gplx.texts.*;
public class Bry__tst {
	@Test  public void MidByPos() {
		tst_MidByPos("abcba", 0, 1, "a");
		tst_MidByPos("abcba", 0, 2, "ab");
		tst_MidByPos("abcba", 1, 4, "bcb");
	}	void tst_MidByPos(String src, int bgn, int end, String expd) {Tfds.Eq(expd, String_.new_utf8_(Bry_.Mid(Bry_.new_utf8_(src), bgn, end)));}
	@Test  public void Replace_one() {
		tst_ReplaceOne("a"		, "b"	, "c"	, "a");
		tst_ReplaceOne("b"		, "b"	, "c"	, "c");
		tst_ReplaceOne("bb"		, "b"	, "c"	, "cb");
		tst_ReplaceOne("abcd"	, "bc"	, ""	, "ad");
		tst_ReplaceOne("abcd"	, "b"	, "ee"	, "aeecd");
	}	void tst_ReplaceOne(String src, String find, String repl, String expd) {Tfds.Eq(expd, String_.new_utf8_(Bry_.Replace_one(Bry_.new_utf8_(src), Bry_.new_utf8_(find), Bry_.new_utf8_(repl))));}
	@Test  public void XtoStrBytesByInt() {
		tst_XtoStrBytesByInt(0, 0);
		tst_XtoStrBytesByInt(9, 9);
		tst_XtoStrBytesByInt(10, 1, 0);
		tst_XtoStrBytesByInt(321, 3, 2, 1);
		tst_XtoStrBytesByInt(-321, Bry_.Byte_NegSign, 3, 2, 1);
		tst_XtoStrBytesByInt(Int_.MaxValue, 2,1,4,7,4,8,3,6,4,7);
	}
	void tst_XtoStrBytesByInt(int val, int... expdAryAsInt) {
		byte[] expd = new byte[expdAryAsInt.length];
		for (int i = 0; i < expd.length; i++) {
			int expdInt = expdAryAsInt[i];
			expd[i] = expdInt == Bry_.Byte_NegSign ? Bry_.Byte_NegSign : Bry_.XtoStrByte(expdAryAsInt[i]);
		}
		Tfds.Eq_ary(expd, Bry_.XtoStrBytesByInt(val, Int_.DigitCount(val)));
	}
	@Test  public void HasAtEnd() {
		tst_HasAtEnd("a|bcd|e", "d"	, 2, 5, true);		// y_basic
		tst_HasAtEnd("a|bcd|e", "bcd"	, 2, 5, true);		// y_many
		tst_HasAtEnd("a|bcd|e", "|bcd"	, 2, 5, false);		// n_long
		tst_HasAtEnd("a|bcd|e", "|bc"	, 2, 5, false);		// n_pos
		tst_HasAtEnd("abc", "bc", true);		// y
		tst_HasAtEnd("abc", "bd", false);		// n
		tst_HasAtEnd("a", "ab", false);		// exceeds_len
	}
	void tst_HasAtEnd(String src, String find, int bgn, int end, boolean expd) {Tfds.Eq(expd, Bry_.HasAtEnd(Bry_.new_utf8_(src), Bry_.new_utf8_(find), bgn, end));}
	void tst_HasAtEnd(String src, String find, boolean expd) {Tfds.Eq(expd, Bry_.HasAtEnd(Bry_.new_utf8_(src), Bry_.new_utf8_(find)));}
	@Test  public void HasAtBgn() {
		tst_HasAtBgn("y_basic"	, "a|bcd|e", "b"	, 2, 5, true);
		tst_HasAtBgn("y_many"	, "a|bcd|e", "bcd"	, 2, 5, true);
		tst_HasAtBgn("n_long"	, "a|bcd|e", "bcde"	, 2, 5, false);
		tst_HasAtBgn("n_pos"	, "a|bcd|e", "|bc"	, 2, 5, false);
	}	void tst_HasAtBgn(String tst, String src, String find, int bgn, int end, boolean expd) {Tfds.Eq(expd, Bry_.HasAtBgn(Bry_.new_utf8_(src), Bry_.new_utf8_(find), bgn, end), tst);}
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
	}	void tst_Match(String src, int srcPos, String find, boolean expd) {Tfds.Eq(expd, Bry_.Match(Bry_.new_utf8_(src), srcPos, Bry_.new_utf8_(find)));}
	@Test  public void ReadCsvStr() {
		tst_ReadCsvStr("a|"	   , "a");
		tst_ReadCsvStr("|a|", 1 , "a");
		Int_obj_ref bgn = Int_obj_ref.zero_(); tst_ReadCsvStr("a|b|c|", bgn, "a"); tst_ReadCsvStr("a|b|c|", bgn, "b"); tst_ReadCsvStr("a|b|c|", bgn, "c");
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
		tst_XtoInt("a", Int_.MinValue, Int_.MinValue);
		tst_XtoInt("-1", Int_.MinValue, -1);
		tst_XtoInt("-123", Int_.MinValue, -123);
		tst_XtoInt("123-1", Int_.MinValue, Int_.MinValue);
		tst_XtoInt("+123", Int_.MinValue, 123);
		tst_XtoInt("", -1);
	}
	void tst_XtoInt(String val, int expd)					{tst_XtoInt(val, -1, expd);}
	void tst_XtoInt(String val, int or, int expd)			{Tfds.Eq(expd, Bry_.Xto_int_or(Bry_.new_utf8_(val), or));}
	void tst_XtoIntBy4Bytes(int expd, byte... ary)	{Tfds.Eq(expd, Bry_.XtoIntBy4Bytes(ary), "XtoInt"); Tfds.Eq_ary(ary, Bry_.XbyInt(expd), "XbyInt");}
	void tst_ReadCsvStr(String raw, String expd)			{tst_ReadCsvStr(raw, Int_obj_ref.zero_()  , expd);}
	void tst_ReadCsvStr(String raw, int bgn, String expd)	{tst_ReadCsvStr(raw, Int_obj_ref.new_(bgn), expd);}
	void tst_ReadCsvStr(String raw, Int_obj_ref bgnRef, String expd) {
		int bgn = bgnRef.Val();
		boolean rawHasQuotes = String_.CharAt(raw, bgn) == '\'';
		String actl = String_.Replace(Bry_.ReadCsvStr(Bry_.new_utf8_(String_.Replace(raw, "'", "\"")), bgnRef, (byte)'|'), "\"", "'");
		Tfds.Eq(expd, actl, "rv");
		if (rawHasQuotes) {
			int quoteAdj = String_.Count(actl, "'");
			Tfds.Eq(bgn + 1 + String_.Len(actl) + 2 + quoteAdj, bgnRef.Val(), "pos_quote");	// +1=lkp.Len; +2=bgn/end quotes
		}
		else
			Tfds.Eq(bgn + 1 + String_.Len(actl), bgnRef.Val(), "pos");	// +1=lkp.Len
	}
	void tst_ReadCsvStr_err(String raw) {
		try {Bry_.ReadCsvStr(Bry_.new_utf8_(String_.Replace(raw, "'", "\"")), Int_obj_ref.zero_(), (byte)'|');}
		catch (Exception e) {Err_.Noop(e); return;}
		Tfds.Fail_expdError();
	}
	@Test  public void ReadCsvDte() {
		tst_ReadCsvDte("20110801 221435.987");
	}	void tst_ReadCsvDte(String raw) {Tfds.Eq_date(DateAdp_.parse_fmt(raw, Bry_.Fmt_csvDte), Bry_.ReadCsvDte(Bry_.new_utf8_(raw + "|"), Int_obj_ref.zero_(), (byte)'|'));}
	@Test  public void ReadCsvInt() {
		tst_ReadCsvInt("1234567890");
	}	void tst_ReadCsvInt(String raw) {Tfds.Eq(Int_.parse_(raw), Bry_.ReadCsvInt(Bry_.new_utf8_(raw + "|"), Int_obj_ref.zero_(), (byte)'|'));}
	@Test  public void Trim() {
		Trim_tst("a b c", 1, 4, "b");
		Trim_tst("a  c", 1, 3, "");
		Trim_tst("  ", 0, 2, "");
	}	void Trim_tst(String raw, int bgn, int end, String expd) {Tfds.Eq(expd, String_.new_utf8_(Bry_.Trim(Bry_.new_utf8_(raw), bgn, end)));}
	@Test  public void Xto_int_lax() {
		tst_Xto_int_lax("12a", 12);
		tst_Xto_int_lax("1", 1);
		tst_Xto_int_lax("123", 123);
		tst_Xto_int_lax("a", 0);
		tst_Xto_int_lax("-1", -1);
	}
	private void tst_Xto_int_lax(String val, int expd)				{Tfds.Eq(expd, Bry_.Xto_int_or_lax(Bry_.new_utf8_(val), 0, String_.Len(val), 0));}
	@Test  public void Xto_int_or_trim() {
		tst_Xto_int_trim("123 "	, 123);
		tst_Xto_int_trim(" 123"	, 123);
		tst_Xto_int_trim(" 123 "	, 123);
		tst_Xto_int_trim(" 1 3 "	, -1);
	}
	private void tst_Xto_int_trim(String val, int expd)			{Tfds.Eq(expd, Bry_.Xto_int_or_trim(Bry_.new_utf8_(val), 0, String_.Len(val), -1));}
	@Test  public void Compare() {
		tst_Compare("abcde", 0, 1, "abcde", 0, 1, CompareAble_.Same);
		tst_Compare("abcde", 0, 1, "abcde", 1, 2, CompareAble_.Less);
		tst_Compare("abcde", 1, 2, "abcde", 0, 1, CompareAble_.More);
		tst_Compare("abcde", 0, 1, "abcde", 0, 2, CompareAble_.Less);
		tst_Compare("abcde", 0, 2, "abcde", 0, 1, CompareAble_.More);
		tst_Compare("abcde", 2, 3, "abçde", 2, 3, CompareAble_.Less);
	}	void tst_Compare(String lhs, int lhs_bgn, int lhs_end, String rhs, int rhs_bgn, int rhs_end, int expd) {Tfds.Eq(expd, Bry_.Compare(Bry_.new_utf8_(lhs), lhs_bgn, lhs_end, Bry_.new_utf8_(rhs), rhs_bgn, rhs_end));}
	@Test  public void Increment_last() {
		tst_IncrementLast(ary_(0), ary_(1));
		tst_IncrementLast(ary_(0, 255), ary_(1, 0));
		tst_IncrementLast(ary_(104, 111, 112, 101), ary_(104, 111, 112, 102));
	}
	byte[] ary_(int... ary) {
		byte[] rv = new byte[ary.length];
		for (int i = 0; i < ary.length; i++)
			rv[i] = Byte_.int_(ary[i]);
		return rv;
	}
	void tst_IncrementLast(byte[] ary, byte[] expd) {Tfds.Eq_ary(expd, Bry_.Increment_last(Bry_.Copy(ary)));}
	@Test   public void Split() {
		tst_Split("a|b|c"		, Byte_ascii.Pipe, "a", "b", "c");
		tst_Split("a|b|c|"		, Byte_ascii.Pipe, "a", "b", "c");
		tst_Split("|"			, Byte_ascii.Pipe, "");
		tst_Split(""			, Byte_ascii.Pipe);
	}
	void tst_Split(String raw_str, byte dlm, String... expd) {
		byte[][] actl_bry = Bry_.Split(Bry_.new_ascii_(raw_str), dlm);
		Tfds.Eq_ary_str(expd, String_.Ary(actl_bry));
	}
	@Test   public void Replace_between() {
		tst_Replace_between("a[0]b"					, "[", "]", "0", "a0b");
		tst_Replace_between("a[0]b[1]c"				, "[", "]", "0", "a0b0c");
		tst_Replace_between("a[0b"					, "[", "]", "0", "a[0b");
	}	public void tst_Replace_between(String src, String bgn, String end, String repl, String expd) {Tfds.Eq(expd, String_.new_ascii_(Bry_.Replace_between(Bry_.new_ascii_(src), Bry_.new_ascii_(bgn), Bry_.new_ascii_(end), Bry_.new_ascii_(repl))));}
	@Test    public void Replace() {
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		tst_Replace(tmp_bfr, "a0b"					,    "0", "00", "a00b");		// 1 -> 1
		tst_Replace(tmp_bfr, "a0b0c"				,    "0", "00", "a00b00c");		// 1 -> 2
		tst_Replace(tmp_bfr, "a00b00c"				,   "00",  "0", "a0b0c");		// 2 -> 1
		tst_Replace(tmp_bfr, "a0b0"					,    "0", "00", "a00b00");		// 1 -> 2; EOS
		tst_Replace(tmp_bfr, "a00b00"				,   "00",  "0", "a0b0");		// 2 -> 1; EOS
		tst_Replace(tmp_bfr, "a0b0"					,    "1",  "2", "a0b0");		// no match
		tst_Replace(tmp_bfr, "a0b0"					,   "b1", "b2", "a0b0");		// false match; EOS
	}
	public void tst_Replace(Bry_bfr tmp_bfr, String src, String bgn, String repl, String expd) {
		Tfds.Eq(expd, String_.new_ascii_(Bry_.Replace(tmp_bfr, Bry_.new_ascii_(src), Bry_.new_ascii_(bgn), Bry_.new_ascii_(repl))));
	}
	@Test  public void Split_bry() {
		Split_bry_tst("a|b|c|"		, "|"	, String_.Ary("a", "b", "c"));
		Split_bry_tst("a|"			, "|"	, String_.Ary("a"));
	}
	void Split_bry_tst(String src, String dlm, String[] expd) {
		String[] actl = String_.Ary(Bry_.Split(Bry_.new_ascii_(src), Bry_.new_ascii_(dlm)));
		Tfds.Eq_ary_str(expd, actl);
	}
	@Test   public void Split_lines() {
		Tst_split_lines("a\nb"		, "a", "b");					// basic
		Tst_split_lines("a\nb\n"	, "a", "b");					// do not create empty trailing lines
		Tst_split_lines("a\r\nb"	, "a", "b");					// crlf
		Tst_split_lines("a\rb"		, "a", "b");					// cr only
	}
	void Tst_split_lines(String src, String... expd) {		
		Tfds.Eq_ary(expd, New_ary(Bry_.Split_lines(Bry_.new_ascii_(src))));
	}
	String[] New_ary(byte[][] lines) {
		int len = lines.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++)
			rv[i] = String_.new_utf8_(lines[i]);
		return rv;
	}
	@Test   public void Add_w_dlm() {
		Tst_add_w_dlm(Byte_ascii.Pipe, String_.Ary("a", "b", "c")	, "a|b|c");					// basic
		Tst_add_w_dlm(Byte_ascii.Pipe, String_.Ary("a")				, "a");						// one item
		Tst_add_w_dlm(Byte_ascii.Pipe, String_.Ary("a", null, "c")	, "a||c");					// null
	}
	void Tst_add_w_dlm(byte dlm, String[] itms, String expd) {
		byte[] actl = Bry_.Add_w_dlm(dlm, Bry_.Ary(itms));
		Tfds.Eq(expd, String_.new_ascii_(actl));
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
		Tfds.Eq(expd, Bry_.Match_bwd_any(Bry_.new_ascii_(src), src_end, src_bgn, Bry_.new_ascii_(find)));
	}
	private ByteAry_fxt fxt = new ByteAry_fxt();
	@Test   public void Trim_end() {
		fxt.Test_trim_end("a "		, Byte_ascii.Space, "a");	// trim.one
		fxt.Test_trim_end("a   "	, Byte_ascii.Space, "a");	// trim.many
		fxt.Test_trim_end("a"		, Byte_ascii.Space, "a");	// trim.none
		fxt.Test_trim_end(""		, Byte_ascii.Space, "");	// empty
	}
	@Test  public void XtoByteAry() {
		fxt.Test_new_utf8_("a"		, Bry_.ints_(97));
		fxt.Test_new_utf8_("a b"	, Bry_.ints_(97, 32, 98));
		fxt.Test_new_utf8_("©"		, Bry_.ints_(194, 169));
	}
}
class ByteAry_fxt {
	public void Test_trim_end(String raw, byte trim, String expd) {
		byte[] raw_bry = Bry_.new_ascii_(raw);
		Tfds.Eq(expd, String_.new_utf8_(Bry_.Trim_end(raw_bry, trim, raw_bry.length)));
	}
	public void Test_new_utf8_(String raw, byte[] expd_bry) {
		Tfds.Eq_ary(expd_bry, Bry_.new_utf8_(raw));
	}
}
