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
package gplx.xowa.htmls.core.wkrs.thms;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.args.BryBfrArgUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.types.custom.brys.fmts.fmtrs.*; import gplx.types.custom.brys.wtrs.args.*;
import gplx.langs.htmls.*;
import gplx.xowa.htmls.core.wkrs.bfr_args.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
public class Xoh_thm_wtr implements BryBfrArg {
	private final BryBfrArgBry div_0_align = BryBfrArgBry.NewEmpty();
	private final BryBfrArgInt div_1_width = new BryBfrArgInt(-1);
	private final Bfr_arg__hatr_id		div_1_id = Bfr_arg__hatr_id.New_id("xowa_file_div_");	// NOTE: "xowa_file_div_" used multiple times; DATE:2016-06-13
	private final Bfr_arg__hatr_bry		div_2_href = new Bfr_arg__hatr_bry(Gfh_atr_.Bry__href);
	private final BryBfrArgBryAry div_2_magnify = new BryBfrArgBryAry();
	private final BryBfrArgBry capt_2 = BryBfrArgBry.New(BryUtl.Empty);
	private final BryBfrArgBry capt_3 = BryBfrArgBry.New(BryUtl.Empty);
	private final BryBfrArgBry enlarge = BryBfrArgBry.New(BryUtl.Empty);
	private final BryWtr tmp_bfr = BryWtr.NewWithSize(255);
	private BryBfrArg div_1_img = BryBfrArgUtl.Noop, capt_1 = BryBfrArgUtl.Noop;
	private byte[] img_is_vid_nl, trailing_space;
	public Xoh_thm_wtr Clear() {
		BryBfrArgUtl.Clear(div_0_align, div_1_id, div_2_href, enlarge, capt_2, capt_3); // , div_1_width, div_2_magnify
		div_1_img = capt_1 = BryBfrArgUtl.Noop;
		img_is_vid_nl = BryUtl.Empty;
		return this;
	}
	public void Write(BryWtr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, boolean img_is_vid, int div_0_align, int div_1_w, Xoh_img_wtr img_wtr, byte[] div_2_href
		, BryBfrArg capt_1, boolean capt_2_exists, boolean capt_2_is_tidy, byte[] capt_2_bry, boolean capt_3_exists, byte[] capt_3_bry, boolean xowa_alt_text_exists) {
		this.Clear();
		this.img_is_vid_nl = img_is_vid ? AsciiByte.NlBry : BryUtl.Empty;
		this.trailing_space = img_is_vid ? BryUtl.Empty : AsciiByte.SpaceBry;
		this.div_0_align.SetByVal(gplx.xowa.parsers.lnkis.Xop_lnki_align_h_.To_bry(div_0_align));
		if (!hctx.Mode_is_diff())
			this.div_1_id.Set(img_wtr.Fsdb_itm().Html_uid());
		this.div_1_width.Set(div_1_w);
		this.div_1_img = img_wtr;
		this.div_2_href.Set_by_bry(div_2_href);
		div_2_magnify.Set(hctx.Fsys__root(), bry_div_2_magnify);
		Xow_wiki wiki = (Xow_wiki)hctx.Wiki__ttl_parser();
		if (wiki.Type_is_edit())
			enlarge.SetByVal(wiki.Lang().Msg_mgr().Itm_by_id_or_null(gplx.xowa.langs.msgs.Xol_msg_itm_.Id_file_enlarge).Val());
		else
			enlarge.SetByVal(Bry__enlarge);
		this.capt_1 = capt_1;
		if (capt_2_exists) {
			if (capt_2_is_tidy)
				capt_2.SetByVal(capt_2_bry);
			else {
				String xowa_alt_text_bgn = xowa_alt_text_exists ? "<div class=\"xowa_alt_text\">\n" : StringUtl.Empty;
				String xowa_alt_text_end = xowa_alt_text_exists ? "\n</div>" : StringUtl.Empty;
				alt_fmtr.BldToBfrMany(tmp_bfr, xowa_alt_text_bgn, capt_2_bry, xowa_alt_text_end);
				capt_2.SetByVal(tmp_bfr.ToBryAndClear());
			}
		}
		else
			capt_2.SetByVal(AsciiByte.NlBry);	// no capt_2, so force \n betwen </div>~{capt_2}</div>
		if (capt_3_exists)
			capt_3.SetByVal(capt_3_bry);
		else
			capt_3.SetByVal(AsciiByte.NlBry);
		this.AddToBfr(bfr);
	}
	public void AddToBfr(BryWtr bfr) {
		fmtr.BldToBfrMany(bfr, div_0_align, div_1_id, div_1_width, img_is_vid_nl, div_1_img, trailing_space, div_2_href, div_2_magnify, enlarge, capt_1, capt_2, capt_3);
	}
	private static final byte[] Bry__enlarge = BryUtl.NewA7("Enlarge");
	private static final BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( "<div class=\"thumb t~{div_0_align}\">"
	,   "<div~{div_1_id} class=\"thumbinner\" style=\"width:~{div_1_width}px;\">~{img_is_vid_nl}~{div_1_img}~{trailing_space}"	// NOTE: trailing space is intentional; matches jtidy behavior
	,     "<div class=\"thumbcaption\">"
	,       "<div class=\"magnify\"><a~{div_2_href} class=\"internal\" title=\"~{enlarge}\"></a></div>"
	,       "~{capt_1}</div>~{capt_2}</div>~{capt_3}</div>"
	), "div_0_align", "div_1_id", "div_1_width", "img_is_vid_nl", "div_1_img", "trailing_space", "div_2_href", "div_2_magnify", "enlarge", "capt_1", "capt_2", "capt_3");
	private static final BryFmtr alt_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	,     "~{xowa_alt_text_bgn}<hr>"
	,     "<div class=\"thumbcaption\">~{alt}</div>~{xowa_alt_text_end}"
	,	  ""
	), "xowa_alt_text_bgn", "alt", "xowa_alt_text_end");
	private static final byte[] bry_div_2_magnify = BryUtl.NewA7("bin/any/xowa/file/mediawiki.file/magnify-clip.png");
}
