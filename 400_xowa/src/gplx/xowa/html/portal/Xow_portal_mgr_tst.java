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
package gplx.xowa.html.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*;
public class Xow_portal_mgr_tst {
	@Before public void init() {fxt.Init();} private Xowh_portal_mgr_fxt fxt = new Xowh_portal_mgr_fxt();
	@Test  public void Div_ns_bry() {
		fxt.Test_div_ns_bry("A"			, "/wiki/A;selected;/wiki/Talk:A;xowa_display_none;");
		fxt.Test_div_ns_bry("Talk:A"	, "/wiki/A;;/wiki/Talk:A;selected;");
	}
	@Test  public void Div_personal_bry() {
		fxt.Test_div_personal_bry("/wiki/User:anonymous;anonymous;xowa_display_none;/wiki/User_talk:anonymous;xowa_display_none;");
	}
	@Test  public void Missing_ns_cls() {
		fxt.Test_missing_ns_cls("xowa_display_none");
	}
}
class Xowh_portal_mgr_fxt {
	public void Init() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			wiki.Ns_mgr().Ns_main().Exists_(true);	// needed for ns
			wiki.Html_mgr().Portal_mgr().Init_assert();	// needed for personal
		}
	}	private Xoae_app app; Xowe_wiki wiki;
	public void Test_div_ns_bry(String ttl, String expd) {
		Tfds.Eq(expd, String_.new_a7(wiki.Html_mgr().Portal_mgr().Div_ns_bry(wiki.Utl__bfr_mkr(), Xoa_ttl.parse_(wiki, Bry_.new_a7(ttl)), wiki.Ns_mgr())));
	}
	public void Test_div_personal_bry(String expd) {
		Tfds.Eq(expd, String_.new_a7(wiki.Html_mgr().Portal_mgr().Div_personal_bry()));
	}
	public void Test_missing_ns_cls(String expd) {
		Tfds.Eq(expd, String_.new_a7(wiki.Html_mgr().Portal_mgr().Missing_ns_cls()));
	}
}
