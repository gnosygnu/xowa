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
import org.junit.*; import gplx.core.tests.*;
public class Xow_domain_itm_tst {		
	private final    Xow_domain_fxt fxt = new Xow_domain_fxt();
	@Test  public void Parse_en_wikipedia() 			{fxt.Test_parse("en.wikipedia.org"				, "en"				, "en"	, Xow_domain_tid_.Tid__wikipedia);}
	@Test  public void Parse_fr_wikipedia() 			{fxt.Test_parse("fr.wikipedia.org"				, "fr"				, "fr"	, Xow_domain_tid_.Tid__wikipedia);}
	@Test  public void Parse_en_wiktionary() 			{fxt.Test_parse("en.wiktionary.org"				, "en"				, "en"	, Xow_domain_tid_.Tid__wiktionary);}
	@Test  public void Parse_zh_classical_wikipedia()	{fxt.Test_parse("zh-classical.wikipedia.org"	, "zh-classical"	, "lzh"	, Xow_domain_tid_.Tid__wikipedia);}
	@Test  public void Parse_commons() 					{fxt.Test_parse("commons.wikimedia.org"			, ""				, ""	, Xow_domain_tid_.Tid__commons);}
	@Test  public void Parse_species() 					{fxt.Test_parse("species.wikimedia.org"			, ""				, ""	, Xow_domain_tid_.Tid__species);}
	@Test  public void Parse_ru_wikimedia_org() 		{fxt.Test_parse("ru.wikimedia.org"				, "ru"				, "ru"	, Xow_domain_tid_.Tid__wikimedia);}
	@Test  public void Parse_home() 					{fxt.Test_parse("home"							, ""				, ""	, Xow_domain_tid_.Tid__home);}
	@Test  public void Parse_other() 					{fxt.Test_parse("other.wiki"					, ""				, ""	, Xow_domain_tid_.Tid__other);}
	@Test  public void Parse_ua_wikimedia_org() 		{fxt.Test_parse("ua.wikimedia.org"				, "ua"				, "uk"	, Xow_domain_tid_.Tid__wikimedia);}
	@Test  public void Parse_ar_wikimedia_org() 		{fxt.Test_parse("ar.wikimedia.org"				, "ar"				, "es"	, Xow_domain_tid_.Tid__wikimedia);}
	@Test  public void Parse_blank() 					{fxt.Test_parse(""								, ""				, ""	, Xow_domain_tid_.Tid__other);}
	@Test  public void Match_lang() {
		fxt.Test__match_lang_y("en", "en.wikipedia.org", "en.wiktionary.org", "simple.wikipedia.org", "species.wikimedia.org", "www.wikidata.org", "commons.wikimedia.org");
		fxt.Test__match_lang_y("fr", "fr.wikipedia.org", "fr.wiktionary.org");
		fxt.Test__match_lang_y("zh", "zh-classical.wikipedia.org");
	}
}
class Xow_domain_fxt {
	public void Test_parse(String domain, String expd_orig_lang, String expd_actl_lang, int expd_tid) {
		Xow_domain_itm actl = Xow_domain_itm_.parse(Bry_.new_a7(domain));
		Tfds.Eq_str(expd_orig_lang, String_.new_a7((actl.Lang_orig_key())));
		Tfds.Eq_str(expd_actl_lang, String_.new_a7((actl.Lang_actl_key())));
		Tfds.Eq_int(expd_tid, actl.Domain_type_id());
	}
	public void Test__match_lang_y(String lang_code, String... domains) {Test__match_lang(Bool_.Y, lang_code, domains);}
	public void Test__match_lang(boolean expd, String lang_key_str, String[] domains) {
		int len = domains.length;
		for (int i = 0; i < len; ++i) {
			Xow_domain_itm domain = Xow_domain_itm_.parse(Bry_.new_u8(domains[i]));
			Gftest.Eq__bool(expd, Xow_domain_itm_.Match_lang(domain, lang_key_str), lang_key_str + "|" + domains[i]);
		}
	}
}
