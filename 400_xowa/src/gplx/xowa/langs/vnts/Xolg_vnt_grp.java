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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xolg_vnt_grp {
	private final ListAdp list = ListAdp_.new_();
	public byte[] Text() {return text;} public void Text_(byte[] v) {text = v;} private byte[] text;
	public int Len() {return list.Count();}
	public Xolg_vnt_itm Get_at(int i) {return (Xolg_vnt_itm)list.FetchAt(i);}
	public void Add(Xolg_vnt_itm itm) {list.Add(itm);}
}
