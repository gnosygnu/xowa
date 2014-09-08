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
public class NumberParser_tst {
	private NumberParser parser = new NumberParser();
	@Test  public void Integer() {
		tst_Int("1", 1);
		tst_Int("1234", 1234);
		tst_Int("1234567890", 1234567890);
		tst_Int("-1234", -1234);
		tst_Int("+1", 1);
		tst_Int("00001", 1);
	}
	@Test  public void Decimal() {
		tst_Dec("1.23", DecimalAdp_.parse_("1.23"));
		tst_Dec("1.023", DecimalAdp_.parse_("1.023"));
		tst_Dec("-1.23", DecimalAdp_.parse_("-1.23"));
	}
	@Test  public void Double_long() {
		tst_Dec(".42190046219457", DecimalAdp_.parse_(".42190046219457"));
	}
	@Test  public void Exponent() {
		tst_Int("1E2", 100);
		tst_Dec("1.234E2", DecimalAdp_.parse_("123.4"));
		tst_Dec("1.234E-2", DecimalAdp_.parse_(".01234"));
		tst_Dec("123.4E-2", DecimalAdp_.parse_("1.234"));
		tst_Dec("+6.0E-3", DecimalAdp_.parse_(".006"));
	}
	@Test  public void Err() {
		tst_Err("+", true);
		tst_Err("-", true);
		tst_Err("a", true);
		tst_Err("1-2", false);
		tst_Err("1..1", true);
		tst_Err("1,,1", true);
		tst_Err("1", false);
	}
	@Test  public void Hex() {
		Hex_tst("0x1"	, 1);
		Hex_tst("0xF"	, 15);
		Hex_tst("0x20"	, 32);
		Hex_tst("x20"	, 0, false);
		Hex_tst("d"		, 0, false);	// PURPOSE: d was being converted to 13; no.w:Hovedbanen; DATE:2014-04-13
	}
	private void tst_Int(String raw, int expd) {
		byte[] raw_bry = Bry_.new_ascii_(raw);
		int actl = parser.Parse(raw_bry, 0, raw_bry.length).AsInt(); 
		Tfds.Eq(expd, actl, raw);
	}
	private void tst_Dec(String raw, DecimalAdp expd) {
		byte[] raw_bry = Bry_.new_ascii_(raw);
		DecimalAdp actl = parser.Parse(raw_bry, 0, raw_bry.length).AsDec(); 
		Tfds.Eq(expd.Xto_decimal(), actl.Xto_decimal(), raw);
	}
	private void tst_Err(String raw, boolean expd) {
		byte[] raw_bry = Bry_.new_ascii_(raw);
		boolean actl = parser.Parse(raw_bry, 0, raw_bry.length).HasErr(); 
		Tfds.Eq(expd, actl, raw);
	}
	private void Hex_tst(String raw, int expd_val) {Hex_tst(raw, expd_val, true);}
	private void Hex_tst(String raw, int expd_val, boolean expd_pass) {
		parser.Hex_enabled_(true);
		byte[] raw_bry = Bry_.new_ascii_(raw);
		int actl = parser.Parse(raw_bry, 0, raw_bry.length).AsInt();
		if (expd_pass) {
			Tfds.Eq(expd_val, actl, raw);
			Tfds.Eq(true, !parser.HasErr());
		}
		else 
			Tfds.Eq(false, !parser.HasErr());
		parser.Hex_enabled_(false);
	}
}
