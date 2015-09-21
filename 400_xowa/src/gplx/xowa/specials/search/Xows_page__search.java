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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*; import gplx.xowa.apis.xowa.specials.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.domains.crts.*;
public class Xows_page__search implements Xows_page, GfoInvkAble, GfoEvObj {
	private final Xoae_app app; private final Xow_domain_itm wiki_domain; private final Xoapi_search search_api;		
	private final Xows_core search_mgr; private final Xows_arg_mgr args_mgr = new Xows_arg_mgr();
	private Xow_domain_itm[] search_domain_ary;
	public Xows_page__search(Xowe_wiki wiki) {
		this.app = wiki.Appe();
		this.wiki_domain = wiki.Domain_itm();
		this.search_mgr = new Xows_core(wiki.Appe().Wiki_mgr());
		this.ev_mgr = GfoEvMgr.new_(this);
		this.search_api = wiki.Appe().Api_root().Special().Search();
		GfoEvMgr_.SubSame_many(search_api, this, Xoapi_search.Evt_multi_wikis_changed, Xoapi_search.Evt_multi_wikis_changed);
	}
	public GfoEvMgr EvMgr() {return ev_mgr;} private GfoEvMgr ev_mgr;
	public Xows_special_meta Special_meta() {return Xows_special_meta_.Itm__search;}
	private void Multi_wikis_changed() {
		Xow_domain_crt_itm crt = search_api.Multi_wikis_crt(wiki_domain);
		this.search_domain_ary = app.Usere().Wiki().Xwiki_mgr().Get_by_crt(wiki_domain, crt);
		if (search_domain_ary.length == 0) search_domain_ary = new Xow_domain_itm[] {wiki_domain};	// default to current if bad input
		Multi_sorts_changed();
	}
	private void Multi_sorts_changed() {
		Xow_domain_crt_itm[] ary = search_api.Multi_sorts_crt(wiki_domain);
		if (ary == null) return;	// default to no sort if bad input
		Xow_domain_sorter__manual sorter = new Xow_domain_sorter__manual(wiki_domain, ary);
		Xow_domain_sorter__manual.Sort(sorter, search_domain_ary);
	}
	public void Special_gen(Xowe_wiki wiki, Xoae_page page, Xoa_url url, Xoa_ttl ttl) {
		if (wiki.Domain_tid() == Xow_domain_type_.Int__home) return;	// do not allow search in home wiki; will throw null ref error b/c no search_ttl dirs
		if (search_domain_ary == null) Multi_wikis_changed();
		// get args
		Xog_search_suggest_mgr search_suggest_mgr = wiki.Appe().Gui_mgr().Search_suggest_mgr();
		args_mgr.Clear();
		args_mgr.Parse(search_suggest_mgr.Args_default());
		args_mgr.Parse(url.Qargs_ary());
		args_mgr.Ns_mgr().Add_main_if_empty();
		// get search_bry
		byte[] search_bry = args_mgr.Search_bry();
		if (search_bry == null) {					// search is not in qarg; EX:Special:Search?search=Earth
			search_bry = ttl.Leaf_txt_wo_qarg();	// assume search is in leaf; EX: Special:Search/Earth
			args_mgr.Search_bry_(search_bry);
		}
		if (Bry_.Len_eq_0(search_bry)) return;		// emptry String; exit now, else null ref error; DATE:2015-08-11
		if (	search_suggest_mgr.Auto_wildcard()	// add * automatically if option set
			&&	wiki.Db_mgr().Tid() == gplx.xowa.dbs.Xodb_mgr_sql.Tid_sql	// only apply to sql
			&&	Bry_find_.Find_fwd(search_bry, Byte_ascii.Star) == -1		// search term does not have asterisk
			)
			search_bry = Bry_.Add(search_bry, Byte_ascii.Star);
		// url.Page_bry_(Bry_.Add(Xows_special_meta_.Itm__search.Ttl_bry(), Byte_ascii.Slash_bry, search_bry));// HACK: need to re-set Page b/c href_parser does not eliminate qargs; DATE:2013-02-08
		// search wiki
		Xoa_ttl search_ttl = Xoa_ttl.parse(wiki, search_bry); 
		Xoae_page search_page = page;
		if (!Bry_.Eq(search_bry, Xows_special_meta_.Itm__search.Ttl_bry()))	// do not lookup page else stack overflow; happens when going directly to Special:Search (from history)
			search_page = wiki.Data_mgr().Get_page(search_ttl, false);	// try to find page; EX:Special:Search?search=Earth -> en.w:Earth; needed for search suggest
		// page not found, or explicit_search invoked
		if (search_page.Missing() || url.Qargs_mgr().Match(Qarg__fulltext, Qarg__fulltext__y)) {
			if (args_mgr.Cancel() != null) {
				search_mgr.Cancel(args_mgr.Cancel());
				page.Tab_data().Cancel_show_y_();
				return;
			}
			page.Html_data().Html_restricted_n_();
			page.Html_data().Xtn_search_text_(search_bry);				
			Xows_ui_qry qry = new Xows_ui_qry(search_bry, args_mgr.Paging_idx(), search_api.Results_per_page(), args_mgr.Sort_tid(), args_mgr.Ns_mgr(), search_api.Async_db(), search_domain_ary);
			search_mgr.Search(wiki, page, qry);
		}
		// page found; return it;
		else {
			wiki.Parser_mgr().Parse(search_page, true);
			page.Data_raw_(search_page.Data_raw());
			if (page.Root() != null) // NOTE: null when going from w:Earth -> q:Earth; DATE:2013-03-20
				page.Root().Data_htm_(search_page.Root().Data_htm());
			page.Ttl_(search_ttl).Url_(Xoa_url.new_(wiki.Domain_bry(), search_ttl.Full_txt())).Redirected_(true);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Xoapi_search.Evt_multi_wikis_changed))				Multi_wikis_changed();
		else if	(ctx.Match(k, Xoapi_search.Evt_multi_sorts_changed))				Multi_sorts_changed();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final byte Match_tid_all = 0, Match_tid_bgn = 1;
	public static final byte Version_null = 0, Version_1 = 1, Version_2 = 2;
	private static final byte[] Qarg__fulltext = Bry_.new_a7("fulltext"), Qarg__fulltext__y = Bry_.new_a7("y");
}
