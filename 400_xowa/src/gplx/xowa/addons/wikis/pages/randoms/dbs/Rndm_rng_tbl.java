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
public class Rndm_rng_tbl implements Rls_able {
	private final    String tbl_name = "rndm_rng"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_qry_idx, fld_rng_idx, fld_seq_bgn, fld_seq_end;
	private final    Db_conn conn;
	public Rndm_rng_tbl(Db_conn conn) {
		this.conn = conn;
		fld_qry_idx		= flds.Add_int("qry_idx");
		fld_rng_idx		= flds.Add_int("rng_idx");
		fld_seq_bgn		= flds.Add_int("seq_bgn");
		fld_seq_end		= flds.Add_int("seq_end");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx() {conn.Meta_idx_create(tbl_name, "core", fld_qry_idx, fld_seq_bgn, fld_seq_end);}
	public Rndm_rng_itm Select_by_rng_idx_or_noop(int qry_idx, int rng_idx) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_rng_idx).Crt_int(fld_rng_idx, rng_idx).Exec_select__rls_auto();
		try		{return Load_or_noop(rdr);}
		finally {rdr.Rls();}
	}
	public Rndm_rng_itm Select_by_rndm_num_or_noop(int qry_idx, int rndm_num) {
		Db_rdr rdr = conn.Stmt_sql(String_.Format("SELECT * FROM rndm_rng WHERE qry_idx = {0} AND seq_bgn <= {1} AND seq_end > {2}", qry_idx, rndm_num)).Exec_select__rls_auto();	// ANSI.Y
		try		{return Load_or_noop(rdr);}
		finally {rdr.Rls();}
	}
	private Rndm_rng_itm Load_or_noop(Db_rdr rdr) {
		return (rdr.Move_next())
			? new Rndm_rng_itm(rdr.Read_int(fld_qry_idx), rdr.Read_int(fld_rng_idx), rdr.Read_int(fld_seq_bgn), rdr.Read_int(fld_seq_end))
			: Rndm_rng_itm.Noop();
	}
	public Db_stmt Insert_stmt() {return conn.Stmt_insert(tbl_name, flds);}
	public void Insert(Db_stmt stmt, int qry_idx, int rng_idx, int seq_bgn, int seq_end) {
		stmt.Clear().Val_int(fld_qry_idx, qry_idx).Val_int(fld_rng_idx, rng_idx).Val_int(fld_seq_bgn, seq_bgn).Val_int(fld_seq_end, seq_end).Exec_insert();
	}
	public void Delete_by_qry_idx(int qry_idx) {conn.Stmt_delete(tbl_name, fld_qry_idx).Crt_int(fld_qry_idx, qry_idx).Exec_delete();}
	public void Rls() {}
}
