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
package gplx.xowa.xtns.pfuncs.ttls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_anchorencode extends Pf_func_base {	// EX: {{anchorencode:a b}} -> a+b
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public int Id() {return Xol_kwd_grp_.Id_url_anchorencode;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_anchorencode().Name_(name);}
	@Override public void Func_evaluate(BryWtr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] raw_bry = Eval_argx(ctx, src, caller, self);
		if (BryUtl.IsNotNullOrEmpty(raw_bry))
			Anchor_encode(bfr, ctx, raw_bry);
	}		
	public static void Anchor_encode(BryWtr bfr, Xop_ctx ctx, byte[] raw) {
		Pfunc_anchorencode_mgr mgr = new Pfunc_anchorencode_mgr(ctx.Wiki().Parser_mgr().Anchor_encoder_parser_or_new(), ctx, raw);
		mgr.Encode_anchor(bfr);
	}
}
