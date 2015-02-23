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
import gplx.json.*; import gplx.xowa.xtns.wdatas.*; import gplx.xowa.xtns.wdatas.parsers.*;	
public class Scrib_lib_wikibase implements Scrib_lib {
	public Scrib_lib_wikibase(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.wikibase.lua"));
		notify_page_changed_fnc = mod.Fncs_get_by_key("notify_page_changed");
		return mod;
	}	private Scrib_lua_proc notify_page_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getEntity:						return GetEntity(args, rslt);
			case Proc_getEntityId:						return GetEntityId(args, rslt);
			case Proc_getGlobalSiteId:					return GetGlobalSiteId(args, rslt);
			default: throw Err_.unhandled(key);
		}
	}
	private static final int Proc_getEntity = 0, Proc_getEntityId = 1, Proc_getGlobalSiteId = 2;
	public static final String Invk_getEntity = "getEntity", Invk_getEntityId = "getEntityId", Invk_getGlobalSiteId = "getGlobalSiteId";
	private static final String[] Proc_names = String_.Ary(Invk_getEntity, Invk_getEntityId, Invk_getGlobalSiteId);
	public void Notify_page_changed() {if (notify_page_changed_fnc != null) core.Interpreter().CallFunction(notify_page_changed_fnc.Id(), KeyVal_.Ary_empty);}
	public boolean GetEntity(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Pull_bry(0);
		if (Bry_.Len_eq_0(ttl_bry)) return rslt.Init_ary_empty();	// NOTE: some Modules do not pass in an argument; return early, else spurious warning "invalid qid for ttl" (since ttl is blank); EX:w:Module:Authority_control; DATE:2013-10-27
		boolean base_0 = args.Pull_bool(1);
		Xowe_wiki wiki = core.Wiki();
		Wdata_wiki_mgr wdata_mgr = wiki.Appe().Wiki_mgr().Wdata_mgr();
		Wdata_doc wdoc = wdata_mgr.Pages_get_by_ttl_name(ttl_bry); 
		if (wdoc == null) {Wdata_wiki_mgr.Log_missing_qid(core.Ctx(), ttl_bry); return rslt.Init_ary_empty();}
		return rslt.Init_obj(Scrib_lib_wikibase_srl.Srl(wdoc, true, base_0));
	}
	public boolean GetEntityId(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Pull_bry(0);
		Xowe_wiki wiki = core.Wiki();
		Wdata_wiki_mgr wdata_mgr = wiki.Appe().Wiki_mgr().Wdata_mgr();
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ttl_bry);
		byte[] rv = wdata_mgr.Qids_get(wiki, ttl); if (rv == null) rv = Bry_.Empty;
		return rslt.Init_obj(rv);
	}
	public boolean GetGlobalSiteId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.Wiki().Domain_abrv());	// ;siteGlobalID: This site's global ID (e.g. <code>'itwiki'</code>), as used in the sites table. Default: <code>$wgDBname</code>.; REF:/xtns/Wikibase/docs/options.wiki
	}
}
