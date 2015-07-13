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
package gplx.xowa.html.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
class Xoh_skin_regy {
	private final Ordered_hash hash = Ordered_hash_.new_();
	public int Len() {return hash.Count();}
	public Xoh_skin_itm Get_at(int i) {return (Xoh_skin_itm)hash.Get_at(i);}
	public Xoh_skin_itm Get_by_key(String key) {return (Xoh_skin_itm)hash.Get_by(key);}
	public void Set(String key, String fmt) {
		Xoh_skin_itm itm = Get_by_key(key);
		if (itm == null) {
			itm = new Xoh_skin_itm(key, fmt);
			Add(itm);
		}
		else
			itm.Fmt_(fmt);
	}
	public void Add(Xoh_skin_itm itm)		{hash.Add(itm.Key(), itm);}
}
