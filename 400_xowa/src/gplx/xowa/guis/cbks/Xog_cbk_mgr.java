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
}
