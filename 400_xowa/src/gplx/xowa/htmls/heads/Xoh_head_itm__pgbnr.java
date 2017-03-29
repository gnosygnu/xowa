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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.guis.*;
public class Xoh_head_itm__pgbnr extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__pgbnr;}
	@Override public int Flags() {return Flag__css_include;}
	@Override public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url__css__styles == null) {
			Url__css__oojs_ui	= app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "lib", "oojs-ui", "oojs-ui-core-mediawiki.css").To_http_file_bry();	// needed for icons; should move to own file
			Io_url resources_dir = app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "xtns", "WikidataPageBanner", "resources");
			Url__css__styles	= resources_dir.GenSubFil_nest("ext.WikidataPageBanner.styles"		, "ext.WikidataPageBanner.css").To_http_file_bry();
			Url__css__toc		= resources_dir.GenSubFil_nest("ext.WikidataPageBanner.toc.styles"	, "ext.WikidataPageBanner.toc.css").To_http_file_bry();
			Url__css__bottomtoc = resources_dir.GenSubFil_nest("ext.WikidataPageBanner.toc.styles"	, "ext.WikidataPageBanner.bottomtoc.css").To_http_file_bry();
		}
		wtr.Write_css_include(Url__css__oojs_ui);
		wtr.Write_css_include(Url__css__styles);
		wtr.Write_css_include(Url__css__toc);
		wtr.Write_css_include(Url__css__bottomtoc);
	}
	private static byte[] Url__css__styles, Url__css__toc, Url__css__bottomtoc, Url__css__oojs_ui;
}
