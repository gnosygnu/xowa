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
