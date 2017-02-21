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
public class Xop_amp_tkn_num extends Xop_tkn_itm_base {
	public Xop_amp_tkn_num(int bgn, int end, int val, byte[] str_as_bry) {
		this.val = val; this.str_as_bry = str_as_bry;
		this.Tkn_ini_pos(false, bgn, end);
	}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_html_ncr;}
	public int Val() {return val;} private int val;
	public byte[] Str_as_bry() {return str_as_bry;} private byte[] str_as_bry;
}
