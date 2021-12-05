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
package gplx.core.tests;
import gplx.Byte_;
import gplx.Int_;
import gplx.Keyval;
import gplx.Keyval_;
import gplx.Long_;
import gplx.Object_;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.String_;
import gplx.Type_;
import gplx.Type_ids_;
import gplx.core.strings.String_bldr;
import gplx.core.strings.String_bldr_;
import gplx.objects.primitives.BoolUtl;
public class Gfo_test_itm {
	private final boolean is_expd;
	private final Ordered_hash hash = Ordered_hash_.New();
	Gfo_test_itm(boolean is_expd) {
		this.is_expd = is_expd;
	}
	public int Len() {return hash.Len();}
	public Gfo_test_itm Add(String key, Object val) {hash.Add(key, Keyval_.new_(key, val)); return this;}
	private Object Get_by_val(String key) {return ((Keyval)hash.GetByOrFail(key)).Val();}
	public String Get_str(String key) {
		Object val_obj = Get_by_val(key);
		if (Type_.Eq_by_obj(val_obj, byte[].class)) {
			return String_.new_u8((byte[])val_obj);
		}
		else
			return (String)val_obj;
	}
	public void Test_bool(String key, boolean val) {
		boolean cur = BoolUtl.Cast(Get_by_val(key));
		boolean expd = is_expd ? cur : val;
		boolean actl = is_expd ? val : cur;
		Gftest.Eq__bool(expd, actl);
	}
	public void Test_bry(String key, byte[] val) {
		byte[] cur = (byte[])Get_by_val(key);
		byte[] expd = is_expd ? cur : val;
		byte[] actl = is_expd ? val : cur;
		Gftest.Eq__bry(expd, actl);
	}
	public void Test_long(String key, long val) {
		long cur = Long_.cast(Get_by_val(key));
		long expd = is_expd ? cur : val;
		long actl = is_expd ? val : cur;
		Gftest.Eq__long(expd, actl);
	}
	public void Test_int(String key, int val) {
		int cur = Int_.Cast(Get_by_val(key));
		int expd = is_expd ? cur : val;
		int actl = is_expd ? val : cur;
		Gftest.Eq__int(expd, actl);
	}
	public void Test_byte(String key, byte val) {
		byte cur = Byte_.Cast(Get_by_val(key));
		byte expd = is_expd ? cur : val;
		byte actl = is_expd ? val : cur;
		Gftest.Eq__byte(expd, actl);
	}
	public void Test_str(String key, String val) {
		String cur = String_.cast(Get_by_val(key));
		String expd = is_expd ? cur : val;
		String actl = is_expd ? val : cur;
		Gftest.Eq__str(expd, actl);
	}
	public void Test_actl(Gfo_test_itm actl) {
		int expd_len = hash.Len();
		String[] expd_ary = new String[expd_len];
		String[] actl_ary = new String[expd_len];
		for (int i = 0; i < expd_len; i++) {
			Keyval expd_kv = (Keyval)hash.Get_at(i);
			String key = expd_kv.Key();
			expd_ary[i] = Object_.Xto_str_strict_or_null(expd_kv.Val());

			Keyval actl_kv = (Keyval)actl.hash.GetByOrNull(key);
			actl_ary[i] = actl_kv == null ? "MISSING" : Object_.Xto_str_strict_or_null(actl_kv.Val());
		}
		Gftest.Eq__ary(expd_ary, actl_ary);
	}
	public String To_str(boolean single_line) {
		String_bldr bldr = String_bldr_.new_();
		int len = hash.Len();
		String itm_dlm = single_line ? ";" : "\n";
		for (int i = 0; i < len; i++) {
			Object itm = hash.Get_at(i);
			int itm_tid = Type_ids_.To_id_by_obj(itm);
			if (itm_tid == Type_ids_.Id__bry)
				itm = String_.new_u8((byte[])itm);
			bldr.Add_obj(itm);
			bldr.Add(itm_dlm);
		}
		return bldr.To_str();
	}
	@Override public String toString() {
		return this.To_str(true);
	}
	public static Gfo_test_itm New__actl() {return new Gfo_test_itm(false);}
	public static Gfo_test_itm New__expd() {return new Gfo_test_itm(true);}
}
