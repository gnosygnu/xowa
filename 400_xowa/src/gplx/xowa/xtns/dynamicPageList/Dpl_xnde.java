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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.amps.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.*;
public class Dpl_xnde implements Xox_xnde {
	private Dpl_itm itm; private Ordered_hash pages;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {} // NOTE: <dynamicPageList> has no attributes
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		itm = Dpl_itm.Parse(wiki, ctx, ctx.Page().Ttl().Full_txt_w_ttl_case(), src, xnde);
		pages = new Dpl_page_finder(itm, wiki).Find();
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		Xowe_wiki wiki = ctx.Wiki();
		Dpl_html_data html_mode = Dpl_html_data.new_(Dpl_itm_keys.Key_unordered);
		int itms_len = pages.Count();
		if (itms_len == 0) {
			if (!itm.Suppress_errors())
				bfr.Add_str_a7("No pages meet these criteria.");
			return;
		}
		int itms_bgn = 0;
		if (itm.Offset() != Int_.Min_value) {
			itms_bgn = itm.Offset();
		}
		if (itm.Count() != Int_.Min_value && itms_bgn + itm.Count() < itms_len) {
			itms_len = itms_bgn + itm.Count();
		}
		boolean show_ns = itm.Show_ns();
		Bry_bfr tmp_bfr = Bry_bfr_.Get();
		Xop_amp_mgr amp_mgr = wiki.Appe().Parser_amp_mgr();
		try {
			bfr.Add(html_mode.Grp_bgn()).Add_byte_nl();
			for (int i = itms_bgn; i < itms_len; i++) {
				Xowd_page_itm page = (Xowd_page_itm)pages.Get_at(i);
				Xoa_ttl ttl = Xoa_ttl.Parse(wiki, page.Ns_id(), page.Ttl_page_db());
				byte[] ttl_page_txt = show_ns ? ttl.Full_txt_w_ttl_case() : ttl.Page_txt();
				if (ttl_page_txt == null) continue;	// NOTE: apparently DynamicPageList allows null pages; DATE:2013-07-22
				switch (html_mode.Tid()) {
					case Dpl_html_data.Tid_list_ul:
					case Dpl_html_data.Tid_list_ol:
						bfr.Add(Xoh_consts.Space_2).Add(html_mode.Itm_bgn()).Add(Gfh_bldr_.Bry__a_lhs_w_href);
						bfr.Add_str_a7("/wiki/").Add(Gfo_url_encoder_.Href.Encode(ttl.Full_db())).Add_byte_quote();	// NOTE: Full_db to encode spaces as underscores; PAGE:en.q:Wikiquote:Speedy_deletions DATE:2016-01-19
						Gfh_atr_.Add(bfr, Gfh_atr_.Bry__title, Xoh_html_wtr_escaper.Escape(amp_mgr, tmp_bfr, ttl.Full_txt_w_ttl_case()));			// NOTE: Full_txt b/c title always includes ns, even if show_ns is off; PAGE:en.b:Wikibooks:WikiProject DATE:2016-01-20
						if (itm.No_follow()) bfr.Add(Bry_nofollow);
						bfr.Add_byte(Byte_ascii.Gt);
						Xoh_html_wtr_escaper.Escape(amp_mgr, bfr, ttl_page_txt, 0, ttl_page_txt.length, false, false);
						bfr.Add(Gfh_bldr_.Bry__a_rhs).Add(html_mode.Itm_end()).Add_byte_nl();
						// TODO_OLD: lnki_wtr.Clear().Href_wiki_(ttl).Title_(ttl).Nofollow_().Write_head(bfr).Write_text(bfr).Write_tail(bfr)
						break;
					default:
						break;
				}
			}
			bfr.Add(html_mode.Grp_end()).Add_byte_nl();
		}
		finally {
			tmp_bfr.Mkr_rls();
			pages.Clear(); // clear pages else out-of-memory error when Next 200 3 times on en.wiktionary.org/wiki/Category:English_lemmas; DATE:2019-08-25
		}
	}
	private static byte[] Bry_nofollow = Bry_.new_a7(" rel=\"nofollow\"");
}
