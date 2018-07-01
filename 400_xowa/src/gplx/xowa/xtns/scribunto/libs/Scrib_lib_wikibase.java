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
import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.parsers.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.stores.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.xtns.scribunto.procs.*;
import gplx.xowa.xtns.wbases.mediawiki.client.includes.*; import gplx.xowa.xtns.wbases.mediawiki.client.includes.dataAccess.scribunto.*;
public class Scrib_lib_wikibase implements Scrib_lib {
	private final    Scrib_core core;
	private Wbase_doc_mgr entity_mgr;
	private Wbase_entity_accessor entity_accessor;
	private WikibaseLanguageIndependentLuaBindings wikibaseLanguageIndependentLuaBindings;
	private Scrib_lua_proc notify_page_changed_fnc;
	public Scrib_lib_wikibase(Scrib_core core) {this.core = core;}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_proc_mgr Procs() {return procs;} private final    Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public Scrib_lib Init() {
		procs.Init_by_lib(this, Proc_names); 
		this.entity_mgr = core.App().Wiki_mgr().Wdata_mgr().Doc_mgr;
		this.entity_accessor = new Wbase_entity_accessor(entity_mgr);
		this.wikibaseLanguageIndependentLuaBindings = new WikibaseLanguageIndependentLuaBindings(entity_mgr);
		return this;
	}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_wikibase(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.wikibase.lua"));
		notify_page_changed_fnc = mod.Fncs_get_by_key("notify_page_changed");
		return mod;
	}
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getLabel:							return GetLabel(args, rslt);
			case Proc_getLabelByLanguage:				return GetLabelByLanguage(args, rslt);
			case Proc_getEntity:						return GetEntity(args, rslt);
			case Proc_entityExists:						return EntityExists(args, rslt);
			case Proc_getEntityStatements:				return GetEntityStatements(args, rslt);
			case Proc_getSetting:						return GetSetting(args, rslt);
			case Proc_getEntityUrl:						return GetEntityUrl(args, rslt);
			case Proc_renderSnak:						return RenderSnak(args, rslt);
			case Proc_formatValue:						return FormatValue(args, rslt);
			case Proc_renderSnaks:						return RenderSnaks(args, rslt);
			case Proc_getEntityId:						return GetEntityId(args, rslt);
			case Proc_getReferencedEntityId:			return GetReferencedEntityId(args, rslt);
			case Proc_getUserLang:						return GetUserLang(args, rslt);
			case Proc_getDescription:					return GetDescription(args, rslt);
			case Proc_resolvePropertyId:				return ResolvePropertyId(args, rslt);
			case Proc_getSiteLinkPageName:				return GetSiteLinkPageName(args, rslt);
			case Proc_incrementExpensiveFunctionCount:	return IncrementExpensiveFunctionCount(args, rslt);
			case Proc_isValidEntityId:					return IsValidEntityId(args, rslt);
			case Proc_getPropertyOrder:					return GetPropertyOrder(args, rslt);
			case Proc_orderProperties:					return OrderProperties(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int 
	  Proc_getLabel = 0, Proc_getLabelByLanguage = 1, Proc_getEntity = 2, Proc_entityExists = 3, Proc_getEntityStatements = 4, Proc_getSetting = 5, Proc_getEntityUrl = 6
	, Proc_renderSnak = 7, Proc_formatValue = 8, Proc_renderSnaks = 9, Proc_getEntityId = 10, Proc_getReferencedEntityId = 11
	, Proc_getUserLang = 12, Proc_getDescription = 13, Proc_resolvePropertyId = 14, Proc_getSiteLinkPageName = 15, Proc_incrementExpensiveFunctionCount = 16
	, Proc_isValidEntityId = 17, Proc_getPropertyOrder = 18, Proc_orderProperties = 19;
	public static final String
	  Invk_getLabel = "getLabel", Invk_getLabelByLanguage = "getLabelByLanguage", Invk_getEntity = "getEntity", Invk_entityExists = "entityExists"
	, Invk_getEntityStatements = "getEntityStatements"
	, Invk_getSetting = "getSetting", Invk_getEntityUrl = "getEntityUrl"
	, Invk_renderSnak = "renderSnak", Invk_formatValue = "formatValue", Invk_renderSnaks = "renderSnaks"
	, Invk_getEntityId = "getEntityId", Invk_getReferencedEntityId = "getReferencedEntityId"
	, Invk_getUserLang = "getUserLang", Invk_getDescription = "getDescription", Invk_resolvePropertyId = "resolvePropertyId"
	, Invk_getSiteLinkPageName = "getSiteLinkPageName", Invk_incrementExpensiveFunctionCount = "incrementExpensiveFunctionCount"
	, Invk_isValidEntityId = "isValidEntityId", Invk_getPropertyOrder = "getPropertyOrder", Invk_orderProperties = "orderProperties"
	;
	private static final    String[] Proc_names = String_.Ary
	( Invk_getLabel, Invk_getLabelByLanguage, Invk_getEntity, Invk_entityExists, Invk_getEntityStatements, Invk_getSetting, Invk_getEntityUrl
	, Invk_renderSnak, Invk_formatValue, Invk_renderSnaks
	, Invk_getEntityId, Invk_getReferencedEntityId, Invk_getUserLang, Invk_getDescription, Invk_resolvePropertyId, Invk_getSiteLinkPageName, Invk_incrementExpensiveFunctionCount
	, Invk_isValidEntityId, Invk_getPropertyOrder, Invk_orderProperties
	);
	public void Notify_page_changed() {if (notify_page_changed_fnc != null) core.Interpreter().CallFunction(notify_page_changed_fnc.Id(), Keyval_.Ary_empty);}

	public boolean IsValidEntityId(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] entityId = args.Pull_bry(0);

		// EntityId.php.extractSerializationParts expands "localRepoName:random:serialization:parts:entityId"
		int colonPos = Bry_find_.Find_bwd(entityId, Byte_ascii.Colon);
		if (colonPos != -1) {
			entityId = Bry_.Mid(entityId, colonPos + 1);
		}

		boolean valid = checkEntityIdOrNull(entityId) != null;
		return rslt.Init_obj(valid);
	}
	private static byte[] checkEntityIdOrNull(byte[] entityId) {
		/* REF: https://github.com/wmde/WikibaseDataModel/tree/master/src/Entity/
		   PropertyId.php.PATTERN: '/^P[1-9]\d{0,9}\z/i';
		   ItemId.php.PATTERN    : '/^Q[1-9]\d{0,9}\z/i';
		*/
		if (entityId.length > 0) {
			switch (entityId[0]) {
				case Byte_ascii.Ltr_P:
				case Byte_ascii.Ltr_Q:
					if (entityId.length > 1) {
						switch (entityId[1]) {
							case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
							case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
								boolean numeric = true;
								for (int i = 2; i < entityId.length; i++) {
									switch (entityId[i]) {
										case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
										case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
											break;
										default:
											numeric = false;
											break;
									}
								}
								if (numeric)
									return entityId;
								break;
						}
					}
					break;
			}
		}
		return null;
	}
	public boolean GetEntityId(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] ttl_bry = args.Pull_bry(0);
		Xowe_wiki wiki = core.Wiki();
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
		byte[] rv = wiki.Appe().Wiki_mgr().Wdata_mgr().Qid_mgr.Get_qid_or_null(wiki, ttl); if (rv == null) rv = Bry_.Empty;
		return rslt.Init_obj(rv);
	}
	// REF: https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/client/config/WikibaseClient.default.php
	private static final int ReferencedEntityIdMaxDepth = 4, ReferencedEntityIdMaxReferencedEntityVisits = 50;
	// private static final int ReferencedEntityIdAccessLimit = 3; // max # of calls per page?
	public boolean GetReferencedEntityId(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// get fromId, propertyId, and toIds
		byte[] fromId = checkEntityIdOrNull(args.Pull_bry(0));
		byte[] propertyIdBry = checkEntityIdOrNull(args.Pull_bry(1));
		int propertyId = Bry_.To_int(Bry_.Mid(propertyIdBry, 1));
		Keyval[] toIdsAry = args.Pull_kv_ary_safe(2);
		Ordered_hash toIds = Ordered_hash_.New_bry();
		for (Keyval kv : toIdsAry) {
			toIds.Add_as_key_and_val(checkEntityIdOrNull(Bry_.new_u8((String)kv.Val())));
		}
		Referenced_entity_lookup_wkr wkr = new Referenced_entity_lookup_wkr(ReferencedEntityIdMaxDepth, ReferencedEntityIdMaxReferencedEntityVisits, entity_mgr, core.Page().Url(), fromId, propertyId, toIds);
		return rslt.Init_obj(wkr.Get_referenced_entity());
	}
	public boolean EntityExists(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, false); 
		return rslt.Init_obj(wdoc != null);
	}
	public boolean GetEntity(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, true); 
		if (wdoc == null) {
			return rslt.Init_ary_empty();
			// return rslt.Init_obj(Keyval_.Ary(Keyval_.new_("schemaVersion", 2))); // NOTE: was "rslt.Init_ary_empty()" PAGE:de.w:Critérium_International_2016 DATE:2017-12-30
		}

		Wbase_prop_mgr prop_mgr = core.Wiki().Appe().Wiki_mgr().Wdata_mgr().Prop_mgr();
		return rslt.Init_obj(Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc, true, false));	// "false": wbase now always uses v2; PAGE:ja.w:東京競馬場; DATE:2015-07-28
	}
	public boolean GetEntityUrl(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] entityId = args.Pull_bry(0);
		byte[] entity_url = Wbase_client.getDefaultInstance().RepoLinker().getEntityUrl(entityId);
		return rslt.Init_obj(entity_url);
	}
	public boolean GetEntityStatements(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] prefixedEntityId = args.Pull_bry(0);
		byte[] propertyId = args.Pull_bry(1);
		byte[] rank = args.Pull_bry(2);

		Wbase_prop_mgr prop_mgr = core.Wiki().Appe().Wiki_mgr().Wdata_mgr().Prop_mgr();
		Wbase_claim_base[] statements = this.entity_accessor.getEntityStatements(prefixedEntityId, propertyId, rank);
		if (statements == null)
			return rslt.Init_null();
		return rslt.Init_obj(Scrib_lib_wikibase_srl.Srl_claims_prop_ary(prop_mgr, String_.new_u8(propertyId), statements, 1));
	}
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
	public boolean FormatValue(Scrib_proc_args args, Scrib_proc_rslt rslt) {
/*
public function formatValues( $snaksSerialization ) {
	$this->checkType( 'formatValues', 1, $snaksSerialization, 'table' );
	try {
		$ret = [ $this->getSnakSerializationRenderer( 'rich-wikitext' )->renderSnaks( $snaksSerialization ) ];
		return $ret;
	} catch ( DeserializationException $e ) {
		throw new ScribuntoException( 'wikibase-error-deserialize-error' );
	}
}	
*/
		throw Err_.new_unimplemented();
	}
	public boolean ResolvePropertyId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, true); if (wdoc == null) return rslt.Init_ary_empty();	// NOTE: prop should be of form "P123"; do not add "P"; PAGE:no.w:Anne_Enger; DATE:2015-10-27
		return rslt.Init_obj(wdoc.Label_list__get_or_fallback(core.Lang()));
	}
	public boolean GetPropertyOrder(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		throw Err_.new_("wbase", "getPropertyOrder not implemented", "url", core.Page().Url().To_str());
	}
	public boolean OrderProperties(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		throw Err_.new_("wbase", "orderProperties not implemented", "url", core.Page().Url().To_str());
	}
	public boolean GetLabel(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, true); 
		if (wdoc == null) 
			return rslt.Init_ary_empty();
		else
			return rslt.Init_obj(wdoc.Label_list__get_or_fallback(core.Lang()));
	}
	public boolean GetLabelByLanguage(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] prefixedEntityId = args.Pull_bry(0);
		byte[] languageCode = args.Pull_bry(1);
		return rslt.Init_obj(wikibaseLanguageIndependentLuaBindings.getLabelByLanguage(prefixedEntityId, languageCode));
	}
	public boolean GetSiteLinkPageName(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, true); if (wdoc == null) return rslt.Init_ary_empty();	// NOTE: prop should be of form "P123"; do not add "P"; PAGE:no.w:Anne_Enger; DATE:2015-10-27
		Xow_domain_itm domain_itm = core.Wiki().Domain_itm();
		return rslt.Init_obj(wdoc.Slink_list__get_or_fallback(domain_itm.Abrv_wm()));
	}
	public boolean GetDescription(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, true); if (wdoc == null) return rslt.Init_ary_empty();
		return rslt.Init_obj(wdoc.Descr_list__get_or_fallback(core.Lang()));
	}
	public boolean GetUserLang(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.App().Usere().Lang().Key_bry());
	}
	public boolean GetGlobalSiteId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.Wiki().Domain_abrv());	// ;siteGlobalID: This site's global ID (e.g. <code>'itwiki'</code>), as used in the sites table. Default: <code>$wgDBname</code>.; REF:/xtns/Wikibase/docs/options.wiki
	}
	public boolean GetSetting(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		throw Err_.new_("wbase", "getSetting not implemented", "url", core.Page().Url().To_str());
	}
	public boolean IncrementExpensiveFunctionCount(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_obj(Keyval_.Ary_empty);	// NOTE: for now, always return null (XOWA does not care about expensive parser functions)
	}
	public Wdata_doc Get_wdoc_or_null(Scrib_proc_args args, Scrib_core core, boolean logMissing) {
		// get qid / pid from scrib_arg[0]; if none, return null;
		byte[] xid_bry = args.Pull_bry(0); if (Bry_.Len_eq_0(xid_bry)) return null;	// NOTE: some Modules do not pass in an argument; return early, else spurious warning "invalid qid for ttl" (since ttl is blank); EX:w:Module:Authority_control; DATE:2013-10-27

		// get wdoc
		Wdata_doc wdoc = entity_mgr.Get_by_xid_or_null(xid_bry); // NOTE: by_xid b/c Module passes just "p1" not "Property:P1"
		if (wdoc == null && logMissing) Wdata_wiki_mgr.Log_missing_qid(core.Ctx(), xid_bry);
		return wdoc;
	}
}
