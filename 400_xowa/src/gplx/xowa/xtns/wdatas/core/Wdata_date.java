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
public class Wdata_date {
	public Wdata_date(long year, int month, int day, int hour, int minute, int second) {this.year = year; this.month = month; this.day = day; this.hour = hour; this.minute = minute; this.second = second;}
	public long Year() {return year;} private final long year;
	public int Month() {return month;} private final int month;
	public int Day() {return day;} private final int day;
	public int Hour() {return hour;} private final int hour;
	public int Minute() {return minute;} private final int minute;
	public int Second() {return second;} private final int second;
	public static Wdata_date parse(byte[] bry) {// EX:+00000002001-02-03T04:05:06Z
		int year_sign = 1;
		switch (bry[0]) {
			case Byte_ascii.Plus:	break;
			case Byte_ascii.Dash:	year_sign = -1; break;
			default:				throw Err_.unhandled(bry[0]);
		}
		int year_end = Bry_finder.Find_fwd(bry, Byte_ascii.Dash, 1);
		long year	= Long_.parse_or_(String_.new_ascii_(bry, 1, year_end), -1); if (year == -1) throw Err_.new_("parse failed; raw={0}", String_.new_ascii_(bry));
		int month	= Bry_.Xto_int_or(bry, year_end +  1, year_end +  3, -1);
		int day		= Bry_.Xto_int_or(bry, year_end +  4, year_end +  6, -1);
		int hour	= Bry_.Xto_int_or(bry, year_end +  7, year_end +  9, -1);
		int minute	= Bry_.Xto_int_or(bry, year_end + 10, year_end + 12, -1);
		int second	= Bry_.Xto_int_or(bry, year_end + 13, year_end + 15, -1);
		return new Wdata_date(year * year_sign, month, day, hour, minute, second);
	}
}
