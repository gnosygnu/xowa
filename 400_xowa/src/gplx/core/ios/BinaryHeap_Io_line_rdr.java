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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.lists.*;
class BinaryHeap_Io_line_rdr {
	public BinaryHeap_Io_line_rdr(ComparerAble comparer) {this.comparer = comparer;} ComparerAble comparer;
	Io_line_rdr[] ary = Ary_empty; int ary_len = 0, ary_max = 0;
	public int Len() {return ary_len;}
	public void Add(Io_line_rdr itm) {
		int new_len = ary_len + 1;
		if (new_len > ary_max) {
			ary_max = new_len * 2;
			ary = (Io_line_rdr[])Array_.Resize(ary, ary_max);
		}
		ary[ary_len] = itm;
		ary_len = new_len;
		Add_move_up(ary_len - 1);
	}
	public Io_line_rdr Pop() {
		if (ary_len == 0) return null;
		Io_line_rdr rv = ary[0];
		--ary_len;
		if (ary_len > 0) {
			ary[0] = ary[ary_len];
			Pop_move_down(0);
		}
		return rv;
	}
	public void Rls() {
		for (int i = 0; i < ary_len; i++) {
			Io_line_rdr rdr = ary[i];
			if (rdr != null) rdr.Rls();
			ary[i] = null;
		}
		ary = null;
		ary_len = 0;
	}
	private void Add_move_up(int pos) {
		while (pos > 0) {
			int owner = (pos - 1) / 2;
			if (Compare(pos, owner) > CompareAble_.Less) break;
			Swap(pos, owner);
			pos = owner;
		}
	}
	private void Pop_move_down(int pos) {
		int idx_last = ary_len - 1;
		while (pos < ary_len / 2) {
			int sub = 2 * pos + 1;
			if (sub < idx_last && Compare(sub, sub + 1) > CompareAble_.Same)
				++sub;
			if (Compare(pos, sub) < CompareAble_.More) break;
			Swap(pos, sub);
			pos = sub;
		}
	}
	int Compare(int lhs_idx, int rhs_idx) {
		Io_line_rdr lhs = ary[lhs_idx], rhs = ary[rhs_idx];
		lhs_itm.Set(lhs); rhs_itm.Set(rhs);
		return comparer.compare(lhs_itm, rhs_itm); 
//			return Bry_.Compare(lhs.Bfr(), lhs.Key_pos_bgn(), lhs.Key_pos_end(), rhs.Bfr(), rhs.Key_pos_bgn(), rhs.Key_pos_end());
	}	Io_sort_split_itm lhs_itm = new Io_sort_split_itm(), rhs_itm = new Io_sort_split_itm();
	private void Swap(int lhs_idx, int rhs_idx) {
		Io_line_rdr tmp = ary[lhs_idx];
		ary[lhs_idx] = ary[rhs_idx];
		ary[rhs_idx] = tmp;
	}
	private static final Io_line_rdr[] Ary_empty = new Io_line_rdr[0]; 
}
