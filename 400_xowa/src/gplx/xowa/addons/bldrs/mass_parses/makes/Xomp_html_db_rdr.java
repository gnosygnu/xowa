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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*; 
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
class Xomp_html_db_rdr {
	private final    Xowd_html_tbl[] src_tbls;
	private final    Xomp_db_core db;
	public Xomp_html_db_rdr(Xowe_wiki wiki) {
		this.db = Xomp_db_core.New__load(wiki);
		this.src_tbls = new Xowd_html_tbl[db.Wkr_count()];
	}
	public void Rows__get(Xowd_html_row rv, int wkr_id, int page_id) {
		Xowd_html_tbl src_tbl = src_tbls[wkr_id];
		if (src_tbl == null) {
			Db_conn wkr_conn = db.Wkr_db(Bool_.N, wkr_id).Conn();
			src_tbl = new Xowd_html_tbl(wkr_conn);
			src_tbls[wkr_id] = src_tbl;
		}
		src_tbl.Select_as_row(rv, page_id);
	}
	public void Rls() {
		for (Xowd_html_tbl src_tbl : src_tbls) {
			if (src_tbl == null) continue;	// can be null if fsys has dirs, but db does not have any wkr_ids
			src_tbl.Conn().Rls_conn();
		}
	}
}
