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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.*; import gplx.core.bits.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.files.*; 	
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.tmpls.*;
public class Xoh_file_wtr__basic implements Gfo_invk {
	private final    Xowe_wiki wiki; private final    Xow_html_mgr html_mgr; private final    Xoh_html_wtr html_wtr;
	private final    Xoh_file_fmtr__basic fmtr__basic = new Xoh_file_fmtr__basic(), fmtr__hdump = new Xoh_file_fmtr__hdump();
	private final    Xoh_lnki_text_fmtr alt_fmtr, caption_fmtr;
	private final    Xop_link_parser tmp_link_parser = new Xop_link_parser(); private Xoa_url tmp_url = Xoa_url.blank();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(32);
	private Xoae_page page; private byte[] msg_file_enlarge;
	private Xoh_file_fmtr__basic html_fmtr;		
	private boolean alt_in_caption = true, alt_defaults_to_caption = true;
	public Xoh_file_wtr__basic(Xowe_wiki wiki, Xow_html_mgr html_mgr, Xoh_html_wtr html_wtr) {
		this.wiki = wiki; this.html_mgr = html_mgr; this.html_wtr = html_wtr;
		this.alt_fmtr = new Xoh_lnki_text_fmtr(wiki.Utl__bfr_mkr(), html_wtr);
		this.caption_fmtr = new Xoh_lnki_text_fmtr(wiki.Utl__bfr_mkr(), html_wtr);
		this.html_fmtr = fmtr__basic;
	}
	public boolean Alt_show_in_html() {return alt_show_in_html;} private boolean alt_show_in_html;
	public void Init_by_wiki(Xowe_wiki wiki) {
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__alt_in_caption, Cfg__alt_defaults_to_caption, Cfg__alt_show_in_html);
	}
	public void Init_by_page(Xoh_wtr_ctx hctx, Xoae_page page) {
		this.page = page;
		this.html_fmtr = hctx.Mode_is_hdump() ? fmtr__hdump : fmtr__basic;
		if (msg_file_enlarge == null) this.msg_file_enlarge = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_file_enlarge);
	}
	public void Write_file(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, Xof_file_itm xfer_itm, byte[] img_alt) {
		// init
		int uid = xfer_itm.Html_uid();
		Xof_ext orig_ext = xfer_itm.Orig_ext();

		// lnki_ttl; note if orig exists and orig_ttl is different, use it; PAGE:en.w:Switzerland; EX:[[File:Wappen_Schwyz_matt.svg]] which has redirect of Wappen_des_Kantons_Schwyz.svg; DATE:2016-08-11
		Xoa_ttl lnki_ttl = lnki.Ttl();
		if (xfer_itm.Orig_exists() && !Bry_.Eq(xfer_itm.Orig_ttl(), xfer_itm.Lnki_ttl()))
			lnki_ttl = wiki.Ttl_parse(Xow_ns_.Tid__file, xfer_itm.Orig_ttl());
		byte[] lnki_ttl_bry = lnki_ttl.Page_txt();
		byte[] lnki_href = wiki.Html__href_wtr().Build_to_bry(wiki, lnki_ttl);
		
		// get div_width
		int div_width = xfer_itm.Html_w();
		if (div_width < 1 && wiki.File_mgr().Version_2_y()) // NOTE: html_w is -1 for v2 and missing files; use lnki_w if available; primarily affects audio files with specified width; [[File:A.oga|30px]]; DATE:2014-05-03
			div_width = xfer_itm.Lnki_w();	
		if (div_width < 1)	// && hctx.Mode_is_hdump()	// TODO_OLD: should manually insert?
			div_width = wiki.Html_mgr().Img_thumb_width();
		boolean lnki_is_thumbable = Xop_lnki_type.Id_is_thumbable(lnki.Lnki_type());
		if (	lnki_is_thumbable 
			&&	(	!xfer_itm.File_exists()	// "non-found" thumbs should default to 220; otherwise large "non-found" thumbs will create large boxes; PAGE:en.w:Wikipedia:Featured_picture_candidates/September_Morn "|1000000x260px"; DATE:2014-09-24
				&&	!hctx.Mode_is_hdump())	// ignore for hdump mode b/c all images are "non-found"; DATE:2016-05-30
			)
			div_width = Xof_img_size.Thumb_width_img;

		// get halign
		int lnki_halign = lnki.Align_h();
		if (lnki_halign == Xop_lnki_align_h_.Null)
			lnki_halign = wiki.Lang().Img_thumb_halign_default();	// if halign is not supplied, then default to align for language
		byte[] lnki_halign_bry = Xop_lnki_align_h_.Html_names[lnki_halign];

		// img_view_src, img_orig_src
		byte[] img_view_src = xfer_itm.Html_view_url().To_http_file_bry();
		byte[] img_orig_src = xfer_itm.Html_orig_url().To_http_file_bry();
		if (	html_mgr.Img_suppress_missing_src()					// option to suppress src when file is missing
			&&	!xfer_itm.File_exists()								// file is missing; wipe values and wait for "correct" info before regenerating; mostly to handle unknown redirects
			&&	!orig_ext.Id_is_media()								// file is media; never suppress; src needs to be available for "click" on play; note that most media will be missing (not downloaded)
			&&	lnki.Ns_id() != Xow_ns_.Tid__media					// ns is media; never suppress; "src" will use only orig_src; DATE:2014-01-30
			) {						
			img_orig_src = img_view_src = Bry_.Empty;				// null out src
		}

		// main html build
		// if [[Media:]], (a) render like any other ns, not [[File:]]; (b) doo not add to download queue; REF.MW:Linker.php|makeMediaLinkObj;
		// PAGE:en.w:Beethoven; EX: [[:Media:De-Ludwig_van_Beethoven.ogg|listen]]); [[File:Beethoven 3.jpg|The [[Media:BeethovenWithLyreGuitar( W. J. Mahler - 1804).jpg|complete painting]]...]]
		if		(lnki.Ns_id() == Xow_ns_.Tid__media) {
			byte[] media_html = lnki.Caption_exists()
				? caption_fmtr.To_bry(ctx, hctx, src, lnki.Caption_val_tkn(), Bool_.N, Xoh_lnki_text_fmtr.Null__fmt)
				: lnki.Ttl().Full_db();
			html_fmtr.Add_media(bfr, hctx.Mode_is_hdump(), img_orig_src, lnki_ttl_bry, media_html);	// NOTE: use orig_src not view_src; DATE:2014-01-19
		}
		else {
			// orig_is_video 
			boolean orig_is_video = Xof_ext_.Id_is_video_strict(orig_ext.Id());
			if (orig_ext.Id_is_ogg()) {	// note that this is complicated b/c .ogg usually means audio, but can sometimes mean video
				if	(wiki.File_mgr().Version_1_y()) {		// v1
					if (	xfer_itm.File_exists()			// NOTE: xfer_itm.Html_pass() checks for video .ogg files (ext = .ogg and thumb is available); EX: WWI;
						||	xfer_itm.Dbmeta_is_new()		// NOTE: State_new() will always assume that ogg is video; needed for 1st load and dynamic updates
						)						
					orig_is_video = true;
				}
				else										// v2; note that v2 always marks .ogg video as ext=.oga, so .ogg is always audio; 
					orig_is_video = false;
			}
			if (orig_is_video) {	
				xfer_itm.Html_elem_tid_(Xof_html_elem.Tid_vid);		// mark as vid for js_mgr
				this.Write_file_video(bfr, ctx, hctx, src, lnki, img_orig_src, uid, div_width, lnki_halign_bry, lnki_href, img_alt, lnki_ttl_bry, img_view_src, xfer_itm);
			}
			else if  (orig_ext.Id_is_audio())						// audio
				this.Write_file_audio(bfr, ctx, hctx, src, lnki, img_orig_src, uid, div_width, lnki_halign_bry, lnki_href, img_alt, lnki_ttl_bry);
			else													// image
				this.Write_file_image(bfr, ctx, hctx, src, lnki, img_orig_src, uid, div_width, lnki_halign_bry, lnki_href, img_alt, lnki_ttl_bry, img_view_src, xfer_itm, lnki_is_thumbable, lnki_halign, orig_ext);
		}
	}
	private void Write_file_audio(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] img_orig_src, int uid, int div_width, byte[] lnki_halign_bry, byte[] lnki_href, byte[] alt, byte[] lnki_ttl) {
		// init
		int play_btn_width = Get_play_btn_width(lnki.W());
		byte[] info_btn = lnki.Media_icon() ? html_fmtr.Bld_thumb_part_info(lnki_href) : Bry_.Empty;

		// bld audio_div
		byte[] audio_div = html_fmtr.Bld_thumb_file_audio
			( Bld_caption_div(ctx, hctx, src, lnki, uid, img_orig_src, lnki_href)
			, Bld_alt(Bool_.Y, ctx, hctx, src, lnki)
			, Bld_play_btn(hctx, uid, img_orig_src, lnki_ttl, play_btn_width, play_btn_width)	// NOTE: changed max_width from 1024: DATE:2016-08-05
			, info_btn);

		// if "media" flag, add as thumb; else, add directly
		if (lnki.Media_icon())
			html_fmtr.Add_thumb_core(bfr, hctx.Mode_is_hdump(), lnki_halign_bry, uid, div_width, audio_div);
		else
			bfr.Add(audio_div);
	}
	private void Write_file_video(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] img_orig_src, int uid, int div_width, byte[] lnki_halign_bry, byte[] lnki_href, byte[] alt, byte[] lnki_ttl, byte[] img_view_src, Xof_file_itm xfer_itm) {
		// init
		int play_btn_width = Get_play_btn_width(xfer_itm.Html_w());
		boolean video_is_thumb = Xop_lnki_type.Id_defaults_to_thumb(lnki.Lnki_type());
		xfer_itm.Html_elem_tid_(Xof_html_elem.Tid_vid);

		// bld caption / alt
		byte[] caption_html = Bry_.Empty, alt_html = Bry_.Empty;
		if (video_is_thumb) {
			caption_html = Bld_caption_div(ctx, hctx, src, lnki, uid, img_orig_src, lnki_href);
			alt_html = Bld_alt(Bool_.Y, ctx, hctx, src, lnki);
		}

		// bld video_div
		byte[] video_div = html_fmtr.Bld_thumb_file_video(caption_html, alt_html
			, Bld_play_btn(hctx, uid, img_orig_src, lnki_ttl, play_btn_width, play_btn_width)
			, html_fmtr.Bld_thumb_part_img(hctx, page, src, xfer_itm, uid, lnki_ttl, lnki_href, img_view_src, alt));

		// if "thumbable", add as thumb; else, add directly
		if (video_is_thumb)
			html_fmtr.Add_thumb_core(bfr, hctx.Mode_is_hdump(), lnki_halign_bry, uid, div_width, video_div);
		else
			bfr.Add(video_div);
	}
	private void Write_file_image(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] img_orig_src, int uid, int div_width, byte[] lnki_halign_bry, byte[] lnki_href, byte[] alt, byte[] lnki_ttl, byte[] img_view_src, Xof_file_itm xfer_itm
		, boolean lnki_is_thumbable, int lnki_halign, Xof_ext orig_ext) {
		// if centered, add <div class="center">; applies to both thumb and full
		if (lnki_halign == Xop_lnki_align_h_.Center) bfr.Add(Div_center_bgn);

		// bld anch_ttl; EX: title="A.png"
		byte[] anch_ttl = html_wtr.Cfg().Lnki__title()
			? Bld_anch_title(tmp_bfr, src, lnki, lnki_ttl)	// NOTE: Bld_anch_title should only be called if there is no caption, else refs may not show; DATE:2014-03-05
			: Bry_.Empty;

		// get fmtr; note conditional is for imap, which has its own img_fmtr to put in 'imagemap=""'
		Xoh_file_fmtr img_fmtr = lnki.Lnki_file_wkr(); if (img_fmtr == null) img_fmtr = html_fmtr;

		// main image build
		if (lnki_is_thumbable) {	// is "thumb"
			if (bfr.Len() > 0) bfr.Add_byte_nl();

			// write image_div
			byte[] alt_html = alt_in_caption ? Bld_alt(Bool_.Y, ctx, hctx, src, lnki) : Bry_.Empty;
			img_fmtr.Add_full_img(tmp_bfr, hctx, page, src, xfer_itm, uid, lnki_href, Bool_.N, Xoh_lnki_consts.Tid_a_cls_image, Xoh_lnki_consts.Tid_a_rel_none, anch_ttl
				, Xoh_file_fmtr__basic.Escape_xowa_title(lnki_ttl), xfer_itm.Html_w(), xfer_itm.Html_h(), img_view_src, alt
				, xfer_itm.File_exists() ? Xoh_img_cls_.Tid__thumbimage : Xoh_img_cls_.Tid__none
				, Xoh_img_cls_.Bry__none);
			byte[] thumb = tmp_bfr.To_bry_and_clear();
			html_fmtr.Add_thumb_core(bfr, hctx.Mode_is_hdump(), lnki_halign_bry, uid, div_width
				, html_fmtr.Bld_thumb_file_image(thumb, Bld_caption_div(ctx, hctx, src, lnki, uid, img_orig_src, lnki_href), alt_html)
				);
		}
		else {	// is full
			if	(	alt_defaults_to_caption
				&& 	Bry_.Len_eq_0(alt)					// NOTE: if no alt, always use caption for alt; DATE:2013-07-22
				&& 	!lnki.Alt_exists()					// unless blank alt exists; EX: [[File:A.png|a|alt=]] should have alt of "", not "a" 
				) {
				alt = Bld_caption(ctx, Xoh_wtr_ctx.Alt, src, lnki);
			}

			// open "<div class=float>"
			boolean div_align_exists = false;
			switch (lnki.Align_h()) {
				case Xop_lnki_align_h_.Left:	bfr.Add(Div_float_left)	.Add_byte_nl();	div_align_exists = true; break;
				case Xop_lnki_align_h_.Right:	bfr.Add(Div_float_right).Add_byte_nl();	div_align_exists = true; break;
				case Xop_lnki_align_h_.None:	bfr.Add(Div_float_none)	.Add_byte_nl();	div_align_exists = true; break;
			}

			// init vars
			byte img_cls_tid = lnki.Border() == Bool_.Y_byte ? Xoh_img_cls_.Tid__thumbborder : Xoh_img_cls_.Tid__none;
			byte[] img_cls_other = lnki.Lnki_cls(); // PAGE:en.s:Page:Notes_on_Osteology_of_Baptanodon._With_a_Description_of_a_New_Species.pdf/3; DATE:2014-09-06

			Arg_nde_tkn lnki_link_tkn = lnki.Link_tkn();
			if (lnki_link_tkn == Arg_nde_tkn.Null)		// link absent; just write it
				img_fmtr.Add_full_img(bfr, hctx, page, src, xfer_itm, uid, lnki_href, Bool_.N, Xoh_lnki_consts.Tid_a_cls_image, Xoh_lnki_consts.Tid_a_rel_none, anch_ttl, Xoh_file_fmtr__basic.Escape_xowa_title(lnki_ttl), xfer_itm.Html_w(), xfer_itm.Html_h(), img_view_src, alt, img_cls_tid, img_cls_other);
			else {										// link exists; more logic; EX: [[File:A.png|thumb|link=B]]
				// over-ride other vars based on link arg; below should be cleaned up
				Arg_itm_tkn link_tkn = lnki_link_tkn.Val_tkn();
				byte[] link_arg = Xoa_ttl.Replace_spaces(link_tkn.Dat_to_bry(src));	// replace spaces with unders, else "/wiki/File:A b.ogg" instead of "A_b.ogg"; DATE:2015-11-27
				if (Bry_.Has_at_bgn(link_arg, Xop_tkn_.Anchor_byte)) link_arg = Bry_.Add(ctx.Page().Ttl().Page_db(), link_arg);
				byte[] link_arg_html = tmp_link_parser.Parse(tmp_bfr, tmp_url, wiki, link_arg, lnki_href);
				byte[] xowa_title_bry = tmp_link_parser.Html_xowa_ttl();			// NOTE: xowa_title_bry will be link arg; [[File:A.png|link=file:///A.ogg]] -> A.ogg x> A.png
				boolean a_href_is_file = true;
				if (xowa_title_bry == null) {xowa_title_bry = lnki_ttl; a_href_is_file = false;}
				link_arg = link_arg_html == null ? lnki_href : link_arg_html;		// if parse fails, then assign to lnki_href; EX:link={{{1}}}
				link_arg = Gfo_url_encoder_.Href_quotes_v2.Encode(link_arg);		// must encode quotes; PAGE:en.w:List_of_cultural_heritage_sites_in_Punjab,_Pakistan; DATE:2014-07-16; changed to use "v2" so not to double encode "%" values; DATE:2016-10-10
				// if (Bry_.Len_gt_0(tmp_link_parser.Html_xowa_ttl())) lnki_ttl = tmp_link_parser.Html_xowa_ttl(); // DELETE: not sure why this is here; breaks test; DATE:2015-11-28
				img_fmtr.Add_full_img(bfr, hctx, page, src, xfer_itm, uid, link_arg, a_href_is_file, tmp_link_parser.Html_anchor_cls(), tmp_link_parser.Html_anchor_rel(), anch_ttl, Xoh_file_fmtr__basic.Escape_xowa_title(xowa_title_bry), xfer_itm.Html_w(), xfer_itm.Html_h(), img_view_src, alt, img_cls_tid, img_cls_other);
			}

			// close "<div class=float>"
			if (div_align_exists) bfr.Add(Gfh_tag_.Div_rhs);
		}

		// close <div class="center">
		if (lnki_halign == Xop_lnki_align_h_.Center) bfr.Add(Gfh_tag_.Div_rhs);
	}
	private byte[] Bld_caption_div(Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, int uid, byte[] img_orig_src, byte[] lnki_href) {
		byte[] caption = Bld_caption(ctx, hctx, src, lnki);
		byte[] magnify_btn = lnki.Media_icon() ? html_fmtr.Bld_thumb_part_magnify(lnki_href, msg_file_enlarge) : Bry_.Empty;
		return html_fmtr.Bld_thumb_part_caption(magnify_btn, caption);
	}
	private byte[] Bld_caption(Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki) {
		return lnki.Caption_exists() 
			? caption_fmtr.To_bry(ctx, hctx, src, lnki.Caption_val_tkn(), Bool_.N, Xoh_lnki_text_fmtr.Null__fmt)
			: Bry_.Empty;
	}
	private byte[] Bld_anch_title(Bry_bfr bfr, byte[] src, Xop_lnki_tkn lnki, byte[] lnki_ttl) {
		if		(	Bitmask_.Has_int(lnki.Lnki_type(), Xop_lnki_type.Id_thumb)
				||	Bitmask_.Has_int(lnki.Lnki_type(), Xop_lnki_type.Id_frame)			// If the image is a thumb, do not add a title / alt, even if a caption is available
				)
			return Bry_.Empty;
		else if	(	Bitmask_.Has_int(lnki.Lnki_type(), Xop_lnki_type.Id_frameless)) {	// If the image is frameless, add the caption as a title / alt. If no caption is available, do not add a title / alt
		}
		Xop_tkn_itm capt_tkn = lnki.Caption_tkn();
		if (capt_tkn == Xop_tkn_null.Null_tkn) return Bry_.Empty; // no caption; return empty; NOTE: do not use lnki as caption; DATE:2013-12-31

		// build title="text"
		bfr.Add(Atr_title);
		Xoh_lnki_title_bldr.Add(bfr, src, capt_tkn);
		bfr.Add_byte(Byte_ascii.Quote);
		return bfr.To_bry_and_clear();
	}
	public byte[] Bld_alt(boolean html, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki) {
		if (!lnki.Alt_exists()) return Bry_.Empty;
		return html 
			? alt_fmtr.To_bry(ctx, hctx, src, lnki.Alt_tkn().Val_tkn(), Bool_.Y, html_fmtr.Fmt_thumb_part_alt())
			: alt_fmtr.To_bry(ctx, hctx, src, lnki.Alt_tkn().Val_tkn(), Bool_.N, Xoh_lnki_text_fmtr.Null__fmt);
	}
	private byte[] Bld_play_btn(Xoh_wtr_ctx hctx, int uid, byte[] a_href, byte[] lnki_href, int width, int max_width) {
		return html_fmtr.Bld_thumb_part_play(hctx.Mode_is_hdump(), uid, a_href, Xoh_file_fmtr__basic.Escape_xowa_title(lnki_href)
			, width - 2, max_width);	// NOTE: -2 is fudge factor else play btn will jut out over video thumb; see Earth and ISS video
	}
	private int Get_play_btn_width(int w) {return w > 0 ? w : html_mgr.Img_thumb_width();}	// if no width set width to default img width
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__alt_in_caption))				alt_in_caption = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__alt_defaults_to_caption))	alt_defaults_to_caption = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__alt_show_in_html))			alt_show_in_html = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Cfg__alt_in_caption				= "xowa.html.alt.show_in_caption"
	, Cfg__alt_defaults_to_caption		= "xowa.html.alt.empty_alt_uses_caption"
	, Cfg__alt_show_in_html				= "xowa.html.alt.show_in_html"
	;

	private static final    byte[]
	  Div_center_bgn			= Bry_.new_a7("<div class=\"center\">")
	, Div_float_none			= Bry_.new_a7("<div class=\"floatnone\">")
	, Div_float_left			= Bry_.new_a7("<div class=\"floatleft\">")
	, Div_float_right			= Bry_.new_a7("<div class=\"floatright\">")
	, Atr_title					= Bry_.new_a7(" title=\"")
	;
}
