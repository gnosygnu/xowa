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
import gplx.core.intls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_pad extends Pf_func_base {
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {// REF.MW: CoreParserFunctions.php|pad
		int self_args_len = self.Args_len();
		byte[] val = Eval_argx(ctx, src, caller, self);
		int val_len = Utf8_.Len_of_bry(val);			// NOTE: length must be in chars, not bytes, else won't work for non-ASCII chars; EX:niǎo has length of 4, not 5; PAGE:zh.d:不 DATE:2014-08-27
		
		byte[] pad_len = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);
		int pad_len_int = Bry_.To_int_or(pad_len, 0, pad_len.length, -1);
		if (pad_len_int == -1) {bfr.Add(val); return;}	// NOTE: if pad_len is non-int, add val and exit silently; EX: {{padleft: a|bad|0}}
		if (pad_len_int > 500) pad_len_int = 500;		// MW: force it to be <= 500
		
		byte[] pad_str = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 1, Ary_pad_dflt);
		int pad_str_len = pad_str.length;
		if (pad_str_len == 0) {bfr.Add(val); return;}// NOTE: pad_str is entirely empty or whitespace; add val and return; SEE:NOTE_1

		int pad_idx = 0;
		if (pad_dir_right) bfr.Add(val);
		for (int val_idx = val_len; val_idx < pad_len_int; val_idx++) {
			byte b = pad_str[pad_idx];
			int b_len = gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
			if (b_len == 1)
				bfr.Add_byte(b);
			else
				bfr.Add_mid(pad_str, pad_idx, pad_idx + b_len);
			pad_idx += b_len;
			if (pad_idx >= pad_str_len) pad_idx = 0;
		}
		if (!pad_dir_right) bfr.Add(val);
	}
	boolean pad_dir_right; static final byte[] Ary_pad_dflt = Bry_.new_a7("0");
	public Pfunc_pad(int id, boolean pad_dir_right) {this.id = id; this.pad_dir_right = pad_dir_right;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_pad(id, pad_dir_right).Name_(name);}
}	
/*
NOTE_1
difference between following:
-	{{padleft:a|4}} -> 000a
	Pad_arg omitted: default to 0	
-	{{padleft:a|4| \n\t}} -> a
	Pad_arg specified, but empty: add val only	
*/