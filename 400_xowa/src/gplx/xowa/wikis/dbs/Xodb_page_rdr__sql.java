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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
class Xodb_page_rdr__sql implements Xodb_page_rdr {
	private final    Xow_db_mgr db_mgr;
	private final    Xowd_page_tbl page_tbl; private final    Db_rdr rdr;
	public Xodb_page_rdr__sql(Xowe_wiki wiki) {
		this.db_mgr = wiki.Data__core_mgr();
		this.page_tbl = db_mgr.Tbl__page();
		this.rdr = page_tbl.Select_all__id__ttl();
	}
	public boolean Move_next() {return rdr.Move_next();}
	public boolean Read(Xowd_page_itm page) {
		page_tbl.Read_page__all(page, rdr);
		Xowd_text_tbl text_tbl = db_mgr.Dbs__get_by_id_or_fail(page.Text_db_id()).Tbl__text();
		page.Text_(text_tbl.Select(page.Id()));
		return true;
	}
	public void Rls() {
		rdr.Rls();
	}
}

