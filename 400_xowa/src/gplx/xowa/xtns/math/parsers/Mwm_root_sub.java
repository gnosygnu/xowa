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
class Mwm_root_sub {
	private Int_ary[] ary = new Int_ary[0]; private int ary_max;
	public Int_ary Get_at(int i) {return i < ary_max ? ary[i] : null;}
	public int Get_subs_len(int uid) {
		Int_ary subs_ary = uid < ary_max ? ary[uid] : null;
		return subs_ary == null ? 0 : subs_ary.Len();
	}
	public void Init(int expd_itms) {
		if (expd_itms > ary_max) {
			this.ary_max = expd_itms;
			this.ary = new Int_ary[ary_max];
		}
		else {
			for (int i = 0; i < ary_max; ++i)
				ary[i] = null;
		}
	}
	public void Add(int owner_uid, int sub_uid) {
		if (owner_uid >= ary_max) {
			int new_max = owner_uid == 0 ? 2 : owner_uid * 2;
			Int_ary[] new_ary = new Int_ary[new_max];
			for (int i = 0; i < ary_max; ++i)
				new_ary[i] = ary[i];
			this.ary = new_ary;
			this.ary_max = new_max;
		}
		Int_ary subs_ary = ary[owner_uid];
		if (subs_ary == null) {
			subs_ary = new Int_ary(2);
			ary[owner_uid] = subs_ary;
		}
		subs_ary.Add(sub_uid);
	}
}