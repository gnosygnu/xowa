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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.xtns.wdatas.*;
import gplx.json.*; import gplx.xowa.xtns.wdatas.core.*;
public class Scrib_lib_wikibase_entity implements Scrib_lib {
	public Scrib_lib_wikibase_entity(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.wikibase.entity.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getGlobalSiteId:								return GetGlobalSiteId(args, rslt);
			case Proc_formatPropertyValues:							return FormatPropertyValues(args, rslt);
			default: throw Exc_.new_unhandled(key);
		}
	}
	private static final int Proc_getGlobalSiteId = 0, Proc_formatPropertyValues = 1;
	public static final String Invk_getGlobalSiteId = "getGlobalSiteId", Invk_formatPropertyValues = "formatPropertyValues";
	private static final String[] Proc_names = String_.Ary(Invk_getGlobalSiteId, Invk_formatPropertyValues);
	public boolean GetGlobalSiteId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.Wiki().Domain_abrv());	// ;siteGlobalID: This site's global ID (e.g. <code>'itwiki'</code>), as used in the sites table. Default: <code>$wgDBname</code>.; REF:/xtns/Wikibase/docs/options.wiki
	}
	public boolean FormatPropertyValues(Scrib_proc_args args, Scrib_proc_rslt rslt)	{
		byte[] qid = args.Pull_bry(0);
		byte[] pid = args.Pull_bry(1);
		Xoae_app app = core.App(); Xowe_wiki wiki = core.Wiki();
		Wdata_wiki_mgr wdata_mgr = app.Wiki_mgr().Wdata_mgr();
		byte[] lang = wiki.Wdata_wiki_lang();
		Wdata_doc wdoc = wdata_mgr.Pages_get(qid); if (wdoc == null) {Wdata_wiki_mgr.Log_missing_qid(core.Ctx(), qid); return rslt.Init_str_empty();}	// NOTE: return empty String, not nil; PAGE:fr.s:Henri_Bergson; DATE:2014-08-13
		int pid_int = wdata_mgr.Pids_get(lang, pid); if (pid_int == Wdata_wiki_mgr.Pid_null) return rslt.Init_str_empty();
		Wdata_claim_grp prop_grp = wdoc.Claim_list_get(pid_int); if (prop_grp == null) return rslt.Init_str_empty();
		Bry_bfr bfr = app.Utl__bfr_mkr().Get_b512();
		wdata_mgr.Resolve_to_bfr(bfr, prop_grp, lang);
		return rslt.Init_obj(bfr.To_bry_and_rls());
	}
}