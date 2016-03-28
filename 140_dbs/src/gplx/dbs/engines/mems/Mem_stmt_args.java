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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.criterias.*;
public class Mem_stmt_args {
	private final List_adp list = List_adp_.new_();
	private int cur_idx = -1;
	public void Clear() {list.Clear(); cur_idx = -1;}
	public void Add(String k, Object v) {list.Add(Keyval_.new_(k, v));}
	public Keyval Get_next() {
		int idx = ++cur_idx;
		return idx == list.Count() ? null: (Keyval)list.Get_at(idx);
	}
}
class Mem_stmt_args_ {
	public static void Fill(Mem_stmt_args args, Criteria crt) {
		int tid = crt.Tid();
		if (tid == Criteria_.Tid_wrapper) {
			Criteria_fld fld = (Criteria_fld)crt;
			Criteria sub = fld.Crt();
			String fld_key = fld.Key();
			switch (sub.Tid()) {
				case Criteria_.Tid_eq:
				case Criteria_.Tid_comp:
				case Criteria_.Tid_like:
				case Criteria_.Tid_iomatch:
					Keyval kvp = args.Get_next();
					if (!String_.Eq(kvp.Key(), fld.Key())) throw Err_.new_("db", "fld_crt.key mismatch", "fld.key", fld_key, "kvp.key", kvp.Key());
					sub.Val_as_obj_(kvp.Val());
					break;
				case Criteria_.Tid_in:
					Criteria_in crt_in = (Criteria_in)sub;
					Object[] ary = crt_in.Ary(); int ary_len = crt_in.Ary_len();
					for (int i = 0; i < ary_len; ++i)
						ary[i] = args.Get_next().Val();
					break;
				case Criteria_.Tid_between:
					Criteria_between crt_between = (Criteria_between)sub;
					crt_between.Lo_((Comparable)(args.Get_next()).Val());
					crt_between.Hi_((Comparable)(args.Get_next()).Val());
					break;
				default: throw Err_.new_unhandled(sub.Tid());
			}
		}
		else {
			switch (tid) {
				case Criteria_.Tid_const: break;	// true / false; nothing to fill
				case Criteria_.Tid_and:
				case Criteria_.Tid_or:
					Criteria_bool_base crt_dual = (Criteria_bool_base)crt;
					Fill(args, crt_dual.Lhs());
					Fill(args, crt_dual.Rhs());
					break;
				case Criteria_.Tid_not:
					Criteria_not crt_not = (Criteria_not)crt;
					Fill(args, crt_not.Crt());
					break;
				default: throw Err_.new_unhandled(tid);
			}
		}
	}
}
