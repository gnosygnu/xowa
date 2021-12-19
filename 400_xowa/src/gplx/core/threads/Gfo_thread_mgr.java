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
package gplx.core.threads;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
public class Gfo_thread_mgr {
	private final Ordered_hash hash = Ordered_hash_.New();
	public Gfo_thread_grp Get_by_or_new(String k) {
		Gfo_thread_grp rv = (Gfo_thread_grp)hash.GetByOrNull(k);
		if (rv == null) {
			rv = new Gfo_thread_grp(k);
			hash.Add(k, rv);
		}
		return rv;
	}
	public void Stop_all() {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Gfo_thread_grp grp = (Gfo_thread_grp)hash.GetAt(i);
			grp.Stop_all();
		}
		hash.Clear();
	}
}