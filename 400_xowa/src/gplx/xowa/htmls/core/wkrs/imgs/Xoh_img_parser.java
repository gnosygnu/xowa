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
import gplx.core.brys.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.files.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
public class Xoh_img_parser {		
	private byte[] src;
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	public int Anch_tag_bgn() {return anch_tag_bgn;} private int anch_tag_bgn;
	public int Anch_tag_end() {return anch_tag_end;} private int anch_tag_end;
	public Xoh_anch_href_parser Anch_href_parser() {return anch_href_parser;} private Xoh_anch_href_parser anch_href_parser = new Xoh_anch_href_parser();
	public Xoh_anch_cls_parser Anch_cls_parser() {return anch_cls_parser;} private Xoh_anch_cls_parser anch_cls_parser = new Xoh_anch_cls_parser();
	public Xoh_img_src_parser Img_src_parser() {return img_src_parser;} private final Xoh_img_src_parser img_src_parser = new Xoh_img_src_parser();
	public Xoh_img_cls_parser Img_cls_parser() {return img_cls_parser;} private final Xoh_img_cls_parser img_cls_parser = new Xoh_img_cls_parser();
	public Xoh_img_xoimg_parser Img_xoimg_parser() {return img_xoimg_parser;} private Xoh_img_xoimg_parser img_xoimg_parser = new Xoh_img_xoimg_parser();
	public Html_atr Anch_title_atr() {return anch_title_atr;} private Html_atr anch_title_atr;
	public Html_atr Img_alt_atr() {return img_alt_atr;} private Html_atr img_alt_atr;
	public boolean Img_alt__diff__anch_title() {return !Bry_.Match(src, img_alt_atr.Val_bgn(), img_alt_atr.Val_end(), src, anch_title_atr.Val_bgn(), anch_title_atr.Val_end());}
	public int Img_w() {return img_w;} private int img_w;
	public boolean Img_w_exists() {return img_w != -1;}
	public int Img_h() {return img_h;} private int img_h;
	public boolean File_w__diff__img_w() {return img_src_parser.File_w() != img_w;}
	public int Parse(Xoh_hdoc_wkr hdoc_wkr, byte[] src, Html_tag_rdr tag_rdr, Html_tag anch_tag) {
		// "<a href='/wiki/File:A.png' class='image'><img alt='' src='file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/1/2/A.png/220px.png' width='220' height='110' class='thumbimage'></a>"
		this.src = src; this.anch_tag_bgn = anch_tag.Src_bgn(); this.anch_tag_end = anch_tag.Src_end();
		this.rng_bgn = anch_tag_bgn;																	// <a
		this.anch_title_atr = anch_tag.Atrs__get_by_or_empty(Html_atr_.Bry__title);						// title='abc'
		anch_cls_parser.Parse(tag_rdr.Rdr(), src, anch_tag);											// class='image'
		anch_href_parser.Parse(tag_rdr.Rdr(), hdoc_wkr.Ctx().Wiki(), anch_tag);							// href='/wiki/File:A.png'
		Html_tag img_tag = tag_rdr.Tag__move_fwd_head(Html_tag_.Id__img);								// <img>
		img_xoimg_parser.Parse(tag_rdr.Rdr(), src, img_tag);
		this.img_w = img_tag.Atrs__get_as_int_or(Html_atr_.Bry__width, Xof_img_size.Size__neg1);		// width='220'
		this.img_h = img_tag.Atrs__get_as_int_or(Html_atr_.Bry__height, Xof_img_size.Size__neg1);		// height='110'
		this.img_alt_atr = img_tag.Atrs__get_by_or_empty(Html_atr_.Bry__alt);							// alt='File:A.png'
		img_cls_parser.Parse(tag_rdr.Rdr(), src, img_tag);												// class='thumbborder'
		img_src_parser.Parse(tag_rdr.Rdr(), hdoc_wkr.Ctx().Wiki().Domain_bry(), img_tag);				// src='...'
		Html_tag anch_tail_tag = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a);							// </a>
		this.rng_end = anch_tail_tag.Src_end();
		hdoc_wkr.On_img(this);
		return rng_end;
	}
	public static final byte[]
	  Bry__cls__anch__image			= Bry_.new_a7("image")
	, Bry__cls__img__thumbimage		= Bry_.new_a7("thumbimage")
	;
}
