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
package gplx.xowa.htmls.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xoh_skin_mgr implements Gfo_invk {
	private final    Xoh_skin_regy regy = new Xoh_skin_regy();
	public Xoh_skin_mgr() {
		read = make_and_add(regy, "read");
		edit = make_and_add(regy, "edit");
		html = make_and_add(regy, "html");
	}
	public Xoh_skin_itm Read() {return read;} private Xoh_skin_itm read;
	public Xoh_skin_itm Edit() {return edit;} private Xoh_skin_itm edit;
	public Xoh_skin_itm Html() {return html;} private Xoh_skin_itm html;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_read))		return read;
		else if	(ctx.Match(k, Invk_read_))		read = regy.Get_by_key(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_edit))		return edit;
		else if	(ctx.Match(k, Invk_edit_))		edit = regy.Get_by_key(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_html))		return html;
		else if	(ctx.Match(k, Invk_html_))		html = regy.Get_by_key(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_set))		regy.Set(m.ReadStr("key"), m.ReadStr("fmt"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_read = "read", Invk_read_ = "read_"
	, Invk_edit = "edit", Invk_edit_ = "edit_"
	, Invk_html = "html", Invk_html_ = "html_"
	, Invk_set = "set"
	;
	private static Xoh_skin_itm make_and_add(Xoh_skin_regy regy, String key) {
		Xoh_skin_itm rv = new Xoh_skin_itm(key, key);
		regy.Add(rv);
		return rv;
	}
}
