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
package gplx.xowa.htmls.heads;
import gplx.libs.files.Io_url;
import gplx.xowa.*;
public class Xoh_head_itm__tabber extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__tabber;}
	@Override public int Flags() {return Flag__css_include | Flag__js_tail_script;}
	@Override public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url__css == null) {
			Io_url tabber_dir = app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "xtns", "wikia", "Tabber");
			Url__js			= tabber_dir.GenSubFil_nest("js" , "tabber.js").To_http_file_bry();
			Url__css		= tabber_dir.GenSubFil_nest("css", "tabber.css").To_http_file_bry();
		}
		wtr.Write_css_include(Url__css);
	}
	@Override public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_tail_load_lib(Url__js);
	}
	private static byte[] Url__css, Url__js;
}
