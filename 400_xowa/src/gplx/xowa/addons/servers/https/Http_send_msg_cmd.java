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
public class Http_send_msg_cmd implements gplx.xowa.htmls.bridges.Bridge_cmd_itm {
	public void Init_by_app(Xoa_app app) {}
	public String Exec(gplx.langs.jsons.Json_nde data) {
		gplx.langs.jsons.Json_nde jnde = (gplx.langs.jsons.Json_nde)data.Get_as_itm_or_null(Bry_.new_a7("msg"));
		Http_long_poll_cmd.Instance.Send_msg(jnde.Print_as_json());
		return "{}";
	}

	public byte[] Key() {return BRIDGE_KEY;}
	public static final byte[] BRIDGE_KEY = Bry_.new_a7("send_msg");
}
