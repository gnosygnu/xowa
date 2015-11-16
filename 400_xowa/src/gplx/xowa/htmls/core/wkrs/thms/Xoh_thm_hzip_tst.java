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
	private String Html__basic = String_.Concat_lines_nl_skip_last
	( "<div class='thumb tleft'>"
	, "  <div id='xothm_0' class='thumbinner' style='width:0px;'>"
	, "<a href='/wiki/File:A.png' class='image'><img id='xoimg_0' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png' width='220' height='119' class='thumbimage'></a>"
	, "    <div class='thumbcaption'>"
	, "      <div class='magnify'>"
	, "        <a href='/wiki/File:A.png' class='@gplx.Internal protected' title='Enlarge'>"
	, "          <img src='file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png' width='15' height='11' alt=''>"
	, "        </a>"
	, "      </div>abc"
	, "    </div>"
	, "  </div>"
	, "</div>"
	);
	@Test   public void Page__basic() {			
		fxt.Test__decode("~&#abc~!T8#T\"DA.png~", Html__basic);
	}
	@Test   public void Dump() {
//			fxt.Exec_write_to_fsys(Io_url_.new_dir_("J:\\xowa\\dev\\html\\"), "temp_earth_xo.html");
	}
}
