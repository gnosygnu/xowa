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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
import gplx.xowa.wikis.nss.*;
public class Xowd_site_ns_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_name, fld_case, fld_count, fld_is_alias;		
	private final    Db_conn conn;
	public Xowd_site_ns_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		this.tbl_name = schema_is_1 ? "xowa_ns" : "site_ns";
		fld_id				= flds.Add_int_pkey	("ns_id");
		fld_name			= flds.Add_str		("ns_name", 255);
		fld_case			= flds.Add_byte		("ns_case");
		fld_is_alias		= flds.Add_bool		("ns_is_alias");
		fld_count			= flds.Add_int		("ns_count");
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert(Xow_ns_mgr ns_mgr) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		int len = ns_mgr.Ids_len();
		for (int i = 0; i < len; i++) {
			Xow_ns ns = ns_mgr.Ids_get_at(i);
			stmt.Clear()
				.Val_int(fld_id, ns.Id())
				.Val_str(fld_name, ns.Name_db_str())
				.Val_byte(fld_case, ns.Case_match())
				.Val_bool_as_byte(fld_is_alias, ns.Is_alias())
				.Val_int(fld_count, ns.Count())
				.Exec_insert();
				;
		}
	}
	public void Select_all(Xow_ns_mgr ns_mgr) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			ns_mgr.Clear();
			while (rdr.Move_next()) {
				int ns_id			= rdr.Read_int(fld_id);
				byte[] ns_name		= rdr.Read_bry_by_str(fld_name);
				byte ns_case_match	= rdr.Read_byte(fld_case);
				int ns_count		= rdr.Read_int(fld_count);
				boolean ns_is_alias	= rdr.Read_byte(fld_is_alias) == Bool_.Y_byte;
				ns_mgr.Add_new(ns_id, ns_name, ns_case_match, ns_is_alias);
				if (ns_id < 0) continue;			// don't load counts for Special / Media					
				Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
				ns.Count_(ns_count);
				if (ns_count > 0) ns.Exists_(true);	// ns has article; mark it as exists, else Talk tab won't show; DATE:2013-12-04
			}
			ns_mgr.Init();
		}	finally {rdr.Rls();}
	}
	public int Select_ns_count(int ns_id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, String_.Ary(fld_id))
				.Crt_int(fld_id, ns_id)
				.Exec_select__rls_auto();
		try {
			return rdr.Move_next() ? Int_.cast(rdr.Read_int(fld_count)) : 0;
		}	finally {rdr.Rls();}
	}
	public void Update_ns_count(int ns_id, int ns_count) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.site_ns: update started: ns_id=~{0} ns_count=~{1}", ns_id, ns_count);
		Db_stmt stmt = conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_count);
		stmt.Clear()
			.Val_int(fld_count, ns_count)
			.Crt_int(fld_id, ns_id)
			.Exec_update();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.site_ns: update done");
	}
	public void Rls() {}

	public static final String TBL_NAME = "site_ns";
	public static Xowd_site_ns_tbl Get_by_key(Db_tbl_owner owner) {return (Xowd_site_ns_tbl)owner.Tbls__get_by_key(TBL_NAME);}
}
