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
package gplx.core.threads; import gplx.*; import gplx.core.*;
class Gfo_async_cmd_mkr {
//		private Gfo_async_cmd_itm[] free = Gfo_async_cmd_itm.Ary_empty, used = Gfo_async_cmd_itm.Ary_empty;
//		private int free_bgn = 0, free_end = 0, ary_len = 0;
//		public void Resize(int v) {
//			free = (Gfo_async_cmd_itm[])Array_.Resize(free, v);
//			used = (Gfo_async_cmd_itm[])Array_.Resize(used, v);
//			ary_len = v;
//		}
	public Gfo_async_cmd_itm Get(Gfo_invk invk, String invk_key, Object... args) {
		Gfo_async_cmd_itm rv = new Gfo_async_cmd_itm();
		rv.Init(invk, invk_key, args);
		return rv;
	}
	public void Rls(Gfo_async_cmd_itm cmd) {
	}
}
