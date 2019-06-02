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
package gplx.xowa.htmls.hxtns.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.hxtns.*;
import gplx.dbs.*;
import gplx.xowa.htmls.hxtns.pages.*;
public class Hxtn_wkr_mgr {
	private Hxtn_wkr_tbl  wkr_tbl;
	public void Init_by_xomp_merge(Db_conn conn) {
		wkr_tbl = new Hxtn_wkr_tbl(conn);
		wkr_tbl.Create_tbl();
		Reg_wkr(new gplx.xowa.xtns.template_styles.Hxtn_page_wkr__template_styles(null)); // TODO:do formal registration of extensions; WHEN: rework tkn_mkr
	}
	private void Reg_wkr(Hxtn_page_wkr wkr) {
		wkr_tbl.Insert(wkr.Id(), wkr.Key());
	}
}
