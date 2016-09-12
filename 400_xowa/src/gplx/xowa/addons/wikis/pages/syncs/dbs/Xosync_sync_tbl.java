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
package gplx.xowa.addons.wikis.pages.syncs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xosync_sync_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_page_id, fld_sync_date;
	private final    Db_conn conn;
	public Xosync_sync_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_page_id		= flds.Add_int_pkey("page_id");
		this.fld_sync_date		= flds.Add_str("sync_date", 32);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "sync";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public DateAdp Select_sync_date_or_min(int page_id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_page_id).Crt_int(fld_page_id, page_id).Exec_select__rls_auto();
		try		{return rdr.Move_next() ? rdr.Read_date_by_str(fld_sync_date) : DateAdp_.MinValue;}
		finally {rdr.Rls();}
	}
	public void Upsert(int page_id, DateAdp sync_date) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld_page_id), page_id, sync_date.XtoStr_fmt_yyyyMMdd_HHmmss());
	}
	public void Rls() {}
}
