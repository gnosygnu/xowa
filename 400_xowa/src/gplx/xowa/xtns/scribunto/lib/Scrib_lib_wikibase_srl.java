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
import gplx.json.*; import gplx.xowa.xtns.wdatas.*;
class Scrib_lib_wikibase_srl {
	public static KeyVal[] Srl(Wdata_doc_parser parser, Wdata_doc doc, boolean header_enabled, boolean legacy_style) {// REF.MW:/Wikibase/lib/includes/serializers/EntitySerializer.php!getSerialized; http://www.mediawiki.org/wiki/Extension:Wikibase_Client/Lua
		int base_adj = legacy_style ? 0 : 1;
		ListAdp rv = ListAdp_.new_();
		if (header_enabled) {
			rv.Add(KeyVal_.new_("id", doc.Qid()));
			rv.Add(KeyVal_.new_("type", Wdata_doc_consts.Val_ent_entity_type_item_str));
			rv.Add(KeyVal_.new_("schemaVersion", base_adj + 1));	// NOTE: needed by mw.wikibase.lua
		}
		Json_itm_nde root = doc.Doc().Root();
		int len = root.Subs_len();
		for (int i = 0; i < len; i++) {
			Json_itm_kv sub = (Json_itm_kv)root.Subs_get_at(i);
			String key = sub.Key_as_str();
			if			(String_.Eq(key, Wdata_doc_consts.Key_atr_label_str))			rv.Add(KeyVal_.new_("labels", Srl_nde_langs("language", "value", (Json_grp)sub.Val())));
			else if		(String_.Eq(key, Wdata_doc_consts.Key_atr_description_str))		rv.Add(KeyVal_.new_("descriptions", Srl_nde_langs("language", "value", (Json_grp)sub.Val())));
			else if		(String_.Eq(key, Wdata_doc_consts.Key_atr_links_str))			rv.Add(KeyVal_.new_("sitelinks", Srl_nde_sitelinks("site", "title", (Json_grp)sub.Val())));
			else if		(String_.Eq(key, Wdata_doc_consts.Key_atr_aliases_str))			rv.Add(KeyVal_.new_("aliases", Srl_aliases((Json_grp)sub.Val(), base_adj)));
			else if		(String_.Eq(key, Wdata_doc_consts.Key_atr_claims_str))			rv.Add(KeyVal_.new_(Wdata_doc_consts.Key_atr_claims_str, Srl_claims(parser, doc.Doc().Src(), (Json_itm_ary)sub.Val(), legacy_style, base_adj)));
		}
		return (KeyVal[])rv.XtoAry(KeyVal.class);
	}
	private static KeyVal[] Srl_nde_sitelinks(String key_label, String val_label, Json_grp grp) {
		int len = grp.Subs_len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Json_itm_kv sub = (Json_itm_kv)grp.Subs_get_at(i);
			String lang = sub.Key_as_str();													// key is lang; EX: enwiki
			Json_itm sub_val = sub.Val();
			Json_itm_kv sub_kv = null;
			if (sub_val.Tid() == Json_itm_.Tid_nde) {										// new fmt; EX: '"enwiki":{"name":"Earth", "badges":[]}'; DATE:2014-02-03
				Json_itm_nde sub_nde = (Json_itm_nde)sub.Val();								// key is nde; see EX: above
				sub_kv = (Json_itm_kv)sub_nde.Subs_get_by_key(Wdata_doc_.Key_name);			// get "name" sub
			}
			else																			// old fmt; EX: '"enwiki":"Earth"';DATE:2014-02-06
				sub_kv = sub;
			Object description = sub_kv == null ? null : sub_kv.Val().Data();				// get val of "name"; EX: Earth
			rv[i] = KeyVal_.new_(lang, KeyVal_.Ary(KeyVal_.new_(key_label, lang), KeyVal_.new_(val_label, description)));
		}
		return rv;
	}
	private static KeyVal[] Srl_nde_langs(String key_label, String val_label, Json_grp grp) {
		int len = grp.Subs_len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Json_itm_kv sub = (Json_itm_kv)grp.Subs_get_at(i);
			String lang = sub.Key_as_str();
			Object description = sub.Val().Data();
			rv[i] = KeyVal_.new_(lang, KeyVal_.Ary(KeyVal_.new_(key_label, lang), KeyVal_.new_(val_label, description)));
		}
		return rv;
	}
	private static KeyVal[] Srl_aliases(Json_grp grp, int base_adj) {
		int len = grp.Subs_len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Json_itm_kv sub = (Json_itm_kv)grp.Subs_get_at(i);
			String lang = sub.Key_as_str();
			rv[i] = KeyVal_.new_(lang, Srl_aliases_langs(lang, (Json_grp)sub.Val(), base_adj));
		}
		return rv;
	}
	private static KeyVal[] Srl_aliases_langs(String lang, Json_grp ary, int base_adj) {
		int len = ary.Subs_len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Json_itm sub = ary.Subs_get_at(i);
			rv[i] = KeyVal_.int_(i + base_adj, KeyVal_.Ary(KeyVal_.new_("language", lang), KeyVal_.new_("value", sub.Data())));	// NOTE: using same base_adj logic as claims
		}
		return rv;
	}
	private static KeyVal[] Srl_claims(Wdata_doc_parser parser, byte[] src, Json_itm_ary claims_nde, boolean legacy_style, int base_adj) {
		OrderedHash props = parser.Bld_props(src, claims_nde);
		int len = props.Count();
		int rv_len = legacy_style ? len * 2 : len;	// NOTE: legacyStyle returns 2 sets of properties: official "P" and legacy "p"; DATE:2014-05-11
		KeyVal[] rv = new KeyVal[rv_len];
		for (int i = 0; i < len; i++) {
			Wdata_prop_grp grp = (Wdata_prop_grp)props.FetchAt(i);
			String pid_str = Int_.XtoStr(grp.Id());
			KeyVal[] grp_val = Srl_claims_prop_grp("P" + pid_str, grp, base_adj);
			rv[i] = KeyVal_.new_("P" + pid_str, grp_val);
			if (legacy_style)
				rv[i + len] = KeyVal_.new_("p" + pid_str, grp_val);	// SEE:WikibaseLuaBindings.php; This is a B/C hack to allow existing lua code to use hardcoded IDs in both lower (legacy) and upper case.; DATE:2014-05-11
		}
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_grp(String pid, Wdata_prop_grp grp, int base_adj) {
		int len = grp.Itms_len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Wdata_prop_itm_core itm = grp.Itms_get_at(i);
			rv[i] = KeyVal_.int_(i + base_adj, Srl_claims_prop_itm(pid, itm));	// NOTE: must be super 0 or super 1; DATE:2014-05-09
		}
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm(String pid, Wdata_prop_itm_core itm) {
		ListAdp list = ListAdp_.new_();
		list.Add(KeyVal_.new_("id", String_.new_utf8_(itm.Wguid())));
		list.Add(KeyVal_.new_("mainsnak", Srl_claims_prop_itm_core(pid, itm)));
		list.Add(KeyVal_.new_(Wdata_doc_consts.Key_claims_rank_str, itm.Rank_str()));
		list.Add(KeyVal_.new_("type", itm.Prop_type()));
		return (KeyVal[])list.XtoAryAndClear(KeyVal.class);
	}
	private static KeyVal[] Srl_claims_prop_itm_core(String pid, Wdata_prop_itm_core itm) {
		KeyVal[] rv = new KeyVal[3];
		rv[0] = KeyVal_.new_("datavalue", Srl_claims_prop_itm_core_val(itm));
		rv[1] = KeyVal_.new_("property", pid);
		rv[2] = KeyVal_.new_("snaktype", itm.Snak_str());
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_val(Wdata_prop_itm_core itm) {
		switch (itm.Snak_tid()) {
			case Wdata_prop_itm_base_.Snak_tid_somevalue:
			case Wdata_prop_itm_base_.Snak_tid_novalue:	// NOTE: not sure about this logic, but can't find somevalue snaktid formattercode in wikibase; DATE:2014-04-07
				return DataValue_nil;
			default:
				switch (itm.Val_tid_byte()) {
					case Wdata_prop_itm_base_.Val_tid_string			: return Srl_claims_prop_itm_core_str(itm);
					case Wdata_prop_itm_base_.Val_tid_entity			: return Srl_claims_prop_itm_core_entity(itm);
					case Wdata_prop_itm_base_.Val_tid_time				: return Srl_claims_prop_itm_core_time(itm);
					case Wdata_prop_itm_base_.Val_tid_globecoordinate	: return Srl_claims_prop_itm_core_globecoordinate(itm);
					case Wdata_prop_itm_base_.Val_tid_quantity			: return Srl_claims_prop_itm_core_quantity(itm);
					default: return KeyVal_.Ary_empty;
				}
			}
	}
	private static KeyVal[] Srl_claims_prop_itm_core_str(Wdata_prop_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, itm.Val_tid_str());
		rv[1] = KeyVal_.new_(Key_value, String_.new_utf8_(itm.Val()));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_entity(Wdata_prop_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_prop_itm_base_.Val_str_entity);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_entity_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_entity_value(Wdata_prop_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Wdata_doc_consts.Key_ent_entity_type_str, Wdata_doc_consts.Val_ent_entity_type_item_str);
		rv[1] = KeyVal_.new_(Wdata_doc_consts.Key_ent_numeric_id_str, String_.new_utf8_(itm.Val()));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_time(Wdata_prop_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_doc_consts.Key_time_time_str);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_time_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_time_value(Wdata_prop_itm_core itm) {
		KeyVal[] rv = new KeyVal[6];
		rv[0] = KeyVal_.new_(Wdata_doc_consts.Key_time_time_str				, String_.new_ascii_(itm.Val()));
		rv[1] = KeyVal_.new_(Wdata_doc_consts.Key_time_precision_str		, Wdata_doc_consts.Val_time_precision_int);	// NOTE: must return int, not str; DATE:2014-02-18
		rv[2] = KeyVal_.new_(Wdata_doc_consts.Key_time_before_str			, Wdata_doc_consts.Val_time_before_int);
		rv[3] = KeyVal_.new_(Wdata_doc_consts.Key_time_after_str			, Wdata_doc_consts.Val_time_after_int);
		rv[4] = KeyVal_.new_(Wdata_doc_consts.Key_time_timezone_str			, Wdata_doc_consts.Val_time_timezone_str);
		rv[5] = KeyVal_.new_(Wdata_doc_consts.Key_time_calendarmodel_str	, Wdata_doc_consts.Val_time_calendarmodel_str);
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_globecoordinate(Wdata_prop_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_doc_consts.Key_geo_type_str);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_globecoordinate_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_globecoordinate_value(Wdata_prop_itm_core itm) {
		byte[][] flds = Bry_.Split(itm.Val(), Byte_ascii.Pipe);
		KeyVal[] rv = new KeyVal[5];
		rv[0] = KeyVal_.new_(Wdata_doc_consts.Key_geo_latitude_str			, Double_.parse_(String_.new_ascii_(flds[0])));
		rv[1] = KeyVal_.new_(Wdata_doc_consts.Key_geo_longitude_str			, Double_.parse_(String_.new_ascii_(flds[1])));
		rv[2] = KeyVal_.new_(Wdata_doc_consts.Key_geo_altitude_str			, null);
		rv[3] = KeyVal_.new_(Wdata_doc_consts.Key_geo_globe_str				, Wdata_doc_consts.Val_time_globe_bry);
		rv[4] = KeyVal_.new_(Wdata_doc_consts.Key_geo_precision_str			, .00001d);
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_quantity(Wdata_prop_itm_core itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Key_type, Wdata_doc_consts.Key_quantity_type_str);
		rv[1] = KeyVal_.new_(Key_value, Srl_claims_prop_itm_core_quantity_value(itm));
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm_core_quantity_value(Wdata_prop_itm_core itm) {
		byte[][] flds = Bry_.Split(itm.Val(), Byte_ascii.Pipe);
		KeyVal[] rv = new KeyVal[4];
		rv[0] = KeyVal_.new_(Wdata_doc_consts.Key_quantity_amount_str		, String_.new_utf8_(flds[0]));
		rv[1] = KeyVal_.new_(Wdata_doc_consts.Key_quantity_unit_str			, String_.new_utf8_(flds[1]));
		rv[2] = KeyVal_.new_(Wdata_doc_consts.Key_quantity_ubound_str		, String_.new_utf8_(flds[2]));
		rv[3] = KeyVal_.new_(Wdata_doc_consts.Key_quantity_lbound_str		, String_.new_utf8_(flds[3]));
		return rv;
	}
	private static final String Key_type = "type", Key_value = "value";
	private static final KeyVal[] DataValue_nil = new KeyVal[] {KeyVal_.new_(Key_type, ""), KeyVal_.new_(Key_value, "")};	// NOTE: must return ""; null fails; EX:w:Joseph-Fran�ois_Malgaigne; DATE:2014-04-07
}