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
package gplx;
import gplx.core.primitives.*;
import gplx.core.intls.*;
public class Hash_adp_bry extends gplx.core.lists.Hash_adp_base implements Hash_adp {
	private final    Hash_adp_bry_itm_base proto, key_ref;
	Hash_adp_bry(Hash_adp_bry_itm_base proto) {
		this.proto = proto;
		this.key_ref = proto.New();
	}
	@Override protected Object Fetch_base(Object key)				{synchronized (key_ref) {return super.Fetch_base(key_ref.Init((byte[])key));}}	// TS: DATE:2016-07-06
	@Override protected void Del_base(Object key)					{synchronized (key_ref) {super.Del_base(key_ref.Init((byte[])key));}}// TS: DATE:2016-07-06
	@Override protected boolean Has_base(Object key)					{synchronized (key_ref) {return super.Has_base(key_ref.Init((byte[])key));}}// TS: DATE:2016-07-06
	public int Get_as_int(byte[] key) {return Get_as_int(key, 0, key.length);}
	public int Get_as_int(byte[] key, int bgn, int end) {
		int rv = Get_as_int_or(key, bgn, end, Int_.Min_value); if (rv == Int_.Min_value) throw Err_.new_("core", "unknown key", "key", key);
		return rv;
	}
	public int Get_as_int_or(byte[] key, int or) {return Get_as_int_or(key, 0, key.length, or);}
	public int Get_as_int_or(byte[] key, int bgn, int end, int or) {
		Object o = Get_by_mid(key, bgn, end); 
		return (o == null) ? or : ((Int_obj_val)o).Val();
	}
	public byte Get_as_byte_or(byte[] key, byte or) {return Get_as_byte_or(key, 0, key.length, or);}
	public byte Get_as_byte_or(byte[] key, int bgn, int end, byte or) {
		Object o = Get_by_mid(key, bgn, end); 
		return o == null ? or : ((Byte_obj_val)o).Val();
	}
	public Object Get_by_bry(byte[] src)							{synchronized (key_ref) {return super.Fetch_base(key_ref.Init(src));}}	// TS: DATE:2016-07-06
	public Object Get_by_mid(byte[] src, int bgn, int end)			{synchronized (key_ref) {return super.Fetch_base(key_ref.Init(src, bgn, end));}}// TS: DATE:2016-07-06
	public Hash_adp_bry Add_byte_int(byte key, int val)				{this.Add_base(new byte[]{key}, new Int_obj_val(val)); return this;}
	public Hash_adp_bry Add_bry_byte(byte[] key, byte val)			{this.Add_base(key, Byte_obj_val.new_(val)); return this;}
	public Hash_adp_bry Add_bry_int(byte[] key, int val)			{this.Add_base(key, new Int_obj_val(val)); return this;}
	public Hash_adp_bry Add_bry_bry(byte[] key)						{this.Add_base(key, key); return this;}
	public Hash_adp_bry Add_str_byte(String key, byte val)			{this.Add_base(Bry_.new_u8(key), Byte_obj_val.new_(val)); return this;}
	public Hash_adp_bry Add_str_int(String key, int val)			{this.Add_base(Bry_.new_u8(key), new Int_obj_val(val)); return this;}
	public Hash_adp_bry Add_str_obj(String key, Object val)			{this.Add_base(Bry_.new_u8(key), val); return this;}
	public Hash_adp_bry Add_bry_obj(byte[] key, Object val)			{this.Add_base(key, val); return this;}
	public Hash_adp_bry Add_many_str(String... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			byte[] bry = Bry_.new_u8(itm);
			Add_bry_bry(bry);
		}
		return this;
	}
	public Hash_adp_bry Add_many_bry(byte[]... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++)
			Add_bry_bry(ary[i]);
		return this;
	}
	@Override protected void Add_base(Object key, Object val) {
		byte[] key_bry = (byte[])key;
		Hash_adp_bry_itm_base key_itm = proto.New();
		key_itm.Init(key_bry, 0, key_bry.length);
		super.Add_base(key_itm, val);
	}
	public static Hash_adp_bry cs()												{return new Hash_adp_bry(Hash_adp_bry_itm_cs.Instance);}
	public static Hash_adp_bry ci_a7()											{return new Hash_adp_bry(Hash_adp_bry_itm_ci_a7.Instance);}
	public static Hash_adp_bry ci_u8(Gfo_case_mgr case_mgr)						{return new Hash_adp_bry(Hash_adp_bry_itm_ci_u8.get_or_new(case_mgr));}
	public static Hash_adp_bry c__u8(boolean case_match, Gfo_case_mgr case_mgr)	{return case_match ? cs() : ci_u8(case_mgr);}
}
abstract class Hash_adp_bry_itm_base {
	public abstract Hash_adp_bry_itm_base New();
	public Hash_adp_bry_itm_base Init(byte[] src) {return this.Init(src, 0, src.length);}
	public abstract Hash_adp_bry_itm_base Init(byte[] src, int src_bgn, int src_end);
}
class Hash_adp_bry_itm_cs extends Hash_adp_bry_itm_base {
	private byte[] src; int src_bgn, src_end;
	@Override public Hash_adp_bry_itm_base New() {return new Hash_adp_bry_itm_cs();}
	@Override public Hash_adp_bry_itm_base Init(byte[] src, int src_bgn, int src_end) {this.src = src; this.src_bgn = src_bgn; this.src_end = src_end; return this;}
	@Override public int hashCode() {
		int rv = 0;
		for (int i = src_bgn; i < src_end; i++) {
			int b_int = src[i] & 0xFF;	// JAVA: patch
			rv = (31 * rv) + b_int;
		}
		return rv;	
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		Hash_adp_bry_itm_cs comp = (Hash_adp_bry_itm_cs)obj;
		byte[] comp_src = comp.src; int comp_bgn = comp.src_bgn, comp_end = comp.src_end;
		int comp_len = comp_end - comp_bgn, src_len = src_end - src_bgn;
		if (comp_len != src_len) return false;
		for (int i = 0; i < comp_len; i++) {
			int src_pos = src_bgn + i;
			if (src_pos >= src_end) return false;	// ran out of src; exit; EX: src=ab; find=abc
			if (src[src_pos] != comp_src[i + comp_bgn]) return false;
		}
		return true;
	}
        public static final    Hash_adp_bry_itm_cs Instance = new Hash_adp_bry_itm_cs(); Hash_adp_bry_itm_cs() {}
}
class Hash_adp_bry_itm_ci_a7 extends Hash_adp_bry_itm_base {
	private byte[] src; int src_bgn, src_end;
	@Override public Hash_adp_bry_itm_base New() {return new Hash_adp_bry_itm_ci_a7();}
	@Override public Hash_adp_bry_itm_base Init(byte[] src, int src_bgn, int src_end) {this.src = src; this.src_bgn = src_bgn; this.src_end = src_end; return this;}
	@Override public int hashCode() {
		int rv = 0;
		for (int i = src_bgn; i < src_end; i++) {
			int b_int = src[i] & 0xFF;		// JAVA: patch
			if (b_int > 64 && b_int < 91)	// 64=before A; 91=after Z; NOTE: lowering upper-case on PERF assumption that there will be more lower-case letters than upper-case
				b_int += 32;
			rv = (31 * rv) + b_int;
		}
		return rv;	
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		Hash_adp_bry_itm_ci_a7 comp = (Hash_adp_bry_itm_ci_a7)obj;
		byte[] comp_src = comp.src; int comp_bgn = comp.src_bgn, comp_end = comp.src_end;
		int comp_len = comp_end - comp_bgn, src_len = src_end - src_bgn;
		if (comp_len != src_len) return false;
		for (int i = 0; i < comp_len; i++) {
			int src_pos = src_bgn + i;
			if (src_pos >= src_end) return false;	// ran out of src; exit; EX: src=ab; find=abc
			byte src_byte = src[src_pos];
			if (src_byte > 64 && src_byte < 91) src_byte += 32;
			byte comp_byte = comp_src[i + comp_bgn];
			if (comp_byte > 64 && comp_byte < 91) comp_byte += 32;
			if (src_byte != comp_byte) return false;
		}
		return true;
	}
        public static final    Hash_adp_bry_itm_ci_a7 Instance = new Hash_adp_bry_itm_ci_a7(); Hash_adp_bry_itm_ci_a7() {}
}
class Hash_adp_bry_itm_ci_u8 extends Hash_adp_bry_itm_base {
	private final    Gfo_case_mgr case_mgr;
	Hash_adp_bry_itm_ci_u8(Gfo_case_mgr case_mgr) {this.case_mgr = case_mgr;}
	private byte[] src; int src_bgn, src_end;
	@Override public Hash_adp_bry_itm_base New() {return new Hash_adp_bry_itm_ci_u8(case_mgr);}
	@Override public Hash_adp_bry_itm_base Init(byte[] src, int src_bgn, int src_end) {this.src = src; this.src_bgn = src_bgn; this.src_end = src_end; return this;}
	@Override public int hashCode() {
		int rv = 0;
		for (int i = src_bgn; i < src_end; i++) {
			byte b = src[i];
			int b_int = b & 0xFF;			// JAVA: patch
			Gfo_case_itm itm = case_mgr.Get_or_null(b, src, i, src_end);
			if (itm == null) {				// unknown itm; byte is a number, symbol, or unknown; just use the existing byte
			}
			else {							// known itm; use its hash_code
				b_int = itm.Hashcode_lo();
				int b_len = Utf8_.Len_of_char_by_1st_byte(b);	// NOTE: must calc b_len for langs with asymmetric upper / lower; PAGE:tr.w:Zvishavane DATE:2015-09-07
				i += b_len - 1;
			}
			rv = (31 * rv) + b_int;
		}
		return rv;	
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		Hash_adp_bry_itm_ci_u8 trg_itm = (Hash_adp_bry_itm_ci_u8)obj;
		byte[] trg = trg_itm.src; int trg_bgn = trg_itm.src_bgn, trg_end = trg_itm.src_end;
		int src_c_bgn = src_bgn;
		int trg_c_bgn = trg_bgn;
		while	(	src_c_bgn < src_end
				&&	trg_c_bgn < trg_end) {			// exit once one goes out of bounds
			byte src_c = src[src_c_bgn];
			byte trg_c = trg[trg_c_bgn];
			int src_c_len = Utf8_.Len_of_char_by_1st_byte(src_c);
			int trg_c_len = Utf8_.Len_of_char_by_1st_byte(trg_c);
			int src_c_end = src_c_bgn + src_c_len;
			int trg_c_end = trg_c_bgn + trg_c_len;
			Gfo_case_itm src_c_itm = case_mgr.Get_or_null(src_c, src, src_c_bgn, src_c_end);
			Gfo_case_itm trg_c_itm = case_mgr.Get_or_null(trg_c, trg, trg_c_bgn, trg_c_end);
			if		(src_c_itm != null && trg_c_itm == null)	return false;						// src == ltr; trg != ltr; EX: a, 1
			else if (src_c_itm == null && trg_c_itm != null)	return false;						// src != ltr; trg == ltr; EX: 1, a
			else if (src_c_itm == null && trg_c_itm == null) {										// src != ltr; trg != ltr; EX: 1, 2; ＿, Ⓐ
				if (!Bry_.Match(src, src_c_bgn, src_c_end, trg, trg_c_bgn, trg_c_end)) return false;// syms do not match; return false;
			}
			else {
				if (src_c_itm.Utf8_id_lo() != trg_c_itm.Utf8_id_lo()) return false;					// lower-case utf8-ids don't match; return false; NOTE: using utf8-ids instead of hash-code to handle asymmetric brys; DATE:2015-09-07
			}
			src_c_bgn = src_c_end;
			trg_c_bgn = trg_c_end;
		}
		return src_c_bgn == src_end && trg_c_bgn == trg_end;										// only return true if both src and trg read to end of their brys, otherwise "a","ab" will match
	}
        public static Hash_adp_bry_itm_ci_u8 get_or_new(Gfo_case_mgr case_mgr) {
		switch (case_mgr.Tid()) {
			case Gfo_case_mgr_.Tid_a7:			if (Itm_a7 == null) Itm_a7 = new Hash_adp_bry_itm_ci_u8(case_mgr); return Itm_a7;
			case Gfo_case_mgr_.Tid_u8:			if (Itm_u8 == null) Itm_u8 = new Hash_adp_bry_itm_ci_u8(case_mgr); return Itm_u8;
			case Gfo_case_mgr_.Tid_custom:		return new Hash_adp_bry_itm_ci_u8(case_mgr);
			default:							throw Err_.new_unhandled(case_mgr.Tid());
		}
	}
	private static Hash_adp_bry_itm_ci_u8 Itm_a7, Itm_u8;
}
