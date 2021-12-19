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
package gplx.xowa.addons.bldrs.exports.splits.htmls;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.wikis.data.Xow_db_file;
import gplx.xowa.wikis.data.Xow_db_file_;
import gplx.xowa.wikis.data.Xow_db_mgr;
public class Xoh_trg_tbl_mgr {
	private final Xow_db_mgr db_mgr;
	public Xoh_trg_tbl_mgr(Xowe_wiki wiki) {
		this.db_mgr = wiki.Data__core_mgr();
	}
	public Xoh_page_tbl_itm Make_new(int ns, int part) {
		Xow_db_file db_file = db_mgr.Dbs__make_by_tid(Xow_db_file_.Tid__html_data, IntUtl.ToStr(ns), part, Make_file_name(Repack_suffix, Xow_db_file_.Tid__html_data, ns, part));
		Xoh_page_tbl_itm rv = new Xoh_page_tbl_itm(BoolUtl.Y, db_file.Id(), db_file.Conn());
		rv.Html_tbl().Create_tbl();
		return rv;
	}
	public static String Make_file_name(String suffix, byte type, int ns, int part) {
		String rv = StringUtl.Format("{0}{1}{2}{3}.xowa"							// EX: .repack-html-ns.000-001.xowa
			, suffix															// EX: .repack
			, "-" + Xow_db_file_.To_key(type)									// EX: -html
			, ns   < 0 ? "" : "-ns." + IntUtl.ToStrPadBgnZero(ns, 3)			// EX: -ns.001
			, part < 0 ? "" : "-db." + IntUtl.ToStrPadBgnZero(part, 3)		// EX: -db.001
			);
		return rv;
	}
	public int Get_max_id() {
		int len = db_mgr.Dbs__len();
		int rv = -1;
		for (int i = 0; i < len; ++i) {
			Xow_db_file db_file = db_mgr.Dbs__get_at(i);
			int db_id = db_file.Id();
			if (db_id > rv) rv = db_id;
		}
		return rv;
	}
	public static final String Repack_suffix = ".repack";
}
