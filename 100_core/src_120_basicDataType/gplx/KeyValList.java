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
package gplx;
public class KeyValList {//20101217
	public int Count() {return list.Count();} ListAdp list = ListAdp_.new_();
	public void Clear() {list.Clear();}
	public KeyVal GetAt(int i) {return (KeyVal)list.FetchAt(i);}
	public KeyValList Add(String key, Object val) {list.Add(KeyVal_.new_(key, val)); return this;}
	public KeyVal[] XtoAry() {return (KeyVal[])list.XtoAry(KeyVal.class);}
	public String XtoStr() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < list.Count(); i++) {
			KeyVal kv = (KeyVal)list.FetchAt(i);
			sb.Add_spr_unless_first(kv.Key(), " ", i);
			sb.Add("=").Add(kv.Val_to_str_or_empty());
		}
		return sb.XtoStr();
	}
	public static KeyValList args_(String key, Object val) {return new KeyValList().Add(key, val);}
}
