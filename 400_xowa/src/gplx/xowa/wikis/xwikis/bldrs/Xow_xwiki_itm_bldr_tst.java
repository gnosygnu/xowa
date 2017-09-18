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
package gplx.xowa.wikis.xwikis.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.wikis.domains.*;
public class Xow_xwiki_itm_bldr_tst {
	@Before public void init() {fxt.Clear();} 	private final    Xow_xwiki_itm_bldr_fxt fxt = new Xow_xwiki_itm_bldr_fxt();
	@Test   public void Commons()				{fxt.Test_bld("commons.wikimedia.org/wiki/$1"	, "commons.wikimedia.org"	, "commons.wikimedia.org/wiki/~{0}"		, Xow_domain_tid_.Tid__commons		, Xol_lang_stub_.Id__unknown);}
	@Test   public void Wiktionary()			{fxt.Test_bld("fr.wiktionary.org/wiki/$1"		, "fr.wiktionary.org"		, "fr.wiktionary.org/wiki/~{0}"			, Xow_domain_tid_.Tid__wiktionary	, Xol_lang_stub_.Id_fr);}
	@Test   public void Lang()					{fxt.Test_bld("fr.wikipedia.org/wiki/$1"		, "fr.wikipedia.org"		, "fr.wikipedia.org/wiki/~{0}"			, Xow_domain_tid_.Tid__wikipedia	, Xol_lang_stub_.Id_fr);}
}
class Xow_xwiki_itm_bldr_fxt {
	private Xow_domain_itm domain_itm;
	public void Clear() {
		domain_itm = Xow_domain_itm.new_(Bry_.new_a7("en.wikivoyage.org"), Xow_domain_tid_.Tid__wikivoyage, Xol_lang_itm_.Key_en);	// NOTE: use "en.wikivoyage.org" to domain_name; needed for "Related sites"
	}
	public void Test_bld(String url, String expd_domain, String expd_url_fmt, int expd_wiki_tid, int expd_lang_tid) {
		Xow_xwiki_itm itm = Xow_xwiki_itm_bldr.Instance.Bld_mw(domain_itm, domain_itm.Domain_bry(), Bry_.new_u8(url), null);
		Tfds.Eq(expd_domain			, String_.new_u8(itm.Domain_bry()));
		Tfds.Eq(expd_url_fmt		, String_.new_u8(itm.Url_fmt()));
		Tfds.Eq(expd_wiki_tid		, itm.Domain_tid(), "wiki");
		Tfds.Eq(expd_lang_tid		, itm.Lang_id(), "lang");
	}
}
