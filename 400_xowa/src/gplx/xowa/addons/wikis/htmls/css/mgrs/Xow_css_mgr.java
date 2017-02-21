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
package gplx.xowa.addons.wikis.htmls.css.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.htmls.*; import gplx.xowa.addons.wikis.htmls.css.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.htmls.css.dbs.*;
public class Xow_css_mgr {
	private final    Xow_wiki wiki;
	private boolean db_css_exists;
	private Xowd_css_core_tbl css_core_tbl; private Xowd_css_file_tbl css_file_tbl;
	public Xow_css_mgr(Xow_wiki wiki) {this.wiki = wiki;}
	public void Init_by_wiki() {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		Xow_db_mgr core_db_mgr = wiki.Data__core_mgr();
		if (core_db_mgr	== null) {
			usr_dlg.Log_many("", "", "db.css.exists; css_missing b/c tdb; wiki=~{0}", wiki.Domain_str());
			return;
		}
		if (	core_db_mgr.Props()	== null
			||	core_db_mgr.Props().Schema_is_1()) {
			usr_dlg.Log_many("", "", "db.css.exists; css_missing b/c v1; wiki=~{0}", wiki.Domain_str());
			return;
		}
		Xow_db_file core_db = core_db_mgr.Db__core();
		this.css_core_tbl = core_db.Tbl__css_core();
		this.css_file_tbl = core_db.Tbl__css_file();
		if (	core_db	== null
			||	!core_db.Conn().Meta_tbl_exists(css_core_tbl.Tbl_name())
			) {
			usr_dlg.Log_many("", "", "db.css.exists; css_missing b/c v2 w/o css; wiki=~{0}", wiki.Domain_str());
			return;
		}
		this.db_css_exists = true;
	}
	public void Db_del_all() {
		if (!db_css_exists) {Xoa_app_.Usr_dlg().Log_many("", "", "db.css.del_all; del_all skipped; wiki=~{0}", wiki.Domain_str()); return;}
		css_core_tbl.Delete_all();
		css_file_tbl.Delete_all();
	}
}
