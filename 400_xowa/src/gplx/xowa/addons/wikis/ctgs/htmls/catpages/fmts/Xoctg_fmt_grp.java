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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.htmls.core.htmls.*; import gplx.langs.htmls.encoders.*; import gplx.core.intls.ucas.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.*;
public class Xoctg_fmt_grp {	// subc|page|file
	private final    byte tid;
	private final    byte[] div_id, url_arg_bgn, url_arg_end;
	private final    int msg_label_id, msg_stats_id;
	private final    Xoctg_fmt_ltr itms_fmt;
	Xoctg_fmt_grp(byte tid, Xoctg_fmt_itm_base itm_fmt, int msg_label_id, int msg_stats_id, byte[] url_arg_bgn, byte[] url_arg_end, byte[] div_id) {
		this.tid = tid;
		this.itm_fmt = itm_fmt;
		this.itms_fmt = new Xoctg_fmt_ltr(itm_fmt);
		this.msg_label_id = msg_label_id; this.msg_stats_id = msg_stats_id;
		this.url_arg_bgn = url_arg_bgn; this.url_arg_end = url_arg_end; this.div_id = div_id;
	}
	public Xoctg_fmt_itm_base Itm_fmt() {return itm_fmt;} private final    Xoctg_fmt_itm_base itm_fmt;
	public void Write_catpage_grp(Bry_bfr bfr, Xow_wiki wiki, Xol_lang_itm lang, Uca_ltr_extractor ltr_extractor, Xoctg_catpage_ctg dom_ctg, int grp_max) {	// TEST:
		Xoctg_catpage_grp dom_grp = dom_ctg.Grp_by_tid(tid);
		if (dom_grp.Count_all() == 0) return;	// no items in grp; EX: 0 items in File

		// get msgs
		Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		byte[] msg_label_bry = msg_mgr.Val_by_id_args(msg_label_id, dom_ctg.Name());
		byte[] msg_stats_bry = msg_mgr.Val_by_id_args(msg_stats_id, dom_grp.Itms__len(), dom_grp.Count_all());

		// get nav html; next / previous 200
		Xoa_ttl ctg_ttl = wiki.Ttl_parse(Xow_ns_.Tid__category, dom_ctg.Name());
		byte[] nav_html = this.Bld_bwd_fwd(wiki, ctg_ttl, dom_grp, grp_max);

		// init grp; write
		itms_fmt.Init_from_grp(wiki, dom_grp, ltr_extractor);
		Fmt__ctg.Bld_many(bfr, div_id, msg_label_bry, msg_stats_bry, nav_html, lang.Key_bry(), lang.Dir_ltr_bry(), itms_fmt);
	}
	public byte[] Bld_bwd_fwd(Xow_wiki wiki, Xoa_ttl ttl, Xoctg_catpage_grp view_grp, int grp_max) {	// TEST:
		if (view_grp.Count_all() < grp_max) return Bry_.Empty;	// < 200; never show;
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		Html_nav_bry(bfr, wiki, ttl, view_grp, grp_max, Bool_.N);
		Html_nav_bry(bfr, wiki, ttl, view_grp, grp_max, Bool_.Y);
		return bfr.To_bry_and_rls();
	}
	private void Html_nav_bry(Bry_bfr bfr, Xow_wiki wiki, Xoa_ttl ttl, Xoctg_catpage_grp grp, int grp_max, boolean url_is_from) {
		Bry_bfr href_bfr = wiki.Utl__bfr_mkr().Get_b512();

		// get nav_href; EX:href="/wiki/Category:Ctg_1?pageuntil=A1#mw-pages"
		wiki.Html__href_wtr().Build_to_bfr(href_bfr, wiki.App(), Xoh_wtr_ctx.Basic, wiki.Domain_bry(), ttl);
		byte[] arg_idx_lbl = null; byte[] arg_sortkey = null;
		if (url_is_from) {
			arg_idx_lbl = url_arg_bgn;
			arg_sortkey = grp.Next_sortkey();
		}
		else {
			arg_idx_lbl = url_arg_end;
			arg_sortkey = grp.Itms__get_at(0).Sortkey_handle();	// use 1st item as sortkey for "until" args
		}
		href_bfr.Add_byte(Byte_ascii.Question).Add(arg_idx_lbl).Add_byte(Byte_ascii.Eq);		// filefrom=
		Gfo_url_encoder_.Http_url.Encode(href_bfr, arg_sortkey);								// Abc
		href_bfr.Add_byte(Byte_ascii.Hash).Add(div_id);											// #mw-subcategories
		byte[] nav_href = href_bfr.To_bry_and_rls();

		// get nav_text
		int nav_text_id = url_is_from ? Xol_msg_itm_.Id_next_results : Xol_msg_itm_.Id_prev_results;
		byte[] nav_text = wiki.Msg_mgr().Val_by_id_args(nav_text_id, grp_max);					// next 200 / previous 200

		// print text if 1st / zth page; else, print html
		if (	( url_is_from && Bry_.Len_eq_0(grp.Next_sortkey()))
			||	(!url_is_from && grp.Prev_disable())
			)
			Fmt__nav__text.Bld_many(bfr, nav_text);
		else
			Fmt__nav__href.Bld_many(bfr, nav_href, ttl.Full_url(), nav_text);
	}
	private static final    Bry_fmt 
	  Fmt__nav__href = Bry_fmt.New("\n(<a href=\"~{nav_href}\" class=\"xowa_nav\" title=\"~{nav_title}\">~{nav_text}</a>)")
	, Fmt__nav__text = Bry_fmt.New("\n(~{nav_text})")
	, Fmt__ctg = Bry_fmt.Auto_nl_skip_last
	( ""
	, "<div id=\"~{div_id}\">"
	, "  <h2>~{msg_label_bry}</h2>"
	, "  <p>~{msg_stats_bry}</p>~{nav_html}"
	, "  <div lang=\"~{lang_key}\" dir=\"~{lang_ltr}\" class=\"mw-content-~{lang_ltr}\">"
	, "    <table style=\"width: 100%;\">"
	, "      <tr style=\"vertical-align: top;\">~{grps}"
	, "      </tr>"
	, "    </table>"
	, "  </div>~{nav_html}"
	, "</div>"
	);
	public static Xoctg_fmt_grp New__subc() {return new Xoctg_fmt_grp(Xoa_ctg_mgr.Tid__subc, new Xoctg_fmt_itm_subc(), Xol_msg_itm_.Id_ctg_subc_header, Xol_msg_itm_.Id_ctg_subc_count, Xoctg_catpage_url_parser.Bry__arg_subc_bgn, Xoctg_catpage_url_parser.Bry__arg_subc_end, Bry_.new_a7("mw-subcategories"));}
	public static Xoctg_fmt_grp New__page() {return new Xoctg_fmt_grp(Xoa_ctg_mgr.Tid__page, new Xoctg_fmt_itm_page(), Xol_msg_itm_.Id_ctg_page_header, Xol_msg_itm_.Id_ctg_page_count, Xoctg_catpage_url_parser.Bry__arg_page_bgn, Xoctg_catpage_url_parser.Bry__arg_page_end, Bry_.new_a7("mw-pages"));}
	public static Xoctg_fmt_grp New__file() {return new Xoctg_fmt_grp(Xoa_ctg_mgr.Tid__file, new Xoctg_fmt_itm_file(), Xol_msg_itm_.Id_ctg_file_header, Xol_msg_itm_.Id_ctg_file_count, Xoctg_catpage_url_parser.Bry__arg_file_bgn, Xoctg_catpage_url_parser.Bry__arg_file_end, Bry_.new_a7("mw-category-media"));}
}
