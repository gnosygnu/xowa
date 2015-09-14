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
package gplx.xowa.specials; import gplx.*; import gplx.xowa.*;
import gplx.xowa.users.history.*;
import gplx.xowa.specials.*;
import gplx.xowa.specials.allPages.*; import gplx.xowa.specials.search.*; import gplx.xowa.specials.nearby.*; import gplx.xowa.specials.randoms.*; import gplx.xowa.specials.statistics.*; import gplx.xowa.xtns.translates.*; import gplx.xowa.specials.movePage.*;
import gplx.xowa.specials.xowa.system_data.*; import gplx.xowa.specials.xowa.default_tab.*; import gplx.xowa.specials.xowa.popup_history.*; import gplx.xowa.specials.xowa.file_browsers.*; import gplx.xowa.specials.xowa.diags.*;
import gplx.xowa.xtns.wdatas.specials.*;
import gplx.xowa.users.data.*; import gplx.xowa.users.bmks.*;
public class Xows_mgr {
	private final Hash_adp_bry hash;
	public Xows_mgr(Xowe_wiki wiki, Xol_lang lang) {
		hash = Hash_adp_bry.ci_u8(lang.Case_mgr());
		page_allpages = new Xows_page_allpages(wiki);
		page_search = new Xows_page__search(wiki);
		page_random = new Xows_page_random(wiki);
		Evt_lang_changed(wiki.Lang());
	}
	public Xows_page_allpages			Page_allpages() {return page_allpages;} private final Xows_page_allpages page_allpages;
	public Xows_page__search			Page_search() {return page_search;} private final Xows_page__search page_search;
	public Xows_page_random				Page_random() {return page_random;} private final Xows_page_random page_random;
	public Xop_randomRootPage_page		Page_randomRootPage() {return page_randomRootPage;} private final Xop_randomRootPage_page page_randomRootPage = new Xop_randomRootPage_page();
	public Xou_history_html				Page_history() {return page_history;} private final Xou_history_html page_history = new Xou_history_html();
	public Xoud_history_special			Page_history2() {return page_history2;} private final Xoud_history_special page_history2 = new Xoud_history_special();
	public Nearby_mgr					Page_nearby() {return page_nearby;} private final Nearby_mgr page_nearby = new Nearby_mgr();
	public Xop_mylanguage_page			Page_mylanguage() {return page_mylanguage;} private final Xop_mylanguage_page page_mylanguage = new Xop_mylanguage_page();
	public Wdata_itemByTitle_page		Page_itemByTitle() {return page_itemByTitle;} private final Wdata_itemByTitle_page page_itemByTitle = new Wdata_itemByTitle_page();
	public Xop_statistics_page			Page_statistics() {return page_statistics;} private final Xop_statistics_page page_statistics = new Xop_statistics_page();
	public Move_page					Page_movePage() {return page_movePage;} private final Move_page page_movePage = new Move_page();
	public System_data_page				Page_system_data() {return page_system_data;} private final System_data_page page_system_data = new System_data_page();
	public Default_tab_page				Page_default_tab() {return page_default_tab;} private final Default_tab_page page_default_tab = new Default_tab_page();
	public Popup_history_page			Page_popup_history() {return page_popup_history;} private final Popup_history_page page_popup_history = new Popup_history_page();
	public Xosp_fbrow_special			Page_file_browser() {return page_file_browser;} private final Xosp_fbrow_special page_file_browser = new Xosp_fbrow_special();
	public Xows_bmk_page				Page_bmk() {return page_bmk;} private final Xows_bmk_page page_bmk = new Xows_bmk_page();
	public Xows_diag_page				Page_diag() {return page_diag;} private final Xows_diag_page page_diag = new Xows_diag_page();
	public void Evt_lang_changed(Xol_lang lang) {
		hash.Clear();
		hash.Add_str_obj(Xows_special_meta_.Key__search					, page_search);
		hash.Add_str_obj(Xows_special_meta_.Key__all_pages				, page_allpages);
		hash.Add_str_obj("prefixindex"									, page_allpages);
		hash.Add_str_obj(Xows_special_meta_.Key__random					, page_random);
		hash.Add_str_obj("random"										, page_random);
		hash.Add_str_obj(Xows_special_meta_.Key__random_root_page		, page_randomRootPage);
		hash.Add_bry_obj(Xou_history_mgr.Ttl_name						, page_history);
		hash.Add_str_obj(Xows_special_meta_.Key__page_history			, page_history2);
		hash.Add_str_obj(Xows_special_meta_.Key__nearby					, page_nearby);
		hash.Add_str_obj(Xows_special_meta_.Key__my_language			, page_mylanguage);
		hash.Add_str_obj(Xows_special_meta_.Key__item_by_title			, page_itemByTitle);
		hash.Add_str_obj(Xows_special_meta_.Key__statistics				, page_statistics);
		hash.Add_str_obj(Xows_special_meta_.Key__move_page				, page_movePage);
		hash.Add_str_obj(Xows_special_meta_.Key__system_data			, page_system_data);
		hash.Add_str_obj(Xows_special_meta_.Key__default_tab			, page_default_tab);
		hash.Add_str_obj(Xows_special_meta_.Key__popup_history			, page_popup_history);
		hash.Add_str_obj(Xows_special_meta_.Key__file_browser			, page_file_browser);
		hash.Add_str_obj(Xows_special_meta_.Key__bookmarks				, page_bmk);
		hash.Add_str_obj(Xows_special_meta_.Key__diag					, page_diag);
	}
	public void Special_gen(Xowe_wiki wiki, Xoae_page page, Xoa_url url, Xoa_ttl ttl) {
		int slash_pos = Bry_finder.Find_fwd(ttl.Page_txt_wo_qargs(), Xoa_ttl.Subpage_spr);	// check for slash
		byte[] special_name = slash_pos == Bry_.NotFound
				? ttl.Base_txt_wo_qarg()							// no slash found; use base_txt; ignore qry args and just get page_names; EX: Search/Earth?fulltext=y; Allpages?from=Earth...
				: Bry_.Mid(ttl.Page_txt_wo_qargs(), 0, slash_pos);	// slash found; use root page; EX: Special:ItemByTitle/enwiki/Earth
		Object o = hash.Get_by_bry(special_name);
		if (o == null) {
			gplx.xowa.Xol_specials_itm special_itm = wiki.Lang().Specials_mgr().Get_by_alias(special_name);
			if (special_itm != null)
				o = hash.Get_by_bry(special_itm.Special());
		}
		if (o != null) {
			Xows_page special = (Xows_page)o;
			page.Revision_data().Modified_on_(DateAdp_.Now());
			special.Special_gen(wiki, page, url, ttl);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_search))				return page_search;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_search = "search";
}
