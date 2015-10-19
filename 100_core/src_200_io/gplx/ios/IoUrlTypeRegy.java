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
package gplx.ios; import gplx.*;
import gplx.core.strings.*;
public class IoUrlTypeRegy implements GfoInvkAble {
	public String[] FetchAryOr(String key, String... or) {
		IoUrlTypeGrp itm = (IoUrlTypeGrp)hash.Get_by(key);
		return itm == null ? or : itm.AsAry();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Get)) {
			String key = m.ReadStr(k);
			if (ctx.Deny()) return this;
			IoUrlTypeGrp itm = (IoUrlTypeGrp)hash.Get_by(key);
			if (itm == null) {
				itm = new IoUrlTypeGrp(key);
				hash.Add(key, itm);
			}
			return itm;
		}
		else return GfoInvkAble_.Rv_unhandled;
//			return this;
	}	public static final String Invk_Get = "Get";
	Ordered_hash hash = Ordered_hash_.New();
        public static final IoUrlTypeRegy Instance = new IoUrlTypeRegy(); IoUrlTypeRegy() {}
}
class IoUrlTypeGrp implements GfoInvkAble {
	public String[] AsAry() {
		String[] rv = new String[list.Count()];
		for (int i = 0; i < list.Count(); i++)
			rv[i] = (String)list.Get_at(i);
		return rv;
	}
	Ordered_hash list = Ordered_hash_.New();
	public IoUrlTypeGrp(String key) {this.key = key;} private String key;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_AddMany)) {
			if (ctx.Deny()) return this;
			for (int i = 0; i < m.Args_count(); i++) {
				String s = m.ReadStr("v");
				if (list.Has(s)) {
					ctx.Write_warn(UsrMsg.new_("itm already has filter").Add("key", key).Add("filter", s).To_str());
					list.Del(s);
				}
				list.Add(s, s);
			}
		}
		else if	(ctx.Match(k, Invk_Print)) {
			if (ctx.Deny()) return this;
			String_bldr sb = String_bldr_.new_();
			sb.Add(key).Add("{");
			for (int i = 0; i < list.Count(); i++)
				sb.Add_spr_unless_first((String)list.Get_at(i), " ", i);
			sb.Add("}");
			return sb.To_str();
		}
		else if	(ctx.Match(k, Invk_Clear)) {if (ctx.Deny()) return this; list.Clear();}
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_AddMany = "Add_many", Invk_Clear = "Clear", Invk_Print = "Print";
}
