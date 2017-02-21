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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_abrv_xo__tst {
	@Before public void init() {fxt.Clear();} private final    Xow_abrv_xo__fxt fxt = new Xow_abrv_xo__fxt();
	@Test  public void Basic() {
		fxt.Test("en.wikipedia.org"			, "en.w");			// multi.enwiki
		fxt.Test("fr.wiktionary.org"		, "fr.d");			// multi.frwiktionary
		fxt.Test("commons.wikimedia.org"	, "c");				// important.unique.commons
		fxt.Test("species.wikimedia.org"	, "species");		// important.unique.species
		fxt.Test("www.wikidata.org"			, "wd");			// important.unique.wikidata
		fxt.Test("home"						, "home");			// important.unique.xowa
		fxt.Test("meta.wikimedia.org"		, "meta");			// wikimedia.unique
		fxt.Test("pl.wikimedia.org"			, "pl.m");			// wikimedia.multi
		fxt.Test("a.b.c"					, "a.b.c");			// unkonwn
	}
}
class Xow_abrv_xo__fxt {
	public void Clear() {}
	public void Test(String domain_str, String expd_abrv) {
		Xow_domain_itm domain = Xow_domain_itm_.parse(Bry_.new_u8(domain_str));
		byte[] actl_abrv = Xow_abrv_xo_.To_bry(domain.Domain_bry(), domain.Lang_actl_key(), domain.Domain_type());
		Tfds.Eq(expd_abrv, String_.new_u8(actl_abrv), "To_bry");
		domain = Xow_abrv_xo_.To_itm(actl_abrv);
		Tfds.Eq(domain_str, domain.Domain_str(), "To_itm");
	}
}
