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
package gplx.xowa2.gui; import gplx.*; import gplx.xowa2.*;
import gplx.xowa.*;
import gplx.xowa.html.hdumps.core.*; import gplx.xowa.gui.history.*;
import gplx.xowa2.apps.*; import gplx.xowa2.wikis.*; import gplx.xowa2.gui.*; import gplx.xowa2.apps.urls.*;
public abstract class Xogv_tab_base {
	private Xog_history_stack history_stack = new Xog_history_stack(); private Gfo_url_parser url_parser;
	private Xoav_wiki_mgr wiki_mgr;
	public void Ctor(Xoav_wiki_mgr wiki_mgr, Gfo_url_parser url_parser) {this.wiki_mgr = wiki_mgr; this.url_parser = url_parser;}
	public Xog_history_itm Cur_itm()					{return history_stack.Cur_itm();}
	public Xog_page Go_to(byte[] page)				{return Go_to(history_stack.Cur_itm().Wiki(), page, Bry_.Empty, Bry_.Empty, false, "");}
	public Xog_page Go_to(byte[] wiki, byte[] page)	{return Go_to(wiki, page, Bry_.Empty, Bry_.Empty, false, "");}
	public Xog_page Go_to(byte[] wiki, byte[] page, byte[] anch, byte[] qarg, boolean redirect_force, String bmk_pos) {
		Xog_history_itm old_itm = this.Cur_itm();
		Xog_history_itm new_itm = new Xog_history_itm(wiki, page, anch, qarg, redirect_force, bmk_pos);
		Xog_page rv = Fetch_page_and_show(old_itm, new_itm);
		if (rv.Exists())
			history_stack.Add(new_itm);
		return rv;
	}
	public Xog_page Go_bwd() {return Go_by_dir(Bool_.Y);}
	public Xog_page Go_fwd() {return Go_by_dir(Bool_.N);}
	private Xog_page Go_by_dir(boolean bwd) {
		Xog_history_itm old_itm = this.Cur_itm();
		Xog_history_itm new_itm = bwd ? history_stack.Go_bwd() : history_stack.Go_fwd();
		return Fetch_page_and_show(old_itm, new_itm);
	}
	private Xog_page Fetch_page_and_show(Xog_history_itm old_itm, Xog_history_itm new_itm) {
		if (new_itm == Xog_history_itm.Null) return new Xog_page().Exists_n_();
		Xog_page new_hpg = Fetch_page(new_itm.Wiki(), new_itm.Page(), new_itm.Qarg());
		if (new_hpg.Exists())
			Show_page(old_itm, new_itm, new_hpg);
		return new_hpg;
	}
	private Xog_page Fetch_page(byte[] wiki_domain, byte[] page_bry, byte[] qarg_bry) {
		Xowv_wiki wiki = wiki_mgr.Get_by_domain(wiki_domain);
		if (wiki == null) return new Xog_page().Exists_n_();	// wiki does not exist; happens with xwiki; PAGE:s.w:Photon; EX:[[wikt:transmit]]; DATE:2015-04-27
		Xoa_ttl ttl = wiki.Ttl_parse(page_bry);
		Gfo_url url = url_parser.Parse(Bry_.Add(wiki_domain, Byte_ascii.Slash_bry, page_bry, qarg_bry));
		Xog_page rv = new Xog_page();
		wiki.Pages_get(rv, url, ttl);
		return rv;
	}
	public void Srl_save(Bry_bfr bfr)					{history_stack.Srl_save(bfr);}
	public void Srl_load(byte[] raw)					{history_stack.Srl_load(raw);}
	protected abstract void Show_page(Xog_history_itm old_itm, Xog_history_itm new_itm, Xog_page new_hpg);
}
