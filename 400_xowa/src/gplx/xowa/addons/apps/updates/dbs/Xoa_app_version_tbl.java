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
package gplx.xowa.addons.apps.updates.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xoa_app_version_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__version_id, fld__version_date, fld__version_priority, fld__version_summary, fld__version_details;
	private final    Db_conn conn;
	public Xoa_app_version_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld__version_id		= flds.Add_str("version_id", 32);
		this.fld__version_date		= flds.Add_str("version_date", 32);
		this.fld__version_priority	= flds.Add_int("version_priority");		// 3:trivial; 5:minor; 7:major;
		this.fld__version_summary	= flds.Add_str("version_summary", 255);
		this.fld__version_details	= flds.Add_text("version_details");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = TBL_NAME;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld__version_date, fld__version_date)));}
	public Xoa_app_version_itm[] Select_by_date(String date) {	// NOTE: version_ids are not easy to sort; using version_date instead
		String sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  *"
		, "FROM    app_version"
		, "WHERE   version_date > '{0}'"
		, "ORDER BY version_date DESC"
		), date);

		Db_rdr rdr = conn.Stmt_sql(sql).Exec_select__rls_auto();
		try {
			List_adp list = List_adp_.New();
			while (rdr.Move_next()) {
				list.Add(Load(rdr));
			}
			return (Xoa_app_version_itm[])list.To_ary_and_clear(Xoa_app_version_itm.class);
		} finally {rdr.Rls();}
	}
	private Xoa_app_version_itm Load(Db_rdr rdr) {
		return new Xoa_app_version_itm
		( rdr.Read_str(fld__version_id)
		, rdr.Read_str(fld__version_date)
		, rdr.Read_int(fld__version_priority)
		, rdr.Read_str(fld__version_summary)
		, rdr.Read_str(fld__version_details)
		);
	}
	public void Rls() {}
	public static final String TBL_NAME = "app_version";
}
