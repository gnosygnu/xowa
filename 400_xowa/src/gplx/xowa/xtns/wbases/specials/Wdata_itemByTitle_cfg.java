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
package gplx.xowa.xtns.wbases.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
public class Wdata_itemByTitle_cfg implements Gfo_invk {
	public byte[] Site_default() {return site_default;} private byte[] site_default = Bry_.new_a7("enwiki");
	public void Init_by_app(Xoae_app app) {
		app.Cfg().Bind_many_app(this, Cfg__site_default);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if	(ctx.Match(k, Cfg__site_default))		site_default = m.ReadBry("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Key = "itemByTitle";
	private static final String Cfg__site_default = "xowa.addon.wikibase.site_default";

}
