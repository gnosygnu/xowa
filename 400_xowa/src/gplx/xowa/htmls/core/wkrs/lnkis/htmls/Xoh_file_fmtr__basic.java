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
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.makes.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.files.*;	
import gplx.xowa.parsers.lnkis.*;
public class Xoh_file_fmtr__basic implements Xoh_file_fmtr {
	private final    Xoh_arg_img_core img_atrs = new Xoh_arg_img_core__basic(); 
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(128);
	private final    Bfr_arg__hatr_id div2_id_atr = Bfr_arg__hatr_id.New_id("xowa_file_div_"), play_id_atr = Bfr_arg__hatr_id.New_id("xowa_file_play_");

	public void Add_media(Bry_bfr bfr, boolean mode_is_hdump, byte[] a_href, byte[] a_title, byte[] a_html) {
		if (mode_is_hdump) a_href = Bry_.Empty;
		fmtr_full_media.Bld_many(bfr, a_href, a_title, a_html);
	}
	private final    Bry_fmt fmtr_full_media = Bry_fmt.Auto("<a href=\"~{a_href}\" xowa_title=\"~{a_xowa_title}\">~{html}\n</a>");

	@gplx.Virtual public void Add_full_img(Bry_bfr bfr, Xoh_wtr_ctx hctx, Xoae_page page, byte[] src, Xof_file_itm xfer_itm, int uid
	, byte[] a_href, boolean a_href_is_file, byte a_cls, byte a_rel, byte[] a_title, byte[] a_xowa_title
	, int img_w, int img_h, byte[] img_src, byte[] img_alt, byte img_cls, byte[] img_cls_other
	) {
		// init atrs
		img_atrs.Init(uid, img_src, img_w, img_h);
		byte[] img_cls_atr = Xoh_img_cls_.To_html(img_cls, img_cls_other);

		// fmt
		if (Bry_.Len_eq_0(a_href))	// empty link should not create anchor; EX:[[File:A.png|link=|abc]]; PAGE:en.w:List_of_counties_in_New_York; DATE:2016-01-10
			fmtr_full_img__anch_n.Bld_many(bfr
			, uid, img_alt, img_atrs, img_cls_atr);
		else
			fmtr_full_img__anch_y.Bld_many(bfr
			, a_href, Xoh_lnki_consts.A_cls_to_bry(a_cls), Xoh_lnki_consts.A_rel_to_bry(a_rel), a_title, a_xowa_title
			, uid, img_alt, img_atrs, img_cls_atr);
	}
	private final    Bry_fmt fmtr_full_img__anch_y = Bry_fmt.Auto
	( "<a href=\"~{a_href}\"~{a_class}~{a_rel}~{a_title} xowa_title=\"~{a_xowa_title}\">"
	+ "<img id=\"xoimg_~{uid}\" alt=\"~{img_alt}\"~{img_core}~{img_class} /></a>"
	);
	private final    Bry_fmt fmtr_full_img__anch_n = Bry_fmt.Auto
	( "<img id=\"xoimg_~{uid}\" alt=\"~{img_alt}\"~{img_core}~{img_class} />");

