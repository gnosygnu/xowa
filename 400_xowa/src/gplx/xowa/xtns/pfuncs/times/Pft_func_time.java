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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pft_func_time extends Pf_func_base {
	Pft_func_time(boolean utc) {this.utc = utc;} private boolean utc;
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_time;}
	@Override public Pf_func New(int id, byte[] name) {return new Pft_func_time(utc).Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {// REF.MW:ParserFunctions_body.php
		int self_args_len = self.Args_len();
		byte[] arg_fmt = Eval_argx(ctx, src, caller, self);
		Pft_fmt_itm[] fmt_ary = Pft_fmt_itm_.Parse(ctx, arg_fmt);
		byte[] arg_date = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);
		byte[] arg_lang = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1);
		Bry_bfr error_bfr = Bry_bfr.new_();
		DateAdp date = ParseDate(arg_date, utc, error_bfr);
		if (date == null || error_bfr.Len() > 0)
			bfr.Add_str("<strong class=\"error\">").Add_bfr_and_clear(error_bfr).Add_str("</strong>");
		else {
			Xol_lang lang = ctx.Lang();
			if (Bry_.Len_gt_0(arg_lang)) {
				Xol_lang_itm specified_lang_itm = Xol_lang_itm_.Get_by_key(arg_lang);
				if (specified_lang_itm != null) {	// NOTE: if lang_code is bad, then ignore (EX:bad_code)
					Xol_lang specified_lang = ctx.Wiki().App().Lang_mgr().Get_by_key_or_new(arg_lang);
					lang = specified_lang;	
				}
			}
			Pft_func_formatdate.Date_bldr().Format(bfr, ctx.Wiki(), lang, date, fmt_ary);
		}
	}
	public static DateAdp ParseDate(byte[] date, boolean utc, Bry_bfr error_bfr) {
		if (date == Bry_.Empty) return utc ? DateAdp_.Now().XtoUtc() : DateAdp_.Now();
		try {
			DateAdp rv = new Pxd_parser().Parse(date, error_bfr);
			return rv;
		}
		catch (Exception exc) {
			Err_.Noop(exc);
			error_bfr.Add_str("Invalid time");
			return null;
		}
	}
	public static final Pft_func_time _Lcl = new Pft_func_time(false), _Utc = new Pft_func_time(true);
}
class DateAdpTranslator_xapp {
	public static void Translate(Xow_wiki wiki, Xol_lang lang, int type, int val, Bry_bfr bb) {
		lang.Init_by_load_assert();
		byte[] itm_val = lang.Msg_mgr().Val_by_id(type + val); if (itm_val == null) return;
		bb.Add(itm_val);
	}
}
class Pfxtp_roman {
	public static void ToRoman(int num, Bry_bfr bfr) {
		if (num > 3000 || num <= 0) {
			bfr.Add_int_variable(num);
			return;
		}
		int pow10 = 1000;
		for (int i = 3; i > -1; i--) {
			if (num >= pow10) {
				bfr.Add(Names[i][Math_.Trunc(num / pow10)]);
			}
			num %= pow10;
			pow10 /= 10;
		} 
	}
	private static byte[][][] Names = new byte[][][]
		{ Bry_dim2_new_("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X")
		, Bry_dim2_new_("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C")
		, Bry_dim2_new_("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM", "M")
		, Bry_dim2_new_("", "M", "MM", "MMM")
		};
	private static byte[][] Bry_dim2_new_(String... names) {
		int len = names.length;
		byte[][] rv = new byte[len][];
		for (int i = 0; i < len; i++)
			rv[i] = Bry_.new_utf8_(names[i]);
		return rv;
	}
}
