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
public class Xog_js_wkr_ {
	public static final    Xog_js_wkr Noop = new Xog_js_wkr__noop();
}
class Xog_js_wkr__noop implements Xog_js_wkr {
	public void Html_img_update			(String uid, String src, int w, int h) {}
	public void Html_atr_set			(String uid, String key, String val) {}
	public void Html_elem_replace_html	(String uid, String html) {}
	public void Html_elem_append_above	(String uid, String html) {}
	public void Html_redlink			(String html_uid) {}
	public void Html_elem_delete		(String elem_id) {}
	public void Html_gallery_packed_exec() {}
	public void Html_popups_bind_hover_to_doc() {}
}
