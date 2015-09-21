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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.langs.jsons.*;
class Scrib_lib_text__json_util {
	private final Json_wtr wtr = new Json_wtr();
	public void Reindex_arrays(Scrib_lib_text__reindex_data rv, KeyVal[] kv_ary, boolean is_encoding) {
		int next = 0;
		if (is_encoding) {
			Array_.Sort(kv_ary, KeyVal__sorter__key_is_numeric.I);
			next = 1;
		}
		boolean is_sequence = true;
		int len = kv_ary.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = kv_ary[i];
			Object kv_val = kv.Val();
			if (kv_val != null && Type_adp_.Eq(kv_val.getClass(), KeyVal[].class)) {
				Reindex_arrays(rv, (KeyVal[])kv_val, is_encoding);
				if (!rv.Rv_is_kvy())
					kv.Val_(rv.Rv_as_ary());
			}
			if (is_sequence) {
				if (kv.Key_tid() == Type_adp_.Tid__int) {
					int kv_key_as_int = Int_.cast(kv.Key_as_obj());
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
				rv.Init(Bool_.N, null, rv_as_ary);
				return;
			} else {
				Convert_to_base1(kv_ary);			// PHP: return $arr ? array_combine( range( 1, count( $arr ) ), $arr ) : $arr;
			}
		}
		rv.Init(Bool_.Y, kv_ary, null);
	}
	private static Object[] To_array_values(KeyVal[] ary) {
		int len = ary.length;
		Object[] rv = new Object[len];
		for (int i = 0; i < len; ++i) {
			KeyVal itm = ary[i];
			rv[i] = itm.Val();
		}
		return rv;
	}
	private static void Convert_to_base1(KeyVal[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			KeyVal itm = ary[i];
			itm.Key_(i + 1);
		}
	}
	public KeyVal[] Decode_rslt_as_nde() {return decode_rslt_as_nde;} private KeyVal[] decode_rslt_as_nde;
	public Object	Decode_rslt_as_ary() {return decode_rslt_as_ary;} private Object   decode_rslt_as_ary;
	public byte Decode(Json_parser parser, byte[] src, int flag) {
		synchronized (wtr) {
			Json_doc jdoc = parser.Parse(src);
			if (jdoc.Root_grp().Tid() == Json_itm_.Tid__ary) {
				this.decode_rslt_as_ary = Decode_ary(jdoc.Root_ary());
				return Bool_.N_byte;
			}
			else {
				Json_nde root = (Json_nde)jdoc.Root_grp();
				int len = root.Len();
				this.decode_rslt_as_nde = new KeyVal[len];
				for (int i = 0; i < len; ++i) {
					Json_kv json_kv = root.Get_at_as_kv(i);
					String kv_str = json_kv.Key_as_str();
					Object kv_val = Decode_obj(json_kv.Val());
					decode_rslt_as_nde[i] = KeyVal_.new_(kv_str, kv_val);
				}
				return Bool_.Y_byte;
			}
		}
	}
	private Object Decode_obj(Json_itm itm) {
		int itm_tid = itm.Tid();
		switch (itm_tid) {
			case Json_itm_.Tid__ary:	return Decode_ary(Json_ary.cast(itm));
			case Json_itm_.Tid__nde:	return Decode_nde(Json_nde.cast(itm));
			default:					return itm.Data();
		}
	}
	private Object Decode_ary(Json_ary ary) {
		int len = ary.Len();
		Object rv = Array_.Create(Object.class, len);
		for (int i = 0; i < len; ++i) {
			Json_itm itm = ary.Get_at(i);
			Array_.Set_at(rv, i, Decode_obj(itm));
		}
		return rv;
	}
	private KeyVal[] Decode_nde(Json_nde nde) {
		int len = nde.Len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; ++i) {
			Json_kv itm = nde.Get_at_as_kv(i);
			rv[i] = KeyVal_.new_(itm.Key_as_str(), Decode_obj(itm.Val()));
		}
		return rv;
	}
	public byte[] Encode_as_nde(KeyVal[] itm, int flag, int skip) {
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
	private void Encode_kv_ary(KeyVal[] kv_ary) {
		int len = kv_ary.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = kv_ary[i];
			Encode_kv(kv);
		}
	}
	private void Encode_kv(KeyVal kv) {
		Object kv_val = kv.Val();
		Class<?> type = Type_adp_.ClassOf_obj(kv_val);
		if		(Type_adp_.Eq(type, KeyVal[].class)) {
			wtr.Nde_bgn(kv.Key());
			Encode_kv_ary((KeyVal[])kv_val);
			wtr.Nde_end();
		}
		else if (Type_adp_.Is_array(type)) {	// encode as array
			wtr.Ary_bgn(kv.Key());
			Encode_ary(kv_val);
			wtr.Ary_end();
		}
		else if (Type_adp_.Eq(type, Int_.Cls_ref_type))			wtr.Kv_int(kv.Key(), Int_.cast(kv_val));
		else if (Type_adp_.Eq(type, Long_.Cls_ref_type))		wtr.Kv_long(kv.Key(), Long_.cast(kv_val));
		else if (Type_adp_.Eq(type, Float_.Cls_ref_type))		wtr.Kv_float(kv.Key(), Float_.cast(kv_val));
		else if (Type_adp_.Eq(type, Double_.Cls_ref_type))		wtr.Kv_double(kv.Key(), Double_.cast(kv_val));
		else if (Type_adp_.Eq(type, Bool_.Cls_ref_type))		wtr.Kv_bool(kv.Key(), Bool_.cast(kv_val));
		else													wtr.Kv_str(kv.Key(), Object_.Xto_str_strict_or_null(kv_val));
	}
	private void Encode_ary(Object ary) {
		int ary_len = Array_.Len(ary);
		for (int j = 0; j < ary_len; ++j)
			wtr.Ary_itm_obj(Array_.Get_at(ary, j));
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
class KeyVal__sorter__key_is_numeric implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		KeyVal lhs_itm = (KeyVal)lhsObj;
		KeyVal rhs_itm = (KeyVal)rhsObj;
		int lhs_int = Int_.parse_or(lhs_itm.Key(), Int_.Min_value);
		int rhs_int = Int_.parse_or(rhs_itm.Key(), Int_.Min_value);
		return CompareAble_.Compare(lhs_int, rhs_int);
	}
	public static final KeyVal__sorter__key_is_numeric I = new KeyVal__sorter__key_is_numeric(); KeyVal__sorter__key_is_numeric() {}
}
class Scrib_lib_text__reindex_data {
	public boolean				Rv_is_kvy() {return rv_is_kvy;} private boolean rv_is_kvy;
	public KeyVal[]			Rv_as_kvy() {return rv_as_kvy;} private KeyVal[] rv_as_kvy;
	public Object		Rv_as_ary() {return rv_as_ary;} private Object rv_as_ary;	
	public void Init(boolean rv_is_kvy, KeyVal[] rv_as_kvy, Object rv_as_ary) {
		this.rv_is_kvy = rv_is_kvy;
		this.rv_as_kvy = rv_as_kvy;
		this.rv_as_ary = rv_as_ary;
	}
}
