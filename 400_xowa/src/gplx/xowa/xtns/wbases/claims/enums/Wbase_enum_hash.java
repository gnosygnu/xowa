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
package gplx.xowa.xtns.wbases.claims.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
public class Wbase_enum_hash {
	private final    Wbase_enum_itm[] ary; private final    int ary_len;
	private final    Hash_adp_bry hash_by_bry = Hash_adp_bry.cs();
	public Wbase_enum_hash(String name, int ary_len) {
		this.name = name;
		this.ary = new Wbase_enum_itm[ary_len];
		this.ary_len = ary_len;
	}
	public String Name() {return name;} private final    String name;
	public int Len() {return ary_len;}
	public Wbase_enum_itm Add(byte tid, String key)	{return Add(new Wbase_enum_itm(tid, key));}
	public Wbase_enum_itm Add(Wbase_enum_itm rv) {
		hash_by_bry.Add(rv.Key_bry(), rv);
		ary[rv.Tid()] = rv;
		return rv;
	}
	public Wbase_enum_itm Get_itm_or(byte tid, Wbase_enum_itm or) {return tid < ary_len ? ary[tid] : or;}
	public String Get_str_or(byte tid, String or)	{return tid < ary_len ? ary[tid].Key_str() : or;}
	public String Get_str_or_fail(byte tid) {return ary[tid].Key_str();}
	public byte[] Get_bry_or_fail(byte tid) {return ary[tid].Key_bry();}
	public byte Get_tid_or_fail(byte[] key) {return ((Wbase_enum_itm)hash_by_bry.Get_by_or_fail(key)).Tid();}
	public byte Get_tid_or_max_and_log(byte[] qid, String key) {return Get_tid_or_max_and_log(qid, Bry_.new_u8(key));}
	public byte Get_tid_or_max_and_log(byte[] qid, byte[] key) {
		Object rv_obj = hash_by_bry.Get_by(key);
		if (rv_obj == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown enum key for wikibase; qid=~{0} enum=~{1} key=~{2}", qid, name, key);
			return Byte_.Max_value_127;
		}
		return ((Wbase_enum_itm)rv_obj).Tid();
	}
	public byte Get_tid_or(String key, byte or) {return Get_tid_or(Bry_.new_u8(key), or);}
	public byte Get_tid_or(byte[] key, byte or) {
		Object obj = hash_by_bry.Get_by_bry(key);
		return obj == null ? or : ((Wbase_enum_itm)obj).Tid();
	}
	public byte Get_tid_or(byte[] key, int bgn, int end, byte or) {
		Object obj = hash_by_bry.Get_by_mid(key, bgn, end);
		return obj == null ? or : ((Wbase_enum_itm)obj).Tid();
	}
}