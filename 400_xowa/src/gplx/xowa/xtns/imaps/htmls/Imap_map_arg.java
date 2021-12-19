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
import gplx.xowa.xtns.imaps.itms.*;
public class Imap_map_arg implements BryBfrArg {
	private final int imap_id;
	private final Imap_shapes_arg shapes_arg;
	public Imap_map_arg(int imap_id, Imap_part_shape[] shapes, double scale) {
		this.imap_id = imap_id;
		this.shapes_arg = new Imap_shapes_arg(shapes, scale);
	}
	public void AddToBfr(BryWtr bfr) {
		Imap_html_fmtrs.Map.BldToBfrMany(bfr, imap_id, shapes_arg);
	}
}
