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
package gplx.xowa.htmls.core.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.htmls.core.dbs.*;
public class Xob_ns_to_db_wkr__html implements Xob_ns_to_db_wkr {
	private final    Xow_db_file page_db;
	public Xob_ns_to_db_wkr__html(Xow_db_file page_db) {this.page_db = page_db;}
	public byte Db_tid() {return Xow_db_file_.Tid__html_data;}
	public void Tbl_init(Xow_db_file db) {			
		Xowd_html_tbl tbl = db.Tbl__html();
		tbl.Create_tbl();
		tbl.Insert_bgn(); 
	}
	public void Tbl_term(Xow_db_file db) {
		db.Tbl__text().Insert_end(); 
		Db_conn db_conn = db.Conn();
		new Db_attach_mgr(page_db.Conn(), new Db_attach_itm("html_db", db.Url()))
			.Exec_sql_w_msg("hdump.update page.html_db_id", Sql_update_page_html_db_id, db.Id());
		db_conn.Rls_conn();
	}
	private static final    String Sql_update_page_html_db_id = String_.Concat_lines_nl_skip_last
	( "REPLACE INTO page (page_id, page_namespace, page_title, page_is_redirect, page_touched, page_len, page_random_int, page_text_db_id, page_html_db_id, page_redirect_id, page_score)"
	, "SELECT   p.page_id"
	, ",        p.page_namespace"
	, ",        p.page_title"
	, ",        p.page_is_redirect"
	, ",        p.page_touched"
	, ",        p.page_len"
	, ",        p.page_random_int"
	, ",        p.page_text_db_id"
	, ",        {0}"
	, ",        p.page_redirect_id"
	, ",        p.page_score"
	, "FROM     page p"
	, "         JOIN <html_db>html h ON p.page_id = h.page_id"
	);
}
