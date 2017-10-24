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
public class Xocfg_grp_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__grp_id, fld__grp_key;
	private final    Db_conn conn;
	public Xocfg_grp_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld__grp_id			= flds.Add_int_pkey("grp_id");
		this.fld__grp_key			= flds.Add_str("grp_key", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = TBL_NAME;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, fld__grp_key, fld__grp_key)
		));
	}
	public void Upsert(int grp_id, String grp_key) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__grp_id), grp_id, grp_key);
	}
	public int Select_id_by_key_or_fail(String key) {
		Xocfg_grp_row itm = this.Select_by_key_or_null(key);
		if (itm == null) throw Err_.new_wo_type("cfg.grp:invalid key", "key", key);
		return itm.Id();
	}
	public Xocfg_grp_row Select_by_key_or_null(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__grp_key).Crt_str(fld__grp_key, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? Load(rdr): null;}
		finally {rdr.Rls();}
	}
	private Xocfg_grp_row Load(Db_rdr rdr) {
		return new Xocfg_grp_row
		( rdr.Read_int(fld__grp_id)
		, rdr.Read_str(fld__grp_key)
		);
	}
	public void Rls() {}
	public static final String TBL_NAME = "cfg_grp";
}
