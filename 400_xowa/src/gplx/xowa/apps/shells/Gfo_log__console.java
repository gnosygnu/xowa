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
package gplx.xowa.apps.shells; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.logs.*;
public class Gfo_log__console extends Gfo_log__base {
	@Override public void Exec(byte type, long time, long elapsed, String msg, Object[] args) {
		switch (type) {
			case Gfo_log_itm.Type__prog: Gfo_usr_dlg_.Instance.Prog_many("", "", gplx.core.errs.Err_msg.To_str(msg, args)); break;
			case Gfo_log_itm.Type__warn: Gfo_usr_dlg_.Instance.Warn_many("", "", gplx.core.errs.Err_msg.To_str(msg, args)); break;
			case Gfo_log_itm.Type__note: Gfo_usr_dlg_.Instance.Note_many("", "", gplx.core.errs.Err_msg.To_str(msg, args)); break;
			case Gfo_log_itm.Type__info: Gfo_usr_dlg_.Instance.Log_many ("", "", gplx.core.errs.Err_msg.To_str(msg, args)); break;
			default: throw Err_.new_unhandled_default(type);
		}
	}
	@Override public List_adp Itms() {return itms;} @Override public Gfo_log Itms_(List_adp v) {itms = v; return this;} private List_adp itms;
	@Override public void Flush() {itms.Clear();}
}
