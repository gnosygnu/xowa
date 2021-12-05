/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scribunto.libs;

import gplx.objects.arrays.ArrayUtl;
import gplx.objects.primitives.BoolUtl;
import gplx.Bry_;
import gplx.objects.lists.CompareAbleUtl;
import gplx.Double_;
import gplx.Float_;
import gplx.Int_;
import gplx.Keyval;
import gplx.Keyval_;
import gplx.List_adp_;
import gplx.Long_;
import gplx.Object_;
import gplx.Type_;
import gplx.Type_ids_;
import gplx.langs.jsons.Json_ary;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_itm;
import gplx.langs.jsons.Json_itm_;
import gplx.langs.jsons.Json_kv;
import gplx.langs.jsons.Json_nde;
import gplx.langs.jsons.Json_parser;
import gplx.langs.jsons.Json_wtr;
import gplx.objects.lists.ComparerAble;
public class Scrib_lib_text__json_util {
	private final Json_wtr wtr = new Json_wtr();
	public void Reindex_arrays(Scrib_lib_text__reindex_data rv, Keyval[] kv_ary, boolean is_encoding) {
		int next = 0;
		if (is_encoding) {
			ArrayUtl.Sort(kv_ary, KeyVal__sorter__key_is_numeric.Instance);
			next = 1;
		}
		boolean is_sequence = true;
		int len = kv_ary.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kv_ary[i];
			Object kv_val = kv.Val();
			if (kv_val != null && Type_.Eq(kv_val.getClass(), Keyval[].class)) {
				Reindex_arrays(rv, (Keyval[])kv_val, is_encoding);
				if (!rv.Rv_is_kvy())
					kv.Val_(rv.Rv_as_ary());
			}
			if (is_sequence) {
				if (kv.Key_tid() == Type_ids_.Id__int) {
					int kv_key_as_int = Int_.Cast(kv.Key_as_obj());
					is_sequence = next++ == kv_key_as_int;
				}
				else {
					int kv_key_to_int = Bry_.To_int_or__strict(Bry_.new_a7(kv.Key()), -1);	// NOTE: a7 b/c proc is only checking for digits; none a7 bytes will be replaced by ?
					if (is_encoding && kv_key_to_int != -1) {
						is_sequence = next == kv_key_to_int;
						++next;
					}
					else
						is_sequence = false;
				}
			}
		}
		if (is_sequence) {
			if (is_encoding) {
				Object rv_as_ary = To_array_values(kv_ary);	
				rv.Init(BoolUtl.N, null, rv_as_ary);
				return;
			} else {
				Convert_to_base1(kv_ary);			// PHP: return $arr ? array_combine( range( 1, count( $arr ) ), $arr ) : $arr;
			}
		}
		rv.Init(BoolUtl.Y, kv_ary, null);
	}
	private static Object[] To_array_values(Keyval[] ary) {
		int len = ary.length;
		Object[] rv = new Object[len];
		for (int i = 0; i < len; ++i) {
			Keyval itm = ary[i];
			rv[i] = itm.Val();
		}
		return rv;
	}
	private static void Convert_to_base1(Keyval[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Keyval itm = ary[i];
			itm.Key_(i + 1);
		}
	}
	public Keyval[] Decode_rslt_as_nde() {return decode_rslt_as_nde;} private Keyval[] decode_rslt_as_nde;
	public Keyval[] Decode_rslt_as_ary() {return decode_rslt_as_ary;} private Keyval[] decode_rslt_as_ary;
	public byte Decode(Json_parser parser, byte[] src, int flag) {
		synchronized (wtr) {
			Json_doc jdoc = parser.Parse(src);
			if (jdoc.Root_grp().Tid() == Json_itm_.Tid__ary) {
				this.decode_rslt_as_ary = Decode_ary_top(jdoc.Root_ary());
				return BoolUtl.NByte;
			}
			else {
				Json_nde root = (Json_nde)jdoc.Root_grp();
				int len = root.Len();
				this.decode_rslt_as_nde = new Keyval[len];
				for (int i = 0; i < len; ++i) {
					Json_kv json_kv = root.Get_at_as_kv(i);
					String kv_str = json_kv.Key_as_str();
					Object kv_val = Decode_obj(json_kv.Val());
					int kv_int = Int_.Parse_or(kv_str, Int_.Min_value);
					decode_rslt_as_nde[i] = kv_int == Int_.Min_value ? Keyval_.new_(kv_str, kv_val) : Keyval_.int_(kv_int, kv_val);	// use int_key if applicable; PAGE:it.s:Il_Re_Cervo; DATE:2015-12-06
				}
				return BoolUtl.YByte;
			}
		}
	}
	private Object Decode_obj(Json_itm itm) {
		int itm_tid = itm.Tid();
		switch (itm_tid) {
			case Json_itm_.Tid__ary:	return Decode_ary_sub(Json_ary.cast(itm));
			case Json_itm_.Tid__nde:	return Decode_nde(Json_nde.Cast(itm));
			default:					return itm.Data();
		}
	}
	private Keyval[] Decode_ary_top(Json_ary ary) {	// NOTE: top-level arrays must be returned as numbered nodes; EX: [{1:a}, {2:b}, {3:c}] not [a, b, c]; DATE:2016-08-01
		int len = ary.Len();
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; ++i) {
			Json_itm itm = ary.Get_at(i);
			rv[i] = Keyval_.int_(i + List_adp_.Base1, Decode_obj(itm));
		}
		return rv;
	}
	private Object Decode_ary_sub(Json_ary ary) {
		boolean subs_are_primitive = true;

		// if ary has sub_ary / sub_nde, then unflag subs_are_primitive
		int len = ary.Len();
		if (len > 0) {
			Json_itm sub = ary.Get_at(0);
			switch (sub.Tid()) {
				case Json_itm_.Tid__nde:
				case Json_itm_.Tid__ary:
					subs_are_primitive = false;
					break;
			}
		}

		// generate array
		Object[] rv = null;
		// if subs_are_primitive, then just generate an Object[]
		if (subs_are_primitive) {
			rv = new Object[len];
			for (int i = 0; i < len; ++i) {
				Json_itm itm = ary.Get_at(i);
				rv[i] = Decode_obj(itm);
			}
		}
		// else generate a Keyval where val is ary / nde
		else {
			rv = new Keyval[len];
			for (int i = 0; i < len; ++i) {
				Json_itm itm = ary.Get_at(i);
				rv[i] = Keyval_.int_(i, Decode_obj(itm));
			}
		}
		return rv;
	}
	private Keyval[] Decode_nde(Json_nde nde) {
		int len = nde.Len();
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; ++i) {
			Json_kv itm = nde.Get_at_as_kv(i);
			String kv_str = itm.Key_as_str();
			int kv_int = Int_.Parse_or(kv_str, Int_.Min_value);
			Object kv_val = Decode_obj(itm.Val());
			rv[i] = kv_int == Int_.Min_value ? Keyval_.new_(kv_str, kv_val) : Keyval_.int_(kv_int, kv_val);	// use int_key if applicable; PAGE:it.s:Il_Re_Cervo; DATE:2015-12-06
		}
		return rv;
	}
	public byte[] Encode_as_nde(Keyval[] itm, int flag, int skip) {
		synchronized (wtr ) {
			wtr.Clear().Doc_nde_bgn();
			Encode_kv_ary(itm);
			return wtr.Doc_nde_end().To_bry_and_clear();
		}
	}
	public byte[] Encode_as_ary(Object ary, int flag, int skip) {
		synchronized (wtr ) {
			wtr.Clear().Doc_ary_bgn();
			Encode_ary(ary);
			return wtr.Doc_ary_end().To_bry_and_clear();
		}
	}
	private void Encode_kv_ary(Keyval[] kv_ary) {
		int len = kv_ary.length;
		for (int i = 0; i < len; ++i) {
			Keyval kv = kv_ary[i];
			Encode_kv(kv);
		}
	}
	private void Encode_kv(Keyval kv) {
		Object kv_val = kv.Val();
		Class<?> type = Type_.Type_by_obj(kv_val);
		if		(Type_.Eq(type, Keyval[].class)) {
			wtr.Nde_bgn(kv.Key());
			Encode_kv_ary((Keyval[])kv_val);
			wtr.Nde_end();
		}
		else if (Type_.Is_array(type)) {	// encode as array
			wtr.Ary_bgn(kv.Key());
			Encode_ary(kv_val);
			wtr.Ary_end();
		}
		else if (Type_.Eq(type, Int_.Cls_ref_type))			wtr.Kv_int(kv.Key(), Int_.Cast(kv_val));
		else if (Type_.Eq(type, Long_.Cls_ref_type))		wtr.Kv_long(kv.Key(), Long_.cast(kv_val));
		else if (Type_.Eq(type, Float_.Cls_ref_type))		wtr.Kv_float(kv.Key(), Float_.cast(kv_val));
		else if (Type_.Eq(type, Double_.Cls_ref_type))		wtr.Kv_double(kv.Key(), Double_.cast(kv_val));
		else if (Type_.Eq(type, BoolUtl.ClsRefType))		wtr.Kv_bool(kv.Key(), BoolUtl.Cast(kv_val));
		else													wtr.Kv_str(kv.Key(), Object_.Xto_str_strict_or_null(kv_val));
	}
	private void Encode_ary(Object ary) {
		int ary_len = ArrayUtl.Len(ary);
		for (int j = 0; j < ary_len; ++j)
			wtr.Ary_itm_obj(ArrayUtl.GetAt(ary, j));
	}
	public static final int
		Flag__none				= 0
	,	Flag__preserve_keys		= 1
	,	Flag__try_fixing		= 2
	,	Flag__pretty			= 4
	;
	public static final int
		Skip__utf8				= 1
	,	Skip__xml				= 2
	,	Skip__all				= 3
	;
	public static final int
		Opt__force_assoc		= 1
	;
}
class KeyVal__sorter__key_is_numeric implements ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Keyval lhs_itm = (Keyval)lhsObj;
		Keyval rhs_itm = (Keyval)rhsObj;
		int lhs_int = Int_.Parse_or(lhs_itm.Key(), Int_.Min_value);
		int rhs_int = Int_.Parse_or(rhs_itm.Key(), Int_.Min_value);
		return CompareAbleUtl.Compare((Integer)lhs_int, (Integer)rhs_int);
	}
	public static final KeyVal__sorter__key_is_numeric Instance = new KeyVal__sorter__key_is_numeric(); KeyVal__sorter__key_is_numeric() {}
}
