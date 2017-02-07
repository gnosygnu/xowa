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
package gplx.xowa.addons.wikis.ctgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.addons.wikis.ctgs.*; 
public class Xodb_cat_sort_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_key;
	public Xodb_cat_sort_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = "cat_sort";
		flds.Add_int_pkey_autonum("cs_id");
		this.fld_key			= flds.Add_str	("cs_key", 255);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn; 
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; 
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx__key() {conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld_key, fld_key));}
	public void Delete_idx__key() {conn.Meta_idx_delete(tbl_name, fld_key);}
	public void Insert_by_select(Db_conn tmp_conn) {
		Db_attach_mgr attach_mgr = new Db_attach_mgr(conn, new Db_attach_itm("temp_db", tmp_conn));
		attach_mgr.Exec_sql(String_.Concat_lines_nl
		( "INSERT INTO cat_sort (cs_key)"
		, "SELECT DISTINCT cl_sortkey"
		, "FROM   <temp_db>tmp_cat_link"
		));
	}
	public void Rls() {}
}
