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
package gplx.types.custom.brys.fmts.itms;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
public class BryFmtItm {
	public BryFmtItm(int tid, int src_bgn, int src_end) {
		this.Tid = tid;
		this.Src_bgn = src_bgn;
		this.Src_end = src_end;
	}
	public int        Tid;
	public int        Src_bgn;
	public int        Src_end;
	public int        Key_idx;
	public BryBfrArg Arg;

	public static final int Tid__txt = 0, Tid__key = 1, Tid__arg = 2;
}
