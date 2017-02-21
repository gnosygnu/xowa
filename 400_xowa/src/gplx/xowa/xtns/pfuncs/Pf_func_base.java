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
package gplx.xowa.xtns.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public abstract class Pf_func_base implements Pf_func {
	public byte Defn_tid() {return Xot_defn_.Tid_func;}
	public byte[] Name() {return name;} public Pf_func_base Name_(byte[] v) {name = v; name_len = v.length; return this;} private byte[] name = Bry_.Empty; int name_len = 0;
	@gplx.Virtual public boolean Func_require_colon_arg() {return false;}
	public boolean Defn_require_colon_arg() {return this.Func_require_colon_arg();}
	public int Cache_size() {return 1024;}	// arbitrary size
	public abstract int Id();
	public void Rls() {name = null; argx_dat = null;}
	public abstract Pf_func New(int id, byte[] name);
	public Xot_defn Clone(int id, byte[] name) {return New(id, name);}
	public abstract void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src);
	public byte[] Argx_dat() {return argx_dat;} public void Argx_dat_(byte[] v) {argx_dat = v;} private byte[] argx_dat = Bry_.Empty;
	public byte[] Eval_argx(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self) {
		if (argx_dat == Bry_.Empty) {
			Arg_itm_tkn name_val_tkn = self.Name_tkn().Val_tkn();
			int subs_len = name_val_tkn.Subs_len();
			if (subs_len > 0) {
				Bry_bfr tmp = Bry_bfr_.New();
				for (int i = 0; i < subs_len; i++)
					name_val_tkn.Subs_get(i).Tmpl_evaluate(ctx, src, caller, tmp);
				argx_dat = tmp.To_bry_and_clear_and_trim();
			}
		}
		return argx_dat;
	}		
	public byte[] Eval_argx_or_null(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, byte[] func_name) {
		if (argx_dat == Bry_.Empty) {
			Arg_nde_tkn name_tkn = self.Name_tkn();
			Arg_itm_tkn name_val_tkn = name_tkn.Val_tkn();
			int subs_len = name_val_tkn.Subs_len();
			if (subs_len == 0) {	// no subs; either {{#func}} or {{#func:}}
				int src_bgn = name_tkn.Src_bgn();
				int colon_pos = Bry_find_.Find_bwd(src, Byte_ascii.Colon, self.Src_end(), src_bgn);	// look for ":"; NOTE: used to be src_bgn - 1, but this would always search one character too many; DATE:2014-02-11
				if (colon_pos == Bry_find_.Not_found)		// no colon; EX: {{#func}}
					return Eval_arg_or_null_is_null;
				else {									// colon found; EX: {{#func:}}
					if (Bry_.Match_bwd_any(src, colon_pos - 1, src_bgn - 1, func_name))  // #func == func_name; EX: {{NAMESPACE:}}
						return Eval_arg_or_null_is_empty;
					else								// #func != func_name; assume subst: or safesubst:; EX: {{safesubst:NAMESPACE}}; NOTE: can check subst / safesubs trie, but will be expensive; also, only Pfunc_ttl calls this function
						return Eval_arg_or_null_is_null;
				}
			}
			else {
				Bry_bfr tmp = Bry_bfr_.New();
				for (int i = 0; i < subs_len; i++)
					name_val_tkn.Subs_get(i).Tmpl_evaluate(ctx, src, caller, tmp);
				argx_dat = tmp.To_bry_and_clear_and_trim();
			}
		}
		return argx_dat;
	}
	public static final    byte[] Eval_arg_or_null_is_null = null, Eval_arg_or_null_is_empty = Bry_.Empty;
}
