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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
public abstract class Db_in_wkr__base {
	@gplx.Virtual protected int Interval() {return gplx.dbs.engines.sqlite.Sqlite_engine_.Stmt_arg_max - 10;}	// -10 for safety's sake
	protected abstract Db_qry	Make_qry	(int bgn, int end);
	protected abstract void		Fill_stmt	(Db_stmt stmt, int bgn, int end);
	protected abstract void		Read_data	(Cancelable cancelable, Db_rdr rdr);
	@gplx.Virtual protected boolean		Show_progress() {return false;}
	public void Select_in(Cancelable cancelable, Db_conn conn, int full_bgn, int full_end) {
		int part_len = Interval();
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance;
		boolean show_progress = this.Show_progress();;
		for (int part_bgn = full_bgn; part_bgn < full_end; part_bgn += part_len) {
			int part_end = part_bgn + part_len;
			if (part_end > full_end) part_end = full_end;
			Db_stmt stmt = Db_stmt_.Null; Db_rdr rdr = Db_rdr_.Empty; 
			try {
				if (show_progress) usr_dlg.Prog_many("", "", "reading: count=~{0}", part_end);
				stmt = conn.Stmt_new(Make_qry(part_bgn, part_end));
				Fill_stmt(stmt, part_bgn, part_end);
				rdr = stmt.Exec_select__rls_manual();
				Read_data(cancelable, rdr);
			}
			finally {rdr.Rls(); stmt.Rls();}
		}
	}
	public static Object[] In_ary(int len) {
		Object[] rv = new Object[len];
		for (int i = 0; i < len; i++)
			rv[i] = "";
		return rv;
	}
}
