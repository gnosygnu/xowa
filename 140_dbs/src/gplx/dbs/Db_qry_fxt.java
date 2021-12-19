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
package gplx.dbs;
import gplx.dbs.qrys.*; import gplx.core.gfo_ndes.*;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValList;
public class Db_qry_fxt {
	public static void Insert_kvo(Db_conn conn, String tblName, KeyValList kvList) {
		Db_qry_insert qry = Db_qry_.insert_(tblName);
		for (int i = 0; i < kvList.Len(); i++) {
			KeyVal kv = kvList.GetAt(i);
			qry.Val_obj(kv.KeyToStr(), kv.Val());
		}
		qry.Exec_qry(conn);
	}
	public static GfoNde SelectAll(Db_conn conn, String tblName) {
		return Db_qry_.Exec_as_nde(conn, Db_qry_.select_tbl_(tblName));
	}
	public static int SelectAll_count(Db_conn conn, String tblName) {
		GfoNde nde = Db_qry_fxt.SelectAll(conn, tblName);
		return nde.Subs().Count();
	}
	public static void DeleteAll(Db_conn conn, String... ary) {
		for (String s : ary)
			Db_qry_.delete_tbl_(s).Exec_qry(conn);
	}
	public static void tst_Select(Db_conn conn, String tblName, Db_mock_row... expdAry) {
		GfoNde nde = Db_qry_fxt.SelectAll(conn, tblName);
		int len = ArrayUtl.Len(expdAry);
		for (int i = 0; i < len; i++) {
			Db_mock_row expdRow = expdAry[i];
			int actlIdx = (expdRow.Idx() == -1) ? i : expdRow.Idx();
			GfoNde actlNde = nde.Subs().FetchAt_asGfoNde(actlIdx);
			int fldLen = ArrayUtl.Len(expdRow.Dat());
			for (int j = 0; j < fldLen; j++) {
				Db_mock_cell expdDat = expdRow.Dat()[j];
				Object actlVal = expdDat.Fld() == null ? actlNde.ReadAt(j) : actlNde.Read(expdDat.Fld());
				GfoTstr.EqObj(expdDat.Val(), actlVal);
			}
		}
	}
	public static String Db__print_tbl_as_str(BryWtr bfr, Db_conn conn, String tbl, String... cols) {
		int cols_len = cols.length;
		Db_rdr rdr = conn.Stmt_select(tbl, cols).Exec_select__rls_auto();
		while (rdr.Move_next()) {
			for (int i = 0; i < cols_len; ++i) {
				bfr.AddObj(rdr.Read_at(i));
				bfr.AddByte(i == cols_len - 1 ? AsciiByte.Nl : AsciiByte.Pipe);
			}
		}
		rdr.Rls();
		return bfr.ToStrAndClear();
	}
}
