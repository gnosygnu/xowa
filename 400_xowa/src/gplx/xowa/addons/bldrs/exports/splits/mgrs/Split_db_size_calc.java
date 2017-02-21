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
package gplx.xowa.addons.bldrs.exports.splits.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
public class Split_db_size_calc {
	private final    long size_max;
	private long size_cur;		
	public Split_db_size_calc(long size_max, int idx) {
		this.size_max = size_max;
		this.idx = idx;
	}
	public int Idx() {return idx;} private int idx;
	public int Size_cur_add_(int v) {
		long size_new = size_cur + v;
		if (size_new > size_max) {
			++idx;
			size_cur = 0;
		}
		else
			size_cur = size_new;
		return idx;
	}
}
