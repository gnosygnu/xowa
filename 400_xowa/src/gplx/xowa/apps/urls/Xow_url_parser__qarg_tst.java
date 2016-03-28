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
public class Xow_url_parser__qarg_tst {
	private final    Xow_url_parser_fxt tstr = new Xow_url_parser_fxt();
	@Test  public void Redirect() {
		tstr.Run_parse("A?redirect=no").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_qargs("?redirect=no");
	}
	@Test  public void Action_is_edit() {
		tstr.Run_parse("A?action=edit").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_action_is_edit_y();
	}
	@Test  public void Assert_state_cleared() {	// PURPOSE.fix: action_is_edit (et. al.) was not being cleared on parse even though Xoa_url reused; DATE:20121231
		tstr.Run_parse("A?action=edit")	.Chk_action_is_edit_y();
		tstr.Run_parse_reuse("B")		.Chk_action_is_edit_n();
	}
	@Test  public void Query_arg() {	// PURPOSE.fix: query args were not printing out
		tstr.Run_parse("en.wikipedia.org/wiki/Special:Search/Earth?fulltext=yes").Chk_build_str_is_same();
	}
	@Test   public void Dupe_key() {
		tstr.Run_parse("A?B=C1&B=C2").Chk_page("A").Chk_qargs("?B=C1&B=C2");
	}
	@Test  public void Question_is_eos() {
		tstr.Run_parse("A?").Chk_wiki("en.wikipedia.org").Chk_page("A?").Chk_qargs("");
	}
	@Test  public void Question_is_page() {
		tstr.Run_parse("A?B").Chk_wiki("en.wikipedia.org").Chk_page("A?B").Chk_qargs("");
	}
	@Test  public void Question_is_anchor() {
		tstr.Run_parse("A#b?c").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_anch("b.3Fc");
	}
	@Test  public void Title_remove_w() {	// PURPOSE: fix /w/ showing up as seg; DATE:2014-05-30
		tstr.Run_parse("http://en.wikipedia.org/w/index.php?title=A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Search() {
		tstr.Run_parse("Special:Search/Moon%3Ffulltext%3Dy%26xowa_page_index%3D1").Chk_page("Special:Search/Moon").Chk_qargs("?fulltext=y&xowa_page_index=1");
	}
	@Test  public void Ctg() {
		tstr.Run_parse("Category:A?pagefrom=A#mw-pages").Chk_page("Category:A").Chk_qargs("?pagefrom=A").Chk_anch("mw-pages");
	}
	@Test  public void Encoded() {
		tstr.Run_parse("en.wikipedia.org/wiki/A?action&#61;edit&preload&#61;B").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_qargs("?action=&#61;edit=&preload=&=").Chk_anch("61.3BB");	// NOTE: this is wrong; fix later
	}
}
