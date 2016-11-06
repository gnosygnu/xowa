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
package gplx.xowa.addons.apps.cfgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xogrp_meta_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__grp_id, fld__grp_key;
	private final    Db_conn conn;
	public Xogrp_meta_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "cfg_grp_meta";
		this.fld__grp_id			= flds.Add_int("grp_id");
		this.fld__grp_key			= flds.Add_str("grp_key", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Upsert(int grp_id, String grp_key) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__grp_id), grp_id, grp_key);
	}
	public void Select_stub() {
		Db_rdr rdr = Db_rdr_.Empty;
		rdr.Read_int(fld__grp_id);
		rdr.Read_str(fld__grp_key);
	}
	public void Rls() {}
}
