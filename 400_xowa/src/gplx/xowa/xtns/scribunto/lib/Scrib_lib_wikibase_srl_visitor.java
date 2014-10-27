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
package gplx.xowa.xtns.scribunto.lib; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.xtns.wdatas.core.*;
class Scrib_lib_wikibase_srl_visitor implements Wdata_claim_visitor {
	public KeyVal[] Rv() {return rv;} KeyVal[] rv;
	public void Visit_str(Wdata_claim_itm_str itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_type, Wdata_dict_val_tid.Xto_str(itm.Val_tid()));
		rv[1] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_value, String_.new_utf8_(itm.Val_str()));
	}
	public void Visit_entity(Wdata_claim_itm_entity itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_type, Wdata_dict_val_tid.Str_entity);
		rv[1] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_value, Entity_value(itm));
	}
	private static KeyVal[] Entity_value(Wdata_claim_itm_core itm) {
		Wdata_claim_itm_entity claim_entity = (Wdata_claim_itm_entity)itm;
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Wdata_dict_value_entity.Str_entity_type, Wdata_dict_value_entity.Val_entity_type_item_str);
		rv[1] = KeyVal_.new_(Wdata_dict_value_entity.Str_numeric_id, Int_.Xto_str(claim_entity.Entity_id()));
		return rv;
	}
	public void Visit_monolingualtext(Wdata_claim_itm_monolingualtext itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_type, Wdata_dict_val_tid.Str_monolingualtext);
		rv[1] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_value, Monolingualtext_value(itm));
	}
	private static KeyVal[] Monolingualtext_value(Wdata_claim_itm_monolingualtext itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Wdata_dict_value_monolingualtext.Str_text			, String_.new_utf8_(itm.Text()));
		rv[1] = KeyVal_.new_(Wdata_dict_value_monolingualtext.Str_language		, String_.new_utf8_(itm.Lang()));
		return rv;
	}		public void Visit_quantity(Wdata_claim_itm_quantity itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_type, Wdata_dict_val_tid.Str_quantity);
		rv[1] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_value, Quantity_value(itm));
	}
	private static KeyVal[] Quantity_value(Wdata_claim_itm_quantity itm) {
		KeyVal[] rv = new KeyVal[4];
		rv[0] = KeyVal_.new_(Wdata_dict_value_quantity.Str_amount			, String_.new_utf8_(itm.Amount()));
		rv[1] = KeyVal_.new_(Wdata_dict_value_quantity.Str_unit				, String_.new_utf8_(itm.Unit()));
		rv[2] = KeyVal_.new_(Wdata_dict_value_quantity.Str_upperbound		, String_.new_utf8_(itm.Ubound()));
		rv[3] = KeyVal_.new_(Wdata_dict_value_quantity.Str_lowerbound		, String_.new_utf8_(itm.Lbound()));
		return rv;
	}
	public void Visit_time(Wdata_claim_itm_time itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_type, Wdata_dict_val_tid.Str_time);
		rv[1] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_value, Time_value(itm));
	}
	private static KeyVal[] Time_value(Wdata_claim_itm_time itm) {
		KeyVal[] rv = new KeyVal[6];
		rv[0] = KeyVal_.new_(Wdata_dict_value_time.Str_time				, String_.new_ascii_(itm.Time()));
		rv[1] = KeyVal_.new_(Wdata_dict_value_time.Str_precision		, Wdata_dict_value_time.Val_precision_int);	// NOTE: must return int, not str; DATE:2014-02-18
		rv[2] = KeyVal_.new_(Wdata_dict_value_time.Str_before			, Wdata_dict_value_time.Val_before_int);
		rv[3] = KeyVal_.new_(Wdata_dict_value_time.Str_after			, Wdata_dict_value_time.Val_after_int);
		rv[4] = KeyVal_.new_(Wdata_dict_value_time.Str_timezone			, Wdata_dict_value_time.Val_timezone_str);
		rv[5] = KeyVal_.new_(Wdata_dict_value_time.Str_calendarmodel	, Wdata_dict_value_time.Val_calendarmodel_str);
		return rv;
	}
	public void Visit_globecoordinate(Wdata_claim_itm_globecoordinate itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_type, Wdata_dict_val_tid.Str_globecoordinate);
		rv[1] = KeyVal_.new_(Scrib_lib_wikibase_srl.Key_value, Globecoordinate_value(itm));
	}
	private static KeyVal[] Globecoordinate_value(Wdata_claim_itm_globecoordinate itm) {
		KeyVal[] rv = new KeyVal[5];
		rv[0] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_latitude			, Double_.parse_(String_.new_ascii_(itm.Lat())));
		rv[1] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_longitude			, Double_.parse_(String_.new_ascii_(itm.Lng())));
		rv[2] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_altitude			, null);
		rv[3] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_globe				, Wdata_dict_value_globecoordinate.Val_globe_dflt_str);
		rv[4] = KeyVal_.new_(Wdata_dict_value_globecoordinate.Str_precision			, .00001d);
		return rv;
	}
	public void Visit_system(Wdata_claim_itm_system itm) {
		rv = KeyVal_.Ary_empty;
	}
}