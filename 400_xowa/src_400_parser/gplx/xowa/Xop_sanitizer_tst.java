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
package gplx.xowa; import gplx.*;
import org.junit.*;
import gplx.xowa.parsers.amps.*;
public class Xop_sanitizer_tst {
	Xop_sanitizer_fxt fxt = new Xop_sanitizer_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Space()						{fxt.tst_Escape_id("a b"						, "a_b");}
	@Test  public void Colon()						{fxt.tst_Escape_id("a%3Ab"						, "a:b");}
	@Test  public void Percent()					{fxt.tst_Escape_id("a%b"						, "a%b");}
	@Test  public void Amp_eos()					{fxt.tst_Escape_id("a&"							, "a&");}
	@Test  public void Amp_unrecognized()			{fxt.tst_Escape_id("a&bcd"						, "a&bcd");}
	@Test  public void Amp_name()					{fxt.tst_Escape_id("a&lt;b"						, "a<b");}
	@Test  public void Amp_ncr_dec_pass()			{fxt.tst_Escape_id("a&#33;b"					, "a!b");}
	@Test  public void Amp_ncr_dec_fail()			{fxt.tst_Escape_id("a&#33x;b"					, "a&#33x;b");}
	@Test  public void Amp_ncr_hex_pass()			{fxt.tst_Escape_id("a&#x21;b"					, "a!b");}
}
class Xop_sanitizer_fxt {
	public Xop_sanitizer sanitizer;
	public void Clear() {
		if (sanitizer != null) return;
		sanitizer = new Xop_sanitizer(new Xop_amp_mgr(), new Gfo_msg_log(Xoa_app_.Name));
	}
	public void tst_Escape_id(String raw, String expd)  {
		byte[] raw_bry = Bry_.new_utf8_(raw);
		Tfds.Eq(expd, String_.new_utf8_(sanitizer.Escape_id(raw_bry)));
	}
}
