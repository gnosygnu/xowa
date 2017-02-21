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
package gplx.xowa.drds.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*;
import gplx.core.net.*; import gplx.xowa.addons.wikis.imports.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.sections.*;
import gplx.xowa.wikis.pages.redirects.*;
public class Xod_page_mgr {
	public Xod_page_itm Get_page(Xow_wiki wiki, Xoa_url page_url) {
		Xod_page_itm rv = new Xod_page_itm();

		// load meta info like page_id, modified, etc
		Xoa_ttl ttl = wiki.Ttl_parse(page_url.Page_bry());
		if (ttl.Ns().Id_is_special()) return Load_special(rv, wiki, page_url, ttl);
		Xowd_page_itm dbpg = new Xowd_page_itm();
		try {wiki.Data__core_mgr().Tbl__page().Select_by_ttl(dbpg, ttl.Ns(), ttl.Page_db());}
		catch (Exception e) {// throw detailed exception to track down page_score exception
			throw Err_.new_("", "failed to retrieve page", "wiki", wiki.Domain_str(), "page_url", page_url.Page_bry(), "err", Err_.Message_lang(e));
		} 
		rv.Init_by_dbpg(ttl, dbpg);

		// load page data
		Xoh_page hpg = new Xoh_page();
		hpg.Ctor_by_hview(wiki, Xoa_url.New(wiki, ttl), ttl, 1);
		rv.Init_by_hpg(hpg);
		wiki.Html__hdump_mgr().Load_mgr().Load_by_xowh(hpg, ttl, Bool_.Y);
		Load_sections(rv, hpg);
		return rv;
	}
	private void Load_sections(Xod_page_itm rv, Xoh_page hpg) {
		Xoh_section_mgr section_mgr = hpg.Section_mgr();
		int len = section_mgr.Len();
		for (int i = 0; i < len; ++i) {
			Xoh_section_itm itm = section_mgr.Get_at(i);
			rv.Section_list().Add(itm);
		}
	}
	private Xod_page_itm Load_special(Xod_page_itm rv, Xow_wiki wiki, Xoa_url url, Xoa_ttl ttl) {
		// get prototype
		gplx.xowa.specials.Xow_special_page proto = wiki.App().Special_regy().Get_by_or_null(ttl.Page_txt_wo_qargs());
		if (proto == null) return rv;	// invalid url

		// generate special
		Xoh_page page = new Xoh_page();
		page.Ctor_by_hview(wiki, Xoa_url.New(wiki, ttl), ttl, 1);	// NOTE: init page to set url, ttl; DATE:2016-06-23
		try {proto.Special__clone().Special__gen(wiki, page, url, ttl);}
		catch (Exception e) {Gfo_log_.Instance.Warn("failed to generate special page", "url", url.To_str(), "err", Err_.Message_gplx_log(e)); return rv;}

		// handle redirects; EX: Special:XowaWikiInfo
		Xopg_redirect_itm redirect_itm = page.Redirect_trail().Itms__get_at_nth_or_null();
		if (redirect_itm != null)
			return Get_page(wiki, redirect_itm.Url());

		rv.Init(-1, -1, String_.new_u8(ttl.Page_txt()), String_.new_u8(ttl.Page_db()), null, null, Datetime_now.Get().XtoStr_fmt_iso_8561(), false, false, false, 0, "", "", "");
		rv.Init_by_hpg(page);
		Xoh_section_itm section = new Xoh_section_itm(1, 1, Bry_.Empty, Bry_.Empty);
		section.Content_(page.Html_data().Custom_body());
		rv.Section_list().Add(section);
		rv.Ttl_special_(String_.new_u8(page.Html_data().Display_ttl()));
		rv.Head_tags().Copy(page.Html_data().Custom_head_tags());
		rv.Tail_tags().Copy(page.Html_data().Custom_tail_tags());
		return rv;
	}
}
