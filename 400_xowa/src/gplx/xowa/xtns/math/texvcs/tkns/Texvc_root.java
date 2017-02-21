/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.math.texvcs.tkns; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
import gplx.core.primitives.*;
public class Texvc_root implements Texvc_tkn {
	private final    Texvc_regy_tkn regy_tkn;
	private final    Texvc_regy_nde regy_nde = new Texvc_regy_nde();
	private final    Texvc_regy_sub regy_sub = new Texvc_regy_sub();
	public Texvc_root() {
		this.regy_tkn = new Texvc_regy_tkn(this);
	}
	public Texvc_root Root() {return this;}
	public int Tid() {return Texvc_tkn_.Tid__root;}
	public int Uid() {return Uid__root;}
	public byte[] Src() {return src;} private byte[] src;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public void Src_end_(int v) {this.src_end = v;}
	public Texvc_tkn Init(Texvc_root root, int tid, int uid, int src_bgn, int src_end) {throw Err_.new_unsupported();}
	public Texvc_tkn Init_as_root(Texvc_tkn_mkr tkn_mkr, byte[] src, int src_bgn, int src_end) {
		this.src = src; this.src_bgn = src_bgn; this.src_end = src_end;
		int expd_len = (src_end - src_bgn) / 5;	// estimate # of tkns by dividing src_len by 5
		regy_tkn.Init(expd_len, tkn_mkr);
		regy_nde.Init(expd_len);
		regy_sub.Init(expd_len);
		regy_tkn.Add(Texvc_tkn_.Tid__root, Uid__root, Texvc_tkn_mkr.Singleton_id__null, src_bgn, src_end);
		return this;
	}
	public int Subs__len() {return Regy__get_subs_len(Uid__root);}
	public Texvc_tkn Subs__get_at(int i) {return Regy__get_subs_tkn(Uid__root, i);}
	public int Regy__add(int tid, int singleton_id, int bgn, int end, Texvc_tkn nde_tkn) {
		int uid = regy_tkn.Add(tid, Uid__root, singleton_id, bgn, end);
		if (nde_tkn != null) {
			nde_tkn.Init(this, tid, uid, bgn, end);
			regy_nde.Add(uid, nde_tkn);
		}
		regy_sub.Add(Uid__root, uid);
		return uid;
	}
	public int Regy__get_subs_len(int uid) {return regy_sub.Get_subs_len(uid);}
	public Texvc_tkn Regy__get_subs_tkn(int uid, int sub_idx) {return Regy__get_tkn(regy_sub.Get_subs_at(uid, sub_idx));}
	public Texvc_tkn Regy__get_tkn(int uid) {
		Texvc_tkn rv = regy_tkn.Get_singleton_or_null(uid);
		return rv == null ? regy_nde.Get_at_or_fail(uid) : rv;
	}
	public void Regy__move(int uid, int new_owner_uid) {
		int old_owner_uid = regy_tkn.Update_owner_id(uid, new_owner_uid);
		regy_sub.Del(old_owner_uid, uid);
		regy_sub.Add(new_owner_uid, uid);
	}
	public void Regy__take_from_root_end(int uid) {
		Int_ary root_subs = regy_sub.Get_subs_or_fail(Uid__root);
		int uid_idx = root_subs.Idx_of(uid); if (uid_idx == Int_ary.Not_found) throw Err_.new_("math.texvc", "unable to find tkn in root", "uid", uid);
		int subs_len = root_subs.Len();
		int bgn_idx = uid_idx + 1;	// +1 to skip current uid_idx
		for (int i = bgn_idx; i < subs_len; ++i) {
			int sub_uid = root_subs.Get_at_or_fail(bgn_idx);	// NOTE: use bgn_idx, not i, b/c move will shorten subs
			Regy__move(sub_uid, uid);
		}
	}
	public void Regy__update_end(int uid, int end) {
		if (regy_tkn.Update_end(uid, end))
			regy_nde.Update_end(uid, end);
	}
	public String Print_tex_str(Bry_bfr bfr) {Print_tex_bry(bfr, src, 0); return bfr.To_str_and_clear();}
	public void Print_tex_bry(Bry_bfr bfr, byte[] src, int indent) {
		int subs_len = Subs__len();
		for (int i = 0; i < subs_len; ++i) {
			Texvc_tkn sub_tkn = Subs__get_at(i);
			sub_tkn.Print_tex_bry(bfr, src, indent + 1);
		}
	}
	public String Print_dbg_str(Bry_bfr bfr) {Print_dbg_bry(bfr, 0); return bfr.To_str_and_clear();}
	public void Print_dbg_bry(Bry_bfr bfr, int indent) {
		int len = this.Subs__len();
		for (int i = 0; i < len; ++i) {
			Texvc_tkn sub = this.Subs__get_at(i);
			sub.Print_dbg_bry(bfr, 0);
		}
	}
	private static final int Uid__root = 0;
}
