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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
import gplx.xowa.html.tidy.*; import gplx.xowa.html.utils.*;
public class Xoh_html_mgr implements GfoInvkAble {
	public Xoh_html_mgr(Xoa_app app) {
		js_cleaner = new Xoh_js_cleaner(app);
	}
	public void Init_by_app(Xoa_app app) {
		tidy_mgr.Init_by_app(app);
	}
	public Xop_xatr_whitelist_mgr Whitelist_mgr() {return whitelist_mgr;} private Xop_xatr_whitelist_mgr whitelist_mgr = new Xop_xatr_whitelist_mgr().Ini();
	public Xoh_page_mgr Page_mgr() {return page_mgr;} private Xoh_page_mgr page_mgr = new Xoh_page_mgr();
	public Xoh_tidy_mgr Tidy_mgr() {return tidy_mgr;} private Xoh_tidy_mgr tidy_mgr = new Xoh_tidy_mgr();
	public Xoh_js_cleaner Js_cleaner() {return js_cleaner;} private Xoh_js_cleaner js_cleaner;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_page))	return page_mgr;
		else if	(ctx.Match(k, Invk_tidy))	return tidy_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_page = "page", Invk_tidy = "tidy";
}
