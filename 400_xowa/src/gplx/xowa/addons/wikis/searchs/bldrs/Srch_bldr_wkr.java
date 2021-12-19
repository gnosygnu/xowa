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
package gplx.xowa.addons.wikis.searchs.bldrs;
import gplx.frameworks.invks.GfoMsg;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.bldrs.Xob_bldr;
import gplx.xowa.bldrs.Xob_cmd_keys;
import gplx.xowa.bldrs.wkrs.Xob_page_wkr;
public class Srch_bldr_wkr implements Xob_page_wkr {
	private final Xowe_wiki wiki;
	private final Srch_temp_tbl_wkr temp_tbl_wkr = new Srch_temp_tbl_wkr();
	public Srch_bldr_wkr(Xob_bldr bldr, Xowe_wiki wiki) {this.wiki = wiki;}
	public String Page_wkr__key() {return Xob_cmd_keys.Key_text_search_wkr;}
	public void Page_wkr__bgn() {
		temp_tbl_wkr.Init(BoolUtl.N, wiki);
	}
	public void Page_wkr__run(gplx.xowa.wikis.data.tbls.Xowd_page_itm page) {
		try {temp_tbl_wkr.Exec_by_wkr(page.Id(), page.Ttl_page_db());}
		catch (Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "search:error: page=~{0} err=~{1}", page.Ttl_page_db(), ErrUtl.ToStrFull(e));}
	}
	public void Page_wkr__run_cleanup() {}
	public void Page_wkr__end() {
		temp_tbl_wkr.Term();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
}
