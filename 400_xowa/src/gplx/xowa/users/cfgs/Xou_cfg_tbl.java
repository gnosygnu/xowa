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
package gplx.xowa.users.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
public class Xou_cfg_tbl implements Db_tbl {
	public final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_key, fld_usr, fld_ctx, fld_val;		
	public Xou_cfg_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_usr				= flds.Add_int		("cfg_usr");			// EX: 1=anonymous; others will require usr_regy
		this.fld_ctx				= flds.Add_str		("cfg_ctx", 1024);		// EX: "app"; "en.w"
		this.fld_key				= flds.Add_str		("cfg_key", 1024);		// EX: "xowa.net.web_enabled"
		this.fld_val				= flds.Add_str		("cfg_val", 4096);		// EX: "y"
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "user_cfg"; 
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld_key, fld_key)
		));
	}
	public void Upsert(boolean insert, int usr, String ctx, String key, String val) {
		if (insert)
			Insert(usr, ctx, key, val);
		else
			Update(usr, ctx, key, val);
	}
	private void Insert(int usr, String ctx, String key, String val) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Clear().Val_int(fld_usr, usr).Val_str(fld_ctx, ctx).Val_str(fld_key, key).Val_str(fld_val, val).Exec_insert();
		stmt.Rls();
	}
	private void Update(int usr, String ctx, String key, String val) {
		Db_stmt stmt = conn.Stmt_update(tbl_name, String_.Ary(fld_usr, fld_ctx, fld_key), fld_val);
		stmt.Clear().Val_str(fld_val, val).Crt_int(fld_usr, usr).Crt_str(fld_ctx, ctx).Crt_str(fld_key, key).Exec_update();
		stmt.Rls();
	}
	public Xou_cfg_itm[] Select_by_usr_ctx(int usr, String ctx) {
		List_adp list = List_adp_.New();
		Db_stmt stmt_select = conn.Stmt_select(tbl_name, flds, fld_usr, fld_ctx);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_usr, usr).Crt_str(fld_ctx, ctx).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				list.Add(Make_itm(rdr));
			}
		} finally {rdr.Rls();}
		return (Xou_cfg_itm[])list.To_ary_and_clear(Xou_cfg_itm.class);
	}
	public void Rls() {}

	private Xou_cfg_itm Make_itm(Db_rdr rdr) {
		return new Xou_cfg_itm(rdr.Read_int(fld_usr), rdr.Read_str(fld_ctx), rdr.Read_str(fld_key), rdr.Read_str(fld_val));
	}		
}
