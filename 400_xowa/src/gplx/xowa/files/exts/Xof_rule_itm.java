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
public class Xof_rule_itm implements Gfo_invk {
	public Xof_rule_itm(Xof_rule_grp owner, Xof_ext ext) {this.owner = owner;}	// NOTE: ext currently not used; may want to set as property in future; DATE:2015-02-14
	public Xof_rule_grp Owner() {return owner;} private Xof_rule_grp owner;
	public long Make_max() {return make_max;} public Xof_rule_itm Make_max_(long v) {make_max = v; return this;} long make_max = Max_wildcard;
	public long View_max() {return view_max;} public Xof_rule_itm View_max_(long v) {view_max = v; return this;} long view_max = Max_wildcard;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_owner))		return owner;
		else if (ctx.Match(k, Invk_make_max_))	make_max = gplx.core.ios.Io_size_.Load_int_(m);
		else if (ctx.Match(k, Invk_view_max_))	view_max = gplx.core.ios.Io_size_.Load_int_(m);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_owner = "owner", Invk_make_max_ = "make_max_", Invk_view_max_ = "view_max_";
	public static final long Max_wildcard = -1;
}
