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
import gplx.xowa.files.*; import gplx.xowa.hdumps.htmls.*;
public class Xoh_file_html_fmtr__hdump extends Xoh_file_html_fmtr__base {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(128);
	@gplx.Internal @Override protected Xoh_arg_img_core New_arg_img_core() {return new Xoh_arg_img_core__hdump();}
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
