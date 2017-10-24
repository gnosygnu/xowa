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
package gplx.xowa.htmls.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.brys.fmtrs.*;
public class Xoh_skin_itm implements Gfo_invk {
	private final    Bry_fmtr fmtr = Bry_fmtr.new_();
	public Xoh_skin_itm(String key, String fmt) {this.key = key; fmtr.Fmt_(fmt);}
	public String Key() {return key;} private final    String key;
	public void Fmt_(String v) {fmtr.Fmt_(v);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fmt))		return String_.new_u8(fmtr.Fmt());
		else if	(ctx.Match(k, Invk_fmt_))		fmtr.Fmt_(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_fmt = "fmt", Invk_fmt_ = "fmt_"
	;
}
