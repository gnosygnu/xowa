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
package gplx.xowa.specials; import gplx.*; import gplx.xowa.*;
import gplx.xowa.xtns.wbases.specials.*;
public class Xoa_special_mgr implements Gfo_invk {
	private Wdata_itemByTitle_cfg wbase_cfg = new Wdata_itemByTitle_cfg();
	private Ordered_hash hash = Ordered_hash_.New();
	public Xoa_special_mgr() {
		hash.Add(Wdata_itemByTitle_cfg.Key, wbase_cfg);
	}
	public void Init_by_app(Xoae_app app) {
		wbase_cfg.Init_by_app(app);
	}
	public void Add(String key, Gfo_invk cfg)	{hash.Add(key, cfg);}
	public Gfo_invk Get_or_null(String key)		{return (Gfo_invk)hash.Get_by(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))		return Get_or_null(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
