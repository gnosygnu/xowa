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
	public static void Idx_create(Gfo_usr_dlg usr_dlg, Db_conn conn, String tbl, Dbmeta_idx_itm[] idx_ary) {
		int len = idx_ary.length;
		for (int i = 0; i < len; ++i) {
			Dbmeta_idx_itm idx = idx_ary[i];
			String idx_sql = idx.To_sql_create(conn.Engine().Sql_wtr());
			usr_dlg.Plog_many("", "", "creating index: ~{0} ~{1}", tbl, idx_sql);
			conn.Exec_qry(Db_qry_sql.ddl_(idx.To_sql_create(conn.Engine().Sql_wtr())));
			usr_dlg.Log_many("", "", "index created: ~{0} ~{1}", tbl, idx_sql);
		}
	}
	public static void Idx_create(Db_conn p, Db_idx_itm... idxs) {Idx_create(Gfo_usr_dlg_.Noop, p, "", idxs);}
	public static void Idx_create(Gfo_usr_dlg usr_dlg, Db_conn p, String file_id, Db_idx_itm... idxs) {
		int len = idxs.length;
		for (int i = 0; i < len; i++) {
			String index = idxs[i].Xto_sql();
			usr_dlg.Plog_many("", "", "creating index: ~{0} ~{1}", file_id, index);
			p.Exec_qry(Db_qry_sql.ddl_(index));
			usr_dlg.Log_many("", "", "index created: ~{0} ~{1}", file_id, index);
		}
	}
	public static final int Stmt_arg_max = 999;					// 999 is max number of variables allowed by sqlite
	public static final boolean Supports_read_binary_stream = false;	
	public static final boolean Supports_indexed_by = true;			
	public static String X_date_to_str(DateAdp v) {return v == Date_null ? "" : v.XtoStr_fmt_iso_8561();}
	public static final    DateAdp Date_null = null;
	public static final byte Wildcard_byte = Byte_ascii.Hash;
}
