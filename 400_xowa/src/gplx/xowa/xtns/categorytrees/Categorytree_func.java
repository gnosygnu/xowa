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
package gplx.xowa.xtns.categorytrees; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.htmls.core.htmls.utls.*;
public class Categorytree_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_categorytree;}
	@Override public Pf_func New(int id, byte[] name) {return new Categorytree_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] argx = Eval_argx(ctx, src, caller, self); if (argx == null) return;
		Xow_wiki wiki = ctx.Wiki();
		Xoa_ttl ctg_ttl = wiki.Ttl_parse(gplx.xowa.wikis.nss.Xow_ns_.Tid__category, argx); if (ctg_ttl == null) return;	// ignore bad titles; EX: {{#categorytree:<>}}
		gplx.xowa.parsers.lnkis.Xop_lnki_wkr_.Write_lnki(bfr, ctg_ttl, true);
	}
	public static final    Categorytree_func Instance = new Categorytree_func(); Categorytree_func() {}
}
