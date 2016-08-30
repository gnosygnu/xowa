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
package gplx.xowa.apps.wms.apis.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import gplx.core.tests.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
class Wm_page_loader_fxt {
	private final    Wm_page_loader mgr = new Wm_page_loader();		
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Xoh_page hpg = new Xoh_page();
	private Xowe_wiki wiki;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		hpg.Clear();
	}
	public Wm_page_loader_fxt Exec__parse(String raw) {
		mgr.Parse(wiki, hpg, Bry_.new_u8(raw));
		return this;
	}
	public Wm_page_loader_fxt Test__html(String expd) {
		Gftest.Eq__ary__lines(expd, hpg.Db().Html().Html_bry(), "converted html");
		return this;
	}
	public Xof_fsdb_itm Make__fsdb(boolean repo_is_commons, boolean file_is_thumb, String file_ttl, int file_w, double file_time, int file_page) {
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_by_wm_parse(wiki.Domain_itm().Abrv_xo(), repo_is_commons, file_is_thumb, Bry_.new_u8(file_ttl), file_w, file_time, file_page);
		return itm;
	}
	public Wm_page_loader_fxt Test__fsdb(Xof_fsdb_itm expd) {
		Xof_fsdb_itm actl = (Xof_fsdb_itm)hpg.Img_mgr().Get_at(0);
		Gftest.Eq__str(Xowm_parse_mgr_fxt.To_str(tmp_bfr, expd), Xowm_parse_mgr_fxt.To_str(tmp_bfr, actl));
		return this;
	}
}
