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
public class Xop_nl_tkn extends Xop_tkn_itm_base {
	public Xop_nl_tkn(int bgn, int end, byte nl_tid, int nl_len) {
		this.Tkn_ini_pos(false, bgn, end);
		this.nl_tid = nl_tid;
	}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_newLine;}		
	public byte Nl_tid() {return nl_tid;} private byte nl_tid = Xop_nl_tkn.Tid_unknown; 		
	public static final byte Tid_unknown = 0, Tid_char = 1, Tid_hdr = 2, Tid_hr = 3, Tid_list = 4, Tid_tblw = 5, Tid_file = 6;
}
