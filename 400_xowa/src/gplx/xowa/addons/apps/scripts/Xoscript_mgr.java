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
package gplx.xowa.addons.apps.scripts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.core.scripts.*;
import gplx.xowa.wikis.pages.tags.*;
import gplx.xowa.addons.apps.scripts.apis.*; import gplx.xowa.addons.apps.scripts.xtns.*;
public class Xoscript_mgr {
	private Io_url root_dir;
	private Xoscript_xtn_mgr xtn_mgr;
	public void Init(Xow_wiki wiki) {
		// check script_dir
		this.root_dir = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("bin", "any", "script");
		if (!Io_mgr.Instance.ExistsDir(root_dir)) return;
		this.xtn_mgr = new Xoscript_xtn_mgr(root_dir);
	}
	public void Write(Bry_bfr rv, Xow_wiki wiki, Xoa_page page) {
		// init
		if (!wiki.Html__wtr_mgr().Scripting_enabled()) return;
		this.Init(wiki);
		if (xtn_mgr == null) return;

		xtn_mgr.Load_root();
		xtn_mgr.Run(rv, wiki, page);
	}	
}
