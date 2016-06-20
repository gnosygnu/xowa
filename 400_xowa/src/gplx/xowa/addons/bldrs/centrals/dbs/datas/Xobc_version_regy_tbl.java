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
package gplx.xowa.addons.bldrs.centrals.dbs.datas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
import gplx.dbs.*;
public class Xobc_version_regy_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_version_id, fld_version_date, fld_version_note;
	private final    Db_conn conn;
	public Xobc_version_regy_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "version_regy";
		this.fld_version_id			= flds.Add_int_pkey("version_id");
		this.fld_version_date		= flds.Add_str("version_date", 16);
		this.fld_version_note		= flds.Add_str("version_note", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; 
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
		conn.Stmt_insert(tbl_name, flds)
			.Val_int(fld_version_id, 1).Val_str(fld_version_date, DateAdp_.Now().XtoStr_fmt_yyyy_MM_dd_HH_mm()).Val_str(fld_version_note, "initial")
			.Exec_insert();
	}
	public Xobc_version_regy_itm Select_latest() {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try		{
			if (rdr.Move_next())
				return new Xobc_version_regy_itm(rdr.Read_int(fld_version_id), rdr.Read_str(fld_version_date), rdr.Read_str(fld_version_note));
			else
				throw Err_.new_("", "version_regy does not have version");
		}
		finally {rdr.Rls();}
	}
	public void Rls() {}
}
