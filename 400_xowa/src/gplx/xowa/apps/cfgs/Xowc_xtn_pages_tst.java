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
package gplx.xowa.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.nss.*;
public class Xowc_xtn_pages_tst {
	@Before public void init() {fxt.Clear();} private Xowc_xtn_pages_fxt fxt = new Xowc_xtn_pages_fxt();
	@Test  public void Init() {
		fxt.Init_ns(200, "Foreign_page").Init_ns(201, "Foreign_page_talk").Init_ns(202, "Foreign_index").Init_ns(203, "Foreign_index_talk");	// ns set by <siteinfo>
		fxt.Init_names("Foreign_page", "Foreign_page_talk", "Foreign_index", "Foreign_index_talk");	// ns set by .gfs files in /user/wiki/#cfg
		fxt.Exec_init();
		fxt.Test_ns_ids(200, 201, 202, 203);
		fxt.Test_ns_canonical("Page", "Page_talk", "Index", "Index_talk");
	}
	@Test  public void Spaces() {	// PURPOSE: ensure underlines, not space; EX:"Mục_lục" not "Mục lục"; PAGE:vi.s:Việt_Nam_sử_lược/Quyển_II DATE:2015-10-27
		fxt.Init_ns(200, "Foreign_page").Init_ns(201, "Foreign_page_talk").Init_ns(202, "Foreign_index").Init_ns(203, "Foreign_index_talk");	// ns set by <siteinfo>
		fxt.Init_names("Foreign page", "Foreign page talk", "Foreign index", "Foreign index talk");	// ns set by .gfs files in /user/wiki/#cfg
		fxt.Exec_init();
		fxt.Test_ns_ids(200, 201, 202, 203);
		fxt.Test_ns_canonical("Page", "Page_talk", "Index", "Index_talk");
	}
}
class Xowc_xtn_pages_fxt {
	private Xow_ns_mgr ns_mgr;
	private Xowc_xtn_pages cfg_pages;
	public void Clear() {
		ns_mgr = Xow_ns_mgr_.default_(gplx.xowa.langs.cases.Xol_case_mgr_.A7());
		cfg_pages = new Xowc_xtn_pages();
	}
	public Xowc_xtn_pages_fxt Init_ns(int id, String name) {
		ns_mgr.Add_new(id, name);
		return this;
	} 
	public void Init_names(String page_name, String page_talk_name, String index_name, String index_talk_name) {
		cfg_pages.Ns_names_(Bry_.new_a7(page_name), Bry_.new_a7(page_talk_name), Bry_.new_a7(index_name), Bry_.new_a7(index_talk_name));
	}
	public void Exec_init() {
		ns_mgr.Init_w_defaults();	// init ns_msg
		cfg_pages.Init(ns_mgr);		// init cfg
	}
	public void Test_ns_ids(int page_id, int page_talk_id, int index_id, int index_talk_id) {
		Tfds.Eq(page_id			, cfg_pages.Ns_page_id());
		Tfds.Eq(page_talk_id	, cfg_pages.Ns_page_talk_id());
		Tfds.Eq(index_id		, cfg_pages.Ns_index_id());
		Tfds.Eq(index_talk_id	, cfg_pages.Ns_index_talk_id());
	}
	public void Test_ns_canonical(String page_name, String page_talk_name, String index_name, String index_talk_name) {
		Test_ns_canonical_itm(page_name			, cfg_pages.Ns_page_id());
		Test_ns_canonical_itm(page_talk_name	, cfg_pages.Ns_page_talk_id());
		Test_ns_canonical_itm(index_name		, cfg_pages.Ns_index_id());
		Test_ns_canonical_itm(index_talk_name 	, cfg_pages.Ns_index_talk_id());
	}
	private void Test_ns_canonical_itm(String name, int expd_ns_id) {
		Xow_ns ns = ns_mgr.Names_get_or_null(Bry_.new_a7(name));
		int actl_ns_id = ns == null ? Int_.Min_value : ns.Id();
		Tfds.Eq(expd_ns_id, actl_ns_id);
	}
}
