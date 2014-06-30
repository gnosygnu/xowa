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
package gplx.xowa.xtns.imageMap; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Xop_imageMap_xnde_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki_str("<imagemap>File:A.png</imagemap>", html_img_none("File:A.png", ""));
	}
	@Test  public void Caption() {
		fxt.Test_parse_page_wiki_str("<imagemap>File:A.png|thumb|bcde</imagemap>", String_.Concat_lines_nl_skip_last
			(	Xop_para_wkr_basic_tst.File_html("File", "A.png", "7/0", "bcde")
			,	""
			));
	}
	@Test  public void Err_trailing_ws() {	// PURPOSE: empty 1st line causes failure
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<imagemap> "
			,	"File:A.png"
			,	"</imagemap>"
			), html_img_none("File:A.png", ""));		
	}
	@Test  public void Coords_one() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<imagemap>"
			,	"File:A.png"
			,	"circle 1 3 5 [[b|c]]"
//				,	"rect  2 4 6 8"
//				,	"poly  1 2 3 4 5"
			,	"</imagemap>"
			), html_img_none("File:A.png", ""));		
	}
	@Test  public void Para_omitted() {	// PURPOSE: imagemap should not be automatically enclosed in para; cs.w:Seznam_clenu_ctrn�ct�ho_Knesetu; DATE:2014-05-08
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str("<imagemap>File:A.png</imagemap> a", html_img_none("File:A.png", "") + String_.Concat_lines_nl_skip_last
		( ""
		, ""
		, "<p> a"
		, "</p>"
		));
		fxt.Init_para_n_();
	}
	private static String html_img_none(String trg, String alt) {
		return String_.Format("<a href=\"/wiki/{0}\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>", trg, alt);
	}		
}
