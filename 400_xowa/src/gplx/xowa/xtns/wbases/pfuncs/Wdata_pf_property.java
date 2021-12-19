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
package gplx.xowa.xtns.wbases.pfuncs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.langs.kwds.Xol_kwd_grp_;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.tmpls.Xot_invk;
import gplx.xowa.xtns.pfuncs.Pf_func;
import gplx.xowa.xtns.pfuncs.Pf_func_base;
public class Wdata_pf_property extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_property;}
	@Override public Pf_func New(int id, byte[] name) {return new Wdata_pf_property().Name_(name);}
	@Override public void Func_evaluate(BryWtr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {// EX: {{#property:p123|}}
		synchronized (this) { // LOCK: must synchronized b/c bfr will later be set as member variable in .Resolve_to_bfr; DATE:2016-07-06
			Wbase_statement_mgr_.Get_wbase_data(bfr, ctx, caller, self, src, this, BoolUtl.N);
		}
	}
}
