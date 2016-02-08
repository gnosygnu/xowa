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
package gplx.xowa.bldrs.cmds.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.core.stores.*; import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.ctgs.*; import gplx.xowa.bldrs.*; import gplx.xowa.wikis.nss.*;
public class Xob_categorylinks_sql_tst {
	@Before public void init() {if (Xoa_test_.Db_skip()) return; fxt.Ctor_fsys();} Db_mgr_fxt fxt = new Db_mgr_fxt();
	@After public void term() {if (Xoa_test_.Db_skip()) return; fxt.Rls();} 
	@Test   public void Basic() {
		if (Xoa_test_.Db_skip()) return;
		fxt.Init_db_sqlite();
		fxt.Init_page_insert(Int_obj_ref.new_(1), Xow_ns_.Tid__category, String_.Ary("Ctg_1", "Ctg_2"));
		fxt.Init_fil(Xoa_test_.Url_wiki_enwiki().GenSubFil("xowa_categorylinks.sql"), String_.Concat
		(	Xob_categorylinks_sql.Sql_categorylinks
		,	"INSERT INTO `categorylinks` VALUES"
		,	" (3,'Ctg_2','File:2a','2013-04-15 01:02:03','','uppercase','file')"
		,	",(4,'Ctg_1','1b','2013-04-15 01:02:03','','uppercase','page')"
		,	",(5,'Ctg_1','1a','2013-04-15 01:02:03','','uppercase','page')"
		,	";"
		));
		fxt.Exec_run(new Xob_category_registry_sql(fxt.Bldr(), fxt.Wiki()));
		fxt.Exec_run(new Xob_categorylinks_sql(fxt.Bldr(), fxt.Wiki()));
		Db_conn conn = fxt.Wiki().Db_mgr_as_sql().Core_data_mgr().Db__cat_core().Conn();
		Db_tst_qry.tbl_("cat_core", "cat_id")
			.Cols_("cat_id", "cat_subcats", "cat_files", "cat_pages")
			.Rows_add_vals(1, 0, 0, 2)
			.Rows_add_vals(2, 0, 1, 0)
			.Test(conn);
		Db_tst_qry.tbl_("cat_link", "cl_from")
			.Cols_("cl_from", "cl_to_id", "cl_sortkey", "cl_type_id")
			.Rows_add_vals(3, 2, "File:2a"	, Xoa_ctg_mgr.Tid_file)
			.Rows_add_vals(4, 1, "1b"		, Xoa_ctg_mgr.Tid_page)
			.Rows_add_vals(5, 1, "1a"		, Xoa_ctg_mgr.Tid_page)
			.Test(conn);		
	}
}
class Db_tst_val {
	public Db_tst_val(int idx, String key, Object val) {this.idx = idx; this.key = key; this.val = val;}
	public int Idx() {return idx;} public Db_tst_val Idx_(int val) {idx = val; return this;} private int idx = -1;
	public String Key() {return key;} public Db_tst_val Key_(String v) {this.key = v; return this;} private String key;
	public Object Val() {return val;} public Db_tst_val Val_(Object v) {this.val = v; return this;} Object val;
	public static final int Idx_null = -1;
	public static final String Key_null = null;
}
class Db_tst_row {
	public int Idx() {return idx;} public Db_tst_row Idx_(int val) {idx = val; return this;} private int idx = -1;
	public Db_tst_val[] Vals_ary() {return vals_ary;} Db_tst_val[] vals_ary = null;
	public static Db_tst_row vals_(Object... ary) {
		Db_tst_row rv = new Db_tst_row();
		int len = ary.length;
		Db_tst_val[] vals_ary = new Db_tst_val[len];
		for (int i = 0; i < len; i++)
			vals_ary[i] = new Db_tst_val(Db_tst_val.Idx_null, Db_tst_val.Key_null, ary[i]);
		rv.vals_ary = vals_ary;
		return rv;
	}
	public static Db_tst_row kvs_(String[] cols, Object[] vals) {
		int cols_len = cols.length;
		int vals_len = vals.length;
		if (cols_len != vals_len) throw Err_.new_wo_type("mismatch in cols / vals");
		Db_tst_row rv = new Db_tst_row();
		Db_tst_val[] vals_ary = new Db_tst_val[cols_len];
		for (int i = 0; i < cols_len; i++) {
			String col = cols[i];
			Object val = vals[i];
			vals_ary[i] = new Db_tst_val(Db_tst_val.Idx_null, col, val);
		}
		rv.vals_ary = vals_ary;
		return rv;
	}
}
class Db_tst_qry {
	public Db_qry Qry() {return qry;} Db_qry qry;
	public String[] Cols() {return cols;} public Db_tst_qry Cols_(String... v) {this.cols = v; return this;} private String[] cols;
	public List_adp Rows() {return rows;} List_adp rows = List_adp_.new_();
	public Db_tst_qry Rows_add_vals(Object... ary) {
		Db_tst_row row = Db_tst_row.kvs_(cols, ary);
		rows.Add(row);
		return this;
	}
	public void Test(Db_conn conn) {
		DataRdr rdr = DataRdr_.Null;
		Bry_bfr bfr = Bry_bfr.new_();
		try {
			rdr = conn.Exec_qry_as_old_rdr(qry);
			int expd_row_idx = 0, expd_row_max = rows.Count();
			while (rdr.MoveNextPeer()) {
				if (expd_row_idx == expd_row_max) break;
				Db_tst_row expd_row = (Db_tst_row)rows.Get_at(expd_row_idx);
				Test_row(bfr, expd_row_idx, expd_row, rdr);
				++expd_row_idx;
			}
		}	finally {rdr.Rls();}
	}
	private void Test_row(Bry_bfr bfr, int expd_row_idx, Db_tst_row expd_row, DataRdr rdr) {
		Db_tst_val[] expd_vals = expd_row.Vals_ary();
		int len = expd_vals.length;
		int pad_max = 16;
		boolean pass = true;
		bfr.Clear().Add_byte_nl();	// add prefix nl for JUnit formatting 
		for (int i = 0; i < len; i++) {
			Db_tst_val expd_val = expd_vals[i];
			String expd_key = expd_val.Key();
			String expd_str = Object_.Xto_str_strict_or_empty(expd_val.Val());
			String actl_str = Object_.Xto_str_strict_or_empty(rdr.Read(expd_key));
			boolean eq = String_.Eq(expd_str, actl_str);	// NOTE: always compare strings, not objs; problems with comparing byte to int
			bfr.Add_str_pad_space_end(expd_key, pad_max);
			bfr.Add_str_pad_space_bgn(expd_str, pad_max);
			bfr.Add(eq ? Lbl_eq_y : Lbl_eq_n);
			bfr.Add_str_pad_space_end(actl_str, pad_max);
			bfr.Add_byte_nl();
			if (!eq) pass = false;
		}
		if (!pass) {
			bfr.Add(Lbl_row_hdr).Add_int_variable(expd_row_idx).Add_byte_nl();
			bfr.Add_str_u8(qry.To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr_.Sqlite)).Add_byte(Byte_ascii.Semic);
			throw Err_.new_wo_type(bfr.To_str_and_clear());
		}
	}	static final byte[] Lbl_row_hdr = Bry_.new_a7("row: "), Lbl_eq_y = Bry_.new_a7(" == "), Lbl_eq_n = Bry_.new_a7(" != ");
	public static Db_tst_qry tbl_(String tbl_name, String order_by) {return new_(Db_qry_.select_tbl_(tbl_name).Order_asc_(order_by));}
	public static Db_tst_qry new_(Db_qry qry) {
		Db_tst_qry rv = new Db_tst_qry();
		rv.qry = qry;
		return rv;
	}
}
