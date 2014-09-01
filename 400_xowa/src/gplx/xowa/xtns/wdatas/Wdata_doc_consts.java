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
public class Wdata_doc_consts {
	public static final String 
	  Key_atr_entity_str = "entity", Key_atr_label_str = "label", Key_atr_description_str = "description", Key_atr_aliases_str = "aliases"
	, Key_atr_links_str = "links", Key_atr_claims_str = "claims"
	, Key_claims_m_str = "m", Key_claims_q_str = "q", Key_claims_g_str = "g", Key_claims_rank_str = "rank", Key_claims_refs_str = "refs"
	, Key_ent_entity_type_str = "entity-type", Key_ent_numeric_id_str = "numeric-id"
	, Val_ent_entity_type_item_str = "item"
	, Val_prop_value_str = "value", Val_prop_novalue_str = "novalue", Val_prop_somevalue_str = "somevalue"
	, Key_time_time_str = "time", Key_time_precision_str = "precision", Key_time_before_str = "before", Key_time_after_str = "after", Key_time_timezone_str = "timezone", Key_time_calendarmodel_str = "calendarmodel"
	, Val_time_precision_str = "11", Val_time_before_str = "0", Val_time_after_str = "0", Val_time_timezone_str = "0"
	, Val_time_globe_str = "http:\\/\\/www.wikidata.org\\/entity\\/Q2", Val_time_calendarmodel_str = "http://www.wikidata.org/entity/Q1985727"
	, Key_geo_type_str = "globecoordinate", Key_geo_latitude_str = "latitude", Key_geo_longitude_str = "longitude", Key_geo_altitude_str = "altitude"
	, Key_geo_globe_str = "globe", Key_geo_precision_str = "precision"
	, Key_quantity_type_str = "quantity", Key_quantity_amount_str = "amount", Key_quantity_unit_str = "unit"
	, Key_quantity_ubound_str = "upperBound", Key_quantity_lbound_str = "lowerBound"
	, Key_monolingualtext_language_str = "language", Key_monolingualtext_text_str = "text"
	;
	public static final int 
		Val_time_precision_int = 11, Val_time_before_int = 0, Val_time_after_int = 0, Val_time_timezone_int = 0;
	public static final byte[] 
	  Key_atr_entity_bry = bry_(Key_atr_entity_str), Key_atr_label_bry = bry_(Key_atr_label_str), Key_atr_description_bry = bry_(Key_atr_description_str), Key_atr_aliases_bry = bry_(Key_atr_aliases_str)
	, Key_atr_links_bry = bry_(Key_atr_links_str), Key_atr_claims_bry = bry_(Key_atr_claims_str)			
	, Key_claims_g_bry = bry_(Key_claims_g_str), Key_claims_m_bry = bry_(Key_claims_m_str), Key_claims_q_bry = bry_(Key_claims_q_str), Key_claims_rank_bry = bry_(Key_claims_rank_str), Key_claims_refs_bry = bry_(Key_claims_refs_str)
	
	, Key_ent_entity_type_bry = bry_(Key_ent_entity_type_str), Key_ent_numeric_id_bry = bry_(Key_ent_numeric_id_str)
	, Val_ent_entity_type_item_bry = bry_(Val_ent_entity_type_item_str)
	, Val_prop_value_bry = bry_(Val_prop_value_str), Val_prop_novalue_bry = bry_(Val_prop_novalue_str), Val_prop_somevalue_bry = bry_(Val_prop_somevalue_str)
	, Key_time_time_bry = bry_(Key_time_time_str), Key_time_timezone_bry = bry_(Key_time_timezone_str), Key_time_before_bry = bry_(Key_time_before_str), Key_time_after_bry = bry_(Key_time_after_str)
	, Key_time_precision_bry = bry_(Key_time_precision_str), Key_time_calendarmodel_bry = bry_(Key_time_calendarmodel_str)
	, Val_time_precision_bry = bry_(Val_time_precision_str), Val_time_before_bry = bry_(Val_time_before_str), Val_time_after_bry = bry_(Val_time_after_str), Val_time_timezone_bry = bry_(Val_time_timezone_str)
	, Val_time_calendarmodel_bry = bry_(Val_time_timezone_str), Val_time_globe_bry = bry_(Val_time_globe_str)
	, Key_geo_latitude_bry = bry_(Key_geo_latitude_str), Key_geo_longitude_bry = bry_(Key_geo_longitude_str), Key_geo_altitude_bry = bry_(Key_geo_altitude_str)
	, Key_geo_globe_bry = bry_(Key_geo_globe_str), Key_geo_precision_bry = bry_(Key_geo_precision_str)
	, Key_quantity_amount_bry = bry_(Key_quantity_amount_str), Key_quantity_unit_bry = bry_(Key_quantity_unit_str)
	, Key_quantity_ubound_bry = bry_(Key_quantity_ubound_str), Key_quantity_lbound_bry = bry_(Key_quantity_lbound_str)
	, Key_monolingualtext_language_bry = bry_(Key_monolingualtext_language_str), Key_monolingualtext_text_bry = bry_(Key_monolingualtext_text_str)
	;
	private static byte[] bry_(String s) {return Bry_.new_ascii_(s);}
}
