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
package gplx.dbs.engines.sqlite; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.primitives.*;	import gplx.dbs.qrys.*; import gplx.dbs.utls.*; import gplx.dbs.engines.*; import gplx.dbs.engines.sqlite.*;
public class Sqlite_engine_ {
	public static void Db_attach(Db_conn p, String alias, String url) {
		String s = String_.Format("ATTACH '{0}' AS {1};", url, alias);
		Db_qry qry = Db_qry_sql.xtn_(s);
		p.Exec_qry(qry);
	}
	public static void Db_detach(Db_conn p, String alias) {
		String s = String_.Format("DETACH '{0}';", alias);
		Db_qry qry = Db_qry_sql.xtn_(s);
		p.Exec_qry(qry);
	}
	public static void Tbl_create_and_delete(Db_conn p, String tbl_name, String tbl_sql) {
		Tbl_delete(p, tbl_name);
		Db_qry qry = Db_qry_sql.ddl_(tbl_sql);
		p.Exec_qry(qry);
	}
	public static void Tbl_create(Db_conn p, String tbl_name, String tbl_sql) {
		Db_qry qry = Db_qry_sql.ddl_(tbl_sql);
		p.Exec_qry(qry);
	}
	public static void Tbl_delete_many(Db_conn p, String... tbls) {
		int len = tbls.length;
		for (int i = 0; i < len; i++)
			Tbl_delete(p, tbls[i]);
	}
	public static void Tbl_delete(Db_conn p, String tbl) {
		Db_qry qry = Db_qry_sql.ddl_("DROP TABLE IF EXISTS " + tbl + ";");
		p.Exec_qry(qry);
	}
	public static void Tbl_rename(Db_conn p, String src, String trg) {
		Db_qry qry = Db_qry_sql.ddl_(String_.Format("ALTER TABLE {0} RENAME TO {1};", src, trg));
		p.Exec_qry(qry);
	}
	public static void Pragma_page_size(Db_conn p, int val) {
		Db_qry qry = Db_qry_sql.ddl_("PRAGMA page_size = " + Int_.Xto_str(val) + ";");
		p.Exec_qry(qry);
	}
	public static void Idx_create(Gfo_usr_dlg usr_dlg, Db_conn conn, String tbl, Db_meta_idx[] idx_ary) {
		int len = idx_ary.length;
		for (int i = 0; i < len; ++i) {
			Db_meta_idx idx = idx_ary[i];
			String idx_sql = idx.To_sql_create();
			usr_dlg.Plog_many("", "", "creating index: ~{0} ~{1}", tbl, idx_sql);
			conn.Exec_qry(Db_qry_sql.ddl_(idx.To_sql_create()));
			usr_dlg.Log_many("", "", "index created: ~{0} ~{1}", tbl, idx_sql);
		}
	}
	public static void Idx_create(Db_conn p, Db_idx_itm... idxs) {Idx_create(Gfo_usr_dlg_.Null, p, "", idxs);}
	public static void Idx_create(Gfo_usr_dlg usr_dlg, Db_conn p, String file_id, Db_idx_itm... idxs) {
		int len = idxs.length;
		for (int i = 0; i < len; i++) {
			String index = idxs[i].Xto_sql();
			usr_dlg.Plog_many("", "", "creating index: ~{0} ~{1}", file_id, index);
			p.Exec_qry(Db_qry_sql.ddl_(index));
			usr_dlg.Log_many("", "", "index created: ~{0} ~{1}", file_id, index);
		}
	}
	public static Db_conn Conn_load_or_make_(Io_url url, Bool_obj_ref created) {
		boolean exists = Io_mgr._.ExistsFil(url);
		created.Val_(!exists);
		Db_conn_info connect = exists ? Sqlite_conn_info.load_(url) : Sqlite_conn_info.make_(url); 
		Db_conn p = Db_conn_pool.I.Get_or_new(connect);
		if (!exists)
			Pragma_page_size(p, 4096);
		return p;
	}
	public static final int Stmt_arg_max = 999;					// 999 is max number of variables allowed by sqlite
	public static final boolean Supports_read_binary_stream = false;	
	public static final boolean Supports_indexed_by = true;			
	public static String X_date_to_str(DateAdp v) {return v == Date_null ? "" : v.XtoStr_fmt_iso_8561();}
	public static final DateAdp Date_null = null;
	public static final byte Wildcard_byte = Byte_ascii.Hash;
}
