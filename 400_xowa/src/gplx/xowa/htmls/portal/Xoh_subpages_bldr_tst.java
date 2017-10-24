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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xoh_subpages_bldr_tst {
	@Before public void init() {fxt.Init();} private Xoh_subpages_bldr_fxt fxt = new Xoh_subpages_bldr_fxt();
	@Test  public void Basic() {
		fxt.Test_bld("Help:A/B/C", String_.Concat_lines_nl_skip_last
		( "<span class=\"subpages\">"
		, "  &lt; <a href=\"/wiki/Help:A\" title=\"Help:A\">Help:A</a>"
		, "  &lrm; | <a href=\"/wiki/Help:A/B\" title=\"Help:A/B\">B</a>"
		, "</span>"
		));
	}
	@Test  public void Skip_page() {
		fxt.Wiki().Ns_mgr().Add_new(104, "Page");
		fxt.Wiki().Ns_mgr().Ns_page_id_(104);
		fxt.Test_bld("Page:A/B/C", "");
	}
}
class Xoh_subpages_bldr_fxt {
	private Xoae_app app;
	private Xoh_subpages_bldr subpages_bldr = new Xoh_subpages_bldr();
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public void Init() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__help).Subpages_enabled_(true);
	}
	public void Test_bld(String ttl_str, String expd) {
		byte[] actl = subpages_bldr.Bld(wiki.Ns_mgr(), Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl_str)));
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
}
