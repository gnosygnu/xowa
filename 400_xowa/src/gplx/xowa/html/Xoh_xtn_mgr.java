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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
public class Xoh_xtn_mgr {
	private OrderedHash itms = OrderedHash_.new_bry_();
	public Xoh_xtn_itm Get_or_null(byte[] key) {return (Xoh_xtn_itm)itms.Fetch(key);}
	public void Clear() {itms.Clear();}
	public void Add(Xoh_xtn_itm itm) {itms.Add(itm.Key(), itm);}
	public void Exec() {
		int len = itms.Count();
		for (int i = 0; i < len; i++) {
			Xoh_xtn_itm itm = (Xoh_xtn_itm)itms.FetchAt(i);
			itm.Exec();
		}
	}
}
