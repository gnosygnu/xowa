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
package gplx;
import gplx.core.primitives.*;
public class Hash_adp_ {
	public static Hash_adp New() {return new Hash_adp_obj();}
	public static final    Hash_adp Noop = new Hash_adp_noop();
}
class Hash_adp_obj extends gplx.core.lists.Hash_adp_base implements Hash_adp {}//_20110428
class Hash_adp_noop implements Hash_adp {
	public int Count() {return 0;}
	public boolean Has(Object key) {return false;}
	public Object Get_by(Object key) {return null;}
	public Object Get_by_or_fail(Object key)				{throw Err_.new_missing_key(Object_.Xto_str_strict_or_null_mark(key));}
	public void Add(Object key, Object val) {}
	public Hash_adp Add_and_more(Object key, Object val) {return this;}
	public void Add_as_key_and_val(Object val) {}
	public void Add_if_dupe_use_nth(Object key, Object val) {}
	public boolean Add_if_dupe_use_1st(Object key, Object val) {return false;}
	public void Del(Object key) {}
	public void Clear() {}
	public java.util.Iterator iterator() {return gplx.core.lists.Iterator_null.Instance;}
}
