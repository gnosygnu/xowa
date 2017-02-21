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
package gplx.xowa.wikis.pages.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_xtn_skin_fmtr_arg implements gplx.core.brys.Bfr_arg {
	private Xoae_page page; private byte xtn_skin_tid;
	public Xopg_xtn_skin_fmtr_arg(Xoae_page page, byte xtn_skin_tid) {
		this.page = page; this.xtn_skin_tid = xtn_skin_tid;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Xopg_xtn_skin_mgr mgr = page.Html_data().Xtn_skin_mgr();
		int len = mgr.Count();
		for (int i = 0; i < len; ++i) {
			Xopg_xtn_skin_itm itm = mgr.Get_at(i);
			if (itm.Tid() == xtn_skin_tid)
				itm.Write(bfr, page);
		}
	}
}
