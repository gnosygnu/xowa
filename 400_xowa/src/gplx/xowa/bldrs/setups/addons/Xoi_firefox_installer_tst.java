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
package gplx.xowa.bldrs.setups.addons; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
import org.junit.*;
import gplx.core.ios.*;
public class Xoi_firefox_installer_tst {
	private Xoi_firefox_pref_fxt fxt = new Xoi_firefox_pref_fxt();
	@Test  public void Pref_update() {
		fxt.Test_pref_update(String_.Concat_lines_nl
		(	"pref(\"key_0\", \"val_0\"); // comment_0"
		,	"pref(\"key_1\", \"val_1\"); // comment_1"
		,	"pref(\"key_2\", \"val_2\"); // comment_2"
		)
		,	"key_1", "val_1_updated"
		,	String_.Concat_lines_nl
		(	"pref(\"key_0\", \"val_0\"); // comment_0"
		,	"pref(\"key_1\", \"val_1_updated\");"
		,	"pref(\"key_2\", \"val_2\"); // comment_2"
		)
		);
	}
}
class Xoi_firefox_pref_fxt {
	public void Test_pref_update(String src, String key, String val, String expd) {
		String actl = Xoi_firefox_installer.Pref_update(src, key, val);
		Tfds.Eq_str_lines(expd, actl);
	}
}
