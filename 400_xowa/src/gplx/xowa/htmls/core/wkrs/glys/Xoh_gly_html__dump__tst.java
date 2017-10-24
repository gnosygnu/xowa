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
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_gly_html__dump__tst {		
	@Before public void init() {fxt.Clear();} private final    Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Test   public void Basic() {
		fxt.Test__html(String_.Concat_lines_nl_skip_last
		( "<gallery>"
		, "File:A.png|A1"
		, "File:B.png|B1"
		, "</gallery>"
		), String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional'>"
		, "  <li class='gallerybox' style='width:155px;'>"
		, "    <div style='width:155px;'>"
		, "      <div class='thumb' style='width:150px;'>"
		, "        <div style='margin:15px auto;'>"
		, "          <a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title=\"A.png\" data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''/></a>"
		, "        </div>"
		, "      </div>"
		, "      <div class='gallerytext'><p>A1"
		, "</p>"
		, ""
		, "      </div>"
		, "    </div>"
		, "  </li>"
		, "  <li class='gallerybox' style='width:155px;'>"
		, "    <div style='width:155px;'>"
		, "      <div class='thumb' style='width:150px;'>"
		, "        <div style='margin:15px auto;'>"
		, "          <a href='/wiki/File:B.png' class='image' xowa_title='B.png'><img data-xowa-title=\"B.png\" data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''/></a>"
		, "        </div>"
		, "      </div>"
		, "      <div class='gallerytext'><p>B1"
		, "</p>"
		, ""
		, "      </div>"
		, "    </div>"
		, "  </li>"
		, "</ul>"));
	}
	@Test   public void Atrs() {
		fxt.Test__html(String_.Concat_lines_nl_skip_last
		( "<gallery perrow='5' widths='200' heights='300'>"
		, "File:A.png|A1"
		, "</gallery>"
		), String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='200|300|5' class='gallery mw-gallery-traditional' style='max-width:1215px; _width:1215px;'>"
		, "  <li class='gallerybox' style='width:235px;'>"
		, "    <div style='width:235px;'>"
		, "      <div class='thumb' style='width:230px;'>"
		, "        <div style='margin:15px auto;'>"
		, "          <a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title=\"A.png\" data-xoimg='0|200|300|-1|-1|-1' src='' width='0' height='0' alt=''/></a>"
		, "        </div>"
		, "      </div>"
		, "      <div class='gallerytext'><p>A1"
		, "</p>"
		, ""
		, "      </div>"
		, "    </div>"
		, "  </li>"
		, "</ul>"));
	}
	@Test   public void Packed() {
		fxt.Test__html(String_.Concat_lines_nl_skip_last
		( "<gallery mode='packed'>"
		, "File:A.png|A1"
		, "</gallery>"
		), String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-packed'>"
		, "  <li class='gallerybox' style='width:1302px;'>"
		, "    <div style='width:1302px;'>"
		, "      <div class='thumb' style='width:1300px;'>"
		, "        <div style='margin:0px auto;'>"
		, "          <a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title=\"A.png\" data-xoimg='0|1950|180|-1|-1|-1' src='' width='0' height='0' alt=''/></a>"
		, "        </div>"
		, "      </div>"
		, "      <div class='gallerytext'><p>A1"
		, "</p>"
		, ""
		, "      </div>"
		, "    </div>"
		, "  </li>"
		, "</ul>"));
	}
//					case Gallery_xnde_atrs.Mode_tid:			mode = Gallery_mgr_base_.Get_or_traditional(xatr.Val_as_bry()); break;
//					case Gallery_xnde_atrs.Perrow_tid:			itms_per_row = xatr.Val_as_int_or(Null); break;
//					case Gallery_xnde_atrs.Widths_tid:			itm_w = xatr.Val_as_int_or(Null); break;
//					case Gallery_xnde_atrs.Heights_tid:			itm_h = xatr.Val_as_int_or(Null); break;
}
