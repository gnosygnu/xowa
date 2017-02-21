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
import gplx.xowa.parsers.tmpls.*;
public class Xop_ignore_tkn extends Xop_tkn_itm_base {
	public Xop_ignore_tkn(int bgn, int end, byte ignore_type) {this.Tkn_ini_pos(false, bgn, end); this.ignore_type = ignore_type;}
	public byte Ignore_type() {return ignore_type;} private byte ignore_type = Ignore_tid_null;
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_ignore;}
	@Override public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {}
	@Override public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {return true;}
	public static final byte 
	  Ignore_tid_null = 0, Ignore_tid_comment = 1, Ignore_tid_include_tmpl = 2, Ignore_tid_include_wiki = 3, Ignore_tid_htmlTidy_tblw = 4
	, Ignore_tid_xnde_dangling = 5, Ignore_tid_nbsp = 6, Ignore_tid_empty_li = 7, Ignore_tid_pre_at_bos = 8, Ignore_tid_tr_w_td = 9, Ignore_tid_double_pipe = 10;
}
