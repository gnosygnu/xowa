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
package gplx.gfui.draws; import gplx.*; import gplx.gfui.*;
import org.junit.*;
public class ColorAdp__tst {
	@Test  public void parse_hex_() {
		tst_parse_hex_("#00000000", 0, 0, 0, 0);
		tst_parse_hex_("#000102FF", 0, 1, 2, 255);
		tst_parse_hex_("#FF000102", 255, 0, 1, 2);
	}
	@Test  public void parse_int_() {
		tst_parse_int_(0, 0, 0, 0, 0);
		tst_parse_int_(255, 0, 0, 0, 255);
		tst_parse_int_(65535, 0, 0, 255, 255);
		tst_parse_int_(16777215, 0, 255, 255, 255);
		tst_parse_int_(Int_.Max_value, 127, 255, 255, 255);
		tst_parse_int_(-1, 255, 255, 255, 255);
	}
	@Test  public void parse() {
		tst_parse_("0,0,0,0", 0, 0, 0, 0);	// parse all ints
		tst_parse_("0,0,0", 255, 0, 0, 0);	// a=255, parse rest
		tst_parse_("255", 0, 0, 0, 255);	// parse as single int
	}
	void tst_parse_hex_(String raw, int a, int r, int g, int b) {
		ColorAdp color = ColorAdp_.parse_hex_(raw);
		tst_ColorAdp(color, a, r, g, b);
		Tfds.Eq(color.XtoHexStr(), raw);
	}
	void tst_parse_int_(int val, int a, int r, int g, int b) {
		ColorAdp color = ColorAdp_.new_int_(val);
		tst_ColorAdp(color, a, r, g, b);
		Tfds.Eq(color.Value(), val);
	}
	void tst_parse_(String s, int alpha, int red, int green, int blue) {tst_ColorAdp(ColorAdp_.parse(s), alpha, red, green, blue);}
	void tst_ColorAdp(ColorAdp color, int alpha, int red, int green, int blue) {
		TfdsTstr_fxt tstr = TfdsTstr_fxt.new_();
		tstr.Eq_str(color.Alpha(), alpha, "alpha");
		tstr.Eq_str(color.Red(), red, "red");
		tstr.Eq_str(color.Green(), green, "green");
		tstr.Eq_str(color.Blue(), blue, "blue");
		tstr.tst_Equal("color");
	}
}	
