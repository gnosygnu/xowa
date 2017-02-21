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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.primitives.*; import gplx.xowa.xtns.pfuncs.times.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_titleparts extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {// REF.MW:ParserFunctions_body.php
		// get argx
		int args_len = self.Args_len();
		byte[] argx = Eval_argx(ctx, src, caller, self); if (argx == null) return; // no argx; return empty
		Xoa_ttl argx_as_ttl = Xoa_ttl.Parse(ctx.Wiki(), argx, 0, argx.length); // transform to title in order to upper first, replace _, etc..
		if (argx_as_ttl == null)	{bfr.Add(argx); return;}	// NOTE: argx_as_ttl will be null if invalid, such as [[a|b]]; PAGE:en.w:owl and {{taxobox/showtaxon|Dinosauria}}
		else						argx = argx_as_ttl.Full_txt_w_ttl_case();
		// get parts_len
		byte[] parts_len_ary = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, args_len, 0);
		int parts_len = parts_len_ary == Bry_.Empty ? Int_.Min_value : Bry_.To_int_or(parts_len_ary, Int_.Max_value);
		if (parts_len == Int_.Max_value) {// len is not an int; EX: "a";
			ctx.Msg_log().Add_itm_none(Pfunc_titleparts_log.Len_is_invalid, src, caller.Src_bgn(), caller.Src_end());
			bfr.Add(argx);
			return;
		}
		// get parts_bgn
		byte[] parts_bgn_arg = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, args_len, 1);
		int parts_bgn = parts_bgn_arg == Bry_.Empty ? 0 : Bry_.To_int_or(parts_bgn_arg, Int_.Min_value);
		if (parts_bgn == Int_.Min_value) {// parts_bgn is not an int; EX: "a"
			ctx.Msg_log().Add_itm_none(Pfunc_titleparts_log.Bgn_is_invalid, src, caller.Src_bgn(), caller.Src_end());
			parts_bgn = 0;	// NOTE: do not return
		}
		else if (parts_bgn > 0) parts_bgn -= List_adp_.Base1;	// adjust for base1
		bfr.Add(TitleParts(argx, parts_len, parts_bgn));
	}
	private byte[] TitleParts(byte[] src, int parts_len, int parts_bgn) {
		// find dlm positions; EX: ab/cde/f/ will have -1,2,6,8
		synchronized (dlms_ary) {// LOCK:static-obj; DATE:2016-07-06
			int src_len = src.length; int dlms_ary_len = 1;	// 1 b/c dlms_ary[0] will always be -1
			for (int i = 0; i < src_len; i++) {
				if (src[i] == Byte_ascii.Slash) dlms_ary[dlms_ary_len++] = i;
			}
			dlms_ary[dlms_ary_len] = src_len;	// put src_len into last dlms_ary; makes dlms_ary[] logic easier
			// calc bgn_idx; must occur before adjust parts_len
			int bgn_idx = parts_bgn > -1 ? parts_bgn : parts_bgn + dlms_ary_len;			// negative parts_bgn means calc from end of dlms_ary_len; EX a/b/c|1|-1 means start from 2
			if (	bgn_idx < 0																// bgn_idx can be negative when parts_len is negative and greater than array; EX: {{#titleparts:a/b|-1|-2}} results in dlms_ary_len of 1 and parts_bgn of -2 which will be parts_bgn of -1
				||	bgn_idx > dlms_ary_len) return Bry_.Empty;							// if bgn > len, return ""; EX: {{#titleparts:a/b|1|3}} should return ""
			// adjust parts_len for negative/null
			if		(parts_len == Int_.Min_value) parts_len = dlms_ary_len;					// no parts_len; default to dlms_ary_len
			else if (parts_len < 0)	{														// neg parts_len; shorten parts now and default to rest of String; EX: a/b/c|-1 -> makes String a/b/c and get 2 parts
				dlms_ary_len += parts_len;
				parts_len = dlms_ary_len;
				if (parts_len < 1) return Bry_.Empty;									// NOTE: if zerod'd b/c of neg length, return empty; contrast with line below; EX: a/b/c|-4
			}		
			if (parts_len == 0) return src;													// if no dlms, return orig
			// calc idxs for extraction
			int bgn_pos = dlms_ary[bgn_idx] + 1; // +1 to start after slash
			int end_idx = bgn_idx + parts_len;
			int end_pos = end_idx > dlms_ary_len ? dlms_ary[dlms_ary_len] : dlms_ary[end_idx];
			if (end_pos < bgn_pos) return Bry_.Empty;
			return Bry_.Mid(src, bgn_pos, end_pos);
		}
	}	private static final    int[] dlms_ary = new Int_ary_bldr(255).Set(0, -1).Xto_int_ary();	// set pos0 to -1; makes +1 logic easier
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_titleparts;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_titleparts().Name_(name);}
}
