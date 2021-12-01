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
import gplx.core.strings.*;
public class Gfo_test_lnr_base {
	private String[] keys;
	public List_adp Expd() {return expd_list;} private final List_adp expd_list = List_adp_.New();
	public List_adp Actl() {return actl_list;} private final List_adp actl_list = List_adp_.New();
	public void Clear() {
		expd_list.Clear();
		actl_list.Clear();
	}
	public void Add_actl_args(Object... vals) {
		int len = vals.length;
		Gfo_test_itm itm = Gfo_test_itm.New__actl();
		for (int i = 0; i < len; i++) {
			itm.Add(keys[i], vals[i]);
		}
		actl_list.Add(itm);
	}
	public void Test() {Test(null);}
	public void Test(Gfo_test_lnr_itm_cbk cbk) {
		int expd_len = expd_list.Len();
		int actl_len = actl_list.Len();
		if (expd_len == 0 && actl_len == 0) {}
		else if (actl_len == 0) {
			Gftest.Fail("expected some itms; got zero; expd={0}", expd_list.ToStr());
		}
		else if (expd_len == 0) {
			Gftest.Fail("expected zero itms; got some; actl={0}", actl_list.ToStr());
		}
		else {
			for (int i = 0; i < actl_len; i++) {
				Gfo_test_itm expd_itm = (Gfo_test_itm)expd_list.Get_at(i);
				Gfo_test_itm actl_itm = (Gfo_test_itm)actl_list.Get_at(i);
				expd_itm.Test_actl(actl_itm);
				if (cbk != null)
					cbk.Test_itm(i, expd_len, expd_itm, actl_itm);
			}
		}
	}
	public String To_str() {
		String_bldr sb = String_bldr_.new_();
		int len = actl_list.Len();
		for (int i = 0; i < len; i++) {
			Gfo_test_itm itm = (Gfo_test_itm)actl_list.Get_at(i);
			sb.Add(itm.To_str(true)).Add_char_nl();
		}
		return sb.To_str_and_clear();
	}
	public static Gfo_test_lnr_base New__keys(String... keys) {
		Gfo_test_lnr_base rv = new Gfo_test_lnr_base();
		rv.keys = keys;
		return rv;
	}
}
