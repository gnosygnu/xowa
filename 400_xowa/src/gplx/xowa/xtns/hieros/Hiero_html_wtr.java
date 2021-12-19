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
package gplx.xowa.xtns.hieros;
import gplx.langs.htmls.Gfh_tag_;
import gplx.langs.htmls.Gfh_utl;
import gplx.langs.html.HtmlEntityCodes;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
class Hiero_html_wtr {
	private Hiero_phoneme_mgr phoneme_mgr;
	private BryWtr temp_bfr = BryWtr.NewAndReset(255);
	public Hiero_html_wtr(Hiero_html_mgr mgr, Hiero_phoneme_mgr phoneme_mgr) {this.phoneme_mgr = phoneme_mgr;}
	public void Init_for_write(Xoh_wtr_ctx hctx) {this.hiero_img_dir = Hiero_xtn_mgr.Img_src_dir;} private byte[] hiero_img_dir = null;
	public void Hr(BryWtr bfr)			{bfr.Add(Gfh_tag_.Hr_inl).AddByteNl();}
	public void Tbl_eol(BryWtr bfr)	{bfr.Add(Tbl_eol_bry);}
	public byte[] Td_height(int height) {
		return temp_bfr.Add(Option_bgn_bry).AddIntVariable(height).Add(Option_end_bry).ToBryAndClear();
	}
	private static final byte[]
	  Option_bgn_bry = BryUtl.NewA7("height: ")
	, Option_end_bry = BryUtl.NewA7("px;")
	;
	public void Td(BryWtr bfr, byte[] glyph) {
		bfr.Add(Td_bgn_bry).Add(glyph).Add(Td_end_bry);
	}
	private static final byte[]
	  Td_bgn_bry = BryUtl.NewA7("\n          <td>")
	, Td_end_bry = BryUtl.NewA7("\n          </td>")
	;
	public void Cartouche_bgn(BryWtr bfr) {
		bfr.Add(Cartouche_bgn_lhs_bry).AddIntVariable((Hiero_html_mgr.Cartouche_width * Hiero_html_mgr.scale) / 100).Add(Cartouche_bgn_rhs_bry);
	}
	private static final String Tbl_bgn_str = "<table class=\"mw-hiero-table\">";
	private static final byte[]
	  Cartouche_bgn_lhs_bry = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "          <td>"
	, "            " + Tbl_bgn_str
	, "              <tr>"
	, "                <td class='mw-hiero-box' style='height: "
	))
	, Cartouche_bgn_rhs_bry = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( "px;'>"
	, "                </td>"
	, "              </tr>"
	, "              <tr>"
	, "                <td>"
	, "                  " + Tbl_bgn_str
	, "                    <tr>"
	));
	public void Cartouche_end(BryWtr bfr) {
		bfr.Add(Cartouche_end_lhs_bry).AddIntVariable((Hiero_html_mgr.Cartouche_width * Hiero_html_mgr.scale) / 100).Add(Cartouche_end_rhs_bry);
	}
	private static final byte[]
	  Cartouche_end_lhs_bry = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "                    </tr>"
	, "                  </table>"
	, "                </td>"
	, "              </tr>"
	, "              <tr>"
	, "                <td class='mw-hiero-box' style='height: "
	))
	, Cartouche_end_rhs_bry = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( "px;'>"
	, "                </td>"
	, "              </tr>"
	, "            </table>"
	, "          </td>"
	));
	public byte[] Cartouche_img(Xoh_wtr_ctx hctx, boolean bgn, byte[] glyph) { // render open / close cartouche; note that MW has two branches, but they are both the same
		int height = (int)((Hiero_html_mgr.Max_height * Hiero_html_mgr.scale) / 100);
		Hiero_phoneme_itm phoneme_itm = phoneme_mgr.Get_by_key(glyph); if (phoneme_itm == null) throw ErrUtl.NewArgs("missing phoneme", "glyph", StringUtl.NewU8(glyph));
		byte[] code = phoneme_itm.Gardiner_code();
		byte[] title = bgn ? HtmlEntityCodes.LtBry : HtmlEntityCodes.GtBry;

		// ISSUE#:553; DATE:2019-09-25
		byte[] img_src_dir = hiero_img_dir;
		byte[] img_hdump_atr = BryUtl.Empty;
		if (hctx.Mode_is_hdump()) {
			img_src_dir = BryUtl.Empty;
			img_hdump_atr = Hiero_hdump_wkr.HDUMP_ATR;
		}

		return cartouche_img_fmtr.BldToBryMany(temp_bfr, img_src_dir, code, height, title, img_hdump_atr);
	}
	public void Tbl_inner(BryWtr html_bfr, BryWtr text_bfr) {
		html_bfr.Add(Tbl_inner_bgn).AddBfrAndClear(text_bfr).Add(Tbl_inner_end); //	$html .= self::TABLE_START . "<tr>\n" . $tableContentHtml . '</tr></table>';
	}
	private static final byte[]
	  Tbl_inner_bgn = BryUtl.NewU8(StringUtl.ConcatLinesNlSkipLast
	( "      <table class=\"mw-hiero-table\">"
	, "        <tr>"
	))
	, Tbl_inner_end = BryUtl.NewU8(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "        </tr>"
	, "      </table>"
	))
	;
	public void Tbl_outer(BryWtr bfr, BryWtr html_bfr) {
		bfr.Add(Outer_tbl_bgn);
		bfr.AddBfrAndClear(html_bfr);
		bfr.Add(Outer_tbl_end);
	}
	private static final byte[]
	  Outer_tbl_bgn = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
	, "  <tr>"
	, "    <td>"
	, ""
	)
	)
	, Outer_tbl_end = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "    </td>"
	, "  </tr>"
	, "</table>"
	, ""
	));
	public byte[] Img_phoneme(Xoh_wtr_ctx hctx, byte[] img_cls, byte[] td_height, byte[] glyph_esc, byte[] code) {
		byte[] code_esc = Gfh_utl.Escape_html_as_bry(temp_bfr, code);
		byte[] img_title = temp_bfr.Add(code_esc).AddByteSpace().AddByte(AsciiByte.BrackBgn).Add(glyph_esc).AddByte(AsciiByte.BrackEnd).ToBryAndClear(); // "~{code} [~{glyph}]"
		return Img(hctx, img_cls, td_height, glyph_esc, code_esc, img_title);
	}
	public byte[] Img_file(Xoh_wtr_ctx hctx, byte[] img_cls, byte[] td_height, byte[] glyph_esc) {return Img(hctx, img_cls, td_height, glyph_esc, glyph_esc, glyph_esc);}
	private byte[] Img(Xoh_wtr_ctx hctx, byte[] img_cls, byte[] td_height, byte[] glyph, byte[] img_src_name, byte[] img_title) {
		// ISSUE#:553; DATE:2019-09-25
		byte[] img_src_dir = hiero_img_dir;
		byte[] img_hdump_atr = BryUtl.Empty;
		if (hctx.Mode_is_hdump()) {
			img_src_dir = BryUtl.Empty;
			img_hdump_atr = Hiero_hdump_wkr.HDUMP_ATR;
		}
		return glyph_img_fmtr.BldToBryMany(temp_bfr, img_cls, Hiero_html_mgr.Image_margin, td_height, img_src_dir, img_src_name, img_title, glyph, img_hdump_atr);
	}
	private static final byte[]
	  Tbl_eol_bry = BryUtl.NewA7(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "        </tr>"
	, "      </table>"
	, "      " + Tbl_bgn_str 
	, "        <tr>"
	));
	public byte[] Void(boolean half) { // render void
		int width = Hiero_html_mgr.Max_height;
		if (half) width /= 2;
		return void_fmtr.BldToBryMany(temp_bfr, width);
	}

	private static final BryFmtr glyph_img_fmtr = BryFmtr.New(StringUtl.Concat
	( "\n            "
	, "<img ~{img_cls}style='margin: ~{img_margin}px; ~{option}'"
	, " src='~{hiero_img_dir}hiero_~{img_code}.png' title='~{img_title}' alt='~{glyph}' ~{data-xowa-hdump}/>"
	), "img_cls", "img_margin", "option", "hiero_img_dir", "img_code", "img_title", "glyph", "data-xowa-hdump");
	private static final BryFmtr cartouche_img_fmtr = BryFmtr.New(StringUtl.Concat
	( "\n            "
	, "<img src='~{hiero_img_dir}hiero_~{img_code}.png'"
	, " height='~{height}' title='~{title}'"
	, " alt='~{title}' ~{data_xowa_hdump}/>"
	), "hiero_img_dir", "img_code", "height", "title", "data_xowa_hdump");
	private static final BryFmtr void_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "            <table class=\"mw-hiero-table\" style=\"width: ~{width}px;\">"
	, "              <tr>"
	, "                <td>&#160;"
	, "                </td>"
	, "              </tr>"
	, "            </table>"
	), "width");
}
