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
public class Xpath__tst {
	@Test public void Select_all() {
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
		XmlDoc xdoc = XmlDoc_.parse_(raw);
		XmlNdeList xndeList = Xpath_.SelectAll(xdoc.Root(), xpath);
		Tfds.Eq(expdCount, xndeList.Count());
	}
}
