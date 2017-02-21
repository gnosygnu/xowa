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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_amp_wkr implements Xop_ctx_wkr {
	public void Ctor_ctx(Xop_ctx ctx) {}
	public void Page_bgn(Xop_ctx ctx, Xop_root_tkn root) {}
	public void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn, int cur) {
		if (cur == src_len) return ctx.Lxr_make_txt_(cur);	// NOTE: & is last char in page; strange and rare, but don't raise error

		Xop_amp_mgr amp_mgr = ctx.App().Parser_amp_mgr();
		Xop_amp_mgr_rslt amp_rv = amp_mgr.Parse_tkn(tkn_mkr, src, src_len, bgn, cur);
		Xop_tkn_itm amp_tkn = amp_rv.Tkn();
		int rv_pos = amp_rv.Pos();
		if (amp_tkn == null) return ctx.Lxr_make_txt_(rv_pos);
		ctx.Subs_add(root, amp_tkn);
		return rv_pos;
	}
}
