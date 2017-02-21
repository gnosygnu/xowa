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
package gplx.dbs; import gplx.*;
import gplx.dbs.sqls.*; import gplx.dbs.sqls.itms.*;
public class Db_attach_mgr {
	private Db_conn main_conn; private Io_url main_conn_url;
	private final    Ordered_hash links_hash = Ordered_hash_.New();
	private final    List_adp attach_list = List_adp_.New();
	public Db_attach_mgr() {}
	public Db_attach_mgr(Db_conn main_conn, Db_attach_itm... links_ary) {
		this.Conn_main_(main_conn);
		this.Conn_links_(links_ary);
	}
	public Db_conn Conn_main() {return main_conn;}
	public Db_attach_mgr Conn_main_(Db_conn conn) {
		this.main_conn = conn; this.main_conn_url = Db_conn_info_.To_url(conn.Conn_info());
		return this;
	}
	public Db_attach_mgr Conn_links_(Db_attach_itm... itms_ary) {
		links_hash.Clear();
		int len = itms_ary.length;
		for (int i = 0; i < len; ++i) {
			Db_attach_itm itm = itms_ary[i];
			links_hash.Add(itm.Key, itm);
		}
		return this;
	}
	public void Attach() {
		int len = attach_list.Len();
		for (int i = 0; i < len; ++i) {
			Db_attach_itm itm = (Db_attach_itm)attach_list.Get_at(i);
			main_conn.Env_db_attach(itm.Key, itm.Url);
		}
	}
	public void Detach() {
		int len = attach_list.Len();
		for (int i = 0; i < len; ++i) {
			Db_attach_itm itm = (Db_attach_itm)attach_list.Get_at(i);
			main_conn.Env_db_detach(itm.Key);
		}
		attach_list.Clear();	// clear list so multiple detachs don't fail
	}
	public String Resolve_sql(String sql) {
		attach_list.Clear();
		int hash_len = links_hash.Count();
		for (int i = 0; i < hash_len; ++i) {
			Db_attach_itm attach_itm = (Db_attach_itm)links_hash.Get_at(i);
			String tkn = "<" + attach_itm.Key + ">";
			if (String_.Has(sql, tkn)) {
				Io_url attach_url = attach_itm.Url;
				String repl = "";
				if (!attach_url.Eq(main_conn_url)) {
					repl = attach_itm.Key + ".";
					attach_list.Add(attach_itm);
				}
				sql = String_.Replace(sql, tkn, repl);
			}
		}
		attached_sql = sql;
		return sql;
	}
	public Db_attach_mgr Exec_sql_w_msg(String msg, String sql, Object... args) {
		Gfo_usr_dlg_.Instance.Plog_many("", "", msg);
		Exec_sql(sql, args);
		return this;
	}
	public Db_attach_mgr Exec_sql(String sql, Object... args) {
		String attach_sql = String_.Format(Resolve_sql(sql), args);
		this.Attach();
		try {main_conn.Exec_sql(attach_sql);}
		finally {this.Detach();}
		return this;
	}
	public String Test__attach_sql() {return attached_sql;} private String attached_sql;
	public String[] Test__attach_list_keys() {
		int rv_len = attach_list.Count();
		String[] rv = new String[rv_len];
		for (int i = 0; i < rv_len; ++i) {
			Db_attach_itm itm = (Db_attach_itm)attach_list.Get_at(i);
			rv[i] = itm.Key;
		}
		return rv;
	}
	public Db_stmt Test__make_stmt_and_attach(Db_qry qry, gplx.dbs.sqls.itms.Sql_from_clause from_itm) {	// NOTE: tries to do attach via DOM not SQL 
		attach_list.Clear();
		Sql_qry_wtr sql_wtr = main_conn.Engine().Sql_wtr();
		List_adp from_tbls = from_itm.Tbls;
		int from_tbls_len = from_tbls.Count();
		for (int i = 0; i < from_tbls_len; ++i) {
			Sql_tbl_itm from_tbl = (Sql_tbl_itm)from_tbls.Get_at(i);
			String from_tbl_db = from_tbl.Db;
			if (String_.Eq(Sql_tbl_itm.Db__null, from_tbl_db)) continue;	// tbl does not have db defined; only "tbl" not "db.tbl"; skip
			Db_attach_itm attach_itm = (Db_attach_itm)links_hash.Get_by(from_tbl_db); if (attach_itm == null) throw Err_.new_("dbs", "qry defines an unknown database for attach_wkr", "from_tbl_db", from_tbl_db, "sql", qry.To_sql__exec(sql_wtr)); 
			if (attach_itm.Url.Eq(main_conn_url)) // attach_db same as conn; blank db, so "tbl", not "db.tbl"
				from_tbl.Db_enabled = false;
			else
				attach_list.Add(attach_itm);
		}
		attached_sql = sql_wtr.To_sql_str(qry, true);
		this.Attach();
		for (int i = 0; i < from_tbls_len; ++i) {	// reverse blanking from above
			Sql_tbl_itm from_tbl = (Sql_tbl_itm)from_tbls.Get_at(i);
			from_tbl.Db_enabled = true;
		}
		return main_conn.Stmt_sql(attached_sql);
	}
}
