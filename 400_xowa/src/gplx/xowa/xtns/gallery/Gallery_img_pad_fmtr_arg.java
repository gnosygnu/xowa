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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Gallery_img_pad_fmtr_arg implements gplx.core.brys.Bfr_arg {
	private final    int vpad;
	public Gallery_img_pad_fmtr_arg(int vpad) {this.vpad = vpad;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add(Style_bgn);
		bfr.Add_int_variable(vpad);
		bfr.Add(Style_end);
	}
	private static final    byte[] Style_bgn = Bry_.new_a7("style=\"margin:"), Style_end = Bry_.new_a7("px auto;\"");
}
