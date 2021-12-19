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
package gplx.xowa.apps.wms.apis;
import gplx.types.custom.brys.wtrs.BryWtr;
public class Xowm_api_bldr {
	public static void Bld_bgn(BryWtr bfr, byte[] wiki) {
		bfr.AddStrA7("https://");
		bfr.Add(wiki);
		bfr.AddStrA7("/w/api.php?");
	}
}
