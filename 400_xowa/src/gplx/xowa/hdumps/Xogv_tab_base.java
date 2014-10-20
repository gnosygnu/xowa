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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.xowa.hdumps.core.*; import gplx.xowa.gui.history.*;
public abstract class Xogv_tab_base {
	private Xog_history_stack history_stack = new Xog_history_stack();
	private Xoav_wiki_mgr wiki_mgr;
	public void Ctor(Xoav_wiki_mgr wiki_mgr) {this.wiki_mgr = wiki_mgr;}
	public Xog_history_itm Cur_itm()					{return history_stack.Cur_itm();}
	public Hdump_page Go_to(byte[] page)				{return Go_to(history_stack.Cur_itm().Wiki(), page, Bry_.Empty, Bry_.Empty, false, "");}
	public Hdump_page Go_to(byte[] wiki, byte[] page)	{return Go_to(wiki, page, Bry_.Empty, Bry_.Empty, false, "");}
	public Hdump_page Go_to(byte[] wiki, byte[] page, byte[] anch, byte[] qarg, boolean redirect_force, String bmk_pos) {
		Xog_history_itm old_itm = this.Cur_itm();
		Xog_history_itm new_itm = new Xog_history_itm(wiki, page, anch, qarg, redirect_force, bmk_pos);
		history_stack.Add(new_itm);
		return Fetch_page_and_show(old_itm, new_itm);
	}
	public Hdump_page Go_bwd() {return Go_by_dir(Bool_.Y);}
	public Hdump_page Go_fwd() {return Go_by_dir(Bool_.N);}
	private Hdump_page Go_by_dir(boolean bwd) {
		Xog_history_itm old_itm = this.Cur_itm();
		Xog_history_itm new_itm = bwd ? history_stack.Go_bwd() : history_stack.Go_fwd();
		return Fetch_page_and_show(old_itm, new_itm);
	}
	private Hdump_page Fetch_page_and_show(Xog_history_itm old_itm, Xog_history_itm new_itm) {
		Hdump_page new_hpg = Fetch_page(new_itm.Wiki(), new_itm.Page());
		Show_page(old_itm, new_itm, new_hpg);
		return new_hpg;
	}
	private Hdump_page Fetch_page(byte[] wiki_domain, byte[] page_ttl) {
		Xowv_wiki wiki = wiki_mgr.Get_by_domain(wiki_domain);
		Hdump_page rv = new Hdump_page();
		wiki.Hdump_mgr().Load(rv, page_ttl);
		return rv;
	}
	protected abstract void Show_page(Xog_history_itm old_itm, Xog_history_itm new_itm, Hdump_page new_hpg);
}
