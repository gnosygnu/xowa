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
package gplx.xowa.langs.durations; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_duration_itm_ {
	private static final Hash_adp_bry regy = Hash_adp_bry.ci_ascii_();	// ASCII:MW.consts
	public static final byte
	  Tid_millenia		= 0
	, Tid_centuries		= 1
	, Tid_decades		= 2
	, Tid_years			= 3
	, Tid_weeks			= 4
	, Tid_days			= 5
	, Tid_hours			= 6
	, Tid_minutes		= 7
	, Tid_seconds		= 8
	;
	public static final Xol_duration_itm
	  Itm_millenia		= new_(Tid_millenia		, "millenia"		, 31556952000L)
	, Itm_centuries		= new_(Tid_centuries	, "centuries"		,  3155695200L)
	, Itm_decades		= new_(Tid_decades		, "decades"			,   315569520L)
	, Itm_years			= new_(Tid_years		, "years"			,    31556952L)	// 86400 * (365 + (24 * 3 + 25) / 400)
	, Itm_weeks			= new_(Tid_weeks		, "weeks"			,      604800L)
	, Itm_days			= new_(Tid_days			, "days"			,       86400L)
	, Itm_hours			= new_(Tid_hours		, "hours"			,        3600L)
	, Itm_minutes		= new_(Tid_minutes		, "minutes"			,          60L)
	, Itm_seconds		= new_(Tid_seconds		, "seconds"			,           1L)
	;
	private static Xol_duration_itm new_(byte tid, String name, long factor) {
		Xol_duration_itm rv = new Xol_duration_itm(tid, name, factor);
		regy.Add(rv.Name_bry(), rv);
		return rv;
	}
	public static final Xol_duration_itm[] Ary_default = new Xol_duration_itm[]
	{ Itm_millenia
	, Itm_centuries
	, Itm_decades
	, Itm_years
	, Itm_weeks
	, Itm_days
	, Itm_hours
	, Itm_minutes
	, Itm_seconds
	};
	public static Xol_duration_itm[] Xto_itm_ary(KeyVal[] kv_ary) {
		if (kv_ary == null) return Xol_duration_itm_.Ary_default;
		ListAdp rv = ListAdp_.new_();
		int len = kv_ary.length;
		for (int i = 0; i < len; i++) {
			KeyVal kv = kv_ary[i];
			String name = kv.Val_to_str_or_empty();
			Xol_duration_itm itm = (Xol_duration_itm)regy.Fetch(Bry_.new_utf8_(name));
			if (itm != null)
				rv.Add(itm);
		}
		return (Xol_duration_itm[])rv.XtoAry(Xol_duration_itm.class);
	}
}
class Xol_duration_itm_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xol_duration_itm lhs = (Xol_duration_itm)lhsObj;
		Xol_duration_itm rhs = (Xol_duration_itm)rhsObj;
		return -Long_.Compare(lhs.Seconds(), rhs.Seconds());	// - to sort from largest to smallest
	}
	public static final Xol_duration_itm_sorter _ = new Xol_duration_itm_sorter(); Xol_duration_itm_sorter() {}
}
