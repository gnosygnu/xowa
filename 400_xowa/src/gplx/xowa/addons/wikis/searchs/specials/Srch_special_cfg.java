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
package gplx.xowa.addons.wikis.searchs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.core.net.*; import gplx.core.net.qargs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.domains.crts.*;
import gplx.xowa.addons.wikis.searchs.searchers.*;
public class Srch_special_cfg implements Gfo_invk {
	private Xoa_app app;
	private final    Xow_domain_crt_kv_itm_mgr multi_wikis_mgr = new Xow_domain_crt_kv_itm_mgr(); private byte[] multi_wikis_bry = Dflt_multi_wikis_bry;
	private final    Xow_domain_crt_kv_itm_mgr multi_sorts_mgr = new Xow_domain_crt_kv_itm_mgr(); private byte[] multi_sorts_bry = Dflt_multi_sorts_bry;
	private String args_default_str = "";// default args for search
	public Srch_special_cfg() {
		multi_wikis_mgr.Parse_as_itms(multi_wikis_bry);
		multi_sorts_mgr.Parse_as_arys(multi_sorts_bry);
		ns_mgr.Add_main_if_empty();
	}
	public void Init_by_kit(Xoae_app app, gplx.gfui.kits.core.Gfui_kit kit) {
		this.app = app;
		app.Cfg().Bind_many_app(this, Cfg__results_per_page, Cfg__async_db, Cfg__auto_wildcard, Cfg__args_default, Cfg__multi_wikis, Cfg__multi_sorts);
	}
	public int					Results_per_page()		{return results_per_page;}		private int results_per_page = 100;
	public boolean					Async_db()				{return async_db;}				private boolean async_db = true;
	public boolean				Auto_wildcard()			{return auto_wildcard;}			private boolean auto_wildcard = false;		// automatically add wild-card; EX: Earth -> *Earth*
	public Gfo_qarg_itm[]		Args_default()			{return args_default;}			private Gfo_qarg_itm[] args_default = Gfo_qarg_itm.Ary_empty;
	public Srch_ns_mgr			Ns_mgr()				{return ns_mgr;}				private final    Srch_ns_mgr ns_mgr = new Srch_ns_mgr();
	public Xow_domain_crt_itm	Multi_wikis_crt			(Xow_domain_itm cur_domain) {return multi_wikis_mgr.Find_itm(cur_domain, cur_domain);}
	public Xow_domain_crt_itm[] Multi_sorts_crt			(Xow_domain_itm cur_domain) {return multi_sorts_mgr.Find_ary(cur_domain, cur_domain);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__results_per_page)) 						results_per_page = m.ReadInt("v");
		else if	(ctx.Match(k, Cfg__async_db)) 								async_db = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__auto_wildcard))							auto_wildcard = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__args_default)) {
			this.args_default_str = m.ReadStr("v");
			byte[] bry = Bry_.new_a7("http://x.org/a?" + args_default_str);
			Gfo_url tmp_url = app.User().Wikii().Utl__url_parser().Url_parser().Parse(bry, 0, bry.length);
			this.args_default = tmp_url.Qargs();
		}
		else if	(ctx.Match(k, Cfg__multi_wikis)) {
			byte[] multi_wikis_temp = m.ReadBry("v");
			if (multi_wikis_mgr.Parse_as_itms(multi_wikis_temp)) {
				this.multi_wikis_bry = multi_wikis_temp;
			}
		}
		else if	(ctx.Match(k, Cfg__multi_sorts)) {
			byte[] multi_sorts_temp = m.ReadBry("v");
			if (multi_sorts_mgr.Parse_as_arys(multi_sorts_temp)) {
				this.multi_sorts_bry = multi_sorts_temp;
			}
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Cfg__results_per_page		= "xowa.addon.search.results_per_page"
	, Cfg__async_db				= "xowa.addon.search.async_db"
	, Cfg__auto_wildcard		= "xowa.addon.search.auto_wildcard"
	, Cfg__args_default			= "xowa.addon.search.args_default"
	, Cfg__multi_wikis			= "xowa.addon.search.multi_wikis"
	, Cfg__multi_sorts			= "xowa.addon.search.multi_sorts"
	;
	public static final    byte[]
	  Dflt_multi_wikis_bry = Bry_.new_a7("<any>|<self>")
	, Dflt_multi_sorts_bry = Bry_.new_a7("<any>|<self>,*.wikipedia,*.wikivoyage,*.wiktionary,*.wikisource,*.wikiquote,*.wikibooks,*.wikiversity,*.wikinews")
	;
}
