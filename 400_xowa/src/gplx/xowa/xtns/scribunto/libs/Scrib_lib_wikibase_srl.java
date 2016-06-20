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
import gplx.xowa.xtns.wdatas.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;
class Scrib_lib_wikibase_srl {
	public static Keyval[] Srl(Wdata_doc wdoc, boolean header_enabled, boolean legacy_style) {// REF.MW:/Wikibase/lib/includes/serializers/EntitySerializer.php!getSerialized; http://www.mediawiki.org/wiki/Extension:Wikibase_Client/Lua
		int base_adj = legacy_style ? 0 : 1;
		List_adp rv = List_adp_.New();
		if (header_enabled) {
			rv.Add(Keyval_.new_("id", wdoc.Qid()));
			rv.Add(Keyval_.new_("type", Wdata_dict_value_entity_tid.Str_item));
			rv.Add(Keyval_.new_("schemaVersion", base_adj + 1));	// NOTE: needed by mw.wikibase.lua
		}
		Srl_root(rv, Wdata_doc_parser_v2.Str_labels			, Srl_langtexts	(Wdata_dict_langtext.Str_language	, Wdata_dict_langtext.Str_value, wdoc.Label_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_descriptions	, Srl_langtexts	(Wdata_dict_langtext.Str_language	, Wdata_dict_langtext.Str_value, wdoc.Descr_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_sitelinks		, Srl_sitelinks	(Wdata_dict_sitelink.Str_site		, Wdata_dict_sitelink.Str_title, wdoc.Slink_list(), base_adj));
		Srl_root(rv, Wdata_doc_parser_v2.Str_aliases		, Srl_aliases	(base_adj, wdoc.Alias_list()));
		Srl_root(rv, Wdata_doc_parser_v2.Str_claims			, Srl_claims	(base_adj, legacy_style, wdoc.Claim_list()));
		return (Keyval[])rv.To_ary(Keyval.class);
	}
	private static void Srl_root(List_adp rv, String label, Keyval[] ary) {
		if (ary == null) return;	// don't add node if empty; EX: labels:{} should not add "labels" kv
		rv.Add(Keyval_.new_(label, ary));
	}
	private static Keyval[] Srl_langtexts(String lang_label, String text_label, Ordered_hash list) {
		int len = list.Count(); if (len == 0) return null;
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
		int len = list.Count(); if (len == 0) return null;
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
		int len = list.Count(); if (len == 0) return null;
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
			rv[i] = Keyval_.int_(i + base_adj, Keyval_.Ary(Keyval_.new_(Wdata_dict_langtext.Str_language, lang), Keyval_.new_(Wdata_dict_langtext.Str_value, String_.new_u8(itm))));	// NOTE: using same base_adj logic as claims
		}
		return rv;
	}
	private static Keyval[] Srl_claims(int base_adj, boolean legacy_style, Ordered_hash claim_grps) {
		int len = claim_grps.Count(); if (len == 0) return null;
		int rv_len = legacy_style ? len * 2 : len;	// NOTE: legacyStyle returns 2 sets of properties: official "P" and legacy "p"; DATE:2014-05-11
		Keyval[] rv = new Keyval[rv_len];
		for (int i = 0; i < len; i++) {
			Wdata_claim_grp grp = (Wdata_claim_grp)claim_grps.Get_at(i);
			String pid_str = Int_.To_str(grp.Id());
			Keyval[] grp_val = Srl_claims_prop_grp("P" + pid_str, grp, base_adj);
			rv[i] = Keyval_.new_("P" + pid_str, grp_val);
			if (legacy_style)
				rv[i + len] = Keyval_.new_("p" + pid_str, grp_val);	// SEE:WikibaseLuaBindings.php; This is a B/C hack to allow existing lua code to use hardcoded IDs in both lower (legacy) and upper case.; DATE:2014-05-11
		}
		return rv;
	}
	private static Keyval[] Srl_claims_prop_grp(String pid, Wdata_claim_grp grp, int base_adj) {
		int len = grp.Len();
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Wdata_claim_itm_core itm = grp.Get_at(i);
			rv[i] = Keyval_.int_(i + base_adj, Srl_claims_prop_itm(pid, itm, base_adj));	// NOTE: must be super 0 or super 1; DATE:2014-05-09
		}
		return rv;
	}
	private static Keyval[] Srl_claims_prop_itm(String pid, Wdata_claim_itm_core itm, int base_adj) {
		List_adp list = List_adp_.New();
		list.Add(Keyval_.new_("id", pid));
		list.Add(Keyval_.new_("mainsnak", Srl_claims_prop_itm_core(pid, itm)));
		list.Add(Keyval_.new_(Wdata_dict_claim_v1.Str_rank, Wdata_dict_rank.Xto_str(itm.Rank_tid())));
		list.Add(Keyval_.new_("type", itm.Prop_type()));
		Srl_root(list, Wdata_dict_claim.Str_qualifiers, Srl_qualifiers(itm.Qualifiers(), base_adj));
		return (Keyval[])list.To_ary_and_clear(Keyval.class);
	}
	private static Keyval[] Srl_qualifiers(Wdata_claim_grp_list list, int base_adj) {
		if (list == null) return null;
		int list_len = list.Len(); if (list_len == 0) return Keyval_.Ary_empty;
		List_adp rv = List_adp_.New();
		List_adp pid_list = List_adp_.New();
		for (int i = 0; i < list_len; ++i) {
			Wdata_claim_grp grp = list.Get_at(i);
			int grp_len = grp.Len();
			pid_list.Clear();
			String itm_pid = grp.Id_str();
			for (int j = 0; j < grp_len; ++j) {
				Wdata_claim_itm_core itm = grp.Get_at(j);
				pid_list.Add(Keyval_.int_(j + base_adj, Srl_claims_prop_itm_core(itm_pid, itm)));	// NOTE: was originally "+ 1"; changed to base_adj; PAGE:ru.w:Tor ru.w:Кактусовые DATE:2014-10-25
			}
			rv.Add(Keyval_.new_(itm_pid, (Keyval[])pid_list.To_ary_and_clear(Keyval.class)));
		}
		return (Keyval[])rv.To_ary_and_clear(Keyval.class);
	}
	private static Keyval[] Srl_claims_prop_itm_core(String pid, Wdata_claim_itm_core itm) {
		boolean snak_is_valued = itm.Snak_tid() == Wdata_dict_snak_tid.Tid_value; // PURPOSE: was != Wdata_dict_snak_tid.Tid_novalue; PAGE:it.s:Autore:Anonimo DATE:2015-12-06 
		int snak_is_valued_adj = snak_is_valued ? 1 : 0;
		Keyval[] rv = new Keyval[3 + snak_is_valued_adj];
		if (snak_is_valued)	// NOTE: novalue must not return slot (no datavalue node in json); PAGE:ru.w:Лимонов,_Эдуард_Вениаминович; DATE:2015-02-16; ALSO: sv.w:Joseph_Jaquet; DATE:2015-07-31
			rv[0] = Keyval_.new_("datavalue", Srl_claims_prop_itm_core_val(itm));
		rv[0 + snak_is_valued_adj] = Keyval_.new_("property", pid);
		rv[1 + snak_is_valued_adj] = Keyval_.new_("snaktype", Wdata_dict_snak_tid.Xto_str(itm.Snak_tid()));
		rv[2 + snak_is_valued_adj] = Keyval_.new_("datatype", Wdata_dict_val_tid.To_str__srl(itm.Val_tid()));	// NOTE: datatype needed for Modules; PAGE:eo.w:WikidataKoord; DATE:2015-11-08
		return rv;
	}
	private static final    Scrib_lib_wikibase_srl_visitor visitor = new Scrib_lib_wikibase_srl_visitor();
	private static Keyval[] Srl_claims_prop_itm_core_val(Wdata_claim_itm_core itm) {
		switch (itm.Snak_tid()) {
			case Wdata_dict_snak_tid.Tid_somevalue:		return Datavalue_somevalue;
			case Wdata_dict_snak_tid.Tid_novalue:		return Datavalue_novalue;	// TODO_OLD: throw exc
			default:
				itm.Welcome(visitor);
				return visitor.Rv();
			}
	}
	public static final String Key_type = "type", Key_value = "value";
	private static final    Keyval[] Datavalue_somevalue = Keyval_.Ary_empty;	// changed to not return value-node; PAGE:it.s:Autore:Anonimo DATE:2015-12-06 // new Keyval[] {Keyval_.new_(Key_type, ""), Keyval_.new_(Key_value, "")};	// NOTE: must return ""; null fails; EX:w:Joseph-François_Malgaigne; DATE:2014-04-07
	private static final    Keyval[] Datavalue_novalue = Keyval_.Ary_empty;
}
