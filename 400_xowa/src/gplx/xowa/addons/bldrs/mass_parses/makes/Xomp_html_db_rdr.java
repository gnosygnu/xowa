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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*; 
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
class Xomp_html_db_rdr {
	private final    Xowd_html_tbl[] src_tbls;
	private final    Xomp_mgr_db mgr_db;
	public Xomp_html_db_rdr(Xowe_wiki wiki) {
		this.mgr_db = Xomp_mgr_db.New__load(wiki);
		this.src_tbls = new Xowd_html_tbl[mgr_db.Tbl__wkr().Select_count()];
	}
	public void Rows__get(Xowd_html_row rv, int wkr_uid, int page_id) {
		Xowd_html_tbl src_tbl = src_tbls[wkr_uid];
		if (src_tbl == null) {
			Db_conn wkr_conn = Xomp_wkr_db.New(mgr_db.Dir(), wkr_uid).Conn();
			src_tbl = new Xowd_html_tbl(wkr_conn);
			src_tbls[wkr_uid] = src_tbl;
		}
		src_tbl.Select_as_row(rv, page_id);
	}
	public void Rls() {
		for (Xowd_html_tbl src_tbl : src_tbls) {
			if (src_tbl == null) continue;	// can be null if fsys has dirs, but db does not have any wkr_ids
			src_tbl.Conn().Rls_conn();
		}
	}
}
