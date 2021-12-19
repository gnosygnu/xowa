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
package gplx.core.brys;
import gplx.types.custom.brys.wtrs.BryWtr;
public class Bry_tmp {
	public byte[] src;
	public int src_bgn;
	public int src_end;
	public boolean dirty;
	public Bry_tmp Init(byte[] src, int src_bgn, int src_end) {
		this.dirty = false;
		this.src = src;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		return this;
	}
	public void Set_by_bfr(BryWtr bfr) {
		dirty = true;
		src = bfr.ToBryAndClear();
		src_bgn = 0;
		src_end = src.length;
	}
	public void Add_to_bfr(BryWtr bfr) {
		bfr.AddMid(src, src_bgn, src_end);
	}
}
