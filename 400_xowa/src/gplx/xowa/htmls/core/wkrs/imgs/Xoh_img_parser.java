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
import gplx.xowa.wikis.domains.*;
import gplx.xowa.files.*;
public class Xoh_img_parser {
	private byte[] src;
	public int						Src_bgn() {return src_bgn;} private int src_bgn;
	public int						Src_end() {return src_end;} private int src_end;
	public Xoh_anch_href_itm		Anch_href() {return anch_href;} private Xoh_anch_href_itm anch_href = new Xoh_anch_href_itm();
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
	public boolean Rel_nofollow_exists() {
		if (anch_href.Site_exists()) {
			if (rel_nofollow_exists == Bool_.__byte) {
				Xow_domain_itm itm = Xow_domain_itm_.parse(Bry_.Mid(src, anch_href.Site_bgn(), anch_href.Site_end()));
				rel_nofollow_exists = itm.Domain_type_id() == Xow_domain_tid_.Int__other ? Bool_.Y_byte : Bool_.N_byte;
			}
			return rel_nofollow_exists == Bool_.Y_byte;
		}
		else
			return false;
	}	private byte rel_nofollow_exists;
	private void Clear() {
		this.rel_nofollow_exists = Bool_.__byte;
		this.src_bgn = src_end = img_w = img_h = -1;
		this.anch_title = this.img_alt = Html_atr.Noop;
	}
	public boolean Parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, byte[] src, Html_tag_rdr tag_rdr, Html_tag anch_head) {
		this.Clear();
		this.src = src; Bry_err_wkr err_wkr = tag_rdr.Err_wkr();
		this.src_bgn = anch_head.Src_bgn();																// <a
		if (!anch_href.Parse(err_wkr, hctx, anch_head)) return false;									// href='/wiki/File:A.png'
		if (!anch_cls.Parse(err_wkr, src, anch_head)) return false;										// class='image'
		this.anch_title = anch_head.Atrs__get_by_or_empty(Html_atr_.Bry__title);						// title='abc'
		Html_atr xowa_title = anch_head.Atrs__get_by_or_empty(Bry__atr__xowa_title);					// xowa_title='A.png'
		if (xowa_title.Val_dat_exists()) anch_page.Val_(xowa_title.Val());
		Html_tag img_tag = tag_rdr.Tag__move_fwd_head().Chk_id(Html_tag_.Id__img);						// <img
		img_xoimg_parser.Parse(err_wkr, src, img_tag);													// data-xoimg='...'
		this.img_w = img_tag.Atrs__get_as_int_or(Html_atr_.Bry__width, Xof_img_size.Size__neg1);		// width='220'
		this.img_h = img_tag.Atrs__get_as_int_or(Html_atr_.Bry__height, Xof_img_size.Size__neg1);		// height='110'
		this.img_alt = img_tag.Atrs__get_by_or_empty(Html_atr_.Bry__alt);								// alt='File:A.png'
		img_cls.Parse(err_wkr, src, img_tag);															// class='thumbborder'
		if (!img_src.Parse(err_wkr, hctx.Wiki__domain_bry(), img_tag)) return false;					// src='...'
		if (anch_page.Val_is_empty()) {
			anch_page.Val_(img_src.File_ttl_bry());
			if (anch_page.Val_is_empty())
				anch_page.Val_(anch_href.Ttl_page_db());
		}
		Html_tag anch_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a);								// </a>
		this.src_end = anch_tail.Src_end();
		return true;
	}
	public static final byte[]
	  Bry__cls__anch__image			= Bry_.new_a7("image")
	, Bry__cls__img__thumbimage		= Bry_.new_a7("thumbimage")
	, Bry__atr__xowa_title			= Bry_.new_a7("xowa_title")
	;
}
