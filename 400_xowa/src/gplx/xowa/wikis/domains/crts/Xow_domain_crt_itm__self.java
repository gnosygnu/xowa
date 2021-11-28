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
package gplx.xowa.wikis.domains.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
class Xow_domain_crt_itm__self implements Xow_domain_crt_itm {
	public boolean Matches(Xow_domain_itm cur, Xow_domain_itm comp) {return Bry_.Eq(cur.Domain_bry(), comp.Domain_bry());}
        public static final Xow_domain_crt_itm__self Instance = new Xow_domain_crt_itm__self(); Xow_domain_crt_itm__self() {}
}
