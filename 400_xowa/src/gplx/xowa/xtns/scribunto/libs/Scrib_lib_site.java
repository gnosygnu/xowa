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
package gplx.xowa.xtns.scribunto.libs;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.wikis.ctgs.Xoax_ctg_addon;
import gplx.xowa.addons.wikis.ctgs.Xoctg_ctg_itm;
import gplx.xowa.langs.msgs.Xol_msg_itm_;
import gplx.xowa.wikis.data.site_stats.Xowd_site_stats_mgr;
import gplx.xowa.wikis.domains.Xow_domain_itm;
import gplx.xowa.wikis.domains.Xow_domain_itm_;
import gplx.xowa.wikis.domains.Xow_domain_tid_;
import gplx.xowa.wikis.metas.Xow_wiki_props;
import gplx.xowa.wikis.nss.Xow_ns;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.wikis.nss.Xow_ns_mgr;
import gplx.xowa.wikis.xwikis.interwikis.Xow_interwiki_itm;
import gplx.xowa.wikis.xwikis.interwikis.Xow_interwiki_map;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Scrib_lib_site implements Scrib_lib {
	public Scrib_lib_site(Scrib_core core) {this.core = core;} private final Scrib_core core;
	public String Key() {return "mw.site";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_site(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.site.lua"));// NOTE: info not passed by default; rely on Init_for_wiki
		notify_wiki_changed_fnc = mod.Fncs_get_by_key("notify_wiki_changed");
		return mod;
	}	private Scrib_lua_proc notify_wiki_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private final Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getNsIndex:						return GetNsIndex(args, rslt);
			case Proc_pagesInCategory:					return PagesInCategory(args, rslt);
			case Proc_pagesInNs:						return PagesInNs(args, rslt);
			case Proc_usersInGroup:						return UsersInGroup(args, rslt);
			case Proc_interwikiMap:						return InterwikiMap(args, rslt);
			case Proc_init_site_for_wiki:				return Init_site_for_wiki(args, rslt);
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	private static final int Proc_getNsIndex = 0, Proc_pagesInCategory = 1, Proc_pagesInNs = 2, Proc_usersInGroup = 3, Proc_interwikiMap = 4, Proc_init_site_for_wiki = 5;
	public static final String Invk_getNsIndex = "getNsIndex", Invk_pagesInCategory = "pagesInCategory", Invk_pagesInNs = "pagesInName"+"space", Invk_usersInGroup = "usersInGroup", Invk_interwikiMap = "interwikiMap", Invk_init_site_for_wiki = "init_site_for_wiki";
	private static final String[] Proc_names = StringUtl.Ary(Invk_getNsIndex, Invk_pagesInCategory, Invk_pagesInNs, Invk_usersInGroup, Invk_interwikiMap, Invk_init_site_for_wiki);
	public boolean GetNsIndex(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ns_name = args.Pull_bry(0);
		Object ns_obj = core.Wiki().Ns_mgr().Names_get_or_null(ns_name, 0, ns_name.length);
		return ns_obj == null ? rslt.Init_ary_empty() : rslt.Init_obj(((Xow_ns)ns_obj).Id());
	}
	public boolean PagesInCategory(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// args
		byte[] ctg_name = args.Pull_bry(0);
		String ctg_type = args.Cast_str_or(1, "all");

		// get ttl
		Xoa_ttl ctg_ttl = core.Wiki().Ttl_parse(Xow_ns_.Tid__category, ctg_name);
		if (ctg_ttl == null) return rslt.Init_obj(0);
		// replace space with underlines (and possibly other normalizations) else TOC will not show; PAGE:en.wiktionary.org/wiki/Category:English_conjunctions; ISSUE#:557; DATE:2019-08-22
		ctg_name = ctg_ttl.Page_db();

		Xoax_ctg_addon ctg_mgr = Xoax_ctg_addon.Get(core.Wiki());
		Xoctg_ctg_itm ctg_itm = ctg_mgr.Itms__get_or_null(ctg_name);
		if (ctg_itm == null) {
			core.Increment_expensive_function_count();				
			ctg_itm = ctg_mgr.Itms__load(ctg_name);
		}
		if (StringUtl.Eq(ctg_type, "*")) {
			KeyVal[] rv = new KeyVal[4];
			rv[0] = KeyVal.NewStr("all", ctg_itm.All);
			rv[1] = KeyVal.NewStr("pages", ctg_itm.Pages);
			rv[2] = KeyVal.NewStr("subcats", ctg_itm.Subcs);
			rv[3] = KeyVal.NewStr("files", ctg_itm.Files);
			return rslt.Init_obj(rv);
		}
		else {
			int rv_count = 0;
			if		(StringUtl.Eq(ctg_type, "all"))		rv_count = ctg_itm.All;
			else if (StringUtl.Eq(ctg_type, "pages"))		rv_count = ctg_itm.Pages;
			else if (StringUtl.Eq(ctg_type, "subcats"))	rv_count = ctg_itm.Subcs;
			else if (StringUtl.Eq(ctg_type, "files"))		rv_count = ctg_itm.Files;
			else										throw Scrib_err.Make__err__arg(Invk_pagesInCategory, 2, ctg_type, "one of '*', 'all', 'pages', 'subcats', or 'files'");
			return rslt.Init_obj(rv_count);
		}
	}
	public boolean PagesInNs(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		int ns_id = args.Pull_int(0);
		Xow_ns ns = core.Wiki().Ns_mgr().Ids_get_or_null(ns_id);
		int ns_count = ns == null ? 0 : ns.Count();
		return rslt.Init_obj(ns_count);
	}
	public boolean UsersInGroup(Scrib_proc_args args, Scrib_proc_rslt rslt) {	// TODO_OLD.9: get user_groups table
		// byte[] grp_name = args.Pull_bry(0);
		return rslt.Init_obj(0);
	}
	public boolean InterwikiMap(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		String filter = args.Cast_str_or_null(0);
		int local = -1;
		if		(StringUtl.Eq(filter, "local"))
			local = 1;
		else if (StringUtl.Eq(filter, "!local"))
			local = 0;
		else if (filter != null)
			throw ErrUtl.NewArgs("bad argument #1 to 'interwikiMap' (unknown filter '$filter')", "filter", filter);
		Hash_adp misc_cache = core.Wiki().Cache_mgr().Misc_cache();
		String cache_key = "scribunto.interwikimap." + core.Wiki().Domain_str() + "." + filter;
		KeyVal[] rv = (KeyVal[])misc_cache.GetByOrNull(cache_key);
		if (rv == null) {
			Xow_interwiki_map interwiki_map = core.Wiki().Xwiki_mgr().Interwiki_map();
			int interwiki_map_len = interwiki_map.Len();
			List_adp list = List_adp_.New();
			for (int i = 0; i < interwiki_map_len; i++) {
				Xow_interwiki_itm itm = interwiki_map.Get_at(i);
				Xow_domain_itm domain = Xow_domain_itm_.parse(itm.Domain());
				boolean itm_is_local = domain.Domain_type_id() != Xow_domain_tid_.Itm__other.Tid(); // NOTE: define local as anything with an "other" domain; EX: "en.wikipedia.org" -> Wikipedia; "toollabs.org" -> Other; was itm.Offline(); DATE:2017-04-01
				if (local == 1 && !itm_is_local) continue;
				if (local == 0 &&  itm_is_local) continue;
				String prefix = StringUtl.NewU8(itm.Key());
				list.Add(KeyVal.NewStr(prefix, InterwikiMap_itm(prefix, itm_is_local, StringUtl.NewU8(itm.Url()))));
			}
			rv = (KeyVal[])list.ToAryAndClear(KeyVal.class);
			misc_cache.Add(cache_key, rv);
		}
		return rslt.Init_obj(rv);
	}
	public static KeyVal[] InterwikiMap_itm(String prefix, boolean itm_is_local, String url) {
		// NOTE: wiki.core_db.xowa_cfg should be upgraded to store isProtocolRelative, isLocal and other properties from WMF_API. DATE:2017-04-01
		boolean is_extralanguage_link = false;
		int rv_len = 7;
		if (is_extralanguage_link) rv_len += 2;
		boolean url_is_relative = StringUtl.HasAtBgn(url, "//"); // effectively false since url is built up programatically
		KeyVal[] rv = new KeyVal[rv_len];
		rv[ 0] = KeyVal.NewStr("prefix"					, prefix);
		rv[ 1] = KeyVal.NewStr("url"						, url);								// wfExpandUrl( $row['iw_url'], PROTO_RELATIVE ),
		rv[ 2] = KeyVal.NewStr("isProtocolRelative"		, url_is_relative);					// substr( $row['iw_url'], 0, 2 ) === '//',
		rv[ 3] = KeyVal.NewStr("isLocal"					, itm_is_local);					// isset( $row['iw_local'] ) && $row['iw_local'] == '1',
		rv[ 4] = KeyVal.NewStr("isTranscludable"			, BoolUtl.N);							// isset( $row['iw_trans'] ) && $row['iw_trans'] == '1',
		rv[ 5] = KeyVal.NewStr("isCurrentWiki"			, BoolUtl.N);							// in_array( $prefix, $wgLocalInterwikis ),
		rv[ 6] = KeyVal.NewStr("isExtraLanguageLink"		, is_extralanguage_link);			// in_array( $prefix, $wgExtraInterlanguageLinkPrefixes ),
		if (is_extralanguage_link) {
			rv[7] = KeyVal.NewStr("displayText"			, "displayText_TODO");				// $displayText = wfMessage( "interlanguage-link-$prefix" ); if ( !$displayText->isDisabled() ) ...
			rv[7] = KeyVal.NewStr("tooltip"				, "tooltip_TODO");					// $tooltip = wfMessage( "interlanguage-link-sitename-$prefix" );
		}
		return rv;
	}
	public void Notify_wiki_changed() {if (notify_wiki_changed_fnc != null) core.Interpreter().CallFunction(notify_wiki_changed_fnc.Id(), KeyValUtl.AryEmpty);}
	public boolean Init_site_for_wiki(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xowe_wiki wiki = core.Wiki();
		KeyVal[] rv = new KeyVal[7];
		Bld_info(rv);
		rv[5] = KeyVal.NewStr("name" + "spaces", Bld_ns_ary(wiki));
		rv[6] = KeyVal.NewStr("stats", Bld_stats(wiki));
		return rslt.Init_obj(rv);
	}
	private void Bld_info(KeyVal[] rv) {
		Xow_wiki_props props = core.Wiki().Props();
		rv[0] = KeyVal.NewStr("siteName"			, props.Site_name());
		rv[1] = KeyVal.NewStr("server"			, BryUtl.Add(gplx.core.net.Gfo_protocol_itm.Bry_relative, props.Server_name()));	// NOTE: should generate "//en.wikipedia.org", not "de.wikipedia.org"; PAGE:de.w:Giro_d�Italia_1996 DATE:2016-04-17
		rv[2] = KeyVal.NewStr("scriptPath"		, props.Script_path());
		rv[3] = KeyVal.NewStr("stylePath"		, props.Style_path());
		rv[4] = KeyVal.NewStr("currentVersion"	, props.Current_version());
	}
	private KeyVal[] Bld_ns_ary(Xowe_wiki wiki) {
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		int len = ns_mgr.Ids_len();
		KeyVal[] rv = new KeyVal[len];
		int rv_idx = 0;
		for (int i = 0; i < len; i++) {
			Xow_ns ns = ns_mgr.Ids_get_at(i);
			if (ns == null) continue;
			int ns_id = ns.Id();
			rv[rv_idx++] = KeyVal.NewInt(ns_id, Bld_ns(wiki, ns, ns_id));
		}
		return rv;
	}
	private KeyVal[] Bld_ns(Xowe_wiki wiki, Xow_ns ns, int ns_id) {
		int len = 16;
		if		(ns_id <  Xow_ns_.Tid__main) len = 14;
		else if (ns_id == Xow_ns_.Tid__main) len = 17;
		KeyVal[] rv = new KeyVal[len];
		rv[ 0] = KeyVal.NewStr("id"						, ns_id);
		rv[ 1] = KeyVal.NewStr("name"					, ns.Name_ui());
		rv[ 2] = KeyVal.NewStr("canonicalName"			, ns.Name_db_str());			// strtr( $canonical, "_", " " ),
		rv[ 3] = KeyVal.NewStr("hasSubpages"				, ns.Subpages_enabled());		// MWNs::hasSubpages( $ns ),
		rv[ 4] = KeyVal.NewStr("hasGenderDistinction"	, ns.Is_gender_aware());		// MWNs::hasGenderDistinction( $ns ),
		rv[ 5] = KeyVal.NewStr("isCapitalized"			, ns.Is_capitalized());			// MWNs::isCapitalized( $ns ),
		rv[ 6] = KeyVal.NewStr("isContent"				, ns.Is_content());				// MWNs::isContent( $ns ),
		rv[ 7] = KeyVal.NewStr("isIncludable"			, ns.Is_includable());			// !MWNs::isNonincludable( $ns ),
		rv[ 8] = KeyVal.NewStr("isMovable"				, ns.Is_movable());				// MWNs::isMovable( $ns ),
		rv[ 9] = KeyVal.NewStr("isSubject"				, ns.Id_is_subj());
		rv[10] = KeyVal.NewStr("isTalk"					, ns.Id_is_talk());
		rv[11] = KeyVal.NewStr("defaultContentModel"		, null);						// MWNs::getNsContentModel( $ns ), ASSUME: always null?
		rv[12] = KeyVal.NewStr("aliases"					, ns.Aliases_as_scrib_ary());	// DATE:2014-02-15
		if (ns_id < 0)
			rv[13] = KeyVal.NewStr("subject"				, ns_id);						// MWNs::getSubject( $ns );
		else {
			rv[13] = KeyVal.NewStr("subject"				, ns_id);						// MWNs::getSubject( $ns );
			rv[14] = KeyVal.NewStr("talk"				, ns.Id_talk_id());				// MWNs::getTalk( $ns );
			rv[15] = KeyVal.NewStr("associated"			, ns.Id_alt_id());				// MWNs::getAssociated( $ns );
			if (ns_id == Xow_ns_.Tid__main)
				rv[16] = KeyVal.NewStr("displayName"		, wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_ns_blankns));	// MWNs::getAssociated( $ns );
		}
		return rv;
	}
	private KeyVal[] Bld_stats(Xowe_wiki wiki) {
		Xowd_site_stats_mgr stats = wiki.Stats();
		KeyVal[] rv = new KeyVal[8];
		rv[0] = KeyVal.NewStr("pages"		, stats.Num_pages());			// SiteStats::pages(),
		rv[1] = KeyVal.NewStr("articles"		, stats.Num_articles());		// SiteStats::articles(),
		rv[2] = KeyVal.NewStr("files"		, stats.Num_files());			// SiteStats::images(),
		rv[3] = KeyVal.NewStr("edits"		, stats.Num_edits());			// SiteStats::edits(),
		rv[4] = KeyVal.NewStr("views"		, stats.Num_views());			// $wgDisableCounters ? null : (int)SiteStats::views(),
		rv[5] = KeyVal.NewStr("users"		, stats.Num_users());			// SiteStats::users(),
		rv[6] = KeyVal.NewStr("activeUsers"	, stats.Num_active());			// SiteStats::activeUsers(),
		rv[7] = KeyVal.NewStr("admins"		, stats.Num_admins());			// SiteStats::activeUsers(),
		return rv;
	}
}
