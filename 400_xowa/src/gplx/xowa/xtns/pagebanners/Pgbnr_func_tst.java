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
public class Pgbnr_func_tst {
	private final Pgbnr_func_fxt fxt = new Pgbnr_func_fxt();
	@Test  public void Basic() {			
		fxt.Test__parse__eq("{{PAGEBANNER:A.png|icon-star=Star_article}}", String_.Concat_lines_nl_apos_skip_last
		( "<div class='ext-wpb-pagebanner noprint pre-content'>"
		, "	<div class='wpb-topbanner'>"
		, "		<h1 class='wpb-name'>Test page</h1>"
		, "		<a class='image' title='Test page' href=''><img id='xoimg_0' src='file:///mem/wiki/repo/trg/orig/7/0/A.png' srcset='' class='wpb-banner-image ' data-pos-x='0' data-pos-y='0' style='max-width:0px'></a>"
		, "		<div class='wpb-iconbox'>"
		, "				<a href='/wiki/Star_article'><span aria-disabled='false' title='Star article' class='oo-ui-widget oo-ui-widget-enabled oo-ui-iconElement-icon oo-ui-icon-star oo-ui-iconElement oo-ui-iconWidget'></span></a>"
		, "		</div>"
		, "	</div>"
		, "	<div class='wpb-topbanner-toc '><div class='wpb-banner-toc'></div></div>"
		, "</div>"
		));
	}
}
class Pgbnr_func_fxt {
	private final Xop_fxt fxt;
	public Pgbnr_func_fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "en.wikivoyage.org");
		fxt = new Xop_fxt(app, wiki);
	}
	public void Test__parse__eq(String raw, String expd) {
		fxt.Exec_parse_page_all_as_str(raw);
		Bfr_arg arg = fxt.Wiki().Xtn_mgr().Xtn_pgbnr().Write_html(fxt.Ctx(), fxt.Page(), null, null);
		Bry_bfr bfr = Bry_bfr.new_();
		arg.Bfr_arg__add(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
