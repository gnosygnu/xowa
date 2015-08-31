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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.json.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;
public class Wdata_doc_wtr {
	private Json_doc_wtr wtr = new Json_doc_wtr();
	public byte[] Xto_bry(Wdata_doc wdoc) {
		wtr.Nde_bgn();
		wtr.Kv(Bool_.N, Wdata_doc_parser_v1.Bry_entity, wdoc.Qid());
		Xto_bry__list(Wdata_doc_parser_v1.Bry_label, wdoc.Label_list());
		Xto_bry__list(Wdata_doc_parser_v1.Bry_description, wdoc.Descr_list());
		Xto_bry__sitelinks(Wdata_doc_parser_v1.Bry_links, wdoc.Slink_list());
		Xto_bry__aliases(wdoc.Alias_list());
		Xto_bry__claims(wdoc.Qid(), wdoc.Claim_list());
		wtr.Nde_end();
		return wtr.Bld();
	}
	private void Xto_bry__list(byte[] key, Ordered_hash list) {
		int len = list.Count();
		if (len == 0) return;
		wtr.Key(true, key);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			KeyVal kv = (KeyVal)list.Get_at(i);
			wtr.Kv(i != 0, Bry_.new_u8(kv.Key()), Bry_.new_u8(kv.Val_to_str_or_empty()));
		}
		wtr.Nde_end();
		list.Clear();
	}
	private void Xto_bry__sitelinks(byte[] key, Ordered_hash list) {	// NOTE: changed to reflect new sitelinks structure; DATE:2014-02-04
		int len = list.Count();
		if (len == 0) return;
		wtr.Key(true, key);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			if (i != 0) wtr.Comma();
			KeyVal kv = (KeyVal)list.Get_at(i);
			wtr.Key(false, Bry_.new_u8(kv.Key()));												// write key;	EX: enwiki:
			wtr.Nde_bgn();																			// bgn nde;		EX: {
			wtr.Kv(false, Wdata_doc_parser_v1.Bry_name, Bry_.new_u8(kv.Val_to_str_or_empty()));	// write name;	EX:   name=Earth
			wtr.Nde_end();																			// end nde;		EX: }
		}
		wtr.Nde_end();
		list.Clear();
	}
	private void Xto_bry__aliases(Ordered_hash aliases) {
		int len = aliases.Count();
		if (len == 0) return;
		wtr.Key(true, Wdata_doc_parser_v1.Bry_aliases);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			Wdata_alias_itm alias = (Wdata_alias_itm)aliases.Get_at(i);
			wtr.Key(i != 0, alias.Lang());
			wtr.Ary_bgn();
			byte[][] aliases_ary = alias.Vals();
			int aliases_len = aliases_ary.length;
			for (int j = 0; j < aliases_len; j++) {
				byte[] aliases_itm = aliases_ary[j];
				wtr.Val(j != 0, aliases_itm);
			}
			wtr.Ary_end();
		}
		wtr.Nde_end();
		aliases.Clear();
	}
	private void Xto_bry__claims(byte[] qid, Ordered_hash props) {
		int len = props.Count();
		if (len == 0) return;
		wtr.Key(true, Wdata_doc_parser_v1.Bry_claims);
		wtr.Ary_bgn();
		for (int i = 0; i < len; i++) {
			if (i != 0) wtr.Comma();
			Wdata_claim_itm_core prop = (Wdata_claim_itm_core)props.Get_at(i);
			wtr.Nde_bgn();
			wtr.Key(false, Wdata_dict_claim_v1.Bry_m);
			wtr.Ary_bgn();
			wtr.Val(Bool_.N, Wdata_dict_snak_tid.Xto_bry(prop.Snak_tid()));
			wtr.Val(Bool_.Y, prop.Pid());
			if (prop.Snak_tid() == Wdata_dict_snak_tid.Tid_value) {
				switch (prop.Val_tid()) {
					case Wdata_dict_val_tid.Tid_string:
						Wdata_claim_itm_str claim_str = (Wdata_claim_itm_str)prop;
						wtr.Val(Bool_.Y, Wdata_dict_val_tid.Bry_string);
						wtr.Val(Bool_.Y, claim_str.Val_str());
						break;
					case Wdata_dict_val_tid.Tid_entity:
						Wdata_claim_itm_entity claim_entity = (Wdata_claim_itm_entity)prop;
						wtr.Val(Bool_.Y, Wdata_dict_val_tid.Bry_entity);
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv(Bool_.N, Wdata_dict_value_entity.Bry_entity_type			, claim_entity.Entity_tid_bry());
						wtr.Kv(Bool_.Y, Wdata_dict_value_entity.Bry_numeric_id			, claim_entity.Entity_id());
						wtr.Nde_end();
						break;
					case Wdata_dict_val_tid.Tid_time:
						Wdata_claim_itm_time claim_time = (Wdata_claim_itm_time)prop;
						wtr.Val(Bool_.Y, Wdata_dict_val_tid.Bry_time);
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv(Bool_.N, Wdata_dict_value_time.Bry_time					, claim_time.Time());
						wtr.Kv(Bool_.Y, Wdata_dict_value_time.Bry_precision				, Wdata_dict_value_time.Val_timezone_bry);
						wtr.Kv(Bool_.Y, Wdata_dict_value_time.Bry_before				, Wdata_dict_value_time.Val_before_bry);
						wtr.Kv(Bool_.Y, Wdata_dict_value_time.Bry_after					, Wdata_dict_value_time.Val_after_bry);
						wtr.Kv(Bool_.Y, Wdata_dict_value_time.Bry_timezone				, Wdata_dict_value_time.Val_timezone_bry);
						wtr.Kv(Bool_.Y, Wdata_dict_value_time.Bry_calendarmodel			, Wdata_dict_value_time.Val_calendarmodel_bry);
						wtr.Nde_end();
						break;
					case Wdata_dict_val_tid.Tid_globecoordinate: {
						Wdata_claim_itm_globecoordinate claim_globecoordinate = (Wdata_claim_itm_globecoordinate)prop;
						wtr.Val(Bool_.Y, Wdata_dict_val_tid.Bry_globecoordinate);
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv_double	(Bool_.N, Wdata_dict_value_globecoordinate.Bry_latitude		, Double_.parse(String_.new_a7(claim_globecoordinate.Lat())));
						wtr.Kv_double	(Bool_.Y, Wdata_dict_value_globecoordinate.Bry_longitude	, Double_.parse(String_.new_a7(claim_globecoordinate.Lng())));
						wtr.Kv			(Bool_.Y, Wdata_dict_value_globecoordinate.Bry_altitude		, null);
						wtr.Kv			(Bool_.Y, Wdata_dict_value_globecoordinate.Bry_globe		, Wdata_dict_value_globecoordinate.Val_globe_dflt_bry);
						wtr.Kv_double	(Bool_.Y, Wdata_dict_value_globecoordinate.Bry_precision	, .00001d);
						wtr.Nde_end();
						break;
					}
					case Wdata_dict_val_tid.Tid_quantity: {
						Wdata_claim_itm_quantity claim_quantity = (Wdata_claim_itm_quantity)prop;
						wtr.Val(Bool_.Y, Wdata_dict_val_tid.Bry_quantity);
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv			(Bool_.N, Wdata_dict_value_quantity.Bry_amount		, claim_quantity.Amount());		// +1,234
						wtr.Kv			(Bool_.Y, Wdata_dict_value_quantity.Bry_unit		, claim_quantity.Unit());		// 1
						wtr.Kv			(Bool_.Y, Wdata_dict_value_quantity.Bry_upperbound	, claim_quantity.Ubound());		// +1,235
						wtr.Kv			(Bool_.Y, Wdata_dict_value_quantity.Bry_lowerbound	, claim_quantity.Lbound());		// +1,233
						wtr.Nde_end();
						break;
					}
					case Wdata_dict_val_tid.Tid_monolingualtext: {
						Wdata_claim_itm_monolingualtext claim_monolingualtext = (Wdata_claim_itm_monolingualtext)prop;
						wtr.Val(Bool_.Y, Wdata_dict_val_tid.Bry_monolingualtext);
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv			(Bool_.N, Wdata_dict_value_monolingualtext.Bry_text		, claim_monolingualtext.Text());		// text
						wtr.Kv			(Bool_.Y, Wdata_dict_value_monolingualtext.Bry_language	, claim_monolingualtext.Lang());		// en
						wtr.Nde_end();
						break;
					}
					default: throw Err_.new_unhandled(prop.Val_tid());
				}
			}
			wtr.Ary_end();
			wtr.Kv_ary_empty(Bool_.Y, Wdata_dict_claim_v1.Bry_q);
			wtr.Kv(Bool_.Y, Wdata_dict_claim_v1.Bry_g, qid);
			wtr.Kv(Bool_.Y, Wdata_dict_claim_v1.Bry_rank, Wdata_dict_rank.Tid_normal);
			wtr.Kv_ary_empty(Bool_.Y, Wdata_dict_claim_v1.Bry_refs);
			wtr.Nde_end();
		}
		wtr.Ary_end();
		props.Clear();
	}
}
