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
public class Xoa_gfs_php_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoa_gfs_php_mgr_fxt fxt = new Xoa_gfs_php_mgr_fxt();
	@Test  public void Xto_gfs() {
		fxt.Test_Xto_gfs("a\\\\b\\'c\\\"d\\$e"		, "a\\b'c\"d$e");			// backslash.escape
		fxt.Test_Xto_gfs("\\"						, "\\");					// backslash.eos; eos, but nothing to escape; render self but dont fail
		fxt.Test_Xto_gfs("a~b"						, "a~~b");					// tilde.escape
		fxt.Test_Xto_gfs("a$1b"						, "a~{0}b");				// dollar
	}
	@Test  public void Xto_php() {
		fxt.Test_Xto_php_escape_y("a~{0}b"					, "a$1b");					// tilde.arg.one
		fxt.Test_Xto_php_escape_y("a~{0}b~{1}c~{2}d"		, "a$1b$2c$3d");			// tilde.arg.many
		fxt.Test_Xto_php_escape_y("a~{9}"					, "a$10");					// tilde.arg.9 -> 10
		fxt.Test_Xto_php_escape_y("a~~b"					, "a~b");					// tilde.escape
		fxt.Test_Xto_php_escape_y("a\\b'c\"d$e"				, "a\\\\b\\'c\\\"d\\$e");	// backslash.escape_y
		fxt.Test_Xto_php_escape_n("a\\b'c\"d$e"				, "a\\b'c\"d$e");			// backslash.escape_n
	}
}
class Xoa_gfs_php_mgr_fxt {
	private Bry_bfr bfr = Bry_bfr.new_();
	public void Clear() {}
	public void Test_Xto_gfs(String raw, String expd) {
		byte[] actl = Xoa_gfs_php_mgr.Xto_gfs(bfr, Bry_.new_u8(raw));
		Tfds.Eq(expd, String_.new_u8(actl));
	}
	public void Test_Xto_php_escape_y(String raw, String expd) {Test_Xto_php(raw, Bool_.Y, expd);}
	public void Test_Xto_php_escape_n(String raw, String expd) {Test_Xto_php(raw, Bool_.N, expd);}
	public void Test_Xto_php(String raw, boolean escape_backslash, String expd) {
		byte[] actl = Xoa_gfs_php_mgr.Xto_php(bfr, escape_backslash, Bry_.new_u8(raw));
		Tfds.Eq(expd, String_.new_u8(actl));
	}
}
