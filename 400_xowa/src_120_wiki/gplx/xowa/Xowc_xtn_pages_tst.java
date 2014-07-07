/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa; import gplx.*;
import org.junit.*; import gplx.xowa.wikis.*;
public class Xowc_xtn_pages_tst {
	@Before public void init() {fxt.Clear();} private Xowc_xtn_pages_fxt fxt = new Xowc_xtn_pages_fxt();
	@Test  public void Init() {
		fxt.Init_ns(200, "Foreign_page").Init_ns(201, "Foreign_page_talk").Init_ns(202, "Foreign_index").Init_ns(203, "Foreign_index_talk");	// ns set by <siteinfo>
		fxt.Init_names("Foreign_page", "Foreign_page_talk", "Foreign_index", "Foreign_index_talk");	// ns set by .gfs files in /user/wiki/#cfg
		fxt.Exec_init();
		fxt.Test_ns_ids(200, 201, 202, 203);
		fxt.Test_ns_canonical("Page", "Page_talk", "Index", "Index_talk");
	}
}
class Xowc_xtn_pages_fxt {
	private Xow_ns_mgr ns_mgr;
	private Xowc_xtn_pages cfg_pages;
	public void Clear() {
		ns_mgr = Xow_ns_mgr_.default_(gplx.xowa.langs.cases.Xol_case_mgr_.Ascii());
		cfg_pages = new Xowc_xtn_pages();
	}
	public Xowc_xtn_pages_fxt Init_ns(int id, String name) {
		ns_mgr.Add_new(id, name);
		return this;
	} 
	public void Init_names(String page_name, String page_talk_name, String index_name, String index_talk_name) {
		cfg_pages.Ns_names_(Bry_.new_ascii_(page_name), Bry_.new_ascii_(page_talk_name), Bry_.new_ascii_(index_name), Bry_.new_ascii_(index_talk_name));
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
		Xow_ns ns = ns_mgr.Names_get_or_null(Bry_.new_ascii_(name));
		int actl_ns_id = ns == null ? Int_.MinValue : ns.Id();
		Tfds.Eq(expd_ns_id, actl_ns_id);
	}
}
