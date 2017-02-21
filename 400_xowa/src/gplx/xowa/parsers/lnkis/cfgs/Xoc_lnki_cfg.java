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
package gplx.xowa.parsers.lnkis.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*;
public class Xoc_lnki_cfg implements Gfo_invk {
	public Xoc_lnki_cfg(Xowe_wiki wiki) {xwiki_repo_mgr = new Xoc_xwiki_repo_mgr(wiki);}
	public Xoc_xwiki_repo_mgr Xwiki_repo_mgr() {return xwiki_repo_mgr;} private Xoc_xwiki_repo_mgr xwiki_repo_mgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_xwiki_repos))			return xwiki_repo_mgr;
		else return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_xwiki_repos = "xwiki_repos";
}
