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
import gplx.xowa.apps.apis.xowa.addons.bldrs.*;
public class Xoapi_addon_bldr implements Gfo_invk {
	public Xoapi_central_api		Central()		{return central;}		private final    Xoapi_central_api central = new Xoapi_central_api();
	public Xoapi_sync_api			Sync()			{return sync;}			private final    Xoapi_sync_api sync = new Xoapi_sync_api();
	public boolean						Wikis__ctgs__hidden_enabled() {return wikis__ctgs__hidden_enabled;} private boolean wikis__ctgs__hidden_enabled = false;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__central)) 						return central;
		else if	(ctx.Match(k, Invk__sync)) 							return sync;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String
	  Invk__central							= "central"
	, Invk__sync							= "sync"
	;
}
