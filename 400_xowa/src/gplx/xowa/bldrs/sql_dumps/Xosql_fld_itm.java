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
package gplx.xowa.bldrs.sql_dumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
class Xosql_fld_itm implements gplx.CompareAble {
	public Xosql_fld_itm(int uid, byte[] key, int idx) {
		this.uid = uid;
		this.key = key;
		this.idx = idx;
	}
	public int		Uid() {return uid;} private int uid;
	public byte[]	Key() {return key;} private final    byte[] key;
	public int		Idx() {return idx;} private int idx;
	public void Idx_(int v) {this.idx = v;}
	public void Uid_(int v) {this.uid = v;}

	public int compareTo(Object obj) {
		Xosql_fld_itm comp = (Xosql_fld_itm)obj;
		return Int_.Compare(idx, comp.idx);
	}
}
class Xosql_fld_hash {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	private int hash_len;
	public int Len() {return hash.Len();}
	public Xosql_fld_itm Get_by_key(byte[] k)	{return (Xosql_fld_itm)hash.Get_by(k);}
	public Xosql_fld_itm Get_by_idx_or_null(int i) {
		return i > -1 && i < hash_len ? (Xosql_fld_itm)hash.Get_at(i) : null;
	}
	public void Add(Xosql_fld_itm itm)		{hash.Add(itm.Key(), itm); hash_len = hash.Len();}
	public void Sort() {hash.Sort();}
	public static Xosql_fld_hash New(String[] keys) {	// NOTE: keys must be passed in uid order
		int len = keys.length;
		Xosql_fld_hash rv = new Xosql_fld_hash();
		for (int i = 0; i < len; ++i) {
			Xosql_fld_itm itm = new Xosql_fld_itm(i, Bry_.new_u8(keys[i]), -1);
			rv.Add(itm);
		}
		return rv;
	}
}
