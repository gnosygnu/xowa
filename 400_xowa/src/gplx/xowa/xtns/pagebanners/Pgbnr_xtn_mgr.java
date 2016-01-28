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
public class Pgbnr_xtn_mgr extends Xox_mgr_base implements Bfr_arg {
	@Override public byte[] Xtn_key() {return Xtn_key_static;} public static final byte[] Xtn_key_static = Bry_.new_a7("pagebanner");
	@Override public Xox_mgr Clone_new() {return new Pgbnr_xtn_mgr();}
	public Pgbnr_cfg Cfg() {return cfg;} private final Pgbnr_cfg cfg = new Pgbnr_cfg();		
	public Mustache_tkn_itm Template_root() {
		if (template_root == null) {
			Mustache_tkn_parser parser = new Mustache_tkn_parser();
			template_root = parser.Parse(Template_dflt, 0, Template_dflt.length);
		}
		return template_root;
	} private Mustache_tkn_itm template_root;
	@Override public void Xtn_init_by_app(Xoae_app app) {
	}
	public Bfr_arg Write_html(Xop_ctx pctx, Xoae_page page) {
		this.pctx = pctx; this.page = page;
		return this;
	}	private Xop_ctx pctx; private Xoae_page page;
	public void Bfr_arg__add(Bry_bfr bfr) {
		Pgbnr_itm itm = page.Html_data().Xtn__pgbnr();
		if (itm == null) return;
		Pgbnr_func.Add_banner(bfr, pctx);
	}
	private static final byte[] Template_dflt = Bry_.New_u8_nl_apos
	( "<div class='ext-wpb-pagebanner noprint pre-content'>"
	, "	<div class='wpb-topbanner'>"
	, "		{{#isHeadingOverrideEnabled}}<h1 class='wpb-name'>{{title}}</h1>{{/isHeadingOverrideEnabled}}"
	, "		<a class='image' title='{{tooltip}}' href='{{bannerfile}}'><img src='{{banner}}' srcset='{{srcset}}' class='wpb-banner-image {{originx}}' data-pos-x='{{data-pos-x}}' data-pos-y='{{data-pos-y}}' style='max-width:{{maxWidth}}px'></a>"
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
