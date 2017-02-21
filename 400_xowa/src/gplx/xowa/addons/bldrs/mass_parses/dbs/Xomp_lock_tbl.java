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
package gplx.xowa.addons.bldrs.mass_parses.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
public class Xomp_lock_tbl implements Db_tbl {
	private final    String fld_uid_prv;
	private final    Db_conn conn;
	public Xomp_lock_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = "xomp_lock";
		this.fld_uid_prv		= flds.Add_int("uid_prv");				// EX: -1
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public Dbmeta_fld_list Flds() {return flds;} private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
		conn.Stmt_insert(tbl_name, flds).Clear().Val_int(fld_uid_prv, -1).Exec_insert();	// always add default record when creating table				
	}
	public int Select() {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			if (!rdr.Move_next()) throw Err_.new_wo_type("xomp_lock has no rows");
			return rdr.Read_int(fld_uid_prv);}
		finally {rdr.Rls();}
	}
	public void Update(int uid_prv) {
		conn.Stmt_update(tbl_name, String_.Ary_empty, fld_uid_prv).Clear().Val_int(fld_uid_prv, uid_prv).Exec_update();
	}
	public void Rls() {}
}