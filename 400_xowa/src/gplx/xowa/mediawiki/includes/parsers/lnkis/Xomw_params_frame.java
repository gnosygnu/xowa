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
package gplx.xowa.mediawiki.includes.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_params_frame {
	public byte[] align = null;
	public byte[] valign = null;
	public byte[] caption = null;
	public byte[] frame = null;
	public byte[] framed = null;
	public byte[] frameless = null;
	public byte[] thumbnail = null;
	public byte[] manualthumb = null;
	public byte[] alt = null;
	public byte[] title = null;
	public byte[] cls = null;
	public byte[] img_cls = null;
	public byte[] link_title = null;
	public byte[] link_url = null;
	public byte[] link_target = null;
	public byte[] no_link = null;
	public byte[] border = null;
	public byte[] custom_url_link = null;
	public byte[] custom_target_link = null;
	public boolean desc_link = false;
	public byte[] desc_query = null;
	public double upright;
	public void Set(int uid, byte[] val_bry, int val_int) {
		switch (uid) {
			case Xomw_param_itm.Name__thumbnail: thumbnail = val_bry; break;
		}
	}
	public Xomw_params_frame Clear() {
		desc_link = false;
		upright = XophpUtility.NULL_DOUBLE;
		align = valign = caption = frame = framed = frameless
		= thumbnail = manualthumb = alt = title = cls = img_cls
		= link_title = link_url = link_target = no_link 
		= custom_url_link = custom_target_link = desc_query
		= XophpUtility.NULL_BRY;
		return this;
	}
	public void Copy_to(Xomw_params_frame src) {
		this.desc_link = src.desc_link;
		this.upright = src.upright;
		this.align = src.align;
		this.valign = src.valign;
		this.caption = src.caption;
		this.frame = src.frame;
		this.framed = src.framed;
		this.frameless = src.frameless;
		this.thumbnail = src.thumbnail;
		this.manualthumb = src.manualthumb;
		this.alt = src.alt;
		this.title = src.title;
		this.cls = src.cls;
		this.img_cls = src.img_cls;
		this.link_title = src.link_title;
		this.link_url = src.link_url;
		this.link_target = src.link_target;
		this.no_link = src.no_link;
		this.border = src.border;
		this.custom_url_link = src.custom_url_link;
		this.custom_target_link = src.custom_target_link;
		this.desc_query = src.desc_query;
	}
	public static byte[] Cls_add(byte[] lhs, byte[] rhs) {
		return Bry_.Len_eq_0(lhs) ? rhs : Bry_.Add(lhs, Byte_ascii.Space_bry, rhs);
	}
}
