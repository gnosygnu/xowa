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
package gplx.xowa.addons.wikis.pages.randoms.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.randoms.*;
import gplx.core.ios.*; import gplx.dbs.*; import gplx.dbs.utls.*;
public class Rndm_seq_tbl implements Rls_able {	// list of page_ids w/ random_idx; EX: 0,123|1,23|2,31|...
	private final    String fld_qry_idx, fld_rng_idx, fld_seq_idx, fld_page_id;
	private final    Db_conn conn;
	public Rndm_seq_tbl(Db_conn conn) {
		this.conn = conn;
		fld_qry_idx			= flds.Add_int("qry_idx");
		fld_rng_idx			= flds.Add_int("rng_idx");
		fld_seq_idx			= flds.Add_int("seq_idx");
		fld_page_id 		= flds.Add_int("page_id");
	}
	public Db_conn Conn() {return conn;}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "rndm_seq"; 
	public Dbmeta_fld_list Flds() {return flds;} private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public String Fld__qry_idx() {return fld_qry_idx;}
	public String Fld__rng_idx() {return fld_rng_idx;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx() {conn.Meta_idx_create(tbl_name, "core", fld_qry_idx, fld_rng_idx, fld_seq_idx);}
	public int Select_or_neg_1(int qry_idx, int rng_idx, int seq_idx) {
		Db_stmt stmt = conn.Stmt_select(tbl_name, flds, fld_qry_idx, fld_rng_idx, fld_seq_idx);
		Db_rdr rdr = stmt.Clear().Crt_int(fld_qry_idx, qry_idx).Crt_int(fld_rng_idx, rng_idx).Val_int(fld_seq_idx, seq_idx).Exec_select__rls_auto();
		try {return rdr.Move_next() ? rdr.Read_int(fld_page_id) : -1;}
		catch (Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to get seq_idx; url=~{0} qry_idx=~{1} rng_idx=~{2} seq_idx=~{3} err=~{4}", conn.Conn_info().Db_api(), qry_idx, rng_idx, seq_idx, Err_.Message_gplx_log(e)); return -1;}
		finally {rdr.Rls();}
	}
	public Db_stmt Insert_stmt() {return conn.Stmt_insert(tbl_name, flds);}
	public void Insert(Db_stmt stmt, int qry_idx, int rng_idx, int seq_idx, int page_id) {
		stmt.Clear().Val_int(fld_qry_idx, qry_idx).Val_int(fld_rng_idx, rng_idx).Val_int(fld_seq_idx, seq_idx).Val_int(fld_page_id, page_id).Exec_insert();
	}
	public void Delete_by_qry_idx(int qry_idx) {conn.Stmt_delete(tbl_name, fld_qry_idx).Crt_int(fld_qry_idx, qry_idx).Exec_delete();}
	public void Rls() {}

	public static final int Db_row_size_fixed = 4 * 4;
}
