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
package gplx.xowa.apps.cfgs.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.cfgs.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.guis.langs.*;
public class Xocfg_win implements GfoInvkAble {
	public Xocfg_win(Xoae_app app) {}
	public Xol_font_info Font() {return font;} private Xol_font_info font = new Xol_font_info("Arial", 8, gplx.gfui.FontStyleAdp_.Plain);
	public Bry_fmtr Search_box_fmtr() {return search_box_fmtr;} private Bry_fmtr search_box_fmtr = Bry_fmtr.new_("Special:Allpages?from=", "search");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_font))					return font;
		else if	(ctx.Match(k, Invk_search_box_fmt_))		search_box_fmtr.Fmt_(m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_search_box_fmt_ = "search_box_fmt_", Invk_font = "font";
	public static final float Font_size_default = 8;
}
