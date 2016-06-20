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
import gplx.xowa.guis.cbks.*; import gplx.core.gfobjs.*;
public class Xog_cbk_wkr__http implements Xog_cbk_wkr {
	public Object Send_json(Xog_cbk_trg trg, String func, Gfobj_nde data) {return null;}
	public void Send_prog(String head) {
		Http_long_poll_cmd.Instance.Send_msg(head);
	}
	public static final    Xog_cbk_wkr__http Instance = new Xog_cbk_wkr__http(); Xog_cbk_wkr__http() {}
}
