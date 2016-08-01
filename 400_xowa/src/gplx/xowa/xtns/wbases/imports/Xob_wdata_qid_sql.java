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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_wdata_qid_sql extends Xob_wdata_qid_base {
	private Xowd_wbase_qid_tbl tbl;
	@Override public String Page_wkr__key() {return gplx.xowa.bldrs.Xob_cmd_keys.Key_wbase_qid;}
	@Override public void Qid_bgn() {
		Xow_db_mgr db_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		boolean db_is_all_or_few = db_mgr.Props().Layout_text().Tid_is_all_or_few();
		Xow_db_file wbase_db = db_is_all_or_few
			? db_mgr.Db__core()
			: db_mgr.Dbs__make_by_tid(Xow_db_file_.Tid__wbase);
		if (db_is_all_or_few)
			db_mgr.Db__wbase_(wbase_db);
		tbl = wbase_db.Tbl__wbase_qid();
		tbl.Create_tbl();
		tbl.Insert_bgn();
	}
	@Override public void Qid_add(byte[] wiki_key, int ns_id, byte[] ttl, byte[] qid) {
		tbl.Insert_cmd_by_batch(wiki_key, ns_id, ttl, qid);
	}
	@Override public void Qid_end() {
		tbl.Insert_end();
		tbl.Create_idx();
	}
}
