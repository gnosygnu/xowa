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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
public class Wdata_claim_itm_quantity extends Wdata_claim_itm_core { 	public Wdata_claim_itm_quantity(int pid, byte snak_tid, byte[] amount, byte[] unit, byte[] ubound, byte[] lbound) {
		this.Ctor(pid, snak_tid);
		this.amount = amount; this.unit = unit; this.ubound = ubound; this.lbound = lbound;
	}
	@Override public byte Val_tid() {return Wdata_dict_val_tid.Tid_quantity;}
	public byte[] Amount() {return amount;} private final byte[] amount;
	public byte[] Unit() {return unit;} private final byte[] unit;
	public byte[] Ubound() {return ubound;} private final byte[] ubound;
	public byte[] Lbound() {return lbound;} private final byte[] lbound;
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wdata_dict_snak_tid.Xto_str(this.Snak_tid()), Wdata_dict_val_tid.Xto_str(this.Val_tid()), String_.new_utf8_(amount), String_.new_utf8_(unit), String_.new_utf8_(ubound), String_.new_utf8_(lbound));
	}
}
