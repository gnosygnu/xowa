/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scribunto.libs;

import gplx.Bry_;
import gplx.Decimal_adp;
import gplx.Decimal_adp_;
import gplx.Double_;
import gplx.Keyval;
import gplx.Keyval_;
import gplx.Object_;
import gplx.String_;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_visitor;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_globecoordinate;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_globecoordinate_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_monolingualtext;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_monolingualtext_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_quantity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_quantity_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_string;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_value;

public class Scrib_lib_wikibase_srl_visitor implements Wbase_claim_visitor {
	public Keyval[] Rv() {return rv;} Keyval[] rv;
	public void Visit_str(Wbase_claim_string itm) {
		rv = new Keyval[2];
		rv[0] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Reg.Get_str_or(itm.Val_tid(), Wbase_claim_type_.Itm__unknown.Key_str()));
		rv[1] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_value, String_.new_u8(itm.Val_bry()));
	}
	public void Visit_entity(Wbase_claim_entity itm) {
		rv = new Keyval[2];
		rv[0] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__entity.Key_str());
		rv[1] = Keyval_.new_(Scrib_lib_wikibase_srl.Key_value, Entity_value(itm));
	}
	private static Keyval[] Entity_value(Wbase_claim_base itm) {
		Wbase_claim_entity claim_entity = (Wbase_claim_entity)itm;
		Keyval[] rv = new Keyval[3];
		rv[0] = Keyval_.new_(Wbase_claim_entity_.Itm__entity_type.Key_str(), claim_entity.Entity_tid_str());
		rv[1] = Keyval_.new_(Wbase_claim_entity_.Itm__numeric_id.Key_str(), claim_entity.Entity_id());	// NOTE: must be int, not String, else will fail when comparing directly to integer; PAGE:en.w:Hollywood_Walk_of_Fame DATE:2016-12-17
		rv[2] = Keyval_.new_(Wbase_claim_entity_.Itm__id.Key_str(), Wbase_claim_entity.To_xid__db(Wbase_claim_entity_.Itm__entity_type.Tid(), claim_entity.Entity_id_bry())); // "id" needed PAGE:es.w:Premio_Hugo_a_la_mejor_novela DATE:2017-09-04
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
		rv[4] = Keyval_.new_(Wbase_claim_time_.Itm__timezone.Key_str()			, Wbase_claim_time_.Dflt__timezone.Val_int());	// ASSUME: always 0 b/c UTC?; DATE:2015-09-21
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
		rv[4] = Keyval_.new_(Wbase_claim_globecoordinate_.Itm__precision.Key_str()			, CalcPrecision(itm.Prc(), itm.Lng()).To_double());
		return rv;
	}
	public void Visit_system(Wbase_claim_value itm) {
		rv = Keyval_.Ary_empty;
	}
	public static Decimal_adp CalcPrecision(byte[] prc, byte[] lng) {
		Decimal_adp rv;

		// precision is "null"
		if (Bry_.Eq(prc, Object_.Bry__null)) {
			// 2020-09-25|ISSUE#:792|use longitude to determine precision (contributed by desb42@)
			int lngLen = lng.length;
			int power = 0;
			// calc power by finding decimal point
			for (int i = 0; i < lngLen; i++) {
				byte b = lng[i];
				if (b == '.') {
					power = lngLen - (i + 1); // +1 to set after "."
					if (power > 8) { // ensure power is 8 or less
						power = 8;
					}
					break;
				}
			}

			rv = Decimal_adp_.float_((float)Math.pow(10, -power));
		}
		else {
			rv = Decimal_adp_.parse(String_.new_a7(prc));
		}
		return rv;
	}
}