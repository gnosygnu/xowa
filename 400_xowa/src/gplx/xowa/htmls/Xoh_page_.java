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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*; import gplx.xowa.wikis.pages.htmls.*;
public class Xoh_page_ {
	public static byte[] Save_sidebars(Bry_bfr tmp_bfr, Xoae_page page, Xopg_html_data html_data) {
		Xopg_xtn_skin_mgr mgr = html_data.Xtn_skin_mgr();
		int len = mgr.Count();
		boolean sidebar_exists = false;
		for (int i = 0; i < len; ++i) {
			Xopg_xtn_skin_itm itm = mgr.Get_at(i);
			if (itm.Tid() == Xopg_xtn_skin_itm_tid.Tid_sidebar) {
				sidebar_exists = true;
				itm.Write(tmp_bfr, page);
			}
		}
		return sidebar_exists ? tmp_bfr.To_bry_and_clear() : null;
	}
}
