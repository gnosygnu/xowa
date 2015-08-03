/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.syntaxHighlight; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.html.*; import gplx.xowa.html.*;
public class Xtn_syntaxHighlight_nde implements Xox_xnde, Xop_xnde_atr_parser {
	private byte[] lang = Bry_.Empty; private byte[] style = null; private byte[] enclose = Bry_.Empty;
	private boolean line_enabled = false; private int start = 1; private Int_rng_mgr highlight_idxs = Int_rng_mgr_null._;
	public Xop_xnde_tkn Xnde() {throw Err_.new_unimplemented();}
	public void Xatr_parse(Xowe_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_obj) {
		if (xatr_obj == null) return;
		byte xatr_tid = ((Byte_obj_val)xatr_obj).Val();
		switch (xatr_tid) {
			case Xatr_enclose:		enclose = xatr.Val_as_bry(src); break;
			case Xatr_lang:			lang = xatr.Val_as_bry(src); break;
			case Xatr_style:		style = xatr.Val_as_bry(src); break;
			case Xatr_line:			line_enabled = true; break;
			case Xatr_start:		start = xatr.Val_as_int_or(src, 1); break;
			case Xatr_highlight:	highlight_idxs = new Int_rng_mgr_base(); highlight_idxs.Parse(xatr.Val_as_bry(src)); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xoae_app app = ctx.App(); Xop_xnde_tag tag = xnde.Tag();
		ctx.Para().Process_block__xnde(tag, tag.Block_open());	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-24
		Xop_xatr_itm.Xatr_parse(app, this, xatrs_syntaxHighlight, wiki, src, xnde);
		ctx.Para().Process_block__xnde(tag, tag.Block_close());	// deactivate pre; pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-24
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
		boolean lang_is_text = Bry_.Eq(lang, Lang_text);
		boolean enclose_is_none = Bry_.Eq(enclose, Enclose_none);
		if 		(enclose_is_none) {
			bfr.Add(Xoh_consts.Span_bgn);			
		}
		else if (lang_is_text) {
			bfr.Add(Xoh_consts.Code_bgn_open);
			if (style != null) 
				bfr.Add(Xoh_consts.Style_atr).Add(this.style).Add_byte(Byte_ascii.Quote);
			bfr.Add_byte(Byte_ascii.Gt);
		}
		else {
			bfr.Add(Xoh_consts.Pre_bgn_open);
			bfr.Add(Xoh_consts.Style_atr).Add(Xoh_consts.Pre_style_overflow_auto);
			if (style != null) bfr.Add(style);
			bfr.Add(Xoh_consts.__end_quote);
		}
		int text_bgn = xnde.Tag_open_end();
		int text_end = Bry_finder.Find_bwd_while(src, xnde.Tag_close_bgn(), -1, Byte_ascii.Space) + 1; // trim space from end; PAGE:en.w:Comment_(computer_programming) DATE:2014-06-23
		if (line_enabled || enclose_is_none) {
			bfr.Add_byte_nl();
			byte[][] lines = Bry_.Split_lines(Bry_.Mid(src, text_bgn, text_end));
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
				if (enclose_is_none)
					bfr.Add(Html_tag_.Br_inl);
				bfr.Add_byte_nl();
			}
		}
		else
			Xox_mgr_base.Xtn_write_escape(app, bfr, src, text_bgn, text_end);
		if 		(enclose_is_none)		bfr.Add(Xoh_consts.Span_end);
		else if (lang_is_text)			bfr.Add(Xoh_consts.Code_end);
		else							bfr.Add(Xoh_consts.Pre_end);
	}
	private static final byte[] Lang_text = Bry_.new_a7("text"), Style_line = Bry_.new_a7("-moz-user-select:none;"), Style_highlight = Bry_.new_a7("background-color: #FFFFCC;"), Enclose_none = Bry_.new_a7("none");
	public static final byte Xatr_enclose = 2, Xatr_lang = 3, Xatr_style = 4, Xatr_line = 5, Xatr_start = 6, Xatr_highlight = 7;
	private static final Hash_adp_bry xatrs_syntaxHighlight = Hash_adp_bry.ci_a7().Add_str_byte("enclose", Xatr_enclose).Add_str_byte("lang", Xatr_lang).Add_str_byte("style", Xatr_style).Add_str_byte("line", Xatr_line).Add_str_byte("start", Xatr_start).Add_str_byte("highlight", Xatr_highlight);
}
