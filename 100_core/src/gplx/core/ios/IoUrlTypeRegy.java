/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.strings.*; import gplx.langs.gfs.*;
public class IoUrlTypeRegy implements Gfo_invk {
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
		else return Gfo_invk_.Rv_unhandled;
//			return this;
	}	public static final    String Invk_Get = "Get";
	Ordered_hash hash = Ordered_hash_.New();
        public static final    IoUrlTypeRegy Instance = new IoUrlTypeRegy(); IoUrlTypeRegy() {}
}
class IoUrlTypeGrp implements Gfo_invk {
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
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final    String Invk_AddMany = "Add_many", Invk_Clear = "Clear", Invk_Print = "Print";
}
