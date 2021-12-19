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
package gplx.xowa.htmls.core.wkrs.imgs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.args.BryBfrArgClearable;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_url_;
import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.types.custom.brys.fmts.fmtrs.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.files.*; import gplx.xowa.xtns.imaps.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_img_wtr implements BryBfrArg, Xoh_wtr_itm {
	private final BryBfrArgClearable[] arg_ary;
	private final Bfr_arg__hatr_arg img_xowa_image = new Bfr_arg__hatr_arg(Xoh_img_xoimg_data.Bry__data_xowa_image);
	private final Bfr_arg__hatr_id img_id = Bfr_arg__hatr_id.New_id(Xoh_img_mgr.Bry__html_uid), vid_play_id = Bfr_arg__hatr_id.New_id("xowa_file_play_"), img_imap_usemap = new Bfr_arg__hatr_id(Imap_xtn_mgr.Bry__usemap__name, Imap_xtn_mgr.Bry__usemap__prefix);
	private final Bfr_arg__hatr_int img_w = new Bfr_arg__hatr_int(Gfh_atr_.Bry__width), img_h = new Bfr_arg__hatr_int(Gfh_atr_.Bry__height);
	private final Bfr_arg__hatr_bry
	  anch_href = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__href)
	, anch_rel = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__rel)
	, anch_xowa_title = new Bfr_arg__hatr_bry(Xoh_img_data.Bry__atr__xowa_title)
	, anch_cls = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__class)
	, anch_title = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__title)
	, img_alt = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__alt)
	, img_src = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__src)
	, img_cls = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__class)
	, img_xowa_title = new Bfr_arg__hatr_bry(Xoh_img_xoimg_data.Bry__data_xowa_title)
	;
	private final Bfr_arg__pgbnr img_pgbnr_atrs = new Bfr_arg__pgbnr();
	private boolean img_is_vid; private boolean img_wo_anch;
	private int div_w;
	public Xoh_img_wtr() {
		arg_ary = new BryBfrArgClearable[]
		{ anch_href, anch_rel, anch_cls, anch_title, anch_xowa_title
		, img_id, img_xowa_title, img_xowa_image, img_src, img_w, img_h, img_cls, img_alt
		, img_pgbnr_atrs
		};
	}
	public Xof_fsdb_itm	Fsdb_itm() {return fsdb_itm;} private Xof_fsdb_itm fsdb_itm;
	public Xoh_img_wtr Clear() {			
		for (BryBfrArgClearable arg : arg_ary)
			arg.BfrArgClear();
		vid_play_id.BfrArgClear();
		img_imap_usemap.BfrArgClear();
		img_is_vid = false; img_wo_anch = false;
		div_w = -1;
		return this;
	}
	public Xoh_img_wtr Anch_cls_(byte[] v)	{anch_cls.Set_by_bry(v); return this;}
	public Xoh_img_wtr Img_id_(int uid)		{img_id.Set(uid); return this;}
	public boolean Init_by_parse(BryWtr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_img_data data) {
		if (!Init_by_decode(hpg, hctx, src, data)) return false;
		this.AddToBfr(bfr);
		return true;
	}
	public void Init_html(int html_w, int html_h, byte[] src_bry) {
		img_w.Set_by_int(html_w);
		img_h.Set_by_int(html_h);
		if (gplx.core.envs.Op_sys.Cur().Tid_is_drd())
			src_bry = BryUtlByWtr.Replace(src_bry, AsciiByte.QuestionBry, Bry__qarg__esc);	// NOTE: if drd, always escape "?" as "%3F" PAGE:en.w:Cleopatra en.w:Cave_painting; DATE:2016-01-31
		img_src.Set_by_bry(src_bry);
		this.div_w = html_w;
	}
	private void Init_xoimg(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] lnki_ttl, Xoh_img_xoimg_data img_xowa_image) {
		fsdb_itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, hpg.Wiki().Domain_itm().Abrv_xo(), lnki_ttl, Xop_lnki_type.To_flag(img_xowa_image.Lnki_type()), img_xowa_image.Lnki_upright(), img_xowa_image.Lnki_w(), img_xowa_image.Lnki_h(), img_xowa_image.Lnki_time(), img_xowa_image.Lnki_page(), Xof_patch_upright_tid_.Tid_all);
		hctx.Cache_mgr().Find(hpg.Wiki(), hpg.Url_bry_safe(), fsdb_itm);
		this.img_xowa_image.Set_by_arg(img_xowa_image.Clone());	// NOTE: must clone b/c img_xowa_image is member of Xoh_img_data which is poolable (and cleared); PAGE:en.w:Almagest; DATE:2016-01-05
		this.Init_html(fsdb_itm.Html_w(), fsdb_itm.Html_h(), fsdb_itm.Html_view_url().To_http_file_bry());
		this.div_w = fsdb_itm.Lnki_w();
	}
	public boolean Init_by_decode(Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, Xoh_data_itm data_itm) {
		Xoh_img_data data = (Xoh_img_data)data_itm;
		this.Clear();
		this.img_is_vid = data.Img_is_vid();
		this.img_wo_anch = data.Img_wo_anch();
		this.fsdb_itm = hpg.Img_mgr().Make_img(data.Img_is_gallery());

		byte[] xowa_title = data.Anch_xo_ttl().Val();

		// set lnki_ttl to img_src.file_ttl;
		byte[] lnki_ttl = data.Img_src().File_ttl_bry();
		if (lnki_ttl == null) {
			// in most cases, @src; set lnki_ttl from @xowa_title instead; EX: '<img src="" xowa-title="A.png">' DATE:2019-03-10
			lnki_ttl = xowa_title;
		}

		// url-decode; must decode since HTML will be url-encoded, but fsdb dbs will be actual characters; EX: A%C3%A9b -> A�b
		if (lnki_ttl != null) // NOTE: @src and @xowa_title will be null for score
			lnki_ttl = Xoa_ttl.Replace_spaces(Gfo_url_encoder_.Href.Decode(lnki_ttl));

		boolean write_xowa_file_title = true;
		if		(data.Img_pgbnr().Exists()) {
			img_pgbnr_atrs.Set(data.Img_pgbnr());
			hpg.Html_data().Head_mgr().Itm__pgbnr().Enabled_y_();
			this.Init_xoimg(hpg, hctx, lnki_ttl, Xoh_img_xoimg_data.New__pgbnr());
		}
		else if (data.Img_xoimg().Val_dat_exists()) {
			Xoh_img_xoimg_data img_xowa_image = data.Img_xoimg();
			this.Init_xoimg(hpg, hctx, lnki_ttl, img_xowa_image);
		}
		else if (data.Img_w() != -1) {	// orig exists or some hard-coded image (hiero)
			Xoh_img_src_data img_src_data = data.Img_src();
			
			byte[] img_src_bry = img_src_data.Src_mid();
			this.Init_html(data.Img_w(), data.Img_h(), img_src_bry);
			int file_w = data.Img_src().File_w();
			// NOTE: init lnki with "64|file_w|-1|-1|-1|-1"; DATE:2016-08-10
			fsdb_itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, hctx.Cache__wiki_abrv(img_src_data.Repo_is_commons()), lnki_ttl, Xop_lnki_type.Tid_orig_known, Xop_lnki_tkn.Upright_null, file_w, Xof_img_size.Null, data.Img_src().File_time(), data.Img_src().File_page(), Xof_patch_upright_tid_.Tid_all);
			fsdb_itm.Init_at_gallery_bgn(data.Img_w(), data.Img_h(), file_w);
			fsdb_itm.Html_view_url_(Io_url_.New__http_or_fail(img_src_bry));
			fsdb_itm.File_is_orig_(data.Img_src().File_is_orig());

			// ASSUME: if file_w != img_w, then page has packed gallery; PAGE:en.w:Mexico; DATE:2016-08-14
			if (file_w != data.Img_w())
				hpg.Html_data().Xtn_gallery_packed_exists_y_();
			write_xowa_file_title = false;
		}
		if (data.Anch_rel_nofollow_exists()) anch_rel.Set_by_bry(gplx.xowa.htmls.core.wkrs.lnkes.Xoh_lnke_dict_.Html__rel__nofollow);
		if (!hctx.Mode_is_diff()) {
			this.Img_id_(fsdb_itm.Html_uid());
			vid_play_id.Set(fsdb_itm.Html_uid());
		}
		anch_href.Set_by_mid(data.Anch_href().Rng_src(), data.Anch_href().Rng_bgn(), data.Anch_href().Rng_end());
		anch_cls.Set_by_arg(data.Anch_cls());
		anch_title.Set_by_mid_or_null(src, data.Anch_title_bgn(), data.Anch_title_end());
		if (	data.Img_wo_anch() 					// anchor-less image
			||	BryUtl.IsNotNullOrEmpty(xowa_title))		// regular anch with image
			anch_xowa_title.Set_by_bry(xowa_title);

		if (write_xowa_file_title)
			img_xowa_title.Set_by_bry(xowa_title);
		img_alt.Set_by_mid_or_empty(src, data.Img_alt_bgn(), data.Img_alt_end());
		img_cls.Set_by_arg(data.Img_cls());
		if (data.Img_imap_idx() != -1) img_imap_usemap.Set(data.Img_imap_idx());
		return true;
	}
	public void Init_by_gly(BryBfrArgClearable href, byte[] xowa_title, BryBfrArgClearable xoimg) {
		anch_href.Set_by_arg(href);
		anch_xowa_title.Set_by_bry(xowa_title);
		img_xowa_image.Set_by_arg(xoimg);
	}
	public void AddToBfr(BryWtr bfr) {
		if (img_wo_anch)
			img_wo_anch_fmtr.BldToBfrMany(bfr, img_id, img_xowa_title, img_xowa_image, img_alt, img_src, img_w, img_h, img_cls, img_imap_usemap);
		else {
			if (img_is_vid) {
				bfr.Add(Vid__bry__bgn);
			}
			img_fmtr.BldToBfrMany(bfr, (Object[])arg_ary);
			if (img_is_vid) {
				if (div_w <= 0) div_w = Xof_img_size.Thumb_width_img;	// if no div_w, default to 220;
				vid_fmt.Bld_many(bfr, vid_play_id, anch_xowa_title, div_w - 2, div_w);
			}
		}
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_wtr rv = new Xoh_img_wtr(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
	private static final byte[] Vid__bry__bgn = BryUtl.NewA7("<div class=\"xowa_media_div\">\n<div>");
	private static final BryFmtr
	  img_fmtr = BryFmtr.New
	( "<a~{anch_href}~{anch_rel}~{anch_cls}~{anch_title}~{anch_xowa_title}><img~{img_id}~{img_xowa_title}~{img_xowa_image}~{img_src}~{img_w}~{img_h}~{img_cls}~{img_alt}~{img_pgbnr_atrs}></a>"
	, "anch_href", "anch_rel", "anch_cls", "anch_title", "anch_xowa_title", "img_id", "img_xowa_title", "img_xowa_image", "img_src", "img_w", "img_h", "img_cls", "img_alt", "img_pgbnr_atrs")
	, img_wo_anch_fmtr = BryFmtr.New
	( "<img~{img_id}~{img_xowa_title}~{img_xowa_image}~{img_alt}~{img_src}~{img_w}~{img_h}~{img_cls}~{img_imap_usemap}/>"
	, "img_id", "img_xowa_title", "img_xowa_image", "img_src", "img_w", "img_h", "img_cls", "img_alt", "img_imap_usemap")
	;
	private final BryFmt
	  vid_fmt = BryFmt.Auto_nl_apos
	( "</div>"
	, "<div><a~{vid_play_id} href=''~{xowa_title} class='xowa_media_play' style='width:~{a_width}px;max-width:~{a_max_width}px;' alt='Play sound'></a></div>"
	, "</div>"
	);
	private static final byte[] Bry__qarg__esc = BryUtl.NewA7("%3F");
}
