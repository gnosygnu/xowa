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
public class Xoh_module_itm__collapsible implements Xoh_module_itm {
	public byte[] Key() {return Xoh_module_itm_.Key_collapsible;}
	public boolean Enabled() {return enabled;} public void Enabled_y_() {enabled = true;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled;
	public void Clear() {enabled = false;}
	public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_css_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_head_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {}
	public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		wtr.Write_js_global_ini_atr_val(Key_enabled		, true);
		wtr.Write_js_global_ini_atr_val(Key_collapsed	, app.Api_root().Html().Modules().Collapsible().Collapsed());
		wtr.Write_js_global_ini_atr_msg(wiki			, Key_collapse);
		wtr.Write_js_global_ini_atr_msg(wiki			, Key_expand);
	}
	private static final byte[]
	  Key_enabled				= Bry_.new_ascii_("collapsible-enabled")
	, Key_collapsed				= Bry_.new_ascii_("collapsible-collapsed")
	, Key_collapse				= Bry_.new_ascii_("collapsible-collapse")
	, Key_expand				= Bry_.new_ascii_("collapsible-expand")
	;
}
