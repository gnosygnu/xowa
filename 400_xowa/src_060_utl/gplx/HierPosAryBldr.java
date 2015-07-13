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
import gplx.core.strings.*;
public class HierPosAryBldr {
	public void Init() {
		int ary_max = ary.length;
		for (int i = 0; i < ary_max; i++)
			ary[i] = 0;
		aryIdx = -1;
		root = 0;
	}
	public void MoveDown() {
		aryIdx += 1;
		if (aryIdx == 0)
			ary[aryIdx] = root;
		else
			ary[aryIdx] = 0;
	}
	public void MoveUp() {
		aryIdx -= 1;
		MoveNext();
	}
	public void MoveNext() {
		if (aryIdx == -1)
			root += 1;
		else
			ary[aryIdx] += 1;
	}
	public boolean Dirty() {return aryIdx > -1 || root > 0;}
	public int[] XtoIntAry() {
		if (aryIdx == -1) return Int_.Ary_empty;
		int[] rv = new int[aryIdx + 1];
		for (int i = 0; i < aryIdx + 1; i++)
			rv[i] = ary[i];
		return rv;
	}
	public String XtoStr() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < aryIdx; i++)
			sb.Add_spr_unless_first(Int_.Xto_str(ary[i]), " ", i);
		return sb.XtoStr();
	}
	int[] ary; int aryIdx = -1; int root = -1;
	public HierPosAryBldr(int ary_max)	{ary = new int[ary_max]; this.Init();}
}
