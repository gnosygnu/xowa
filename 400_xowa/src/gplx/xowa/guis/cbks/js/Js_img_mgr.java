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
package gplx.xowa.guis.cbks.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.cbks.*;
import gplx.xowa.xtns.gallery.*;
import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.xowa.guis.views.*; import gplx.xowa.parsers.lnkis.*;
public class Js_img_mgr {
	public static void Update_link_missing(Xog_js_wkr html_itm, String html_id) {
		html_itm.Html_redlink(html_id);
	}
	public static void Update_img(Xoa_page page, Xog_js_wkr js_wkr, Xof_file_itm itm) {
		Js_img_mgr.Update_img(page, js_wkr, itm.Html_img_wkr(), itm.Html_uid(), itm.Lnki_type(), itm.Html_elem_tid(), itm.Html_w(), itm.Html_h(), itm.Html_view_url()
			, itm.Orig_w(), itm.Orig_h(), itm.Orig_ext(), itm.Html_orig_url(), itm.Orig_ttl(), itm.Html_gallery_mgr_h());
	}
	private static void Update_img(Xoa_page page, Xog_js_wkr js_wkr, Js_img_wkr img_wkr, int uid, byte lnki_type, byte elem_tid, int html_w, int html_h, Io_url html_view_url
		, int orig_w, int orig_h, Xof_ext orig_ext, Io_url html_orig_url, byte[] lnki_ttl, int gallery_mgr_h) {
		if (!page.Wiki().App().Mode().Tid_supports_js()) return;	// do not update html widget unless app is gui; null ref on http server; DATE:2014-09-17
		switch (elem_tid) {
			case Xof_html_elem.Tid_gallery_v2:
				img_wkr.Js_wkr__update_hdoc(page, js_wkr, uid, html_w, html_h, html_view_url, orig_w, orig_h, orig_ext, html_orig_url, lnki_ttl);
				return;
		}
		String html_id = To_doc_uid(uid);
		js_wkr.Html_img_update(html_id, html_view_url.To_http_file_str(), html_w, html_h);
		if (Xop_lnki_type.Id_is_thumbable(lnki_type)) {	// thumb needs to set cls and width
			js_wkr.Html_atr_set(html_id, "class", gplx.xowa.htmls.core.wkrs.imgs.atrs.Xoh_img_cls_.Str__thumbimage);
			js_wkr.Html_atr_set("xowa_file_div_" + uid, "style", "width:" + html_w + "px;");
		}
		switch (elem_tid) {
			case Xof_html_elem.Tid_gallery:
				js_wkr.Html_atr_set("xowa_gallery_div3_" + uid, "style", "margin:" + Gallery_mgr_wtr_.Calc_vpad(gallery_mgr_h, html_h) + "px auto;");					
				break;
			case Xof_html_elem.Tid_imap:
				img_wkr.Js_wkr__update_hdoc(page, js_wkr, uid, html_w, html_h, html_view_url, orig_w, orig_h, orig_ext, html_orig_url, lnki_ttl);
				break;
			case Xof_html_elem.Tid_vid:
				String html_id_vid = "xowa_file_play_" + uid;
				js_wkr.Html_atr_set(html_id_vid, "style", "width:" + html_w + "px;max-width:" + (html_w - 2) + "px;");
				js_wkr.Html_atr_set(html_id_vid, "href", html_orig_url.To_http_file_str());
				break;
		}
	}
	public static String To_doc_uid(int html_uid) {return gplx.xowa.htmls.Xoh_img_mgr.Str__html_uid + Int_.To_str(html_uid);}
}
