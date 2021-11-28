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
package gplx.xowa.xtns.pagebanners;

import gplx.Bool_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.String_;
import gplx.Tfds;
import gplx.core.brys.Bfr_arg;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xop_fxt;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import org.junit.Before;
import org.junit.Test;

public class Pgbnr_func_tst {
	private final Pgbnr_func_fxt fxt = new Pgbnr_func_fxt();
	@Before public void init() {fxt.Clear();}
	@Test public void Basic() {
		fxt.Init__orig(true, "A.png", 500, 200); // 500 > 200 * 2 for pageBanner;
		fxt.Test__parse(Bool_.N, "{{PAGEBANNER:A.png|icon-star=Star_article}}", String_.Concat_lines_nl_apos_skip_last
		( "<div class='ext-wpb-pagebanner pre-content'>"
		, " <div class='wpb-banner-image-panorama wpb-topbanner'>"
		, "		<h1 class='wpb-name'>Test page</h1>"
		, "		<a href='' class='image' title='Test page' xowa_title='A.png'><img id='xoimg_0' src='file:///mem/wiki/repo/src/orig/7/0/A.png' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='0' data-pos-y='0' style='max-width:500px'></a>"
		, "		<div class='wpb-iconbox'>"
		, "				<a href='/wiki/Star_article'><span aria-disabled='false' title='Star article' class='oo-ui-widget oo-ui-widget-enabled oo-ui-iconElement-icon oo-ui-icon-star oo-ui-iconElement oo-ui-iconWidget'></span></a>"
		, "		</div>"
		, "	</div>"
		, "	<div class='wpb-topbanner-toc'><div class='wpb-banner-toc'><div>"
		, "  <div id=\"toctitle\" class=\"toctitle\">"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, "</div>"
		, "</div></div>"
		, "</div>"
		));
	}
	@Test public void Hdump__basic() {
		fxt.Test__parse(Bool_.Y, "{{PAGEBANNER:A.png|icon-star=Star_article}}", String_.Concat_lines_nl_apos_skip_last
		( "<div class='ext-wpb-pagebanner pre-content'>"
		, " <div class='wpb-topbanner'>"
		, "		<h1 class='wpb-name'>Test page</h1>"
		, "		<a href='/wiki/File:A.png' class='image' title='Test page' xowa_title='A.png'>"
		+         "<img data-xowa-title=\"A.png\" data-xoimg=\"1|-1|-1|-1|-1|-1\" src='' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='0' data-pos-y='0' style='max-width:0px'></a>"
		, "		<div class='wpb-iconbox'>"
		, "				<a href='/wiki/Star_article'><span aria-disabled='false' title='Star article' class='oo-ui-widget oo-ui-widget-enabled oo-ui-iconElement-icon oo-ui-icon-star oo-ui-iconElement oo-ui-iconWidget'></span></a>"
		, "		</div>"
		, "	</div>"
		, "	<div class='wpb-topbanner-toc'><div class='wpb-banner-toc'><div class=\"xo-toc\" data-toc-mode=\"1\"></div></div></div>"
		, "</div>"
		));
	}
	@Test public void Hdump__quote() {	// PAGE:en.v:Europe; DATE:2016-07-12
		fxt.Test__parse(Bool_.Y, "{{PAGEBANNER:A\"b.png|icon-star=Star_article}}", String_.Concat_lines_nl_apos_skip_last
		( "<div class='ext-wpb-pagebanner pre-content'>"
		, " <div class='wpb-topbanner'>"
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
	private Xop_fxt fxt;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "en.wikivoyage.org");
		fxt = new Xop_fxt(app, wiki);
		fxt.Init__file_find_mgr();
	}
	public void Init__orig(boolean wiki_is_commons, String orig_ttl, int orig_w, int orig_h) {
		fxt.Init__orig(wiki_is_commons, orig_ttl, orig_w, orig_h);
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
