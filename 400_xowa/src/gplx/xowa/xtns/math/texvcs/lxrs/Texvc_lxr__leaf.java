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
package gplx.xowa.xtns.math.texvcs.lxrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
import gplx.xowa.xtns.math.texvcs.tkns.*;
class Texvc_lxr__leaf implements Texvc_lxr {
	public Texvc_lxr__leaf(int tkn_tid) {this.tkn_tid = tkn_tid;} private final int tkn_tid;
	public int		Tid() {return Texvc_lxr_.Tid__raw;}
	public int		Make_tkn(Texvc_ctx ctx, Texvc_root root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		root.Regy__add(tkn_tid, tkn_tid, bgn_pos, cur_pos, null);
		return cur_pos;
	}
}
