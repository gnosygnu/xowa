/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.apps.updates.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xoa_app_version_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__version_id, fld__version_name, fld__version_date, fld__version_priority, fld__version_url, fld__version_summary, fld__version_details;
	private final    Db_conn conn;
	public Xoa_app_version_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld__version_id		= flds.Add_int_pkey("version_id");
		this.fld__version_name		= flds.Add_str("version_name", 32);
		this.fld__version_date		= flds.Add_str("version_date", 32);
		this.fld__version_priority	= flds.Add_int("version_priority");		// 3:trivial; 5:minor; 7:major;
		this.fld__version_url		= flds.Add_str("version_url", 255);
		this.fld__version_summary	= flds.Add_str("version_summary", 255);
		this.fld__version_details	= flds.Add_text("version_details");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = TBL_NAME;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
	}
	public Xoa_app_version_itm[] Select_by_id(int id) {
		String sql = Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  *"
		, "FROM    app_version"
		, "WHERE   version_id > {0}"
		, "ORDER BY version_id"
		), id);

		Db_rdr rdr = conn.Stmt_sql(sql).Exec_select__rls_auto();
		try {
			List_adp list = List_adp_.New();
			while (rdr.Move_next()) {
				list.Add(Load(rdr));
			}
			return (Xoa_app_version_itm[])list.To_ary_and_clear(Xoa_app_version_itm.class);
		} finally {rdr.Rls();}
	}
	public Xoa_app_version_itm Select_by_name_or_null(String name) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__version_name).Crt_str(fld__version_name, name).Exec_select__rls_auto();
		try {
			return rdr.Move_next() ? Load(rdr) : null;
		} finally {rdr.Rls();}
	}
	private Xoa_app_version_itm Load(Db_rdr rdr) {
		return new Xoa_app_version_itm
		( rdr.Read_int(fld__version_id)
		, rdr.Read_str(fld__version_name)
		, rdr.Read_str(fld__version_date)
		, rdr.Read_int(fld__version_priority)
		, rdr.Read_str(fld__version_url)
		, rdr.Read_str(fld__version_summary)
		, rdr.Read_str(fld__version_details)
		);
	}
	public void Rls() {}
	public static final String TBL_NAME = "app_version";
}
