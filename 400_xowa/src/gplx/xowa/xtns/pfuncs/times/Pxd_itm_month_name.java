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
import gplx.core.brys.*;
class Pxd_itm_month_name extends Pxd_itm_base implements Pxd_itm_prototype {
	public Pxd_itm_month_name(int ary_idx, byte[] name, int seg_idx, int seg_val) {Ctor(ary_idx); this.name = name; Seg_idx_(seg_idx); this.seg_val = seg_val;} private byte[] name;
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_month_name;}
	@Override public int Eval_idx() {return 20;}
	int seg_val;
	public Pxd_itm MakeNew(int ary_idx) {
		Pxd_itm_month_name rv = new Pxd_itm_month_name(ary_idx, name, this.Seg_idx(), seg_val);
		return rv;
	}
	@Override public boolean Eval(Pxd_parser state) {
		state.Seg_idxs_(this, this.Seg_idx(), seg_val);
		switch (this.Data_idx()) {
			case 0: Eval_month_0(state); break;
			case 1: Eval_month_1(state); break;
			case 2: Eval_month_2(state); break;
			default:
				Pxd_itm[] data_ary = state.Data_ary();
				int data_idx = this.Data_idx();
				if (data_idx + 1 < data_ary.length) {
					Pxd_itm itm = data_ary[data_idx + 1];
					if (itm == null) return false;	// PAGE:s.w:Leonid_Hurwicz EX:1944, 2004, [[7 August]] [[2007]] DATE:2016-07-06 
					Pxd_itm_int itm_rhs = Pxd_itm_int_.CastOrNull(itm);
					if (itm_rhs == null) return false;	// PAGE:s.w:Leonid_Hurwicz EX:1944, 2004, [[7 August]] [[2007]] DATE:2016-07-06 
					if (itm_rhs.Digits() == 4) {	// handle fmts of 1 January 2010
						Eval_month_1(state);
						return true;
					}
				}
				Eval_month_2(state);				
				break;
		} 
		return true;
	}
	public void Eval_month_0(Pxd_parser state) {
		Pxd_itm[] data_ary = state.Data_ary();
		int data_ary_len = state.Data_ary_len();
		switch (data_ary_len) {
			case 1: break; // noop; nothing needs to be done
			case 2: {
				Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
				if (itm_1 == null) {return;} // trie: fail
				switch (itm_1.Digits()) {
					case 4: 	Pxd_eval_seg.Eval_as_y(state, itm_1); break;
					default: 	Pxd_eval_seg.Eval_as_d(state, itm_1); break;				
				}
				break;
			}
			default:	// NOTE: handle invalid dates like January 2, 1999, 2000; DATE:2013-05-06
			case 3: {
				Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
				Pxd_itm_int itm_2 = Pxd_itm_int_.CastOrNull(data_ary[2]);
				if (itm_1 == null || itm_2 == null) {return;} // trie: fail
				if (itm_1.Digits() == 4) {	// ASSUME: year since there are 4 digits; EX: May 2012
					if (Pxd_eval_seg.Eval_as_y(state, itm_1)) return;	// no error; return; otherwise continue below;
				}
				if (!Pxd_eval_seg.Eval_as_d(state, itm_1)) return;
				if (!Pxd_eval_seg.Eval_as_y(state, itm_2)) return;
				break;
			}
		}
	}
	public void Eval_month_1(Pxd_parser state) {
		Pxd_itm[] data_ary = state.Data_ary();
		int data_ary_len = state.Data_ary_len();
		switch (data_ary_len) {
			case 2: {
				Pxd_itm_int itm_0 = Pxd_itm_int_.CastOrNull(data_ary[0]);
				if (itm_0 == null) {return;} // trie: fail
				switch (itm_0.Digits()) {
					case 4: 	Pxd_eval_seg.Eval_as_y(state, itm_0); break;
					default: 	Pxd_eval_seg.Eval_as_d(state, itm_0); break;				
				}
				break;
			}
			default:
			case 3: {
				Pxd_itm_int itm_0 = Pxd_itm_int_.GetNearest(data_ary, this.Data_idx(), false);
				Pxd_itm_int itm_2 = Pxd_itm_int_.GetNearest(data_ary, this.Data_idx(), true);
				if (itm_0 == null || itm_2 == null) {return;} // trie: fail
				switch (itm_0.Digits()) {
					case 4:
						if (!Pxd_eval_seg.Eval_as_y(state, itm_0)) return;						
						if (!Pxd_eval_seg.Eval_as_d(state, itm_2)) return;
						break;
					default:
						if (itm_2.Digits() == 4) {
							if (!Pxd_eval_seg.Eval_as_d(state, itm_0)) return;							
							if (!Pxd_eval_seg.Eval_as_y(state, itm_2)) return;						
						}
						else { // 2 digits on either side of month; assume dd mm yy; EX: 03 Feb 01 -> 2001-02-03
							if (!Pxd_eval_seg.Eval_as_d(state, itm_0)) return;						
							if (!Pxd_eval_seg.Eval_as_y(state, itm_2)) return;							
						}
						break;
				}
				break;
			}
		}			
	}
	public void Eval_month_2(Pxd_parser state) {
		Pxd_itm[] data_ary = state.Data_ary();
		int data_ary_len = state.Data_ary_len();
		switch (data_ary_len) {
			case 3: {
				Pxd_itm_int itm_0 = Pxd_itm_int_.CastOrNull(data_ary[0]);
				Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
				if (itm_0 == null || itm_1 == null) {return;} // trie: fail
				switch (itm_0.Digits()) {
					case 4:
						if (!Pxd_eval_seg.Eval_as_y(state, itm_0)) return;						
						if (!Pxd_eval_seg.Eval_as_d(state, itm_1)) return;
						break;
					default:
						if (itm_1.Digits() == 4) {
							if (!Pxd_eval_seg.Eval_as_d(state, itm_0)) return;							
							if (!Pxd_eval_seg.Eval_as_y(state, itm_1)) return;
						}
						else { // 2 digits on either side of month; assume dd mm yy; EX: 03 Feb 01 -> 2001-02-03
							if (!Pxd_eval_seg.Eval_as_d(state, itm_0)) return;						
							if (!Pxd_eval_seg.Eval_as_y(state, itm_1)) return;							
						}
						break;
				}
				break;
			}
		}	
	}
	@Override public boolean Time_ini(DateAdpBldr bldr) {
		bldr.Seg_set(this.Seg_idx(), seg_val);
		return true;
	}
}
class Pxd_itm_unit extends Pxd_itm_base implements Pxd_itm_prototype {
	public Pxd_itm_unit(int ary_idx, byte[] name, int seg_idx, int seg_multiple) {Ctor(ary_idx); this.name = name; Seg_idx_(seg_idx); this.seg_multiple = seg_multiple;} 
	public byte[] Name() {return name;} private byte[] name;
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_unit;}
	@Override public int Eval_idx() {return 10;}
	int seg_val = 1; int seg_multiple;
	public Pxd_itm MakeNew(int ary_idx) {
		return new Pxd_itm_unit(ary_idx, name, this.Seg_idx(), seg_val);
	}
	public void Unit_seg_val_(int v) {	// handled by relative; EX: next year
		seg_val = v; seg_multiple = 1;
		eval_done_by_relative = true;
	}	private boolean eval_done_by_relative;		
	@Override public boolean Eval(Pxd_parser state) {
		if (eval_done_by_relative) return true;
		state.Seg_idxs_(this, this.Seg_idx(), seg_val);
		Pxd_itm[] tkns = state.Tkns();
		Pxd_itm_int itm_int = Pxd_itm_int_.GetNearest(tkns, this.Ary_idx(), false);
		if (itm_int == null) return false; // PAGE:s.w:Crich_Tramway_Village EX:Yearly DATE:2016-07-06
		state.Seg_idxs_(itm_int, Pxd_itm_base.Seg_idx_skip);
		seg_val = itm_int.Val();
		for (int i = itm_int.Ary_idx(); i > -1; i--) {
			Pxd_itm itm = tkns[i];
			switch (itm.Tkn_tid()) {
				case Pxd_itm_.Tid_dash:														// negative sign; stop;
					seg_val *= -1;
					i = -1;
					break;
				case Pxd_itm_.Tid_dot: case Pxd_itm_.Tid_int: case Pxd_itm_.Tid_ws:	// ignore
					break;
				default:																		// word; stop;
					i = -1;
					break;
			}
		}
		return true;
	}
	@Override public boolean Time_ini(DateAdpBldr bldr) {
		DateAdp cur = bldr.Date();
		if (cur == null) cur = Datetime_now.Get();
		int val = seg_val * seg_multiple;
		switch (this.Seg_idx()) {
			case DateAdp_.SegIdx_second: cur = cur.Add_second(val); break;
			case DateAdp_.SegIdx_minute: cur = cur.Add_minute(val); break;
			case DateAdp_.SegIdx_hour  : cur = cur.Add_hour  (val); break;
			case DateAdp_.SegIdx_day   : cur = cur.Add_day   (val); break;
			case DateAdp_.SegIdx_month : cur = cur.Add_month (val); break;
			case DateAdp_.SegIdx_year  : cur = cur.Add_year  (val); break;
			default: throw Err_.new_unhandled(this.Seg_idx());
		}
		bldr.Date_(cur);
		return true;
	}
}
class Pxd_itm_ago extends Pxd_itm_base implements Pxd_itm_prototype {
	public Pxd_itm_ago(int ary_idx, int seg_idx) {Ctor(ary_idx); Seg_idx_(seg_idx);} 
	public byte[] Name() {return Name_ago;} public static final    byte[] Name_ago = Bry_.new_a7("ago");
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_ago;}
	@Override public int Eval_idx() {return 5;}	// set to high priority so it can evaluate before unit_name
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_ago(ary_idx, this.Seg_idx());}
	@Override public boolean Eval(Pxd_parser state) {
		Pxd_itm[] tkns = state.Tkns();
		int end = this.Ary_idx();
		boolean unit_seen = false;
		for (int i = end - 1; i > -1; i--) {	// loop over tokens, moving backward
			Pxd_itm itm = tkns[i];
			switch (itm.Tkn_tid()) {
				case Pxd_itm_.Tid_ws: break;	// ignore ws
				case Pxd_itm_.Tid_unit: unit_seen = true; break;	// unit seen; flag
				case Pxd_itm_.Tid_int:
					if (unit_seen) {	// only if unit seen, reverse int; EX: "1 month ago" effectively becomes "-1 month"
						Pxd_itm_int itm_int = (Pxd_itm_int)itm;
						itm_int.Val_(-itm_int.Val());
						i = -1;	// end loop
					}
					break;
				default: break;
			}
		}
		return true;
	}
	@Override public boolean Time_ini(DateAdpBldr bldr) {return true;}
}
class Pxd_itm_day_suffix extends Pxd_itm_base implements Pxd_itm_prototype {
	public Pxd_itm_day_suffix(int ary_idx) {Ctor(ary_idx);}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_day_suffix;}
	@Override public int Eval_idx() {return 99;}	// set to low priority  so it can evaluate after day
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_day_suffix(ary_idx);}
	@Override public boolean Eval(Pxd_parser state) {
		Pxd_itm[] tkn_ary = state.Tkns();
		int tkn_idx = this.Ary_idx();
		if (tkn_idx == 0) {state.Err_set(Pft_func_time_log.Invalid_day, Bfr_arg_.New_int(Int_.Min_value)); return false;}	// PAGE:s.w:Aachen EX: "936" DATE:2016-07-06
		Pxd_itm day_itm = tkn_ary[tkn_idx - 1];
		if (day_itm.Seg_idx() != DateAdp_.SegIdx_day) {
			state.Err_set(Pft_func_time_log.Invalid_day, Bfr_arg_.New_int(Int_.Min_value));
		}
		return true;
	}
	public static final    Pxd_itm_day_suffix Instance = new Pxd_itm_day_suffix(); Pxd_itm_day_suffix() {}
}
class Pxd_itm_day_relative extends Pxd_itm_base implements Pxd_itm_prototype {
	public Pxd_itm_day_relative(int adj, int ary_idx) {Ctor(ary_idx); this.adj = adj;}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_day_relative;}
	@Override public int Eval_idx() {return 5;}	
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_day_relative(adj, ary_idx);}
	@Override public boolean Eval(Pxd_parser state) {return true;}
	@Override public boolean Time_ini(DateAdpBldr bldr) {
		DateAdp date = Datetime_now.Get();
		if (adj != 0) date = date.Add_day(adj);			
		bldr.Seg_set(DateAdp_.SegIdx_year		, date.Year());
		bldr.Seg_set(DateAdp_.SegIdx_month		, date.Month());
		bldr.Seg_set(DateAdp_.SegIdx_day		, date.Day());
		return true;
	}

	public static final    Pxd_itm_day_relative
	  Today		= new Pxd_itm_day_relative(0)
	, Tomorrow	= new Pxd_itm_day_relative(1)
	, Yesterday	= new Pxd_itm_day_relative(-1)
	;
	Pxd_itm_day_relative(int adj) {this.adj = adj;} private int adj;
}
class Pxd_itm_time_relative extends Pxd_itm_base implements Pxd_itm_prototype {
	public Pxd_itm_time_relative(int ary_idx) {Ctor(ary_idx);}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_time_relative;}
	@Override public int Eval_idx() {return 5;}	
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_time_relative(ary_idx);}
	@Override public boolean Eval(Pxd_parser state) {return true;}
	@Override public boolean Time_ini(DateAdpBldr bldr) {
		DateAdp date = Datetime_now.Get();
		bldr.Seg_set(DateAdp_.SegIdx_year		, date.Year());
		bldr.Seg_set(DateAdp_.SegIdx_month		, date.Month());
		bldr.Seg_set(DateAdp_.SegIdx_day		, date.Day());
		bldr.Seg_set(DateAdp_.SegIdx_hour		, date.Hour());
		bldr.Seg_set(DateAdp_.SegIdx_minute		, date.Minute());
		bldr.Seg_set(DateAdp_.SegIdx_second		, date.Second());
		bldr.Seg_set(DateAdp_.SegIdx_frac		, date.Frac());
		return true;
	}
	public static final    Pxd_itm_time_relative
	  Now		= new Pxd_itm_time_relative()
	;
	Pxd_itm_time_relative() {}		
}
class Pxd_itm_unit_relative extends Pxd_itm_base implements Pxd_itm_prototype {
	public Pxd_itm_unit_relative(int adj, int ary_idx) {Ctor(ary_idx); this.adj = adj;}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_unit_relative;}
	@Override public int Eval_idx() {return 5;}
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_unit_relative(adj, ary_idx);}
	@Override public boolean Eval(Pxd_parser state) {
		Pxd_itm itm = Pxd_itm_.Find_fwd_by_tid(state.Tkns(), this.Ary_idx() + 1, Pxd_itm_.Tid_unit);
		if (itm == null) state.Err_set(Pft_func_time_log.Invalid_date, Bfr_arg_.New_int(adj));
		Pxd_itm_unit unit_tkn = (Pxd_itm_unit)itm;
		if (unit_tkn == null) {state.Err_set(Pft_func_time_log.Invalid_date, Bfr_arg_.New_int(adj)); return false;} // PAGE:s.w:Synesthesia EX:"March 2006 [last update]"; DATE:2016-07-06
		unit_tkn.Unit_seg_val_(adj);
		return true;
	}
	@Override public boolean Time_ini(DateAdpBldr bldr) {return true;}
	public static final    Pxd_itm_unit_relative
	  Next		= new Pxd_itm_unit_relative(1)
	, Prev		= new Pxd_itm_unit_relative(-1)
	, This		= new Pxd_itm_unit_relative(0)
	;
	Pxd_itm_unit_relative(int adj) {this.adj = adj;} private int adj;
}
class Pxd_itm_unixtime extends Pxd_itm_base implements Pxd_itm_prototype {
	private long unixtime;
	public Pxd_itm_unixtime(int ary_idx, int seg_idx) {Ctor(ary_idx); Seg_idx_(seg_idx);} 
	public byte[] Name() {return Name_const;} public static final    byte[] Name_const = Bry_.new_a7("@");
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_unixtime;}
	@Override public int Eval_idx() {return 5;}	// set to high priority so it can evaluate number early
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_unixtime(ary_idx, this.Seg_idx());}
	@Override public boolean Eval(Pxd_parser state) {
		Pxd_itm[] tkns = state.Tkns();
		unixtime = Pxd_itm_int_.Read_nearest_as_int_and_skip(state, tkns, this.Ary_idx(), true, Int_.Min_value);
		return true;
	}
	@Override public boolean Time_ini(DateAdpBldr bldr) {
		DateAdp date = DateAdp_.unixtime_utc_seconds_(unixtime);
		bldr.Seg_set(DateAdp_.SegIdx_year		, date.Year());
		bldr.Seg_set(DateAdp_.SegIdx_month		, date.Month());
		bldr.Seg_set(DateAdp_.SegIdx_day		, date.Day());
		bldr.Seg_set(DateAdp_.SegIdx_hour		, date.Hour());
		bldr.Seg_set(DateAdp_.SegIdx_minute		, date.Minute());
		bldr.Seg_set(DateAdp_.SegIdx_second		, date.Second());
		bldr.Seg_set(DateAdp_.SegIdx_frac		, date.Frac());
		return true;
	}
}
class Pxd_itm_dow_name extends Pxd_itm_base implements Pxd_itm_prototype {
	private int dow_idx;
	private byte[] dow_name;
	public Pxd_itm_dow_name(int ary_idx, byte[] dow_name, int dow_idx) {Ctor(ary_idx); this.dow_name = dow_name; this.Seg_idx_(DateAdp_.SegIdx_dayOfWeek); this.dow_idx = dow_idx;}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_dow_name;}
	@Override public int Eval_idx() {return 20;}
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_dow_name(ary_idx, dow_name, dow_idx);}
	@Override public boolean Eval(Pxd_parser state) {return true;}
	@Override public boolean Time_ini(DateAdpBldr bldr) {
		DateAdp now = Datetime_now.Get();
		int adj = dow_idx - now.DayOfWeek();	// adj = requested_dow - current_dow; EX: requesting Friday, and today is Wednesday; adj = 2 (4 - 2); DATE:2014-05-02
		if (adj < 0) adj += 7;					// requested_down is before current_dow; add 7 to get the next day
		bldr.Date_(bldr.Date().Add_day(adj));
		return true;
	}
}
class Pxd_itm_iso8601_t extends Pxd_itm_base implements Pxd_itm_prototype {
	public Pxd_itm_iso8601_t(int ary_idx, int seg_idx) {Ctor(ary_idx); Seg_idx_(seg_idx);} 
	public byte[] Name() {return Name_const;} public static final    byte[] Name_const = Bry_.new_a7("T");
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_iso8601_t;}
	@Override public int Eval_idx() {return 99;}	// evaluate last
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_iso8601_t(ary_idx, this.Seg_idx());}
	@Override public boolean Eval(Pxd_parser state) {			
		Pxd_itm hour = Next_non_ws_tkn(state.Tkns(), this.Ary_idx() + 1);
		if (hour != null && hour.Seg_idx() == DateAdp_.SegIdx_hour) return true;	// next item is hour
		state.Err_set(Pft_func_time_log.Invalid_hour, Bfr_arg_.New_bry("T"));
		return true;
	}
	@Override public boolean Time_ini(DateAdpBldr bldr) {return true;}
	private static Pxd_itm Next_non_ws_tkn(Pxd_itm[] tkns, int bgn) {
		int len = tkns.length;
		for (int i = bgn; i < len; i++) {
			Pxd_itm itm = tkns[i];
			if (itm == null) return null; // PAGE:s.w:Christianity_and_alcohol EX:January 8 to January 14, 2001; DATE:2016-07-06 
			if (itm.Tkn_tid() != Pxd_itm_.Tid_ws) return itm;
		}
		return null;
	}
}
