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
package gplx.xowa.pages.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.pages.*;
public class Xopg_xtn_skin_mgr {
	private OrderedHash hash = OrderedHash_.new_bry_();
	public int Count() {return hash.Count();}
	public void Add(Xopg_xtn_skin_itm itm) {hash.Add_if_new(itm.Key(), itm);}
	public Xopg_xtn_skin_itm Get_at(int i) {return (Xopg_xtn_skin_itm)hash.FetchAt(i);}
	public Xopg_xtn_skin_itm Get_or_null(byte[] key) {return (Xopg_xtn_skin_itm)hash.Fetch(key);}
	public void Clear() {hash.Clear();}
}
