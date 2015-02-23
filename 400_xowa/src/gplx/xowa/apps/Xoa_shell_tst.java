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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xoa_shell_tst {
	@Test  public void Fetch_page() {	// PURPOSE.fix: fetch_page failed with nullRef; DATE:2013-04-12
		Xop_fxt parser_fxt = new Xop_fxt();
		Xoae_app app = parser_fxt.App(); Xowe_wiki wiki = parser_fxt.Wiki();
		parser_fxt.Init_page_create("A", "test"); // need to create page in order for html output to gen
		wiki.Html_mgr().Page_wtr_mgr().Html_capable_(true);	// need to set html_capable in order for div_home to load
		Xoa_gfs_mgr.Msg_parser_init();
		wiki.Html_mgr().Portal_mgr().Div_home_fmtr().Fmt_("~{<>app.user.msgs.get('mainpage-description');<>}");
		app.Gfs_mgr().Run_str("app.shell.fetch_page('en.wikipedia.org/wiki/A' 'html');"); // this causes a nullRef error b/c app.user.lang is null
	}
}
