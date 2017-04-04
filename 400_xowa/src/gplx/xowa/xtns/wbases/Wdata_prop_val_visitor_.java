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

		// loop datavalue_ary to get (a) tid,; (b) val_obj
		byte tid = Byte_.Max_value_127;
		Object val_obj = null; 
		len = datavalue_ary.length;
		for (int i = 0; i < len; ++i) {
			Keyval datavalue_itm = datavalue_ary[i];
			byte datavalue_tid = Wdata_dict_datavalue.Reg.Get_tid_or_max_and_log(page_url, datavalue_itm.Key()); if (datavalue_tid == Byte_.Max_value_127) continue;
			switch (datavalue_tid) {
				case Wdata_dict_datavalue.Tid__type:		tid = Wbase_claim_type_.Get_tid_or_unknown((String)datavalue_itm.Val()); break;
				case Wdata_dict_datavalue.Tid__value:		val_obj = datavalue_itm.Val(); break;
				case Wdata_dict_datavalue.Tid__error:		break;	// ignore: "Can only construct GlobeCoordinateValue with a String globe parameter"
			}					
		}

		// write claim
		switch (tid) {
			case Wbase_claim_type_.Tid__entity:				Write_entity			(bfr, wdata_mgr, lang, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__string:				Wdata_prop_val_visitor.Write_str(bfr, To_bry_by_str(val_obj)); break;
			case Wbase_claim_type_.Tid__time:				Write_time				(bfr, wdata_mgr, wiki, page_url, pid, snak_tid, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__globecoordinate:	Write_geo				(bfr, wdata_mgr, lang, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__quantity:			Write_quantity			(bfr, wdata_mgr, lang, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__monolingualtext:	Write_langtext			(bfr, page_url, (Keyval[])val_obj); break;
			default:										throw Err_.new_unhandled_default(tid);
		}
		lang.Comma_wkr().Comma__itm(bfr, sub_idx, sub_len);
	}
	private static void Write_entity(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] page_url, Keyval[] kvs) {
		byte entity_tid = Byte_.Max_value_127;
		byte[] entity_id = null;

		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte tid = Wbase_claim_entity_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (tid == Byte_.Max_value_127) continue;
			switch (tid) {
				case Wbase_claim_entity_.Tid__entity_type:		entity_tid = Wbase_claim_entity_type_.Reg.Get_tid_or_fail(kv.Val_to_bry()); break;
				case Wbase_claim_entity_.Tid__numeric_id:		entity_id = kv.Val_to_bry(); break;
				case Wbase_claim_entity_.Tid__id:				break;	// ignore
			}
		}
		entity_id = Wbase_claim_entity.To_xid__db(entity_tid, entity_id);	// get p/q and number; PAGE:en.v:Mongolia; EX: [numeric-id=6498663, entity-type=item]; DATE:2016-10-18
		Wdata_prop_val_visitor.Write_entity(bfr, wdata_mgr, lang.Key_bry(), entity_id, Bool_.N);
	}
	private static void Write_time(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, Xowe_wiki wiki, byte[] page_url, int pid, byte snak_tid, Keyval[] kvs) {
		byte[] time = null, calendar = null;
		int precision = 0, before = 0, after = 0;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_time_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			switch (val_tid) {
				case Wbase_claim_time_.Tid__time:			time				= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_time_.Tid__before:			before				= Int_.cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__after:			after				= Int_.cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__precision:		precision			= Int_.cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__calendarmodel:	calendar			= To_bry_by_str(kv.Val()); break;
				case Wbase_claim_time_.Tid__timezone:		break;
			}
		}

		Wbase_date date = Wbase_date_.Parse(time, precision, before, after, Bry_.Eq(calendar, Wbase_claim_time.Calendar_julian));
		Xow_parser_mgr parser_mgr = wiki.Parser_mgr();
		Wdata_prop_val_visitor.Write_time(bfr, parser_mgr.Wbase__time__bfr(), parser_mgr.Wbase__time__fmtr(), parser_mgr.Wbase__time__msgs(), page_url, pid, date);
	}
	private static void Write_quantity(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] page_url, Keyval[] kvs) {
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
		Wdata_prop_val_visitor.Write_quantity(bfr, wdata_mgr, lang, amount, lbound, ubound, unit);
	}
	private static void Write_geo(Bry_bfr bfr, Wdata_wiki_mgr wdata_mgr, Xol_lang_itm lang, byte[] page_url, Keyval[] kvs) {
		byte[] lat = null, lng = null, alt = null, prc = null, glb = null;
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
		Wdata_prop_val_visitor.Write_geo(Bool_.Y, bfr, wdata_mgr.Hwtr_mgr().Lbl_mgr(), wdata_mgr.Hwtr_mgr().Msgs(), lat, lng, alt, prc, glb);
	}
	private static void Write_langtext(Bry_bfr bfr, byte[] page_url, Keyval[] kvs) {
		byte[] text = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_monolingualtext_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			switch (val_tid) {
				case Wbase_claim_monolingualtext_.Tid__text:		text = To_bry_by_str(kv.Val()); break;
				case Wbase_claim_monolingualtext_.Tid__language:	break;
			}
		}
		Wdata_prop_val_visitor.Write_langtext(bfr, text);
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
}
