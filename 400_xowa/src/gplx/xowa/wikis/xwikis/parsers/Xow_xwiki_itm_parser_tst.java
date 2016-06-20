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
package gplx.xowa.wikis.xwikis.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import org.junit.*; import gplx.xowa.wikis.domains.*;
public class Xow_xwiki_itm_parser_tst {
	private final    Xow_xwiki_itm_parser_fxt fxt = new Xow_xwiki_itm_parser_fxt();
	@Test   public void Manual() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "0|a|https://a.org/~{0}|A"
		));
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "a|https://a.org/~{0}|A"
		));
	}
	@Test   public void Mw_domain() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "1|w|en.wikipedia.org|Wikipedia"
		));
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "w|https://en.wikipedia.org/wiki/~{0}|Wikipedia"
		));
	}
	@Test   public void Wm_peer() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "2|wikt|wiktionary|Wiktionary"
		));
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "wikt|https://en.wiktionary.org/wiki/~{0}|Wiktionary"
		));
	}
	@Test   public void Wm_lang() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "3|fr|fr|French"
		));
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "fr|https://fr.wikipedia.org/wiki/~{0}|French"
		));
	}
	@Test   public void Multiple() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "2|wikt;wiktionary|wiktionary|Wiktionary"
		));
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "wikt|https://en.wiktionary.org/wiki/~{0}|Wiktionary"
		, "wiktionary|https://en.wiktionary.org/wiki/~{0}|Wiktionary"
		));
	}
	@Test   public void Default_name() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "2|wikt|wiktionary|"
		));
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "wikt|https://en.wiktionary.org/wiki/~{0}|English Wiktionary"
		));
	}
}
class Xow_xwiki_itm_parser_fxt {
	private final    Xow_xwiki_itm_parser parser = new Xow_xwiki_itm_parser();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xow_xwiki_itm_parser_fxt() {
		parser.Init_by_wiki(Xow_domain_itm_.parse(Bry_.new_a7("en.wikipedia.org")));
	}
	public void Exec_parse(String raw) {
		byte[] src = Bry_.new_u8(raw);
		parser.Load_by_bry(src);
	}
	public void Test_parse(String expd) {
		Tfds.Eq_str_lines(expd, To_str());
	}
	private String To_str() {
		Ordered_hash list = parser.Xwiki_list();
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Xow_xwiki_itm itm = (Xow_xwiki_itm)list.Get_at(i);
			tmp_bfr.Add(itm.Key_bry()).Add_byte_pipe();
			tmp_bfr.Add(itm.Url_fmt()).Add_byte_pipe();
			tmp_bfr.Add(itm.Domain_name()).Add_byte_nl();
		}
		list.Clear();
		return tmp_bfr.To_str_and_clear();
	}
}
