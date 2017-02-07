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
package gplx.xowa.addons.bldrs.exports.splits.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.htmls.core.dbs.*;
public class Xoh_src_tbl_mgr {
	private final    Xow_wiki wiki;
	private final    Ordered_hash hash = Ordered_hash_.New();
	public Xoh_src_tbl_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
	}
	public Xoh_page_tbl_itm Get_or_load(int id) {
		Xoh_page_tbl_itm rv = (Xoh_page_tbl_itm)hash.Get_by(id);
		if (rv == null) {
			Xow_db_file html_db = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(id);
			rv = new Xoh_page_tbl_itm(Bool_.N, id, html_db.Conn());
			hash.Add(id, rv);
		}
		return rv;
	}
	public void Cleanup() {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Xoh_page_tbl_itm itm = (Xoh_page_tbl_itm)hash.Get_at(i);
			itm.Rls();
		}
		hash.Clear();
	}
}
