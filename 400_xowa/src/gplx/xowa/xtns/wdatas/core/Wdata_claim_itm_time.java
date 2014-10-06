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
import gplx.xowa.xtns.wdatas.hwtrs.*;
public class Wdata_claim_itm_time extends Wdata_claim_itm_core { 	public Wdata_claim_itm_time(int pid, byte snak_tid, byte[] time, byte[] timezone, byte[] before, byte[] after, byte[] precision, byte[] calendar) {
		this.Ctor(pid, snak_tid);
		this.time = time; this.before = before; this.after = after; this.precision = precision; this.calendar = calendar;
	}
	@Override public byte Val_tid() {return Wdata_dict_val_tid.Tid_time;}
	public byte[] Time() {return time;} private final byte[] time;
	public Wdata_date Time_as_date() {
		if (time_as_date == null) time_as_date = Wdata_date.parse(time);
		return time_as_date;
	} private Wdata_date time_as_date;
	public byte[] Before() {return before;} private final byte[] before;
	public byte[] After() {return after;} private final byte[] after;
	public byte[] Precision() {return precision;} private final byte[] precision;
	public int Precision_int() {
		if (precision_int == Int_.MinValue)
			precision_int = Bry_.Xto_int_or(precision, Wdata_date.Fmt_ymdhns);			
		return precision_int;
	}	private int precision_int = Int_.MinValue;
	public byte[] Calendar() {return calendar;} private final byte[] calendar;
	public byte[] Calendar_ttl() {return calendar_ttl;} public void Calendar_ttl_(byte[] v) {calendar_ttl = v;} private byte[] calendar_ttl;
	public boolean Calendar_is_julian() {return Bry_.Eq(calendar, Calendar_julian);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wdata_dict_snak_tid.Xto_str(this.Snak_tid()), Wdata_dict_val_tid.Xto_str(this.Val_tid()), String_.new_utf8_(time), String_.new_utf8_(before), String_.new_utf8_(after), String_.new_utf8_(precision), String_.new_utf8_(calendar));
	}
	@Override public void Welcome(Wdata_claim_visitor visitor) {visitor.Visit_time(this);}
	private static final byte[] Calendar_julian = Bry_.new_ascii_("http://www.wikidata.org/entity/Q1985786");
}
