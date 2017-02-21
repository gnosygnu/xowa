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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.mustaches.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.files.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*; import gplx.langs.htmls.encoders.*;
public class Pgbnr_itm implements Mustache_doc_itm {
	public Xoa_ttl banner_ttl;
	public byte[] banner_img_src;
	public byte[] toc;
	public Xof_file_itm banner_file_itm;
	private byte[] banner_anch_title, banner_hdr_text, originx, banner_anch_href, srcset;
	private double data_pos_x, data_pos_y;
	private int max_width;
	private boolean bottomtoc, isHeadingOverrideEnabled;
	private byte[] file_ttl;
	private Pgbnr_icon[] icons;
	private byte[] img_id_atr, img_xottl_atr, img_xoimg_atr;

	// NOTE: used by hdump
	public boolean Exists() {return exists;} private boolean exists;
	public byte[] Srcset() {return srcset;}
	public byte[] Style_if_not_dflt() {return Bry_.Eq(style, Atr_val__style_dflt) ? Bry_.Empty : style;}
	public byte[] Style() {return style;} private byte[] style;
	public double Data_pos_x() {return data_pos_x;}
	public double Data_pos_y() {return data_pos_y;}

	public void Clear_by_hdump() {
		this.exists = false;
		this.srcset = style = null;
		this.data_pos_x = this.data_pos_y = 0;
	}
	public void Init_by_parse(Gfh_tag tag) {
		this.exists = true;
		this.srcset		= tag.Atrs__get_as_bry(Atr_key__srcset);
		this.style		= tag.Atrs__get_as_bry(Gfh_atr_.Bry__style);
		this.data_pos_x = tag.Atrs__get_as_double_or(Atr_key__data_pos_x, 0);
		this.data_pos_y = tag.Atrs__get_as_double_or(Atr_key__data_pos_y, 0);
	}
	public void Init_by_decode(double data_pos_x, double data_pos_y, byte[] srcset, byte[] style_if_not_dflt) {
		this.data_pos_x = data_pos_x;
		this.data_pos_y = data_pos_y;
		this.srcset = srcset;
		this.style = Bry_.Eq(style_if_not_dflt, Bry_.Empty) ? Atr_val__style_dflt : style_if_not_dflt;
		this.exists = true;
	}
	public void Init_from_wtxt(Xoa_ttl banner_ttl, Xof_file_itm banner_file_itm, byte[] banner_anch_title, byte[] banner_hdr_text, boolean bottomtoc
		, byte[] toc, double data_pos_x, double data_pos_y, byte[] originx, Pgbnr_icon[] icons) {
		this.banner_ttl = banner_ttl; this.banner_file_itm = banner_file_itm;
		this.banner_anch_title = banner_anch_title; this.banner_hdr_text = banner_hdr_text; this.bottomtoc = bottomtoc; this.toc = toc; this.icons = icons;
		this.data_pos_x = data_pos_x; this.data_pos_y = data_pos_y; this.originx = originx;
		this.banner_img_src = banner_file_itm.Html_view_url().To_http_file_bry();
		this.file_ttl = Gfo_url_encoder_.Href_quotes.Encode(banner_file_itm.Lnki_ttl());	// NOTE: Encode(Lnki_ttl) not Orig_ttl; else "%27s" instead of "'s" PAGE:en.v:'s-Hertogenbosch; DATE:2016-07-12
	}
	public void Init_from_html(int max_width, byte[] banner_anch_href, byte[] banner_img_src, byte[] srcset, boolean isHeadingOverrideEnabled, byte[] toc) {
		this.max_width = max_width;
		this.banner_anch_href = banner_anch_href;
		this.banner_img_src = banner_img_src;
		this.srcset = srcset;
		this.isHeadingOverrideEnabled = isHeadingOverrideEnabled;
		this.toc = toc;
	}
	public void Init_hdump(boolean mode_is_hdump) {
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		if (mode_is_hdump) {
			this.img_id_atr = Bry_.Empty;
			this.img_xottl_atr = Gfh_atr_.Add_to_bry(tmp_bfr, Xoh_img_xoimg_data.Bry__data_xowa_title, file_ttl);
			this.img_xoimg_atr = Gfh_atr_.Add_to_bry(tmp_bfr, Xoh_img_xoimg_data.Bry__data_xowa_image, Xoh_img_xoimg_data.Bry__data_xowa_image__full);
			this.banner_img_src = Bry_.Empty;	// do not write img_src else hzip_diff will complain for every image; DATE:2016-10-18
		}
		else {
			this.img_id_atr = tmp_bfr.Add(Bry__anch_atr_id).Add_int_variable(banner_file_itm.Html_uid()).Add_byte_quote().To_bry_and_clear();
			this.img_xottl_atr = this.img_xoimg_atr = Bry_.Empty;
		}
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "title"))							bfr.Add_bry(banner_hdr_text);
		else if	(String_.Eq(key, "tooltip"))						bfr.Add_bry(banner_anch_title);
		else if	(String_.Eq(key, "bannerfile"))						bfr.Add_bry(banner_anch_href);
		else if	(String_.Eq(key, "banner"))							bfr.Add_bry(banner_img_src);
		else if	(String_.Eq(key, "srcset"))							bfr.Add_bry(srcset == null ? Bry_.Empty : Bry_.Empty);
		else if	(String_.Eq(key, "originx"))						bfr.Add_bry(originx);
		else if	(String_.Eq(key, "data-pos-x"))						bfr.Add_double(data_pos_x);
		else if	(String_.Eq(key, "data-pos-y"))						bfr.Add_double(data_pos_y);
		else if	(String_.Eq(key, "maxWidth"))						bfr.Add_int(max_width);
		else if	(String_.Eq(key, "toc"))							bfr.Add_bry(toc);
		else if	(String_.Eq(key, "img_id_atr"))						bfr.Add_bry(img_id_atr);
		else if	(String_.Eq(key, "img_xottl"))						bfr.Add_bry(img_xottl_atr);
		else if	(String_.Eq(key, "img_xoimg"))						bfr.Add_bry(img_xoimg_atr);
		else if	(String_.Eq(key, "file_ttl"))						bfr.Add_bry(file_ttl);
		else														return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "icons"))							return icons;
		else if	(String_.Eq(key, "hasIcons"))						return Mustache_doc_itm_.Ary__bool(icons.length > 0);
		else if	(String_.Eq(key, "bottomtoc"))						return Mustache_doc_itm_.Ary__bool(bottomtoc);
		else if	(String_.Eq(key, "isHeadingOverrideEnabled"))		return Mustache_doc_itm_.Ary__bool(isHeadingOverrideEnabled);
		return Mustache_doc_itm_.Ary__empty;
	}
	private static final    byte[] Bry__anch_atr_id = Bry_.new_a7(" id=\"xoimg_");
	public static final    byte[] 
	  Atr_key__srcset = Bry_.new_a7("srcset")
	, Atr_key__data_pos_x = Bry_.new_a7("data-pos-x")
	, Atr_key__data_pos_y = Bry_.new_a7("data-pos-y")
	, Atr_val__style_dflt = Bry_.new_a7("max-width:-1px")
	;
}
