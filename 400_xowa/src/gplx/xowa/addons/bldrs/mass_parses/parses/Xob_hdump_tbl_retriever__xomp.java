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
package gplx.xowa.addons.bldrs.mass_parses.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.htmls.core.bldrs.*; import gplx.xowa.htmls.core.dbs.*;
class Xob_hdump_tbl_retriever__xomp implements Xob_hdump_tbl_retriever {
	private final    Db_conn conn;
	private final    Xowd_html_tbl tbl;
	public Xob_hdump_tbl_retriever__xomp(Xowd_html_tbl tbl) {
		this.tbl = tbl;
		this.conn = tbl.Conn();
	}
	public Xowd_html_tbl Get_html_tbl(Xow_ns ns, int prv_row_len) {
		return tbl;
	}
	public void Commit() {conn.Txn_sav();}
	public void Rls_all() {conn.Txn_sav(); conn.Rls_conn();}
}
