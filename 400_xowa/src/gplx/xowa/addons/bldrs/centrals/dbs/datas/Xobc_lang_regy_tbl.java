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
public class Xobc_lang_regy_tbl implements Db_tbl {		
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__lang_id, fld__lang_key, fld__lang_name;
	private final    Db_conn conn;
	public Xobc_lang_regy_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "lang_regy";
		this.fld__lang_id			= flds.Add_int_pkey("lang_id");
		this.fld__lang_key			= flds.Add_str("lang_key", 255);
		this.fld__lang_name			= flds.Add_str("lang_name", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
		}
	public Xobc_lang_regy_itm[] Select_all() {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				list.Add(new Xobc_lang_regy_itm(rdr.Read_int(fld__lang_id), rdr.Read_str(fld__lang_key), rdr.Read_str(fld__lang_name)));
		}
		finally {rdr.Rls();}
		return (Xobc_lang_regy_itm[])list.To_ary_and_clear(Xobc_lang_regy_itm.class);
	}
	public void Rls() {}
}
