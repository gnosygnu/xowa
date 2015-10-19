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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_tab_close_mgr {
	private List_adp list = List_adp_.new_();
	public void Clear() {list.Clear();}
	public void Add(Xog_tab_close_lnr lnr) {list.Add(lnr);} 
	public int Len() {return list.Count();} 
	public Xog_tab_close_lnr Get_at(int i) {return (Xog_tab_close_lnr)list.Get_at(i);}
	public boolean When_close(Xog_tab_itm tab, Xoa_url url) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Xog_tab_close_lnr lnr = Get_at(i);
			if (!lnr.When_close(tab, url)) return false;
		}
		return true;
	}
}
