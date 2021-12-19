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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.fmtrs.*;
public class Xoh_arg_img_core__basic implements BryBfrArg, Xoh_arg_img_core {
	private byte[] src; private int w, h;
	public Xoh_arg_img_core Init(int uid, byte[] src, int w, int h) {this.src = src; this.w = w; this.h = h; return this;}
	public void AddToBfr(BryWtr bfr) {
		fmtr_img_atrs.BldToBfrMany(bfr, src, w, h);
	}
	private BryFmtr fmtr_img_atrs = BryFmtr.New(" src=\"~{img_src}\" width=\"~{img_w}\" height=\"~{img_h}\"", "img_src", "img_w", "img_h");
}
