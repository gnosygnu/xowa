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
package gplx.xowa.dbs.hdumps.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.hdumps.*;
class Hdump_html_mgr {
	private Hdump_html_fmtr__sidebars sidebars_fmtr = new Hdump_html_fmtr__sidebars();
	private Hdump_html_fmtr__body body_fmtr = new Hdump_html_fmtr__body();
	public void Init_by_app(Gfo_usr_dlg usr_dlg, byte[] app_dir) {body_fmtr.Init_by_app(usr_dlg, app_dir);}
	public void Write(Bry_bfr bfr, Bry_fmtr skin_fmtr, Hdump_page_itm page) {
		body_fmtr.Init_by_page(page);
		sidebars_fmtr.Init_by_page(page);
		skin_fmtr.Bld_bfr_many(bfr, page.Display_ttl(), page.Content_sub(), sidebars_fmtr, body_fmtr);
	}
}
