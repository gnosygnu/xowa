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
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		try {
			for (int i = 0; i < len; ++i) {
				Keyval[] itm = (Keyval[])snaks[i].Val();
				Render_snak(bfr, wiki, wiki.Lang(), page_url, itm, i, len);
			}
			wiki.Lang().Comma_wkr().Comma__end(bfr);
			rv = bfr.To_str_and_clear();
		} finally {bfr.Mkr_rls();}
		return rv;
	}
	public static String Render_snak(Xowe_wiki wiki, byte[] page_url, Keyval[] props) {
		String rv = null;
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		try {
			Render_snak(bfr, wiki, wiki.Lang(), page_url, props, 0, 1);
			rv = bfr.To_str_and_clear();
		} finally {bfr.Mkr_rls();}
		return rv;
	}
	private static void Render_snak(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, byte[] page_url, Keyval[] props, int sub_idx, int sub_len) {
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

		// render val_obj based on tid
		switch (tid) {
			case Wbase_claim_type_.Tid__entity:				Render__entity		(bfr, wiki, lang, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__string:				bfr.Add_str_u8((String)val_obj); break;
			case Wbase_claim_type_.Tid__time:				Render__time		(bfr, wiki, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__globecoordinate:	Render__geo			(bfr, lang, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__quantity:			Render__quantity	(bfr, wiki, lang, page_url, pid, snak_tid, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__monolingualtext:	Render__langtext	(bfr, lang, (Keyval[])val_obj); break;
		}
		lang.Comma_wkr().Comma__itm(bfr, sub_idx, sub_len);
	}
	private static void Render__entity(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, byte[] page_url, Keyval[] kvs) {
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

		// convert p/q, number to xid; "p123", "q123"
		if (entity_id_bry == null) throw Err_.new_wo_type("xid is invalid entity", "xid", page_url);
		byte xid_pre = entity_tid == Wbase_claim_entity_type_.Tid__item ? Byte_ascii.Ltr_Q : Byte_ascii.Ltr_P;
		entity_id_bry = Bry_.Add(xid_pre, entity_id_bry);

		// get doc
		Wdata_doc wdoc = wiki.Appe().Wiki_mgr().Wdata_mgr().Doc_mgr.Get_by_xid_or_null(entity_id_bry); // NOTE: by_xid b/c Module passes just "p1" not "Property:P1"
		if (wdoc == null) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "qid not found in wikidata for renderSnak; page=~{0} qid=~{1}", page_url, entity_id_bry);
			return;
		}

		// add label
		bfr.Add(wdoc.Label_list__get(lang.Key_bry()));
	}
	private static void Render__time(Bry_bfr bfr, Xowe_wiki wiki, byte[] page_url, Keyval[] kvs) {
		Wbase_date date = null;
		byte[] time = null;
		int precision_int = 0, before_int = 0, after_int = 0;
		boolean calendar_is_julian = true;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_time_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			switch (val_tid) {
				case Wbase_claim_time_.Tid__time:			time				= Bry_.new_u8((String)kv.Val()); break;
				case Wbase_claim_time_.Tid__before:			before_int			= Int_.cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__after:			after_int			= Int_.cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__precision:		precision_int		= Int_.cast(kv.Val()); break;
				case Wbase_claim_time_.Tid__calendarmodel:	calendar_is_julian	= Bry_.Eq(Bry_.new_u8((String)kv.Val()), Wbase_claim_time.Calendar_julian); break;
				case Wbase_claim_time_.Tid__timezone:		if (!String_.Eq((String)kv.Val(), "0")) throw Err_.new_unimplemented(); break;
			}
		}
		Xow_parser_mgr parser_mgr = wiki.Parser_mgr();
		date = Wbase_date.Parse(time, precision_int, before_int, after_int, calendar_is_julian);
		Wbase_claim_time.Write_to_bfr(bfr, parser_mgr.Wbase__time__bfr(), parser_mgr.Wbase__time__fmtr()
			, parser_mgr.Wbase__time__msgs(), page_url, Bry_.Empty, date, calendar_is_julian
			);
	}
	private static void Render__quantity(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, byte[] page_url, int pid, byte snak_tid, Keyval[] kvs) {
		byte[] amount_bry = null, lbound_bry = null, ubound_bry = null, unit_bry = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_quantity_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			byte[] val_bry = Bry_.new_u8((String)kv.Val());
			switch (val_tid) {
				case Wbase_claim_quantity_.Tid__amount:		amount_bry	= val_bry; break;
				case Wbase_claim_quantity_.Tid__unit:		unit_bry	= val_bry; break;
				case Wbase_claim_quantity_.Tid__lowerbound:	lbound_bry	= val_bry; break;
				case Wbase_claim_quantity_.Tid__upperbound:	ubound_bry	= val_bry; break;
			}
		}
		Wbase_claim_quantity quantity = new Wbase_claim_quantity(pid, snak_tid, amount_bry, unit_bry, ubound_bry, lbound_bry);
		wiki.Appe().Wiki_mgr().Wdata_mgr().Resolve_claim(bfr, wiki.Domain_itm(), quantity);
	}
