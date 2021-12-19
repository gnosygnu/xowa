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
package gplx.xowa.xtns.pfuncs.times;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDateNow;
import gplx.types.basics.utls.IntUtl;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.args.BryBfrArgUtl;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.core.log_msgs.Gfo_msg_itm;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
class Pxd_parser {
	private final Btrie_rv trv = new Btrie_rv();
	private byte[] src; int cur_pos, tkn_bgn_pos, src_len, tkn_type;
	public Pxd_itm[] Tkns() {return tkns;} Pxd_itm[] tkns;
	public int Tkns_len() {return tkns_len;} private int tkns_len;
	public Pxd_itm[] Data_ary() {return data_ary;} Pxd_itm[] data_ary;
	public int Data_ary_len() {return data_ary_len;} private int data_ary_len;
	public int Colon_count;
	public int[] Seg_idxs() {return seg_idxs;} private int[] seg_idxs = new int[GfoDateUtl.SegIdxMaxLen];	// temp ary for storing current state
	public byte[] Src() {return src;}
//		public Pxd_parser(Xop_ctx ctx) {
//			this.trie = Pxd_parser_.Trie(ctx);
//			Dbx_scan_support.Init(trie);
//		}
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
	public void Err_set(Gfo_msg_itm itm, BryBfrArg... args) {
		if (itm == null) return;
		BryFmtr fmtr = itm.Fmtr();
		fmtr.BldToBfrArgs(error_bfr, args);
	}	private BryWtr error_bfr = BryWtr.NewWithSize(32);
	public GfoDate Parse(byte[] src, BryWtr error_bfr) {
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
		for (int i = 0; i < GfoDateUtl.SegIdxMaxLen; i++)
			seg_idxs[i] = Pxd_itm_base.Seg_idx_null;
		while (cur_pos < src_len) {
			byte b = src[cur_pos];
			switch (b) {	
				case AsciiByte.Space: case AsciiByte.Tab: case AsciiByte.Nl:
					if (tkn_type != Pxd_itm_.Tid_ws) MakePrvTkn(cur_pos, Pxd_itm_.Tid_ws); break; // SEE:NOTE_1 for logic
				case AsciiByte.Dash: case AsciiByte.Dot: case AsciiByte.Colon: case AsciiByte.Slash:
					if (tkn_type != b) MakePrvTkn(cur_pos, b); break;
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					if (tkn_type != Pxd_itm_.Tid_int)	MakePrvTkn(cur_pos, Pxd_itm_.Tid_int); break;
				case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E:
				case AsciiByte.Ltr_F: case AsciiByte.Ltr_G: case AsciiByte.Ltr_H: case AsciiByte.Ltr_I: case AsciiByte.Ltr_J:
				case AsciiByte.Ltr_K: case AsciiByte.Ltr_L: case AsciiByte.Ltr_M: case AsciiByte.Ltr_N: case AsciiByte.Ltr_O:
				case AsciiByte.Ltr_P: case AsciiByte.Ltr_Q: case AsciiByte.Ltr_R: case AsciiByte.Ltr_S: case AsciiByte.Ltr_T:
				case AsciiByte.Ltr_U: case AsciiByte.Ltr_V: case AsciiByte.Ltr_W: case AsciiByte.Ltr_X: case AsciiByte.Ltr_Y: case AsciiByte.Ltr_Z:
				case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e:
				case AsciiByte.Ltr_f: case AsciiByte.Ltr_g: case AsciiByte.Ltr_h: case AsciiByte.Ltr_i: case AsciiByte.Ltr_j:
				case AsciiByte.Ltr_k: case AsciiByte.Ltr_l: case AsciiByte.Ltr_m: case AsciiByte.Ltr_n: case AsciiByte.Ltr_o:
				case AsciiByte.Ltr_p: case AsciiByte.Ltr_q: case AsciiByte.Ltr_r: case AsciiByte.Ltr_s: case AsciiByte.Ltr_t:
				case AsciiByte.Ltr_u: case AsciiByte.Ltr_v: case AsciiByte.Ltr_w: case AsciiByte.Ltr_x: case AsciiByte.Ltr_y: case AsciiByte.Ltr_z:
				case AsciiByte.At:
					MakePrvTkn(cur_pos, Pxd_itm_.Tid_null);			// first, make prv tkn
					Object o = trie.Match_at_w_b0(trv, b, src, cur_pos, src_len);	// now match String against tkn
					if (o == null) return false;	// unknown letter / word; exit now;
					tkns[tkns_len] = ((Pxd_itm_prototype)o).MakeNew(tkns_len); 
					++tkns_len;
					cur_pos = trv.Pos() - 1; // -1 b/c trie matches to next char, and ++ below
					break;
				case AsciiByte.Comma: case AsciiByte.Plus:
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
				int int_val = BryUtl.ToIntOr(src, tkn_bgn_pos, cur_pos, IntUtl.MinValue);
				if (int_val == IntUtl.MinValue) {} // FUTURE: warn
				int digits = cur_pos - tkn_bgn_pos;
				switch (digits) {
					case 14:	// yyyyMMddhhmmss
					case 12:	// yyyyMMddhhmm; PAGE:en.w:Boron; DATE:2015-07-29
					case 8:		// yyyyMMdd
						itm = new Pxd_itm_int_dmy_14(tkns_len, BryLni.Mid(src, tkn_bgn_pos, cur_pos), digits); break;
					case 6:
						itm = new Pxd_itm_int_mhs_6(tkns_len, BryLni.Mid(src, tkn_bgn_pos, cur_pos)); break;
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
	GfoDate Evaluate(byte[] src, BryWtr error) {
		if (tkns_len == 0) {
			Err_set(Pft_func_time_log.Invalid_day, BryBfrArgUtl.NewBry(src));
			return null;
		}
		Pxd_itm[] eval_ary = Pxd_itm_sorter.XtoAryAndSort(tkns, tkns_len);
		MakeDataAry();
		for (int i = 0; i < tkns_len; i++) {
			if (!eval_ary[i].Eval(this)) {
				error.AddBfrAndClear(error_bfr);
				return GfoDateUtl.MinValue;
			}
			if (error_bfr.Len() != 0) {
				error.AddBfrAndClear(error_bfr);
				return GfoDateUtl.MinValue;
			}
		}

		// build date
		GfoDate now = GfoDateNow.Get();
		Pxd_date_bldr bldr = new Pxd_date_bldr(now.Year(), now.Month(), now.Day(), 0, 0, 0, 0);
		for (int i = 0; i < tkns_len; i++) {
			Pxd_itm itm = (Pxd_itm)tkns[i];
			if (!itm.Time_ini(bldr)) {
				error.AddStrA7("Invalid time");
				return null;
			}
		}
		return bldr.To_date();
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
	private static final    String[] Names_month_full		= {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};
	private static final    String[] Names_month_abrv		= {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
	private static final    String[] Names_month_roman		= {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
	private static final    String[] Names_day_suffix		= {"st", "nd", "rd", "th"};
	private static final    String[] Names_day_full			= {"sunday", "monday", "tuesday", "wednesday" , "thursday", "friday", "saturday"};
	private static final    String[] Names_day_abrv			= {"sun", "mon", "tue", "wed" , "thu", "fri", "sat"};
	//TODO_OLD:
	//private static final    String[] Names_day_text		= {"weekday", "weekdays"};
	//private static final    String[] Names_ordinal_num		= {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelfth"};
	
	private static void Init_unit(int seg_idx, String... name_ary) {Init_unit(seg_idx, 1, name_ary);}
	private static void Init_unit(int seg_idx, int seg_val, String... name_ary) {
		int name_ary_len = name_ary.length;
		for (int i = 0; i < name_ary_len; i++) {
			byte[] name_bry = BryUtl.NewU8(name_ary[i]);
			trie.AddObj(name_bry, new Pxd_itm_unit(-1, name_bry, seg_idx, seg_val));
		}
	}
	public static final byte[]
	  Unit_name_month		= BryUtl.NewA7("month")
	, Unit_name_day			= BryUtl.NewA7("day")
	, Unit_name_hour		= BryUtl.NewA7("hour")
	;
	private static void Init() {
		Init_reg_months(Names_month_full);
		Init_reg_months(Names_month_abrv);
		Init_reg_months(Names_month_roman);
		Init_reg_month("sept", 9);
		Init_reg_days_of_week(Names_day_full);
		Init_reg_days_of_week(Names_day_abrv);
		Init_unit(GfoDateUtl.SegIdxSecond, "sec", "secs", "second", "seconds");
		Init_unit(GfoDateUtl.SegIdxMinute, "min", "mins", "minute", "minutes");
		Init_unit(GfoDateUtl.SegIdxHour, "hour", "hours");
		Init_unit(GfoDateUtl.SegIdxDay, "day", "days");
		Init_unit(GfoDateUtl.SegIdxDay, 14	, "fortnight", "forthnight");
		Init_unit(GfoDateUtl.SegIdxMonth, "month", "months");
		Init_unit(GfoDateUtl.SegIdxYear, "year", "years");
		Init_unit(GfoDateUtl.SegIdxDay,  7	, "week", "weeks");
		trie.AddObj(Pxd_itm_ago.Name_ago, new Pxd_itm_ago(-1, -1));
		Init_suffix(Names_day_suffix);
		Init_relative();
		trie.AddObj(Pxd_itm_unixtime.Name_const, new Pxd_itm_unixtime(-1, -1));
		trie.AddObj(Pxd_itm_iso8601_t.Name_const, new Pxd_itm_iso8601_t(-1, -1));
		Init_meridian(BoolUtl.N, "am", "a.m", "am.", "a.m.");
		Init_meridian(BoolUtl.Y, "pm", "p.m", "pm.", "p.m.");
	}
	private static void Init_reg_months(String[] names) {
		for (int i = 0; i < names.length; i++)
			Init_reg_month(names[i], i + IntUtl.Base1);	// NOTE: Months are Base1: 1-12
	}
	private static void Init_reg_month(String name_str, int seg_val) {
		byte[] name_ary = BryUtl.NewU8(name_str);
		trie.AddObj(name_ary, new Pxd_itm_month_name(-1, name_ary, GfoDateUtl.SegIdxMonth, seg_val));
	}
	private static void Init_reg_days_of_week(String[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			byte[] itm_bry = BryUtl.NewU8(ary[i]);
			trie.AddObj(itm_bry, new Pxd_itm_dow_name(-1, itm_bry, i));	// NOTE: days are base0; 0-6
		}
	}
	private static void Init_suffix(String[] suffix_ary) {
		int len = suffix_ary.length;
		for (int i = 0; i < len; i++) {
			String suffix = suffix_ary[i];
			trie.AddObj(suffix, Pxd_itm_day_suffix.Instance);
		}
	}
	private static void Init_relative() {
		trie.AddObj("today", Pxd_itm_day_relative.Today);
		trie.AddObj("tomorrow", Pxd_itm_day_relative.Tomorrow);
		trie.AddObj("yesterday", Pxd_itm_day_relative.Yesterday);
		trie.AddObj("now", Pxd_itm_time_relative.Now);
		trie.AddObj("next", Pxd_itm_unit_relative.Next);
		trie.AddObj("last", Pxd_itm_unit_relative.Prev);
		trie.AddObj("previous", Pxd_itm_unit_relative.Prev);
		trie.AddObj("this", Pxd_itm_unit_relative.This);
	}
	private static void Init_meridian(boolean is_pm, String... ary) {
		Pxd_itm_meridian meridian = new Pxd_itm_meridian(-1, is_pm);
		for (String itm : ary)
			trie.AddObj(itm, meridian);
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