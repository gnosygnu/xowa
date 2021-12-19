/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scribunto.libs;

import gplx.types.basics.utls.BryLni;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.basics.utls.StringUtl;
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
	public KeyVal[] Rv() {return rv;} KeyVal[] rv;
	public void Visit_str(Wbase_claim_string itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Reg.Get_str_or(itm.Val_tid(), Wbase_claim_type_.Itm__unknown.Key_str()));
		rv[1] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_value, StringUtl.NewU8(itm.Val_bry()));
	}
	public void Visit_entity(Wbase_claim_entity itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__entity.Key_str());
		rv[1] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_value, Entity_value(itm));
	}
	private static KeyVal[] Entity_value(Wbase_claim_base itm) {
		Wbase_claim_entity claim_entity = (Wbase_claim_entity)itm;
		KeyVal[] rv = new KeyVal[3];
		rv[0] = KeyVal.NewStr(Wbase_claim_entity_.Itm__entity_type.Key_str(), claim_entity.Entity_tid_str());
		rv[1] = KeyVal.NewStr(Wbase_claim_entity_.Itm__numeric_id.Key_str(), claim_entity.Entity_id());	// NOTE: must be int, not String, else will fail when comparing directly to integer; PAGE:en.w:Hollywood_Walk_of_Fame DATE:2016-12-17
		rv[2] = KeyVal.NewStr(Wbase_claim_entity_.Itm__id.Key_str(), Wbase_claim_entity.To_xid__db(Wbase_claim_entity_.Itm__entity_type.Tid(), claim_entity.Entity_id_bry())); // "id" needed PAGE:es.w:Premio_Hugo_a_la_mejor_novela DATE:2017-09-04
		return rv;
	}
	public void Visit_monolingualtext(Wbase_claim_monolingualtext itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__monolingualtext.Key_str());
		rv[1] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_value, Monolingualtext_value(itm));
	}
	private static KeyVal[] Monolingualtext_value(Wbase_claim_monolingualtext itm) {
		KeyVal[] rv = new KeyVal[2];
		rv[0] = KeyVal.NewStr(Wbase_claim_monolingualtext_.Itm__text.Key_str()		, StringUtl.NewU8(itm.Text()));
		rv[1] = KeyVal.NewStr(Wbase_claim_monolingualtext_.Itm__language.Key_str()	, StringUtl.NewU8(itm.Lang()));
		return rv;
	}
	public void Visit_quantity(Wbase_claim_quantity itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__quantity.Key_str());
		rv[1] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_value, Quantity_value(itm));
	}
	private static KeyVal[] Quantity_value(Wbase_claim_quantity itm) {
		KeyVal[] rv = new KeyVal[4];
		rv[0] = KeyVal.NewStr(Wbase_claim_quantity_.Itm__amount.Key_str()			, itm.Amount_as_num().ToStr());	// NOTE: must be num b/c Module code will directly do math calc on it; EX: "99" not "+99"; PAGE:eo.w:Mud�; DATE:2015-11-08
		rv[1] = KeyVal.NewStr(Wbase_claim_quantity_.Itm__unit.Key_str()				, StringUtl.NewU8(itm.Unit()));
		rv[2] = KeyVal.NewStr(Wbase_claim_quantity_.Itm__upperbound.Key_str()		, itm.Ubound_as_num() == null ? null : itm.Ubound_as_num().ToStr());
		rv[3] = KeyVal.NewStr(Wbase_claim_quantity_.Itm__lowerbound.Key_str()		, itm.Lbound_as_num() == null ? null : itm.Lbound_as_num().ToStr());
		return rv;
	}
	public void Visit_time(Wbase_claim_time itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__time.Key_str());
		rv[1] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_value, Time_value(itm));
	}
	private static KeyVal[] Time_value(Wbase_claim_time itm) {
		KeyVal[] rv = new KeyVal[6];
		rv[0] = KeyVal.NewStr(Wbase_claim_time_.Itm__time.Key_str()				, StringUtl.NewA7(itm.Time()));
		rv[1] = KeyVal.NewStr(Wbase_claim_time_.Itm__precision.Key_str()			, itm.Precision_int());		// NOTE: must return int, not str; DATE:2014-02-18
		rv[2] = KeyVal.NewStr(Wbase_claim_time_.Itm__before.Key_str()			, itm.Before_int());
		rv[3] = KeyVal.NewStr(Wbase_claim_time_.Itm__after.Key_str()				, itm.After_int());
		rv[4] = KeyVal.NewStr(Wbase_claim_time_.Itm__timezone.Key_str()			, Wbase_claim_time_.Dflt__timezone.Val_int());	// ASSUME: always 0 b/c UTC?; DATE:2015-09-21
		rv[5] = KeyVal.NewStr(Wbase_claim_time_.Itm__calendarmodel.Key_str()		, Wbase_claim_time_.Dflt__calendarmodel.Val_str());
		return rv;
	}
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm) {
		rv = new KeyVal[2];
		rv[0] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_type, Wbase_claim_type_.Itm__globecoordinate.Key_str());
		rv[1] = KeyVal.NewStr(Scrib_lib_wikibase_srl.Key_value, Globecoordinate_value(itm));
	}
	private static KeyVal[] Globecoordinate_value(Wbase_claim_globecoordinate itm) {
		KeyVal[] rv = new KeyVal[5];
		rv[0] = KeyVal.NewStr(Wbase_claim_globecoordinate_.Itm__latitude.Key_str()			, DoubleUtl.Parse(StringUtl.NewA7(itm.Lat())));
		rv[1] = KeyVal.NewStr(Wbase_claim_globecoordinate_.Itm__longitude.Key_str()			, DoubleUtl.Parse(StringUtl.NewA7(itm.Lng())));
		rv[2] = KeyVal.NewStr(Wbase_claim_globecoordinate_.Itm__altitude.Key_str()			, StringUtl.NewU8(itm.Alt()));
		rv[3] = KeyVal.NewStr(Wbase_claim_globecoordinate_.Itm__globe.Key_str()				, StringUtl.NewU8(itm.Glb()));
		rv[4] = KeyVal.NewStr(Wbase_claim_globecoordinate_.Itm__precision.Key_str()			, CalcPrecision(itm.Prc(), itm.Lng()).ToDouble());
		return rv;
	}
	public void Visit_system(Wbase_claim_value itm) {
		rv = KeyValUtl.AryEmpty;
	}
	public static GfoDecimal CalcPrecision(byte[] prc, byte[] lng) {
		GfoDecimal rv;

		// precision is "null"
		if (BryLni.Eq(prc, ObjectUtl.NullBry)) {
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

			rv = GfoDecimalUtl.NewByFloat((float)Math.pow(10, -power));
		}
		else {
			rv = GfoDecimalUtl.Parse(StringUtl.NewA7(prc));
		}
		return rv;
	}
}