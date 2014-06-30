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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xof_img_wkr_api_size_base_tst {
	Xof_img_wkr_api_size_base_fxt fxt = new Xof_img_wkr_api_size_base_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Bld_api_url() {
		fxt.Bld_api_url_tst("A.png"		, 220, 110, "http://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A.png&iiurlwidth=220&iiurlheight=110");
		fxt.Bld_api_url_tst("A.png"		, 220,   0, "http://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A.png&iiurlwidth=220");
		fxt.Bld_api_url_tst("A.png"		,   0, 110, "http://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A.png");	// assert that null width does not write height
		fxt.Bld_api_url_tst("A b.png"	, 220,   0, "http://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A_b.png&iiurlwidth=220");
		fxt.Bld_api_url_tst("A&b.png"	, 220,   0, "http://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A%26b.png&iiurlwidth=220");
	}
	@Test  public void Parse_size() {
		String raw = "<api><query><pages><page ns=\"6\" title=\"File:A.png\" missing=\"\" imagerepository=\"shared\"><imageinfo><ii size=\"1234\" width=\"220\" height=\"110\" /></imageinfo></page></pages></query></api>";
		fxt.Parse_size_tst(raw, 220, 110);
	}
	@Test  public void Parse_reg() {
		String raw = "<api><query><pages><page ns=\"6\" title=\"File:A.png\" missing=\"\" imagerepository=\"shared\"><imageinfo><ii descriptionurl=\"http://commons.wikimedia.org/wiki/File:Berkheyde-Haarlem.png\" /></imageinfo></page></pages></query></api>";
		fxt.Parse_reg_tst(raw, "commons.wikimedia.org", "Berkheyde-Haarlem.png");
	}
}
