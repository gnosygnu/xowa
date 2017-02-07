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
import org.junit.*; import gplx.core.brys.*; import gplx.xowa.wikis.pages.skins.*;
import gplx.xowa.htmls.core.htmls.*;
public class Pgbnr_func_tst {
	private final    Pgbnr_func_fxt fxt = new Pgbnr_func_fxt();
	@Test  public void Basic() {			
		fxt.Test__parse(Bool_.N, "{{PAGEBANNER:A.png|icon-star=Star_article}}", String_.Concat_lines_nl_apos_skip_last
		( "<div class='ext-wpb-pagebanner noprint pre-content'>"
		, "	<div class='wpb-topbanner'>"
		, "		<h1 class='wpb-name'>Test page</h1>"
		, "		<a href='' class='image' title='Test page' xowa_title='A.png'><img id='xoimg_0' src='file:///mem/wiki/repo/trg/orig/7/0/A.png' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='0' data-pos-y='0' style='max-width:0px'></a>"
		, "		<div class='wpb-iconbox'>"
		, "				<a href='/wiki/Star_article'><span aria-disabled='false' title='Star article' class='oo-ui-widget oo-ui-widget-enabled oo-ui-iconElement-icon oo-ui-icon-star oo-ui-iconElement oo-ui-iconWidget'></span></a>"
		, "		</div>"
		, "	</div>"
		, "	<div class='wpb-topbanner-toc'><div class='wpb-banner-toc'><div>"
		, "  <div id=\"toctitle\">"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, "</div>"
		, "</div></div>"
		, "</div>"
		));
	}
	@Test  public void Hdump__basic() {
		fxt.Test__parse(Bool_.Y, "{{PAGEBANNER:A.png|icon-star=Star_article}}", String_.Concat_lines_nl_apos_skip_last
		( "<div class='ext-wpb-pagebanner noprint pre-content'>"
		, "	<div class='wpb-topbanner'>"
		, "		<h1 class='wpb-name'>Test page</h1>"
		, "		<a href='/wiki/File:A.png' class='image' title='Test page' xowa_title='A.png'><img data-xowa-title=\"A.png\" data-xoimg=\"1|-1|-1|-1|-1|-1\" src='' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='0' data-pos-y='0' style='max-width:0px'></a>"
		, "		<div class='wpb-iconbox'>"
		, "				<a href='/wiki/Star_article'><span aria-disabled='false' title='Star article' class='oo-ui-widget oo-ui-widget-enabled oo-ui-iconElement-icon oo-ui-icon-star oo-ui-iconElement oo-ui-iconWidget'></span></a>"
		, "		</div>"
		, "	</div>"
		, "	<div class='wpb-topbanner-toc'><div class='wpb-banner-toc'><div class=\"xo-toc\" data-toc-mode=\"1\"></div></div></div>"
		, "</div>"
		));
	}
	@Test  public void Hdump__quote() {	// PAGE:en.v:Europe; DATE:2016-07-12
		fxt.Test__parse(Bool_.Y, "{{PAGEBANNER:A\"b.png|icon-star=Star_article}}", String_.Concat_lines_nl_apos_skip_last
		( "<div class='ext-wpb-pagebanner noprint pre-content'>"
		, "	<div class='wpb-topbanner'>"
		, "		<h1 class='wpb-name'>Test page</h1>"
		, "		<a href='/wiki/File:A%22b.png' class='image' title='Test page' xowa_title='A%22b.png'><img data-xowa-title=\"A%22b.png\" data-xoimg=\"1|-1|-1|-1|-1|-1\" src='' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='0' data-pos-y='0' style='max-width:0px'></a>"
		, "		<div class='wpb-iconbox'>"
		, "				<a href='/wiki/Star_article'><span aria-disabled='false' title='Star article' class='oo-ui-widget oo-ui-widget-enabled oo-ui-iconElement-icon oo-ui-icon-star oo-ui-iconElement oo-ui-iconWidget'></span></a>"
		, "		</div>"
		, "	</div>"
		, "	<div class='wpb-topbanner-toc'><div class='wpb-banner-toc'><div class=\"xo-toc\" data-toc-mode=\"1\"></div></div></div>"
		, "</div>"
		));
	}
}
class Pgbnr_func_fxt {
	private final    Xop_fxt fxt;
	public Pgbnr_func_fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "en.wikivoyage.org");
		fxt = new Xop_fxt(app, wiki);
	}
	public void Test__parse(boolean hdump, String raw, String expd) {
		fxt.Exec_parse_page_all_as_str(raw);
		Xoh_wtr_ctx hctx = hdump ? Xoh_wtr_ctx.Hdump : Xoh_wtr_ctx.Basic;
		Bfr_arg arg = fxt.Wiki().Xtn_mgr().Xtn_pgbnr().Write_html(fxt.Page(), fxt.Ctx(), hctx);
		Bry_bfr bfr = Bry_bfr_.New();
		arg.Bfr_arg__add(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
