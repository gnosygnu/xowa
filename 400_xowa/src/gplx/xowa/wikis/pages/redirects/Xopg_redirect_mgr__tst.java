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
package gplx.xowa.wikis.pages.redirects;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.xowa.*;
import org.junit.*;
import gplx.xowa.specials.*;
public class Xopg_redirect_mgr__tst {
	@Before public void init() {fxt.Clear();} private final Xopg_redirect_mgr__fxt fxt = new Xopg_redirect_mgr__fxt();
	@Test public void Basic() {
		fxt.Test__Itms__add__special(fxt.Make_meta("XowaTest"), KeyValUtl.AryEmpty, fxt.Make__itm("en.wikipedia.org/wiki/Special:XowaTest", "Special:XowaTest", null));
	}
	@Test public void Args() {
		fxt.Test__Itms__add__special
		( fxt.Make_meta("XowaTest"), KeyValUtl.Ary(KeyVal.NewStr("k1", "v1"), KeyVal.NewStr("k2", "v2"), KeyVal.NewStr("k3", "v3"))
		, fxt.Make__itm("en.wikipedia.org/wiki/Special:XowaTest?k1=v1&k2=v2&k3=v3", "Special:XowaTest", null)
		);
	}
}
class Xopg_redirect_mgr__fxt {
	private Xow_wiki wiki;
	private final Xopg_redirect_mgr mgr = new Xopg_redirect_mgr();
	public void Clear() {
		mgr.Clear();
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
	}
	public Xow_special_meta Make_meta(String key) {return Xow_special_meta.New_xo(key, "test display name");}
	public Xopg_redirect_itm Make__itm(String url_str, String ttl_str, String wikitext) {
		Xoa_url url = wiki.Utl__url_parser().Parse(BryUtl.NewU8(url_str));
		Xoa_ttl ttl = wiki.Ttl_parse(BryUtl.NewU8(ttl_str));
		return new Xopg_redirect_itm(url, ttl, BryUtl.NewU8Safe(wikitext));
	}
	public void Test__Itms__add__special(Xow_special_meta meta, KeyVal[] url_args, Xopg_redirect_itm expd) {
		mgr.Itms__add__special(wiki, meta, url_args);
		Xopg_redirect_itm actl = mgr.Itms__get_at(0);
		GfoTstr.Eq(expd.Ttl().Raw(), actl.Ttl().Raw(), "ttl");
		GfoTstr.Eq(expd.Url().To_str(), actl.Url().To_str(), "url");
		GfoTstr.Eq(expd.Wikitext(), actl.Wikitext(), "wikitext");
	}
}
