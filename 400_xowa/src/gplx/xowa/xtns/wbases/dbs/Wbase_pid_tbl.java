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
package gplx.xowa.xtns.wbases.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.dbs.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.core.*;
import gplx.xowa.wikis.data.*;
public class Wbase_pid_tbl implements Rls_able {
	private final String tbl_name; private final Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final String fld_src_lang, fld_src_ttl, fld_trg_ttl;
	private final Db_conn conn; private Db_stmt stmt_select, stmt_insert;
	Wbase_pid_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		String fld_prefix = "";
		if (schema_is_1)	{tbl_name = "wdata_pids"; fld_prefix = "wp_";}
		else				{tbl_name = "wbase_pid";}
		fld_src_lang		= flds.Add_str(fld_prefix + "src_lang", 255);
		fld_src_ttl			= flds.Add_str(fld_prefix + "src_ttl", 512);
		fld_trg_ttl			= flds.Add_str(fld_prefix + "trg_ttl", 512);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx() {conn.Meta_idx_create(Xoa_app_.Usr_dlg(), Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "src", fld_src_lang, fld_src_ttl));}
	public void Insert_bgn() {conn.Txn_bgn("schema__wbase_pid__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(byte[] src_lang, byte[] src_ttl, byte[] trg_ttl) {
		stmt_insert.Clear()
			.Val_bry_as_str(fld_src_lang, src_lang).Val_bry_as_str(fld_src_ttl, src_ttl).Val_bry_as_str(fld_trg_ttl, trg_ttl)
			.Exec_insert();
	}
	public int Select_pid(byte[] src_lang, byte[] src_ttl) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_src_lang, fld_src_ttl);
		Db_rdr rdr = stmt_select.Clear()
				.Crt_bry_as_str(fld_src_lang, src_lang).Crt_bry_as_str(fld_src_ttl, src_ttl)
				.Exec_select__rls_manual();
		try {
			if (!rdr.Move_next()) return Wbase_pid.Id_null;	// occurs when pid exists, but does not have entry for language; see hu.w:Marco Polo argali; DATE: 2014-02-01
			byte[] pid_bry = rdr.Read_bry_by_str(fld_trg_ttl);
			return pid_bry == null ?  Wbase_pid.Id_null : Bry_.To_int_or(pid_bry, 1, pid_bry.length, Wbase_pid.Id_null);
		}
		catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "db.wdata_pids:failed to select pid; lang=~{0} src_ttl=~{1} err=~{2}", src_lang, src_ttl, Err_.Message_gplx_log(e));
			try {stmt_select.Rls();}
			catch (Exception e2) {Gfo_usr_dlg_.Instance.Warn_many("", "", "db.wdata_pids: failed to rls stmt; err=~{0}", Err_.Message_gplx_log(e2));}
			stmt_select = null;
			return Wbase_pid.Id_null;
		}
		finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public static Wbase_pid_tbl New_load(Xow_db_mgr core_db_mgr) {
		return new Wbase_pid_tbl(core_db_mgr.Db__wbase().Conn(), core_db_mgr.Props().Schema_is_1());
	}
	public static Wbase_pid_tbl New_make(Db_conn conn) { // schema_is_1 == false b/c new wbase_db will always be latest version
		return new Wbase_pid_tbl(conn, Bool_.N);
	}
}
