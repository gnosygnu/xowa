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
package gplx.xowa.addons.wikis.searchs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.core.net.*; import gplx.core.net.qargs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.domains.crts.*;
import gplx.xowa.addons.wikis.searchs.searchers.*;
public class Srch_special_cfg implements Gfo_invk {
	private final    Xow_domain_crt_kv_itm_mgr multi_wikis_mgr = new Xow_domain_crt_kv_itm_mgr(); private byte[] multi_wikis_bry = Dflt_multi_wikis_bry;
	private final    Xow_domain_crt_kv_itm_mgr multi_sorts_mgr = new Xow_domain_crt_kv_itm_mgr(); private byte[] multi_sorts_bry = Dflt_multi_sorts_bry;
	public Srch_special_cfg() {
		multi_wikis_mgr.Parse_as_itms(multi_wikis_bry);
		multi_sorts_mgr.Parse_as_arys(multi_sorts_bry);
		ns_mgr.Add_main_if_empty();
	}
	public void Init_by_kit(Xoae_app app, gplx.gfui.kits.core.Gfui_kit kit) {
		app.Cfg().Bind_many_app(this, Cfg__results_per_page, Cfg__async_db, Cfg__auto_wildcard, Cfg__multi_wikis, Cfg__multi_sorts);
	}
	public int					Results_per_page()		{return results_per_page;}		private int results_per_page = 100;
	public boolean				Async_db()				{return async_db;}				private boolean async_db = true;
	public boolean					Auto_wildcard()			{return auto_wildcard;}			private boolean auto_wildcard = false;		// automatically add wild-card; EX: Earth -> *Earth*
	public Srch_ns_mgr			Ns_mgr()				{return ns_mgr;}				private final    Srch_ns_mgr ns_mgr = new Srch_ns_mgr();
	public Xow_domain_crt_itm	Multi_wikis_crt			(Xow_domain_itm cur_domain) {return multi_wikis_mgr.Find_itm(cur_domain, cur_domain);}
	public Xow_domain_crt_itm[] Multi_sorts_crt			(Xow_domain_itm cur_domain) {return multi_sorts_mgr.Find_ary(cur_domain, cur_domain);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__results_per_page)) 						results_per_page = m.ReadInt("v");
		else if	(ctx.Match(k, Cfg__async_db)) 								async_db = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__auto_wildcard))							auto_wildcard = m.ReadYn("v");
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
	  Cfg__results_per_page		= "xowa.addon.search.special.results_per_page"
	, Cfg__async_db				= "xowa.addon.search.special.async_db"
	, Cfg__auto_wildcard		= "xowa.addon.search.special.auto_wildcard"
	, Cfg__multi_wikis			= "xowa.addon.search.special.multi_wikis"
	, Cfg__multi_sorts			= "xowa.addon.search.special.multi_sorts"
	;
	public static final    byte[]
	  Dflt_multi_wikis_bry = Bry_.new_a7("<any>|<self>")
	, Dflt_multi_sorts_bry = Bry_.new_a7("<any>|<self>,*.wikipedia,*.wikivoyage,*.wiktionary,*.wikisource,*.wikiquote,*.wikibooks,*.wikiversity,*.wikinews")
	;
}
