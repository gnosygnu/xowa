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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import gplx.dbs.*;
public class Site_core_tbl implements Db_tbl {
	private static final String tbl_name = "site_core"; private final Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final String fld_site_abrv, fld_site_domain, fld_json_completed, fld_json_date, fld_json_text;
	private final Db_conn conn;
	private Db_stmt stmt_select, stmt_insert, stmt_delete, stmt_update;
	public Site_core_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_site_abrv		= flds.Add_str("site_abrv", 255);
		this.fld_site_domain	= flds.Add_str("site_domain", 255);
		this.fld_json_completed	= flds.Add_bool("json_completed");
		this.fld_json_date		= flds.Add_str("json_date", 20);
		this.fld_json_text		= flds.Add_text("json_text");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_name(tbl_name, Dbmeta_idx_itm.Bld_idx_name(tbl_name, "main"), fld_site_abrv)));}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
		stmt_update = Db_stmt_.Rls(stmt_update);
	}
	public void Update(byte[] site_abrv, boolean json_completed) {
		if (stmt_update == null) stmt_update = conn.Stmt_update(tbl_name, String_.Ary(fld_site_abrv), fld_json_completed);
		stmt_update.Clear()
			.Val_bool_as_byte	(fld_json_completed		, json_completed)
			.Crt_bry_as_str		(fld_site_abrv			, site_abrv)
			.Exec_update();
	}
	public void Insert(byte[] site_abrv, byte[] site_domain, boolean json_completed, DateAdp json_date, byte[] json_text) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_site_abrv);
		stmt_delete.Clear().Crt_bry_as_str(fld_site_abrv, site_abrv).Exec_delete();
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_bry_as_str		(fld_site_abrv			, site_abrv)
			.Val_bry_as_str		(fld_site_domain		, site_domain)
			.Val_bool_as_byte	(fld_json_completed		, json_completed)
			.Val_str			(fld_json_date			, json_date.XtoStr_gplx())
			.Val_bry_as_str		(fld_json_text			, json_text)
			.Exec_insert();
	}
	public Site_core_itm Select_itm(byte[] site_abrv) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_site_abrv)
			.Clear()
			.Crt_bry_as_str(fld_site_abrv, site_abrv)
			.Exec_select__rls_auto();
		try {
			return (rdr.Move_next()) ? new_itm(rdr) : null;
		}
		finally {rdr.Rls();}
	}
	public Site_core_itm[] Select_all_downloaded(DateAdp cutoff) {
		List_adp list = List_adp_.new_();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Clear().Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Site_core_itm itm = new_itm(rdr);
				if (itm.Json_date().compareTo(cutoff) == CompareAble_.MoreOrSame) continue;	// ignore those downloaded after cutoff date
				itm.Json_text_null_();
				list.Add(itm);
			}
		}
		finally {rdr.Rls();}
		return (Site_core_itm[])list.To_ary_and_clear(Site_core_itm.class);
	}
	private Site_core_itm new_itm(Db_rdr rdr) {
		return new Site_core_itm
		( rdr.Read_bry_by_str(fld_site_abrv)
		, rdr.Read_bry_by_str(fld_site_domain)
		, rdr.Read_bool_by_byte(fld_json_completed)
		, rdr.Read_date_by_str(fld_json_date)
		, rdr.Read_bry_by_str(fld_json_text)
		);
	}
}
