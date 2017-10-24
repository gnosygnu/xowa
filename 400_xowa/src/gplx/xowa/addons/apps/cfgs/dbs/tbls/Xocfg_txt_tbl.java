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
public class Xocfg_txt_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__nde_id, fld__nde_lang, fld__nde_name, fld__nde_help;
	private final    Db_conn conn;
	public Xocfg_txt_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "cfg_txt";
		this.fld__nde_id			= flds.Add_int_pkey("nde_id");			// EX: '2'
		this.fld__nde_lang			= flds.Add_str("nde_lang", 16);			// EX: 'en'
		this.fld__nde_name			= flds.Add_str("nde_name", 255);		// EX: 'Cfg Name'
		this.fld__nde_help			= flds.Add_str("nde_help", 4096);		// EX: 'Help text'
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Upsert(int nde_id, String nde_lang, String nde_name, String nde_help) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld__nde_id, fld__nde_lang), nde_id, nde_lang, nde_name, nde_help);
	}
	public Xocfg_txt_itm Select_by_id_or_null(int id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__nde_id).Crt_int(fld__nde_id, id).Exec_select__rls_auto();
		try {return rdr.Move_next() ? Load(rdr) : null;}
		finally {rdr.Rls();}
	}
	private Xocfg_txt_itm Load(Db_rdr rdr) {
		return new Xocfg_txt_itm
		( rdr.Read_int(fld__nde_id)
		, rdr.Read_str(fld__nde_lang)
		, rdr.Read_str(fld__nde_name)
		, rdr.Read_str(fld__nde_help)
		);
	}
	public void Rls() {}
}
