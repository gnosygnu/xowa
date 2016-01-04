/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
class Gfdb_diff_wkr__db implements Gfdb_diff_wkr {
	private Gfdb_diff_tbl lhs_tbl, rhs_tbl;
	private Db_meta_fld[] val_flds; private int val_flds_len;
	private Db_conn diff_conn; private Db_stmt stmt;
	private int uid__upsert, uid__delete; private int prog_interval, prog_count;
	public void Init_conn(Gfdb_diff_db diff_db, int prog_interval) {this.diff_conn = diff_db.Conn(); this.prog_interval = prog_interval;}
	public void Init_tbls(Gfdb_diff_tbl lhs_tbl, Gfdb_diff_tbl rhs_tbl) {
		this.lhs_tbl = lhs_tbl; this.rhs_tbl = rhs_tbl;
		this.val_flds = lhs_tbl.Vals(); val_flds_len = val_flds.length;
		this.uid__upsert = 0; uid__delete = 0; this.prog_count = 0;
		String tbl_name = rhs_tbl.Name();
		Db_meta_fld[] diff_flds = Gfdb_diff_wkr__db_.New_diff_flds(rhs_tbl.Flds());
		if (!diff_conn.Meta_tbl_exists(tbl_name)) diff_conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, diff_flds));
		this.stmt = diff_conn.Stmt_insert(tbl_name, Gfdb_diff_wkr__db_.To_str_ary(diff_flds));
		diff_conn.Txn_bgn("diff_db");
	}
	public void Term_tbls() {
		diff_conn.Txn_end();
	}
	public void Handle_same() {
		if (Gfdb_rdr_utl_.Compare(val_flds, val_flds_len, lhs_tbl.Rdr(), rhs_tbl.Rdr()) != CompareAble_.Same)
			Insert(Gfdb_diff_db_.Tid__update, uid__upsert++, rhs_tbl.Flds(), rhs_tbl.Rdr());
	}
	public void Handle_lhs_missing() {Insert(Gfdb_diff_db_.Tid__insert, uid__upsert++, rhs_tbl.Flds(), rhs_tbl.Rdr());}
	public void Handle_rhs_missing() {Insert(Gfdb_diff_db_.Tid__delete, uid__delete++, lhs_tbl.Keys(), lhs_tbl.Rdr());}
	private void Insert(byte diff_type, int uid, Db_meta_fld[] flds, Db_rdr rdr) {
		stmt.Val_int	(Gfdb_diff_db_.Fld__diff_site	, -1)
			.Val_int	(Gfdb_diff_db_.Fld__diff_time	, -1)
			.Val_int	(Gfdb_diff_db_.Fld__diff_db_trg	, -1)
			.Val_int	(Gfdb_diff_db_.Fld__diff_db_src	, -1)
			.Val_byte	(Gfdb_diff_db_.Fld__diff_type	, diff_type)
			.Val_int	(Gfdb_diff_db_.Fld__diff_type	, uid)
			;
		Gfdb_rdr_utl_.Stmt_args(stmt, flds, flds.length, rdr);
		stmt.Exec_insert();
		if ((++prog_count % prog_interval) == 0) diff_conn.Txn_sav();
	}
}
class Gfdb_diff_wkr__db_ {
	public static Db_meta_fld[] New_diff_flds(Db_meta_fld[] all_flds) {
		int len = all_flds.length;
		int system_flds = 6;
		Db_meta_fld[] rv = new Db_meta_fld[len + system_flds];
		rv[0] = Db_meta_fld.new_int	(Gfdb_diff_db_.Fld__diff_site);
		rv[1] = Db_meta_fld.new_int	(Gfdb_diff_db_.Fld__diff_time);
		rv[2] = Db_meta_fld.new_int	(Gfdb_diff_db_.Fld__diff_db_trg);
		rv[3] = Db_meta_fld.new_int	(Gfdb_diff_db_.Fld__diff_db_src);
		rv[4] = Db_meta_fld.new_byte(Gfdb_diff_db_.Fld__diff_type);
		rv[5] = Db_meta_fld.new_int (Gfdb_diff_db_.Fld__diff_uid);
		for (int i = 0; i < len; ++i) {
			Db_meta_fld orig_fld = all_flds[i];
			Db_meta_fld diff_fld = new Db_meta_fld(orig_fld.Name(), orig_fld.Tid(), orig_fld.Len()).Nullable_y_();	// keep same name and type, but make nullable
			all_flds[i + system_flds] = diff_fld;
		}
		return rv;
	}
	public static String[] To_str_ary(Db_meta_fld[] ary) {
		int len = ary.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i)
			rv[i] = ary[i].Name();
		return rv;
	}
}
