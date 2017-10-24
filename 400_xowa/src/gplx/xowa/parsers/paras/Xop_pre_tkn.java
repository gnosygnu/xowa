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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*;
public class Xop_pre_tkn extends Xop_tkn_itm_base {
	public Xop_pre_tkn(int bgn, int end, byte pre_tid, Xop_tkn_itm pre_bgn_tkn) {
		this.Tkn_ini_pos(false, bgn, end);
		this.pre_tid = pre_tid;
	}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_pre;}
	public byte Pre_tid() {return pre_tid;} private byte pre_tid = Pre_tid_null;
	@Override public void Html__write(Bry_bfr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src) {
		switch (pre_tid) {
			case Xop_pre_tkn.Pre_tid_bgn: bfr.Add(Gfh_tag_.Pre_lhs); break;		// '<pre>'
			case Xop_pre_tkn.Pre_tid_end: bfr.Add(Bry__pre__rhs); break;			// '\n</pre>\n\n'
		}
	}
	public static final byte Pre_tid_null = 0, Pre_tid_bgn = 1, Pre_tid_end = 2;
	private static final byte[] Bry__pre__rhs = Bry_.new_a7("\n</pre>\n\n");
}
