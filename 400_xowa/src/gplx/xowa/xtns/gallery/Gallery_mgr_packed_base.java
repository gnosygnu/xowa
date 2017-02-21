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
import gplx.xowa.files.*; import gplx.xowa.htmls.modules.*;
import gplx.xowa.parsers.lnkis.*;
public class Gallery_mgr_packed_base extends Gallery_mgr_base {
	@Override public byte	Tid() {return Gallery_mgr_base_.Tid__packed;}
	@Override public void Init(int itm_default_w, int itm_default_h, int itms_per_row) {
		this.itm_default_w = itm_default_w;
		this.itm_default_h = itm_default_h;
		this.itms_per_row = 0;	// Does not support per row option.
	}
	@Override public void Get_modules(Xoae_page page) {
		super.Get_modules(page);
		page.Html_data().Head_mgr().Itm__gallery().Enabled_y_();
	}
	@Override public int Get_thumb_padding()				{return 0;}
	@Override public int Get_gb_padding()				{return 2;}
	@Override public int Get_vpad(int itm_h, int thm_h)	{
		return (int)((this.Get_thumb_padding() + itm_h - thm_h / Scale_factor) / 2);
	}
	@Override public int Get_thumb_div_width(int thm_w) {
		if (thm_w < Scale_factor_x_60)
			thm_w = Scale_factor_x_60;	// Require at least 60px wide, so caption is wide enough to work.
		return (int)(thm_w / Scale_factor) + this.Get_thumb_padding();
	}
	@Override public int Get_gb_width(int thm_w, int thm_h) {
		int val = thm_w == -1 ? (int)(itm_default_w * Scale_factor) : thm_w;
		return Get_thumb_div_width(val) + this.Get_gb_padding();
	}
	@Override public void Get_thumb_size(Xop_lnki_tkn lnki, Xof_ext ext) {
		Get_thumb_size_static(lnki, ext, itm_default_w, itm_default_h);
	}
	@Override public void Adjust_image_parameters(Xof_file_itm xfer_itm) {
		int w = (int)(xfer_itm.Html_w() / Scale_factor);
		int h = (int)(xfer_itm.Html_h() / Scale_factor);
		xfer_itm.Html_size_(w, h);
	}
	public static final double Scale_factor = 1.5d;	// We artificially have 1.5 the resolution neccessary so that we can scale it up by that much on the client side, without worrying about requesting a new image.
	private static final int Scale_factor_x_60 = (int)(Scale_factor * 60);
	public static void Get_thumb_size_static(Xop_lnki_tkn lnki, Xof_ext ext, int itm_default_w, int itm_default_h) {
		int w;
		if (ext.Id_is_audio())
			w = itm_default_w;
		else
			w = (itm_default_h) * 10 + 100;	// We want the width not to be the constraining factor, so use random big number.
		lnki.W_((int)(Scale_factor * w));
		lnki.H_((int)(Scale_factor * itm_default_h));
	}
}
class Gallery_mgr_packed_overlay extends Gallery_mgr_packed_base {
	@Override public void Wrap_gallery_text(Bry_bfr bfr, byte[] gallery_text, int thm_w, int thm_h) {
		if (gallery_text.length == 0) return; // If we have no text, do not output anything to avoid ugly white overlay.
		int img_w = this.Get_gb_width(thm_w, thm_h) - this.Get_thumb_padding() - this.Get_gb_padding();
		int caption_w = (img_w - 20);
		bfr	.Add(Wrap_gallery_text_0).Add_int_variable(caption_w)
			.Add(Wrap_gallery_text_1).Add(gallery_text)
			.Add(Wrap_gallery_text_2)
			;
	}
	private static final    byte[] 
	  Wrap_gallery_text_0 = Bry_.new_a7("\n      <div class=\"gallerytextwrapper\" style=\"width: ")
	, Wrap_gallery_text_1 = Bry_.new_a7("px\"><div class=\"gallerytext\">\n") // NOTE: The newline after <div class="gallerytext"> is needed to accommodate htmltidy
	, Wrap_gallery_text_2 = Bry_.new_a7("\n      </div></div>")	// NOTE: 2nd </div> is not part of MW, but needed to close div
	;
}
class Gallery_mgr_packed_hover extends Gallery_mgr_packed_overlay {	}
