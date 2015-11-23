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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_gly_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	private String Html__basic = gplx.langs.htmls.Html_utl.Replace_apos(String_.Concat_lines_nl_skip_last
	( "<ul class='gallery mw-gallery-traditional' style='max-width:978px;_width:978px;'>"
	, "  <li class='gallerybox' style='width: 155px'>"
	, "    <div style='width: 155px'>"
	, "      <div class='thumb' style='width: 150px;'>"
	, "        <div style='margin:75px auto;'>"
	, "          <a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a>"
	, "        </div>"
	, "      </div>"
	, "      <div class='gallerytext'>"
	, "        <p>"
	, "          abc"
	, "        </p>"
	, "      </div>"
	, "    </div>"
	, "  </li>"
	, "  <li class='gallerybox' style='width: 155px'>"
	, "    <div style='width: 155px'>"
	, "      <div class='thumb' style='width: 150px;'>"
	, "        <div style='margin:75px auto;'>"
	, "          <a href='/wiki/File:B.png' class='image' xowa_title='B.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a>"
	, "        </div>"
	, "      </div>"
	, "      <div class='gallerytext'>"
	, "        <p>"
	, "          abc"
	, "        </p>"
	, "      </div>"
	, "    </div>"
	, "  </li>"
	, "</ul>"
	));
	@Test   public void Basic() {
		fxt.Test__bicode(Html__basic, Html__basic);
	}
}
