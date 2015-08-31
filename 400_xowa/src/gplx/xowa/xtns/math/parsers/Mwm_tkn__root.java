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
class Mwm_tkn__root implements Mwm_tkn {
	private final Mwm_root_reg root_reg;
	private final Mwm_root_ary root_ary = new Mwm_root_ary();
	private final Mwm_root_sub root_sub = new Mwm_root_sub();
	public Mwm_tkn__root(Mwm_tkn_mkr tkn_mkr) {
		this.tkn_mkr = tkn_mkr;
		this.root_reg = new Mwm_root_reg(this);
	}
	public Mwm_tkn__root Root() {return this;}
	public Mwm_tkn_mkr Tkn_mkr() {return tkn_mkr;} private final Mwm_tkn_mkr tkn_mkr;
	public int Tid() {return Mwm_tkn_.Tid__root;}
	public int Uid() {return Mwm_tkn_.Uid__root;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public void Src_end_(int v) {this.src_end = v;}
	public Mwm_tkn Init(Mwm_tkn__root root, int tid, int uid, int src_bgn, int src_end) {throw Err_.new_unsupported();}
	public Mwm_tkn Init_as_root(int src_bgn, int src_end) {
		this.src_bgn = src_bgn; this.src_end = src_end;
		int expd_len = (src_end - src_bgn) / 5;
		root_reg.Init(expd_len);
		root_ary.Init(expd_len);
		root_sub.Init(expd_len);
		root_reg.Add(Mwm_tkn_.Tid__root, Mwm_tkn_.Uid__root, src_bgn, src_end);
//			this.Regy__add(Mwm_tkn_.Tid__root, src_bgn, src_end, this);
		return this;
	}
	public int Subs__len() {return Regy__get_subs_len(Mwm_tkn_.Uid__root);}
	public Mwm_tkn Subs__get_at(int i) {return Regy__get_subs_tkn(Mwm_tkn_.Uid__root, i);}
	public void To_bry(Bry_bfr bfr, int indent) {
		Mwm_tkn_.Tkn_to_bry__bgn(bfr, indent, this);
		Mwm_tkn_.Tkn_to_bry__end_head(bfr);
	}
	public int Regy__add(int tid, int bgn, int end, Mwm_tkn tkn) {
		int uid = root_reg.Add(tid, Mwm_tkn_.Uid__root, bgn, end);
		if (tkn != null) {
			tkn.Init(this, tid, uid, bgn, end);
			root_ary.Add(uid, tkn);
		}
		root_sub.Add(Mwm_tkn_.Uid__root, uid);
		return uid;
	}
	public int Regy__get_subs_len(int uid) {return root_sub.Get_subs_len(uid);}
	public Mwm_tkn Regy__get_subs_tkn(int owner_uid, int sub_idx) {
		return Regy__get_tkn(root_sub.Get_at(owner_uid).Get_at(sub_idx));
	}
	public Mwm_tkn Regy__get_tkn(int uid) {
		Mwm_tkn rv = root_reg.Get_at(tkn_mkr, uid);
		return rv == null ? root_ary.Get_at(uid) : rv;
	}
	public void Regy__move(int new_owner_uid, int cur_uid) {
		int old_owner_uid = root_reg.Update_owner_id(cur_uid, new_owner_uid);
		if (!root_sub.Del_by_key_from_end(old_owner_uid, cur_uid)) throw Err_.new_("mwm.parse", "unable to find sub in owner", "old_owner_uid", old_owner_uid, "new_owner_uid", new_owner_uid, "cur_uid", cur_uid);
		root_sub.Add(new_owner_uid, cur_uid);
	}
	public void Regy__move_to_end(int src_uid, int trg_uid) {
		Int_ary subs_ary = root_sub.Get_at(Mwm_tkn_.Uid__root);
		int bgn_idx = subs_ary.Idx_of(src_uid);
		int subs_len = subs_ary.Len();
		for (int i = bgn_idx + 1; i < subs_len; ++i) {
			int sub_uid = subs_ary.Get_at(i);
			Regy__move(trg_uid, sub_uid);
		}
	}
	public void Regy__update_end(int uid, int end) {
		if (root_reg.Update_end(uid, end))
			root_ary.Update_end(uid, end);
	}
}
