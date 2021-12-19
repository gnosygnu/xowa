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
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateNow;
import gplx.types.basics.utls.MathUtl;
import gplx.xowa.*;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pft_func_time extends Pf_func_base {
	Pft_func_time(boolean utc) {this.utc = utc;} private boolean utc;
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_time;}
	@Override public Pf_func New(int id, byte[] name) {return new Pft_func_time(utc).Name_(name);}
	@Override public void Func_evaluate(BryWtr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {// REF.MW:ParserFunctions_body.php
		int self_args_len = self.Args_len();
		byte[] arg_fmt = Eval_argx(ctx, src, caller, self);
		Pft_fmt_itm[] fmt_ary = Pft_fmt_itm_.Parse(ctx, arg_fmt);
		byte[] arg_date = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);
		byte[] arg_lang = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1);
		BryWtr error_bfr = BryWtr.New();
		GfoDate date = ParseDate(arg_date, utc, error_bfr);
		Xowe_wiki wiki = ctx.Wiki();
		if (date == null || error_bfr.Len() > 0)
			bfr.AddStrA7("<strong class=\"error\">").AddBfrAndClear(error_bfr).AddStrA7("</strong>");
		else {
			Xol_lang_itm lang = ctx.Lang();
			if (BryUtl.IsNotNullOrEmpty(arg_lang)) {
				Xol_lang_stub specified_lang_itm = Xol_lang_stub_.Get_by_key_or_null(arg_lang);
				if (specified_lang_itm != null) {	// NOTE: if lang_code is bad, then ignore (EX:bad_code)
					Xol_lang_itm specified_lang = wiki.Appe().Lang_mgr().Get_by_or_load(arg_lang);
					lang = specified_lang;	
				}
			}
			wiki.Parser_mgr().Date_fmt_bldr().Format(bfr, wiki, lang, date, fmt_ary);
		}
	}
	public static GfoDate ParseDate(byte[] date, boolean utc, BryWtr error_bfr) {
		if (date == BryUtl.Empty) return utc ? GfoDateNow.Get().ToUtc() : GfoDateNow.Get();
		try {
			GfoDate rv = new Pxd_parser().Parse(date, error_bfr);
			return rv;
		}
		catch (Exception exc) {
			error_bfr.AddStrA7("Invalid time");
			return null;
		}
	}
	public static final Pft_func_time _Lcl = new Pft_func_time(false), _Utc = new Pft_func_time(true);
}
class DateAdpTranslator_xapp {
	public static void Translate(Xowe_wiki wiki, Xol_lang_itm lang, int type, int val, BryWtr bb) {
		byte[] itm_val = lang.Msg_mgr().Val_by_id(type + val); 
		if (itm_val != null)
			bb.Add(itm_val);
	}
}
class Pfxtp_roman {
	public static void ToRoman(int num, BryWtr bfr) {
		if (num > 3000 || num <= 0) {
			bfr.AddIntVariable(num);
			return;
		}
		int pow10 = 1000;
		for (int i = 3; i > -1; i--) {
			if (num >= pow10) {
				bfr.Add(Names[i][MathUtl.Trunc(num / pow10)]);
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
			rv[i] = BryUtl.NewU8(names[i]);
		return rv;
	}
}
