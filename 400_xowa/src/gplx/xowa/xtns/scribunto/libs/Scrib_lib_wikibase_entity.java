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
import gplx.xowa.xtns.wbases.*;
import gplx.langs.jsons.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.stores.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_wikibase_entity implements Scrib_lib { // REF.MW:https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/client/includes/DataAccess/Scribunto/Scribunto_LuaWikibaseEntityLibrary.php
	private Scrib_core core;
	private Wdata_wiki_mgr wdata_mgr;
	public Scrib_lib_wikibase_entity(Scrib_core core) {
		this.core = core;
	}
	public String Key() {return "mw.wikibase.entity";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {
		procs.Init_by_lib(this, Proc__names);
		this.wdata_mgr = core.App().Wiki_mgr().Wdata_mgr();
		return this;
	}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_wikibase_entity(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.wikibase.entity.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc__getGlobalSiteId:                             return GetGlobalSiteId(args, rslt);
			case Proc__getLanguageCode:                             return GetLanguageCode(args, rslt);
			case Proc__formatStatements:                            return FormatStatements(args, rslt);
			case Proc__formatPropertyValues:                        return FormatPropertyValues(args, rslt);
			case Proc__addStatementUsage:                           return AddStatementUsage(args, rslt);
			case Proc__addLabelUsage:                               return AddLabelUsage(args, rslt);
			case Proc__addDescriptionUsage:                         return AddDescriptionUsage(args, rslt);
			case Proc__addSiteLinksUsage:                           return AddSiteLinksUsage(args, rslt);
			case Proc__addOtherUsage:                               return AddOtherUsage(args, rslt);
			case Proc__getSetting:                                  return GetSetting(args, rslt);
			case Proc__incrementStatsKey:                           return IncrementStatsKey(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	public boolean GetGlobalSiteId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.Wiki().Domain_abrv());	// ;siteGlobalID: This site's global ID (e.g. <code>'itwiki'</code>), as used in the sites table. Default: <code>$wgDBname</code>.; REF:/xtns/Wikibase/docs/options.wiki
	}
	public boolean GetLanguageCode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_obj(core.Wiki().Lang().Key_bry());
	}
	public boolean FormatStatements(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		throw Err_.new_unimplemented();
//			return FormatPropertyValues(args, rslt); // NOTE: implementation should be like Visit_entity but return [[A]] instead of <a href='A'>
	}
	public boolean FormatPropertyValues(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// get qid / pid
		byte[] qid = args.Pull_bry(0);
		byte[] pid = args.Pull_bry(1);

		// get wdata_mgr and lang
		Xoae_app app = core.App(); Xowe_wiki wiki = core.Wiki();
		Wdata_wiki_mgr wdata_mgr = app.Wiki_mgr().Wdata_mgr();
		byte[] lang = wiki.Wdata_wiki_lang();

		// get wdoc
		Wdata_doc wdoc = wdata_mgr.Doc_mgr.Get_by_loose_id_or_null(qid);
		if (wdoc == null) {
			Wdata_wiki_mgr.Log_missing_qid(core.Ctx(), "FormatPropertyValues", qid);
			return rslt.Init_str_empty(); // NOTE: return empty String, not nil; PAGE:fr.s:Henri_Bergson; DATE:2014-08-13
		}

		// get pid_int
		int pid_int = Wbase_pid.To_int_or_null(pid);												// parse as num; EX: p123 -> 123; PAGE:hr.w:Hepatitis DATE:2015-11-08
		if (pid_int == Wbase_pid.Id_null) pid_int = wdata_mgr.Pid_mgr.Get_pid_or_neg1(lang, pid);	// parse as name; EX: name > 123
		if (pid_int == Wbase_pid.Id_null) return rslt.Init_str_empty();

		// get prop_grp
		Wbase_claim_grp prop_grp = wdoc.Get_claim_grp_or_null(pid_int);
		if (prop_grp == null)
			return rslt.Init_str_empty();

		// print it
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		wdata_mgr.Resolve_to_bfr(bfr, prop_grp, lang, Bool_.N);
		return rslt.Init_obj(bfr.To_bry_and_rls());
	}
	public boolean AddStatementUsage(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_null();
	}
	public boolean AddLabelUsage(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_null();
	}
	public boolean AddDescriptionUsage(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_null();
	}
	public boolean AddSiteLinksUsage(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_null();
	}
	public boolean AddOtherUsage(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_null();
	}
	public boolean GetSetting(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return Scrib_lib_wikibase.GetSetting(args, rslt, core, wdata_mgr);
	}
	public boolean IncrementStatsKey(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_null();
	}
	private static final int 
	  Proc__getGlobalSiteId        =  0
	, Proc__getLanguageCode        =  1
	, Proc__formatStatements       =  2
	, Proc__formatPropertyValues   =  3
	, Proc__addStatementUsage      =  4
	, Proc__addLabelUsage          =  5
	, Proc__addDescriptionUsage    =  6
	, Proc__addSiteLinksUsage      =  7
	, Proc__addOtherUsage          =  8
	, Proc__getSetting             =  9
	, Proc__incrementStatsKey      = 10
	;
	public static final String 
	  Invk__getGlobalSiteId        = "getGlobalSiteId"
	, Invk__getLanguageCode        = "getLanguageCode"
	, Invk__formatStatements       = "formatStatements"
	, Invk__formatPropertyValues   = "formatPropertyValues"
	, Invk__addStatmentUsage       = "addStatementUsage"
	, Invk__addLabelUsage          = "addLabelUsage"
	, Invk__addDescriptionUsage    = "addDescriptionUsage"
	, Invk__addSiteLinkUsage       = "addSiteLinkUsage"
	, Invk__addOtherUsage          = "addOtherUsage"
	, Invk__getSetting             = "getSetting"
	, Invk__incrementStatsKey      = "incrementStatsKey"
	;
	private static final    String[] Proc__names = String_.Ary
	( Invk__getGlobalSiteId
	, Invk__getLanguageCode
	, Invk__formatStatements
	, Invk__formatPropertyValues
	, Invk__addStatmentUsage
	, Invk__addLabelUsage
	, Invk__addDescriptionUsage
	, Invk__addSiteLinkUsage
	, Invk__addOtherUsage
	, Invk__getSetting
	, Invk__incrementStatsKey
	);
}
