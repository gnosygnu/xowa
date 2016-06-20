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
package gplx.xowa.apps.apis.xowa.addons; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.domains.crts.*;
import gplx.xowa.apps.apis.xowa.addons.searchs.*;
public class Xoapi_search_addon implements Gfo_invk {
	public Xoapi_search_addon() {}
	public Xoapi_url_bar		Url_bar()		{return url_bar;}		private final    Xoapi_url_bar url_bar = new Xoapi_url_bar();
	@gplx.Internal protected Xoapi_search_box		Search_box()	{return search_box;}	private final    Xoapi_search_box search_box = new Xoapi_search_box();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__url_bar)) 						return url_bar;
		else if	(ctx.Match(k, Invk__search_box)) 					return search_box;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String
	  Invk__url_bar				= "url_bar"
	, Invk__search_box			= "search_box"
	;
}
class Xoapi_search_box implements Gfo_invk {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return null;
	}
}
