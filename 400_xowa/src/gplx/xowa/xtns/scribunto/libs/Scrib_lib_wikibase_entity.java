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
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_wikibase_entity implements Scrib_lib {
	public Scrib_lib_wikibase_entity(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_wikibase_entity(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.wikibase.entity.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getGlobalSiteId:								return GetGlobalSiteId(args, rslt);
			case Proc_getLanguageCode:								return GetLanguageCode(args, rslt);
//				case Proc_formatStatements:								return FormatStatements(args, rslt);
			case Proc_formatPropertyValues:							return FormatPropertyValues(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_getGlobalSiteId = 0, Proc_getLanguageCode = 1, Proc_formatPropertyValues = 2;
	public static final String Invk_getGlobalSiteId = "getGlobalSiteId", Invk_getLanguageCode = "getLanguageCode", Invk_formatPropertyValues = "formatPropertyValues";
	private static final    String[] Proc_names = String_.Ary(Invk_getGlobalSiteId, Invk_getLanguageCode, Invk_formatPropertyValues);
	public boolean GetGlobalSiteId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.Wiki().Domain_abrv());	// ;siteGlobalID: This site's global ID (e.g. <code>'itwiki'</code>), as used in the sites table. Default: <code>$wgDBname</code>.; REF:/xtns/Wikibase/docs/options.wiki
	}
	public boolean GetLanguageCode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_obj(core.Wiki().Lang().Key_bry());
	}
//		public boolean FormatStatements(Scrib_proc_args args, Scrib_proc_rslt rslt) {
//			return FormatPropertyValues(args, rslt); // NOTE: implementation should be like Visit_entity but return [[A]] instead of <a href='A'>
//		}
	public boolean FormatPropertyValues(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// get qid / pid
		byte[] qid = args.Pull_bry(0);
		byte[] pid = args.Pull_bry(1);

		// get wdata_mgr and lang
		Xoae_app app = core.App(); Xowe_wiki wiki = core.Wiki();
		Wdata_wiki_mgr wdata_mgr = app.Wiki_mgr().Wdata_mgr();
		byte[] lang = wiki.Wdata_wiki_lang();

		// get wdoc
		Wdata_doc wdoc = wdata_mgr.Doc_mgr.Get_by_bry_or_null(qid);
		if (wdoc == null) {
			Wdata_wiki_mgr.Log_missing_qid(core.Ctx(), qid);
			return rslt.Init_str_empty(); // NOTE: return empty String, not nil; PAGE:fr.s:Henri_Bergson; DATE:2014-08-13
		}

		// get pid_int
		int pid_int = Wbase_pid_mgr.To_int_or_null(pid);										// parse as num; EX: p123 -> 123; PAGE:hr.w:Hepatitis DATE:2015-11-08
		if (pid_int == Wdata_wiki_mgr.Pid_null) pid_int = wdata_mgr.Pid_mgr.Get_or_null(lang, pid);		// parse as name; EX: name > 123
		if (pid_int == Wdata_wiki_mgr.Pid_null) return rslt.Init_str_empty();

		// get prop_grp
		Wbase_claim_grp prop_grp = wdoc.Claim_list_get(pid_int);
		if (prop_grp == null)
			return rslt.Init_str_empty();

		// print it
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		wdata_mgr.Resolve_to_bfr(bfr, prop_grp, lang, Bool_.N);
		return rslt.Init_obj(bfr.To_bry_and_rls());
	}
}
