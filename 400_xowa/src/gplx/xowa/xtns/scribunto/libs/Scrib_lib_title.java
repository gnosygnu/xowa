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
import gplx.core.primitives.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.caches.*; import gplx.xowa.xtns.pfuncs.ttls.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.files.commons.*; import gplx.xowa.files.origs.*;
import gplx.xowa.apps.wms.apis.*;
import gplx.xowa.xtns.scribunto.procs.*;
import gplx.xowa.wikis.pages.redirects.*;
public class Scrib_lib_title implements Scrib_lib {
	public Scrib_lib_title(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_title(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.title.lua")
			, Keyval_.new_("thisTitle", "")					// NOTE: pass blank; will be updated by GetCurrentTitle
			, Keyval_.new_("NS_MEDIA", Xow_ns_.Tid__media)	// NOTE: MW passes down NS_MEDIA; this should be -2 on all wikis...
			);
		notify_page_changed_fnc = mod.Fncs_get_by_key("notify_page_changed");
		return mod;
	}	private Scrib_lua_proc notify_page_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_newTitle:							return NewTitle(args, rslt);
			case Proc_makeTitle:						return MakeTitle(args, rslt);
			case Proc_getExpensiveData:					return GetExpensiveData(args, rslt);
			case Proc_getUrl:							return GetUrl(args, rslt);
			case Proc_getContent:						return GetContent(args, rslt);
			case Proc_getFileInfo:						return GetFileInfo(args, rslt);
			case Proc_getCurrentTitle:					return GetCurrentTitle(args, rslt);
			case Proc_protectionLevels:					return ProtectionLevels(args, rslt);
			case Proc_cascadingProtection:				return CascadingProtection(args, rslt);
			case Proc_redirectTarget:					return RedirectTarget(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_newTitle = 0, Proc_makeTitle = 1, Proc_getExpensiveData = 2, Proc_getUrl = 3, Proc_getContent = 4, Proc_getFileInfo = 5, Proc_getCurrentTitle = 6, Proc_protectionLevels = 7, Proc_cascadingProtection = 8, Proc_redirectTarget = 9;
	public static final String 
	  Invk_newTitle = "newTitle", Invk_getExpensiveData = "getExpensiveData", Invk_makeTitle = "makeTitle"
	, Invk_getUrl = "getUrl", Invk_getContent = "getContent", Invk_getFileInfo = "getFileInfo", Invk_getCurrentTitle = "getCurrentTitle"
	, Invk_protectionLevels = "protectionLevels", Invk_cascadingProtection = "cascadingProtection"
	, Invk_redirectTarget = "redirectTarget"
	;
	private static final    String[] Proc_names = String_.Ary(Invk_newTitle, Invk_makeTitle, Invk_getExpensiveData, Invk_getUrl, Invk_getContent, Invk_getFileInfo, Invk_getCurrentTitle, Invk_protectionLevels, Invk_cascadingProtection, Invk_redirectTarget);
	public boolean NewTitle(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		if (args.Len() == 0) return rslt.Init_obj(null);	// invalid title, return null; EX:{{#invoke:Message box|fmbox}} DATE:2015-03-04
		byte[] ttl_bry = args.Xstr_bry_or_null(0);			// NOTE: Pull_bry fails if caller passes int; PAGE:de.w:Wikipedia:Lua/Modul/Pinging/Test/recipients; DATE:2016-04-21
		Object ns_obj = args.Cast_obj_or_null(1);
		Xowe_wiki wiki = core.Wiki();
		byte[] ns_bry = null;
		if (ns_obj != null) {
			ns_bry = Parse_ns(wiki, ns_obj); if (ns_bry == null) throw Err_.new_wo_type("unknown ns", "ns", Object_.Xto_str_strict_or_empty(ns_obj));
		}
		if (ns_bry != null) {
			Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
			ttl_bry = bfr.Add(ns_bry).Add_byte(Byte_ascii.Colon).Add(ttl_bry).To_bry_and_rls();
		}
		Xoa_ttl ttl = Xoa_ttl.Parse(core.Wiki(), ttl_bry);
		if (ttl == null) return rslt.Init_obj(null);	// invalid title; exit; matches MW logic
		return rslt.Init_obj(GetInexpensiveTitleData(ttl));
	}
	public void Notify_page_changed() {if (notify_page_changed_fnc != null) core.Interpreter().CallFunction(notify_page_changed_fnc.Id(), Keyval_.Ary_empty);}
	public boolean GetUrl(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xowe_wiki wiki = core.Wiki();
		byte[] ttl_bry = args.Pull_bry(0);
		byte[] url_func_bry = args.Pull_bry(1);
		Object url_func_obj = url_func_hash.Get_by(url_func_bry);
		if (url_func_obj == null) throw Err_.new_wo_type("url_function is not valid", "url_func", String_.new_u8(url_func_bry));
		byte url_func_tid = ((Byte_obj_val)url_func_obj).Val();
		byte[] qry_bry = args.Extract_qry_args(wiki, 2);
		// byte[] proto = Scrib_kv_utl_.Val_to_bry_or(values, 3, null);	// NOTE: Scribunto has more conditional logic around argument 2 and setting protocols; DATE:2014-07-07
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry); if (ttl == null) return rslt.Init_obj(null);
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		//if (url_func_tid == Pfunc_urlfunc.Tid_full) {
		//	if (proto == null) proto = Proto_relative;
		//	Object proto_obj = proto_hash.Get_by(proto); if (proto_obj == null) throw Err_.new_fmt_("protocol is not valid: {0}", proto);
		//	//qry_bry = (byte[])proto_obj;
		//	byte proto_tid = ((Byte_obj_val)proto_obj).Val();
		//	bfr.Add();
		//}
		Pfunc_urlfunc.UrlString(core.Ctx(), url_func_tid, false, ttl_bry, bfr, qry_bry);
		return rslt.Init_obj(bfr.To_str_and_rls());
	}
	private static final    Hash_adp_bry url_func_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("fullUrl", Pfunc_urlfunc.Tid_full)
	.Add_str_byte("localUrl", Pfunc_urlfunc.Tid_local)
	.Add_str_byte("canonicalUrl", Pfunc_urlfunc.Tid_canonical);
	// private static final    byte[] Proto_relative = Bry_.new_a7("relative");
	// private static final    Hash_adp_bry proto_hash = Hash_adp_bry.ci_a7().Add_str_obj("http", Bry_.new_a7("http://")).Add_str_obj("https", Bry_.new_a7("https://")).Add_str_obj("relative", Bry_.new_a7("//")).Add_str_obj("canonical", Bry_.new_a7("1"));
	private byte[] Parse_ns(Xowe_wiki wiki, Object ns_obj) {
		if (Type_adp_.Eq_typeSafe(ns_obj, String.class))
			return Bry_.new_u8(String_.cast(ns_obj));
		else {
			int ns_id = Int_.cast(ns_obj);
			Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
			if (ns != null)
				return ns.Name_db();
		}
		return null;
	}
	public boolean MakeTitle(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xowe_wiki wiki = core.Wiki();
		byte[] ns_bry = Parse_ns(wiki, args.Cast_obj_or_null(0));
		String ttl_str = args.Pull_str(1);
		String anchor_str = args.Cast_str_or_null(2);
		String xwiki_str = args.Cast_str_or_null(3);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_k004();
		if (xwiki_str != null) tmp_bfr.Add_str_u8(xwiki_str).Add_byte(Byte_ascii.Colon);		
		if (Bry_.Len_gt_0(ns_bry))	// only prefix ns if available; EX:"Template:Title"; else will get ":Title"; DATE:2014-10-30
			tmp_bfr.Add(ns_bry).Add_byte(Byte_ascii.Colon);
		tmp_bfr.Add_str_u8(ttl_str);
		if (anchor_str != null) tmp_bfr.Add_byte(Byte_ascii.Hash).Add_str_u8(anchor_str);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, tmp_bfr.To_bry_and_rls());
		if (ttl == null) return rslt.Init_obj(null);	// invalid title; exit;
		return rslt.Init_obj(GetInexpensiveTitleData(ttl));
	}
	public boolean GetExpensiveData(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Pull_bry(0);
		Xoa_ttl ttl = Xoa_ttl.Parse(core.Wiki(), ttl_bry);
		if (ttl == Xoa_ttl.Null) return rslt.Init_null();
		// TODO_OLD: MW does extra logic here to cache ttl in ttl cache to avoid extra title lookups
		boolean ttl_exists = false, ttl_redirect = false; int ttl_id = 0;
		Xowd_page_itm db_page = Xowd_page_itm.new_tmp();
		ttl_exists = core.Wiki().Db_mgr().Load_mgr().Load_by_ttl(db_page, ttl.Ns(), ttl.Page_db());
		if (ttl_exists) {
			ttl_redirect = db_page.Redirected();
			ttl_id = db_page.Id();
		}
		Keyval[] rv = new Keyval[4];
		rv[ 0] = Keyval_.new_("isRedirect"			, ttl_redirect);						// title.isRedirect
		rv[ 1] = Keyval_.new_("id"					, ttl_id);								// $title->getArticleID(),
		rv[ 2] = Keyval_.new_("contentModel"		, Key_wikitext);						// $title->getContentModel(); see Defines.php and CONTENT_MODEL_
		rv[ 3] = Keyval_.new_("exists"				, ttl_exists);							// $ret['id'] > 0; TODO_OLD: if Special: check regy of implemented pages
		return rslt.Init_obj(rv);
	}
	public boolean GetFileInfo(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// init; get ttl
		byte[] ttl_bry = args.Pull_bry(0);
		Xowe_wiki wiki = core.Wiki();
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
		if (	ttl == null
			|| !ttl.Ns().Id_is_file_or_media()
			) return rslt.Init_obj(GetFileInfo_absent);
		if (ttl.Ns().Id_is_media()) ttl = Xoa_ttl.Parse(wiki, Xow_ns_.Tid__file, ttl.Page_db());	// if [[Media:]] change to [[File:]]; theoretically, this should be changed in Get_page, but not sure if I want to put this logic that low; DATE:2014-01-07

		// get file info from db / wmf
		wiki.File_mgr().Init_file_mgr_by_load(wiki);
		Xof_orig_itm itm = wiki.File__orig_mgr().Find_by_ttl_or_null(ttl.Page_db());	// NOTE: MW registers image if deleted; XOWA doesn't register b/c needs width / height also, not just image name
		Keyval[] rv = itm == Xof_orig_itm.Null
			? GetFileInfo_absent
			: Keyval_.Ary
			( Keyval_.new_("exists"		, true)
			, Keyval_.new_("width"		, itm.W())
			, Keyval_.new_("height"		, itm.H())
			, Keyval_.new_("pages"		, null)	// TODO_OLD: get pages info
			);
		return rslt.Init_obj(rv);
	}	private static final    Keyval[] GetFileInfo_absent = Keyval_.Ary(Keyval_.new_("exists", false), Keyval_.new_("width", 0), Keyval_.new_("height", 0));	// NOTE: must supply non-null values for w / h, else Modules will fail with nil errors; PAGE:pl.w:Andrespol DATE:2016-08-01
	public boolean GetContent(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Pull_bry(0);
		byte[] rv = GetContentInternal(ttl_bry);
		return rv == null ? rslt.Init_obj(null) : rslt.Init_obj(String_.new_u8(rv));
	}
	public boolean GetCurrentTitle(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_obj(GetInexpensiveTitleData(core.Page().Ttl()));
	}
	public boolean ProtectionLevels(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_obj(protectionLevels_dflt);
	}
	private byte[] GetContentInternal(byte[] ttl_bry) {
		Xowe_wiki wiki = core.Wiki();
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry); if (ttl == null) return null;
		Xow_page_cache_itm page_itm = wiki.Cache_mgr().Page_cache().Get_or_load_as_itm_2(ttl);
		byte[] rv = null;
		if (page_itm != null) {
			byte[] redirected_src = page_itm.Wtxt__redirect();
			if (redirected_src != null) {						// page is redirect; use its src, not its target's src; DATE:2014-07-11
				rv = redirected_src;
				core.Frame_parent().Rslt_is_redirect_(true);	// flag frame as redirect, so that \n won't be prepended; EX:"#REDIRECT" x> "\n#REDIRECT"
			}
			else
				rv = page_itm.Wtxt__direct();
		}
		return rv;
	}
	private static final    Keyval[] protectionLevels_dflt = Keyval_.Ary(Keyval_.new_("move", Keyval_.int_(1, "sysop")), Keyval_.new_("edit", Keyval_.int_(1, "sysop")));	// protectionLevels are stored in different table which is currently not mirrored; per en.w:Module:Effective_protection_level; DATE:2014-04-09; 2016-09-07
	public boolean CascadingProtection(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Pull_bry(0);
		Xowe_wiki wiki = core.Wiki();
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry); if (ttl == null) return rslt.Init_obj(null);
		return rslt.Init_obj(CascadingProtection_rv);
	}
	public boolean RedirectTarget(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Pull_bry(0);
		Xowe_wiki wiki = core.Wiki();
		Xoa_ttl ttl = wiki.Ttl_parse(ttl_bry);
		Xoae_page page = Xoae_page.New(wiki, ttl);
		wiki.Data_mgr().Load_from_db(page, ttl.Ns(), ttl, false);
		return page.Redirect_trail().Itms__len() == 0 ? rslt.Init_obj(null) : rslt.Init_obj(GetInexpensiveTitleData(page.Ttl()));
	}
	public static final    Keyval[] CascadingProtection_rv = Keyval_.Ary(Keyval_.new_("sources", Keyval_.Ary_empty), Keyval_.new_("restrictions", Keyval_.Ary_empty));	// changed sources from "false" to "{}"; DATE:2016-09-09
	private Keyval[] GetInexpensiveTitleData(Xoa_ttl ttl) {
		Xow_ns ns = ttl.Ns();
		boolean ns_file_or_media = ns.Id_is_file_or_media(), ns_special = ns.Id_is_special();
		int rv_len = 7, rv_idx = 7;
		if (ns_special) ++rv_len;
		if (!ns_file_or_media) ++rv_len;
		Xow_xwiki_itm xwiki_itm = ttl.Wik_itm();
		String xwiki_str = xwiki_itm == null ? "" : xwiki_itm.Key_str();
		Keyval[] rv = new Keyval[rv_len];
		rv[ 0] = Keyval_.new_("isLocal"				, true);										// title.isLocal; NOTE: always true; passing "http:" also returns true; not sure how to handle "Interwiki::fetch( $this->mInterwiki )->isLocal()"
		rv[ 1] = Keyval_.new_("interwiki"			, xwiki_str);									// $title->getInterwiki(),
		rv[ 2] = Keyval_.new_("namespace"		, ns.Id());										// $ns,
		rv[ 3] = Keyval_.new_("nsText"				, ns.Name_db());								// $title->getNsText(), NOTE: must be local language's version; Russian "Шаблон" not English "Template"; PAGE:ru.w:Королевство_Нидерландов DATE:2016-11-23
		rv[ 4] = Keyval_.new_("text"				, ttl.Page_txt());								// $title->getText(),
		rv[ 5] = Keyval_.new_("fragment"			, ttl.Anch_txt());								// $title->getFragment(),
		rv[ 6] = Keyval_.new_("thePartialUrl"		, ttl.Page_db());								// $title->getPartialUrl(),
		if (ns_special)
			rv[rv_idx++] = Keyval_.new_("exists"	, false);										// TODO_OLD: lookup specials
		if (!ns_file_or_media)
			rv[rv_idx++] = Keyval_.new_("file"		, false);										// REF.MW: if ( $ns !== NS_FILE && $ns !== NS_MEDIA )  $ret['file'] = false;
		return rv;
	}
	public static final String Key_wikitext = "wikitext";
}
