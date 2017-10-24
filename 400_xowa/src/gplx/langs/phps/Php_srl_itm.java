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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
interface Php_srl_itm {
	byte Tid();
	int Src_bgn();
	int Src_end();
	Object Val();
	void Xto_bfr(Bry_bfr bfr, int depth);
	void Clear();
}
class Php_srl_itm_ {
	public static final    Php_srl_itm[] Ary_empty = new Php_srl_itm[0];
	public static final byte  Tid_unknown = 0, Tid_nil = 1, Tid_bool = 2, Tid_int = 3, Tid_double = 4, Tid_string = 5, Tid_array = 6, Tid_function = 7;
	public static final    byte[][] Names = Bry_.Ary("unknown", "nil", "boolean", "int", "double", "string", "array", "function");
	public static final    Object Val_nil = null, Val_table = null;
}
abstract class Php_srl_itm_base implements Php_srl_itm {
	public abstract byte Tid();
	public void Ctor(int src_bgn, int src_end, Object val) {this.src_bgn = src_bgn; this.src_end = src_end; this.val = val;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public Object Val() {return val;} Object val;
	@gplx.Virtual public void Xto_bfr(Bry_bfr bfr, int depth) {
		Php_srl_wtr.Indent(bfr, depth);
		bfr.Add(Php_srl_itm_.Names[this.Tid()]).Add_byte(Byte_ascii.Colon);
		bfr.Add_str_u8(Object_.Xto_str_strict_or_null_mark(this.Val())).Add_byte(Byte_ascii.Semic).Add_byte_nl();		
	}
	public void Clear() {}
}
class Php_srl_itm_nil extends Php_srl_itm_base {
	public Php_srl_itm_nil() {this.Ctor(-1, -1, null);}
	@Override public byte Tid() {return Php_srl_itm_.Tid_nil;}
	public byte[] Bry_extract(byte[] raw) {return null;}
	public static Php_srl_itm_nil Nil = new Php_srl_itm_nil();
}
class Php_srl_itm_bool extends Php_srl_itm_base {
	public Php_srl_itm_bool(boolean val, byte[] bry) {this.val = val; this.bry = bry; this.Ctor(-1, -1, val);}
	@Override public byte Tid() {return Php_srl_itm_.Tid_bool;}
	public byte[] Bry_extract(byte[] raw) {return bry;} private byte[] bry;
	public boolean Val_as_bool() {return val;} private boolean val;
	public static Php_srl_itm_bool Bool_n = new Php_srl_itm_bool(false, new byte[] {Byte_ascii.Num_0}), Bool_y = new Php_srl_itm_bool(true, new byte[] {Byte_ascii.Num_1});
}
class Php_srl_itm_int extends Php_srl_itm_base {
	public Php_srl_itm_int(int src_bgn, int src_end, int val) {this.val = val; this.Ctor(src_bgn, src_end, val);}
	@Override public byte Tid() {return Php_srl_itm_.Tid_int;}
	public int Val_as_int() {return val;} private int val;
}
class Php_srl_itm_double extends Php_srl_itm_base {
	public Php_srl_itm_double(int src_bgn, int src_end, double val) {this.val = val; this.Ctor(src_bgn, src_end, val);}
	@Override public byte Tid() {return Php_srl_itm_.Tid_double;}
	public double Val_as_double() {return val;} double val;
}
class Php_srl_itm_str extends Php_srl_itm_base {
	public Php_srl_itm_str(int src_bgn, int src_end, String val) {this.val = val; this.Ctor(src_bgn, src_end, val);}
	@Override public byte Tid() {return Php_srl_itm_.Tid_string;}
	public String Val_as_str() {return val;} private String val;
}
class Php_srl_itm_func extends Php_srl_itm_base {
	public Php_srl_itm_func(int src_bgn, int src_end, int val) {this.val = val; this.Ctor(src_bgn, src_end, val);}
	@Override public byte Tid() {return Php_srl_itm_.Tid_function;}
	public int Val_as_int() {return val;} private int val;
}
class Php_srl_itm_ary extends Php_srl_itm_base {
	public Php_srl_itm_ary(int src_bgn, int src_end) {this.Ctor(src_bgn, src_end, null);}
	@Override public byte Tid() {return Php_srl_itm_.Tid_array;}
	public Php_srl_itm_kv[] Subs_ary() {return subs;}
	public int Subs_len() {return subs_len;} private int subs_len = 0, subs_max = 0;
	public Php_srl_itm_kv Subs_get_at(int i) {return subs[i];}
	public void Subs_clear() {
		for (int i = 0; i < subs_len; i++) {
			subs[i].Clear();
		}
		subs = Php_srl_itm_kv.Ary_empty;
		subs_len = subs_max = 0;
	}
	public Php_srl_itm_ary Subs_add_many(Php_srl_itm_kv... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			Subs_add(ary[i]);
		return this;
	}
	public Php_srl_itm_ary Subs_add(Php_srl_itm_kv itm) {
		int new_len = subs_len + 1;
		if (new_len > subs_max) {	// ary too small >>> expand
			subs_max = new_len * 2;
			Php_srl_itm_kv[] new_subs = new Php_srl_itm_kv[subs_max];
			Array_.Copy_to(subs, 0, new_subs, 0, subs_len);
			subs = new_subs;
		}
		subs[subs_len] = itm;
		subs_len = new_len;
		return this;
	}
	@Override public void Xto_bfr(Bry_bfr bfr, int depth) {
		Php_srl_wtr.Indent(bfr, depth);
		bfr.Add_byte(Byte_ascii.Ltr_a).Add_byte(Byte_ascii.Brack_bgn).Add_int_variable(subs_len).Add(CONST_ary_bgn);
		for (int i = 0; i < subs_len; i++)
			subs[i].Xto_bfr(bfr, depth + 1);
		Php_srl_wtr.Indent(bfr, depth);
		bfr.Add_byte(Byte_ascii.Curly_end).Add_byte_nl();
	}
	private static final    byte[] CONST_ary_bgn = Bry_.new_a7("]{\n");
	Php_srl_itm_kv[] subs = Php_srl_itm_kv.Ary_empty;
}
class Php_srl_itm_kv {
	public int Idx_int() {return idx_int;} public Php_srl_itm_kv Idx_int_(int v) {idx_int = v; return this;} private int idx_int = -1;
	public Php_srl_itm Key() {return key;} public Php_srl_itm_kv Key_(Php_srl_itm v) {key = v; return this;} Php_srl_itm key;
	public Php_srl_itm Val() {return val;} public Php_srl_itm_kv Val_(Php_srl_itm v) {val = v; return this;} Php_srl_itm val;
	public void Clear() {
		key.Clear();
		val.Clear();
	}
	public void Xto_bfr(Bry_bfr bfr, int depth) {
		key.Xto_bfr(bfr, depth);
		val.Xto_bfr(bfr, depth);
	}
	public static final    Php_srl_itm_kv[] Ary_empty = new Php_srl_itm_kv[0];
}
class Php_srl_wtr {
	public static void Indent(Bry_bfr bfr, int depth) {
		if (depth > 0) bfr.Add_byte_repeat(Byte_ascii.Space, depth * 2);	// indent
	}
}
