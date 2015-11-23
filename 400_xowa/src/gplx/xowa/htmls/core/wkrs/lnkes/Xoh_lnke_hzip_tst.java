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
package gplx.xowa.htmls.core.wkrs.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_lnke_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Test   public void Free() {
		fxt.Test__bicode("~#!http://a.org~", Xoh_lnke_html__hdump__tst.Html__free);
	}
	@Test   public void Auto() {
		fxt.Test__bicode("~#*http://a.org~\"", Xoh_lnke_html__hdump__tst.Html__auto);
	}
	@Test   public void Text() {
		fxt.Test__bicode("~#'http://a.org~a~", Xoh_lnke_html__hdump__tst.Html__text);
	}
	@Test   public void Text__tidy() {	// PURPOSE:handle reparenting of html elements by HTML tidy EX:<font color="red">[http://a.org]</font>; DATE:2015-08-25
		fxt.Test__bicode
		( "~#&http://a.org~<font color=\"red\">[123]</font>~"
		, "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external autonumber\"><font color=\"red\">[123]</font></a>"
		);
	}
	@Test   public void Fail__href() {
		String html = "<a rel='nofollow' class='external autonumber'>a</a>";
		fxt.Test__encode__fail(html, html);
	}
	@Test   public void Fail__lnke_type() {
		String html = "<a href='http://a.org' rel='nofollow' class='external invalid'>a</a>";
		fxt.Test__encode__fail(html, html);
	}
	@Test   public void Fail__auto() {
		String html = "<a href='http://a.org' rel='nofollow' class='external autonumber'>[abc]</a>";
		fxt.Test__encode__fail(html, html);
	}
}
