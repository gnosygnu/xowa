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
import gplx.dbs.*; import gplx.dbs.metas.*; import gplx.dbs.engines.mems.*;
public class Gfdb_diff_bldr_tst {
	private final Gfdb_diff_bldr_fxt fxt = new Gfdb_diff_bldr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Same() {
		fxt.Init__tbl__old(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Init__tbl__cur(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Test__bld();
	}
	@Test   public void Update() {
		fxt.Init__tbl__old(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Init__tbl__cur(Object_.Ary(1, "A1")	, Object_.Ary(2, "B1"));
		fxt.Test__bld("U|1|A1", "U|2|B1");
	}
	@Test   public void Insert() {
		fxt.Init__tbl__old(Object_.Ary(1, "A"));
		fxt.Init__tbl__cur(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Test__bld("I|2|B");
	}
	@Test   public void Delete() {
		fxt.Init__tbl__old(Object_.Ary(1, "A")	, Object_.Ary(2, "B"));
		fxt.Init__tbl__cur(Object_.Ary(1, "A"));
		fxt.Test__bld("D|2");
	}
	@Test   public void Basic() {
		fxt.Init__tbl__old
		( Object_.Ary(1, "A")
		, Object_.Ary(2, "B")
		, Object_.Ary(3, "C")
		);
		fxt.Init__tbl__cur
		( Object_.Ary(1, "A")
		, Object_.Ary(2, "B1")
		, Object_.Ary(4, "D")
		);			
		fxt.Test__bld("U|2|B1", "D|3", "I|4|D");
	}
}
class Gfdb_diff_bldr_fxt {
	private final Gfdb_diff_bldr bldr = new Gfdb_diff_bldr();
	private final Db_conn old_conn, new_conn;
	private final Gfdb_diff_tbl tbl;
	private final Gfdb_diff_wkr__test wkr = new Gfdb_diff_wkr__test();
	private final Dbmeta_fld_itm[] flds_ary;
	private final String tbl_name = "tbl";
	private final Gdif_bldr_ctx ctx = new Gdif_bldr_ctx();
	public Gfdb_diff_bldr_fxt() {
		old_conn = Db_conn_utl.Conn__new("old_db");
		new_conn = Db_conn_utl.Conn__new("new_db");
		this.flds_ary = new Dbmeta_fld_itm[] {Dbmeta_fld_itm.new_int("id").Primary_y_(), Dbmeta_fld_itm.new_str("val", 255)};
		tbl = Gfdb_diff_tbl.New(Dbmeta_tbl_itm.New(tbl_name, flds_ary));
		bldr.Init(wkr);
	}
	public void Clear() {
		ctx.Clear();
		old_conn.Meta_tbl_drop("tbl");
		new_conn.Meta_tbl_drop("tbl");
	}
	public void Init__tbl__old(Object[]... rows) {Db_conn_utl.Tbl__new(old_conn, "tbl", flds_ary, rows);}
	public void Init__tbl__cur(Object[]... rows) {Db_conn_utl.Tbl__new(new_conn, "tbl", flds_ary, rows);}
	public void Test__bld(String... expd) {
		bldr.Compare(ctx, tbl, old_conn, new_conn);
		Tfds.Eq_ary_str(expd, wkr.To_str_ary());
	}
}
class Gfdb_diff_wkr__test implements Gfdb_diff_wkr {
	private final List_adp list = List_adp_.new_();
	private final Bry_bfr bfr = Bry_bfr.new_();
	private Db_rdr old_rdr, new_rdr;
	public void Init_rdrs(Gdif_bldr_ctx ctx, Gfdb_diff_tbl tbl, Db_rdr old_rdr, Db_rdr new_rdr) {
		this.old_rdr = old_rdr; this.new_rdr = new_rdr;
	}
	public void Term_tbls() {}
	public void Handle_same() {
		String old_val = old_rdr.Read_str("val");
		String new_val = new_rdr.Read_str("val");
		if (!String_.Eq(old_val, new_val))
			list.Add(bfr.Add_str_a7("U").Add_byte_pipe().Add_obj(old_rdr.Read_obj("id")).Add_byte_pipe().Add_str_a7(new_val).To_str_and_clear());
	}
	public void Handle_old_missing() {
		String new_val = new_rdr.Read_str("val");
		list.Add(bfr.Add_str_a7("I").Add_byte_pipe().Add_obj(new_rdr.Read_obj("id")).Add_byte_pipe().Add_str_a7(new_val).To_str_and_clear());
	}
	public void Handle_new_missing() {
		list.Add(bfr.Add_str_a7("D").Add_byte_pipe().Add_obj(old_rdr.Read_obj("id")).To_str_and_clear());
	}
	public String[] To_str_ary() {return list.To_str_ary_and_clear();}
}
