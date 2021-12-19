/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.lists.hashs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.wrappers.IntRef;
public class Hash_adp__int {
	private final Hash_adp hash = Hash_adp_.New();
	private final IntRef tmp_key = IntRef.NewNeg1();
	public void Clear()                                {hash.Clear();}
	public int Len()                                {return hash.Len();}
	public Object Get_by_or_fail(int key)            {synchronized (tmp_key) {return hash.GetByOrFail(tmp_key.ValSet(key));}}    // LOCK:used by Xomp_ns_ord_mgr in xomp; DATE:2016-10-18
	public Object Get_by_or_null(int key)            {synchronized (tmp_key) {return hash.GetByOrNull(tmp_key.ValSet(key));}}    // LOCK:used by Xomp_ns_ord_mgr in xomp; DATE:2016-10-18
	public void Add(int key, Object obj)            {hash.Add(IntRef.New(key), obj);}
	public void Add(IntRef key, Object obj)    {hash.Add(key, obj);}
	public void Add_if_dupe_use_1st(int key, Object obj)            {hash.AddIfDupeUse1st(IntRef.New(key), obj);}
	public void Add_if_dupe_use_nth(IntRef key, Object obj)    {hash.AddIfDupeUseNth(key, obj);}
	public Hash_adp__int Add_as_bry(int key, String val) {hash.Add(IntRef.New(key), BryUtl.NewU8(val)); return this;}
}
