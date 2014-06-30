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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Wdata_itemByTitle_cfg implements GfoInvkAble {
	public byte[] Site_default() {return site_default;} private byte[] site_default = Bry_.new_ascii_("enwiki");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_site_default))		return site_default;
		else if (ctx.Match(k, Invk_site_default_))		site_default = m.ReadBry("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_site_default = "site_default", Invk_site_default_ = "site_default_";
	public static final String Key = "itemByTitle";
}
