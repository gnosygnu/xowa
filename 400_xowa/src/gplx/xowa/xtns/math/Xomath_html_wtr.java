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
import gplx.core.envs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.entitys.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
class Xomath_html_wtr {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(512);
	private final    Xomath_subst_mgr subst_mgr = new Xomath_subst_mgr();
	private final    gplx.core.security.Hash_algo md5_wkr = gplx.core.security.Hash_algo_.New__md5();
	private final    Bry_fmt 
	  fmt__latex	= Bry_fmt.Auto( "<img id='xowa_math_img_~{math_idx}' src='' width='' height=''/><span id='xowa_math_txt_~{math_idx}'>~{math_text}</span>")
	, fmt__mathjax	= Bry_fmt.Auto("<span id='xowa_math_txt_~{math_idx}'>~{math_text}</span>");

	public void Write(Bry_bfr bfr, Xop_ctx ctx, Xop_xnde_tkn xnde, byte[] src, boolean is_latex, boolean enabled) {
		// init vars
		Xoae_app app = ctx.App(); Xowe_wiki wiki = ctx.Wiki(); Xoae_page page = ctx.Page();

		// get math_bry; also, escape if mathjax and check js
		byte[] math_bry = Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		if (!is_latex) math_bry = Escape_for_mathjax(tmp_bfr, math_bry);
		byte[] scrubbed_js = wiki.Html_mgr().Js_cleaner().Clean(wiki, math_bry, 0, math_bry.length);
		if (scrubbed_js != null) math_bry = scrubbed_js;	// js found; use clean version; DATE:2013-08-26

		// if latex, (a) calc md5 and url; (b) write <img> or add to queue
		int uid = page.File_math().Count();
		if (is_latex) {
			byte[] math_src = subst_mgr.Subst(math_bry);
			byte[] md5 = md5_wkr.Hash_bry_as_bry(math_src);

			// make url
			byte dir_spr = Op_sys.Cur().Fsys_dir_spr_byte();
			tmp_bfr
				.Add_str_u8(app.Fsys_mgr().Root_dir().GenSubDir_nest("file", wiki.Domain_str(), "math").Raw())	// add dir
				.Add_byte(md5[0]).Add_byte(dir_spr)
				.Add_byte(md5[1]).Add_byte(dir_spr)
				.Add_byte(md5[2]).Add_byte(dir_spr)
				.Add(md5).Add_str_a7(".png");
			Io_url png = Io_url_.new_fil_(tmp_bfr.To_str_and_clear());

			// if url exists, just write <img> directly
			if (Io_mgr.Instance.ExistsFil(png)) {
				Gfh_tag_.Bld_lhs_bgn(bfr, Gfh_tag_.Bry__img);
				Gfh_atr_.Add(bfr, Gfh_atr_.Bry__src, png.To_http_file_bry());
				Gfh_tag_.Bld_lhs_end_nde(bfr);
				return;
			}
			else {
				// if enabled, add to pending queue; NOTE: must check enabled, else "downloading" prints at bottom of screen; also avoids file IO
				if (enabled)
					page.File_math().Add(new Xomath_latex_itm(uid, math_src, md5, png));
			}
		}

		// write html: <span>math_expr</math>
		byte[] unique_bry = wiki.Parser_mgr().Uniq_mgr().Add(math_bry);
		Bry_fmt fmt = is_latex ? fmt__latex : fmt__mathjax;
		fmt.Bld_many(tmp_bfr, uid, unique_bry);
		bfr.Add_bfr_and_clear(tmp_bfr);
	}
	private static byte[] Escape_for_mathjax(Bry_bfr tmp, byte[] src) {
		if (src == null) return null;
		boolean dirty = false;
		byte[] escaped = null;

		// loop bytes
		for (int i = 0; i < src.length; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Lt: 		escaped = Gfh_entity_.Lt_bry; break;
				case Byte_ascii.Gt: 		escaped = Gfh_entity_.Gt_bry; break;
				// case Byte_ascii.Amp:		escaped = Const_amp; break;				// TOMBSTONE:do not escape ampersand; PAGE:s.w:Matrix_(mathematics); DATE:2014-07-19
				// case Byte_ascii.Quote:	escaped = Gfh_entity_.Quote_bry; break; // TOMBSTONE:do not escape quote; PAGE:s.w:Matrix_(mathematics); DATE:2014-07-19
				default:
					if (dirty)
						tmp.Add_byte(b);
					continue;
			}

			// add escaped; note that only lt / gt will enter here; all other bytes are handled by "default / continue" above
			if (!dirty) {
				dirty = true;
				tmp.Add_mid(src, 0, i);
			}
			tmp.Add(escaped);
			escaped = null;
		}
		return dirty ? tmp.To_bry_and_clear() : src;
	}
}
