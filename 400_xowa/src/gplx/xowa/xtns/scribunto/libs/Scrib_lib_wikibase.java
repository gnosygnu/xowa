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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.parsers.*;	
import gplx.xowa.wikis.domains.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_wikibase implements Scrib_lib {
	public Scrib_lib_wikibase(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_wikibase(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.wikibase.lua"));
		notify_page_changed_fnc = mod.Fncs_get_by_key("notify_page_changed");
		return mod;
	}	private Scrib_lua_proc notify_page_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getLabel:							return GetLabel(args, rslt);
			case Proc_getEntity:						return GetEntity(args, rslt);
			case Proc_getSetting:						return GetSetting(args, rslt);
			case Proc_getEntityUrl:						return GetEntityUrl(args, rslt);
			case Proc_renderSnak:						return RenderSnak(args, rslt);
			case Proc_renderSnaks:						return RenderSnaks(args, rslt);
			case Proc_getEntityId:						return GetEntityId(args, rslt);
			case Proc_getUserLang:						return GetUserLang(args, rslt);
			case Proc_getDescription:					return GetDescription(args, rslt);
			case Proc_resolvePropertyId:				return ResolvePropertyId(args, rslt);
			case Proc_getSiteLinkPageName:				return GetSiteLinkPageName(args, rslt);
			case Proc_incrementExpensiveFunctionCount:	return IncrementExpensiveFunctionCount(args, rslt);
			case Proc_getPropertyOrder:					return GetPropertyOrder(args, rslt);
			case Proc_orderProperties:					return OrderProperties(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int 
	  Proc_getLabel = 0, Proc_getEntity = 1, Proc_getSetting = 2, Proc_getEntityUrl = 3, Proc_renderSnak = 4, Proc_renderSnaks = 5, Proc_getEntityId = 6, Proc_getUserLang = 7
	, Proc_getDescription = 8, Proc_resolvePropertyId = 9, Proc_getSiteLinkPageName = 10, Proc_incrementExpensiveFunctionCount = 11, Proc_getPropertyOrder = 12, Proc_orderProperties = 13;
	public static final String Invk_getLabel = "getLabel", Invk_getEntity = "getEntity", Invk_getSetting = "getSetting", Invk_getEntityUrl = "getEntityUrl"
	, Invk_renderSnak = "renderSnak", Invk_renderSnaks = "renderSnaks", Invk_getEntityId = "getEntityId", Invk_getUserLang = "getUserLang"
	, Invk_getDescription = "getDescription", Invk_resolvePropertyId = "resolvePropertyId", Invk_getSiteLinkPageName = "getSiteLinkPageName", Invk_incrementExpensiveFunctionCount = "incrementExpensiveFunctionCount"
	, Invk_getPropertyOrder = "getPropertyOrder", Invk_orderProperties = "orderProperties"
	;
	private static final    String[] Proc_names = String_.Ary
	( Invk_getLabel, Invk_getEntity, Invk_getSetting, Invk_getEntityUrl, Invk_renderSnak, Invk_renderSnaks, Invk_getEntityId, Invk_getUserLang
	, Invk_getDescription, Invk_resolvePropertyId, Invk_getSiteLinkPageName, Invk_incrementExpensiveFunctionCount, Invk_getPropertyOrder, Invk_orderProperties
	);
	public void Notify_page_changed() {if (notify_page_changed_fnc != null) core.Interpreter().CallFunction(notify_page_changed_fnc.Id(), Keyval_.Ary_empty);}
	public boolean GetLabel(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core); if (wdoc == null) return rslt.Init_ary_empty();
		return rslt.Init_obj(wdoc.Label_list__get_or_fallback(core.Lang()));
	}
	public boolean GetEntity(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Wdata_doc wdoc = Get_wdoc_or_null(args, core); if (wdoc == null) return rslt.Init_ary_empty();
		Wbase_prop_mgr prop_mgr = core.Wiki().Appe().Wiki_mgr().Wdata_mgr().Prop_mgr();
		return rslt.Init_obj(Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc, true, false));	// "false": wbase now always uses v2; PAGE:ja.w:東京競馬場; DATE:2015-07-28
	}
	public boolean GetEntityUrl(Scrib_proc_args args, Scrib_proc_rslt rslt)		{throw Err_.new_("wbase", "getEntityUrl not implemented", "url", core.Page().Url().To_str());}
	public boolean GetSetting(Scrib_proc_args args, Scrib_proc_rslt rslt)			{throw Err_.new_("wbase", "getSetting not implemented", "url", core.Page().Url().To_str());}
	public boolean RenderSnak(Scrib_proc_args args, Scrib_proc_rslt rslt)	{
		Xowe_wiki wiki = core.Wiki();
		Wdata_wiki_mgr wdata_mgr = wiki.Appe().Wiki_mgr().Wdata_mgr();
		String rv = Wdata_prop_val_visitor_.Render_snak(wdata_mgr, wiki, core.Page().Url_bry_safe(), args.Pull_kv_ary_safe(0));
		return rslt.Init_obj(rv);
	}
	public boolean RenderSnaks(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String rv = Wdata_prop_val_visitor_.Render_snaks(core.Wiki(), core.Page().Url_bry_safe(), args.Pull_kv_ary_safe(0));
		return rslt.Init_obj(rv);
	}
	public boolean GetEntityId(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Pull_bry(0);
		Xowe_wiki wiki = core.Wiki();
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
		byte[] rv = wiki.Appe().Wiki_mgr().Wdata_mgr().Qid_mgr.Get_or_null(wiki, ttl); if (rv == null) rv = Bry_.Empty;
		return rslt.Init_obj(rv);
	}
	public boolean GetUserLang(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.App().Usere().Lang().Key_bry());
	}
	public boolean GetDescription(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core); if (wdoc == null) return rslt.Init_ary_empty();
		return rslt.Init_obj(wdoc.Descr_list__get_or_fallback(core.Lang()));
	}
	public boolean ResolvePropertyId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core); if (wdoc == null) return rslt.Init_ary_empty();	// NOTE: prop should be of form "P123"; do not add "P"; PAGE:no.w:Anne_Enger; DATE:2015-10-27
		return rslt.Init_obj(wdoc.Label_list__get_or_fallback(core.Lang()));
	}
	public boolean GetSiteLinkPageName(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core); if (wdoc == null) return rslt.Init_ary_empty();	// NOTE: prop should be of form "P123"; do not add "P"; PAGE:no.w:Anne_Enger; DATE:2015-10-27
		Xow_domain_itm domain_itm = core.Wiki().Domain_itm();
		return rslt.Init_obj(wdoc.Slink_list__get_or_fallback(domain_itm.Abrv_wm()));
	}
	public boolean IncrementExpensiveFunctionCount(Scrib_proc_args args, Scrib_proc_rslt rslt) {return rslt.Init_obj(Keyval_.Ary_empty);}	// NOTE: for now, always return null (XOWA does not care about expensive parser functions)
	public boolean GetGlobalSiteId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.Wiki().Domain_abrv());	// ;siteGlobalID: This site's global ID (e.g. <code>'itwiki'</code>), as used in the sites table. Default: <code>$wgDBname</code>.; REF:/xtns/Wikibase/docs/options.wiki
	}
	private static Wdata_doc Get_wdoc_or_null(Scrib_proc_args args, Scrib_core core) {
		// get qid / pid from scrib_arg[0]; if none, return null;
		byte[] xid_bry = args.Pull_bry(0); if (Bry_.Len_eq_0(xid_bry)) return null;	// NOTE: some Modules do not pass in an argument; return early, else spurious warning "invalid qid for ttl" (since ttl is blank); EX:w:Module:Authority_control; DATE:2013-10-27

		// get wdoc
		Wdata_doc wdoc = core.Wiki().Appe().Wiki_mgr().Wdata_mgr().Doc_mgr.Get_by_xid_or_null(xid_bry); // NOTE: by_xid b/c Module passes just "p1" not "Property:P1"
		if (wdoc == null) Wdata_wiki_mgr.Log_missing_qid(core.Ctx(), xid_bry);
		return wdoc;
	}
	public boolean GetPropertyOrder(Scrib_proc_args args, Scrib_proc_rslt rslt)	{throw Err_.new_("wbase", "getPropertyOrder not implemented", "url", core.Page().Url().To_str());}
	public boolean OrderProperties(Scrib_proc_args args, Scrib_proc_rslt rslt)		{throw Err_.new_("wbase", "orderProperties not implemented", "url", core.Page().Url().To_str());}
}
