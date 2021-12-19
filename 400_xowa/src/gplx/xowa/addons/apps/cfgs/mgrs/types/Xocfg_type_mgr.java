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
package gplx.xowa.addons.apps.cfgs.mgrs.types;
import gplx.types.commons.KeyVal;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
public class Xocfg_type_mgr {
	private final Ordered_hash list_hash = Ordered_hash_.New();
	public Xocfg_type_mgr() {
		this.Lists__add("list:xowa.app.startup.window.mode", "previous", "maximized", "absolute", "relative", "default");
		this.Lists__add("list:xowa.app.startup.pages.type", "blank", "xowa", "previous", "custom");
		this.Lists__add("list:xowa.gui.html_box.page_load_mode", "mem", "url");
		this.Lists__add("list:xowa.html.portal.missing_class", KeyVal.NewStr("", "Show as blue link"), KeyVal.NewStr("new", "Show as red link"), KeyVal.NewStr("xowa_display_none", "Hide"));
		this.Lists__add("list:xowa.html.tidy.engine", "tidy", "jtidy", "vnu");
		this.Lists__add("list:xowa.bldr.db.zip_mode", KeyVal.NewStr("raw", "text"), KeyVal.NewStr("gzip"), KeyVal.NewStr("bzip2"), KeyVal.NewStr("xz"));
		this.Lists__add("list:xowa.addon.category.catpage.missing_class", "normal", "hide", "red_link");
		this.Lists__add("list:xowa.addon.http_server.file_retrieve_mode", KeyVal.NewStr("wait"), KeyVal.NewStr("skip"), KeyVal.NewStr("async_server", "async server"));
		this.Lists__add("list:xowa.addon.scribunto.engine", "luaj", "lua");
		this.Lists__add("list:xowa.addon.math.renderer", KeyVal.NewStr("mathjax","MathJax"), KeyVal.NewStr("latex", "LaTeX"));
		this.Lists__add("list:xowa.app.dbs.sqlite.read_only_detection", KeyVal.NewStr("basic_file", "Basic - File only"), KeyVal.NewStr("basic_file_and_dirs", "Basic - File and parent dirs"), KeyVal.NewStr("perms_file", "Permissions - File"));
	}
	public void	Lists__add(String key, String... vals) {
		int len = vals.length;
		KeyVal[] itms = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			itms[i] = KeyVal.NewStr(vals[i]);
		}
		Lists__add(key, itms);
	}
	public void	Lists__add(String key, KeyVal... itms) {
		if (!list_hash.Has(key)) // ignore multiple calls from Init_by_wiki; EX: Xow_hdump_mode
			list_hash.Add(key, itms);
	}
	public KeyVal[] Lists__get(String key) {
		return (KeyVal[])list_hash.GetByOrFail(key);
	}
}
