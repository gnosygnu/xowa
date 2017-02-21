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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_eq_tkn extends Xop_tkn_itm_base {//20111222
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_eq;}
	public int Eq_len() {return eq_len;} private int eq_len = -1;
	public int Eq_ws_rhs_bgn() {return eq_ws_rhs_bgn;} public Xop_eq_tkn Eq_ws_rhs_bgn_(int v) {eq_ws_rhs_bgn = v; return this;} private int eq_ws_rhs_bgn = -1;
	public Xop_eq_tkn(int bgn, int end, int eq_len) {this.Tkn_ini_pos(false, bgn, end); this.eq_len = eq_len;}
}
