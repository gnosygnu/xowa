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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.mustaches.*;
public class Pgbnr_itm implements Mustache_doc_itm {
	public byte[] name, toc;
	private byte[] tooltip, title, originx, bannerfile, banner, srcset;
	private double data_pos_x, data_pos_y;
	private int maxWidth;
	private boolean bottomtoc, isHeadingOverrideEnabled;
	private Pgbnr_icon[] icons;
	public void Init_from_wtxt(byte[] name, byte[] tooltip, byte[] title, boolean bottomtoc, byte[] toc, double data_pos_x, double data_pos_y, byte[] originx, Pgbnr_icon[] icons) {
		this.name = name;
		this.tooltip = tooltip; this.title = title; this.bottomtoc = bottomtoc; this.toc = toc; this.icons = icons;
		this.data_pos_x = data_pos_x; this.data_pos_y = data_pos_y; this.originx = originx;
	}
	public void Init_from_html(int maxWidth, byte[] bannerfile, byte[] banner, byte[] srcset, boolean isHeadingOverrideEnabled) {
		this.maxWidth = maxWidth;
		this.bannerfile = bannerfile;
		this.banner = banner;
		this.srcset = srcset;
		this.isHeadingOverrideEnabled = isHeadingOverrideEnabled;
	}
	public byte[] Get_prop(String key) {
		if		(String_.Eq(key, "title"))							return title;
		else if	(String_.Eq(key, "tooltip"))						return tooltip;
		else if	(String_.Eq(key, "bannerfile"))						return bannerfile;
		else if	(String_.Eq(key, "banner"))							return banner;
		else if	(String_.Eq(key, "srcset"))							return srcset;
		else if	(String_.Eq(key, "originx"))						return originx;
		else if	(String_.Eq(key, "data-pos-x"))						return Bry_.new_a7(Double_.To_str(data_pos_x));
		else if	(String_.Eq(key, "data-pos-y"))						return Bry_.new_a7(Double_.To_str(data_pos_y));
		else if	(String_.Eq(key, "maxWidth"))						return Int_.To_bry(maxWidth);
		else if	(String_.Eq(key, "toc"))							return toc;
		return Mustache_doc_itm_.Null_val;
	}
	public Mustache_doc_itm[] Get_subs(String key) {
		if		(String_.Eq(key, "icons"))							return icons;
		else if	(String_.Eq(key, "hasIcons"))						return Mustache_doc_itm_.Ary__bool(icons.length > 0);
		else if	(String_.Eq(key, "bottomtoc"))						return Mustache_doc_itm_.Ary__bool(bottomtoc);
		else if	(String_.Eq(key, "isHeadingOverrideEnabled"))		return Mustache_doc_itm_.Ary__bool(isHeadingOverrideEnabled);
		return Mustache_doc_itm_.Ary__empty;
	}
}
