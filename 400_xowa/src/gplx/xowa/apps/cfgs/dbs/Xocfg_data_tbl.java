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
package gplx.xowa.apps.cfgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.cfgs.*;
import gplx.dbs.*;
public class Xocfg_data_tbl implements Rls_able {
	private final    String tbl_name; public final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_key, fld_usr, fld_ctx, fld_val;
	private final    Db_conn conn;
	public Xocfg_data_tbl(Db_conn conn) {
		this.conn = conn;
		tbl_name			= Tbl_name;
		fld_key				= flds.Add_str_pkey	("cfg_key", 1024);		// EX: "xowa.net.web_enabled"
		fld_usr				= flds.Add_str		("cfg_usr", 1024);		// EX: "xowa_user"
		fld_ctx				= flds.Add_str		("cfg_ctx", 1024);		// EX: "app"
		fld_val				= flds.Add_str		("cfg_val", 4096);		// EX: "y"
	}
	public void Create_tbl()		{conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert(String key, String usr, String ctx, String val) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_str(fld_key, key).Val_str(fld_usr, usr).Val_str(fld_ctx, ctx).Val_str(fld_val, val)
			.Exec_insert();
	}
	public Xocfg_data_itm Select(String key, String usr, String ctx) {
		Db_stmt stmt_select = conn.Stmt_select(tbl_name, flds, fld_key, fld_usr, fld_ctx);
		Db_rdr rdr = stmt_select.Clear().Crt_str(fld_key, key).Crt_str(fld_usr, usr).Crt_str(fld_ctx, ctx).Exec_select__rls_auto();
		try {
			if (rdr.Move_next())
				return new Xocfg_data_itm(key, usr, ctx, rdr.Read_str(fld_val));
			else
				return null;
		} finally {rdr.Rls();}
	}
	public void Rls() {conn.Rls_conn();}
	public static final String Tbl_name = "cfg_data";
}
