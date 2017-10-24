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
package gplx.xowa.guis.cbks; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.core.gfobjs.*;
public class Xog_cbk_mgr {	// INSTANCE:app
	private Xog_cbk_wkr[] wkrs = Xog_cbk_wkr_.Ary_empty; private int wkrs_len = 0;
	public void Reg(Xog_cbk_wkr wkr) {
		this.wkrs = (Xog_cbk_wkr[])Array_.Resize_add_one(wkrs, wkrs_len, wkr);
		++wkrs_len;
	}
	public void Send_json(Xog_cbk_trg trg, String func, Gfobj_nde data) {
		for (int i = 0; i < wkrs_len; ++i) {
			Xog_cbk_wkr wkr = wkrs[i];
			wkr.Send_json(trg, func, data);
		}
	}
	public void Send_redirect(Xog_cbk_trg trg, String url) {
		this.Send_json(trg, "xo.server.redirect__recv", gplx.core.gfobjs.Gfobj_nde.New().Add_str("url", url));
	}
	public void Send_notify(Xog_cbk_trg trg, String text) {
		this.Send_json(trg, "xo.notify.show__recv", gplx.core.gfobjs.Gfobj_nde.New().Add_str("text", text).Add_str("status", "success"));
	}
}
