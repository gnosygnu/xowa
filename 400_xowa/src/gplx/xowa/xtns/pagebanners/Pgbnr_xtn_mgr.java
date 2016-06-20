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
			case Xow_domain_tid_.Int__home:
				enabled = true;
				ns_ary = Int_.Ary(Xow_ns_.Tid__main);
				break;
			case Xow_domain_tid_.Int__wikivoyage:
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
			case Xow_domain_tid_.Int__wikipedia:
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
	public Bfr_arg Write_html(Xop_ctx pctx, Xoae_page page, Xoh_wtr_ctx hctx, byte[] src) {
		this.pctx = pctx; this.page = page; this.hctx = hctx; this.src = src;
		return this;
	}	private Xop_ctx pctx; private Xoae_page page; private Xoh_wtr_ctx hctx; private byte[] src;
	public void Bfr_arg__add(Bry_bfr bfr) {
		Pgbnr_itm itm = page.Html_data().Xtn_pgbnr();
		if (itm == null) return;
		Pgbnr_func.Add_banner(bfr, pctx, hctx, src);
	}
	private static final    byte[] Template_dflt = Bry_.New_u8_nl_apos
	( "<div class='ext-wpb-pagebanner noprint pre-content'>"
	, "	<div class='wpb-topbanner'>"
	, "		{{#isHeadingOverrideEnabled}}<h1 class='wpb-name'>{{title}}</h1>{{/isHeadingOverrideEnabled}}"
	, "		<a class='image' title='{{tooltip}}' href='{{bannerfile}}'><img id='{{html_uid}}' src='{{banner}}' srcset='{{srcset}}' class='wpb-banner-image {{originx}}' data-pos-x='{{data-pos-x}}' data-pos-y='{{data-pos-y}}' style='max-width:{{maxWidth}}px'></a>"
	, "		{{#hasIcons}}"
	, "		<div class='wpb-iconbox'>"
	, "			{{#icons}}"
	, "				<a href='{{url}}'>{{{html}}}</a>"
	, "			{{/icons}}"
	, "		</div>"
	, "		{{/hasIcons}}"
	, "	</div>"
	, "	<div class='wpb-topbanner-toc {{#bottomtoc}}wpb-bottomtoc{{/bottomtoc}}'><div class='wpb-banner-toc'>{{{toc}}}</div></div>"
	, "</div>"
	);
}
