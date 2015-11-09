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
package gplx.xowa.htmls.core.wkrs.spaces; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.tests.*;
public class Xoh_space_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Test   public void Len__8() {
		fxt.Test__bicode("~!)", "        ");
	}
	@Test   public void Len__85() {
		fxt.Test__bicode("~!{\"!", String_.Repeat(" ", 85));
	}
	@Test   public void Many() {
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "  <div id='bodyContent' class='mw-body-content'>"
		, "~!%<div id='siteSub'>a</div>"
		, "~!%<div id='contentSub'></div>"
		, "</div>"
		), String_.Concat_lines_nl_skip_last
		( "  <div id='bodyContent' class='mw-body-content'>"
		, "    <div id='siteSub'>a</div>"
		, "    <div id='contentSub'></div>"
		, "</div>"
		));
	}
}
