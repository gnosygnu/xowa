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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.times.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.wbases.hwtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.commas.*;
import gplx.xowa.parsers.*;
public class Wdata_prop_val_visitor_ {
	public static String Render_snaks(Xowe_wiki wiki, byte[] page_url, Keyval[] snaks) {
		String rv = null;
		int len = snaks.length;
		Wdata_wiki_mgr wdata_mgr = wiki.Appe().Wiki_mgr().Wdata_mgr();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		try {
			for (int i = 0; i < len; ++i) {
				Keyval[] itm = (Keyval[])snaks[i].Val();
				Render_snak(bfr, wdata_mgr, wiki, wiki.Lang(), page_url, itm, i, len);
			}
			wiki.Lang().Comma_wkr().Comma__end(bfr);
			rv = bfr.To_str_and_clear();
		} finally {bfr.Mkr_rls();}
		return rv;
	}
	public static String Render_snak(Wdata_wiki_mgr wdata_mgr, Xowe_wiki wiki, byte[] page_url, Keyval[] props) {
		String rv = null;
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		try {
			Render_snak(bfr, wdata_mgr, wiki, wiki.Lang(), page_url, props, 0, 1);
			rv = bfr.To_str_and_clear();
		} finally {bfr.Mkr_rls();}
		return rv;
	}
	private static void Render_snak(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, Xowe_wiki wiki, Xol_lang_itm lang, byte[] page_url, Keyval[] props, int sub_idx, int sub_len) {
		// loop props to get (a) snaktype; (b) property; (c) datavalue
		byte snak_tid = Byte_.Max_value_127;
		int pid = -1;
		Keyval[] datavalue_ary = null;
		int len = props.length;
		for (int i = 0; i < len; ++i) {
			Keyval prop = props[i];
			byte prop_tid = Wdata_dict_mainsnak.Reg.Get_tid_or_max_and_log(page_url, prop.Key()); if (prop_tid == Byte_.Max_value_127) continue;
			switch (prop_tid) {
				case Wdata_dict_mainsnak.Tid__snaktype:		snak_tid = Wbase_claim_value_type_.Reg.Get_tid_or_fail(prop.Val_to_bry()); break;
				case Wdata_dict_mainsnak.Tid__datavalue:	datavalue_ary = (Keyval[])prop.Val(); break;
				case Wdata_dict_mainsnak.Tid__datatype:		break;		// ignore: has values like "wikibase-property"; EX: www.wikidata.org/wiki/Property:P397; DATE:2015-06-12
				case Wdata_dict_mainsnak.Tid__property:		pid = To_pid_int(prop.Val_to_bry()); break;
				case Wdata_dict_mainsnak.Tid__hash:			break;		// ignore: "84487fc3f93b4f74ab1cc5a47d78f596f0b49390"
			}
		}

		// loop datavalue_ary to get tid, val_obj
		byte tid = Byte_.Max_value_127;
		Object val_obj = null; 
		len = datavalue_ary.length;
		for (int i = 0; i < len; ++i) {
			Keyval datavalue_itm = datavalue_ary[i];
			String key = datavalue_itm.Key();
			if		(String_.Eq(key, Wdata_dict_datavalue.Itm__type.Key_str()))
				tid = Wbase_claim_type_.Get_tid_or_unknown((String)datavalue_itm.Val());
			else if (String_.Eq(key, Wdata_dict_datavalue.Itm__value.Key_str()))
				val_obj = datavalue_itm.Val();
		}

		// get claim and render
		// NOTE: converting to claim before writing; used to write directly; less efficient, but more consistent; DATE:2016-10-20
		Wbase_claim_base claim = null;
		switch (tid) {
			case Wbase_claim_type_.Tid__entity:				claim = Make__entity	(page_url, pid, snak_tid, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__string:				claim = new Wbase_claim_string(pid, snak_tid, To_bry_by_str(val_obj)); break;
			case Wbase_claim_type_.Tid__time:				claim = Make__time		(page_url, pid, snak_tid, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__globecoordinate:	claim = Make__geo		(page_url, pid, snak_tid, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__quantity:			claim = Make__quantity	(page_url, pid, snak_tid, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__monolingualtext:	claim = Make__langtext	(page_url, pid, snak_tid, (Keyval[])val_obj); break;
			default:										throw Err_.new_unhandled_default(tid);
		}
		wdata_mgr.Resolve_claim(bfr, wiki.Domain_itm(), claim);
		lang.Comma_wkr().Comma__itm(bfr, sub_idx, sub_len);
	}
	private static Wbase_claim_entity Make__entity(byte[] page_url, int pid, byte snak_tid, Keyval[] kvs) {
		byte entity_tid = Byte_.Max_value_127;
		byte[] entity_id_bry = null;

		// get p/q and number; PAGE:en.v:Mongolia; EX: [numeric-id=6498663, entity-type=item]; DATE:2016-10-18
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte tid = Wbase_claim_entity_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (tid == Byte_.Max_value_127) continue;
			switch (tid) {
				case Wbase_claim_entity_.Tid__entity_type:		entity_tid = Wbase_claim_entity_type_.Reg.Get_tid_or_fail(kv.Val_to_bry()); break;
				case Wbase_claim_entity_.Tid__numeric_id:		entity_id_bry = kv.Val_to_bry(); break;
				case Wbase_claim_entity_.Tid__id:				break;	// ignore
			}
		}
		return new Wbase_claim_entity(pid, snak_tid, entity_tid, entity_id_bry);
	}
	private static Wbase_claim_time Make__time(byte[] page_url, int pid, byte snak_tid, Keyval[] kvs) {
		byte[] time = null, timezone = null;
		byte[] precision = null, before = null, after = null;
		byte[] calendar = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_time_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			switch (val_tid) {
				case Wbase_claim_time_.Tid__time:			time				= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_time_.Tid__before:			before				= To_bry_by_int(kv.Val()); break;
				case Wbase_claim_time_.Tid__after:			after				= To_bry_by_int(kv.Val()); break;
				case Wbase_claim_time_.Tid__precision:		precision			= To_bry_by_int(kv.Val()); break;
				case Wbase_claim_time_.Tid__calendarmodel:	calendar			= To_bry_by_str(kv.Val()); break;	// Bry_.Eq(Bry_.new_u8((String)kv.Val()), Wbase_claim_time.Calendar_julian); 
				case Wbase_claim_time_.Tid__timezone:		timezone			= To_bry_by_str(kv.Val()); break;	// if (!String_.Eq((String)kv.Val(), "0")) throw Err_.new_unimplemented(); 
			}
		}
//			Xow_parser_mgr parser_mgr = wiki.Parser_mgr();
//			date = Wbase_date.Parse(time, precision_int, before_int, after_int, calendar_is_julian);
//			Wbase_claim_time.Write_to_bfr(bfr, parser_mgr.Wbase__time__bfr(), parser_mgr.Wbase__time__fmtr()
//				, parser_mgr.Wbase__time__msgs(), page_url, Bry_.Empty, date, calendar_is_julian
//				);
		return new Wbase_claim_time(pid, snak_tid, time, timezone, before, after, precision, calendar);
	}
	private static Wbase_claim_quantity Make__quantity(byte[] page_url, int pid, byte snak_tid, Keyval[] kvs) {
		byte[] amount = null, lbound = null, ubound = null, unit = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_quantity_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			switch (val_tid) {
				case Wbase_claim_quantity_.Tid__amount:		amount	= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_quantity_.Tid__unit:		unit	= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_quantity_.Tid__lowerbound:	lbound	= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_quantity_.Tid__upperbound:	ubound	= To_bry_by_str(kv.Val()); break;
			}
		}
		return new Wbase_claim_quantity(pid, snak_tid, amount, unit, ubound, lbound);
	}
	private static Wbase_claim_globecoordinate Make__geo(byte[] page_url, int pid, byte snak_tid, Keyval[] kvs) {
		byte[] lat = null, lng = null, prc = null,  alt = null, glb = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_globecoordinate_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			switch (val_tid) {
				case Wbase_claim_globecoordinate_.Tid__latitude:		lat = To_bry_by_double(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__longitude:		lng = To_bry_by_double(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__altitude:		alt = To_bry_by_str(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__precision:		prc = To_bry_by_double(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__globe:			glb = To_bry_by_str(kv.Val()); break;
			}
		}
		return new Wbase_claim_globecoordinate(pid, snak_tid, lat, lng, alt, prc, glb);
	}
	private static Wbase_claim_monolingualtext Make__langtext(byte[] page_url, int pid, byte snak_tid, Keyval[] kvs) {
		byte[] lang = null, text = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_monolingualtext_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			switch (val_tid) {
				case Wbase_claim_monolingualtext_.Tid__language:	lang = To_bry_by_str(kv.Val()); break;
				case Wbase_claim_monolingualtext_.Tid__text:		text = To_bry_by_str(kv.Val()); break;
			}
		}
		return new Wbase_claim_monolingualtext(pid, snak_tid, lang, text);
	}
	private static int To_pid_int(byte[] pid) {return Bry_.To_int_or(pid, 1, pid.length, -1);}	// skip "P" at bgn; EX: "p123" -> 123
	private static byte[] To_bry_by_str(Object o) {
		String rv = String_.cast(o);
		return rv == null ? null : Bry_.new_u8(rv);
	}
	private static byte[] To_bry_by_double(Object o) {
		double rv = Double_.cast(o);
		return Bry_.new_a7(Double_.To_str(rv));
	}
	private static byte[] To_bry_by_int(Object o) {
		int rv = Int_.cast(o);
		return Int_.To_bry(rv);
	}
}
