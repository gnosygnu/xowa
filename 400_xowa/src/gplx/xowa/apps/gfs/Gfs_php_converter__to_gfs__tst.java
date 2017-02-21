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
