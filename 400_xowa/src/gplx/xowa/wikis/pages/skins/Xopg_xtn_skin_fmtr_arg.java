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
package gplx.xowa.wikis.pages.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_xtn_skin_fmtr_arg implements Bry_fmtr_arg {
	private Xoae_page page; private byte xtn_skin_tid;
	public Xopg_xtn_skin_fmtr_arg(Xoae_page page, byte xtn_skin_tid) {
		this.page = page; this.xtn_skin_tid = xtn_skin_tid;
	}
	public void Fmt__do(Bry_bfr bfr) {
		Xopg_xtn_skin_mgr mgr = page.Html_data().Xtn_skin_mgr();
		int len = mgr.Count();
		for (int i = 0; i < len; ++i) {
			Xopg_xtn_skin_itm itm = mgr.Get_at(i);
			if (itm.Tid() == xtn_skin_tid)
				itm.Write(bfr, page);
		}
	}
}
