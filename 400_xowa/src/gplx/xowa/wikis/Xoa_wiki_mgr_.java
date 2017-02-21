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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
import gplx.dbs.cfgs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.bldrs.infos.*;
public class Xoa_wiki_mgr_ {
	public static Xow_wiki Import_by_url(Xoa_app app, Xoa_wiki_mgr wiki_mgr, Io_url url) {
		Db_conn conn = Db_conn_bldr.Instance.Get_or_noop(url); if (conn == Db_conn_.Noop) return null;	// invalid url
		Db_cfg_tbl cfg_tbl = Xowd_cfg_tbl_.Get_or_null(conn); if (cfg_tbl == null) return null;	// no xowa_cfg;
		byte[] wiki_domain = cfg_tbl.Select_bry(Xowd_cfg_key_.Grp__bldr_session, Xob_info_session.Cfg_key__wiki_domain); if (wiki_domain == null) return null;
		Io_url wiki_root_dir = url.OwnerDir();
		Xow_wiki rv = wiki_mgr.Make(wiki_domain, wiki_root_dir);
		wiki_mgr.Add(rv);
		rv.Init_by_wiki();		// must init for Modified_latest
		String wiki_date = rv.Props().Modified_latest__yyyy_MM_dd();
		app.User().User_db_mgr().Site_mgr().Import(rv.Domain_str(), rv.Domain_str(), wiki_root_dir.Raw(), wiki_date, "");
		if (app.Tid_is_edit()) {
			// get_or_new wiki and mark it offline so it can show up in wikis sidebar
			gplx.xowa.wikis.xwikis.Xow_xwiki_itm xwiki = app.User().Wikii().Xwiki_mgr().Get_by_key(wiki_domain);
			if (xwiki == null)
				xwiki = app.User().Wikii().Xwiki_mgr().Add_by_atrs(wiki_domain, wiki_domain);
			xwiki.Offline_(true);
			((Xoae_app)app).Gui_mgr().Html_mgr().Portal_mgr().Wikis().Itms_reset();	// dirty wiki list so that next refresh will load itm
		}
		rv.Rls();				// rls wiki, else open connections will cause later file copies to fail; DATE:2016-06-26
		rv.Init_needed_y_();	// mark Init_needed_y_(), else wiki may have NOOP connection which will hang around on next release
		return rv;
	}
	public static final String Invk__import_by_url = "import_by_url";
}
