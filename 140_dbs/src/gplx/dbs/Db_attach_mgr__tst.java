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
package gplx.dbs; import gplx.*;
import org.junit.*; import gplx.dbs.qrys.*;
public class Db_attach_mgr__tst {
	private final    Db_attach_mgr__fxt fxt = new Db_attach_mgr__fxt();
	@Test  public void Basic() {
		Db_qry__select_cmd qry = Db_qry_.select_()
			.Cols_w_tbl_("t1", "fld_1")
			.Cols_w_tbl_("t2", "fld_2")
			.Cols_w_tbl_("t3", "fld_3")
			.From_("db_1", "tbl_1", "t1")
			.Join_("db_2", "tbl_2", "t2", Db_qry_.New_join__same("t1", "fld_2"))
			.Join_("db_3", "tbl_3", "t3", Db_qry_.New_join__same("t1", "fld_3"))
			.Join_(		   "tbl_4", "t4", Db_qry_.New_join__same("t1", "fld_4"))
			;
		fxt.Init("db_2", fxt.Make__itm("db_1"), fxt.Make__itm("db_2"), fxt.Make__itm("db_3"));
		fxt.Test__make_stmt_and_attach(qry
		, "SELECT t1.fld_1, t2.fld_2, t3.fld_3 "
		+ "FROM db_1.tbl_1 t1 "
		+     "INNER JOIN tbl_2 t2 ON t1.fld_2 = t2.fld_2 "		// NOTE: curr is db_2 so do not prefix tbl_2 with db_2; fails if "db_2.tbl_2"
		+     "INNER JOIN db_3.tbl_3 t3 ON t1.fld_3 = t3.fld_3 "
		+     "INNER JOIN tbl_4 t4 ON t1.fld_4 = t4.fld_4"
		, String_.Ary("db_1", "db_3")							// NOTE: no "db_2"
		);
	}
}
class Db_attach_mgr__fxt {
	private Db_attach_mgr mgr;
	public Db_attach_mgr__fxt() {
		Db_conn_bldr.Instance.Reg_default_mem();
	}
	public Db_attach_itm Make__itm(String key) {return new Db_attach_itm(key, Io_url_.mem_fil_("mem/" + key));}
	public Db_conn Make__conn(String key) {return Db_conn_bldr.Instance.New(Io_url_.mem_fil_(key));}
	public void Init(String conn_key, Db_attach_itm... ary) {
		Db_conn conn = Make__conn(conn_key);
		mgr = new Db_attach_mgr(conn, ary);
	}
	public void Test__make_stmt_and_attach(Db_qry__select_cmd qry, String expd_sql, String[] expd_dbs) {
		mgr.Make_stmt_and_attach(qry, qry.From());
		Tfds.Eq_str(expd_sql, mgr.Attached_sql());
		Tfds.Eq_ary_str(expd_dbs, To_key_ary(mgr.attached_dbs_list));
	}
	private static String[] To_key_ary(List_adp attach_dbs_list) {
		int rv_len = attach_dbs_list.Count();
		String[] rv = new String[rv_len];
		for (int i = 0; i < rv_len; ++i) {
			Db_attach_itm itm = (Db_attach_itm)attach_dbs_list.Get_at(i);
			rv[i] = itm.Key;
		}
		return rv;
	}
}
