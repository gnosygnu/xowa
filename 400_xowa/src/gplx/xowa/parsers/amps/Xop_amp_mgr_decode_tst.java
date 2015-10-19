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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_amp_mgr_decode_tst {
	@Before public void init() {fxt.Reset();} private Xop_amp_mgr_fxt fxt = new Xop_amp_mgr_fxt();
	@Test  public void Text()						{fxt.Test_decode_as_bry("a"					, "a");}
	@Test  public void Name()						{fxt.Test_decode_as_bry("&amp;"				, "&");}
	@Test  public void Name_w_text()				{fxt.Test_decode_as_bry("a&amp;b"			, "a&b");}
	@Test  public void Name_fail_semic_missing()	{fxt.Test_decode_as_bry("a&ampb"			, "a&ampb");}
	@Test  public void Name_fail_amp_only()			{fxt.Test_decode_as_bry("a&"				, "a&");}
	@Test  public void Num_fail()					{fxt.Test_decode_as_bry("&#!;"				, "&#!;");}		// ! is not valid num
	@Test  public void Hex_fail()					{fxt.Test_decode_as_bry("&#x!;"				, "&#x!;");}	// ! is not valid hex
	@Test  public void Num_basic()					{fxt.Test_decode_as_bry("&#0931;"			, "Σ");}
	@Test  public void Num_zero_padded()			{fxt.Test_decode_as_bry("&#00931;"			, "Σ");}
	@Test  public void Hex_upper()					{fxt.Test_decode_as_bry("&#x3A3;"			, "Σ");}
	@Test  public void Hex_lower()					{fxt.Test_decode_as_bry("&#x3a3;"			, "Σ");}
	@Test  public void Hex_zero_padded()			{fxt.Test_decode_as_bry("&#x03a3;"			, "Σ");}
	@Test  public void Hex_upper_x()				{fxt.Test_decode_as_bry("&#X3A3;"			, "Σ");}
	@Test  public void Num_fail_large_codepoint()	{fxt.Test_decode_as_bry("&#538189831;"		, "&#538189831;");}
	@Test  public void Num_ignore_extra_x()			{fxt.Test_decode_as_bry("&#xx26D0;"			, Char_.To_str(Char_.By_int(9936)));}	// 2nd x is ignored
}
class Xop_amp_mgr_fxt {
	private Xop_amp_mgr amp_mgr = Xop_amp_mgr.Instance;
	public void Reset() {}
	public void Test_decode_as_bry(String raw, String expd) {
		Tfds.Eq(expd, String_.new_u8(amp_mgr.Decode_as_bry(Bry_.new_u8(raw))));
	}
}
