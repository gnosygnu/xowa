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
package gplx.xowa.addons.bldrs.mass_parses.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
public class Xomp_page_tbl implements Db_tbl {
	private final    Object thread_lock = new Object();
	private final    Db_conn conn;
	// private final    String fld_page_id, fld_page_status, fld_page_mgr_id;
	public Xomp_page_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "xomp_page";
		flds.Add_int_pkey("xomp_uid");
		flds.Add_int("page_id");
		flds.Add_int("page_ns");
		flds.Add_byte("page_status");			// 0=wait; 1=done; 2=fail
		flds.Add_int_dflt("html_len", -1);
		flds.Add_int_dflt("xomp_wkr_id", -1);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public Dbmeta_fld_list Flds() {return flds;} private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();

	public void Create_tbl() {
		conn.Meta_tbl_create
		( Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_normal_by_tbl("xomp_page", "xomp_uid__page_status"	, "xomp_uid", "page_status")// for parse
		, Dbmeta_idx_itm.new_normal_by_tbl("xomp_page", "page_ns__page_id"		, "page_ns", "page_id")		// for make
		));
	}
	public void Update_wkr_uid(int wkr_uid, Db_conn wkr_conn) {
		synchronized (thread_lock) {	// LOCK:used by multiple threads
			Db_attach_mgr attach_mgr = new Db_attach_mgr(conn, new Db_attach_itm("wkr_db", wkr_conn));
			attach_mgr.Exec_sql_w_msg("updating page_regy: wkr_id=" + wkr_uid, String_.Concat_lines_nl_skip_last	// ANSI.Y
			( "UPDATE  xomp_page"
			, "SET     xomp_wkr_id = " + Int_.To_str(wkr_uid)
			, ",       html_len = (SELECT length(body) FROM <wkr_db>html h WHERE h.page_id = xomp_page.page_id)"
			, "WHERE   page_id IN (SELECT page_id FROM <wkr_db>html h)"
			));
		}
	}
	public void Rls() {}
}
