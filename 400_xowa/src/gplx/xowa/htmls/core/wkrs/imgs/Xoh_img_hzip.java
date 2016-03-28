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
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.core.encoders.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
public class Xoh_img_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(32);
	private final Xoh_img_xoimg_hzip xoimg = new Xoh_img_xoimg_hzip();
	public int Tid() {return Xoh_hzip_dict_.Tid__img;}
	public String Key() {return Xoh_hzip_dict_.Key__img;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Xoh_img_wtr Wtr() {return wtr;} private Xoh_img_wtr wtr = new Xoh_img_wtr();
	public byte[] Anch_href_bry() {return anch_href_bry;} private byte[] anch_href_bry;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_img_data data = (Xoh_img_data)data_obj;
		Xoh_anch_href_data anch_href = data.Anch_href();
		boolean					img_wo_anch = data.Img_wo_anch();
		Bry_obj_ref				anch_page = data.Anch_page();
		byte					anch_href_tid = anch_href.Tid();
		boolean					anch__ns_is_custom = anch_href.Ttl_ns_custom() != null;
		boolean					anch__ns_id_needs_saving = Xoh_anch_href_data.Ns_exists(anch_href.Tid()) && anch_href.Ttl_ns_id() != Xow_ns_.Tid__file;
		boolean					anch_title_exists = data.Anch_title_exists();
		Xoh_img_xoimg_data		img_xoimg = data.Img_xoimg();
		Xoh_img_cls_data		img_cls = data.Img_cls();
		Xoh_img_src_data		img_src = data.Img_src();
		boolean					img__alt_diff_from_anch_title = data.Img_alt__diff__anch_title();
		boolean					file__src_exists = !img_xoimg.Val_dat_exists();
		boolean					anch_href_diff_file = !img_wo_anch && !Bry_.Match(anch_page.Val(), anch_page.Val_bgn(), anch_page.Val_end(), anch_href.Ttl_page_db());
		boolean	img__imap_exists = flag_bldr.Set_as_bool(Flag__img__imap_exists				, data.Img_imap_idx() != -1);
		flag_bldr.Set(Flag__img__wo_anch					, img_wo_anch);
		flag_bldr.Set(Flag__img__is_vid						, data.Img_is_vid());
		flag_bldr.Set(Flag__file__w_diff_from_html			, file__src_exists && data.Img_w__diff__file_w());
		flag_bldr.Set(Flag__file__time_exists				, file__src_exists && img_src.File_time_exists());
		flag_bldr.Set(Flag__file__page_exists				, file__src_exists && img_src.File_page_exists());
		flag_bldr.Set(Flag__file__is_orig					, file__src_exists && img_src.File_is_orig());
		flag_bldr.Set(Flag__file__repo_is_local				, file__src_exists && !img_src.Repo_is_commons());
		flag_bldr.Set(Flag__file__src_exists				, file__src_exists);
		flag_bldr.Set(Flag__img__cls_other_exists			, img_cls.Other_exists());
		flag_bldr.Set(Flag__anch__ns_is_custom				, anch__ns_is_custom);
		flag_bldr.Set(Flag__anch__cls_tid					, data.Anch_cls().Tid());
		flag_bldr.Set(Flag__anch__ns_id_needs_saving		, anch__ns_id_needs_saving);
		flag_bldr.Set(Flag__img__alt_diff_from_anch_title	, img__alt_diff_from_anch_title);
		flag_bldr.Set(Flag__anch__href_diff_file			, anch_href_diff_file);
		flag_bldr.Set(Flag__anch__title_missing				, !anch_title_exists);
		flag_bldr.Set(Flag__img__cls_tid					, img_cls.Cls_tid());
		flag_bldr.Set(Flag__anch__href_tid					, anch_href.Tid());
		// Tfds.Dbg(flag_bldr.Encode(), Array_.To_str(flag_bldr.Val_ary()));
		if (wkr_is_root) bfr.Add(hook);
		Gfo_hzip_int_.Encode(2, bfr, flag_bldr.Encode());
		if (img_wo_anch)		bfr.Add_hzip_mid(anch_page.Val(), anch_page.Val_bgn(), anch_page.Val_end());
		else {
			switch (anch_href_tid) {
				case Xoh_anch_href_data.Tid__inet:
					bfr.Add_mid(src, anch_href.Rng_bgn(), anch_href.Rng_end());
					break;
				case Xoh_anch_href_data.Tid__site:
					bfr.Add_mid(src, anch_href.Site_bgn(), anch_href.Site_end()).Add_byte(Byte_ascii.Pipe).Add(anch_href.Ttl_page_db());
					break;
				case Xoh_anch_href_data.Tid__wiki:
				case Xoh_anch_href_data.Tid__anch:
					bfr.Add(anch_href.Ttl_page_db());
					break;
			}
			bfr.Add_byte(Xoh_hzip_dict_.Escape);
		}
		if (anch_href_diff_file) {
			data.Anch_page().Bfr_arg__add(bfr);
			bfr.Add_byte(Xoh_hzip_dict_.Escape);
		}
		switch (anch_href_tid) {
			case Xoh_anch_href_data.Tid__anch:
			case Xoh_anch_href_data.Tid__inet:
				break;
			case Xoh_anch_href_data.Tid__wiki:
			case Xoh_anch_href_data.Tid__site:
				if (anch__ns_id_needs_saving)
					Xoh_lnki_dict_.Ns_encode(bfr, anch_href.Ttl_ns_id());
				break;
		}
		if (anch__ns_is_custom) bfr.Add(data.Anch_href().Ttl_ns_custom()).Add_byte(Xoh_hzip_dict_.Escape);
		if (file__src_exists) {
			Gfo_hzip_int_.Encode(2, bfr, Gfo_hzip_int_.Neg_1_adj + data.Img_w());
			Gfo_hzip_int_.Encode(2, bfr, Gfo_hzip_int_.Neg_1_adj + data.Img_h());
			if (data.Img_w__diff__file_w())		Gfo_hzip_int_.Encode(2, bfr, Gfo_hzip_int_.Neg_1_adj + img_src.File_w());
			if (img_src.File_time_exists())		Gfo_hzip_int_.Encode(1, bfr, Gfo_hzip_int_.Neg_1_adj + img_src.File_time());
			if (img_src.File_page_exists())		Gfo_hzip_int_.Encode(1, bfr, Gfo_hzip_int_.Neg_1_adj + img_src.File_page());
		}
		else
			xoimg.Encode(bfr, hctx.Hzip__stat(), src, img_xoimg);
		if (anch_title_exists)					bfr.Add_hzip_mid(src, data.Anch_title_bgn(), data.Anch_title_end());
		if (img__alt_diff_from_anch_title)		bfr.Add_hzip_mid(src, data.Img_alt_bgn(), data.Img_alt_end());
		if (img_cls.Other_exists())				bfr.Add_hzip_mid(src, img_cls.Other_bgn(), img_cls.Other_end());
		if (img__imap_exists)					bfr.Add_hzip_int(1, data.Img_imap_idx());
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		Xoh_img_data data = (Xoh_img_data)data_itm; data.Clear();
		int flag = rdr.Read_hzip_int(2); flag_bldr.Decode(flag);
		boolean img_imap_exists					= flag_bldr.Get_as_bool(Flag__img__imap_exists);
		boolean img_wo_anch						= flag_bldr.Get_as_bool(Flag__img__wo_anch);
		boolean file__is_orig						= flag_bldr.Get_as_bool(Flag__file__is_orig);
		boolean file__repo_is_local				= flag_bldr.Get_as_bool(Flag__file__repo_is_local);
		boolean file__src_exists					= flag_bldr.Get_as_bool(Flag__file__src_exists);
		boolean img__cls_other_exists				= flag_bldr.Get_as_bool(Flag__img__cls_other_exists);
		boolean anch__ns_is_custom					= flag_bldr.Get_as_bool(Flag__anch__ns_is_custom);
		boolean anch__ns_id_needs_saving			= flag_bldr.Get_as_bool(Flag__anch__ns_id_needs_saving);
		int anch__cls_tid						= flag_bldr.Get_as_int(Flag__anch__cls_tid);
		boolean img__alt_diff_from_anch_title		= flag_bldr.Get_as_bool(Flag__img__alt_diff_from_anch_title);
		boolean anch_href_diff_file				= flag_bldr.Get_as_bool(Flag__anch__href_diff_file);
		boolean anch__title_missing				= flag_bldr.Get_as_bool(Flag__anch__title_missing);
		boolean img_is_vid							= flag_bldr.Get_as_bool(Flag__img__is_vid);
		int img_cls								= flag_bldr.Get_as_int(Flag__img__cls_tid);
		int anch__href_tid						= flag_bldr.Get_as_int(Flag__anch__href_tid);
		byte[] page_db = rdr.Read_bry_to();
		byte[] site_bry = null;
            switch (anch__href_tid) {
			case Xoh_anch_href_data.Tid__anch:
			case Xoh_anch_href_data.Tid__inet:
			case Xoh_anch_href_data.Tid__wiki:
				break;
			case Xoh_anch_href_data.Tid__site:
				int pipe_pos = Bry_find_.Find_fwd(page_db, Byte_ascii.Pipe);
				site_bry = Bry_.Mid(page_db, 0, pipe_pos);
				page_db = Bry_.Mid(page_db, pipe_pos + 1);	// encode needed for foreign characters; PAGE:en.w:Pho; DATE:2016-01-04
				break;
		}
		byte[] file_db = page_db;
		if (anch_href_diff_file) file_db = rdr.Read_bry_to();
		int anch_href_ns = -1;
		if (anch__ns_id_needs_saving)
			anch_href_ns = Xoh_lnki_dict_.Ns_decode(rdr);
		byte[] ns_custom_bry = null;
		if (anch__ns_is_custom) ns_custom_bry = rdr.Read_bry_to();
		int img_w = -1, img_h = -1, file_time = -1, file_page = -1;
		if (file__src_exists) {
			img_w = rdr.Read_hzip_int(2) - Gfo_hzip_int_.Neg_1_adj;
			img_h = rdr.Read_hzip_int(2) - Gfo_hzip_int_.Neg_1_adj;
		}
		else
			xoimg.Decode(bfr, hctx, hpg, rdr, src, data.Img_xoimg());
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
		int img_cls_other_bgn = -1; int img_cls_other_end = -1;
		if (img__cls_other_exists) {
			img_cls_other_bgn = rdr.Pos();
			img_cls_other_end = rdr.Find_fwd_lr();
		}
		int img_imap_idx = img_imap_exists ? rdr.Read_hzip_int(1) : -1;
		// transform values
		boolean anch_rel_is_nofollow = false;
		if (anch__href_tid == Xoh_anch_href_data.Tid__inet) {// external links should get rel=nofollow
			Gfo_url_encoder_.Href.Encode(tmp_bfr, page_db);
			if (Bry_.Len_gt_0(page_db))	// NOTE: page_db == "" for Media links; EX:[[File:A.png|link=file:///C:/A.ogg]] -> <a href='' class='image'>
				anch_rel_is_nofollow = true;
		}
		else {
			if (anch__href_tid == Xoh_anch_href_data.Tid__site)
				tmp_bfr.Add(Xoh_href_.Bry__site).Add(site_bry);
			if (anch__ns_id_needs_saving) {
				byte[] page_ttl_bry = page_db;
				if (	anch__href_tid == Xoh_anch_href_data.Tid__wiki	// if wiki, parse ttl
					||	anch_href_ns != Xow_ns_.Tid__main) {			// or if site and !main_ns; handles /site/en.wiktionary.org/wiki/Special:Search but not /site/creativecommons.org/wiki/by/2.5; DATE:2015-12-28
					if (page_db.length == 0) {	// handle invalid titles in link arg; EX:[[File:A.png|link=wikt:]]; PAGE:en.w:List_of_Saint_Petersburg_Metro_stations; DATE:2016-01-04
						Xow_ns anch_href_ns_itm = hctx.Wiki__ttl_parser().Ns_mgr().Ids_get_or_null(anch_href_ns);
						page_ttl_bry = anch_href_ns_itm.Name_db_w_colon();	// ASSUME:anch_href_ns is db_name not ui_name; EX: "Category_talk" vs "Category talk"
					}
					else {
						if (anch__ns_is_custom)
							page_ttl_bry = Bry_.Add(ns_custom_bry, Byte_ascii.Colon_bry, page_db);	// handle ns aliases; EX:WP; PAGE:en.w:Wikipedia:WikiProject_Molecular_and_Cell_Biology; DATE:2016-01-11
						else {
							if (anch__href_tid == Xoh_anch_href_data.Tid__site) {	// if site, do not title-case page; EX:[[File:A.png|link=wikt:Category:en:A]]; PAGE:en.w:Portal:Trucks/Wikimedia; DATE:2016-01-11
								Xow_ns anch_href_ns_itm = hctx.Wiki__ttl_parser().Ns_mgr().Ids_get_or_null(anch_href_ns);
								page_ttl_bry = Bry_.Add(anch_href_ns_itm.Name_db_w_colon(), page_db);
							}
							else {
								Xoa_ttl anch_href_ttl = hctx.Wiki__ttl_parser().Ttl_parse(anch_href_ns, page_db);
								page_ttl_bry = anch_href_ttl.Full_db_w_anch();
							}
						}
					}
				}
				tmp_bfr.Add(Xoh_href_.Bry__wiki).Add(page_ttl_bry);
			}
			else {
				byte[] ns_bry = anch__ns_is_custom ? ns_custom_bry : hctx.Wiki__ttl_parser().Ns_mgr().Ns_file().Name_db();
				tmp_bfr.Add(Xoh_href_.Bry__wiki).Add(ns_bry).Add_byte_colon();
				tmp_bfr.Add(page_db);
				// Gfo_url_encoder_.Href.Encode(tmp_bfr, page_db);	// encode needed for ?; PAGE:en.w:Voiceless_alveolar_affricate; DATE:2016-01-04
			}
		}
		this.anch_href_bry = tmp_bfr.To_bry_and_clear();
		// NOTE: src must go underneath ttl
		Xof_url_bldr url_bldr = hctx.File__url_bldr();
		url_bldr.Init_by_root(file__repo_is_local ? hctx.Fsys__file__wiki() : hctx.Fsys__file__comm(), Byte_ascii.Slash, false, false, Md5_depth);
		url_bldr.Init_by_itm(file__is_orig ? Xof_repo_itm_.Mode_orig : Xof_repo_itm_.Mode_thumb, file_db, Xof_file_wkr_.Md5(file_db), Xof_ext_.new_by_ttl_(file_db), img_w, file_time, file_page);
            
		data.Init_by_decode(anch_rel_is_nofollow, anch_title_bgn, anch_title_end, img_wo_anch, img_is_vid, img_w, img_h, img_alt_bgn, img_alt_end, img_imap_idx);
		data.Anch_href().Init_by_decode(anch_href_bry);
		data.Anch_cls().Init_by_decode(anch__cls_tid);
		data.Img_cls().Init_by_decode(src, img_cls, img_cls_other_bgn, img_cls_other_end);
		data.Img_src().Init_by_decode(file_db, url_bldr.Xto_bry());
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_hzip rv = new Xoh_img_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
	public static int Md5_depth = 2;
	private final Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_(1, 1,  1, 1, 1, 1		, 1	, 1, 1, 1	, 1 , 2, 1, 1	, 1, 1, 2, 2);	
	private static final int // SERIALIZED
	  Flag__img__imap_exists				=  0
	, Flag__img__wo_anch					=  1
	, Flag__img__is_vid						=  2
	, Flag__file__w_diff_from_html			=  3
	, Flag__file__time_exists				=  4
	, Flag__file__page_exists				=  5
	, Flag__file__is_orig					=  6
	, Flag__file__repo_is_local				=  7
	, Flag__file__src_exists				=  8
	, Flag__img__cls_other_exists			=  9
	, Flag__anch__ns_is_custom				= 10
	, Flag__anch__cls_tid					= 11	// none, image
	, Flag__anch__ns_id_needs_saving		= 12
	, Flag__img__alt_diff_from_anch_title	= 13
	, Flag__anch__href_diff_file			= 14
	, Flag__anch__title_missing				= 15
	, Flag__img__cls_tid					= 16	// none, thumbimage, thumbborder
	, Flag__anch__href_tid					= 17	// wiki, site, anch, inet
	;
}
