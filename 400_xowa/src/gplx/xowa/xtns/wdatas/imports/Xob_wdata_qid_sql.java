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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xob_wdata_qid_sql extends Xob_wdata_qid_base {
	Xodb_mgr_sql db_mgr; Xodb_wdata_qids_tbl tbl; Db_stmt stmt; Db_provider provider;
	@Override public String Wkr_key() {return KEY;} public static final String KEY = "import.sql.wdata.qid";	
	@Override public void Qid_bgn() {
		db_mgr = wiki.Db_mgr_as_sql();
		tbl = db_mgr.Tbl_wdata_qids();
		long wikidata_max = wiki.App().Setup_mgr().Dump_mgr().Db_wikidata_max();
		if (wikidata_max > 0) {
			Xodb_file wdata_file = db_mgr.Fsys_mgr().Make(Xodb_file_tid.Tid_wikidata);
			db_mgr.Fsys_mgr().Provider_wdata_(wdata_file);
		}
		provider = db_mgr.Fsys_mgr().Provider_wdata();
		stmt = tbl.Insert_stmt(provider);
		provider.Txn_mgr().Txn_bgn_if_none();
	}
	@Override public void Qid_add(byte[] wiki_key, Xow_ns ns, byte[] ttl, byte[] qid) {
		tbl.Insert(stmt, wiki_key, ns.Id(), ttl, qid);
	}
	@Override public void Qid_end() {
		provider.Txn_mgr().Txn_end_all();
		stmt.Rls();
		db_mgr.Fsys_mgr().Index_create(wiki.App().Usr_dlg(), Byte_.Ary(Xodb_file_tid.Tid_core, Xodb_file_tid.Tid_wikidata), Index_wdata_qids);
	}
	private static final Db_idx_itm Index_wdata_qids	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_qids__src ON wdata_qids (wq_src_wiki, wq_src_ns, wq_src_ttl);");
}
