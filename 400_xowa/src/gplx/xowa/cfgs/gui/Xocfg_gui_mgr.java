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
package gplx.xowa.cfgs.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.cfgs.*;
public class Xocfg_gui_mgr implements GfoInvkAble {
	public Xocfg_gui_mgr(Xoa_app app) {
		win_cfg = new Xocfg_win(app);
	}
	public Xocfg_win Win() {return win_cfg;} private Xocfg_win win_cfg;
	public Xocfg_html Html() {return html_cfg;} private Xocfg_html html_cfg = new Xocfg_html();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_win))			return win_cfg;
		else if	(ctx.Match(k, Invk_html))			return html_cfg;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_win = "win", Invk_html = "html";
}
