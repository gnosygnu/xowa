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
		Srl_root(rv, Wdata_doc_parser_v2.Str_descriptions	, Srl_langtexts	(Wdata_dict_langtext.Str_language	, Wdata_dict_langtext.Str_value, wdoc.Descr_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_sitelinks		, Srl_sitelinks	(Wdata_dict_sitelink.Str_site		, Wdata_dict_sitelink.Str_title, wdoc.Slink_list(), base_adj));
		Srl_root(rv, Wdata_doc_parser_v2.Str_aliases		, Srl_aliases	(base_adj, wdoc.Alias_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_claims			, Srl_claims	(base_adj, legacy_style, wdoc.Claim_list()));
		return (KeyVal[])rv.Xto_ary(KeyVal.class);
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
	private static KeyVal[] Srl_sitelinks(String key_label, String val_label, OrderedHash list, int base_adj) {
		int len = list.Count(); if (len == 0) return null;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.FetchAt(i);
			String site = String_.new_utf8_(itm.Site());
			String name = String_.new_utf8_(itm.Name());
			rv[i] = KeyVal_.new_(site, KeyVal_.Ary(KeyVal_.new_(key_label, site), KeyVal_.new_(val_label, name), Srl_sitelinks_badges(itm.Badges(), base_adj)));
		}
		return rv;
	}
	private static KeyVal Srl_sitelinks_badges(byte[][] badges, int base_adj) {	// DATE:2014-11-13
		if (badges == null) badges = Bry_.Ary_empty;	// null badges -> badges:[]
		int len = badges.length;
		KeyVal[] kvs = len == 0 ? KeyVal_.Ary_empty : new KeyVal[len];
		for (int i = 0; i < len; i++) {
			byte[] badge = badges[i];
			kvs[i] = KeyVal_.int_(i + base_adj, String_.new_utf8_(badge));
		}
		return KeyVal_.new_("badges", kvs);
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
		int len = grp.Len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; i++) {
			Wdata_claim_itm_core itm = grp.Get_at(i);
			rv[i] = KeyVal_.int_(i + base_adj, Srl_claims_prop_itm(pid, itm, base_adj));	// NOTE: must be super 0 or super 1; DATE:2014-05-09
		}
		return rv;
	}
	private static KeyVal[] Srl_claims_prop_itm(String pid, Wdata_claim_itm_core itm, int base_adj) {
		ListAdp list = ListAdp_.new_();
		list.Add(KeyVal_.new_("id", pid));
		list.Add(KeyVal_.new_("mainsnak", Srl_claims_prop_itm_core(pid, itm)));
		list.Add(KeyVal_.new_(Wdata_dict_claim_v1.Str_rank, Wdata_dict_rank.Xto_str(itm.Rank_tid())));
		list.Add(KeyVal_.new_("type", itm.Prop_type()));
		Srl_root(list, Wdata_dict_claim.Str_qualifiers, Srl_qualifiers(itm.Qualifiers(), base_adj));
		return (KeyVal[])list.Xto_ary_and_clear(KeyVal.class);
	}
	private static KeyVal[] Srl_qualifiers(Wdata_claim_grp_list list, int base_adj) {
		if (list == null) return null;
		int list_len = list.Len(); if (list_len == 0) return KeyVal_.Ary_empty;
		ListAdp rv = ListAdp_.new_();
		ListAdp pid_list = ListAdp_.new_();
		for (int i = 0; i < list_len; ++i) {
			Wdata_claim_grp grp = list.Get_at(i);
			int grp_len = grp.Len();
			pid_list.Clear();
			String itm_pid = grp.Id_str();
			for (int j = 0; j < grp_len; ++j) {
				Wdata_claim_itm_core itm = grp.Get_at(j);
				pid_list.Add(KeyVal_.int_(j + base_adj, Srl_claims_prop_itm_core(itm_pid, itm)));	// NOTE: was originally "+ 1"; changed to base_adj; PAGE:ru.w:Tor ru.w:Кактусовые DATE:2014-10-25
			}
			rv.Add(KeyVal_.new_(itm_pid, (KeyVal[])pid_list.Xto_ary_and_clear(KeyVal.class)));
		}
		return (KeyVal[])rv.Xto_ary_and_clear(KeyVal.class);
	}
	private static KeyVal[] Srl_claims_prop_itm_core(String pid, Wdata_claim_itm_core itm) {
		KeyVal[] rv = new KeyVal[3];
		rv[0] = KeyVal_.new_("datavalue", Srl_claims_prop_itm_core_val(itm));
		rv[1] = KeyVal_.new_("property", pid);
		rv[2] = KeyVal_.new_("snaktype", Wdata_dict_snak_tid.Xto_str(itm.Snak_tid()));
		return rv;
	}
	private static final Scrib_lib_wikibase_srl_visitor visitor = new Scrib_lib_wikibase_srl_visitor();
	private static KeyVal[] Srl_claims_prop_itm_core_val(Wdata_claim_itm_core itm) {
		switch (itm.Snak_tid()) {
			case Wdata_dict_snak_tid.Tid_somevalue:
			case Wdata_dict_snak_tid.Tid_novalue:	// NOTE: not sure about this logic, but can't find somevalue snaktid formattercode in wikibase; DATE:2014-04-07
				return DataValue_nil;
			default:
				itm.Welcome(visitor);
				return visitor.Rv();
			}
	}
	public static final String Key_type = "type", Key_value = "value";
	private static final KeyVal[] DataValue_nil = new KeyVal[] {KeyVal_.new_(Key_type, ""), KeyVal_.new_(Key_value, "")};	// NOTE: must return ""; null fails; EX:w:Joseph-François_Malgaigne; DATE:2014-04-07
}
