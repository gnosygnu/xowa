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
package gplx.xowa.langs.names; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
import gplx.core.tests.*;
public class Strcpn_tst {
	private final    Strcpn_fxt fxt = new Strcpn_fxt();

	@Test  public void Single() {
		fxt.Test__exec(0, "a"			, "a__");
		fxt.Test__exec(1, "a"			, "_a_");
		fxt.Test__exec(2, "a"			, "__a");
		fxt.Test__exec(3, "a"			, "___");
	}
	@Test  public void Multiple() {
		fxt.Test__exec(2, "ab"			, "__a");
		fxt.Test__exec(2, "ab"			, "__b");
		fxt.Test__exec(1, "ab"			, "_ba");
		fxt.Test__exec(3, "ab"			, "___");
	}
	@Test  public void Utf8() {
		fxt.Test__exec(3, "d"			, "ab¢d"); // len=2
		fxt.Test__exec(3, "d"			, "ab€d"); // len=3
		fxt.Test__exec(4, "z"			, "ab€d"); // len=3
	}
	@Test  public void Php_doc() { // REF: http://php.net/manual/en/function.strcspn.php
		fxt.Test__exec_rev(0, "abcd"			, "apple");
		fxt.Test__exec_rev(0, "abcd"			, "banana");
		fxt.Test__exec_rev(2, "hello"			, "l");
		fxt.Test__exec_rev(2, "hello"			, "world");
		fxt.Test__exec_rev(5, "hello"			, "na");
		// fxt.Test__exec(0, "abcdhelloabcd"	, "l"		, -9);
		// fxt.Test__exec(0, "abcdhelloabcd"	, "l"		, -9, -5);
	}
}
class Strcpn_fxt {
	public void Test__exec_rev(int expd, String subject, String mask) {Test__exec(expd, mask, subject);}
	public void Test__exec(int expd, String mask, String subject) {
		Strcpn mgr = Strcpn.New_by_concatenated_ascii(mask);
		Gftest.Eq__int(expd, mgr.Exec(Bry_.new_u8(subject)));
	}
}
