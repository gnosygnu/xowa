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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Gfo_number_parser_tst {		
	@Before public void init() {fxt.Clear();} private final    Gfo_number_parser_fxt fxt = new Gfo_number_parser_fxt();
	@Test  public void Integer() {
		fxt.Test_int("1", 1);
		fxt.Test_int("1234", 1234);
		fxt.Test_int("1234567890", 1234567890);
		fxt.Test_int("-1234", -1234);
		fxt.Test_int("+1", 1);
		fxt.Test_int("00001", 1);
	}
	@Test  public void Long() {
		fxt.Test_long("9876543210", 9876543210L);
	}
	@Test  public void Decimal() {
		fxt.Test_dec("1.23", Decimal_adp_.parse("1.23"));
		fxt.Test_dec("1.023", Decimal_adp_.parse("1.023"));
		fxt.Test_dec("-1.23", Decimal_adp_.parse("-1.23"));
	}
	@Test  public void Double_long() {
		fxt.Test_dec(".42190046219457", Decimal_adp_.parse(".42190046219457"));
	}
	@Test  public void Exponent() {
		fxt.Test_int("1E2", 100);
		fxt.Test_dec("1.234E2", Decimal_adp_.parse("123.4"));
		fxt.Test_dec("1.234E-2", Decimal_adp_.parse(".01234"));
		fxt.Test_dec("123.4E-2", Decimal_adp_.parse("1.234"));
		fxt.Test_dec("+6.0E-3", Decimal_adp_.parse(".006"));
	}
	@Test  public void Err() {
		fxt.Test_err("+", true);
		fxt.Test_err("-", true);
		fxt.Test_err("a", true);
		fxt.Test_err("1-2", false);
		fxt.Test_err("1..1", true);
		fxt.Test_err("1,,1", true);
		fxt.Test_err("1", false);
	}
	@Test  public void Hex() {
		fxt.Test_hex("0x1"	, 1);
		fxt.Test_hex("0xF"	, 15);
		fxt.Test_hex("0x20"	, 32);
		fxt.Test_hex("x20"	, 0, false);
		fxt.Test_hex("d"	, 0, false);	// PURPOSE: d was being converted to 13; no.w:Hovedbanen; DATE:2014-04-13
	}
	@Test  public void Ignore() {
		fxt.Init_ignore("\n\t");
		fxt.Test_int("1"	, 1);
		fxt.Test_int("1\n"	, 1);
		fxt.Test_int("1\t"	, 1);
		fxt.Test_int("1\n2"	, 12);
		fxt.Test_err("1\r"	, true);
	}
}
class Gfo_number_parser_fxt {
	private final    Gfo_number_parser parser = new Gfo_number_parser();
	public void Clear() {parser.Clear();}
	public void Init_ignore(String chars) {parser.Ignore_chars_(Bry_.new_a7(chars));}
	public void Test_int(String raw, int expd) {
		byte[] raw_bry = Bry_.new_a7(raw);
		int actl = parser.Parse(raw_bry, 0, raw_bry.length).Rv_as_int(); 
		Tfds.Eq(expd, actl, raw);
	}
	public void Test_long(String raw, long expd) {
		byte[] raw_bry = Bry_.new_a7(raw);
		Tfds.Eq(expd, parser.Parse(raw_bry, 0, raw_bry.length).Rv_as_long(), raw);
	}
	public void Test_dec(String raw, Decimal_adp expd) {
		byte[] raw_bry = Bry_.new_a7(raw);
		Decimal_adp actl = parser.Parse(raw_bry, 0, raw_bry.length).Rv_as_dec(); 
		Tfds.Eq(expd.To_double(), actl.To_double(), raw);
	}
	public void Test_err(String raw, boolean expd) {
		byte[] raw_bry = Bry_.new_a7(raw);
		boolean actl = parser.Parse(raw_bry, 0, raw_bry.length).Has_err(); 
		Tfds.Eq(expd, actl, raw);
	}
	public void Test_hex(String raw, int expd_val) {Test_hex(raw, expd_val, true);}
	public void Test_hex(String raw, int expd_val, boolean expd_pass) {
		parser.Hex_enabled_(true);
		byte[] raw_bry = Bry_.new_a7(raw);
		int actl = parser.Parse(raw_bry, 0, raw_bry.length).Rv_as_int();
		if (expd_pass) {
			Tfds.Eq(expd_val, actl, raw);
			Tfds.Eq(true, !parser.Has_err());
		}
		else 
			Tfds.Eq(false, !parser.Has_err());
		parser.Hex_enabled_(false);
	}
}
