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
