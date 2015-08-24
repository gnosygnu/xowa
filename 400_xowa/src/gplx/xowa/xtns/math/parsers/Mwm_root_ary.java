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
package gplx.xowa.xtns.math.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
class Mwm_root_ary {
	private Mwm_tkn[] ary = Mwm_tkn_.Ary_empty; private int ary_max;
	public Mwm_tkn Get_at(int i) {return i < ary_max ? ary[i] : null;}
	public void Init(int expd_itms) {
		if (expd_itms > ary_max) {
			this.ary_max = expd_itms;
			this.ary = new Mwm_tkn[ary_max];
		}
		else {
			for (int i = 0; i < ary_max; ++i)
				ary[i] = null;
		}
	}
	public void Add(int idx, Mwm_tkn tkn) {
		if (idx >= ary_max) {
			int new_max = idx == 0 ? 2 : idx * 2;
			Mwm_tkn[] new_ary = new Mwm_tkn[new_max];
			for (int i = 0; i < ary_max; ++i)
				new_ary[i] = ary[i];
			this.ary = new_ary;
			this.ary_max = new_max;
		}
		ary[idx] = tkn;
	}
}