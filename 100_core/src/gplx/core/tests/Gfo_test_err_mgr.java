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
package gplx.core.tests; import gplx.*;
public class Gfo_test_err_mgr {
	private final List_adp expd = List_adp_.New();
	public void Init() {
		Gfo_usr_dlg_.Test__list__init();
		expd.Clear();
	}
	public void Term() {
		Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;
	}
	public void Add_expd(boolean contains, String msg) {
		Object[] itm = new Object[] {contains, msg};
		expd.Add(itm);
	}
	public void Test() {
		List_adp actl = ((Gfo_usr_dlg__gui_mock)Gfo_usr_dlg_.Instance.Gui_wkr()).Warns();
		int expd_len = expd.Len();
		int actl_len = actl.Len();
		if (expd_len == 0 && actl_len == 0) {}
		else if (actl_len == 0) {
			Gftest.Fail("expected some errors; got zero; expd={0}", expd.ToStr());
		}
		else if (expd_len == 0) {
			Gftest.Fail("expected zero errors; got some; actl={0}", actl.ToStr());
		}
		else {
			for (int i = 0; i < actl_len; i++) {
				String actl_err = (String)actl.Get_at(i);
				Object[] expd_err_ary = (Object[])expd.Get_at(i);
				if (Bool_.Cast(expd_err_ary[0])) {
					Gftest.Eq__bool(true, String_.Has(actl_err, (String)expd_err_ary[1]));
				}
				else {
					Gftest.Eq__str((String)expd_err_ary[1], (String)actl_err);
				}
			}
		}
	}
}
