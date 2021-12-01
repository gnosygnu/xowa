/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scribunto.libs;

import gplx.Bry_;
import gplx.Int_;
import gplx.Keyval;
import gplx.Keyval_;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.Ordered_hash;
import gplx.String_;
import gplx.xowa.xtns.wbases.Wdata_doc;
import gplx.xowa.xtns.wbases.Wdata_wiki_mgr;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp_list;
import gplx.xowa.xtns.wbases.claims.Wbase_references_grp;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_entity_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_rank_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_enum_itm;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.core.Wdata_alias_itm;
import gplx.xowa.xtns.wbases.core.Wdata_dict_claim;
import gplx.xowa.xtns.wbases.core.Wdata_dict_claim_v1;
import gplx.xowa.xtns.wbases.core.Wdata_dict_langtext;
import gplx.xowa.xtns.wbases.core.Wdata_dict_sitelink;
import gplx.xowa.xtns.wbases.core.Wdata_langtext_itm;
import gplx.xowa.xtns.wbases.core.Wdata_sitelink_itm;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser_v2;
import gplx.xowa.xtns.wbases.stores.Wbase_prop_mgr;

public class Scrib_lib_wikibase_srl {
	public static Keyval[] Srl(Wbase_prop_mgr prop_mgr, Wdata_doc wdoc, boolean header_enabled, boolean legacy_style, byte[] page_url) {// REF.MW:/Wikibase/lib/includes/serializers/EntitySerializer.php!getSerialized; http://www.mediawiki.org/wiki/Extension:Wikibase_Client/Lua
		int base_adj = legacy_style ? 0 : 1;
		List_adp rv = List_adp_.New();
		if (header_enabled) {
			byte[] qid = wdoc.Qid();
			Wbase_enum_itm entity_itm = Wbase_claim_entity_type_.ToTid(qid);
			rv.Add(Keyval_.new_("id", qid));
			rv.Add(Keyval_.new_("type", entity_itm.Key_str()));	// type should be "property"; PAGE:ru.w:Викитека:Проект:Викиданные DATE:2016-11-23
			rv.Add(Keyval_.new_("schemaVersion", base_adj + 1));	// NOTE: needed by mw.wikibase.lua

			// for Property pages, add a "datatype" property PAGE:ru.w:Маргарян,_Андраник_Наапетович; wd:Property:P18; DATE:2017-03-27
			if (entity_itm == Wbase_claim_entity_type_.Itm__property) {
				String pid_name = String_.new_u8(Bry_.Mid(qid, Wdata_wiki_mgr.Ns_property_name_bry.length + 1));// +1 for ":" in "Property:"
				rv.Add(Keyval_.new_("datatype", prop_mgr.Get_or_null(pid_name, page_url)));
			}
		}
		Srl_root(rv, Wdata_doc_parser_v2.Str_labels			, Srl_langtexts	(Wdata_dict_langtext.Itm__language.Key_str(), Wdata_dict_langtext.Itm__value.Key_str(), wdoc.Label_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_descriptions	, Srl_langtexts	(Wdata_dict_langtext.Itm__language.Key_str(), Wdata_dict_langtext.Itm__value.Key_str(), wdoc.Descr_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_sitelinks		, Srl_sitelinks	(Wdata_dict_sitelink.Itm__site.Key_str()	, Wdata_dict_sitelink.Itm__title.Key_str(), wdoc.Slink_list(), base_adj));
		Srl_root(rv, Wdata_doc_parser_v2.Str_aliases		, Srl_aliases	(base_adj, wdoc.Alias_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_claims			, Srl_claims	(base_adj, legacy_style, prop_mgr, wdoc.Claim_list(), page_url));
		return (Keyval[])rv.ToAry(Keyval.class);
	}
	private static void Srl_root(List_adp rv, String label, Keyval[] ary) {
		if (ary == null) return;	// don't add node if empty; EX: labels:{} should not add "labels" kv
		rv.Add(Keyval_.new_(label, ary));
	}
	private static Keyval[] Srl_langtexts(String lang_label, String text_label, Ordered_hash list) {
		int len = list.Len(); if (len == 0) return null;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Wdata_langtext_itm itm = (Wdata_langtext_itm)list.Get_at(i);
			String lang = String_.new_u8(itm.Lang());
			String text = String_.new_u8(itm.Text());
			rv[i] = Keyval_.new_(lang, Keyval_.Ary(Keyval_.new_(lang_label, lang), Keyval_.new_(text_label, text)));
		}
		return rv;
	}
	private static Keyval[] Srl_sitelinks(String key_label, String val_label, Ordered_hash list, int base_adj) {
		int len = list.Len(); if (len == 0) return null;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.Get_at(i);
			String site = String_.new_u8(itm.Site());
			String name = String_.new_u8(itm.Name());
			rv[i] = Keyval_.new_(site, Keyval_.Ary(Keyval_.new_(key_label, site), Keyval_.new_(val_label, name), Srl_sitelinks_badges(itm.Badges(), base_adj)));
		}
		return rv;
	}
	private static Keyval Srl_sitelinks_badges(byte[][] badges, int base_adj) {	// DATE:2014-11-13
		if (badges == null) badges = Bry_.Ary_empty;	// null badges -> badges:[]
		int len = badges.length;
		Keyval[] kvs = len == 0 ? Keyval_.Ary_empty : new Keyval[len];
		for (int i = 0; i < len; i++) {
			byte[] badge = badges[i];
			kvs[i] = Keyval_.int_(i + base_adj, String_.new_u8(badge));
		}
		return Keyval_.new_("badges", kvs);
	}
	private static Keyval[] Srl_aliases(int base_adj, Ordered_hash list) {
		int len = list.Len(); if (len == 0) return null;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Wdata_alias_itm itm = (Wdata_alias_itm)list.Get_at(i);
			String lang = String_.new_u8(itm.Lang());
			rv[i] = Keyval_.new_(lang, Srl_aliases_langs(base_adj, lang, itm.Vals()));
		}
		return rv;
	}
	private static Keyval[] Srl_aliases_langs(int base_adj, String lang, byte[][] ary) {
		int len = ary.length;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			byte[] itm = ary[i];
			rv[i] = Keyval_.int_(i + base_adj, Keyval_.Ary(Keyval_.new_(Wdata_dict_langtext.Itm__language.Key_str(), lang), Keyval_.new_(Wdata_dict_langtext.Itm__value.Key_str(), String_.new_u8(itm))));	// NOTE: using same base_adj logic as claims
		}
		return rv;
	}
	private static Keyval[] Srl_claims(int base_adj, boolean legacy_style, Wbase_prop_mgr prop_mgr, Ordered_hash claim_grps, byte[] page_url) {
		int len = claim_grps.Len(); if (len == 0) return null;
		Scrib_lib_wikibase_srl_visitor visitor = new Scrib_lib_wikibase_srl_visitor();
		int rv_len = legacy_style ? len * 2 : len;	// NOTE: legacyStyle returns 2 sets of properties: official "P" and legacy "p"; DATE:2014-05-11
		Keyval[] rv = new Keyval[rv_len];
		for (int i = 0; i < len; i++) {
			Wbase_claim_grp grp = (Wbase_claim_grp)claim_grps.Get_at(i);
			String pid_str = Int_.To_str(grp.Id());
			Keyval[] grp_val = Srl_claims_prop_grp(prop_mgr, visitor, "P" + pid_str, grp, base_adj, page_url);
			rv[i] = Keyval_.new_("P" + pid_str, grp_val);
			if (legacy_style)
				rv[i + len] = Keyval_.new_("p" + pid_str, grp_val);	// SEE:WikibaseLuaBindings.php; This is a B/C hack to allow existing lua code to use hardcoded IDs in both lower (legacy) and upper case.; DATE:2014-05-11
		}
		return rv;
	}
	private static Keyval[] Srl_claims_prop_grp(Wbase_prop_mgr prop_mgr, Scrib_lib_wikibase_srl_visitor visitor, String pid, Wbase_claim_grp grp, int base_adj, byte[] page_url) {
		int len = grp.Len();
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Wbase_claim_base itm = grp.Get_at(i);
			rv[i] = Keyval_.int_(i + base_adj, Srl_claims_prop_itm(prop_mgr, visitor, pid, itm, base_adj, page_url));	// NOTE: must be super 0 or super 1; DATE:2014-05-09
		}
		return rv;
	}
	public static Keyval[] Srl_claims_prop_ary(Wbase_prop_mgr prop_mgr, String pid, Wbase_claim_base[] itms, int base_adj, byte[] page_url) {
		Scrib_lib_wikibase_srl_visitor visitor = new Scrib_lib_wikibase_srl_visitor();
		int len = itms.length;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Wbase_claim_base itm = itms[i];
			rv[i] = Keyval_.int_(i + base_adj, Srl_claims_prop_itm(prop_mgr, visitor, pid, itm, base_adj, page_url));	// NOTE: must be super 0 or super 1; DATE:2014-05-09
		}
		return rv;
	}
	private static Keyval[] Srl_claims_prop_itm(Wbase_prop_mgr prop_mgr, Scrib_lib_wikibase_srl_visitor visitor, String pid, Wbase_claim_base itm, int base_adj, byte[] page_url) {
		List_adp list = List_adp_.New();
		list.Add(Keyval_.new_("id", pid));
		list.Add(Keyval_.new_("mainsnak", Srl_claims_prop_itm_core(prop_mgr, visitor, pid, itm, page_url)));
		list.Add(Keyval_.new_(Wdata_dict_claim_v1.Str_rank, Wbase_claim_rank_.Reg.Get_str_or_fail(itm.Rank_tid())));
		list.Add(Keyval_.new_("type", itm.Prop_type()));
		Srl_root(list, Wdata_dict_claim.Itm__qualifiers.Key_str(), Srl_qualifiers(prop_mgr, visitor, itm.Qualifiers(), base_adj, page_url));
		Srl_root(list, Wdata_dict_claim.Itm__qualifiers_order.Key_str(), Srl_qualifiers_order(prop_mgr, visitor, itm.Qualifiers_order(), base_adj, page_url));
		Srl_root(list, Wdata_dict_claim.Itm__references.Key_str(), Srl_references(prop_mgr, visitor, itm.References(), base_adj, page_url));
		return (Keyval[])list.ToAryAndClear(Keyval.class);
	}
	private static Keyval[] Srl_qualifiers(Wbase_prop_mgr prop_mgr, Scrib_lib_wikibase_srl_visitor visitor, Wbase_claim_grp_list list, int base_adj, byte[] page_url) {
		if (list == null) return null;
		int list_len = list.Len(); if (list_len == 0) return Keyval_.Ary_empty;
		List_adp rv = List_adp_.New();
		List_adp pid_list = List_adp_.New();
		for (int i = 0; i < list_len; ++i) {
			Wbase_claim_grp grp = list.Get_at(i);
			int grp_len = grp.Len();
			pid_list.Clear();
			String itm_pid = grp.Id_str();
			for (int j = 0; j < grp_len; ++j) {
				Wbase_claim_base itm = grp.Get_at(j);
				pid_list.Add(Keyval_.int_(j + base_adj, Srl_claims_prop_itm_core(prop_mgr, visitor, itm_pid, itm, page_url)));	// NOTE: was originally "+ 1"; changed to base_adj; PAGE:ru.w:Tor ru.w:Кактусовые DATE:2014-10-25
			}
			rv.Add(Keyval_.new_(itm_pid, pid_list.ToAryAndClear(Keyval.class)));
		}
		return (Keyval[])rv.ToAryAndClear(Keyval.class);
	}
	private static Keyval[] Srl_qualifiers_order(Wbase_prop_mgr prop_mgr, Scrib_lib_wikibase_srl_visitor visitor, int[] list, int base_adj, byte[] page_url) {
		if (list == null) return null;
		int list_len = list.length; if (list_len == 0) return Keyval_.Ary_empty;
		List_adp rv = List_adp_.New();
		for (int i = 0; i < list_len; ++i) {
			String itm_pid = "P" + Int_.To_str(list[i]);
			rv.Add(Keyval_.int_(i + base_adj, itm_pid));
		}
		return (Keyval[])rv.ToAryAndClear(Keyval.class);
	}
	private static Keyval[] Srl_references(Wbase_prop_mgr prop_mgr, Scrib_lib_wikibase_srl_visitor visitor, Wbase_references_grp[] list, int base_adj, byte[] page_url) {
		if (list == null) return null;
		int list_len = list.length; if (list_len == 0) return Keyval_.Ary_empty;
		List_adp rv = List_adp_.New();
		for (int i = 0; i < list_len; ++i) {
			Wbase_references_grp references_grp = list[i];
			Keyval[] references_kvs = new Keyval[3];
			references_kvs[0] = Keyval_.new_("hash", references_grp.Hash());
			references_kvs[1] = Keyval_.new_("snaks", Srl_qualifiers(prop_mgr, visitor, references_grp.Snaks(), base_adj, page_url));
			references_kvs[2] = Keyval_.new_("snaks-order", Srl_qualifiers_order(prop_mgr, visitor, references_grp.Snaks_order(), base_adj, page_url));
			rv.Add(Keyval_.int_(i + base_adj, references_kvs));
		}
		return (Keyval[])rv.ToAryAndClear(Keyval.class);
	}
	private static Keyval[] Srl_claims_prop_itm_core(Wbase_prop_mgr prop_mgr, Scrib_lib_wikibase_srl_visitor visitor, String pid, Wbase_claim_base itm, byte[] page_url) {
		boolean snak_is_valued = itm.Snak_tid() == Wbase_claim_value_type_.Tid__value; // PURPOSE: was != Wbase_claim_value_type_.Tid__novalue; PAGE:it.s:Autore:Anonimo DATE:2015-12-06 
		int snak_is_valued_adj = snak_is_valued ? 1 : 0;
		Keyval[] rv = new Keyval[3 + snak_is_valued_adj];
		if (snak_is_valued)	// NOTE: novalue must not return slot (no datavalue node in json); PAGE:ru.w:Лимонов,_Эдуард_Вениаминович; DATE:2015-02-16; ALSO: sv.w:Joseph_Jaquet; DATE:2015-07-31
			rv[0] = Keyval_.new_("datavalue", Srl_claims_prop_itm_core_val(visitor, itm));
		rv[0 + snak_is_valued_adj] = Keyval_.new_("property", pid);
		rv[1 + snak_is_valued_adj] = Keyval_.new_("snaktype", Wbase_claim_value_type_.Reg.Get_str_or_fail(itm.Snak_tid()));

		// get prop datatype; NOTE: datatype needed for Modules; PAGE:eo.w:WikidataKoord; DATE:2015-11-08
		String datatype = prop_mgr.Get_or_null(pid, page_url);
		if (datatype == null)	// if null, fallback to value based on tid; needed for (a) tests and (b) old wbase dbs that don't have wbase_prop tbl; DATE:2016-12-01
			datatype = Wbase_claim_type_.Get_scrib_or_unknown(itm.Val_tid());
		rv[2 + snak_is_valued_adj] = Keyval_.new_("datatype", datatype);
		return rv;
	}
	private static Keyval[] Srl_claims_prop_itm_core_val(Scrib_lib_wikibase_srl_visitor visitor, Wbase_claim_base itm) {
		switch (itm.Snak_tid()) {
			case Wbase_claim_value_type_.Tid__somevalue:		return Datavalue_somevalue;
			case Wbase_claim_value_type_.Tid__novalue:		return Datavalue_novalue;	// TODO_OLD: throw exc
			default:
				itm.Welcome(visitor);
				return visitor.Rv();
			}
	}
	public static final String Key_type = "type", Key_value = "value";
	private static final Keyval[] Datavalue_somevalue = Keyval_.Ary_empty;	// changed to not return value-node; PAGE:it.s:Autore:Anonimo DATE:2015-12-06 // new Keyval[] {Keyval_.new_(Key_type, ""), Keyval_.new_(Key_value, "")};	// NOTE: must return ""; null fails; EX:w:Joseph-François_Malgaigne; DATE:2014-04-07
	private static final Keyval[] Datavalue_novalue = Keyval_.Ary_empty;
}
