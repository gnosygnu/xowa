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
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*; import gplx.core.btries.*; import gplx.core.log_msgs.*;
class Pxd_parser {
	private final    Btrie_rv trv = new Btrie_rv();
	byte[] src; int cur_pos, tkn_bgn_pos, src_len, tkn_type;
	public Pxd_itm[] Tkns() {return tkns;} Pxd_itm[] tkns;
	public int Tkns_len() {return tkns_len;} private int tkns_len;
	public Pxd_itm[] Data_ary() {return data_ary;} Pxd_itm[] data_ary;
	public int Data_ary_len() {return data_ary_len;} private int data_ary_len;
	public int Colon_count;
	public int[] Seg_idxs() {return seg_idxs;} private int[] seg_idxs = new int[DateAdp_.SegIdx__max];	// temp ary for storing current state
	public byte[] Src() {return src;}
	public boolean Seg_idxs_chk(int... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			if (ary[i] == Pxd_itm_base.Seg_idx_null) return false;
		return true;
	}
	public void Seg_idxs_(Pxd_itm_int  itm, int seg_idx)				{Seg_idxs_(itm, seg_idx, itm.Val());}
	public void Seg_idxs_(Pxd_itm_base itm, int seg_idx, int val) {
		itm.Seg_idx_(seg_idx);
		if (seg_idx >= 0)	// ignore Seg_idx_null and Seg_idx_skip
			seg_idxs[seg_idx] = val;
	}
	public void Err_set(Gfo_msg_itm itm, Bfr_arg... args) {
		if (itm == null) return;
		Bry_fmtr fmtr = itm.Fmtr();
		fmtr.Bld_bfr(error_bfr, args);
	}	private Bry_bfr error_bfr = Bry_bfr_.New_w_size(32);
	public DateAdp Parse(byte[] src, Bry_bfr error_bfr) {
		Tokenize(src);	// NOTE: should check if Tokenize failed, but want to be liberal as date parser is not fully implemented; this will always default to 1st day of year; DATE:2014-03-27
		return Evaluate(src, error_bfr);
	}
	private boolean Tokenize(byte[] src) { 
		this.src = src; src_len = src.length;
		tkns = new Pxd_itm[src_len]; tkns_len = 0;		
		tkn_type = Pxd_itm_.Tid_null; tkn_bgn_pos = -1;
		cur_pos = 0;
		Colon_count = 0;
		error_bfr.Clear();
		for (int i = 0; i < DateAdp_.SegIdx__max; i++)
			seg_idxs[i] = Pxd_itm_base.Seg_idx_null;
		while (cur_pos < src_len) {
			byte b = src[cur_pos];
			switch (b) {	
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl:
					if (tkn_type != Pxd_itm_.Tid_ws) MakePrvTkn(cur_pos, Pxd_itm_.Tid_ws); break; // SEE:NOTE_1 for logic
				case Byte_ascii.Dash: case Byte_ascii.Dot: case Byte_ascii.Colon: case Byte_ascii.Slash:
					if (tkn_type != b) MakePrvTkn(cur_pos, b); break;
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					if (tkn_type != Pxd_itm_.Tid_int)	MakePrvTkn(cur_pos, Pxd_itm_.Tid_int); break;
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
				case Byte_ascii.At:
					MakePrvTkn(cur_pos, Pxd_itm_.Tid_null);			// first, make prv tkn
					Object o = trie.Match_at_w_b0(trv, b, src, cur_pos, src_len);	// now match String against tkn
					if (o == null) return false;	// unknown letter / word; exit now;
					tkns[tkns_len] = ((Pxd_itm_prototype)o).MakeNew(tkns_len); 
					++tkns_len;
					cur_pos = trv.Pos() - 1; // -1 b/c trie matches to next char, and ++ below
					break;
				case Byte_ascii.Comma: case Byte_ascii.Plus:
					MakePrvTkn(cur_pos, Pxd_itm_.Tid_null);					
					tkns[tkns_len] = new Pxd_itm_sym(tkns_len, b);
					++tkns_len;
					break;
			}
			++cur_pos;
		}
		MakePrvTkn(cur_pos, Pxd_itm_.Tid_null);
		return true;
	}
	private void MakePrvTkn(int cur_pos, int nxt_type) {
		Pxd_itm itm = null;
		switch (tkn_type) {
			case Pxd_itm_.Tid_int:
				int int_val = Bry_.To_int_or(src, tkn_bgn_pos, cur_pos, Int_.Min_value);
				if (int_val == Int_.Min_value) {} // FUTURE: warn
				int digits = cur_pos - tkn_bgn_pos;
				switch (digits) {
					case 14:	// yyyyMMddhhmmss
					case 12:	// yyyyMMddhhmm; PAGE:en.w:Boron; DATE:2015-07-29
					case 8:		// yyyyMMdd
						itm = new Pxd_itm_int_dmy_14(tkns_len, Bry_.Mid(src, tkn_bgn_pos, cur_pos), digits); break;
					case 6:
						itm = new Pxd_itm_int_mhs_6(tkns_len, Bry_.Mid(src, tkn_bgn_pos, cur_pos)); break;
					default:
						itm = new Pxd_itm_int(tkns_len, digits, int_val); break;
				}
				break;
			case Pxd_itm_.Tid_ws: 		itm = new Pxd_itm_ws(tkns_len); break;
			case Pxd_itm_.Tid_dash:		itm = new Pxd_itm_dash(tkns_len); break;
			case Pxd_itm_.Tid_dot:		itm = new Pxd_itm_dot(tkns_len); break;
			case Pxd_itm_.Tid_colon:	itm = new Pxd_itm_colon(tkns_len); break;
			case Pxd_itm_.Tid_slash:	itm = new Pxd_itm_slash(tkns_len); break;
			case Pxd_itm_.Tid_null:	break; // NOOP
		}
		if (itm != null) {
			tkns[tkns_len] = itm;
			++tkns_len;
		}
		tkn_type = nxt_type;
		tkn_bgn_pos = cur_pos;
	}
	DateAdp Evaluate(byte[] src, Bry_bfr error) {
		if (tkns_len == 0) {
			Err_set(Pft_func_time_log.Invalid_day, Bfr_arg_.New_bry(src));
			return null;
		}
		Pxd_itm[] eval_ary = Pxd_itm_sorter.XtoAryAndSort(tkns, tkns_len);
		MakeDataAry();
		for (int i = 0; i < tkns_len; i++) {
			if (!eval_ary[i].Eval(this)) {
				error.Add_bfr_and_clear(error_bfr);
				return DateAdp_.MinValue;
			}
			if (error_bfr.Len() != 0) {
				error.Add_bfr_and_clear(error_bfr);
				return DateAdp_.MinValue;			
			}
		}
		DateAdp now = Datetime_now.Get();
		DateAdpBldr bldr = new DateAdpBldr(now.Year(), now.Month(), now.Day(), 0, 0, 0, 0);
		for (int i = 0; i < tkns_len; i++) {
			Pxd_itm itm = (Pxd_itm)tkns[i];
			if (!itm.Time_ini(bldr)) {
				error.Add_str_a7("Invalid time");
				return null;
			}
		}
		return bldr.Bld();
	}
	private void MakeDataAry() {
		data_ary = new Pxd_itm[tkns_len]; data_ary_len = 0;
		for (int i = 0; i < tkns_len; i++) {
			Pxd_itm itm = tkns[i];
			switch (itm.Tkn_tid()) {
				case Pxd_itm_.Tid_month_name:
				case Pxd_itm_.Tid_int:
					itm.Data_idx_(data_ary_len);
					data_ary[data_ary_len++] = itm;
					break;			
			}
		}
	}
	private static Btrie_slim_mgr trie = Pxd_parser_.Trie();
}
class Pxd_parser_ {
	public static Btrie_slim_mgr Trie() {
		if (trie == null) {
			trie = Btrie_slim_mgr.ci_a7();	// NOTE:ci.ascii:MW_const.en
			Init();
		}
		return trie;
	}	private static Btrie_slim_mgr trie;
	private static final       String[] Names_month_full		= {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};
	private static final       String[] Names_month_abrv		= {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
	private static final       String[] Names_month_roman		= {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
	private static final       String[] Names_day_suffix		= {"st", "nd", "rd", "th"};
	private static final       String[] Names_day_full			= {"sunday", "monday", "tuesday", "wednesday" , "thursday", "friday", "saturday"};
	private static final       String[] Names_day_abrv			= {"sun", "mon", "tue", "wed" , "thu", "fri", "sat"};
	//TODO_OLD:
	//private static final       String[] Names_day_text		= {"weekday", "weekdays"};
	//private static final       String[] Names_ordinal_num		= {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelfth"};
	
	private static void Init_unit(int seg_idx, String... name_ary) {Init_unit(seg_idx, 1, name_ary);}
	private static void Init_unit(int seg_idx, int seg_val, String... name_ary) {
		int name_ary_len = name_ary.length;
		for (int i = 0; i < name_ary_len; i++) {
			byte[] name_bry = Bry_.new_u8(name_ary[i]);
			trie.Add_obj(name_bry, new Pxd_itm_unit(-1, name_bry, seg_idx, seg_val));
		}
	}
	public static final    byte[] 
	  Unit_name_month		= Bry_.new_a7("month")
	, Unit_name_day			= Bry_.new_a7("day")
	, Unit_name_hour		= Bry_.new_a7("hour")
	;
	private static void Init() {
		Init_reg_months(Names_month_full);
		Init_reg_months(Names_month_abrv);
		Init_reg_months(Names_month_roman);
		Init_reg_month("sept", 9);
		Init_reg_days_of_week(Names_day_full);
		Init_reg_days_of_week(Names_day_abrv);
		Init_unit(DateAdp_.SegIdx_second	, "sec", "secs", "second", "seconds");
		Init_unit(DateAdp_.SegIdx_minute	, "min", "mins", "minute", "minutes");
		Init_unit(DateAdp_.SegIdx_hour  	, "hour", "hours");
		Init_unit(DateAdp_.SegIdx_day   	, "day", "days");
		Init_unit(DateAdp_.SegIdx_day, 14	, "fortnight", "forthnight");
		Init_unit(DateAdp_.SegIdx_month 	, "month", "months");
		Init_unit(DateAdp_.SegIdx_year  	, "year", "years");
		Init_unit(DateAdp_.SegIdx_day,  7	, "week", "weeks");
		trie.Add_obj(Pxd_itm_ago.Name_ago, new Pxd_itm_ago(-1, -1));
		Init_suffix(Names_day_suffix);
		Init_relative();
		trie.Add_obj(Pxd_itm_unixtime.Name_const, new Pxd_itm_unixtime(-1, -1));
		trie.Add_obj(Pxd_itm_iso8601_t.Name_const, new Pxd_itm_iso8601_t(-1, -1));
	}
	private static void Init_reg_months(String[] names) {
		for (int i = 0; i < names.length; i++)
			Init_reg_month(names[i], i + Int_.Base1);	// NOTE: Months are Base1: 1-12
	}
	private static void Init_reg_month(String name_str, int seg_val) {
		byte[] name_ary = Bry_.new_u8(name_str);
		trie.Add_obj(name_ary, new Pxd_itm_month_name(-1, name_ary, DateAdp_.SegIdx_month, seg_val));
	}
	private static void Init_reg_days_of_week(String[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			byte[] itm_bry = Bry_.new_u8(ary[i]);
			trie.Add_obj(itm_bry, new Pxd_itm_dow_name(-1, itm_bry, i));	// NOTE: days are base0; 0-6
		}
	}
	private static void Init_suffix(String[] suffix_ary) {
		int len = suffix_ary.length;
		for (int i = 0; i < len; i++) {
			String suffix = suffix_ary[i];
			trie.Add_obj(suffix, Pxd_itm_day_suffix.Instance);
		}
	}
	private static void Init_relative() {
		trie.Add_obj("today", Pxd_itm_day_relative.Today);
		trie.Add_obj("tomorrow", Pxd_itm_day_relative.Tomorrow);
		trie.Add_obj("yesterday", Pxd_itm_day_relative.Yesterday);
		trie.Add_obj("now", Pxd_itm_time_relative.Now);
		trie.Add_obj("next", Pxd_itm_unit_relative.Next);
		trie.Add_obj("last", Pxd_itm_unit_relative.Prev);
		trie.Add_obj("previous", Pxd_itm_unit_relative.Prev);
		trie.Add_obj("this", Pxd_itm_unit_relative.This);
	}
}
/*
NOTE_1:parsing works by completing previous items and then setting current;
EX: "123  456"
 1: tkn_type = null; b = number
    complete tkn (note that tkn is null)
    set tkn_type to number; set tkn_bgn to 0
 2: b == number == tkn_type; noop
 3: b == number == tkn_type; noop
 4: b == space  != tkn_type;
    complete prv
	  create tkn with bgn = tkn_bgn and end = cur_pos
	set tkn_type to space; set tkn_bgn to cur_pos
 etc..
*/