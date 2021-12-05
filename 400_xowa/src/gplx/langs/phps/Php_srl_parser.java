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
package gplx.langs.phps; import gplx.*;
import gplx.objects.strings.AsciiByte;
public class Php_srl_parser {
	@gplx.Internal protected Php_srl_factory Factory() {return factory;} Php_srl_factory factory = new Php_srl_factory();
	byte[] raw; int raw_len, pos;
	public Keyval[] Parse_as_kvs(byte[] raw) {
		Php_srl_itm_ary root = Parse(raw);
		return Xto_kv_ary(root);
	}
	Keyval[] Xto_kv_ary(Php_srl_itm_ary ary) {
		int len = ary.Subs_len();
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++)
			rv[i] = Xto_kv(ary.Subs_get_at(i));
		return rv;
	}
	Keyval Xto_kv(Php_srl_itm_kv itm) {
		if (itm == null) return null;
		Php_srl_itm itm_key = itm.Key();
		Object key = itm_key == null ? null : itm_key.Val();
		Php_srl_itm itm_val = itm.Val();
		Object val = null;
		switch (itm_val.Tid()) {
			case Php_srl_itm_.Tid_array:
				Php_srl_itm_ary ary = (Php_srl_itm_ary)itm_val;
				val = Xto_kv_ary(ary);
				break;
			case Php_srl_itm_.Tid_function:
				val = new gplx.xowa.xtns.scribunto.Scrib_lua_proc(Object_.Xto_str_strict_or_null_mark(key), Int_.Cast(itm_val.Val()));	// NOTE: in most cases, key is a STRING (name of ScribFunction); however, for gsub it is an INT (arg_idx) b/c it is passed as a parameter
				break;
			default:
				val = itm_val.Val();
				break;
		}
		return Keyval_.obj_(key, val);
	}
	@gplx.Internal protected Php_srl_itm_ary Parse(byte[] raw) {
		this.raw = raw; this.raw_len = raw.length; pos = 0;
		Php_srl_itm_ary rv = new Php_srl_itm_ary(0, raw_len);
		Php_srl_itm_kv cur_kv = factory.Kv();
		rv.Subs_add(cur_kv);
		boolean mode_is_key = false;
		while (true) {
			if (pos >= raw_len) break;
			if (mode_is_key) {
				cur_kv.Key_(Parse_itm(pos));
				mode_is_key = false;
			}
			else {
				cur_kv.Val_(Parse_itm(pos));
				mode_is_key = true;
			}
		}
		return rv;
	}
	Php_srl_itm_ary Parse_array(int bgn, int subs_len) {	// enters after '{'; EX: 'a:1{' -> Parse_array
		Php_srl_itm_ary rv = factory.Ary(bgn, bgn);
		for (int i = 0; i < subs_len; i++) {
			Php_srl_itm_kv kv = factory.Kv();
			Php_srl_itm key_itm = Parse_itm(pos);

			// handle null kv; PAGE:en.w:Abziri DATE:2017-11-29
			if (	key_itm.Tid() == Php_srl_itm_.Tid_string 
				&&	String_.Eq(Php_srl_parser.NULL_ARRAY_ITEM, (String)key_itm.Val())) {
				rv.Subs_add(null);
				continue;
			} 
			kv.Key_(key_itm);
			Php_srl_itm val_itm = Parse_itm(pos);
			kv.Val_(val_itm);
			rv.Subs_add(kv);
		}
		return rv;
	}
	Php_srl_itm Parse_itm(int bgn) {
		pos = bgn;
		Php_srl_itm rv = null;
		byte b = raw[pos];
		switch (b) {
			case AsciiByte.Ltr_N:		// EX: 'N;'
				rv = factory.Nil();
				pos = Chk(raw, pos + 1, AsciiByte.Semic);
				break;
			case AsciiByte.Ltr_b:		// EX: 'b:0;' or 'b:1;'
				pos = Chk(raw, pos + 1, AsciiByte.Colon);
				b = raw[pos];
				switch (b) {
					case AsciiByte.Num1: 	rv = factory.Bool_y(); break;
					case AsciiByte.Num0:	rv = factory.Bool_n(); break;
					default:				throw err_(raw, pos, raw_len, "unknown boolean type {0}", Char_.To_str(b));
				}
				pos = Chk(raw, pos + 1, AsciiByte.Semic);
				break;
			case AsciiByte.Ltr_i:		// EX: 'i:123;'
				rv = Parse_int(pos);
				pos = Chk(raw, pos, AsciiByte.Semic);
				break;
			case AsciiByte.Ltr_d:		// EX: 'd:1.23;'
				pos = Chk(raw, pos + 1, AsciiByte.Colon);
				int double_end = Bry_find_.Find_fwd(raw, AsciiByte.Semic, pos, raw_len);
				String double_str = String_.new_a7(raw, pos, double_end);
				double double_val = 0;
				if		(String_.Eq(double_str, "INF")) double_val = Double_.Inf_pos;
				else if (String_.Eq(double_str, "NAN")) double_val = Double_.NaN;
				else 									double_val = Double_.parse(double_str);
				rv = factory.Double(pos, double_end, double_val);
				pos = Chk(raw, double_end, AsciiByte.Semic);
				break;
			case AsciiByte.Ltr_s:		// EX: 's:3:"abc";'
				int len_val = Parse_int(pos).Val_as_int();
				pos = Chk(raw, pos, AsciiByte.Colon);
				pos = Chk(raw, pos, AsciiByte.Quote);
				int str_end = pos + len_val;
				String str_val = String_.new_u8(raw, pos, str_end);
				rv = factory.Str(pos, str_end, str_val);
				pos = Chk(raw, str_end, AsciiByte.Quote);
				pos = Chk(raw, pos, AsciiByte.Semic);
				break;
			case AsciiByte.Ltr_a:		// EX: 'a:0:{}'
				int subs_len = Parse_int(pos).Val_as_int();
				pos = Chk(raw, pos, AsciiByte.Colon);
				pos = Chk(raw, pos, AsciiByte.CurlyBgn);
				rv = Parse_array(pos, subs_len);
				pos = Chk(raw, pos, AsciiByte.CurlyEnd);
				break;
			case AsciiByte.Ltr_O:		// EX: 'O:42:"Scribunto_LuaStandaloneInterpreterFunction":1:{s:2:"id";i:123;}'
				int func_bgn = pos;
				pos += 62; // 64= len of constant String after ":42:"Scribunto...."
				int func_id = Parse_int_val(pos);
				rv = factory.Func(func_bgn, pos, func_id);
				pos += 2;
				break;
			default: throw err_(raw, pos, "unexpected type: {0}", Char_.To_str(b));
		}
		return rv;
	}
	int Parse_int_val(int bgn) {
		pos = bgn;
		pos = Chk(raw, pos + 1, AsciiByte.Colon);
		int int_end = Skip_while_num(raw, raw_len, pos, true);
		int int_val = Bry_.To_int_or(raw, pos, int_end, Int_.Min_value);
		pos = int_end;
		return int_val;		
	}
	Php_srl_itm_int Parse_int(int bgn) {
		pos = bgn;
		pos = Chk(raw, pos + 1, AsciiByte.Colon);
		int int_end = Skip_while_num(raw, raw_len, pos, true);
		int int_val = Bry_.To_int_or(raw, pos, int_end, Int_.Min_value);
		Php_srl_itm_int rv = factory.Int(pos, int_end, int_val);
		pos = int_end;
		return rv;
	}
	int Chk(byte[] raw, int i, byte expd) {
		byte actl = raw[i];
		if (actl == expd)
			return i + 1;
		else
			throw err_(raw, i, "expected '{0}' but got '{1}'", Char_.To_str(expd), Char_.To_str(actl));
	}
	int Skip_while_num(byte[] raw, int raw_len, int bgn, boolean num_is_int) {
		int num_len = 1;
		for (int i = bgn; i < raw_len; i++) {
			byte b = raw[i];			
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					break;
				case AsciiByte.Dot:
				case AsciiByte.Dash:
					break;
				default:
					if (num_is_int && num_len < 11) {
						return i;
					}
					else
						return i;
			}
		}
		throw err_(raw, raw_len, raw_len, "skip_ws found eos");
	}
	Err err_(byte[] raw, int bgn, String fmt, Object... args) {return err_(raw, bgn, raw.length, fmt, args);}
	Err err_(byte[] raw, int bgn, int raw_len, String fmt, Object... args) {
		String msg = String_.Format(fmt, args) + " " + Int_.To_str(bgn) + " " + String_.new_u8__by_len(raw, bgn, 20);
		return Err_.new_wo_type(msg);
	}
	public static final String NULL_ARRAY_ITEM = "NULL_ARRAY_ITEM";
}
class Php_srl_factory {
	public Php_srl_itm		Nil()									{return Php_srl_itm_nil.Nil;}
	public Php_srl_itm		Bool_n()								{return Php_srl_itm_bool.Bool_n;}
	public Php_srl_itm		Bool_y()								{return Php_srl_itm_bool.Bool_y;}
	public Php_srl_itm_int	Int(int bgn, int end, int v)			{return new Php_srl_itm_int(bgn, end, v);}
	public Php_srl_itm		Double(int bgn, int end, double v)		{return new Php_srl_itm_double(bgn, end, v);}
	public Php_srl_itm		Str(int bgn, int end)					{return new Php_srl_itm_str(bgn, end, null);}
	public Php_srl_itm		Str(int bgn, int end, String v)			{return new Php_srl_itm_str(bgn, end, v);}
	public Php_srl_itm_func	Func(int bgn, int end, int v)			{return new Php_srl_itm_func(bgn, end, v);}
	public Php_srl_itm_ary	Ary(int bgn, int end)					{return new Php_srl_itm_ary(bgn, end);}
	public Php_srl_itm_kv	Kv()									{return new Php_srl_itm_kv();}
}
