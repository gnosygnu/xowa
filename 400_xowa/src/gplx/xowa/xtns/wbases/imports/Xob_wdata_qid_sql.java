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
