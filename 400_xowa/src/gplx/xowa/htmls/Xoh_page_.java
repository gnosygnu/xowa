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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*;
class Xoh_page_ {
	public static byte[] Save_sidebars(Bry_bfr tmp_bfr, Xoae_page page, Xopg_html_data html_data) {
		Xopg_xtn_skin_mgr mgr = html_data.Xtn_skin_mgr();
		int len = mgr.Count();
		boolean sidebar_exists = false;
		for (int i = 0; i < len; ++i) {
			Xopg_xtn_skin_itm itm = mgr.Get_at(i);
			if (itm.Tid() == Xopg_xtn_skin_itm_tid.Tid_sidebar) {
				sidebar_exists = true;
				itm.Write(tmp_bfr, page);
			}
		}
		return sidebar_exists ? tmp_bfr.To_bry_and_clear() : null;
	}
}
