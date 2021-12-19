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
package gplx.xowa.xtns.math;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Xomath_xnde implements Xox_xnde {
	public Xop_xnde_tkn Xnde() {throw ErrUtl.NewUnimplemented();}
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xomath_core math_mgr = wiki.Parser_mgr().Math__core();
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_math, src, xnde);
		if (math_mgr.Enabled() && math_mgr.Renderer_is_mathjax())
			ctx.Page().Html_data().Head_mgr().Itm__mathjax().Enabled_y_();
	}
	public void Xtn_write(BryWtr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		wpg.Stat_itm().Math_count++;
		ctx.Wiki().Parser_mgr().Math__core().Write(bfr, ctx, xnde, src);
	}
	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
}
