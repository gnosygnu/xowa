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
package gplx.xowa; import gplx.*;
class Pf_str_pad extends Pf_func_base {
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {// REF.MW: CoreParserFunctions.php|pad
		int self_args_len = self.Args_len();
		byte[] val = Eval_argx(ctx, src, caller, self);
		int val_len = val.length;
		
		byte[] pad_len = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);
		int pad_len_int = Bry_.X_to_int_or(pad_len, 0, pad_len.length, -1);
		if (pad_len_int == -1) {bfr.Add(val); return;}// NOTE: if pad_len is non-int, add val and exit silently; EX: {{padleft: a|bad|0}}
		if (pad_len_int > 500) pad_len_int = 500;	// MW: force to be <= 500
		
		byte[] pad_str = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 1, Ary_pad_dflt);
		int pad_str_len = pad_str.length;
		if (pad_str_len == 0) {bfr.Add(val); return;}// NOTE: pad_str is entirely empty or whitespace; add val and return; SEE:NOTE_1

		int pad_idx = 0;
		if (pad_dir_right) bfr.Add(val);
		for (int val_idx = val_len; val_idx < pad_len_int; val_idx++) {
			byte b = pad_str[pad_idx];
			int b_len = gplx.intl.Utf8_.Len_of_char_by_1st_byte(b);
			if (b_len == 1)
				bfr.Add_byte(b);
			else
				bfr.Add_mid(pad_str, pad_idx, pad_idx + b_len);
			pad_idx += b_len;
			if (pad_idx >= pad_str_len) pad_idx = 0;
		}
		if (!pad_dir_right) bfr.Add(val);
	}
	boolean pad_dir_right; static final byte[] Ary_pad_dflt = Bry_.new_ascii_("0");
	public Pf_str_pad(int id, boolean pad_dir_right) {this.id = id; this.pad_dir_right = pad_dir_right;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pf_str_pad(id, pad_dir_right).Name_(name);}
}	
/*
NOTE_1
difference between following:
-	{{padleft:a|4}} -> 000a
	Pad_arg omitted: default to 0	
-	{{padleft:a|4| \n\t}} -> a
	Pad_arg specified, but empty: add val only	
*/