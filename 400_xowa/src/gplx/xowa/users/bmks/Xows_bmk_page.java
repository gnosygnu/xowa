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
package gplx.xowa.users.bmks; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.primitives.*;
import gplx.xowa.html.bridges.dbuis.tbls.*;
import gplx.xowa.users.data.*; import gplx.xowa.specials.*;
public class Xows_bmk_page implements Xows_page {
	public Xows_special_meta Special_meta() {return Xows_special_meta_.Itm__bookmarks;}
	public void Special_gen(Xowe_wiki wiki, Xoae_page page, Xoa_url url, Xoa_ttl ttl) {
		Xoa_app app = wiki.App();
		Dbui_tbl_itm__bmk ui_tbl = Dbui_tbl_itm__bmk.get_or_new(app, app.User().User_db_mgr().Bmk_mgr().Tbl__itm());
		page.Html_data().Head_mgr().Itm__dbui().Init(app).Enabled_y_();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_m001();
		ui_tbl.Select(bfr, Xoud_bmk_mgr.Owner_root);
		page.Hdump_data().Body_(bfr.To_bry_and_rls());
	}
}
