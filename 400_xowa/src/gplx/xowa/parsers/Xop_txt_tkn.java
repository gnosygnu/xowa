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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
public class Xop_txt_tkn extends Xop_tkn_itm_base {
	public Xop_txt_tkn(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end);}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_txt;}
}
class Xop_colon_tkn extends Xop_tkn_itm_base {
	public Xop_colon_tkn(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end);}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_colon;}
}
class Xop_brack_bgn_tkn extends Xop_tkn_itm_base {
	public Xop_brack_bgn_tkn(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end);}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_brack_bgn;}
}
class Xop_brack_end_tkn extends Xop_tkn_itm_base {
	public Xop_brack_end_tkn(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end);}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_brack_end;}
}
