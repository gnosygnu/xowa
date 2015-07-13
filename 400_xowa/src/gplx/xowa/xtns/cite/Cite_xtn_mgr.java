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
package gplx.xowa.xtns.cite; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Cite_xtn_mgr extends Xox_mgr_base {
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_a7("cite");
//		public byte[] Group_default_name() {return group_default_name;} private byte[] group_default_name = Bry_.new_a7("lower-alpha");
	public static byte[] Group_default_name() {return group_default_name;} private static byte[] group_default_name = Bry_.new_a7("lower-alpha");
	@Override public Xox_mgr Clone_new() {return new Cite_xtn_mgr();}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_group_default_name))		return String_.new_u8(group_default_name);
		else if	(ctx.Match(k, Invk_group_default_name_))	group_default_name = m.ReadBry("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_group_default_name = "group_default_name", Invk_group_default_name_ = "group_default_name_";
}
