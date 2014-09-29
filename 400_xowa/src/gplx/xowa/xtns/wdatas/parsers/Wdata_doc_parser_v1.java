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
public class Wdata_doc_parser_v1 implements Wdata_doc_parser {
	public Wdata_doc_parser_v1(Gfo_usr_dlg usr_dlg) {this.usr_dlg = usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public Wdata_doc_parser_v1() {}
	public byte[] Parse_qid(Json_doc doc) {
		try {
			Json_itm kv_val = doc.Find_nde(Bry_entity);
			switch (kv_val.Tid()) {
				case Json_itm_.Tid_string:	// "entity":"q1"
					return kv_val.Data_bry();
				case Json_itm_.Tid_array:	// "entity":["item",1]
					Json_itm_ary kv_val_as_ary = (Json_itm_ary)kv_val;
					Json_itm entity_id = kv_val_as_ary.Subs_get_at(1);
					return Bry_.Add(Byte_ascii.Ltr_q, entity_id.Data_bry());
				default:
					throw Err_.unhandled(kv_val.Tid());
			}
		} catch (Exception e) {throw Err_.err_(e, "failed to parse qid; src={0}", String_.new_utf8_(doc.Src()));}
	}
	public OrderedHash Parse_sitelinks(byte[] qid, Json_doc doc) {
		try {
			Json_itm_nde list_nde = Json_itm_nde.cast_(doc.Get_grp(Bry_links)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			OrderedHash rv = OrderedHash_.new_bry_();
			int list_len = list_nde.Subs_len();
			for (int i = 0; i < list_len; ++i) {
				Json_itm_kv wiki_kv		= Json_itm_kv.cast_(list_nde.Subs_get_at(i));
				byte[] site_bry			= wiki_kv.Key().Data_bry();
				byte[] title_bry = null; byte[][] badges_bry_ary = null;
				if (wiki_kv.Val().Tid() == Json_itm_.Tid_nde) {	// v1.2: "enwiki":{name:"Earth", badges:[]}
					Json_itm_nde val_nde	= Json_itm_nde.cast_(wiki_kv.Val());
					Json_itm_kv name_kv		= Json_itm_kv.cast_(val_nde.Subs_get_at(0));
					title_bry = name_kv.Val().Data_bry();
					Json_itm_kv badges_kv	= Json_itm_kv.cast_(val_nde.Subs_get_at(1));
					if (badges_kv != null) {// TEST:some tests do not define a badges nde; ignore if null; DATE:2014-09-19
						Json_itm_ary badges_ary	= Json_itm_ary.cast_(badges_kv.Val());
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
		} catch (Exception e) {throw Err_.err_(e, "failed to parse sitelinks; qid={0}", String_.new_utf8_(qid));}
	}
	public OrderedHash Parse_langvals(byte[] qid, Json_doc doc, boolean label_or_description) {
		try {
			byte[] langval_key = label_or_description ? Bry_label : Bry_description;
			Json_itm_nde list_nde = Json_itm_nde.cast_(doc.Get_grp(langval_key)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_bry;
			OrderedHash rv = OrderedHash_.new_bry_();
			int list_len = list_nde.Subs_len();
			for (int i = 0; i < list_len; ++i) {
				Json_itm_kv data_kv		= Json_itm_kv.cast_(list_nde.Subs_get_at(i));
				byte[] lang_bry			= data_kv.Key().Data_bry();
				Wdata_langtext_itm itm = new Wdata_langtext_itm(lang_bry, data_kv.Val().Data_bry());
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
			for (int i = 0; i < list_len; ++i) {
				Json_itm_kv data_kv		= Json_itm_kv.cast_(list_nde.Subs_get_at(i));
				byte[] lang_bry			= data_kv.Key().Data_bry();
				byte[][] vals_bry_ary	= null;
				Json_itm data_val		= data_kv.Val();
				switch (data_val.Tid()) {
					case Json_itm_.Tid_array:		// EX: 'en':['en_val_1','en_val_2']
						Json_itm_ary vals_ary	= Json_itm_ary.cast_(data_val);
						vals_bry_ary = vals_ary.Xto_bry_ary();
						break;
					case Json_itm_.Tid_nde:			// EX: 'en':{'0:en_val_1','1:en_val_2'}; PAGE:wd.q:621080 DATE:2014-09-21
						Json_itm_nde vals_nde	= Json_itm_nde.cast_(data_val);
						int vals_len = vals_nde.Subs_len();
						vals_bry_ary = new byte[vals_len][];
						for (int j = 0; j < vals_len; ++j) {
							Json_itm_kv vals_sub_kv = Json_itm_kv.cast_(vals_nde.Subs_get_at(j));
							vals_bry_ary[j] = vals_sub_kv.Val().Data_bry();
						}
						break;
					default: throw Err_.unhandled(data_val.Tid());
				}
				Wdata_alias_itm itm		= new Wdata_alias_itm(lang_bry, vals_bry_ary);
				rv.Add(lang_bry, itm);
			}
			return rv;
		} catch (Exception e) {throw Err_.err_(e, "failed to parse aliases; qid={0}", String_.new_utf8_(qid));}
	}
	public OrderedHash Parse_claims(byte[] qid, Json_doc doc) {
		try {
			Json_itm_ary list_nde = Json_itm_ary.cast_(doc.Get_grp(Bry_claims)); if (list_nde == null) return Empty_ordered_hash_generic;
			ListAdp temp_list = ListAdp_.new_();
			byte[] src = doc.Src();
			int len = list_nde.Subs_len();
			for (int i = 0; i < len; i++) {
				Json_itm_nde claim_nde			= Json_itm_nde.cast_(list_nde.Subs_get_at(i));
				Wdata_claim_itm_core claim_itm	= Make_claim_itm(src, claim_nde);
				temp_list.Add(claim_itm);
			}
			return Claims_list_to_hash(temp_list);
		} catch (Exception e) {throw Err_.err_(e, "failed to parse claims; qid={0}", String_.new_utf8_(doc.Src()));}
	}
	public Wdata_claim_itm_base Parse_claims_data(byte[] qid, int pid, byte snak_tid, Json_itm_nde nde) {throw Err_.not_implemented_();}
	public static OrderedHash Claims_list_to_hash(ListAdp full_list) {
		full_list.Sort();
		OrderedHash rv = OrderedHash_.new_(); ListAdp temp_itms = ListAdp_.new_();
		int prv_pid = -1;
		int len = full_list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_claim_itm_core claim_itm = (Wdata_claim_itm_core)full_list.FetchAt(i);
			int cur_pid = claim_itm.Pid();
			if (prv_pid != cur_pid && prv_pid != -1)
				Claims_list_to_hash__add(rv, prv_pid, temp_itms);
			temp_itms.Add(claim_itm);
			prv_pid = cur_pid;
		}
		Claims_list_to_hash__add(rv, prv_pid, temp_itms);
		return rv;
	}
	private static void Claims_list_to_hash__add(OrderedHash rv, int pid, ListAdp temp_itms) {
		Int_obj_ref claim_grp_key = Int_obj_ref.new_(pid);
		Wdata_claim_grp claim_grp = new Wdata_claim_grp(claim_grp_key, (Wdata_claim_itm_core[])temp_itms.XtoAryAndClear(Wdata_claim_itm_core.class));
		rv.Add(claim_grp_key, claim_grp);
	}
	private Wdata_claim_itm_core Make_claim_itm(byte[] src, Json_itm_nde prop_nde) {
		int len = prop_nde.Subs_len();	// should have 5 (m, q, g, rank, refs), but don't enforce (can rely on keys)
		Wdata_claim_itm_core rv = null;
		for (int i = 0; i < len; i++) {
			Json_itm_kv kv = Json_itm_kv.cast_(prop_nde.Subs_get_at(i));
			Json_itm kv_key = kv.Key();
			Byte_obj_val bv = (Byte_obj_val)Prop_key_hash.Get_by_mid(src, kv_key.Src_bgn(), kv_key.Src_end());
			if (bv == null) {Warn("invalid prop node: ~{0}", String_.new_utf8_mid_safe_(src, kv_key.Src_bgn(), kv_key.Src_end())); return null;}
			switch (bv.Val()) {
				case Prop_tid_m:
					rv = New_prop_by_m(src, Json_itm_ary.cast_(kv.Val()));
					if (rv == null) return null;
					break;
				case Prop_tid_g:
					rv.Wguid_(kv.Data_bry());
					break;
				case Prop_tid_rank:
					rv.Rank_tid_((byte)Int_.cast_(((Json_itm_int)kv.Val()).Data_as_int()));
					break;
				case Prop_tid_q:
					break;
				case Prop_tid_refs:
					break;
				default:				throw Err_.unhandled(bv.Val());
			}
		}
		return rv;
	}
	private Wdata_claim_itm_core New_prop_by_m(byte[] src, Json_itm_ary ary) {
		byte snak_tid = Wdata_dict_snak_tid.Xto_tid(ary.Subs_get_at(0).Data_bry());
		int pid = Json_itm_int.cast_(ary.Subs_get_at(1)).Data_as_int();
		switch (snak_tid) {
			case Wdata_dict_snak_tid.Tid_novalue		: return Wdata_claim_itm_system.new_novalue(pid);
			case Wdata_dict_snak_tid.Tid_somevalue		: return Wdata_claim_itm_system.new_somevalue(pid);
		}
		Json_itm val_tid_itm = ary.Subs_get_at(2);
		byte val_tid = Wdata_dict_val_tid.Xto_tid(src, val_tid_itm.Src_bgn(), val_tid_itm.Src_end());
		return Make_itm(pid, snak_tid, val_tid, ary);
	}

	private Wdata_claim_itm_core Make_itm(int pid, byte snak_tid, byte val_tid, Json_itm_ary ary) {
		switch (val_tid) {
			case Wdata_dict_val_tid.Tid_string:
				return new Wdata_claim_itm_str(pid, snak_tid, ary.Subs_get_at(3).Data_bry());
			case Wdata_dict_val_tid.Tid_entity: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				Json_itm_kv entity_kv = Json_itm_kv.cast_(sub_nde.Subs_get_at(1));
				return new Wdata_claim_itm_entity(pid, snak_tid, entity_kv.Val().Data_bry());
			}
			case Wdata_dict_val_tid.Tid_time: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				return new Wdata_claim_itm_time(pid, snak_tid, Get_val(sub_nde, 0), Get_val(sub_nde, 1), Get_val(sub_nde, 2), Get_val(sub_nde, 3), Get_val(sub_nde, 4), Get_val(sub_nde, 5));
			}
			case Wdata_dict_val_tid.Tid_globecoordinate: case Wdata_dict_val_tid.Tid_bad: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				return new Wdata_claim_itm_globecoordinate(pid, snak_tid, Get_val(sub_nde, 0), Get_val(sub_nde, 1), Get_val(sub_nde, 2), Get_val(sub_nde, 3), Get_val(sub_nde, 4));
			}
			case Wdata_dict_val_tid.Tid_quantity: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				return new Wdata_claim_itm_quantity(pid, snak_tid, Get_val(sub_nde, 0), Get_val(sub_nde, 1), Get_val(sub_nde, 2), Get_val(sub_nde, 3));
			}
			case Wdata_dict_val_tid.Tid_monolingualtext: {
				Json_itm_nde sub_nde = Json_itm_nde.cast_(ary.Subs_get_at(3));
				return new Wdata_claim_itm_monolingualtext(pid, snak_tid, Get_val(sub_nde, 1), Get_val(sub_nde, 0));
			}
			default: {throw Err_.unhandled(val_tid);}
		}		
	}
	private static byte[] Get_val(Json_itm_nde sub_nde, int i) {
		Json_itm_kv kv = Json_itm_kv.cast_(sub_nde.Subs_get_at(i));
		return kv.Val().Data_bry();
	}
	private void Warn(String fmt, Object... args) {usr_dlg.Warn_many("", "", fmt, args);}
	public static final OrderedHash Empty_ordered_hash_bry = OrderedHash_.new_bry_(), Empty_ordered_hash_generic = OrderedHash_.new_();
	private static final byte Prop_tid_m = 0, Prop_tid_q = 1, Prop_tid_g = 2, Prop_tid_rank = 3, Prop_tid_refs = 4;
	private static final Hash_adp_bry Prop_key_hash = Hash_adp_bry.ci_ascii_()
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_m		, Prop_tid_m)
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_q		, Prop_tid_q)
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_g		, Prop_tid_g)
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_rank	, Prop_tid_rank)
	.Add_bry_byte(Wdata_dict_claim_v1.Bry_refs	, Prop_tid_refs);
	OrderedHash Bld_hash(Json_doc doc, byte[] key) {
		Json_itm_nde nde = Json_itm_nde.cast_(doc.Get_grp(key)); if (nde == null) return Empty_ordered_hash_bry;
		OrderedHash rv = OrderedHash_.new_bry_();
		int len = nde.Subs_len();
		for (int i = 0; i < len; i++) {
			Json_itm_kv kv = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte[] kv_key = kv.Key().Data_bry();
			rv.Add(kv_key, kv);
		}
		return rv;
	}
	public Wdata_claim_grp_list Parse_qualifiers(byte[] qid, Json_itm_nde nde)		{throw Err_.not_implemented_();}
	public Wdata_references_grp[] Parse_references(byte[] qid, Json_itm_ary owner)	{throw Err_.not_implemented_();}
	public int[] Parse_pid_order(byte[] qid, Json_itm_ary ary) {throw Err_.not_implemented_();}
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
	public static final byte[]
	  Bry_entity								= Bry_.new_ascii_(Str_entity)
	, Bry_id									= Bry_.new_ascii_(Str_id)
	, Bry_links									= Bry_.new_ascii_(Str_links)
	, Bry_label									= Bry_.new_ascii_(Str_label)
	, Bry_aliases								= Bry_.new_ascii_(Str_aliases)
	, Bry_claims								= Bry_.new_ascii_(Str_claims)
	, Bry_description							= Bry_.new_ascii_(Str_description)
	, Bry_name									= Bry_.new_ascii_(Str_name)
	;
}
