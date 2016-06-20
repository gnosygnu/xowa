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
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*;
public class Xoh_page_tbl_itm {
	private final    boolean trg;
	public Xoh_page_tbl_itm(boolean trg, int db_id, Db_conn conn) {
		this.trg = trg;
		this.db_id = db_id;
		this.html_tbl = new Xoh_page_tbl(conn);
	}
	public int Db_id() {return db_id;} private final    int db_id;
	public Xoh_page_tbl Html_tbl() {return html_tbl;} private final    Xoh_page_tbl html_tbl;
	public void Rls() {
		html_tbl.Conn().Rls_conn();
		if (trg) html_tbl.Conn().Env_vacuum();
	}
}
