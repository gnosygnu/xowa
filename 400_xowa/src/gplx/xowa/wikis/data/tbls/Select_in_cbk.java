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
package gplx.xowa.wikis.data.tbls;
import gplx.dbs.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public interface Select_in_cbk {
	int Hash_max();
	void Write_sql(BryWtr bfr, int idx);
	void Read_data(Db_rdr rdr);
}
class Select_in_wkr {
	private final byte[] sql_bgn;
	public Select_in_wkr(byte[] sql_bgn) {this.sql_bgn = sql_bgn;}
	public int Make_sql_or_null(BryWtr bfr, Select_in_cbk cbk, int bgn) {
		// read 50 at a time
		int max = cbk.Hash_max();
		int end = bgn + 50; if (end > max) end = max;
		if (bgn == end) return -1; // at eos; return;

		// concat itms
		bfr.Add(sql_bgn);
		for (int i = bgn; i < end; ++i) {
			if (i != bgn) bfr.AddStrA7(", ");
			cbk.Write_sql(bfr, i);
		}
		bfr.AddByte(AsciiByte.ParenEnd);
		return end;
	}
	public static Select_in_wkr New(BryWtr bfr, String tbl_name, String[] select_flds, String where_fld) {
		bfr.AddStrA7("SELECT ");
		int select_flds_len = select_flds.length;
		for (int i = 0; i < select_flds_len; ++i) {
			String select_fld = select_flds[i];
			if (i != 0) bfr.AddStrA7(", ");
			bfr.AddStrU8(select_fld);
		}
		bfr.AddStrA7(" FROM ").AddStrU8(tbl_name);
		bfr.AddStrA7(" WHERE ").AddStrU8(where_fld);
		bfr.AddStrA7(" IN (");
		return new Select_in_wkr(bfr.ToBryAndClear());
	}
}
