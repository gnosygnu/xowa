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
package gplx.dbs.diffs.merges; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import gplx.core.brys.fmtrs.*;
interface Gfdb_diff_cmd {
	void Merge_undo();
	void Merge_exec();
}
class Gfdb_diff_cmd__insert {
//      else if I
//        // txn_bgn
//        // audit
//        INSERT INTO db_temp.page (diff_type, diff, *)
//        SELECT 'D', d.page_id, *
//        FROM   db_diff.page d
//               JOIN db_main.page m ON d.page_id = m.page_id
//        WHERE  d.diff_type = 0
//        AND    d.diff_idx BETWEEN lo and hi
//        
//        // update
//        INSERT INTO db_main.page
//        SELECT  d.page_title
//        FROM    db_diff.page d
//                JOIN db_main.page m        
//        // txn_end
	private Db_conn conn;
	private String exec_sql;
	public void Init(Db_conn main_conn, Db_conn diff_conn, Db_conn temp_conn, Gfdb_diff_tbl tbl) {
		this.conn = temp_conn;
		this.exec_sql = "";
//			this.exec_sql = String_.Format(String_.Concat_lines_nl_skip_last
//			( "INSERT INTO db_curr.{tbl}"
//			), Gfdb_diff_cmd_ctx.Alias__curr);
	}
	public void Merge_exec() {
		conn.Exec_sql(exec_sql);
	}
}
class Gfdb_diff_cmd_sql_bldr {
	private final Bry_fmtr fmtr = Bry_fmtr.new_();
	private final Bry_bfr tmp_bfr = Bry_bfr.new_();
	public void Bld_insert(Bry_bfr bfr, String tbl_name, String[] keys, String[] vals, int rng_bgn, int rng_end) {
		fmtr.Fmt_(Insert__fmt).Keys_(Insert__keys);
		fmtr.Bld_bfr_many(bfr, tbl_name, Bld_flds(tmp_bfr, ", ", "d.", keys, vals), Bld_join(keys), rng_bgn, rng_end);
	}
	public void Bld_update(Bry_bfr bfr, String tbl_name, String[] keys, String[] vals, int rng_bgn, int rng_end) {
		fmtr.Fmt_(Update__fmt).Keys_(Update__keys);
		fmtr.Bld_bfr_many(bfr, tbl_name, Bld_flds(tmp_bfr, ", ", "d.", keys, vals), Bld_join(keys), rng_bgn, rng_end);
	}
	public void Bld_delete(Bry_bfr bfr, String tbl_name, String[] keys, int rng_bgn, int rng_end) {
		fmtr.Fmt_(Delete__fmt).Keys_(Delete__keys);
		fmtr.Bld_bfr_many(bfr, tbl_name, Bld_flds(tmp_bfr, " || '|' || ", "", keys, String_.Ary_empty), Bld_flds(tmp_bfr, " || '|' || ", "k.", keys, String_.Ary_empty), Bld_join(keys), rng_bgn, rng_end);
	}
	private static String Bld_flds(Bry_bfr tmp_bfr, String dlm, String alias, String[] keys, String[] vals) {
		int keys_len = keys.length;
		for (int i = 0; i < keys_len; ++i) {
			String key = keys[i];
			if (i != 0) tmp_bfr.Add_str_a7(dlm);
			tmp_bfr.Add_str_a7(alias).Add_str_a7(key);
		}
		int flds_len = vals.length;
		for (int i = 0; i < flds_len; ++i) {
			String val = vals[i];
			tmp_bfr.Add_str_a7(dlm);
			tmp_bfr.Add_str_a7(alias).Add_str_a7(val);
		}
		return tmp_bfr.To_str_and_clear();
	}
	private String Bld_join(String[] keys) {
		int len = keys.length;
		for (int i = 0; i < len; ++i) {
			String key = keys[i];
			tmp_bfr.Add_str_a7(i == 0 ? " ON " : " AND ");
			tmp_bfr.Add_str_a7("k.").Add_str_a7(key).Add_str_a7(" = ");
			tmp_bfr.Add_str_a7("d.").Add_str_a7(key);
		}
		return tmp_bfr.To_str_and_clear();
	}
	private static final String[] Insert__keys = String_.Ary("tbl", "flds", "join", "rng_bgn", "rng_end");
	private static final String Insert__fmt = String_.Concat_lines_nl_skip_last
	( "INSERT  INTO db_curr.~{tbl}"
	, "SELECT  ~{flds}"
	, "FROM    db_temp.~{tbl}_pkey k"
	, "        JOIN db_diff.~{tbl} d~{join}"
	, "WHERE   k.diff_type = 1"
	, "AND     k.diff_uid BETWEEN ~{rng_bgn} AND ~{rng_end};"
	);
	private static final String[] Update__keys = String_.Ary("tbl", "flds", "join", "rng_bgn", "rng_end");
	private static final String Update__fmt = String_.Concat_lines_nl_skip_last
	( "REPLACE INTO db_curr.~{tbl}" 
	, "SELECT  ~{flds}"
	, "FROM    db_temp.~{tbl}_pkey k"
	, "        JOIN db_diff.~{tbl} d~{join}"
	, "WHERE   k.diff_type = 2"
	, "AND     k.diff_uid BETWEEN ~{rng_bgn} AND ~{rng_end};"
	);
	private static final String[] Delete__keys = String_.Ary("tbl", "pkey_where", "pkey_select", "join", "rng_bgn", "rng_end");
	private static final String Delete__fmt = String_.Concat_lines_nl_skip_last
	( "DELETE  db_curr.~{tbl}"
	, "WHERE   ~{pkey_where} IN"
	, "(       SELECT  ~{pkey_select}"
	, "        FROM    db_temp.~{tbl}_pkey k"
	, "                JOIN db_diff.~{tbl} d~{join}"
	, "        WHERE   k.diff_type = 0"
	, "        AND     k.diff_uid BETWEEN ~{rng_bgn} AND ~{rng_end}"
	, ");"
	);
}
