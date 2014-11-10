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
package gplx.xowa.files.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.xtns.gallery.*;
import gplx.xowa.files.fsdb.*; import gplx.xowa.gui.views.*;
public class Js_img_mgr {
	public static void Update_img(Xoa_page page, Xof_xfer_itm itm) {
		Js_img_mgr.Update_img(page, itm.Html_uid(), itm.Lnki_type(), itm.Html_elem_tid(), itm.Html_w(), itm.Html_h(), String_.new_utf8_(itm.Html_view_src()), itm.Orig_w(), itm.Orig_h(), String_.new_utf8_(itm.Html_orig_src()), itm.Lnki_ttl(), itm.Gallery_mgr_h(), itm.Html_img_wkr());
	}
	public static void Update_img(Xoa_page page, Xof_fsdb_itm itm) {
		Js_img_mgr.Update_img(page, itm.Html_uid(), itm.Lnki_type(), itm.Html_elem_tid(), itm.Html_w(), itm.Html_h(), itm.Html_url().To_http_file_str(), itm.Orig_w(), itm.Orig_h(), itm.Html_orig_url().To_http_file_str(), itm.Lnki_ttl(), itm.Gallery_mgr_h(), itm.Html_img_wkr());
	}
	public static void Update_link_missing(Xog_html_itm html_itm, String html_id) {
		html_itm.Html_elem_atr_set_append(html_id, "class", " new");
	}
	private static void Update_img(Xoa_page page, int uid, byte lnki_type, byte elem_tid, int html_w, int html_h, String html_src, int orig_w, int orig_h, String orig_src, byte[] lnki_ttl, int gallery_mgr_h, Js_img_wkr img_wkr) {
		if (Env_.Mode_testing()) return;
		if (page.App().Mode() != Xoa_app_.Mode_gui) return;	// do not update html widget unless app is gui; null ref on http server; DATE:2014-09-17
		Xog_html_itm html_itm = page.Tab().Html_itm();
		switch (elem_tid) {
			case Xof_html_elem.Tid_gallery_v2:
				img_wkr.Html_update(page, html_itm, uid, html_w, html_h, html_src, orig_w, orig_h, orig_src, lnki_ttl);
				return;
		}
		String html_id = "xowa_file_img_" + uid;
		html_itm.Html_img_update(html_id, html_src, html_w, html_h);
		if (Xop_lnki_type.Id_is_thumbable(lnki_type)) {	// thumb needs to set cls and width
			html_itm.Html_atr_set(html_id, "class", gplx.xowa.html.lnkis.Xoh_lnki_consts.Str_img_cls_thumbimage);
			html_itm.Html_atr_set("xowa_file_div_" + uid, "style", "width:" + html_w + "px;");
		}
		switch (elem_tid) {
			case Xof_html_elem.Tid_gallery:
				html_itm.Html_atr_set("xowa_gallery_div3_" + uid, "style", "margin:" + Gallery_html_wtr_utl.Calc_vpad(gallery_mgr_h, html_h) + "px auto;");					
				break;
			case Xof_html_elem.Tid_imap:
				img_wkr.Html_update(page, html_itm, uid, html_w, html_h, html_src, orig_w, orig_h, orig_src, lnki_ttl);
				break;
			case Xof_html_elem.Tid_vid:
				String html_id_vid = "xowa_file_play_" + uid;
				html_itm.Html_atr_set(html_id_vid, "style", "width:" + html_w + "px;max-width:" + (html_w - 2) + "px;");
				html_itm.Html_atr_set(html_id_vid, "href", orig_src);
				break;
		}
	}
}
