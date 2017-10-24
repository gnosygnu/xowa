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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.core.primitives.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.xtns.gallery.*;
public class Xoh_gly_itm_wtr_tst {
	private final    Xoh_gly_itm_wtr_fxt fxt = new Xoh_gly_itm_wtr_fxt();
	@Test   public void Basic() {
		fxt.Init__gly(Gallery_mgr_base_.Tid__traditional, -1, -1, -1, 0, 155, 150, 5, "caption");
		fxt.Init__img("/wiki/File:A.png", "A.png", "0|120|120|-1|-1|-1");
		fxt.Test__write(String_.Concat_lines_nl_skip_last
		( ""
		, "<li id='xogly_li_0' class='gallerybox' style='width:155px;'>"
		,   "<div id='xowa_gallery_div1_0' style='width:155px;'>"
		,     "<div id='xowa_gallery_div2_0' class='thumb' style='width:150px;'>"
		,       "<div id='xowa_gallery_div3_0' style='margin:5px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|120|120|-1|-1|-1' width='0' height='0'></a></div>"
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
	public void Init__gly(byte mode, int xnde_w, int xnde_h, int xnde_per_row, int id, int itm_w, int div_1_w, int div_3_margin, String caption) {
		wtr.Init(Bool_.N, mode, xnde_w, xnde_h, xnde_per_row, id, 0, 0, itm_w, div_1_w, div_3_margin, Xoh_gly_itm_data.Capt_tid__p, Bry_.new_a7(caption));
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
