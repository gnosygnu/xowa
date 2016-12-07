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
		this.fld__grp_id			= flds.Add_int_pkey("grp_id");
		this.fld__grp_key			= flds.Add_str("grp_key", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = TBL_NAME;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, fld__grp_key, fld__grp_key)
		));
	}
	public void Upsert(int grp_id, String grp_key) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__grp_id), grp_id, grp_key);
	}
	public int Select_id_by_key_or_fail(String key) {
		Xogrp_meta_itm itm = this.Select_by_key_or_null(key);
		if (itm == null) throw Err_.new_wo_type("cfg.grp:invalid key", "key", key);
		return itm.Id();
	}
	public Xogrp_meta_itm Select_by_key_or_null(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__grp_key).Crt_str(fld__grp_key, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? Load(rdr): null;}
		finally {rdr.Rls();}
	}
	private Xogrp_meta_itm Load(Db_rdr rdr) {
		return new Xogrp_meta_itm
		( rdr.Read_int(fld__grp_id)
		, rdr.Read_str(fld__grp_key)
		);
	}
	public void Rls() {}
	public static final String TBL_NAME = "cfg_grp_meta";
}
