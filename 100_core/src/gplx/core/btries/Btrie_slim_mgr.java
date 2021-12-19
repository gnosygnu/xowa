/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.btries;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteVal;
import gplx.types.basics.wrappers.IntVal;
public class Btrie_slim_mgr implements Btrie_mgr {
	Btrie_slim_mgr(boolean case_match) {root = new Btrie_slim_itm(ByteUtl.Zero, null, !case_match);}    private Btrie_slim_itm root;
	public int Count() {return count;} private int count;
	private int match_pos;
	public Object MatchAt(Btrie_rv rv, byte[] src, int bgn_pos, int end_pos) {
		if (bgn_pos < end_pos)
			return Match_at_w_b0(rv, src[bgn_pos], src, bgn_pos, end_pos);
		else { // handle out of bounds gracefully; EX: Match_bgn("abc", 3, 3) should (a) return null not fail; (b) return a pos of bgn_pos, not 0; DATE:2018-04-12
			rv.Init(bgn_pos, null);
			return null;
		}
	}
	public Object Match_at_w_b0(Btrie_rv rv, byte b, byte[] src, int bgn_pos, int src_end) {
		Object rv_obj = null; 
		int rv_pos = bgn_pos;
		int cur_pos = bgn_pos;
		Btrie_slim_itm cur = root;
		while (true) {
			Btrie_slim_itm nxt = cur.Ary_find(b);
			if (nxt == null) {
				rv.Init(rv_pos, rv_obj);            // nxt does not have b; return rv_obj;
				return rv_obj;
			}
			++cur_pos;
			if (nxt.Ary_is_empty()) {
				rv_obj = nxt.Val();
				rv.Init(cur_pos, rv_obj);            // nxt is leaf; return nxt.Val() (which should be non-null)
				return rv_obj;
			}
			Object nxt_val = nxt.Val();
			if (nxt_val != null) {rv_pos = cur_pos; rv_obj = nxt_val;}                            // nxt is node; cache rv_obj (in case of false match)
			if (cur_pos == src_end) {                    
				rv.Init(rv_pos, rv_obj);            // increment cur_pos and exit if src_end
				return rv_obj;
			}
			b = src[cur_pos];
			cur = nxt;
		}
	}
	public Object Match_exact(byte[] src) {return src == null ? null : Match_exact(src, 0, src.length);}
	public Object Match_exact(byte[] src, int bgn_pos, int end_pos) {
		if (bgn_pos == end_pos) return null;    // NOTE:handle empty String; DATE:2016-04-21
		Object rv = Match_bgn_w_byte(src[bgn_pos], src, bgn_pos, end_pos);
		return rv == null ? null : match_pos - bgn_pos == end_pos - bgn_pos ? rv : null;
	}
	public Object MatchBgn(byte[] src, int bgn_pos, int end_pos) {return bgn_pos < end_pos ? Match_bgn_w_byte(src[bgn_pos], src, bgn_pos, end_pos) : null;} // handle out of bounds gracefully; EX: Match_bgn("abc", 3, 3) should return null not fail
	public Object Match_bgn_w_byte(byte b, byte[] src, int bgn_pos, int src_end) {
		Object rv = null; int cur_pos = match_pos = bgn_pos;
		Btrie_slim_itm cur = root;
		while (true) {
			Btrie_slim_itm nxt = cur.Ary_find(b); if (nxt == null) return rv;    // nxt does not hav b; return rv;
			++cur_pos;
			if (nxt.Ary_is_empty()) {match_pos = cur_pos; return nxt.Val();}    // nxt is leaf; return nxt.Val() (which should be non-null)
			Object nxt_val = nxt.Val();
			if (nxt_val != null) {match_pos = cur_pos; rv = nxt_val;}            // nxt is node; cache rv (in case of false match)
			if (cur_pos == src_end) return rv;                                    // increment cur_pos and exit if src_end
			b = src[cur_pos];
			cur = nxt;
		}
	}
	public byte Match_byte_or(byte b, byte[] src, int bgn, int end, byte or) {
		Object rv_obj = Match_bgn_w_byte(b, src, bgn, end);
		return rv_obj == null ? or : ((ByteVal)rv_obj).Val();
	}
	public byte Match_byte_or(byte[] src, int bgn, int end, byte or) {
		Object rv_obj = MatchBgn(src, bgn, end);
		return rv_obj == null ? or : ((ByteVal)rv_obj).Val();
	}
	public byte Match_byte_or(Btrie_rv trv, byte b, byte[] src, int bgn, int end, byte or) {
		Object rv_obj = Match_at_w_b0(trv, b, src, bgn, end);
		return rv_obj == null ? or : ((ByteVal)rv_obj).Val();
	}
	public byte Match_byte_or(Btrie_rv trv, byte[] src, int bgn, int end, byte or) {
		Object rv_obj = MatchAt(trv, src, bgn, end);
		return rv_obj == null ? or : ((ByteVal)rv_obj).Val();
	}
	public byte Match_byte_or(Btrie_rv trv, byte[] src, byte or) {
		Object rv_obj = MatchAt(trv, src, 0, src.length);
		return rv_obj == null ? or : ((ByteVal)rv_obj).Val();
	}
	public Btrie_slim_mgr Add_bry_tid(byte[] bry, byte tid)            {return (Btrie_slim_mgr)AddObj(bry, ByteVal.New(tid));}
	public Btrie_slim_mgr Add_bry_int(byte[] key, int val)            {return (Btrie_slim_mgr)AddObj(key, new IntVal(val));}
	public Btrie_slim_mgr Add_str_byte(String key, byte val)        {return (Btrie_slim_mgr)AddObj(BryUtl.NewU8(key), ByteVal.New(val));}
	public Btrie_slim_mgr Add_str_int(String key, int val)            {return (Btrie_slim_mgr)AddObj(BryUtl.NewU8(key), new IntVal(val));}
	public Btrie_slim_mgr Add_bry(String key, String val)            {return (Btrie_slim_mgr)AddObj(BryUtl.NewU8(key), BryUtl.NewU8(val));}
	public Btrie_slim_mgr Add_bry(String key, byte[] val)            {return (Btrie_slim_mgr)AddObj(BryUtl.NewU8(key), val);}
	public Btrie_slim_mgr Add_bry(byte[] v)                            {return (Btrie_slim_mgr)AddObj(v, v);}
	public Btrie_slim_mgr Add_str_str(String key, String val)        {return (Btrie_slim_mgr)AddObj(BryUtl.NewU8(key), BryUtl.NewU8(val));}
	public Btrie_slim_mgr Add_bry_bry(byte[] key, byte[] val)        {return (Btrie_slim_mgr)AddObj(key, val);}
	public Btrie_slim_mgr Add_bry_byte(byte b, byte val)            {return (Btrie_slim_mgr)AddObj(new byte[] {b}, ByteVal.New(val));}
	public Btrie_slim_mgr Add_bry_byte(byte[] bry, byte val)        {return (Btrie_slim_mgr)AddObj(bry, ByteVal.New(val));}
	public Btrie_slim_mgr Add_str_byte__many(byte val, String... ary) {
		int ary_len = ary.length;
		ByteVal bval = ByteVal.New(val);
		for (int i = 0; i < ary_len; i++)
			AddObj(BryUtl.NewU8(ary[i]), bval);
		return this;
	}
	public Btrie_slim_mgr Add_many_str(String... ary) {
		int len = ary.length;            
		for (int i = 0; i < len; i++) {
			byte[] itm = BryUtl.NewU8(ary[i]);
			AddObj(itm, itm);
		}
		return this;
	}
	public Btrie_slim_mgr Add_many_bry(byte[]... ary) {
		int len = ary.length;            
		for (int i = 0; i < len; i++) {
			byte[] itm = ary[i];
			AddObj(itm, itm);
		}
		return this;
	}
	public Btrie_slim_mgr Add_many_int(int val, String... ary) {return Add_many_int(val, BryUtl.Ary(ary));}
	public Btrie_slim_mgr Add_many_int(int val, byte[]... ary) {
		int len = ary.length;
		IntVal obj = new IntVal(val);
		for (int i = 0; i < len; i++)
			AddObj(ary[i], obj);
		return this;
	}
	public Btrie_slim_mgr Add_ary_byte(byte... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			byte b = ary[i];
			ByteVal bval = ByteVal.New(b);
			AddObj(BryUtl.NewByByte(b), bval);
		}
		return this;
	}
	public Btrie_slim_mgr Add_replace_many(String trg_str, String... src_ary) {return Add_replace_many(BryUtl.NewU8(trg_str), src_ary);}
	public Btrie_slim_mgr Add_replace_many(byte[] trg_bry, String... src_ary) {
		int len = src_ary.length;
		for (int i = 0; i < len; i++)
			AddObj(BryUtl.NewU8(src_ary[i]), trg_bry);
		return this;
	}
	public Btrie_slim_mgr Add_stub(String key, byte val)        {byte[] bry = BryUtl.NewU8(key); return (Btrie_slim_mgr)AddObj(bry, new Btrie_itm_stub(val, bry));}
	public Btrie_slim_mgr Add_stubs(byte[][] ary)                {return Add_stubs(ary, ary.length);}
	public Btrie_slim_mgr Add_stubs(byte[][] ary, int ary_len) {
		for (byte i = 0; i < ary_len; i++) {
			byte[] bry = ary[i];
			AddObj(bry, new Btrie_itm_stub(i, bry));
		}
		return this;
	}
	public Btrie_mgr AddObj(String key, Object val) {return AddObj(BryUtl.NewU8(key), val);}
	public Btrie_mgr AddObj(byte[] key, Object val) {
		if (val == null) throw ErrUtl.NewArgs("null objects cannot be registered", "key", StringUtl.NewU8(key));
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
			if (nxt_val == null)    // cur is end of chain; remove entry; EX: Abc and at c
				cur.Ary_del(b);
			else                    // cur is mid of chain; null out entry
				nxt.Val_set(null);
			cur = nxt;
		}
		count--; // FUTURE: do not decrement if not found
	}
	public byte[] Replace(BryWtr tmp_bfr, byte[] src, int bgn, int end) {
		int pos = bgn;
		boolean dirty = false;
		while (pos < end) {
			byte b = src[pos];
			Object o = this.Match_bgn_w_byte(b, src, pos, end);
			if (o == null) {
				if (dirty)
					tmp_bfr.AddByte(b);
				pos++;
			}
			else {
				if (!dirty) {
					tmp_bfr.AddMid(src, bgn, pos);
					dirty = true;
				}
				tmp_bfr.Add((byte[])o);
				pos = match_pos;
			}
		}
		return dirty ? tmp_bfr.ToBryAndClear() : src;
	}
	public void Clear() {root.Clear(); count = 0;}
	public static Btrie_slim_mgr cs()                {return new Btrie_slim_mgr(BoolUtl.Y);}
	public static Btrie_slim_mgr ci_a7()            {return new Btrie_slim_mgr(BoolUtl.N);}
	public static Btrie_slim_mgr ci_u8()            {return new Btrie_slim_mgr(BoolUtl.N);}
	public static Btrie_slim_mgr new_(boolean cs)        {return new Btrie_slim_mgr(cs);}
}
