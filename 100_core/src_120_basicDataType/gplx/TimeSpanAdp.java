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
package gplx;
public class TimeSpanAdp implements CompareAble, EqAble {
	public long Fracs() {return fracs;} long fracs; public int FracsAsInt() {return (int)fracs;}
	public DecimalAdp TotalSecs() {
		return DecimalAdp_.divide_(fracs, TimeSpanAdp_.Divisors[TimeSpanAdp_.Idx_Sec]);
	}
	public DecimalAdp Total_days() {
		return DecimalAdp_.divide_(fracs, TimeSpanAdp_.Divisors[TimeSpanAdp_.Idx_Hour]  * 24);
	}
	public int[] Units() {return TimeSpanAdp_.Split_long(fracs, TimeSpanAdp_.Divisors);}
	public int Units_fracs() {
		int[] ary = TimeSpanAdp_.Split_long(fracs, TimeSpanAdp_.Divisors);
		return ary[TimeSpanAdp_.Idx_Frac];
	}
	public TimeSpanAdp Add(TimeSpanAdp val)			{return new TimeSpanAdp(fracs + val.fracs);}
	public TimeSpanAdp Add_fracs(long val)			{return new TimeSpanAdp(fracs + val);}
	public TimeSpanAdp Add_unit(int idx, int val) {
		int[] units = TimeSpanAdp_.Split_long(fracs, TimeSpanAdp_.Divisors);
		units[idx] += val;
		int sign = fracs >= 0 ? 1 : -1;
		long rv = sign * TimeSpanAdp_.Merge_long(units, TimeSpanAdp_.Divisors);
		return TimeSpanAdp_.fracs_(rv);
	}
	public TimeSpanAdp Subtract(TimeSpanAdp val)	{return new TimeSpanAdp(fracs - val.fracs);}

	public int compareTo(Object obj)				{TimeSpanAdp comp = TimeSpanAdp_.cast_(obj); return CompareAble_.Compare_obj(fracs, comp.fracs);}
	public boolean Eq(Object o) {
		TimeSpanAdp comp = TimeSpanAdp_.cast_(o); if (comp == null) return false;
		return fracs == comp.fracs;
	}
	@Override public String toString()				{return XtoStr(TimeSpanAdp_.Fmt_Default);}
	@Override public boolean equals(Object obj)			{TimeSpanAdp comp = TimeSpanAdp_.cast_(obj); return Object_.Eq(fracs, comp.fracs);}
	@Override public int hashCode() {return super.hashCode();}

	public String XtoStr()	{return TimeSpanAdp_.XtoStr(fracs, TimeSpanAdp_.Fmt_Default);}
	public String XtoStr(String format)	{
		return TimeSpanAdp_.XtoStr(fracs, format);
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
		return sb.XtoStr();
	}
	String UnitAbbrv(int i) {
		switch (i) {
			case 0: return "f";
			case 1: return "s";
			case 2: return "m";
			case 3: return "h";
			default: return "unknown:<" + Int_.Xto_str(i) + ">";
		}
	}
	@gplx.Internal protected TimeSpanAdp(long fracs) {this.fracs = fracs;}
}
