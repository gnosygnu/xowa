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
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Xpath__tst {
	@Test  public void Select_all() {
		String xml = String_.Concat
			( "<root>"
			,	"<a>"
			,	"</a>"
			,	"<b>"
			,		"<c/>"
			,		"<c/>"
			,		"<c/>"
			,	"</b>"
			,	"<a>"
			,	"</a>"
			, "</root>"
			);
		tst_SelectAll(xml, "a", 2);
		tst_SelectAll(xml, "b", 1);
		tst_SelectAll(xml, "b/c", 3);
	}
	void tst_SelectAll(String raw, String xpath, int expdCount) {
		XmlDoc xdoc = XmlDoc_.parse(raw);
		XmlNdeList xndeList = Xpath_.SelectAll(xdoc.Root(), xpath);
		Tfds.Eq(expdCount, xndeList.Count());
	}
}
