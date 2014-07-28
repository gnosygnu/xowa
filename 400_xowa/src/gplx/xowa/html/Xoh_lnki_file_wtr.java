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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
import gplx.xowa.files.*;
import gplx.xowa.parsers.lnkis.*;
public class Xoh_lnki_file_wtr {
	private Xoh_lnki_file_wkr_basic lnki_file_wkr_basic = new Xoh_lnki_file_wkr_basic();
	public Xoh_lnki_file_wtr(Xow_wiki wiki, Xow_html_mgr html_mgr, Xoh_html_wtr html_wtr) {
		this.html_mgr = html_mgr;
		this.wiki = wiki; this.html_wtr = html_wtr; bfr_mkr = wiki.Utl_bry_bfr_mkr();
		lnki_file_wkr_basic.Init(html_mgr.Lnki_full_image());
	}	private Xow_html_mgr html_mgr; private boolean lnki_title_enabled;
	private Xow_wiki wiki; private Xoh_html_wtr html_wtr;
	private Xoh_lnki_txt_fmtr media_alt_fmtr = new Xoh_lnki_txt_fmtr(), caption_fmtr = new Xoh_lnki_txt_fmtr(); private Bry_bfr_mkr bfr_mkr;
	private Xoa_url tmp_url = Xoa_url.blank_();
	public void Write_or_queue(Bry_bfr bfr, Xoa_page page, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki) {
		Xof_xfer_itm xfer_itm = this.Lnki_eval(ctx, page, lnki, queue_add_ref);
		this.Write_media(bfr, hctx, src, lnki, xfer_itm, Alt_text(src, lnki));
	}
	public void Write_or_queue(Bry_bfr bfr, Xoa_page page, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] alt_text) {
		Xof_xfer_itm xfer_itm = this.Lnki_eval(ctx, page, lnki, queue_add_ref);
		this.Write_media(bfr, hctx, src, lnki, xfer_itm, alt_text);
	}	private Bool_obj_ref queue_add_ref = Bool_obj_ref.n_();
	public void Page_bgn(Xoa_page page) {
		cfg_alt_defaults_to_caption = page.Wiki().App().User().Wiki().Html_mgr().Imgs_mgr().Alt_defaults_to_caption().Val();
	}	private boolean cfg_alt_defaults_to_caption = true;
	private Xop_ctx ctx;
	public Xof_xfer_itm Lnki_eval(Xop_ctx ctx, Xoa_page page, Xop_lnki_tkn lnki, Bool_obj_ref queue_add_ref) {return Lnki_eval(ctx, page.File_queue(), lnki.Ttl().Page_url(), lnki.Lnki_type(), lnki.Lnki_w(), lnki.Lnki_h(), lnki.Upright(), lnki.Thumbtime(), lnki.Page(), lnki.Ns_id() == Xow_ns_.Id_media, queue_add_ref);}
	public Xof_xfer_itm Lnki_eval(Xop_ctx ctx, Xof_xfer_queue queue, byte[] lnki_ttl, byte lnki_type, int lnki_w, int lnki_h, double lnki_upright, double lnki_thumbtime, int lnki_page, boolean lnki_is_media_ns, Bool_obj_ref queue_add_ref) {
		this.ctx = ctx;
		queue_add_ref.Val_n_();
		tmp_xfer_itm.Clear().Init_by_lnki(lnki_ttl, Bry_.Empty, lnki_type, lnki_w, lnki_h, lnki_upright, lnki_thumbtime, lnki_page);
		boolean found = Find_file(ctx, tmp_xfer_itm);
		boolean file_queue_add = File_queue_add(wiki, tmp_xfer_itm, lnki_is_media_ns, found);
		Xof_xfer_itm rv = tmp_xfer_itm;
		if (file_queue_add) {
			queue_add_ref.Val_y_();
			return Queue_add_manual(queue, tmp_xfer_itm);
		}
		rv.File_found_(found);
		return rv;
	}	private Xof_xfer_itm tmp_xfer_itm = new Xof_xfer_itm();
	private boolean Find_file(Xop_ctx ctx, Xof_xfer_itm xfer_itm) {
		if (wiki.File_mgr().Version() == Xow_file_mgr.Version_2)
			return ctx.Cur_page().Lnki_file_mgr().Find(wiki, ctx.Cur_page(), Xof_exec_tid.Tid_wiki_page, xfer_itm);
		else
			return wiki.File_mgr().Find_meta(xfer_itm);
	}
	public static Xof_xfer_itm Queue_add_manual(Xof_xfer_queue queue, Xof_xfer_itm xfer_itm) {
		int elem_id = queue.Elem_id().Val_add();
		Xof_xfer_itm rv = xfer_itm.Clone().Html_elem_atrs_(elem_id, Xof_html_elem.Tid_img);
		queue.Add(rv);
		return rv;
	}
	private static boolean File_queue_add(Xow_wiki wiki, Xof_xfer_itm xfer_itm, boolean lnki_is_media_ns, boolean found) {
		if (!wiki.File_mgr().Cfg_download().Enabled()) return false;
//			if (xfer_itm.Meta_itm() == null) return false;		// occurs when repos are missing; // DELETE: caused Redownload_missing to fail; no reason why missing shouldn't return a default repo; DATE:2013-01-26
		if (lnki_is_media_ns) return false;
		switch (wiki.File_mgr().Cfg_download().Redownload()) {
			case Xof_cfg_download.Redownload_none:
				if (found) return false;
				if (!found 
					&& xfer_itm.Meta_itm() != null		// null check; fsdb_call does not set meta
					&& xfer_itm.Meta_itm().Orig_exists() == Xof_meta_itm.Exists_n) 
					return false;	// not found, and orig_exists is n; do not download again (NOTE: even if current lnki is thumb, don't bother looking for thumb if orig is missing)
				break;
			case Xof_cfg_download.Redownload_missing:
				if (found) return false;
				break;
			case Xof_cfg_download.Redownload_all:
				break;
		}
		return true;
	}
	private Xop_link_parser tmp_link_parser = new Xop_link_parser();
	private Xohp_title_wkr anchor_title_wkr = new Xohp_title_wkr();
	public void Write_media(Bry_bfr bfr, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, Xof_xfer_itm xfer_itm, byte[] lnki_alt_text) {
		try {
		lnki_title_enabled = html_wtr.Cfg().Lnki_title();
		int elem_id = xfer_itm.Html_uid();
		int div_width = xfer_itm.Html_w();
		if (div_width < 1 && wiki.File_mgr().Version_2_y()) // NOTE: html_w is -1 for v2 and missing files; use lnki_w if available; primarily affects audio files with specified width; [[File:A.oga|30px]]; DATE:2014-05-03
			div_width = xfer_itm.Lnki_w();	
		if (div_width < 1) div_width = wiki.Html_mgr().Img_thumb_width();
		int lnki_halign = lnki.Align_h();
		if (lnki_halign == Xop_lnki_align_h.Null) lnki_halign = wiki.Lang().Img_thumb_halign_default();	// if halign is not supplied, then default to align for language
		byte[] lnki_halign_bry = Xop_lnki_align_h.Html_names[lnki_halign];
		byte[] lnki_href = wiki.App().Href_parser().Build_to_bry(wiki, lnki.Ttl());
		byte[] html_orig_src = xfer_itm.Html_orig_src();
		byte[] html_view_src = xfer_itm.Html_view_src();
		byte[] content = Bry_.Empty;
		byte[] lnki_ttl = lnki.Ttl().Page_txt();				
		Xof_ext lnki_ext = xfer_itm.Lnki_ext();
		if (	html_mgr.Img_suppress_missing_src()			// option to suppress src when file is missing
			&&	!xfer_itm.Html_pass()					// file is missing; wipe values and wait for "correct" info before regenerating; mostly to handle unknown redirects
			&&	!lnki_ext.Id_is_media()					// file is media; never suppress; src needs to be available for "click" on play; note that most media will be missing (not downloaded)
			&&	lnki.Ns_id() != Xow_ns_.Id_media		// ns is media; never suppress; "src" will use only orig_src; DATE:2014-01-30
			) {						
			html_orig_src = html_view_src = Bry_.Empty;		// null out src
		}

		if (lnki.Ns_id() == Xow_ns_.Id_media) {	// REF.MW:Linker.php|makeMediaLinkObj; NOTE: regardless of ext (ogg vs jpeg) and literal status (Media vs :Media), [[Media]] links are always rendered the same way; see Beethoven; EX: [[:Media:De-Ludwig_van_Beethoven.ogg|listen]]); [[File:Beethoven 3.jpg|The [[Media:BeethovenWithLyreGuitar( W. J. Mahler - 1804).jpg|complete painting]]...]]
			html_mgr.Lnki_full_media().Bld_bfr_many(bfr, html_orig_src	// NOTE: should always use orig_src; was using view_src; DATE:2014-01-19
				, lnki.Ttl().Page_txt(), Caption(src, lnki, Xoh_html_wtr_ctx.Basic, html_orig_src));
			return;
		}
		if (lnki_ext.Id_is_media()) {
			if		(	Xof_ext_.Id_is_video_strict(lnki_ext.Id())		// id is .ogv or .webm
					||	(	lnki_ext.Id_is_ogg()						// id is ogg
						&&	wiki.File_mgr().Version_1_y()				// version is v1 (v2 always marks ogg as aud); DATE:2014-02-01
						&&	(	xfer_itm.Html_pass()					// NOTE: xfer_itm.Html_pass() checks for video .ogg files (ext = .ogg and thumb is available); EX: WWI;
							||	xfer_itm.Meta_itm().State_new()			// NOTE: State_new() will always assume that ogg is video; needed for 1st load and dynamic updates
							)
						)
					) {	
				xfer_itm.Html_elem_tid_(Xof_html_elem.Tid_vid);
				if (Xop_lnki_type.Id_defaults_to_thumb(lnki.Lnki_type())) {
					content = Video(src, hctx, lnki, xfer_itm, elem_id, true, lnki_href, html_view_src, html_orig_src, lnki_alt_text);
				}
				else {
					content = Video(src, hctx, lnki, xfer_itm, elem_id, false, lnki_href, html_view_src, html_orig_src, lnki_alt_text);
					html_mgr.Plain().Bld_bfr_many(bfr, content);
					return;
				}
			}
			else if	(lnki_ext.Id_is_audio()) {
				content = Audio(src, hctx, lnki, elem_id, lnki_href, html_orig_src, lnki_alt_text);
				if (lnki.Media_icon())
					html_mgr.Lnki_thumb_core().Bld_bfr_many(bfr, div_width, lnki_halign_bry, content, elem_id);
				else
					html_mgr.Plain().Bld_bfr_many(bfr, content);
				return;
			}
			html_mgr.Lnki_thumb_core().Bld_bfr_many(bfr, div_width, lnki_halign_bry, content, elem_id);
		}
		else {	// image
			if (lnki_halign == Xop_lnki_align_h.Center) bfr.Add(Bry_div_bgn_center);
			Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
			byte[] anchor_title = lnki_title_enabled 
				? Make_anchor_title(tmp_bfr, src, lnki, lnki_ttl, anchor_title_wkr)	// NOTE: Make_anchor_title should only be called if there is no caption, else refs may not show; DATE:2014-03-05
				: Bry_.Empty;	
			Xoh_lnki_file_wkr lnki_file_wkr = lnki.Lnki_file_wkr(); if (lnki_file_wkr == null) lnki_file_wkr = lnki_file_wkr_basic;
			if (Xop_lnki_type.Id_is_thumbable(lnki.Lnki_type())) {	// is "thumb"
				if (bfr.Len() > 0) bfr.Add_byte_nl();
				content = Image_thumb(lnki_file_wkr, src, hctx, lnki, xfer_itm, elem_id, lnki_href, html_view_src, html_orig_src, lnki_alt_text, lnki_ttl, anchor_title);
				html_mgr.Lnki_thumb_core().Bld_bfr_many(bfr, div_width, lnki_halign_bry, content, elem_id);
			}
			else {
				if (	cfg_alt_defaults_to_caption 
					&& 	Bry_.Len_eq_0(lnki_alt_text)	// NOTE: if no alt, always use caption; DATE:2013-07-22
					&& 	!lnki.Alt_exists()) {				// unless blank alt exists; EX: [[File:A.png|a|alt=]] should have alt of "", not "a" 
					Caption(src, lnki, Xoh_html_wtr_ctx.Alt, html_orig_src).XferAry(tmp_bfr, 0);
					lnki_alt_text = tmp_bfr.XtoAryAndClear();
				}
				switch (lnki.Align_h()) {
					case Xop_lnki_align_h.Left:		bfr.Add(Bry_div_float_left).Add_byte_nl();	break;
					case Xop_lnki_align_h.Right:	bfr.Add(Bry_div_float_right).Add_byte_nl();	break;
					case Xop_lnki_align_h.None:		bfr.Add(Bry_div_float_none).Add_byte_nl();	break;
				}
				Arg_nde_tkn lnki_link_tkn = lnki.Link_tkn();
				if (lnki_link_tkn == Arg_nde_tkn.Null)						
					lnki_file_wkr.Write_img_full(bfr, xfer_itm, elem_id, lnki_href, html_view_src, xfer_itm.Html_w(), xfer_itm.Html_h(), lnki_alt_text, lnki_ttl, Xow_html_mgr.Bry_anchor_class_image, Xow_html_mgr.Bry_anchor_rel_blank, anchor_title, Img_cls(lnki));
				else {
					Arg_itm_tkn link_tkn = lnki_link_tkn.Val_tkn();
					byte[] link_ref = link_tkn.Dat_to_bry(src);
					byte[] link_ref_new = tmp_link_parser.Parse(tmp_bfr, tmp_url, wiki, link_ref, lnki_href);
					link_ref = link_ref_new == null ? lnki_href: link_ref_new;	// if parse fails, then assign to lnki_href; EX:link={{{1}}}
					link_ref = ctx.App().Encoder_mgr().Href_quotes().Encode(link_ref);	// must encode quotes; PAGE:en.w:List_of_cultural_heritage_sites_in_Punjab,_Pakistan; DATE:2014-07-16
					lnki_ttl = Bry_.Coalesce(lnki_ttl, tmp_link_parser.Html_xowa_ttl());
					lnki_file_wkr.Write_img_full(bfr, xfer_itm, elem_id, link_ref, html_view_src, xfer_itm.Html_w(), xfer_itm.Html_h(), lnki_alt_text, lnki_ttl, tmp_link_parser.Html_anchor_cls(), tmp_link_parser.Html_anchor_rel(), anchor_title, Img_cls(lnki));
				}
				switch (lnki.Align_h()) {
					case Xop_lnki_align_h.Left:
					case Xop_lnki_align_h.Right:
					case Xop_lnki_align_h.None:	bfr.Add(Bry_div_end); break;
				}
			}
			if (lnki_halign == Xop_lnki_align_h.Center) bfr.Add(Bry_div_end);
			tmp_bfr.Mkr_rls();
		}
		} catch (Exception e) {
			wiki.App().Usr_dlg().Warn_many("", "", "lnki_wtr:fatal error while writing lnki: ttl=~{0} err=~{1}", String_.new_utf8_(lnki.Ttl().Raw()), Err_.Message_gplx_brief(e));
		}
	}
	private static byte[] Make_anchor_title(Bry_bfr bfr, byte[] src, Xop_lnki_tkn lnki, byte[] lnki_ttl, Xohp_title_wkr anchor_title_wkr) {
		switch (lnki.Lnki_type()) {
			case Xop_lnki_type.Id_thumb:		// If the image is a thumb, do not add a title / alt, even if a caption is available
			case Xop_lnki_type.Id_frame:
				return Bry_.Empty;
			case Xop_lnki_type.Id_frameless:	// If the image is frameless, add the caption as a title / alt. If no caption is available, do not add a title / alt
				break;
		}
		Xop_tkn_itm anchor_title_tkn = lnki.Caption_tkn();
		if (anchor_title_tkn == Xop_tkn_null.Null_tkn) return Bry_.Empty; // no caption; return empty; (do not use lnki); DATE:2013-12-31
		bfr.Add(Anchor_title);
		anchor_title_wkr.Set(src, anchor_title_tkn).XferAry(bfr, 0);
		bfr.Add_byte(Byte_ascii.Quote);
		return bfr.XtoAryAndClear();
	}
	private byte[] Video(byte[] src, Xoh_html_wtr_ctx hctx, Xop_lnki_tkn lnki, Xof_xfer_itm xfer_itm, int elem_id, boolean lnki_thumb, byte[] lnki_href, byte[] html_view_src, byte[] html_orig_src, byte[] lnki_alt_text) {
		int thumb_w = xfer_itm.Html_w();
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		int play_btn_width = thumb_w; if (play_btn_width < 1) play_btn_width = wiki.Html_mgr().Img_thumb_width();
		if (lnki_thumb)
			html_mgr.Lnki_thumb_file_video().Bld_bfr_many(tmp_bfr, Play_btn(elem_id, play_btn_width, play_btn_width, html_orig_src, lnki.Ttl().Page_txt()), Img_thumb(lnki, xfer_itm, elem_id, lnki_href, html_view_src, lnki_alt_text), Caption_div(src, lnki, html_orig_src, lnki_href), Alt_html(src, lnki));
		else
			html_mgr.Lnki_thumb_file_video().Bld_bfr_many(tmp_bfr, Play_btn(elem_id, play_btn_width, play_btn_width, html_orig_src, lnki.Ttl().Page_txt()), Img_thumb(lnki, xfer_itm, elem_id, lnki_href, html_view_src, lnki_alt_text), Bry_.Empty, Bry_.Empty);
		return tmp_bfr.Mkr_rls().XtoAryAndClear();
	}
	private byte[] Image_thumb(Xoh_lnki_file_wkr lnki_file_wkr, byte[] src, Xoh_html_wtr_ctx hctx, Xop_lnki_tkn lnki, Xof_xfer_itm xfer_itm, int elem_id, byte[] lnki_href, byte[] html_view_src, byte[] html_orig_src, byte[] lnki_alt_text, byte[] lnki_ttl, byte[] anchor_title) {
		byte[] lnki_alt_html = Alt_html(src, lnki);
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		byte[] lnki_class = xfer_itm.Html_pass()
			? Xow_html_mgr.Bry_img_class_thumbimage
			: Xow_html_mgr.Bry_img_class_none;
		lnki_file_wkr.Write_img_full(tmp_bfr, xfer_itm, elem_id, lnki_href, html_view_src, xfer_itm.Html_w(), xfer_itm.Html_h(), lnki_alt_text, lnki_ttl, Xow_html_mgr.Bry_anchor_class_image, Xow_html_mgr.Bry_anchor_rel_blank, anchor_title, lnki_class);
		byte[] thumb = tmp_bfr.XtoAryAndClear();
		if (!wiki.Html_mgr().Imgs_mgr().Alt_in_caption().Val()) lnki_alt_html = Bry_.Empty;
		html_mgr.Lnki_thumb_file_image().Bld_bfr_many(tmp_bfr, thumb, Caption_div(src, lnki, html_orig_src, lnki_href), lnki_alt_html);
		return tmp_bfr.Mkr_rls().XtoAryAndClear();
	}	private static final byte[] Anchor_title = Bry_.new_utf8_(" title=\"");
	private byte[] Audio(byte[] src, Xoh_html_wtr_ctx hctx, Xop_lnki_tkn lnki, int elem_id, byte[] lnki_href, byte[] html_orig_src, byte[] lnki_alt_text) {
		byte[] info_btn = Bry_.Empty;
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		if (lnki.Media_icon()) {
			html_mgr.Lnki_thumb_part_info_btn().Bld_bfr_many(tmp_bfr, wiki.Html_mgr().Img_media_info_btn(), lnki_href);
			info_btn = tmp_bfr.XtoAryAndClear();
		}
		int play_btn_width = lnki.Lnki_w(); if (play_btn_width < 1) play_btn_width = wiki.Html_mgr().Img_thumb_width();	// if no width set width to default img width
		html_mgr.Lnki_thumb_file_audio().Bld_bfr_many(tmp_bfr, Play_btn(elem_id, play_btn_width, Play_btn_max_width, html_orig_src, lnki.Ttl().Page_txt()), info_btn, Caption_div(src, lnki, html_orig_src, lnki_href), Alt_html(src, lnki));
		return tmp_bfr.Mkr_rls().XtoAryAndClear();
	}
	private byte[] Img_thumb(Xop_lnki_tkn lnki, Xof_xfer_itm xfer_itm, int elem_id, byte[] lnki_href, byte[] html_view_src, byte[] alt) {			
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		html_mgr.Lnki_thumb_part_image().Bld_bfr_many(tmp_bfr, elem_id, Bry_class_internal, lnki_href, lnki.Ttl().Page_txt(), html_view_src, xfer_itm.Html_w(), xfer_itm.Html_h(), alt);
		return tmp_bfr.Mkr_rls().XtoAryAndClear();
	}
	private byte[] Img_cls(Xop_lnki_tkn lnki) {
		if (lnki.Border() == Bool_.Y_byte) {
			return Img_cls_thumbborder;
		}
		else
			return Bry_.Empty;
	}	private static final byte[] Img_cls_thumbborder = Bry_.new_ascii_(" class=\"thumbborder\"");
	public static final byte[] Bry_class_internal = Bry_.new_ascii_("image");
	private byte[] Alt_text(byte[] src, Xop_lnki_tkn lnki) {
		if (!lnki.Alt_exists()) return Bry_.Empty;
		media_alt_fmtr.Set(html_wtr, ctx, Xoh_html_wtr_ctx.Alt, src, lnki.Alt_tkn().Val_tkn(), html_mgr.Plain());
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		media_alt_fmtr.XferAry(tmp_bfr, 0);
		return tmp_bfr.Mkr_rls().XtoAryAndClear(); 
	}
	private byte[] Alt_html(byte[] src, Xop_lnki_tkn lnki) {
		if (!lnki.Alt_exists()) return Bry_.Empty;
		media_alt_fmtr.Set(html_wtr, ctx, Xoh_html_wtr_ctx.Basic, src, lnki.Alt_tkn().Val_tkn(), html_mgr.Lnki_thumb_part_alt());
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		media_alt_fmtr.XferAry(tmp_bfr, 0);
		return tmp_bfr.Mkr_rls().XtoAryAndClear(); 
	}
	private byte[] Caption_div(byte[] src, Xop_lnki_tkn lnki, byte[] html_orig_src, byte[] lnki_href) {
		Bry_fmtr_arg caption = Caption(src, lnki, Xoh_html_wtr_ctx.Basic, html_orig_src);
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		byte[] magnify_btn = Bry_.Empty;
		if (lnki.Media_icon()) {
			html_mgr.Lnki_thumb_part_magnfiy_btn().Bld_bfr_many(tmp_bfr, wiki.Html_mgr().Img_thumb_magnify(), lnki_href, wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_file_enlarge));
			magnify_btn = tmp_bfr.XtoAryAndClear();
		}
		html_mgr.Lnki_thumb_part_caption().Bld_bfr_many(tmp_bfr, magnify_btn, caption);
		return tmp_bfr.Mkr_rls().XtoAryAndClear();				
	}
	private Bry_fmtr_arg Caption(byte[] src, Xop_lnki_tkn lnki, Xoh_html_wtr_ctx hctx, byte[] html_orig_src) {
		return lnki.Caption_exists()
			? caption_fmtr.Set(html_wtr, ctx, hctx, src, lnki.Caption_val_tkn(), html_mgr.Plain())
			: Bry_fmtr_arg_.Null;
	}
	private byte[] Play_btn(int elem_id, int width, int max_width, byte[] html_orig_src, byte[] lnki_href) {
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		html_mgr.Lnki_thumb_part_play_btn().Bld_bfr_many(tmp_bfr, elem_id, wiki.Html_mgr().Img_media_play_btn(), width - 2, max_width, html_orig_src, lnki_href);	// NOTE: -2 is fudge factor else play btn will jut out over video thumb; see Earth and ISS video
		return tmp_bfr.Mkr_rls().XtoAryAndClear();				
	}
	private static final int Play_btn_max_width = 1024;
	private static final byte[] Bry_div_bgn_center = Bry_.new_ascii_("<div class=\"center\">"), Bry_div_end = Bry_.new_ascii_("</div>")
		, Bry_div_float_none = Bry_.new_ascii_("<div class=\"floatnone\">"), Bry_div_float_left = Bry_.new_ascii_("<div class=\"floatleft\">"), Bry_div_float_right = Bry_.new_ascii_("<div class=\"floatright\">");
	public static byte[] Lnki_cls_visited(gplx.xowa.users.history.Xou_history_mgr history_mgr, byte[] wiki_key, byte[] page_ttl) {
		return history_mgr.Has(wiki_key, page_ttl) ? Lnki_cls_visited_bry : Bry_.Empty;
	}	private static final byte[] Lnki_cls_visited_bry = Bry_.new_ascii_(" class=\"xowa-visited\"");
}
