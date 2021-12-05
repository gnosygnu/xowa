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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_fmt;
import gplx.core.intls.ucas.Uca_ltr_extractor;
import gplx.langs.htmls.encoders.Gfo_url_encoder_;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xow_wiki;
import gplx.xowa.addons.wikis.ctgs.Xoa_ctg_mgr;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.Xoctg_catpage_ctg;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.Xoctg_catpage_grp;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.Xoctg_catpage_url_parser;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.msgs.Xol_msg_itm_;
import gplx.xowa.langs.msgs.Xow_msg_mgr;
import gplx.xowa.wikis.nss.Xow_ns_;
public class Xoctg_fmt_grp {	// subc|page|file
	private final byte tid;
	private final byte[] div_id, url_arg_bgn, url_arg_end;
	private final byte[] msg_label_key, msg_stats_key;
	private final Xoctg_fmt_ltr itms_fmt;
	Xoctg_fmt_grp(byte tid, Xoctg_fmt_itm_base itm_fmt, byte[] msg_label_key, byte[] msg_stats_key, byte[] url_arg_bgn, byte[] url_arg_end, byte[] div_id) {
		this.tid = tid;
		this.itm_fmt = itm_fmt;
		this.itms_fmt = new Xoctg_fmt_ltr(itm_fmt);
		this.msg_label_key = msg_label_key; this.msg_stats_key = msg_stats_key;
		this.url_arg_bgn = url_arg_bgn; this.url_arg_end = url_arg_end; this.div_id = div_id;
	}
	public Xoctg_fmt_itm_base Itm_fmt() {return itm_fmt;} private final Xoctg_fmt_itm_base itm_fmt;
	public void Write_catpage_grp(Bry_bfr bfr, Xow_wiki wiki, Xol_lang_itm lang, Uca_ltr_extractor ltr_extractor, Xoctg_catpage_ctg dom_ctg, int grp_max) {	// TEST:
		Xoctg_catpage_grp dom_grp = dom_ctg.Grp_by_tid(tid);
		int count = dom_grp.Count_all();
		if (count == 0) return;	// no items in grp; EX: 0 items in File

		// get msgs
		Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		byte[] msg_label_bry = msg_mgr.Val_by_key_args(msg_label_key, dom_ctg.Name());
		byte[] msg_stats_bry = msg_mgr.Val_by_key_args(msg_stats_key, dom_grp.Itms__len(), lang.Num_mgr().Format_num(count));

		// get nav html; next / previous 200
		Xoa_ttl ctg_ttl = wiki.Ttl_parse(Xow_ns_.Tid__category, dom_ctg.Name());
		byte[] nav_html = this.Bld_bwd_fwd(wiki, ctg_ttl, dom_grp, grp_max);

		// according to mediawiki/includes/CategoryViewer.php cutoff default is 6
		byte[] startdiv = Bry_.Empty, enddiv = Bry_.Empty;
//			if (count > 6) {
//				startdiv = Bry_.new_a7("<div class=\"mw-category\">");
//				enddiv = Bry_.new_a7("</div>");
//			}
		// init grp; write
		itms_fmt.Init_from_grp(wiki, dom_grp, ltr_extractor);
		Fmt__ctg.Bld_many(bfr, div_id, msg_label_bry, msg_stats_bry, nav_html, lang.Key_bry(), lang.Dir_ltr_bry(), startdiv, itms_fmt, enddiv);
	}
	public byte[] Bld_bwd_fwd(Xow_wiki wiki, Xoa_ttl ttl, Xoctg_catpage_grp view_grp, int grp_max) {	// TEST:
		if (view_grp.Count_all() < grp_max) return Bry_.Empty;	// NOTE: must be "<", not "<="; FOOTNOTE:LT_NOT_LTE; DATE:2019-12-14
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		Html_nav_bry(bfr, wiki, ttl, view_grp, grp_max, BoolUtl.N);
		Html_nav_bry(bfr, wiki, ttl, view_grp, grp_max, BoolUtl.Y);
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
		href_bfr.Add_byte(AsciiByte.Question).Add(arg_idx_lbl).Add_byte(AsciiByte.Eq);		// filefrom=
		Gfo_url_encoder_.Http_url.Encode(href_bfr, arg_sortkey);								// Abc
		href_bfr.Add_byte(AsciiByte.Hash).Add(div_id);											// #mw-subcategories
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
	private static final Bry_fmt
	  Fmt__nav__href = Bry_fmt.New("\n(<a href=\"~{nav_href}\" class=\"xowa_nav\" title=\"~{nav_title}\">~{nav_text}</a>)")
	, Fmt__nav__text = Bry_fmt.New("\n(~{nav_text})")
	, Fmt__ctg = Bry_fmt.Auto_nl_skip_last
	( ""
	, "<div id=\"~{div_id}\">"
	, "  <h2>~{msg_label_bry}</h2>"
	, "  <p>~{msg_stats_bry}</p>~{nav_html}"
	, "  <div lang=\"~{lang_key}\" dir=\"~{lang_ltr}\" class=\"mw-content-~{lang_ltr}\">"
	, "    ~{startdiv}<table style=\"width: 100%;\">"
	, "      <tr style=\"vertical-align: top;\">~{grps}"
	, "      </tr>"
	, "    </table>~{enddiv}"
	, "  </div>~{nav_html}"
	, "</div>"
	);
	public static Xoctg_fmt_grp New__subc() {return new Xoctg_fmt_grp(Xoa_ctg_mgr.Tid__subc, new Xoctg_fmt_itm_subc(), Ctg_subc_header, Ctg_subc_count, Xoctg_catpage_url_parser.Bry__arg_subc_bgn, Xoctg_catpage_url_parser.Bry__arg_subc_end, Mw_subcategories);}
	public static Xoctg_fmt_grp New__page() {return new Xoctg_fmt_grp(Xoa_ctg_mgr.Tid__page, new Xoctg_fmt_itm_page(), Ctg_page_header, Ctg_page_count, Xoctg_catpage_url_parser.Bry__arg_page_bgn, Xoctg_catpage_url_parser.Bry__arg_page_end, Mw_pages);}
	public static Xoctg_fmt_grp New__file() {return new Xoctg_fmt_grp(Xoa_ctg_mgr.Tid__file, new Xoctg_fmt_itm_file(), Ctg_file_header, Ctg_file_count, Xoctg_catpage_url_parser.Bry__arg_file_bgn, Xoctg_catpage_url_parser.Bry__arg_file_end, Mw_category_media);}
	private static byte[]
	  Ctg_subc_header = Bry_.new_a7("subcategories")
	, Ctg_subc_count = Bry_.new_a7("category-subcat-count")
	, Mw_subcategories = Bry_.new_a7("mw-subcategories")
	, Ctg_page_header = Bry_.new_a7("category_header")
	, Ctg_page_count = Bry_.new_a7("category-article-count")
	, Mw_pages = Bry_.new_a7("mw-pages")
	, Ctg_file_header = Bry_.new_a7("category-media-header")
	, Ctg_file_count = Bry_.new_a7("category-file-count")
	, Mw_category_media = Bry_.new_a7("mw-category-media")
	;
}
/*
== LT_NOT_LTE ==
DATE:2019-12-14

Must be <, not <=.

* Intuitively, it seems like it should be <=. For example, 200 <= 200
* However, when there are exactly 200 categories, MediaWiki shows "(previous 200) (next 200)" headers but with no links
* If changed to <=, then the "(previous 200) (next 200)" disappears. This has no meaningful effect, but might as well imitate MW

Tested with following
* en.wikipedia.org/wiki/Category:1603_births
* Also, SQL to find other categories
<pre>
  ATTACH 'en.wikipedia.org-core.xowa' AS page_db;
  SELECT c.cat_id, c.cat_pages, p.page_title FROM cat_core c JOIN page_db.page p ON c.cat_id = p.page_id WHERE c.cat_pages = 200 LIMIT 10;
</pre>
*/
