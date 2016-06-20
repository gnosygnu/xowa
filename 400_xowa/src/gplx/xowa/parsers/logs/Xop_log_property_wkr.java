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
package gplx.xowa.parsers.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.envs.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*;
public class Xop_log_property_wkr implements Gfo_invk {
	private Xop_log_mgr log_mgr; private Db_conn conn; private Db_stmt stmt;
	private boolean log_enabled = true;
	private boolean include_all = true;
	private Hash_adp_bry include_props = Hash_adp_bry.cs();
	public Xop_log_property_wkr(Xop_log_mgr log_mgr, Db_conn conn) {
		this.log_mgr = log_mgr;
		this.conn = conn;
		if (log_enabled) {
			Xob_log_property_temp_tbl.Create_table(conn);
			stmt = Xob_log_property_temp_tbl.Insert_stmt(conn);
		}
	}
	public void Init_reset() {Xob_log_property_temp_tbl.Delete(conn);}
	public boolean Eval_bgn(Xoae_page page, byte[] prop) {return include_all || include_props.Has(prop);}
	public void Eval_end(Xoae_page page, byte[] prop, long invoke_time_bgn) {
		if (log_enabled && stmt != null) {
			int eval_time = (int)(Env_.TickCount() - invoke_time_bgn);
			Xob_log_property_temp_tbl.Insert(stmt, page.Ttl().Rest_txt(), prop, eval_time);
			log_mgr.Commit_chk();
		}
	}
	private void Include_props_add(String[] v) {
		int len = v.length;
		for (int i = 0; i < len; i++) {
			byte[] bry = Bry_.new_u8(v[i]);
			include_props.Add_bry_bry(bry);
		}
		include_all = false;	// set include_all to false, since specific items added
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_include_props_add))		Include_props_add(m.ReadStrAry("v", "|"));
		else if	(ctx.Match(k, Invk_log_enabled_))			log_enabled = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_include_props_add = "include_props_add", Invk_log_enabled_ = "log_enabled_";
}
class Xob_log_property_temp_tbl {
	public static void Create_table(Db_conn conn)		{Sqlite_engine_.Tbl_create(conn, Tbl_name, Tbl_sql);}
	public static void Delete(Db_conn conn) {conn.Exec_qry(Db_qry_delete.new_all_(Tbl_name));}
	public static Db_stmt Insert_stmt(Db_conn conn) {return Db_stmt_.new_insert_(conn, Tbl_name, Fld_prop_page_ttl, Fld_prop_prop_name, Fld_prop_eval_time);}
	public static void Insert(Db_stmt stmt, byte[] page_ttl, byte[] prop_name, int eval_time) {
		stmt.Clear()
		.Val_bry_as_str(page_ttl)
		.Val_bry_as_str(prop_name)
		.Val_int(eval_time)
		.Exec_insert();
	}
	public static final String Tbl_name = "log_property_temp", Fld_prop_page_ttl = "prop_page_ttl", Fld_prop_prop_name = "prop_prop_name", Fld_prop_eval_time = "prop_eval_time";
	private static final    String Tbl_sql = String_.Concat_lines_nl
		(	"CREATE TABLE IF NOT EXISTS log_property_temp"
		,	"( prop_id                  integer             NOT NULL    PRIMARY KEY AUTOINCREMENT"
		,	", prop_page_ttl            varchar(255)        NOT NULL"
		,	", prop_prop_name           varchar(255)        NOT NULL"
		,	", prop_eval_time           integer             NOT NULL"
		,	");"
		);
}
