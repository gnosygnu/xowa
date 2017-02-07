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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_thm_hzip__avo__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void setup() {fxt.Clear();}
	@Test   public void Video() {
		fxt.Test__bicode("~&%test_caption~|E9eA.ogv~%A.ogv~~", Gfh_utl.Replace_apos(String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		,   "<div class='thumbinner' style='width:220px;'>"
		,     "<div class='xowa_media_div'>"
		,       "<div><a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'><img data-xowa-title='A.ogv' data-xoimg='4|-1|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "<div><a href='' xowa_title='A.ogv' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'></a></div>"
		,     "</div>"
		,     "<div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.ogv' class='internal' title='Enlarge'></a></div>"
		,       "test_caption</div>"
		,   "</div>"
		, "</div>"
		)));
	}
	@Test   public void Audio() {
		fxt.Test__bicode(Gfh_utl.Replace_apos(String_.Concat_lines_nl
		( "<div class='thumb tright'>"
		, "  <div class='thumbinner' style='width:11px;'>"
		, "    <div class=\"xowa_media_div\">"
		, "<div><a href=\"\" xowa_title=\"A.oga\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		, "<div>$|#na)A.ogaAbout this file</div>"
		, "</div>"
		, "    <div class='thumbcaption'>"
		,       "<div class='magnify'>~$|%%,)~A.oga~Enlarge~</div>b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		)), String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "  <div class='thumbinner' style='width:11px;'>"
		, "    <div class='xowa_media_div'>"
		,       "<div><a href='' xowa_title='A.oga' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'></a></div>"
		,       "<div><a href='/wiki/File:A.oga' class='xowa_media_info' title='About this file'></a></div>"
		, "</div>"
		, "    <div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.oga' class='internal' title='Enlarge'></a></div>b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));		
	}
}
