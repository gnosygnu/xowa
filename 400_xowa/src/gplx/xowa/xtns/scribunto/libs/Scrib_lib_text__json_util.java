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
import gplx.core.json.*;
class Scrib_lib_text__json_util {
	private final Json_wtr wtr = new Json_wtr();
	public void Init_for_tests() {wtr.Opt_quote_byte_(Byte_ascii.Apos);}
	public KeyVal[] Reindex_arrays(KeyVal[] kv_ary, boolean is_encoding) {
		int next = 0;
		if (is_encoding) {
			// ksort( $arr, SORT_NUMERIC );
			next = 1;
		}
		boolean is_sequence = true;
		int len = kv_ary.length;
		for (int i = 0; i < len; ++i) {
			KeyVal kv = kv_ary[i];
			Object kv_val = kv.Val();
			if (kv_val != null && Type_adp_.Eq(kv_val.getClass(), KeyVal[].class))
				kv_val = Reindex_arrays((KeyVal[])kv_val, is_encoding);
			if (is_sequence) {
				if (kv.Key_tid() == Type_adp_.Tid__int) {
					int kv_key_as_int = Int_.cast(kv.Key_as_obj());
					is_sequence = next++ == kv_key_as_int;
				// } elseif ( $isEncoding && ctype_digit( $k ) ) {
				//	// json_decode currently doesn't return integer keys for {}
				//	$isSequence = $next++ === (int)$k;
				} else {
					is_sequence = false;
				}
			}
		}
		if (is_sequence) {
			if (is_encoding) {
				// return array_values( $arr );
			} else {
				// return $arr ? array_combine( range( 1, count( $arr ) ), $arr ) : $arr;
			}
		}
		return kv_ary;
	}
	public KeyVal[] Decode(Json_parser parser, byte[] src, int flag) {
		Json_doc jdoc = parser.Parse(src);
		Json_nde root = jdoc.Root_nde();
		int len = root.Len();
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; ++i) {
			Json_kv json_kv = root.Get_at_as_kv(i);
			String kv_str = json_kv.Key_as_str();
			Object kv_val = Decode_obj(json_kv.Val());
			rv[i] = KeyVal_.new_(kv_str, kv_val);
		}
		return rv;
	}
	private Object Decode_obj(Json_itm itm) {
		int itm_tid = itm.Tid();
		switch (itm_tid) {
			case Json_itm_.Tid__ary:	return Decode_ary(Json_ary.cast(itm));
			case Json_itm_.Tid__nde:		return Decode_nde(Json_nde.cast(itm));
			default:					return itm.Data();
		}
	}
	private Object Decode_ary(Json_ary ary) {
		int len = ary.Len();
		Object rv = Array_.Create(int.class, len);	
		for (int i = 0; i < len; ++i) {
			Json_itm itm = ary.Get_at(i);
			Array_.Set(rv, i, Decode_obj(itm));
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
	public byte[] Encode(KeyVal[] kv_ary, int flag, int skip) {
		synchronized (wtr ) {
			wtr.Clear().Doc_bgn();
			Encode_kv_ary(kv_ary);
			return wtr.Doc_end().To_bry_and_clear();
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
			Object ary = Array_.cast(kv_val);	
			int ary_len = Array_.Len(ary);
			for (int j = 0; j < ary_len; ++j)
				wtr.Ary_itm_obj(Array_.Get_at(ary, j));
			wtr.Ary_end();
		}
		else if (Type_adp_.Eq(type, Int_.Cls_ref_type))			wtr.Kv_int(kv.Key(), Int_.cast(kv_val));
		else if (Type_adp_.Eq(type, Long_.Cls_ref_type))		wtr.Kv_long(kv.Key(), Long_.cast(kv_val));
		else if (Type_adp_.Eq(type, Float_.Cls_ref_type))		wtr.Kv_float(kv.Key(), Float_.cast(kv_val));
		else if (Type_adp_.Eq(type, Double_.Cls_ref_type))		wtr.Kv_double(kv.Key(), Double_.cast(kv_val));
		else if (Type_adp_.Eq(type, Bool_.Cls_ref_type))		wtr.Kv_bool(kv.Key(), Bool_.cast(kv_val));
		else													wtr.Kv_str(kv.Key(), Object_.Xto_str_strict_or_null(kv_val));
	}		
	public static final int
		Flag__preserve_keys		= 1
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
