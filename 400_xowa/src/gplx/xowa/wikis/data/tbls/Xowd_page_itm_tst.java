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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import org.junit.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.nss.*;
public class Xowd_page_itm_tst {
	@Before public void init() {fxt.Init();} private Xowd_page_itm_fxt fxt = new Xowd_page_itm_fxt();
	@Test  public void Ttl_() {
		fxt.Test_ttl_("User_talk:A", Xow_ns_.Tid__user_talk, "A");
		fxt.Test_ttl_("User talk:A", Xow_ns_.Tid__user_talk, "A");
	}
}
class Xowd_page_itm_fxt {
	public void Init() {
		if (ns_mgr == null) {
			ns_mgr = new Xow_ns_mgr(gplx.xowa.langs.cases.Xol_case_mgr_.A7());
			ns_mgr.Add_new(Xow_ns_.Tid__main, "");
			ns_mgr.Add_new(Xow_ns_.Tid__user_talk, "User talk");
			ns_mgr.Init_w_defaults();
			tmp_page = new Xowd_page_itm();
		}
	}	private Xow_ns_mgr ns_mgr; Xowd_page_itm tmp_page;
	public void Test_ttl_(String ttl, int expd_ns, String expd_ttl) {
		tmp_page.Ttl_(Bry_.new_a7(ttl), ns_mgr);
	    Tfds.Eq(expd_ns, tmp_page.Ns_id());
		Tfds.Eq(expd_ttl, String_.new_a7(tmp_page.Ttl_page_db()));
	}
}
