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
package gplx.xowa.xtns.pfuncs.strings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_tag extends Pf_func_base {// REF:CoreParserFunctions.php
	@Override public int Id() {return Xol_kwd_grp_.Id_misc_tag;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_tag().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		// make <xnde> based on {{#tag}}; EX: {{#tag:ref|a|name=1}} -> <ref name='1'>a</ref>
		Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();			
		try {
			// get vars
			byte[] tag_name = Eval_argx(ctx, src, caller, self); if (tag_name.length == 0) return;
			
			// open tag
			tmp_bfr.Add_byte(Byte_ascii.Lt).Add(tag_name);	// EX: "<ref"

			// iterate args and build attributes; EX: "|a=1|b=2" -> "a='1' b='2'"
			int args_len = self.Args_len();
			if (args_len > 1) {	// NOTE: starting from 1 b/c 0 is innerText
				Pfunc_tag_kvp_wtr kvp_wtr = new Pfunc_tag_kvp_wtr();
				for (int i = 1; i < args_len; i++) {
					byte[] arg = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, args_len, i);	// NOTE: must evaluate arg; don't try to parse arg_tkn's key / val separately; EX:{{#tag:pre|a|{{#switch:a|a=id}}=c}}
					if (arg.length == 0) continue;	// skip empty atrs						
					tmp_bfr.Add_byte(Byte_ascii.Space);			// write space between html_args
					kvp_wtr.Write_as_html_atr(tmp_bfr, arg);	// write html_arg
				}
			}
			tmp_bfr.Add_byte(Byte_ascii.Gt);	// EX: ">"

			// add innerText;
			if (args_len > 0)	// handle no args; EX: "{{#tag:ref}}" -> "<ref></ref>"
				tmp_bfr.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, args_len, 0));

			// close tag
			tmp_bfr.Add_byte(Byte_ascii.Lt).Add_byte(Byte_ascii.Slash).Add(tag_name).Add_byte(Byte_ascii.Gt);	// EX: "</ref>"

			// add to UNIQ hash; DATE:2017-03-31
			byte[] val = tmp_bfr.To_bry_and_clear();
			byte[] key = ctx.Wiki().Parser_mgr().Uniq_mgr().Add(tag_name, val);
			bfr.Add(key);
		}
		finally {tmp_bfr.Mkr_rls();}
	}
}
