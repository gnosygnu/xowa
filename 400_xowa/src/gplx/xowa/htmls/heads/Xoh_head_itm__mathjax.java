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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.guis.*;
public class Xoh_head_itm__mathjax extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__mathjax;}
	@Override public int Flags() {return Flag__js_include;}
	@Override public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url_mathjax == null) Url_mathjax = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "xtns", "Math", "modules", "mathjax", "xowa_mathjax.js").To_http_file_bry();
		wtr.Write_js_include(Url_mathjax);
	}
	private static byte[] Url_mathjax;
}
