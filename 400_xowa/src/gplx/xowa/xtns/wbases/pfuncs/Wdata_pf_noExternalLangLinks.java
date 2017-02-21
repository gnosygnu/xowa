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
package gplx.xowa.xtns.wbases.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.langs.jsons.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Wdata_pf_noExternalLangLinks extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_noexternallanglinks;}
	@Override public Pf_func New(int id, byte[] name) {return new Wdata_pf_noExternalLangLinks().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		ctx.Page().Wdata_external_lang_links().Parse(ctx, src, caller, self, this);
	}
	public static void Print_self(Gfo_usr_dlg usr_dlg, Bry_bfr bfr, byte[] src, Xot_invk self, String warn_cls, String warn_fmt, Object... args) {
		bfr.Add_mid(src, self.Src_bgn(), self.Src_end());
		usr_dlg.Warn_many(GRP_KEY, warn_cls, warn_fmt, args);
	}
	static final String GRP_KEY = "xowa.xtns.wdata.noexternallanglinks";
}
