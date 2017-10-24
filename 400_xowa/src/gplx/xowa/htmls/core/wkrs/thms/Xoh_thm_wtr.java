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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.core.brys.fmtrs.*; import gplx.core.brys.args.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xoh_thm_wtr implements gplx.core.brys.Bfr_arg {
	private final    Bfr_arg__bry			div_0_align = Bfr_arg__bry.New_empty();
	private final    Bfr_arg__int			div_1_width = new Bfr_arg__int(-1);
	private final    Bfr_arg__hatr_id		div_1_id = Bfr_arg__hatr_id.New_id("xowa_file_div_");	// NOTE: "xowa_file_div_" used multiple times; DATE:2016-06-13
	private final    Bfr_arg__hatr_bry		div_2_href = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__href);
	private final    Bfr_arg__bry_ary		div_2_magnify = new Bfr_arg__bry_ary();
	private final    Bfr_arg__bry			capt_2 = Bfr_arg__bry.New(Bry_.Empty);
	private final    Bfr_arg__bry			capt_3 = Bfr_arg__bry.New(Bry_.Empty);
	private final    Bfr_arg__bry			enlarge = Bfr_arg__bry.New(Bry_.Empty);
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	private Bfr_arg div_1_img = Bfr_arg_.Noop, capt_1 = Bfr_arg_.Noop;
	private byte[] img_is_vid_nl, trailing_space;
	public Xoh_thm_wtr Clear() {
		Bfr_arg_.Clear(div_0_align, div_1_id, div_2_href, enlarge, capt_2, capt_3); // , div_1_width, div_2_magnify
		div_1_img = capt_1 = Bfr_arg_.Noop;
		img_is_vid_nl = Bry_.Empty;
		return this;
	}
	public void Write(Bry_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, boolean img_is_vid, int div_0_align, int div_1_w, Xoh_img_wtr img_wtr, byte[] div_2_href
		, Bfr_arg capt_1, boolean capt_2_exists, boolean capt_2_is_tidy, byte[] capt_2_bry, boolean capt_3_exists, byte[] capt_3_bry, boolean xowa_alt_text_exists) {
		this.Clear();
		this.img_is_vid_nl = img_is_vid ? Byte_ascii.Nl_bry : Bry_.Empty;
		this.trailing_space = img_is_vid ? Bry_.Empty : Byte_ascii.Space_bry;
		this.div_0_align.Set_by_val(gplx.xowa.parsers.lnkis.Xop_lnki_align_h_.To_bry(div_0_align));
		if (!hctx.Mode_is_diff())
			this.div_1_id.Set(img_wtr.Fsdb_itm().Html_uid());
		this.div_1_width.Set(div_1_w);
		this.div_1_img = img_wtr;
		this.div_2_href.Set_by_bry(div_2_href);
		div_2_magnify.Set(hctx.Fsys__root(), bry_div_2_magnify);
		Xow_wiki wiki = (Xow_wiki)hctx.Wiki__ttl_parser();
		if (wiki.Type_is_edit())
			enlarge.Set_by_val(wiki.Lang().Msg_mgr().Itm_by_id_or_null(gplx.xowa.langs.msgs.Xol_msg_itm_.Id_file_enlarge).Val());
		else
			enlarge.Set_by_val(Bry__enlarge);
		this.capt_1 = capt_1;
		if (capt_2_exists) {
			if (capt_2_is_tidy)
				capt_2.Set_by_val(capt_2_bry);
			else {
				String xowa_alt_text_bgn = xowa_alt_text_exists ? "<div class=\"xowa_alt_text\">\n" : String_.Empty;
				String xowa_alt_text_end = xowa_alt_text_exists ? "\n</div>" : String_.Empty;
				alt_fmtr.Bld_bfr_many(tmp_bfr, xowa_alt_text_bgn, capt_2_bry, xowa_alt_text_end);
				capt_2.Set_by_val(tmp_bfr.To_bry_and_clear());
			}
		}
		else
			capt_2.Set_by_val(Byte_ascii.Nl_bry);	// no capt_2, so force \n betwen </div>~{capt_2}</div>
		if (capt_3_exists)
			capt_3.Set_by_val(capt_3_bry);
		else
			capt_3.Set_by_val(Byte_ascii.Nl_bry);
		this.Bfr_arg__add(bfr);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr.Bld_bfr_many(bfr, div_0_align, div_1_id, div_1_width, img_is_vid_nl, div_1_img, trailing_space, div_2_href, div_2_magnify, enlarge, capt_1, capt_2, capt_3);
	}
	private static final    byte[] Bry__enlarge = Bry_.new_a7("Enlarge");
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div class=\"thumb t~{div_0_align}\">"
	,   "<div~{div_1_id} class=\"thumbinner\" style=\"width:~{div_1_width}px;\">~{img_is_vid_nl}~{div_1_img}~{trailing_space}"	// NOTE: trailing space is intentional; matches jtidy behavior
	,     "<div class=\"thumbcaption\">"
	,       "<div class=\"magnify\"><a~{div_2_href} class=\"internal\" title=\"~{enlarge}\"></a></div>"
	,       "~{capt_1}</div>~{capt_2}</div>~{capt_3}</div>"
	), "div_0_align", "div_1_id", "div_1_width", "img_is_vid_nl", "div_1_img", "trailing_space", "div_2_href", "div_2_magnify", "enlarge", "capt_1", "capt_2", "capt_3");
	private static final    Bry_fmtr alt_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	,     "~{xowa_alt_text_bgn}<hr>"
	,     "<div class=\"thumbcaption\">~{alt}</div>~{xowa_alt_text_end}"
	,	  ""
	), "xowa_alt_text_bgn", "alt", "xowa_alt_text_end");
	private static final    byte[] bry_div_2_magnify = Bry_.new_a7("bin/any/xowa/file/mediawiki.file/magnify-clip.png");
}
