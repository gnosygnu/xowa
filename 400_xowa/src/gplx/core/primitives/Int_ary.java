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
	public int Get_at_or_fail(int i) {
		if (i > -1 && i < len) return ary[i];
		else throw Err_.new_("core.int_ary", "index is invalid", "i", i, "len", len);
	}
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
	public int Idx_of(int find) {
		for (int i = len - 1; i > -1; --i) {
			if (ary[i] == find) return i;
		}
		return Not_found;
	}
	public boolean Del_from_end(int find) {
		int find_idx = Idx_of(find); if (find_idx == Not_found) return false;
		int last_idx = len - 1;
		for (int i = find_idx; i < last_idx; ++i)
			ary[i] = ary[i + 1];
		ary[last_idx] = 0;
		--len;
		return true;
	}
	public static final int Not_found = -1;
}
