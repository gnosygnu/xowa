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
package gplx.xowa.xtns.wbases.claims.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.times.*;
public class Wbase_claim_time_ {
	public static final byte
	  Tid__time									= 0
	, Tid__timezone								= 1
	, Tid__before								= 2
	, Tid__after								= 3
	, Tid__precision							= 4
	, Tid__calendarmodel						= 5
	;
	private static final int Ary__len = 6;
	private static final    Wbase_claim_enum[] Ary = new Wbase_claim_enum[Ary__len];
	private static final    Hash_adp_bry hash_by_bry = Hash_adp_bry.cs();
	private static final    Hash_adp hash_by_str = Hash_adp_.New();
	public static final    Wbase_claim_enum
	  Itm__time						= New(Tid__time				, "time")
	, Itm__timezone					= New(Tid__timezone			, "timezone")
	, Itm__before					= New(Tid__before			, "before")
	, Itm__after					= New(Tid__after			, "after")
	, Itm__precision				= New(Tid__precision		, "precision")
	, Itm__calendarmodel			= New(Tid__calendarmodel	, "calendarmodel")
	;
	private static Wbase_claim_enum New(byte tid, String key) {
		Wbase_claim_enum rv = new Wbase_claim_enum(tid, key);
		hash_by_bry.Add(rv.Key_bry(), rv);
		hash_by_str.Add(rv.Key_str(), rv);
		Ary[tid] = rv;
		return rv;
	}

	public static String To_str_or_invalid(byte tid) {return Ary[tid].Key_str();}
	public static byte[] To_bry_or_fail(byte tid) {return Ary[tid].Key_bry();}
	public static byte To_tid_or_invalid(byte[] page_url, String key) {return Wbase_claim_enum_.To_tid_or_invalid(hash_by_str, page_url, key);}
	public static byte To_tid_or_invalid(byte[] page_url, byte[] key) {return Wbase_claim_enum_.To_tid_or_invalid(hash_by_bry, page_url, key);}

	public static final    Wbase_data_itm 
	  Dflt__precision		= Wbase_data_itm.New_int(11)
	, Dflt__before			= Wbase_data_itm.New_int(0)
	, Dflt__after			= Wbase_data_itm.New_int(0)
	, Dflt__timezone		= Wbase_data_itm.New_int(0)
	, Dflt__calendarmodel	= Wbase_data_itm.New_str("http://www.wikidata.org/entity/Q1985727")
	;

	private static final    byte[] Bry_year_prefix = Bry_.new_a7("+0000000");
	public static byte[] To_bry(Bry_bfr bfr,  String date) {return To_bry(bfr, DateAdp_.parse_fmt(date, "yyyy-MM-dd HH:mm:ss"));}
	public static byte[] To_bry(Bry_bfr bfr, DateAdp date) {// +0000000yyyy-MM-ddTHH:mm:ssZ
		bfr
		.Add(Bry_year_prefix)
		.Add_int_fixed(date.Year(), 4)
		.Add_byte(Byte_ascii.Dash)
		.Add_int_fixed(date.Month(), 2)
		.Add_byte(Byte_ascii.Dash)
		.Add_int_fixed(date.Day(), 2)
		.Add_byte(Byte_ascii.Ltr_T)
		.Add_int_fixed(date.Hour(), 2)
		.Add_byte(Byte_ascii.Colon)
		.Add_int_fixed(date.Minute(), 2)
		.Add_byte(Byte_ascii.Colon)
		.Add_int_fixed(date.Second(), 2)
		.Add_byte(Byte_ascii.Ltr_Z)
		;
		return bfr.To_bry_and_clear();
	}
}
