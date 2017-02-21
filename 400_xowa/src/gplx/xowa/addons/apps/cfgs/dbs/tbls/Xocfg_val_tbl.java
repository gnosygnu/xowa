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
package gplx.xowa.addons.apps.cfgs.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.dbs.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xocfg_val_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__itm_key, fld__itm_ctx, fld__itm_val, fld__itm_date;
	private final    Db_conn conn;
	public Xocfg_val_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "cfg_val";
		this.fld__itm_key			= flds.Add_str("itm_key", 255);			// EX: 'xowa.app.cfg_1'
		this.fld__itm_ctx			= flds.Add_str("itm_ctx", 255);			// EX: 'app'; 'en.w'; 'ns-10'
		this.fld__itm_val			= flds.Add_str("itm_val", 4096);		// EX: 'abc'
		this.fld__itm_date			= flds.Add_str("itm_date", 16);			// EX: '20160901_010203'
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "core", fld__itm_key, fld__itm_ctx)	// NOTE: key,ctx b/c key has greater specificity
		));
	}
	public void Upsert(String ctx, String key, String val, String date) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__itm_key, fld__itm_ctx), key, ctx, val, date);
	}
	public void Delete(String ctx, String key) {
		conn.Stmt_delete(tbl_name, fld__itm_key, fld__itm_ctx).Crt_str(fld__itm_key, key).Crt_str(fld__itm_ctx, ctx).Exec_delete();
	}
	public Xocfg_val_row Select_one_or_null(String ctx, String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__itm_key, fld__itm_ctx).Crt_str(fld__itm_key, key).Crt_str(fld__itm_ctx, ctx).Exec_select__rls_auto();
		try {return rdr.Move_next() ? Load(rdr) : null;}
		finally {rdr.Rls();}
	}
	public Xocfg_val_row[] Select_all(String key) {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__itm_key).Crt_str(fld__itm_key, key).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				list.Add(Load(rdr));
			}
		}
		finally {rdr.Rls();}
		return (Xocfg_val_row[])list.To_ary_and_clear(Xocfg_val_row.class);
	}
	private Xocfg_val_row Load(Db_rdr rdr) {
		return new Xocfg_val_row
		( rdr.Read_str(fld__itm_key)
		, rdr.Read_str(fld__itm_ctx)
		, rdr.Read_str(fld__itm_val)
		, rdr.Read_str(fld__itm_date)
		);
	}
	public void Rls() {}
}
