/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.commons;
import gplx.types.commons.lists.CompareAble;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
public class GfoTimeSpan implements CompareAble<GfoTimeSpan> {
	public GfoTimeSpan(long fracs) {this.fracs = fracs;}
	public long Fracs() {return fracs;} private final long fracs;
	public int FracsAsInt() {return (int)fracs;}
	public GfoDecimal TotalDays()  {return GfoDecimalUtl.NewByDivide(fracs, GfoTimeSpanUtl.Divisors[GfoTimeSpanUtl.Idx_Hour]  * 24);}
	public GfoDecimal TotalHours() {return GfoDecimalUtl.NewByDivide(fracs, GfoTimeSpanUtl.Divisors[GfoTimeSpanUtl.Idx_Hour]);}
	public GfoDecimal TotalMins()  {return GfoDecimalUtl.NewByDivide(fracs, GfoTimeSpanUtl.Divisors[GfoTimeSpanUtl.Idx_Min]);}
	public GfoDecimal TotalSecs()  {return GfoDecimalUtl.NewByDivide(fracs, GfoTimeSpanUtl.Divisors[GfoTimeSpanUtl.Idx_Sec]);}
	public int[] Units() {return GfoTimeSpanUtl.SplitLong(fracs, GfoTimeSpanUtl.Divisors);}
	public GfoTimeSpan Add(GfoTimeSpan val)      {return new GfoTimeSpan(fracs + val.fracs);}
	public GfoTimeSpan AddFracs(long val)        {return new GfoTimeSpan(fracs + val);}
	public GfoTimeSpan AddUnit(int idx, int val) {
		int[] units = GfoTimeSpanUtl.SplitLong(fracs, GfoTimeSpanUtl.Divisors);
		units[idx] += val;
		int sign = fracs >= 0 ? 1 : -1;
		long rv = sign * GfoTimeSpanUtl.MergeLong(units, GfoTimeSpanUtl.Divisors);
		return GfoTimeSpanUtl.NewFracs(rv);
	}
	public GfoTimeSpan Subtract(GfoTimeSpan val)     {return new GfoTimeSpan(fracs - val.fracs);}
	public boolean Eq(Object o) {
		GfoTimeSpan comp = (GfoTimeSpan)o; if (comp == null) return false;
		return fracs == comp.fracs;
	}
	public String ToStr()              {return GfoTimeSpanUtl.ToStr(fracs, GfoTimeSpanUtl.Fmt_Default);}
	public String ToStr(String format) {return GfoTimeSpanUtl.ToStr(fracs, format);}
	public String ToStrUiAbrv()    {
		if (fracs == 0) return "0" + UnitAbbrv(0);
		int[] units = Units();
		boolean started = false;
		GfoStringBldr sb = new GfoStringBldr();
		for (int i = units.length - 1; i > -1; i--) {
			int unit = units[i];
			if (!started) {
				if (unit == 0)
					continue;
				else
					started = true;
			}
			if (sb.Len() != 0) sb.Add(" ");
			sb.AddObj(unit).Add(UnitAbbrv(i));
		}
		return sb.ToStr();
	}
	private String UnitAbbrv(int i) {
		switch (i) {
			case 0: return "f";
			case 1: return "s";
			case 2: return "m";
			case 3: return "h";
			default: return "unknown:<" + IntUtl.ToStr(i) + ">";
		}
	}

	@Override public int compareTo(GfoTimeSpan comp)  {return CompareAbleUtl.Compare_obj(fracs, comp.fracs);}
	@Override public String toString()                {return ToStr(GfoTimeSpanUtl.Fmt_Default);}
	@Override public boolean equals(Object obj)       {GfoTimeSpan comp = (GfoTimeSpan)obj; return ObjectUtl.Eq(fracs, comp.fracs);}
	@Override public int hashCode()                   {return new Long(fracs).hashCode();}
}
