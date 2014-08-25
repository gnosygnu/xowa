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
package gplx.xowa; import gplx.*;
import gplx.xowa.parsers.lnkis.cfgs.*;
public class Xowc_parser implements GfoInvkAble {
	public Xowc_parser(Xow_wiki wiki) {
		lnki_cfg = new Xoc_lnki_cfg(wiki);
	}
	public Xoc_lnki_cfg Lnki_cfg() {return lnki_cfg;} private Xoc_lnki_cfg lnki_cfg;
	public Xowc_xtns Xtns() {return xtns;} private Xowc_xtns xtns = new Xowc_xtns();
	public boolean Display_title_restrict() {return display_title_restrict;} public void Display_title_restrict_(boolean v) {display_title_restrict = v;} private boolean display_title_restrict = true;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_xtns))						return xtns;
		else if	(ctx.Match(k, Invk_lnki))						return lnki_cfg;
		else if	(ctx.Match(k, Invk_display_title_restrict))		return display_title_restrict;
		else if	(ctx.Match(k, Invk_display_title_restrict_))	display_title_restrict = m.ReadYn("v");
		else													return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_xtns = "xtns", Invk_lnki = "lnki", Invk_display_title_restrict = "display_title_restrict", Invk_display_title_restrict_ = "display_title_restrict_";
}
