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
package gplx.core.strings; import gplx.*; import gplx.core.*;
public class String_ring {
	String[] ary = String_.Ary_empty;
	public int Len() {return len;} int len;
	public String_ring Max_(int v) {
		if (v != max) {
			ary = new String[v];
			max = v;
		}
		return this;
	} int max;
	public void Clear() {
		for (int i = 0; i < max; i++) {
			ary[i] = null;
		}
		len = nxt = 0;
	}
	int nxt;
	public void Push(String v) {
		int idx = nxt++;
		if (idx == max) {
			idx = 0;
		}
		if (nxt == max) {
			nxt = 0;
		}
		ary[idx] = v;
		if (len < max)
			++len;
	}
	public String[] Xto_str_ary() {
		String[] rv = new String[len];
		int ary_i = nxt - 1;
		for (int rv_i = len - 1; rv_i > -1; rv_i--) {
			if (ary_i == -1) {
				ary_i = max - 1;
			} 
			rv[rv_i] = ary[ary_i];
			--ary_i;
		}
		return rv;
	}
//	public String Get_at(int i) {return }
}
