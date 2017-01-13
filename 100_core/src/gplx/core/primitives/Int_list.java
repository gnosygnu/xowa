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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Int_list {
	private int capacity = 0;
	private int[] ary = Int_.Ary_empty; private int ary_len, ary_max;
	public Int_list()				{this.capacity = 0;        this.ary = Int_.Ary_empty;}
	public Int_list(int capacity)	{this.capacity = capacity; this.ary = new int[capacity];}
	public void Add(int uid) {
		int new_len = ary_len + 1;
		if (new_len > ary_max) {
			ary_max += 16;
			int[] new_ary = new int[ary_max];
			Int_.Ary_copy_to(ary, ary_len, new_ary);
			ary = new_ary;
		}
		ary[ary_len] = uid;
		ary_len = new_len;
	}
	public int Len() {return ary_len;}
	public int Get_at(int i) {return ary[i];}
	public void Clear() {
		if (ary_len > capacity) {
			ary = (capacity == 0) ? Int_.Ary_empty : new int[capacity];
		}
		ary_len = ary_max = 0;
	}
	public int[] To_ary() {
		int[] rv = new int[ary_len];
		for (int i = 0; i < ary_len; i++)
			rv[i] = ary[i];
		return rv;
	}
	public static Int_list new_(int... ary) {
		Int_list rv = new Int_list();
		int len = ary.length;
		rv.ary = ary; rv.ary_len = len; rv.ary_max = len;
		return rv;
	}
}
