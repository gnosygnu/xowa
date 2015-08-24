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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_domain_itm_tst {		
	private final Xow_domain_fxt fxt = new Xow_domain_fxt();
	@Test  public void Parse_en_wikipedia() 			{fxt.Test_parse("en.wikipedia.org"				, "en"				, "en"	, Xow_domain_type_.Int__wikipedia);}
	@Test  public void Parse_fr_wikipedia() 			{fxt.Test_parse("fr.wikipedia.org"				, "fr"				, "fr"	, Xow_domain_type_.Int__wikipedia);}
	@Test  public void Parse_en_wiktionary() 			{fxt.Test_parse("en.wiktionary.org"				, "en"				, "en"	, Xow_domain_type_.Int__wiktionary);}
	@Test  public void Parse_zh_classical_wikipedia()	{fxt.Test_parse("zh-classical.wikipedia.org"	, "zh-classical"	, "lzh"	, Xow_domain_type_.Int__wikipedia);}
	@Test  public void Parse_commons() 					{fxt.Test_parse("commons.wikimedia.org"			, ""				, ""	, Xow_domain_type_.Int__commons);}
	@Test  public void Parse_species() 					{fxt.Test_parse("species.wikimedia.org"			, ""				, ""	, Xow_domain_type_.Int__species);}
	@Test  public void Parse_ru_wikimedia_org() 		{fxt.Test_parse("ru.wikimedia.org"				, "ru"				, "ru"	, Xow_domain_type_.Int__wikimedia);}
	@Test  public void Parse_home() 					{fxt.Test_parse("home"							, ""				, ""	, Xow_domain_type_.Int__home);}
	@Test  public void Parse_other() 					{fxt.Test_parse("other.wiki"					, ""				, ""	, Xow_domain_type_.Int__other);}
	@Test  public void Parse_ua_wikimedia_org() 		{fxt.Test_parse("ua.wikimedia.org"				, "ua"				, "uk"	, Xow_domain_type_.Int__wikimedia);}
	@Test  public void Parse_ar_wikimedia_org() 		{fxt.Test_parse("ar.wikimedia.org"				, "ar"				, "es"	, Xow_domain_type_.Int__wikimedia);}
}
class Xow_domain_fxt {
	public void Test_parse(String domain, String expd_orig_lang, String expd_actl_lang, int expd_tid) {
		Xow_domain_itm actl = Xow_domain_itm_.parse(Bry_.new_a7(domain));
		Tfds.Eq_str(expd_orig_lang, String_.new_a7((actl.Lang_orig_key())));
		Tfds.Eq_str(expd_actl_lang, String_.new_a7((actl.Lang_actl_key())));
		Tfds.Eq_int(expd_tid, actl.Domain_type_id());
	}
}
