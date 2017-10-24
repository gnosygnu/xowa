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
import gplx.langs.jsons.*; import gplx.core.btries.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Wdata_doc_parser_v2 implements Wdata_doc_parser {
	private Wdata_claims_parser_v2 claims_parser = new Wdata_claims_parser_v2();
	public byte[] Parse_qid(Json_doc doc) {
		try {
			Json_itm itm = doc.Find_nde(Bry_id);
			return Bry_.Lcase__1st(itm.Data_bry());	// standardize on "q" instead of "Q" for compatibility with v1
		}	catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse qid", "src", String_.new_u8(doc.Src()));}
	}
	public Ordered_hash Parse_sitelinks(byte[] qid, Json_doc doc) {
		try {
			Json_nde list_nde = Json_nde.cast(doc.Get_grp(Bry_sitelinks)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			Ordered_hash rv = Ordered_hash_.New_bry();
			int list_len = list_nde.Len();
			for (int i = 0; i < list_len; ++i) {
				Json_kv data_kv		= Json_kv.cast(list_nde.Get_at(i));
				Json_nde data_nde	= Json_nde.cast(data_kv.Val());
				int data_nde_len		= data_nde.Len();
				Json_kv site_kv = null, name_kv = null; Json_ary badges_ary = null;
				for (int j = 0; j < data_nde_len; ++j) {
					Json_kv sub = Json_kv.cast(data_nde.Get_at(j));
					byte tid = Wdata_dict_sitelink.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
					switch (tid) {
						case Wdata_dict_sitelink.Tid__site:			site_kv	= Json_kv.cast(sub); break;
						case Wdata_dict_sitelink.Tid__title:		name_kv	= Json_kv.cast(sub); break;
						case Wdata_dict_sitelink.Tid__badges:		badges_ary = Json_ary.cast_or_null(Json_kv.cast(sub).Val()); break;
					}
				}
				byte[] site_bry			= site_kv.Val().Data_bry();
				Wdata_sitelink_itm itm	= new Wdata_sitelink_itm(site_bry, name_kv.Val().Data_bry(), badges_ary.Xto_bry_ary());
				rv.Add(site_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse sitelinks", "qid", String_.new_u8(qid));}
	}
	public Ordered_hash Parse_langvals(byte[] qid, Json_doc doc, boolean label_or_description) {
		try {
			byte[] langval_key = label_or_description ? Bry_labels : Bry_descriptions;
			Json_nde list_nde = Json_nde.cast(doc.Get_grp(langval_key)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			Ordered_hash rv = Ordered_hash_.New_bry();
			int list_len = list_nde.Len();
			for (int i = 0; i < list_len; ++i) {
				Json_kv data_kv		= Json_kv.cast(list_nde.Get_at(i));
				Json_nde data_nde	= Json_nde.cast(data_kv.Val());
				Json_kv text_kv = null;
				int data_nde_len = data_nde.Len();
				for (int j = 0; j < data_nde_len; ++j) {
					Json_kv sub = Json_kv.cast(data_nde.Get_at(j));
					byte tid = Wdata_dict_langtext.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
					switch (tid) {
						case Wdata_dict_langtext.Tid__language:		break;
						case Wdata_dict_langtext.Tid__value:		text_kv	= Json_kv.cast(sub); break;
					}
				}
				byte[] lang_bry			= data_kv.Key().Data_bry();
				Wdata_langtext_itm itm = new Wdata_langtext_itm(lang_bry, text_kv.Val().Data_bry());
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
				Json_ary vals_ary	= Json_ary.cast_or_null(data_kv.Val());
				int vals_len = vals_ary.Len();
				byte[][] vals = new byte[vals_len][];
				for (int j = 0; j < vals_len; ++j) {
					Json_nde lang_nde = Json_nde.cast(vals_ary.Get_at(j));
					int k_len = lang_nde.Len();
					for (int k = 0; k < k_len; ++k) {
						Json_kv sub = Json_kv.cast(lang_nde.Get_at(k));
						byte tid = Wdata_dict_langtext.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
						switch (tid) {
							case Wdata_dict_langtext.Tid__language:		break;
							case Wdata_dict_langtext.Tid__value:		vals[j] = sub.Val().Data_bry(); break;
						}
					}
				}
				byte[] lang_bry			= data_kv.Key().Data_bry();
				Wdata_alias_itm itm		= new Wdata_alias_itm(lang_bry, vals);
				rv.Add(lang_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse sitelinks", "qid", String_.new_u8(qid));}
	}
	public Ordered_hash Parse_claims(byte[] qid, Json_doc doc) {
		synchronized (this) {// TS; DATE:2016-07-06
 				try {
				Json_nde list_nde = Json_nde.cast(doc.Get_grp(Bry_claims)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_generic;
				List_adp temp_list = List_adp_.New();
				byte[] src = doc.Src();
				int len = list_nde.Len();
				for (int i = 0; i < len; i++) {
					Json_kv claim_nde			= Json_kv.cast(list_nde.Get_at(i));
					claims_parser.Make_claim_itms(qid, temp_list, src, claim_nde);
				}
				return Wdata_doc_parser_v1.Claims_list_to_hash(temp_list);
			} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse claims", "qid", String_.new_u8(doc.Src()));}
		}
	}
	public Wbase_claim_base Parse_claims_data(byte[] qid, int pid, byte snak_tid, Json_nde nde) {return claims_parser.Parse_datavalue(qid, pid, snak_tid, nde);}
	public Wbase_claim_grp_list Parse_qualifiers(byte[] qid, Json_nde nde) {return claims_parser.Parse_qualifiers(qid, nde);}
	public Wbase_references_grp[] Parse_references(byte[] qid, Json_ary owner) {return claims_parser.Parse_references(qid, owner);}
	public int[] Parse_pid_order(byte[] qid, Json_ary ary) {return claims_parser.Parse_pid_order(ary);}
	public static final String
	  Str_id									= "id"
	, Str_sitelinks								= "sitelinks"
	, Str_labels								= "labels"
	, Str_descriptions							= "descriptions"
	, Str_aliases								= "aliases"
	, Str_claims								= "claims"
	, Str_type									= "type"
	;
	public static final    byte[] 
	  Bry_id									= Bry_.new_a7(Str_id)
	, Bry_sitelinks								= Bry_.new_a7(Str_sitelinks)
	, Bry_labels								= Bry_.new_a7(Str_labels)
	, Bry_descriptions							= Bry_.new_a7(Str_descriptions)
	, Bry_aliases								= Bry_.new_a7(Str_aliases)
	, Bry_claims								= Bry_.new_a7(Str_claims)
	, Bry_type									= Bry_.new_a7(Str_type)
	;
}
