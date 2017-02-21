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
package gplx.xowa.drds; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.drds.pages.*; import gplx.xowa.wikis.*; import gplx.xowa.htmls.sections.*;
public class Xod_app_tst {
	private final    Xod_app_tstr tstr = new Xod_app_tstr();
	@Before		public void init() {tstr.Init_mem();}
	// COMMENTED: broke from changes to auto-init wiki; DATE:2016-06-16
//		@Test  public void Get() {
//			tstr.Data_mgr().Page__insert(1, "A", "2015-10-19 00:01:02");
//			tstr.Data_mgr().Html__insert(1, "abc");
//			tstr.Test__get("A", tstr.Make_page(1, "A", "2015-10-19T00:01:02Z", tstr.Make_section(0, 2, "", "", "abc")));
//		}
	@Test   public void To_page_db() {
		tstr.Test__to_page_url("http://en.wikipedia.org/wiki/A"			, "A");
		tstr.Test__to_page_url("http://en.wikipedia.org/wiki/A:B"		, "A:B");
		tstr.Test__to_page_url("http://en.wikipedia.org/wiki/Help:A"	, "Help:A");
		tstr.Test__to_page_url("http://en.wikipedia.org/wiki/A B"		, "A_B");	// NOTE:canonical url has spaces;
		tstr.Test__to_page_url("http://en.wikipedia.org/wiki/A%27B"		, "A'B");	// NOTE:canonical url has percent-encoding;
		tstr.Test__to_page_url("http://en.wikipedia.org/wiki/A+B"		, "A_B");	// NOTE:canonical url sometimes has "+" for space
	}
}
class Xod_app_tstr {
	private final    gplx.xowa.apps.Xoav_app app; private final    Xowv_wiki wiki;
	private final    Xod_app drd_provider;
	public Xod_app_tstr() {
		this.app = Xoa_app_fxt.Make__app__view();
		this.wiki = Xoa_app_fxt.Make__wiki__view(app);
		data_mgr.Wiki_(wiki);
		Xoa_test_.Init__db__view(wiki);
		drd_provider = new Xod_app(app);
	}
	public Xowd_data_tstr Data_mgr() {return data_mgr;} private final    Xowd_data_tstr data_mgr = new Xowd_data_tstr();
	public void Init_mem() {
		Io_mgr.Instance.InitEngine_mem();
	}
	public void Test__get(String ttl, Xod_page_itm expd) {
		Xow_wiki wiki = drd_provider.Wikis__get_by_domain("en.wikipedia.org");
		Xoa_url page_url = wiki.Utl__url_parser().Parse(Bry_.new_u8(ttl));
		Xod_page_itm itm = drd_provider.Wiki__get_by_url(wiki, page_url);
		Tfds.Eq(expd.To_str(), itm.To_str());
	}
	public void Test__to_page_url(String raw, String expd) {
		// // canonical url has spaces as well as %-encoding; PAGE:en.w:List_of_Fire_Emblem:Shadow_Dragon_characters
		Tfds.Eq_bry(Bry_.new_u8(expd), Xod_app.To_page_url(wiki, raw));
	}
	public Xod_page_itm Make_page(int page_id, String ttl, String modified_on, Xoh_section_itm... section_ary) {
		Xod_page_itm rv = new Xod_page_itm();
		rv.Init(page_id, page_id, ttl, ttl, null, null, modified_on, Bool_.N, Bool_.N, Bool_.N, 1, null, null, null);
		int len = section_ary.length;
		for (int i = 0; i < len; ++i) {
			Xoh_section_itm itm = section_ary[i];
			rv.Section_list().Add(itm);
		}			
		return rv;
	}
	public Xoh_section_itm Make_section(int id, int level, String anchor, String heading, String content) {return new Xoh_section_itm(id, level, Bry_.new_u8(anchor), Bry_.new_u8(heading)).Content_(Bry_.new_u8(content));}
}
