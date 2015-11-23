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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
public class Xoh_img_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	public String Key() {return Xoh_hzip_dict_.Key__img;}
	private final Xoh_img_xoimg_parser xoimg_parser = new Xoh_img_xoimg_parser();		
	public Xoh_img_bldr Bldr() {return bldr;} private Xoh_img_bldr bldr = new Xoh_img_bldr();
	public Bfr_arg__href Anch_href_arg() {return anch_href_arg;} private final Bfr_arg__href anch_href_arg = new Bfr_arg__href();
	private final Bry_obj_ref
	  anch_cls_mid		= Bry_obj_ref.New_empty()
	, anch_title_mid	= Bry_obj_ref.New_empty()
	, anch_href_mid		= Bry_obj_ref.New_empty()
	, img_alt_mid		= Bry_obj_ref.New_empty()
	, img_src_mid		= Bry_obj_ref.New_empty()
	, img_cls_mid		= Bry_obj_ref.New_empty()
	;
	public Xoh_img_hzip Encode(Bry_bfr bfr, Hzip_stat_itm stat_itm, byte[] src, Xoh_img_parser arg, boolean write_hdr) {
		// img_map: <img id="xowa_file_img_100" alt="" src="file:///J:/xowa/file/commons.wikimedia.org/orig/b/8/a/7/Solar_System_Template_Final.png" width="666" height="36" usemap="#imagemap_1_1">
		Xoh_anch_href_parser	anch_href = arg.Anch_href();
		Bry_obj_ref				anch_page = arg.Anch_page();
		byte					anch_href_tid = anch_href.Tid();
		boolean					anch__ns_id_needs_saving = anch_href.Tid_has_ns() && anch_href.Page_ns_id() != Xow_ns_.Tid__file;
		Html_atr				anch_title = arg.Anch_title();
		Xoa_ttl					anch_href_ttl = anch_href.Page_ttl();
		Xoh_img_xoimg_parser	img_xoimg = arg.Img_xoimg();
		Xoh_img_cls_parser		img_cls = arg.Img_cls();
		Xoh_img_src_parser		img_src = arg.Img_src();
		boolean					img__alt_diff_from_anch_title = arg.Img_alt__diff__anch_title();
		boolean					file__src_exists = !img_xoimg.Val_dat_exists();
		boolean					anch_href_diff_file = !Bry_.Match(anch_page.Val(), anch_page.Val_bgn(), anch_page.Val_end(), anch_href.Page_ttl().Page_db());

		flag_bldr.Set(Flag__file__w_diff_from_html			, file__src_exists && arg.Img_w__diff__file_w());
		flag_bldr.Set(Flag__file__time_exists				, file__src_exists && img_src.File_time_exists());
		flag_bldr.Set(Flag__file__page_exists				, file__src_exists && img_src.File_page_exists());
		flag_bldr.Set(Flag__file__is_orig					, file__src_exists && img_src.File_is_orig());
		flag_bldr.Set(Flag__file__repo_is_local				, file__src_exists && !img_src.Repo_is_commons());
		flag_bldr.Set(Flag__file__src_exists				, file__src_exists);
		flag_bldr.Set(Flag__img__cls_other_exists			, img_cls.Other_exists());
		flag_bldr.Set(Flag__anch__ns_is_image				, anch_href.Page_ns_id_is_image());
		flag_bldr.Set(Flag__anch__cls_tid					, arg.Anch_cls().Tid());
		flag_bldr.Set(Flag__anch__ns_id_needs_saving		, anch__ns_id_needs_saving);
		flag_bldr.Set(Flag__img__alt_diff_from_anch_title	, img__alt_diff_from_anch_title);
		flag_bldr.Set(Flag__anch__href_diff_file			, anch_href_diff_file);
		flag_bldr.Set(Flag__anch__title_missing				, anch_title.Val_dat_missing());
		flag_bldr.Set(Flag__img__cls_tid					, img_cls.Cls_tid());
		flag_bldr.Set(Flag__anch__href_tid					, anch_href.Tid());
		// Tfds.Dbg(flag_bldr.Encode(), Array_.To_str(flag_bldr.Val_ary()));

		if (write_hdr) bfr.Add(Xoh_hzip_dict_.Bry__img);
		Xoh_hzip_int_.Encode(2, bfr, flag_bldr.Encode());
		if (anch_href_tid == Xoh_anch_href_parser.Tid__inet)
			anch_href_mid.Mid_(src, anch_href.Val_bgn(), anch_href.Val_end());
		else
			anch_href_mid.Val_(anch_href_ttl.Page_db());
		bfr.Add_bry_ref_obj(anch_href_mid);
		bfr.Add_byte(Xoh_hzip_dict_.Escape);
		if (anch_href_diff_file) {
			arg.Anch_page().Bfr_arg__add(bfr);
			bfr.Add_byte(Xoh_hzip_dict_.Escape);
		}
		switch (anch_href_tid) {
			case Xoh_anch_href_parser.Tid__anch:
			case Xoh_anch_href_parser.Tid__inet:
				break;
			case Xoh_anch_href_parser.Tid__wiki:
			case Xoh_anch_href_parser.Tid__site:
				if (anch__ns_id_needs_saving)
					Xoh_lnki_dict_.Ns_encode(bfr, anch_href_ttl.Ns().Id());
				break;
		}
		if (file__src_exists) {
			Xoh_hzip_int_.Encode(2, bfr, Xoh_hzip_int_.Neg_1_adj + arg.Img_w());
			Xoh_hzip_int_.Encode(2, bfr, Xoh_hzip_int_.Neg_1_adj + arg.Img_h());
			if (arg.Img_w__diff__file_w())		Xoh_hzip_int_.Encode(2, bfr, Xoh_hzip_int_.Neg_1_adj + img_src.File_w());
			if (img_src.File_time_exists())		Xoh_hzip_int_.Encode(1, bfr, Xoh_hzip_int_.Neg_1_adj + img_src.File_time());
			if (img_src.File_page_exists())		Xoh_hzip_int_.Encode(1, bfr, Xoh_hzip_int_.Neg_1_adj + img_src.File_page());
		}
		else
			bfr.Add_mid(src, img_xoimg.Val_bgn(), img_xoimg.Val_end()).Add_byte(Xoh_hzip_dict_.Escape);
		if (anch_title.Val_dat_exists())		bfr.Add_mid(src, anch_title.Val_bgn(), anch_title.Val_end()).Add_byte(Xoh_hzip_dict_.Escape);
		if (img__alt_diff_from_anch_title)		bfr.Add_mid(src, arg.Img_alt().Val_bgn(), arg.Img_alt().Val_end()).Add_byte(Xoh_hzip_dict_.Escape);
		if (img_cls.Other_exists())		bfr.Add_mid(src, img_cls.Other_bgn(), img_cls.Other_end()).Add_byte(Xoh_hzip_dict_.Escape);
		return this;
	}
	public int Decode(Bry_bfr bfr, boolean write_to_bfr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int hook_bgn) {
		// decode rdr
		int flag = rdr.Read_int_by_base85(2);
		flag_bldr.Decode(flag);
		boolean file__is_orig						= flag_bldr.Get_as_bool(Flag__file__is_orig);
		boolean file__repo_is_local				= flag_bldr.Get_as_bool(Flag__file__repo_is_local);
		boolean file__src_exists					= flag_bldr.Get_as_bool(Flag__file__src_exists);
		boolean img__cls_other_exists				= flag_bldr.Get_as_bool(Flag__img__cls_other_exists);
		boolean anch__ns_is_image					= flag_bldr.Get_as_bool(Flag__anch__ns_is_image);
		boolean anch__ns_id_needs_saving			= flag_bldr.Get_as_bool(Flag__anch__ns_id_needs_saving);
		int anch__cls_tid						= flag_bldr.Get_as_int(Flag__anch__cls_tid);
		boolean img__alt_diff_from_anch_title		= flag_bldr.Get_as_bool(Flag__img__alt_diff_from_anch_title);
		boolean anch_href_diff_file				= flag_bldr.Get_as_bool(Flag__anch__href_diff_file);
		boolean anch__title_missing				= flag_bldr.Get_as_bool(Flag__anch__title_missing);
		int img_cls								= flag_bldr.Get_as_int(Flag__img__cls_tid);
		int anch__href_tid						= flag_bldr.Get_as_int(Flag__anch__href_tid);
		byte[] page_db = rdr.Read_bry_to();
		byte[] file_db = page_db;
		if (anch_href_diff_file) file_db = rdr.Read_bry_to();
		int anch_href_ns = -1;
		if (anch__ns_id_needs_saving)
			anch_href_ns = Xoh_lnki_dict_.Ns_decode(rdr);
		int img_xoimg_bgn = -1, img_xoimg_end = -1, img_w = -1, img_h = -1, file_time = -1, file_page = -1;
		xoimg_parser.Clear();
		if (file__src_exists) {
			img_w = rdr.Read_int_by_base85(2) - Xoh_hzip_int_.Neg_1_adj;
			img_h = rdr.Read_int_by_base85(2) - Xoh_hzip_int_.Neg_1_adj;
		}
		else {
			img_xoimg_bgn = rdr.Pos();
			img_xoimg_end = rdr.Find_fwd_lr();
			xoimg_parser.Parse(rdr, src, img_xoimg_bgn, img_xoimg_end);
		}
		int anch_title_bgn = -1, anch_title_end = -1;
		if (!anch__title_missing) {
			anch_title_bgn = rdr.Pos();
			anch_title_end = rdr.Find_fwd_lr();				
		}
		int img_alt_bgn = -1, img_alt_end = -1;
		if (!anch__title_missing) {
			img_alt_bgn = anch_title_bgn;
			img_alt_end = anch_title_end;
		}
		if (img__alt_diff_from_anch_title) {
			img_alt_bgn = rdr.Pos();
			img_alt_end = rdr.Find_fwd_lr();
		}
		byte[] img_cls_other = Bry_.Empty;
		if (img__cls_other_exists)
			img_cls_other = Bry_.Mid(src, rdr.Pos(), rdr.Find_fwd_lr());
		int rv = rdr.Move_to(rdr.Pos());

		// transform values
		boolean anch_rel_is_nofollow = false;
		if (anch__ns_id_needs_saving) {
			Xoa_ttl anch_href_ttl = hctx.Wiki__ttl_parser().Ttl_parse(anch_href_ns, page_db);
			anch_href_arg.Set_by_page(anch_href_ttl.Full_db());
		}
		else {
			if (anch__href_tid == Xoh_anch_href_parser.Tid__inet) {
				anch_href_arg.Set_by_raw(gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(page_db));
				anch_rel_is_nofollow = true;
			}
			else {
				if (anch__ns_is_image)
					anch_href_arg.Set_by_raw(Bry_.Add(gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki, gplx.xowa.wikis.nss.Xow_ns_.Alias__image__bry, Byte_ascii.Colon_bry, gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(page_db)));
				else
					anch_href_arg.Set_by_file(gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(page_db));
			}
		}
		// NOTE: src must go underneath ttl
		Xof_url_bldr url_bldr = hctx.File__url_bldr();
		url_bldr.Init_by_root(file__repo_is_local ? hctx.Fsys__file__wiki() : hctx.Fsys__file__comm(), Byte_ascii.Slash, false, false, Md5_depth);
		url_bldr.Init_by_itm(file__is_orig ? Xof_repo_itm_.Mode_orig : Xof_repo_itm_.Mode_thumb, file_db, Xof_file_wkr_.Md5(file_db), Xof_ext_.new_by_ttl_(file_db), img_w, file_time, file_page);
		byte[] img_src = url_bldr.Xto_bry();

		anch_cls_mid.Val_(Xoh_anch_cls_.To_val(anch__cls_tid));
		anch_title_mid.Mid_(src, anch_title_bgn, anch_title_end); if (anch_title_bgn == anch_title_end) anch_title_mid.Val_(null);
		img_alt_mid.Mid_(src, img_alt_bgn, img_alt_end); if (img_alt_mid.Val_is_empty()) img_alt_mid.Val_(Bry_.Empty);
		img_src_mid.Val_(img_src);
		img_cls_mid.Val_(Xoh_img_cls_.To_val_or_null(img_cls, img_cls_other));

		bldr.Make(bfr, hpg, hctx, src, file_db, xoimg_parser, anch_rel_is_nofollow, anch_href_arg, anch_cls_mid, anch_title_mid, img_w, img_h, img_src_mid, img_cls_mid, img_alt_mid);
		if (write_to_bfr) bldr.Wtr().Bfr_arg__add(bfr);

		return rv;
	}
	public int				Pool__idx() {return pool_idx;} private int pool_idx;
	public void				Pool__clear (Object[] args) {}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_hzip rv = new Xoh_img_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
	public static int Md5_depth = 2;
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_( 1, 1, 1, 1		, 1, 1, 1, 1	, 2, 1, 1, 1	, 1, 2, 2);	
	private static final int // SERIALIZED
	  Flag__file__w_diff_from_html			=  0
	, Flag__file__time_exists				=  1
	, Flag__file__page_exists				=  2
	, Flag__file__is_orig					=  3
	, Flag__file__repo_is_local				=  4
	, Flag__file__src_exists				=  5
	, Flag__img__cls_other_exists			=  6
	, Flag__anch__ns_is_image				=  7
	, Flag__anch__cls_tid					=  8	// none, image
	, Flag__anch__ns_id_needs_saving		=  9
	, Flag__img__alt_diff_from_anch_title	= 10
	, Flag__anch__href_diff_file			= 11
	, Flag__anch__title_missing				= 12
	, Flag__img__cls_tid					= 13	// none, thumbimage, thumbborder
	, Flag__anch__href_tid					= 14	// wiki, site, anch, inet
	;
}
