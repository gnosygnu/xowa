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
package gplx.xowa.files;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.wrappers.ByteRef;
public class Xofv_repo_mgr {
	private final Hash_adp_bry key_regy = Hash_adp_bry.cs();
	private final Hash_adp tid_regy = Hash_adp_.New(); private final ByteRef tid_key = ByteRef.NewZero();
	public Xofv_repo_mgr Add(Xofv_repo_itm itm) {
		key_regy.Add(itm.Key(), itm);
		tid_regy.Add(ByteRef.New(itm.Tid()), itm);
		return this;
	}
	public Xofv_repo_itm Get_by_key(byte[] key) {
		return (Xofv_repo_itm)key_regy.GetByOrNull(key);
	}
	public Xofv_repo_itm Get_by_tid(byte tid) {
		return (Xofv_repo_itm)tid_regy.GetByOrNull(tid_key.ValSet(tid));
	}
}