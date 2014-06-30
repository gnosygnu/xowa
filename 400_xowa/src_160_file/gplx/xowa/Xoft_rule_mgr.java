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
import gplx.xowa.apps.*;
public class Xoft_rule_mgr implements GfoInvkAble {
	public Xoft_rule_mgr() {
		Xoft_rule_grp app_default = new Xoft_rule_grp(this, Xoft_rule_grp.Grp_app_default);
		App_default_set(app_default, Io_mgr.Len_gb, Xof_ext_.Bry__ary);
		hash.Add(Xoft_rule_grp.Grp_app_default, app_default);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set))		return Get_or_new(Bry_.new_utf8_(m.ReadStr("v")));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_set = "set";
	Xoft_rule_grp Get_or_null(byte[] key) {return (Xoft_rule_grp)hash.Fetch(key);}
	public Xoft_rule_grp Get_or_new(byte[] key) {
		Xoft_rule_grp rv = Get_or_null(key);
		if (rv == null) {
			rv = new Xoft_rule_grp(this, key);
			hash.Add(key, rv);
		}
		return rv;
	}	HashAdp hash = HashAdp_.new_bry_();
	private void App_default_set(Xoft_rule_grp app_default, long make_max, byte[][] keys) {
		int keys_len = keys.length;
		for (int i = 0; i < keys_len; i++)
			app_default.Get_or_new(keys[i]).Make_max_(make_max);
	}
}
