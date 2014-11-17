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
package gplx.xowa.html.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.html.*;
import gplx.xowa.files.*; import gplx.xowa.hdumps.htmls.*;
public class Xoh_file_html_fmtr__hdump extends Xoh_file_html_fmtr__base {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(128);
	@gplx.Internal @Override protected Xoh_arg_img_core New_arg_img_core() {return new Xoh_arg_img_core__hdump();}
//		public override void Html_full_media(Bry_bfr tmp_bfr, byte[] a_href, byte[] a_title, Bry_fmtr_arg html) {
//			fmtr_full_media.Bld_bfr_many(tmp_bfr, a_href, a_title, html);
//		}
	@Override public void Html_full_img(Bry_bfr tmp_bfr, Xoh_wtr_ctx hctx, Xoa_page page, Xof_xfer_itm xfer_itm, int uid
		, byte[] a_href, byte a_cls, byte a_rel, byte[] a_title, byte[] a_xowa_title
		, int img_w, int img_h, byte[] img_src, byte[] img_alt, byte img_cls, byte[] img_cls_other) {
		tmp_bfr.Add_str_ascii("<a xtid='a_img_full' xatrs='");
		tmp_bfr.Add_str_ascii(a_cls == Xoh_lnki_consts.Tid_a_cls_none ? "0|" : "1|");	// a_cls			: "" || image
		tmp_bfr.Add_str_ascii(a_rel == Xoh_lnki_consts.Tid_a_rel_none ? "0|" : "1|");	// a_rel			: "" || nofollow
		tmp_bfr.Add_int_fixed(img_cls, 1).Add_byte_pipe();								// img_cls			: "" || thumbborder || thumbimage || other
		tmp_bfr.Add_safe(img_cls_other).Add_byte_pipe();								// img_cls_other	: "" || {other}
		tmp_bfr.Add_int_variable(uid).Add_byte_pipe();
		Html_utl.Escape_html_to_bfr(tmp_bfr, img_alt, 0, img_alt.length, Bool_.N, Bool_.N, Bool_.N, Bool_.N, Bool_.Y);
		tmp_bfr.Add_str_ascii("'/>");
	}
	@Override public void Html_thumb_core(Bry_bfr bfr, int uid, byte[] div1_halign, int div2_width, byte[] div2_content) {
		tmp_bfr.Add(Hdump_html_consts.Key_img_style);
		tmp_bfr.Add_int_variable(uid);
		tmp_bfr.Add_byte_apos();
		byte[] div2_width_repl = tmp_bfr.Xto_bry_and_clear();
		fmtr_thumb_core.Bld_bfr_many(bfr, uid, div1_halign, div2_width_repl, div2_content);
	}
	@Override public void Html_thumb_part_magnify(Bry_bfr bfr, int uid, byte[] a_href, byte[] a_title, byte[] img_src)									{Write_xnde(bfr, Hdump_html_consts.Key_file_mgnf, uid);}
	@Override public void Html_thumb_part_info(Bry_bfr bfr, int uid, byte[] a_href, byte[] img_src)														{Write_xnde(bfr, Hdump_html_consts.Key_file_info, uid);}
	@Override public void Html_thumb_part_play(Bry_bfr bfr, int uid, int a_width, int a_max_width, byte[] a_href, byte[] a_xowa_title, byte[] img_src)	{Write_xnde(bfr, Hdump_html_consts.Key_file_play, uid);}
	public static void Write_xnde(Bry_bfr bfr, byte[] key, int uid) {
		bfr.Add(key);
		bfr.Add_int_variable(uid);
		bfr.Add(Bry_xnde_end);
	}	private static final byte[] Bry_xnde_end = Bry_.new_ascii_("'/>");
        public static final Xoh_file_html_fmtr__hdump Hdump = new Xoh_file_html_fmtr__hdump(); Xoh_file_html_fmtr__hdump() {}
}
