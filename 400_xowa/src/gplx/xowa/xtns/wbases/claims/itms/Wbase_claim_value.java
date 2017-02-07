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
public class Wbase_claim_value extends Wbase_claim_base {
	public Wbase_claim_value(int pid, byte val_tid, byte snak_tid) {super(pid, snak_tid);
		this.val_tid = val_tid;
	}
	@Override public byte	Val_tid() {return val_tid;} private final    byte val_tid;

	@Override public void Welcome(Wbase_claim_visitor visitor) {visitor.Visit_system(this);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wbase_claim_value_type_.Reg.Get_str_or_fail(this.Snak_tid()), Wbase_claim_type_.Reg.Get_str_or_fail(this.Val_tid()));
	}

	public static Wbase_claim_value New_novalue(int pid)		{return new Wbase_claim_value(pid, Wbase_claim_type_.Tid__unknown	, Wbase_claim_value_type_.Tid__novalue);}
	public static Wbase_claim_value New_somevalue(int pid)		{return new Wbase_claim_value(pid, Wbase_claim_type_.Tid__unknown	, Wbase_claim_value_type_.Tid__somevalue);}
}
