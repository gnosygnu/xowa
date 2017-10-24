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
import gplx.core.tests.*;
public class Xot_prm_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xot_prm_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tmpl_prm;}
	public Xop_tkn_chkr_base Find_tkn() {return find_tkn;} public Xot_prm_chkr Find_tkn_(Xop_arg_itm_tkn_chkr v) {find_tkn = v; return this;} private Xop_arg_itm_tkn_chkr find_tkn;
	public Xop_tkn_chkr_base[] Args() {return args;} public Xot_prm_chkr Args_(Xop_tkn_chkr_base... v) {args = v; return this;} private Xop_tkn_chkr_base[] args = Xop_tkn_chkr_base.Ary_empty;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xot_prm_tkn actl = (Xot_prm_tkn)actl_obj;
		if (find_tkn != null) err += mgr.Tst_sub_obj(find_tkn, actl.Find_tkn(), path + "." + "find", err);
		return err;
	}
}
