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
public class ByteTrieMgr_bwd_slim {
	public int Match_pos() {return match_pos;} private int match_pos;
	public Object MatchAtCurExact(byte[] src, int bgn_pos, int end_pos) {
		Object rv = Match(src[bgn_pos], src, bgn_pos, end_pos);
		return rv == null ? null : match_pos - bgn_pos == end_pos - bgn_pos ? rv : null;
	}
	public Object MatchAtCur(byte[] src, int bgn_pos, int end_pos) {return Match(src[bgn_pos], src, bgn_pos, end_pos);}
	public Object Match(byte b, byte[] src, int bgn_pos, int end_pos) {
		// NOTE: bgn, end follows same semantics as fwd where bgn >= & end < except reversed: bgn <= & end >; EX: "abcde" should pass 5, -1
		Object rv = null; int cur_pos = match_pos = bgn_pos;
		ByteTrieItm_slim cur = root;
		while (true) {
			ByteTrieItm_slim nxt = cur.Ary_find(b); if (nxt == null) return rv;	// nxt does not hav b; return rv;
			--cur_pos;
			if (nxt.Ary_is_empty()) {match_pos = cur_pos; return nxt.Val();}	// nxt is leaf; return nxt.Val() (which should be non-null)
			Object nxt_val = nxt.Val();
			if (nxt_val != null) {match_pos = cur_pos; rv = nxt_val;}			// nxt is node; cache rv (in case of false match)
			if (cur_pos == end_pos) return rv;									// increment cur_pos and exit if src_len
			b = src[cur_pos];
			cur = nxt;
		}
	}
	public ByteTrieMgr_bwd_slim Add_str_byte(String key, byte val) {return Add(Bry_.new_utf8_(key), Byte_obj_val.new_(val));}
	public ByteTrieMgr_bwd_slim Add_byteVal_strAry(byte val, String... ary) {
		int ary_len = ary.length;
		Byte_obj_val byteVal = Byte_obj_val.new_(val);
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			Add(Bry_.new_utf8_(itm), byteVal);
		}
		return this;
	}
	public ByteTrieMgr_bwd_slim Add(String key, Object val) {return Add(Bry_.new_utf8_(key), val);}
	public ByteTrieMgr_bwd_slim Add(byte[] key, Object val) {
		if (val == null) throw Err_.new_("null objects cannot be registered").Add("key", String_.new_utf8_(key));
		int key_len = key.length;
		ByteTrieItm_slim cur = root;
		for (int i = key_len - 1; i > -1; i--) {
			byte b = key[i];
			if (root.Case_any() && (b > 64 && b < 91)) b += 32;
			ByteTrieItm_slim nxt = cur.Ary_find(b);
			if (nxt == null)
				nxt = cur.Ary_add(b, null);
			if (i == 0)
				nxt.Val_set(val);
			cur = nxt;
		}
		count++; // FUTURE: do not increment if replacing value
		return this;
	}
	public int Count() {return count;} private int count;
	public void Del(byte[] key) {
		int key_len = key.length;
		ByteTrieItm_slim cur = root;
		for (int i = 0; i < key_len; i++) {
			byte b = key[i];
			cur = cur.Ary_find(b);
			if (cur == null) break;
			cur.Ary_del(b);
		}
		count--; // FUTURE: do not decrement if not found
	}
	public void Clear() {root.Clear(); count = 0;}
	public static ByteTrieMgr_bwd_slim cs_() {return new ByteTrieMgr_bwd_slim(false);}
	public static ByteTrieMgr_bwd_slim ci_() {return new ByteTrieMgr_bwd_slim(true);}
	public ByteTrieMgr_bwd_slim(boolean caseAny) {
		root = new ByteTrieItm_slim(Byte_.Zero, null, caseAny);
	}	private ByteTrieItm_slim root;
}
