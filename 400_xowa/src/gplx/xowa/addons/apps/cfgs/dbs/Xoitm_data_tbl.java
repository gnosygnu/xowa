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
public class Xoitm_data_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__itm_id, fld__itm_ctx, fld__itm_val, fld__itm_date;
	private final    Db_conn conn;
	public Xoitm_data_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "cfg_itm_data";
		this.fld__itm_id			= flds.Add_int("itm_id");				// EX: '1'
		this.fld__itm_ctx			= flds.Add_str("itm_ctx", 255);			// EX: 'app'; 'en.w'; 'ns-10'
		this.fld__itm_val			= flds.Add_str("itm_val", 4096);		// EX: 'abc'
		this.fld__itm_date			= flds.Add_str("itm_date", 16);			// EX: '20160901_010203'
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Upsert(int itm_id, String ctx, String itm_val, String itm_date) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__itm_id), itm_id, ctx, itm_val, itm_date);
	}
	public void Delete(int id, String ctx) {
		conn.Stmt_delete(tbl_name, fld__itm_id, fld__itm_ctx).Crt_int(fld__itm_id, id).Crt_str(fld__itm_ctx, ctx).Exec_delete();
	}
	public Xoitm_data_itm Select_by_id_or_null(int id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__itm_id).Exec_select__rls_auto();
		try {return rdr.Move_next() ? Load(rdr) : null;}
		finally {rdr.Rls();}
	}
	private Xoitm_data_itm Load(Db_rdr rdr) {
		return new Xoitm_data_itm
		( rdr.Read_int(fld__itm_id)
		, rdr.Read_str(fld__itm_ctx)
		, rdr.Read_str(fld__itm_val)
		, rdr.Read_str(fld__itm_date)
		);
	}
	public void Rls() {}
}
