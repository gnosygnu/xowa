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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*;
class Scrib_lib_wikibase_srl_visitor implements Wbase_claim_visitor {
	public Keyval[] Rv() {return rv;} Keyval[] rv;
	public void Visit_str(Wbase_claim_string itm) {
		rv = new Keyval[2];
		rv[0] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Reg.Get_str_or(itm.Val_tid(), Wbase_claim_type_.Itm__unknown.Key_str()));
		rv[1] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_value, String_.new_u8(itm.Val_str()));
	}
	public void Visit_entity(Wbase_claim_entity itm) {
		rv = new Keyval[2];
		rv[0] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__entity.Key_str());
		rv[1] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_value, Entity_value(itm));
	}
	private static Keyval[] Entity_value(Wbase_claim_base itm) {
		Wbase_claim_entity claim_entity = (Wbase_claim_entity)itm;
		Keyval[] rv = new Keyval[2];
		rv[0] = Keyval_.new_(Wbase_claim_entity_.Itm__entity_type.Key_str(), claim_entity.Entity_tid_str());
		rv[1] = Keyval_.new_(Wbase_claim_entity_.Itm__numeric_id.Key_str(), Int_.To_str(claim_entity.Entity_id()));
		return rv;
	}
	public void Visit_monolingualtext(Wbase_claim_monolingualtext itm) {
		rv = new Keyval[2];
		rv[0] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__monolingualtext.Key_str());
		rv[1] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_value, Monolingualtext_value(itm));
	}
	private static Keyval[] Monolingualtext_value(Wbase_claim_monolingualtext itm) {
		Keyval[] rv = new Keyval[2];
		rv[0] = Keyval_.new_(Wbase_claim_monolingualtext_.Itm__text.Key_str()		, String_.new_u8(itm.Text()));
		rv[1] = Keyval_.new_(Wbase_claim_monolingualtext_.Itm__language.Key_str()	, String_.new_u8(itm.Lang()));
		return rv;
	}
	public void Visit_quantity(Wbase_claim_quantity itm) {
		rv = new Keyval[2];
		rv[0] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__quantity.Key_str());
		rv[1] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_value, Quantity_value(itm));
	}
	private static Keyval[] Quantity_value(Wbase_claim_quantity itm) {
		Keyval[] rv = new Keyval[4];
		rv[0] = Keyval_.new_(Wbase_claim_quantity_.Itm__amount.Key_str()			, itm.Amount_as_num().To_str());	// NOTE: must be num b/c Module code will directly do math calc on it; EX: "99" not "+99"; PAGE:eo.w:Mud�; DATE:2015-11-08
		rv[1] = Keyval_.new_(Wbase_claim_quantity_.Itm__unit.Key_str()				, String_.new_u8(itm.Unit()));
		rv[2] = Keyval_.new_(Wbase_claim_quantity_.Itm__upperbound.Key_str()		, itm.Ubound_as_num().To_str());
		rv[3] = Keyval_.new_(Wbase_claim_quantity_.Itm__lowerbound.Key_str()		, itm.Lbound_as_num().To_str());
		return rv;
	}
	public void Visit_time(Wbase_claim_time itm) {
		rv = new Keyval[2];
		rv[0] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__time.Key_str());
		rv[1] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_value, Time_value(itm));
	}
	private static Keyval[] Time_value(Wbase_claim_time itm) {
		Keyval[] rv = new Keyval[6];
		rv[0] = Keyval_.new_(Wbase_claim_time_.Itm__time.Key_str()				, String_.new_a7(itm.Time()));
		rv[1] = Keyval_.new_(Wbase_claim_time_.Itm__precision.Key_str()			, itm.Precision_int());		// NOTE: must return int, not str; DATE:2014-02-18
		rv[2] = Keyval_.new_(Wbase_claim_time_.Itm__before.Key_str()			, itm.Before_int());
		rv[3] = Keyval_.new_(Wbase_claim_time_.Itm__after.Key_str()				, itm.After_int());
		rv[4] = Keyval_.new_(Wbase_claim_time_.Itm__timezone.Key_str()			, Wbase_claim_time_.Dflt__timezone.Val_str());	// ASSUME: always 0 b/c UTF?; DATE:2015-09-21
		rv[5] = Keyval_.new_(Wbase_claim_time_.Itm__calendarmodel.Key_str()		, Wbase_claim_time_.Dflt__calendarmodel.Val_str());
		return rv;
	}
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm) {
		rv = new Keyval[2];
		rv[0] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__globecoordinate.Key_str());
		rv[1] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_value, Globecoordinate_value(itm));
	}
	private static Keyval[] Globecoordinate_value(Wbase_claim_globecoordinate itm) {			
		Keyval[] rv = new Keyval[5];
		rv[0] = Keyval_.new_(Wbase_claim_globecoordinate_.Itm__latitude.Key_str()			, Double_.parse(String_.new_a7(itm.Lat())));
		rv[1] = Keyval_.new_(Wbase_claim_globecoordinate_.Itm__longitude.Key_str()			, Double_.parse(String_.new_a7(itm.Lng())));
		rv[2] = Keyval_.new_(Wbase_claim_globecoordinate_.Itm__altitude.Key_str()			, String_.new_u8(itm.Alt()));
		rv[3] = Keyval_.new_(Wbase_claim_globecoordinate_.Itm__globe.Key_str()				, String_.new_u8(itm.Glb()));
		rv[4] = Keyval_.new_(Wbase_claim_globecoordinate_.Itm__precision.Key_str()			, itm.Prc_as_num().To_double());
		return rv;
	}
	public void Visit_system(Wbase_claim_value itm) {
		rv = Keyval_.Ary_empty;
	}
}