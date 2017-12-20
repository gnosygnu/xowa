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
package gplx.core.lists.hashs; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
import gplx.core.primitives.*;
public class Hash_adp__int {
	private final    Hash_adp hash = Hash_adp_.New();
	private final    Int_obj_ref tmp_key = Int_obj_ref.New_neg1();
	public void Clear()								{hash.Clear();}
	public int Len()								{return hash.Count();}
	public Object Get_by_or_fail(int key)			{synchronized (tmp_key) {return hash.Get_by_or_fail(tmp_key.Val_(key));}}	// LOCK:used by Xomp_ns_ord_mgr in xomp; DATE:2016-10-18
	public Object Get_by_or_null(int key)			{synchronized (tmp_key) {return hash.Get_by(tmp_key.Val_(key));}}	// LOCK:used by Xomp_ns_ord_mgr in xomp; DATE:2016-10-18
	public void Add(int key, Object obj)			{hash.Add(Int_obj_ref.New(key), obj);}
	public void Add(Int_obj_ref key, Object obj)	{hash.Add(key, obj);}
	public void Add_if_dupe_use_1st(int key, Object obj)			{hash.Add_if_dupe_use_1st(Int_obj_ref.New(key), obj);}
	public void Add_if_dupe_use_nth(Int_obj_ref key, Object obj)	{hash.Add_if_dupe_use_nth(key, obj);}
	public Hash_adp__int Add_as_bry(int key, String val) {hash.Add(Int_obj_ref.New(key), Bry_.new_u8(val)); return this;}
}
