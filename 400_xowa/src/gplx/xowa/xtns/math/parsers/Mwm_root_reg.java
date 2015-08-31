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
class Mwm_root_reg {
	private int[] ary = Int_.Ary_empty; private int ary_len, ary_max; private int itms_len;
	private final Mwm_tkn__root root;
	public int Len() {return itms_len;}
	public Mwm_root_reg(Mwm_tkn__root root) {
		this.root = root;
	}
	public void Init(int expd_itms) {
		int expd_max = expd_itms * Idx__slots;
		if (expd_max > ary_max) {
			this.ary = new int[expd_max];
			this.ary_max = expd_max;
		}
		else {
			for (int i = 0; i < ary_len; ++i)
				ary[i] = 0;
		}
		this.ary_len = 0;
		this.itms_len = 0;
	}
	public Mwm_tkn Get_at(Mwm_tkn_mkr tkn_mkr, int uid) {
		int idx = uid * Idx__slots;
		int tid = ary[idx];
		return Mwm_tkn_.Tid_is_node(tid) ? null: tkn_mkr.Make_leaf(root, tid, uid, ary[idx + Idx__bgn], ary[idx + Idx__end]);
	}
	public int Get_owner_id(int uid) {
		int idx = uid * Idx__slots;
		return ary[idx + Idx__oid];
	}
	public int Add(int tid, int oid, int bgn, int end) {
		int new_ary_len = ary_len + Idx__slots;
		if (new_ary_len >= ary_max) {
			int new_max = ary_max == 0 ? Idx__slots : ary_max * 2;
			int[] new_ary = new int[new_max];
			for (int i = 0; i < ary_len; ++i)
				new_ary[i] = ary[i];
			this.ary = new_ary;
			this.ary_max = new_max;
		}
		int idx = ary_len;
		int uid = idx / Idx__slots;
		ary[idx           ] = tid;
		ary[idx + Idx__oid] = oid;
		ary[idx + Idx__bgn] = bgn;
		ary[idx + Idx__end] = end;
		this.ary_len = new_ary_len;
		++itms_len;
		return uid;
	}
	public boolean Update_end(int uid, int end) {
		int idx = uid * Idx__slots;
		ary[idx + Idx__end] = end;
		return Mwm_tkn_.Tid_is_node(ary[idx]);
	}
	public int Update_owner_id(int cur_uid, int new_owner_uid) {	// return old owner_id
		int cur_idx = cur_uid * Idx__slots;
		int cur_owner_uid_idx = cur_idx + Idx__oid;
		int old_owner_uid = ary[cur_owner_uid_idx];
		ary[cur_owner_uid_idx] = new_owner_uid;
		return old_owner_uid;
	}
	public void Change_owner(Mwm_tkn cur_owner, Mwm_tkn new_owner) {
	}
	private static final int
		Idx__oid	= 1
	,	Idx__bgn	= 2
	,	Idx__end	= 3
	,	Idx__slots	= 4
	;		
}