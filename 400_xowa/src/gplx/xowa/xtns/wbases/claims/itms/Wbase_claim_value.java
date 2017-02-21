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
