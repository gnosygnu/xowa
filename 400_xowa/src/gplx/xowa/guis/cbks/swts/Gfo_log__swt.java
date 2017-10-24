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
package gplx.xowa.guis.cbks.swts; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.cbks.*;
import gplx.core.logs.*; import gplx.core.gfobjs.*; import gplx.xowa.guis.cbks.*;
public class Gfo_log__swt extends Gfo_log__file {		private final    Xog_cbk_mgr cbk_mgr;
	public Gfo_log__swt(Xog_cbk_mgr cbk_mgr, Io_url url, Gfo_log_itm_wtr fmtr) {super(url, fmtr);this.cbk_mgr = cbk_mgr;}
	@Override public void Exec(byte type, long time, long elapsed, String msg, Object[] args) {
		if (type == Gfo_log_itm.Type__prog) return;
		super.Exec(type, time, elapsed, msg, args);
		Gfobj_nde nde = Gfobj_nde.New().Add_str("msg", msg);
		int args_len = args.length;
		for (int i = 0; i < args_len; i += 2) {
			String key = Object_.Xto_str_strict_or_null_mark(args[i]);
			Object val = i + 1 < args_len ? args[i + 1] : "<<NULL>>";
			nde.Add_str(key, Object_.Xto_str_strict_or_null_mark(val));
		}
		cbk_mgr.Send_json(Xog_cbk_trg.Any, "xo.log.add__recv", nde);
	}
}
