/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.indicators;

import gplx.xowa.Xoa_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.addons.Xoax_addon_itm;
import gplx.xowa.addons.Xoax_addon_itm__init;

public class Indicator_addon implements Xoax_addon_itm, Xoax_addon_itm__init {
	public void Init_addon_by_app(Xoa_app app) {}
	public void Init_addon_by_wiki(Xow_wiki wiki) {
		wiki.Hxtn_mgr().Reg_wkr(new Indicator_hxtn_page_wkr(wiki.Hxtn_mgr().Blob_tbl()));
	}

	public String Addon__key() {return "xowa.indicators";}
}