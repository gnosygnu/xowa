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
import gplx.core.btries.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pft_func_formatdate extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_str_formatdate;}
	@Override public Pf_func New(int id, byte[] name) {return new Pft_func_formatdate().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		int self_args_len = self.Args_len();
		byte[] date_bry = Eval_argx(ctx, src, caller, self);
		byte[] fmt_bry = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);
		if (fmt_bry == Bry_.Empty) {bfr.Add(date_bry); return;}	// no format given; add self;
		int fmt_bry_len = fmt_bry.length;
		Object o = trie.Match_bgn(fmt_bry, 0, fmt_bry_len);
		if (o == null 
			|| o == Fmt_itms_default) {// NOOP for default?
			bfr.Add(date_bry);
			return;
		}
		Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();
		DateAdp date = null;
		try {date = Pft_func_time.ParseDate(date_bry, false, tmp_bfr);}
		finally {tmp_bfr.Mkr_rls();}
		if (date == null) {bfr.Add(date_bry); return;}	// date not parseable; return self; DATE:2014-04-13
		ctx.Wiki().Parser_mgr().Date_fmt_bldr().Format(bfr, ctx.Wiki(), ctx.Lang(), date, (Pft_fmt_itm[])o);
	}
	private static final    Pft_fmt_itm[] Fmt_itms_default = new Pft_fmt_itm[0];
	private static final    Btrie_fast_mgr trie = Btrie_fast_mgr.cs()
		.Add("dmy"			, new Pft_fmt_itm[] {Pft_fmt_itm_.Day_int, Pft_fmt_itm_.Byte_space, Pft_fmt_itm_.Month_name, Pft_fmt_itm_.Byte_space, Pft_fmt_itm_.Year_len4})
		.Add("mdy"			, new Pft_fmt_itm[] {Pft_fmt_itm_.Month_name, Pft_fmt_itm_.Byte_space, Pft_fmt_itm_.Day_int, Pft_fmt_itm_.Byte_comma, Pft_fmt_itm_.Byte_space, Pft_fmt_itm_.Year_len4})
		.Add("ymd"			, new Pft_fmt_itm[] {Pft_fmt_itm_.Year_len4, Pft_fmt_itm_.Byte_space, Pft_fmt_itm_.Month_name, Pft_fmt_itm_.Byte_space, Pft_fmt_itm_.Day_int})
		.Add("ISO 8601"		, new Pft_fmt_itm[] {Pft_fmt_itm_.Year_len4, Pft_fmt_itm_.Byte_dash, Pft_fmt_itm_.Month_int_len2, Pft_fmt_itm_.Byte_dash, Pft_fmt_itm_.Day_int_len2})
		.Add("default"		, Fmt_itms_default)
	;
}
