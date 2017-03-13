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
package gplx.xowa.addons.wikis.pages.syncs.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*;
import gplx.dbs.*;
import gplx.xowa.htmls.*;
import gplx.xowa.addons.wikis.pages.syncs.dbs.*;
import gplx.xowa.apps.apis.xowa.addons.bldrs.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xosync_read_mgr implements Gfo_invk {
	private boolean auto_enabled = false;
	private int auto_interval = 60 * 24;	// in minutes
	private final    Xowd_page_itm tmp_dbpg = new Xowd_page_itm();
	private Db_conn sync_conn; private Xosync_sync_tbl sync_tbl;
	private final    Xopg_match_mgr auto_page_matcher = new Xopg_match_mgr();
	private final    Xosync_update_mgr update_mgr = new Xosync_update_mgr();
	public void Init_by_wiki(Xow_wiki wiki) {
		this.Auto_scope_("*:Main_Page");
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__manual__enabled, Cfg__auto__enabled, Cfg__auto__interval, Cfg__auto__scope);
	}
	public boolean Manual_enabled() {return manual_enabled;} private boolean manual_enabled;
	public boolean Auto_update(Xow_wiki wiki, Xoa_page page, Xoa_ttl page_ttl) {
		if (wiki.Domain_itm().Domain_type_id() == gplx.xowa.wikis.domains.Xow_domain_tid_.Tid__home) return false;
		if (wiki.Domain_itm().Domain_type_id() == gplx.xowa.wikis.domains.Xow_domain_tid_.Tid__other) return false;
		if (page_ttl.Ns().Id_is_special()) return false;

		if (!auto_enabled) return false;
		if (!auto_page_matcher.Match(wiki, page_ttl.Full_db())) return false;

		wiki.Data__core_mgr().Db__core().Tbl__page().Select_by_ttl(tmp_dbpg, page_ttl.Ns(), page_ttl.Page_db());

		if (sync_conn == null) {
			Io_url sync_db_url = wiki.Fsys_mgr().Root_dir().GenSubFil(wiki.Domain_str() + "-sync.xowa");
			Gfo_usr_dlg_.Instance.Log_many("", "", "page_sync: loading database for page_sync_data; url=~{0}", sync_db_url.Raw());
			sync_conn = Db_conn_bldr.Instance.Get_or_autocreate(true, sync_db_url);
			sync_tbl = new Xosync_sync_tbl(sync_conn);
			sync_conn.Meta_tbl_assert(sync_tbl);
		}
		DateAdp sync_date = sync_tbl.Select_sync_date_or_min(tmp_dbpg.Id());
		if (Datetime_now.Get().Diff(sync_date).Total_mins().To_int() <= auto_interval) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "page_sync: skipping auto-sync for page; wiki=~{0} page=~{1} sync_date=~{2}", wiki.Domain_bry(), page_ttl.Full_db(), sync_date.XtoStr_fmt_yyyy_MM_dd_HH_mm_ss());
			return false;
		}
		else {
			Gfo_usr_dlg_.Instance.Log_many("", "", "page_sync: running auto-sync for page; wiki=~{0} page=~{1} sync_date=~{2}", wiki.Domain_bry(), page_ttl.Full_db(), sync_date.XtoStr_fmt_yyyy_MM_dd_HH_mm_ss());
		}
		
		Xoa_app app = wiki.App();
		Xoh_page hpg = new Xoh_page();
		update_mgr.Init_by_app(app);
		update_mgr.Init_by_page(wiki, hpg);
		update_mgr.Update(app.Wmf_mgr().Download_wkr(), wiki, page_ttl);

		Gfo_usr_dlg_.Instance.Log_many("", "", "page_sync: updating sync table; page=~{0}", page_ttl.Full_db());
		sync_tbl.Upsert(tmp_dbpg.Id(), Datetime_now.Get());
		return true;
	}
	private void Auto_scope_(String v) {
		auto_page_matcher.Set(v);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__manual__enabled))	this.manual_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__auto__enabled))		this.auto_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__auto__interval))		this.auto_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Cfg__auto__scope))		Auto_scope_(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Cfg__manual__enabled		= "xowa.bldr.page_sync.manual.enabled"
	, Cfg__auto__enabled		= "xowa.bldr.page_sync.auto.enabled"
	, Cfg__auto__interval		= "xowa.bldr.page_sync.auto.interval"
	, Cfg__auto__scope			= "xowa.bldr.page_sync.auto.scope"
	;
}
