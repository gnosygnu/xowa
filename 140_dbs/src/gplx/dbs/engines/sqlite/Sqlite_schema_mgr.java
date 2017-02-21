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
import gplx.dbs.qrys.*;
import gplx.dbs.metas.*; import gplx.dbs.metas.parsers.*;
public class Sqlite_schema_mgr implements Dbmeta_reload_cmd {
	private final    Db_engine engine; private boolean init = true;
	private final    Dbmeta_idx_mgr idx_mgr = new Dbmeta_idx_mgr();		
	public Sqlite_schema_mgr(Db_engine engine) {
		this.engine = engine;
		this.tbl_mgr = new Dbmeta_tbl_mgr(this);
	}
	public Dbmeta_tbl_mgr Tbl_mgr() {
		if (init) Init(engine);
		return tbl_mgr;
	}	private final    Dbmeta_tbl_mgr tbl_mgr;
	public boolean Tbl_exists(String name) {
		if (init) Init(engine);
		return tbl_mgr.Has(name);
	}
	public boolean Fld_exists(String tbl, String fld) {
		if (init) Init(engine);
		Dbmeta_tbl_itm tbl_itm = tbl_mgr.Get_by(tbl);
		return tbl_itm == null ? false : tbl_itm.Flds().Has(fld);
	}
	public boolean Idx_exists(String idx) {
		if (init) Init(engine);
		return idx_mgr.Has(idx);
	}
	public void Load_all() {
		Init(engine);
	}
	private void Init(Db_engine engine) {
		init = false;
		tbl_mgr.Clear(); idx_mgr.Clear();
		Dbmeta_parser__tbl tbl_parser = new Dbmeta_parser__tbl();
		Dbmeta_parser__idx idx_parser = new Dbmeta_parser__idx();
		Db_qry__select_in_tbl qry = Db_qry__select_in_tbl.new_("sqlite_master", String_.Ary_empty, String_.Ary("type", "name", "sql"), Db_qry__select_in_tbl.Order_by_null);
		Db_rdr rdr = engine.Stmt_by_qry(qry).Exec_select__rls_auto();	
		try {
			Gfo_usr_dlg_.Instance.Log_many("", "", "db.schema.load.bgn: conn=~{0}", engine.Conn_info().Db_api());
			while (rdr.Move_next()) {
				String type_str = rdr.Read_str("type");
				String name = rdr.Read_str("name");
				String sql = rdr.Read_str("sql");
				try {
					int type_int = Dbmeta_itm_tid.Xto_int(type_str);
					switch (type_int) {
						case Dbmeta_itm_tid.Tid_table:
							if (String_.Has_at_bgn(name, "sqlite_")) continue;	// ignore b/c of non-orthodox syntax; EX: "CREATE TABLE sqlite_sequence(name, seq)"; also "CREATE TABLE sqlite_stat(tbl,idx,stat)";
							tbl_mgr.Add(tbl_parser.Parse(Bry_.new_u8(sql)));
							break;
						case Dbmeta_itm_tid.Tid_index:
							if (sql == null) continue; // ignore "autoindex"; EX: sqlite_autoindex_temp_page_len_avg_1
							idx_mgr.Add(idx_parser.Parse(Bry_.new_u8(sql)));
							break;
						default:
							Gfo_usr_dlg_.Instance.Log_many("", "", "db.schema.unknown type: conn=~{0} type=~{1} name=~{2} sql=~{3}", engine.Conn_info().Db_api(), type_str, name, sql);
							break;
					}
				} catch (Exception e) {	// tables / indexes may be unparseable; skip them; EX: CREATE TABLE unparseable (col_1 /*comment*/ int); DATE:2016-06-08
					Gfo_usr_dlg_.Instance.Log_many("", "", "db.schema.unparseable: conn=~{0} type=~{1} name=~{2} sql=~{3} err=~{4}", engine.Conn_info().Db_api(), type_str, name, sql, Err_.Message_gplx_log(e));
				}
			}
		}	finally {rdr.Rls();}
	}
}
