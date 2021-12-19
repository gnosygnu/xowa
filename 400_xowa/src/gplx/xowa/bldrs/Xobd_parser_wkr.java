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
package gplx.xowa.bldrs;
import gplx.frameworks.invks.Gfo_invk;
import gplx.types.basics.lists.Ordered_hash;
import gplx.xowa.wikis.data.tbls.*;
public interface Xobd_parser_wkr extends Gfo_invk {
	Ordered_hash Wkr_hooks();
	void Wkr_bgn(Xob_bldr bldr);
	int Wkr_run(Xowd_page_itm page, byte[] src, int src_len, int bgn, int end);
	void Wkr_end();
}
