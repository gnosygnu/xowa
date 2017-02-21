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
package gplx.xowa.addons.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
public class Xoctg_ctg_itm {
	public Xoctg_ctg_itm(byte[] ttl_wo_ns, int pages, int subcs, int files) {
		this.Ttl_wo_ns = ttl_wo_ns;
		this.Pages = pages;
		this.Subcs = subcs;
		this.Files = files;
		this.All = pages + subcs  + files;
	}
	public final    byte[] Ttl_wo_ns;
	public final    int Pages;
	public final    int Subcs;
	public final    int Files;
	public final    int All;
}
