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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*;
import gplx.xowa.files.*; import gplx.xowa.htmls.core.makes.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xoh_file_html_fmtr__hdump extends Xoh_file_html_fmtr__base {
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(128);
	@Override public void Html_full_img(Bry_bfr bfr, gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx hctx, Xoae_page page, Xof_file_itm xfer_itm, int uid, byte[] a_href, byte a_cls, byte a_rel, byte[] a_title, byte[] a_xowa_title, int img_w, int img_h, byte[] img_src, byte[] img_alt, byte img_cls, byte[] img_cls_other) {
		tmp_bfr.Add_str_a7(" data-xoimg=\"");
		tmp_bfr.Add_int_digits(1, xfer_itm.Lnki_type()).Add_byte_pipe();
		tmp_bfr.Add_int_variable(xfer_itm.Lnki_w()).Add_byte_pipe();
		tmp_bfr.Add_int_variable(xfer_itm.Lnki_h()).Add_byte_pipe();
		tmp_bfr.Add_double(xfer_itm.Lnki_upright()).Add_byte_pipe();
		tmp_bfr.Add_double(xfer_itm.Lnki_time()).Add_byte_pipe();
		tmp_bfr.Add_int_variable(xfer_itm.Lnki_page()).Add_byte_quote();
		fmtr__img__full.Bld_bfr_many(bfr
		, a_href, Xoh_lnki_consts.A_cls_to_bry(a_cls), Xoh_lnki_consts.A_rel_to_bry(a_rel), a_title
		, img_alt, tmp_bfr.To_bry_and_clear(), arg_img_core.Init(uid, img_src, img_w, img_h), Xoh_img_cls_.To_html(img_cls, img_cls_other));
	}
	private Bry_fmtr fmtr__img__full = Bry_fmtr.new_
	( "<a href=\"~{a_href}\"~{a_class}~{a_rel}~{a_title}>"
	+   "<img alt=\"~{img_alt}\"~{img_xoimg}~{img_core}~{img_class}/>"
	+ "</a>"
	, "a_href", "a_class", "a_rel", "a_title", "img_alt", "img_xoimg", "img_core", "img_class"
	);
//		public override void Html_full_media(Bry_bfr tmp_bfr, byte[] a_href, byte[] a_title, Bfr_arg html) {
//			fmtr_full_media.Bld_bfr_many(tmp_bfr, a_href, a_title, html);
//		}
	@Override public void Html_thumb_core(Bry_bfr bfr, int uid, byte[] div1_halign, int div2_width, byte[] div2_content) {
		tmp_bfr.Add(Xoh_make_trie_.Bry__img_style);
		tmp_bfr.Add_int_variable(uid);
		tmp_bfr.Add_byte_quote();
		byte[] div2_width_repl = tmp_bfr.To_bry_and_clear();
		fmtr_thumb_core.Bld_bfr_many(bfr, uid, div1_halign, div2_width_repl, div2_content);
	}
	@Override public void Html_thumb_part_magnify(Bry_bfr bfr, int uid, byte[] a_href, byte[] a_title, byte[] img_src)									{Write_xnde(bfr, Xoh_make_trie_.Bry__file_mgnf, uid);}
	@Override public void Html_thumb_part_info(Bry_bfr bfr, int uid, byte[] a_href, byte[] img_src)														{Write_xnde(bfr, Xoh_make_trie_.Bry__file_info, uid);}
	@Override public void Html_thumb_part_play(Bry_bfr bfr, int uid, int a_width, int a_max_width, byte[] a_href, byte[] a_xowa_title, byte[] img_src)	{Write_xnde(bfr, Xoh_make_trie_.Bry__file_play, uid);}
	private static void Write_xnde(Bry_bfr bfr, byte[] key, int uid) {
		bfr.Add(key);
		bfr.Add_int_variable(uid);
		bfr.Add(Bry_xnde_end);
	}	private static final byte[] Bry_xnde_end = Bry_.new_a7("\"/>");
	public static final Xoh_file_html_fmtr__hdump Hdump = new Xoh_file_html_fmtr__hdump(); Xoh_file_html_fmtr__hdump() {}
}
