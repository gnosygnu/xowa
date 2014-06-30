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
package gplx.stores.xmls; import gplx.*; import gplx.stores.*;
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
		Tfds.Eq(expd, wtr.XtoStr());
	}
	XmlDataWtr wtr;
}
