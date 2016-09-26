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
public class Gfs_php_converter__to_php__tst {
	@Before public void init() {fxt.Clear();} private final    Gfs_php_converter_fxt fxt = new Gfs_php_converter_fxt();
	@Test  public void Xto_php() {
		fxt.Test_Xto_php_escape_y("a~{0}b"					, "a$1b");					// tilde.arg.one
		fxt.Test_Xto_php_escape_y("a~{0}b~{1}c~{2}d"		, "a$1b$2c$3d");			// tilde.arg.many
		fxt.Test_Xto_php_escape_y("a~{9}"					, "a$10");					// tilde.arg.9 -> 10
		fxt.Test_Xto_php_escape_y("a~~b"					, "a~b");					// tilde.escape
		fxt.Test_Xto_php_escape_y("a\\b'c\"d$e"				, "a\\\\b\\'c\\\"d\\$e");	// backslash.escape_y
		fxt.Test_Xto_php_escape_n("a\\b'c\"d$e"				, "a\\b'c\"d$e");			// backslash.escape_n
	}
}
