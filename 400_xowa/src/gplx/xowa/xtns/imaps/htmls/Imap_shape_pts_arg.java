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
package gplx.xowa.xtns.imaps.htmls;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.MathUtl;
import gplx.types.basics.wrappers.DoubleVal;
public class Imap_shape_pts_arg implements BryBfrArg {
	private final double scale; private DoubleVal[] pts;
	public Imap_shape_pts_arg(double scale) {
		this.scale = scale;
	}
	public void Pts_(DoubleVal[] v) {this.pts = v;}
	public void AddToBfr(BryWtr bfr) {
		int pts_len = pts.length;
		for (int i = 0; i < pts_len; ++i) {
			DoubleVal pt = pts[i];
			int pt_int = (int)pt.Val();
			pt_int = (int)MathUtl.Round(scale * pt_int, 0);
			if (i != 0) bfr.AddByteComma();
			bfr.AddIntVariable(pt_int);
		}
	}
}
