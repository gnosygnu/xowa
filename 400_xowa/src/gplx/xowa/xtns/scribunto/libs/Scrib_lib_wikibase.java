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
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.mediawiki.extensions.Wikibase.client.includes.WikibaseClient;
import gplx.xowa.mediawiki.extensions.Wikibase.client.includes.dataAccess.scribunto.Wbase_entity_accessor;
import gplx.xowa.wikis.domains.Xow_domain_itm;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
import gplx.xowa.xtns.wbases.Wdata_doc;
import gplx.xowa.xtns.wbases.Wdata_prop_val_visitor_;
import gplx.xowa.xtns.wbases.Wdata_wiki_mgr;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.core.Wdata_langtext_itm;
import gplx.xowa.xtns.wbases.core.Wdata_sitelink_itm;
import gplx.xowa.xtns.wbases.stores.Wbase_doc_mgr;
import gplx.xowa.xtns.wbases.stores.Wbase_prop_mgr;
// REF.MW:https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/client/includes/DataAccess/Scribunto/Scribunto_LuaWikibaseLibrary.php
public class Scrib_lib_wikibase implements Scrib_lib {
	private final Scrib_core core;
	private Wbase_doc_mgr entity_mgr;
	private Wbase_entity_accessor entity_accessor;
	private Wdata_wiki_mgr wdata_mgr;
	private Scrib_lua_proc notify_page_changed_fnc;
	public Scrib_lib_wikibase(Scrib_core core) {this.core = core;}
	public String Key() {return "mw.wikibase";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_proc_mgr Procs() {return procs;} private final Scrib_proc_mgr procs = new Scrib_proc_mgr();

//		/**
//		* @var WikibaseLanguageIndependentLuaBindings|null
//		*/
//		private $languageIndependentLuaBindings = null;
//
//		/**
//		* @var WikibaseLanguageDependentLuaBindings|null
//		*/
//		private $languageDependentLuaBindings = null;
//
//		/**
//		* @var EntityAccessor|null
//		*/
//		private $entityAccessor = null;
//
//		/**
//		* @var SnakSerializationRenderer[]
//		*/
//		private $snakSerializationRenderers = [];
//
//		/**
//		* @var LanguageFallbackChain|null
//		*/
//		private $fallbackChain = null;
//
//		/**
//		* @var ParserOutputUsageAccumulator|null
//		*/
//		private $usageAccumulator = null;
//
//		/**
//		* @var PropertyIdResolver|null
//		*/
//		private $propertyIdResolver = null;

	/**
	* @var PropertyOrderProvider|null
	*/
//		private XomwPropertyOrderProvider propertyOrderProvider = null;

//		/**
//		* @var EntityIdParser|null
//		*/
//		private $entityIdParser = null;
//
//		/**
//		* @var RepoLinker|null
//		*/
//		private $repoLinker = null;

	public Scrib_lib Init() {
		procs.Init_by_lib(this, Proc_names); 
		this.wdata_mgr = core.App().Wiki_mgr().Wdata_mgr();
		this.entity_mgr = wdata_mgr.Doc_mgr;
		this.entity_accessor = new Wbase_entity_accessor(entity_mgr);
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
			case Proc_formatValues:						return FormatValues(args, rslt);
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
			case Proc_incrementStatsKey:                return IncrementStatsKey(args, rslt);
			case Proc_getEntityModuleName:              return GetEntityModuleName(args, rslt);
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	private static final int 
	  Proc_getLabel = 0, Proc_getLabelByLanguage = 1, Proc_getEntity = 2, Proc_entityExists = 3, Proc_getEntityStatements = 4, Proc_getSetting = 5, Proc_getEntityUrl = 6
	, Proc_renderSnak = 7, Proc_formatValue = 8, Proc_renderSnaks = 9, Proc_formatValues = 10, Proc_getEntityId = 11, Proc_getReferencedEntityId = 12
	, Proc_getUserLang = 13, Proc_getDescription = 14, Proc_resolvePropertyId = 15, Proc_getSiteLinkPageName = 16, Proc_incrementExpensiveFunctionCount = 17
	, Proc_isValidEntityId = 18, Proc_getPropertyOrder = 19, Proc_orderProperties = 20, Proc_incrementStatsKey = 21, Proc_getEntityModuleName = 22;
	public static final String
	  Invk_getLabel = "getLabel", Invk_getLabelByLanguage = "getLabelByLanguage", Invk_getEntity = "getEntity", Invk_entityExists = "entityExists"
	, Invk_getEntityStatements = "getEntityStatements"
	, Invk_getSetting = "getSetting", Invk_getEntityUrl = "getEntityUrl"
	, Invk_renderSnak = "renderSnak", Invk_formatValue = "formatValue", Invk_renderSnaks = "renderSnaks", Invk_formatValues = "formatValues"
	, Invk_getEntityId = "getEntityId", Invk_getReferencedEntityId = "getReferencedEntityId"
	, Invk_getUserLang = "getUserLang", Invk_getDescription = "getDescription", Invk_resolvePropertyId = "resolvePropertyId"
	, Invk_getSiteLinkPageName = "getSiteLinkPageName", Invk_incrementExpensiveFunctionCount = "incrementExpensiveFunctionCount"
	, Invk_isValidEntityId = "isValidEntityId", Invk_getPropertyOrder = "getPropertyOrder", Invk_orderProperties = "orderProperties"
	, Invk_incrementStatsKey = "incrementStatsKey", Invk_getEntityModuleName = "getEntityModuleName"
	;
	private static final String[] Proc_names = StringUtl.Ary
	( Invk_getLabel, Invk_getLabelByLanguage, Invk_getEntity, Invk_entityExists, Invk_getEntityStatements, Invk_getSetting, Invk_getEntityUrl
	, Invk_renderSnak, Invk_formatValue, Invk_renderSnaks, Invk_formatValues
	, Invk_getEntityId, Invk_getReferencedEntityId, Invk_getUserLang, Invk_getDescription, Invk_resolvePropertyId, Invk_getSiteLinkPageName, Invk_incrementExpensiveFunctionCount
	, Invk_isValidEntityId, Invk_getPropertyOrder, Invk_orderProperties, Invk_incrementStatsKey, Invk_getEntityModuleName
	);
	public void Notify_page_changed() {if (notify_page_changed_fnc != null) core.Interpreter().CallFunction(notify_page_changed_fnc.Id(), KeyValUtl.AryEmpty);}

	public boolean IsValidEntityId(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] entityId = args.Pull_bry(0);

		// EntityId.php.extractSerializationParts expands "localRepoName:random:serialization:parts:entityId"
		int colonPos = BryFind.FindBwd(entityId, AsciiByte.Colon);
		if (colonPos != -1) {
			entityId = BryLni.Mid(entityId, colonPos + 1);
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
				case AsciiByte.Ltr_P:
				case AsciiByte.Ltr_Q:
					if (entityId.length > 1) {
						switch (entityId[1]) {
							case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
							case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
								boolean numeric = true;
								for (int i = 2; i < entityId.length; i++) {
									switch (entityId[i]) {
										case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
										case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
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
		byte[] rv = null;
		if (ttl != null) // must check for null; PAGE:en.w:Water_treader DATE:2018-07-01
			rv = wiki.Appe().Wiki_mgr().Wdata_mgr().Qid_mgr.Get_qid_or_null(wiki, ttl);
		return rv == null 
			? rslt.Init_null() // null if ttl is invalid or doesn't exist; NOTE: must be null, not ""; PAGE:en.w:Nicoletella; ISSUE#:415 DATE:2019-03-31
			: rslt.Init_obj(rv);
	}
	// REF: https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/client/config/WikibaseClient.default.php
	private static final int ReferencedEntityIdMaxDepth = 4, ReferencedEntityIdMaxReferencedEntityVisits = 50;
	// private static final int ReferencedEntityIdAccessLimit = 3; // max # of calls per page?
	public boolean GetReferencedEntityId(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// get fromId, propertyId, and toIds
		byte[] fromId = checkEntityIdOrNull(args.Pull_bry(0));
		byte[] propertyIdBry = checkEntityIdOrNull(args.Pull_bry(1));
		int propertyId = BryUtl.ToInt(BryLni.Mid(propertyIdBry, 1));
		KeyVal[] toIdsAry = args.Pull_kv_ary_safe(2);
		Ordered_hash toIds = Ordered_hash_.New_bry();
		for (KeyVal kv : toIdsAry) {
			toIds.AddAsKeyAndVal(checkEntityIdOrNull(BryUtl.NewU8((String)kv.Val())));
		}
		Referenced_entity_lookup_wkr wkr = new Referenced_entity_lookup_wkr(ReferencedEntityIdMaxDepth, ReferencedEntityIdMaxReferencedEntityVisits, entity_mgr, core.Page().Url(), fromId, propertyId, toIds);
		return rslt.Init_obj(wkr.Get_referenced_entity());
	}
	public boolean EntityExists(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, "EntityExists", false); 
		return rslt.Init_obj(wdoc != null);
	}
	public boolean GetEntity(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, "GetEntity", true); 
		if (wdoc == null) {
			return rslt.Init_ary_empty();
			// return rslt.Init_obj(Keyval_.Ary(Keyval_.new_("schemaVersion", 2))); // NOTE: was "rslt.Init_ary_empty()" PAGE:de.w:Critérium_International_2016 DATE:2017-12-30
		}

		Wbase_prop_mgr prop_mgr = core.Wiki().Appe().Wiki_mgr().Wdata_mgr().Prop_mgr();
		return rslt.Init_obj(Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc, true, false, core.Page().Url_bry_safe()));	// "false": wbase now always uses v2; PAGE:ja.w:東京競馬場; DATE:2015-07-28
	}
	public boolean GetEntityUrl(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] entityId = args.Pull_bry(0);
		byte[] entity_url = WikibaseClient.getDefaultInstance().RepoLinker().getEntityUrl(entityId);
		return rslt.Init_obj(entity_url);
	}
	public boolean GetEntityStatements(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] prefixedEntityId = args.Pull_bry(0);
		byte[] propertyId = args.Pull_bry(1);
		byte[] rank = args.Pull_bry(2);

		Wbase_prop_mgr prop_mgr = core.Wiki().Appe().Wiki_mgr().Wdata_mgr().Prop_mgr();
		Wbase_claim_base[] statements = this.entity_accessor.getEntityStatements(prefixedEntityId, propertyId, rank);
		if (statements == null) return rslt.Init_null();

		String propertyIdAsString = StringUtl.NewU8(propertyId);
		return rslt.Init_obj(KeyVal.NewStr(propertyIdAsString, Scrib_lib_wikibase_srl.Srl_claims_prop_ary(prop_mgr, propertyIdAsString, statements, 1, core.Page().Url_bry_safe())));
	}
	public boolean RenderSnak(Scrib_proc_args args, Scrib_proc_rslt rslt)	{
		Xowe_wiki wiki = core.Wiki();
		Wdata_wiki_mgr wdata_mgr = wiki.Appe().Wiki_mgr().Wdata_mgr();
		String rv = Wdata_prop_val_visitor_.Render_snak(wdata_mgr, wiki, core.Page().Url_bry_safe(), args.Pull_kv_ary_safe(0));
		return rslt.Init_obj(rv);
	}
	public boolean RenderSnaks(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String rv = Wdata_prop_val_visitor_.Render_snaks(core.Wiki(), core.Page().Url_bry_safe(), Deserialize_snaks(args, 0));
		return rslt.Init_obj(rv);
	}
	public boolean FormatValue(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// WORKAROUND: return same as RenderSnaks until ISSUE:#593 is completed
		Xowe_wiki wiki = core.Wiki();
		Wdata_wiki_mgr wdata_mgr = wiki.Appe().Wiki_mgr().Wdata_mgr();
		String rv = Wdata_prop_val_visitor_.Render_snak(wdata_mgr, wiki, core.Page().Url_bry_safe(), args.Pull_kv_ary_safe(0));
		return rslt.Init_obj(rv);
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
	}
	public boolean FormatValues(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// WORKAROUND: return same as RenderSnaks until ISSUE:#593 is completed
		String rv = Wdata_prop_val_visitor_.Render_snaks(core.Wiki(), core.Page().Url_bry_safe(), Deserialize_snaks(args, 0));
		return rslt.Init_obj(rv);
	}
	public boolean ResolvePropertyId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		byte[] rv = null;
		byte[] pid = Get_xid_from_args(args);
		if (pid != null) {
			// if pid is "P####", look-up in db by title
			byte b0 = pid[0];
			if (b0 == AsciiByte.Ltr_P || b0 == AsciiByte.Ltr_p) {
				// check if rest is numeric
				boolean numeric = true;
				for (int i = 1; i < pid.length; i++) {
					if (!AsciiByte.IsNum(pid[i])) {
						numeric = false;
						break;
					}
				}
				if (numeric) {
					byte[] key = pid;
					if (b0 == AsciiByte.Ltr_p) key[0] = AsciiByte.Ltr_P; // uppercase key
					Wdata_doc wdoc = entity_mgr.Get_by_xid_or_null(key); // NOTE: by_xid b/c Module passes just "p1" not "Property:P1"
					if (wdoc != null) rv = key; // found wdoc; set rv
				}
			}

			// pid is non-numeric or does not exist; look-up by label
			if (rv == null) {
				Wdata_wiki_mgr wdata_mgr = core.Wiki().Appe().Wiki_mgr().Wdata_mgr();
				int pid_int = wdata_mgr.Pid_mgr.Get_pid_or_neg1(core.Wiki().Wdata_wiki_lang(), pid);
				if (pid_int != gplx.xowa.xtns.wbases.core.Wbase_pid.Id_null) {
					BryWtr tmp_bfr = BryWtr.New();
					tmp_bfr.AddByte(AsciiByte.Ltr_P);
					tmp_bfr.AddLongVariable(pid_int);
					rv = tmp_bfr.ToBryAndClear();
				}
			}
		}
		if (rv == null) {
			Wdata_wiki_mgr.Log_missing_qid(core.Ctx(), "ResolvePropertyId", pid);
			return rslt.Init_null(); // NOTE: should be null, not empty; verified with "=mw.wikibase.resolvePropertyId('')" -> nil; DATE:2019-04-07
		}
		else
			return rslt.Init_obj(rv);
	}
	public boolean GetPropertyOrder(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		throw ErrUtl.NewArgs("getPropertyOrder not implemented", "url", core.Page().Url().To_str());
	}

