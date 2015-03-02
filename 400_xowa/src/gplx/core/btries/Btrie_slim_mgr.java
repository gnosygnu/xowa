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
package gplx.core.btries; import gplx.*; import gplx.core.*;
import gplx.core.primitives.*;
public class Btrie_slim_mgr implements Btrie_mgr {
	Btrie_slim_mgr(boolean case_match) {root = new Btrie_slim_itm(Byte_.Zero, null, !case_match);}	private Btrie_slim_itm root;
	public int Count() {return count;} private int count;
	public int Match_pos() {return match_pos;} private int match_pos;
	public Object Match_exact(byte[] src, int bgn_pos, int end_pos) {
		Object rv = Match_bgn_w_byte(src[bgn_pos], src, bgn_pos, end_pos);
		return rv == null ? null : match_pos - bgn_pos == end_pos - bgn_pos ? rv : null;
	}
	public Object Match_bgn(byte[] src, int bgn_pos, int end_pos) {return Match_bgn_w_byte(src[bgn_pos], src, bgn_pos, end_pos);}
	public Object Match_bgn_w_byte(byte b, byte[] src, int bgn_pos, int src_len) {
		Object rv = null; int cur_pos = match_pos = bgn_pos;
		Btrie_slim_itm cur = root;
		while (true) {
			Btrie_slim_itm nxt = cur.Ary_find(b); if (nxt == null) return rv;	// nxt does not hav b; return rv;
			++cur_pos;
			if (nxt.Ary_is_empty()) {match_pos = cur_pos; return nxt.Val();}	// nxt is leaf; return nxt.Val() (which should be non-null)
			Object nxt_val = nxt.Val();
			if (nxt_val != null) {match_pos = cur_pos; rv = nxt_val;}			// nxt is node; cache rv (in case of false match)
			if (cur_pos == src_len) return rv;									// increment cur_pos and exit if src_len
			b = src[cur_pos];
			cur = nxt;
		}
	}
	public Btrie_slim_mgr Add_bry_tid(byte[] bry, byte tid)			{return (Btrie_slim_mgr)Add_obj(bry, Byte_obj_val.new_(tid));}
	public Btrie_slim_mgr Add_str_byte(String key, byte val)		{return (Btrie_slim_mgr)Add_obj(Bry_.new_utf8_(key), Byte_obj_val.new_(val));}
	public Btrie_slim_mgr Add_str_int(String key, int val)			{return (Btrie_slim_mgr)Add_obj(Bry_.new_utf8_(key), Int_obj_val.new_(val));}
	public Btrie_slim_mgr Add_bry(String key, String val)			{return (Btrie_slim_mgr)Add_obj(Bry_.new_utf8_(key), Bry_.new_utf8_(val));}
	public Btrie_slim_mgr Add_bry(String key, byte[] val)			{return (Btrie_slim_mgr)Add_obj(Bry_.new_utf8_(key), val);}
	public Btrie_slim_mgr Add_bry(byte[] v)							{return (Btrie_slim_mgr)Add_obj(v, v);}
	public Btrie_slim_mgr Add_bry_bval(byte b, byte val)			{return (Btrie_slim_mgr)Add_obj(new byte[] {b}, Byte_obj_val.new_(val));}
	public Btrie_slim_mgr Add_bry_bval(byte[] bry, byte val)		{return (Btrie_slim_mgr)Add_obj(bry, Byte_obj_val.new_(val));}
	public Btrie_slim_mgr Add_str_byte__many(byte val, String... ary) {
		int ary_len = ary.length;
		Byte_obj_val bval = Byte_obj_val.new_(val);
		for (int i = 0; i < ary_len; i++)
			Add_obj(Bry_.new_utf8_(ary[i]), bval);
		return this;
	}
	public Btrie_slim_mgr Add_stub(String key, byte val)		{byte[] bry = Bry_.new_utf8_(key); return (Btrie_slim_mgr)Add_obj(bry, new Btrie_itm_stub(val, bry));}
	public Btrie_slim_mgr Add_stubs(byte[][] ary)				{return Add_stubs(ary, ary.length);}
	public Btrie_slim_mgr Add_stubs(byte[][] ary, int ary_len) {
		for (byte i = 0; i < ary_len; i++) {
			byte[] bry = ary[i];
			Add_obj(bry, new Btrie_itm_stub(i, bry));
		}
		return this;
	}
	public Btrie_mgr Add_obj(String key, Object val) {return Add_obj(Bry_.new_utf8_(key), val);}
	public Btrie_mgr Add_obj(byte[] key, Object val) {
		if (val == null) throw Err_.new_("null objects cannot be registered").Add("key", String_.new_utf8_(key));
		int key_len = key.length; int key_end = key_len - 1;
		Btrie_slim_itm cur = root;
		for (int i = 0; i < key_len; i++) {
			byte b = key[i];
			if (root.Case_any() && (b > 64 && b < 91)) b += 32;
			Btrie_slim_itm nxt = cur.Ary_find(b);
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
		Btrie_slim_itm cur = root;
		for (int i = 0; i < key_len; i++) {
			byte b = key[i];
			Btrie_slim_itm nxt = cur.Ary_find(b);
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
			Object o = this.Match_bgn_w_byte(b, src, pos, end);
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
		return dirty ? tmp_bfr.Xto_bry_and_clear() : src;
	}
	public void Clear() {root.Clear(); count = 0;}
	public static Btrie_slim_mgr cs_()				{return new Btrie_slim_mgr(true);}
	public static Btrie_slim_mgr ci_ascii_()		{return new Btrie_slim_mgr(false);}
	public static Btrie_slim_mgr ci_utf_8_()		{return new Btrie_slim_mgr(false);}
	public static Btrie_slim_mgr new_(boolean v)		{return new Btrie_slim_mgr(v);}
}
