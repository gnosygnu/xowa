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
package gplx.xowa.addons.servers.https; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.servers.*;
import gplx.xowa.guis.cbks.*; import gplx.core.gfobjs.*; import gplx.xowa.guis.cbks.swts.*;
public class Xog_cbk_wkr__http implements Xog_cbk_wkr {
	private final    Gfobj_wtr__json__browser json_wtr = new Gfobj_wtr__json__browser();
	public Object Send_json(Xog_cbk_trg trg, String func, Gfobj_nde data) {
		String script = json_wtr.Write_as_func__drd(func, data);
		Http_long_poll_cmd.Instance.Send_msg(script);
		return null;
	}
	public void Send_prog(String head) {
		Http_long_poll_cmd.Instance.Send_msg(head);
	}
	public static final    Xog_cbk_wkr__http Instance = new Xog_cbk_wkr__http(); Xog_cbk_wkr__http() {}
}
