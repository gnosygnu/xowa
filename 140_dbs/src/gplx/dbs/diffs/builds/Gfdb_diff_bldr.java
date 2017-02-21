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
package gplx.dbs.diffs.builds; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import gplx.dbs.*;
public class Gfdb_diff_bldr {
	private Gfdb_diff_rdr_comparer rdr_comparer = new Gfdb_diff_rdr_comparer();
	private Gfdb_diff_wkr diff_wkr;
	public void Init(Gfdb_diff_wkr diff_wkr) {this.diff_wkr = diff_wkr;}
	public void Compare(Gdif_bldr_ctx ctx, Gfdb_diff_tbl tbl, Db_conn old_conn, Db_conn new_conn) {
		Db_rdr old_rdr = tbl.Make_rdr(old_conn), new_rdr = tbl.Make_rdr(new_conn);
		rdr_comparer.Init_rdrs(tbl, old_rdr, new_rdr);
		diff_wkr.Init_rdrs(ctx, tbl, old_rdr, new_rdr);
		boolean loop = true;
		while (loop) {
			int rslt = rdr_comparer.Compare();
			switch (rslt) {
				case Gfdb_diff_rdr_comparer.Rslt__same:				diff_wkr.Handle_same(); break;
				case Gfdb_diff_rdr_comparer.Rslt__old_missing:		diff_wkr.Handle_old_missing(); break;
				case Gfdb_diff_rdr_comparer.Rslt__new_missing:		diff_wkr.Handle_new_missing(); break;
				case Gfdb_diff_rdr_comparer.Rslt__done:				loop = false; break;
			}
		}
		diff_wkr.Term_tbls();
	}
}
