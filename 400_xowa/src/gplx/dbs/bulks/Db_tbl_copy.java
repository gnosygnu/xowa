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
package gplx.dbs.bulks; import gplx.*; import gplx.dbs.*;
import gplx.dbs.*; import gplx.dbs.metas.*;
public class Db_tbl_copy {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Db_attach_mgr attach_mgr = new Db_attach_mgr();
	public void Copy_many(Db_conn src_conn, Db_conn trg_conn, String... tbl_names) {
		for (String tbl_name : tbl_names)
			Copy_one(src_conn, trg_conn, tbl_name, tbl_name);
	}
	public void Copy_one(Db_conn src_conn, Db_conn trg_conn, String src_tbl, String trg_tbl) {
		Dbmeta_tbl_itm tbl = src_conn.Meta_mgr().Get_by(src_tbl); if (tbl == null) throw Err_.new_wo_type("tbl does not exist", "tbl_name", src_tbl);
		trg_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New(trg_tbl, tbl.Flds().To_ary(), tbl.Idxs().To_ary()));

		// do copy
		attach_mgr.Conn_main_(trg_conn).Conn_links_(new Db_attach_itm("src_db", src_conn));
		attach_mgr.Exec_sql(Bld_sql(tbl, src_tbl, trg_tbl));
	}
	public String Bld_sql(Dbmeta_tbl_itm tbl, String src_tbl, String trg_tbl) {
		Dbmeta_fld_mgr flds = tbl.Flds();
		int flds_len = flds.Len();
		bfr.Add_str_a7("INSERT INTO ").Add_str_a7(trg_tbl).Add_byte_nl();
		bfr.Add_byte(Byte_ascii.Paren_bgn);
		for (int i = 0; i < flds_len; ++i) {
			Dbmeta_fld_itm fld = flds.Get_at(i);
			if (i != 0) bfr.Add_str_a7(", ");
			bfr.Add_str_a7(fld.Name());
		}
		bfr.Add_byte(Byte_ascii.Paren_end).Add_byte_nl();
		bfr.Add_str_a7("SELECT").Add_byte_nl().Add_byte_space();
		for (int i = 0; i < flds_len; ++i) {
			Dbmeta_fld_itm fld = flds.Get_at(i);
			if (i != 0) bfr.Add_str_a7(", ");
			bfr.Add_str_a7(fld.Name());
		}
		bfr.Add_byte_nl();
		bfr.Add_str_a7("FROM <src_db>").Add_str_a7(src_tbl);
		return bfr.To_str_and_clear();
	}
}