	// TEST:
	// * 0 propertyIds
	// * same membership, but unsorted
	// * more in lhs
	// * more in rhs
	public boolean OrderProperties(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		KeyVal[] propertyIds = args.Pull_kv_ary_safe(0);

//			if (propertyIds.length == 0) {
//				return rslt.Init_obj(propertyIds);
//			}
//
//			XophpArray orderedPropertiesPart = XophpArray.New();
//			XophpArray unorderedProperties = XophpArray.New();
//
//			// item is [{P1,1}]
//			XophpArray propertyOrder = this.getPropertyOrderProvider().getPropertyOrder();
//			foreach (Keyval propertyIdKv in propertyIds) {
//				// item is [{0,P1}]
//				String propertyId = propertyIdKv.Val_to_str_or_empty();
//				if (propertyOrder.isset(propertyId)) {
//					int propertyOrderSort = propertyOrder.Get_by_int(propertyId);
//					orderedPropertiesPart.Set(propertyOrderSort, propertyId);
//				} else {
//					unorderedProperties.Add(propertyId);
//				}
//			}
//			ksort( orderedPropertiesPart );
//			orderedProperties = XophpArray.array_merge(orderedPropertiesPart, unorderedProperties);

		// Lua tables start at 1
//			XophpArray orderedPropertiesResult = XophpArray.array_combine(
//					range(1, count(orderedProperties)), XophpArray.array_values(orderedProperties)
//			);
//			return rslt.Init_obj(orderedPropertiesResult.To_kv_ary());
		return rslt.Init_obj(propertyIds);
//			throw Err_.new_("wbase", "orderProperties not implemented", "url", core.Page().Url().To_str());
	}
	public boolean GetLabel(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, "GetLabel", true); 
		if (wdoc == null) return rslt.Init_ary_empty();

