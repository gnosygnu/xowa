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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.htmls.core.htmls.tidy.*; import gplx.xowa.htmls.js.*; import gplx.xowa.htmls.skins.*;
import gplx.xowa.parsers.xndes.*;
public class Xoh_html_mgr implements Gfo_invk {
	public Xoh_html_mgr(Xoae_app app) {}
	public void Init_by_app(Xoae_app app) {
		tidy_mgr.Init_by_app(app);
	}
	public Xoh_page_mgr Page_mgr() {return page_mgr;} private final    Xoh_page_mgr page_mgr = new Xoh_page_mgr();
	public Xoa_tidy_mgr Tidy_mgr() {return tidy_mgr;} private final    Xoa_tidy_mgr tidy_mgr = new Xoa_tidy_mgr();
	public Xoh_skin_mgr Skin_mgr() {return skin_mgr;} private final    Xoh_skin_mgr skin_mgr = new Xoh_skin_mgr();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_page))	return page_mgr;
		else if	(ctx.Match(k, Invk_tidy))	return tidy_mgr;
		else if	(ctx.Match(k, Invk_skins))	return skin_mgr;
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_page = "page", Invk_tidy = "tidy", Invk_skins = "skins";
}
