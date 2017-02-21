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
package gplx.xowa.addons.bldrs.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.xowa.specials.*; import gplx.xowa.htmls.bridges.*;
import gplx.xowa.addons.bldrs.infos.*; import gplx.xowa.addons.bldrs.xodirs.*;
public class Xobc_task_addon implements Xoax_addon_itm, Xoax_addon_itm__special, Xoax_addon_itm__json, Xoax_addon_itm__init {
	public Xobc_xodir_mgr Xodir_mgr() {return xodir_mgr;} private Xobc_xodir_mgr xodir_mgr;
	public void Xodir_mgr_(Xobc_xodir_mgr v) {this.xodir_mgr = v;}
	public void Init_addon_by_app(Xoa_app app) {
		this.xodir_mgr = new Xobc_xodir_mgr__pc(app);
	}
	public void Init_addon_by_wiki(Xow_wiki wiki) {}
	public Xow_special_page[] Special_pages() {
		return new Xow_special_page[]
		{ Xobc_task_special.Prototype
		, Xobc_info_special.Prototype
		};
	}
	public Bridge_cmd_itm[] Json_cmds() {
		return new Bridge_cmd_itm[]
		{ gplx.xowa.addons.bldrs.centrals.Xobc_task_bridge.Prototype
		};
	}

	public String Addon__key() {return ADDON__KEY;} public static final    String ADDON__KEY = "xowa.imports.downloads";
}
