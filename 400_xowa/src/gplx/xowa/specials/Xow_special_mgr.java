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
package gplx.xowa.specials; import gplx.*; import gplx.xowa.*;
import gplx.xowa.users.history.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.specials.*;
import gplx.xowa.specials.*;
import gplx.xowa.specials.allPages.*; import gplx.xowa.specials.nearby.*; import gplx.xowa.specials.statistics.*; import gplx.xowa.xtns.translates.*; import gplx.xowa.specials.movePage.*;
import gplx.xowa.specials.xowa.system_data.*; import gplx.xowa.specials.xowa.default_tab.*; import gplx.xowa.specials.xowa.popup_history.*; import gplx.xowa.addons.wikis.imports.*; import gplx.xowa.specials.xowa.diags.*;
import gplx.xowa.xtns.wbases.specials.*;
import gplx.xowa.users.data.*; import gplx.xowa.users.bmks.*;
import gplx.xowa.specials.mgrs.*; import gplx.xowa.addons.wikis.searchs.specials.*;
import gplx.xowa.wikis.pages.*;
public class Xow_special_mgr {
	private final    Hash_adp_bry hash;
	private Xoa_app app;
	public Xow_special_mgr(Xowe_wiki wiki, Xol_lang_itm lang) {
		this.app = wiki.App();
		hash = Hash_adp_bry.ci_u8(lang.Case_mgr());
		page_allpages = new Xows_page_allpages(wiki);
		Evt_lang_changed(wiki.Lang());
	}
	public Xows_page_allpages			Page_allpages() {return page_allpages;} private final    Xows_page_allpages page_allpages;
	public Srch_special_page			Page_search() {return page_search;} private final    Srch_special_page page_search = new Srch_special_page();
	public Xou_history_html				Page_history() {return page_history;} private final    Xou_history_html page_history = new Xou_history_html();
	public Xoud_history_special			Page_history2() {return page_history2;} private final    Xoud_history_special page_history2 = new Xoud_history_special();
	public Nearby_mgr					Page_nearby() {return page_nearby;} private final    Nearby_mgr page_nearby = new Nearby_mgr();
	public Xop_mylanguage_page			Page_mylanguage() {return page_mylanguage;} private final    Xop_mylanguage_page page_mylanguage = new Xop_mylanguage_page();
	public Wdata_itemByTitle_page		Page_itemByTitle() {return page_itemByTitle;} private final    Wdata_itemByTitle_page page_itemByTitle = new Wdata_itemByTitle_page();
	public Xop_statistics_page			Page_statistics() {return page_statistics;} private final    Xop_statistics_page page_statistics = new Xop_statistics_page();
	public Move_page					Page_movePage() {return page_movePage;} private final    Move_page page_movePage = new Move_page();
	public System_data_page				Page_system_data() {return page_system_data;} private final    System_data_page page_system_data = new System_data_page();
	public Default_tab_page				Page_default_tab() {return page_default_tab;} private final    Default_tab_page page_default_tab = new Default_tab_page();
	public Popup_history_page			Page_popup_history() {return page_popup_history;} private final    Popup_history_page page_popup_history = new Popup_history_page();
	public Xows_bmk_page				Page_bmk() {return page_bmk;} private final    Xows_bmk_page page_bmk = new Xows_bmk_page();
	public Xows_diag_page				Page_diag() {return page_diag;} private final    Xows_diag_page page_diag = new Xows_diag_page();
	public void Evt_lang_changed(Xol_lang_itm lang) {
		// add special pages by old manual method; DEPRECATED
		hash.Clear();
		hash.Add_str_obj(Xow_special_meta_.Ttl__search					, page_search);
		hash.Add_str_obj(Xow_special_meta_.Ttl__all_pages				, page_allpages);
		hash.Add_str_obj("prefixindex"									, page_allpages);
		hash.Add_bry_obj(Xou_history_mgr.Ttl_name						, page_history);
		hash.Add_str_obj(Xow_special_meta_.Ttl__page_history			, page_history2);
		hash.Add_str_obj(Xow_special_meta_.Ttl__nearby					, page_nearby);
		hash.Add_str_obj(Xow_special_meta_.Ttl__my_language				, page_mylanguage);
		hash.Add_str_obj(Xow_special_meta_.Ttl__item_by_title			, page_itemByTitle);
		hash.Add_str_obj(Xow_special_meta_.Ttl__statistics				, page_statistics);
		hash.Add_str_obj(Xow_special_meta_.Ttl__move_page				, page_movePage);
		hash.Add_str_obj(Xow_special_meta_.Ttl__system_data				, page_system_data);
		hash.Add_str_obj(Xow_special_meta_.Ttl__default_tab				, page_default_tab);
		hash.Add_str_obj(Xow_special_meta_.Ttl__popup_history			, page_popup_history);
		hash.Add_str_obj(Xow_special_meta_.Ttl__bookmarks				, page_bmk);
		hash.Add_str_obj(Xow_special_meta_.Ttl__diag					, page_diag);

		// add app's Special_regy to hash table; needed for case insensitivity by wiki's lang; EX: Special:rANDom; NOTE: needs to go before lang aliases
		Xoa_special_regy special_regy = app.Special_regy();
		int len = special_regy.Len();
		for (int i = 0; i < len; ++i) {
			Xow_special_page proto = special_regy.Get_at(i);
			Xow_special_meta proto_meta = proto.Special__meta();
			hash.Add_if_dupe_use_1st(proto_meta.Key_bry(), proto);
			for (byte[] alias : proto_meta.Aliases())
				hash.Add_if_dupe_use_1st(alias, proto);
		}

		// add lang's special aliases to hash table; EX: Special:Recherche
		Xol_specials_mgr lang_mgr = lang.Specials_mgr();
		len = lang_mgr.Len();
		for (int i = 0; i < len; ++i) {
			Xol_specials_itm lang_itm = lang_mgr.Get_at(i);
			Xow_special_page page = (Xow_special_page)hash.Get_by_bry(lang_itm.Special());
			if (page == null) continue;	// NOTE: ignore specials that are not in XOWA; EX: Special:ChangeEmail
			for (byte[] alias : lang_itm.Aliases())
				hash.Add_if_dupe_use_1st(alias, page);
		}
	}
	public void Special__gen(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		int slash_pos = Bry_find_.Find_fwd(ttl.Page_txt_wo_qargs(), Xoa_ttl.Subpage_spr);	// check for slash
		byte[] special_name = slash_pos == Bry_find_.Not_found
				? ttl.Base_txt_wo_qarg()							// slash absent; use base_txt; ignore qry args and just get page_names; EX: Search/Earth?fulltext=y; Allpages?from=Earth...
				: Bry_.Mid(ttl.Page_txt_wo_qargs(), 0, slash_pos);	// slash exists; use root page; EX: Special:ItemByTitle/enwiki/Earth
		special_name = Xoa_ttl.Replace_spaces(special_name);		// handle spaces; EX:Spezial:Zufällige_Seite

		Xow_special_page special = (Xow_special_page)hash.Get_by_bry(special_name);
		if (special != null) {	// special found; generate it;
			// check safelisted pages; DATE:2017-07-22
			Hash_adp safelist = app.Special_regy().Safelist_pages();
			if (safelist.Count() > 0) { // safelist pages enabled
				if (!safelist.Has(special_name)) {
					byte[] safelist_failed = Bry_.new_u8("This special page is not listed in the special_pages safelist. Re-run XOWA and list it in the command-line arguments similar to this: \"--http_server.special_pages_safelist " + String_.new_u8(special_name) + "\"");
					Xopage_html_data page_data = new Xopage_html_data(special.Special__meta().Display_ttl(), safelist_failed);
					page_data.Apply(page);
					return;
				}
			}

			special = special.Special__clone();
			page.Db().Page().Modified_on_(Datetime_now.Get());
			try {special.Special__gen(wiki, page, url, ttl);}
			catch (Exception e) {Gfo_log_.Instance.Warn("failed to generate special page", "url", url.To_str(), "err", Err_.Message_gplx_log(e));}
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_search))				return page_search;
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_search = "search";
}
