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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public interface Xot_fmtr {
	void Reg_ary(Xop_ctx ctx, byte[] src, boolean tmpl_static, int src_bgn, int src_end, int subs_len, Xop_tkn_itm[] subs);
	void Reg_prm(Xop_ctx ctx, byte[] src, Xot_prm_tkn self, int prm_idx, byte[] prm_key, Xop_tkn_itm dflt_tkn);
	void Reg_tmpl(Xop_ctx ctx, byte[] src, Xop_tkn_itm name_tkn, int args_len, Arg_nde_tkn[] args);
	void Reg_arg(Xop_ctx ctx, byte[] src, int arg_idx, Arg_nde_tkn self_tkn);
}
