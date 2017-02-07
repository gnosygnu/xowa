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
package gplx.xowa.apps.gfs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Gfs_php_converter__to_gfs__tst {
	@Before public void init() {fxt.Clear();} private final    Gfs_php_converter_fxt fxt = new Gfs_php_converter_fxt();
	@Test  public void Escape_sequences() {
		fxt.Test__to_gfs("a\\\"b"					, "a\"b");
		fxt.Test__to_gfs("a\\'b"					, "a'b");
		fxt.Test__to_gfs("a\\$b"					, "a$b");
		fxt.Test__to_gfs("a\\\\b"					, "a\\b");
		fxt.Test__to_gfs("a\\nb"					, "a\nb");
		fxt.Test__to_gfs("a\\tb"					, "a\tb");
		fxt.Test__to_gfs("a\\rb"					, "a\rb");
		fxt.Test__to_gfs("a\\ b"					, "a\\ b");					// "\ " seems to be rendered literally; EX:"You do not need to put \ before a /."; PAGE:en.w:MediaWiki:Spam-whitelist; DATE:2016-09-12
		fxt.Test__to_gfs("a\\\\b\\'c\\\"d\\$e"		, "a\\b'c\"d$e");			// backslash.escape
		fxt.Test__to_gfs("\\"						, "\\");					// backslash.eos; eos, but nothing to escape; render self but dont fail
	}
	@Test  public void Tilde() {
		fxt.Test__to_gfs("a~b"						, "a~~b");					// tilde.escape
	}
	@Test  public void Arguments() {
		fxt.Test__to_gfs("a$1b"						, "a~{0}b");				// dollar
		fxt.Test__to_gfs("a $ b"					, "a $ b");					// noop
	}
}
class Gfs_php_converter_fxt {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Clear() {}
	public void Test__to_gfs(String raw, String expd) {
		byte[] actl = Gfs_php_converter.To_gfs(bfr, Bry_.new_u8(raw));
		Tfds.Eq(expd, String_.new_u8(actl));
	}
	public void Test_Xto_php_escape_y(String raw, String expd) {Test_Xto_php(raw, Bool_.Y, expd);}
	public void Test_Xto_php_escape_n(String raw, String expd) {Test_Xto_php(raw, Bool_.N, expd);}
	public void Test_Xto_php(String raw, boolean escape_backslash, String expd) {
		byte[] actl = Gfs_php_converter.Xto_php(bfr, escape_backslash, Bry_.new_u8(raw));
		Tfds.Eq(expd, String_.new_u8(actl));
	}
}
