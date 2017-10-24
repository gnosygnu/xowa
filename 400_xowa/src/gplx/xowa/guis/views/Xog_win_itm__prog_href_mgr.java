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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.controls.standards.*; import gplx.xowa.htmls.hrefs.*;
public class Xog_win_itm__prog_href_mgr {
	public static void Print(Xog_win_itm win) {	// PURPOSE: print href in prog box when in content editable mode
		String href = win.Active_html_box().Html_js_eval_proc_as_str(Xog_js_procs.Selection__get_active_for_editable_mode, Gfui_html.Atr_href, "");// get selected href from html_box
		href = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Decode_str(href);								// remove url encodings
		if (!String_.Eq(href, win.Prog_box().Text()))
			win.Usr_dlg().Prog_direct(href);
	}
	public static void Hover(Xoae_app app, boolean show_status_url, Xowe_wiki wiki, Xoae_page page, String href) {
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		if (	String_.Len_eq_0(href)			// href is null / empty; occurs when hovering over empty space
			||	String_.Eq(href, "file:///")) {
			usr_dlg.Prog_direct("");			// clear out previous entry
			return;
		}
		Xoa_url url = Xoa_url.blank();
		app.Html__href_parser().Parse_as_url(url, Bry_.new_u8(href), wiki, page.Ttl().Page_txt());
		usr_dlg.Prog_direct(String_.new_u8(url.To_bry(!show_status_url, Bool_.Y)));
	}
}
