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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xow_domain_tst {		
	@Before public void init() {fxt.Clear();} private final Xow_domain_fxt fxt = new Xow_domain_fxt();
	@Test  public void Parse_en_wikipedia() 			{fxt.Test_parse("en.wikipedia.org"				, "en"	, Xow_domain_.Tid_int_wikipedia);}
	@Test  public void Parse_fr_wikipedia() 			{fxt.Test_parse("fr.wikipedia.org"				, "fr"	, Xow_domain_.Tid_int_wikipedia);}
	@Test  public void Parse_en_wiktionary() 			{fxt.Test_parse("en.wiktionary.org"				, "en"	, Xow_domain_.Tid_int_wiktionary);}
	@Test  public void Parse_zh_classical_wikipedia()	{fxt.Test_parse("zh-classical.wikipedia.org"	, "lzh"	, Xow_domain_.Tid_int_wikipedia);}
	@Test  public void Parse_commons() 					{fxt.Test_parse("commons.wikimedia.org"			, ""	, Xow_domain_.Tid_int_commons);}
	@Test  public void Parse_species() 					{fxt.Test_parse("species.wikimedia.org"			, ""	, Xow_domain_.Tid_int_species);}
	@Test  public void Parse_ru_wikimedia_org() 		{fxt.Test_parse("ru.wikimedia.org"				, "ru"	, Xow_domain_.Tid_int_wikimedia);}
	@Test  public void Parse_home() 					{fxt.Test_parse("home"							, ""	, Xow_domain_.Tid_int_home);}
	@Test  public void Parse_other() 					{fxt.Test_parse("other.wiki"					, ""	, Xow_domain_.Tid_int_other);}
}
class Xow_domain_fxt {
	public void Clear() {}
	public void Test_parse(String domain, String expd_lang, int expd_tid) {
		Xow_domain actl = Xow_domain_.parse(Bry_.new_ascii_(domain));
		Tfds.Eq(expd_lang, String_.new_ascii_((actl.Lang_key())));
		Tfds.Eq(expd_tid, actl.Domain_tid());
	}
}
