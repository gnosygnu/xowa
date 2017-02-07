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
package gplx.xowa.xtns.math.texvcs.tkns; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
import gplx.core.primitives.*;
public class Texvc_regy_sub {
	private Int_ary[] ary = new Int_ary[0]; private int ary_max;
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
	public Int_ary Get_subs_or_fail(int uid) {
		Int_ary rv = Get_subs_or_null(uid); if (rv == null) throw Err_.new_("math.texvc", "token does not have any subs", "uid", uid);
		return rv;
	}
	private Int_ary Get_subs_or_null(int uid) {
		return uid > -1 && uid < ary_max ? ary[uid] : null;	// NOTE: do not fail on bad bounds check; can pass "uid" larger than "ary_max"; occurs when estimate of "/ 5" is wrong and many leaf tokesn created at end;
	}
	public int Get_subs_at(int uid, int sub_idx) {
		Int_ary subs_ary = Get_subs_or_fail(uid);
		return subs_ary.Get_at_or_fail(sub_idx);
	}
	public int Get_subs_len(int uid) {
		Int_ary subs_ary = Get_subs_or_null(uid);
		return subs_ary == null ? 0 : subs_ary.Len();
	}
	public void Add(int uid, int sub_uid) {
		if (uid >= ary_max) {
			int new_max = uid == 0 ? 2 : uid * 2;
			Int_ary[] new_ary = new Int_ary[new_max];
			for (int i = 0; i < ary_max; ++i)
				new_ary[i] = ary[i];
			this.ary = new_ary;
			this.ary_max = new_max;
		}
		Int_ary subs_ary = ary[uid];
		if (subs_ary == null) {
			subs_ary = new Int_ary(2);
			ary[uid] = subs_ary;
		}
		subs_ary.Add(sub_uid);
	}
	public void Del(int uid, int sub_uid) {
		Int_ary subs_ary = Get_subs_or_fail(uid);
		if (!subs_ary.Del_from_end(sub_uid)) throw Err_.new_("math.texvc", "sub_uid does not exist", "uid", uid, "sub_uid", sub_uid);
	}
}