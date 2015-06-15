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
package gplx.xowa.xtns.wdatas.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.json.*; import gplx.xowa.xtns.wdatas.core.*;
abstract class Wdata_doc_parser_fxt_base {
	protected Wdata_doc_parser parser;
	public void Init() {
		if (parser == null) parser = Make_parser();
	}
	public abstract Wdata_doc_parser Make_parser();
	public Wdata_sitelink_itm Make_sitelink(String site, String name, String... badges) {return new Wdata_sitelink_itm(Bry_.new_u8(site), Bry_.new_u8(name), Bry_.Ary(badges));}
	public Wdata_langtext_itm Make_langval(String lang, String text) {return new Wdata_langtext_itm(Bry_.new_u8(lang), Bry_.new_u8(text));}
	public Wdata_alias_itm Make_alias(String lang, String... vals) {return new Wdata_alias_itm(Bry_.new_u8(lang), Bry_.Ary(vals));}
	public Wdata_claim_itm_core Make_claim_str(int pid, String val) {return new Wdata_claim_itm_str(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_u8(val));}
	public Wdata_claim_itm_core Make_claim_entity_qid(int pid, int entityId) {return new Wdata_claim_itm_entity(pid, Wdata_dict_snak_tid.Tid_value, Wdata_dict_value_entity_tid.Tid_item, Int_.Xto_bry(entityId));}
	public Wdata_claim_itm_core Make_claim_entity_pid(int pid, int entityId) {return new Wdata_claim_itm_entity(pid, Wdata_dict_snak_tid.Tid_value, Wdata_dict_value_entity_tid.Tid_property, Int_.Xto_bry(entityId));}
	public Wdata_claim_itm_core Make_claim_monolingualtext(int pid, String lang, String text) {return new Wdata_claim_itm_monolingualtext(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_u8(lang), Bry_.new_u8(text));}
	public Wdata_claim_itm_core Make_claim_globecoordinate(int pid, String lat, String lng, String prc) {return new Wdata_claim_itm_globecoordinate(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_u8(lat), Bry_.new_u8(lng), Bry_.new_a7("null"), Bry_.new_u8(prc), Bry_.new_a7("http://www.wikidata.org/entity/Q2"));}
	public Wdata_claim_itm_core Make_claim_quantity(int pid, int val, int unit, int ubound, int lbound) {return new Wdata_claim_itm_quantity(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_u8(Int_.Xto_str(val)), Bry_.new_u8(Int_.Xto_str(unit)), Bry_.new_u8(Int_.Xto_str(ubound)), Bry_.new_u8(Int_.Xto_str(lbound)));}
	public Wdata_claim_itm_core Make_claim_time(int pid, String val) {return new Wdata_claim_itm_time(pid, Wdata_dict_snak_tid.Tid_value, Wdata_dict_value_time.Xto_time(val), Wdata_dict_value_time.Val_timezone_bry, Wdata_dict_value_time.Val_before_bry, Wdata_dict_value_time.Val_after_bry, Wdata_dict_value_time.Val_precision_bry, Wdata_dict_value_time.Val_calendarmodel_bry);}
	public Wdata_claim_itm_core Make_claim_novalue(int pid) {return new Wdata_claim_itm_system(pid, Wdata_dict_val_tid.Tid_unknown, Wdata_dict_snak_tid.Tid_novalue);}
	public void Test_entity(String raw, String expd)		{Tfds.Eq(expd, String_.new_u8(parser.Parse_qid(Json_doc.new_apos_(raw))));}
	public void Test_sitelinks(String raw, Wdata_sitelink_itm... expd) {
		Ordered_hash actl_hash = parser.Parse_sitelinks(Q1_bry, Json_doc.new_apos_(raw));
		Tfds.Eq_ary_str((Wdata_sitelink_itm[])actl_hash.To_ary(Wdata_sitelink_itm.class), expd);
	}
	public void Test_labels(String raw, Wdata_langtext_itm... expd)		{Test_langvals(raw, Bool_.Y, expd);}
	public void Test_descriptions(String raw, Wdata_langtext_itm... expd)	{Test_langvals(raw, Bool_.N, expd);}
	private void Test_langvals(String raw, boolean labels_or_descriptions, Wdata_langtext_itm... expd) {
		Ordered_hash actl_hash = parser.Parse_langvals(Q1_bry, Json_doc.new_apos_(raw), labels_or_descriptions);
		Tfds.Eq_ary_str((Wdata_langtext_itm[])actl_hash.To_ary(Wdata_langtext_itm.class), expd);
	}
	public void Test_aliases(String raw, Wdata_alias_itm... expd) {
		Ordered_hash actl_hash = parser.Parse_aliases(Q1_bry, Json_doc.new_apos_(raw));
		Tfds.Eq_ary_str((Wdata_alias_itm[])actl_hash.To_ary(Wdata_alias_itm.class), expd);
	}
	public void Test_claims(String raw, Wdata_claim_itm_core... expd) {
		Ordered_hash actl_hash = parser.Parse_claims(Q1_bry, Json_doc.new_apos_(raw));
		List_adp actl_list = Wdata_claim_grp.Xto_list(actl_hash);
		Tfds.Eq_ary_str((Wdata_claim_itm_core[])actl_list.To_ary(Wdata_claim_itm_core.class), expd);
	}
	public void Test_claims_data(String raw, Wdata_claim_itm_core expd) {
		Json_doc jdoc = Json_doc.new_apos_(raw);
		Wdata_claim_itm_base actl = parser.Parse_claims_data(Q1_bry, 1, Wdata_dict_snak_tid.Tid_value, jdoc.Root());
		Tfds.Eq(expd.toString(), actl.toString());
	}
	public void Test_qualifiers(String raw, Wdata_claim_itm_base... expd_itms) {
		Json_doc jdoc = Json_doc.new_apos_(raw);
		Json_itm_nde qualifiers_nde = Json_itm_nde.cast_(Json_itm_kv.cast_(jdoc.Root().Subs_get_at(0)).Val());
		Wdata_claim_grp_list actl = parser.Parse_qualifiers(Q1_bry, qualifiers_nde);
		Tfds.Eq_ary_str(expd_itms, To_ary(actl));
	}
	public void Test_references(String raw, int[] expd_order, Wdata_claim_itm_base... expd_itms) {
		Json_doc jdoc = Json_doc.new_apos_(raw);
		Json_itm_ary owner = Json_itm_ary.cast_(Json_itm_kv.cast_(jdoc.Root().Subs_get_at(0)).Val());
		Wdata_references_grp[] actl = parser.Parse_references(Q1_bry, owner);
		Wdata_references_grp actl_grp = actl[0];
		Tfds.Eq_ary(expd_order, actl_grp.References_order());
		Tfds.Eq_ary_str(expd_itms, To_ary(actl_grp.References()));
	}
	public void Test_pid_order(String raw, int... expd) {
		Json_doc jdoc = Json_doc.new_apos_(raw);
		Json_itm_ary nde = Json_itm_ary.cast_(Json_itm_kv.cast_(jdoc.Root().Subs_get_at(0)).Val());
		int[] actl = parser.Parse_pid_order(Q1_bry, nde);
		Tfds.Eq_ary(expd, actl);
	}
	Wdata_claim_itm_base[] To_ary(Wdata_claim_grp_list list) {
		List_adp rv = List_adp_.new_();
		int list_len = list.Len();
		for (int i = 0; i < list_len; ++i) {
			Wdata_claim_grp grp = list.Get_at(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wdata_claim_itm_core itm = grp.Get_at(j);
				rv.Add(itm);
			}
		}
		return (Wdata_claim_itm_base[])rv.To_ary_and_clear(Wdata_claim_itm_base.class);
	}
	private static final byte[] Q1_bry = Bry_.new_a7("Q1");
}
class Wdata_doc_parser_v2_fxt extends Wdata_doc_parser_fxt_base {
	@Override public Wdata_doc_parser Make_parser() {return new Wdata_doc_parser_v2();}
}
