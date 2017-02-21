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
package gplx.xowa.bldrs.cmds.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.dbs.metas.*; import gplx.dbs.diffs.*;
class Xob_diff_manifest {
	// page|page_id|*
	public static Gfdb_diff_tbl[] Parse(Db_conn conn, String src_str) {
//			byte[][] rows_ary = Bry_split_.Split_lines(Bry_.new_u8(src_str));
//			int rows_len = rows_ary.length;
//			for (int i = 0; i < rows_len; ++i) {
//				byte[] row = rows_ary[i];
//				byte[][] itms_ary = Bry_split_.Split(row, Byte_ascii.Pipe);
//				byte[] tbl_name = itms_ary[0];
//				conn.Meta_tbl_exists
//				int itms_len = itms_ary.length;
//				for (int j = 0; j < itms_len; ++j) {
//					byte[] itm = itms_ary[j];
//                    Tfds.Dbg(itm);
//				}
//				Gfdb_diff_tbl tbl = new Gfdb_diff_tbl(String_.new_u8(itms_ary[0]),keys, vals, Db_rdr_.Empty);
//			}
		return null;
	}
}
/*
class Wkr {
	public void Make() {		
		sdif_db_mgr sdif_db = new Sdif_db_mgr(conn);
		for (int i = 0; i < rhs_tbl_len; ++i) {
			
		}
	}		
}
*/
