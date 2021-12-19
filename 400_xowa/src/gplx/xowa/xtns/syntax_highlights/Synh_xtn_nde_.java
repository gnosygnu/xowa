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
package gplx.xowa.xtns.syntax_highlights;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*;
class Synh_xtn_nde_ {
	public static void Make(BryWtr bfr, Xoae_app app, byte[] src, int src_bgn, int src_end, byte[] lang, byte[] enclose, byte[] style, boolean line_enabled, int start, Int_rng_mgr highlight_idxs) {
		boolean enclose_is_none	= BryLni.Eq(enclose, Enclose_none);
		if (enclose_is_none) {	// enclose=none -> put in <code>
			bfr.Add(Bry__code_bgn);
			if (style != null) bfr.Add(Xoh_consts.Style_atr).Add(style).AddByte(AsciiByte.Quote);
			bfr.AddByte(AsciiByte.Gt);
		}
		else {
			bfr.Add(Bry__div_bgn);
			if (style != null) bfr.Add(Xoh_consts.Style_atr).Add(style).AddByte(AsciiByte.Quote);
			bfr.AddByte(AsciiByte.AngleEnd);
			Gfh_tag_.Bld_lhs_bgn(bfr, Gfh_tag_.Id__pre);
			Gfh_atr_.Add(bfr, Gfh_atr_.Bry__style, Bry__style__overflow__auto);
			if (BryUtl.IsNotNullOrEmpty(lang)) {
				Gfh_atr_.Add(bfr, Gfh_atr_.Bry__class, BryUtl.Add(Bry__pretty_print, lang));
			}
			Gfh_tag_.Bld_lhs_end_nde(bfr);
		}
		int text_bgn = src_bgn;
		int text_end = BryFind.FindBwdWhile(src, src_end, -1, AsciiByte.Space) + 1; // trim space from end; PAGE:en.w:Comment_(computer_programming) DATE:2014-06-23
		if (line_enabled || highlight_idxs != Int_rng_mgr_null.Instance) { // NOTE: if "highlight" specified without "line" highlight_idxs will not be null instance; add '<span style="background-color: #FFFFCC;">' below; ISSUE#:498; DATE:2019-06-22
			bfr.AddByteNl();
			byte[][] lines = BrySplit.SplitLines(BryLni.Mid(src, text_bgn, text_end));
			int lines_len = lines.length;
			int line_idx = start;
			int line_end = (line_idx + lines_len) - 1; // EX: line_idx=9 line_len=1; line_end=9
			int digits_max = IntUtl.CountDigits(line_end);
			for (int i = 0; i < lines_len; i++) {
				byte[] line = lines[i]; if (i == 0 && BryUtl.IsNullOrEmpty(line)) continue;
				if (line_enabled) { // add '<span style="-moz-user-select:none;">1 </span>' if "line" is enabled
					bfr.Add(Xoh_consts.Span_bgn_open).Add(Xoh_consts.Style_atr).Add(Style_line).Add(Xoh_consts.__end_quote);
					int pad = digits_max - IntUtl.CountDigits(line_idx);
					if (pad > 0) bfr.AddByteRepeat(AsciiByte.Space, pad);
					bfr.AddIntVariable(line_idx++).AddByte(AsciiByte.Space);
					bfr.Add(Xoh_consts.Span_end);
				}
				bfr.Add(Xoh_consts.Span_bgn_open);
				if (highlight_idxs.Match(i))
					bfr.Add(Xoh_consts.Style_atr).Add(Style_highlight).Add(Xoh_consts.__end_quote);
				else
					bfr.Add(Xoh_consts.__end);
				Xox_mgr_base.Xtn_write_escape(app, bfr, line);
				bfr.Add(Xoh_consts.Span_end);
				bfr.AddByteNl();
			}
		}
		else
			Xox_mgr_base.Xtn_write_escape_pre(app, bfr, src, text_bgn, text_end);
		if 		(enclose_is_none) {
			bfr.Add(Xoh_consts.Code_end);
		}
		else {
			bfr.Add(Xoh_consts.Pre_end);
			bfr.Add(Gfh_bldr_.Bry__div_rhs);
		}
	}
	private static final byte[]
	  Enclose_none = BryUtl.NewA7("none")
	, Style_line = BryUtl.NewA7("-moz-user-select:none;"), Style_highlight = BryUtl.NewA7("background-color: #FFFFCC;")
	, Bry__style__overflow__auto = BryUtl.NewA7("overflow:auto")
	, Bry__pretty_print = BryUtl.NewA7("prettyprint lang-")
	, Bry__div_bgn = BryUtl.NewA7("<div class=\"mw-highlight\"")
	, Bry__code_bgn = BryUtl.NewA7("<code class=\"mw-highlight\"")
	;
}
