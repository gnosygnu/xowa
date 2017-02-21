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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.parsers.*; import gplx.xowa.htmls.core.htmls.*;
public class Gallery_mgr_wtr_ {
	public static final    Bry_fmtr
	  Fmtr__ul__style				= Bry_fmtr.new_(	"max-width:~{max_width}px; _width:~{max_width}px;", "max_width")
	, Fmtr__ul__cls					= Bry_fmtr.new_(	"gallery mw-gallery-~{mode}", "mode")
	, Fmtr__mgr_caption				= Bry_fmtr.new_(	"\n  <li class=\"gallerycaption\">~{caption}</li>", "caption")
	, Fmtr__li__lhs					= Bry_fmtr.new_(	"\n  <li~{li_id} class=\"gallerybox\" ~{itm_box_w}>"
													  + "\n    <div ~{itm_box_w}>", "li_id", "itm_box_w")
	, Fmtr__div1__img				= Bry_fmtr.new_(	"\n      <div class=\"thumb\" style=\"width:~{width}px;\">", "width")
	, Fmtr__div1__missing			= Bry_fmtr.new_(	"\n      <div class=\"thumb\" style=\"height:~{height}px;\">~{ttl_text}</div>", "height", "ttl_text")
	, Fmtr__div1__vpad				= Bry_fmtr.new_(	"\n        <div ~{vpad}>\n          ", "vpad")
	, hdump_box_w_fmtr				= Bry_fmtr.new_(	"width:~{width}px;", "width")
	, hdump_img_pad_fmtr			= Bry_fmtr.new_(	"margin:~{width}px auto;", "width")
	;
	public static final    byte[] 
	  Id__ul	= Bry_.new_a7("xowa_gallery_ul_")
	, Id__li	= Bry_.new_a7("xowa_gallery_li_")
	;
	private static final    byte[] Id__atr = Bry_.new_a7(" id=\"");
	public static byte[] Bld_id(Bry_bfr bfr, byte[] prefix, int id)			{return bfr.Add(prefix).Add_int_variable(id).To_bry_and_clear();}
	public static byte[] Bld_id_atr(Bry_bfr bfr, boolean hdump, byte[] li_id)	{return hdump ? Bry_.Empty : bfr.Add(Id__atr).Add(li_id).Add_byte_quote().To_bry_and_clear();}
	public static byte[] Bld_caption(Xowe_wiki wiki, Xop_ctx ctx, Xoh_html_wtr wtr, Xoh_wtr_ctx hctx, Gallery_itm itm) {
		byte[] rv = itm.Caption_bry();
		if (Bry_.Len_gt_0(rv)) {
			Bry_bfr caption_bfr = wiki.Utl__bfr_mkr().Get_k004();
			Xop_root_tkn caption_root = itm.Caption_tkn();
			wtr.Write_tkn_to_html(caption_bfr, ctx, hctx, caption_root.Root_src(), caption_root, Xoh_html_wtr.Sub_idx_null, caption_root);
			rv = caption_bfr.To_bry_and_rls();
		}
		return rv;
	}
	public static int Calc_vpad(int mgr_itm_height, int html_h) {
		int	min_thumb_height = html_h > 17 ? html_h : 17;						// $minThumbHeight =  $thumb->height > 17 ? $thumb->height : 17;
		return (int)Math_.Floor((30 + mgr_itm_height - min_thumb_height) / 2);	// $vpad = floor(( self::THUMB_PADDING + $this->mHeights - $minThumbHeight ) /2);
	}
}
