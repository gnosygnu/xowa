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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.ios.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.dbs.*; import gplx.dbs.cfgs.*;
public class Xowd_pagelinks_tbl implements RlsAble {
	private final String tbl_name = "pagelinks"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_src_id, fld_trg_id;
	private final Db_conn conn;
	public Xowd_pagelinks_tbl(Db_conn conn) {
		this.conn = conn;
		fld_src_id			= flds.Add_int("src_id");
		fld_trg_id			= flds.Add_int("trg_id");
		flds.Add_int("trg_count");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));}
	public void Create_idx__src_trg() {conn.Ddl_create_idx(Gfo_usr_dlg_.I, Db_meta_idx.new_unique_by_tbl(tbl_name, "src_trg", fld_src_id, fld_trg_id));}
	public void Create_idx__trg_src() {conn.Ddl_create_idx(Gfo_usr_dlg_.I, Db_meta_idx.new_unique_by_tbl(tbl_name, "trg_src", fld_trg_id, fld_src_id));}
	public void Rls() {}
}
