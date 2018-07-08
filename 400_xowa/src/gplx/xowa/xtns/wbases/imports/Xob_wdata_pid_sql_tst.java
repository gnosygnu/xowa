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
import gplx.*;
import gplx.dbs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Xob_wdata_pid_sql_tst {
	private Db_conn conn;
	private final    Xob_fxt fxt = new Xob_fxt().Ctor_mem();
	private final    Gfo_db_tester db_tester = new Gfo_db_tester();
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		this.conn = Db_conn_bldr.Instance.New(Io_url_.mem_fil_("mem/db/wbase.xowa"));
	}

	@Test   public void Basic() {
		fxt.Wiki().Ns_mgr().Add_new(Wdata_wiki_mgr.Ns_property, "Property");
		Xob_wdata_pid_sql wkr = new Xob_wdata_pid_sql(conn, null);
		wkr.Ctor(fxt.Bldr(), fxt.Wiki());

		fxt.Run_page_wkr(wkr
			, fxt.doc_wo_date_(2, "Property:P2", Xob_wdata_tst_utl.Json("p2", "label", String_.Ary("en", "p2_en", "fr", "p2_fr")))
			, fxt.doc_wo_date_(1, "Property:P1", Xob_wdata_tst_utl.Json("p1", "label", String_.Ary("en", "p1_en", "fr", "p1_fr")))
			);

		db_tester.Test__select_tbl(conn
		, "wbase_pid", new Dbmeta_fld_list().Bld_str("src_lang").Bld_str("src_ttl").Bld_str("trg_ttl")
		, String_.Ary("en", "p2_en", "p2")
		, String_.Ary("fr", "p2_fr", "p2")
		, String_.Ary("en", "p1_en", "p1")
		, String_.Ary("fr", "p1_fr", "p1")
		);
	}
}
class Gfo_db_tester {
	public void Test__select_tbl(Db_conn conn, String tbl, Dbmeta_fld_list flds, String[]... expd_rows) {
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = conn.Stmt_select_all(tbl, flds).Exec_select__rls_auto();
			int row_idx = 0;
			while (rdr.Move_next()) {
				String[] actl_row = new String[flds.Len()];
				for (int i = 0; i < flds.Len(); i++)
					actl_row[i] = Object_.Xto_str_strict_or_null(rdr.Read_at(i));
				String[] expd_row = expd_rows[row_idx++];
                    Gftest.Eq__ary(expd_row, actl_row, "row compare failed", "row", row_idx);
			}
		}
		finally {
			rdr.Rls();
		}
	}
	public void Test__objary(Object[][] expd, Object[][] actl) {
		// gplx.core.strings.String_bldr_.new_()
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
