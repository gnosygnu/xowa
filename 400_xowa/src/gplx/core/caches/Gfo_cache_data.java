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
package gplx.core.caches; import gplx.*; import gplx.core.*;
import gplx.core.envs.*;
class Gfo_cache_data implements gplx.CompareAble, Rls_able {
	private int count = 0;
	public Gfo_cache_data(byte[] key, Rls_able val, int size) {
		this.key = key; this.val = val;
		this.size = size;
		this.count = 1;
	}
	public byte[]		Key()			{return key;} private final    byte[] key;
	public Rls_able		Val()			{return val;} private Rls_able val;
	public int			Size()			{return size;} private int size;

	public Object		Val_and_update() {
		++count;
		return val;
	}
	public void Val_(Rls_able val, int size) {
		this.val = val;
		this.size = size;
	}
	public int compareTo(Object obj) {
		Gfo_cache_data comp = (Gfo_cache_data)obj;
		return -Long_.Compare(count, comp.count);	// "-" to sort most-recent first
	}
	public void Rls() {
		val.Rls();
	}
}
