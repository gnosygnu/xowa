/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
		}
		Io_mgr.Instance.InitEngine_mem();
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
} 
