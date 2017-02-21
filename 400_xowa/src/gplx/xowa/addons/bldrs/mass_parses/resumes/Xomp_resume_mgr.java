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
package gplx.xowa.addons.bldrs.mass_parses.resumes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
class Xomp_resume_mgr {
	public void Exec(Xowe_wiki wiki) {
		// init
		Xomp_mgr_db mgr_db = Xomp_mgr_db.New__load(wiki);
		Db_conn mgr_conn = mgr_db.Conn();

		// clear out page_status
		Gfo_usr_dlg_.Instance.Prog_many("", "", "xomp_resume:clearing status");
		mgr_conn.Exec_sql("UPDATE xomp_page SET page_status = 0, xomp_wkr_id = -1");

		// update mgr.xomp_page.status for each row in wkr.html
		Db_attach_mgr attach_mgr = new Db_attach_mgr(mgr_conn);
		int wkrs_len = mgr_db.Tbl__wkr().Select_count();
		for (int i = 0; i < wkrs_len; ++i) {
			Gfo_usr_dlg_.Instance.Prog_many("", "", "xomp_resume:updating status; wkr=~{0}", i);
			Xomp_wkr_db wkr_db = Xomp_wkr_db.New(mgr_db.Dir(), i);
			attach_mgr.Conn_links_(new Db_attach_itm("wkr_db", wkr_db.Conn()));
			String sql = Db_sql_.Make_by_fmt
			( String_.Ary
			( "UPDATE  xomp_page"
			, "SET     page_status = 1"
			, ",       xomp_wkr_id = {0}"
			, "WHERE   page_id IN (SELECT page_id FROM <wkr_db>html)"
			), i);
			attach_mgr.Exec_sql(sql);
		}
	}
}
