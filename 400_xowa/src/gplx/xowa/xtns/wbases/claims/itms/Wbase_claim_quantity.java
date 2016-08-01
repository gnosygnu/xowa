/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.wbases.claims.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class Wbase_claim_quantity extends Wbase_claim_base {
	public Wbase_claim_quantity(int pid, byte snak_tid, byte[] amount, byte[] unit, byte[] ubound, byte[] lbound) {super(pid, snak_tid);
		this.amount = amount; this.unit = unit; this.ubound = ubound; this.lbound = lbound;
	}
	@Override public byte	Val_tid()	{return Wbase_claim_type_.Tid__quantity;}
	public byte[]			Amount()	{return amount;} private final    byte[] amount;
	public byte[]			Ubound()	{return ubound;} private final    byte[] ubound;
	public byte[]			Lbound()	{return lbound;} private final    byte[] lbound;
	public byte[]			Unit()		{return unit;} private final    byte[] unit;

	public Decimal_adp Amount_as_num() {
		if (amount_as_num == null) amount_as_num = To_decimal("amount", amount);
		return amount_as_num;
	}	private Decimal_adp amount_as_num;
	public Decimal_adp Ubound_as_num() {
		if (ubound_as_num == null) ubound_as_num = To_decimal("upper", ubound);
		return ubound_as_num;
	}	private Decimal_adp ubound_as_num;
	public Decimal_adp Lbound_as_num() {
		if (lbound_as_num == null) lbound_as_num = To_decimal("lower", lbound);
		return lbound_as_num;
	}	private Decimal_adp lbound_as_num;
	private Decimal_adp To_decimal(String type, byte[] v) {
		if (v == null) throw Err_.new_("wbase", "value is null", "type", type);
		int len = v.length; if (len == 0) throw Err_.new_("wbase", "value is empty", "type", type);
		if (v[0] == Byte_ascii.Plus) v = Bry_.Mid(v, 1);
		return Decimal_adp_.parse(String_.new_a7(v));
	}

	@Override public void Welcome(Wbase_claim_visitor visitor) {visitor.Visit_quantity(this);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wbase_claim_value_type_.To_str_or_fail(this.Snak_tid()), Wbase_claim_type_.To_key_or_unknown(this.Val_tid()), String_.new_u8(amount), String_.new_u8(unit), String_.new_u8(ubound), String_.new_u8(lbound));
	}
	public static final    byte[] Unit_1 = Bry_.new_a7("1");
}
