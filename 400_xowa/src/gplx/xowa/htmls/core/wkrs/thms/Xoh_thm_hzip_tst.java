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
import org.junit.*; import gplx.xowa.htmls.core.hzips.tests.*;
public class Xoh_thm_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Test   public void Page__basic() {
		try {
			Io_url html_dir = Io_url_.new_dir_("J:\\xowa\\dev\\html\\");
			Bry_bfr bfr = Bry_bfr.new_();
			fxt.Hzip_mgr().Encode(bfr, fxt.Parser_fxt().Wiki(), Bry_.Empty, Io_mgr.Instance.LoadFilBry(html_dir.GenSubFil("temp_earth_mw.html")), new gplx.xowa.htmls.core.hzips.stats.Hzip_stat_itm());
			Io_mgr.Instance.SaveFilBry(html_dir.GenSubFil("temp_earth_mw_hzip.html"), bfr.To_bry_and_clear());
		} catch (Exception e) {
			Tfds.Write(e);
		}
//			fxt.Test__encode(""
//			, String_.Concat_lines_nl_skip_last
//			( "<div class='thumb tleft'>"
//			, "  <div id='xowa_file_div_1' class='thumbinner' style='width:220px;'>"
//			, "    <a href='/wiki/File:A.png' class='image' xowa_title='A.png'>"
//			, "      <img id='xowa_file_img_1' alt='' "
//			, "        src='file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/0/0/A.png/220px.png' "
//			, "        width='220' height='119' class='thumbimage'>"
//			, "    </a> "
//			, "    <div class='thumbcaption'>"
//			, "      <div class='magnify'>"
//			, "        <a href='/wiki/File:A.png' class='@gplx.Internal protected' title='Enlarge'>"
//			, "          <img src='file:///C:/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png' width='15' height='11' alt=''>"
//			, "        </a>"
//			, "      </div>b"
//			, "    </div>"
//			, "  </div>"
//			, "</div>"
//			));
	}
}
/*
<div class="thumb tleft">
  <div id="xowa_file_div_60" class="thumbinner" style="width:220px;">
    <a href="/wiki/File:Protoplanetary-disk.jpg" class="image" xowa_title="Protoplanetary-disk.jpg">
      <img id="xowa_file_img_60" alt="" 
        src="file:///J:/xowa/file/commons.wikimedia.org/thumb/7/1/7/c/Protoplanetary-disk.jpg/220px.jpg" 
        width="220" height="119" class="thumbimage">
    </a> 
    <div class="thumbcaption">
      <div class="magnify">
        <a href="/wiki/File:Protoplanetary-disk.jpg" class="@gplx.Internal protected" title="Enlarge">
          <img src="file:///J:/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png" width="15" height="11" alt="">
        </a>
      </div>Artist's impression of the early Solar System's planetary disk
    </div>
  </div>
</div>

<div class="thumb tleft">
  <div id="xowa_file_div_60" class="thumbinner" style="width:0px;">
    <a href="/wiki/File:Protoplanetary-disk.jpg" class="image" xowa_title="Protoplanetary-disk.jpg" data-xoimg='1|2|3|4|5|6|A.png'>
      <img id="xowa_file_img_60" alt="" 
        src="" 
        width="0" height="0" class="thumbimage">
    </a> 
    <div class="thumbcaption">
      <div class="magnify">
        <a href="/wiki/File:Protoplanetary-disk.jpg" class="@gplx.Internal protected" title="Enlarge">
          <img src="file:///J:/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png" width="15" height="11" alt="">
        </a>
      </div>Artist's impression of the early Solar System's planetary disk
    </div>
  </div>
</div>
*/
