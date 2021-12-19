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
package gplx.core.logs;
import gplx.libs.logs.Gfo_log;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
public class Gfo_log__mem extends Gfo_log__base {        
	@Override public List_adp Itms() {return itms;} @Override public Gfo_log Itms_(List_adp v) {this.itms = v; return this;} private List_adp itms = List_adp_.New();
	@Override public void Exec(byte type, long time, long elapsed, String msg, Object[] args) {
		Gfo_log_itm itm = new Gfo_log_itm(type, time, elapsed, msg, args);
		itms.Add(itm);
	}
	@Override public void Flush() {}
}