//		private static void Render__quantity(Bry_bfr bfr, Xol_lang_itm lang, byte[] amount_bry, byte[] lbound_bry, byte[] ubound_bry, byte[] unit_bry) {
//			long val = Bry_.To_long_or(amount_bry, Byte_ascii.Comma_bry, 0, amount_bry.length, 0);	// NOTE: must cast to long for large numbers; EX:{{#property:P1082}} PAGE:en.w:Earth; DATE:2015-08-02
//			bfr.Add(lang.Num_mgr().Format_num_by_long(val));// amount; EX: 1,234
//			long lbound = Bry_.To_long_or(lbound_bry, val);
//			long ubound = Bry_.To_long_or(lbound_bry, val);
//			if (lbound != val && ubound != val) {	// NOTE: do not output � if lbound == val == ubound; PAGE:en.w:Tintinan DATE:2015-08-02
//				bfr.Add(Wdata_prop_val_visitor.Bry__quantity_margin_of_error);	// symbol: EX: �
//				bfr.Add(unit_bry); // unit; EX: 1
//			}
//		}
	private static void Render__geo(Bry_bfr bfr, Xol_lang_itm lang, byte[] page_url, Keyval[] kvs) {
		double lat = 0, lng = 0;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_globecoordinate_.Reg.Get_tid_or_max_and_log(page_url, kv.Key()); if (val_tid == Byte_.Max_value_127) continue;
			switch (val_tid) {
				case Wbase_claim_globecoordinate_.Tid__latitude:		lat = Double_.cast(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__longitude:		lng = Double_.cast(kv.Val()); break;
			}
		}
		Render__geo(bfr, lat, lng);
	}
	public static void Render__geo(Bry_bfr bfr, byte[] lat, byte[] lng) {
		bfr.Add(lat);
		bfr.Add(Bry__geo_dlm);
		bfr.Add(lng);
	}
	public static void Render__geo(Bry_bfr bfr, double lat, double lng) {
		bfr.Add_double(lat);
		bfr.Add(Bry__geo_dlm);
		bfr.Add_double(lng);
	}
	private static void Render__langtext(Bry_bfr bfr, Xol_lang_itm lang, Keyval[] kvs) {
		bfr.Add_str_u8((String)Scrib_kv_utl_.Get_sub_by_key_or_null(kvs, Wbase_claim_monolingualtext_.Itm__text.Key_str()));
	}
	private static int To_pid_int(byte[] pid) {return Bry_.To_int_or(pid, 1, pid.length, -1);}	// skip "P" at bgn; EX: "p123" -> 123
	private static final    byte[] Bry__geo_dlm = Bry_.new_a7(", ");
}
