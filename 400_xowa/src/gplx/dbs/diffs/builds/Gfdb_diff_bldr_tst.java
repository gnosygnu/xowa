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
import org.junit.*;
import gplx.dbs.*; import gplx.dbs.engines.mems.*;
public class Gfdb_diff_bldr_tst {
	private final Gfdb_diff_bldr_fxt fxt = new Gfdb_diff_bldr_fxt();
	@Test   public void Same() {
		fxt.Init__tbl__lhs(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Init__tbl__rhs(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Test__bld();
	}
	@Test   public void Update() {
		fxt.Init__tbl__lhs(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Init__tbl__rhs(Object_.Ary(1, "A1")	, Object_.Ary(2, "B1"));
		fxt.Test__bld("U|1|A1", "U|2|B1");
	}
	@Test   public void Insert() {
		fxt.Init__tbl__lhs(Object_.Ary(1, "A"));
		fxt.Init__tbl__rhs(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Test__bld("I|2|B");
	}
	@Test   public void Delete() {
		fxt.Init__tbl__lhs(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Init__tbl__rhs(Object_.Ary(1, "A"));
		fxt.Test__bld("D|2");
	}
	@Test   public void Basic() {
		fxt.Init__tbl__lhs
		( Object_.Ary(1, "A")
		, Object_.Ary(2, "B")
		, Object_.Ary(3, "C")
		);
		fxt.Init__tbl__rhs
		( Object_.Ary(1, "A")
		, Object_.Ary(2, "B1")
		, Object_.Ary(4, "D")
		);			
		fxt.Test__bld("U|2|B1", "D|3", "I|4|D");
	}
}
class Gfdb_diff_bldr_fxt {
	private final Gfdb_diff_bldr bldr = new Gfdb_diff_bldr();
	private final Gfdb_diff_wkr__test wkr = new Gfdb_diff_wkr__test();
	private final Db_meta_fld[] key_flds, val_flds;
	private Gfdb_diff_tbl lhs_tbl, rhs_tbl;
	public Gfdb_diff_bldr_fxt() {
		Db_meta_fld_list fld_list = new Db_meta_fld_list();
		fld_list.Add_int("id");
		key_flds = fld_list.To_fld_ary();
		fld_list.Clear();
		fld_list.Add_str("val", 255);
		val_flds = fld_list.To_fld_ary();
		bldr.Init(wkr);
	}
	public void Init__tbl__lhs(Object[]... rows) {this.lhs_tbl = Make__tbl(key_flds, val_flds, rows);}
	public void Init__tbl__rhs(Object[]... rows) {this.rhs_tbl = Make__tbl(key_flds, val_flds, rows);}
	public void Test__bld(String... expd) {
		bldr.Compare(lhs_tbl, rhs_tbl);
		Tfds.Eq_ary_str(expd, wkr.To_str_ary());
	}
	private static Gfdb_diff_tbl Make__tbl(Db_meta_fld[] keys, Db_meta_fld[] vals, Object[][] rows) {
		int keys_len = keys.length; int vals_len = vals.length;
		int cols_len = keys_len + vals_len;
		String[] cols = new String[cols_len];
		for (int i = 0; i < keys_len; ++i)
			cols[i] = keys[i].Name();
		for (int i = 0; i < vals_len; ++i)
			cols[i + keys_len] = vals[i].Name();

		int rows_len = rows.length;
		Mem_row[] mem_rows = new Mem_row[rows_len];
		for (int i = 0; i < rows_len; ++i) {
			Object[] row = rows[i];
			Mem_row mem_row = new Mem_row();
			mem_rows[i] = mem_row;
			for (int j = 0; j < cols_len; ++j) {
				Object cell = row[j];
				mem_row.Add(cols[j], cell);
			}
		}
		Db_rdr rdr = new Db_rdr__mem(cols, mem_rows);
		return new Gfdb_diff_tbl("tbl1", keys, vals, rdr);
	}
}
class Gfdb_diff_wkr__test implements Gfdb_diff_wkr {
	private final List_adp list = List_adp_.new_();
	private final Bry_bfr bfr = Bry_bfr.new_();
	private Db_rdr lhs_rdr, rhs_rdr;
	public void Init_tbls(Gfdb_diff_tbl lhs_tbl, Gfdb_diff_tbl rhs_tbl) {
		this.lhs_rdr = lhs_tbl.Rdr(); this.rhs_rdr = rhs_tbl.Rdr();
	}
	public void Term_tbls() {}
	public void Handle_same() {
		String lhs_val = lhs_rdr.Read_str("val");
		String rhs_val = rhs_rdr.Read_str("val");
		if (!String_.Eq(lhs_val, rhs_val))
			list.Add(bfr.Add_str_a7("U").Add_byte_pipe().Add_obj(lhs_rdr.Read_obj("id")).Add_byte_pipe().Add_str_a7(rhs_val).To_str_and_clear());
	}
	public void Handle_lhs_missing() {
		String rhs_val = rhs_rdr.Read_str("val");
		list.Add(bfr.Add_str_a7("I").Add_byte_pipe().Add_obj(rhs_rdr.Read_obj("id")).Add_byte_pipe().Add_str_a7(rhs_val).To_str_and_clear());
	}
	public void Handle_rhs_missing() {
		list.Add(bfr.Add_str_a7("D").Add_byte_pipe().Add_obj(lhs_rdr.Read_obj("id")).To_str_and_clear());
	}
	public String[] To_str_ary() {return list.To_str_ary_and_clear();}
}
