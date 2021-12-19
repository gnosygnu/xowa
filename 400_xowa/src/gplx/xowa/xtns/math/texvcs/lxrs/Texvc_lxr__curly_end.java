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
package gplx.xowa.xtns.math.texvcs.lxrs; import gplx.xowa.xtns.math.texvcs.*;
import gplx.xowa.xtns.math.texvcs.tkns.*;
public class Texvc_lxr__curly_end implements Texvc_lxr {
	public int		Tid() {return Texvc_lxr_.Tid__curly_end;}
	public int		Make_tkn(Texvc_ctx ctx, Texvc_root root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int bgn_uid = ctx.Stack().PopOrFail();
		root.Regy__take_from_root_end(bgn_uid);
		root.Regy__update_end(bgn_uid, cur_pos);
		return cur_pos;
	}
}
