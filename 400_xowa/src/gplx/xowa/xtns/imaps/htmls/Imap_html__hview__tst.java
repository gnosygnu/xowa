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
package gplx.xowa.xtns.imaps.htmls;
import gplx.String_;
import gplx.objects.primitives.BoolUtl;
import org.junit.Test;
public class Imap_html__hview__tst {
	private final Imap_html__fxt fxt = new Imap_html__fxt();
	@Test public void Basic() {
		String wtxt = fxt.Basic__wtxt();
		fxt.Test__hview(wtxt, fxt.Hdump_n_().Basic__html(BoolUtl.Y));
		fxt.Test__hdump(wtxt, fxt.Hdump_y_().Basic__html(BoolUtl.N), fxt.Basic__html(BoolUtl.Y), fxt.Basic__fsdb());
	}
	@Test public void Caption_xml() {	// PURPOSE: xnde in caption was being escaped; PAGE:en.w:Council_of_Europe; DATE:2014-07-25
		fxt.Test_html_full_frag("<imagemap>File:A.png|thumb|<b>c</b>\n</imagemap>", "<b>c</b>");
	}
	@Test public void Default() {
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( "<imagemap>"
		, "File:A.png|thumb|123px|a1"
		, "default [[B|b1]]"
		, "</imagemap>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"	// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		, "    <div id=\"imap_div_0\" class=\"noresize\">"
		, "      <map name=\"imageMap_1_1\">"
		, "      </map>"
		, "      <a href=\"/wiki/B\" title=\"b1\">"
		, "        <img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png\" width=\"123\" height=\"0\" usemap=\"#imageMap_1_1\"/>"
		, "      </a>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>a1"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test public void Desc() {
		String wtxt = fxt.Desc__wtxt();
		fxt.Test__hview(wtxt, fxt.Hdump_n_().Desc__html(BoolUtl.Y));
		fxt.Test__hdump(wtxt, fxt.Hdump_y_().Desc__html(BoolUtl.N), fxt.Desc__html(BoolUtl.Y), fxt.Basic__fsdb());
	}
	@Test public void Lnke() {	// PURPOSE: handle shapes with lnke; PAGE:en.w:Cholesterolt DATE:2014-07-25
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( "<imagemap>"
		, "File:A.png|thumb|123px|a1"
		, "circle 0 0 5 [[http://b.org b1]]"
		, "desc none"
		, "</imagemap>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"	// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		, "    <div id=\"imap_div_0\" class=\"noresize\">"
		, "      <map name=\"imageMap_1_1\">"
		, "        <area href=\"http://b.org\" shape=\"circle\" coords=\"0,0,5\" alt=\"b1\" title=\"b1\"/>"
		, "      </map>"
		, "      <img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png\" width=\"123\" height=\"0\" usemap=\"#imageMap_1_1\"/>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>a1"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test public void Err_trailing_ws() {	// PURPOSE: empty 1st line causes failure
		fxt.Test_html_full_frag(String_.Concat_lines_nl_skip_last
		( "<imagemap> "
		, "File:A.png|thumb|test_caption"
		, "</imagemap>"
		), "test_caption"		// no error if test_caption appears; 
		);
	}
	@Test public void Para_omitted() {	// PURPOSE: imagemap should not be automatically enclosed in para; PAGE:cs.w:Seznam_clenu_ctrn�ct�ho_Knesetu; DATE:2014-05-08;
		fxt.Fxt().Init_para_y_();
		fxt.Test_html_full_str("<imagemap>File:A.png</imagemap> a", fxt.Frag_html_full() + " a"); // NOTE: "a" no longer enclosed in <p>; DATE:2014-07-25
		fxt.Fxt().Init_para_n_();
	}
	@Test public void Xnde_double_pipe() {// PURPOSE: if || is inside table and imagemap, treat as lnki; EX:w:United_States_presidential_election,_1992; DATE:2014-03-29; DATE:2014-05-06
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "| z"
		, "<imagemap>"
		, "File:A.png||123px|b"	// NOTE: "||" should not be tblw; also should not be pipe + text; if it is pipe + text, then caption will be "|123px" and width will be -1; DATE:2014-05-06
		, "</imagemap>"
		, "|}"
		) , String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td> z"
		, "<div id=\"imap_div_0\" class=\"noresize\">"
		, "      <map name=\"imageMap_1_1\">"
		, "      </map>"
		, "      <img id=\"xoimg_0\" alt=\"b\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png\" width=\"123\" height=\"0\" usemap=\"#imageMap_1_1\"/>"	// NOTE: width must be 123, not 0
		, "    </div>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		)
		);
	}
	@Test public void Template_image() {	// PURPOSE: handle templates in caption; PAGE:en.w:Kilauea; DATE:2014-07-27
		fxt.Fxt().Init_page_create("Template:Test_template", "xyz");
		fxt.Test_html_full_frag("<imagemap>File:A.png|thumb|{{Test_template}}\n</imagemap>", "xyz");
	}
	@Test public void Template_shape() {	// PURPOSE: handle templates in shape; PAGE:fr.w:Arrondissements_de_Lyon DATE:2014-08-12
		fxt.Fxt().Init_page_create("Template:B1", "<b>b1</b>");	// note that an xnde is a better example as it will throw ArrayOutOfBounds error
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( "<imagemap>"
		, "File:A.png|thumb|123px|a1"
		, "circle 0 0 5 [[B|{{b1}}]]"
		, "desc none"
		, "</imagemap>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"	// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		, "    <div id=\"imap_div_0\" class=\"noresize\">"
		, "      <map name=\"imageMap_1_1\">"
		, "        <area href=\"/wiki/B\" shape=\"circle\" coords=\"0,0,5\" alt=\"b1\" title=\"b1\"/>"
		, "      </map>"
		, "      <img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/123px.png\" width=\"123\" height=\"0\" usemap=\"#imageMap_1_1\"/>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>a1"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test public void Template_multi_line() {	// PURPOSE: handle multiple-line captions; PAGE:en.w:Archaea; DATE:2014-08-22
		fxt.Test_html_full_frag(String_.Concat_lines_nl_skip_last
		( "<imagemap>"
		, "File:A.png|thumb|<ref>text"
		, "</ref>"
		, "</imagemap>"
		), "id=\"cite_ref-0\"");
	}
}
