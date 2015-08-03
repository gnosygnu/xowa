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
package gplx.xowa.urls; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xoa_url_parser__proto_tst {
	private final Xoa_url_parser_fxt tstr = new Xoa_url_parser_fxt();
	@Test  public void Relative() {
		tstr.Run_parse("//en.wikipedia.org/wiki/A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Http__basic() {
		tstr.Run_parse("http://en.wikipedia.org/wiki/A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Upload__basic() { 
		tstr.Prep_add_xwiki_to_user("commons.wikimedia.org");	// NOTE: need to add xwiki to be able to resolve "/commons/"
		tstr.Run_parse("http://upload.wikimedia.org/wikipedia/commons/a/ab/C.svg").Chk_wiki("commons.wikimedia.org").Chk_page("File:C.svg");	// orig
		tstr.Run_parse("http://upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220px-A.png").Chk_wiki("commons.wikimedia.org").Chk_page("File:A.png"); // thum
	}
	@Test  public void File__basic() {
		tstr.Run_parse("file:///C:/a/b/c").Chk_tid(Xoa_url_.Tid_file);
	}
	@Test  public void Ftp__basic() {
		tstr.Run_parse("ftp://en.wikipedia.org/wiki/A").Chk_tid(Xoa_url_.Tid_inet);
	}
	@Test  public void Extended() {
		tstr.Run_parse("http://en.wikipedia.org/w/index.php?A=B").Chk_wiki("en.wikipedia.org").Chk_page("index.php").Chk_qargs("?A=B").Chk_anch(null);
	}
}
