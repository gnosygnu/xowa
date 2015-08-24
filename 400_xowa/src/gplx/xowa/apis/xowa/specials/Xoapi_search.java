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
package gplx.xowa.apis.xowa.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.domains.crts.*;
public class Xoapi_search implements GfoInvkAble, GfoEvMgrOwner {
	private final Xow_domain_crt_kv_itm_mgr multi_wikis_mgr = new Xow_domain_crt_kv_itm_mgr(); private byte[] multi_wikis_bry = Dflt_multi_wikis_bry;
	private final Xow_domain_crt_kv_itm_mgr multi_sorts_mgr = new Xow_domain_crt_kv_itm_mgr(); private byte[] multi_sorts_bry = Dflt_multi_sorts_bry;
	public Xoapi_search() {
		this.evMgr = GfoEvMgr.new_(this);
		multi_wikis_mgr.Parse_as_itms(multi_wikis_bry);
		multi_sorts_mgr.Parse_as_arys(multi_sorts_bry);
	}
	public GfoEvMgr EvMgr() {return evMgr;} private GfoEvMgr evMgr;
	public int			Results_per_page()			{return results_per_page;}		private int results_per_page = 20;
	public boolean			Async_db()					{return async_db;}				private boolean async_db = true;
	public void Multi_wikis_bry_(byte[] v) {
		if (!multi_wikis_mgr.Parse_as_itms(v)) return;
		this.multi_wikis_bry = v;
		GfoEvMgr_.PubVal(this, Evt_multi_wikis_changed, v);
	}
	public Xow_domain_crt_itm Multi_wikis_crt(Xow_domain_itm cur_domain) {
		return multi_wikis_mgr.Find_itm(cur_domain, cur_domain);
	}
	public void Multi_sorts_bry_(byte[] v) {
		if (!multi_sorts_mgr.Parse_as_arys(v)) return;
		this.multi_sorts_bry = v;
		GfoEvMgr_.PubVal(this, Evt_multi_sorts_changed, v);
	}
	public Xow_domain_crt_itm[] Multi_sorts_crt(Xow_domain_itm cur_domain) {
		return multi_sorts_mgr.Find_ary(cur_domain, cur_domain);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_results_per_page)) 						return results_per_page;
		else if	(ctx.Match(k, Invk_results_per_page_)) 						results_per_page = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_async_db)) 								return Yn.Xto_str(async_db);
		else if	(ctx.Match(k, Invk_async_db_)) 								async_db = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_multi_wikis)) 							return String_.new_u8(multi_wikis_bry);
		else if	(ctx.Match(k, Invk_multi_wikis_)) 							Multi_wikis_bry_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_multi_sorts)) 							return String_.new_u8(multi_sorts_bry);
		else if	(ctx.Match(k, Invk_multi_sorts_)) 							Multi_sorts_bry_(m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_results_per_page		= "results_per_page"	, Invk_results_per_page_	= "results_per_page_"
	, Invk_async_db				= "async_db"			, Invk_async_db_			= "async_db_"
	, Invk_multi_wikis			= "multi_wikis"			, Invk_multi_wikis_			= "multi_wikis_"
	, Invk_multi_sorts			= "multi_sorts"			, Invk_multi_sorts_			= "multi_sorts_"
	;
	public static final String 
	  Evt_multi_wikis_changed = "multi_wikis_changed"
	, Evt_multi_sorts_changed = "multi_sorts_changed"
	;
	public static final byte[]
	  Dflt_multi_wikis_bry = Bry_.new_a7("<any>|<self>")
	, Dflt_multi_sorts_bry = Bry_.new_a7("<any>|<self>,*.wikipedia,*.wikivoyage,*.wiktionary,*.wikisource,*.wikiquote,*.wikibooks,*.wikiversity,*.wikinews")
	;
}
