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
package gplx.xowa.parsers.lnkis.redlinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*;
import gplx.core.primitives.*;
public class Xopg_redlink_idx_list {
	private final Int_list list = new Int_list();
	public int Len() {return list.Len();}
	public int Max() {return max;} private int max;
	public int Get_at(int i) {return list.Get_at(i);}
	public void Clear() {
		list.Clear();
		max = 0;
	}
	public void Add(int i) {
		list.Add(i);
		if (i > max) max = i;
	}
}
