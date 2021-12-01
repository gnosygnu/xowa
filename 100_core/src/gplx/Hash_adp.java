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
package gplx;
public interface Hash_adp extends gplx.core.lists.EnumerAble {
	int        Len();
	boolean    Has(Object key);
	Object     GetByOrNull(Object key);
	Object     GetByOrFail(Object key);
	void       Add(Object key, Object val);
	Hash_adp   AddAndMore(Object key, Object val);
	Hash_adp   AddManyAsKeyAndVal(Object... ary);
	void       AddAsKeyAndVal(Object val);
	boolean    AddIfDupeUse1st(Object key, Object val);
	void       AddIfDupeUseNth(Object key, Object val);
	void       Del(Object key);
	void       Clear();
}
