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
package gplx.core.stores.xmls; import gplx.*; import gplx.core.*; import gplx.core.stores.*;
import org.junit.*;
public class XmlDataWtr_tst {
	@Before public void setup() {
		wtr = XmlDataWtr.new_();
	}
	@Test  public void WriteNodeBgn() {
		wtr.WriteNodeBgn("chapter");
		tst_XStr(wtr, "<chapter />", String_.CrLf);
	}
	@Test  public void Attributes() {
		wtr.WriteNodeBgn("chapter");
		wtr.WriteData("id", 1);
		wtr.WriteData("name", "first");
		tst_XStr(wtr, "<chapter id=\"1\" name=\"first\" />", String_.CrLf);
	}
	@Test  public void Subs() {
		wtr.WriteNodeBgn("title");
		wtr.WriteNodeBgn("chapters");
		wtr.WriteNodeBgn("chapter");
		tst_XStr(wtr
			,	"<title>", String_.CrLf
			,		"<chapters>", String_.CrLf
			,			"<chapter />", String_.CrLf
			,		"</chapters>", String_.CrLf
			,	"</title>", String_.CrLf
			);
	}
	@Test  public void Subs_Iterate() {
		wtr.WriteNodeBgn("titles");
		for (int title = 1; title <= 2; title++) {
			wtr.WriteNodeBgn("title");
			wtr.WriteData("id", title);
			wtr.WriteNodeBgn("chapters");
			wtr.WriteNodeEnd();	// chapters
			wtr.WriteNodeEnd();	// title
		}
		wtr.WriteNodeEnd();		//titles
		tst_XStr(wtr
			,	"<titles>", String_.CrLf
			,		"<title id=\"1\">", String_.CrLf
			,			"<chapters />", String_.CrLf
			,		"</title>", String_.CrLf
			,		"<title id=\"2\">", String_.CrLf
			,			"<chapters />", String_.CrLf
			,		"</title>", String_.CrLf
			,	"</titles>", String_.CrLf
			);
	}
	@Test  public void Peers() {
		wtr.WriteNodeBgn("title");
		wtr.WriteNodeBgn("chapters");
		wtr.WriteNodeEnd();
		wtr.WriteNodeBgn("audioStreams");
		tst_XStr(wtr
			,	"<title>", String_.CrLf
			,		"<chapters />", String_.CrLf
			,		"<audioStreams />", String_.CrLf
			,	"</title>", String_.CrLf
			);
	}
	@Test  public void AtrsWithNesting() {
		wtr.WriteNodeBgn("title");
		wtr.WriteData("id", 1);
		wtr.WriteData("name", "first");
		wtr.WriteNodeBgn("chapters");
		tst_XStr(wtr
			,	"<title id=\"1\" name=\"first\">", String_.CrLf
			,		"<chapters />", String_.CrLf
			,	"</title>", String_.CrLf
		);
	}
	void tst_XStr(XmlDataWtr wtr, String... parts) {
		String expd = String_.Concat(parts);
		Tfds.Eq(expd, wtr.To_str());
	}
	XmlDataWtr wtr;
}
