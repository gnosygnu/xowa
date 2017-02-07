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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*;
public class Xoh_lnki_hzip__anch__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Basic() {			// EX: [[#a]]
		fxt.Test__bicode("~$Ba~#a~", "<a href='#a'>#a</a>");
	}
	@Test   public void Capt() {			// EX: [[#a|b]]
		fxt.Test__bicode("~$Ba~b~", "<a href='#a'>b</a>");
	}
	@Test   public void Capt_similar() {	// EX: [[#a|a]]
		fxt.Test__bicode("~$Ba~a~", "<a href='#a'>a</a>");
	}
	@Test   public void Error() {			// EX: [[#a|b]]; make sure bad title character does not cause error
		fxt.Test__bicode("~$Ba|b~#a|b~", "<a href='#a|b'>#a|b</a>");	// NOTE: the "|" should be url-encoded
	}
	@Test   public void Inet__file() {
		fxt.Test__bicode("~$Rfile:///C://A.png~b~", "<a href='file:///C://A.png' title='file:///C://A.png'>b</a>");
	}
	@Test   public void Inet__enc() {
		fxt.Test__bicode("~${'Thttps://simple.wikisource.org/wiki/A%C3%A6e~b~Aæe~", "<a href='https://simple.wikisource.org/wiki/A%C3%A6e' title='Aæe'>b</a>");
	}
}
