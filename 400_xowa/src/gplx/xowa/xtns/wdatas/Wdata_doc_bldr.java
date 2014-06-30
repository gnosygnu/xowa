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
import gplx.json.*;
public class Wdata_doc_bldr {
	public Wdata_doc_bldr(Wdata_wiki_mgr mgr) {this.mgr = mgr;} Wdata_wiki_mgr mgr;
	public Wdata_doc_bldr Qid_(String v) {this.qid = Bry_.new_ascii_(v); return this;} private byte[] qid; ListAdp props = ListAdp_.new_();
	public Wdata_doc_bldr Props_add(Wdata_prop_itm_core... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			props.Add(ary[i]);
		return this;
	}
	public static byte[] Xto_time(DateAdp date) {
		// +0000000yyyy-MM-ddTHH:mm:ssZ
		tmp_bfr
		.Add(Bry_year_prefix)
		.Add_int_fixed(date.Year(), 4)
		.Add_byte(Byte_ascii.Dash)
		.Add_int_fixed(date.Month(), 2)
		.Add_byte(Byte_ascii.Dash)
		.Add_int_fixed(date.Day(), 2)
		.Add_byte(Byte_ascii.Ltr_T)
		.Add_int_fixed(date.Hour(), 2)
		.Add_byte(Byte_ascii.Colon)
		.Add_int_fixed(date.Minute(), 2)
		.Add_byte(Byte_ascii.Colon)
		.Add_int_fixed(date.Second(), 2)
		.Add_byte(Byte_ascii.Ltr_Z)
		;
		return tmp_bfr.XtoAryAndClear();
	}	private static Bry_bfr tmp_bfr = Bry_bfr.new_(); private static byte[] Bry_year_prefix = Bry_.new_ascii_("+0000000");
	public Wdata_doc_bldr Description_add(String lang, String val) {descriptions.Add(KeyVal_.new_(lang, val)); return this;} ListAdp descriptions = ListAdp_.new_();
	public Wdata_doc_bldr Label_add(String lang, String val) {labels.Add(KeyVal_.new_(lang, val)); return this;} ListAdp labels = ListAdp_.new_();
	public Wdata_doc_bldr Link_add(String xwiki, String ttl) {links.Add(KeyVal_.new_(xwiki, ttl)); return this;} ListAdp links = ListAdp_.new_();
	public Wdata_doc_bldr Alias_add(String lang, String[] ary) {
		aliases.AddReplace(lang, new Wdata_doc_alias(lang, ary));
		return this;
	} 	OrderedHash aliases = OrderedHash_.new_();
	public byte[] Xto_bry() {
		wtr.Nde_bgn();
		wtr.Kv(Bool_.N, Wdata_doc_consts.Key_atr_entity_bry, qid);
		Xto_bry__list(Wdata_doc_consts.Key_atr_label_bry, labels);
		Xto_bry__list(Wdata_doc_consts.Key_atr_description_bry, descriptions);
		Xto_bry__sitelinks(Wdata_doc_consts.Key_atr_links_bry, links);
		Xto_bry__aliases();
		Xto_bry__claims();
		wtr.Nde_end();
		return wtr.Bld();
	}	Json_doc_wtr wtr = new Json_doc_wtr();
	private void Xto_bry__list(byte[] key, ListAdp list) {
		int len = list.Count();
		if (len == 0) return;
		wtr.Key(true, key);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			KeyVal kv = (KeyVal)list.FetchAt(i);
			wtr.Kv(i != 0, Bry_.new_utf8_(kv.Key()), Bry_.new_utf8_(kv.Val_to_str_or_empty()));
		}
		wtr.Nde_end();
		list.Clear();
	}
	private void Xto_bry__sitelinks(byte[] key, ListAdp list) {	// NOTE: changed to reflect new sitelinks structure; DATE:2014-02-04
		int len = list.Count();
		if (len == 0) return;
		wtr.Key(true, key);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			if (i != 0) wtr.Comma();
			KeyVal kv = (KeyVal)list.FetchAt(i);
			wtr.Key(false, Bry_.new_utf8_(kv.Key()));											// write key;	EX: enwiki:
			wtr.Nde_bgn();																			// bgn nde;		EX: {
			wtr.Kv(false, Wdata_doc_.Key_name, Bry_.new_utf8_(kv.Val_to_str_or_empty()));		// write name;	EX:   name=Earth
			wtr.Nde_end();																			// end nde;		EX: }
		}
		wtr.Nde_end();
		list.Clear();
	}
	private void Xto_bry__aliases() {
		int len = aliases.Count();
		if (len == 0) return;
		wtr.Key(true, Wdata_doc_consts.Key_atr_aliases_bry);
		wtr.Nde_bgn();
		for (int i = 0; i < len; i++) {
			Wdata_doc_alias alias = (Wdata_doc_alias)aliases.FetchAt(i);
			wtr.Key(i != 0, Bry_.new_utf8_(alias.Lang()));
			wtr.Ary_bgn();
			String[] aliases_ary = alias.Aliases();
			int aliases_len = aliases_ary.length;
			for (int j = 0; j < aliases_len; j++) {
				String aliases_str = aliases_ary[j];
				wtr.Val(j != 0, Bry_.new_utf8_(aliases_str));
			}
			wtr.Ary_end();
		}
		wtr.Nde_end();
		aliases.Clear();
	}
	private void Xto_bry__claims() {
		int len = props.Count();
		if (len == 0) return;
		wtr.Key(true, Wdata_doc_consts.Key_atr_claims_bry);
		wtr.Ary_bgn();
		for (int i = 0; i < len; i++) {
			if (i != 0) wtr.Comma();
			Wdata_prop_itm_core prop = (Wdata_prop_itm_core)props.FetchAt(i);
			wtr.Nde_bgn();
			wtr.Key(false, Wdata_doc_consts.Key_claims_m_bry);
			wtr.Ary_bgn();
			wtr.Val(Bool_.N, prop.Snak_bry());
			wtr.Val(Bool_.Y, prop.Pid());
			if (prop.Snak_tid() == Wdata_prop_itm_base_.Snak_tid_value) {
				switch (prop.Val_tid_byte()) {
					case Wdata_prop_itm_base_.Val_tid_string:
						wtr.Val(Bool_.Y, Wdata_prop_itm_base_.Val_bry_string);
						wtr.Val(Bool_.Y, prop.Val());
						break;
					case Wdata_prop_itm_base_.Val_tid_entity:
						wtr.Val(Bool_.Y, Wdata_prop_itm_base_.Val_bry_entity);
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv(Bool_.N, Wdata_doc_consts.Key_ent_entity_type_bry, Wdata_doc_consts.Val_ent_entity_type_item_bry);
						wtr.Kv(Bool_.Y, Wdata_doc_consts.Key_ent_numeric_id_bry, Bry_.X_to_int(prop.Val()));
						wtr.Nde_end();
						break;
					case Wdata_prop_itm_base_.Val_tid_time:
						wtr.Val(Bool_.Y, Wdata_prop_itm_base_.Val_bry_time);
						wtr.Comma();
						wtr.Nde_bgn();
						wtr.Kv(Bool_.N, Wdata_doc_consts.Key_time_time_bry					, prop.Val());
						wtr.Kv(Bool_.Y, Wdata_doc_consts.Key_time_precision_bry				, Wdata_doc_consts.Val_time_precision_bry);
						wtr.Kv(Bool_.Y, Wdata_doc_consts.Key_time_before_bry				, Wdata_doc_consts.Val_time_before_bry);
						wtr.Kv(Bool_.Y, Wdata_doc_consts.Key_time_after_bry					, Wdata_doc_consts.Val_time_after_bry);
						wtr.Kv(Bool_.Y, Wdata_doc_consts.Key_time_timezone_bry				, Wdata_doc_consts.Val_time_timezone_bry);
						wtr.Kv(Bool_.Y, Wdata_doc_consts.Key_time_calendarmodel_bry			, Wdata_doc_consts.Val_time_calendarmodel_bry);
						wtr.Nde_end();
						break;
					case Wdata_prop_itm_base_.Val_tid_globecoordinate: {
						wtr.Val(Bool_.Y, Wdata_prop_itm_base_.Val_bry_globecoordinate);
						wtr.Comma();
						wtr.Nde_bgn();
						byte[][] flds = Bry_.Split(prop.Val(), Byte_ascii.Pipe);
						wtr.Kv_double	(Bool_.N, Wdata_doc_consts.Key_geo_latitude_bry		, Double_.parse_(String_.new_ascii_(flds[0])));
						wtr.Kv_double	(Bool_.Y, Wdata_doc_consts.Key_geo_longitude_bry	, Double_.parse_(String_.new_ascii_(flds[1])));
						wtr.Kv			(Bool_.Y, Wdata_doc_consts.Key_geo_altitude_bry		, null);
						wtr.Kv			(Bool_.Y, Wdata_doc_consts.Key_geo_globe_bry		, Wdata_doc_consts.Val_time_globe_bry);
						wtr.Kv_double	(Bool_.Y, Wdata_doc_consts.Key_geo_precision_bry	, .00001d);
						wtr.Nde_end();
						break;
					}
					case Wdata_prop_itm_base_.Val_tid_quantity: {
						wtr.Val(Bool_.Y, Wdata_prop_itm_base_.Val_bry_quantity);
						wtr.Comma();
						wtr.Nde_bgn();
						byte[][] flds = Bry_.Split(prop.Val(), Byte_ascii.Pipe);
						wtr.Kv			(Bool_.N, Wdata_doc_consts.Key_quantity_amount_bry	, flds[0]);		// +1,234
						wtr.Kv			(Bool_.Y, Wdata_doc_consts.Key_quantity_unit_bry	, flds[1]);		// 1
						wtr.Kv			(Bool_.Y, Wdata_doc_consts.Key_quantity_ubound_bry	, flds[2]);		// +1,235
						wtr.Kv			(Bool_.Y, Wdata_doc_consts.Key_quantity_lbound_bry	, flds[3]);		// +1,233
						wtr.Nde_end();
						break;
					}
					default: throw Err_.unhandled(prop.Val_tid_byte());
				}
			}
			wtr.Ary_end();
			wtr.Kv_ary_empty(Bool_.Y, Wdata_doc_consts.Key_claims_q_bry);
			wtr.Kv(Bool_.Y, Wdata_doc_consts.Key_claims_g_bry, qid);
			wtr.Kv(Bool_.Y, Wdata_doc_consts.Key_claims_rank_bry, Wdata_prop_itm_base_.Rank_normal);
			wtr.Kv_ary_empty(Bool_.Y, Wdata_doc_consts.Key_claims_refs_bry);
			wtr.Nde_end();
		}
		wtr.Ary_end();
		props.Clear();
	}
	public Wdata_doc Xto_page_doc() {return new Wdata_doc(qid, mgr, mgr.Parser().Parse(Xto_bry()));}
}
class Wdata_doc_alias {
	public Wdata_doc_alias(String lang, String[] aliases) {this.lang = lang; this.aliases = aliases;}
	public String Lang() {return lang;} private String lang;
	public String[] Aliases() {return aliases;} private String[] aliases;
}
