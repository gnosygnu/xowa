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
import gplx.xowa.xtns.math.texvcs.funcs.*;
public class Texvc_tkn_mkr {
	private final Texvc_tkn[] regy = new Texvc_tkn[Texvc_tkn_.Tid_len + Texvc_func_itm_.Id_len];
	public void Reg_singleton(int tid, Texvc_tkn tkn) {
		regy[tid] = tkn;
	}
	public Texvc_tkn Get_singleton(Texvc_root root, int tid, int leaf_id, int uid, int bgn, int end) {
		Texvc_tkn singleton = regy[leaf_id];
		return singleton == null ? null : singleton.Init(root, tid, uid, bgn, end);
	}
	public static final int Singleton_id__null = -1;
}