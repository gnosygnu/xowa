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
package gplx.dbs; import gplx.*;
import gplx.dbs.sqls.*; import gplx.dbs.sqls.itms.*;
public class Db_attach_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public final    List_adp attached_dbs_list = List_adp_.new_();
	public String Attached_sql() {return attached_sql;} private String attached_sql;	// TEST
	private Db_conn main_conn; private Io_url main_conn_url;
	public Db_attach_mgr() {}
	public Db_attach_mgr(Db_conn main_conn, Db_attach_itm... itms_ary) {
		this.Main_conn_(main_conn);
		this.Init(itms_ary);
	}
	public Db_attach_mgr Init(Db_attach_itm... itms_ary) {
		hash.Clear();
		int itms_len = itms_ary.length;
		for (int i = 0; i < itms_len; ++i) {
			Db_attach_itm itm = itms_ary[i];
			hash.Add(itm.Key, itm);
		}
		return this;
	}
	public Db_attach_mgr Main_conn_(Db_conn conn) {
		this.main_conn = conn; this.main_conn_url = Db_conn_info_.To_url(conn.Conn_info());
		return this;
	}
	public void Attach() {
		int len = attached_dbs_list.Len();
		for (int i = 0; i < len; ++i) {
			Db_attach_itm itm = (Db_attach_itm)attached_dbs_list.Get_at(i);
			main_conn.Env_db_attach(itm.Key, itm.Url);
		}
	}
	public void Detach() {
		int len = attached_dbs_list.Len();
		for (int i = 0; i < len; ++i) {
			Db_attach_itm itm = (Db_attach_itm)attached_dbs_list.Get_at(i);
			main_conn.Env_db_detach(itm.Key);
		}
		attached_dbs_list.Clear();	// clear list so multiple detachs don't fail
	}
	public String List__to_str() {
		String rv = "";
		int len = attached_dbs_list.Len();
		for (int i = 0; i < len; ++i) {
			Db_attach_itm itm = (Db_attach_itm)attached_dbs_list.Get_at(i);
			rv += itm.Key + ";";
		}
		return rv;
	}
	public String Resolve_sql(String sql) {
		attached_dbs_list.Clear();
		int hash_len = hash.Count();
		for (int i = 0; i < hash_len; ++i) {
			Db_attach_itm attach_itm = (Db_attach_itm)hash.Get_at(i);
			String tkn = "<" + attach_itm.Key + ">";
			if (String_.Has(sql, tkn)) {
				Io_url attach_url = attach_itm.Url;
				String repl = "";
				if (!attach_url.Eq(main_conn_url)) {
					repl = attach_itm.Key + ".";
					attached_dbs_list.Add(attach_itm);
				}
				sql = String_.Replace(sql, tkn, repl);
			}
		}
		attached_sql = sql;
		return sql;
	}
	public Db_stmt Make_stmt_and_attach(Db_qry qry, gplx.dbs.sqls.itms.Sql_from_clause from_itm) {
		attached_dbs_list.Clear();
		Sql_qry_wtr sql_wtr = main_conn.Engine().Sql_wtr();
		List_adp from_tbls = from_itm.Tbls;
		int from_tbls_len = from_tbls.Count();
		for (int i = 0; i < from_tbls_len; ++i) {
			Sql_tbl_itm from_tbl = (Sql_tbl_itm)from_tbls.Get_at(i);
			String from_tbl_db = from_tbl.Db;
			if (String_.Eq(Sql_tbl_itm.Db__null, from_tbl_db)) continue;	// tbl does not have db defined; only "tbl" not "db.tbl"; skip
			Db_attach_itm attach_itm = (Db_attach_itm)hash.Get_by(from_tbl_db); if (attach_itm == null) throw Err_.new_("dbs", "qry defines an unknown database for attach_wkr", "from_tbl_db", from_tbl_db, "sql", qry.To_sql__exec(sql_wtr)); 
			if (attach_itm.Url.Eq(main_conn_url)) // attach_db same as conn; blank db, so "tbl", not "db.tbl"
				from_tbl.Db_enabled = false;
			else
				attached_dbs_list.Add(attach_itm);
		}
		attached_sql = sql_wtr.To_sql_str(qry, true);
		this.Attach();
		for (int i = 0; i < from_tbls_len; ++i) {	// reverse blanking from above
			Sql_tbl_itm from_tbl = (Sql_tbl_itm)from_tbls.Get_at(i);
			from_tbl.Db_enabled = true;
		}
		return main_conn.Stmt_sql(attached_sql);
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
}
