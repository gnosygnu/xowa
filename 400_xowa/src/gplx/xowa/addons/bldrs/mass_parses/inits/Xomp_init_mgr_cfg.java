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
package gplx.xowa.addons.bldrs.mass_parses.inits;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.arrays.IntAryUtl;
import gplx.xowa.*;
import gplx.xowa.wikis.nss.*;
class Xomp_init_mgr_cfg implements Gfo_invk {
	public int[] Ns_ids() {return ns_ids;} private int[] ns_ids = new int[] {0, 4, 14, 100};
	public void Init(Xowe_wiki wiki) {
		if (ns_ids == null)				ns_ids = Ns_ids(wiki.Ns_mgr());
	}
	private static int[] Ns_ids(Xow_ns_mgr ns_mgr) {
		int ns_ids_len = ns_mgr.Ids_len();
		int[] rv = new int[ns_ids_len];
		for (int i = 0; i < ns_ids_len; ++i)
			rv[i] = ns_mgr.Ids_get_at(i).Id();
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__ns_ids_))				ns_ids = IntAryUtl.Parse(m.ReadStr("v"), "|");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk__ns_ids_ = "ns_ids_"
	;
}
