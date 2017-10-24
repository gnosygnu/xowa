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
package gplx.xowa.addons.bldrs.files.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*;
public class Xob_xfer_regy_log_tbl {
	public static void Create_table(Db_conn p) {gplx.dbs.engines.sqlite.Sqlite_engine_.Tbl_create_and_delete(p, Tbl_name, Tbl_sql);}
	public static Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_lnki_id, Fld_xfer_status, Fld_xfer_bin_tid, Fld_xfer_bin_msg);}
	public static void Insert(Db_stmt stmt, byte status, int id, byte wkr_tid, String wkr_msg) {
		stmt.Clear()
		.Val_int(id)
		.Val_byte(status)
		.Val_byte(wkr_tid)
		.Val_str(wkr_msg)
		.Exec_insert();
	}
	private static final    String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS xfer_regy_log"
	,	"( lnki_id             integer             NOT NULL"
	,	", xfer_status         tinyint             NOT NULL"	// 0=todo; 1=fail; 2=pass; 3=done
	,	", xfer_bin_tid        tinyint             NOT NULL"
	,	", xfer_bin_msg        varchar(255)        NOT NULL"
	,	");"
	);
	public static final    String Tbl_name = "xfer_regy_log"
	, Fld_lnki_id = "lnki_id", Fld_xfer_status = "xfer_status", Fld_xfer_bin_tid = "xfer_bin_tid", Fld_xfer_bin_msg = "xfer_bin_msg"
	;
}
