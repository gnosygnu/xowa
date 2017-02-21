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
public class Texvc_regy_tkn {
	private int[] ary = Int_.Ary_empty; private int ary_max; private int itms_len;
	private final Texvc_root root; private Texvc_tkn_mkr tkn_mkr;
	public int Len() {return itms_len;}
	public Texvc_regy_tkn(Texvc_root root) {
		this.root = root;
	}
	public void Init(int expd_itms, Texvc_tkn_mkr tkn_mkr) {
		this.tkn_mkr = tkn_mkr;
		int expd_max = expd_itms * Idx__slots;
		if (expd_max > ary_max) {
			this.ary = new int[expd_max];
			this.ary_max = expd_max;
		}
		else {
			for (int i = 0; i < ary_max; ++i)
				ary[i] = 0;
		}
		this.itms_len = 0;
	}
	public int Add(int type_id, int owner_id, int singleton_id, int src_bgn, int src_end) {
		int idx = itms_len * Idx__slots;
		if (idx >= ary_max) {
			int new_max = ary_max == 0 ? Idx__slots * 4 : ary_max * 2;	// "* 4": if empty, allocate 4 items; applies to root
			int[] new_ary = new int[new_max];
			for (int i = 0; i < ary_max; ++i)
				new_ary[i] = ary[i];
			this.ary = new_ary;
			this.ary_max = new_max;
		}
		int uid = itms_len;
		ary[idx]						= type_id;
		ary[idx + Idx__owner_id]		= owner_id;
		ary[idx + Idx__singleton_id]	= singleton_id;
		ary[idx + Idx__src_bgn]			= src_bgn;
		ary[idx + Idx__src_end]			= src_end;
		++itms_len;
		return uid;
	}
	public Texvc_tkn Get_singleton_or_null(int uid) {
		int idx = uid * Idx__slots; if (idx >= ary_max) throw Make_err_invalid_uid(uid);
		int singleton_id = ary[idx + Idx__singleton_id];
		return singleton_id == Texvc_tkn_mkr.Singleton_id__null ? null : tkn_mkr.Get_singleton(root, ary[idx], singleton_id, uid, ary[idx + Idx__src_bgn], ary[idx + Idx__src_end]);
	}
	public int Get_owner_id(int uid) {
		int idx = uid * Idx__slots; if (idx >= ary_max) throw Make_err_invalid_uid(uid);
		return ary[idx + Idx__owner_id];
	}
	public boolean Update_end(int uid, int end) {	// return if tkn is node
		int idx = uid * Idx__slots; if (idx >= ary_max) throw Make_err_invalid_uid(uid);
		ary[idx + Idx__src_end] = end;
		return Texvc_tkn_.Tid_is_node(ary[idx]);
	}
	public int Update_owner_id(int cur_uid, int new_owner_uid) {	// return old owner_id
		int cur_idx = cur_uid * Idx__slots;
		int cur_owner_uid_idx = cur_idx + Idx__owner_id;
		int old_owner_uid = ary[cur_owner_uid_idx];
		ary[cur_owner_uid_idx] = new_owner_uid;
		return old_owner_uid;
	}
	private Err Make_err_invalid_uid(int uid) {return Err_.new_("math.texvc", "invalid uid", "uid", uid, "max", ary_max);}
	private static final int
	  Idx__owner_id		= 1
	, Idx__singleton_id	= 2
	, Idx__src_bgn		= 3
	, Idx__src_end		= 4
	, Idx__slots		= 5
	;		
}
