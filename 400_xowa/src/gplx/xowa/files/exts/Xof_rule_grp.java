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
import gplx.core.primitives.*;
public class Xof_rule_grp implements Gfo_invk {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xof_rule_grp(Xof_rule_mgr owner, byte[] key) {this.owner = owner; this.key = key;}
	public Xof_rule_mgr Owner() {return owner;} private final    Xof_rule_mgr owner;
	public byte[] Key() {return key;} private final    byte[] key;
	public Xof_rule_itm Get_or_null(byte[] ext_bry) {return (Xof_rule_itm)hash.Get_by_bry(ext_bry);}
	public Xof_rule_itm Get_or_new(byte[] ext_bry) {
		Xof_rule_itm rv = Get_or_null(ext_bry);
		if (rv == null) {
			Xof_ext ext = Xof_ext_.new_by_ext_(ext_bry);
			rv = new Xof_rule_itm(this, ext);
			hash.Add(ext_bry, rv);
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))		return owner;
		else if	(ctx.Match(k, Invk_set))		return Get_or_new(Bry_.new_u8(m.ReadStr("v")));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_owner = "owner", Invk_set = "set";
	private static final String Grp_app_default_str = "app_default";
	public static byte[] Grp_app_default = Bry_.new_u8(Grp_app_default_str);
}
