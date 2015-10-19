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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.vnts.*;
public class Xof_math_html_wtr {
	private Xof_math_itm tmp_math_itm = new Xof_math_itm();
	private Bry_fmtr math_fmtr_latex		= Bry_fmtr.new_("<img id='xowa_math_img_~{math_idx}' src='' width='' height=''/><span id='xowa_math_txt_~{math_idx}'>~{math_text}</span>", "math_idx", "math_text");
	private Bry_fmtr math_fmtr_mathjax	= Bry_fmtr.new_("<span id='xowa_math_txt_~{math_idx}'>~{math_text}</span>", "math_idx", "math_text");
	public void Write(Xoh_html_wtr wtr, Xop_ctx ctx, Xoh_wtr_ctx opts, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		Xoae_app app = ctx.App(); Xowe_wiki wiki = ctx.Wiki(); Xoae_page page = ctx.Cur_page();
		boolean renderer_is_latex = !app.File_mgr().Math_mgr().Renderer_is_mathjax();
		byte[] math_bry = Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		Bry_bfr tmp_bfr = app.Utl__bfr_mkr().Get_b512().Mkr_rls();
		math_bry = Escape_tex(tmp_bfr, !renderer_is_latex, math_bry);
		byte[] math_bry_clean = app.Html_mgr().Js_cleaner().Clean(wiki, math_bry, 0, math_bry.length);	// check for js; 
		if (math_bry_clean != null) math_bry = math_bry_clean;	// js found; use clean version; DATE:2013-08-26
		boolean enabled = app.File_mgr().Math_mgr().Enabled();
		if (renderer_is_latex && app.File_mgr().Math_mgr().Find_itm(tmp_math_itm, page.Wiki().Domain_str(), math_bry)) {
			bfr.Add(Xoh_consts.Img_bgn);
			bfr.Add_str(tmp_math_itm.Png_url().To_http_file_str());
			bfr.Add(Xoh_consts.__inline_quote);
		}
		else
			Write_for_mathjax(bfr, page, enabled, renderer_is_latex, math_bry, tmp_bfr);
	}
	private void Write_for_mathjax(Bry_bfr bfr, Xoae_page page, boolean enabled, boolean renderer_is_latex, byte[] math_bry, Bry_bfr tmp_bfr) {
		int id = page.File_math().Count();
		Xof_math_itm new_math_itm = tmp_math_itm.Clone().Id_(id);
		Bry_fmtr math_fmtr = renderer_is_latex ? math_fmtr_latex : math_fmtr_mathjax;
		boolean armor_math = page.Lang().Vnt_mgr().Enabled() && !renderer_is_latex;	// REF.MW:LangConverter.php|armourMath
		if (armor_math) bfr.Add(Vnt_convert_lang.Bry__armor_bgn);
		math_fmtr.Bld_bfr_many(tmp_bfr, id, math_bry);
		bfr.Add_bfr_and_clear(tmp_bfr);
		if (armor_math) bfr.Add(Vnt_convert_lang.Bry__armor_end);
		if (enabled && renderer_is_latex)	// NOTE: only generate images if math is enabled; otherwise "downloading" prints at bottom of screen, but no action (also a lot of file IO)
			page.File_math().Add(new_math_itm);
	}
	private static byte[] Escape_tex(Bry_bfr tmp_bfr, boolean mathjax, byte[] bry) {return Escape_tex(false, tmp_bfr, mathjax, bry, 0, bry.length);}
	private static byte[] Escape_tex(boolean write_to_bfr, Bry_bfr bfr, boolean mathjax, byte[] bry, int bgn, int end) {
		if (bry == null) return null;
		boolean dirty = write_to_bfr ? true : false;	// if write_to_bfr, then mark true, else bfr.Add_mid(bry, 0, i); will write whole bry again
		byte[] escaped = null;
		for (int i = bgn; i < end; i++) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Lt: 	if (mathjax) escaped = gplx.langs.htmls.Html_entity_.Lt_bry; break;
				case Byte_ascii.Gt: 	if (mathjax) escaped = gplx.langs.htmls.Html_entity_.Gt_bry; break;
				// case Byte_ascii.Amp:	escaped = Const_amp; break;	// TOMBSTONE:never escape ampersand; PAGE:s.w:Matrix_(mathematics); DATE:2014-07-19
				// case Byte_ascii.Quote:	if (mathjax) escaped = gplx.langs.htmls.Html_entity_.Quote_bry; break; // TOMBSTONE:do not escape quote; PAGE:s.w:Matrix_(mathematics); DATE:2014-07-19
				default:
					if (dirty || write_to_bfr)
						bfr.Add_byte(b);
					continue;
			}
			// handle lt, gt, amp, quote; everything else handled by default: continue above
			if (escaped == null) {	// handle do-not-escape calls; EX: Escape(y, y, n, y);
				if (dirty || write_to_bfr)
					bfr.Add_byte(b);
			}
			else {
				if (!dirty) {
					bfr.Add_mid(bry, 0, i);
					dirty = true;
				}
				bfr.Add(escaped);
				escaped = null;
			}
		}
		if (write_to_bfr)
			return null;
		else
			return dirty ? bfr.To_bry_and_clear() : bry;
	}
}
