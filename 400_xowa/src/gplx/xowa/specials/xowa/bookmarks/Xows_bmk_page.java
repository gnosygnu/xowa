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
package gplx.xowa.specials.xowa.bookmarks; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.core.primitives.*;
public class Xows_bmk_page implements Xows_page {
	public Xows_special_meta Special_meta() {return Xows_special_meta_.Itm__bookmarks;}
	public void Special_gen(Xowe_wiki wiki, Xoae_page page, Xoa_url url, Xoa_ttl ttl) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
		wiki.App().User().User_db_mgr().Bmk_mgr().Get_all(tmp_bfr);
		page.Data_raw_(tmp_bfr.To_bry_and_rls());
		page.Html_data().Html_restricted_n_();
	}
}
