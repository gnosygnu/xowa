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
package gplx.xowa.bldrs.cmds.texts.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import org.junit.*; import gplx.xowa.htmls.portal.*; import gplx.xowa.wikis.xwikis.*;
public class Xob_init_base_tst {
	@Before public void init() {fxt.Clear();} private Xob_init_base_fxt fxt = new Xob_init_base_fxt();
	@Test  public void Dirty_wiki_itms() {
		Xoae_app app = fxt.App(); Xowe_wiki wiki = fxt.Wiki();
		Xoa_available_wikis_mgr wikis_list = fxt.App().Gui_mgr().Html_mgr().Portal_mgr().Wikis();
		Tfds.Eq("", wikis_list.Itms_as_html());			// assert
		Xow_xwiki_itm xwiki_itm = app.Usere().Wiki().Xwiki_mgr().Add_by_atrs("en.wikipedia.org", "en.wikipedia.org");
		xwiki_itm.Offline_(Bool_.Y);	// simulate add via Available_from_fsys; DATE:2014-09-21
		Tfds.Eq("", wikis_list.Itms_as_html());			// still empty
		new Xob_init_tdb(app.Bldr(), wiki).Cmd_end();	// mock "init" task
		Tfds.Eq("\n        <li><a href=\"/site/en.wikipedia.org/\" class='xowa-hover-off'>en.wikipedia.org</a></li>", wikis_list.Itms_as_html());	// no longer empty
	}
}
class Xob_init_base_fxt {		
	public void Clear() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
		}
		Io_mgr.Instance.InitEngine_mem();
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
} 
