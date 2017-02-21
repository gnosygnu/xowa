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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.vnts.*;
import gplx.xowa.guis.views.*;	import gplx.xowa.parsers.utils.*;
import gplx.xowa.wikis.pages.dbs.*; import gplx.xowa.wikis.pages.redirects.*;
public class Xow_page_mgr implements Gfo_invk {
	private final    Xowe_wiki wiki;
	public Xow_page_mgr(Xowe_wiki wiki) {this.wiki = wiki;}
	public Xoae_page Load_page_by_ttl			(Xoa_ttl ttl)	{return Load_page(ttl, Bool_.N);}
	public Xoae_page Load_page_by_ttl_for_msg	(Xoa_ttl ttl)	{return Load_page(ttl, Bool_.Y);}
	private Xoae_page Load_page(Xoa_ttl ttl, boolean called_from_msg) {
		Xoae_page rv = Xoae_page.New(wiki, ttl);
		Load_by_ns(rv, wiki.Utl__url_parser().Parse(ttl.Raw()), ttl, called_from_msg);
		return rv;
	}
	private void Load_by_ns(Xoae_page rv, Xoa_url url, Xoa_ttl ttl, boolean called_from_msg) {
		rv.Url_(url);	// NOTE: must update page.Url(); should combine with Xoae_page.New()

		// WORKAROUND: loop twice in order to allow redirects from regular page to special
		for (int i = 0; i < 2; i++) {
			Xow_ns ns = ttl.Ns();
			switch (ns.Id()) {
				case Xow_ns_.Tid__special:		// Special pages are built (not loaded from db)
					wiki.Special_mgr().Special__gen(wiki.App(), wiki, rv, url, ttl);
					Xopg_redirect_itm redirect_itm = rv.Redirect_trail().Itms__get_at_nth_or_null();
					if (redirect_itm != null) {
						rv.Redirect_trail().Clear();	// clear needed; EX: Special:Random -> [[Redirected_page]] -> {must clear here} -> [[Page]]
						Load_by_ns(rv, redirect_itm.Url(), redirect_itm.Ttl(), called_from_msg);
					}
					return;
				case Xow_ns_.Tid__mediawiki:	// MediaWiki msgs can either be loaded from memory, or from database
					if (	!called_from_msg	// if called_from_msg, fall through to actual data retrieval below, else infinite loop; DATE:2014-05-09
						&&	Xow_page_tid.Identify_by_ttl(ttl.Page_db()) == Xow_page_tid.Tid_wikitext // skip ".js" and ".css" pages in MediaWiki; DATE:2014-06-13
						) {		
						Xol_lang_itm lang = wiki.Lang();
						byte[] msg_key = ttl.Page_db();
						Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
						msg_key = lang.Case_mgr().Case_build_1st_lower(tmp_bfr, msg_key, 0, msg_key.length);
						byte[] msg_val = Xol_msg_mgr_.Get_msg_itm(tmp_bfr, wiki, wiki.Lang(), msg_key).Val();	// NOTE: do not change to Get_msg_val; Get_msg_val, also replaces $1 with values, and $1 needs to be preserved for callers;
						rv.Db().Text().Text_bry_(msg_val);
						tmp_bfr.Mkr_rls();
						return;
					}
					break;
			}
			Xoa_ttl redirect_ttl = Load_from_db(rv, ns, ttl, url.Qargs_mgr().Match(Xoa_url_.Qarg__redirect, Xoa_url_.Qarg__redirect__no));
			// GOTO_BY_LOOP: handle redirect to special
			if (redirect_ttl != null && redirect_ttl.Ns().Id_is_special()) {
				ttl = redirect_ttl;
				url = wiki.Utl__url_parser().Parse(ttl.Raw());	// update url w/ redirected ttl; need to update qargs else will redirect to correct ttl (Special:XowaCfg) but not qargs (grp=xowa.addon.math); EX:src=Options/Math; trg=Special:XowaCfg?grp=xowa.addon.math; DATE:2017-01-02
				rv.Redirect_trail().Clear();
				rv.Db().Page().Exists_y_();
			}
			// not a redirect to special; just exit
			else
				return;
		}
	}
	public Xoa_ttl Load_from_db(Xoae_page rv, Xow_ns ns, Xoa_ttl ttl, boolean redirect_force) {
		int redirects = 0;
		Xowd_page_itm page_row = Xowd_page_itm.new_tmp();
		while (true) {	// loop until (a) no more redirects or (b) page not found
			// load from page table
			boolean exists = wiki.Db_mgr().Load_mgr().Load_by_ttl(page_row, ns, ttl.Page_db());
			if (!exists) {rv.Db().Page().Exists_n_(); return ttl;}
			if (wiki.App().Mode().Tid_is_gui())	// NOTE: must check if gui, else will write during mass build; DATE:2014-05-03
				wiki.Appe().Usr_dlg().Prog_many("", "", "loading page for ~{0}", ttl.Raw());

			// load page_info
			rv.Db().Page().Id_(page_row.Id()).Modified_on_(page_row.Modified_on()).Html_db_id_(page_row.Html_db_id());

			// load from text table
			wiki.Db_mgr().Load_mgr().Load_page(page_row, ns);
			byte[] wtxt = page_row.Text();
			rv.Db().Text().Text_bry_(wtxt);
			if (redirect_force) return ttl;			// redirect_force passed; return page now, even if page is a redirect elsewhere; NOTE: only applies to WTXT, not HTML

			// handle redirects for html_dbs
			if (	page_row.Redirect_id() > 0		// redirect exists
				&&	Bry_.Len_eq_0(wtxt)) {			// wikitext is not found
				Redirect_to_html_page(rv, wiki, page_row);
				return ttl;
			}

			// handle redirects
			Xoa_ttl redirect_ttl = wiki.Redirect_mgr().Extract_redirect(wtxt);
			if  (	redirect_ttl == null		// not a redirect
				||	redirects++ > 4)			// too many redirects; something went wrong
				return redirect_ttl;

			// redirect; do some bookkeeping and reset ns / ttl
			// NOTE: this adds the target ttl to redirect_mgr (#REDIRECT [[A]]); note that special redirects will add source ttl; DATE:2016-07-31
			rv.Redirect_trail().Itms__add__article(Xoa_url.New(wiki, rv.Ttl()), rv.Ttl(), wtxt);// NOTE: must be url_encoded; EX: "en.wikipedia.org/?!" should generate link of "en.wikipedia.org/%3F!?redirect=no"
			rv.Ttl_(redirect_ttl);
			ns = redirect_ttl.Ns();
			ttl = redirect_ttl;
		}
	}
	private void Redirect_to_html_page(Xoae_page rv, Xowe_wiki wiki, Xowd_page_itm page_row) {	// handle redirects for HtmlDbs; PAGE:fr.b:Wikibooks DATE:2016-07-14
		// load redirect
		Xowd_page_itm redirect_row = new Xowd_page_itm();
		wiki.Db_mgr().Load_mgr().Load_by_id(redirect_row, page_row.Redirect_id());
		if (rv.Db().Page().Exists_n()) {
			return;
		}

		// set redirect data
		rv.Db().Page().Id_(redirect_row.Id()).Modified_on_(redirect_row.Modified_on()).Html_db_id_(redirect_row.Html_db_id());
		Xoa_ttl redirect_ttl = wiki.Ttl_parse(redirect_row.Ns_id(), redirect_row.Ttl_page_db());
		rv.Ttl_(redirect_ttl);
		rv.Redirect_trail().Itms__add__article(Xoa_url.New(wiki, redirect_ttl), redirect_ttl, Bry_.Empty);					// NOTE: must be url_encoded; EX: "en.wikipedia.org/?!" should generate link of "en.wikipedia.org/%3F!?redirect=no"
	}
	public Xoae_page Load_page_and_parse(Xoa_url url, Xoa_ttl ttl) {return Load_page_and_parse(url, ttl, wiki.Lang(), wiki.Appe().Gui_mgr().Browser_win().Active_tab(), true);}
	public Xoae_page Load_page_and_parse(Xoa_url url, Xoa_ttl ttl, Xol_lang_itm lang, Xog_tab_itm tab, boolean parse_page) {
		wiki.Init_assert();
		Xoae_page page = Xoae_page.New(wiki, ttl); page.Tab_data().Tab_(tab); 
		// COMMENT: breaks position-restore when moving back from history; if (tab != null) tab.Page_ref_(page);	// HACK: (1) null check for http server; (2) Page_ref_(page) needed for log in xobc
		this.Load_by_ns(page, url, ttl, false);
		if (page.Db().Page().Exists_n()) {	// page doesn't exist; try variants
			boolean vnt_missing = true;
			Xol_vnt_mgr vnt_mgr = lang.Vnt_mgr();
			if (vnt_mgr.Enabled()) {	// if vnt enabled, then try to load by vnt form; DATE:2015-09-15
				gplx.xowa.wikis.data.tbls.Xowd_page_itm page_itm = vnt_mgr.Convert_mgr().Convert_ttl(wiki, ttl);
				if (page_itm != null && page_itm.Exists()) {
					Xoa_ttl vnt_ttl = Xoa_ttl.Parse(wiki, ttl.Ns().Id(), page_itm.Ttl_page_db());
					page = this.Load_page(vnt_ttl, false);
					vnt_missing = page.Db().Page().Exists_n();
				}
			}
			if (vnt_missing) {
				if (ttl.Ns().Id_is_file()) {
					Xowe_wiki commons_wiki = (Xowe_wiki)wiki.Appe().Wiki_mgr().Get_by_or_null(wiki.Commons_wiki_key());
					if (commons_wiki != null) {										// commons exists
						if (!Bry_.Eq(wiki.Domain_bry(), commons_wiki.Domain_bry())) {		// !Bry_.Eq is recursion guard
							Xoae_page rv = commons_wiki.Data_mgr().Load_page_and_parse(url, ttl, wiki.Lang(), tab, true);
							if (rv.Db().Page().Exists()) {
								rv.Commons_mgr().Source_wiki_(wiki);
								return rv;
							}
							else {
								rv.Db().Page().Exists_y_();
								page.Commons_mgr().Xowa_mockup_(true);
								return page;
							}
						}
					}
				}
				else {
					page.Db().Page().Exists_n_();
					return page;
				}
			}
		}
		if (page.Db().Page().Exists_n()) return page; // NOTE: commons can return null page
		page.Tab_data().Tab_(tab);
		page.Lang_(lang);
		if (parse_page)
			wiki.Parser_mgr().Parse(page, false);	// NOTE: do not clear page b/c reused for search
		return page;
	}
	public void Redirect(Xoae_page page, byte[] page_bry) {
		Xoa_ttl trg_ttl = Xoa_ttl.Parse(wiki, page_bry);
		Xoa_url trg_url = Xoa_url.New(wiki.Domain_bry(), page_bry);
		page.Ttl_(trg_ttl).Url_(trg_url);
		page.Redirect_trail().Itms__add__article(trg_url, trg_ttl, null);
		wiki.Data_mgr().Load_from_db(page, trg_ttl.Ns(), trg_ttl, trg_url.Qargs_mgr().Match(Xoa_url_.Qarg__redirect, Xoa_url_.Qarg__redirect__no));
	}
	public void Redirect2(Xowe_wiki wiki2, Xoae_page page, byte[] page_bry) {
		Xoa_ttl trg_ttl = Xoa_ttl.Parse(wiki2, page_bry);
		Xoa_url trg_url = Xoa_url.New(wiki2.Domain_bry(), page_bry);
		page.Ttl_(trg_ttl).Url_(trg_url);
		page.Redirect_trail().Itms__add__article(trg_url, trg_ttl, null);
		wiki2.Data_mgr().Load_from_db(page, trg_ttl.Ns(), trg_ttl, trg_url.Qargs_mgr().Match(Xoa_url_.Qarg__redirect, Xoa_url_.Qarg__redirect__no));
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_create_enabled_))				wiki.Db_mgr().Save_mgr().Create_enabled_(m.ReadYn("v"));
		else if	(ctx.Match(k, Invk_update_modified_on_enabled_))	wiki.Db_mgr().Save_mgr().Update_modified_on_enabled_(m.ReadYn("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_create_enabled_ = "create_enabled_", Invk_update_modified_on_enabled_ = "update_modified_on_enabled_";
}
