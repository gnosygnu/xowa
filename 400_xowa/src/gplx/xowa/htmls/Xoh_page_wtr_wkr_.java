/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls;

import gplx.Bool_;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_find_;
import gplx.Gfo_usr_dlg_;
import gplx.langs.htmls.Gfh_tag_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.msgs.Xow_msg_mgr;
import gplx.xowa.parsers.utils.Xop_redirect_mgr;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.wikis.pages.tags.Xopg_tag_wtr;
import gplx.xowa.wikis.pages.tags.Xopg_tag_wtr_cbk_;
public class Xoh_page_wtr_wkr_ {
	public static byte[] Bld_page_content_sub(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Bry_bfr tmp_bfr) {
		byte[] subpages = app.Html_mgr().Page_mgr().Subpages_bldr().Bld(wiki, page.Ttl());
		byte[] page_content_sub = page.Html_data().Content_sub();		// contentSub exists; SEE: {{#isin}}
		byte[] redirect_msg = Xop_redirect_mgr.Bld_redirect_msg(app, wiki, page.Redirect_trail());
		return Bry_.Add(subpages, page_content_sub, redirect_msg);
	}
	public static byte[] BuildPagenameForTab(Bry_bfr tmp, Xow_msg_mgr msgMgr, Xoa_ttl ttl, byte[] mainPageTtl) {
		byte[] pagename = BuildPagename(tmp, ttl);

		// default to sitewide pagetitle; ISSUE#:715; DATE:2020-05-01
		byte[] msgKey = Bry_.new_a7("pagetitle"); // $1 – Travel guide at {{SITENAME}}

		// if MAIN_PAGE, use the pageTitle for MAIN_PAGE; may not need Replace_unders depending on whether "mainPageTtl" is "MAIN PAGE"
		if (Bry_.Eq(Xoa_ttl.Replace_unders(ttl.Raw()), Xoa_ttl.Replace_unders(mainPageTtl)))
			msgKey = Bry_.new_a7("pagetitle-view-mainpage"); // {{SITENAME}} – The free worldwide travel guide that you can edit

		// do the actual swap
		return msgMgr.Val_by_key_args(msgKey, pagename);
	}
	public static byte[] BuildPagenameForH1(Bry_bfr tmp, Xoa_ttl ttl, byte[] displayTtl) {
		// display_ttl explicitly set; use it
		return Bry_.Len_gt_0(displayTtl)
			? displayTtl
			: BuildPagename(tmp, ttl);
	}
	private static byte[] BuildPagename(Bry_bfr tmp, Xoa_ttl ttl) {
		// SPECIAL page: omit query args, else excessively long titles: EX:"Special:Search/earth?fulltext=y&xowa page index=1"
		if (ttl.Ns().Id() == Xow_ns_.Tid__special) {
			tmp.Add(ttl.Ns().Name_ui_w_colon()).Add(ttl.Page_txt_wo_qargs());
			return tmp.To_bry_and_clear();
		}
		else {
			// NOTE: include ns with ttl as per defect d88a87b3
			return ttl.Full_txt();
		}
	}
	public static void Bld_head_end(Bry_bfr html_bfr, Bry_bfr tmp_bfr, Xoae_page page) {
		byte[] head_end = Xopg_tag_wtr.Write(tmp_bfr, Bool_.Y, Xopg_tag_wtr_cbk_.Basic, page.Html_data().Custom_head_tags());
		if (Bry_.Len_eq_0(head_end)) return;
		int insert_pos = Bry_find_.Find_fwd(html_bfr.Bfr(), Gfh_tag_.Head_rhs);
		if (insert_pos == Bry_find_.Not_found) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "could not find </head>");
			return;
		}
		html_bfr.Insert_at(insert_pos, head_end);
	}
	public static void Bld_html_end(Bry_bfr html_bfr, Bry_bfr tmp_bfr, Xoae_page page) {
		byte[] html_end = Xopg_tag_wtr.Write(tmp_bfr, Bool_.Y, Xopg_tag_wtr_cbk_.Basic, page.Html_data().Custom_tail_tags());
		if (html_end == null) return;
		int insert_pos = Bry_find_.Find_bwd(html_bfr.Bfr(), Gfh_tag_.Html_rhs, html_bfr.Len());
		if (insert_pos == Bry_find_.Not_found) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "could not find </html>");
			return;
		}
		html_bfr.Insert_at(insert_pos, html_end);
	}
}
