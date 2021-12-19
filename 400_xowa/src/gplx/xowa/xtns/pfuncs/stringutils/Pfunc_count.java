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
package gplx.xowa.xtns.pfuncs.stringutils;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_count extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_strx_count;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_count().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(BryWtr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] str = Eval_argx(ctx, src, caller, self);
		int self_args_len = self.Args_len();
		byte[] find = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 0, null); if (find == null) find = AsciiByte.SpaceBry;
		bfr.AddIntVariable(Count(str, find));
	}
	public static int Count(byte[] src, byte[] find) {
		int src_len = src.length; int find_len = find.length;
		int pos = 0;
		int rv = 0;
		while (true) {
			int find_pos = BryFind.FindFwd(src, find, pos, src_len);
			if (find_pos == BryFind.NotFound) break;
			pos = find_pos + find_len;
			++rv;
		}
		return rv;
	}
}	
