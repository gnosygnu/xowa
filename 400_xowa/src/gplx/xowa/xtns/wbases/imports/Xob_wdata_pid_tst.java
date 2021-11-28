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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.*; import gplx.dbs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Xob_wdata_pid_tst {
	private Db_conn conn;
	private final    Xobldr_fxt fxt = new Xobldr_fxt().Ctor_mem();
	private final    Gfo_db_tester db_tester = new Gfo_db_tester();
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		this.conn = Db_conn_bldr.Instance.New(Io_url_.mem_fil_("mem/db/wbase.xowa"));
	}

	@Test  public void Basic() {
		fxt.Wiki().Ns_mgr().Add_new(Wdata_wiki_mgr.Ns_property, "Property");
		Xob_wdata_pid wkr = new Xob_wdata_pid(conn);
		wkr.Ctor(fxt.Bldr(), fxt.Wiki());

		fxt.Run_page_wkr(wkr
			, fxt.New_page_wo_date(2, "Property:P2", Xob_wdata_tst_utl.Json("p2", "label", String_.Ary("en", "p2_en", "fr", "p2_fr")))
			, fxt.New_page_wo_date(1, "Property:P1", Xob_wdata_tst_utl.Json("p1", "label", String_.Ary("en", "p1_en", "fr", "p1_fr")))
			);

		db_tester.Test__select_tbl(conn, "wbase_pid", new Dbmeta_fld_list().Bld_str("src_lang").Bld_str("src_ttl").Bld_str("trg_ttl")
		, Object_.Ary("en", "p2_en", "p2")
		, Object_.Ary("fr", "p2_fr", "p2")
		, Object_.Ary("en", "p1_en", "p1")
		, Object_.Ary("fr", "p1_fr", "p1")
		);

		db_tester.Test__select_tbl(conn, "wbase_prop", new Dbmeta_fld_list().Bld_str("wbp_pid").Bld_int("wbp_datatype")
		, Object_.Ary("p2", 12)
		, Object_.Ary("p1", 12)
		);
	}
}
class Gfo_db_tester {
	public void Test__select_tbl(Db_conn conn, String tbl, Dbmeta_fld_list flds, Object[]... expd_rows) {
		// get actl
		List_adp actl_list = List_adp_.New();
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = conn.Stmt_select_all(tbl, flds).Exec_select__rls_auto();
			while (rdr.Move_next()) {
				Object[] actl_row = new Object[flds.Len()];
				actl_list.Add(actl_row);
				for (int i = 0; i < flds.Len(); i++) {
					Object val = rdr.Read_at(i);
					int val_tid = Dbmeta_fld_tid.Get_by_obj(val);
					Dbmeta_fld_itm fld = flds.Get_at(i);
					if (val_tid != fld.Type().Tid_ansi())
						actl_row[i] = Object_.Xto_str_strict_or_null_mark(val) + "|shouldBe=" + String_.new_u8(fld.Type().Name()) + "|was=" + Type_.Name_by_obj(val);
					else
						actl_row[i] = val;
				}
			}
		}
		finally {
			rdr.Rls();
		}
		Object[][] actl_rows = (Object[][])actl_list.To_ary_and_clear(Object[].class);

		// validate expd datatypes
		for (Object[] expd_row : expd_rows) {
			int len = expd_row.length;
			for (int i = 0; i < len; i++) {
				Object val = expd_row[i];
				int val_tid = Dbmeta_fld_tid.Get_by_obj(val);
				Dbmeta_fld_itm fld = flds.Get_at(i);
				if (val_tid != fld.Type().Tid_ansi())
					expd_row[i] = Object_.Xto_str_strict_or_null_mark(val) + "|shouldBe=" + String_.new_u8(fld.Type().Name()) + "|was=" + Type_.Name_by_obj(val);
			}
		}
            Gftest.Eq__ary(To_str_ary(expd_rows), To_str_ary(actl_rows), "rows mismatch");
	}
	public byte[][] To_str_ary(Object[][] rows) {
		Bry_bfr bfr = Bry_bfr_.New();
		byte[][] rv = new byte[rows.length][];
		int rows_len = rows.length;
		for (int i = 0; i < rows_len; i++) {
			Object[] row = rows[i];
			for (int j = 0; j < row.length; j++) {
				if (j != 0) bfr.Add_byte_pipe();
				bfr.Add_obj(row[j]);
			}
			bfr.Add_byte_nl();
			rv[i] = bfr.To_bry_and_clear();
		}
		return rv;
	}
}

class Xob_wdata_tst_utl {
	public static String Json(String entity_id, String grp_key, String[] grp_vals) {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("{ 'entity':'").Add_str_u8(entity_id).Add_byte(Byte_ascii.Apos).Add_byte_nl();
		bfr.Add_str_a7(", 'datatype':'commonsMedia'\n");
		bfr.Add_str_a7(", '").Add_str_u8(grp_key).Add_str_a7("':").Add_byte_nl();
		int len = grp_vals.length;
		for (int i = 0; i < len; i += 2) {
			bfr.Add_byte_repeat(Byte_ascii.Space, 2);
			bfr.Add_byte(i == 0 ? Byte_ascii.Curly_bgn : Byte_ascii.Comma).Add_byte(Byte_ascii.Space);			
			bfr.Add_byte(Byte_ascii.Apos).Add_str_u8(grp_vals[i    ]).Add_byte(Byte_ascii.Apos).Add_byte(Byte_ascii.Colon);
			bfr.Add_byte(Byte_ascii.Apos).Add_str_u8(grp_vals[i + 1]).Add_byte(Byte_ascii.Apos).Add_byte_nl();
		}			
		bfr.Add_str_a7("  }").Add_byte_nl();
		bfr.Add_str_a7("}").Add_byte_nl();
		return String_.Replace(bfr.To_str_and_clear(), "'", "\""); 
	}
}