		Wdata_langtext_itm itm = wdoc.Get_label_itm_or_null(core.Lang());
		return itm == null ? rslt.Init_ary_empty() : rslt.Init_many_objs(itm.Text(), itm.Lang());
	}
	public boolean GetLabelByLanguage(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] prefixedEntityId = args.Pull_bry(0);
		byte[] languageCode = args.Pull_bry(1);
		byte[] label = core.Wiki().Xtn_mgr().Xtn_wikibase().Lua_bindings().getLabelByLanguage_or_null(prefixedEntityId, languageCode);
		return label == null ? rslt.Init_str_empty() : rslt.Init_obj(label);
	}
	public boolean GetSiteLinkPageName(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// get wdoc from args; EX: "q2"
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, "GetSiteLinkPageName", true);  // NOTE: prop should be of form "P123"; do not add "P"; PAGE:no.w:Anne_Enger; DATE:2015-10-27
		if (wdoc == null) return rslt.Init_ary_empty();

		// get wiki from args (EX: "enwiki"), or default to current wiki; ISSUE#:665; PAGE:commons.wikimedia.org/wiki/Category:Paddy_Ashdown; DATE:2020-02-19
		byte[] wiki_bry = args.Cast_bry_or_null(1);
		if (wiki_bry == null) {
			Xow_domain_itm domain_itm = core.Wiki().Domain_itm();
			wiki_bry = domain_itm.Abrv_wm();
		}

		// get sitelink for wiki from wdoc
		Wdata_sitelink_itm itm = wdoc.Get_slink_itm_or_null(wiki_bry);
		return itm == null ? rslt.Init_ary_empty() : rslt.Init_many_objs(itm.Name(), itm.Lang());
	}
	public boolean GetDescription(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Wdata_doc wdoc = Get_wdoc_or_null(args, core, "GetDescription", true);
		if (wdoc == null) return rslt.Init_ary_empty();

		Wdata_langtext_itm itm = wdoc.Get_descr_itm_or_null(core.Lang());
		return itm == null ? rslt.Init_ary_empty() : rslt.Init_many_objs(itm.Text(), itm.Lang());
	}
	public boolean GetUserLang(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.App().Usere().Lang().Key_bry());
	}
	public boolean GetGlobalSiteId(Scrib_proc_args args, Scrib_proc_rslt rslt) {			
		return rslt.Init_obj(core.Wiki().Domain_abrv());	// ;siteGlobalID: This site's global ID (e.g. <code>'itwiki'</code>), as used in the sites table. Default: <code>$wgDBname</code>.; REF:/xtns/Wikibase/docs/options.wiki
	}
	public boolean GetSetting(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return Scrib_lib_wikibase.GetSetting(args, rslt, core, wdata_mgr);
	}
	public static boolean GetSetting(Scrib_proc_args args, Scrib_proc_rslt rslt, Scrib_core core, Wdata_wiki_mgr wdata_mgr) {
		byte[] key = args.Pull_bry(0);
		Object rv = core.Wiki().Xtn_mgr().Xtn_wikibase().Lua_bindings().getSetting(key);
		if (rv == null) 
			throw ErrUtl.NewArgs("getSetting key missing", "key", key, "url", core.Page().Url().To_str());
		return rslt.Init_obj(rv);
	}
	public boolean IncrementExpensiveFunctionCount(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_obj(KeyValUtl.AryEmpty);	// NOTE: for now, always return null (XOWA does not care about expensive parser functions)
	}
	public boolean IncrementStatsKey(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_null();
	}
	public boolean GetEntityModuleName(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String moduleName = "mw.wikibase.entity"; // FOOTNOTE:GetEntityModuleName
		// String prefixedEntityId = Get_xid_from_args(args);
		// try {
			// $type = $entityId->getEntityType();
			// $moduleName = $this->getLuaEntityModules()[$type] ?? 'mw.wikibase.entity';
		// }
		// catch (Exception exc) {
		//	moduleName = "mw.wikibase.entity";
		// }
		return rslt.Init_obj(moduleName);
	}
	private byte[] Get_xid_from_args(Scrib_proc_args args) {
		// get qid / pid from scrib_arg[0]
		byte[] xid_bry = args.Pull_bry(0);
		// NOTE: some Modules do not pass in an argument; return early, else spurious warning "invalid qid for ttl" (since ttl is blank); EX:w:Module:Authority_control; DATE:2013-10-27
		return BryUtl.IsNullOrEmpty(xid_bry)
			? null
			: BryUtl.Trim(xid_bry); // trim, b/c some pages will literally pass in "Property:P5\n"; PAGE:de.w:Mailand–Sanremo_2016 ISSUE#:363; DATE:2019-02-12
	}
	private Wdata_doc Get_wdoc_or_null(Scrib_proc_args args, Scrib_core core, String type, boolean logMissing) {
		byte[] xid_bry = Get_xid_from_args(args);
		if (xid_bry == null) return null;

		// get wdoc
		Wdata_doc wdoc = entity_mgr.Get_by_xid_or_null(xid_bry); // NOTE: by_xid b/c Module passes just "p1" not "Property:P1"
		if (wdoc == null && logMissing) Wdata_wiki_mgr.Log_missing_qid(core.Ctx(), type, xid_bry);
		return wdoc;
	}
	private static KeyVal[] Deserialize_snaks(Scrib_proc_args args, int idx) {
		// NOTE: SnakListDeserializer has an if-case to check for either "Snak[]" or "[key:"key",value:Snaks[]]" ISSUE#:666; PAGE:ja.w:Sed_(コンピュータ) DATE:2020-03-01
		// REF.MW: https://github.com/wikimedia/mediawiki-vendor/blob/4361929262cc87a08345c69a71258f59319be2c7/wikibase/data-model-serialization/src/Deserializers/SnakListDeserializer.php#L53-L68
		// get kvs
		KeyVal[] kvs = args.Pull_kv_ary_safe(idx);
		int kvs_len = kvs.length;

		if (kvs_len == 0) return kvs; // empty kvs; just return it;

		// get 1st
		KeyVal kv = kvs[0];

		// key is String; EX: {"P10":[{"property":"P20"}]}
		if (ClassUtl.EqByObj(String.class, kv.KeyAsObj())) {
			if (ClassUtl.EqByObj(KeyVal[].class, kv.KeyAsObj())) {
				throw ErrUtl.NewArgs("The snaks per property " + kv.KeyToStr() + " should be an array" );
			}
			return (KeyVal[])kv.Val();
		}
		// key is int; EX: ["1":{"property":"P20"}]
		else {
			return kvs;
		}
	}

	/**
	* @return PropertyOrderProvider
	*/
//		private XomwPropertyOrderProvider getPropertyOrderProvider() {
//			if (!XophpObject_.is_true(this.propertyOrderProvider)) {
//				WikibaseClient wikibaseClient = WikibaseClient.getDefaultInstance();
//				this.propertyOrderProvider = wikibaseClient.getPropertyOrderProvider();
//			}
//			return this.propertyOrderProvider;
//		}
}
/*
FOOTNOTE:GetEntityModuleName
Wikibase currently always returns 'mw.wikibase.entity' b/c "$this->getLuaEntityModules()[$type]" is always null
* getLuaEntityModules returns an EntityTypeDefinitions
* EntityTypeDefinitions is created in WikibaseRepo using entitytypes.php
* Neither entitytypes.php has a key definition for 'lua-entity-module'

See below references
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/EntityTypeDefinitions.php
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/repo/includes/WikibaseRepo.php
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/repo/WikibaseRepo.entitytypes.php
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/WikibaseLib.entitytypes.php
*/
