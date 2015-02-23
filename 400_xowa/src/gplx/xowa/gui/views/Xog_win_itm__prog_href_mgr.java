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
package gplx.xowa.gui.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.gfui.*;
public class Xog_win_itm__prog_href_mgr {
	private static Xoh_href		tmp_href	= new Xoh_href();
	private static Bry_bfr	tmp_bfr		= Bry_bfr.reset_(512);
	public static void Print(Xog_win_itm win) {	// PURPOSE: print href in prog box when in content editable mode
		String href = win.Active_html_box().Html_active_atr_get_str(Gfui_html.Atr_href, "");// get selected href from html_box
		href = Xoa_app_.Utl_encoder_mgr().Href().Decode_str(href);								// remove url encodings
		if (!String_.Eq(href, win.Prog_box().Text()))
			win.Usr_dlg().Prog_direct(href);
	}
	public static void Hover(Xoae_app app, Xowe_wiki wiki, Xoae_page page, String href) {
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		if (	String_.Len_eq_0(href)			// href is null / empty; occurs when hovering over empty space
			||	String_.Eq(href, "file:///")) {
			usr_dlg.Prog_direct("");			// clear out previous entry
			return;
		}
		app.Href_parser().Parse(tmp_href, href, wiki, page.Ttl().Page_url());
		tmp_href.Print_to_bfr(tmp_bfr, wiki.Gui_mgr().Cfg_browser().Link_hover_full());
		usr_dlg.Prog_direct(tmp_bfr.Xto_str_and_clear());
	}
}
