/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.tests;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.BryBfrAble;
import gplx.types.custom.brys.wtrs.BryBfrAbleUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.errs.ErrUtl;
public class Gftest extends GfoTstr {
	public static void EqAry(BryBfrAble[] expd_ary, BryBfrAble[] actl_ary) {Gftest.EqAry(expd_ary, actl_ary, null);}
	private static void EqAry(BryBfrAble[] expd_ary, BryBfrAble[] actl_ary, String msg_fmt, Object... msg_args) {
		Gftest.EqAryWkr(TypeIds.IdBry, BryBfrAbleUtl.ToBryAry(Gftest.bfr, expd_ary), BryBfrAbleUtl.ToBryAry(Gftest.bfr, actl_ary), msg_fmt, msg_args);
	}
	private static void EqAryWkr(int type_tid, Object expd_ary, Object actl_ary, String msg_fmt, Object... msg_args) {
		boolean[] failures = Calc__failures(type_tid, expd_ary, actl_ary);
		if (failures != null) {
			Write_fail_head(bfr, msg_fmt, msg_args);
			Write_fail_ary(bfr, failures, type_tid, expd_ary, actl_ary);
			throw ErrUtl.NewArgs(bfr.ToStrAndClear());
		}
	}
	private static final BryWtr bfr = BryWtr.New();
	private static void Write_fail_head(BryWtr bfr, String msg_fmt, Object[] msg_args) {
		bfr.Add(Bry__line_bgn);
		if (msg_fmt != null) {
			bfr.AddStrU8(StringUtl.Format(msg_fmt, msg_args));
			bfr.Add(Bry__line_mid);
		}
	}
	private static void Write_fail_ary(BryWtr bfr, boolean[] failures, int type_id, Object expd_ary, Object actl_ary) {
		int len = failures.length;
		int expd_len = ArrayUtl.Len(expd_ary);
		int actl_len = ArrayUtl.Len(actl_ary);
		for (int i = 0; i < len; ++i) {
			boolean failure = failures[i];
			int pad_len = 5 - IntUtl.CountDigits(i);
			bfr.AddIntPadBgn(AsciiByte.Num0, pad_len, i).AddByteColon().AddByteSpace();
			Write__itm(bfr, type_id, expd_ary, expd_len, i);
			if (failure) {
				bfr.Add(Bry__item__eq_n).AddByteRepeat(AsciiByte.Space, pad_len - 1);
				Write__itm(bfr, type_id, actl_ary, actl_len, i);
			}
		}
		bfr.Add(Bry__line_end);
	}
	private static void Write__itm(BryWtr bfr, int type_id, Object ary, int len, int idx) {
		if (idx < len) {
			Object val = ArrayUtl.GetAt(ary, idx);
			switch (type_id) {
				case TypeIds.IdBool:    bfr.AddYn(BoolUtl.Cast(val)); break;
				case TypeIds.IdBry:        bfr.AddSafe((byte[])val); break;
				case TypeIds.IdLong:    bfr.AddLongVariable(LongUtl.Cast(val)); break;
				case TypeIds.IdInt:        bfr.AddIntVariable(IntUtl.Cast(val)); break;
				case TypeIds.IdByte:    bfr.AddIntVariable((int)(ByteUtl.Cast(val))); break;
				case TypeIds.IdObj:     bfr.AddStrU8(ObjectUtl.ToStrOrNullMark(val)); break;
				default:                    throw ErrUtl.NewUnhandled(type_id);
			}
		}
		else
			bfr.Add(Bry__null);
		bfr.AddByteNl();
	}
	private static boolean[] Calc__failures(int tid, Object expd_ary, Object actl_ary) {    
		int expd_len = ArrayUtl.Len(expd_ary);
		int actl_len = ArrayUtl.Len(actl_ary);
		int max_len = expd_len > actl_len ? expd_len : actl_len; if (max_len == 0) return null;
		boolean[] rv = null;
		for (int i = 0; i < max_len; ++i) {
			Object expd_obj = i < expd_len ? ArrayUtl.GetAt(expd_ary, i) : null;
			Object actl_obj = i < actl_len ? ArrayUtl.GetAt(actl_ary, i) : null;
			boolean eq = false;
			if        (expd_obj == null && actl_obj == null)    eq = true;
			else if (expd_obj == null || actl_obj == null)    eq = false;
			else {
				switch (tid) {
					case TypeIds.IdBool:        eq = BoolUtl.Cast(expd_obj) == BoolUtl.Cast(actl_obj); break;
					case TypeIds.IdBry:            eq = BryLni.Eq((byte[])expd_obj, (byte[])actl_obj); break;
					case TypeIds.IdLong:        eq = LongUtl.Cast(expd_obj) == LongUtl.Cast(actl_obj); break;
					case TypeIds.IdInt:            eq = IntUtl.Cast(expd_obj) == IntUtl.Cast(actl_obj); break;
					case TypeIds.IdByte:        eq = ByteUtl.Cast(expd_obj) == ByteUtl.Cast(actl_obj); break;
					case TypeIds.IdObj:         eq = ObjectUtl.Eq(expd_obj, actl_obj); break;
				}
			}
			if (!eq) {
				if (rv == null) {
					rv = new boolean[max_len];
				}
				rv[i] = true;
			}
		}
		return rv;
	}
	private static final String Str__null = "<<NULL>>";
	private static final byte[] Bry__item__eq_n = BryUtl.NewA7("!= ") // Bry__item__eq_y = Bry_.new_a7("== "),
	, Bry__null = BryUtl.NewA7(Str__null)
	, Bry__line_bgn = BryUtl.NewA7("\n************************************************************************************************\n")
	, Bry__line_mid = BryUtl.NewA7("\n------------------------------------------------------------------------------------------------\n")
	, Bry__line_end = BryUtl.NewA7(  "________________________________________________________________________________________________")
	;
}
