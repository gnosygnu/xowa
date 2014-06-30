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
package gplx.lists; import gplx.*;
public class HashAdp_list extends HashAdp_base {
	@gplx.New public ListAdp Fetch(Object key) {return ListAdp_.as_(Fetch_base(key));}
	public ListAdp FetchOrNew(Object key) {
		ListAdp rv = Fetch(key);
		if (rv == null) {
			rv = ListAdp_.new_();
			Add_base(key, rv);
		}
		return rv;
	}
	public void AddInList(Object key, Object val) {
		ListAdp list = FetchOrNew(key);
		list.Add(val);
	}
	public void DelInList(Object key, Object val) {
		ListAdp list = Fetch(key);
		if (list == null) return;
		list.Del(val);
		if (list.Count() == 0) Del(key);
	}
	public static HashAdp_list new_() {return new HashAdp_list();} HashAdp_list() {}
}
