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
package gplx;
public class ByteTrieMgr_slim {
	ByteTrieMgr_slim(boolean case_match) {root = new ByteTrieItm_slim(Byte_.Zero, null, !case_match);}	private ByteTrieItm_slim root;
	public int Count() {return count;} private int count;
	public int Match_pos() {return match_pos;} private int match_pos;
	public Object MatchAtCurExact(byte[] src, int bgn_pos, int end_pos) {
		Object rv = Match(src[bgn_pos], src, bgn_pos, end_pos);
		return rv == null ? null : match_pos - bgn_pos == end_pos - bgn_pos ? rv : null;
	}
	public Object MatchAtCur(byte[] src, int bgn_pos, int end_pos) {return Match(src[bgn_pos], src, bgn_pos, end_pos);}
	public Object Match(byte b, byte[] src, int bgn_pos, int src_len) {
		Object rv = null; int cur_pos = match_pos = bgn_pos;
		ByteTrieItm_slim cur = root;
		while (true) {
			ByteTrieItm_slim nxt = cur.Ary_find(b); if (nxt == null) return rv;	// nxt does not hav b; return rv;
			++cur_pos;
			if (nxt.Ary_is_empty()) {match_pos = cur_pos; return nxt.Val();}	// nxt is leaf; return nxt.Val() (which should be non-null)
			Object nxt_val = nxt.Val();
			if (nxt_val != null) {match_pos = cur_pos; rv = nxt_val;}			// nxt is node; cache rv (in case of false match)
			if (cur_pos == src_len) return rv;									// increment cur_pos and exit if src_len
			b = src[cur_pos];
			cur = nxt;
		}
	}
	public ByteTrieMgr_slim Add_str_byte(String key, byte val)		{return Add(Bry_.new_utf8_(key), Byte_obj_val.new_(val));}
	public ByteTrieMgr_slim Add_bry(String key, String val)			{return Add(Bry_.new_utf8_(key), Bry_.new_utf8_(val));}
	public ByteTrieMgr_slim Add_bry(String key, byte[] val)			{return Add(Bry_.new_utf8_(key), val);}
	public ByteTrieMgr_slim Add_bry(byte[] v)						{return Add(v, v);}
	public ByteTrieMgr_slim Add_bry_bval(byte b, byte val)			{return Add(new byte[] {b}, Byte_obj_val.new_(val));}
	public ByteTrieMgr_slim Add_bry_bval(byte[] bry, byte val)		{return Add(bry, Byte_obj_val.new_(val));}
	public ByteTrieMgr_slim Add_str_byte__many(byte val, String... ary) {
		int ary_len = ary.length;
		Byte_obj_val bval = Byte_obj_val.new_(val);
		for (int i = 0; i < ary_len; i++)
			Add(Bry_.new_utf8_(ary[i]), bval);
		return this;
	}
	public ByteTrieMgr_slim Add_stub(String key, byte val)		{byte[] bry = Bry_.new_utf8_(key); return Add(bry, new ByteTrie_stub(val, bry));}
	public ByteTrieMgr_slim Add_stubs(byte[][] ary)				{return Add_stubs(ary, ary.length);}
	public ByteTrieMgr_slim Add_stubs(byte[][] ary, int ary_len) {
		for (byte i = 0; i < ary_len; i++) {
			byte[] bry = ary[i];
			this.Add(bry, new ByteTrie_stub(i, bry));
		}
		return this;
	}
	public ByteTrieMgr_slim Add(String key, Object val) {return Add(Bry_.new_utf8_(key), val);}
	public ByteTrieMgr_slim Add(byte[] key, Object val) {
		if (val == null) throw Err_.new_("null objects cannot be registered").Add("key", String_.new_utf8_(key));
		int key_len = key.length; int key_end = key_len - 1;
		ByteTrieItm_slim cur = root;
		for (int i = 0; i < key_len; i++) {
			byte b = key[i];
			if (root.Case_any() && (b > 64 && b < 91)) b += 32;
			ByteTrieItm_slim nxt = cur.Ary_find(b);
			if (nxt == null)
				nxt = cur.Ary_add(b, null);
			if (i == key_end)
				nxt.Val_set(val);
			cur = nxt;
		}
		count++; // FUTURE: do not increment if replacing value
		return this;
	}
	public void Del(byte[] key) {
		int key_len = key.length;
		ByteTrieItm_slim cur = root;
		for (int i = 0; i < key_len; i++) {
			byte b = key[i];
			ByteTrieItm_slim nxt = cur.Ary_find(b);
			if (nxt == null) break;
			Object nxt_val = nxt.Val();
			if (nxt_val == null)	// cur is end of chain; remove entry; EX: Abc and at c
				cur.Ary_del(b);
			else					// cur is mid of chain; null out entry
				nxt.Val_set(null);
			cur = nxt;
		}
		count--; // FUTURE: do not decrement if not found
	}
	public byte[] Replace(Bry_bfr tmp_bfr, byte[] src, int bgn, int end) {
		int pos = bgn;
		boolean dirty = false;
		while (pos < end) {
			byte b = src[pos];
			Object o = this.Match(b, src, pos, end);
			if (o == null) {
				if (dirty)
					tmp_bfr.Add_byte(b);
				pos++;
			}
			else {
				if (!dirty) {
					tmp_bfr.Add_mid(src, bgn, pos);
					dirty = true;
				}
				tmp_bfr.Add((byte[])o);
				pos = match_pos;
			}
		}
		return dirty ? tmp_bfr.XtoAryAndClear() : src;
	}
	public void Clear() {root.Clear(); count = 0;}
	public static ByteTrieMgr_slim cs_()			{return new ByteTrieMgr_slim(true);}
	public static ByteTrieMgr_slim ci_ascii_()		{return new ByteTrieMgr_slim(false);}
	public static ByteTrieMgr_slim ci_utf_8_()		{return new ByteTrieMgr_slim(false);}
	public static ByteTrieMgr_slim new_(boolean v)		{return new ByteTrieMgr_slim(v);}
}
class ByteTrieItm_slim {
	private ByteTrieItm_slim[] ary = ByteTrieItm_slim.Ary_empty;
	public ByteTrieItm_slim(byte key_byte, Object val, boolean case_any) {this.key_byte = key_byte; this.val = val; this.case_any = case_any;}
	public byte Key_byte() {return key_byte;} private byte key_byte;
	public Object Val() {return val;} public void Val_set(Object val) {this.val = val;} Object val;
	public boolean Case_any() {return case_any;} private boolean case_any;
	public boolean Ary_is_empty() {return ary == ByteTrieItm_slim.Ary_empty;}
	public void Clear() {
		val = null;
		for (int i = 0; i < ary_len; i++)
			ary[i].Clear();
		ary = ByteTrieItm_slim.Ary_empty;
		ary_len = ary_max = 0;
	}
	public ByteTrieItm_slim Ary_find(byte b) {
		int find_val = (case_any && (b > 64 && b < 91) ? b + 32 : b) & 0xff;// PATCH.JAVA:need to convert to unsigned byte
		int key_val = 0;
		switch (ary_len) {
			case 0: return null;
			case 1:
				ByteTrieItm_slim rv = ary[0];
				key_val = rv.Key_byte() & 0xff;// PATCH.JAVA:need to convert to unsigned byte;
				key_val = (case_any && (key_val > 64 && key_val < 91) ? key_val + 32 : key_val);
				return key_val == find_val ? rv : null;
			default:
				int adj = 1;
				int prv_pos = 0;
				int prv_len = ary_len;
				int cur_len = 0;
				int cur_idx = 0;
				ByteTrieItm_slim itm = null;
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
	public ByteTrieItm_slim Ary_add(byte b, Object val) {
		int new_len = ary_len + 1;
		if (new_len > ary_max) {
			ary_max += 4;
			ary = (ByteTrieItm_slim[])Array_.Resize(ary, ary_max);
		}
		ByteTrieItm_slim rv = new ByteTrieItm_slim(b, val, case_any);
		ary[ary_len] = rv;
		ary_len = new_len;
		ByteHashItm_sorter._.Sort(ary, ary_len);
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
	public static final ByteTrieItm_slim[] Ary_empty = new ByteTrieItm_slim[0]; int ary_len = 0, ary_max = 0;
}
class ByteHashItm_sorter {// quicksort
	ByteTrieItm_slim[] ary; int ary_len;
	public void Sort(ByteTrieItm_slim[] ary, int ary_len) {
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
				ByteTrieItm_slim tmp = ary[i];
				ary[i] = ary[j];
				ary[j] = tmp;
				i++;
				j--;
			}
		}
		if (lo < j) Sort_recurse(lo, j);
		if (i < hi) Sort_recurse(i, hi);
	}
	public static final ByteHashItm_sorter _ = new ByteHashItm_sorter(); ByteHashItm_sorter() {}
}
