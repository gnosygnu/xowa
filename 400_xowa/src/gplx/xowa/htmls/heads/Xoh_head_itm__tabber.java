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
public class Xoh_head_itm__tabber extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__tabber;}
	@Override public int Flags() {return Flag__css_include | Flag__js_tail_script;}
	@Override public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url__css == null) {
			Io_url tabber_dir = app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "xtns", "wikia", "Tabber");
			Url__js			= tabber_dir.GenSubFil_nest("js" , "tabber.js").To_http_file_bry();
			Url__css		= tabber_dir.GenSubFil_nest("css", "tabber.css").To_http_file_bry();
		}
		wtr.Write_css_include(Url__css);
	}
	@Override public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_tail_load_lib(Url__js);
	}
	private static byte[] Url__css, Url__js;
}
