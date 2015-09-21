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
import gplx.core.primitives.*;
import gplx.langs.jsons.*; import gplx.xowa.xtns.wdatas.core.*;
class Wdata_claims_parser_v2 {
	public void Make_claim_itms(byte[] qid, List_adp claim_itms_list, byte[] src, Json_kv claim_grp) {
		Json_ary claim_itms_ary = Json_ary.cast_or_null(claim_grp.Val());
		int claim_itms_len = claim_itms_ary.Len();
		int pid = Parse_pid(claim_grp.Key().Data_bry());
		for (int i = 0; i < claim_itms_len; ++i) {
			Json_nde claim_itm_nde = Json_nde.cast(claim_itms_ary.Get_at(i));
			Wdata_claim_itm_base itm = Parse_claim_itm(qid, claim_itm_nde, pid);
			if (itm != null)	// HACK: itm can be null if value is "somevalue"; DATE:2014-09-20
				claim_itms_list.Add(itm);
		}
	}
	private Wdata_claim_itm_core Parse_claim_itm(byte[] qid, Json_nde nde, int pid) {
		int len = nde.Len();
		Hash_adp_bry dict = Wdata_dict_claim.Dict;
		byte rank_tid = Wdata_dict_rank.Tid_unknown;
		Wdata_claim_itm_core claim_itm = null; Wdata_claim_grp_list qualifiers = null; int[] qualifiers_order = null; Wdata_references_grp[] snaks_grp = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
			switch (tid) {
				case Wdata_dict_claim.Tid_mainsnak:			claim_itm = Parse_mainsnak(qid, Json_nde.cast(sub.Val()), pid); break;
				case Wdata_dict_claim.Tid_rank:				rank_tid = Wdata_dict_rank.Xto_tid(sub.Val().Data_bry()); break;
				case Wdata_dict_claim.Tid_references:		snaks_grp = Parse_references(qid, Json_ary.cast_or_null(sub.Val())); break;
				case Wdata_dict_claim.Tid_qualifiers:		qualifiers = Parse_qualifiers(qid, Json_nde.cast(sub.Val())); break;
				case Wdata_dict_claim.Tid_qualifiers_order:	qualifiers_order = Parse_pid_order(Json_ary.cast_or_null(sub.Val())); break;
				case Wdata_dict_claim.Tid_type:				break;		// ignore: "statement"
				case Wdata_dict_claim.Tid_id:				break;		// ignore: "Q2$F909BD1C-D34D-423F-9ED2-3493663321AF"
			}
		}
		if (claim_itm != null) {
			claim_itm.Rank_tid_(rank_tid);
			if (qualifiers != null)			claim_itm.Qualifiers_(qualifiers);
			if (qualifiers_order != null)	claim_itm.Qualifiers_order_(qualifiers_order);
			if (snaks_grp != null)			claim_itm.References_(snaks_grp);
		}
		return claim_itm;
	}
	public Wdata_references_grp[] Parse_references(byte[] qid, Json_ary owner) {
		int len = owner.Len();
		Wdata_references_grp[] rv = new Wdata_references_grp[len];
		for (int i = 0; i < len; ++i) {
			Json_nde grp_nde = Json_nde.cast(owner.Get_at(i));
			rv[i] = Parse_references_grp(qid, grp_nde);
		}
		return rv;
	}
	private Wdata_references_grp Parse_references_grp(byte[] qid, Json_nde owner) {
		int len = owner.Len();
		Hash_adp_bry dict = Wdata_dict_reference.Dict;
		Wdata_claim_grp_list snaks = null; int[] snaks_order = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(owner.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
			switch (tid) {
				case Wdata_dict_reference.Tid_hash:	break;	// ignore: "b923b0d68beb300866b87ead39f61e63ec30d8af"
				case Wdata_dict_reference.Tid_snaks:			snaks = Parse_qualifiers(qid, Json_nde.cast(sub.Val())); break;
				case Wdata_dict_reference.Tid_snaks_order:		snaks_order = Parse_pid_order(Json_ary.cast_or_null(sub.Val())); break;
			}
		}
		return new Wdata_references_grp(snaks, snaks_order);
	}
	public Wdata_claim_grp_list Parse_qualifiers(byte[] qid, Json_nde qualifiers_nde) {
		Wdata_claim_grp_list rv = new Wdata_claim_grp_list();
		if (qualifiers_nde == null) return rv;	// NOTE:sometimes references can have 0 snaks; return back an empty Wdata_claim_grp_list, not null; PAGE:Птичкин,_Евгений_Николаевич; DATE:2015-02-16
		int len = qualifiers_nde.Len();
		for (int i = 0; i < len; ++i) {
			Json_kv qualifier_kv = Json_kv.cast(qualifiers_nde.Get_at(i));
			int pid = Parse_pid(qualifier_kv.Key().Data_bry());
			Wdata_claim_grp claims_grp = Parse_props_grp(qid, pid, Json_ary.cast_or_null(qualifier_kv.Val()));
			rv.Add(claims_grp);
		}
		return rv;
	}
	public int[] Parse_pid_order(Json_ary ary) {
		int len = ary.Len();
		int[] rv = new int[len];
		for (int i = 0; i < len; ++i) {
			Json_itm pid_itm = ary.Get_at(i);
			rv[i] = Parse_pid(pid_itm.Data_bry());				
		}
		return rv;
	}
	private Wdata_claim_grp Parse_props_grp(byte[] qid, int pid, Json_ary props_ary) {
		List_adp list = List_adp_.new_();
		int len = props_ary.Len();
		for (int i = 0; i < len; ++i) {
			Json_nde qualifier_nde = Json_nde.cast(props_ary.Get_at(i));
			Wdata_claim_itm_core qualifier_itm = Parse_mainsnak(qid, qualifier_nde, pid);
			list.Add(qualifier_itm);
		}
		return new Wdata_claim_grp(Int_obj_ref.new_(pid), (Wdata_claim_itm_core[])list.To_ary_and_clear(Wdata_claim_itm_core.class));
	}
	public Wdata_claim_itm_core Parse_mainsnak(byte[] qid, Json_nde nde, int pid) {
		int len = nde.Len();
		Hash_adp_bry dict = Wdata_dict_mainsnak.Dict;
		byte snak_tid = Byte_.Max_value_127;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
			switch (tid) {
				case Wdata_dict_mainsnak.Tid_snaktype:		snak_tid = Wdata_dict_snak_tid.Xto_tid(sub.Val().Data_bry()); break;
				case Wdata_dict_mainsnak.Tid_datavalue:		return Parse_datavalue(qid, pid, snak_tid, Json_nde.cast(sub.Val()));
				case Wdata_dict_mainsnak.Tid_datatype:		break;		// ignore: has values like "wikibase-property"; EX: www.wikidata.org/wiki/Property:P397; DATE:2015-06-12
				case Wdata_dict_mainsnak.Tid_property:		break;		// ignore: pid already available above
				case Wdata_dict_mainsnak.Tid_hash:			break;		// ignore: "84487fc3f93b4f74ab1cc5a47d78f596f0b49390"
			}
		}
		return new Wdata_claim_itm_system(pid, Wdata_dict_val_tid.Tid_unknown, snak_tid); // NOTE: mainsnak can be null, especially for qualifiers; PAGE:Q2!P576; DATE:2014-09-20
	}
	public Wdata_claim_itm_core Parse_datavalue(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		int len = nde.Len();
		Hash_adp_bry dict = Wdata_dict_datavalue.Dict;
		Json_itm value_itm = null; byte value_tid = Wdata_dict_val_tid.Tid_unknown;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
			switch (tid) {
				case Wdata_dict_datavalue.Tid_type:			value_tid = Wdata_dict_val_tid.Xto_tid(sub.Val().Data_bry()); break;
				case Wdata_dict_datavalue.Tid_value:		value_itm = sub.Val(); break;
				case Wdata_dict_datavalue.Tid_error:		break;	// ignore: "Can only construct GlobeCoordinateValue with a String globe parameter"
			}
		}
		switch (value_tid) {
			case Wdata_dict_val_tid.Tid_string:				return new Wdata_claim_itm_str(pid, snak_tid, value_itm.Data_bry());
			case Wdata_dict_val_tid.Tid_entity:				return Parse_datavalue_entity				(qid, pid, snak_tid, Json_nde.cast(value_itm));
			case Wdata_dict_val_tid.Tid_time:				return Parse_datavalue_time					(qid, pid, snak_tid, Json_nde.cast(value_itm));
			case Wdata_dict_val_tid.Tid_quantity:			return Parse_datavalue_quantity				(qid, pid, snak_tid, Json_nde.cast(value_itm));
			case Wdata_dict_val_tid.Tid_globecoordinate:	return Parse_datavalue_globecoordinate		(qid, pid, snak_tid, Json_nde.cast(value_itm));
			case Wdata_dict_val_tid.Tid_monolingualtext:	return Parse_datavalue_monolingualtext		(qid, pid, snak_tid, Json_nde.cast(value_itm));
		}
		throw Err_.new_wo_type("unknown val_tid", "val_tid", value_tid);
	}
	private Wdata_claim_itm_entity Parse_datavalue_entity(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_entity.Dict;
		int len = nde.Len();
		byte entity_tid = Byte_.Max_value_127;
		byte[] entity_id_bry = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
			switch (tid) {
				case Wdata_dict_value_entity.Tid_entity_type:		entity_tid = Wdata_dict_value_entity_tid.Xto_tid(sub.Val().Data_bry()); break;
				case Wdata_dict_value_entity.Tid_numeric_id:		entity_id_bry = sub.Val().Data_bry(); break;
			}
		}
		if (entity_id_bry == null) throw Err_.new_wo_type("pid is invalid entity", "pid", pid);
		return new Wdata_claim_itm_entity(pid, snak_tid, entity_tid, entity_id_bry);
	}
	private Wdata_claim_itm_monolingualtext Parse_datavalue_monolingualtext(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_monolingualtext.Dict;
		int len = nde.Len();
		byte[] lang = null, text = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wdata_dict_value_monolingualtext.Tid_text:			text = sub_val_bry; break;
				case Wdata_dict_value_monolingualtext.Tid_language:		lang = sub_val_bry; break;
			}
		}
		if (lang == null || text == null) throw Err_.new_wo_type("pid is invalid monolingualtext", "pid", pid);
		return new Wdata_claim_itm_monolingualtext(pid, snak_tid, lang, text);
	}
	private Wdata_claim_itm_globecoordinate Parse_datavalue_globecoordinate(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_globecoordinate.Dict;
		int len = nde.Len();
		byte[] lat = null, lng = null, alt = null, prc = null, glb = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wdata_dict_value_globecoordinate.Tid_latitude:			lat = sub_val_bry; break;
				case Wdata_dict_value_globecoordinate.Tid_longitude:		lng = sub_val_bry; break;
				case Wdata_dict_value_globecoordinate.Tid_altitude:			alt = sub_val_bry; break;
				case Wdata_dict_value_globecoordinate.Tid_precision:		prc = sub_val_bry; break;
				case Wdata_dict_value_globecoordinate.Tid_globe:			glb = sub_val_bry; break;
			}
		}
		if (lat == null || lng == null) throw Err_.new_wo_type("pid is invalid globecoordinate", "pid", pid);
		return new Wdata_claim_itm_globecoordinate(pid, snak_tid, lat, lng, alt, prc, glb);
	}
	private Wdata_claim_itm_quantity Parse_datavalue_quantity(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_quantity.Dict;
		int len = nde.Len();
		byte[] amount = null, unit = null, ubound = null, lbound = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wdata_dict_value_quantity.Tid_amount:			amount = sub_val_bry; break;
				case Wdata_dict_value_quantity.Tid_unit:			unit = sub_val_bry; break;
				case Wdata_dict_value_quantity.Tid_upperbound:		ubound = sub_val_bry; break;
				case Wdata_dict_value_quantity.Tid_lowerbound:		lbound = sub_val_bry; break;
			}
		}
		if (amount == null) throw Err_.new_wo_type("pid is invalid quantity", "pid", pid);
		return new Wdata_claim_itm_quantity(pid, snak_tid, amount, unit, ubound, lbound);
	}
	private Wdata_claim_itm_time Parse_datavalue_time(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		Hash_adp_bry dict = Wdata_dict_value_time.Dict;
		int len = nde.Len();
		byte[] time = null, timezone = null, before = null, after = null, precision = null, calendarmodel = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wdata_dict_utl.Get_tid_or_invalid(qid, dict, sub.Key().Data_bry()); if (tid == Wdata_dict_utl.Tid_invalid) continue;
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
		if (time == null) throw Err_.new_wo_type("pid is invalid time", "pid", pid);
		return new Wdata_claim_itm_time(pid, snak_tid, time, timezone, before, after, precision, calendarmodel);
	}
	private static int Parse_pid(byte[] pid_bry) {
		int rv = Bry_.To_int_or(pid_bry, 1, pid_bry.length, -1); if (rv == -1) throw Err_.new_wo_type("invalid pid", "pid", String_.new_u8(pid_bry));
		return rv;
	}
}
