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
import gplx.core.primitives.*; import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.files.*;
public class Xoh_img_parser {		
	private byte[] src;
	public int						Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int						Rng_end() {return rng_end;} private int rng_end;
	public Xoh_anch_href_parser		Anch_href() {return anch_href;} private Xoh_anch_href_parser anch_href = new Xoh_anch_href_parser();
	public Xoh_anch_cls_parser		Anch_cls() {return anch_cls;} private Xoh_anch_cls_parser anch_cls = new Xoh_anch_cls_parser();
	public Html_atr					Anch_title() {return anch_title;} private Html_atr anch_title;
	public Bry_obj_ref				Anch_page() {return anch_page;} private Bry_obj_ref anch_page = Bry_obj_ref.New_empty();
	public Xoh_img_src_parser		Img_src() {return img_src;} private final Xoh_img_src_parser img_src = new Xoh_img_src_parser();
	public Xoh_img_cls_parser		Img_cls() {return img_cls;} private final Xoh_img_cls_parser img_cls = new Xoh_img_cls_parser();
	public Xoh_img_xoimg_parser		Img_xoimg() {return img_xoimg_parser;} private Xoh_img_xoimg_parser img_xoimg_parser = new Xoh_img_xoimg_parser();
	public Html_atr					Img_alt() {return img_alt;} private Html_atr img_alt;
	public boolean						Img_alt__diff__anch_title() {return !Bry_.Match(src, img_alt.Val_bgn(), img_alt.Val_end(), src, anch_title.Val_bgn(), anch_title.Val_end());}
	public int						Img_w() {return img_w;} private int img_w;
	public int						Img_h() {return img_h;} private int img_h;
	public boolean						Img_w__diff__file_w() {return img_w != img_src.File_w();}
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Html_tag_rdr tag_rdr, Html_tag anch_head) {
		this.src = src; Bry_rdr rdr = tag_rdr.Rdr();
		this.rng_bgn = anch_head.Src_bgn();																		// <a
		if (!anch_href.Parse(rdr, hctx.App(), hctx.Wiki__ttl_parser(), anch_head)) return Xoh_hdoc_ctx.Invalid;	// href='/wiki/File:A.png'
		if (!anch_cls.Parse(rdr, src, anch_head)) return Xoh_hdoc_ctx.Invalid;									// class='image'
		this.anch_title = anch_head.Atrs__get_by_or_empty(Html_atr_.Bry__title);								// title='abc'
		Html_atr xowa_title = anch_head.Atrs__get_by_or_empty(Bry__atr__xowa_title);							// xowa_title='A.png'
		if (xowa_title.Val_dat_exists()) anch_page.Val_(xowa_title.Val());
		Html_tag img_tag = tag_rdr.Tag__move_fwd_head().Chk_id(Html_tag_.Id__img);								// <img
		img_xoimg_parser.Parse(rdr, src, img_tag);																// data-xoimg='...'
		this.img_w = img_tag.Atrs__get_as_int_or(Html_atr_.Bry__width, Xof_img_size.Size__neg1);				// width='220'
		this.img_h = img_tag.Atrs__get_as_int_or(Html_atr_.Bry__height, Xof_img_size.Size__neg1);				// height='110'
		this.img_alt = img_tag.Atrs__get_by_or_empty(Html_atr_.Bry__alt);										// alt='File:A.png'
		img_cls.Parse(rdr, src, img_tag);																		// class='thumbborder'
		if (!img_src.Parse(rdr, hctx.Wiki__domain_bry(), img_tag)) return Xoh_hdoc_ctx.Invalid;					// src='...'
		if (anch_page.Val_is_empty()) {
			anch_page.Val_(img_src.File_ttl_bry());
			if (anch_page.Val_is_empty())
				anch_page.Val_(anch_href.Page_ttl().Page_db());
		}
		Html_tag anch_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a);										// </a>
		this.rng_end = anch_tail.Src_end();
		return rng_end;
	}
	public static final byte[]
	  Bry__cls__anch__image			= Bry_.new_a7("image")
	, Bry__cls__img__thumbimage		= Bry_.new_a7("thumbimage")
	, Bry__atr__xowa_title			= Bry_.new_a7("xowa_title")
	;
}
