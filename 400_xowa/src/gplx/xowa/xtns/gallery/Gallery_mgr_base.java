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
import gplx.xowa.files.*;
import gplx.xowa.parsers.lnkis.*;
public abstract class Gallery_mgr_base {
	public abstract byte	Tid();
	public int				Itm_default_w() {return itm_default_w;} protected int itm_default_w;
	public int				Itm_default_h() {return itm_default_h;} protected int itm_default_h;
	public int				Itms_per_row()	{return itms_per_row;} protected int itms_per_row;
	@gplx.Virtual public int		Get_all_padding() {return this.Get_thumb_padding() + this.Get_gb_padding() + this.Get_gb_borders();} // REF.MW: getAllPadding; How many pixels of whitespace surround the thumbnail.
	@gplx.Virtual public int		Get_gb_padding() {return 5;}		// REF.MW: getGBPadding; GB stands for gallerybox (as in the <li class="gallerybox"> element)
	@gplx.Virtual public int		Get_gb_borders() {return 8;}		// REF.MW: getGBBorders; Get how much extra space the borders around the image takes up. For this mode, it is 2px borders on each side + 2px implied padding on each side from the stylesheet, giving us 2*2+2*2 = 8.
	@gplx.Virtual public int		Get_gb_width(int thm_w, int thm_h) {// REF.MW: getGBWidth; Width of gallerybox <li>. Generally is the width of the image, plus padding on image plus padding on gallerybox.s
		return itm_default_w + this.Get_thumb_padding() + this.Get_gb_padding();
	}
	@gplx.Virtual public int		Get_vpad(int xnde_h, int html_h) {	// REF.MW: getVPad; Get vertical padding for a thumbnail; Generally this is the total height minus how high the thumb is.
		if (html_h == -1) html_h = xnde_h; // NOTE: html_h will be -1 on 1st pass; set to dflt_h, else will end up with 115px for hdump; PAGE:en.w:National_Gallery_of_Art  DATE:2016-06-19
		return (this.Get_thumb_padding() + xnde_h - html_h) / 2;
	}
	@gplx.Virtual public int		Get_thumb_padding() {return 30;}	// REF.MW: getThumbPadding; How much padding such the thumb have between image and inner div that that contains the border. This is both for verical and horizontal padding. (However, it is cut in half in the vertical direction).
	@gplx.Virtual public int Get_thumb_div_width(int thm_w) {			// REF.MW: getThumbDivWidth; Get the width of the inner div that contains the thumbnail in question. This is the div with the class of "thumb".
		return itm_default_w + this.Get_thumb_padding();
	}
	@gplx.Virtual public void Get_thumb_size(Xop_lnki_tkn lnki, Xof_ext ext) { // REF.MW: getThumbParams; Get the transform parameters for a thumbnail.
		lnki.W_(itm_default_w);
		lnki.H_(itm_default_h);
	}
	@gplx.Virtual public void Get_modules(Xoae_page page) {			// REF.MW: getModules; Get a list of modules to include in the page.
		page.Html_data().Head_mgr().Itm__gallery_styles().Enabled_y_();	// enable styles or some galleries will show up as list items; PAGE:s.w:Gothic_architecture DATE:2015-11-06
	}
	@gplx.Virtual public void Init(int itm_default_w, int itm_default_h, int itms_per_row) {
		this.itm_default_w = itm_default_w;
		this.itm_default_h = itm_default_h;
		this.itms_per_row = itms_per_row;
	}
	@gplx.Virtual public void Adjust_image_parameters(Xof_file_itm xfer_itm) {}	// REF.MW: Adjust the image parameters for a thumbnail. Used by a subclass to insert extra high resolution images.		
	@gplx.Virtual public void Wrap_gallery_text(Bry_bfr bfr, byte[] gallery_text, int thm_w, int thm_h) {
		bfr	.Add_str_a7("\n      <div class=\"gallerytext\">")	// NOTE: The newline after <div class="gallerytext"> is needed to accommodate htmltidy
			.Add(gallery_text)
			.Add_str_a7("\n      </div>");						// NOTE: prepend "\n"; will cause extra \n when caption exists, but needed when caption doesn't exists; EX: "<div class='caption'>    </div>"; \n puts
	}
}
class Gallery_mgr_traditional extends Gallery_mgr_base {
	@Override public byte	Tid()		{return Gallery_mgr_base_.Tid__traditional;}		
}
class Gallery_mgr_nolines extends Gallery_mgr_base {
	@Override public byte	Tid()		{return Gallery_mgr_base_.Tid__nolines;}
	@Override public int Get_thumb_padding()				{return 0;}
	@Override public int Get_gb_padding()				{return 4;} // This accounts for extra space between <li> elements.
	@Override public int Get_vpad(int itm_h, int thm_h)	{return 0;}
}
