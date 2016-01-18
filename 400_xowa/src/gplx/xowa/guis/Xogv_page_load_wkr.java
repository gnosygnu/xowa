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
package gplx.xowa.guis; import gplx.*; import gplx.xowa.*;
import gplx.core.threads.*; import gplx.core.net.*;
import gplx.xowa.guis.history.*;
import gplx.xowa.apps.*; import gplx.xowa.wikis.*;
import gplx.xowa.htmls.*;
class Xogv_page_load_wkr implements Gfo_thread_wkr, GfoInvkAble {
	private final Xoav_wiki_mgr wiki_mgr; private final Gfo_url_parser url_parser;
	private final Xogv_tab_base tab; private final Xog_history_itm old_itm, new_itm;
	public Xogv_page_load_wkr(Xoav_wiki_mgr wiki_mgr, Gfo_url_parser url_parser, Xogv_tab_base tab, Xog_history_itm old_itm, Xog_history_itm new_itm) {
		this.wiki_mgr = wiki_mgr; this.url_parser = url_parser; this.tab = tab; this.old_itm = old_itm; this.new_itm = new_itm;
	}
	public String Name() {return Thread_name;} public static final String Thread_name = "xowa.page_load";
	public boolean Resume() {return true;}
	public void Exec() {
		Xoh_page new_hpg = Fetch_page(new_itm.Wiki(), new_itm.Page(), new_itm.Qarg());
		tab.Show_page(old_itm, new_itm, new_hpg);
	}
	private Xoh_page Fetch_page(byte[] wiki_domain, byte[] page_bry, byte[] qarg_bry) {
		Xowv_wiki wiki = (Xowv_wiki)wiki_mgr.Get_by_or_null(wiki_domain);
		if (wiki == null) return new Xoh_page().Exists_n_();	// wiki does not exist; happens with xwiki; PAGE:s.w:Photon; EX:[[wikt:transmit]]; DATE:2015-04-27
		Xoa_ttl ttl = wiki.Ttl_parse(page_bry);
		Gfo_url url = url_parser.Parse(Bry_.Add(wiki_domain, Byte_ascii.Slash_bry, page_bry, qarg_bry));
		Xoh_page rv = new Xoh_page();
		wiki.Pages_get(rv, url, ttl);
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_exec))	this.Exec();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_exec = "exec";
}
