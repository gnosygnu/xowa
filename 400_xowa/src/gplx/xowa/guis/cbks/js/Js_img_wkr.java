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
import gplx.xowa.files.*;
public interface Js_img_wkr {
	void Js_wkr__update_hdoc(Xoa_page page, Xog_js_wkr js_wkr, int html_uid, int html_w, int html_h, Io_url html_view_url
		, int orig_w, int orig_h, Xof_ext orig_ext, Io_url html_orig_url, byte[] lnki_ttl);
}
