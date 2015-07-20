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
package gplx.xowa.html.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public abstract class Xoh_head_itm__base {
	public abstract byte[] Key();
	public abstract int Flags();
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} public void Enabled_y_() {this.Enabled_(Bool_.Y);} public void Enabled_n_() {this.Enabled_(Bool_.N);} private boolean enabled;
	@gplx.Virtual public void Clear() {this.Enabled_(Bool_.N);}
	@gplx.Virtual public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {}
	@gplx.Virtual public void Write_css_text(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {}
	@gplx.Virtual public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {}
	@gplx.Virtual public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {}
	@gplx.Virtual public void Write_js_head_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {}
	@gplx.Virtual public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {}
	@gplx.Virtual public void Write_js_window_onload(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {}
	public static final int
	  Flag__disabled			= 0
	, Flag__css_include			= 1
	, Flag__css_text			= 2
	, Flag__js_include			= 4
	, Flag__js_head_global		= 8
	, Flag__js_head_script		= 16
	, Flag__js_tail_script		= 32
	, Flag__js_window_onload	= 64
	;
	public static final int Idx__max	= 7;
}
/*
Position	// top, bottom
Targets		// mobile, desktop
Dependencies
Messages
*/
