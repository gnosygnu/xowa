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
package gplx.xowa.addons.wikis.searchs.bldrs.cmds.adjustments; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.bldrs.*; import gplx.xowa.addons.wikis.searchs.bldrs.cmds.*;
import gplx.core.lists.hashs.*; import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
class Page_matcher_mgr implements Gfo_invk {
	private final    Xow_wiki wiki;
	public Page_matcher_mgr(Xow_wiki wiki) {this.wiki = wiki;}
	private final    Hash_adp__int hash = new Hash_adp__int();
	public Page_matcher_wkr Get_by(int ns_id) {
		Page_matcher_wkr rv = (Page_matcher_wkr)hash.Get_by_or_null(ns_id);
		if (rv == null) {
			rv = new Page_matcher_wkr(wiki, ns_id);
			hash.Add(ns_id, rv);
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__get))		return Get_by(m.ReadInt("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk__get = "get";
}
