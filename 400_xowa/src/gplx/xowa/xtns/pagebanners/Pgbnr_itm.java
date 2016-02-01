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
import gplx.langs.mustaches.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.files.*;
public class Pgbnr_itm implements Mustache_doc_itm {
	public Xoa_ttl banner_ttl;
	public byte[] banner_img_src;
	private int html_uid;
	public byte[] toc;
	public Xof_file_itm banner_file_itm;
	private byte[] banner_anch_title, banner_hdr_text, originx, banner_anch_href, srcset;
	private double data_pos_x, data_pos_y;
	private int maxWidth;
	private boolean bottomtoc, isHeadingOverrideEnabled;
	private Pgbnr_icon[] icons;
	public void Init_from_wtxt(Xoa_ttl banner_ttl, Xof_file_itm banner_file_itm, byte[] banner_anch_title, byte[] banner_hdr_text, boolean bottomtoc, byte[] toc, double data_pos_x, double data_pos_y, byte[] originx, Pgbnr_icon[] icons) {
		this.banner_ttl = banner_ttl; this.banner_file_itm = banner_file_itm;
		this.banner_anch_title = banner_anch_title; this.banner_hdr_text = banner_hdr_text; this.bottomtoc = bottomtoc; this.toc = toc; this.icons = icons;
		this.data_pos_x = data_pos_x; this.data_pos_y = data_pos_y; this.originx = originx;
		this.html_uid = banner_file_itm.Html_uid();
		this.banner_img_src = banner_file_itm.Html_view_url().To_http_file_bry();
	}
	public void Init_from_html(int maxWidth, byte[] banner_anch_href, byte[] banner_img_src, byte[] srcset, boolean isHeadingOverrideEnabled, byte[] toc) {
		this.maxWidth = maxWidth;
		this.banner_anch_href = banner_anch_href;
		this.banner_img_src = banner_img_src;
		this.srcset = srcset;
		this.isHeadingOverrideEnabled = isHeadingOverrideEnabled;
		this.toc = toc;
	}
	public byte[] Get_prop(String key) {
		if		(String_.Eq(key, "title"))							return banner_hdr_text;
		else if	(String_.Eq(key, "tooltip"))						return banner_anch_title;
		else if	(String_.Eq(key, "bannerfile"))						return banner_anch_href;
		else if	(String_.Eq(key, "banner"))							return banner_img_src;
		else if	(String_.Eq(key, "html_uid"))						return Bry_.new_u8(gplx.xowa.htmls.Xoh_img_mgr.Str__html_uid + Int_.To_str(html_uid));
		else if	(String_.Eq(key, "srcset"))							return srcset == null ? Bry_.Empty : Bry_.Empty;
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
