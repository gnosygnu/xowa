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
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.gui.*;
public class Xoh_module_itm__timeline implements Xoh_module_itm {
	public byte[] Key() {return Xoh_module_itm_.Key_timeline;} 
	public boolean Enabled() {return enabled;} public void Enabled_y_() {enabled = true;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = false;
	public void Clear() {enabled = false;}
	public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		if (Url_js  == null) Url_js = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "src", "xowa", "timeline", "timeline.js").To_http_file_bry();
		wtr.Write_js_include(Url_js);
	}
	public void Write_css_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_head_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	private static byte[] Url_js;
}
