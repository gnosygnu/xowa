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
package gplx.xowa.htmls.hxtns.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.hxtns.*;
class Hxtn_page_itm {
	public Hxtn_page_itm(int page_id, int wkr_id, int data_id) {
		this.page_id = page_id;
		this.wkr_id = wkr_id;
		this.data_id = data_id;
	}
	public int Page_id() {return page_id;} private final    int page_id;
	public int Wkr_id() {return wkr_id;} private final    int wkr_id;
	public int Data_id() {return data_id;} private final    int data_id;
}
