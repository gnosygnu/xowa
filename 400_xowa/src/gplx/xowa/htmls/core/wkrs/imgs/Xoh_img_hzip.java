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
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.threads.poolables.*; import gplx.core.encoders.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.xtns.pagebanners.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*;
public class Xoh_img_hzip implements Xoh_hzip_wkr, Gfo_poolable_itm {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
	private final    Xoh_img_xoimg_hzip xoimg = new Xoh_img_xoimg_hzip();
	public int Tid() {return Xoh_hzip_dict_.Tid__img;}
	public String Key() {return Xoh_hzip_dict_.Key__img;}
	public byte[] Hook() {return hook;} private byte[] hook;
	public Xoh_img_wtr Wtr() {return wtr;} private Xoh_img_wtr wtr = new Xoh_img_wtr();
	public byte[] Anch_href_bry() {return anch_href_bry;} private byte[] anch_href_bry;
	public Gfo_poolable_itm Encode1(Xoh_hzip_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, boolean wkr_is_root, byte[] src, Object data_obj) {
		Xoh_img_data data = (Xoh_img_data)data_obj;
		Xoh_anch_href_data anch_href = data.Anch_href();
		boolean					img_wo_anch = data.Img_wo_anch();
		Bry_obj_ref				anch_page = data.Anch_xo_ttl();
		byte					anch_href_tid = anch_href.Tid();
		boolean					anch__ns_is_custom = anch_href.Ttl_ns_custom() != null;
		boolean					anch__ns_needs_saving = Xoh_anch_href_data.Ns_exists(anch_href.Tid()) && anch_href.Ttl_ns_id() != Xow_ns_.Tid__file;
		boolean					anch_title_exists = data.Anch_title_exists();
		Xoh_img_xoimg_data		img_xoimg = data.Img_xoimg();
		Xoh_img_cls_data		img_cls = data.Img_cls();
		Xoh_img_src_data		img_src = data.Img_src();
		boolean					img__alt_diff_from_anch_title = data.Img_alt__diff__anch_title();
		boolean					file__src_exists = !img_xoimg.Val_dat_exists();
		boolean					anch_href_diff_file = !img_wo_anch && !Bry_.Match(anch_page.Val(), anch_page.Val_bgn(), anch_page.Val_end(), anch_href.Ttl_page_db());
		boolean					img__is_imap		= flag_bldr.Set_as_bool(Flag__img__is_imap			, data.Img_imap_idx() != -1);
		boolean					img__is_pgbnr		= flag_bldr.Set_as_bool(Flag__img__is_pgbnr			, data.Img_pgbnr().Exists());
		boolean					img__src_is_diff	= flag_bldr.Set_as_bool(Flag__file__src_diff_href	, file__src_exists && img_src.Src_end() != -1 && !Bry_.Eq(anch_href.Ttl_page_db(), img_src.File_ttl_bry()));	// && img_src.Src_end() != -1; handle missing src in corrupt html; EX: <img w=1 h=2>
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
		flag_bldr.Set(Flag__anch__ns_needs_saving			, anch__ns_needs_saving);
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
			data.Anch_xo_ttl().Bfr_arg__add(bfr);
			bfr.Add_byte(Xoh_hzip_dict_.Escape);
		}
		switch (anch_href_tid) {
			case Xoh_anch_href_data.Tid__anch:
			case Xoh_anch_href_data.Tid__inet:
				break;
			case Xoh_anch_href_data.Tid__wiki:
			case Xoh_anch_href_data.Tid__site:
				if (anch__ns_needs_saving)
					Xoh_lnki_dict_.Ns_encode(bfr, anch_href.Ttl_ns_id());
				break;
		}
		if (anch__ns_is_custom) bfr.Add(data.Anch_href().Ttl_ns_custom()).Add_byte(Xoh_hzip_dict_.Escape);
		if (file__src_exists) {
			Gfo_hzip_int_.Encode(2, bfr, Gfo_hzip_int_.Neg_1_adj + data.Img_w());
			Gfo_hzip_int_.Encode(2, bfr, Gfo_hzip_int_.Neg_1_adj + data.Img_h());
			if (data.Img_w__diff__file_w())		Gfo_hzip_int_.Encode(2, bfr, Gfo_hzip_int_.Neg_1_adj + img_src.File_w());
			if (img_src.File_time_exists())		bfr.Add_hzip_double(img_src.File_time());
			if (img_src.File_page_exists())		Gfo_hzip_int_.Encode(1, bfr, Gfo_hzip_int_.Neg_1_adj + img_src.File_page());
		}
		else
			xoimg.Encode(bfr, hctx.Hzip__stat(), src, img_xoimg);
		if (anch_title_exists)					bfr.Add_hzip_mid(src, data.Anch_title_bgn(), data.Anch_title_end());
		if (img__alt_diff_from_anch_title)		bfr.Add_hzip_mid(src, data.Img_alt_bgn(), data.Img_alt_end());
		if (img_cls.Other_exists())				bfr.Add_hzip_mid(src, img_cls.Other_bgn(), img_cls.Other_end());
		if (img__is_imap)						bfr.Add_hzip_int(1, data.Img_imap_idx());
		if (img__is_pgbnr) {
			Pgbnr_itm pgbnr = data.Img_pgbnr();
			bfr.Add_hzip_double(pgbnr.Data_pos_x());
			bfr.Add_hzip_double(pgbnr.Data_pos_y());
			bfr.Add_hzip_bry(pgbnr.Srcset());
			bfr.Add_hzip_bry(pgbnr.Style_if_not_dflt());
		}
		if (img__src_is_diff) {
			bfr.Add_hzip_bry(img_src.File_ttl_bry());
		}
		return this;
	}
	public void Decode1(Bry_bfr bfr, Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, int src_bgn, int src_end, Xoh_data_itm data_itm) {
		// read flags; order doesn't matter, but reading <a> -> <img> -> src=
		Xoh_img_data data = (Xoh_img_data)data_itm; data.Clear();
		int flag = rdr.Read_hzip_int(2); flag_bldr.Decode(flag);
		int		anch__href_tid					= flag_bldr.Get_as_int(Flag__anch__href_tid);
		boolean	anch__title_missing				= flag_bldr.Get_as_bool(Flag__anch__title_missing);
		boolean	anch__href_diff_file			= flag_bldr.Get_as_bool(Flag__anch__href_diff_file);
		boolean	anch__ns_needs_saving			= flag_bldr.Get_as_bool(Flag__anch__ns_needs_saving);
		boolean	anch__ns_is_custom				= flag_bldr.Get_as_bool(Flag__anch__ns_is_custom);
		int		anch__cls_tid					= flag_bldr.Get_as_int(Flag__anch__cls_tid);
		int		img__cls						= flag_bldr.Get_as_int(Flag__img__cls_tid);
		boolean	img__cls_other_exists			= flag_bldr.Get_as_bool(Flag__img__cls_other_exists);
		boolean	img__alt_diff_from_anch_title	= flag_bldr.Get_as_bool(Flag__img__alt_diff_from_anch_title);
		boolean	img__wo_anch					= flag_bldr.Get_as_bool(Flag__img__wo_anch);
		boolean	img__is_vid						= flag_bldr.Get_as_bool(Flag__img__is_vid);
		boolean img__is_imap					= flag_bldr.Get_as_bool(Flag__img__is_imap);
		boolean	img__is_pgbnr					= flag_bldr.Get_as_bool(Flag__img__is_pgbnr);
		boolean	img__src_is_diff				= flag_bldr.Get_as_bool(Flag__file__src_diff_href);
		boolean	file__is_orig					= flag_bldr.Get_as_bool(Flag__file__is_orig);
		boolean	file__repo_is_local				= flag_bldr.Get_as_bool(Flag__file__repo_is_local);
		boolean	file__src_exists				= flag_bldr.Get_as_bool(Flag__file__src_exists);
		boolean	file__time_exists				= flag_bldr.Get_as_bool(Flag__file__time_exists);
		boolean	file__page_exists				= flag_bldr.Get_as_bool(Flag__file__page_exists);
		boolean	file__w_diff_from_html			= flag_bldr.Get_as_bool(Flag__file__w_diff_from_html);

		// get href_page; note: encoded; EX: "A%C3%A9b.png" not "Aéb.png"
		byte[] href_page = rdr.Read_bry_to();

		// get site_bry; note: href_page will include site_bry and separate with pipe; EX:"en.wiktionary.org|A~"
		byte[] site_bry = null;
		if (anch__href_tid == Xoh_anch_href_data.Tid__site) {
			int pipe_pos = Bry_find_.Find_fwd(href_page, Byte_ascii.Pipe);
			site_bry = Bry_.Mid(href_page, 0, pipe_pos);
			href_page = Bry_.Mid(href_page, pipe_pos + 1);
		}

		// get xo_ttl; note: unencoded; EX: "Aéb.png"; defaults to href_page, else uses next bry
		byte[] xo_ttl = anch__href_diff_file ? rdr.Read_bry_to() : href_page;

		// get href_ns; usually -1 which means "File"; else, read next int
		int href_ns_id = anch__ns_needs_saving ? Xoh_lnki_dict_.Ns_decode(rdr) : -1;

		// get href_custom; usually null, but can be "Image";
		byte[] href_ns_custom = anch__ns_is_custom 
			? Xoa_ttl.Replace_spaces(rdr.Read_bry_to())	// NOTE: use unders not spaces; will be used directly below to generate href; else href="User talk:A"; PAGE:de.b:Wikibooks:Benutzersperrung/_InselFahrer DATE:2016-06-25
			: null;

		// get img.src attributes like width, height, page, time
		int file_w = -1, img_w = -1, img_h = -1, file_page = -1;
		double file_time = -1;
		if (file__src_exists) {
			img_w = rdr.Read_hzip_int(2) - Gfo_hzip_int_.Neg_1_adj;
			img_h = rdr.Read_hzip_int(2) - Gfo_hzip_int_.Neg_1_adj;
			file_w = file__w_diff_from_html ? rdr.Read_hzip_int(2) - Gfo_hzip_int_.Neg_1_adj : img_w; 
			if (file__time_exists)		file_time = rdr.Read_double_to();
			if (file__page_exists)		file_page = rdr.Read_hzip_int(1) - Gfo_hzip_int_.Neg_1_adj;
		}
		else
			xoimg.Decode(bfr, hctx, hpg, rdr, src, data.Img_xoimg());

		// get anch_title; usually missing, else read next bry
		int anch_title_bgn = -1, anch_title_end = -1;
		if (!anch__title_missing) {
			anch_title_bgn = rdr.Pos();
			anch_title_end = rdr.Find_fwd_lr();				
		}

		// get alt; usually anch_title, else read next bry
		int img_alt_bgn = -1, img_alt_end = -1;
		if (!anch__title_missing) {
			img_alt_bgn = anch_title_bgn;
			img_alt_end = anch_title_end;
		}
		if (img__alt_diff_from_anch_title) {
			img_alt_bgn = rdr.Pos();
			img_alt_end = rdr.Find_fwd_lr();
		}

		// get img_cls; usually missing, else read next bry
		int img_cls_other_bgn = -1; int img_cls_other_end = -1;
		if (img__cls_other_exists) {
			img_cls_other_bgn = rdr.Pos();
			img_cls_other_end = rdr.Find_fwd_lr();
		}

		// get imap_idx
		int img_imap_idx = img__is_imap ? rdr.Read_hzip_int(1) : -1;

		// get pgbnr
		if (img__is_pgbnr) {
			double data_pos_x = rdr.Read_double_to();
			double data_pos_y = rdr.Read_double_to();
			byte[] srcset = rdr.Read_bry_to();
			byte[] style = rdr.Read_bry_to();
			
			data.Img_pgbnr().Init_by_decode(data_pos_x, data_pos_y, srcset, style);
		}

		// get img.src.page; usually same as anch.href.page, else read next bry
		byte[] src_page = img__src_is_diff ? rdr.Read_bry_to() : href_page;

		// rdr done; build full anch_href			
		boolean anch_rel_is_nofollow = false; // anch_rel_is_nofollow usually false, but true when image points to another wiki
		if (anch__href_tid == Xoh_anch_href_data.Tid__inet) {// handle external links
			tmp_bfr.Add(href_page);			// href_page is actually entire url
			if (Bry_.Len_gt_0(href_page))	// NOTE: href_page == "" for Media links; EX:[[File:A.png|link=file:///C:/A.ogg]] -> <a href='' class='image'>
				anch_rel_is_nofollow = true;
		}
		else {
			// if site exists, add "/site/site_domain"
			if (anch__href_tid == Xoh_anch_href_data.Tid__site)
				tmp_bfr.Add(Xoh_href_.Bry__site).Add(site_bry);

			// bld ttl_full; extra logic to handle ns
			if (anch__ns_needs_saving) {// link exists
				byte[] ttl_full = href_page;
				if (	anch__href_tid == Xoh_anch_href_data.Tid__wiki	// if wiki, parse ttl
					||	href_ns_id != Xow_ns_.Tid__main) {				// or if site and !main_ns; handles /site/en.wiktionary.org/wiki/Special:Search but not /site/creativecommons.org/wiki/by/2.5; DATE:2015-12-28
					if (href_page.length == 0) {						// handle invalid titles in link arg; EX:[[File:A.png|link=wikt:]]; PAGE:en.w:List_of_Saint_Petersburg_Metro_stations; DATE:2016-01-04
						Xow_ns href_ns = hctx.Wiki__ttl_parser().Ns_mgr().Ids_get_or_null(href_ns_id);

						// 2 notes:
						// (1) use db_name not ui_name; EX: "Category_talk" vs "Category talk"
						// (2) use canonical name, not local name; EX: "Help:A" not "Aide:A"; DATE:2016-10-28
						ttl_full = Xow_ns_canonical_.To_canonical_or_local_as_bry_w_colon(href_ns);
					}
					else {
						if (anch__ns_is_custom)	// handle ns aliases; EX: "Image:Page"; EX:WP; PAGE:en.w:Wikipedia:WikiProject_Molecular_and_Cell_Biology; DATE:2016-01-11
							ttl_full = Bry_.Add(href_ns_custom, Byte_ascii.Colon_bry, href_page);
						else {
							if (anch__href_tid == Xoh_anch_href_data.Tid__site) {	// if site, do not title-case page; EX:[[File:A.png|link=wikt:Category:en:A]]; PAGE:en.w:Portal:Trucks/Wikimedia; DATE:2016-01-11
								Xow_ns href_ns = hctx.Wiki__ttl_parser().Ns_mgr().Ids_get_or_null(href_ns_id);
								ttl_full = Bry_.Add(href_ns.Name_db_w_colon(), href_page);	// NOTE: just add href_page; do not call ttl.Parse which will title-case;
							}
							else {
								Xoa_ttl anch_href_ttl = hctx.Wiki__ttl_parser().Ttl_parse(href_ns_id, href_page);
								ttl_full = anch_href_ttl.Full_db_w_anch();
							}
						}
					}
				}
				tmp_bfr.Add(Xoh_href_.Bry__wiki).Add(ttl_full);
			}
			else {// no link
				byte[] ns_bry = anch__ns_is_custom ? href_ns_custom : hctx.Wiki__ttl_parser().Ns_mgr().Ns_file().Name_db();
				tmp_bfr.Add(Xoh_href_.Bry__wiki).Add(ns_bry).Add_byte_colon().Add(href_page);
			}
		}
		this.anch_href_bry = tmp_bfr.To_bry_and_clear();

		// set url_bldr
		Xof_url_bldr url_bldr = hctx.File__url_bldr();
		byte repo_tid = file__repo_is_local ? Xof_repo_tid_.Tid__local : Xof_repo_tid_.Tid__remote;
		byte[] fsys_root = file__repo_is_local ? hctx.Fsys__file__wiki() : hctx.Fsys__file__comm();

		url_bldr.Init_by_repo(repo_tid, fsys_root, hctx.Fsys__is_wnt(), Byte_ascii.Slash, false, false, Md5_depth);
		// NOTE: set md5 / ext now b/c src_page will be changed for gui mode
		byte[] md5 = Xof_file_wkr_.Md5(src_page);
		Xof_ext ext = Xof_ext_.new_by_ttl_(src_page);
		if (!hpg.Wiki().File__fsdb_mode().Tid__bld())	// if gui, do not url-encode; else "View HTML" will not work, since fsys uses non-url-decoded form; note needs to be url-encoded else hdiff will return different values
			src_page = Xoa_ttl.Replace_spaces(Gfo_url_encoder_.Href.Decode(src_page));
		url_bldr.Init_for_trg_html(hctx.Fsys__repo(file__repo_is_local), file__is_orig ? Xof_img_mode_.Tid__orig : Xof_img_mode_.Tid__thumb, src_page, md5, ext, file_w, file_time, file_page);

		// set data vars for wtr
		data.Init_by_decode(anch_rel_is_nofollow, anch_title_bgn, anch_title_end, img__wo_anch, img__is_vid, img_w, img_h, img_alt_bgn, img_alt_end, img_imap_idx);
		data.Anch_xo_ttl().Val_(xo_ttl);	// set data-xowa-title
		data.Anch_href().Init_by_decode(anch_href_bry);
		data.Anch_cls().Init_by_decode(anch__cls_tid);
		data.Img_cls().Init_by_decode(src, img__cls, img_cls_other_bgn, img_cls_other_end);
		data.Img_src().Init_by_decode(url_bldr.Xto_bry(), file__is_orig, src_page, file_w, file_time, file_page);
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_hzip rv = new Xoh_img_hzip(); rv.pool_mgr = mgr; rv.pool_idx = idx; rv.hook = (byte[])args[0]; return rv;}
	public static int Md5_depth = 4;
	private final    Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_( 1, 1, 1, 1,  1, 1, 1, 1		, 1	, 1, 1, 1	, 1 , 2, 1, 1	, 1, 1, 2, 2);	
	private static final int // SERIALIZED
	  Flag__file__src_diff_href				=  0	// compares anch_href vs img_src; EX: <a href='...File:A.png'><img src='...A.png'>
	, Flag__img__is_pgbnr					=  1	// is pagebanner
	, Flag__img__is_imap					=  2	// is imap
	, Flag__img__wo_anch					=  3	// handles hiero, magnify, enlarge where only <img> is parsed; parsing <a> is too irregular
	, Flag__img__is_vid						=  4	// is video thumb
	, Flag__file__w_diff_from_html			=  5	// handles gallery; EX: <img src='A.png/220px.png' width=330px>
	, Flag__file__time_exists				=  6	// img_src has time; EX: 220px-2.png
	, Flag__file__page_exists				=  7	// img_src has page; EX: 220px-2.png
	, Flag__file__is_orig					=  8	// n=orig; y=thumb
	, Flag__file__repo_is_local				=  9	// n=commons.wikimedia.org; y=en.wikipedia.org
	, Flag__file__src_exists				= 10	// <img> has "src="
	, Flag__img__cls_other_exists			= 11	// handle manual class in <img>; EX: <img class='cls1'>
	, Flag__anch__ns_is_custom				= 12	// handle ns-alias's; EX: "Image:A.png"
	, Flag__anch__cls_tid					= 13	// ENUM: none, image
	, Flag__anch__ns_needs_saving			= 14	// handle links which redirect to different ns; EX:[[File:A.png|B]]
	, Flag__img__alt_diff_from_anch_title	= 15	// HTML entities will generate different title vs alt attributes; EX:[[File:A.png|a&amp;]]
	, Flag__anch__href_diff_file			= 16	// compares anch_href vs anch_xowa_title; EX:<a href='...File:A.png#b' xowa_title='A.png'>; differs when (a) anchor (A#B); (b) url-encoding (A'b; Aéb)
	, Flag__anch__title_missing				= 17	// title missing for frameless images
	, Flag__img__cls_tid					= 18	// ENUM: none, thumbimage, thumbborder
	, Flag__anch__href_tid					= 19	// ENUM: wiki, site, anch, inet
	;
}