	public byte[] Bld_thumb_part_img(Xoh_wtr_ctx hctx, Xoae_page page, byte[] src, Xof_file_itm xfer_itm, int uid, byte[] lnki_ttl, byte[] a_href, byte[] img_src, byte[] img_alt) {
		byte[] a_title_atr = Gfh_atr_.Make(tmp_bfr, Gfh_atr_.Bry__title, xfer_itm.Lnki_ttl());
		Add_full_img(tmp_bfr, hctx, page, src, xfer_itm, uid, a_href, Bool_.N, Xoh_lnki_consts.Tid_a_cls_image
			, Xoh_lnki_consts.Tid_a_rel_none, a_title_atr
			, Xoh_file_fmtr__basic.Escape_xowa_title(lnki_ttl)	// NOTE: must use lnki_ttl, not xfer_itm.Lnki_ttl(); 1st observes case-sensitivity; EX:"a.ogv"; PAGE:de.d:fappieren DATE:2016-06-23
			, xfer_itm.Html_w(), xfer_itm.Html_h(), img_src, img_alt, Xoh_img_cls_.Tid__none, null);
		return tmp_bfr.To_bry_and_clear();
	}
	public void Add_thumb_core(Bry_bfr bfr, boolean mode_is_hdump, byte[] div1_halign, int div2_id, int div2_width, byte[] div2_content) {
		div2_id_atr.Set(mode_is_hdump ? Bfr_arg__hatr_id.Id__ignore : div2_id);
		fmtr_thumb_core.Bld_many(bfr, div1_halign, div2_id_atr, div2_width, div2_content);
	}
	private final    Bry_fmt fmtr_thumb_core = Bry_fmt.Auto(String_.Concat_lines_nl_skip_last	// REF.MW: Linker.php|makeImageLink2
	( "<div class=\"thumb t~{div1_halign}\">"
	, "  <div~{div2_id} class=\"thumbinner\" style=\"width:~{div2_width}px;\">"
	,     "~{div3_content}"
	, "  </div>"
	, "</div>"
	, ""
	));
	public byte[] Bld_thumb_part_caption(byte[] magnify_btn, byte[] caption) {return fmtr_thumb_part_caption.Bld_many_to_bry(tmp_bfr, magnify_btn, caption);}
	private final    Bry_fmt fmtr_thumb_part_caption = Bry_fmt.Auto(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div class=\"thumbcaption\">~{magnify_btn}~{caption}"
	, "    </div>"
	));

	public byte[] Bld_thumb_file_image(byte[] thumb_image, byte[] caption, byte[] alt) {return fmtr_thumb_file_image.Bld_many_to_bry(tmp_bfr, thumb_image, caption, alt);}
	private final    Bry_fmt fmtr_thumb_file_image = Bry_fmt.Auto("    ~{thumb_image}~{caption}~{alt}");

	@gplx.Virtual public byte[] Bld_thumb_file_audio(byte[] caption, byte[] alt, byte[] play_btn, byte[] info_btn) {return fmtr_thumb_file_audio.Bld_many_to_bry(tmp_bfr, play_btn, info_btn, caption, alt);}
	private Bry_fmt fmtr_thumb_file_audio = Bry_fmt.Auto(String_.Concat_lines_nl_skip_last
	( "    <div class=\"xowa_media_div\">~{play_btn}~{info_btn}"
	, "    </div>~{caption}~{alt}"
	));

	@gplx.Virtual public byte[] Bld_thumb_file_video(byte[] caption, byte[] alt, byte[] play_btn, byte[] vid_img) {return fmtr_thumb_file_video.Bld_many_to_bry(tmp_bfr, vid_img, play_btn, caption, alt);}
	private final    Bry_fmt fmtr_thumb_file_video = Bry_fmt.Auto(String_.Concat_lines_nl_skip_last
	( "    <div class=\"xowa_media_div\">"
	, "      <div>~{vid_img}"
	, "      </div>~{play_btn}"
	, "    </div>~{caption}~{alt}"
	));

	public Bry_fmt Fmt_thumb_part_alt() {return fmt__thumb_part_alt;} private final    Bry_fmt fmt__thumb_part_alt = Bry_fmt.Auto
	(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div class=\"" + String_.new_a7(Bry__xowa_alt_text) + "\">"
	, "    <hr/>"
	, "    <div class=\"thumbcaption\">~{html}"
	, "    </div>"
	, "    </div>"
	));
	public static final    byte[] Bry__xowa_alt_text = Bry_.new_a7("xowa_alt_text");

	public byte[] Bld_thumb_part_magnify(byte[] a_href, byte[] a_title) {
		return fmtr_thumb_part_magnify.Bld_many_to_bry(tmp_bfr, a_href, a_title);
	}
	private final    Bry_fmt fmtr_thumb_part_magnify = Bry_fmt.Auto("\n<div class=\"magnify\"><a href=\"~{a_href}\" class=\"internal\" title=\"~{a_title}\"></a></div>");

	public byte[] Bld_thumb_part_info(byte[] a_href) {
		return fmtr_thumb_part_info.Bld_many_to_bry(tmp_bfr, a_href);
	}
	private final    Bry_fmt fmtr_thumb_part_info = Bry_fmt.Auto("\n<div><a href=\"~{a_href}\" class=\"xowa_media_info\" title=\"About this file\"></a></div>");

	public byte[] Bld_thumb_part_play(boolean mode_is_hdump, int uid, byte[] a_href, byte[] a_xowa_title, int a_width, int a_max_width) {
		if (mode_is_hdump) {
			play_id_atr.Set(Bfr_arg__hatr_id.Id__ignore);
			a_href = Bry_.Empty;
			if (a_max_width == -1) {
				a_width = 218; a_max_width = 220;	// NOTE: hardcode widths; hdump will get actual file with from fsdb
			}
		}
		else
			play_id_atr.Set(uid);
		return fmtr_thumb_part_play.Bld_many_to_bry(tmp_bfr, play_id_atr, a_href, a_xowa_title, a_width, a_max_width);
	}
	private final    Bry_fmt fmtr_thumb_part_play = Bry_fmt.Auto
	("\n<div><a~{id} href=\"~{a_href}\" xowa_title=\"~{a_xowa_title}\" class=\"xowa_media_play\" style=\"width:~{a_width}px;max-width:~{a_max_width}px;\" alt=\"Play sound\"></a></div>");

	public static byte[] Escape_xowa_title(byte[] lnki_ttl) {
		return Xoa_ttl.Replace_spaces(gplx.langs.htmls.encoders.Gfo_url_encoder_.Href_quotes.Encode(lnki_ttl)); // must encode xowa_title, particularly quotes; EX: xowa_title="A"b.png"; PAGE:en.w:Earth DATE:2015-11-27
	}
	public static byte[] Escape_xowa_title_v2(byte[] lnki_ttl) {
		return Xoa_ttl.Replace_spaces(gplx.langs.htmls.encoders.Gfo_url_encoder_.Href_quotes_v2.Encode(lnki_ttl)); // must encode xowa_title, particularly quotes; EX: xowa_title="A"b.png"; PAGE:en.w:Earth DATE:2015-11-27
	}
}
