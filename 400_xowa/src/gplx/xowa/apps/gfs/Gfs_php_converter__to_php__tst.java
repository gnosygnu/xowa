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
