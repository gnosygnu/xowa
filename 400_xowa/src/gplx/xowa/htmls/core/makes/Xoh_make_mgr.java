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
package gplx.xowa.htmls.core.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.brys.fmtrs.*; import gplx.core.btries.*; import gplx.langs.htmls.encoders.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.makes.imgs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.xtns.gallery.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.apps.fsys.*;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_make_mgr {
	private final    Xoh_hzip_bfr bfr = Xoh_hzip_bfr.New_txt(255); private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255); private final    Bry_rdr_old bry_rdr = new Bry_rdr_old(); private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance;
	private Xoh_cfg_file cfg_file; private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2(); private Xoh_file_html_fmtr__base html_fmtr;
	private final    byte[] root_dir, file_dir; private byte[] file_dir_comm, file_dir_wiki, hiero_img_dir; private final    byte[] wiki_domain;
	private final    Bry_rdr parser = new Bry_rdr();
	private final    Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	private final    Xoh_hdoc_parser make_parser = new Xoh_hdoc_parser(new Xoh_hdoc_wkr__make());
	public Xoh_make_mgr(Gfo_usr_dlg usr_dlg, Xoa_fsys_mgr fsys_mgr, Gfo_url_encoder fsys_encoder, byte[] wiki_domain) {
		this.usr_dlg = usr_dlg;
		this.root_dir = fsys_mgr.Root_dir().To_http_file_bry();
		this.file_dir = fsys_mgr.File_dir().To_http_file_bry();
		this.hiero_img_dir = gplx.xowa.xtns.hieros.Hiero_xtn_mgr.Hiero_root_dir(fsys_mgr).GenSubDir("img").To_http_file_bry();
		this.cfg_file = new Xoh_cfg_file(fsys_encoder, fsys_mgr.Bin_xowa_dir());
		this.html_fmtr = Xoh_file_html_fmtr__hdump.Base;
		this.wiki_domain = wiki_domain;
	}
	public byte[] Parse(byte[] src, Xoh_page hpg, Xow_wiki wiki) {
		hctx.Init_by_page(wiki, hpg);
		hpg.Section_mgr().Add(0, 2, Bry_.Empty, Bry_.Empty).Content_bgn_(0);	// +1 to skip \n
		make_parser.Parse(bfr, hpg, hctx, src);
		hpg.Section_mgr().Set_content(hpg.Section_mgr().Len() - 1, src, src.length);
		return bfr.To_bry_and_clear();
	}
	public byte[] Parse_old(Xoh_page hpg, byte[] src) {
		this.file_dir_comm = tmp_bfr.Add(file_dir).Add(Xow_domain_itm_.Bry__commons).Add_byte_slash().To_bry_and_clear();
		this.file_dir_wiki = tmp_bfr.Add(file_dir).Add(wiki_domain).Add_byte_slash().To_bry_and_clear();
		int src_len = src.length;
		bry_rdr.Init(src);
		hpg.Section_mgr().Add(0, 2, Bry_.Empty, Bry_.Empty).Content_bgn_(0);	// +1 to skip \n
		Xohd_img_itm__base[] imgs = hpg.Img_itms(); int imgs_len = hpg.Img_itms().length;
		int pos = 0; int rng_bgn = -1;
		parser.Init_by_page(hpg.Url_bry_safe(), src, src_len);
		while (pos < src_len) {
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, src_len);
			if (o == null) {			// regular char; set bgn and move to next char
				if (rng_bgn == -1) rng_bgn = pos;
				++pos;
			}
			else {						// special tkn
				if (rng_bgn != -1) {	// pending rng exists; add it
					bfr.Add_mid(src, rng_bgn, pos);
					rng_bgn = -1;
				}
				pos = Parse_itm(bfr, html_fmtr, hpg, src, src_len, imgs, imgs_len, pos, trie.Match_pos(), (Xoh_make_trie_itm)o);
			}
		}
		if (rng_bgn != -1) bfr.Add_mid(src, rng_bgn, src_len);
		hpg.Section_mgr().Set_content(hpg.Section_mgr().Len() - 1, src, src_len);
		return bfr.To_bry_and_clear();
	}
	private int Parse_itm(Bry_bfr bfr, Xoh_file_html_fmtr__base fmtr, Xoh_page hpg, byte[] src, int src_len, Xohd_img_itm__base[] imgs, int imgs_len, int hook_bgn, int hook_end, Xoh_make_trie_itm itm) {
		bry_rdr.Pos_(hook_end);
		int uid = itm.Subst_end_byte() == Byte_ascii.Escape ? -1 : bry_rdr.Read_int_to(itm.Subst_end_byte());
		int uid_end = bry_rdr.Pos();			// set uid_end after subst_end
		int rv = uid_end;
		byte tid = itm.Tid();
		switch (tid) {
			case Xoh_make_trie_.Tid__dir:					bfr.Add(root_dir); return rv;
			case Xoh_make_trie_.Tid__hiero_dir:				bfr.Add(hiero_img_dir); return rv;
		}
		if (itm.Elem_is_xnde()) rv += 2;		// if xnde, skip "/>"
		if (uid == bry_rdr.Or_int())			{usr_dlg.Warn_many("", "", "index is not a valid int; hpg=~{0} text=~{1}", hpg.Url().To_str(), Bry_.Mid_safe(src, hook_end, uid_end)); return uid_end;}
		if (!Int_.Between(uid, 0, imgs_len))	{usr_dlg.Warn_many("", "", "index is out of range; hpg=~{0} idx=~{1} len=~{2}", hpg.Url().To_str(), uid, imgs_len); return uid_end;}
		if (uid >= imgs.length) return rv;
		Xohd_img_itm__base img = imgs[uid];
		int img_view_w = img.Html_w();
		switch (tid) {
			case Xoh_make_trie_.Tid__img_style: 
				bfr.Add(Xoh_make_trie_.Bry_img_style_bgn);
				bfr.Add_int_variable(img_view_w);
				bfr.Add(Xoh_make_trie_.Bry_img_style_end);
				return rv;
		}
		byte[] a_title = img.Lnki_ttl();
		byte[] a_href = Bry_.Add(Xoh_make_trie_.A_href_bgn, a_title);
		try {
			switch (tid) {
				case Xoh_make_trie_.Tid__file_info: fmtr.Html_thumb_part_info	(bfr, uid, true, a_href, cfg_file.Img_media_info_btn()); return rv;
				case Xoh_make_trie_.Tid__file_mgnf: fmtr.Html_thumb_part_magnify(bfr, uid, a_href, a_title, cfg_file.Img_thumb_magnify()); return rv;
				case Xoh_make_trie_.Tid__file_play: fmtr.Html_thumb_part_play	(bfr, uid, true, img_view_w, Xoh_file_wtr__basic.Play_btn_max_width, a_href, a_title, cfg_file.Img_media_play_btn()); return rv;
				case Xoh_make_trie_.Tid__gallery_box_max: {
					Xohd_img_itm__gallery_mgr gly = (Xohd_img_itm__gallery_mgr)hpg.Gallery_itms().Get_by(uid);
					if (gly != null) {	// -1 means no box_max
						byte[] style = Gallery_html_wtr_.Fmtr__ul__style.Bld_bry_many(tmp_bfr, gly.Box_max());
						Gfh_wtr.Write_atr_bry(bfr, Bool_.N, Byte_ascii.Quote, Gfh_atr_.Bry__style, style);
					}
					return rv;
				}
				case Xoh_make_trie_.Tid__gallery_box_w: {
					Xohd_img_itm__gallery_itm gly = (Xohd_img_itm__gallery_itm)img;
					byte[] style = Gallery_html_wtr_.hdump_box_w_fmtr.Bld_bry_many(tmp_bfr, gly.Box_w());
					Gfh_wtr.Write_atr_bry(bfr, Bool_.N, Byte_ascii.Quote, Gfh_atr_.Bry__style, style);
					return rv;
				}
				case Xoh_make_trie_.Tid__gallery_img_w: {
					Xohd_img_itm__gallery_itm gly = (Xohd_img_itm__gallery_itm)img;
					byte[] style = Gallery_html_wtr_.hdump_box_w_fmtr.Bld_bry_many(tmp_bfr, gly.Img_w());
					Gfh_wtr.Write_atr_bry(bfr, Bool_.N, Byte_ascii.Quote, Gfh_atr_.Bry__style, style);
					return rv;
				}
				case Xoh_make_trie_.Tid__gallery_img_pad: {
					Xohd_img_itm__gallery_itm gly = (Xohd_img_itm__gallery_itm)img;
					byte[] style = Gallery_html_wtr_.hdump_img_pad_fmtr.Bld_bry_many(tmp_bfr, gly.Img_pad());
					Gfh_wtr.Write_atr_bry(bfr, Bool_.N, Byte_ascii.Quote, Gfh_atr_.Bry__style, style);
					return rv;
				}
			}
			url_bldr.Init_by_root(img.Orig_repo_id() == Xof_repo_itm_.Repo_remote ? file_dir_comm : file_dir_wiki, Byte_ascii.Slash, false, false, 2);
			url_bldr.Init_by_itm(img.File_is_orig() ? Xof_repo_itm_.Mode_orig : Xof_repo_itm_.Mode_thumb, img.Lnki_ttl(), Xof_file_wkr_.Md5(img.Lnki_ttl()), Xof_ext_.new_by_id_(img.Orig_ext()), img.File_w(), img.Lnki_time(), img.Lnki_page());
			byte[] img_src = url_bldr.Xto_bry(); 
			if (tid == Xoh_make_trie_.Tid__img) {
				fmtr_img.Bld_bfr_many(bfr, img_src, img_view_w, img.Html_h());
			}
		} catch (Exception e) {Xoa_app_.Usr_dlg().Warn_many("", "", "abrv.read: page=~{0} itm=~{1} err=~{2}", hpg.Url_bry_safe(), img == null ? "<NULL>" : img.Data_print(), Err_.Message_gplx_full(e));}
		return rv;
	}
	public static final    Bry_fmtr fmtr_img = Bry_fmtr.new_("src='~{src}' width='~{w}' height='~{h}'", "src", "w", "h");
	private static final    Btrie_slim_mgr trie = Xoh_make_trie_.new_trie();
}
