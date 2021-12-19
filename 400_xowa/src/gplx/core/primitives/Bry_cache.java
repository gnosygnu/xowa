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
package gplx.core.primitives;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.custom.brys.wtrs.BryRef;
public class Bry_cache {
	private final Hash_adp hash = Hash_adp_.New(); private final BryRef hash_ref = BryRef.NewEmpty();
	public byte[] Get_or_new(String v) {return Get_or_new(BryUtl.NewU8(v));}
	public byte[] Get_or_new(byte[] v) {
		if (v.length == 0) return BryUtl.Empty;
		Object rv = hash.GetByOrNull(hash_ref.ValSet(v));
		if (rv == null) {
			BryRef bry = BryRef.New(v);
			hash.AddAsKeyAndVal(bry);
			return v;
		}
		else
			return ((BryRef)rv).Val();
	}
	public static final Bry_cache Instance = new Bry_cache(); Bry_cache() {}
}
