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
package gplx.xowa.htmls.core.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.dbs.*;
class Xob_link_dump_tbl implements Rls_able {
	public static final String Tbl_name = "link_dump"; private static final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public static final    String
	  Fld_uid				= flds.Add_int_pkey_autonum("uid")
	, Fld_src_page_id		= flds.Add_int("src_page_id")
	, Fld_src_html_uid		= flds.Add_int("src_html_uid")
	, Fld_trg_page_id		= flds.Add_int_dflt("trg_page_id", -1)
	, Fld_trg_ns			= flds.Add_int("trg_ns")
	, Fld_trg_ttl			= flds.Add_str("trg_ttl", 255)
	;		
	private Db_stmt stmt_insert;
	public Xob_link_dump_tbl(Db_conn conn) {
		this.conn = conn;
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(Tbl_name, flds));}
	public void Create_idx_1() {
		conn.Meta_idx_create
		( Dbmeta_idx_itm.new_normal_by_tbl(Tbl_name, "src", Fld_src_page_id, Fld_src_html_uid)
		, Dbmeta_idx_itm.new_normal_by_tbl(Tbl_name, "trg_temp", Fld_trg_ns, Fld_trg_ttl)
		);
	}
	public void Create_idx_2() {
		conn.Meta_idx_create
		( Dbmeta_idx_itm.new_normal_by_tbl(Tbl_name, "trg", Fld_trg_page_id, Fld_src_page_id, Fld_src_html_uid)
		);			
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert_bgn() {conn.Txn_bgn("bldr__link_dump");}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int src_page_id, int src_html_uid, int trg_ns, byte[] trg_ttl) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(Tbl_name, flds.To_str_ary_wo_autonum());
		stmt_insert.Clear().Val_int(Fld_src_page_id, src_page_id)
			.Val_int(Fld_src_html_uid, src_html_uid).Val_int(Fld_trg_page_id, -1).Val_int(Fld_trg_ns, trg_ns).Val_bry_as_str(Fld_trg_ttl, trg_ttl)
			.Exec_insert();
	}
	public Db_rdr Select_missing() {
		return conn.Stmt_select_order(Tbl_name, flds, String_.Ary(Fld_trg_page_id), Fld_src_page_id, Fld_src_html_uid)
			.Crt_int(Fld_trg_page_id, -1).Exec_select__rls_auto();
	}
	public static Xob_link_dump_tbl Get_or_new(Xow_wiki wiki) {
		Db_conn_bldr_data conn_data = Db_conn_bldr.Instance.Get_or_new(wiki.Fsys_mgr().Root_dir().GenSubFil("xowa.temp.redlink.sqlite3"));
		Xob_link_dump_tbl rv = new Xob_link_dump_tbl(conn_data.Conn());
		if (conn_data.Created()) rv.Create_tbl();
		return rv;
	}
}
