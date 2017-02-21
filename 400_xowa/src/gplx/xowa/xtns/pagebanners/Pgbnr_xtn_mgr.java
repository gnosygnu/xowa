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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.*; import gplx.langs.mustaches.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.htmls.core.htmls.*;
public class Pgbnr_xtn_mgr extends Xox_mgr_base implements Bfr_arg {
	@Override public byte[] Xtn_key() {return Xtn_key_static;} public static final    byte[] Xtn_key_static = Bry_.new_a7("pagebanner");
	@Override public Xox_mgr Xtn_clone_new() {return new Pgbnr_xtn_mgr();}
	public Pgbnr_cfg Cfg() {return cfg;} private Pgbnr_cfg cfg;
	public Mustache_tkn_itm Template_root() {return template_root;} private Mustache_tkn_itm template_root;
	@Override public void Xtn_init_by_app(Xoae_app app) {}
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		// load config; TODO_OLD: load by file
		boolean enabled = false, enable_heading_override = true, enable_default_banner = false;
		int[] ns_ary = Int_.Ary(Xow_ns_.Tid__main, Xow_ns_.Tid__user);
		int[] standard_sizes = new int[] {640, 1280, 2560};
		int dflt_img_wdata_prop = 948; byte[] dflt_img_title = Bry_.new_a7("Pagebanner_default.jpg");	// www.wikidata.org/wiki/Property:P948
		switch (wiki.Domain_tid()) {
			case Xow_domain_tid_.Tid__home:
				enabled = true;
				ns_ary = Int_.Ary(Xow_ns_.Tid__main);
				break;
			case Xow_domain_tid_.Tid__wikivoyage:
				switch (wiki.Lang().Lang_id()) {
					case Xol_lang_stub_.Id_en:
					case Xol_lang_stub_.Id_fr:
					case Xol_lang_stub_.Id_zh:
						enabled = true;
						break;
					case Xol_lang_stub_.Id_ru:
						ns_ary = Int_.Ary(0, 1, 10, 11, 12, 13, 14, 15, 2, 2300, 2301, 2302, 2303, 2600, 3, 4, 5, 6, 7, 8, 828, 829, 9);
						enabled = true;
						break;
					case Xol_lang_stub_.Id_uk:
						ns_ary = Int_.Ary(Xow_ns_.Tid__main, Xow_ns_.Tid__user, Xow_ns_.Tid__project);
						enabled = true;
						break;
				}
				break;
			case Xow_domain_tid_.Tid__wikipedia:
				switch (wiki.Lang().Lang_id()) {
					case Xol_lang_stub_.Id_ca:
						// enabled = enable_default_banner = true;
						ns_ary = Int_.Ary(102, Xow_ns_.Tid__user);
						break;
					 case Xol_lang_stub_.Id_en:
						// enabled = enable_default_banner = true;
						enable_heading_override = false;
						break;
				}
				break;
		}
		cfg = new Pgbnr_cfg(enabled, enable_heading_override, enable_default_banner, ns_ary, dflt_img_wdata_prop, dflt_img_title, standard_sizes);

		if (!enabled) return;
		// load template file;
		byte[] template_data = Io_mgr.Instance.LoadFilBryOr(wiki.Appe().Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "xtns", "WikidataPageBanner", "templates", "banner.mustache"), Template_dflt);
		Mustache_tkn_parser parser = new Mustache_tkn_parser();
		template_root = parser.Parse(template_data, 0, template_data.length);
	}
	public Bfr_arg Write_html(Xoae_page wpg, Xop_ctx pctx, Xoh_wtr_ctx hctx) {
		this.wpg = wpg; this.pctx = pctx; this.hctx = hctx;
		return this;
	}	private Xoae_page wpg; private Xop_ctx pctx; private Xoh_wtr_ctx hctx;
	public void Bfr_arg__add(Bry_bfr bfr) {
		Pgbnr_itm itm = wpg.Html_data().Xtn_pgbnr(); if (itm == null) return;
		Pgbnr_func.Add_banner(bfr, wpg, pctx, hctx, itm);
	}

	public static final    byte[] Bry__cls__wpb_banner_image = Bry_.new_a7("wpb-banner-image");
	private static final    byte[] Template_dflt = Bry_.New_u8_nl_apos
	( "<div class='ext-wpb-pagebanner noprint pre-content'>"
	, "	<div class='wpb-topbanner'>"
	, "		{{#isHeadingOverrideEnabled}}<h1 class='wpb-name'>{{title}}</h1>{{/isHeadingOverrideEnabled}}"
	, "		<a href='{{bannerfile}}' class='image' title='{{tooltip}}' xowa_title='{{file_ttl}}'><img{{{img_id_atr}}}{{{img_xottl}}}{{{img_xoimg}}} src='{{banner}}' width='0' height='0' class='wpb-banner-image {{originx}}' alt='' srcset='{{srcset}}' data-pos-x='{{data-pos-x}}' data-pos-y='{{data-pos-y}}' style='max-width:{{maxWidth}}px'></a>"
	, "		{{#hasIcons}}"
	, "		<div class='wpb-iconbox'>"
	, "			{{#icons}}"
	, "				<a href='{{url}}'>{{{html}}}</a>"
	, "			{{/icons}}"
	, "		</div>"
	, "		{{/hasIcons}}"
	, "	</div>"
	, "	<div class='wpb-topbanner-toc{{#bottomtoc}} wpb-bottomtoc{{/bottomtoc}}'><div class='wpb-banner-toc'>{{{toc}}}</div></div>"
	, "</div>"
	);
}
