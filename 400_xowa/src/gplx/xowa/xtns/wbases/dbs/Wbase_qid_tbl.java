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
package gplx.xowa.xtns.wbases.dbs;
import gplx.frameworks.objects.Rls_able;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.Db_stmt_;
import gplx.dbs.DbmetaFldList;
import gplx.dbs.Dbmeta_idx_itm;
import gplx.dbs.Dbmeta_tbl_itm;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.Xoa_app_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.wikis.data.Xow_db_file;
import gplx.xowa.wikis.data.Xow_db_mgr;
public class Wbase_qid_tbl implements Rls_able {
	private final Object thread_lock = new Object();
	private final String tbl_name; private final DbmetaFldList flds = new DbmetaFldList();
	private final String fld_src_wiki, fld_src_ns, fld_src_ttl, fld_trg_ttl;
	private final Db_conn conn; private Db_stmt stmt_select, stmt_insert;
	private boolean src_ttl_has_spaces;
	Wbase_qid_tbl(Db_conn conn, boolean schema_is_1, boolean src_ttl_has_spaces) {
		this.conn = conn; 
		this.src_ttl_has_spaces = src_ttl_has_spaces;
		String fld_prefix = "";
		if (schema_is_1)	{tbl_name = "wdata_qids"; fld_prefix = "wq_";}
		else				{tbl_name = "wbase_qid";}
		fld_src_wiki		= flds.AddStr(fld_prefix + "src_wiki", 255);
		fld_src_ns			= flds.AddInt(fld_prefix + "src_ns");
		fld_src_ttl			= flds.AddStr(fld_prefix + "src_ttl", 512);
		fld_trg_ttl			= flds.AddStr(fld_prefix + "trg_ttl", 512);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx() {conn.Meta_idx_create(Xoa_app_.Usr_dlg(), Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "src", fld_src_wiki, fld_src_ns, fld_src_ttl));}
	public void Insert_bgn() {conn.Txn_bgn("schema__wbase_qid__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(byte[] src_wiki, int src_ns, byte[] src_ttl, byte[] trg_ttl) {
		stmt_insert.Clear()
			.Val_bry_as_str(fld_src_wiki, src_wiki).Val_int(fld_src_ns, src_ns).Val_bry_as_str(fld_src_ttl, src_ttl).Val_bry_as_str(fld_trg_ttl,trg_ttl)
			.Exec_insert();
	}
	public byte[] Select_qid(byte[] src_wiki, int src_ns, byte[] src_ttl) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_src_wiki, fld_src_ns, fld_src_ttl);
		synchronized (stmt_select) {	// LOCK:stmt-rls; DATE:2016-07-06
			if (src_ttl_has_spaces) src_ttl = Xoa_ttl.Replace_unders(src_ttl);	// NOTE: v2.4.2.1-v2.4.3.2 stores ttl in spaces ("A B"), while xowa will use under form ("A_B"); DATE:2015-04-21
			Db_rdr rdr = stmt_select.Clear()
					.Crt_bry_as_str(fld_src_wiki, src_wiki).Crt_int(fld_src_ns, src_ns).Crt_bry_as_str(fld_src_ttl, src_ttl)
					.Exec_select__rls_manual();
			try {
				byte[] rv = null;
				if (rdr.Move_next()) {
					rv = rdr.Read_bry_by_str(fld_trg_ttl);
					if (rv != null) {
						if (rv.length > 0 && rv[0] == AsciiByte.Ltr_q)
							rv[0] = AsciiByte.Ltr_Q;
					}
				}
				return rv;
			} finally {rdr.Rls();}
		}
	}
	public void Rls() {
		synchronized (thread_lock) {
			stmt_insert = Db_stmt_.Rls(stmt_insert);
			stmt_select = Db_stmt_.Rls(stmt_select);
		}
	}
	public static Wbase_qid_tbl New_load(Xow_db_mgr core_db_mgr) {
		Xow_db_file core_db = core_db_mgr.Db__core();
		return new Wbase_qid_tbl(core_db_mgr.Db__wbase().Conn()
			, core_db.Db_props().Schema_is_1()
			, core_db.Schema_props().Wbase__qid__src_ttl_has_spaces()
			);
	}
	public static Wbase_qid_tbl New_make(Db_conn conn, boolean src_ttl_has_spaces) {
		return new Wbase_qid_tbl(conn, BoolUtl.N, src_ttl_has_spaces);
	}
}
