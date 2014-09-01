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
package gplx.xowa.hdumps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.core.brys.*; import gplx.core.btries.*; import gplx.html.*; import gplx.xowa.html.*;
import gplx.xowa.hdumps.core.*; import gplx.xowa.html.lnkis.*; import gplx.xowa.xtns.gallery.*;	
public class Hdump_html_fmtr__body implements Bry_fmtr_arg {
	private Bry_rdr bry_rdr = new Bry_rdr();
	private Xow_wiki wiki; private Hdump_page page;
	private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_._; private byte[] root_dir, file_dir, hiero_img_dir; private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Init_by_app(Xoa_app app) {
		this.usr_dlg = app.Usr_dlg();
		this.root_dir = app.Fsys_mgr().Root_dir().To_http_file_bry();
		this.file_dir = app.Fsys_mgr().File_dir().To_http_file_bry();
		this.hiero_img_dir = gplx.xowa.xtns.hieros.Hiero_xtn_mgr.Hiero_root_dir(app).GenSubDir("img").To_http_file_bry();
	}
	public void Init_by_page(Xow_wiki wiki, Hdump_page page) {this.wiki = wiki; this.page = page;}
	public void XferAry(Bry_bfr bfr, int idx) {
		byte[] src = page.Page_body(); int len = src.length;
		Hdump_data_img__base[] imgs = page.Img_itms(); int imgs_len = page.Img_itms().length;
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
				pos = trie.Match_pos();	// position after match; EX: "xowa_img='" positions after "'"
				Hdump_html_fmtr_itm itm = (Hdump_html_fmtr_itm)o;
				pos = Write_data(bfr, html_mgr, html_fmtr, page, src, imgs, imgs_len, pos, itm); // note no +1; Write_data return pos after }
			}
		}
		if (rng_bgn != -1) bfr.Add_mid(src, rng_bgn, len);
	}
	private int Write_data(Bry_bfr bfr, Xow_html_mgr html_mgr, Xoh_file_html_fmtr__base fmtr, Hdump_page hpg, byte[] src, Hdump_data_img__base[] imgs, int imgs_len, int uid_bgn, Hdump_html_fmtr_itm itm) {
		bry_rdr.Pos_(uid_bgn);
		int uid = itm.Subst_end_byte() == Byte_ascii.Nil ? -1 : bry_rdr.Read_int_to(itm.Subst_end_byte());
		int uid_end = bry_rdr.Pos();			// set uid_end after subst_end
		int rv = uid_end;
		byte tid = itm.Tid();
		switch (tid) {
			case Hdump_html_consts.Tid_dir:					bfr.Add(root_dir); return rv;
			case Hdump_html_consts.Tid_hiero_dir:			bfr.Add(hiero_img_dir); return rv;
		}
		if (itm.Elem_is_xnde()) rv += 2;		// if xnde, skip "/>"
		if (uid == bry_rdr.Or_int())			{usr_dlg.Warn_many("", "", "index is not a valid int; page=~{0} text=~{1}", hpg.Page_url().Xto_full_str_safe(), Bry_.Mid_safe(src, uid_bgn, uid_end)); return uid_end;}
		if (!Int_.Between(uid, 0, imgs_len))	{usr_dlg.Warn_many("", "", "index is out of range; page=~{0} idx=~{1} len=~{2}", hpg.Page_url().Xto_full_str_safe(), uid, imgs_len); return uid_end;}
		Hdump_data_img__base img = imgs[uid];
		int img_view_w = img.View_w();
		switch (tid) {
			case Hdump_html_consts.Tid_img_style: 
				bfr.Add(Hdump_html_consts.Bry_img_style_bgn);
				bfr.Add_int_variable(img_view_w);
				bfr.Add(Hdump_html_consts.Bry_img_style_end);
				return rv;
		}
		byte[] a_title = img.Lnki_ttl();
		byte[] a_href = Bry_.Add(Hdump_html_consts.A_href_bgn, a_title);
		switch (tid) {
			case Hdump_html_consts.Tid_file_info: fmtr.Html_thumb_part_info	(bfr, uid, a_href, html_mgr.Img_media_info_btn()); return rv;
			case Hdump_html_consts.Tid_file_mgnf: fmtr.Html_thumb_part_magnify(bfr, uid, a_href, a_title, html_mgr.Img_thumb_magnify()); return rv;
			case Hdump_html_consts.Tid_file_play: fmtr.Html_thumb_part_play	(bfr, uid, img_view_w, Xoh_file_wtr__basic.Play_btn_max_width, a_href, a_title, html_mgr.Img_media_play_btn()); return rv;
			case Hdump_html_consts.Tid_gallery_box_max: {
				Hdump_data_img__gallery gly = (Hdump_data_img__gallery)img;
				if (gly.Box_max() > 0) {	// -1 means no box_max
					byte[] style = Gallery_mgr_base.box_style_max_width_fmtr.Bld_bry_many(tmp_bfr, gly.Box_max());
					Html_wtr.Write_atr_bry(bfr, Bool_.N, Byte_ascii.Quote, Html_atr_.Style_bry, style);
				}
				return rv;
			}
			case Hdump_html_consts.Tid_gallery_box_w: {
				Hdump_data_img__gallery gly = (Hdump_data_img__gallery)img;
				byte[] style = Gallery_mgr_base.hdump_box_w_fmtr.Bld_bry_many(tmp_bfr, gly.Box_w());
				Html_wtr.Write_atr_bry(bfr, Bool_.N, Byte_ascii.Quote, Html_atr_.Style_bry, style);
				return rv;
			}
			case Hdump_html_consts.Tid_gallery_img_w: {
				Hdump_data_img__gallery gly = (Hdump_data_img__gallery)img;
				byte[] style = Gallery_mgr_base.hdump_box_w_fmtr.Bld_bry_many(tmp_bfr, gly.Img_w());
				Html_wtr.Write_atr_bry(bfr, Bool_.N, Byte_ascii.Quote, Html_atr_.Style_bry, style);
				return rv;
			}
			case Hdump_html_consts.Tid_gallery_img_pad: {
				Hdump_data_img__gallery gly = (Hdump_data_img__gallery)img;
				byte[] style = Gallery_mgr_base.hdump_img_pad_fmtr.Bld_bry_many(tmp_bfr, gly.Img_pad());
				Html_wtr.Write_atr_bry(bfr, Bool_.N, Byte_ascii.Quote, Html_atr_.Style_bry, style);
				return rv;
			}
		}
		byte[] img_src = Bry_.Add(file_dir, img.View_src()); 
		if (tid == Hdump_html_consts.Tid_img) {
			fmtr_img.Bld_bfr_many(bfr, img_src, img_view_w, img.View_h());
		}
		return rv;
	}
	public static final Bry_fmtr fmtr_img = Bry_fmtr.new_("src='~{src}' width='~{w}' height='~{h}'", "src", "w", "h");
	private static final Btrie_slim_mgr trie = Hdump_html_consts.trie_();
}
class Hdump_html_fmtr_itm {
	public Hdump_html_fmtr_itm(byte tid, boolean elem_is_xnde, byte subst_end_byte, byte[] key) {this.tid = tid; this.key = key; this.elem_is_xnde = elem_is_xnde; this.subst_end_byte = subst_end_byte;}
	public byte Tid() {return tid;} private final byte tid;
	public byte[] Key() {return key;} private final byte[] key;
	public boolean Elem_is_xnde() {return elem_is_xnde;} private final boolean elem_is_xnde;
	public byte Subst_end_byte() {return subst_end_byte;} private final byte subst_end_byte;
}
