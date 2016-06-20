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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_gly_itm_wtr_tst {
	private final    Xoh_gly_itm_wtr_fxt fxt = new Xoh_gly_itm_wtr_fxt();
	@Test   public void Basic() {
		fxt.Init__gly(0, 155, 150, 5, "caption");
		fxt.Init__img("/wiki/File:A.png", "A.png", "0|120|120|-1|-1|-1");
		fxt.Test__write(String_.Concat_lines_nl_skip_last
		( ""
		, "<li id='xogly_li_0' class='gallerybox' style='width:155px;'>"
		,   "<div id='xogly_div1_0' style='width:155px;'>"
		,     "<div id='xogly_div2_0' class='thumb' style='width:150px;'>"
		,       "<div id='xogly_div3_0' style='margin:5px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|120|120|-1|-1|-1' width='0' height='0'></a></div>"
		,     "</div>"
		,     "<div class='gallerytext'>"
		,       "<p>caption</p>"
		,     "</div>"
		,   "</div>"
		, "</li>"
		));
	}
}
class Xoh_gly_itm_wtr_fxt {
	private final    Xoh_gly_itm_wtr wtr = new Xoh_gly_itm_wtr();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public void Init__gly(int id, int itm_w, int file_div_w, int file_div_margin, String caption) {
		wtr.Init(Bool_.N, id, itm_w, file_div_w, file_div_margin, Xoh_gly_itm_data.Capt_tid__p, Bry_.new_a7(caption));
	}
	public void Init__img(String href, String xowa_title, String xoimg) {
		wtr.Img_wtr().Init_by_gly(gplx.core.brys.args.Bfr_arg__bry.New(Bry_.new_u8(href)), Bry_.new_u8(xowa_title), gplx.core.brys.args.Bfr_arg__bry.New(Bry_.new_u8(xoimg)));
	}
	public void Test__write(String expd) {
		expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		wtr.Bfr_arg__add(tmp_bfr);
		Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_clear());
	}
}
