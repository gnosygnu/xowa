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
package gplx.xowa.wikis.pages.tags; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public interface Xopg_tag_wtr_cbk {
	void Write_tag(Bry_bfr bfr, Xopg_tag_itm itm);
}
class Xopg_tag_wtr_cbk__basic implements Xopg_tag_wtr_cbk {
	public void Write_tag(Bry_bfr bfr, Xopg_tag_itm itm) {itm.To_html(bfr);}
}
