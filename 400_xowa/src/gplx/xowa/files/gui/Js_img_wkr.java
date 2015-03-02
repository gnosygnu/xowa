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
import gplx.xowa.gui.views.*;
public interface Js_img_wkr {
	void Html_update(Xoa_page page, Xog_js_wkr js_wkr, int html_uid, int html_w, int html_h, String html_src, int orig_w, int orig_h, String orig_src, byte[] lnki_ttl);
}
