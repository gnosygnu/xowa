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
package gplx.xowa.addons.bldrs.exports.splits.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
public class Wkr_stats_tbl implements Rls_able {
	private final    String tbl_name;
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_wkr_id, fld_wkr_count, fld_wkr_size;
	public final    Db_conn conn;
	public Wkr_stats_tbl(Db_conn conn) {
		this.conn = conn; conn.Rls_reg(this);
		this.tbl_name				= "wkr_stats";
		this.fld_wkr_id				= flds.Add_int_pkey("wkr_id");
		this.fld_wkr_count			= flds.Add_int("wkr_count");
		this.fld_wkr_size			= flds.Add_long("wkr_size");
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public Db_stmt Insert_stmt() {return conn.Stmt_insert(tbl_name, flds);}
	public void Insert(Db_stmt stmt, int wkr_id, int wkr_count, long wkr_size) {
		stmt.Clear()
			.Val_int(fld_wkr_id		, wkr_id)
			.Val_int(fld_wkr_count	, wkr_count)
			.Val_long(fld_wkr_size	, wkr_size)
			.Exec_insert();
	}
	public Wkr_stats_itm[] Select_all() {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select_all(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Wkr_stats_itm itm = new Wkr_stats_itm(rdr.Read_int(fld_wkr_id), rdr.Read_int(fld_wkr_count), rdr.Read_int(fld_wkr_size));
				list.Add(itm);
			}
			rdr.Rls();
		} finally {rdr.Rls();}
		return (Wkr_stats_itm[])list.To_ary_and_clear(Wkr_stats_itm.class);
	}
	public Wkr_stats_itm Select_all__summary() {
		Db_rdr rdr = conn.Stmt_sql(String_.Format("SELECT Sum({0}) AS {0}, Sum({1}) AS {1} FROM {2}", fld_wkr_count, fld_wkr_size, tbl_name)).Exec_select__rls_auto();	// ANSI.Y
		try {
			if (rdr.Move_next())
				return new Wkr_stats_itm(-1, rdr.Read_int(fld_wkr_count), rdr.Read_long(fld_wkr_size));
			else
				throw Err_.new_wo_type("failed to get sum of wkr_size", "url", conn.Conn_info().Raw());
		} finally {rdr.Rls();}
	}
	public void Rls() {}
}
