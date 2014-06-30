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
public interface Xoh_module_itm {
	byte[] Key();
	void Write_css_include(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr);
	void Write_css_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr);
	void Write_js_include(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr);
	void Write_js_head_global(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr);
	void Write_js_head_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr);
	void Write_js_tail_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr);
	void Clear();
}
/*
Position	// top, bottom
Targets		// mobile, desktop
Dependencies
Messages
*/
