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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.htmls.core.dbs.*; import gplx.xowa.htmls.core.bldrs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.wikis.data.*;
class Xob_hdump_tbl_retriever__ns_to_db implements Xob_hdump_tbl_retriever {
	private final    Xob_ns_to_db_mgr ns_to_db_mgr;
	public Xob_hdump_tbl_retriever__ns_to_db(Xowe_wiki wiki) {
		Xow_db_mgr core_data_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		this.ns_to_db_mgr = new Xob_ns_to_db_mgr(new Xob_ns_to_db_wkr__html(core_data_mgr.Db__core()), core_data_mgr, wiki.Appe().Api_root().Bldr().Wiki().Import().Html_db_max());
		Xob_ns_file_itm.Init_ns_bldr_data(Xow_db_file_.Tid__html_data, wiki.Ns_mgr(), gplx.xowa.apps.apis.xowa.bldrs.imports.Xoapi_import.Ns_file_map__each);
	}
	public Xowd_html_tbl Get_html_tbl(Xow_ns ns, int prv_row_len) {
		Xow_db_file html_db = ns_to_db_mgr.Get_by_ns(ns.Bldr_data(), prv_row_len);							// get html_db
		return html_db.Tbl__html();
	}
	public void Commit() {ns_to_db_mgr.Commit();}
	public void Rls_all() {ns_to_db_mgr.Rls_all();}
}
