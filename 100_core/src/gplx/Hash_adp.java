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
public interface Hash_adp extends gplx.core.lists.EnumerAble {
	int        Count();
	boolean    Has(Object key);
	Object     Get_by(Object key);
	Object     Get_by_or_fail(Object key);
	void       Add(Object key, Object val);
	Hash_adp   Add_and_more(Object key, Object val);
	void       Add_as_key_and_val(Object val);
	boolean    Add_if_dupe_use_1st(Object key, Object val);
	void       Add_if_dupe_use_nth(Object key, Object val);
	void       Del(Object key);
	void       Clear();
}
