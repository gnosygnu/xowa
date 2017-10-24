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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Cite_xtn_mgr extends Xox_mgr_base {
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final    byte[] XTN_KEY = Bry_.new_a7("cite");
	@Override public Xox_mgr Xtn_clone_new() {return new Cite_xtn_mgr();}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_group_default_name))		return String_.new_u8(group_default_name);
		else if	(ctx.Match(k, Invk_group_default_name_))	group_default_name = m.ReadBry("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final    String Invk_group_default_name = "group_default_name", Invk_group_default_name_ = "group_default_name_";

	public static byte[] Group_default_name() {return group_default_name;} private static byte[] group_default_name = Bry_.new_a7("lower-alpha");
}
