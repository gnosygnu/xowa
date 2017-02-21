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
public class XmlDoc_tst {
	String xml; XmlDoc xdoc; XmlNde xnde;
	@Test  public void parse() {
		xml = String_.Concat("<root/>");
		xdoc = XmlDoc_.parse(xml);
		Tfds.Eq("root", xdoc.Root().Name());
		Tfds.Eq(true, xdoc.Root().NdeType_element());
	}
	@Test  public void Xml_outer() {
		xml = String_.Concat
			( "<root>"
			,	"<a>"
			,		"<b/>"
			,		"<b/>"
			,	"</a>"
			, "</root>"
			);
		xdoc = XmlDoc_.parse(xml);
		xnde = xdoc.Root().SubNdes().Get_at(0);
		Tfds.Eq("a", xnde.Name());
		Tfds.Eq("<a><b/><b/></a>", xnde.Xml_outer());	
	}
	@Test  public void Text_inner() {
		xml = String_.Concat
			( "<root>"
			,	"<a>"
			,		"test me"
			,	"</a>"
			, "</root>"
			);
		xdoc = XmlDoc_.parse(xml);
		xnde = xdoc.Root().SubNdes().Get_at(0);
		Tfds.Eq("a", xnde.Name());
		Tfds.Eq("test me", xnde.Text_inner());
	}
	@Test  public void Atrs() {
		xml = String_.Concat
			( "<root atr0=\"0\" atr1=\"1\">"
			, "</root>"
			);
		xdoc = XmlDoc_.parse(xml);
		XmlAtrList atrs = xdoc.Root().Atrs();
		XmlAtr atr = atrs.Get_at(1);
		tst_Atr(atr, "atr1", "1");
		atr = atrs.Get_at(1);
		tst_Atr(atr, "atr1", "1");
	}
	void tst_Atr(XmlAtr atr, String expdName, String expdVal) {
		Tfds.Eq(expdName, atr.Name());
		Tfds.Eq(expdVal, atr.Value());
	}
}
