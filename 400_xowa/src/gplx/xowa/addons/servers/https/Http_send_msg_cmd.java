/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
	public static final    byte[] BRIDGE_KEY = Bry_.new_a7("send_msg");
}
