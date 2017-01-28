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
public class Bool_ary_bldr {
	private final    boolean[] ary;
	public Bool_ary_bldr(int len) {
		this.ary = new boolean[len];
	}
	public Bool_ary_bldr Set_many(int... v) {
		int len = v.length;
		for (int i = 0; i < len; i++)
			ary[v[i]] = true;
		return this;
	}
	public Bool_ary_bldr Set_rng(int bgn, int end) {
		for (int i = bgn; i <= end; i++)
			ary[i] = true;
		return this;
	}
	public boolean[] To_ary() {
		return ary;
	}
	public static Bool_ary_bldr New_u8() {return new Bool_ary_bldr(256);}
}
