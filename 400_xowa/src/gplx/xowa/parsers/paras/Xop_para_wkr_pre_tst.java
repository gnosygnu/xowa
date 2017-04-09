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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_para_wkr_pre_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final    Xop_fxt fxt = new Xop_fxt();
	@After public void teardown() {fxt.Init_para_n_();}
	@Test  public void Pre_ignore_bos() {			// PURPOSE: ignore pre at bgn; DATE:2013-07-09
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	" "
		,	"b"
		), String_.Concat_lines_nl
		(	"<p>"
		,	"b"
		,	"</p>"
		));
	}
	@Test  public void Pre_ignore_bos_tblw() {		// PURPOSE: ignore pre at bgn shouldn't break tblw; EX:commons.wikimedia.org; DATE:2013-07-11
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	" "
		,	"{|"
		,	"|-"
		,	"|a"
		,	"|}"
		), String_.Concat_lines_nl
		(	"<p>"	// NOTE: <p> added for TRAILING_TBLW fix; DATE:2017-04-08
		,	"</p>"
		,	"<table>"
		,	"  <tr>"
		,	"    <td>a"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		));
	}
	@Test  public void Ignore_bos_xnde() {		// PURPOSE: space at bgn shouldn't create pre; EX:commons.wikimedia.org; " <center>a\n</center>"; DATE:2013-11-28
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	" <center>a"	// NOTE: leading " " matches MW; DATE:2014-06-23
		,	"</center>"
		),	String_.Concat_lines_nl_skip_last
		(	" <center>a"
		,	"</center>"
		,	""
		));
	}
	@Test  public void Ignore_pre_in_gallery() {// PURPOSE: pre in gallery should be ignored; EX:uk.w:EP2; DATE:2014-03-11
		gplx.xowa.xtns.gallery.Gallery_mgr_wtr.File_found_mode = Bool_.Y_byte;
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a"
		, ""
		, " <gallery>"
		, " File:A.png"
		, " </gallery>"
		),	String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, " <ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\">"
		, "  <li id=\"xowa_gallery_li_0\" class=\"gallerybox\" style=\"width:155px;\">"
		, "    <div style=\"width:155px;\">"
		, "      <div class=\"thumb\" style=\"width:150px;\">"
		, "        <div style=\"margin:15px auto;\">"
		, "          <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"A.png\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/120px.png\" width=\"120\" height=\"120\" /></a>"
		, "        </div>"
		, "      </div>"
		, "      <div class=\"gallerytext\">"
		, "      </div>"
		, "    </div>"
		, "  </li>"
		, "</ul>"
		,""
		));
		gplx.xowa.xtns.gallery.Gallery_mgr_wtr.File_found_mode = Bool_.N_byte;
	}
	@Test  public void Pre_xnde_gallery() {	// PURPOSE: <gallery> should invalidate pre; EX: en.w:Mary, Queen of Scots
		gplx.xowa.xtns.gallery.Gallery_mgr_wtr.File_found_mode = Bool_.Y_byte;
		fxt.Wiki().Xtn_mgr().Init_by_wiki(fxt.Wiki());
		String raw = String_.Concat_lines_nl_skip_last
			( " <gallery>"
			, "File:A.png|b"
			, "</gallery>"
			);
		fxt.Test_parse_page_wiki_str(raw, String_.Concat_lines_nl_skip_last
			( " <ul id=\"xowa_gallery_ul_0\" class=\"gallery mw-gallery-traditional\">"	// NOTE: leading " " matches MW; DATE:2014-06-23
			, "  <li id=\"xowa_gallery_li_0\" class=\"gallerybox\" style=\"width:155px;\">"
			, "    <div style=\"width:155px;\">"
			, "      <div class=\"thumb\" style=\"width:150px;\">"
			, "        <div style=\"margin:15px auto;\">"
			, "          <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/120px.png\" width=\"120\" height=\"120\" /></a>"
			, "        </div>"
			, "      </div>"
			, "      <div class=\"gallerytext\"><p>b"
			, "</p>"
			, ""
			, "      </div>"
			, "    </div>"
			, "  </li>"
			, "</ul>"
			));
		gplx.xowa.xtns.gallery.Gallery_mgr_wtr.File_found_mode = Bool_.N_byte;
	}
	@Test  public void Ignore_pre_in_center() {// PURPOSE: pre in gallery should be ignored; EX:uk.w:EP2; DATE:2014-03-11
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a"
		, " <center>b"
		, " </center>"
		, "d"
		),	String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, " <center>b"
		, " </center>"
		, ""
		, "<p>d"
		, "</p>"
		)
		);
	}
	@Test  public void Remove_only_1st_space() {	// PURPOSE: pre should only remove 1st space]; EX: w:Wikipedia:WikiProject_History/CategoryExample; DATE:2014-04-14
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "    a"
		, "    b"
		, "    c"
		), String_.Concat_lines_nl_skip_last
		( "<pre>   a"
		, "   b"
		, "   c"
		, "</pre>"
		)
		);
	}
	@Test  public void Remove_only_1st_space__bos() {	// PURPOSE: similar to above but check that pre at \n\s is indented correctly; DATE:2014-04-14
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( ""
		, "    a"
		, "    b"
		), String_.Concat_lines_nl_skip_last
		( ""
		, "<pre>   a"
		, "   b"
		, "</pre>"
		)
		);
	}
	@Test  public void Ignore_tblw_td() {// PURPOSE: \n\s| should continue pre; EX:w:Wikipedia:WikiProject_History/CategoryExample; DATE:2014-04-14
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( " a"
		, " |"
		, " b"
		),	String_.Concat_lines_nl_skip_last
		( "<pre>a"
		, "|"
		, "b"
		, "</pre>"
		)
		);
	}
	@Test   public void Tab() {	// PURPOSE: tab inside pre was being converted to space; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
		fxt.Test_html_full_str
		( " \ta"
		, String_.Concat_lines_nl
		( "<pre>\ta"
		, "</pre>"
		));	
	}
	@Test   public void Style() {	// PURPOSE: " <style>" was not being put in pre; PAGE:en.w:Cascading_Style_Sheets DATE:2014-06-23
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		( " <style>"
		, " </style>"
		), String_.Concat_lines_nl
		( "<pre>&lt;style>"
		, "&lt;/style>"
		, "</pre>"
		));	
	}
	@Test  public void Nl_only() {	// PURPOSE: wiki_pre with \n only was being dropped; PAGE:en.w:Preferred_number DATE:2014-06-24
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( " a"
		, " "	// was being dropped
		, " b"
		), String_.Concat_lines_nl
		( "<pre>a"
		, ""	// make sure it's still there
		, "b"
		, "</pre>"
		));	
	}
	@Test  public void Nl_w_ws() {	// PURPOSE: based on Nl_only; make sure that 1 or more spaces does not add extra \n; PAGE:en.w:Preferred_number DATE:2014-06-24
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( " a"
		, "  "	// 2 spaces
		, " b"
		), String_.Concat_lines_nl
		( "<pre>a"
		, " "	// 1 space
		, "b"
		, "</pre>"
		));	
	}
	@Test  public void Nl_many() {	// PURPOSE: handle alternating \n\s; PAGE:en.w:Preferred_number DATE:2014-06-24
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( " a"
		, " "
		, " b"
		, " "
		, " c"
		), String_.Concat_lines_nl
		( "<pre>a"
		, ""
		, "b"
		, ""
		, "c"
		, "</pre>"
		));	
	}
	@Test   public void Source() {	// PURPOSE: " <source>" in pre has issues; PAGE:en.w:Comment_(computer_programming) DATE:2014-06-23
		fxt.Init_para_y_();
		fxt.Test_html_wiki_str(String_.Concat_lines_nl
		( " "
		, " <source>"
		, " a"
		, " </source>"
		, " "
		), String_.Concat_lines_nl
		( "<p>"			// this is wrong, but will be stripped by tidy
		, "</p>"
		, " <div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		, " a"
		, "</pre></div>"
		, ""
		, "<p><br/>"	// also wrong, but leave for now
		, "</p>"
		));	
	}
	@Test   public void False_match_xnde() {	// PURPOSE: "\s<trk>" being evaluted as "\s<tr>"; PAGE:de.v:Via_Jutlandica/Gpx DATE:2014-11-29
		fxt.Init_para_y_();
		fxt.Test_html_wiki_str(String_.Concat_lines_nl
		( ""
		, " <trk>"
		), String_.Concat_lines_nl
		( ""
		, "<pre>&lt;trk&gt;"
		, "</pre>"
		));
	}
}
