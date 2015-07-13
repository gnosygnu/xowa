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
package gplx.xowa.xtns.pfuncs.stringutils; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pfunc_pos extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_strx_pos;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_pos().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr trg) {
		byte[] str = Eval_argx(ctx, src, caller, self);
		int self_args_len = self.Args_len();
		byte[] find = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 0, Byte_ascii.Space_bry);	// MW: use " " if find is missing
		byte[] offset_bry = Pf_func_.Eval_arg_or(ctx, src, caller, self, self_args_len, 1, null);
		int offset = offset_bry == null ? 0 : Bry_.Xto_int(offset_bry);
		int pos = Bry_finder.Find_fwd(str, find, offset);
		if (pos != Bry_finder.Not_found)
			trg.Add_int_variable(pos);
	}
}	
