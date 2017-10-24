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
import gplx.xowa.wikis.domains.*;
import gplx.xowa.addons.wikis.pages.syncs.wmapis.*;
public class Xosync_read_mgr implements Gfo_invk {		
	private int auto_interval = 60 * 24;	// in minutes
	private Db_conn sync_conn; private Xosync_sync_tbl sync_tbl;
	private final    Xopg_match_mgr auto_page_matcher = new Xopg_match_mgr();
	private final    Xosync_update_mgr update_mgr = new Xosync_update_mgr();
	public void Init_by_wiki(Xow_wiki wiki) {
		this.Auto_scope_("*:Main_Page");
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__manual__enabled, Cfg__auto__enabled, Cfg__auto__interval, Cfg__auto__scope);
	}
	public boolean Auto_enabled() {return auto_enabled;} private boolean auto_enabled = false;
	public boolean Manual_enabled() {return manual_enabled;} private boolean manual_enabled;
	public Xoa_ttl Auto_update(Xow_wiki wiki, Xoa_page page, Xoa_ttl page_ttl) {
		// skip if not enabled
		if (!auto_enabled) return null;

		// skip if home or other
		if (Int_.In
			( wiki.Domain_itm().Domain_type_id()
			, Xow_domain_tid_.Tid__home
			, Xow_domain_tid_.Tid__other))
			return null;

		// init some vars
		Xoa_ttl rv = null;
		int prv_page_id = -1;
		Hash_adp pages = Hash_adp_.New();

		// loop to handle redirects
		while (true) {
			// exit early...
			if (pages.Has(page_ttl.Full_db()) // ... if circular redirect; EX: A -> B -> A
				|| pages.Count() == 3) {      // ... or too many redirects EX: A -> B -> C -> D
				return rv;
			}
			// else, add to list of pages
			else {
				pages.Add_as_key_and_val(page_ttl.Full_db());
			}

			// skip if special
			if (page_ttl.Ns().Id_is_special()) return rv;

			// skip if it doesn't match criteria in Options
			if (!auto_page_matcher.Match(wiki, page_ttl.Full_db())) return rv;

			// get page data based on id
			Xowd_page_itm tmp_dbpg = new Xowd_page_itm();
			wiki.Data__core_mgr().Db__core().Tbl__page().Select_by_ttl(tmp_dbpg, page_ttl.Ns(), page_ttl.Page_db());

			// get sync conn
			if (sync_conn == null) {
				Io_url sync_db_url = wiki.Fsys_mgr().Root_dir().GenSubFil(wiki.Domain_str() + "-sync.xowa");
				Gfo_usr_dlg_.Instance.Log_many("", "", "page_sync: loading database for page_sync_data; url=~{0}", sync_db_url.Raw());
				sync_conn = Db_conn_bldr.Instance.Get_or_autocreate(true, sync_db_url);
				sync_tbl = new Xosync_sync_tbl(sync_conn);
				sync_conn.Meta_tbl_assert(sync_tbl);
			}

			// get sync_date and check if sync needed
			DateAdp sync_date = sync_tbl.Select_sync_date_or_min(tmp_dbpg.Id());
			if (Datetime_now.Get().Diff(sync_date).Total_mins().To_int() <= auto_interval) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "page_sync: skipping auto-sync for page; wiki=~{0} page=~{1} sync_date=~{2}", wiki.Domain_bry(), page_ttl.Full_db(), sync_date.XtoStr_fmt_yyyy_MM_dd_HH_mm_ss());
				return rv;
			}
			else {
				Gfo_usr_dlg_.Instance.Log_many("", "", "page_sync: running auto-sync for page; wiki=~{0} page=~{1} sync_date=~{2}", wiki.Domain_bry(), page_ttl.Full_db(), sync_date.XtoStr_fmt_yyyy_MM_dd_HH_mm_ss());
			}
			
			// auto-sync page
			Xoa_app app = wiki.App();
			Xoh_page hpg = new Xoh_page();
			update_mgr.Init_by_app(app);
			update_mgr.Init_by_page(wiki, hpg);
			Xowm_parse_data parse_data = update_mgr.Update(app.Wmf_mgr().Download_wkr(), wiki, page_ttl);
			if (parse_data == null)
				return rv;

			// insert into sync_db
			Gfo_usr_dlg_.Instance.Log_many("", "", "page_sync: updating sync table; page=~{0}", page_ttl.Full_db());
			sync_tbl.Upsert(parse_data.Page_id(), Datetime_now.Get());

			// redirect occurred; EX: A -> B will have A,B in pages
			if (pages.Count() > 1) {
				wiki.Data__core_mgr().Tbl__page().Update__redirect(parse_data.Page_id(), prv_page_id);
			}

			// NOTE: set rv to last good page; EX: A -> B; A exists but B doesn't; show A, not B; DATE:2017-05-07
			rv = page_ttl;

			// no redirects
			if (parse_data.Redirect_to_ttl == null)
				return rv;
			// redirect occured;
			else {
				prv_page_id = parse_data.Page_id();
				page_ttl = wiki.Ttl_parse(parse_data.Redirect_to_ttl);
			}
		}
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
