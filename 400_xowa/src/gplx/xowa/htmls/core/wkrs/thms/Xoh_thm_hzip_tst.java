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
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_thm_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	private String Html__image = String_.Concat_lines_nl_skip_last
	( "<div class='thumb tleft'>"
	, "  <div id='xothm_0' class='thumbinner' style='width:220px;'>"
	, "    <a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img id='xoimg_0' data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' class='thumbimage' alt='abc'></a>"
	, "    <div class='thumbcaption'>"
	, "      <div class='magnify'>"
	, "        <a href='/wiki/File:A.png' class='internal' title='Enlarge'><img src='file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png' width='15' height='11' alt=''></a>"
	, "      </div>abc"
	, "    </div>"
	, "    <hr>"
	, "    <div class='thumbcaption'>"
	, "      abc"
	, "    </div>"
	, "  </div>"
	, "</div>"
	)
	, Html__video = String_.Replace(String_.Concat_lines_nl_skip_last
	( "<div class='thumb tright'>"
	, "  <div id='xowa_file_div_3' class='thumbinner' style='width:220px;'>"
	, "    <div id='xowa_media_div'>"
	, "      <div>"
	, "        <a href='/wiki/File:a.ogv' class='image' title='a.ogv'><img id='xowa_file_img_3' src='file:///' width='-1' height='-1' alt=''></a>"
	, "      </div>"
	, "      <div>"
	, "        <a id='xowa_file_play_3' href='file:///' xowa_title='a.ogv' class='xowa_anchor_button' style='width:218px;max-width:220px;'><img src='file:///C:/xowa/bin/any/xowa/file/mediawiki.file/play.png' width='22' height='22' alt='Play sound'></a>"
	, "      </div>"
	, "    </div>"
	, "    <div class='thumbcaption'>"
	, "      <div class='magnify'>"
	, "        <a href='/wiki/File:a.ogv' class='@gplx.Internal protected' title='Enlarge'><img src='file:///C:/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png' width='15' height='11' alt=''></a>"
	, "      </div>Moscow (Russian Empire) in 1908"
	, "    </div>"
	, "  </div>"
	, "</div>"
	), "'", "\"")
	;
	@Test   public void Image() {
		fxt.Test__bicode("~&3abc~abc~!uA.png~0|220|110|0.5|-1|-1~abc~", Html__image);
	}
	@Test   public void Video() {
		fxt.Test__bicode(Html__video, Html__video);
	}
//		@Test   public void Dump() {
//			Xowe_wiki en_d = fxt.Prep_create_wiki("wikt", "en.wiktionary.org");
//			gplx.xowa.wikis.nss.Xow_ns_mgr ns_mgr = en_d.Ns_mgr();
//			ns_mgr.Ns_main().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
//
//			fxt.Wiki().Ns_mgr().Aliases_add(gplx.xowa.wikis.nss.Xow_ns_.Tid__portal, "WP");
//			fxt.Wiki().Ns_mgr().Init();
//
//			fxt.Exec_write_to_fsys(Io_url_.new_dir_("D:\\xowa\\dev_rls\\html\\"), "temp_earth_xo.html");
//		}
}
