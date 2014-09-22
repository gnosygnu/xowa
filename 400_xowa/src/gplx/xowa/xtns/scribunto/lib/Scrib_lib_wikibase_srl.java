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
package gplx.xowa.xtns.scribunto.lib; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.xtns.wdatas.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;
class Scrib_lib_wikibase_srl {
	public static KeyVal[] Srl(Wdata_doc wdoc, boolean header_enabled, boolean legacy_style) {// REF.MW:/Wikibase/lib/includes/serializers/EntitySerializer.php!getSerialized; http://www.mediawiki.org/wiki/Extension:Wikibase_Client/Lua
		int base_adj = legacy_style ? 0 : 1;
		ListAdp rv = ListAdp_.new_();
		if (header_enabled) {
			rv.Add(KeyVal_.new_("id", wdoc.Qid()));
			rv.Add(KeyVal_.new_("type", Wdata_dict_value_entity.Val_entity_type_item_str));
			rv.Add(KeyVal_.new_("schemaVersion", base_adj + 1));	// NOTE: needed by mw.wikibase.lua
		}
		Srl_root(rv, Wdata_doc_parser_v2.Str_labels			, Srl_langtexts	(Wdata_dict_langtext.Str_language	, Wdata_dict_langtext.Str_value, wdoc.Label_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_descriptions	, Srl_langtexts	(Wdata_dict_langtext.Str_language	, Wdata_dict_langtext.Str_value, wdoc.Description_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_sitelinks		, Srl_sitelinks	(Wdata_dict_sitelink.Str_site		, Wdata_dict_sitelink.Str_title, wdoc.Sitelink_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_aliases		, Srl_aliases	(base_adj, wdoc.Alias_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_claims			, Srl_claims	(base_adj, legacy_style, wdoc.Claim_list()));
		return (KeyVal[])rv.XtoAry(KeyVal.class);
	}
	private static void Srl_root(ListAdp rv, String label, KeyVal[] ary) {
		if (ary == null) return;	// don't add node if empty; EX: labels:{} should not add "labels" kv
		rv.Add(KeyVal_.new_(label, ary));
	}
	private static KeyVal[] Srl_langtexts(String lang_label, String text_label, OrderedHash list) {
		int len = list.Count(); if (len == 0) return null;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Wdata_langtext_itm itm = (Wdata_langtext_itm)list.FetchAt(i);
			String lang = String_.new_utf8_(itm.Lang());
			String text = String_.new_utf8_(itm.Text());
			rv[i] = KeyVal_.new_(lang, KeyVal_.Ary(KeyVal_.new_(lang_label, lang), KeyVal_.new_(text_label, text)));
		}
		return rv;
	}
	private static KeyVal[] Srl_sitelinks(String key_label, String val_label, OrderedHash list) {
		int len = list.Count(); if (len == 0) return null;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.FetchAt(i);
			String site = String_.new_utf8_(itm.Site());
			String name = String_.new_utf8_(itm.Name());
			rv[i] = KeyVal_.new_(site, KeyVal_.Ary(KeyVal_.new_(key_label, site), KeyVal_.new_(val_label, name)));
		}
		return rv;
	}
	private static KeyVal[] Srl_aliases(int base_adj, OrderedHash list) {
		int len = list.Count(); if (len == 0) return null;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Wdata_alias_itm itm = (Wdata_alias_itm)list.FetchAt(i);
			String lang = String_.new_utf8_(itm.Lang());
			rv[i] = KeyVal_.new_(lang, Srl_aliases_langs(base_adj, lang, itm.Vals()));
		}
		return rv;
	}
	private static KeyVal[] Srl_aliases_langs(int base_adj, String lang, byte[][] ary) {
		int len = ary.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			byte[] itm = ary[i];
			rv[i] = KeyVal_.int_(i + base_adj, KeyVal_.Ary(KeyVal_.new_(Wdata_dict_langtext.Str_language, lang), KeyVal_.new_(Wdata_dict_langtext.Str_value, String_.new_utf8_(itm))));	// NOTE: using same base_adj logic as claims
		}
		return rv;
	}
	private static KeyVal[] Srl_claims(int base_adj, boolean legacy_style, OrderedHash claim_grps) {
		int len = claim_grps.Count(); if (len == 0) return null;
		int rv_len = legacy_style ? len * 2 : len;	// NOTE: legacyStyle returns 2 sets of properties: official "P" and legacy "p"; DATE:2014-05-11
		KeyVal[] rv = new KeyVal[rv_len];
		for (int i = 0; i < len; i++) {
			Wdata_claim_grp grp = (Wdata_claim_grp)claim_grps.FetchAt(i);
			String pid_str = Int_.Xto_str(grp.Id());
			KeyVal[] grp_val = Srl_claims_prop_grp("P" + pid_str, grp, base_adj);
			rv[i] = KeyVal_.new_("P" + pid_str, grp_val);
			if (legacy_style)
				rv[i + len] = KeyVal_.new_("p" + pid_str, grp_val);	// SEE:WikibaseLuaBindings.php; This is a B/C hack to allow existing lua code to use hardcoded IDs in both lower (legacy) and upper case.; DATE:2014-05-11
		}
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_grp(String pid, Wdata_claim_grp grp, int base_adj) {
		int len = grp.Itms_len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Wdata_claim_itm_core itm = grp.Itms_get_at(i);
			rv[i] = KeyVal_.int_(i + base_adj, Srl_claims_prop_itm(pid, itm));	// NOTE: must be super 0 or super 1; DATE:2014-05-09
		}
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm(String pid, Wdata_claim_itm_core itm) {
		ListAdp list = ListAdp_.new_();
		list.Add(KeyVal_.new_("id", String_.new_utf8_(itm.Wguid())));
		list.Add(KeyVal_.new_("mainsnak", Srl_claims_prop_itm_core(pid, itm)));
		list.Add(KeyVal_.new_(Wdata_dict_claim_v1.Str_rank, Wdata_dict_rank.Xto_str(itm.Rank_tid())));
		list.Add(KeyVal_.new_("type", itm.Prop_type()));
		return (KeyVal[])list.XtoAryAndClear(KeyVal.class);
	}
	private static KeyVal[] Srl_claims_prop_itm_core(String pid, Wdata_claim_itm_core itm) {
		KeyVal[] rv = new KeyVal[3];
		rv[0] = KeyVal_.new_("datavalue", Srl_claims_prop_itm_core_val(itm));
		rv[1] = KeyVal_.new_("property", pid);
		rv[2] = KeyVal_.new_("snaktype", Wdata_dict_snak_tid.Xto_str(itm.Snak_tid()));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_val(Wdata_claim_itm_core itm) {
		switch (itm.Snak_tid()) {
			case Wdata_dict_snak_tid.Tid_somevalue:
			case Wdata_dict_snak_tid.Tid_novalue:	// NOTE: not sure about this logic, but can't find somevalue snaktid formattercode in wikibase; DATE:2014-04-07
				return DataValue_nil;
			default:
				switch (itm.Val_tid()) {
					case Wdata_dict_val_tid.Tid_string				: return Srl_claims_prop_itm_core_str(itm);
					case Wdata_dict_val_tid.Tid_entity				: return Srl_claims_prop_itm_core_entity(itm);
					case Wdata_dict_val_tid.Tid_time				: return Srl_claims_prop_itm_core_time(itm);
					case Wdata_dict_val_tid.Tid_globecoordinate		: return Srl_claims_prop_itm_core_globecoordinate(itm);
					case Wdata_dict_val_tid.Tid_quantity			: return Srl_claims_prop_itm_core_quantity(itm);
					case Wdata_dict_val_tid.Tid_monolingualtext		: return Srl_claims_prop_itm_core_monolingualtext(itm);
					default: return KeyVal_.Ary_empty;
				}
			}
	}
	private static KeyVal[] Srl_claims_prop_itm_core_str(Wdata_claim_itm_core itm) {
		Wdata_claim_itm_str claim_str = (Wdata_claim_itm_str)itm;
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_dict_val_tid.Xto_str(itm.Val_tid()));
		rv[1] = KeyVal_.new_(Key_value, String_.new_utf8_(claim_str.Val_str()));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_entity(Wdata_claim_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_dict_val_tid.Str_entity);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_entity_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_entity_value(Wdata_claim_itm_core itm) {
		Wdata_claim_itm_entity claim_entity = (Wdata_claim_itm_entity)itm;
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Wdata_dict_value_entity.Str_entity_type, Wdata_dict_value_entity.Val_entity_type_item_str);
		rv[1] = KeyVal_.new_(Wdata_dict_value_entity.Str_numeric_id, Int_.Xto_str(claim_entity.Entity_id()));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_time(Wdata_claim_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_dict_val_tid.Str_time);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_time_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_time_value(Wdata_claim_itm_core itm) {
		Wdata_claim_itm_time claim_time = (Wdata_claim_itm_time)itm;
		KeyVal[] rv = new KeyVal[6];
		rv[0] = KeyVal_.new_(Wdata_dict_value_time.Str_time				, String_.new_ascii_(claim_time.Time()));
		rv[1] = KeyVal_.new_(Wdata_dict_value_time.Str_precision		, Wdata_dict_value_time.Val_precision_int);	// NOTE: must return int, not str; DATE:2014-02-18
		rv[2] = KeyVal_.new_(Wdata_dict_value_time.Str_before			, Wdata_dict_value_time.Val_before_int);
		rv[3] = KeyVal_.new_(Wdata_dict_value_time.Str_after			, Wdata_dict_value_time.Val_after_int);
		rv[4] = KeyVal_.new_(Wdata_dict_value_time.Str_timezone			, Wdata_dict_value_time.Val_timezone_str);
		rv[5] = KeyVal_.new_(Wdata_dict_value_time.Str_calendarmodel	, Wdata_dict_value_time.Val_calendarmodel_str);
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_globecoordinate(Wdata_claim_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_dict_val_tid.Str_globecoordinate);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_globecoordinate_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_globecoordinate_value(Wdata_claim_itm_core itm) {
		Wdata_claim_itm_globecoordinate claim_globecoordinate = (Wdata_claim_itm_globecoordinate)itm;
		KeyVal[] rv = new KeyVal[5];
		rv[0] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_latitude			, Double_.parse_(String_.new_ascii_(claim_globecoordinate.Lat())));
		rv[1] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_longitude			, Double_.parse_(String_.new_ascii_(claim_globecoordinate.Lng())));
		rv[2] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_altitude			, null);
		rv[3] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_globe				, Wdata_dict_value_globecoordinate.Val_globe_dflt_str);
		rv[4] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_precision			, .00001d);
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_quantity(Wdata_claim_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_dict_val_tid.Str_quantity);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_quantity_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_quantity_value(Wdata_claim_itm_core itm) {
		Wdata_claim_itm_quantity claim_quantity = (Wdata_claim_itm_quantity)itm;
		KeyVal[] rv = new KeyVal[4];
		rv[0] = KeyVal_.new_(Wdata_dict_value_quantity.Str_amount			, String_.new_utf8_(claim_quantity.Amount()));
		rv[1] = KeyVal_.new_(Wdata_dict_value_quantity.Str_unit				, String_.new_utf8_(claim_quantity.Unit()));
		rv[2] = KeyVal_.new_(Wdata_dict_value_quantity.Str_upperbound		, String_.new_utf8_(claim_quantity.Ubound()));
		rv[3] = KeyVal_.new_(Wdata_dict_value_quantity.Str_lowerbound		, String_.new_utf8_(claim_quantity.Lbound()));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_monolingualtext(Wdata_claim_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_dict_value_monolingualtext.Str_language);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_monolingualtext_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_monolingualtext_value(Wdata_claim_itm_core itm) {
		Wdata_claim_itm_monolingualtext claim_monolingualtext = (Wdata_claim_itm_monolingualtext)itm;
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Wdata_dict_value_monolingualtext.Str_text			, String_.new_utf8_(claim_monolingualtext.Text()));
		rv[1] = KeyVal_.new_(Wdata_dict_value_monolingualtext.Str_language		, String_.new_utf8_(claim_monolingualtext.Lang()));
		return rv;
	}
	private static final String Key_type = "type", Key_value = "value";
	private static final KeyVal[] DataValue_nil = new KeyVal[] {KeyVal_.new_(Key_type, ""), KeyVal_.new_(Key_value, "")};	// NOTE: must return ""; null fails; EX:w:Joseph-Fran�ois_Malgaigne; DATE:2014-04-07
}