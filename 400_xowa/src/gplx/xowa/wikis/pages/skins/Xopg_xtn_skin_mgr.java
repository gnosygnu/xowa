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
public class Xopg_xtn_skin_mgr {
	private Ordered_hash hash = Ordered_hash_.New_bry();
	public int Count() {return hash.Count();}
	public void Add(Xopg_xtn_skin_itm itm) {hash.Add_if_dupe_use_1st(itm.Key(), itm);}
	public Xopg_xtn_skin_itm Get_at(int i) {return (Xopg_xtn_skin_itm)hash.Get_at(i);}
	public Xopg_xtn_skin_itm Get_or_null(byte[] key) {return (Xopg_xtn_skin_itm)hash.Get_by(key);}
	public void Clear() {hash.Clear();}
}
