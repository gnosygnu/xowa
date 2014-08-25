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
package gplx.xowa.hdumps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
 import gplx.xowa.hdumps.core.*;
public class Hdump_html_mgr {
	private Hdump_html_fmtr__sidebars sidebars_fmtr = new Hdump_html_fmtr__sidebars();
	private Hdump_html_fmtr__body body_fmtr = new Hdump_html_fmtr__body();
	public Hdump_html_mgr Init_by_app(Gfo_usr_dlg usr_dlg, byte[] file_dir) {body_fmtr.Init_by_app(usr_dlg, file_dir); return this;}
	public Bry_fmtr Skin_fmtr() {return skin_fmtr;} private Bry_fmtr skin_fmtr = Bry_fmtr.new_("~{display_ttl}~{content_sub}~{sidebar_divs}~{body_html}", "display_ttl", "content_sub", "sidebar_divs", "body_html");
	public void Write(Bry_bfr bfr, Xow_wiki wiki, Hdump_page_itm page) {
		body_fmtr.Init_by_page(wiki, page);
		sidebars_fmtr.Init_by_page(page);
		skin_fmtr.Bld_bfr_many(bfr, page.Display_ttl(), page.Content_sub(), sidebars_fmtr, body_fmtr);
	}
}
