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
package gplx.core.encoders; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.tests.*;
public class Hex_utl__tst {
	private final    Hex_utl__fxt fxt = new Hex_utl__fxt();
	@Test  public void To_int() {
		fxt.Test__to_int("0"		, 0);
		fxt.Test__to_int("F"		, 15);
		fxt.Test__to_int("0F"		, 15);
		fxt.Test__to_int("10"		, 16);
		fxt.Test__to_int("20"		, 32);
		fxt.Test__to_int("FF"		, 255);
		fxt.Test__to_int("100"		, 256);
		fxt.Test__to_int("0a"		, 10);
		fxt.Test__to_int("7FFFFFFF"	, Int_.Max_value);
		fxt.Test__to_int_bry("100"	, 256);
	}
	@Test  public void To_str() {
		fxt.Test__to_str(0			, "0");
		fxt.Test__to_str(15			, "F");
		fxt.Test__to_str(16			, "10");
		fxt.Test__to_str(32			, "20");
		fxt.Test__to_str(255		, "FF");
		fxt.Test__to_str(Int_.Max_value, "7FFFFFFF");

		fxt.Test__to_str(15, 2		, "0F");
		fxt.Test__to_str(15, 3		, "00F");
	}
	@Test   public void Write() {
		fxt.Test__write("[00000000]", 1, 9,  15, "[0000000F]");
		fxt.Test__write("[00000000]", 1, 9, 255, "[000000FF]");
	}
	@Test   public void Write_bfr() {
		fxt.Test__write_bfr(Bool_.Y,              0, "0");
		fxt.Test__write_bfr(Bool_.Y,             15, "f");
		fxt.Test__write_bfr(Bool_.Y,             16, "10");
		fxt.Test__write_bfr(Bool_.Y,             32, "20");
		fxt.Test__write_bfr(Bool_.Y,            255, "ff");
		fxt.Test__write_bfr(Bool_.Y,            256, "100");
		fxt.Test__write_bfr(Bool_.Y, Int_.Max_value, "7fffffff");
	}
	@Test   public void Encode() {
		fxt.Test__parse_hex_to_bry("E2A7BC", 226, 167, 188);
	}
}
class Hex_utl__fxt {
	public void Test__write(String s, int bgn, int end, int val, String expd) {
		byte[] bry = Bry_.new_a7(s);
		Hex_utl_.Write(bry, bgn, end, val);
		Tfds.Eq(expd, String_.new_a7(bry));
	}
	public void Test__to_int(String raw, int expd) {
		int actl = Hex_utl_.Parse(raw);
		Tfds.Eq(expd, actl);
	}
	public void Test__to_int_bry(String raw, int expd) {Tfds.Eq(expd, Hex_utl_.Parse_or(Bry_.new_a7(raw), -1));}
	public void Test__to_str(int val, String expd) {Test__to_str(val, 0, expd);}
	public void Test__to_str(int val, int pad, String expd) {
		String actl = Hex_utl_.To_str(val, pad);
		Tfds.Eq(expd, actl);
	}
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Test__write_bfr(boolean lcase, int val, String expd) {
		Hex_utl_.Write_bfr(bfr, lcase, val);
		Gftest.Eq__str(expd, bfr.To_str_and_clear());
	}
	public void Test__encode_bry(String val, int... expd) {
		byte[] actl = Hex_utl_.Encode_bry(Bry_.new_u8(val));
		Gftest.Eq__ary(Byte_.Ary_by_ints(expd), actl, "encode");
	}
	public void Test__parse_hex_to_bry(String val, int... expd) {
		byte[] actl = Hex_utl_.Parse_hex_to_bry(Bry_.new_u8(val));
		Gftest.Eq__ary(Byte_.Ary_by_ints(expd), actl, "encode");
	}
}
