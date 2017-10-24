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
package gplx.xowa.addons.wikis.searchs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.core.primitives.*; import gplx.xowa.addons.wikis.searchs.specials.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.domains.crts.*;	
import gplx.xowa.specials.*; import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.cbks.*;
import gplx.xowa.addons.wikis.searchs.gui.htmlbars.*;
public class Srch_special_page implements Xow_special_page {
	public Xow_special_meta		Special__meta()			{return Xow_special_meta_.Itm__search;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		Srch_special_cfg search_cfg = wiki.Appe().Addon_mgr().Itms__search__special();
		Xow_domain_itm[] search_domain_ary = Get_domains(wiki.Appe(), search_cfg, wiki.Domain_itm());

		// get args from urls while applying defaults from search_cfg
		Srch_qarg_mgr qargs_mgr = new Srch_qarg_mgr(wiki.App().Addon_mgr().Itms__search__special().Ns_mgr());
		qargs_mgr.Clear();

		// parse args_default_str
		String args_default_str = wiki.App().Cfg().Get_str_wiki_or(wiki, Srch_search_mgr.Cfg__args_default, "");
		if (String_.Len_gt_0(args_default_str)) {
			byte[] bry = Bry_.new_a7("http://x.org/a?" + args_default_str);
			gplx.core.net.Gfo_url tmp_url = wiki.App().User().Wikii().Utl__url_parser().Url_parser().Parse(bry, 0, bry.length);
			qargs_mgr.Parse(tmp_url.Qargs());
		}
		qargs_mgr.Parse(url.Qargs_ary());
		qargs_mgr.Ns_mgr().Add_main_if_empty();

		// get search_raw
		byte[] search_raw = qargs_mgr.Search_raw();
		if (search_raw == null) {					// search is not in qarg; EX:Special:Search?search=Earth
			search_raw = ttl.Leaf_txt_wo_qarg();	// assume search is in leaf; EX: Special:Search/Earth
			qargs_mgr.Search_raw_(search_raw);
		}
		if (Bry_.Len_eq_0(search_raw)) return;		// emptry String; exit now, else null ref error; DATE:2015-08-11

		// get page directly from url
		boolean fulltext_invoked = url.Qargs_mgr().Match(Qarg__fulltext, Qarg__fulltext__y);
		Xoa_ttl search_ttl = Xoa_ttl.Parse(wiki, search_raw); 
		Xoae_page search_page = page;
		if (	!fulltext_invoked
			&&	!Bry_.Eq(search_raw, Xow_special_meta_.Itm__search.Ttl_bry()))	// do not lookup self else stack overflow; happens when going directly to Special:Search (from history)
			search_page = wiki.Data_mgr().Load_page_by_ttl(search_ttl);					// try to find page; EX:Special:Search?search=Earth -> en.w:Earth; needed for search suggest

		// page not found, or explicit_search invoked
		if (search_page.Db().Page().Exists_n() || fulltext_invoked) {
			Srch_special_searcher search_mgr = new Srch_special_searcher(wiki.Appe().Wiki_mgr());
			if (qargs_mgr.Cancel() != null) {	// cancel any existing searches
				search_mgr.Search__cancel(qargs_mgr.Cancel());
				page.Tab_data().Cancel_show_y_();
				return;
			}
			page.Html_data().Html_restricted_n_();
			page.Html_data().Xtn_search_text_(search_raw);
			Srch_search_qry qry = Srch_search_qry.New__search_page(search_domain_ary, wiki, wiki.App().Addon_mgr().Itms__search__special().Ns_mgr(), search_cfg.Auto_wildcard(), search_raw, qargs_mgr.Slab_idx(), search_cfg.Results_per_page());
			search_mgr.Search(wiki, page, search_cfg.Async_db(), search_domain_ary, qry);
		}
		// page found; return it;
		else {
			wiki.Parser_mgr().Parse(search_page, true);
			page.Db().Text().Text_bry_(search_page.Db().Text().Text_bry());
			if (page.Root() != null) // NOTE: null when going from w:Earth -> q:Earth; DATE:2013-03-20
				page.Root().Data_htm_(search_page.Root().Data_htm());
			Xoa_url redirect_url = Xoa_url.New(wiki, search_ttl);
			page.Ttl_(search_ttl).Url_(redirect_url);
			page.Redirect_trail().Itms__add__article(redirect_url, search_ttl, null);
		}
	}
	public static final byte Match_tid_all = 0, Match_tid_bgn = 1;
	public static final byte Version_null = 0, Version_1 = 1, Version_2 = 2;
	private static final    byte[] Qarg__fulltext = Bry_.new_a7("fulltext"), Qarg__fulltext__y = Bry_.new_a7("y");
	private static Xow_domain_itm[] Get_by_crt(gplx.xowa.wikis.xwikis.Xow_xwiki_mgr xwiki_mgr, Xow_domain_itm cur, gplx.xowa.wikis.domains.crts.Xow_domain_crt_itm crt) {
		List_adp rv = List_adp_.New();
		int len = xwiki_mgr.Len();
		for (int i = 0; i < len; ++i) {
			gplx.xowa.wikis.xwikis.Xow_xwiki_itm xwiki = xwiki_mgr.Get_at(i);
			if (	!xwiki.Offline()									// note that filters are broad (*.wiktionary); skip offline wikis which won't be available on system
				&&	xwiki.Domain_tid() != Xow_domain_tid_.Tid__home)	// note that home is marked "offline" so it won't show up in wikis sidebar
				continue;
			Xow_domain_itm domain_itm = Xow_domain_itm_.parse(xwiki.Domain_bry());
			if (crt.Matches(cur, domain_itm)) rv.Add(domain_itm);
		}
		return (Xow_domain_itm[])rv.To_ary_and_clear(Xow_domain_itm.class);
	}
	private static Xow_domain_itm[] Get_domains(Xoae_app app, Srch_special_cfg cfg, Xow_domain_itm wiki_domain) {
		Xow_domain_crt_itm crt = cfg.Multi_wikis_crt(wiki_domain);
		Xow_domain_itm[] rv = Get_by_crt(app.Usere().Wiki().Xwiki_mgr(), wiki_domain, crt);
		if (rv.length == 0) rv = new Xow_domain_itm[] {wiki_domain};	// default to current if bad input

		Xow_domain_crt_itm[] ary = cfg.Multi_sorts_crt(wiki_domain);
		if (ary == null) return Xow_domain_itm_.Ary_empty;	// default to no sort if bad input
		Xow_domain_sorter__manual sorter = new Xow_domain_sorter__manual(wiki_domain, ary);
		Xow_domain_sorter__manual.Sort(sorter, rv);
		return rv;
	}

	public Xow_special_page Special__clone() {return this;}
}
