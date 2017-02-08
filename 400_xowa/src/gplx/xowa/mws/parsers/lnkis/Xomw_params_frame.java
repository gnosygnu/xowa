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
package gplx.xowa.mws.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
public class Xomw_params_frame {
	public byte[] align = null;
	public byte[] valign = null;
	public byte[] caption = null;
	public byte[] frame = null;
	public byte[] framed = null;
	public byte[] frameless = null;
	public byte[] thumbnail = null;
	public byte[] manual_thumb = null;
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
	public double upright = -1;
	public void Set(int uid, byte[] val_bry, int val_int) {
		switch (uid) {
			case Xomw_param_itm.Name__thumbnail: thumbnail = val_bry; break;
		}
	}
	public Xomw_params_frame Clear() {
		desc_link = false;
		upright = -1;
		align = valign = caption = frame = framed = frameless
		= thumbnail = manual_thumb = alt = title = cls = img_cls
		= link_title = link_url = link_target = no_link 
		= custom_url_link = custom_target_link = desc_query
		= null;
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
		this.manual_thumb = src.manual_thumb;
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
