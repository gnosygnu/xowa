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
package gplx.langs.jsons;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.arrays.IntAry;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.KeyVal;
import gplx.types.errs.ErrUtl;
public class Json_wtr {
	private final BryWtr bfr = BryWtr.NewWithSize(255);
	private final IntAry idx_stack = new IntAry(4);
	private int idx = 0;		
	public BryWtr Bfr() {return bfr;}
	public void Indent_(int v) {this.indent = v;} private int indent;
	public byte Opt_quote_byte() {return opt_quote_byte;} public Json_wtr Opt_quote_byte_(byte v) {opt_quote_byte = v; return this;} private byte opt_quote_byte = AsciiByte.Quote;
	public boolean Opt_ws() {return opt_ws;} public Json_wtr Opt_ws_(boolean v) {opt_ws = v; return this;} private boolean opt_ws = true;
	public boolean Opt_backslash_2x() {return opt_backslash_2x;} public Json_wtr Opt_backslash_2x_(boolean v) {opt_backslash_2x = v; return this;} private boolean opt_backslash_2x = false;
	public byte[] To_bry_and_clear() {return bfr.ToBryAndClear();}
	public String To_str_and_clear() {return bfr.ToStrAndClear();}
	public Json_wtr () {this.Clear();}
	public Json_wtr Clear() {
		indent = -1;
		idx_stack.Clear();
		idx = 0;
		return this;
	}
	public Json_wtr Doc_nde_bgn() {return Write_grp_bgn(Sym_nde_bgn);}
	public Json_wtr Doc_nde_end() {Write_grp_end(BoolUtl.Y, Sym_nde_end); return Write_nl();}
	public Json_wtr Doc_ary_bgn() {return Write_grp_bgn(Sym_ary_bgn);}
	public Json_wtr Doc_ary_end() {Write_grp_end(BoolUtl.N, Sym_ary_end); return Write_nl();}
	public Json_wtr Nde_bgn_ary() {return Nde_bgn(BryUtl.Empty);}
	public Json_wtr Nde_bgn(String key) {return Nde_bgn(BryUtl.NewU8(key));}
	public Json_wtr Nde_bgn(byte[] key) {
		Write_indent_itm();
		if (key == BryUtl.Empty) {
			if (opt_ws) bfr.DelBy1();	// remove trailing space from Write_indent_itm
			++idx;
		}
		else
			Write_key(key);
		Write_nl();
		return Write_grp_bgn(Sym_nde_bgn);
	}
	public Json_wtr Nde_end() {
		Write_grp_end(BoolUtl.Y, Sym_nde_end);
		return Write_nl();
	}
	public Json_wtr Ary_bgn_ary() {return Ary_bgn(StringUtl.Empty);}
	public Json_wtr Ary_bgn(String nde) {
		Write_indent_itm();
		if (nde == StringUtl.Empty) {
			if (opt_ws) bfr.DelBy1();	// remove trailing space from Write_indent_itm
			++idx;
		}
		else
			Write_key(BryUtl.NewU8(nde));
		return Ary_bgn_keyless();
	}
	private Json_wtr Ary_bgn_keyless() {
		Write_nl();
		return Write_grp_bgn(Sym_ary_bgn);
	}
	public Json_wtr Ary_itm_str(String itm) {return Ary_itm_by_type_tid(TypeIds.IdStr, itm);}
	public Json_wtr Ary_itm_bry(byte[] itm) {return Ary_itm_by_type_tid(TypeIds.IdBry, itm);}
	public Json_wtr Ary_itm_obj(Object itm) {return Ary_itm_by_type_tid(TypeIds.ToIdByObj(itm), itm);}
	public Json_wtr Ary_itm_by_type_tid(int itm_type_tid, Object itm) {
		Write_indent_itm();
		Write_val_obj(BoolUtl.Y, itm_type_tid, itm);
		Write_nl();
		++idx;
		return this;
	}
	public Json_wtr Ary_end() {
		Write_grp_end(BoolUtl.N, Sym_ary_end);
		return Write_nl();
	}
	public Json_wtr Kv_bool_as_mw(String key, boolean val)	{
		if (val) Kv_bry(key, BryUtl.Empty);	// if true, write 'key:""'; if false, write nothing
		return this;
	}
	public Json_wtr Kv_bool(String key, boolean val)		{return Kv_bool(BryUtl.NewU8(key), val);}
	public Json_wtr Kv_bool(byte[] key, boolean val)		{return Kv_raw(key, val ? BoolUtl.TrueBry : BoolUtl.FalseBry);}
	public Json_wtr Kv_int(String key, int val)			{return Kv_raw(BryUtl.NewU8(key), IntUtl.ToBry(val));}
	public Json_wtr Kv_long(String key, long val)		{return Kv_raw(BryUtl.NewU8(key), BryUtl.NewA7(LongUtl.ToStr(val)));}
	public Json_wtr Kv_float(String key, float val)		{return Kv_raw(BryUtl.NewU8(key), BryUtl.NewA7(FloatUtl.ToStr(val)));}
	public Json_wtr Kv_double(String key, double val)	{return Kv_raw(BryUtl.NewU8(key), BryUtl.NewA7(DoubleUtl.ToStr(val)));}
	private Json_wtr Kv_raw(byte[] key, byte[] val) {
		Write_indent_itm();
		Write_key(key);
		bfr.Add(val);
		Write_nl();
		return this;
	}
	public Json_wtr Kv_str(String key, String val) {return Kv_bry(BryUtl.NewU8(key), val == null ? null : BryUtl.NewU8(val));}
	public Json_wtr Kv_str(byte[] key, String val) {return Kv_bry(key, BryUtl.NewU8(val));}
	public Json_wtr Kv_bry(String key, byte[] val) {return Kv_bry(BryUtl.NewU8(key), val);}
	public Json_wtr Kv_bry(byte[] key, byte[] val) {
		Write_indent_itm();
		Write_key(key);
		Write_str(val);
		Write_nl();
		return this;
	}
	public Object Get_x(Json_itm itm) {
		switch (itm.Tid()) {
			case Json_itm_.Tid__ary:
			case Json_itm_.Tid__nde:
				return itm;
			default:
			case Json_itm_.Tid__kv:		throw ErrUtl.NewUnsupported();
			case Json_itm_.Tid__bool:
			case Json_itm_.Tid__int:
			case Json_itm_.Tid__decimal:
			case Json_itm_.Tid__str:
				return itm.Data();
		}
	}
	public void Kv_itm_x(byte[] key, Json_itm itm) {
		Object val = Get_x(itm);
		int val_tid = TypeIds.ToIdByObj(val);
		Kv_obj(key, val, val_tid);
	}
	public Json_wtr Kv_obj(byte[] key, Object val, int val_tid) {
		Write_indent_itm();
		Write_key(key);
		Write_val_obj(BoolUtl.N, val_tid, val);
		Write_nl();
		return this;
	}
	private Json_wtr Write_grp_bgn(byte[] grp_sym) {return Write_grp_bgn(grp_sym, BoolUtl.Y);}
	private Json_wtr Write_grp_bgn(byte[] grp_sym, boolean write_indent) {
		idx_stack.Add(idx);
		idx = 0;
		++indent;
		if (write_indent) Write_indent();
		bfr.Add(grp_sym);
		return this;
	}
	private Json_wtr Write_grp_end(boolean grp_is_nde, byte[] grp_sym) {
		if ((grp_is_nde && idx == 0) || (!grp_is_nde && idx == 0))
			Write_nl();
		Write_indent();
		--indent;
		bfr.Add(grp_sym);
		this.idx = idx_stack.PopOr(0);
		return this;
	}
	private Json_wtr Write_key(byte[] bry) {
		Write_str(bry);				// "key"
		bfr.AddByteColon();		// ":"
		++idx;
		return this;
	}
	private void Write_val_obj(boolean called_by_ary, int type_tid, Object obj) {
		switch (type_tid) {
			case TypeIds.IdNull:				bfr.Add(ObjectUtl.NullBry); break;
			case TypeIds.IdBool:				bfr.AddBool(BoolUtl.Cast(obj)); break;
			case TypeIds.IdByte:				bfr.AddByte(ByteUtl.Cast(obj)); break;
			case TypeIds.IdInt:				bfr.AddIntVariable(IntUtl.Cast(obj)); break;
			case TypeIds.IdLong:				bfr.AddLongVariable(LongUtl.Cast(obj)); break;
			case TypeIds.IdFloat:				bfr.AddFloat(FloatUtl.Cast(obj)); break;
			case TypeIds.IdDouble:				bfr.AddDouble(DoubleUtl.Cast(obj)); break;
			case TypeIds.IdStr:				Write_str(BryUtl.NewU8((String)obj)); break;
			case TypeIds.IdBry:				Write_str((byte[])obj); break;
			case TypeIds.IdChar:
			case TypeIds.IdDate:
			case TypeIds.IdDecimal:			Write_str(BryUtl.NewU8(ObjectUtl.ToStrOrEmpty(obj))); break;
			case TypeIds.IdObj:
				int grp_type = Grp_type__get(obj);
				if (grp_type < Grp_type__json_ary)
					Write_val_obj__nde(called_by_ary, grp_type, obj);
				else
					Write_val_itm__ary(called_by_ary, grp_type, obj);
				break;
			default:								throw ErrUtl.NewUnhandled(type_tid);
		}
	}
	private void Handle_nde_as_ary_itm_0() {
		if (idx == 0) {	// if nde, and first item, then put on new line
			bfr.DelBy1();
			if (opt_ws) {
				bfr.AddByteNl();
				++indent;
				Write_indent();
				--indent;
			}
		}
	}
	private void Write_val_obj__nde(boolean called_by_ary, int grp_type, Object obj) {
		if (grp_type == Grp_type__json_nde) {
			if (idx == 0) {	// if nde, and first item, then put on new line
				if (!called_by_ary) {
					bfr.DelBy1();
					if (opt_ws) {
						bfr.AddByteNl();
						++indent;
						Write_indent();
						--indent;
					}
				}
			}
			bfr.AddByteNl();
			Write_grp_bgn(Sym_nde_bgn, BoolUtl.Y);
			Json_nde sub_nde = (Json_nde)obj;
			int sub_nde_len = sub_nde.Len();
			for (int i = 0; i < sub_nde_len; ++i) {
				Json_kv sub_kv = sub_nde.Get_at_as_kv(i);
				Kv_itm_x(sub_kv.Key_as_bry(), sub_kv.Val());
			}
		}
		else {
			Handle_nde_as_ary_itm_0();
			Write_grp_bgn(Sym_nde_bgn, BoolUtl.N);
			KeyVal[] kvy = (KeyVal[])obj;
			int kvy_len = kvy.length;
			for (int i = 0; i < kvy_len; ++i) {
				KeyVal kv = kvy[i];
				Object kv_val = kv.Val();
				Kv_obj(BryUtl.NewU8(kv.KeyToStr()), kv_val, TypeIds.ToIdByObj(kv_val));
			}
		}
		Write_grp_end(BoolUtl.Y, Sym_nde_end);
	}
	private void Write_val_itm__ary(boolean called_by_ary, int grp_type, Object obj) {
		Ary_bgn_keyless();
		if (grp_type == Grp_type__json_ary) {
			Json_ary sub_ary = (Json_ary)(obj);
			int len = sub_ary.Len();
			for (int i = 0; i < len; ++i) {
				Json_itm sub_itm = sub_ary.Get_at(i);
				Ary_itm_obj(Get_x(sub_itm));
			}
		}
		else {
			Object ary = ArrayUtl.Cast(obj);
			int len = ArrayUtl.Len(ary);
			for (int i = 0; i < len; ++i) {
				Object itm = ArrayUtl.GetAt(ary, i);
				Ary_itm_obj(itm);
			}
		}
		Write_grp_end(BoolUtl.N, Sym_ary_end);
	}
	private void Write_str(byte[] bry) {
		if (bry == null) {bfr.Add(ObjectUtl.NullBry); return;}
		int len = bry.length;
		int backslash_count = opt_backslash_2x ? 3 : 1;	// NOTE: 3 handles backslashes usurped by javascript; EX: '{"val":"\\\\"}' --javascript--> '{"val":"\\"}' --json--> '{"val":"\"}'
		bfr.AddByte(opt_quote_byte);
		for (int i = 0; i < len; ++i) {
			byte b = bry[i];
			switch (b) {
				case AsciiByte.Backslash:	bfr.AddByteRepeat(AsciiByte.Backslash, backslash_count).AddByte(b); break; // "\"	-> "\\"; needed else js will usurp \ as escape; EX: "\&" -> "&"; DATE:2014-06-24
				case AsciiByte.Quote:		bfr.AddByteRepeat(AsciiByte.Backslash, backslash_count).AddByte(b); break;
				case AsciiByte.Apos: // // "'" -> "'''"; needed else xocfg fails; DATE:2016-12-07
					if (opt_backslash_2x)
						bfr.AddByteRepeat(AsciiByte.Backslash, 1).AddByte(b);
					else
						bfr.AddByte(b);
					break;
				case AsciiByte.Nl:			bfr.AddByteRepeat(AsciiByte.Backslash, 2).AddByte(AsciiByte.Ltr_n); break;	// "\n" -> "\\n"
				case AsciiByte.Cr:			bfr.AddByteRepeat(AsciiByte.Backslash, 2).AddByte(AsciiByte.Ltr_r); break;	// "\r" -> "\\r"; DATE:2017-03-02
				case AsciiByte.Tab:		bfr.AddByteRepeat(AsciiByte.Backslash, 2).AddByte(AsciiByte.Ltr_t); break;	// "\t" -> "\\t"; DATE:2017-03-02
				default:					bfr.AddByte(b); break;
			}
		}
		bfr.AddByte(opt_quote_byte);
	}
	private void Write_indent_itm() {
		if (idx == 0) {
			if (opt_ws)
				bfr.AddByteSpace();
		}
		else {
			Write_indent();
			bfr.Add(Sym_itm_spr);
			if (opt_ws) bfr.AddByteSpace();
		}
	}
	private void Write_indent() {
		if (opt_ws && indent > 0)
			bfr.AddByteRepeat(AsciiByte.Space, indent * 2);
	}
	private Json_wtr Write_nl() {
		if (opt_ws) bfr.AddByteNl();
		return this;
	}
	private static final byte[]
	  Sym_nde_bgn	= BryUtl.NewA7("{")
	, Sym_nde_end	= BryUtl.NewA7("}")
	, Sym_ary_bgn	= BryUtl.NewA7("[")
	, Sym_ary_end	= BryUtl.NewA7("]")
	, Sym_itm_spr	= BryUtl.NewA7(",")
	;
	private static final int Grp_type__json_nde = 1, Grp_type__kv_ary = 2, Grp_type__json_ary = 3, Grp_type__obj_ary = 4;
	private static int Grp_type__get(Object obj) {
		Class<?> type = obj.getClass();
		if		(ClassUtl.Eq(type, KeyVal[].class))		return Grp_type__kv_ary;
		else if (ClassUtl.IsArray(type))					return Grp_type__obj_ary;
		else if (ClassUtl.Eq(type, Json_nde.class))		return Grp_type__json_nde;
		else if (ClassUtl.Eq(type, Json_ary.class))		return Grp_type__json_ary;
		else												throw ErrUtl.NewUnhandled(type);
	}
}
