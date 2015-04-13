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
package gplx.xowa.apis.xowa.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
public class Xoapi_search implements GfoInvkAble {
	public int			Results_per_page()			{return results_per_page;}		private int results_per_page = 20;
	public boolean			Async_db()					{return async_db;}				private boolean async_db = true;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_results_per_page)) 						return results_per_page;
		else if	(ctx.Match(k, Invk_results_per_page_)) 						results_per_page = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_async_db)) 								return Yn.Xto_str(async_db);
		else if	(ctx.Match(k, Invk_async_db_)) 								async_db = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_results_per_page		= "results_per_page"	, Invk_results_per_page_	= "results_per_page_"
	, Invk_async_db				= "async_db"			, Invk_async_db_			= "async_db_"
	;
}
