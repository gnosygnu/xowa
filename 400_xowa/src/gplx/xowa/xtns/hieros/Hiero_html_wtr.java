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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.entitys.*; import gplx.xowa.htmls.core.htmls.*;
class Hiero_html_wtr {
	private Hiero_phoneme_mgr phoneme_mgr;
	private Bry_bfr temp_bfr = Bry_bfr_.Reset(255);		
	public Hiero_html_wtr(Hiero_html_mgr mgr, Hiero_phoneme_mgr phoneme_mgr) {this.phoneme_mgr = phoneme_mgr;}
	public void Init_for_write(Xoh_wtr_ctx hctx) {this.hiero_img_dir = Hiero_xtn_mgr.Img_src_dir;} private byte[] hiero_img_dir = null;
	public void Hr(Bry_bfr bfr)			{bfr.Add(Gfh_tag_.Hr_inl).Add_byte_nl();}
	public void Tbl_eol(Bry_bfr bfr)	{bfr.Add(Tbl_eol_bry);}	
	public byte[] Td_height(int height) {
		return temp_bfr.Add(Option_bgn_bry).Add_int_variable(height).Add(Option_end_bry).To_bry_and_clear();
	}
	private static final    byte[] 
	  Option_bgn_bry = Bry_.new_a7("height: ")
	, Option_end_bry = Bry_.new_a7("px;")
	;
	public void Td(Bry_bfr bfr, byte[] glyph) {
		bfr.Add(Td_bgn_bry).Add(glyph).Add(Td_end_bry);
	}
	private static final    byte[] 
	  Td_bgn_bry = Bry_.new_a7("\n          <td>")
	, Td_end_bry = Bry_.new_a7("\n          </td>")
	;
	public void Cartouche_bgn(Bry_bfr bfr) {
		bfr.Add(Cartouche_bgn_lhs_bry).Add_int_variable((Hiero_html_mgr.Cartouche_width * Hiero_html_mgr.scale) / 100).Add(Cartouche_bgn_rhs_bry);
	}
	private static final    String Tbl_bgn_str = "<table class=\"mw-hiero-table\">";
	private static final    byte[] 
	  Cartouche_bgn_lhs_bry = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( ""
	, "          <td>"
	, "            " + Tbl_bgn_str
	, "              <tr>"
	, "                <td class='mw-hiero-box' style='height: "
	))
	, Cartouche_bgn_rhs_bry = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "px;'>"
	, "                </td>"
	, "              </tr>"
	, "              <tr>"
	, "                <td>"
	, "                  " + Tbl_bgn_str
	, "                    <tr>"
	));
	public void Cartouche_end(Bry_bfr bfr) {
		bfr.Add(Cartouche_end_lhs_bry).Add_int_variable((Hiero_html_mgr.Cartouche_width * Hiero_html_mgr.scale) / 100).Add(Cartouche_end_rhs_bry);
	}
	private static final    byte[] 
	  Cartouche_end_lhs_bry = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( ""
	, "                    </tr>"
	, "                  </table>"
	, "                </td>"
	, "              </tr>"
	, "              <tr>"
	, "                <td class='mw-hiero-box' style='height: "
	))
	, Cartouche_end_rhs_bry = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "px;'>"
	, "                </td>"
	, "              </tr>"
	, "            </table>"
	, "          </td>"
	));
	public byte[] Cartouche_img(Xoh_wtr_ctx hctx, boolean bgn, byte[] glyph) { // render open / close cartouche; note that MW has two branches, but they are both the same
		int height = (int)((Hiero_html_mgr.Max_height * Hiero_html_mgr.scale) / 100);
		Hiero_phoneme_itm phoneme_itm = phoneme_mgr.Get_by_key(glyph); if (phoneme_itm == null) throw Err_.new_wo_type("missing phoneme", "glyph", String_.new_u8(glyph));
		byte[] code = phoneme_itm.Gardiner_code();
		byte[] title = bgn ? Gfh_entity_.Lt_bry : Gfh_entity_.Gt_bry;
		return cartouche_img_fmtr.Bld_bry_many(temp_bfr, hiero_img_dir, code, height, title);
	}
	private static final    Bry_fmtr cartouche_img_fmtr = Bry_fmtr.new_(String_.Concat
	( "\n            <img src='~{path}hiero_~{code}.png'"
	, " height='~{height}' title='~{title}'"
	, " alt='~{title}' />"
	)
	, "path", "code", "height", "title", "hiero_tid");
	public void Tbl_inner(Bry_bfr html_bfr, Bry_bfr text_bfr) {
		html_bfr.Add(Tbl_inner_bgn).Add_bfr_and_clear(text_bfr).Add(Tbl_inner_end); //	$html .= self::TABLE_START . "<tr>\n" . $tableContentHtml . '</tr></table>';
	}
	private static final    byte[] 
	  Tbl_inner_bgn = Bry_.new_u8(String_.Concat_lines_nl_skip_last
	( "      <table class=\"mw-hiero-table\">"
	, "        <tr>"
	))
	, Tbl_inner_end = Bry_.new_u8(String_.Concat_lines_nl_skip_last
	( ""
	, "        </tr>"
	, "      </table>"
	))
	;
	public void Tbl_outer(Bry_bfr bfr, Bry_bfr html_bfr) {
		bfr.Add(Outer_tbl_bgn);
		bfr.Add_bfr_and_clear(html_bfr);
		bfr.Add(Outer_tbl_end);
	}
	private static final    byte[] 
	  Outer_tbl_bgn = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "<table class='mw-hiero-table mw-hiero-outer' dir='ltr'>"
	, "  <tr>"
	, "    <td>"
	, ""
	)
	)
	, Outer_tbl_end = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( ""
	, "    </td>"
	, "  </tr>"
	, "</table>"
	, ""
	));
	public byte[] Img_phoneme(Xoh_wtr_ctx hctx, byte[] img_cls, byte[] td_height, byte[] glyph_esc, byte[] code) {
		byte[] code_esc = Gfh_utl.Escape_html_as_bry(temp_bfr, code);
		byte[] img_title = temp_bfr.Add(code_esc).Add_byte_space().Add_byte(Byte_ascii.Brack_bgn).Add(glyph_esc).Add_byte(Byte_ascii.Brack_end).To_bry_and_clear(); // "~{code} [~{glyph}]"
		return Img(hctx, img_cls, td_height, glyph_esc, code_esc, img_title);
	}
	public byte[] Img_file(Xoh_wtr_ctx hctx, byte[] img_cls, byte[] td_height, byte[] glyph_esc) {return Img(hctx, img_cls, td_height, glyph_esc, glyph_esc, glyph_esc);}
	private byte[] Img(Xoh_wtr_ctx hctx, byte[] img_cls, byte[] td_height, byte[] glyph, byte[] img_src_name, byte[] img_title) {
		byte[] img_src = Bld_img_src(hiero_img_dir, img_src_name);
		return glyph_img_fmtr.Bld_bry_many(temp_bfr, img_cls, Hiero_html_mgr.Image_margin, td_height, img_src, img_title, glyph);
	}
	private static final    byte[] 
	  Tbl_eol_bry = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( ""
	, "        </tr>"
	, "      </table>"
	, "      " + Tbl_bgn_str 
	, "        <tr>"
	));
	private static final    Bry_fmtr glyph_img_fmtr = Bry_fmtr.new_
	( "\n            <img ~{img_cls}style='margin: ~{img_margin}px; ~{option}' src='~{img_src}' title='~{img_title}' alt='~{glyph}' />"
	, "img_cls", "img_margin", "option", "img_src", "img_title", "glyph");
	public byte[] Void(boolean half) { // render void
		int width = Hiero_html_mgr.Max_height;
		if (half) width /= 2;
		return void_fmtr.Bld_bry_many(temp_bfr, width);
	}
	private static final    Bry_fmtr void_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "            <table class=\"mw-hiero-table\" style=\"width: ~{width}px;\">"
	, "              <tr>"
	, "                <td>&#160;"
	, "                </td>"
	, "              </tr>"
	, "            </table>"
	), "width");
	private static byte[] Bld_img_src(byte[] hiero_img_dir, byte[] name) {
		return Bry_.Add(hiero_img_dir, Img_src_prefix, name, Img_src_ext);
	}
	private static final    byte[] Img_src_prefix = Bry_.new_a7("hiero_")
	, Img_src_ext = Bry_.new_a7(".png")
	;
}
