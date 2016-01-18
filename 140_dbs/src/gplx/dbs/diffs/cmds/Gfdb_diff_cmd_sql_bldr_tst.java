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
package gplx.dbs.diffs.cmds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import org.junit.*;
import gplx.dbs.*; import gplx.dbs.engines.mems.*;
public class Gfdb_diff_cmd_sql_bldr_tst {
	private final Gfdb_diff_cmd_sql_bldr_fxt fxt = new Gfdb_diff_cmd_sql_bldr_fxt();
	@Test   public void Insert() {
		fxt.Test__insert("tbl1", String_.Ary("key1", "key2"), String_.Ary("fld1", "fld2"), 0, 99, String_.Concat_lines_nl_skip_last
		( "INSERT  INTO db_curr.tbl1"
		, "SELECT  d.key1, d.key2, d.fld1, d.fld2"
		, "FROM    db_temp.tbl1_pkey k"
		, "        JOIN db_diff.tbl1 d ON k.key1 = d.key1 AND k.key2 = d.key2"
		, "WHERE   k.diff_type = 1"
		, "AND     k.diff_uid BETWEEN 0 AND 99;"
		));
	}
	@Test   public void Update() {
		fxt.Test__update("tbl1", String_.Ary("key1", "key2"), String_.Ary("fld1", "fld2"), 0, 99, String_.Concat_lines_nl_skip_last
		( "REPLACE INTO db_curr.tbl1"
		, "SELECT  d.key1, d.key2, d.fld1, d.fld2"
		, "FROM    db_temp.tbl1_pkey k"
		, "        JOIN db_diff.tbl1 d ON k.key1 = d.key1 AND k.key2 = d.key2"
		, "WHERE   k.diff_type = 2"
		, "AND     k.diff_uid BETWEEN 0 AND 99;"
		));
	}
	@Test   public void Delete() {
		fxt.Test__delete("tbl1", String_.Ary("key1", "key2"), 0, 99, String_.Concat_lines_nl_skip_last
		( "DELETE  db_curr.tbl1"
		, "WHERE   key1 || '|' || key2 IN"
		, "(       SELECT  k.key1 || '|' || k.key2"
		, "        FROM    db_temp.tbl1_pkey k"
		, "                JOIN db_diff.tbl1 d ON k.key1 = d.key1 AND k.key2 = d.key2"
		, "        WHERE   k.diff_type = 0"
		, "        AND     k.diff_uid BETWEEN 0 AND 99"
		, ");"
		));
	}
}
class Gfdb_diff_cmd_sql_bldr_fxt {
	private Gfdb_diff_cmd_sql_bldr bldr = new Gfdb_diff_cmd_sql_bldr();
	private final Bry_bfr bfr = Bry_bfr.new_();
	public void Test__insert(String tbl_name, String[] keys, String[] flds, int rng_bgn, int rng_end, String expd) {
		bldr.Bld_insert(bfr, tbl_name, keys, flds, 0, 99);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
	public void Test__update(String tbl_name, String[] keys, String[] flds, int rng_bgn, int rng_end, String expd) {
		bldr.Bld_update(bfr, tbl_name, keys, flds, 0, 99);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
	public void Test__delete(String tbl_name, String[] keys, int rng_bgn, int rng_end, String expd) {
		bldr.Bld_delete(bfr, tbl_name, keys, 0, 99);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
