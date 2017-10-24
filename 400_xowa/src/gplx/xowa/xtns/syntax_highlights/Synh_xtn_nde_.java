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
package gplx.xowa.xtns.syntax_highlights; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*;
class Synh_xtn_nde_ {
	public static void Make(Bry_bfr bfr, Xoae_app app, byte[] src, int src_bgn, int src_end, byte[] lang, byte[] enclose, byte[] style, boolean line_enabled, int start, Int_rng_mgr highlight_idxs) {
		boolean enclose_is_none	= Bry_.Eq(enclose, Enclose_none);
		if (enclose_is_none) {	// enclose=none -> put in <code>
			bfr.Add(Bry__code_bgn);
			if (style != null) bfr.Add(Xoh_consts.Style_atr).Add(style).Add_byte(Byte_ascii.Quote);
			bfr.Add_byte(Byte_ascii.Gt);
		}
		else {
			bfr.Add(Bry__div_bgn);
			if (style != null) bfr.Add(Xoh_consts.Style_atr).Add(style).Add_byte(Byte_ascii.Quote);
			bfr.Add_byte(Byte_ascii.Angle_end);
			bfr.Add(Xoh_consts.Pre_bgn_overflow);
		}
		int text_bgn = src_bgn;
		int text_end = Bry_find_.Find_bwd_while(src, src_end, -1, Byte_ascii.Space) + 1; // trim space from end; PAGE:en.w:Comment_(computer_programming) DATE:2014-06-23
		if (line_enabled) {
			bfr.Add_byte_nl();
			byte[][] lines = Bry_split_.Split_lines(Bry_.Mid(src, text_bgn, text_end));
			int lines_len = lines.length;
			int line_idx = start;
			int line_end = (line_idx + lines_len) - 1; // EX: line_idx=9 line_len=1; line_end=9
			int digits_max = Int_.DigitCount(line_end);
			for (int i = 0; i < lines_len; i++) {
				byte[] line = lines[i]; if (i == 0 && Bry_.Len_eq_0(line)) continue;
				if (line_enabled) {
					bfr.Add(Xoh_consts.Span_bgn_open).Add(Xoh_consts.Style_atr).Add(Style_line).Add(Xoh_consts.__end_quote);
					int pad = digits_max - Int_.DigitCount(line_idx);
					if (pad > 0) bfr.Add_byte_repeat(Byte_ascii.Space, pad);
					bfr.Add_int_variable(line_idx++).Add_byte(Byte_ascii.Space);
					bfr.Add(Xoh_consts.Span_end);
				}
				bfr.Add(Xoh_consts.Span_bgn_open);
				if (highlight_idxs.Match(i))
					bfr.Add(Xoh_consts.Style_atr).Add(Style_highlight).Add(Xoh_consts.__end_quote);
				else
					bfr.Add(Xoh_consts.__end);
				Xox_mgr_base.Xtn_write_escape(app, bfr, line);
				bfr.Add(Xoh_consts.Span_end);
				bfr.Add_byte_nl();
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
	  Enclose_none = Bry_.new_a7("none")
	, Style_line = Bry_.new_a7("-moz-user-select:none;"), Style_highlight = Bry_.new_a7("background-color: #FFFFCC;")
	, Bry__div_bgn = Bry_.new_a7("<div class=\"mw-highlight\"")
	, Bry__code_bgn = Bry_.new_a7("<code class=\"mw-highlight\"")
	;
}
