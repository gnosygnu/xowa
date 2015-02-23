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
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
import gplx.xowa.wikis.*; import gplx.xowa.apps.fsys.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.html.lnkis.*; import gplx.xowa.xtns.gallery.*;
import gplx.xowa2.gui.*;
public class Hdump_html_body {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255); private Bry_rdr bry_rdr = new Bry_rdr(); private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_._;
	private Xog_page hpg; private Xoh_cfg_file cfg_file; private Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_(); private Xoh_file_html_fmtr__base html_fmtr;
	private byte[] root_dir, file_dir, file_dir_comm, file_dir_wiki, hiero_img_dir;
	public Hdump_html_body Init_by_app(Gfo_usr_dlg usr_dlg, Xoa_fsys_mgr fsys_mgr, Url_encoder fsys_encoder) {
		this.usr_dlg = usr_dlg;
		this.root_dir = fsys_mgr.Root_dir().To_http_file_bry();
		this.file_dir = fsys_mgr.File_dir().To_http_file_bry();
		this.hiero_img_dir = gplx.xowa.xtns.hieros.Hiero_xtn_mgr.Hiero_root_dir(fsys_mgr).GenSubDir("img").To_http_file_bry();
		this.cfg_file = new Xoh_cfg_file(fsys_encoder, fsys_mgr.Bin_xowa_dir());
		this.html_fmtr = Xoh_file_html_fmtr__hdump.Base;
		return this;
	}
	public Hdump_html_body Init_by_page(byte[] domain_bry, Xog_page hpg) {
		this.hpg = hpg;
		file_dir_comm = tmp_bfr.Add(file_dir).Add(Xow_domain_.Domain_bry_commons).Add_byte_slash().Xto_bry_and_clear();
		file_dir_wiki  = tmp_bfr.Add(file_dir).Add(domain_bry).Add_byte_slash().Xto_bry_and_clear();
		return this;
	}
	public void Write(Bry_bfr bfr) {
		byte[] src = hpg.Page_body(); int len = src.length;
		Hdump_data_img__base[] imgs = hpg.Img_itms(); int imgs_len = hpg.Img_itms().length;
		bry_rdr.Src_(src);
		int pos = 0; int rng_bgn = -1;
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
				pos = Write_data(bfr, html_fmtr, hpg, src, imgs, imgs_len, pos, itm); // note no +1; Write_data return pos after }
			}
		}
		if (rng_bgn != -1) bfr.Add_mid(src, rng_bgn, len);
	}
	private int Write_data(Bry_bfr bfr, Xoh_file_html_fmtr__base fmtr, Xog_page hpg, byte[] src, Hdump_data_img__base[] imgs, int imgs_len, int uid_bgn, Hdump_html_fmtr_itm itm) {
		bry_rdr.Pos_(uid_bgn);
		int uid = itm.Subst_end_byte() == Byte_ascii.Nil ? -1 : bry_rdr.Read_int_to(itm.Subst_end_byte());
		int uid_end = bry_rdr.Pos();			// set uid_end after subst_end
		int rv = uid_end;
		byte tid = itm.Tid();
		switch (tid) {
			case Hdump_html_consts.Tid_dir:					bfr.Add(root_dir); return rv;
			case Hdump_html_consts.Tid_hiero_dir:			bfr.Add(hiero_img_dir); return rv;
			case Hdump_html_consts.Tid_redlink:				return Write_redlink(bfr, hpg, uid, rv);
		}
		if (itm.Elem_is_xnde()) rv += 2;		// if xnde, skip "/>"
		if (uid == bry_rdr.Or_int())			{usr_dlg.Warn_many("", "", "index is not a valid int; hpg=~{0} text=~{1}", hpg.Page_url().Xto_full_str_safe(), Bry_.Mid_safe(src, uid_bgn, uid_end)); return uid_end;}
		if (!Int_.Between(uid, 0, imgs_len))	{usr_dlg.Warn_many("", "", "index is out of range; hpg=~{0} idx=~{1} len=~{2}", hpg.Page_url().Xto_full_str_safe(), uid, imgs_len); return uid_end;}
		if (uid >= imgs.length) return rv;
		Hdump_data_img__base img = imgs[uid];
		int img_view_w = img.Html_w();
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
			case Hdump_html_consts.Tid_file_info: fmtr.Html_thumb_part_info	(bfr, uid, a_href, cfg_file.Img_media_info_btn()); return rv;
			case Hdump_html_consts.Tid_file_mgnf: fmtr.Html_thumb_part_magnify(bfr, uid, a_href, a_title, cfg_file.Img_thumb_magnify()); return rv;
			case Hdump_html_consts.Tid_file_play: fmtr.Html_thumb_part_play	(bfr, uid, img_view_w, Xoh_file_wtr__basic.Play_btn_max_width, a_href, a_title, cfg_file.Img_media_play_btn()); return rv;
			case Hdump_html_consts.Tid_gallery_box_max: {
				Hdump_data_gallery gly = (Hdump_data_gallery)hpg.Gly_itms().Fetch(uid);
				if (gly != null) {	// -1 means no box_max
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
		url_bldr.Init_by_root(img.File_repo_id() == Xof_repo_itm.Repo_remote ? file_dir_comm : file_dir_wiki, Byte_ascii.Slash, false, false, 2);
		url_bldr.Init_by_itm(img.File_is_orig() ? Xof_repo_itm.Mode_orig : Xof_repo_itm.Mode_thumb, img.Lnki_ttl(), Xof_xfer_itm_.Md5_(img.Lnki_ttl()), Xof_ext_.new_by_id_(img.File_ext_id()), img.File_w(), img.File_time(), img.File_page());
		byte[] img_src = url_bldr.Xto_bry(); 
		if (tid == Hdump_html_consts.Tid_img) {
			fmtr_img.Bld_bfr_many(bfr, img_src, img_view_w, img.Html_h());
		}
		return rv;
	}
	private int Write_redlink(Bry_bfr bfr, Xog_page hpg, int uid, int rv) {
		int[] redlink_uids = hpg.Redlink_uids(); if (redlink_uids == null) return rv;
		int redlink_uid_max = redlink_uids.length;
		if (uid < redlink_uid_max && redlink_uids[uid] == 1)
			bfr.Add(Redlink_cls_new);
		else
			bfr.Del_by_1();
		return rv;
	}	private static final byte[] Redlink_cls_new = Bry_.new_ascii_("class='new'");
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
