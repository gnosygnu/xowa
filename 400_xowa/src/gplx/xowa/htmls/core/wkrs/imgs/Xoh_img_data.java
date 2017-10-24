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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.files.*;
import gplx.xowa.xtns.imaps.*; import gplx.xowa.xtns.pagebanners.*;
public class Xoh_img_data implements Xoh_data_itm {
	public int						Tid() {return Xoh_hzip_dict_.Tid__img;}
	public int						Src_bgn() {return src_bgn;} private int src_bgn;
	public int						Src_end() {return src_end;} private int src_end;
	public Xoh_anch_href_data		Anch_href() {return anch_href;} private Xoh_anch_href_data anch_href = new Xoh_anch_href_data();
	public Xoh_anch_cls_data		Anch_cls() {return anch_cls;} private Xoh_anch_cls_data anch_cls = new Xoh_anch_cls_data();
	public boolean					Anch_rel_nofollow_exists() {return anch_rel_is_nofollow;} private boolean anch_rel_is_nofollow;
	public int						Anch_title_bgn() {return anch_title_bgn;} private int anch_title_bgn;
	public int						Anch_title_end() {return anch_title_end;} private int anch_title_end;
	public boolean					Anch_title_exists() {return anch_title_end != -1;}
	public Bry_obj_ref				Anch_xo_ttl() {return anch_xo_ttl;} private Bry_obj_ref anch_xo_ttl = Bry_obj_ref.New_empty();
	public Xoh_img_src_data			Img_src() {return img_src;} private final    Xoh_img_src_data img_src = new Xoh_img_src_data();
	public Xoh_img_cls_data			Img_cls() {return img_cls;} private final    Xoh_img_cls_data img_cls = new Xoh_img_cls_data();
	public Xoh_img_xoimg_data		Img_xoimg() {return img_xoimg;} private final    Xoh_img_xoimg_data img_xoimg = new Xoh_img_xoimg_data();
	public int						Img_alt_bgn() {return img_alt_bgn;} private int img_alt_bgn;
	public int						Img_alt_end() {return img_alt_end;} private int img_alt_end;
	public boolean					Img_alt__diff__anch_title() {return img_alt__diff_anch_title;} private boolean img_alt__diff_anch_title;
	public int						Img_w() {return img_w;} private int img_w;
	public int						Img_h() {return img_h;} private int img_h;
	public boolean					Img_w__diff__file_w() {return !img_src.File_is_orig() && img_w != img_src.File_w();}
	public boolean					Img_is_vid() {return img_is_vid;} private boolean img_is_vid;
	public boolean					Img_wo_anch() {return img_wo_anch;} private boolean img_wo_anch;
	public int						Img_imap_idx() {return img_imap_idx;} private int img_imap_idx;
	public boolean					Img_is_gallery() {return img_is_gallery;} private boolean img_is_gallery; public void Img_is_gallery_(boolean v) {this.img_is_gallery = v;}
	public Pgbnr_itm				Img_pgbnr() {return img_pgbnr;} private final    Pgbnr_itm img_pgbnr = new Pgbnr_itm();
	public void Clear() {
		this.img_alt__diff_anch_title = anch_rel_is_nofollow = img_is_vid = img_wo_anch = img_is_gallery = false;
		this.src_bgn = src_end = anch_title_bgn = anch_title_end = img_w = img_h = img_alt_bgn = img_alt_end = -1;
		this.img_imap_idx = -1;
		anch_href.Clear(); anch_cls.Clear();
		img_src.Clear(); img_cls.Clear(); img_xoimg.Clear(); img_pgbnr.Clear_by_hdump();
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag anch_head, Gfh_tag unused) {
		Gfh_tag img_tag = anch_head;
		Bry_err_wkr err_wkr = tag_rdr.Err_wkr();
		this.img_wo_anch = anch_head.Name_id() == Gfh_tag_.Id__img;
		if (img_wo_anch) {
			Gfh_atr xowa_title = anch_head.Atrs__get_by_or_empty(Xoh_img_xoimg_data.Bry__data_xowa_title);	// data-xowa-title='A.png'
			anch_xo_ttl.Val_(xowa_title.Val());
		}
		else {
			if (anch_head.Name_id() == Gfh_tag_.Id__div) {	// video / audio
				if (!anch_head.Atrs__cls_eq(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_data.Atr__id__xowa_media_div)) return false;	// handle fake-thumbs created through en.w:Template:Image_label_begin; PAGE:en.w:Blackburnshire; DATE:2016-01-04
				img_is_vid = true;
				tag_rdr.Tag__move_fwd_head();				// next <div>
				anch_head = tag_rdr.Tag__move_fwd_head();	// next <div>
			}
			this.src_bgn = anch_head.Src_bgn();															// <a
			if (!anch_href.Parse(err_wkr, hctx, src, anch_head)) return false;							// href='/wiki/File:A.png'
			if (!anch_cls.Parse(err_wkr, src, anch_head)) return false;									// class='image'
			Gfh_atr anch_title = anch_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__title);					// title='abc'
			anch_title_bgn = anch_title.Val_bgn(); anch_title_end = anch_title.Val_end();
			Gfh_atr xowa_title = anch_head.Atrs__get_by_or_empty(Bry__atr__xowa_title);					// xowa_title='A.png'
			if (xowa_title.Val_dat_exists()) anch_xo_ttl.Val_(xowa_title.Val());
			img_tag = tag_rdr.Tag__move_fwd_head();
		}
		img_tag.Chk_name_or_fail(Gfh_tag_.Id__img);														// <img
		if (img_tag.Atrs__cls_has(gplx.xowa.xtns.pagebanners.Pgbnr_xtn_mgr.Bry__cls__wpb_banner_image)) {	// handle pagebanner; EX: <img class="wpb-banner-image">
			img_pgbnr.Init_by_parse(img_tag);
		}
		img_xoimg.Parse(err_wkr, src, img_tag);															// data-xoimg='...'
		this.img_w = img_tag.Atrs__get_as_int_or(Gfh_atr_.Bry__width, Xof_img_size.Size__neg1);			// width='220'
		this.img_h = img_tag.Atrs__get_as_int_or(Gfh_atr_.Bry__height, Xof_img_size.Size__neg1);		// height='110'
		Gfh_atr img_alt = img_tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__alt);								// alt='File:A.png'
		img_alt_bgn = img_alt.Val_bgn(); img_alt_end = img_alt.Val_end();
		img_cls.Init_by_parse(err_wkr, src, img_tag);													// class='thumbborder'
		img_alt__diff_anch_title = !Bry_.Match(src, img_alt_bgn, img_alt_end, src, anch_title_bgn, anch_title_end);
		if (!img_src.Parse(err_wkr, hctx, hctx.Wiki__domain_bry(), img_tag)) return false;				// src='...'
		if (anch_xo_ttl.Val_is_empty()) {
			anch_xo_ttl.Val_(img_src.File_ttl_bry());
			if (anch_xo_ttl.Val_is_empty())
				anch_xo_ttl.Val_(anch_href.Ttl_page_db());
		}
		this.img_imap_idx = Get_imap_idx(tag_rdr.Err_wkr(), img_tag);
		if (img_wo_anch) {
			src_end = img_tag.Src_end();
			return true;
		}
		Gfh_tag anch_tail = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__a);									// </a>
		this.src_end = anch_tail.Src_end();
		if (anch_href.Site_exists()) {
			Xow_domain_itm itm = Xow_domain_itm_.parse(Bry_.Mid(src, anch_href.Site_bgn(), anch_href.Site_end()));
			anch_rel_is_nofollow = itm.Domain_type_id() == Xow_domain_tid_.Tid__other;
		}
		if (img_is_vid) {
			tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
			anch_head = tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__div);
			src_end = anch_head.Src_end();
		}
		return true;
	}
	public void Init_by_decode(boolean anch_rel_is_nofollow, int anch_title_bgn, int anch_title_end, boolean img_wo_anch, boolean img_is_vid, int img_w, int img_h, int img_alt_bgn, int img_alt_end, int img_imap_idx) {
		this.anch_rel_is_nofollow = anch_rel_is_nofollow;
		this.anch_title_bgn = anch_title_bgn; this.anch_title_end = anch_title_end;
		this.img_wo_anch = img_wo_anch; this.img_is_vid = img_is_vid;
		this.img_w = img_w; this.img_h = img_h;
		this.img_alt_bgn = img_alt_bgn; this.img_alt_end = img_alt_end;
		this.img_imap_idx = img_imap_idx;
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_data rv = new Xoh_img_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
	private static int Get_imap_idx(Bry_err_wkr err_wkr, Gfh_tag img_tag) {
		byte[] val = img_tag.Atrs__get_as_bry(Imap_xtn_mgr.Bry__usemap__name); if (Bry_.Len_eq_0(val)) return -1;
		int rv = Bry_.To_int_or(val, Imap_xtn_mgr.Bry__usemap__prefix.length, val.length, -1);
		if (rv == -1) {
			err_wkr.Warn("invalid imap_idx", "val", val);
			return rv;
		}
		return rv;
	}
	public static final    byte[]
	  Bry__cls__anch__image			= Bry_.new_a7("image")
	, Bry__cls__img__thumbimage		= Bry_.new_a7("thumbimage")
	, Bry__atr__xowa_title			= Bry_.new_a7("xowa_title")
	;
}
