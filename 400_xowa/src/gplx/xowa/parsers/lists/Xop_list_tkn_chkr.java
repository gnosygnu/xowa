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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_list_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_list_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_list;}
	public int	List_uid() {return list_uid;} public Xop_list_tkn_chkr List_uid_(int v) {list_uid = v; return this;} private int list_uid = -1;
	public byte List_bgn() {return list_bgn;} public Xop_list_tkn_chkr List_bgn_(byte v) {list_bgn = v; return this;} private byte list_bgn;
	public byte List_itmTyp() {return list_itmTyp;} public Xop_list_tkn_chkr List_itmTyp_(byte v) {list_itmTyp = v; return this;} private byte list_itmTyp = Xop_list_tkn_.List_itmTyp_null;
	public int[] List_path() {return list_path;} public Xop_list_tkn_chkr List_path_(int... v) {list_path = v; return this;} private int[] list_path = Int_.Ary_empty;
	public byte List_sub_last() {return list_sub_last;} public Xop_list_tkn_chkr List_sub_last_(byte v) {list_sub_last = v; return this;} private byte list_sub_last = Bool_.__byte;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_list_tkn actl = (Xop_list_tkn)actl_obj;
		err += mgr.Tst_val(list_uid == -1, path, "list_uid", list_uid, actl.List_uid());
		err += mgr.Tst_val(list_bgn == 0, path, "list_bgn", list_bgn, actl.List_bgn());
		err += mgr.Tst_val(list_itmTyp == Xop_list_tkn_.List_itmTyp_null, path, "list_itmTyp", list_itmTyp, actl.List_itmTyp());
		err += mgr.Tst_val(list_sub_last == Bool_.__byte, path, "list_sub_last", list_sub_last, actl.List_sub_last());
		err += mgr.Tst_val(list_path == Int_.Ary_empty, path, "list_path", Array_.To_str(list_path), Array_.To_str(actl.List_path()));
		return err;
	}
}
