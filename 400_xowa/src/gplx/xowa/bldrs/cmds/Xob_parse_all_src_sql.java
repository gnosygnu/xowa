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
package gplx.xowa.bldrs.cmds;
import gplx.core.stores.DataRdr;
import gplx.core.stores.DataRdr_;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_stmt;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.wikis.data.Xow_db_file;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.wikis.data.tbls.Xowd_page_tbl;
import gplx.xowa.wikis.dbs.Xodb_mgr_sql;
class Xob_dump_src_id {
	private Xodb_mgr_sql db_mgr; private byte redirect;
	private String page_db_url; private int size_max;
	private Db_stmt text_stmt; int cur_text_db_idx = -1;
	public Xob_dump_src_id Init(Xowe_wiki wiki, byte redirect, int size_max) {
		this.db_mgr = wiki.Db_mgr_as_sql(); this.redirect = redirect;
		this.size_max = size_max;
		this.page_db_url = db_mgr.Core_data_mgr().Db__core().Url().Raw();
		return this;
	}
	public void Get_pages(Xoae_app app, List_adp list, int text_db_idx, int cur_ns, int prv_id) {
		DataRdr rdr = DataRdr_.Null;
		int size_len = 0;
		list.Clear();
		try {
			rdr = New_rdr(db_mgr, page_db_url, text_db_idx, cur_ns, prv_id, redirect);
			while (rdr.MoveNextPeer()) {
				Xowd_page_itm page = New_page(app, db_mgr, cur_ns, rdr);
				list.Add(page);
				size_len += page.Text_len();
				if (size_len > size_max)
					break;
			}
		}
		finally {rdr.Rls();}
	}
	private DataRdr New_rdr(Xodb_mgr_sql db_mgr, String page_db_url, int text_db_idx, int cur_ns, int prv_id, byte redirect) {
		if (cur_text_db_idx != text_db_idx) {
			cur_text_db_idx = text_db_idx;
			Xow_db_file text_db = db_mgr.Core_data_mgr().Dbs__get_by_id_or_fail(text_db_idx);
			Db_conn conn = text_db.Conn();
			String sql = StringUtl.Format(Sql_select_clause, New_rdr__redirect_clause(redirect));
			text_stmt = conn.Stmt_sql(sql);
		}
		return text_stmt.Clear().Val_int(prv_id).Val_int(cur_ns).Exec_select();
	}
	private static Xowd_page_itm New_page(Xoae_app app, Xodb_mgr_sql db_mgr, int ns_id, DataRdr rdr) {
		Xowd_page_tbl page_core_tbl = db_mgr.Core_data_mgr().Tbl__page();
		Xowd_page_itm rv = new Xowd_page_itm();
		rv.Id_(rdr.ReadInt(page_core_tbl.Fld_page_id()));
		rv.Ns_id_(ns_id);
		rv.Ttl_page_db_(rdr.ReadBryByStr(page_core_tbl.Fld_page_title()));
		
		String text_data_name = db_mgr.Core_data_mgr().Db__core().Tbl__text().Fld_text_data();
		byte[] text_data = rdr.ReadBry(text_data_name);
		text_data = app.Zip_mgr().Unzip(db_mgr.Core_data_mgr().Props().Zip_tid_text(), text_data);
		rv.Text_(text_data);
		return rv;
	}
	private static String New_rdr__redirect_clause(byte redirect) {
		switch (redirect) {
			case BoolUtl.YByte:	return Sql_select__redirect_y;
			case BoolUtl.NByte:	return Sql_select__redirect_n;
			case BoolUtl.NullByte:	return Sql_select__redirect__;
			default:			throw ErrUtl.NewUnhandled(redirect);
		}
	}
	private static final String Sql_select_clause = StringUtl.ConcatLinesNl
	( "SELECT  p.page_id"
	, ",       p.page_title"
	, ",       t.text_data"
	, "FROM    page_dump p"
	, "        JOIN text t ON t.page_id = p.page_id"
	, "WHERE   p.page_id > ?"
	, "AND     p.page_namespace = ?{0}" 
	, "ORDER BY p.page_id"
	);
	private static final String
	  Sql_select__redirect_y = "\nAND     p.page_is_redirect = 1"
	, Sql_select__redirect_n = "\nAND     p.page_is_redirect = 0"
	, Sql_select__redirect__ = ""
	;
}
