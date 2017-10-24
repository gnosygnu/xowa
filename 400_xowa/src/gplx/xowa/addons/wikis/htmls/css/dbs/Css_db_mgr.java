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
package gplx.xowa.addons.wikis.htmls.css.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.htmls.*; import gplx.xowa.addons.wikis.htmls.css.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*;
public class Css_db_mgr {
	private final    Xow_wiki wiki;
	public Css_db_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
	}
	public Db_conn				Conn() {return conn;} private Db_conn conn;
	public Xowd_css_core_tbl	Tbl__core() {return tbl__core;} private Xowd_css_core_tbl tbl__core; 
	public Xowd_css_file_tbl	Tbl__file() {return tbl__file;} private Xowd_css_file_tbl tbl__file; 
	public Css_db_mgr Init() {
		this.conn = Get_or_new(wiki);
		this.tbl__core = new Xowd_css_core_tbl(conn);
		this.tbl__file = new Xowd_css_file_tbl(conn);
		if (!conn.Meta_tbl_exists(tbl__core.Tbl_name())) {
			tbl__core.Create_tbl();
			tbl__file.Create_tbl();
		}
		return this;
	}

	private static Db_conn Get_or_new(Xow_wiki wiki) {
		int layout_text = wiki.Data__core_mgr().Db__core().Db_props().Layout_text().Tid();
		Io_url url = null;
		switch (layout_text) {
			case Xow_db_layout.Tid__all:	url = wiki.Data__core_mgr().Db__core().Url(); break;
			case Xow_db_layout.Tid__few:	url = wiki.Fsys_mgr().Root_dir().GenSubFil(String_.Format("{0}-data.xowa", wiki.Domain_str())); break;
			case Xow_db_layout.Tid__lot:	url = wiki.Fsys_mgr().Root_dir().GenSubFil(String_.Format("{0}-xtn.css.xowa", wiki.Domain_str())); break;
			default:						throw Err_.new_unhandled(layout_text);
		}
		return Db_conn_bldr.Instance.Get_or_autocreate(true, url);
	}
}
