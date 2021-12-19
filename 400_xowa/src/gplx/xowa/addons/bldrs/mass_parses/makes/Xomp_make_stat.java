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
package gplx.xowa.addons.bldrs.mass_parses.makes;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
class Xomp_make_stat {
	public void Exec(Xowe_wiki wiki, Xomp_make_cmd_cfg cfg) {
		// init mgr_db and mgr_tbl
		Xomp_mgr_db mgr_db = Xomp_mgr_db.New__load(wiki);
		Db_conn mgr_conn = mgr_db.Conn();
		Xomp_stat_tbl mgr_tbl = new Xomp_stat_tbl(mgr_conn);
		mgr_conn.Txn_bgn("xomp_stats");
		mgr_conn.Stmt_delete("xomp_stats", DbmetaFldItm.StrAryEmpty).Exec_delete();
		mgr_tbl.Stmt_new();

		// loop wkrs
		String sql = StringUtl.Format("SELECT * FROM xomp_stats;");
		int wkrs_len = mgr_db.Tbl__wkr().Select_count();
		for (int i = 0; i < wkrs_len; ++i) {
			int count = 0;
			Xomp_wkr_db wkr_db = Xomp_wkr_db.New(mgr_db.Dir(), i);
			Db_rdr rdr = wkr_db.Conn().Stmt_sql(sql).Exec_select__rls_auto();	// ANSI.Y
			try {
				while (rdr.Move_next()) {
					mgr_tbl.Insert_by_copy(rdr);
					if (++count % 10000 == 0) {
						Gfo_usr_dlg_.Instance.Prog_many("", "", "xomp.stat.insert: db=~{0} count=~{1}", IntUtl.ToStrPadBgnSpace(i, 3), IntUtl.ToStrPadBgnSpace(count, 8));
						mgr_conn.Txn_sav();
					}
				}
			} finally {rdr.Rls();}
		}

		// cleanup
		mgr_tbl.Stmt_rls();
		mgr_conn.Txn_end();
		mgr_conn.Rls_conn();
	}
}
