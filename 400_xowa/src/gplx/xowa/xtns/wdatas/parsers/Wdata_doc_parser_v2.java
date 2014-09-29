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
import gplx.json.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.core.btries.*;
public class Wdata_doc_parser_v2 implements Wdata_doc_parser {
	private Wdata_claims_parser_v2 claims_parser = new Wdata_claims_parser_v2();
	public byte[] Parse_qid(Json_doc doc) {
		try {
			Json_itm itm = doc.Find_nde(Bry_id);
			return Bry_.Lower_1st(itm.Data_bry());	// standardize on "q" instead of "Q" for compatibility with v1
		}	catch (Exception e) {throw Err_.err_(e, "failed to parse qid; src={0}", String_.new_utf8_(doc.Src()));}
	}
	public OrderedHash Parse_sitelinks(byte[] qid, Json_doc doc) {
		try {
			Json_itm_nde list_nde = Json_itm_nde.cast_(doc.Get_grp(Bry_sitelinks)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			OrderedHash rv = OrderedHash_.new_bry_();
			int list_len = list_nde.Subs_len();
			Hash_adp_bry dict = Wdata_dict_sitelink.Dict;
			for (int i = 0; i < list_len; ++i) {
				Json_itm_kv data_kv		= Json_itm_kv.cast_(list_nde.Subs_get_at(i));
				Json_itm_nde data_nde	= Json_itm_nde.cast_(data_kv.Val());
				int data_nde_len		= data_nde.Subs_len();
				Json_itm_kv site_kv = null, name_kv = null; Json_itm_ary badges_ary = null;
				for (int j = 0; j < data_nde_len; ++j) {
					Json_itm_kv sub = Json_itm_kv.cast_(data_nde.Subs_get_at(j));
					byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
					switch (tid) {
						case Wdata_dict_sitelink.Tid_site:			site_kv	= Json_itm_kv.cast_(sub); break;
						case Wdata_dict_sitelink.Tid_title:			name_kv	= Json_itm_kv.cast_(sub); break;
						case Wdata_dict_sitelink.Tid_badges:		badges_ary = Json_itm_ary.cast_(Json_itm_kv.cast_(sub).Val()); break;
					}
				}
				byte[] site_bry			= site_kv.Val().Data_bry();
				Wdata_sitelink_itm itm	= new Wdata_sitelink_itm(site_bry, name_kv.Val().Data_bry(), badges_ary.Xto_bry_ary());
				rv.Add(site_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.err_(e, "failed to parse sitelinks; qid={0}", String_.new_utf8_(qid));}
	}
	public OrderedHash Parse_langvals(byte[] qid, Json_doc doc, boolean label_or_description) {
		try {
			byte[] langval_key = label_or_description ? Bry_labels : Bry_descriptions;
			Json_itm_nde list_nde = Json_itm_nde.cast_(doc.Get_grp(langval_key)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			OrderedHash rv = OrderedHash_.new_bry_();
			int list_len = list_nde.Subs_len();
			Hash_adp_bry dict = Wdata_dict_langtext.Dict;
			for (int i = 0; i < list_len; ++i) {
				Json_itm_kv data_kv		= Json_itm_kv.cast_(list_nde.Subs_get_at(i));
				Json_itm_nde data_nde	= Json_itm_nde.cast_(data_kv.Val());
				Json_itm_kv text_kv = null;
				int data_nde_len = data_nde.Subs_len();
				for (int j = 0; j < data_nde_len; ++j) {
					Json_itm_kv sub = Json_itm_kv.cast_(data_nde.Subs_get_at(j));
					byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
					switch (tid) {
						case Wdata_dict_langtext.Tid_language:		break;
						case Wdata_dict_langtext.Tid_value:			text_kv	= Json_itm_kv.cast_(sub); break;
					}
				}
				byte[] lang_bry			= data_kv.Key().Data_bry();
				Wdata_langtext_itm itm = new Wdata_langtext_itm(lang_bry, text_kv.Val().Data_bry());
				rv.Add(lang_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.err_(e, "failed to parse langvals; qid={0} langval_tid={1}", String_.new_utf8_(qid), label_or_description);}
	}
	public OrderedHash Parse_aliases(byte[] qid, Json_doc doc) {
		try {
			Json_itm_nde list_nde = Json_itm_nde.cast_(doc.Get_grp(Bry_aliases)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			OrderedHash rv = OrderedHash_.new_bry_();
			int list_len = list_nde.Subs_len();
			Hash_adp_bry dict = Wdata_dict_langtext.Dict;
			for (int i = 0; i < list_len; ++i) {
				Json_itm_kv data_kv		= Json_itm_kv.cast_(list_nde.Subs_get_at(i));
				Json_itm_ary vals_ary	= Json_itm_ary.cast_(data_kv.Val());
				int vals_len = vals_ary.Subs_len();
				byte[][] vals = new byte[vals_len][];
				for (int j = 0; j < vals_len; ++j) {
					Json_itm_nde lang_nde = Json_itm_nde.cast_(vals_ary.Subs_get_at(j));
					int k_len = lang_nde.Subs_len();
					for (int k = 0; k < k_len; ++k) {
						Json_itm_kv sub = Json_itm_kv.cast_(lang_nde.Subs_get_at(k));
						byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
						switch (tid) {
							case Wdata_dict_langtext.Tid_language:		break;
							case Wdata_dict_langtext.Tid_value:			vals[j] = sub.Val().Data_bry(); break;
						}
					}
				}
				byte[] lang_bry			= data_kv.Key().Data_bry();
				Wdata_alias_itm itm		= new Wdata_alias_itm(lang_bry, vals);
				rv.Add(lang_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.err_(e, "failed to parse sitelinks; qid={0}", String_.new_utf8_(qid));}
	}
	public OrderedHash Parse_claims(byte[] qid, Json_doc doc) {
		try {
			Json_itm_nde list_nde = Json_itm_nde.cast_(doc.Get_grp(Bry_claims)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_generic;
			ListAdp temp_list = ListAdp_.new_();
			byte[] src = doc.Src();
			int len = list_nde.Subs_len();
			for (int i = 0; i < len; i++) {
				Json_itm_kv claim_nde			= Json_itm_kv.cast_(list_nde.Subs_get_at(i));
				claims_parser.Make_claim_itms(qid, temp_list, src, claim_nde);
			}
			return Wdata_doc_parser_v1.Claims_list_to_hash(temp_list);
		} catch (Exception e) {throw Err_.err_(e, "failed to parse claims; qid={0}", String_.new_utf8_(doc.Src()));}
	}
	public Wdata_claim_itm_base Parse_claims_data(byte[] qid, int pid, byte snak_tid, Json_itm_nde nde) {return claims_parser.Parse_datavalue(qid, pid, snak_tid, nde);}
	public Wdata_claim_grp_list Parse_qualifiers(byte[] qid, Json_itm_nde nde) {return claims_parser.Parse_qualifiers(qid, nde);}
	public Wdata_references_grp[] Parse_references(byte[] qid, Json_itm_ary owner) {return claims_parser.Parse_references(qid, owner);}
	public int[] Parse_pid_order(byte[] qid, Json_itm_ary ary) {return claims_parser.Parse_pid_order(ary);}
	public static final String
	  Str_id									= "id"
	, Str_sitelinks								= "sitelinks"
	, Str_labels								= "labels"
	, Str_descriptions							= "descriptions"
	, Str_aliases								= "aliases"
	, Str_claims								= "claims"
	, Str_type									= "type"
	;
	public static final byte[] 
	  Bry_id									= Bry_.new_ascii_(Str_id)
	, Bry_sitelinks								= Bry_.new_ascii_(Str_sitelinks)
	, Bry_labels								= Bry_.new_ascii_(Str_labels)
	, Bry_descriptions							= Bry_.new_ascii_(Str_descriptions)
	, Bry_aliases								= Bry_.new_ascii_(Str_aliases)
	, Bry_claims								= Bry_.new_ascii_(Str_claims)
	, Bry_type									= Bry_.new_ascii_(Str_type)
	;
}
