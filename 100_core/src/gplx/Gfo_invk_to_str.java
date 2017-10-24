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
import gplx.langs.gfs.*;
public class Gfo_invk_to_str {
	public static GfoMsg ReadMsg(Gfo_invk invk, String k) {
		GfsCtx ctx = GfsCtx.wtr_();
		GfoMsg m = GfoMsg_.rdr_(k);
		invk.Invk(ctx, 0, k, m);
		String invkKey = GfsCore.Instance.FetchKey(invk);
		GfoMsg root = GfoMsg_.new_cast_(invkKey);
		root.Subs_add(m);
		return root;
	}
	public static GfoMsg WriteMsg(Gfo_invk invk, String k, Object... ary) {return WriteMsg(GfsCore.Instance.FetchKey(invk), invk, k, ary);}
	public static GfoMsg WriteMsg(String invkKey, Gfo_invk invk, String k, Object... ary) {
		GfsCtx ctx = GfsCtx.wtr_();
		GfoMsg m = GfoMsg_.wtr_();
		invk.Invk(ctx, 0, k, m);
		GfoMsg rv = GfoMsg_.new_cast_(k);
		for (int i = 0; i < m.Args_count(); i++) {
			Keyval kv = m.Args_getAt(i);
			rv.Add(kv.Key(), ary[i]);
		}
		GfoMsg root = GfoMsg_.new_cast_(invkKey);
		root.Subs_add(rv);
		return root;
	}
}
