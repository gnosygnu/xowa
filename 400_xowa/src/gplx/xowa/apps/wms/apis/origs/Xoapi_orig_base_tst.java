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
package gplx.xowa.apps.wms.apis.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import org.junit.*;
// https://en.wikipedia.org/w/api.php?action=query&prop=revisions&titles=Main Page&rvprop=timestamp|content
public class Xoapi_orig_base_tst {
	Xoapi_orig_base_fxt fxt = new Xoapi_orig_base_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Bld_api_url() {
		fxt.Bld_api_url_tst("A.png"		, 220, 110, "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A.png&iiurlwidth=220&iiurlheight=110");
		fxt.Bld_api_url_tst("A.png"		, 220,   0, "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A.png&iiurlwidth=220");
		fxt.Bld_api_url_tst("A.png"		,   0, 110, "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A.png");	// assert that null width does not write height
		fxt.Bld_api_url_tst("A b.png"	, 220,   0, "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A_b.png&iiurlwidth=220");
		fxt.Bld_api_url_tst("A&b.png"	, 220,   0, "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=imageinfo&iiprop=size|url&redirects&titles=File:A%26b.png&iiurlwidth=220");
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
class Xoapi_orig_base_fxt {
	private Xoae_app app; private Xowe_wiki wiki; private Xoapi_orig_rslts rv = new Xoapi_orig_rslts();
	public void Clear() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
	}
	public void Bld_api_url_tst(String ttl_str, int w, int h, String expd) {
		String actl = Xoapi_orig_wmf.Bld_api_url(wiki.Domain_bry(), Bry_.new_u8(ttl_str), w, h);
		Tfds.Eq(expd, actl);
	}
	public void Parse_size_tst(String xml_str, int expd_w, int expd_h) {
		byte[] xml_bry = Bry_.new_u8(xml_str);
		Xoapi_orig_wmf.Parse_xml(rv, app.Usr_dlg(), xml_bry);
		Tfds.Eq(expd_w, rv.Orig_w());
		Tfds.Eq(expd_h, rv.Orig_h());
	}
	public void Parse_reg_tst(String xml_str, String expd_wiki, String expd_page) {
		byte[] xml_bry = Bry_.new_u8(xml_str);
		Xoapi_orig_wmf.Parse_xml(rv, app.Usr_dlg(), xml_bry);
		Tfds.Eq(expd_wiki, String_.new_u8(rv.Orig_wiki()));
		Tfds.Eq(expd_page, String_.new_u8(rv.Orig_page()));
	}
}
