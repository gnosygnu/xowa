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
public class Xow_wiki_domain_tst {		
	@Before public void init() {fxt.Clear();} private Xow_wiki_domain_fxt fxt = new Xow_wiki_domain_fxt();
	@Test  public void Parse_en_wikipedia() 			{fxt.Test_parse("en.wikipedia.org", Xow_wiki_domain_.Tid_wikipedia, "en");}
	@Test  public void Parse_fr_wikipedia() 			{fxt.Test_parse("fr.wikipedia.org", Xow_wiki_domain_.Tid_wikipedia, "fr");}
	@Test  public void Parse_en_wiktionary() 			{fxt.Test_parse("en.wiktionary.org", Xow_wiki_domain_.Tid_wiktionary, "en");}
	@Test  public void Parse_zh_classical_wikipedia()	{fxt.Test_parse("zh-classical.wikipedia.org", Xow_wiki_domain_.Tid_wikipedia, "lzh");}
	@Test  public void Parse_commons() 					{fxt.Test_parse("commons.wikimedia.org", Xow_wiki_domain_.Tid_commons, "");}
	@Test  public void Parse_species() 					{fxt.Test_parse("species.wikimedia.org", Xow_wiki_domain_.Tid_species, "");}
	@Test  public void Parse_home() 					{fxt.Test_parse("home", Xow_wiki_domain_.Tid_home, "");}
	@Test  public void Parse_other() 					{fxt.Test_parse("other.wiki", Xow_wiki_domain_.Tid_other, "");}
}
class Xow_wiki_domain_fxt {
	public void Clear() {}
	public void Test_parse(String domain, byte expd_tid, String expd_lang) {
		Xow_wiki_domain actl = Xow_wiki_domain_.parse_by_domain(Bry_.new_ascii_(domain));
		Tfds.Eq(expd_tid, actl.Wiki_tid());
		Tfds.Eq(expd_lang, String_.new_ascii_((actl.Lang_key())));
	}
}
