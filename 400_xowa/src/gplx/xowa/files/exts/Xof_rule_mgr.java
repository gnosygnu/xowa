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
package gplx.xowa.files.exts; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.apps.*;
public class Xof_rule_mgr implements Gfo_invk {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xof_rule_mgr() {
		Xof_rule_grp app_default = new Xof_rule_grp(this, Xof_rule_grp.Grp_app_default);
		Set_app_default(app_default, Io_mgr.Len_gb, Xof_ext_.Bry__ary);
		hash.Add(Xof_rule_grp.Grp_app_default, app_default);
	}
	private Xof_rule_grp Get_or_null(byte[] key) {return (Xof_rule_grp)hash.Get_by_bry(key);}
	public Xof_rule_grp Get_or_new(byte[] key) {
		Xof_rule_grp rv = Get_or_null(key);
		if (rv == null) {
			rv = new Xof_rule_grp(this, key);
			hash.Add(key, rv);
		}
		return rv;
	}
	private static void Set_app_default(Xof_rule_grp app_default, long make_max, byte[][] keys) {
		int len = keys.length;
		for (int i = 0; i < len; i++)
			app_default.Get_or_new(keys[i]).Make_max_(make_max);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set))		return Get_or_new(Bry_.new_u8(m.ReadStr("v")));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_set = "set";
}
