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
package gplx.xowa.drds.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.sections.*;
public class Xod_page_mgr {
	public Xod_page_itm Get_page(Xow_wiki wiki, String page_ttl) {
		Xod_page_itm rv = new Xod_page_itm();
		// load meta info like page_id, modified, etc
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(page_ttl));
		Xowd_page_itm dbpg = new Xowd_page_itm();
		wiki.Data__core_mgr().Tbl__page().Select_by_ttl(dbpg, ttl.Ns(), ttl.Page_db());
		rv.Init(ttl, dbpg);

		// load page data
		Xoh_page hpg = new Xoh_page();
		hpg.Init(wiki, Xoa_url.new_(wiki.Domain_bry(), ttl.Page_db()), ttl, 1);
		wiki.Html__hdump_mgr().Load_mgr().Load(hpg, ttl);
		Load_sections(rv, hpg);
		return rv;
	}
	private void Load_sections(Xod_page_itm rv, Xoh_page hpg) {
		Xoh_section_mgr section_mgr = hpg.Section_mgr();
		int len = section_mgr.Len();
		for (int i = 0; i < len; ++i) {
			Xoh_section_itm itm = section_mgr.Get_at(i);
			rv.Section_list().Add(itm);
		}
	}
}
