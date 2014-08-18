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
package gplx.xowa.dbs.hdumps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.hdumps.*;
import gplx.core.btries.*; import gplx.xowa.html.*; import gplx.xowa.html.lnkis.*;
public class Hdump_html_fmtr__body implements Bry_fmtr_arg {
	private Bry_rdr bry_rdr = new Bry_rdr();
	private Xow_wiki wiki; private Hdump_page_itm page;
	private Gfo_usr_dlg usr_dlg; private byte[] file_dir;
	public void Init_by_app(Gfo_usr_dlg usr_dlg, byte [] file_dir) {this.usr_dlg = usr_dlg; this.file_dir = file_dir;}
	public void Init_by_page(Xow_wiki wiki, Hdump_page_itm page) {this.wiki = wiki; this.page = page;}
	public void XferAry(Bry_bfr bfr, int idx) {
		byte[] src = page.Page_body(); int len = src.length;
		Hdump_img_itm[] imgs = page.Img_itms(); int imgs_len = page.Img_itms().length;
		bry_rdr.Src_(src);
		int pos = 0; int rng_bgn = -1;
		Xow_html_mgr html_mgr = wiki.Html_mgr();
		Xoh_file_html_fmtr__base html_fmtr = html_mgr.Html_wtr().Lnki_wtr().File_wtr().File_wtr().Html_fmtr();
		while (pos < len) {
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, len);
			if (o == null) {
				if (rng_bgn == -1) rng_bgn = pos;
				++pos;
			}
			else {
				if (rng_bgn != -1) {
					bfr.Add_mid(src, rng_bgn, pos);
					rng_bgn = -1;
				}
				byte tid = ((Byte_obj_val)o).Val();
				pos = trie.Match_pos();	// position after match; EX: "~{xo.img." positions after "."
				pos = Write_img(bfr, html_mgr, html_fmtr, page, src, imgs, imgs_len, pos, tid); // note no +1; Write_img return pos after }
			}
		}
		if (rng_bgn != -1) bfr.Add_mid(src, rng_bgn, len);
	}
	private int Write_img(Bry_bfr bfr, Xow_html_mgr html_mgr, Xoh_file_html_fmtr__base fmtr, Hdump_page_itm page, byte[] src, Hdump_img_itm[] imgs, int imgs_len, int uid_bgn, byte tid) {
		bry_rdr.Pos_(uid_bgn);
		int uid = bry_rdr.Read_int_to(Byte_ascii.Gt);
		int uid_end = bry_rdr.Pos();										// note that uid_end is set to pos after }
		if (uid == bry_rdr.Or_int())			{usr_dlg.Warn_many("", "", "index is not a valid int; page=~{0} text=~{1}", page.Page_url(), Bry_.Mid_safe(src, uid_bgn, uid_end)); return uid_end;}
		if (!Int_.Between(uid, 0, imgs_len))	{usr_dlg.Warn_many("", "", "index is out of range; page=~{0} idx=~{1} len=~{2}", page.Page_url(), uid, imgs_len); return uid_end;}
		Hdump_img_itm img = imgs[uid];
		int img_view_w = img.View_w();
		if (tid == Tid_img_w) {
			bfr.Add_int_variable(img_view_w);
			return uid_end;
		}
		byte[] a_title = img.Lnki_ttl();
		byte[] a_href = Bry_.Add(A_href_bgn, a_title);
		switch (tid) {
			case Tid_mda_info: fmtr.Html_thumb_part_info	(bfr, uid, a_href, html_mgr.Img_media_info_btn()); return uid_end;
			case Tid_mda_mgnf: fmtr.Html_thumb_part_magnify	(bfr, uid, a_href, a_title, html_mgr.Img_thumb_magnify()); return uid_end;
			case Tid_mda_play: fmtr.Html_thumb_part_play	(bfr, uid, img_view_w, Xoh_file_wtr__basic.Play_btn_max_width, a_href, a_title, html_mgr.Img_media_play_btn()); return uid_end;
		}
		byte[] img_src = Bry_.Add(file_dir, img.View_src()); 
		if (tid == Tid_img) {
			fmtr_img.Bld_bfr_many(bfr, img_src, img_view_w, img.View_h());				
		}
		return uid_end;
	}
	private static final Bry_fmtr fmtr_img = Bry_fmtr.new_(" src='~{src}' width='~{w}' height='~{h}'", "src", "w", "h");
	private static final byte[] A_href_bgn = Bry_.new_ascii_("/wiki/File:");
	public static final byte[]
	  Key_img			= Bry_.new_ascii_("~<img|")
	, Key_img_w			= Bry_.new_ascii_("~<img.w|")
	, Key_mda_play		= Bry_.new_ascii_("~<mda.play|")
	, Key_mda_info		= Bry_.new_ascii_("~<mda.info|")
	, Key_mda_mgnf		= Bry_.new_ascii_("~<mda.mgnf|")
	;
	private static final byte Tid_img = 1, Tid_img_w = 2, Tid_mda_play = 3, Tid_mda_info = 4, Tid_mda_mgnf = 5;
	private Btrie_slim_mgr trie = Btrie_slim_mgr.cs_()
	.Add_bry_bval(Key_img		, Tid_img)
	.Add_bry_bval(Key_img_w		, Tid_img_w)
	.Add_bry_bval(Key_mda_play	, Tid_mda_play)
	.Add_bry_bval(Key_mda_info	, Tid_mda_info)
	.Add_bry_bval(Key_mda_mgnf	, Tid_mda_mgnf)
	;
}
