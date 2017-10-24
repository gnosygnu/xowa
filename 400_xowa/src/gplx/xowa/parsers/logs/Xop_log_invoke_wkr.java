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
package gplx.xowa.parsers.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.envs.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.parsers.logs.*;
import gplx.xowa.xtns.scribunto.*;
public class Xop_log_invoke_wkr implements Gfo_invk {
	private Db_conn conn; private Db_stmt stmt;
	private boolean log_enabled = true;
	private Hash_adp_bry exclude_mod_names = Hash_adp_bry.cs();
	public Scrib_err_filter_mgr Err_filter_mgr() {return err_filter_mgr;} private final    Scrib_err_filter_mgr err_filter_mgr = new Scrib_err_filter_mgr();
	public Xop_log_invoke_wkr(Db_conn conn) {
		this.conn = conn;
		if (log_enabled) {
			Xop_log_invoke_tbl.Create_table(conn);
			stmt = Xop_log_invoke_tbl.Insert_stmt(conn);
		}
	}
	public void Init_reset() {Xop_log_invoke_tbl.Delete(conn);}
	public boolean Eval_bgn(Xoae_page page, byte[] mod_name, byte[] fnc_name) {return !exclude_mod_names.Has(mod_name);}
	public void Eval_end(Xoae_page page, byte[] mod_name, byte[] fnc_name, long invoke_time_bgn) {
		if (log_enabled && stmt != null) {
			int eval_time = (int)(System_.Ticks() - invoke_time_bgn);
			Xop_log_invoke_tbl.Insert(stmt, page.Ttl().Rest_txt(), mod_name, fnc_name, eval_time);
		}
	}
	private void Exclude_mod_names_add(String[] v) {
		int len = v.length;
		for (int i = 0; i < len; i++) {
			byte[] bry = Bry_.new_u8(v[i]);
			exclude_mod_names.Add_bry_bry(bry);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_exclude_mod_names_add))	Exclude_mod_names_add(m.ReadStrAry("v", "|"));
		else if	(ctx.Match(k, Invk_log_enabled_))			log_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_err_filter))				return err_filter_mgr;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_exclude_mod_names_add = "exclude_mod_names_add", Invk_log_enabled_ = "log_enabled_", Invk_err_filter = "err_filter";
}
class Xop_log_invoke_tbl {
	public static void Create_table(Db_conn conn)		{Sqlite_engine_.Tbl_create(conn, Tbl_name, Tbl_sql);}
	public static void Delete(Db_conn conn) {conn.Exec_qry(Db_qry_delete.new_all_(Tbl_name));}
	public static Db_stmt Insert_stmt(Db_conn conn) {return Db_stmt_.new_insert_(conn, Tbl_name, Fld_invk_page_ttl, Fld_invk_mod_name, Fld_invk_fnc_name, Fld_invk_eval_time);}
	public static void Insert(Db_stmt stmt, byte[] page_ttl, byte[] mod_name, byte[] fnc_name, int eval_time) {
		stmt.Clear()
		.Val_bry_as_str(page_ttl)
		.Val_bry_as_str(mod_name)
		.Val_bry_as_str(fnc_name)
		.Val_int(eval_time)
		.Exec_insert();
	}
	public static final String Tbl_name = "log_invoke_temp", Fld_invk_page_ttl = "invk_page_ttl", Fld_invk_mod_name = "invk_mod_name", Fld_invk_fnc_name = "invk_fnc_name", Fld_invk_eval_time = "invk_eval_time";
	private static final    String Tbl_sql = String_.Concat_lines_nl
		(	"CREATE TABLE IF NOT EXISTS log_invoke_temp"
		,	"( invk_id                  integer             NOT NULL    PRIMARY KEY AUTOINCREMENT"
		,	", invk_page_ttl            varchar(255)        NOT NULL"
		,	", invk_mod_name            varchar(255)        NOT NULL"
		,	", invk_fnc_name            varchar(255)        NOT NULL"
		,	", invk_eval_time           integer             NOT NULL"
		,	");"
		);
}
