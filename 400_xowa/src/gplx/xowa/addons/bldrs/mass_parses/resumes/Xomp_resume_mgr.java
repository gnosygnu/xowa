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
		mgr_conn.Exec_sql("UPDATE xomp_page SET page_status = 0");

		// update mgr.xomp_page.status for each row in wkr.html
		Db_attach_mgr attach_mgr = new Db_attach_mgr(mgr_conn);
		int wkrs_len = mgr_db.Tbl__wkr().Select_count();
		String sql = Db_sql_.Make_by_fmt
		( String_.Ary
		( "UPDATE  xomp_page"
		, "SET     page_status = 1"
		, "WHERE   page_id IN (SELECT page_id FROM <wkr_db>html)"
		));
		for (int i = 0; i < wkrs_len; ++i) {
			Gfo_usr_dlg_.Instance.Prog_many("", "", "xomp_resume:updating status; wkr=~{0}", i);
			Xomp_wkr_db wkr_db = Xomp_wkr_db.New(mgr_db.Dir(), i);
			attach_mgr.Conn_links_(new Db_attach_itm("wkr_db", wkr_db.Conn()));
			attach_mgr.Exec_sql(sql);
		}
	}
}
