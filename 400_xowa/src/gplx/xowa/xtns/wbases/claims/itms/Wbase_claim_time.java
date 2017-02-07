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
import gplx.core.brys.fmtrs.*;
import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.times.*; import gplx.xowa.xtns.wbases.hwtrs.*;
public class Wbase_claim_time extends Wbase_claim_base {
	public Wbase_claim_time(int pid, byte snak_tid, byte[] time, byte[] timezone, byte[] before, byte[] after, byte[] precision, byte[] calendar) {super(pid, snak_tid);
		this.time = time; this.before = before; this.after = after; this.precision = precision; this.calendar = calendar;
	}
	@Override public byte	Val_tid() {return Wbase_claim_type_.Tid__time;}
	public byte[]			Time() {return time;} private final    byte[] time;
	public byte[]			Before() {return before;} private final    byte[] before;
	public byte[]			After() {return after;} private final    byte[] after;
	public byte[]			Precision() {return precision;} private final    byte[] precision;
	public byte[]			Calendar() {return calendar;} private final    byte[] calendar;
	public byte[]			Calendar_ttl() {return calendar_ttl;} private byte[] calendar_ttl;
	public boolean			Calendar_is_julian() {return Bry_.Eq(calendar, Calendar_julian);}

	public void Calendar_ttl_(byte[] v) {calendar_ttl = v;} 

	public Wbase_date Time_as_date() {
		if (time_as_date == null) time_as_date = Wbase_date_.Parse(time, this.Precision_int(), this.Before_int(), this.After_int(), this.Calendar_is_julian());
		return time_as_date;
	}	private Wbase_date time_as_date;
	public int Precision_int() {
		if (precision_int == Int_.Min_value) {
			precision_int = Bry_.To_int_or(precision, -1);
			if (precision_int == -1) {
				precision_int = Wbase_date.Fmt_ymdhns;
				Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown precision: ~{0}", String_.new_u8(precision));
			}
		}
		return precision_int;
	}	private int precision_int = Int_.Min_value;
	public int Before_int() {
		if (before_int == Int_.Min_value) {
			before_int = Bry_.To_int_or(before, -1);
			if (before_int == -1) {
				before_int = 0;
				Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown before: ~{0}", String_.new_u8(before));
			}
		}
		return before_int;
	}	private int before_int = Int_.Min_value;
	public int After_int() {
		if (after_int == Int_.Min_value) {
			after_int = Bry_.To_int_or(after, -1);
			if (after_int == -1) {
				after_int = 0;
				Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown after: ~{0}", String_.new_u8(after));
			}
		}
		return after_int;
	}	private int after_int = Int_.Min_value;

	public void Write_to_bfr(Bry_bfr bfr, Bry_bfr tmp_time_bfr, Bry_fmtr tmp_time_fmtr, Wdata_hwtr_msgs msgs, byte[] ttl) {
		try {
			Wbase_date date = this.Time_as_date();
			Wbase_date_.To_bfr(bfr, tmp_time_fmtr, tmp_time_bfr, msgs, date);
		} catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to write time; ttl=~{0} pid=~{1} err=~{2}", ttl, this.Pid(), Err_.Message_gplx_log(e));
		}
	}
	public static void Write_to_bfr(Bry_bfr bfr, Bry_bfr tmp_time_bfr, Bry_fmtr tmp_time_fmtr, Wdata_hwtr_msgs msgs
		, byte[] ttl, byte[] pid, Wbase_date date, boolean calendar_is_julian) {
		try {
			Wbase_date_.To_bfr(bfr, tmp_time_fmtr, tmp_time_bfr, msgs, date);
		} catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to write time; ttl=~{0} pid=~{1} err=~{2}", ttl, pid, Err_.Message_gplx_log(e));
		}
	}
	@Override public void Welcome(Wbase_claim_visitor visitor) {visitor.Visit_time(this);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wbase_claim_value_type_.Reg.Get_str_or_fail(this.Snak_tid()), Wbase_claim_type_.Reg.Get_str_or_fail(this.Val_tid()), String_.new_u8(time), String_.new_u8(before), String_.new_u8(after), String_.new_u8(precision), String_.new_u8(calendar));
	}
	public static final    byte[] Calendar_julian = Bry_.new_a7("http://www.wikidata.org/entity/Q1985786");
}
