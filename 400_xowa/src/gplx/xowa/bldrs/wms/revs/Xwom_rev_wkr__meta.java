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
package gplx.xowa.bldrs.wms.revs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
interface Xowm_rev_wkr__meta {
	void Fetch_meta(String domain_bry, Ordered_hash hash, int bgn, int end);
}
interface Xowm_rev_wkr__text {
	void Fetch_text(Ordered_hash hash, int bgn, int end);
}
class Xowm_rev_wkr__meta__xo {
	public void Fetch_meta(Ordered_hash hash, int bgn, int end) {
	}
}
