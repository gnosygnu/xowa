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
package gplx.xowa.xtns.pfuncs.exprs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
class Func_tkn_stack {
	private Func_tkn[] ary = new Func_tkn[0]; private int len = 0, max = 0;
	public void Clear() {len = 0;}
	public int Len() {return len;}
	public Func_tkn Get_at_last() {return len == 0 ? null : ary[len - 1];}
	public Func_tkn Pop() {
		int new_len = len - 1;
		Func_tkn rv = ary[new_len];
		len = new_len;
		return rv;
	}
	public void Push(Func_tkn v) {
		int new_len = len + 1;
		if (new_len > max) {
			max = new_len * 2;
			ary = (Func_tkn[])Array_.Resize(ary, max);
		}
		ary[len] = v;
		len = new_len;
	}
}
