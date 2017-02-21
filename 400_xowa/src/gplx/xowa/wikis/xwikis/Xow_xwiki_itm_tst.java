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
package gplx.xowa.wikis.xwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.wikis.domains.*;
public class Xow_xwiki_itm_tst {
	private final    Xow_xwiki_itm_fxt fxt = new Xow_xwiki_itm_fxt();
	@Test   public void Show_in_sitelangs__basic() {		// PURPOSE: basic test for show in "In other languages"; DATE:2015-11-06
		fxt.Test__type_is_xwiki_lang(Bool_.Y, fxt.Make__xwiki("en", "en.wikipedia.org/wiki/{0}", Xol_lang_stub_.Id_en, Xow_domain_tid_.Tid__wikipedia, "en.wikipedia.org", "enwiki"), "simple");
	}
	@Test   public void Show_in_sitelangs__lang_like() {	// PURPOSE: only consider xwikis with key similar to domain; EX: [[w:A]] in simplewiki; PAGE:Main_Page; DATE:2015-11-06
		fxt.Test__type_is_xwiki_lang(Bool_.N, fxt.Make__xwiki("w", "en.wikipedia.org/wiki/{0}", Xol_lang_stub_.Id_en, Xow_domain_tid_.Tid__wikipedia, "en.wikipedia.org", "enwiki"), "simple");
	}
	@Test   public void Show_in_sitelangs__same() {			// PURPOSE: same wiki should not appear in "In other languages"; DATE:2015-11-06
		fxt.Test__type_is_xwiki_lang(Bool_.N, fxt.Make__xwiki("en", "en.wikipedia.org/wiki/{0}", Xol_lang_stub_.Id_en, Xow_domain_tid_.Tid__wikipedia, "en.wikipedia.org", "enwiki"), "en");
	}
	@Test   public void Show_in_sitelangs__no_url_fmt() {	// PURPOSE: xwikis with no format should not appear in "In other languages"; DATE:2015-11-06
		fxt.Test__type_is_xwiki_lang(Bool_.N, fxt.Make__xwiki("en", "", Xol_lang_stub_.Id_en, Xow_domain_tid_.Tid__wikipedia, "en.wikipedia.org", "enwiki"), "simple");
	}
	@Test   public void Show_in_sitelangs__commons() {		// PURPOSE: commons should not appear in "In other languages"; DATE:2015-11-06
		fxt.Test__type_is_xwiki_lang(Bool_.N, fxt.Make__xwiki("c", "commons.wikimedia.org/wiki/{0}", Xol_lang_stub_.Id__intl, Xow_domain_tid_.Tid__commons, "commons.wikimedia.org", "commonswiki"), "en");
	}
	@Test   public void Show_in_sitelangs__nb() {			// PURPOSE: handle special wikis like nb, lzh, simple; EX: [[nb:]] -> no.w; PAGE:nn.w:; DATE:2015-12-04
		fxt.Test__type_is_xwiki_lang(Bool_.Y, fxt.Make__xwiki("nb", "no.wikipedia.org/wiki/{0}", Xol_lang_stub_.Id_no, Xow_domain_tid_.Tid__wikipedia, "no.wikipedia.org", "nbwiki"), "nn");
		fxt.Test__type_is_xwiki_lang(Bool_.Y, fxt.Make__xwiki("lzh", "zh-classical.wikipedia.org/wiki/{0}", Xol_lang_stub_.Id_zh, Xow_domain_tid_.Tid__wikipedia, "zh-classical.wikipedia.org", "lzwwiki"), "zh");
	}
}
class Xow_xwiki_itm_fxt {
	public Xow_xwiki_itm Make__xwiki(String key_bry, String url_fmt, int lang_id, int domain_tid, String domain_bry, String abrv_wm) {
		return Xow_xwiki_itm.new_(Bry_.new_u8(key_bry), Bry_.new_u8(url_fmt), lang_id, domain_tid, Bry_.new_u8(domain_bry), Bry_.new_u8(abrv_wm));
	}
	public void Test__type_is_xwiki_lang(boolean expd, Xow_xwiki_itm xwiki_itm, String cur_lang_key) {
		Tfds.Eq_bool(expd, xwiki_itm.Show_in_sitelangs(Bry_.new_u8(cur_lang_key)));
	}
}