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
package gplx.xowa.xtns.gallery;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
class Gallery_box_w_fmtr_arg implements BryBfrArg {
	private final int width;
	public Gallery_box_w_fmtr_arg(int width) {this.width = width;}
	public void AddToBfr(BryWtr bfr) {
		bfr.Add(Style_bgn);
		bfr.AddIntVariable(width);
		bfr.Add(Style_end);
	}
	private static final byte[] Style_bgn = BryUtl.NewA7("style=\"width:"), Style_end = BryUtl.NewA7("px;\"");
}
