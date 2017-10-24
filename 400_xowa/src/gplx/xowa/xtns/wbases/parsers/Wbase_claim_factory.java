/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.langs.jsons.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Wbase_claim_factory {
	public Wbase_claim_base Parse(byte[] qid, int pid, byte snak_tid, Json_nde nde, byte value_tid, Json_itm value_itm) {
		switch (value_tid) {
			case Wbase_claim_type_.Tid__string:				return new Wbase_claim_string(pid, snak_tid, value_itm.Data_bry());
			case Wbase_claim_type_.Tid__entity:				return Parse_datavalue_entity				(qid, pid, snak_tid, Json_nde.cast(value_itm));
			case Wbase_claim_type_.Tid__time:				return Parse_datavalue_time					(qid, pid, snak_tid, Json_nde.cast(value_itm));
			case Wbase_claim_type_.Tid__quantity:			return Parse_datavalue_quantity				(qid, pid, snak_tid, Json_nde.cast(value_itm));
			case Wbase_claim_type_.Tid__globecoordinate:	return Parse_datavalue_globecoordinate		(qid, pid, snak_tid, Json_nde.cast(value_itm));
			case Wbase_claim_type_.Tid__monolingualtext:	return Parse_datavalue_monolingualtext		(qid, pid, snak_tid, Json_nde.cast(value_itm));
			default:										throw Err_.new_unhandled_default(value_tid);
		}
	}
	private Wbase_claim_entity Parse_datavalue_entity(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		int len = nde.Len();
		byte entity_tid = Byte_.Max_value_127;
		byte[] entity_id_bry = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wbase_claim_entity_.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
			switch (tid) {
				case Wbase_claim_entity_.Tid__entity_type:		entity_tid = Wbase_claim_entity_type_.Reg.Get_tid_or_fail(sub.Val().Data_bry()); break;
				case Wbase_claim_entity_.Tid__numeric_id:		entity_id_bry = sub.Val().Data_bry(); break;
				case Wbase_claim_entity_.Tid__id:				break;	// ignore
			}
		}
		if (entity_id_bry == null) throw Err_.new_wo_type("pid is invalid entity", "pid", pid);
		return new Wbase_claim_entity(pid, snak_tid, entity_tid, entity_id_bry);
	}
	private Wbase_claim_monolingualtext Parse_datavalue_monolingualtext(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		int len = nde.Len();
		byte[] lang = null, text = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wbase_claim_monolingualtext_.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wbase_claim_monolingualtext_.Tid__text:			text = sub_val_bry; break;
				case Wbase_claim_monolingualtext_.Tid__language:		lang = sub_val_bry; break;
			}
		}
		if (lang == null || text == null) throw Err_.new_wo_type("pid is invalid monolingualtext", "pid", pid);
		return new Wbase_claim_monolingualtext(pid, snak_tid, lang, text);
	}
	private Wbase_claim_globecoordinate Parse_datavalue_globecoordinate(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		int len = nde.Len();
		byte[] lat = null, lng = null, alt = null, prc = null, glb = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wbase_claim_globecoordinate_.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wbase_claim_globecoordinate_.Tid__latitude:		lat = sub_val_bry; break;
				case Wbase_claim_globecoordinate_.Tid__longitude:		lng = sub_val_bry; break;
				case Wbase_claim_globecoordinate_.Tid__altitude:		alt = sub_val_bry; break;
				case Wbase_claim_globecoordinate_.Tid__precision:		prc = sub_val_bry; break;
				case Wbase_claim_globecoordinate_.Tid__globe:			glb = sub_val_bry; break;
			}
		}
		if (lat == null || lng == null) throw Err_.new_wo_type("pid is invalid globecoordinate", "pid", pid);
		return new Wbase_claim_globecoordinate(pid, snak_tid, lat, lng, alt, prc, glb);
	}
	private Wbase_claim_quantity Parse_datavalue_quantity(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		int len = nde.Len();
		byte[] amount = null, unit = null, ubound = null, lbound = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wbase_claim_quantity_.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wbase_claim_quantity_.Tid__amount:			amount = sub_val_bry; break;
				case Wbase_claim_quantity_.Tid__unit:			unit = sub_val_bry; break;
				case Wbase_claim_quantity_.Tid__upperbound:		ubound = sub_val_bry; break;
				case Wbase_claim_quantity_.Tid__lowerbound:		lbound = sub_val_bry; break;
			}
		}
		if (amount == null) throw Err_.new_wo_type("pid is invalid quantity", "pid", pid);
		return new Wbase_claim_quantity(pid, snak_tid, amount, unit, ubound, lbound);
	}
	private Wbase_claim_time Parse_datavalue_time(byte[] qid, int pid, byte snak_tid, Json_nde nde) {
		int len = nde.Len();
		byte[] time = null, timezone = null, before = null, after = null, precision = null, calendarmodel = null;
		for (int i = 0; i < len; ++i) {
			Json_kv sub = Json_kv.cast(nde.Get_at(i));
			byte tid = Wbase_claim_time_.Reg.Get_tid_or_max_and_log(qid, sub.Key().Data_bry()); if (tid == Byte_.Max_value_127) continue;
			byte[] sub_val_bry = sub.Val().Data_bry();
			switch (tid) {
				case Wbase_claim_time_.Tid__time:			time = sub_val_bry; break;
				case Wbase_claim_time_.Tid__timezone:		timezone = sub_val_bry; break;
				case Wbase_claim_time_.Tid__before:			before = sub_val_bry; break;
				case Wbase_claim_time_.Tid__after:			after = sub_val_bry; break;
				case Wbase_claim_time_.Tid__precision:		precision = sub_val_bry; break;
				case Wbase_claim_time_.Tid__calendarmodel:	calendarmodel = sub_val_bry; break;
			}
		}
		if (time == null) throw Err_.new_wo_type("pid is invalid time", "pid", pid);
		return new Wbase_claim_time(pid, snak_tid, time, timezone, before, after, precision, calendarmodel);
	}
}
