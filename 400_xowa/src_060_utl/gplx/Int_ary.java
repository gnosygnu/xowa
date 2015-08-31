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
public class Int_ary {
	private int[] ary; private int len, max;
	public Int_ary(int max) {
		this.len = 0;
		this.max = max;
		this.ary = new int[max];
	}
	public int[] Ary() {return ary;}
	public void Clear() {
		for (int i = 0; i < len; ++i)
			ary[i] = 0;
		len = 0;
	}
	public int Len() {return len;}
	public void Add(int v) {
		if (len == max) {
			int new_max = max * 2;
			int[] new_ary = new int[new_max];
			for (int i = 0; i < len; ++i)
				new_ary[i] = ary[i];
			this.ary = new_ary;
			this.max = new_max;
		}
		ary[len] = v;
		++len;
	}
	public int Pop_or(int or) {
		if (len == 0) return or;
		int rv = ary[len - 1];
		--len;
		return rv;
	}
	public int Idx_of(int key) {
		for (int i = len - 1; i > -1; --i) {
			int val = ary[i];
			if (val == key) return i;
		}
		return -1;
	}
	public boolean Del_by_key_from_end(int key) {
		int key_idx = Idx_of(key); if (key_idx == -1) return false;
		int last_idx = len - 1;
		for (int i = key_idx; i < last_idx; ++i)
			ary[i] = ary[i + 1];
		ary[last_idx] = 0;
		--len;
		return true;
	}
	public int Get_at(int i) {return ary[i];}
}
