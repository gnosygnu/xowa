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
package gplx.xowa.addons.apps.cfgs.mgrs.types; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
public class Xocfg_type_mgr {
	private final    Ordered_hash list_hash = Ordered_hash_.New();
	public Xocfg_type_mgr() {
		this.Lists__add("list:xowa.app.startup.window.mode", "previous", "maximized", "absolute", "relative", "default");
		this.Lists__add("list:xowa.app.startup.pages.type", "blank", "xowa", "previous", "custom");
		this.Lists__add("list:xowa.gui.html_box.page_load_mode", "mem", "url");
		this.Lists__add("list:xowa.html.portal.missing_class", Keyval_.new_("", "Show as blue link"), Keyval_.new_("new", "Show as red link"), Keyval_.new_("xowa_display_none", "Hide"));
		this.Lists__add("list:xowa.html.tidy.engine", "tidy", "jtidy");
		this.Lists__add("list:xowa.bldr.db.zip_mode", Keyval_.new_("raw", "text"), Keyval_.new_("gzip"), Keyval_.new_("bzip2"), Keyval_.new_("xz"));
		this.Lists__add("list:xowa.addon.category.catpage.missing_class", "normal", "hide", "red_link");
		this.Lists__add("list:xowa.addon.http_server.file_retrieve_mode", Keyval_.new_("wait"), Keyval_.new_("skip"), Keyval_.new_("async_server", "async server"));
		this.Lists__add("list:xowa.addon.scribunto.engine", "luaj", "lua");
		this.Lists__add("list:xowa.addon.math.renderer", Keyval_.new_("mathjax","MathJax"), Keyval_.new_("latex", "LaTeX"));
	}
	public void	Lists__add(String key, String... vals) {
		int len = vals.length;
		Keyval[] itms = new Keyval[len];
		for (int i = 0; i < len; i++) {
			itms[i] = Keyval_.new_(vals[i]);
		}
		Lists__add(key, itms);
	}
	public void	Lists__add(String key, Keyval... itms) {
		if (!list_hash.Has(key)) // ignore multiple calls from Init_by_wiki; EX: Xow_hdump_mode
			list_hash.Add(key, itms);
	}
	public Keyval[] Lists__get(String key) {
		return (Keyval[])list_hash.Get_by_or_fail(key);
	}
}
