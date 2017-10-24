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
public class Xob_page_dump_tbl {
	public final    static String Tbl_name = "page_dump";
	private final    String fld_id, fld_title, fld_namespace, fld_is_redirect;
	private final    Db_conn conn; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public Xob_page_dump_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_id				= flds.Add_int_pkey("page_id");
		this.fld_title			= flds.Add_str("page_title", 255);
		this.fld_namespace		= flds.Add_int("page_namespace");
		this.fld_is_redirect	= flds.Add_int("page_is_redirect");
	}
	public void Create_data(Io_url page_db_url, int text_db_id) {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(Tbl_name, flds));
		conn.Stmt_delete(Tbl_name).Exec_delete();	// always clear tables again; allows commands to be rerun; DATE:2015-08-04
		new Db_attach_mgr(conn, new Db_attach_itm("page_db", page_db_url))
			.Exec_sql_w_msg("text_db_prep.clone_page", Sql_insert_data, text_db_id);
		conn.Meta_idx_create(Dbmeta_idx_itm.new_unique_by_tbl(Tbl_name, "main", fld_id, fld_namespace, fld_is_redirect, fld_title));
	}
	private static final    String Sql_insert_data = String_.Concat_lines_nl
	( "INSERT INTO page_dump (page_id, page_title, page_namespace, page_is_redirect)"
	, "SELECT  p.page_id"
	, ",       p.page_title"
	, ",       p.page_namespace"
	, ",       p.page_is_redirect"
	, "FROM    <page_db>page p"
	, "WHERE   p.page_text_db_id = {0};"
	);
}
