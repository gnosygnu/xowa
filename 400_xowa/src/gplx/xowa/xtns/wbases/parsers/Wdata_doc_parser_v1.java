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
package gplx.xowa.xtns.wbases.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.primitives.*; import gplx.langs.jsons.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Wdata_doc_parser_v1 implements Wdata_doc_parser {
	public Wdata_doc_parser_v1(Gfo_usr_dlg usr_dlg) {this.usr_dlg = usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public Wdata_doc_parser_v1() {}
	public byte[] Parse_qid(Json_doc doc) {
		try {
			Json_itm kv_val = doc.Find_nde(Bry_entity);
			switch (kv_val.Tid()) {
				case Json_itm_.Tid__str:	// "entity":"q1"
					return kv_val.Data_bry();
				case Json_itm_.Tid__ary:	// "entity":["item",1]
					Json_ary kv_val_as_ary = (Json_ary)kv_val;
					Json_itm entity_id = kv_val_as_ary.Get_at(1);
					return Bry_.Add(Byte_ascii.Ltr_q, entity_id.Data_bry());
				default:
					throw Err_.new_unhandled(kv_val.Tid());
			}
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse qid", "src", String_.new_u8(doc.Src()));}
	}
	public Ordered_hash Parse_sitelinks(byte[] qid, Json_doc doc) {
		try {
			Json_nde list_nde = Json_nde.cast(doc.Get_grp(Bry_links)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			Ordered_hash rv = Ordered_hash_.New_bry();
			int list_len = list_nde.Len();
			for (int i = 0; i < list_len; ++i) {
				Json_kv wiki_kv		= Json_kv.cast(list_nde.Get_at(i));
				byte[] site_bry			= wiki_kv.Key().Data_bry();
				byte[] title_bry = null; byte[][] badges_bry_ary = null;
				if (wiki_kv.Val().Tid() == Json_itm_.Tid__nde) {	// v1.2: "enwiki":{name:"Earth", badges:[]}
					Json_nde val_nde	= Json_nde.cast(wiki_kv.Val());
					Json_kv name_kv		= Json_kv.cast(val_nde.Get_at(0));
					title_bry = name_kv.Val().Data_bry();
					Json_kv badges_kv	= Json_kv.cast(val_nde.Get_at(1));
					if (badges_kv != null) {// TEST:some tests do not define a badges nde; ignore if null; DATE:2014-09-19
						Json_ary badges_ary	= Json_ary.cast_or_null(badges_kv.Val());
						badges_bry_ary = badges_ary.Xto_bry_ary();
					}
				}
				else {											// v1.1: "enwiki":"Earth"
					title_bry = wiki_kv.Val().Data_bry();
				}
				Wdata_sitelink_itm itm	= new Wdata_sitelink_itm(site_bry, title_bry, badges_bry_ary);
				rv.Add(site_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse sitelinks", "qid", String_.new_u8(qid));}
	}
	public Ordered_hash Parse_langvals(byte[] qid, Json_doc doc, boolean label_or_description) {
		try {
			byte[] langval_key = label_or_description ? Bry_label : Bry_description;
			Json_nde list_nde = Json_nde.cast(doc.Get_grp(langval_key)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			Ordered_hash rv = Ordered_hash_.New_bry();
			int list_len = list_nde.Len();
			for (int i = 0; i < list_len; ++i) {
				Json_kv data_kv		= Json_kv.cast(list_nde.Get_at(i));
				byte[] lang_bry			= data_kv.Key().Data_bry();
				Wdata_langtext_itm itm = new Wdata_langtext_itm(lang_bry, data_kv.Val().Data_bry());
				rv.Add(lang_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse langvals", "qid", String_.new_u8(qid), "langval_tid", label_or_description);}
	}
	public Ordered_hash Parse_aliases(byte[] qid, Json_doc doc) {
		try {
			Json_nde list_nde = Json_nde.cast(doc.Get_grp(Bry_aliases)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			Ordered_hash rv = Ordered_hash_.New_bry();
			int list_len = list_nde.Len();
			for (int i = 0; i < list_len; ++i) {
				Json_kv data_kv		= Json_kv.cast(list_nde.Get_at(i));
				byte[] lang_bry			= data_kv.Key().Data_bry();
				byte[][] vals_bry_ary	= null;
				Json_itm data_val		= data_kv.Val();
				switch (data_val.Tid()) {
					case Json_itm_.Tid__ary:		// EX: 'en':['en_val_1','en_val_2']
						Json_ary vals_ary	= Json_ary.cast_or_null(data_val);
						vals_bry_ary = vals_ary.Xto_bry_ary();
						break;
					case Json_itm_.Tid__nde:			// EX: 'en':{'0:en_val_1','1:en_val_2'}; PAGE:wd.q:621080 DATE:2014-09-21
						Json_nde vals_nde	= Json_nde.cast(data_val);
						int vals_len = vals_nde.Len();
						vals_bry_ary = new byte[vals_len][];
						for (int j = 0; j < vals_len; ++j) {
							Json_kv vals_sub_kv = Json_kv.cast(vals_nde.Get_at(j));
							vals_bry_ary[j] = vals_sub_kv.Val().Data_bry();
						}
						break;
					default: throw Err_.new_unhandled(data_val.Tid());
				}
				Wdata_alias_itm itm		= new Wdata_alias_itm(lang_bry, vals_bry_ary);
				rv.Add(lang_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse aliases", "qid", String_.new_u8(qid));}
	}
	public Ordered_hash Parse_claims(byte[] qid, Json_doc doc) {
		try {
			Json_ary list_nde = Json_ary.cast_or_null(doc.Get_grp(Bry_claims)); if (list_nde == null) return Empty_ordered_hash_generic;
			List_adp temp_list = List_adp_.New();
			byte[] src = doc.Src();
			int len = list_nde.Len();
			for (int i = 0; i < len; i++) {
				Json_nde claim_nde			= Json_nde.cast(list_nde.Get_at(i));
				Wbase_claim_base claim_itm	= Make_claim_itm(src, claim_nde);
				temp_list.Add(claim_itm);
			}
			return Claims_list_to_hash(temp_list);
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse claims", "qid", String_.new_u8(doc.Src()));}
	}
	public Wbase_claim_base Parse_claims_data(byte[] qid, int pid, byte snak_tid, Json_nde nde) {throw Err_.new_unimplemented();}
	public static Ordered_hash Claims_list_to_hash(List_adp full_list) {
		full_list.Sort();
		Ordered_hash rv = Ordered_hash_.New(); List_adp temp_itms = List_adp_.New();
		int prv_pid = -1;
		int len = full_list.Count();
		for (int i = 0; i < len; ++i) {
			Wbase_claim_base claim_itm = (Wbase_claim_base)full_list.Get_at(i);
			int cur_pid = claim_itm.Pid();
			if (prv_pid != cur_pid && prv_pid != -1)
				Claims_list_to_hash__add(rv, prv_pid, temp_itms);
			temp_itms.Add(claim_itm);
			prv_pid = cur_pid;
		}
		Claims_list_to_hash__add(rv, prv_pid, temp_itms);
		return rv;
	}
	private static void Claims_list_to_hash__add(Ordered_hash rv, int pid, List_adp temp_itms) {
		if (temp_itms.Count() == 0) return; // NOTE: will be empty when claims are empty; EX: "claims": []; PAGE:wd.p:585; DATE:2014-10-03
		Int_obj_ref claim_grp_key = Int_obj_ref.New(pid);
		Wbase_claim_grp claim_grp = new Wbase_claim_grp(claim_grp_key, (Wbase_claim_base[])temp_itms.To_ary_and_clear(Wbase_claim_base.class));
		rv.Add(claim_grp_key, claim_grp);
	}
	private Wbase_claim_base Make_claim_itm(byte[] src, Json_nde prop_nde) {
		int len = prop_nde.Len();	// should have 5 (m, q, g, rank, refs), but don't enforce (can rely on keys)
		Wbase_claim_base rv = null;
		for (int i = 0; i < len; i++) {
			Json_kv kv = Json_kv.cast(prop_nde.Get_at(i));
			Json_itm kv_key = kv.Key();
			Byte_obj_val bv = (Byte_obj_val)Prop_key_hash.Get_by_mid(src, kv_key.Src_bgn(), kv_key.Src_end());
			if (bv == null) {Warn("invalid prop node: ~{0}", String_.new_u8(src, kv_key.Src_bgn(), kv_key.Src_end())); return null;}
			switch (bv.Val()) {
				case Prop_tid_m:
					rv = New_prop_by_m(src, Json_ary.cast_or_null(kv.Val()));
					if (rv == null) return null;
					break;
				case Prop_tid_g:
					rv.Wguid_(kv.Data_bry());
					break;
				case Prop_tid_rank:
					rv.Rank_tid_((byte)Int_.cast(((Json_itm_int)kv.Val()).Data_as_int()));
					break;
				case Prop_tid_q:
					break;
				case Prop_tid_refs:
					break;
				default:				throw Err_.new_unhandled(bv.Val());
			}
		}
		return rv;
	}
	private Wbase_claim_base New_prop_by_m(byte[] src, Json_ary ary) {
		byte snak_tid = Wbase_claim_value_type_.Reg.Get_tid_or_fail(ary.Get_at(0).Data_bry());
		int pid = Json_itm_int.cast(ary.Get_at(1)).Data_as_int();
		switch (snak_tid) {
			case Wbase_claim_value_type_.Tid__novalue		: return Wbase_claim_value.New_novalue(pid);
			case Wbase_claim_value_type_.Tid__somevalue		: return Wbase_claim_value.New_somevalue(pid);
		}
		Json_itm val_tid_itm = ary.Get_at(2);
		byte val_tid = Wbase_claim_type_.Get_tid_or_unknown(src, val_tid_itm.Src_bgn(), val_tid_itm.Src_end());
		return Make_itm(pid, snak_tid, val_tid, ary);
	}
	private Wbase_claim_base Make_itm(int pid, byte snak_tid, byte val_tid, Json_ary ary) {
		switch (val_tid) {
			case Wbase_claim_type_.Tid__string:
				return new Wbase_claim_string(pid, snak_tid, ary.Get_at(3).Data_bry());
			case Wbase_claim_type_.Tid__entity: {
				Json_nde sub_nde = Json_nde.cast(ary.Get_at(3));
				Json_kv entity_kv = Json_kv.cast(sub_nde.Get_at(1));
				return new Wbase_claim_entity(pid, snak_tid, Wbase_claim_entity_type_.Tid__item, entity_kv.Val().Data_bry());
			}
			case Wbase_claim_type_.Tid__time: {
				Json_nde sub_nde = Json_nde.cast(ary.Get_at(3));
				return new Wbase_claim_time(pid, snak_tid, Get_val(sub_nde, 0), Get_val(sub_nde, 1), Get_val(sub_nde, 2), Get_val(sub_nde, 3), Get_val(sub_nde, 4), Get_val(sub_nde, 5));
			}
			case Wbase_claim_type_.Tid__globecoordinate: case Wbase_claim_type_.Tid__bad: {
				Json_nde sub_nde = Json_nde.cast(ary.Get_at(3));
				return new Wbase_claim_globecoordinate(pid, snak_tid, Get_val(sub_nde, 0), Get_val(sub_nde, 1), Get_val(sub_nde, 2), Get_val(sub_nde, 3), Get_val(sub_nde, 4));
			}
			case Wbase_claim_type_.Tid__quantity: {
				Json_nde sub_nde = Json_nde.cast(ary.Get_at(3));
				return new Wbase_claim_quantity(pid, snak_tid, Get_val(sub_nde, 0), Get_val(sub_nde, 1), Get_val(sub_nde, 2), Get_val(sub_nde, 3));
			}
			case Wbase_claim_type_.Tid__monolingualtext: {
				Json_nde sub_nde = Json_nde.cast(ary.Get_at(3));
				return new Wbase_claim_monolingualtext(pid, snak_tid, Get_val(sub_nde, 1), Get_val(sub_nde, 0));
			}
			default: {throw Err_.new_unhandled(val_tid);}
		}		
	}
	private static byte[] Get_val(Json_nde sub_nde, int i) {
		Json_kv kv = Json_kv.cast(sub_nde.Get_at(i));
		return kv.Val().Data_bry();
	}
	private void Warn(String fmt, Object... args) {usr_dlg.Warn_many("", "", fmt, args);}
	public static final    Ordered_hash Empty_ordered_hash_bry = Ordered_hash_.New_bry(), Empty_ordered_hash_generic = Ordered_hash_.New();
	private static final byte Prop_tid_m = 0, Prop_tid_q = 1, Prop_tid_g = 2, Prop_tid_rank = 3, Prop_tid_refs = 4;
	private static final    Hash_adp_bry Prop_key_hash = Hash_adp_bry.ci_a7()
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_m		, Prop_tid_m)
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_q		, Prop_tid_q)
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_g		, Prop_tid_g)
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_rank	, Prop_tid_rank)
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_refs	, Prop_tid_refs);
	Ordered_hash Bld_hash(Json_doc doc, byte[] key) {
		Json_nde nde = Json_nde.cast(doc.Get_grp(key)); if (nde == null) return Empty_ordered_hash_bry;
		Ordered_hash rv = Ordered_hash_.New_bry();
		int len = nde.Len();
		for (int i = 0; i < len; i++) {
			Json_kv kv = Json_kv.cast(nde.Get_at(i));
			byte[] kv_key = kv.Key().Data_bry();
			rv.Add(kv_key, kv);
		}
		return rv;
	}
	public Wbase_claim_grp_list Parse_qualifiers(byte[] qid, Json_nde nde)		{throw Err_.new_unimplemented();}
	public Wbase_references_grp[] Parse_references(byte[] qid, Json_ary owner)	{throw Err_.new_unimplemented();}
	public int[] Parse_pid_order(byte[] qid, Json_ary ary) {throw Err_.new_unimplemented();}
	public static final String
	  Str_entity								= "entity"
	, Str_id									= "id"
	, Str_links									= "links"
	, Str_label									= "label"
	, Str_aliases								= "aliases"
	, Str_claims								= "claims"
	, Str_description							= "description"
	, Str_name									= "name"
	;
	public static final    byte[]
	  Bry_entity								= Bry_.new_a7(Str_entity)
	, Bry_id									= Bry_.new_a7(Str_id)
	, Bry_links									= Bry_.new_a7(Str_links)
	, Bry_label									= Bry_.new_a7(Str_label)
	, Bry_aliases								= Bry_.new_a7(Str_aliases)
	, Bry_claims								= Bry_.new_a7(Str_claims)
	, Bry_description							= Bry_.new_a7(Str_description)
	, Bry_name									= Bry_.new_a7(Str_name)
	;
}
