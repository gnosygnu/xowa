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
package gplx.xowa; import gplx.*;
import gplx.core.primitives.*;
public class Xoft_rule_grp implements GfoInvkAble {
	public Xoft_rule_grp(Xoft_rule_mgr owner, byte[] key) {this.owner = owner; this.key = key;}
	public Xoft_rule_mgr Owner() {return owner;} private Xoft_rule_mgr owner;
	public byte[] Key() {return key;} private byte[] key;
	public Xoft_rule_itm Get_or_null(byte[] key) {return (Xoft_rule_itm)hash.Fetch(hash_ref.Val_(key));}
	public Xoft_rule_itm Get_or_new(byte[] key) {
		Xoft_rule_itm rv = Get_or_null(key);
		if (rv == null) {
			Xof_ext file_type = Xof_ext_.new_by_ext_(key);
			rv = new Xoft_rule_itm(this, file_type);
			hash.Add(Bry_obj_ref.new_(key), rv);
		}
		return rv;
	}	HashAdp hash = HashAdp_.new_(); Bry_obj_ref hash_ref = Bry_obj_ref.null_();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))		return owner;
		else if	(ctx.Match(k, Invk_set))		return Get_or_new(Bry_.new_utf8_(m.ReadStr("v")));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_owner = "owner", Invk_set = "set";
	public static final String Grp_app_default_str = "app_default";
	public static byte[] Grp_app_default = Bry_.new_utf8_(Grp_app_default_str);
}
