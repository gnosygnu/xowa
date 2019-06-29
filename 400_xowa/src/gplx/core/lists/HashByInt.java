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
package gplx.core.lists; import gplx.*; import gplx.core.*;
import gplx.core.primitives.*;
public class HashByInt {
	private final    Ordered_hash hash = Ordered_hash_.New();
	private final    Int_obj_ref tmp_key = Int_obj_ref.New_neg1();
	public void Clear() {
		hash.Clear();
	}
	public int Len() {
		return hash.Len();
	}
	public Object Get_at_or_null(int idx) {
		HashByIntItem item = (HashByIntItem)hash.Get_at(idx);
		return item.val;
	}
	public Object Get_by_or_fail(int key) {
		synchronized (tmp_key) {
			HashByIntItem item = (HashByIntItem)hash.Get_by_or_fail(tmp_key.Val_(key));
			return item.val;
		}
	}
	public Object Get_by_or_null(int key) {
		synchronized (tmp_key) {
			HashByIntItem item = (HashByIntItem)hash.Get_by(tmp_key.Val_(key));
			return item == null ? null : item.val;
		}
	}
	public HashByInt Add_as_bry(int key, String val) {return Add(key, Bry_.new_u8(val));}
	public HashByInt Add(int key, Object val) {
		HashByIntItem item = new HashByIntItem(key, val);
		hash.Add(Int_obj_ref.New(key), item);
		return this;
	}
	public HashByInt Clone() {
		HashByInt rv = new HashByInt();

		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			HashByIntItem item = (HashByIntItem)hash.Get_at(i);
			rv.Add(item.key, item.val);
		}
		return rv;
	}
}
class HashByIntItem {
	public final    int key;
	public final    Object val;
	public HashByIntItem(int key, Object val) {
		this.key = key;
		this.val = val;
	}
}
