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
package gplx.xmls; import gplx.*;
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
