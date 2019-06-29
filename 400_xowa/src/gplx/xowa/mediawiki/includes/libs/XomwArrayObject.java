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
package gplx.xowa.mediawiki.includes.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
// bare-bones implementation of PHP ArrayObject
// REF:http://php.net/manual/en/class.arrayobject.php
public abstract class XomwArrayObject {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public boolean offsetExists(Object key) {
		return hash.Has(key);
	}
	public Object offsetGet(Object key) {
		return hash.Get_by(key);
	}
	public void offsetUnset(Object key) {
		hash.Del(key);
	}
	@gplx.Virtual public void offsetSet(int key, Object val) {
		hash.Add(key, val);
	}

	public int count() {return hash.Len();}
	public Object Get_at(int i) {return hash.Get_at(i);}
	public void Add_or_update(Object val) {
		hash.Add(hash.Count(), val);
	}
}
