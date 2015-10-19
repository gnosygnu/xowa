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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xoa_url_parser__xcmd_tst {
	private final Xoa_url_parser_fxt tstr = new Xoa_url_parser_fxt();
	@Test  public void Basic() {
		tstr.Run_parse("xowa-cmd:xowa.app.version").Chk_tid(Xoa_url_.Tid_xcmd).Chk_page("xowa.app.version");
	}
	@Test  public void Encoded() {
		tstr.Run_parse("xowa-cmd:a%22b*c").Chk_tid(Xoa_url_.Tid_xcmd).Chk_page("a\"b*c");
	}
	@Test  public void Ignore_anchor_and_qargs() {
		tstr.Run_parse("xowa-cmd:a/b/c?d=e#f").Chk_tid(Xoa_url_.Tid_xcmd).Chk_page("a/b/c?d=e#f");
	}
}
