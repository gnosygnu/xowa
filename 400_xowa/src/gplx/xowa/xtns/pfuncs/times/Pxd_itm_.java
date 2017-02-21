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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.log_msgs.*;
interface Pxd_itm {
	byte Tkn_tid();
	int Ary_idx();
	int Seg_idx();
	int Eval_idx();
	int Data_idx(); void Data_idx_(int v);
	boolean Eval(Pxd_parser state);
	boolean Time_ini(DateAdpBldr bldr);
}
class Pxd_itm_ {
	public static final int
	  Tid_null				= 0
	, Tid_int				= 1
	, Tid_int_dmy_14		= 2
	, Tid_int_hms_6			= 3
	, Tid_month_name		= 4
	, Tid_dow_name			= 5
	, Tid_unit				= 6
	, Tid_ago 				= 7
	, Tid_day_suffix		= 8		// 1st, 2nd
	, Tid_day_relative		= 9		// today, tomorrow, yesterday
	, Tid_time_relative		= 10	// now
	, Tid_unit_relative		= 11	// next, previous
	, Tid_unixtime			= 12	// @123
	, Tid_iso8601_t			= 13	// T
	, Tid_dash 				= Byte_ascii.Dash
	, Tid_dot 				= Byte_ascii.Dot
	, Tid_slash 			= Byte_ascii.Slash
	, Tid_colon 			= Byte_ascii.Colon
	, Tid_ws				= 98
	, Tid_sym				= 99
	;	
	public static Pxd_itm Find_bwd__non_ws(Pxd_itm[] tkns, int bgn) {
		for (int i = bgn - 1; i > -1; --i) {
			Pxd_itm itm = tkns[i];
			if (itm.Tkn_tid() != Tid_ws) return itm;
		}
		return null;
	}
	public static Pxd_itm Find_fwd_by_tid(Pxd_itm[] tkns, int bgn, int tid) {
		int len = tkns.length;
		for (int i = bgn; i < len; i++) {
			Pxd_itm itm = tkns[i];
			if (itm == null) return null; // PAGE:s.w:Synesthesia EX:"March 2006 [last update]"; DATE:2016-07-06
			if (tid == itm.Tkn_tid()) return itm;
		}
		return null;
	}
	public static boolean Eval_needed(Pxd_itm itm) {return itm.Seg_idx() == -1;}
}
abstract class Pxd_itm_base implements Pxd_itm {
	public abstract byte Tkn_tid();
	public int Ary_idx() {return ary_idx;} private int ary_idx;
	public int Seg_idx() {return seg_idx;} private int seg_idx = Seg_idx_null;
	public void Seg_idx_(int v) {seg_idx = v;}
	public int Data_idx() {return data_idx;} public void Data_idx_(int v) {data_idx = v;} private int data_idx;
	public abstract int Eval_idx();
	@gplx.Virtual public boolean Eval(Pxd_parser state) {return true;}
	@gplx.Virtual public boolean Time_ini(DateAdpBldr bldr) {return true;}
	public void Ctor(int ary_idx) {this.ary_idx = ary_idx;}
	public static final int Seg_idx_null = -1, Seg_idx_skip = -2;
}
interface Pxd_itm_prototype {
	Pxd_itm MakeNew(int ary_idx);
}
class DateAdpBldr {
	public DateAdp Date() {
		if (dirty) {	
			if (date == null) date = DateAdp_.seg_(seg_ary);	// date not set and seg_ary is dirty; make date = seg_ary;
			return date;
		}
		else
			return Datetime_now.Get();	// not dirtied; default to now;
	}
	public DateAdpBldr Date_(DateAdp v) {date = v; return this;} DateAdp date = null;
	public void Seg_set(int idx, int val) {
		if (date == null) seg_ary[idx] = val;
		else {
			seg_ary = date.XtoSegAry();
			seg_ary[idx] = val;
			date = DateAdp_.seg_(seg_ary);
		}
		dirty = true;
	}
	public DateAdp Bld() {
		return date == null ? DateAdp_.seg_(seg_ary) : date;
	}
	public DateAdpBldr(int... seg_ary) {this.seg_ary = seg_ary;}
	int[] seg_ary = new int[DateAdp_.SegIdx__max]; boolean dirty = false;
}
class Pft_func_time_log {
	private static final    Gfo_msg_grp owner = Gfo_msg_grp_.new_(Xoa_app_.Nde, "time_parser");
	public static final    Gfo_msg_itm
		  Invalid_day					= Gfo_msg_itm_.new_warn_(owner, "Invalid day: ~{0}")
		, Invalid_month					= Gfo_msg_itm_.new_warn_(owner, "Invalid month: ~{0}")
		, Invalid_year					= Gfo_msg_itm_.new_warn_(owner, "Invalid year: ~{0}")
		, Invalid_year_mid				= Gfo_msg_itm_.new_warn_(owner, "Invalid date: 4 digit year must be either yyyy-##-## or ##-##-yyyy")
		, Invalid_hour					= Gfo_msg_itm_.new_warn_(owner, "Invalid hour: ~{0}")
		, Invalid_minute				= Gfo_msg_itm_.new_warn_(owner, "Invalid minute: ~{0}")
		, Invalid_second				= Gfo_msg_itm_.new_warn_(owner, "Invalid second: ~{0}")
		, Invalid_date					= Gfo_msg_itm_.new_warn_(owner, "Invalid date: ~{0}")
		, Invalid_timezone				= Gfo_msg_itm_.new_warn_(owner, "Invalid timezone: ~{0}")
		;
}
