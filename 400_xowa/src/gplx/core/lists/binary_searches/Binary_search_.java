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
package gplx.core.lists.binary_searches; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
public class Binary_search_ {
	public static int Search(CompareAble[] ary	, CompareAble val) {return Search(new Binary_search_grp__ary(ary)	, new Binary_search_cmp__comparable(val));}
	public static int Search(Object[] ary		, Binary_comparer comparer, Object val) {return Search(new Binary_search_grp__ary(ary), new Binary_search_cmp__comparer(comparer, val));}
	private static int Search(Binary_search_grp grp, Binary_search_cmp cmp) {
		int grp_len = grp.Len();
		if (grp_len == 1) return 0;						// if 1 item, return 0;
		if (grp_len == 0) return Bry_find_.Not_found;	// if 0 item, return -1

		// init
		int interval = grp_len / 2;
		int pos = interval - List_adp_.Base1;
		int pos_last = grp_len - 1;
		int pos_prv = -1;
		int loop_count = 0;
		while (loop_count++ < 32) {	// 32=32-bit integer
			Object lo = grp.Get_at(pos);
			Object hi = pos + 1 == grp_len ? null : grp.Get_at(pos + 1);
			int adj = 0;
			int lo_comp = cmp.Compare(lo);
			if (lo_comp == CompareAble_.Less)		// val is < lo; search slots below
				adj = -1;
			else {
				if (hi == null) return pos;			// hi is null when at last slot in ary
				int hi_comp = cmp.Compare(hi);
				switch (hi_comp) {
					case CompareAble_.More:			// val is > hi; search slots above 
						adj = 1;
						break;
					case CompareAble_.Same:			// val is > hi; search slots above 
						return pos + 1;
					case CompareAble_.Less:			// val is > lo and < hi; return slot 
						return pos;
				}
			}
			interval /= 2;
			if (interval == 0) interval = 1;		// do not allow 0 intervals; pos must always change; 
			pos += (interval * adj);
			if (pos == 0 && pos_prv == 0) break;	// NOTE: this will only happen when 1st member is not ""
			if 		(pos < 0) 			pos = 0;	
			else if (pos > pos_last)	pos = pos_last;
			pos_prv = pos;
		}
		return Bry_find_.Not_found;	// should only occur if (a) ary's 0th slot is not ""; or (b) some unknown error
	}
}
