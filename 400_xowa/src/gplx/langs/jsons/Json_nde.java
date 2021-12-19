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
package gplx.langs.jsons;

import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
public class Json_nde extends Json_itm_base implements Json_grp {
	private final int src_bgn;
	private int src_end;
	private Json_itm[] subs = Json_itm_.Ary_empty;
	private int subs_len = 0, subs_max = 0;
	private Hash_adp_bry subs_hash;

	private Json_nde(Json_doc jdoc, int src_bgn) {
		this.jdoc = jdoc;
		this.src_bgn = src_bgn;
	}
	@Override public byte Tid() {return Json_itm_.Tid__nde;}
	public Json_doc Doc() {return jdoc;} private final Json_doc jdoc;
	public void Src_end_(int v) {this.src_end = v;}
	@Override public Object Data() {return null;}
	@Override public byte[] Data_bry() {return null;}
	public int Len() {return subs_len;}
	public Json_itm Get_at(int i) {return subs[i];}
	public Json_itm Get_as_itm_or_null(String key) {return Get_as_itm_or_null(BryUtl.NewU8(key));}
	public Json_itm Get_as_itm_or_null(byte[] key) {if (subs_hash == null) subs_hash = subs_hash_init(); return (Json_itm)subs_hash.Get_by_bry(key);}
	public Json_ary Get_as_ary(int idx)		{return Json_ary.cast(Get_at(idx));}
	public Json_nde Get_as_nde(String key)	{return Json_nde.Cast(Get_as_itm_or_null(BryUtl.NewU8(key)));}
	public Json_nde Get_as_nde(int idx)		{return Json_nde.Cast(Get_at(idx));}
	public Json_ary Get_as_ary(String key)	{return Get_as_ary(BryUtl.NewU8(key));}
	public Json_ary Get_as_ary(byte[] key) {
		Json_itm rv = Get_as_itm_or_null(key); if (rv == null) throw ErrUtl.NewArgs("key missing", "key", key);
		return Json_ary.cast(rv);
	}
	public Json_ary Get_as_ary_or_null(String key)	{return Get_as_ary_or_null(BryUtl.NewU8(key));}
	public Json_ary Get_as_ary_or_null(byte[] key) {
		Json_itm rv = Get_as_itm_or_null(key);
		return rv == null
			? null
			: Json_ary.cast(rv);
	}
	public byte[] Get_as_bry(String key) {
		byte[] rv = Get_as_bry_or(BryUtl.NewU8(key), null); if (rv == null) throw ErrUtl.NewArgs("key missing", "key", key);
		return rv;
	}
	public byte[] Get_as_bry_or(byte[] key, byte[] or) {
		Json_itm rv = Get_as_itm_or_null(key);
		return rv == null ? or : rv.Data_bry();
	}
	public String Get_as_str(String key) {
		String rv = Get_as_str_or(key, null); if (rv == null) throw ErrUtl.NewArgs("key missing", "key", key);
		return rv;
	}
	public String Get_as_str_or(String key, String or) {return Get_as_str_or(BryUtl.NewU8(key), or);}
	public String Get_as_str_or(byte[] key, String or) {
		byte[] rv = Get_as_bry_or(key, null);
		return rv == null ? or : StringUtl.NewU8(rv);
	}
	public int Get_as_int(String key) {
		int rv = Get_as_int_or(key, IntUtl.MinValue); if (rv == IntUtl.MinValue) throw ErrUtl.NewArgs("key missing", "key", key);
		return rv;
	}
	public int Get_as_int_or(String key, int or) {return Get_as_int_or(BryUtl.NewU8(key), or);}
	public int Get_as_int_or(byte[] key, int or) {
		byte[] rv = Get_as_bry_or(key, null);
		return rv == null ? or : BryUtl.ToInt(rv);
	}
	public long Get_as_long(String key) {
		long rv = Get_as_long_or(key, LongUtl.MinValue); if (rv == LongUtl.MinValue) throw ErrUtl.NewArgs("key missing", "key", key);
		return rv;
	}
	public long Get_as_long_or(String key, long or) {return Get_as_long_or(BryUtl.NewU8(key), or);}
	public long Get_as_long_or(byte[] key, long or) {
		byte[] rv = Get_as_bry_or(key, null);
		return rv == null ? or : BryUtl.ToLongOr(rv, or);
	}
	public boolean Get_as_bool_or(String key, boolean or) {return Get_as_bool_or(BryUtl.NewU8(key), or);}
	public boolean Get_as_bool_or(byte[] key, boolean or) {
		byte[] rv = Get_as_bry_or(key, null);
		return rv == null ? or : BryLni.Eq(rv, BoolUtl.TrueBry);
	}
	public GfoDate Get_as_date_by_utc(String key) {
		byte[] rv = Get_as_bry_or(BryUtl.NewU8(key), null); if (rv == null) throw ErrUtl.NewArgs("key missing", "key", key);
		return GfoDateUtl.ParseGplx(StringUtl.NewU8(rv));
	}

	// to convert
	public boolean Has(byte[] key) {return Get_bry(key, null) != null;}
	public Json_kv Get_at_as_kv(int i) {
		Json_itm rv_itm = Get_at(i);
		Json_kv rv = Json_kv.Cast(rv_itm);
		if (rv == null) {
			byte[] snip = jdoc == null ? BryUtl.NewA7("no source") : BryLni.Mid(jdoc.Src(), src_bgn, src_end);
			throw ErrUtl.NewArgs("sub is not kv", "i", i, "src", snip);
		}
		return rv;
	}

