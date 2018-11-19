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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pxd_date_bldr {
	private int[] seg_ary;
	public Pxd_date_bldr(int year, int month, int day, int hour, int minute, int second, int frac) {
		this.seg_ary = new int[] {year, month, day, hour, minute, second, frac};
	}

	// most workers will just set individual segments
	public void Seg_set(int idx, int val) {
		seg_ary[idx] = val;
	}

	// relative workers will convert segs to date, add a relative unit, and then convert back to date
	// note that the date conversion is necessary b/c Date class has logic to do things like "2017-12-31 +1 day" and rollover to next month / next year
	public DateAdp To_date() {
		return DateAdp_.seg_(seg_ary);
	}
	public void By_date(DateAdp v) {
		this.seg_ary = v.XtoSegAry();
	}
}
