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
package gplx.xowa.parsers.apos; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_apos_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_apos_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_apos;}
	public int Apos_cmd() {return apos_cmd;} public Xop_apos_tkn_chkr Apos_cmd_(int v) {apos_cmd = v; return this;} private int apos_cmd = Xop_apos_tkn_.Cmd_nil;
	public int Apos_lit() {return apos_lit;} public Xop_apos_tkn_chkr Apos_lit_(int v) {apos_lit = v; return this;} private int apos_lit = -1;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_apos_tkn actl = (Xop_apos_tkn)actl_obj;
		err += mgr.Tst_val(apos_cmd == Xop_apos_tkn_.Cmd_nil, path, "apos_cmd", Xop_apos_tkn_.Cmd_str(apos_cmd), Xop_apos_tkn_.Cmd_str(actl.Apos_cmd()));
		err += mgr.Tst_val(apos_lit == -1, path, "apos_lit", apos_lit, actl.Apos_lit());
		return err;
	}
}
