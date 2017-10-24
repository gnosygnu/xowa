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
package gplx.core.btries; import gplx.*; import gplx.core.*;
public class Btrie_slim_itm {
	private Btrie_slim_itm[] ary = Btrie_slim_itm.Ary_empty;
	public Btrie_slim_itm(byte key_byte, Object val, boolean case_any) {this.key_byte = key_byte; this.val = val; this.case_any = case_any;}
	public byte Key_byte() {return key_byte;} private byte key_byte;
	public Object Val() {return val;} public void Val_set(Object val) {this.val = val;} private Object val;
	public boolean Case_any() {return case_any;} private boolean case_any;
	public boolean Ary_is_empty() {return ary == Btrie_slim_itm.Ary_empty;}
	public void Clear() {
		val = null;
		for (int i = 0; i < ary_len; i++)
			ary[i].Clear();
		ary = Btrie_slim_itm.Ary_empty;
		ary_len = ary_max = 0;
	}
	public Btrie_slim_itm Ary_find(byte b) {
		int find_val = (case_any && (b > 64 && b < 91) ? b + 32 : b) & 0xff;// PATCH.JAVA:need to convert to unsigned byte
		int key_val = 0;
		switch (ary_len) {
			case 0: return null;
			case 1:
				Btrie_slim_itm rv = ary[0];
				key_val = rv.Key_byte() & 0xff;// PATCH.JAVA:need to convert to unsigned byte;
				key_val = (case_any && (key_val > 64 && key_val < 91) ? key_val + 32 : key_val);
				return key_val == find_val ? rv : null;
			default:
				int adj = 1;
				int prv_pos = 0;
				int prv_len = ary_len;
				int cur_len = 0;
				int cur_idx = 0;
				Btrie_slim_itm itm = null;
				while (true) {
					cur_len = prv_len / 2;
					if (prv_len % 2 == 1) ++cur_len;
					cur_idx = prv_pos + (cur_len * adj);
					if		(cur_idx < 0)			cur_idx = 0;
					else if (cur_idx >= ary_len)	cur_idx = ary_len - 1;
					itm = ary[cur_idx];
					key_val = itm.Key_byte() & 0xff;	// PATCH.JAVA:need to convert to unsigned byte;
					key_val = (case_any && (key_val > 64 && key_val < 91) ? key_val + 32 : key_val);
					if		(find_val <	 key_val)	adj = -1;
					else if (find_val >	 key_val)	adj =  1;
					else  /*(find_val == cur_val)*/ return itm;
					if (cur_len == 1) {
						cur_idx += adj;
						if (cur_idx < 0 || cur_idx >= ary_len) return null;
						itm = ary[cur_idx];
						return (itm.Key_byte() & 0xff) == find_val ? itm : null;	// PATCH.JAVA:need to convert to unsigned byte;
					}
					prv_len = cur_len;
					prv_pos = cur_idx;
				}
		}
	}
	public Btrie_slim_itm Ary_add(byte b, Object val) {
		int new_len = ary_len + 1;
		if (new_len > ary_max) {
			ary_max += 4;
			ary = (Btrie_slim_itm[])Array_.Resize(ary, ary_max);
		}
		Btrie_slim_itm rv = new Btrie_slim_itm(b, val, case_any);
		ary[ary_len] = rv;
		ary_len = new_len;
		synchronized (ByteHashItm_sorter.Instance) {// TS: DATE:2016-07-06
			ByteHashItm_sorter.Instance.Sort(ary, ary_len);
		}
		return rv;
	}
	public void Ary_del(byte b) {
		boolean found = false;
		for (int i = 0; i < ary_len; i++) {
			if (found) {
				if (i < ary_len - 1)
					ary[i] = ary[i + 1];
			}
			else {
				if (b == ary[i].Key_byte()) found = true;
			}
		}
		if (found) --ary_len;
	}
	public static final    Btrie_slim_itm[] Ary_empty = new Btrie_slim_itm[0]; int ary_len = 0, ary_max = 0;
}
class ByteHashItm_sorter {// quicksort
	Btrie_slim_itm[] ary; int ary_len;
	public void Sort(Btrie_slim_itm[] ary, int ary_len) {
		if (ary == null || ary_len < 2) return;
		this.ary = ary;
		this.ary_len = ary_len;
		Sort_recurse(0, ary_len - 1);
	}
	private void Sort_recurse(int lo, int hi) {
		int i = lo, j = hi;			
		int mid = ary[lo + (hi-lo)/2].Key_byte()& 0xFF;				// get mid itm
		while (i <= j) {											// divide into two lists
			while ((ary[i].Key_byte() & 0xFF) < mid)				// if lhs.cur < mid, then get next from lhs
				i++;				
			while ((ary[j].Key_byte() & 0xFF) > mid)				// if rhs.cur > mid, then get next from rhs
				j--;

			// lhs.cur > mid && rhs.cur < mid; switch lhs.cur and rhs.cur; increase i and j
			if (i <= j) {
				Btrie_slim_itm tmp = ary[i];
				ary[i] = ary[j];
				ary[j] = tmp;
				i++;
				j--;
			}
		}
		if (lo < j) Sort_recurse(lo, j);
		if (i < hi) Sort_recurse(i, hi);
	}
	public static final    ByteHashItm_sorter Instance = new ByteHashItm_sorter(); ByteHashItm_sorter() {}
}
