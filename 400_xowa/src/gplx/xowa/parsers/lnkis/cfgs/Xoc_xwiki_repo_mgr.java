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
public class Xoc_xwiki_repo_mgr implements GfoInvkAble {
	private OrderedHash hash = OrderedHash_.new_bry_();
	private Xowe_wiki wiki;
	public Xoc_xwiki_repo_mgr(Xowe_wiki wiki) {this.wiki = wiki;}
	public boolean Has(byte[] abrv) {
		Xoc_xwiki_repo_itm itm = (Xoc_xwiki_repo_itm)hash.Fetch(abrv);
		return itm != null;
	}
	public void Add_or_mod(byte[] abrv) {
		Xoc_xwiki_repo_itm itm = (Xoc_xwiki_repo_itm)hash.Fetch(abrv);
		if (itm == null) {
			itm = new Xoc_xwiki_repo_itm(abrv);
			hash.Add(abrv, itm);
			wiki.Cfg_parser_lnki_xwiki_repos_enabled_(true);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add))			Add_or_mod(m.ReadBry("xwiki"));
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_add = "add";
}
class Xoc_xwiki_repo_itm {
	public Xoc_xwiki_repo_itm(byte[] abrv) {this.abrv = abrv;}
	public byte[] Abrv() {return abrv;} private byte[] abrv;
}
