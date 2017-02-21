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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import org.junit.*;
public class IptKey__tst {
	private final    IptKey__fxt fxt = new IptKey__fxt();
	@Test  public void To_str() {
		fxt.Test_to_str(196608, "mod.cs");
	}
	@Test  public void To_str__numeric() {
		fxt.Test_to_str(12345, "key.#12345");
	}
	@Test   public void Parse() {
		fxt.Test_parse("key.#10", 10);
	}
}
class IptKey__fxt {
	public void Test_to_str(int keycode, String expd) {
		Tfds.Eq(expd, IptKey_.To_str(keycode));
	}
	public void Test_parse(String raw, int keycode) {
		Tfds.Eq(keycode, IptKey_.parse(raw).Val());
	}
}
