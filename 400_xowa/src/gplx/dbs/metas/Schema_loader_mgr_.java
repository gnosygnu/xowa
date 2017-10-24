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
package gplx.dbs.metas; import gplx.*; import gplx.dbs.*;
import gplx.dbs.qrys.*;
public class Schema_loader_mgr_ {
	public static final Schema_loader_mgr Null = new Schema_loader_mgr__null();
	public static final Schema_loader_mgr Sqlite = new Schema_loader_mgr__sqlite();
}
class Schema_loader_mgr__null implements Schema_loader_mgr {
	public void Load(Schema_db_mgr db_mgr, Db_conn conn) {}
}
class Schema_loader_mgr__sqlite implements Schema_loader_mgr {
	public void Load(Schema_db_mgr db_mgr, Db_conn conn) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.schema.load.bgn: conn=~{0}", conn.Conn_info().Db_api());
		Dbmeta_tbl_mgr tbl_mgr = db_mgr.Tbl_mgr();
		Db_qry__select_in_tbl qry = Db_qry__select_in_tbl.new_("sqlite_master", String_.Ary_empty, String_.Ary("type", "name", "sql"), Db_qry__select_in_tbl.Order_by_null);
		Db_rdr rdr = conn.Stmt_new(qry).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				String type_str = rdr.Read_str("type");
				String name = rdr.Read_str("name");
				int type_int = Dbmeta_itm_tid.Xto_int(type_str);
				switch (type_int) {
					case Dbmeta_itm_tid.Tid_table:
						Dbmeta_tbl_itm tbl_itm = Dbmeta_tbl_itm.New(name);
						tbl_mgr.Add(tbl_itm);
						break;
					case Dbmeta_itm_tid.Tid_index:	break;	// noop for now
					default:						throw Err_.new_unhandled(type_str);
				}
			}
		}	finally {rdr.Rls();}
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.schema.load.end");
	}
}
