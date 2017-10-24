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
package gplx.xowa.langs.durations; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_interval_itm {
	public Xol_interval_itm(Xol_duration_itm duration_itm, long val) {this.duration_itm = duration_itm; this.val = val;}
	public Xol_duration_itm Duration_itm() {return duration_itm;} private Xol_duration_itm duration_itm;
	public long Val() {return val;} private long val;
	public static Keyval[] Xto_kv_ary(Xol_interval_itm[] ary) {
		int len = ary.length;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Xol_interval_itm itm = ary[i];
			rv[i] = Keyval_.new_(itm.Duration_itm().Name_str(), (int)itm.Val());	// double for scribunto
		}
		return rv;
	}
}
