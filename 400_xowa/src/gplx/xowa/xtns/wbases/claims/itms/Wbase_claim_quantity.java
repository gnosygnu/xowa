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
package gplx.xowa.xtns.wbases.claims.itms;

import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_visitor;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;

public class Wbase_claim_quantity extends Wbase_claim_base {
	public Wbase_claim_quantity(int pid, byte snak_tid, byte[] amount, byte[] unit, byte[] ubound, byte[] lbound) {super(pid, snak_tid);
		this.amount = amount; this.unit = unit; this.ubound = ubound; this.lbound = lbound;
	}
	@Override public byte	Val_tid()	{return Wbase_claim_type_.Tid__quantity;}
	public byte[]			Amount()	{return amount;} private final byte[] amount;
	public byte[]			Ubound()	{return ubound;} private final byte[] ubound;
	public byte[]			Lbound()	{return lbound;} private final byte[] lbound;
	public byte[]			Unit()		{return unit;} private final byte[] unit;

	public GfoDecimal Amount_as_num() {
		if (amount_as_num == null) {
			amount_as_num = To_decimal_or_null("amount", amount);
			if (amount_as_num == null) {
				amount_as_num = GfoDecimalUtl.Zero;
				Gfo_usr_dlg_.Instance.Warn_many("", "", "wbase.claim: value is null; name=~{0}", "amount");
			}
		}
		return amount_as_num;
	}	private GfoDecimal amount_as_num;
	public GfoDecimal Ubound_as_num() {
		if (ubound_as_num == null) {
			ubound_as_num = To_decimal_or_null("upper", ubound);
		}
		return ubound_as_num;
	}	private GfoDecimal ubound_as_num;
	public GfoDecimal Lbound_as_num() {
		if (lbound_as_num == null) {
			lbound_as_num = To_decimal_or_null("lower", lbound);
		}
		return lbound_as_num;
	}	private GfoDecimal lbound_as_num;

	@Override public void Welcome(Wbase_claim_visitor visitor) {visitor.Visit_quantity(this);}
	@Override public String toString() {// TEST:
		return StringUtl.ConcatWith("|", Wbase_claim_value_type_.Reg.Get_str_or_fail(this.Snak_tid()), Wbase_claim_type_.Reg.Get_str_or_fail(this.Val_tid()), StringUtl.NewU8(amount), StringUtl.NewU8(unit), StringUtl.NewU8(ubound), StringUtl.NewU8(lbound));
	}

	public static final byte[] Unit_1 = BryUtl.NewA7("1");
	private static GfoDecimal To_decimal_or_null(String name, byte[] bry) {
		if (bry == null) return null;
		int len = bry.length;
		if (len == 0) return null;
		if (bry[0] == AsciiByte.Plus) bry = BryLni.Mid(bry, 1);
		return GfoDecimalUtl.Parse(StringUtl.NewA7(bry));
	}
}
