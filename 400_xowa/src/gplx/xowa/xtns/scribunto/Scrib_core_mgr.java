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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.*;
import gplx.xowa.parsers.*;
public class Scrib_core_mgr {
	public Scrib_core Core() {return core;} private Scrib_core core;
	public void Terminate_when_page_changes_y_() {terminate_when_page_changes = true;}	private boolean terminate_when_page_changes;
	public Scrib_core Core_init(Xop_ctx ctx) {
		core = new Scrib_core(ctx.App(), ctx);
		terminate_when_page_changes = false;
		return core;
	}
	public void Core_term() {
		if (core != null) {
			core.Term();
			core = null;
		}
	}
	public void When_page_changed(Xoae_page page) {
		if (terminate_when_page_changes) {	// true when error in {{#invoke}}; PAGE:th.d:all; DATE:2014-10-03
			Core_term();					// terminate core; note that next call to {{#invoke}} will check for null and rebuild if null; 
			terminate_when_page_changes = false;
		}
		else {					// no error on previous page; notify core that page changed; note that lua will cache chunks by Module name and two modules in two different wikis can have the same name, but different data: EX:Module:Citation/CS1/Configuration and enwiki / zhwiki; DATE:2014-03-21
			if (core != null)	// null-check needed when engine first created
				core.When_page_changed(page);	// NOTE: must call When_page_changed on core to update page; else, current ttl is not updated, and scrib_wikibase will return wrong document; DATE:2016-07-22
		}
	}

	public static void Term_all(Xoae_app app) {	// NOLOCK.app_level
		if (app == null) return;
		Xoae_wiki_mgr wiki_mgr = app.Wiki_mgr();
		int len = wiki_mgr.Count();
		for (int i = 0; i < len; ++i) {
			Xowe_wiki wiki = (Xowe_wiki)wiki_mgr.Get_at(i);
			wiki.Parser_mgr().Scrib().Core_term();
		}
	}
}
