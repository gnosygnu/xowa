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
package gplx.xowa.addons.bldrs.files.cksums.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.addons.bldrs.files.cksums.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.addons.wikis.ctgs.*; 
public class Xocksum_cksum_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__fil_id, fld__thm_id, fld__bin_db_id, fld__bin_len, fld__cksum_tid, fld__cksum_count, fld__cksum_val, fld__cksum_date;
	private Db_stmt stmt__update;
	public Xocksum_cksum_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = "fsdb_cksum";
		this.fld__fil_id		= flds.Add_int("fil_id");
		this.fld__thm_id		= flds.Add_int("thm_id");
		this.fld__bin_db_id		= flds.Add_int("bin_db_id");
		this.fld__bin_len		= flds.Add_long("bin_size");
		this.fld__cksum_tid		= flds.Add_byte("cksum_tid");
		this.fld__cksum_count	= flds.Add_int("cksum_count");
		this.fld__cksum_val		= flds.Add_str("cksum_val", 255);
		this.fld__cksum_date	= flds.Add_str("cksum_date", 16);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn; 
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; 
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx() {
		conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "fil_id__thm_id", fld__fil_id, fld__thm_id));
		conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld__cksum_val, fld__cksum_val));
	}
	public void Insert_missing() {
		// insert from fsdb_fil
		conn.Exec_sql(Db_sql_.Make_by_fmt(String_.Ary
		( "INSERT INTO fsdb_cksum (fil_id, thm_id, bin_db_id, bin_size, cksum_tid, cksum_count, cksum_val, cksum_date)"
		, "SELECT  f.fil_id, -1, f.fil_bin_db_id, f.fil_size, {0}, 0, '', ''"
		, "FROM    fsdb_fil f"
		, "        LEFT JOIN fsdb_cksum c ON c.fil_id = f.fil_id AND c.thm_id = -1"
		, "WHERE   c.fil_id IS NULL"
		, "AND     f.fil_bin_db_id != -1"
		), Cksum_tid__md5));

		// insert from fsdb_fil
		conn.Exec_sql(Db_sql_.Make_by_fmt(String_.Ary
		( "INSERT INTO fsdb_cksum (fil_id, thm_id, bin_db_id, bin_size, cksum_tid, cksum_count, cksum_val, cksum_date)"
		, "SELECT  t.thm_owner_id, t.thm_id, t.thm_bin_db_id, t.thm_size, {0}, 0, '', ''"
		, "FROM    fsdb_thm t"
		, "        LEFT JOIN fsdb_cksum c ON c.fil_id = t.thm_owner_id AND c.thm_id = t.thm_id"
		, "WHERE   c.fil_id IS NULL"
		), Cksum_tid__md5));
	}
	public Db_stmt Select_samples_stmt(int count) {
		return conn.Stmt_sql(Db_sql_.Make_by_fmt(String_.Ary
		( "SELECT  *"
		, "FROM    fsdb_cksum"
		, "WHERE   cksum_val = ''"
		// , "ORDER BY cksum_count, cksum_date"
		, "LIMIT {0}"
		), count));
	}
	public Xocksum_cksum_row[] Select_samples(Db_stmt stmt) {
		List_adp rv = List_adp_.New();

		Db_rdr rdr = stmt.Exec_select__rls_manual();
		try {
			while (rdr.Move_next()) {
				rv.Add(new Xocksum_cksum_row
				( rdr.Read_int("fil_id")
				, rdr.Read_int("thm_id")
				, rdr.Read_int("bin_db_id")
				, rdr.Read_long("bin_size")
				, rdr.Read_byte("cksum_tid")
				, rdr.Read_int("cksum_count")
				, rdr.Read_bry_by_str("cksum_val")
				, rdr.Read_str("cksum_date")
				));
			}
		} finally {rdr.Rls();}

		return (Xocksum_cksum_row[])rv.To_ary_and_clear(Xocksum_cksum_row.class);
	}
	public void Update(int fil_id, int thm_id, int bin_db_id, long bin_size, byte cksum_tid, int cksum_count, byte[] cksum_val, String cksum_date) {
		if (stmt__update == null) stmt__update = conn.Stmt_update_exclude(tbl_name, flds, fld__fil_id, fld__thm_id);
		stmt__update.Clear()
			.Val_int(fld__bin_db_id, bin_db_id).Val_long(fld__bin_len, bin_size)
			.Val_byte(fld__cksum_tid, cksum_tid).Val_int(fld__cksum_count, cksum_count)
			.Val_bry_as_str(fld__cksum_val, cksum_val).Val_str(fld__cksum_date, cksum_date)
			.Crt_int(fld__fil_id, fil_id).Crt_int(fld__thm_id, thm_id)
			.Exec_update();
	}
	public void Rls() {
		this.stmt__update = Db_stmt_.Rls(stmt__update);
	}
	public static final byte Cksum_tid__md5 = 1;
}
