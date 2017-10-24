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
package gplx.xowa.addons.bldrs.exports.splits.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
public class Split_page_itm {
	private final    Split_page_list[] lists_ary;
	public Split_page_itm(boolean fsdb, int page_id) {
		this.page_id = page_id;
		this.lists_ary = new Split_page_list[Split_page_list_type_.Tid_max];
	}
	public int Page_id() {return page_id;} private final    int page_id;
	public Split_page_list Get_by_or_null(byte type) {return lists_ary[type];}
	public Split_page_list Get_by_or_make(byte type) {
		Split_page_list rv = lists_ary[type];
		if (rv == null) {
			rv = new Split_page_list(type);
			lists_ary[type] = rv;
		}
		return rv;
	}
}
