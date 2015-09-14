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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Xop_lnki_wkr__pre_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Previous_pre() {	// PURPOSE: if pre is already in effect, end it; EX: en.b:Knowing_Knoppix/Other_applications
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"a"
		,	" b"
		,	"[[File:A.png|thumb]]"
		,	"c"
		), String_.Concat_lines_nl_skip_last
		( 	"<p>a"
		,	"</p>"
		,	""
		,	"<pre>b"			// NOTE: pre is ended; " b\n[[" -> <pre>b</pre><div>" 
		,	"</pre>"
		,	""
		,	Html_A_png
		,	""
		,	"<p>c"
		,	"</p>"
		,	""
		));
	}
	@Test  public void Current_pre_and_thumb() {	// PURPOSE: current pre should be ended by thumb; EX: w:Roller coaster; it.w:Provincia_di_Pesaro_e_Urbino; DATE:2014-02-17
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"a"
		,	"  [[File:A.png|thumb]]"
		,	"b"
		), String_.Concat_lines_nl_skip_last
		( 	"<p>a"
		,	"</p>"
		,	""					// NOTE: do not capture " "; confirmed against MW; DATE:2014-02-19
		,	Html_A_png
		,	""
		,	"<p>b"
		,	"</p>"
		,	""
		));
	}
	@Test  public void Current_pre_and_halign() {	// PURPOSE: current pre should be ended by halign since they also produce divs; EX: w:Trombiculidae; DATE:2014-02-17
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"a"
		,	"  [[File:A.png|right]]"
		,	"b"
		), String_.Concat_lines_nl_skip_last
		( 	"<p>a"
		,	"</p>"
		,	"<div class=\"floatright\">"		// NOTE: do not capture " "; confirmed against MW; DATE:2014-02-19
		,	"<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a></div>"
		,	""
		,	"<p>b"
		,	"</p>"
		,	""
		));
	}
	@Test  public void Current_pre() {	// PURPOSE: current pre should exist if not div; DATE:2014-02-17
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"a"
		,	"  [[File:A.png]]"
		,	"b"
		), String_.Concat_lines_nl_skip_last
		( 	"<p>a"
		,	"</p>"
		,	""
		,	"<pre> <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"	// NOTE: capture " "; confirmed against MW; DATE:2014-04-14
		,	"</pre>"
		,	""
		,	"<p>b"
		,	"</p>"
		,	""
		));
	}
	private static final String Html_A_png = String_.Concat_lines_nl_skip_last
	(	"<div class=\"thumb tright\">"
	,	"  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
	,	"    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"0\" height=\"0\" /></a>"
	, 	"    <div class=\"thumbcaption\">"
	,	"      <div class=\"magnify\">"
	,	"        <a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\">"
	,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
	,	"        </a>"
	,	"      </div>"
	,	"      "
	,	"    </div>"
	,	"  </div>"
	,	"</div>"
	);
}

