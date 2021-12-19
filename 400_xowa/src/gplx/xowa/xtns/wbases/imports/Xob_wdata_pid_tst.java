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
package gplx.xowa.xtns.wbases.imports; import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.ClassUtl;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_conn_bldr;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_rdr_;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.DbmetaFldList;
import gplx.dbs.DbmetaFldType;
import gplx.xowa.bldrs.Xobldr_fxt;
import gplx.xowa.xtns.wbases.Wdata_wiki_mgr;
import org.junit.Before;
import org.junit.Test;
public class Xob_wdata_pid_tst {
	private Db_conn conn;
	private final Xobldr_fxt fxt = new Xobldr_fxt().Ctor_mem();
	private final Gfo_db_tester db_tester = new Gfo_db_tester();
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		this.conn = Db_conn_bldr.Instance.New(Io_url_.mem_fil_("mem/db/wbase.xowa"));
	}

	@Test public void Basic() {
		fxt.Wiki().Ns_mgr().Add_new(Wdata_wiki_mgr.Ns_property, "Property");
		Xob_wdata_pid wkr = new Xob_wdata_pid(conn);
		wkr.Ctor(fxt.Bldr(), fxt.Wiki());

		fxt.Run_page_wkr(wkr
			, fxt.New_page_wo_date(2, "Property:P2", Xob_wdata_tst_utl.Json("p2", "label", StringUtl.Ary("en", "p2_en", "fr", "p2_fr")))
			, fxt.New_page_wo_date(1, "Property:P1", Xob_wdata_tst_utl.Json("p1", "label", StringUtl.Ary("en", "p1_en", "fr", "p1_fr")))
			);

		db_tester.Test__select_tbl(conn, "wbase_pid", new DbmetaFldList().BldStr("src_lang").BldStr("src_ttl").BldStr("trg_ttl")
		, ObjectUtl.Ary("en", "p2_en", "p2")
		, ObjectUtl.Ary("fr", "p2_fr", "p2")
		, ObjectUtl.Ary("en", "p1_en", "p1")
		, ObjectUtl.Ary("fr", "p1_fr", "p1")
		);

		db_tester.Test__select_tbl(conn, "wbase_prop", new DbmetaFldList().BldStr("wbp_pid").BldInt("wbp_datatype")
		, ObjectUtl.Ary("p2", 12)
		, ObjectUtl.Ary("p1", 12)
		);
	}
}
class Gfo_db_tester {
	public void Test__select_tbl(Db_conn conn, String tbl, DbmetaFldList flds, Object[]... expd_rows) {
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
					int val_tid = DbmetaFldType.GetTypeIdByObj(val);
					DbmetaFldItm fld = flds.GetAt(i);
					if (val_tid != fld.Type().Tid())
						actl_row[i] = ObjectUtl.ToStrOrNullMark(val) + "|shouldBe=" + fld.Type().Name() + "|was=" + ClassUtl.NameByObj(val);
					else
						actl_row[i] = val;
				}
			}
		}
		finally {
			rdr.Rls();
		}
		Object[][] actl_rows = (Object[][])actl_list.ToAryAndClear(Object[].class);

		// validate expd datatypes
		for (Object[] expd_row : expd_rows) {
			int len = expd_row.length;
			for (int i = 0; i < len; i++) {
				Object val = expd_row[i];
				int val_tid = DbmetaFldType.GetTypeIdByObj(val);
				DbmetaFldItm fld = flds.GetAt(i);
				if (val_tid != fld.Type().Tid())
					expd_row[i] = ObjectUtl.ToStrOrNullMark(val) + "|shouldBe=" + fld.Type().Name() + "|was=" + ClassUtl.NameByObj(val);
			}
		}
            GfoTstr.EqLines(To_str_ary(expd_rows), To_str_ary(actl_rows), "rows mismatch");
	}
	public byte[][] To_str_ary(Object[][] rows) {
		BryWtr bfr = BryWtr.New();
		byte[][] rv = new byte[rows.length][];
		int rows_len = rows.length;
		for (int i = 0; i < rows_len; i++) {
			Object[] row = rows[i];
			for (int j = 0; j < row.length; j++) {
				if (j != 0) bfr.AddBytePipe();
				bfr.AddObj(row[j]);
			}
			bfr.AddByteNl();
			rv[i] = bfr.ToBryAndClear();
		}
		return rv;
	}
}

class Xob_wdata_tst_utl {
	public static String Json(String entity_id, String grp_key, String[] grp_vals) {
		BryWtr bfr = BryWtr.New();
		bfr.AddStrA7("{ 'entity':'").AddStrU8(entity_id).AddByte(AsciiByte.Apos).AddByteNl();
		bfr.AddStrA7(", 'datatype':'commonsMedia'\n");
		bfr.AddStrA7(", '").AddStrU8(grp_key).AddStrA7("':").AddByteNl();
		int len = grp_vals.length;
		for (int i = 0; i < len; i += 2) {
			bfr.AddByteRepeat(AsciiByte.Space, 2);
			bfr.AddByte(i == 0 ? AsciiByte.CurlyBgn : AsciiByte.Comma).AddByte(AsciiByte.Space);
			bfr.AddByte(AsciiByte.Apos).AddStrU8(grp_vals[i    ]).AddByte(AsciiByte.Apos).AddByte(AsciiByte.Colon);
			bfr.AddByte(AsciiByte.Apos).AddStrU8(grp_vals[i + 1]).AddByte(AsciiByte.Apos).AddByteNl();
		}			
		bfr.AddStrA7("  }").AddByteNl();
		bfr.AddStrA7("}").AddByteNl();
		return StringUtl.Replace(bfr.ToStrAndClear(), "'", "\"");
	}
}
