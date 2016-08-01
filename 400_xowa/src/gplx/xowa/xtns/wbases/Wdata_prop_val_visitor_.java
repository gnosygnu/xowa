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
		Keyval[] datavalue_ary = (Keyval[])Scrib_kv_utl_.Get_sub_by_key_or_null(props, Wdata_dict_mainsnak.Str_datavalue);

		// loop datavalue_ary to get tid, val_obj
		byte tid = Byte_.Max_value_127;
		Object val_obj = null; 
		int len = datavalue_ary.length;
		for (int i = 0; i < len; ++i) {
			String key = datavalue_ary[i].Key();
			if		(String_.Eq(key, Wdata_dict_datavalue.Str_type))
				tid = Wbase_claim_type_.To_tid_or_unknown((String)datavalue_ary[i].Val());
			else if (String_.Eq(key, Wdata_dict_datavalue.Str_value))
				val_obj = datavalue_ary[i].Val();
		}

		// render val_obj based on tid
		switch (tid) {
			case Wbase_claim_type_.Tid__entity:				throw Err_.new_unimplemented();
			case Wbase_claim_type_.Tid__string:				bfr.Add_str_u8((String)val_obj); break;
			case Wbase_claim_type_.Tid__time:				Render__time		(bfr, wiki, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__globecoordinate:	Render__geo			(bfr, lang, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__quantity:			Render__quantity	(bfr, lang, page_url, (Keyval[])val_obj); break;
			case Wbase_claim_type_.Tid__monolingualtext:	Render__langtext	(bfr, lang, (Keyval[])val_obj); break;
		}
		lang.Comma_wkr().Comma__itm(bfr, sub_idx, sub_len);
	}
	private static void Render__time(Bry_bfr bfr, Xowe_wiki wiki, byte[] page_url, Keyval[] kvs) {
		Wbase_date date = null;
		byte[] time = null;
		int precision_int = 0, before_int = 0, after_int = 0;
		boolean calendar_is_julian = true;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_time_.To_tid_or_invalid(page_url, kv.Key());
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
	private static void Render__quantity(Bry_bfr bfr, Xol_lang_itm lang, byte[] page_url, Keyval[] kvs) {
		byte[] amount_bry = null, lbound_bry = null, ubound_bry = null, unit_bry = null;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_quantity_.To_tid_or_invalid(page_url, kv.Key());
			byte[] val_bry = Bry_.new_u8((String)kv.Val());
			switch (val_tid) {
				case Wbase_claim_quantity_.Tid__amount:		amount_bry	= val_bry; break;
				case Wbase_claim_quantity_.Tid__unit:		unit_bry	= val_bry; break;
				case Wbase_claim_quantity_.Tid__lowerbound:	lbound_bry	= val_bry; break;
				case Wbase_claim_quantity_.Tid__upperbound:	ubound_bry	= val_bry; break;
			}
		}
		Render__quantity(bfr, lang, amount_bry, lbound_bry, ubound_bry, unit_bry);
	}
	private static void Render__quantity(Bry_bfr bfr, Xol_lang_itm lang, byte[] amount_bry, byte[] lbound_bry, byte[] ubound_bry, byte[] unit_bry) {
		long val = Bry_.To_long_or(amount_bry, Byte_ascii.Comma_bry, 0, amount_bry.length, 0);	// NOTE: must cast to long for large numbers; EX:{{#property:P1082}} PAGE:en.w:Earth; DATE:2015-08-02
		bfr.Add(lang.Num_mgr().Format_num_by_long(val));// amount; EX: 1,234
		long lbound = Bry_.To_long_or(lbound_bry, val);
		long ubound = Bry_.To_long_or(lbound_bry, val);
		if (lbound != val && ubound != val) {	// NOTE: do not output � if lbound == val == ubound; PAGE:en.w:Tintinan DATE:2015-08-02
			bfr.Add(Wdata_prop_val_visitor.Bry__quantity_margin_of_error);	// symbol: EX: �
			bfr.Add(unit_bry); // unit; EX: 1
		}
	}
	private static void Render__geo(Bry_bfr bfr, Xol_lang_itm lang, byte[] page_url, Keyval[] kvs) {
		double lat = 0, lng = 0;
		int len = kvs.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kvs[i];
			byte val_tid = Wbase_claim_globecoordinate_.To_tid_or_invalid(page_url, kv.Key());
			switch (val_tid) {
				case Wbase_claim_globecoordinate_.Tid__latitude:		lat = Double_.cast(kv.Val()); break;
				case Wbase_claim_globecoordinate_.Tid__longitude:		lng = Double_.cast(kv.Val()); break;
				default: break;	// ignore others
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
	private static final    byte[] Bry__geo_dlm = Bry_.new_a7(", ");
}
