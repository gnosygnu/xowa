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
package gplx.xowa.addons.servers.https.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.servers.*; import gplx.xowa.addons.servers.https.*;
import gplx.xowa.htmls.bridges.*;
public class Xoa_util_addon implements Xoax_addon_itm, Xoax_addon_itm__json {
	public String Addon__key() {return ADDON__KEY;} private static final String ADDON__KEY = "xowa.app.util";
	public Bridge_cmd_itm[] Json_cmds() {
		return new Bridge_cmd_itm[]
		{ Xoa_util_bridge.Prototype
		};
	}
	public static Xoa_util_addon Get_by_app(Xoa_app app) {
		return (Xoa_util_addon)app.Addon_mgr().Itms__get_or_null(ADDON__KEY);
	}
}
