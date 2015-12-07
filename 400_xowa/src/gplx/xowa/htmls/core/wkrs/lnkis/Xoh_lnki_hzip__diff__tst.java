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
public class Xoh_lnki_hzip__diff__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Diff__basic() {				// EX: [[A|b]]
		fxt.Test__bicode("~$\"A~b~"	, "<a href='/wiki/A' title='A'>b</a>");
	}
	@Test   public void Diff__cs__lo() {			// EX: [[A|a]]
		fxt.Test__bicode("~$%A~"	, "<a href='/wiki/A' title='A'>a</a>");
	}
	@Test   public void Diff__page_w_anch() {		// EX: [[A#b|c]]
		fxt.Test__bicode("~${'$A#b~b~A~", "<a href='/wiki/A#b' title='A'>b</a>");
	}
	@Test   public void Capt__nest() {				// EX: [[A|B[[C|C1]]D]]
		fxt.Test__bicode
		( "~$\"A~B<a href=\"/wiki/C\" title=\"C\">C1</a>D~"
		, "<a href='/wiki/A' title='A'>B<a href='/wiki/C' title='C'>C1</a>D</a>"
		);
	}
}
