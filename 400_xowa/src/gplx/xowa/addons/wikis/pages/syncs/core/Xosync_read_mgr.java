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
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__auto__enabled, Cfg__auto__interval, Cfg__auto__scope);
	}
	public boolean Manual_enabled() {return manual_enabled;} private boolean manual_enabled;
	public void Auto_update(Xow_wiki wiki, Xoa_page page, Xoa_ttl page_ttl) {
		if (wiki.Domain_itm().Domain_type_id() == gplx.xowa.wikis.domains.Xow_domain_tid_.Tid__home) return;
		if (wiki.Domain_itm().Domain_type_id() == gplx.xowa.wikis.domains.Xow_domain_tid_.Tid__other) return;
		if (page_ttl.Ns().Id_is_special()) return;

		if (!auto_enabled) return;
		if (!auto_page_matcher.Match(wiki, page_ttl.Full_db())) return;

		wiki.Data__core_mgr().Db__core().Tbl__page().Select_by_ttl(tmp_dbpg, page_ttl.Ns(), page_ttl.Page_db());

		if (sync_conn == null) {
			Io_url sync_db_url = wiki.Fsys_mgr().Root_dir().GenSubFil(wiki.Domain_str() + "-sync.xowa");
			sync_conn = Db_conn_bldr.Instance.Get_or_autocreate(true, sync_db_url);
			sync_tbl = new Xosync_sync_tbl(sync_conn);
			sync_conn.Meta_tbl_assert(sync_tbl);
		}
		DateAdp sync_date = sync_tbl.Select_sync_date_or_min(tmp_dbpg.Id());
		if (Datetime_now.Get().Diff(sync_date).Total_mins().To_int() <= auto_interval) return;
		
		Xoa_app app = wiki.App();
		Xoh_page hpg = new Xoh_page();
		update_mgr.Init_by_app(app);
		update_mgr.Init_by_page(wiki, hpg);
		update_mgr.Update(app.Wmf_mgr().Download_wkr(), wiki, page_ttl);

		sync_tbl.Upsert(tmp_dbpg.Id(), Datetime_now.Get());
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
	  Cfg__manual__enabled		= "xowa.wiki.import.page_sync.manual.enabled"
	, Cfg__auto__enabled		= "xowa.wiki.import.page_sync.auto.enabled"
	, Cfg__auto__interval		= "xowa.wiki.import.page_sync.auto.interval"
	, Cfg__auto__scope			= "xowa.wiki.import.page_sync.auto.scope"
	;
}
