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
import gplx.core.primitives.*;
public class Btrie_bwd_mgr {
	public int Match_pos() {return match_pos;} private int match_pos;
	public Object Match_exact(byte[] src, int bgn_pos, int end_pos) {
		Object rv = Match(src[bgn_pos], src, bgn_pos, end_pos);
		return rv == null ? null : match_pos - bgn_pos == end_pos - bgn_pos ? rv : null;
	}

	public Object Match_at(Btrie_rv rv, byte[] src, int bgn_pos, int end_pos) {return Match_at_w_b0(rv, src[bgn_pos], src, bgn_pos, end_pos);}
	public Object Match_at_w_b0(Btrie_rv rv, byte b, byte[] src, int bgn_pos, int end_pos) {
		// NOTE: bgn, end follows same semantics as fwd where bgn >= & end < except reversed: bgn <= & end >; EX: "abcde" should pass 5, -1
		Object rv_obj = null;
		int rv_pos = bgn_pos;
		int cur_pos = bgn_pos;
		Btrie_slim_itm cur = root;
		while (true) {
			Btrie_slim_itm nxt = cur.Ary_find(b);
			if (nxt == null) {				// nxt does not have b; return rv_obj;
				rv.Init(rv_pos, rv_obj);
				return rv_obj;
			}
			--cur_pos;
			if (nxt.Ary_is_empty()) {		// nxt is leaf; return nxt.Val() (which should be non-null)
				rv_obj = nxt.Val();
				rv.Init(cur_pos, rv_obj);
				return rv_obj;
			}
			Object nxt_val = nxt.Val();
			if (nxt_val != null) {rv_pos = cur_pos; rv_obj = nxt_val;}			// nxt is node; cache rv_obj (in case of false match)
			if (cur_pos == end_pos) {		// increment cur_pos and exit if end_pos	
				rv.Init(rv_pos, rv_obj);			
				return rv_obj;
			}
			b = src[cur_pos];
			cur = nxt;
		}
	}

	public Object Match_bgn(byte[] src, int bgn_pos, int end_pos) {return Match(src[bgn_pos], src, bgn_pos, end_pos);}
	public Object Match(byte b, byte[] src, int bgn_pos, int end_pos) {
		// NOTE: bgn, end follows same semantics as fwd where bgn >= & end < except reversed: bgn <= & end >; EX: "abcde" should pass 5, -1
		Object rv = null; int cur_pos = match_pos = bgn_pos;
		Btrie_slim_itm cur = root;
		while (true) {
			Btrie_slim_itm nxt = cur.Ary_find(b); if (nxt == null) return rv;	// nxt does not hav b; return rv;
			--cur_pos;
			if (nxt.Ary_is_empty()) {match_pos = cur_pos; return nxt.Val();}	// nxt is leaf; return nxt.Val() (which should be non-null)
			Object nxt_val = nxt.Val();
			if (nxt_val != null) {match_pos = cur_pos; rv = nxt_val;}			// nxt is node; cache rv (in case of false match)
			if (cur_pos == end_pos) return rv;									// increment cur_pos and exit if src_len
			b = src[cur_pos];
			cur = nxt;
		}
	}
	public Btrie_bwd_mgr Add_str_byte(String key, byte val) {return Add(Bry_.new_u8(key), Byte_obj_val.new_(val));}
	public Btrie_bwd_mgr Add_byteVal_strAry(byte val, String... ary) {
		int ary_len = ary.length;
		Byte_obj_val byteVal = Byte_obj_val.new_(val);
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			Add(Bry_.new_u8(itm), byteVal);
		}
		return this;
	}
	public Btrie_bwd_mgr Add(String key, Object val) {return Add(Bry_.new_u8(key), val);}
	public Btrie_bwd_mgr Add(byte[] key, Object val) {
		if (val == null) throw Err_.new_wo_type("null objects cannot be registered", "key", String_.new_u8(key));
		int key_len = key.length;
		Btrie_slim_itm cur = root;
		for (int i = key_len - 1; i > -1; i--) {
			byte b = key[i];
			if (root.Case_any() && (b > 64 && b < 91)) b += 32;
			Btrie_slim_itm nxt = cur.Ary_find(b);
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
		Btrie_slim_itm cur = root;
		for (int i = 0; i < key_len; i++) {
			byte b = key[i];
			cur = cur.Ary_find(b);
			if (cur == null) break;
			cur.Ary_del(b);
		}
		count--; // FUTURE: do not decrement if not found
	}
	public void Clear() {root.Clear(); count = 0;}
	public static Btrie_bwd_mgr cs_()        {return new Btrie_bwd_mgr(false);}
	public static Btrie_bwd_mgr ci_()        {return new Btrie_bwd_mgr(true);}
	public static Btrie_bwd_mgr c__(boolean cs) {return new Btrie_bwd_mgr(!cs);}
	public Btrie_bwd_mgr(boolean caseAny) {
		root = new Btrie_slim_itm(Byte_.Zero, null, caseAny);
	}	private Btrie_slim_itm root;
}
