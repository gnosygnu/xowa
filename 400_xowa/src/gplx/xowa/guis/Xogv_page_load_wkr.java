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
package gplx.xowa.guis; import gplx.*; import gplx.xowa.*;
import gplx.core.threads.*; import gplx.core.net.*;
import gplx.xowa.guis.history.*;
import gplx.xowa.apps.*; import gplx.xowa.wikis.*;
import gplx.xowa.htmls.*;
class Xogv_page_load_wkr implements Gfo_thread_wkr, Gfo_invk {
	private final    Xoav_wiki_mgr wiki_mgr; private final    Gfo_url_parser url_parser;
	private final    Xogv_tab_base tab; private final    Xog_history_itm old_itm, new_itm;
	public Xogv_page_load_wkr(Xoav_wiki_mgr wiki_mgr, Gfo_url_parser url_parser, Xogv_tab_base tab, Xog_history_itm old_itm, Xog_history_itm new_itm) {
		this.wiki_mgr = wiki_mgr; this.url_parser = url_parser; this.tab = tab; this.old_itm = old_itm; this.new_itm = new_itm;
	}
	public String			Thread__name() {return Thread_name;} public static final String Thread_name = "xowa.page_load";
	public boolean			Thread__resume() {return true;}
	public void Thread__exec() {
		Xoh_page new_hpg = Fetch_page(new_itm.Wiki(), new_itm.Page(), new_itm.Qarg());
		tab.Show_page(old_itm, new_itm, new_hpg);
	}
	private Xoh_page Fetch_page(byte[] wiki_domain, byte[] page_bry, byte[] qarg_bry) {
		Xowv_wiki wiki = (Xowv_wiki)wiki_mgr.Get_by_or_null(wiki_domain);
		if (wiki == null) return Xoh_page.New_missing();	// wiki does not exist; happens with xwiki; PAGE:s.w:Photon; EX:[[wikt:transmit]]; DATE:2015-04-27
		Xoa_ttl ttl = wiki.Ttl_parse(page_bry);
		Gfo_url url = url_parser.Parse(Bry_.Add(wiki_domain, Byte_ascii.Slash_bry, page_bry, qarg_bry));
		Xoh_page rv = new Xoh_page();
		wiki.Pages_get(rv, url, ttl);
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_exec))	this.Thread__exec();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final String Invk_exec = "exec";
}
