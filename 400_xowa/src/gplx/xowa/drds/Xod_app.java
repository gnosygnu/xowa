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
package gplx.xowa.drds; import gplx.*; import gplx.xowa.*;
import gplx.xowa.drds.pages.*;
import gplx.xowa.apps.*; import gplx.xowa.wikis.data.tbls.*;
public class Xod_app {
	private final Xoav_app app;
	private final Xod_page_mgr page_mgr = new Xod_page_mgr();
	public Xod_app(Xoav_app app) {
		this.app = app;
	}
	public Xod_page_itm Get_page(String wiki_domain, String page_ttl) {
		Xow_wiki wiki = app.Wiki_mgri().Get_by_key_or_make_init_y(Bry_.new_u8(wiki_domain));
		return page_mgr.Get_page(wiki, page_ttl);
	}
}
