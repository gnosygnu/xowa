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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
public class Wdata_dict_value_time {
	public static final byte
	  Tid_time									= 0
	, Tid_timezone								= 1
	, Tid_before								= 2
	, Tid_after									= 3
	, Tid_precision								= 4
	, Tid_calendarmodel							= 5
	;
	public static final String
	  Str_time									= "time"
	, Str_timezone								= "timezone"
	, Str_before								= "before"
	, Str_after									= "after"
	, Str_precision								= "precision"
	, Str_calendarmodel							= "calendarmodel"
	;
	public static byte[] 
	  Bry_time									= Bry_.new_a7(Str_time)
	, Bry_timezone								= Bry_.new_a7(Str_timezone)
	, Bry_before								= Bry_.new_a7(Str_before)
	, Bry_after									= Bry_.new_a7(Str_after)
	, Bry_precision								= Bry_.new_a7(Str_precision)
	, Bry_calendarmodel							= Bry_.new_a7(Str_calendarmodel)
	;
	public static final Hash_adp_bry Dict = Hash_adp_bry.cs()
	.Add_bry_byte(Bry_time						, Tid_time)
	.Add_bry_byte(Bry_timezone					, Tid_timezone)
	.Add_bry_byte(Bry_before					, Tid_before)
	.Add_bry_byte(Bry_after						, Tid_after)
	.Add_bry_byte(Bry_precision					, Tid_precision)
	.Add_bry_byte(Bry_calendarmodel				, Tid_calendarmodel)
	;
	public static final int 
	  Val_precision_int							= 11
	, Val_before_int							= 0
	, Val_after_int								= 0
	, Val_timezone_int							= 0
	;
	public static final String
	  Val_precision_str							= "11"
	, Val_before_str							= "0"
	, Val_after_str								= "0"
	, Val_timezone_str							= "0"
	, Val_calendarmodel_str						= "http://www.wikidata.org/entity/Q1985727"
	;
	public static final byte[]
	  Val_precision_bry							= Bry_.new_a7(Val_precision_str)
	, Val_before_bry							= Bry_.new_a7(Val_before_str)
	, Val_after_bry								= Bry_.new_a7(Val_after_str)
	, Val_timezone_bry							= Bry_.new_a7(Val_timezone_str)
	, Val_calendarmodel_bry						= Bry_.new_a7(Val_calendarmodel_str)
	;
	public static byte[] Xto_time(String date) {return Xto_time(DateAdp_.parse_fmt(date, "yyyy-MM-dd HH:mm:ss"));}
	public static byte[] Xto_time(DateAdp date) {
		// +0000000yyyy-MM-ddTHH:mm:ssZ
		tmp_bfr
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
		return tmp_bfr.To_bry_and_clear();
	}
	private static Bry_bfr tmp_bfr = Bry_bfr.new_(); private static byte[] Bry_year_prefix = Bry_.new_a7("+0000000");
}
