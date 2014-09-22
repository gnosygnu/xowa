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
	private Wdata_claims_parser__v2 claims_parser = new Wdata_claims_parser__v2();
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
					byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
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
					byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
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
						byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
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
	public OrderedHash Parse_claims(Json_doc doc) {
		try {
			Json_itm_nde list_nde = Json_itm_nde.cast_(doc.Get_grp(Bry_claims)); if (list_nde == null) return Wdata_doc_parser_v1.Empty_ordered_hash_generic;
			ListAdp temp_list = ListAdp_.new_();
			byte[] src = doc.Src();
			int len = list_nde.Subs_len();
			for (int i = 0; i < len; i++) {
				Json_itm_kv claim_nde			= Json_itm_kv.cast_(list_nde.Subs_get_at(i));
				claims_parser.Make_claim_itms(temp_list, src, claim_nde);
			}
			return Wdata_doc_parser_v1.Claims_list_to_hash(temp_list);
		} catch (Exception e) {throw Err_.err_(e, "failed to parse claims; qid={0}", String_.new_utf8_(doc.Src()));}
	}
	public Wdata_claim_itm_base Parse_claims_data(int pid, byte snak_tid, Json_itm_nde nde) {return claims_parser.Parse_datavalue(pid, snak_tid, nde);}
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
class Wdata_claims_parser__v2 {
	public void Make_claim_itms(ListAdp claim_itms_list, byte[] src, Json_itm_kv claim_grp) {
		Json_itm_ary claim_itms_ary = Json_itm_ary.cast_(claim_grp.Val());
		int claim_itms_len = claim_itms_ary.Subs_len();
		byte[] pid_bry = claim_grp.Key().Data_bry();
		int pid = Bry_.Xto_int_or(pid_bry, 1, pid_bry.length, -1);
		for (int i = 0; i < claim_itms_len; ++i) {
			Json_itm_nde claim_itm_nde = Json_itm_nde.cast_(claim_itms_ary.Subs_get_at(i));
			Wdata_claim_itm_base itm = Parse_claim_itm(claim_itm_nde, pid);
			if (itm != null)	// HACK: itm can be null if value is "somevalue"; DATE:2014-09-20
				claim_itms_list.Add(itm);
		}
	}
	private Wdata_claim_itm_base Parse_claim_itm(Json_itm_nde nde, int pid) {
		int len = nde.Subs_len();
		Hash_adp_bry dict = Wdata_dict_claim.Dict;
		byte rank_tid = Wdata_dict_rank.Tid_unknown;
		Wdata_claim_itm_base claim_itm = null;
		for (int i = 0; i < len; ++i) {
			Json_itm_kv sub = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
			switch (tid) {
				case Wdata_dict_claim.Tid_mainsnak:			claim_itm = Parse_mainsnak(Json_itm_nde.cast_(sub.Val()), pid); break;
				case Wdata_dict_claim.Tid_rank:				rank_tid = Wdata_dict_rank.Xto_tid(sub.Val().Data_bry()); break;
				case Wdata_dict_claim.Tid_references:		break;
				case Wdata_dict_claim.Tid_qualifiers:		break;
				case Wdata_dict_claim.Tid_qualifiers_order:	break;
				case Wdata_dict_claim.Tid_type:				break;		// ignore: "statement"
				case Wdata_dict_claim.Tid_id:				break;		// ignore: "Q2$F909BD1C-D34D-423F-9ED2-3493663321AF"
			}
		}
		if (claim_itm != null) claim_itm.Rank_tid_(rank_tid);
		return claim_itm;
	}
	public Wdata_claim_itm_base Parse_mainsnak(Json_itm_nde nde, int pid) {
		int len = nde.Subs_len();
		Hash_adp_bry dict = Wdata_dict_mainsnak.Dict;
		byte snak_tid = Byte_.MaxValue_127;
		for (int i = 0; i < len; ++i) {
			Json_itm_kv sub = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
			switch (tid) {
				case Wdata_dict_mainsnak.Tid_snaktype:		snak_tid = Wdata_dict_snak_tid.Xto_tid(sub.Val().Data_bry()); break;
				case Wdata_dict_mainsnak.Tid_datavalue:		return Parse_datavalue(pid, snak_tid, Json_itm_nde.cast_(sub.Val()));
				case Wdata_dict_mainsnak.Tid_property:		break;		// ignore: pid already available above
				case Wdata_dict_mainsnak.Tid_hash:			break;		// ignore: "84487fc3f93b4f74ab1cc5a47d78f596f0b49390"
			}
		}
		return null;	// NOTE: mainsnak can be null; PAGE:Q2!P576; DATE:2014-09-20
	}
	public Wdata_claim_itm_base Parse_datavalue(int pid, byte snak_tid, Json_itm_nde nde) {
		int len = nde.Subs_len();
		Hash_adp_bry dict = Wdata_dict_datavalue.Dict;
		Json_itm value_itm = null; byte value_tid = Wdata_dict_val_tid.Tid_unknown;
		for (int i = 0; i < len; ++i) {
			Json_itm_kv sub = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
			switch (tid) {
				case Wdata_dict_datavalue.Tid_type:			value_tid = Wdata_dict_val_tid.Xto_tid(sub.Val().Data_bry()); break;
				case Wdata_dict_datavalue.Tid_value:		value_itm = sub.Val(); break;
			}
		}
		switch (value_tid) {
			case Wdata_dict_val_tid.Tid_string:				return new Wdata_claim_itm_str(pid, snak_tid, value_itm.Data_bry());
			case Wdata_dict_val_tid.Tid_entity:				return Parse_datavalue_entity				(pid, snak_tid, Json_itm_nde.cast_(value_itm));
			case Wdata_dict_val_tid.Tid_time:				return Parse_datavalue_time					(pid, snak_tid, Json_itm_nde.cast_(value_itm));
			case Wdata_dict_val_tid.Tid_quantity:			return Parse_datavalue_quantity				(pid, snak_tid, Json_itm_nde.cast_(value_itm));
			case Wdata_dict_val_tid.Tid_globecoordinate:	return Parse_datavalue_globecoordinate		(pid, snak_tid, Json_itm_nde.cast_(value_itm));
			case Wdata_dict_val_tid.Tid_monolingualtext:	return Parse_datavalue_monolingualtext		(pid, snak_tid, Json_itm_nde.cast_(value_itm));
		}
		throw Err_.new_("unknown val_tid; val_tid={0}", value_tid);
	}
	private Wdata_claim_itm_base Parse_datavalue_entity(int pid, byte snak_tid, Json_itm_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_entity.Dict;
		int len = nde.Subs_len();
		byte[] entity_id_bry = null;
		for (int i = 0; i < len; ++i) {
			Json_itm_kv sub = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
			switch (tid) {
				case Wdata_dict_value_entity.Tid_entity_type:		break;	// ignore: "item"
				case Wdata_dict_value_entity.Tid_numeric_id:		entity_id_bry = sub.Val().Data_bry(); break;
			}
		}
		if (entity_id_bry == null) throw Err_.new_("pid is invalid entity; pid={0}", pid);
		return new Wdata_claim_itm_entity(pid, snak_tid, entity_id_bry);
	}
	private Wdata_claim_itm_base Parse_datavalue_monolingualtext(int pid, byte snak_tid, Json_itm_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_monolingualtext.Dict;
		int len = nde.Subs_len();
		byte[] lang = null, text = null;
		for (int i = 0; i < len; ++i) {
			Json_itm_kv sub = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wdata_dict_value_monolingualtext.Tid_text:			text = sub_val_bry; break;
				case Wdata_dict_value_monolingualtext.Tid_language:		lang = sub_val_bry; break;
			}
		}
		if (lang == null || text == null) throw Err_.new_("pid is invalid monolingualtext; pid={0}", pid);
		return new Wdata_claim_itm_monolingualtext(pid, snak_tid, lang, text);
	}
	private Wdata_claim_itm_base Parse_datavalue_globecoordinate(int pid, byte snak_tid, Json_itm_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_globecoordinate.Dict;
		int len = nde.Subs_len();
		byte[] lat = null, lng = null, alt = null, prc = null, glb = null;
		for (int i = 0; i < len; ++i) {
			Json_itm_kv sub = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wdata_dict_value_globecoordinate.Tid_latitude:			lat = sub_val_bry; break;
				case Wdata_dict_value_globecoordinate.Tid_longitude:		lng = sub_val_bry; break;
				case Wdata_dict_value_globecoordinate.Tid_altitude:			alt = sub_val_bry; break;
				case Wdata_dict_value_globecoordinate.Tid_precision:		prc = sub_val_bry; break;
				case Wdata_dict_value_globecoordinate.Tid_globe:			glb = sub_val_bry; break;
			}
		}
		if (lat == null || lng == null) throw Err_.new_("pid is invalid globecoordinate; pid={0}", pid);
		return new Wdata_claim_itm_globecoordinate(pid, snak_tid, lat, lng, alt, prc, glb);
	}
	private Wdata_claim_itm_base Parse_datavalue_quantity(int pid, byte snak_tid, Json_itm_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_quantity.Dict;
		int len = nde.Subs_len();
		byte[] amount = null, unit = null, ubound = null, lbound = null;
		for (int i = 0; i < len; ++i) {
			Json_itm_kv sub = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wdata_dict_value_quantity.Tid_amount:			amount = sub_val_bry; break;
				case Wdata_dict_value_quantity.Tid_unit:			unit = sub_val_bry; break;
				case Wdata_dict_value_quantity.Tid_upperbound:		ubound = sub_val_bry; break;
				case Wdata_dict_value_quantity.Tid_lowerbound:		lbound = sub_val_bry; break;
			}
		}
		if (amount == null) throw Err_.new_("pid is invalid quantity; pid={0}", pid);
		return new Wdata_claim_itm_quantity(pid, snak_tid, amount, unit, ubound, lbound);
	}
	private Wdata_claim_itm_base Parse_datavalue_time(int pid, byte snak_tid, Json_itm_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_time.Dict;
		int len = nde.Subs_len();
		byte[] time = null, timezone = null, before = null, after = null, precision = null, calendarmodel = null;
		for (int i = 0; i < len; ++i) {
			Json_itm_kv sub = Json_itm_kv.cast_(nde.Subs_get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_fail(dict, sub.Key().Data_bry());
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wdata_dict_value_time.Tid_time:			time = sub_val_bry; break;
				case Wdata_dict_value_time.Tid_timezone:		timezone = sub_val_bry; break;
				case Wdata_dict_value_time.Tid_before:			before = sub_val_bry; break;
				case Wdata_dict_value_time.Tid_after:			after = sub_val_bry; break;
				case Wdata_dict_value_time.Tid_precision:		precision = sub_val_bry; break;
				case Wdata_dict_value_time.Tid_calendarmodel:	calendarmodel = sub_val_bry; break;
			}
		}
		if (time == null) throw Err_.new_("pid is invalid time; pid={0}", pid);
		return new Wdata_claim_itm_time(pid, snak_tid, time, timezone, before, after, precision, calendarmodel);
	}
}
