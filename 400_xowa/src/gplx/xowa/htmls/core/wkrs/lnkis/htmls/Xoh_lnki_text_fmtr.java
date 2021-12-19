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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryBfrMkr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.xowa.parsers.*;
import gplx.xowa.htmls.core.htmls.*;
public class Xoh_lnki_text_fmtr {	// formats alt or caption
	private final BryBfrMkr bfr_mkr; private final Xoh_html_wtr html_wtr;
	private final BryWtr fmt_bfr = BryWtr.NewAndReset(32);
	public Xoh_lnki_text_fmtr(BryBfrMkr bfr_mkr, Xoh_html_wtr html_wtr) {
		this.bfr_mkr = bfr_mkr; this.html_wtr = html_wtr;
	}

	public byte[] To_bry(Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_tkn_itm text_tkn, boolean called_by_alt_as_caption, BryFmt fmt) {
		// write lnki_text using tkn and html_wtr
		BryWtr html_bfr = bfr_mkr.GetK004();		// NOTE: do not make bfr member-variable; possible for captions to be nested, especially during call to Write_tkn_to_html
		html_wtr.Write_tkn_to_html(html_bfr, ctx, hctx, src, null, Xoh_html_wtr.Sub_idx_null, text_tkn);
		byte[] rv = called_by_alt_as_caption 
			? html_bfr.ToBryAndClearAndTrim()	// NOTE: Trim to handle ws-only alt; EX:[[File:A.png|thumb|alt= ]]; en.w:Bird; DATE:2015-12-28
			: html_bfr.ToBryAndClear();
		html_bfr.MkrRls();
		if (rv.length == 0) return BryUtl.Empty;	// NOTE: if no text, exit now; else, empty text will generate empty thumb div

		// return value; fmt if necessary
		return fmt == Null__fmt
			? rv
			: fmt.Bld_many_to_bry(fmt_bfr, rv);
	}
	public static final BryFmt Null__fmt = null;
}
