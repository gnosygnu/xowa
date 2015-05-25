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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
public class Xoa_shell implements GfoInvkAble {
	public Xoa_shell(Xoae_app app) {this.app = app;} private Xoae_app app;
	public boolean Fetch_page_exec_async() {return fetch_page_exec_async;} private boolean fetch_page_exec_async = true;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_fetch_page))				return Fetch_page(m);
		else if	(ctx.Match(k, Invk_chars_per_line_max_))	ConsoleAdp._.CharsPerLineMax_set(m.ReadInt("v"));
		else if	(ctx.Match(k, Invk_backspace_by_bytes_))	ConsoleAdp._.Backspace_by_bytes_(m.ReadYn("v"));
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private String Fetch_page(GfoMsg m) {
		return String_.new_u8(app.Gui_mgr().Browser_win().App__retrieve_by_url(m.ReadStr("url"), m.ReadStrOr("output_type", "html")));
	}
	private static final String Invk_fetch_page = "fetch_page"
	, Invk_chars_per_line_max_ = "chars_per_line_max_"
	, Invk_backspace_by_bytes_ = "backspace_by_bytes_"
	;
}