	public Json_kv Get_kv(byte[] key) {return Json_kv.Cast(Get_itm(key));}
	public Json_nde Get(String key) {return Get(BryUtl.NewU8(key));}
	public Json_nde Get(byte[] key) {
		Json_kv kv = Json_kv.Cast(this.Get_itm(key)); if (kv == null) throw ErrUtl.NewArgs("kv not found", "key", key);
		Json_nde rv = Json_nde.Cast(kv.Val()); if (rv == null) throw ErrUtl.NewArgs("nde not found", "key", key);
		return rv;
	}
	public Json_itm Get_itm(byte[] key) {
		for (int i = 0; i < subs_len; i++) {
			Json_itm itm = subs[i];
			if (itm != null && itm.Tid() == Json_itm_.Tid__kv) {
				Json_kv itm_as_kv = (Json_kv)itm;
				if (BryLni.Eq(key, itm_as_kv.Key().Data_bry()))
					return itm;
			}
		}
		return null;
	}
	public Json_ary Get_ary(String key) {return Get_ary(BryUtl.NewU8(key));}
	public Json_ary Get_ary(byte[] key) {return Json_ary.cast(Get_kv(key).Val_as_ary());}
	public String Get_str(String key) {return StringUtl.NewU8(Get_bry(BryUtl.NewU8(key)));}
	public byte[] Get_bry(byte[] key) {
		byte[] rv = Get_bry(key, null); if (rv == null) throw ErrUtl.NewArgs("key missing", "key", key);
		return rv;
	}
	public byte[] Get_bry_or_null(String key) {return Get_bry(BryUtl.NewU8(key), null);}
	public byte[] Get_bry_or_null(byte[] key) {return Get_bry(key, null);}
	public byte[] Get_bry(byte[] key, byte[] or) {
		Json_itm kv_obj = Get_itm(key);
		if (kv_obj == null) return or;	// key not found;
		if (kv_obj.Tid() != Json_itm_.Tid__kv) return or; // key is not a key_val
		Json_kv kv = (Json_kv)kv_obj;
		Json_itm val = kv.Val();
		return (val == null) ? or : val.Data_bry();
	}
	public void AddKvBool(String key, boolean val)  {AddKv(key, Json_itm_bool.Get(val));}
	public void AddKvInt(String key, int val)       {AddKv(key, Json_itm_int.NewByVal(val));}
	public void AddKvDouble(String key, double val) {AddKv(key, Json_itm_decimal.NewByVal(GfoDecimalUtl.NewByDouble(val)));}
	public void AddKvStr(String key, byte[] val)    {AddKv(key, Json_itm_str.NewByVal(StringUtl.NewU8(val)));}
	public void AddKvStr(String key, String val)    {AddKv(key, Json_itm_str.NewByVal(val));}
	public void AddKvNde(String key, Json_nde val)  {AddKv(key, val);}
	public void AddKvAry(String key, Json_ary val)  {AddKv(key, val);}
	private void AddKv(String key, Json_itm val) {
		Json_kv rv = new Json_kv(Json_itm_str.NewByVal(key), val);
		Add(rv);
	}
	public Json_nde Add_many(Json_itm... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			Add(ary[i]);
		return this;
	}
	public void Add(Json_itm itm) {
		int new_len = subs_len + 1;
		if (new_len > subs_max) {	// ary too small >>> expand
			subs_max = new_len * 2;
			Json_itm[] new_subs = new Json_itm[subs_max];
			ArrayUtl.CopyTo(subs, 0, new_subs, 0, subs_len);
			subs = new_subs;
		}
		subs[subs_len] = (Json_itm)itm;
		subs_len = new_len;
		subs_hash = null;
	}
	@Override public void Print_as_json(BryWtr bfr, int depth) {
		if (bfr.Len() != 0) bfr.AddByteNl();
		Json_grp_.Print_indent(bfr, depth);
		bfr.AddByte(AsciiByte.CurlyBgn).AddByte(AsciiByte.Space);
		for (int i = 0; i < subs_len; i++) {
			if (i != 0) {
				Json_grp_.Print_nl(bfr); Json_grp_.Print_indent(bfr, depth);
				bfr.AddByte(AsciiByte.Comma).AddByte(AsciiByte.Space);
			}
			subs[i].Print_as_json(bfr, depth + 1);
		}
		Json_grp_.Print_nl(bfr); Json_grp_.Print_indent(bfr, depth);
		bfr.AddByte(AsciiByte.CurlyEnd).AddByteNl();
	}
	private Hash_adp_bry subs_hash_init() {
		Hash_adp_bry rv = Hash_adp_bry.cs();
		for (int i = 0; i < subs_len; ++i) {
			Json_itm itm = subs[i];
			if (itm.Tid() == Json_itm_.Tid__kv) {
				Json_kv itm_as_kv = (Json_kv)itm;
				rv.Add(itm_as_kv.Key().Data_bry(), itm_as_kv.Val());
			}
		}
		return rv;
	}

	public static Json_nde NewByDoc(Json_doc doc, int src_bgn) {return new Json_nde(doc, src_bgn);}
	public static Json_nde NewByVal() {return new Json_nde(null, -1);}
	public static Json_nde Cast(Json_itm v) {return v == null || v.Tid() != Json_itm_.Tid__nde ? null : (Json_nde)v;}
}
