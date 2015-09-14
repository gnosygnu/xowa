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
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_explode extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_strx_explode;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_explode().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		// 	 * {{#explode:String | delimiter | position | limit}}
		byte[] s = Eval_argx(ctx, src, caller, self);
		int args_len = self.Args_len();
		byte[] dlm = Byte_ascii.Space_bry; int idx = 0, limit = -1;
		if (args_len > 0) {
			dlm = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 0, null);
			if (Bry_.Len_eq_0(dlm)) dlm = Byte_ascii.Space_bry;	// handle empty String; EX: {{#explode:a b||1}}
			if (args_len > 1) {
				byte[] pos_bry = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 1, null);
				if (pos_bry != null) idx = Bry_.To_int_or(pos_bry, 0);
				if (args_len > 2) {
					byte[] limit_bry = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 2, null);
					if (limit_bry != null) limit = Bry_.To_int_or(pos_bry, -1);
				}
			}
		}
		if (idx < 0) {
			int count = Pfunc_count.Count(s, dlm);
			idx = count + idx;
		}
		byte[] rv = Split_and_get_by_idx(s, dlm, idx, limit);
		bfr.Add(rv);
	}
	private static byte[] Split_and_get_by_idx(byte[] src, byte[] dlm, int idx, int limit) {
		int src_len = src.length; int dlm_len = dlm.length;
		int pos = 0; int found = 0;
		while (true) {
			int find_pos = Bry_finder.Find_fwd(src, dlm, pos);
			if (find_pos == Bry_finder.Not_found) break;
			if (found == idx) return Bry_.Mid(src, pos, find_pos);
			pos = find_pos + dlm_len;
			++found;
		}
		return found == idx ? Bry_.Mid(src, pos, src_len) : Bry_.Empty;
	}
}	
