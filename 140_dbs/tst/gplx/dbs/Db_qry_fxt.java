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
public class Db_qry_fxt {
	public static void Insert_kvo(Db_provider provider, String tblName, KeyValList kvList) {
		Db_qry_insert qry = Db_qry_.insert_(tblName);
		for (int i = 0; i < kvList.Count(); i++) {
			KeyVal kv = kvList.GetAt(i);
			qry.Arg_obj_(kv.Key(), kv.Val());
		}
		qry.Exec_qry(provider);
	}
	public static GfoNde SelectAll(Db_provider provider, String tblName) {
		return Db_qry_.select_tbl_(tblName).ExecRdr_nde(provider);
	}
	public static int SelectAll_count(Db_provider provider, String tblName) {
		GfoNde nde = Db_qry_fxt.SelectAll(provider, tblName);
		return nde.Subs().Count();
	}
	public static void DeleteAll(Db_provider provider, String... ary) {
		for (String s : ary)
			Db_qry_.delete_tbl_(s).Exec_qry(provider);
	}
	public static void tst_Select(Db_provider provider, String tblName, DbTstRow... expdAry) {
		GfoNde nde = Db_qry_fxt.SelectAll(provider, tblName);
		int len = Array_.Len(expdAry);
		for (int i = 0; i < len; i++) {
			DbTstRow expdRow = expdAry[i];
			int actlIdx = (expdRow.Idx() == -1) ? i : expdRow.Idx();
			GfoNde actlNde = nde.Subs().FetchAt_asGfoNde(actlIdx);
			int fldLen = Array_.Len(expdRow.Dat());
			for (int j = 0; j < fldLen; j++) {
				DbTstDat expdDat = expdRow.Dat()[j];
				Object actlVal = expdDat.Fld() == null ? actlNde.ReadAt(j) : actlNde.Read(expdDat.Fld());
				Tfds.Eq(expdDat.Val(), actlVal);
			}
		}
	}
}
