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
package gplx;
import gplx.core.strings.*;
public class Time_span implements CompareAble {	// NOTE: gplx.Time_span b/c System.TimeSpan
	public long Fracs() {return fracs;} long fracs; public int FracsAsInt() {return (int)fracs;}
	public Decimal_adp Total_days()		{return Decimal_adp_.divide_(fracs, Time_span_.Divisors[Time_span_.Idx_Hour]  * 24);}
	public Decimal_adp Total_hours()	{return Decimal_adp_.divide_(fracs, Time_span_.Divisors[Time_span_.Idx_Hour]);}
	public Decimal_adp Total_mins()		{return Decimal_adp_.divide_(fracs, Time_span_.Divisors[Time_span_.Idx_Min]);}
	public Decimal_adp Total_secs()		{return Decimal_adp_.divide_(fracs, Time_span_.Divisors[Time_span_.Idx_Sec]);}
	public int[] Units() {return Time_span_.Split_long(fracs, Time_span_.Divisors);}
	public int Units_fracs() {
		int[] ary = Time_span_.Split_long(fracs, Time_span_.Divisors);
		return ary[Time_span_.Idx_Frac];
	}
	public Time_span Add(Time_span val)			{return new Time_span(fracs + val.fracs);}
	public Time_span Add_fracs(long val)			{return new Time_span(fracs + val);}
	public Time_span Add_unit(int idx, int val) {
		int[] units = Time_span_.Split_long(fracs, Time_span_.Divisors);
		units[idx] += val;
		int sign = fracs >= 0 ? 1 : -1;
		long rv = sign * Time_span_.Merge_long(units, Time_span_.Divisors);
		return Time_span_.fracs_(rv);
	}
	public Time_span Subtract(Time_span val)	{return new Time_span(fracs - val.fracs);}

	public int compareTo(Object obj)				{Time_span comp = Time_span_.cast(obj); return CompareAble_.Compare_obj(fracs, comp.fracs);}
	public boolean Eq(Object o) {
		Time_span comp = Time_span_.cast(o); if (comp == null) return false;
		return fracs == comp.fracs;
	}
	@Override public String toString()				{return To_str(Time_span_.Fmt_Default);}
	@Override public boolean equals(Object obj)			{Time_span comp = Time_span_.cast(obj); return Object_.Eq(fracs, comp.fracs);}
	@Override public int hashCode() {return super.hashCode();}

	public String To_str()	{return Time_span_.To_str(fracs, Time_span_.Fmt_Default);}
	public String To_str(String format)	{
		return Time_span_.To_str(fracs, format);
	}
	public String XtoStrUiAbbrv()	{
		if (fracs == 0) return "0" + UnitAbbrv(0);
		int[] units = Units();
		boolean started = false;
		String_bldr sb = String_bldr_.new_();
		for (int i = units.length - 1; i > -1; i--) {
			int unit = units[i];
			if (!started) {
				if (unit == 0)
					continue;
				else
					started = true;
			}
			if (sb.Count() != 0) sb.Add(" ");
			sb.Add_obj(unit).Add(UnitAbbrv(i));
		}
		return sb.To_str();
	}
	String UnitAbbrv(int i) {
		switch (i) {
			case 0: return "f";
			case 1: return "s";
			case 2: return "m";
			case 3: return "h";
			default: return "unknown:<" + Int_.To_str(i) + ">";
		}
	}
	@gplx.Internal protected Time_span(long fracs) {this.fracs = fracs;}
}
