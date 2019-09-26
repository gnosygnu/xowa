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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.addons.*;
import gplx.xowa.bldrs.wkrs.*;
public class Hiero_addon implements Xoax_addon_itm, Xoax_addon_itm__init {
	public void Init_addon_by_app(Xoa_app app) {}
	public void Init_addon_by_wiki(Xow_wiki wiki) {
		Hiero_hdump_wkr hdump_wkr = new Hiero_hdump_wkr();
		wiki.Html__hdump_mgr().Wkrs().Add(hdump_wkr.Key(), hdump_wkr);
	}

	public String Addon__key() {return "xowa.hieros";}
}
